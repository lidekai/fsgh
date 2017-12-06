package com.kington.fshg.webapp.actions.info;

import java.io.File;
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
import com.kington.fshg.common.PublicType;
import com.kington.fshg.common.PublicType.ActType;
import com.kington.fshg.common.excel.ImportExcel;
import com.kington.fshg.common.excel.vo.StoreProductSaleExcelVO;
import com.kington.fshg.model.info.Area;
import com.kington.fshg.model.info.StoreProductSale;
import com.kington.fshg.model.info.StoreProductSaleVO;
import com.kington.fshg.model.system.User;
import com.kington.fshg.service.info.AreaService;
import com.kington.fshg.service.info.StoreProductSaleService;
import com.kington.fshg.service.info.StoreProductService;
import com.kington.fshg.service.system.UserService;
import com.kington.fshg.webapp.actions.BaseAction;
import com.kington.fshg.webapp.security.UserContext;

public class StoreProductSaleAction extends BaseAction {

	private static final long serialVersionUID = 6598663333632537438L;
	
	@Resource
	private StoreProductSaleService storeProductSaleService;
	@Resource
	private StoreProductService storeProductService;
	@Resource
	private UserService userService;
	@Resource
	private AreaService areaService;
	
	private StoreProductSaleVO vo;
	private File xlsfileupload;// 导入excel文件
	private String header;  //导出表头
	
