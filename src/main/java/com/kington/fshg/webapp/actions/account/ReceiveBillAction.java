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
import com.kington.fshg.common.JsUtils;
import com.kington.fshg.common.PublicType.ActType;
import com.kington.fshg.common.PublicType.IsType;
import com.kington.fshg.common.PublicType.ReceiveState;
import com.kington.fshg.model.account.CheckBill;
import com.kington.fshg.model.account.CheckBillVO;
import com.kington.fshg.model.account.ReceiptBill;
import com.kington.fshg.model.account.ReceiptBillVO;
import com.kington.fshg.model.account.ReceiveBill;
import com.kington.fshg.model.account.ReceiveBillVO;
import com.kington.fshg.model.info.Area;
import com.kington.fshg.model.system.User;
import com.kington.fshg.service.account.CheckBillService;
import com.kington.fshg.service.account.ReceiptBillService;
import com.kington.fshg.service.account.ReceiveBillService;
import com.kington.fshg.service.info.AreaService;
import com.kington.fshg.service.info.CustomsTypeService;
import com.kington.fshg.service.system.UserService;
import com.kington.fshg.webapp.actions.BaseAction;
import com.kington.fshg.webapp.security.UserContext;

public class ReceiveBillAction extends BaseAction {

	private static final long serialVersionUID = 3731324449533417668L;
	
	@Resource
	private ReceiveBillService receiveBillService;
	@Resource
	private UserService userService;
	@Resource
	private ReceiptBillService receiptBillService;
	@Resource
	private CheckBillService checkBillService;
	@Resource
	private AreaService areaService;
	@Resource
	private CustomsTypeService customsTypeService;
	
	private ReceiveBillVO vo;
	private ReceiptBillVO receiptVO;
	private CheckBillVO cvo; 
	private String header;  //表头信息
	private PageList<?> pageList1;
	private IsType isType;
	private String receiptIds;
	private String receiveIds;
	
