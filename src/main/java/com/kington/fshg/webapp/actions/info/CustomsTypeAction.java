package com.kington.fshg.webapp.actions.info;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.ExcelUtil;
import com.kington.fshg.common.JsUtils;
import com.kington.fshg.common.excel.ImportExcel;
import com.kington.fshg.common.excel.vo.CustomsTypeExcelVO;
import com.kington.fshg.model.info.CustomsType;
import com.kington.fshg.model.info.CustomsTypeVO;
import com.kington.fshg.service.info.CustomsTypeService;
import com.kington.fshg.webapp.actions.BaseAction;

public class CustomsTypeAction extends BaseAction {
	private static final long serialVersionUID = -6066341238901627739L;
	
	@Resource
	private CustomsTypeService customsTypeService;
	
	private CustomsTypeVO vo;
	private String treeJSON;
	private File xlsfileupload;// 导入excel文件
	private String header; //导出表头
	
	/**
	 * 客户分类树
	 * @return
	 */
	public String list(){
		try {
			if(vo == null)
				vo = new CustomsTypeVO();
			vo.setObjectsPerPage(Integer.MAX_VALUE);
			
			List<CustomsType> list=new ArrayList<CustomsType>();
			list = customsTypeService.getPageList(vo).getList();
			
			JSONArray json = new JSONArray();
			for(CustomsType ct : list){
				JSONObject member = new JSONObject();
				member.put("id", ct.getId());
				member.put("name", ct.getCustomTypeName());
				member.put("key", ct.getKey());
				
				if(ct.getParents() == null){
					member.put("pid", 0);
				}else{
					member.put("pid", ct.getParents().getId());
				}
				member.put("open", true);
				json.add(member);
			}
			
			treeJSON = json.toString();
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
			if(Common.checkLong(id)){
				vo = customsTypeService.getVOById(id);
				if(vo == null)
					return doException("无效的操作ID");
			}else if(vo != null && vo.getParents() != null && Common.checkLong(vo.getParents().getId())){
				vo.setParents(customsTypeService.getById(vo.getParents().getId()));
			}else if(StringUtils.isBlank(vo.getCustomTypeName())){
				vo = new CustomsTypeVO();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Common.PATH_EDIT;
	}
	
	
	/**
	 * 添加或更新数据
	 * @return
	 */
	public String update(){
		try {
			if(vo.getParents() != null && Common.checkLong(vo.getParents().getId())){
				vo.setParents(customsTypeService.getById(vo.getParents().getId()));
			}
			
			if(StringUtils.isNotBlank(vo.getCustomTypeName())){
				CustomsType ct = customsTypeService.getByName(vo.getCustomTypeName());
				if(ct != null && (!Common.checkLong(vo.getId()) || !vo.getId().equals(ct.getId() ))){
					this.addActionMessage("客户分类名称已存在，请重新指定！");
					return edit();
				}
			}
			
			customsTypeService.saveOrUpdate(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.addActionMessage("操作成功！");
		vo = new CustomsTypeVO();
		return list();
	}
	
	/**
	 * 删除当前节点及其所有子节点
	 * @return
	 */
	public String delete(){
		try {
			boolean success = false;
			int count = customsTypeService.delete(id);
			success = count > 0;
			if(success){
				this.addActionMessage("共删除" + count + "条数据" );
			}else{
				this.addActionMessage("数据删除失败！");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list();
	}
	
	//导入页面
	public String imp(){
		return "imp";
	}
	
	
	/**
	 * 执行导入操作
	 * @return
	 */
	public String doImp(){
		try {
			ImportExcel<CustomsTypeExcelVO> imp = new ImportExcel<CustomsTypeExcelVO>(CustomsTypeExcelVO.class);
			List<CustomsTypeExcelVO> list = null;
			list = (ArrayList<CustomsTypeExcelVO>)imp.importExcel(getXlsfileupload());
			if(list != null && list.size() > 0){
				setAttr("info", customsTypeService.doImports(list));
			}else{
				setAttr("info","没有可导入数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imp();
	}
	
	/**
	 * 查找树
	 * @return
	 */
	public String searchTree(){
		JsUtils.writeText(getTree());
		return null;
	}
	
	public String getTree(){
		JSONArray json = new JSONArray();
		try {
			if(vo == null)
				vo = new CustomsTypeVO();
			vo.setObjectsPerPage(Integer.MAX_VALUE);
			List<CustomsType> list = customsTypeService.getPageList(vo).getList();
			
			for(CustomsType ct : list){
				addCustomsType(ct , json);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json.toString();
	}
	
	public void addCustomsType(CustomsType ct,JSONArray json){
		JSONObject member = new JSONObject();
		member.put("id", ct.getId());
		member.put("name", ct.getCustomTypeName());
		member.put("key", ct.getKey());
		member.put("open", true);
		if(ct.getParents() == null){
			member.put("pid", 0);
			if(!json.contains(member)){
				json.add(member);
			}
		}else{
			member.put("pid", ct.getParents().getId());
			if(!json.contains(member)){
				json.add(member);
			}
			addCustomsType(ct.getParents(),json);
		}
	}
	
	/**
	 * 获取客户类型所有子节点
	 * @return
	 */
	public String getCustomType(){
		try {
			if(vo == null)
				vo = new CustomsTypeVO();
			vo.initMyForeignStr(" left join fetch o.customsTypeList ");
			vo.initMyQueryStr(" and o.customsTypeList is empty ");
			setAttr("customsTypeList", customsTypeService.getPageList(vo));
		} catch (Exception e) {
		}
		return "getCustomType";
	}
	
	/**
	 * 导出客户分类
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String exportCustomsType() throws UnsupportedEncodingException{
		setAttr("customsTypeHeader", Common.getExportHeader("CUSTOMSTYPE"));
		if(StringUtils.isNotBlank(vo.getCustomTypeName())){
			String keyword=new String(vo.getCustomTypeName().getBytes("ISO8859_1"),"UTF8");
			vo.setCustomTypeName(keyword);
		}
		return "export";
	}
	
	/**
	 * 执行导出
	 * @return
	 */
	public String doExport(){
		if(vo==null){
			vo=new CustomsTypeVO();
		}
		vo.setObjectsPerPage(Integer.MAX_VALUE);
		List<CustomsType> list=customsTypeService.customsType(vo);
		List<Integer> intArr = new ArrayList<Integer>();
		String[] heads = StringUtils.split(header, ",");
		for(int i =0; i< heads.length; i++){
			intArr.add(25);
		}
		
		List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
		Map<String ,String> map = null;
		for(CustomsType ct:list){
			map=new HashMap<String, String>();
			customsTypeService.exportCustomsType(heads,ct,map);
			listmap.add(map);
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		String title = "客户分类表";
		Integer[] columnSize = intArr.toArray(new Integer[]{});
		ExcelUtil.export(response, listmap, "客户分类表.xls", title, heads, columnSize);	
		return null;
	}

	public CustomsTypeVO getVo() {
		return vo;
	}

	public void setVo(CustomsTypeVO vo) {
		this.vo = vo;
	}

	public String getTreeJSON() {
		return treeJSON;
	}

	public void setTreeJSON(String treeJSON) {
		this.treeJSON = treeJSON;
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
