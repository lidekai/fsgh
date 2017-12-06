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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.ExcelUtil;
import com.kington.fshg.common.JsUtils;
import com.kington.fshg.common.excel.ImportExcel;
import com.kington.fshg.common.excel.vo.ProductTypeExcelVO;
import com.kington.fshg.model.info.ProductType;
import com.kington.fshg.model.info.ProductTypeVO;
import com.kington.fshg.service.info.ProductTypeService;
import com.kington.fshg.webapp.actions.BaseAction;

public class ProductTypeAction extends BaseAction {
	private static final long serialVersionUID = -177951654111369720L;
	
	private ProductTypeVO vo;
	@Resource
	private ProductTypeService productTypeService;
	private String treeJSON;// 菜单列表
	private File xlsfileupload;// 导入excel文件
	private String header;
	
	/**
	 * 产品类型管理 树形结构
	 */
	public String list(){
		try{
			List<ProductType> list=new ArrayList<ProductType>();
			if(vo == null ){
				vo =  new ProductTypeVO();
			}
			vo.setObjectsPerPage(Integer.MAX_VALUE);
			list = productTypeService.getPageList(vo).getList();
			
			//组装JSON数据，返回到前台供zTree使用
			JSONArray json = new JSONArray();
			for (ProductType prod : list) {
				JSONObject member = new JSONObject();
				member.put("id",prod.getId());
				member.put("name",prod.getProductTypeName());
				member.put("key",prod.getKey());				
		
				if (prod.getProductType() == null) {
					member.put("pid", 0);
				} else {
					member.put("pid", prod.getProductType().getId());
				}

				member.put("open", true);
				json.add(member);

			}
			
			treeJSON = json.toString();
		}catch(Exception e){
			return doException(e);
		}
		
		return "list";
	}
	
	/**
	 *编辑界面
	 */
	public String edit() throws Exception {
		//查询当前分类的上级		
		if (Common.checkLong(id)) {
			vo = productTypeService.getVOById(id);
			if (vo == null){
				return doException("无效的操作ID");
			}
		}else if(vo != null && vo.getProductType() != null && Common.checkLong(vo.getProductType().getId()) )
			vo.setProductType(productTypeService.getById(vo.getProductType().getId()));
		else if(StringUtils.isBlank(vo.getProductTypeName()))
			vo = new ProductTypeVO();

		return Common.PATH_EDIT;
		
	}
	

	/**
	 * 添加或更新数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception {
		try {
			if (vo.getProductType() != null && Common.checkLong(vo.getProductType().getId()))
				vo.setProductType(productTypeService.getById(vo.getProductType().getId()));
			
			if (StringUtils.isNotBlank(vo.getProductTypeName())){
				ProductType pt = productTypeService.getByName(vo.getProductTypeName());
				if(pt != null && (!Common.checkLong(vo.getId()) || !vo.getId().equals(pt.getId()))){
					this.addActionMessage("产品分类名称已存在，请重新填写");
					return edit();
				}		
			}

			productTypeService.saveOrUpdate(vo);
		} catch (Exception e) {
			return doException(e);
		}
		this.addActionMessage("操作成功");
		vo = new ProductTypeVO();
		return list();
	}
	
	/**
	 * 删除当前节点及其所有子节点
	 */
	public String delete() throws Exception {
		try {
			boolean success = false;
			int count = productTypeService.delete(id);

			success = count > 0;
			if (success) {
				this.addActionMessage("共删除 " + count + "记录");
			} else {
				this.addActionError("数据删除失败,可能数据已存在引用，不可删除");
			}
		} catch (Exception e) {
			return doException("数据删除失败,可能数据已存在引用");
		}
		
		return list();
	}
	
	public String searchTree(){
		JsUtils.writeText(getTree());
		return null;
	}
	
	public String getTree(){
			if(vo == null ){
				vo =  new ProductTypeVO();
			}
			vo.setObjectsPerPage(Integer.MAX_VALUE);
			//组装JSON数据，返回到前台供zTree使用
			JSONArray json = new JSONArray();
			List<ProductType> list = new ArrayList<ProductType>();
			try{
				list = productTypeService.getPageList(vo).getList();
			for (ProductType prod : list) {
				addProds(prod, json);
			}
		}catch(Exception e){
			return doException(e);
		}
		
		return json.toString();
	}
	
	public void addProds(ProductType prod,JSONArray json){
		JSONObject member = new JSONObject();
		member.put("id",prod.getId());
		member.put("name",prod.getProductTypeName());
		member.put("key",prod.getKey());
		member.put("open", true);
		if (prod.getProductType() == null) {
			member.put("pid", 0);
			if(!json.contains(member)){
				json.add(member);
			}
		} else {
			member.put("pid", prod.getProductType().getId());
			if(!json.contains(member)){
				json.add(member);
			}
			addProds(prod.getProductType(),json);
		}
	}
	
	/**
	 * 导入产品分类
	 */
	public String imp(){
		return "imp";
	}
	
	/**
	 * 执行导入
	 */
	public String doImp(){
		try{
			ImportExcel<ProductTypeExcelVO> imp = new ImportExcel<ProductTypeExcelVO>(ProductTypeExcelVO.class);
			List<ProductTypeExcelVO> result = null;
			result = (ArrayList<ProductTypeExcelVO>) imp.importExcel(getXlsfileupload());
			
			if (result != null && result.size() > 0) {
				setAttr("info",productTypeService.doImports(result));
			} else {
				setAttr("info", "没有可导入的信息");
			}
		}catch(Exception e){
			return this.doException(e);
		}
		
		return "imp";
	}
	
	
	/**
	 * 导出产品分类
	 * @return
	 * @throws Exception 
	 */
	public String exportProductType() throws Exception{
		setAttr("productTypeHeader", Common.getExportHeader("PRODUCTTYPE"));
		if(StringUtils.isNotBlank(vo.getProductTypeName())){
			String keyword=new String(vo.getProductTypeName().getBytes("ISO8859_1"),"UTF8");
			vo.setProductTypeName(keyword);
		}
		return "export";
	}
	
	/**
	 * 执行导出
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String doExport() throws UnsupportedEncodingException{
		if(vo==null){
			vo=new ProductTypeVO();
		}
		vo.setObjectsPerPage(Integer.MAX_VALUE);
		
		List<ProductType> list=productTypeService.productType(vo);
		List<Integer> intArr = new ArrayList<Integer>();
		String[] heads = StringUtils.split(header, ",");
		for(int i =0; i< heads.length; i++){
			intArr.add(25);
		}
		
		List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
		Map<String ,String> map = null;
		for(ProductType pt:list){
			map=new HashMap<String, String>();
			productTypeService.exportProductType(heads,pt,map);
			listmap.add(map);
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		String title = "产品分类表";
		Integer[] columnSize = intArr.toArray(new Integer[]{});
		ExcelUtil.export(response, listmap, "产品分类表.xls", title, heads, columnSize);		
		return null;
	}
	
	
	
	public String getTreeJSON() {
		return treeJSON;
	}

	public void setTreeJSON(String treeJSON) {
		this.treeJSON = treeJSON;
	}
	public ProductTypeVO getVo() {
		return vo;
	}
	public void setVo(ProductTypeVO vo) {
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
