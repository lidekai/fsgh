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
import com.kington.fshg.common.PublicType.ActType;
import com.kington.fshg.common.excel.ImportExcel;
import com.kington.fshg.common.excel.vo.StoreProductExcelVO;
import com.kington.fshg.model.info.Area;
import com.kington.fshg.model.info.Custom;
import com.kington.fshg.model.info.Product;
import com.kington.fshg.model.info.Store;
import com.kington.fshg.model.info.StoreProduct;
import com.kington.fshg.model.info.StoreProductVO;
import com.kington.fshg.model.info.StoreVO;
import com.kington.fshg.model.system.User;
import com.kington.fshg.service.info.CustomService;
import com.kington.fshg.service.info.ProductService;
import com.kington.fshg.service.info.StoreProductService;
import com.kington.fshg.service.info.StoreService;
import com.kington.fshg.service.system.UserService;
import com.kington.fshg.webapp.actions.BaseAction;
import com.kington.fshg.webapp.security.UserContext;

public class StoreProductAction extends BaseAction {

	private static final long serialVersionUID = 3619778265693651953L;
	
	@Resource
	private StoreService storeService;
	
	@Resource
	private StoreProductService storeProductService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private CustomService customService;
	
	@Resource
	private ProductService productService;
	
	private StoreProductVO vo;
	
	private File xlsfileupload;// 导入excel文件
	
	private String header;
	