	public String list(){
		try {
			if(vo==null){
				vo=new ReceiveBillVO();
				vo.setBeginTime(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
				vo.setEndTime(DateFormat.date2Str(new Date(), 2));
			}
			vo.setObjectsPerPage(Integer.MAX_VALUE);
			vo.setPageNumber(page);
			//用户角色判断
			initUser();
			
			if(act == ActType.COUNT)//统计表
				vo.initMyQueryStr(" and o.state <> 'WCL' ");
			
			vo.initMyOrderStr(" order by csbvcode ");
		
			pageList=receiveBillService.getPageList(vo);
			setAttr("sumList", receiveBillService.countByVo(vo, act));
			setAttr("areas", areaService.getLeafNode());
			
			if(act == ActType.COUNT)//统计表
				return "count";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "list";
	}
	
	/**
	 * 导入U8应收单
	 * @return
	 */
	public String imp(){
		try{
			int count=receiveBillService.impReceiveBillFromU8(vo);
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
			int count = receiveBillService.clear(ids);
			
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
	
	
	/**
	 * 生成待核单
	 * @return
	 */
	public String createCheck(){
		
		try {
			boolean success = receiveBillService.createCheck(ids);
			if(success)	this.addActionMessage("操作成功");
			else this.addActionError("操作失败");
		} catch (Exception e) {
			this.addActionError("操作失败");
			e.printStackTrace();
		}
		return list();
	}
	
	public String edit(){
		try {
			if(vo != null && Common.checkLong(vo.getId())){
				String timeType = vo.getTimeType();
				String beginTime = vo.getBeginTime();
				String endTime = vo.getEndTime();
				
				vo = receiveBillService.getVOById(vo.getId());
				
				vo.setTimeType(timeType);
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
			receiveBillService.saveOrUpdate(vo);
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
	public String exportReceiveBill() throws Exception{
		setAttr("receiveBillHeader",Common.getExportHeader("RECEIVEBILL"));
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
			vo=new ReceiveBillVO();
			vo.setBeginTime(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
			vo.setEndTime(DateFormat.date2Str(new Date(), 2));
		}
		vo.setObjectsPerPage(Integer.MAX_VALUE);
		//用户角色判断
		initUser();
		
		String title = "应收单";
		if(act == ActType.COUNT){
			vo.initMyQueryStr(" and o.state <> 'WCL'");
			title = "待核单明细余额表";
		}
		
		List<ReceiveBill> list=receiveBillService.receiveBill(vo);
		List<Integer> intArr = new ArrayList<Integer>();
		String[] heads = StringUtils.split(header, ",");
		for(int i =0; i< heads.length; i++){
			intArr.add(25);
		}
		
		List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
		Map<String ,String> map = null;
		for(ReceiveBill receiveBill : list){
			map = new HashMap<String,String>();
			receiveBillService.exprotReceiveBill(heads, receiveBill, map);
			listmap.add(map);
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		Integer[] columnSize = intArr.toArray(new Integer[]{});
		ExcelUtil.export(response, listmap, title + ".xls", title, heads, columnSize);
		return null;
	}
	
	public String statAccount() throws Exception{
		if(vo == null){
			vo=new ReceiveBillVO();
			vo.setBeginTime(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
			vo.setEndTime(DateFormat.date2Str(new Date(), 2));
			
			vo.setStatTime(vo.getEndTime());
		}
		initUser();
		
		setAttr("statList", receiveBillService.statAccount(vo));
		return "statAccount";
	}
	
	/**
	 * 导出账期统计表
	 * @return
	 * @throws Exception 
	 */
	public String exportStatAccount() throws Exception{
		setAttr("statAccountHeader",Common.getExportHeader("STATACCOUNT"));
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
		if(vo == null){
			vo=new ReceiveBillVO();
			vo.setBeginTime(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
			vo.setEndTime(DateFormat.date2Str(new Date(), 2));
			vo.setTimeType("1");
			
			vo.setStatTime(vo.getEndTime());
		}
		//用户角色判断
		initUser();
		
		List<Object[]> list = receiveBillService.statAccount(vo);
		List<Integer> intArr = new ArrayList<Integer>();
		String[] heads = StringUtils.split(header, ",");
		for(int i =0; i< heads.length; i++){
			intArr.add(25);
		}
		
		List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
		Map<String ,String> map = null;
		for(Object[] object : list){
			map = new HashMap<String,String>();
			receiveBillService.exprotStatAccount(heads, object, map);
			listmap.add(map);
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		String title = "账期统计表";
		Integer[] columnSize = intArr.toArray(new Integer[]{});
		ExcelUtil.export(response, listmap, "账期统计表.xls", title, heads, columnSize);
		return null;
	}
	
	public void initUser() throws Exception{
		//用户角色判断
		UserContext uc = UserContext.get();
		User user = userService.getByCde(uc.getUserCode());
		String roleName = user.getRoles().get(0).getRoleName();
		if(StringUtils.equals(roleName, "业务员")){
			vo.setUserId(user.getId());
			
			if(receiptVO != null)
				receiptVO.setUserId(user.getId());
		}else if(StringUtils.equals(roleName, "地区经理") || StringUtils.equals(roleName, "大区经理")){
			List<Long> areaList = new ArrayList<Long>();
			for(Area a : user.getAreas()){
				areaList.add(a.getId());
			}
			if(StringUtils.equals(roleName, "地区经理")){
				vo.setAreaIds(areaList);
				
				if(receiptVO != null)
					receiptVO.setAreaIds(areaList);
			}else if(StringUtils.equals(roleName, "大区经理")){
				vo.setParentAreaIds(areaList);
				
				if(receiptVO != null)
					receiptVO.setParentAreaIds(areaList);
				
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public String autoHx() throws Exception{
	//	hx();
		hx2();
		
		try{
			if(act == ActType.AUTOHX){
				//hxBach((List<ReceiveBill>)pageList.getList(), (List<ReceiptBill>)pageList1.getList());
				List<ReceiveBill> rblist=new ArrayList<ReceiveBill>();
				List<CheckBill> cblist=(List<CheckBill>) pageList.getList();
				for(CheckBill cb:cblist){
					rblist.addAll(cb.getReceiveList());
				}
				hxBach(rblist, (List<ReceiptBill>)pageList1.getList());
				
			}
			if(act == ActType.XZHX && StringUtils.isNotBlank(receiveIds) && StringUtils.isNotBlank(receiptIds)){
				hxBach(receiveBillService.getListByIds(receiveIds),receiptBillService.getListByIds(receiptIds));
			}
		}catch(Exception e){
			this.addActionError("操作失败");
			e.printStackTrace();
		}
		return "autoHx";
	}
	
	private void hxBach(List<ReceiveBill> receiveList,List<ReceiptBill> receiptList) throws Exception{
		int count = 0;
		count = receiveBillService.autoHx(receiveList, receiptList, isType);
		//处理表头
		Long checkBillId = 0L;
		for(ReceiveBill bill : receiveList){
			Long billId = receiveBillService.getById(bill.getId()).getCheckBill().getId();
			if(!checkBillId.equals(billId)){
				checkBillId = billId;
				//处理应收单表头
				checkBillService.sumCount(checkBillId);
				
				//处理表头状态
				checkBillService.updateState(checkBillId);
			}
		}
		this.addActionMessage("共核销了" + count + "次");
		hx2();
	}
	
	private void hx() throws Exception{
		if(vo == null){
			vo = new ReceiveBillVO();
			vo.setBeginTime(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
			vo.setEndTime(DateFormat.date2Str(new Date(), 2));
			vo.setTimeType("1");
		}else{
			if(vo.getTimeType() == null)
				vo.setTimeType("1");
			if(StringUtils.isBlank(vo.getBeginTime()))
				vo.setBeginTime(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
			if(StringUtils.isBlank(vo.getEndTime()))
				vo.setEndTime(DateFormat.date2Str(new Date(), 2));
			
			vo.setState(ReceiveState.DHX);
			
			String orderby = " order by o.customCde ";
			if(StringUtils.equals(vo.getTimeType(), "1")){
				orderby += " , o.saleDate ";
			}else if(StringUtils.equals(vo.getTimeType(), "2")){
				orderby += " , o.deliverDate ";
			}else if(StringUtils.equals(vo.getTimeType(), "3")){
				orderby += " , o.createDate ";
			}else if(StringUtils.equals(vo.getTimeType(), "4")){
				orderby += " , o.maturityDate ";
			}
			vo.initMyOrderStr(orderby);
				
			initUser();
			vo.setObjectsPerPage(Integer.MAX_VALUE);
			vo.setPageNumber(page);
			
			pageList=receiveBillService.getPageList(vo);
		}
		
		if(receiptVO == null){
			receiptVO=new ReceiptBillVO();
			receiptVO.setBeginTime(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
			receiptVO.setEndTime(DateFormat.date2Str(new Date(), 2));
		}else{
			if(StringUtils.isBlank(receiptVO.getBeginTime()))
				receiptVO.setBeginTime(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
			if(StringUtils.isBlank(receiptVO.getEndTime()))
				receiptVO.setEndTime(DateFormat.date2Str(new Date(), 2));
			
			initUser();
			receiptVO.setObjectsPerPage(Integer.MAX_VALUE);
			receiptVO.setPageNumber(page);
			
			receiptVO.initMyOrderStr(" order by o.customerCde, o.receiptDate");
			pageList1 = receiptBillService.getPageList(receiptVO);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
		}
		
		setAttr("customsTypes",customsTypeService.getLeafNode());
	}
	
	/**
	 * hx2
	 * @return
	 */
	private void hx2() throws Exception{
		if(cvo==null){
			cvo=new CheckBillVO();
			cvo.setBeginTime(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
			cvo.setEndTime(DateFormat.date2Str(new Date(), 2));
		}else{
			if(StringUtils.isBlank(cvo.getBeginTime()))
				cvo.setBeginTime(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
			if(StringUtils.isBlank(cvo.getEndTime()))
				cvo.setEndTime(DateFormat.date2Str(new Date(), 2));
			initUser();
			cvo.setState(ReceiveState.DHX);
			cvo.setObjectsPerPage(Integer.MAX_VALUE);
			cvo.setPageNumber(page);
			
			cvo.initMyOrderStr(" order by o.customCde,o.createDate");
			pageList=checkBillService.getPageList(cvo);
		}
		
		
		if(receiptVO == null){
			receiptVO=new ReceiptBillVO();
			receiptVO.setBeginTime(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
			receiptVO.setEndTime(DateFormat.date2Str(new Date(), 2));
		}else{
			if(StringUtils.isBlank(receiptVO.getBeginTime()))
				receiptVO.setBeginTime(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
			if(StringUtils.isBlank(receiptVO.getEndTime()))
				receiptVO.setEndTime(DateFormat.date2Str(new Date(), 2));
			
			initUser();
			receiptVO.setObjectsPerPage(Integer.MAX_VALUE);
			receiptVO.setPageNumber(page);
			
			receiptVO.initMyOrderStr(" order by o.customerCde, o.receiptDate");
			pageList1 = receiptBillService.getPageList(receiptVO);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
		}
		
		setAttr("customsTypes",customsTypeService.getLeafNode());
	}
	
	/**
	 * 根据待核单ID查找对应的发票Id以SJON格式返回前台
	 * @throws Exception 
	 */
	public void getreceiveids() throws  Exception{
		String ids="";
		String checkId=ServletActionContext.getRequest().getParameter("checkbillIds");
		if(StringUtils.isBlank(checkId)){
			return;
		}
	//	System.out.println("~~~~~"+checkId);
		String[] cidarr=checkId.split(",");
		for(String id:cidarr){
			List<ReceiveBill> rb=checkBillService.getById(Long.parseLong(id)).getReceiveList();
			for(ReceiveBill r:rb ){
				ids+=r.getId()+",";
			}
		}
		
	//	System.out.println("!!!!!!!!!!!!"+ids);
		JsUtils.writeJson("ids", ids);
		
	}
	
	public ReceiptBillVO getReceiptVO() {
		return receiptVO;
	}

	public void setReceiptVO(ReceiptBillVO receiptVO) {
		this.receiptVO = receiptVO;
	}

	public ReceiveBillVO getVo() {
		return vo;
	}

	public void setVo(ReceiveBillVO vo) {
		this.vo = vo;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public PageList<?> getPageList1() {
		return pageList1;
	}

	public IsType getIsType() {
		return isType;
	}

	public void setIsType(IsType isType) {
		this.isType = isType;
	}

	public String getReceiptIds() {
		return receiptIds;
	}

	public void setReceiptIds(String receiptIds) {
		this.receiptIds = receiptIds;
	}

	public String getReceiveIds() {
		return receiveIds;
	}

	public void setReceiveIds(String receiveIds) {
		this.receiveIds = receiveIds;
	}

	public CheckBillVO getCvo() {
		return cvo;
	}

	public void setCvo(CheckBillVO cvo) {
		this.cvo = cvo;
	}
	
}
