package com.kington.fshg.webapp.actions.info;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.ExcelUtil;
import com.kington.fshg.common.JsUtils;
import com.kington.fshg.common.PublicType.ActType;
import com.kington.fshg.common.excel.ImportExcel;
import com.kington.fshg.common.excel.vo.CustomExcelVO;
import com.kington.fshg.common.excel.vo.CustomProductExcelVO;
import com.kington.fshg.model.info.Area;
import com.kington.fshg.model.info.Custom;
import com.kington.fshg.model.info.CustomProduct;
import com.kington.fshg.model.info.CustomVO;
import com.kington.fshg.model.system.User;
import com.kington.fshg.service.info.AreaService;
import com.kington.fshg.service.info.CustomService;
import com.kington.fshg.service.info.ProductService;
import com.kington.fshg.service.system.UserService;
import com.kington.fshg.webapp.actions.BaseAction;
import com.kington.fshg.webapp.security.UserContext;

public class CustomAction extends BaseAction {
	private static final long serialVersionUID = -2382801531439050857L;

	@Resource
	private CustomService customService;
	@Resource
	private ProductService productService;
	@Resource
	private UserService userService;
	@Resource
	private AreaService areaService;
	
	private CustomVO vo;
	private File xlsfileupload;// 导入excel文件
	
	private String header;
	
	
	
	
	public String getHeader() {
		return header;
	}


	public void setHeader(String header) {
		this.header = header;
	}


