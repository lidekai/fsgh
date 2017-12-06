package com.kington.fshg.webapp.actions.info;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.ExcelUtil;
import com.kington.fshg.common.PublicType;
import com.kington.fshg.common.PublicType.ActType;
import com.kington.fshg.common.excel.ImportExcel;
import com.kington.fshg.common.excel.vo.StoreProductStockExcelVO;
import com.kington.fshg.model.info.Area;
import com.kington.fshg.model.info.Custom;
import com.kington.fshg.model.info.Store;
import com.kington.fshg.model.info.StoreProduct;
import com.kington.fshg.model.info.StoreProductStock;
import com.kington.fshg.model.info.StoreProductStockVO;
import com.kington.fshg.model.system.User;
import com.kington.fshg.service.info.CustomService;
import com.kington.fshg.service.info.StoreProductService;
import com.kington.fshg.service.info.StoreProductStockService;
import com.kington.fshg.service.system.UserService;
import com.kington.fshg.webapp.actions.BaseAction;
import com.kington.fshg.webapp.security.UserContext;

public class StoreProductStockAction extends BaseAction {

	private static final long serialVersionUID = 8461171703323696314L;
	
	@Resource
	private StoreProductStockService storeProductStockService;
	@Resource
	private StoreProductService storeProductService;
	@Resource
	private UserService userService;
	@Resource
	private CustomService customService;

	private StoreProductStockVO vo;
	private File xlsfileupload;// 导入excel文件
	private String header;  //导出表头
	
	public String list(){
		try {
			if(vo == null)
				vo = new StoreProductStockVO();
			vo.setPageNumber(page);
			
			checkRole();		
			pageList = storeProductStockService.getPageList(vo);
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
				vo = storeProductStockService.getVOById(vo.getId());
				if(act == null)
					setAct(ActType.EDIT);
			}else{
				setAct(ActType.ADD);
			}
			
			if(vo == null)
				vo = new StoreProductStockVO();
			
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
			
			if(vo.getCount() != null && vo.getStoreProduct() != null 
					&& vo.getStoreProduct().getProduct().getStandardPrice() != null)
				vo.setMoney(PublicType.setDoubleScale(vo.getCount() * vo.getStoreProduct().getProduct().getStandardPrice()));
			storeProductStockService.saveOrUpdate(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.addActionMessage("操作成功！");
		//vo = new StoreProductStockVO();
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
				if(storeProductStockService.delete(Long.parseLong(id))) 
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
			ImportExcel<StoreProductStockExcelVO> imp = new ImportExcel<StoreProductStockExcelVO>(StoreProductStockExcelVO.class);
			List<StoreProductStockExcelVO> resultList = null;
			resultList = (ArrayList<StoreProductStockExcelVO>) imp.importExcel(getXlsfileupload());
			
			if(resultList != null && resultList.size() > 0){
				setAttr("info", storeProductStockService.doImports(resultList));
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
		setAttr("stoProStockHeader",Common.getExportHeader("STOREPRODUCTSTOCK"));
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
			vo = new StoreProductStockVO();
		
		checkRole();		
		vo.setObjectsPerPage(Integer.MAX_VALUE);
		List<StoreProductStock> list = 	storeProductStockService.getPageList(vo).getList();

		List<Integer> intArr = new ArrayList<Integer>();
		String[] heads = StringUtils.split(header, ",");
		for(int i =0; i< heads.length; i++){
			intArr.add(25);
		}
		
		List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
		Map<String ,String> map = null;
		
		for(StoreProductStock o : list){
			map = new HashMap<String,String>();
			storeProductStockService.export(heads, o, map);
			listmap.add(map);
		}
		
		HttpServletResponse response = ServletActionContext.getResponse();
		String title = "门店库存信息表";
		Integer[] columnSize = intArr.toArray(new Integer[]{});
		ExcelUtil.export(response, listmap, "门店库存信息表.xls", title, heads, columnSize);
		return null;
	}
	
	public void checkRole() throws Exception{
		//角色判断
		UserContext uc = UserContext.get();
		User user = userService.getByCde(uc.getUserCode());
		String roleName = user.getRoles().get(0).getRoleName();
		Custom custom = new Custom();
		Store store = new Store();
		StoreProduct sp = new StoreProduct();
		if(StringUtils.equals(roleName, "业务员")){
			if(vo.getStoreProduct() != null){
				sp = vo.getStoreProduct();
				if(sp.getStore() != null)
					store = sp.getStore();
				if(store.getCustom() != null)
					custom = store.getCustom();
			}
			custom.setUser(user);
			store.setCustom(custom);
			sp.setStore(store);
			vo.setStoreProduct(sp);
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
	public StoreProductStockVO getVo() {
		return vo;
	}
	public void setVo(StoreProductStockVO vo) {
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
