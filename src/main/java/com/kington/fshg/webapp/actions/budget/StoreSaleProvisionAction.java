package com.kington.fshg.webapp.actions.budget;

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
import com.kington.fshg.common.PublicType.ActType;
import com.kington.fshg.common.excel.ImportExcel;
import com.kington.fshg.common.excel.vo.StoreSaleProExcelVO;
import com.kington.fshg.model.budget.StoreSaleProvision;
import com.kington.fshg.model.budget.StoreSaleProvisionVO;
import com.kington.fshg.model.info.Area;
import com.kington.fshg.model.info.Custom;
import com.kington.fshg.model.info.Store;
import com.kington.fshg.model.system.User;
import com.kington.fshg.service.budget.StoreSaleProService;
import com.kington.fshg.service.system.UserService;
import com.kington.fshg.webapp.actions.BaseAction;
import com.kington.fshg.webapp.security.UserContext;

public class StoreSaleProvisionAction extends BaseAction {

	private static final long serialVersionUID = 78672868409886098L;

	@Resource
	private StoreSaleProService storeSaleProService;	
	@Resource
	private UserService userService;
	
	private StoreSaleProvisionVO vo;
	
	private String header;
	private File xlsfileupload;// 导入excel文件
	public String list(){
		try {
			if(vo == null){
				vo = new StoreSaleProvisionVO();
				vo.setDateEnd(DateFormat.date2Str(new Date(), 10));
				vo.setDateStart(DateFormat.date2Str(new Date(), 10));
			}
			vo.setPageNumber(page);
			checkRole();
			
			pageList = storeSaleProService.getPageList(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	
	public String edit(){
		try {
			if(vo != null && Common.checkLong(vo.getId())){
				String dateStart = vo.getDateStart();
				String dateEnd = vo.getDateEnd();
				vo = storeSaleProService.getVOById(vo.getId());
				
				vo.setDateStart(dateStart);
				vo.setDateEnd(dateEnd);
				if(act == null)
					setAct(ActType.EDIT);
			}else{
				setAct(ActType.ADD);
			}
			
			if(vo == null){
				vo = new StoreSaleProvisionVO();
				vo.setProvisionTime(new Date());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Common.PATH_EDIT;
	}
	
	public String update(){
		try {
			StoreSaleProvision po = storeSaleProService.saveOrUpdate(vo);
			if(po != null)
				this.addActionMessage("操作成功！");
			else
				this.addActionError("操作失败！");
		} catch (Exception e) {
			return doException("操作失败！");
		}
		return list();
	}
	
	
	public String delete(){
		boolean success = false;
		int count = 0;
		try {
			count = storeSaleProService.clear(ids);
		} catch (Exception e) {
			this.addActionError("删除失败，可能存在关联不可删除！");
		}
		
		success = count > 0;
		if(success){
			String mess = "共删除" + count + "条数据";
			if(ids.split(",").length != count){
				mess+="," + (ids.split(",").length - count) + "条数据删除失败，可能存在关联引用，不可删除";
			}
			this.addActionMessage(mess);
		}else{
			this.addActionError("删除失败，可能存在关联不可删除！");
		}
			
		return list();
	}
	
	
	private void checkRole() throws Exception{
		//角色判断
		UserContext uc = UserContext.get();
		User user = userService.getByCde(uc.getUserCode());
		String roleName = user.getRoles().get(0).getRoleName();
		Custom custom = new Custom();
		Store store = new Store();
		if(vo.getStore() != null)
			store = vo.getStore();
		
		if(StringUtils.equals(roleName, "业务员")){
			if(store.getCustom() != null)
				custom = store.getCustom();
			custom.setUser(user);
			store.setCustom(custom);
		}else if(StringUtils.equals(roleName, "地区经理") || 
				StringUtils.equals(roleName, "大区经理") ){
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
	 * 导出
	 * @return
	 * @throws Exception 
	 */
	public String export() throws Exception{				
		setAttr("storeSaleProHeader", Common.getExportHeader("STORESALEPRO"));
		if(vo.getStore() != null && StringUtils.isNotBlank(vo.getStore().getStoreName())){
			String name = new String(vo.getStore().getStoreName().getBytes("ISO8859_1"),"UTF8");
			vo.getStore().setStoreName(name);
		}
		return "export";
	}
	
	/**
	 * 执行导出
	 * @return
	 */
	public String doExport(){
		try{
			if(vo==null){
				vo = new StoreSaleProvisionVO();
				vo.setDateEnd(DateFormat.date2Str(new Date(), 10));
				vo.setDateStart(DateFormat.date2Str(new Date(), 10));
			}
			vo.setObjectsPerPage(Integer.MAX_VALUE);
			checkRole();
			List<StoreSaleProvision> list=storeSaleProService.getPageList(vo).getList();
			List<Integer> intArr = new ArrayList<Integer>();
			String[] heads = StringUtils.split(header, ",");
			for(int i =0; i< heads.length; i++){
				intArr.add(25);
			}
			
			List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
			Map<String ,String> map = null;
			for(StoreSaleProvision ssp:list){
				map=new HashMap<String, String>();
				storeSaleProService.export(heads,ssp,map);
				listmap.add(map);
			}
			HttpServletResponse response = ServletActionContext.getResponse();
			String title = "门店销售预提表";
			Integer[] columnSize = intArr.toArray(new Integer[]{});
			ExcelUtil.export(response, listmap, "门店销售预提.xls", title, heads, columnSize);
			
		}catch(Exception e){
			e.printStackTrace();
		}
			
		return null;
	}
	
	/**
	 * 导入
	 * @return
	 */
	public String imp(){		
		return "imp";
	}
	
	
	/**
	 * 执行导入
	 * @return
	 */
	public String doImp(){
		try {
			ImportExcel<StoreSaleProExcelVO> imp = new ImportExcel<StoreSaleProExcelVO>(StoreSaleProExcelVO.class);
			List<StoreSaleProExcelVO> list = (ArrayList<StoreSaleProExcelVO>)imp.importExcel(getXlsfileupload());
			if(list != null && list.size() > 0){
				setAttr("info",storeSaleProService.doImports(list));
			}else{
				setAttr("info","没有可导入数据");
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "imp";
	}
	
	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public File getXlsfileupload() {
		return xlsfileupload;
	}

	public void setXlsfileupload(File xlsfileupload) {
		this.xlsfileupload = xlsfileupload;
	}

	public StoreSaleProvisionVO getVo() {
		return vo;
	}

	public void setVo(StoreSaleProvisionVO vo) {
		this.vo = vo;
	}
	
}