	public String list(){
		try {
			if(vo == null)
				vo = new CustomVO();
			vo.setPageNumber(page);
			
			//用户角色判断
			UserContext uc = UserContext.get();
			User user = userService.getByCde(uc.getUserCode());
			String roleName = user.getRoles().get(0).getRoleName();
			if(StringUtils.equals(roleName, "业务员"))
				vo.setUser(user);
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
			else if(act != ActType.CUSPRO){
				setAttr("parentAreas", areaService.getFristNode());
				setAttr("areas", areaService.getLeafNode());
				setAttr("users", userService.getSalesman());
			}
			
			setAttr("roleName", roleName);
			if(act == ActType.CUSPRO){
				vo.initMyQueryStr(" and o.products is not empty ");
				pageList = customService.getPageList(vo);
				return "customProduct";
			}
			
			pageList = customService.getPageList(vo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "list";
	}
	
	
	public String edit(){
		try {
			if(vo != null && Common.checkLong(vo.getId())){
				vo = customService.getVOById(vo.getId());
				if(act == null)
					setAct(ActType.EDIT);
			}else{
				setAct(ActType.ADD);
			}
			if(vo == null)
				vo = new CustomVO();
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
			Custom po = customService.saveOrUpdate(vo);
			if(po != null)
				this.addActionMessage("操作成功！");
			else
				this.addActionError("操作失败！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//vo = new CustomVO();
		return list();
	}
	
	
	public String delete(){
		boolean success = false;
		int count = 0;
		for(String id : ids.split(",")){
			try {
			if(customService.delete(Long.parseLong(id)))
				count++;
			}catch (Exception e) {
				continue;
			}
		}
		success = count > 0;
		if(success){
			String mess ="共成功删除" + count + "条数据";
			if(ids.split(",").length != count){
				mess+="," + (ids.split(",").length - count) + "条数据删除失败，可能存在关联不可删除！";
			}
			this.addActionMessage(mess);
		}else{
			this.addActionError("删除失败，可能存在关联不可删除！");
		}
		return list();
	}
	
	/**
	 * 检查客户是否存在
	 */
	public void checkCustom(){
		try {
			Long customId = 0L;
			Custom custom = customService.getByCde(vo.getCustomCde());
			if(custom != null && Common.checkLong(custom.getId()))
				customId = custom.getId();
			JsUtils.writeText(customId.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 选择客户列表
	 * @return
	 */
	public String selectCustom(){
		try {
			if(vo == null)
				vo = new CustomVO();
			vo.setPageNumber(page);
			
			//角色判断
			UserContext uc = UserContext.get();
			User user = userService.getByCde(uc.getUserCode());
			String roleName = user.getRoles().get(0).getRoleName();
			if(StringUtils.equals(roleName, "业务员")){
				vo.setUser(user);
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
			
			pageList = customService.getPageList(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "selectCustom";
	}
	
	public String editCuspro() throws Exception {
		try {
			if(vo != null && Common.checkLong(vo.getId())){
				vo = customService.getVOById(vo.getId());
				setAttr("productIds", customService.getProductIds(vo.getId()));
				if(act == null)
					setAct(ActType.EDIT);
			} else {
				setAct(ActType.ADD);
			}
			if(vo == null)
				vo = new CustomVO();
			
			setAttr("productMap", productService.getProductMap());
		} catch (Exception e) {
			return doException(e);
		}
		return "editCuspro";
	}
	
	public String updateCuspro() throws Exception {
		try {
			String productIds = ServletActionContext.getRequest().getParameter("productIds");
			if(StringUtils.isNotBlank(productIds) 
					&& (vo != null && Common.checkLong(vo.getId()) || Common.checkLong(id))){
				
				if(Common.checkLong(id) && Common.checkList(customService.getProductIds(id))){
					this.addActionMessage("该客户存货信息已存在不可添加");
					return editCuspro();
				}
				
				Long customId = Common.checkLong(id) ? id : vo.getId();
				customService.deletCuspro(customId);
				for(String productId : productIds.split(",")){
					CustomProduct cp = new CustomProduct();
					cp.setCustom(customService.getById(customId));
					cp.setProduct(productService.getById(Long.parseLong(productId)));
					customService.saveCuspro(cp);
				}
				
			} 
		} catch (Exception e) {
			return doException(e);
		}
		this.addActionMessage("操作成功");
		vo = new CustomVO();
		act = ActType.CUSPRO;
		return list();
	}
	
	public String deleteCuspro(){
		boolean success = false;
		int count = 0;
		for(String id : ids.split(",")){
			try {
			if(customService.deletCuspro(Long.parseLong(id)) > 0)
				count++;
			}catch (Exception e) {
				continue;
			}
		}
		success = count > 0;
		if(success){
			String mess ="共成功删除" + count + "条数据";
			if(ids.split(",").length != (count)){
				mess+="," + (ids.split(",").length - count) + "条数据删除失败，可能存在关联不可删除！";
			}
			this.addActionMessage(mess);
		}else{
			this.addActionError("删除失败，可能存在关联不可删除！");
		}
		act = ActType.CUSPRO;
		return list();
}
	
	/**
	 * 导入产品分类
	 */
	public String impCuspro(){
		return "impCuspro";
	}
	
	/**
	 * 执行导入
	 */
	public String doImpCuspro(){
		try{
			ImportExcel<CustomProductExcelVO> imp = new ImportExcel<CustomProductExcelVO>(CustomProductExcelVO.class);
			List<CustomProductExcelVO> result = null;
			result = (ArrayList<CustomProductExcelVO>) imp.importExcel(getXlsfileupload());
			
			if (result != null && result.size() > 0) {
				setAttr("info",customService.doImpCuspro(result));
			} else {
				setAttr("info", "没有可导入的信息");
			}
		}catch(Exception e){
			return this.doException(e);
		}
		
		return "impCuspro";
	}
	
	/**
	 * 导入
	 * @return
	 */
	public String imp(){
		return "imp";
	}
	
	public String doImp(){
		try {
			ImportExcel<CustomExcelVO> imp = 
					new ImportExcel<CustomExcelVO>(CustomExcelVO.class);
			List<CustomExcelVO> list = 
					(ArrayList<CustomExcelVO>)imp.importExcel(getXlsfileupload());
			
			if(list != null && list.size() > 0){
				setAttr("info",customService.importCustom(list));
			}else{
				setAttr("info","没有可导入数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "imp";
	}
	
	/**
	 * 导出客户信息表
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String exportCustom() throws UnsupportedEncodingException{
		setAttr("customHeader", Common.getExportHeader("CUSTOM"));
		if(StringUtils.isNotBlank(vo.getCustomName())){
			String keyword=new String(vo.getCustomName().getBytes("ISO8859_1"),"UTF8");
			vo.setCustomName(keyword);
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
			vo=new CustomVO();
		}
		//用户角色判断
		UserContext uc = UserContext.get();
		User user = userService.getByCde(uc.getUserCode());
		String roleName = user.getRoles().get(0).getRoleName();
		if(StringUtils.equals(roleName, "业务员"))
			vo.setUser(user);
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
		
		vo.setObjectsPerPage(Integer.MAX_VALUE);
		List<Custom> list=customService.getPageList(vo).getList();
		List<Integer> intArr = new ArrayList<Integer>();
		String[] heads = StringUtils.split(header, ",");
		for(int i =0; i< heads.length; i++){
			intArr.add(25);
		}
		
		List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
		Map<String ,String> map = null;
		for(Custom custom:list){
			map=new HashMap<String, String>();
			customService.exportCustom(heads,custom,map);
			listmap.add(map);
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		String title = "客户信息表";
		Integer[] columnSize = intArr.toArray(new Integer[]{});
		ExcelUtil.export(response, listmap, "客户信息表.xls", title, heads, columnSize);
		return null;
	}
	
	
	
	
	public CustomVO getVo() {
		return vo;
	}

	public void setVo(CustomVO vo) {
		this.vo = vo;
	}
	public File getXlsfileupload() {
		return xlsfileupload;
	}
	public void setXlsfileupload(File xlsfileupload) {
		this.xlsfileupload = xlsfileupload;
	}
	
}
