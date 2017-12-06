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

import com.kington.fshg.common.Common;
import com.kington.fshg.common.DateFormat;
import com.kington.fshg.common.ExcelUtil;
import com.kington.fshg.common.ObjectUtil;
import com.kington.fshg.common.PublicType;
import com.kington.fshg.common.PublicType.ActType;
import com.kington.fshg.common.PublicType.ReceiveState;
import com.kington.fshg.model.account.CheckBill;
import com.kington.fshg.model.account.CheckBillVO;
import com.kington.fshg.model.account.CheckRecord;
import com.kington.fshg.model.account.CheckRecordVO;
import com.kington.fshg.model.account.ReceiveBill;
import com.kington.fshg.model.account.ReceiveBillVO;
import com.kington.fshg.model.info.Area;
import com.kington.fshg.model.system.User;
import com.kington.fshg.service.account.CheckBillService;
import com.kington.fshg.service.account.ReceiveBillService;
import com.kington.fshg.service.system.UserService;
import com.kington.fshg.webapp.actions.BaseAction;
import com.kington.fshg.webapp.security.UserContext;

public class CheckBillAction extends BaseAction {

	private static final long serialVersionUID = 8535228783351278895L;
	
	@Resource
	private CheckBillService checkBillService;
	@Resource
	private ReceiveBillService receiveBillService; 
	@Resource
	private UserService userService;
	
	private CheckBillVO vo;
	private ReceiveBillVO receiveBillVO;
	private CheckRecordVO checkRecordVO;
	private String header;  //表头信息
	
