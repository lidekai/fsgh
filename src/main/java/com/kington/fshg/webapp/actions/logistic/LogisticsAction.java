package com.kington.fshg.webapp.actions.logistic;

import java.io.UnsupportedEncodingException;
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
import com.kington.fshg.common.PublicType.ApproveState;
import com.kington.fshg.model.charge.InFeeVerificationVO;
import com.kington.fshg.model.info.Area;
import com.kington.fshg.model.info.Custom;
import com.kington.fshg.model.info.Store;
import com.kington.fshg.model.info.StoreProduct;
import com.kington.fshg.model.info.StoreProductStock;
import com.kington.fshg.model.info.StoreProductStockVO;
import com.kington.fshg.model.logistic.Logistics;
import com.kington.fshg.model.logistic.LogisticsVO;
import com.kington.fshg.model.system.User;
import com.kington.fshg.service.info.CustomService;
import com.kington.fshg.service.logistic.LogisticService;
import com.kington.fshg.service.system.UserService;
import com.kington.fshg.webapp.actions.BaseAction;
import com.kington.fshg.webapp.security.UserContext;

public class LogisticsAction extends BaseAction {
	private static final long serialVersionUID = 7751587449026637705L;
	
	@Resource
	private LogisticService logisticService;
	@Resource
	private UserService userService;
	@Resource
	private CustomService customService;
	
	private LogisticsVO vo;
	private String header;  //导出表头
	
	public String list(){
		try {
			if(vo == null)	vo = new LogisticsVO();
			if(StringUtils.isEmpty(vo.getOrderStartTime()))
				vo.setOrderStartTime(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
			if(StringUtils.isEmpty(vo.getOrderEndTime()))
				vo.setOrderEndTime(DateFormat.date2Str(new Date(), 2));
			vo.setPageNumber(page);
			checkRole();
			
			pageList = logisticService.getPageList(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	
	/**
	 * 生成物流费用
	 */
	public String add() throws Exception{
		Date orderTimeStart = DateFormat.getYearMonthFirst(new Date(), 0);
		Date orderTimeEnd = new Date();
		
		if(vo != null){
			if (StringUtils.isNotBlank(vo.getOrderStartTime()))
				orderTimeStart = DateFormat.str2Date(vo.getOrderStartTime(), 2);
			if (StringUtils.isNotBlank(vo.getOrderEndTime()))
				orderTimeEnd = DateFormat.str2Date(vo.getOrderEndTime(), 2);
		}
		
		int count = logisticService.createLogistic(orderTimeStart,orderTimeEnd);	
		logisticService.initLogDeliverFee(orderTimeStart, orderTimeEnd);
		this.addActionMessage("共导入" + count + "条记录");
		return list();
	}
	
	/**
	 * 删除物流费用
	 */
	public String deleteAll() throws Exception{
		
		int count = logisticService.deleteLogistic(vo);
		this.addActionMessage("共删除" + count + "条记录");
		return list();
	}
	
	public String edit() throws Exception{
		
		if(vo != null && Common.checkLong(vo.getId())){
			String start = vo.getOrderStartTime();
			String end = vo.getOrderEndTime();
			
			vo = logisticService.getVOById(vo.getId());
			vo.setOrderStartTime(start);
			vo.setOrderEndTime(end);
		}
		
		return Common.PATH_EDIT;
	}
	
	/**
	 * 执行审批操纵
	 * @return
	 */
	public String approve(){
		for(String id : ids.split(",")){
			try {
				LogisticsVO LVO = logisticService.getVOById(Long.parseLong(id));
				
				if(LVO.getApproveState() == ApproveState.DSP)//审批
					LVO.setApproveState(ApproveState.SPJS);
				else if(LVO.getApproveState() == ApproveState.SPJS)//反审
					LVO.setApproveState(ApproveState.DSP);
				logisticService.saveOrUpdate(LVO);
			} catch (Exception e) {
				e.printStackTrace();
				this.addActionError("操作失败！");
			}
		}
		this.addActionMessage("操作成功！");
		return list();
	}
	
	/**
	 * 删除
	 */
	public String delete() throws Exception {
		boolean success = false;
		try {
			int count = logisticService.clear(ids);

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
	
	/**
	 * 导出
	 * @throws Exception 
	 */
	public String export() throws Exception{
		setAttr("logisticsHeader",Common.getExportHeader("LOGISTICS"));
		String customName = new String(vo.getCustom().getCustomName().getBytes("ISO8859_1"), "UTF8");
		vo.getCustom().setCustomName(customName);
		String productName = new String(vo.getProduct().getProductName().getBytes("ISO8859_1"), "UTF8");
		vo.getProduct().setProductName(productName);
		return "export";
	}
	
	/**
	 * 执行导出操作
	 */
	public String  doExport() throws Exception{
		if(vo == null)	vo = new LogisticsVO();
		if(StringUtils.isEmpty(vo.getOrderStartTime()))
			vo.setOrderStartTime(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
		if(StringUtils.isEmpty(vo.getOrderEndTime()))
			vo.setOrderEndTime(DateFormat.date2Str(new Date(), 2));
		vo.setPageNumber(page);
		
		checkRole();
		
		vo.setObjectsPerPage(Integer.MAX_VALUE);
		List<Logistics> list = logisticService.getPageList(vo).getList();

		List<Integer> intArr = new ArrayList<Integer>();
		String[] heads = StringUtils.split(header, ",");
		for(int i =0; i< heads.length; i++){
			intArr.add(25);
		}
		
		List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
		Map<String ,String> map = null;
		
		for(Logistics o : list){
			map = new HashMap<String,String>();
			logisticService.exportLogistic(heads, o, map);
			listmap.add(map);
		}
		
		HttpServletResponse response = ServletActionContext.getResponse();
		String title = "物流费用信息表";
		Integer[] columnSize = intArr.toArray(new Integer[]{});
		ExcelUtil.export(response, listmap, "物流费用信息表.xls", title, heads, columnSize);
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
	public LogisticsVO getVo() {
		return vo;
	}

	public void setVo(LogisticsVO vo) {
		this.vo = vo;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}
}
