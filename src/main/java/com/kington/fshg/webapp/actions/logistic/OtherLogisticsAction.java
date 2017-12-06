package com.kington.fshg.webapp.actions.logistic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.DateFormat;
import com.kington.fshg.common.ExcelUtil;
import com.kington.fshg.common.JsUtils;
import com.kington.fshg.common.PublicType;
import com.kington.fshg.common.PublicType.ActType;
import com.kington.fshg.common.PublicType.ApproveState;
import com.kington.fshg.model.info.Area;
import com.kington.fshg.model.info.Custom;
import com.kington.fshg.model.logistic.LogisticsVO;
import com.kington.fshg.model.logistic.OtherLogistics;
import com.kington.fshg.model.logistic.OtherLogisticsVO;
import com.kington.fshg.model.system.User;
import com.kington.fshg.service.info.CustomService;
import com.kington.fshg.service.logistic.OtherLogisticsService;
import com.kington.fshg.service.system.UserService;
import com.kington.fshg.webapp.actions.BaseAction;
import com.kington.fshg.webapp.security.UserContext;

public class OtherLogisticsAction extends BaseAction {

	private static final long serialVersionUID = 4992405870318101259L;
	
	@Resource
	private OtherLogisticsService otherLogisticsService;
	@Resource
	private UserService userService;
	@Resource
	private CustomService customService;
	
	private OtherLogisticsVO vo;
	private String header;  //导出表头
	
