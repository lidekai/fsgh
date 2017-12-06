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
import com.kington.fshg.common.JsUtils;
import com.kington.fshg.common.PublicType.ActType;
import com.kington.fshg.common.excel.ImportExcel;
import com.kington.fshg.common.excel.vo.StoreExcelVO;
import com.kington.fshg.model.info.Area;
import com.kington.fshg.model.info.Custom;
import com.kington.fshg.model.info.Store;
import com.kington.fshg.model.info.StoreVO;
import com.kington.fshg.model.system.User;
import com.kington.fshg.service.info.CustomService;
import com.kington.fshg.service.info.StoreService;
import com.kington.fshg.service.system.UserService;
import com.kington.fshg.webapp.actions.BaseAction;
import com.kington.fshg.webapp.security.UserContext;

public class StoreAction extends BaseAction {
	private static final long serialVersionUID = -2967671035504909376L;

	
	@Resource
	private StoreService storeService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private CustomService customService;
	
	
	private StoreVO vo;
	
	private File xlsfileupload;// 导入excel文件
	
	private String header;
	
	public String list(){
		try {
			if(vo == null)
				vo = new StoreVO();
			vo.setPageNumber(page);
			
			checkRole();			
			pageList = storeService.getPageList(vo);
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
				vo = storeService.getVOById(vo.getId());
				if(act == null)
					setAct(ActType.EDIT);
			}else{
				setAct(ActType.ADD);
			}
			
			if(vo == null)
				vo = new StoreVO();
			
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
			if(StringUtils.isNotBlank(vo.getStoreCde())){
				Store store = storeService.getByCde(vo.getStoreCde());
				if(store != null && (!Common.checkLong(vo.getId()) || !vo.getId().equals(store.getId()) )){
					this.addActionError("门店编码已存在，请重新指定!");
					return edit();
				}
			}
			storeService.saveOrUpdate(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.addActionMessage("操作成功！");
		//vo = new StoreVO();
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
				if(storeService.delete(Long.parseLong(id))) 
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
			ImportExcel<StoreExcelVO> imp = new ImportExcel<StoreExcelVO>(StoreExcelVO.class);
			List<StoreExcelVO> resultList = null;
			resultList = (ArrayList<StoreExcelVO>) imp.importExcel(getXlsfileupload());
			
			if(resultList != null && resultList.size() > 0){
				setAttr("info", storeService.doImports(resultList));
			}else{
				setAttr("info","没有可导入信息");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "imp";
	}
	
	/**
	 * 选择门店列表
	 * @return
	 */
	public String selectStore(){
		try {
			if(vo == null)
				vo = new StoreVO();
			vo.setPageNumber(page);
			checkRole();
			
			pageList = storeService.getPageList(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "selectStore";
	}
	
	public void getNumByCustom(){
		try {
			int num = storeService.getNumByCostomId(vo.getCustom().getId());
			JsUtils.writeText(String.valueOf(num));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 导出门店信息
	 * @return
	 * @throws Exception 
	 */
	public String exportStore() throws Exception{
		setAttr("storeHeader",Common.getExportHeader("STORE"));	
		if(StringUtils.isNotBlank(vo.getStoreName())){
			String storeName = new String(vo.getStoreName().getBytes("ISO8859_1"),"UTF8");
			vo.setStoreName(storeName);
		}
		return "export";
	}
	
	/**
	 * 执行导出
	 * @return
	 * @throws Exception 
	 */
	public String doExport() throws Exception{
		if(vo==null) vo=new StoreVO();
		
		checkRole();			
		vo.setObjectsPerPage(Integer.MAX_VALUE);
		List<Store> list=storeService.getPageList(vo).getList();
		List<Integer> intArr = new ArrayList<Integer>();
		String[] heads = StringUtils.split(header, ",");
		for(int i =0; i< heads.length; i++){
			intArr.add(25);
		}
		
		List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
		Map<String ,String> map = null;
		for(Store store : list){
			map=new HashMap<String, String>();
			storeService.exportStore(heads,store,map);
			listmap.add(map);
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		String title = "门店信息表";
		Integer[] columnSize = intArr.toArray(new Integer[]{});
		ExcelUtil.export(response, listmap, "门店信息表.xls", title, heads, columnSize);
		
		return null;
	}
	
	public void checkRole() throws Exception{
		//角色判断
		UserContext uc = UserContext.get();
		User user = userService.getByCde(uc.getUserCode());
		String roleName = user.getRoles().get(0).getRoleName();
		Custom custom = new Custom();
		if(StringUtils.equals(roleName, "业务员")){
			if(vo.getCustom() != null)
				custom = vo.getCustom();
			custom.setUser(user);
			vo.setCustom(custom);
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

	public StoreVO getVo() {
		return vo;
	}
	public void setVo(StoreVO vo) {
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