	public String list(){
		try {
			if(vo==null){
				vo=new CheckBillVO();
				vo.setBeginTime(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
				vo.setEndTime(DateFormat.date2Str(new Date(), 2));
			}
			vo.setObjectsPerPage(Integer.MAX_VALUE);
			vo.setPageNumber(page);
			
			//用户角色判断
			initUser();		
			pageList=checkBillService.getPageList(vo);
			
			setAttr("sumList", checkBillService.countByVo(vo));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "list";
	}
	
	public String hx(){
		try {
			if(vo != null && Common.checkLong(vo.getId())){
				String beginTime = vo.getBeginTime();
				String endTime = vo.getEndTime();
				
				vo = checkBillService.getVOById(vo.getId());
				
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
	
	public String hxReceive() throws Exception{
		try {
			if(receiveBillVO != null && Common.checkLong(receiveBillVO.getId())){
				receiveBillVO = receiveBillService.getVOById(receiveBillVO.getId());
			}else{
				this.addActionError("无效的操作ID");
				return list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "receive";
	}
	
	public String saveReceive() throws Exception{
		try {
			String receiptBillVOId = ServletActionContext.getRequest().getParameter("receiptBillVOId");
			if(receiveBillVO != null && Common.checkLong(receiveBillVO.getId()) 
					&& vo != null && Common.checkLong(vo.getId()) 
					&& StringUtils.isNotBlank(receiptBillVOId)){
				//保存核销记录
				receiveBillService.saveCheckRecord(receiveBillVO, Long.parseLong(receiptBillVOId), checkRecordVO);
				//处理应收单表头
				checkBillService.sumCount(vo.getId());
				//处理表头状态
				vo = checkBillService.getVOById(vo.getId());
				if(PublicType.setDoubleScale(vo.getReceivePrice()).equals(PublicType.setDoubleScale(vo.getCountPrice()))){
					vo.setState(ReceiveState.YHX);
					checkBillService.saveOrUpdate(vo);
				}
			}
			this.addActionMessage("操作成功");
			
		} catch (Exception e) {
			this.addActionError("操作失败");
			e.printStackTrace();
		}
		
		return hxReceive();
	}
	
	public String qxRecord() throws Exception{
		try {
			if(checkRecordVO != null && Common.checkLong(checkRecordVO.getId()) 
					&& vo != null && Common.checkLong(vo.getId())){
				//取消记录
				receiveBillService.qxCheckRecord(checkRecordVO.getId());
				//处理应收单表头
				checkBillService.sumCount(vo.getId());
			}
			this.addActionMessage("操作成功");
			
		} catch (Exception e) {
			this.addActionError("操作失败");
			e.printStackTrace();
		}
		
		return hxReceive();
	}
	
	/**
	 * 导出待核单
	 * @return
	 * @throws Exception 
	 */
	public String exportCheckBill() throws Exception{
		setAttr("checkBillHeader",Common.getExportHeader("CHECKBILL"));
		if(StringUtils.isNotBlank(vo.getCustomName())){
			String name = new String(vo.getCustomName().getBytes("ISO8859_1"),"UTF8");
			vo.setCustomName(name);
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
			vo=new CheckBillVO();
			vo.setBeginTime(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
			vo.setEndTime(DateFormat.date2Str(new Date(), 2));
		}
		vo.setObjectsPerPage(Integer.MAX_VALUE);
		//用户角色判断
		initUser();		
		
		List<CheckBill> list = checkBillService.checkBill(vo);
		List<Integer> intArr = new ArrayList<Integer>();
		String[] heads = StringUtils.split(header, ",");
		for(int i =0; i< heads.length; i++){
			intArr.add(25);
		}
		
		List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
		Map<String ,String> map = null;
		for(CheckBill checkBill : list){
			map = new HashMap<String,String>();
			checkBillService.exprotCheckBill(heads, checkBill, map);
			listmap.add(map);
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		String title = "待核单";
		Integer[] columnSize = intArr.toArray(new Integer[]{});
		ExcelUtil.export(response, listmap, "待核单.xls", title, heads, columnSize);
		return null;
	}
	
	public String statBill() throws Exception{
		if(vo==null){
			vo=new CheckBillVO();
			vo.setBeginTime(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
			vo.setEndTime(DateFormat.date2Str(new Date(), 2));
		}
		//用户角色判断
		initUser();		
		
		setAttr("statList", checkBillService.statBill(vo));
		return "stat";
	}
	
	/**
	 * 导出待核单余额表
	 * @return
	 * @throws Exception 
	 */
	public String exportStatBill() throws Exception{
		setAttr("statBillHeader",Common.getExportHeader("STATBILL"));
		if(StringUtils.isNotBlank(vo.getCustomName())){
			String name = new String(vo.getCustomName().getBytes("ISO8859_1"),"UTF8");
			vo.setCustomName(name);
		}
		
		return "exportStat";
	}
	
	/**
	 * 执行导出
	 * @return
	 * @throws Exception 
	 */
	public String doExportStat() throws Exception{
		if(vo==null){
			vo=new CheckBillVO();
			vo.setBeginTime(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
			vo.setEndTime(DateFormat.date2Str(new Date(), 2));
		}
		//用户角色判断
		initUser();		
		
		List<Object[]> list = checkBillService.statBill(vo);
		List<Integer> intArr = new ArrayList<Integer>();
		String[] heads = StringUtils.split(header, ",");
		for(int i =0; i< heads.length; i++){
			intArr.add(25);
		}
		
		List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
		Map<String ,String> map = null;
		for(Object[] object : list){
			map = new HashMap<String,String>();
			checkBillService.exprotStatBill(heads, object, map);
			listmap.add(map);
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		String title = "待核单表头余额表";
		Integer[] columnSize = intArr.toArray(new Integer[]{});
		ExcelUtil.export(response, listmap, "待核单表头余额表.xls", title, heads, columnSize);
		return null;
	}
	
	public void initUser() throws Exception{
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
	}
	
	
	/**
	 * 删除核单管理  (只有状态全是已取消的才能删)
	 */
	public String delete(){	
		boolean success = false;
		int count=0;
		
		String cbIds="";
		try {
			for(String id:ids.split(",")){	
				boolean candelete=true;
				CheckBillVO cb=checkBillService.getVOById(Long.parseLong(id));
				if(cb!=null&&cb.getReceiveList()!=null){					
					for(ReceiveBill rb:cb.getReceiveList()){						
						for(CheckRecord cr:rb.getRecordList()){					
							if(ReceiveState.YQX!=cr.getState()){
								//只有状态全部都是已取消的才能删除 待核单记录
								candelete = false;
								break;			
							}
						}
						if(candelete){
							rb.setCheckBill(null);
							rb.setChargePrice(rb.getCountPrice1());
							rb.setReceivePrice(0.0);
							rb.setActualPrice(0.0);
							rb.setReturnPrice(0.0);
							rb.setHoldPrice(0.0);
							rb.setOtherPrice(0.0);
							rb.setState(ReceiveState.WCL);
							receiveBillService.saveOrUpdate(ObjectUtil.copy(rb, ReceiveBillVO.class));
						}
						
					}
				}
				if(candelete){
					
					cbIds+=id+",";
				}
			}
			//System.out.println(cbIds);
			if(StringUtils.isNotBlank(cbIds)){
				 count = checkBillService.clear(cbIds);
			}
		

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
	

	public CheckBillVO getVo() {
		return vo;
	}

	public void setVo(CheckBillVO vo) {
		this.vo = vo;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public ReceiveBillVO getReceiveBillVO() {
		return receiveBillVO;
	}

	public void setReceiveBillVO(ReceiveBillVO receiveBillVO) {
		this.receiveBillVO = receiveBillVO;
	}

	public CheckRecordVO getCheckRecordVO() {
		return checkRecordVO;
	}

	public void setCheckRecordVO(CheckRecordVO checkRecordVO) {
		this.checkRecordVO = checkRecordVO;
	}
	
}
