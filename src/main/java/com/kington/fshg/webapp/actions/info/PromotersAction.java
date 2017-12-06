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
import com.kington.fshg.common.excel.vo.PromotersExcelVO;
import com.kington.fshg.model.info.Area;
import com.kington.fshg.model.info.Custom;
import com.kington.fshg.model.info.CustomVO;
import com.kington.fshg.model.info.Promoters;
import com.kington.fshg.model.info.PromotersVO;
import com.kington.fshg.model.info.StoreVO;
import com.kington.fshg.model.system.User;
import com.kington.fshg.service.info.CustomService;
import com.kington.fshg.service.info.PromotersService;
import com.kington.fshg.service.info.StoreService;
import com.kington.fshg.service.system.UserService;
import com.kington.fshg.webapp.actions.BaseAction;
import com.kington.fshg.webapp.security.UserContext;

public class PromotersAction extends BaseAction {
	private static final long serialVersionUID = -2061503010050035300L;

	
	@Resource
	private PromotersService promotersService;
	@Resource
	private CustomService customService;
	@Resource
	private StoreService storeService;
	@Resource
	private UserService userService;
	
	private PromotersVO vo;
	private CustomVO cvo;
	private StoreVO svo;
	private String header;
	
	private File xlsfileupload;// 导入excel文件
	
	
	
	
	/**
	 * 列表
	 * @return
	 */
	public String list(){
		try {
			if(vo == null)
				vo = new PromotersVO();
			vo.setPageNumber(page);
			checkRole();
			pageList = promotersService.getPageList(vo);
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
				vo = promotersService.getVOById(vo.getId());
				if(act == null)
					setAct(ActType.EDIT);
			}else{
				setAct(ActType.ADD);
			}
			
			if(vo == null)
				vo = new PromotersVO();
			
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
			if(StringUtils.isNotBlank(vo.getIDCare())){
				Promoters p = promotersService.getByIDCare(vo.getIDCare());
				if(p != null && (!Common.checkLong(vo.getId()) || !vo.getId().equals(p.getId()) )){
					this.addActionError("身份证号码已存在，请重新指定！");
					return edit();
				}
			}
			
			promotersService.saveOrUpdate(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.addActionMessage("操作成功");
		//vo = new PromotersVO();
		return list();
	}
	
	/**
	 * 批量删除数据
	 * @return
	 */
	public String delete(){
		try {
			boolean success = false;
			int count = promotersService.clear(ids);
			String[] str = ids.split(",");
			
			success = count > 0;
			if(success){
				String message = "共删除"+count + "条数据；";
				if(str.length != count){
					message += (str.length - count) + "条数据删除失败，可能存在关联不可删除";
				}
				this.addActionMessage(message);
			}else{
				this.addActionError("数据删除失败，可能存在关联不可删除");
			}
			return list();
		} catch (Exception e) {
			return doException("数据删除失败，可能存在关联不可删除");
		}
	}
	
	/**
	 * 导入页面
	 * @return
	 */
	public  String imp(){
		return "imp";
	}
	
	
	/**
	 * 执行促销员信息
	 * @return
	 */
	public String doImp(){
		try {
			ImportExcel<PromotersExcelVO> imp = new ImportExcel<PromotersExcelVO>(PromotersExcelVO.class);
			List<PromotersExcelVO> result = null;
			result = (ArrayList<PromotersExcelVO>) imp.importExcel(getXlsfileupload());
			
			if(result != null && result.size() > 0){
				setAttr("info", promotersService.doImports(result));
			}else{
				setAttr("info","没有可导入信息");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "imp";
	}
	
	/**
	 * 导出促销信息表
	 * @return
	 * @throws Exception 
	 */
	public String exportPromoters() throws Exception{	
		setAttr("promotersHeader", Common.getExportHeader("PROMOTERS"));
		if(StringUtils.isNotBlank(vo.getPromotersName())){
			String name = new String(vo.getPromotersName().getBytes("ISO8859_1"),"UTF8");
			vo.setPromotersName(name);
		}
		if(vo.getCustom() != null && StringUtils.isNotBlank(vo.getCustom().getCustomName())){
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
			if(vo == null)
				vo = new PromotersVO();
			checkRole();
			vo.setObjectsPerPage(Integer.MAX_VALUE);
			List<Promoters> list=promotersService.getPageList(vo).getList();
			List<Integer> intArr = new ArrayList<Integer>();
			String[] heads = StringUtils.split(header, ",");
			for(int i =0; i< heads.length; i++){
				intArr.add(25);
			}
			
			List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
			Map<String ,String> map = null;
			for(Promoters p:list){
				map=new HashMap<String, String>();
				promotersService.exportPromoters(heads,p,map);
				listmap.add(map);
			}
			HttpServletResponse response = ServletActionContext.getResponse();
			String title = "促销员信息表";
			Integer[] columnSize = intArr.toArray(new Integer[]{});
			ExcelUtil.export(response, listmap, "促销员信息表.xls", title, heads, columnSize);	
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
	

	public PromotersVO getVo() {
		return vo;
	}
	public void setVo(PromotersVO vo) {
		this.vo = vo;
	}
	public CustomVO getCvo() {
		return cvo;
	}
	public void setCvo(CustomVO cvo) {
		this.cvo = cvo;
	}
	public StoreVO getSvo() {
		return svo;
	}
	public void setSvo(StoreVO svo) {
		this.svo = svo;
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