	public String list(){
		try {
			if(vo == null)
				vo = new StoreProductSaleVO();
			vo.setPageNumber(page);
			
			checkRole();	
			
			pageList = storeProductSaleService.getPageList(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	
	/**
	 * 编辑
	 * @return
	 */
	public String edit(){
		try {
			if(vo != null && Common.checkLong(vo.getId())){
				vo = storeProductSaleService.getVOById(vo.getId());
				if(act == null)
					setAct(ActType.EDIT);
			}else{
				setAct(ActType.ADD);
			}
			
			if(vo == null)
				vo = new StoreProductSaleVO();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Common.PATH_EDIT;
	}
	
	/**
	 * 更新或添加数据
	 * @return
	 */
	public String update(){
		try {
			if(vo.getStoreProduct() != null && Common.checkLong(vo.getStoreProduct().getId()))
				vo.setStoreProduct(storeProductService.getById(vo.getStoreProduct().getId()));
			
			if(vo.getSaleCount() != null && vo.getStoreProduct() != null 
					&& vo.getStoreProduct().getProduct().getStandardPrice() != null)
				vo.setStandardSaleMoney(PublicType.setDoubleScale(vo.getSaleCount() * vo.getStoreProduct().getProduct().getStandardPrice()));
			storeProductSaleService.saveOrUpdate(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.addActionMessage("操作成功！");
		//vo = new StoreProductSaleVO();
		return list();
	}
	
	/**
	 * 批量删除
	 * @return
	 */
	public String delete(){
		boolean success = false;
		int count = 0;
		
		for(String id : ids.split(",")){
			try{
				if(storeProductSaleService.delete(Long.parseLong(id))) 
					count++;
			} catch (Exception e){
				continue;
			}
		}
		success = count > 0;
		
		if(success){
			String mess = "共删除"+ count +"条数据";
			if(count != ids.split(",").length)
				mess+=","+ (ids.split(",").length - count) + "条数据删除失败，可能存在关联不可删除！";
				
			this.addActionMessage(mess);
		}else{
			this.addActionError("删除数据失败,可能存在关联信息！");
		}
		
		return list();
	}
	
	/**
	 * 导入页面
	 * @return
	 */
	public String imp(){
		return "imp";
	}
	
	
	public String doImp(){
		try {
			ImportExcel<StoreProductSaleExcelVO> imp = new ImportExcel<StoreProductSaleExcelVO>(StoreProductSaleExcelVO.class);
			List<StoreProductSaleExcelVO> resultList = null;
			resultList = (ArrayList<StoreProductSaleExcelVO>) imp.importExcel(getXlsfileupload());
			
			if(resultList != null && resultList.size() > 0){
				setAttr("info", storeProductSaleService.doImports(resultList));
			}else{
				setAttr("info","没有可导入信息");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "imp";
	}
	
	/**
	 * 导出
	 * @throws Exception 
	 */
	public String export() throws Exception{
		setAttr("stoProSaleHeader",Common.getExportHeader("STOREPRODUCTSALE"));
		String customName = new String(vo.getStoreProduct().getStore().getCustom().getCustomName().getBytes("ISO8859_1"), "UTF8");
		vo.getStoreProduct().getStore().getCustom().setCustomName(customName);
		String storeName = new String(vo.getStoreProduct().getStore().getStoreName().getBytes("ISO8859_1"), "UTF8");
		vo.getStoreProduct().getStore().setStoreName(storeName);
		String productName = new String(vo.getStoreProduct().getProduct().getProductName().getBytes("ISO8859_1"), "UTF8");
		vo.getStoreProduct().getProduct().setProductName(productName);
		return "export";
	}
	
	/**
	 * 执行导出操作
	 */
	public String  doExport() throws Exception{
		if(vo == null)
			vo = new StoreProductSaleVO();
		
		checkRole();	
		vo.setObjectsPerPage(Integer.MAX_VALUE);
		List<StoreProductSale> list = 	storeProductSaleService.getPageList(vo).getList();

		List<Integer> intArr = new ArrayList<Integer>();
		String[] heads = StringUtils.split(header, ",");
		for(int i =0; i< heads.length; i++){
			intArr.add(25);
		}
		
		List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
		Map<String ,String> map = null;
		
		for(StoreProductSale o : list){
			map = new HashMap<String,String>();
			storeProductSaleService.export(heads, o, map);
			listmap.add(map);
		}
		
		HttpServletResponse response = ServletActionContext.getResponse();
		String title = "门店销售信息";
		Integer[] columnSize = intArr.toArray(new Integer[]{});
		ExcelUtil.export(response, listmap, "门店销售信息表.xls", title, heads, columnSize);
		return null;
	}
	
	public String compare() throws Exception{
		if(vo == null)
			vo = new StoreProductSaleVO();
		if(StringUtils.isEmpty(vo.getStartMonth()))
			vo.setStartMonth(DateFormat.date2Str(DateFormat.getBeforeMonthDay(), 10));
		if(StringUtils.isEmpty(vo.getEndMonth()))
			vo.setEndMonth(DateFormat.date2Str(new Date(), 10));
		
		checkRole();	
		setAttr("reslutList", storeProductSaleService.compare(vo));
		setAttr("parentAreas",areaService.getFristNode());
		if(Common.checkLong(vo.getParentAreaId()))
			setAttr("areas",areaService.getByParentId(vo.getParentAreaId()));
		return "compare";
	}
	
	/**
	 * 导出
	 * @throws Exception 
	 */
	public String exportCompare() throws Exception{
		setAttr("compareHeader",Common.getExportHeader("COMPARE"));
		String customName = new String(vo.getCustomName().getBytes("ISO8859_1"), "UTF8");
		vo.setCustomName(customName);
		String storeName = new String(vo.getStoreName().getBytes("ISO8859_1"), "UTF8");
		vo.setStoreName(storeName);
		String productName = new String(vo.getProductName().getBytes("ISO8859_1"), "UTF8");
		vo.setProductName(productName);
		return "exportCompare";
	}
	
	/**
	 * 执行导出操作
	 */
	public String  doExportCompare() throws Exception{
		if(vo == null)
			vo = new StoreProductSaleVO();
		if(StringUtils.isEmpty(vo.getStartMonth()))
			vo.setStartMonth(DateFormat.date2Str(DateFormat.getBeforeMonthDay(), 10));
		if(StringUtils.isEmpty(vo.getEndMonth()))
			vo.setEndMonth(DateFormat.date2Str(new Date(), 10));
		
		checkRole();	
		List<Object[]> resultList = storeProductSaleService.compare(vo);

		List<Integer> intArr = new ArrayList<Integer>();
		String[] heads = StringUtils.split(header, ",");
		for(int i =0; i< heads.length; i++){
			intArr.add(25);
		}
		
		List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
		Map<String ,String> map = null;
		
		for(Object[] o : resultList){
			map = new HashMap<String,String>();
			storeProductSaleService.exportCompare(heads, o, map);
			listmap.add(map);
		}
		
		HttpServletResponse response = ServletActionContext.getResponse();
		String title = "门店同期销售对比表";
		Integer[] columnSize = intArr.toArray(new Integer[]{});
		ExcelUtil.export(response, listmap, "门店同期销售对比表.xls", title, heads, columnSize);
		return null;
	}
	
	
	public String proportion() throws Exception{
		if(vo == null)
			vo = new StoreProductSaleVO();
		if(StringUtils.isEmpty(vo.getStartMonth()))
			vo.setStartMonth(DateFormat.date2Str(DateFormat.getBeforeMonthDay(), 10));
		if(StringUtils.isEmpty(vo.getEndMonth()))
			vo.setEndMonth(DateFormat.date2Str(new Date(), 10));
		
		checkRole();	
		setAttr("reslutList", storeProductSaleService.proportion(vo));
		setAttr("parentAreas",areaService.getFristNode());
		if(Common.checkLong(vo.getParentAreaId()))
			setAttr("areas",areaService.getByParentId(vo.getParentAreaId()));
		return "proportion";
	}
	
	/**
	 * 导出
	 * @throws Exception 
	 */
	public String exportProportion() throws Exception{
		setAttr("proportionHeader",Common.getExportHeader("PROPORTION"));
		String customName = new String(vo.getCustomName().getBytes("ISO8859_1"), "UTF8");
		vo.setCustomName(customName);
		String storeName = new String(vo.getStoreName().getBytes("ISO8859_1"), "UTF8");
		vo.setStoreName(storeName);
		String productName = new String(vo.getProductName().getBytes("ISO8859_1"), "UTF8");
		vo.setProductName(productName);
		return "exportProportion";
	}
	
	/**
	 * 执行导出操作
	 */
	public String  doExportProportion() throws Exception{
		if(vo == null)
			vo = new StoreProductSaleVO();
		if(StringUtils.isEmpty(vo.getStartMonth()))
			vo.setStartMonth(DateFormat.date2Str(DateFormat.getBeforeMonthDay(), 10));
		if(StringUtils.isEmpty(vo.getEndMonth()))
			vo.setEndMonth(DateFormat.date2Str(new Date(), 10));
		
		checkRole();	
		List<Object[]> resultList = storeProductSaleService.proportion(vo);

		List<Integer> intArr = new ArrayList<Integer>();
		String[] heads = StringUtils.split(header, ",");
		for(int i =0; i< heads.length; i++){
			intArr.add(25);
		}
		
		List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
		Map<String ,String> map = null;
		
		for(Object[] o : resultList){
			map = new HashMap<String,String>();
			storeProductSaleService.exportProportion(heads, o, map);
			listmap.add(map);
		}
		
		HttpServletResponse response = ServletActionContext.getResponse();
		String title = "门店销售占比表";
		Integer[] columnSize = intArr.toArray(new Integer[]{});
		ExcelUtil.export(response, listmap, "门店销售占比表.xls", title, heads, columnSize);
		return null;
	}
	
	public void checkRole() throws Exception{
		//角色判断
		UserContext uc = UserContext.get();
		User user = userService.getByCde(uc.getUserCode());
		String roleName = user.getRoles().get(0).getRoleName();
		if(StringUtils.equals(roleName, "业务员")){
			vo.setUserId(user.getId());
		}else if(StringUtils.equals(roleName, "地区经理") || StringUtils.equals(roleName, "大区经理")){
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
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public StoreProductSaleVO getVo() {
		return vo;
	}
	public void setVo(StoreProductSaleVO vo) {
		this.vo = vo;
	}
	public File getXlsfileupload() {
		return xlsfileupload;
	}
	public void setXlsfileupload(File xlsfileupload) {
		this.xlsfileupload = xlsfileupload;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	
}