	public String list(){
		try {
			if(vo == null)	vo = new OtherLogisticsVO();
			if(StringUtils.isEmpty(vo.getCreateStartTime()))
				vo.setCreateStartTime(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
			if(StringUtils.isEmpty(vo.getCreateEndTime()))
				vo.setCreateEndTime(DateFormat.date2Str(new Date(), 2));
			
			vo.setPageNumber(page);
			
			checkRole();
			
			pageList = otherLogisticsService.getPageList(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	
	public String edit(){
		try {
			if(vo != null && Common.checkLong(vo.getId())){
				String start = vo.getCreateStartTime();
				String end = vo.getCreateEndTime();
				
				vo = otherLogisticsService.getVOById(vo.getId());
				vo.setCreateStartTime(start);
				vo.setCreateEndTime(end);
				
				if(act == null)
					setAct(ActType.EDIT);
			}else{
				setAct(ActType.ADD);
			}
			
			if(vo == null)
				vo = new OtherLogisticsVO();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Common.PATH_EDIT;
	}
	
	public String update(){
		try {
			if(vo.getId() == null || !Common.checkLong(vo.getId())) 
				vo.setApproveState(ApproveState.DSP);
			
			Double otherCost = vo.getReturnGoods() + vo.getCost();//退货 + 促销费用
			if(vo.getWagesShare() != null)
				otherCost = otherCost + vo.getWagesShare();//工资分摊
			if(vo.getTransferCost() != null)
				otherCost = otherCost + vo.getTransferCost();//调拨费
			if(vo.getStorageShare() != null)
				otherCost = otherCost + vo.getStorageShare();//仓储分摊
			vo.setOtherLogisticsCost(PublicType.setDoubleScale(otherCost));//运费合计
			
			OtherLogistics po = otherLogisticsService.saveOrUpdate(vo);
			if(po != null)
				this.addActionMessage("操作成功！");
			else
				this.addActionError("操作失败！");
		} catch (Exception e) {
			return doException("操作失败！");
		}
		return list();
	}
	
	/**
	 * 执行审批操作
	 * @return
	 */
	public String approve(){
		for(String id : ids.split(",")){
			try {
				OtherLogisticsVO LVO = otherLogisticsService.getVOById(Long.parseLong(id));
				
				//根据当前的审批状态，更新为下一审批环节
				if(LVO.getApproveState() == ApproveState.DSP)//审批
					LVO.setApproveState(ApproveState.SPJS);
				else if(LVO.getApproveState() == ApproveState.SPJS)//反审
					LVO.setApproveState(ApproveState.DSP);
				otherLogisticsService.saveOrUpdate(LVO);
			} catch (Exception e) {
				e.printStackTrace();
				this.addActionError("操作失败！");
			}
		}
		
		this.addActionMessage("审批成功！");
		return list();
	}
	
	/**
	 * 删除
	 */
	public String delete() throws Exception {
		boolean success = false;
		try {
			int count = otherLogisticsService.clear(ids);

			success = count > 0;
			if (success) {
				String mesg = "共删除 " + count + "记录";
				if(ids.split(",").length != count)
					mesg += (ids.split(",").length - count) + "条记录未删除成功，可能数据已存在引用，不可删除";
				this.addActionMessage(mesg);
			} else {
				this.addActionError("数据删除失败,可能数据已存在引用，不可删除");
			}
			return list();
		} catch (Exception e) {
			return doException("数据删除失败,可能数据已存在引用");
		}
	}
	
	public String ftCost() throws Exception{
		if(vo == null)	vo = new OtherLogisticsVO();
		if(StringUtils.isEmpty(vo.getCreateStartTime()))
			vo.setCreateStartTime(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
		if(StringUtils.isEmpty(vo.getCreateEndTime()))
			vo.setCreateEndTime(DateFormat.date2Str(new Date(), 2));
		int success = otherLogisticsService.ftCost(vo);
		this.addActionMessage("共处理了" + success + "条记录");
		return list();
	}
	
	/**
	 * 判断用户是否当月已存在其他费用项目
	 */
	public void isExist(){
		String customId = ServletActionContext.getRequest().getParameter("customId");
		if(StringUtils.isNotBlank(customId)){
			try {
				String result = "";
				List<OtherLogistics> list = otherLogisticsService.getMonByCustom(Long.parseLong(customId));
				if(!Common.checkList(list) || (vo.getId() != null && vo.getId().equals(list.get(0).getId())))
					result = "0";
				else 
					result = "1";
				
				JsUtils.writeText(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 导出
	 * @throws Exception 
	 */
	public String export() throws Exception{
		setAttr("otherLogisticsHeader",Common.getExportHeader("OTHERLOGISTICS"));
		String customName = new String(vo.getCustom().getCustomName().getBytes("ISO8859_1"), "UTF8");
		vo.getCustom().setCustomName(customName);
		return "export";
	}
	
	/**
	 * 执行导出操作
	 */
	public String  doExport() throws Exception{
		if(vo == null)	vo = new OtherLogisticsVO();
		if(StringUtils.isEmpty(vo.getCreateStartTime()))
			vo.setCreateStartTime(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
		if(StringUtils.isEmpty(vo.getCreateEndTime()))
			vo.setCreateEndTime(DateFormat.date2Str(new Date(), 2));
		
		vo.setPageNumber(page);
		
		checkRole();
		
		vo.setObjectsPerPage(Integer.MAX_VALUE);
		List<OtherLogistics> list = otherLogisticsService.getPageList(vo).getList();

		List<Integer> intArr = new ArrayList<Integer>();
		String[] heads = StringUtils.split(header, ",");
		for(int i =0; i< heads.length; i++){
			intArr.add(25);
		}
		
		List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
		Map<String ,String> map = null;
		
		for(OtherLogistics o : list){
			map = new HashMap<String,String>();
			otherLogisticsService.export(heads, o, map);
			listmap.add(map);
		}
		
		HttpServletResponse response = ServletActionContext.getResponse();
		String title = "其他物流费用信息表";
		Integer[] columnSize = intArr.toArray(new Integer[]{});
		ExcelUtil.export(response, listmap, "其他物流费用信息表.xls", title, heads, columnSize);
		return null;
	}
	
	private void checkRole() throws Exception{
		//角色判断
		UserContext uc = UserContext.get();
		User user = userService.getByCde(uc.getUserCode());
		String roleName = user.getRoles().get(0).getRoleName();
		Custom custom = new Custom();
		if(StringUtils.equals(roleName, "业务员")){
			if(vo.getCustom() != null)
				custom = vo.getCustom();
			custom.setUser(user);
			vo.setCustom(custom);
		}else if(StringUtils.equals(roleName, "地区经理") || 
				StringUtils.equals(roleName, "大区经理") ){
			List<Long> areaList = new ArrayList<Long>();
			for(Area a : user.getAreas()){
				areaList.add(a.getId());
			}
			if(StringUtils.equals(roleName, "地区经理"))
				vo.setAreaIds(areaList);
			else if(StringUtils.equals(roleName, "大区经理"))
				vo.setParentAreaIds(areaList);
		}
		
	}

	public OtherLogisticsVO getVo() {
		return vo;
	}

	public void setVo(OtherLogisticsVO vo) {
		this.vo = vo;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

}
