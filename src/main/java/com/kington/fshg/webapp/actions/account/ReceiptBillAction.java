package com.kington.fshg.webapp.actions.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.jtframework.websupport.pagination.PageList;
import com.kington.fshg.common.Common;
import com.kington.fshg.common.DateFormat;
import com.kington.fshg.common.ExcelUtil;
import com.kington.fshg.common.PublicType.ActType;
import com.kington.fshg.model.account.ReceiptBill;
import com.kington.fshg.model.account.ReceiptBillVO;
import com.kington.fshg.model.info.Area;
import com.kington.fshg.model.system.User;
import com.kington.fshg.service.account.ReceiptBillService;
import com.kington.fshg.service.system.UserService;
import com.kington.fshg.webapp.actions.BaseAction;
import com.kington.fshg.webapp.security.UserContext;

public class ReceiptBillAction extends BaseAction {

	private static final long serialVersionUID = -7492293362661861560L;
	
	@Resource
	private ReceiptBillService receiptBillService;
	@Resource
	private UserService userService;
	
	private ReceiptBillVO vo;
	private String header;  //表头信息
	
	public String list(){
		try {
			if(vo==null){
				vo=new ReceiptBillVO();
				vo.setBeginTime(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
				vo.setEndTime(DateFormat.date2Str(new Date(), 2));
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
		
			pageList=receiptBillService.getPageList(vo);
			
			setAttr("sumList", receiptBillService.countByVo(vo));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "list";
	}
	
	/**
	 * 导入U8收款单
	 * @return
	 */
	public String imp(){
		try{
			int count=receiptBillService.impReceiptBillFromU8(vo);
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
			int count = receiptBillService.clear(ids);
			
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
				String beginTime = vo.getBeginTime();
				String endTime = vo.getEndTime();
				
				vo = receiptBillService.getVOById(vo.getId());
				
				vo.setBeginTime(beginTime);
				vo.setEndTime(endTime);
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
	
	public String update(){
		try {
			receiptBillService.saveOrUpdate(vo);
			this.addActionMessage("操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.addActionError("操作失败！");
		}
		return list();
	}
	
	/**
	 * 导出销售发票
	 * @return
	 * @throws Exception 
	 */
	public String exportReceiptBill() throws Exception{
		setAttr("receiptBillHeader",Common.getExportHeader("RECEIPTBILL"));
		if(StringUtils.isNotBlank(vo.getCustomerName())){
			String name = new String(vo.getCustomerName().getBytes("ISO8859_1"),"UTF8");
			vo.setCustomerName(name);
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
			vo=new ReceiptBillVO();
			vo.setBeginTime(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
			vo.setEndTime(DateFormat.date2Str(new Date(), 2));
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
		List<ReceiptBill> list=receiptBillService.receiptBill(vo);
		List<Integer> intArr = new ArrayList<Integer>();
		String[] heads = StringUtils.split(header, ",");
		for(int i =0; i< heads.length; i++){
			intArr.add(25);
		}
		
		List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
		Map<String ,String> map = null;
		for(ReceiptBill receiptBill : list){
			map = new HashMap<String,String>();
			receiptBillService.exprotReceiptBill(heads, receiptBill, map);
			listmap.add(map);
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		String title = "收款单";
		Integer[] columnSize = intArr.toArray(new Integer[]{});
		ExcelUtil.export(response, listmap, "收款单.xls", title, heads, columnSize);
		return null;
	}
	
	public String selectReceiptBill(){
		if(StringUtils.isNotBlank(vo.getCustomerCde())){
			try {
				pageList = receiptBillService.getPageList(vo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "select";
	}
	
	public ReceiptBillVO getVo() {
		return vo;
	}
	public void setVo(ReceiptBillVO vo) {
		this.vo = vo;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}

}
