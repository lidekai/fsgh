package com.kington.fshg.webapp.actions.order;

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
import com.kington.fshg.common.PublicType.ActType;
import com.kington.fshg.model.info.Area;
import com.kington.fshg.model.order.SaleBill;
import com.kington.fshg.model.order.SaleBillVO;
import com.kington.fshg.model.system.User;
import com.kington.fshg.service.order.SaleBillService;
import com.kington.fshg.service.system.UserService;
import com.kington.fshg.webapp.actions.BaseAction;
import com.kington.fshg.webapp.security.UserContext;

public class SaleBillAction extends BaseAction {
	private static final long serialVersionUID = -2510531753909004974L;
	
	@Resource
	private SaleBillService saleBillService;
	
	@Resource
	private UserService userService;
	
	private SaleBillVO vo;
	
	private String header;  //表头信息
	
	
	
	public String list(){
		try {
		if(vo==null){
			vo=new SaleBillVO();
			vo.setBeginTime(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
			vo.setEndTime(DateFormat.date2Str(new Date(), 2));
			vo.setTimeType("1");
		}
			vo.setObjectsPerPage(Integer.MAX_VALUE);
			vo.setPageNumber(page);
			
			//用户角色判断
			UserContext uc = UserContext.get();
			User user = userService.getByCde(uc.getUserCode());
			String roleName = user.getRoles().get(0).getRoleName();
			if(StringUtils.equals(roleName, "业务员"))
				vo.setUserId(user.getId());
			else if(StringUtils.equals(roleName, "地区经理") || StringUtils.equals(roleName, "大区经理")){
				List<Long> areaList = new ArrayList<Long>();
				for(Area a : user.getAreas()){
					areaList.add(a.getId());
				}
				if(StringUtils.equals(roleName, "地区经理"))
					vo.setAreaIds(areaList);
				else if(StringUtils.equals(roleName, "大区经理"))
					vo.setParentAreaIds(areaList);
			}
		
			pageList=saleBillService.getPageList(vo);
			
			setAttr("sumList", saleBillService.countByVo(vo));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "list";
	}
	
	/**
	 * 导入U8发票数据
	 * @return
	 */
	public String imp(){
		try{
			int count=saleBillService.impSaleBillFromU8(vo);
			this.addActionMessage("共导入"+count+"条数据");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list();
	}
	
	/**
	 * 批量删除
	 * @return
	 */
	public String delete(){
		try {
			boolean success = false;
			int count = saleBillService.clear(ids);
			
			success = count > 0;
			if(success){
				String mess ="共删除" + count + "条数据";
				if(count != ids.split(",").length){
					mess +="," + (ids.split(",").length - count) + "条数据删除失败，可能存在关联不可删除！";
				}
				this.addActionMessage(mess);
			}else{
				this.addActionError("数据删除失败，可能存在关联不可删除！");
			}
		} catch (Exception e) {
			this.addActionError("数据删除失败，可能存在关联不可删除！");
		}
		return list();
	}
	
	
	public String edit(){
		try {
			if(vo != null && Common.checkLong(vo.getId())){
				vo = saleBillService.getVOById(vo.getId());
				if(act == null)
					setAct(ActType.EDIT);
			}else{
				this.addActionError("无效的操作ID");
				return list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "edit";
	}
	
	/**
	 * 导出销售发票
	 * @return
	 * @throws Exception 
	 */
	public String exportSaleBill() throws Exception{
		setAttr("saleBillHeader",Common.getExportHeader("SALEBILL"));
		if(StringUtils.isNotBlank(vo.getCustomName())){
			String name = new String(vo.getCustomName().getBytes("ISO8859_1"),"UTF8");
			vo.setCustomName(name);
		}
		if(StringUtils.isNotBlank(vo.getProductName())){
			String name = new String(vo.getProductName().getBytes("ISO8859_1"),"UTF8");
			vo.setProductName(name);
		}
		
		return "export";
	}
	
	/**
	 * 执行导出
	 * @return
	 * @throws Exception 
	 */
	public String doExport() throws Exception{
		if(vo==null){
			vo=new SaleBillVO();
			vo.setBeginTime(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
			vo.setEndTime(DateFormat.date2Str(new Date(), 2));
			vo.setTimeType("1");
		}
		vo.setObjectsPerPage(Integer.MAX_VALUE);
		//用户角色判断
		UserContext uc = UserContext.get();
		User user = userService.getByCde(uc.getUserCode());
		String roleName = user.getRoles().get(0).getRoleName();
		if(StringUtils.equals(roleName, "业务员"))
			vo.setUserId(user.getId());
		else if(StringUtils.equals(roleName, "地区经理") || StringUtils.equals(roleName, "大区经理")){
			List<Long> areaList = new ArrayList<Long>();
			for(Area a : user.getAreas()){
				areaList.add(a.getId());
			}
			if(StringUtils.equals(roleName, "地区经理"))
				vo.setAreaIds(areaList);
			else if(StringUtils.equals(roleName, "大区经理"))
				vo.setParentAreaIds(areaList);
		}
		List<SaleBill> list=saleBillService.saleBill(vo);
		List<Integer> intArr = new ArrayList<Integer>();
		String[] heads = StringUtils.split(header, ",");
		for(int i =0; i< heads.length; i++){
			intArr.add(25);
		}
		
		List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
		Map<String ,String> map = null;
		for(SaleBill saleBill : list){
			map = new HashMap<String,String>();
			saleBillService.exprotSaleBill(heads, saleBill, map);
			listmap.add(map);
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		String title = "销售发票表";
		Integer[] columnSize = intArr.toArray(new Integer[]{});
		ExcelUtil.export(response, listmap, "销售发票表.xls", title, heads, columnSize);
		return null;
	}
	
	public String isRebate(){
		int count = 0;
		String isType = ServletActionContext.getRequest().getParameter("isType");
		for(String str : ids.split(",")){
			try {
				if(saleBillService.updateRebate(Long.parseLong(str), isType)){
					count++;
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		this.addActionMessage("共更新"+ count + "条数据！");
		return list();
	}	
	
	
	
	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public SaleBillVO getVo() {
		return vo;
	}

	public void setVo(SaleBillVO vo) {
		this.vo = vo;
	}
	
	
}
