package com.kington.fshg.webapp.actions.budget;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.kington.fshg.common.excel.vo.InFeeClauseExcelVO;
import com.kington.fshg.model.budget.InFeeClause;
import com.kington.fshg.model.budget.InFeeClauseVO;
import com.kington.fshg.model.info.Area;
import com.kington.fshg.model.info.Custom;
import com.kington.fshg.model.system.User;
import com.kington.fshg.service.budget.InFeeClauseService;
import com.kington.fshg.service.info.CustomService;
import com.kington.fshg.service.system.UserService;
import com.kington.fshg.webapp.actions.BaseAction;
import com.kington.fshg.webapp.security.UserContext;

public class InFeeClauseAction extends BaseAction {

	private static final long serialVersionUID = -4083037873316451777L;
	
	@Resource
	private InFeeClauseService inFeeClauseService;
	@Resource
	private UserService userService;
	@Resource
	private CustomService customService;
	
	private InFeeClauseVO vo;
	
	private String header;
	private File xlsfileupload;// 导入excel文件
	public String list(){
		try {
			if(vo == null){
				vo = new InFeeClauseVO();
				vo.setYear(Calendar.getInstance().get(Calendar.YEAR));
			}
			vo.setPageNumber(page);
			checkRole();
			
			pageList = inFeeClauseService.getPageList(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	
	public String edit(){
		try {
			if(vo != null && Common.checkLong(vo.getId())){
				vo = inFeeClauseService.getVOById(vo.getId());
				if(act == null)
					setAct(ActType.EDIT);
			}else{
				setAct(ActType.ADD);
			}
			
			if(vo == null){
				vo = new InFeeClauseVO();
				vo.setYear(Calendar.getInstance().get(Calendar.YEAR));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Common.PATH_EDIT;
	}
	
	public String update(){
		try {
			InFeeClause po = inFeeClauseService.saveOrUpdate(vo);
			if(po != null)
				this.addActionMessage("操作成功！");
			else
				this.addActionError("操作失败！");
		} catch (Exception e) {
			return doException("操作失败！");
		}
		//vo = new InFeeClauseVO();
		return list();
	}
	
	
	public String delete(){
		boolean success = false;
		int count = 0;
		try {
			count = inFeeClauseService.clear(ids);
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
	
	/**
	 * 根据客户和年度判断合同条款是否已存在
	 */
	public void checkCustomYear(){
		try {
			if(vo != null && vo.getYear() != null
					&& vo.getCustom()!= null && vo.getCustom().getId() != null){
				InFeeClause inFeeClause = inFeeClauseService.getByCusIdAndYear(vo.getCustom().getId(), vo.getYear());
				if(inFeeClause != null && (!Common.checkLong(vo.getId()) || !vo.getId().equals(inFeeClause.getId()) ))
					JsUtils.writeText("1");
			}
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
				vo = new InFeeClauseVO();
			vo.setPageNumber(page);
			vo.setObjectsPerPage(Integer.MAX_VALUE);
			vo.setYear(Calendar.getInstance().get(Calendar.YEAR));
			checkRole();
			
			pageList = inFeeClauseService.getPageList(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "selectCustom";
	}
	
	private void checkRole() throws Exception{
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
	 * 导出合同内条款
	 * @return
	 * @throws Exception 
	 */
	public String exportInFeeClause() throws Exception{				
		setAttr("inFeeClauseHeader", Common.getExportHeader("INFEECLAUSE"));
		if(vo.getCustom()!=null&&StringUtils.isNotBlank(vo.getCustom().getCustomName())){
			String name = new String(vo.getCustom().getCustomName().getBytes("ISO8859_1"),"UTF8");
			vo.getCustom().setCustomName(name);
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
				vo = new InFeeClauseVO();
				vo.setYear(Calendar.getInstance().get(Calendar.YEAR));
			}
			vo.setObjectsPerPage(Integer.MAX_VALUE);
			checkRole();
			List<InFeeClause> list=inFeeClauseService.getPageList(vo).getList();
			List<Integer> intArr = new ArrayList<Integer>();
			String[] heads = StringUtils.split(header, ",");
			for(int i =0; i< heads.length; i++){
				intArr.add(25);
			}
			
			List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
			Map<String ,String> map = null;
			for(InFeeClause ifc:list){
				map=new HashMap<String, String>();
				inFeeClauseService.exportInFeeClause(heads,ifc,map);
				listmap.add(map);
			}
			HttpServletResponse response = ServletActionContext.getResponse();
			String title = "合同内条款表";
			Integer[] columnSize = intArr.toArray(new Integer[]{});
			ExcelUtil.export(response, listmap, "合同内条款.xls", title, heads, columnSize);
			
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
			ImportExcel<InFeeClauseExcelVO> imp = new ImportExcel<InFeeClauseExcelVO>(InFeeClauseExcelVO.class);
			List<InFeeClauseExcelVO> list = (ArrayList<InFeeClauseExcelVO>)imp.importExcel(getXlsfileupload());
			if(list != null && list.size() > 0){
				setAttr("info",inFeeClauseService.importInFeeClause(list));
			}else{
				setAttr("info","没有可导入数据");
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "imp";
	}
	
	

	public InFeeClauseVO getVo() {
		return vo;
	}

	public void setVo(InFeeClauseVO vo) {
		this.vo = vo;
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
	
	

}
