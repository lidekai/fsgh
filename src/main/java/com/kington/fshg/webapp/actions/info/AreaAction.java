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

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.jtframework.strutscoc.annotation.JSONResult;
import com.jtframework.websupport.pagination.PageList;
import com.kington.fshg.common.Common;
import com.kington.fshg.common.ExcelUtil;
import com.kington.fshg.common.JsUtils;
import com.kington.fshg.common.excel.ImportExcel;
import com.kington.fshg.common.excel.vo.AreaExcelVO;
import com.kington.fshg.model.info.Area;
import com.kington.fshg.model.info.AreaVO;
import com.kington.fshg.service.info.AreaService;
import com.kington.fshg.webapp.actions.BaseAction;

public class AreaAction extends BaseAction {
	private static final long serialVersionUID = 8225634718504016338L;
	private AreaVO vo;
	@Resource
	private AreaService areaService;
	private String treeJSON;// 菜单列表
	private File xlsfileupload;// 导入excel文件
	private String header;
	
	/**
	 * 产品类型管理 树形结构
	 */
	public String list(){
		try{
			List<Area> list=new ArrayList<Area>();
			if(vo == null ){
				vo =  new AreaVO();
			}
			vo.setObjectsPerPage(Integer.MAX_VALUE);
			list = areaService.getPageList(vo).getList();
			
			//组装JSON数据，返回到前台供zTree使用
			JSONArray json = new JSONArray();
			for (Area area : list) {
				JSONObject member = new JSONObject();
				member.put("id",area.getId());
				member.put("name",area.getAreaName());
				member.put("key",area.getKey());				
		
				if (area.getParentArea() == null) {
					member.put("pid", 0);
				} else {
					member.put("pid", area.getParentArea().getId());
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
			vo = areaService.getVOById(id);
			if (vo == null){
				return doException("无效的操作ID");
			}
		}else if(vo != null && vo.getParentArea() != null && Common.checkLong(vo.getParentArea().getId()) )
			vo.setParentArea(areaService.getById(vo.getParentArea().getId()));
		else if(StringUtils.isBlank(vo.getAreaName()))
			vo = new AreaVO();

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
			if (vo.getParentArea() != null && Common.checkLong(vo.getParentArea().getId()))
				vo.setParentArea(areaService.getById(vo.getParentArea().getId()));
			
			if (StringUtils.isNotBlank(vo.getAreaName())){
				Area area = areaService.getByName(vo.getAreaName());
				if(area != null && (!Common.checkLong(vo.getId()) || !vo.getId().equals(area.getId())) 
						&& area.getParentArea().getId().equals(vo.getParentArea().getId())){
					this.addActionMessage("地区名称已存在，请重新填写");
					return edit();
				}		
			}

			areaService.saveOrUpdate(vo);
		} catch (Exception e) {
			return doException(e);
		}
		this.addActionMessage("操作成功");
		vo = new AreaVO();
		return list();
	}
	
	/**
	 * 删除当前节点及其所有子节点
	 */
	public String delete() throws Exception {
		try {
			boolean success = false;
			int count = areaService.delete(id);

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
				vo =  new AreaVO();
			}
			vo.setObjectsPerPage(Integer.MAX_VALUE);
			//组装JSON数据，返回到前台供zTree使用
			JSONArray json = new JSONArray();
			List<Area> list = new ArrayList<Area>();
			try{
				list = areaService.getPageList(vo).getList();
			for (Area area : list) {
				addProds(area, json);
			}
		}catch(Exception e){
			return doException(e);
		}
		
		return json.toString();
	}
	
	public void addProds(Area area,JSONArray json){
		JSONObject member = new JSONObject();
		member.put("id",area.getId());
		member.put("name",area.getAreaName());
		member.put("key",area.getKey());
		member.put("open", true);
		if (area.getParentArea() == null) {
			member.put("pid", 0);
			if(!json.contains(member)){
				json.add(member);
			}
		} else {
			member.put("pid", area.getParentArea().getId());
			if(!json.contains(member)){
				json.add(member);
			}
			addProds(area.getParentArea(),json);
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
			ImportExcel<AreaExcelVO> imp = new ImportExcel<AreaExcelVO>(AreaExcelVO.class);
			List<AreaExcelVO> result = null;
			result = (ArrayList<AreaExcelVO>) imp.importExcel(getXlsfileupload());
			
			if (result != null && result.size() > 0) {
				setAttr("info",areaService.doImports(result));
			} else {
				setAttr("info", "没有可导入的信息");
			}
		}catch(Exception e){
			return this.doException(e);
		}
		
		return "imp";
	}
	
	/**
	 * 获取地区子节点
	 * @return
	 */
	public String getArea(){
		try {
			if(vo == null)
				vo = new AreaVO();
			vo.setPageNumber(page);
			vo.initMyForeignStr("left join fetch o.areaList ");
			vo.initMyQueryStr(" and o.areaList is empty ");
			setAttr("areaList", areaService.getPageList(vo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "getArea";
	}
	
	public void getChildrenJson() throws Exception{
		String result = "";
		String areaId = ServletActionContext.getRequest().getParameter("areaId");
		if(StringUtils.isNotBlank(areaId)){
			List<Area> areaList = areaService.getByParentId(Long.parseLong(areaId));
			if(Common.checkList(areaList)){
				for(Area a : areaList){
					result += "{\"id\":\"" + a.getId() + "\",\"name\":\"" + a.getAreaName() + "\"},"; 
				}
			}
			
			if(StringUtils.isNotBlank(result))
				result.substring(0, result.length()-1);
		}
		result = "[" + result + "]";
		JsUtils.writeText(result);
	}
	
	/**
	 * 导出地区信息
	 * @return
	 * @throws Exception 
	 */
	public String exportArea() throws Exception{
		setAttr("AreaHeader", Common.getExportHeader("AREA"));
		if(StringUtils.isNotBlank(vo.getAreaName())){
			String keyword=new String(vo.getAreaName().getBytes("ISO8859_1"),"UTF8");
			vo.setAreaName(keyword);
		}
		return "export";
	}
	
	/**
	 * 执行导出
	 * @return
	 */
	public String doExport(){
		if(vo==null){
			vo=new AreaVO();
		}
		vo.setObjectsPerPage(Integer.MAX_VALUE);
		List<Area> list=areaService.area(vo);
		List<Integer> intArr = new ArrayList<Integer>();
		String[] heads = StringUtils.split(header, ",");
		for(int i =0; i< heads.length; i++){
			intArr.add(25);
		}
		
		List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
		Map<String ,String> map = null;
		for(Area area:list){
			map=new HashMap<String, String>();
			areaService.exportArea(heads,area,map);
			listmap.add(map);
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		String title = "地区信息表";
		Integer[] columnSize = intArr.toArray(new Integer[]{});
		ExcelUtil.export(response, listmap, "地区信息表.xls", title, heads, columnSize);
		return null;
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

	public AreaVO getVo() {
		return vo;
	}

	public void setVo(AreaVO vo) {
		this.vo = vo;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}
	
}