	public String list(){
		try {
			if(vo == null)
				vo = new StoreProductVO();
			vo.setPageNumber(page);
			
			checkRole();			
			vo.initMyOrderStr("order by o.store.custom.id,o.store.id ");
			pageList = storeProductService.getPageList(vo);
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
				vo = storeProductService.getVOById(vo.getId());
				if(act == null)
					setAct(ActType.EDIT);
			}else{
				setAct(ActType.ADD);
			}
			
			if(vo == null)
				vo = new StoreProductVO();
			
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
			if(Common.checkLong(vo.getStore().getId())
					&& Common.checkLong(vo.getProduct().getId())){
				StoreProduct sp = storeProductService.getByStoPro(vo.getStore().getId(), vo.getProduct().getId());
				if(sp != null && (!Common.checkLong(vo.getId()) || !vo.getId().equals(sp.getId()))){
					this.addActionMessage("该门店已有此产品不可重复添加！");
					return Common.PATH_EDIT;
				}else{
					vo.setStore(storeService.getById(vo.getStore().getId()));
					vo.setProduct(productService.getById(vo.getProduct().getId()));
					
					storeProductService.saveOrUpdate(vo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.addActionMessage("操作成功！");
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
				if(storeProductService.delete(Long.parseLong(id))) 
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
			ImportExcel<StoreProductExcelVO> imp = new ImportExcel<StoreProductExcelVO>(StoreProductExcelVO.class);
			List<StoreProductExcelVO> resultList = null;
			resultList = (ArrayList<StoreProductExcelVO>) imp.importExcel(getXlsfileupload());
			
			if(resultList != null && resultList.size() > 0){
				setAttr("info", storeProductService.doImports(resultList));
			}else{
				setAttr("info","没有可导入信息");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "imp";
	}
	
	
	/**
	 * 根据客户ID，获取产品列表
	 * @return
	 * @throws Exception
	 */
	public String productList() throws Exception{
		pageList = storeProductService.getPageList(vo);
		return "productList";
	}
	
	public String addStoreProduct() throws Exception{
		if(vo.getStore().getCustom().getId() != null)
			setAttr("customName", customService.getById(vo.getStore().getCustom().getId()).getCustomName());
		return "add";
	}
	
	public String save() throws Exception{
		if(Common.checkLong(vo.getProduct().getId())){
			Product product = productService.getById(vo.getProduct().getId());
			if(product != null){
				vo.setProduct(product);
			}else{
				this.addActionError("产品信息不存在");
				return addStoreProduct();
			}
			if(StringUtils.isNotBlank(vo.getStore().getStoreCde())){
				Store store = storeService.getByCde(vo.getStore().getStoreCde());
				if(store == null ){
					StoreVO storeVO = new StoreVO();
					storeVO.setStoreCde(vo.getStore().getStoreCde());
					storeVO.setStoreName(vo.getStore().getStoreName());
					storeVO.setCustom(customService.getById(vo.getStore().getCustom().getId()));
					store = storeService.saveOrUpdate(storeVO);
				}
				vo.setStore(store);
				if(storeProductService.getByStoNamePro(vo.getStore().getCustom().getCustomName(), 
						vo.getStore().getStoreName(), vo.getProduct().getStockCde()) != null){
					this.addActionError("门店存货信息已存在无需新增");
					return addStoreProduct();
				}
				storeProductService.saveOrUpdate(vo);
			}
		}
		return productList();
	}
	
	
	/**
	 * 选择门店存货列表
	 * @return
	 */
	public String selectStoreProduct(){
		try {
			if(vo == null)
				vo = new StoreProductVO();
			vo.setPageNumber(page);
			
			checkRole();
			
			vo.initMyOrderStr("order by o.store.custom.id,o.store.id ");
			pageList = storeProductService.getPageList(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "selectStoreProduct";
	}
	
	/**
	 * 导出门店存货
	 * @return
	 * @throws Exception 
	 */
	public String exportStoreProduct() throws Exception{
		setAttr("storeProductHeader", Common.getExportHeader("STOREPRODUCT"));
		if(vo.getStore()!=null&&vo.getStore().getCustom()!=null&&StringUtils.isNotBlank(vo.getStore().getCustom().getCustomName())){
			String keyword=new String(vo.getStore().getCustom().getCustomName().getBytes("ISO8859_1"),"UTF8");
			vo.getStore().getCustom().setCustomName(keyword);
		}
		if(vo.getStore()!=null&&StringUtils.isNotBlank(vo.getStore().getStoreName())){
			String keyword=new String(vo.getStore().getStoreName().getBytes("ISO8859_1"),"UTF8");
			vo.getStore().setStoreName(keyword);
		}
		if(vo.getProduct()!=null&&StringUtils.isNotBlank(vo.getProduct().getProductName())){
			String keyword=new String(vo.getProduct().getProductName().getBytes("ISO8859_1"),"UTF8");
			vo.getProduct().setProductName(keyword);
		}
		return "export";
	}
	
	/**
	 * 执行导出
	 * @return
	 */
	public String doExport(){
		try{
			if(vo==null) vo=new StoreProductVO();
			checkRole();
			
			vo.setObjectsPerPage(Integer.MAX_VALUE);
			List<StoreProduct> list=storeProductService.getPageList(vo).getList();
			List<Integer> intArr = new ArrayList<Integer>();
			String[] heads = StringUtils.split(header, ",");
			for(int i =0; i< heads.length; i++){
				intArr.add(25);
			}
			
			List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
			Map<String ,String> map = null;
			for(StoreProduct sp:list){
				map=new HashMap<String, String>();
				storeProductService.exportStoreProduct(heads,sp,map);
				listmap.add(map);
			}
			HttpServletResponse response = ServletActionContext.getResponse();
			String title = "门店存货表";
			Integer[] columnSize = intArr.toArray(new Integer[]{});
			ExcelUtil.export(response, listmap, "门店存货表.xls", title, heads, columnSize);
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void checkRole() throws Exception{
		//角色判断
		UserContext uc = UserContext.get();
		User user = userService.getByCde(uc.getUserCode());
		String roleName = user.getRoles().get(0).getRoleName();
		Custom custom = new Custom();
		Store store = new Store();
		if(StringUtils.equals(roleName, "业务员")){
			if(vo.getStore() != null){
				store = vo.getStore();
				if(vo.getStore().getCustom() != null)
					custom = vo.getStore().getCustom();
			}
			custom.setUser(user);
			store.setCustom(custom);
			vo.setStore(store);
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
	
	public StoreProductVO getVo() {
		return vo;
	}

	public void setVo(StoreProductVO vo) {
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
