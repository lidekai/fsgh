package com.kington.fshg.webapp.actions.logistic;

import java.io.UnsupportedEncodingException;
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
import com.kington.fshg.model.info.Area;
import com.kington.fshg.model.info.Custom;
import com.kington.fshg.model.logistic.LogisticsVO;
import com.kington.fshg.model.system.User;
import com.kington.fshg.service.info.AreaService;
import com.kington.fshg.service.logistic.LogisticService;
import com.kington.fshg.service.system.UserService;
import com.kington.fshg.webapp.actions.BaseAction;
import com.kington.fshg.webapp.security.UserContext;

public class LogisticsAnalysisAction extends BaseAction {

	private static final long serialVersionUID = 6541306281493589487L;
	
	@Resource
	private LogisticService logisticService;
	@Resource
	private AreaService areaService;
	@Resource
	private UserService userService;
	
	private LogisticsVO vo;
	private String header;
	
	public String logisticsDetail() throws Exception {
		if(vo == null)	vo = new LogisticsVO();
		if(StringUtils.isEmpty(vo.getOrderStartTime()))
			vo.setOrderStartTime(DateFormat.date2Str(new Date(), 10));
		
		checkRole();
		
		//List<Object[]> list = logisticService.logisticDetail(vo);
		setAttr("resultList", logisticService.logisticDetail(vo));
		setAttr("parentAreas", areaService.getFristNode());
		return "detail";
	}
	
	public String logisticsAnalysis() throws Exception{
		if(vo == null)	vo = new LogisticsVO();
		if(StringUtils.isEmpty(vo.getOrderStartTime()))
			vo.setOrderStartTime(DateFormat.date2Str(new Date(), 10));
		checkRole();
		
		//List<Object[]> list = logisticService.logisticDetail(vo);
		setAttr("resultList", logisticService.logisticAnalysis(vo));
		setAttr("parentAreas", areaService.getFristNode());
		return "list";
	}
	
	/**
	 * 导出物流费明细页面
	 * @return
	 */
	public String export() throws Exception{
		setAttr("detailHeader", Common.getExportHeader("LOGDETAIL"));
		String keyword = new String(vo.getCustom().getCustomName().getBytes("ISO8859_1"), "UTF8");
		vo.getCustom().setCustomName(keyword);
		return "export";
	}

	/**
	 * 执行导出
	 * @return
	 * @throws Exception
	 */
	public String exportExcel() throws Exception{
		if(vo == null)
			vo = new LogisticsVO();
		vo.setObjectsPerPage(Integer.MAX_VALUE);
		checkRole();
		List<Object[]> list = logisticService.logisticDetail(vo);
		List<Integer> intArr = new ArrayList<Integer>();
		String[] heads = StringUtils.split(header, ",");
		for(int i =0; i< heads.length; i++){
			intArr.add(25);
		}
		
		List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
		Map<String ,String> map = null;
		for(Object[] o : list){
			map = new HashMap<String,String>();
			logisticService.exportDetail(heads, o, map);
			listmap.add(map);
		}
		
		HttpServletResponse response = ServletActionContext.getResponse();
		String title = "物流费明细表";
		Integer[] columnSize = intArr.toArray(new Integer[]{});
		ExcelUtil.export(response, listmap, "物流费明细表.xls", title, heads, columnSize);
		return null;
	}
	
	/*
	 * 导出物流费分析表
	 */
	public String exportAnalysis(){
		setAttr("analysisHeader", Common.getExportHeader("LOGANALYSIS"));
		return "exportAnalysis";
	}
	
	public String exportAnalysisExcel() throws Exception{
		if(vo == null)
			vo = new LogisticsVO();
		
		checkRole();
		vo.setObjectsPerPage(Integer.MAX_VALUE);
		List<Object[]> list = logisticService.logisticAnalysis(vo);
		
		List<Integer> intArr = new ArrayList<Integer>();
		String[] heads = StringUtils.split(header, ",");
		for(int i = 0; i< heads.length; i++){
			intArr.add(25);
		}
		
		List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
		Map<String ,String> map = null ;
		for(Object[] obj : list){
			map = new HashMap<String , String>();
			logisticService.exportAnalysis(heads, obj, map);
			listmap.add(map);
		}
		
		HttpServletResponse response = ServletActionContext.getResponse();
		String title = "物流费分析表";
		Integer[] columnSize = intArr.toArray(new Integer[]{});
		ExcelUtil.export(response, listmap, "物流费分析表.xls", title, heads, columnSize);
		return null;
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

	public LogisticsVO getVo() {
		return vo;
	}

	public void setVo(LogisticsVO vo) {
		this.vo = vo;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}
	
}
