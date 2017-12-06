package com.kington.fshg.webapp.actions.budget;

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
import com.kington.fshg.common.ObjectUtil;
import com.kington.fshg.common.PublicType.ApproveState;
import com.kington.fshg.common.PublicType.ProjectType;
import com.kington.fshg.model.budget.InFeeProvisionVO;
import com.kington.fshg.model.budget.OutFeeProvisionVO;
import com.kington.fshg.model.budget.ProvisionProjectVO;
import com.kington.fshg.model.charge.InFeeVerification;
import com.kington.fshg.model.charge.InFeeVerificationVO;
import com.kington.fshg.model.charge.OutFeeVerification;
import com.kington.fshg.model.charge.OutFeeVerificationVO;
import com.kington.fshg.model.info.Area;
import com.kington.fshg.model.info.Custom;
import com.kington.fshg.model.info.CustomVO;
import com.kington.fshg.model.info.Store;
import com.kington.fshg.model.info.StoreVO;
import com.kington.fshg.model.system.User;
import com.kington.fshg.service.budget.InFeeProvisionService;
import com.kington.fshg.service.budget.OutFeeProvisionService;
import com.kington.fshg.service.budget.ProvisionProjectService;
import com.kington.fshg.service.charge.InFeeVerificationService;
import com.kington.fshg.service.charge.OutFeeVerificationService;
import com.kington.fshg.service.info.AreaService;
import com.kington.fshg.service.info.CustomService;
import com.kington.fshg.service.info.StoreService;
import com.kington.fshg.service.system.UserService;
import com.kington.fshg.webapp.actions.BaseAction;
import com.kington.fshg.webapp.security.UserContext;

public class AnalysisAction extends BaseAction {

	private static final long serialVersionUID = 3464863163728018918L;
	
	@Resource
	private InFeeProvisionService inFeeProvisionService;
	@Resource
	private OutFeeProvisionService outFeeProvisionService;
	@Resource
	private InFeeVerificationService inFeeVerificationService;
	@Resource
	private OutFeeVerificationService outFeeVerificationService;
	@Resource
	private CustomService customService;
	@Resource
	private AreaService areaService;
	@Resource
	private ProvisionProjectService provisionProjectService;
	@Resource
	private StoreService storeService;
	@Resource
	private UserService userService;
	
	private OutFeeProvisionVO vo;
	
	private String header;//导出表头
	private String type;
	
	public String budget() throws Exception{
		getCustomList();
		initVO();
		getInFeeMap();	
		//Map<Long, List<OutFeeProvision>> outFeeMap = outFeeProvisionService.getOutFeeMap(vo);
		vo.setApproveState(ApproveState.SPJS);
		
		setAttr("outFeeMap", outFeeProvisionService.getOutFeeMap(vo));
		
		getAreaList();
		return "budget";
	}
	
	public String charge() throws Exception{
		getCustomList();
		initVO();
		//预提
		//getInFeeMap();	
		//Map<Long, List<OutFeeProvision>> outFeeMap = outFeeProvisionService.getOutFeeMap(vo);
		//vo.setApproveState(ApproveState.SPJS);
		setAttr("outFeeMap", outFeeProvisionService.getOutFeeMap(vo));
		
		//核销
		InFeeVerificationVO inVerVO = new InFeeVerificationVO();
		OutFeeVerificationVO outVerVO = new OutFeeVerificationVO();
		if(vo.getCustom() != null){
			inVerVO.setCustom(vo.getCustom());
			outVerVO.setCustom(vo.getCustom());
			
		}
		//用户角色判断
		UserContext uc = UserContext.get();
		User user = userService.getByCde(uc.getUserCode());
		String roleName = user.getRoles().get(0).getRoleName();
		if(StringUtils.equals(roleName, "业务员")){
			if(inVerVO.getCustom() == null)
				inVerVO.setCustom(new Custom());
			if(outVerVO.getCustom() == null)
				outVerVO.setCustom(new Custom());
			User u = new User();
			u.setId(user.getId());
			inVerVO.getCustom().setUser(u);
			outVerVO.getCustom().setUser(u);
		}else if(StringUtils.equals(roleName, "地区经理") || StringUtils.equals(roleName, "大区经理")){
			List<Long> areaList = new ArrayList<Long>();
			for(Area a : user.getAreas()){
				areaList.add(a.getId());
			}
			if(StringUtils.equals(roleName, "地区经理")){
				inVerVO.setAreaIds(areaList);
				outVerVO.setAreaIds(areaList);
			}else if(StringUtils.equals(roleName, "大区经理")){
				inVerVO.setParentAreaIds(areaList);
				outVerVO.setParentAreaIds(areaList);
			}
		}
		
		inVerVO.setBeginTime(vo.getDateStart());
		inVerVO.setEndTime(vo.getDateEnd());
		inVerVO.setApproveState(ApproveState.SPJS);
		Map<Long, List<InFeeVerification>> inFeeVerMap = inFeeVerificationService.getInFeeVerMap(inVerVO);
		setAttr("inFeeVerMap", inFeeVerMap);
		
		outVerVO.setBeginTime(vo.getDateStart());
		outVerVO.setEndTime(vo.getDateEnd());
		outVerVO.setApproveState(ApproveState.SPJS);
		Map<Long, List<OutFeeVerification>> outFeeVerMap = outFeeVerificationService.getOutFeeVerMap(outVerVO);
		setAttr("outFeeVerMap", outFeeVerMap);
		
		getAreaList();
		return "charge";
	}
	
	public String sum() throws Exception{
		initVO();
		getAreaList();
		List<Object[]> list = outFeeProvisionService.getProVer(vo);
		setAttr("list", list);
		return "sum";
	}
	
	public String differ() throws Exception{
		getCustomList();
		initVO();
		getAreaList();
		
		setAttr("inFeeMap", outFeeProvisionService.getProMap(vo,"1"));
		setAttr("outFeeMap", outFeeProvisionService.getProMap(vo,"2"));
		
		return "differ";
	}
	
	public String productBudget() throws Exception{
		initVO();
		List<Object[]> list = outFeeProvisionService.getProDetailList(vo);
		setAttr("list", list);
		getAreaList();
		getProjectList();
		return "product-budget";
	}
	
	public String productCharge() throws Exception{
		//getCustomList();
		/*StoreVO storeVo = new StoreVO();
		if(vo != null){
			if(vo.getCustom() != null)
				storeVo.setCustom(vo.getCustom());
			storeVo.setStoreName(vo.getStoreName());
		}
		storeVo.setObjectsPerPage(Integer.MAX_VALUE);
		List<Store> list = storeService.getPageList(storeVo).getList();
		setAttr("storeList", list);*/
		getAreaList();
		getProjectList();
		initVO();
		//预提
		//Map<Long, List<Object[]>> proMap = outFeeProvisionService.getProDetailMap(vo);
		//setAttr("proMap", proMap);
		
		//核销
		//Map<Long, List<Object[]>> verMap = outFeeProvisionService.getProVerDetailMap(vo);
		setAttr("verList", outFeeProvisionService.getProVerDetailMap(vo));
		
		return "product-charge";
	}
	
	public String productSum() throws Exception{
		getAreaList();
		initVO();
		List<Object[]> list = outFeeProvisionService.getProductProVer(vo);
		setAttr("list", list);
		return "product-sum";
	}
	
	public void getInFeeMap() throws Exception{
		InFeeProvisionVO inVO = new InFeeProvisionVO();
		if(vo.getCustom() != null)
			inVO.setCustom(vo.getCustom());
		inVO.setCreateTimeStart(vo.getDateStart());
		inVO.setCreateTimeEnd(vo.getDateEnd());
		inVO.setApproveState(ApproveState.SPJS);
		
		//用户角色判断
		UserContext uc = UserContext.get();
		User user = userService.getByCde(uc.getUserCode());
		String roleName = user.getRoles().get(0).getRoleName();
		if(StringUtils.equals(roleName, "业务员")){
			if(inVO.getCustom() == null)
				inVO.setCustom(new Custom());
			User u = new User();
			u.setId(user.getId());
			inVO.getCustom().setUser(u);
		}else if(StringUtils.equals(roleName, "地区经理") || StringUtils.equals(roleName, "大区经理")){
			List<Long> areaList = new ArrayList<Long>();
			for(Area a : user.getAreas()){
				areaList.add(a.getId());
			}
			if(StringUtils.equals(roleName, "地区经理"))
				inVO.setAreaIds(areaList);
			else if(StringUtils.equals(roleName, "大区经理"))
				inVO.setParentAreaIds(areaList);
		}
		//Map<Long, List<InFeeProvision>> inFeeMap = inFeeProvisionService.getInFeeMap(inVO);
		setAttr("inFeeMap", inFeeProvisionService.getInFeeMap(inVO));
	}
	
	public void getCustomList() throws Exception{
		CustomVO customVO = new CustomVO();
		if(vo != null && vo.getCustom() != null)
			customVO = ObjectUtil.copy(vo.getCustom(), CustomVO.class);
		
		//用户角色判断
		UserContext uc = UserContext.get();
		User user = userService.getByCde(uc.getUserCode());
		String roleName = user.getRoles().get(0).getRoleName();
		if(StringUtils.equals(roleName, "业务员")){
			User u = new User();
			u.setId(user.getId());
			customVO.setUser(u);
		}else if(StringUtils.equals(roleName, "地区经理") || StringUtils.equals(roleName, "大区经理")){
			List<Long> areaList = new ArrayList<Long>();
			for(Area a : user.getAreas()){
				areaList.add(a.getId());
			}
			if(StringUtils.equals(roleName, "地区经理"))
				customVO.setAreaIds(areaList);
			else if(StringUtils.equals(roleName, "大区经理"))
				customVO.setParentAreaIds(areaList);
		}
		
		customVO.setObjectsPerPage(Integer.MAX_VALUE);
		customVO.initMyOrderStr("order by o.area.parentArea.id, o.area.id, o.id");
		setAttr("customList", customService.getPageList(customVO).getList());
	}
	
	public void getAreaList() throws Exception {
		setAttr("parentAreas",areaService.getFristNode());
		if(vo != null && vo.getCustom() != null 
				&& vo.getCustom().getArea() != null && vo.getCustom().getArea().getParentArea() != null)
		setAttr("areas",areaService.getByParentId(vo.getCustom().getArea().getParentArea().getId()));
	}
	
	public void initVO() throws Exception{
		if(vo == null) vo = new OutFeeProvisionVO();
		if(StringUtils.isEmpty(vo.getDateStart()))
			vo.setDateStart(DateFormat.getMonthFirst(new Date(), 0).split(" ")[0]);
		if(StringUtils.isEmpty(vo.getDateEnd()))
			vo.setDateEnd(DateFormat.date2Str(new Date(), 2));
		//用户角色判断
		UserContext uc = UserContext.get();
		User user = userService.getByCde(uc.getUserCode());
		String roleName = user.getRoles().get(0).getRoleName();
		if(StringUtils.equals(roleName, "业务员")){
			vo.setUserId(user.getId());
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
	
	public void getProjectList() throws Exception{
		ProvisionProjectVO projectVO = new ProvisionProjectVO();
		projectVO.setObjectsPerPage(Integer.MAX_VALUE);
		projectVO.setProjectType(ProjectType.CPMXL);
		setAttr("projectList", provisionProjectService.getPageList(projectVO).getList());
	}
	
	/**
	 * 导出预提明细表页面
	 * @return
	 * @throws Exception
	 */
	public String exportBudget() throws Exception{
		setAttr("budgetHeader", Common.getExportHeader("ANALYSISBUD"));
		String keyword = new String(vo.getCustom().getCustomName().getBytes("ISO8859_1"), "UTF8");
		vo.getCustom().setCustomName(keyword);
		return "exportBudget";
	}
	
	/**
	 * 导出预提核销明细页面
	 * @return
	 * @throws Exception
	 */
	public String exportCharge() throws Exception{
		setAttr("chargeHeader", Common.getExportHeader("ANALYSISCHAR"));
		String keyword = new String(vo.getCustom().getCustomName().getBytes("ISO8859_1"), "UTF8");
		vo.getCustom().setCustomName(keyword);
		return "exportCharge";
	}
	
	/**
	 * 执行导出操作
	 * @return
	 * @throws Exception
	 */
	public String doExportBudget() throws Exception{
		//获取客户列表数据
		CustomVO customVO = new CustomVO();
		if(vo != null && vo.getCustom() != null)
			customVO = ObjectUtil.copy(vo.getCustom(), CustomVO.class);
		customVO.setObjectsPerPage(Integer.MAX_VALUE);
		//用户角色判断
		UserContext uc = UserContext.get();
		User user = userService.getByCde(uc.getUserCode());
		String roleName = user.getRoles().get(0).getRoleName();
		if(StringUtils.equals(roleName, "业务员")){
			User u = new User();
			u.setId(user.getId());
			customVO.setUser(u);
		}else if(StringUtils.equals(roleName, "地区经理") || StringUtils.equals(roleName, "大区经理")){
			List<Long> areaList = new ArrayList<Long>();
			for(Area a : user.getAreas()){
				areaList.add(a.getId());
			}
			if(StringUtils.equals(roleName, "地区经理"))
				customVO.setAreaIds(areaList);
			else if(StringUtils.equals(roleName, "大区经理"))
				customVO.setParentAreaIds(areaList);
		}
		customVO.initMyOrderStr("order by o.area.parentArea.id, o.area.id, o.id");
		List<Custom> customList = customService.getPageList(customVO).getList();
		
		initVO();
		
		String[] heads = StringUtils.split(header, ",");
		List<Integer> intArr = new ArrayList<Integer>();
		for(int i=0; i< heads.length; i++){
			intArr.add(25);
		}
		List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
		Map<String,String> map = new HashMap<String,String>();
		
		outFeeProvisionService.exportBudget(heads, customList, map, listmap, vo ,type);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		Integer[] columnSize = intArr.toArray(new Integer[]{});
		if(type.equals("BUDGET")){
			ExcelUtil.export(response, listmap, "预提明细表.xls","预提明细表",heads, columnSize);
		}
		if(type.equals("CHARGE"))
			ExcelUtil.export(response, listmap, "核销明细表.xls", "核销明细表", heads, columnSize);
		
		return null;
	}

	/**
	 * 导出预提核销余额表
	 * @return
	 * @throws Exception
	 */
	public String exportSum() throws Exception{
		setAttr("sumHeaders", Common.getExportHeader("ANALYSISSUM"));
		String keyword = new String(vo.getCustom().getCustomName().getBytes("ISO8859_1"),"UTF8");
		vo.getCustom().setCustomName(keyword);
		return "exportSum";
	}
	
	public String doExportSum() throws Exception{
		initVO();
		List<Object[]> list = outFeeProvisionService.getProVer(vo);
		
		String[] heads = StringUtils.split(header, ",");
		List<Integer> intArr = new ArrayList<Integer>();
		for(int i = 0; i< heads.length; i++){
			intArr.add(25);
		}
		
		List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
		Map<String,String> map = null;
		for(Object[] obj : list){
			map = new HashMap<String,String>();
			outFeeProvisionService.exportSum(heads, obj, map);
			listmap.add(map);
		}
		
		HttpServletResponse response = ServletActionContext.getResponse();
		String title = "客户费用分析表";
		Integer[] columnSize = intArr.toArray(new Integer[]{});
		ExcelUtil.export(response, listmap, "客户费用分析表.xls", title, heads, columnSize);
		
		return null;
	}
	
	/**
	 * 导出产品费用预提明细表
	 * @return
	 * @throws Exception
	 */
	public String exportProBudget() throws Exception{
		setAttr("proBudHeader", Common.getExportHeader("PRODUCTBUDGET"));
		String customName = new String(vo.getCustom().getCustomName().getBytes("ISO8859_1"),"UTF8");
		String productName = new String(vo.getProduct().getProductName().getBytes("ISO8859_1"),"UTF8");
		if(StringUtils.isNotBlank(vo.getStoreName())){
			String storeName = new String(vo.getStoreName().getBytes("ISO8859_1"),"UTF8");
			vo.setStoreName(storeName);
		}
		vo.getCustom().setCustomName(customName);
		vo.getProduct().setProductName(productName);
		return "exportProBudget";
	}
	
	public String doExportProBudget() throws Exception{
		initVO();
		List<Object[]> list = outFeeProvisionService.getProDetailList(vo);
		
		String[] heads = StringUtils.split(header, ",");
		List<Integer> intArr = new ArrayList<Integer>();
		for(int i=0; i< heads.length; i++){
			intArr.add(25);
		}
		
		List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
		Map<String,String> map = null;
		for(Object[] obj : list){
			map = new HashMap<String,String>();
			outFeeProvisionService.exportProBudget(heads, obj, map);
			listmap.add(map);
		}
		
		HttpServletResponse response = ServletActionContext.getResponse();
		String title = "产品费用预提明细表";
		Integer[] columnSize = intArr.toArray(new Integer[]{});
		ExcelUtil.export(response, listmap, "产品费用预提明细表.xls", title, heads, columnSize);
		
		return null;
	}
	
	/**
	 * 导出产品费用预提核销明细表页面
	 * @return
	 * @throws Exception
	 */
	public String exportProCharge() throws Exception{
		setAttr("proCharHeader", Common.getExportHeader("PRODUCTCHARGE"));
		String customName = new String(vo.getCustom().getCustomName().getBytes("ISO8859_1"),"UTF8");
		String productName = new String(vo.getProduct().getProductName().getBytes("ISO8859_1"),"UTF8");
		String storeName = new String(vo.getStoreName().getBytes("ISO8859_1"),"UTF8");
		vo.setStoreName(storeName);
		vo.getCustom().setCustomName(customName);
		vo.getProduct().setProductName(productName);
		return "exportProCharge";
	}
	
	/**
	 * 执行导出
	 * @return
	 * @throws Exception
	 */
	public String doExportProCharge() throws Exception{
		String[] heads = StringUtils.split(header, ",");
		List<Integer> intArr = new ArrayList<Integer>();
		for(int i  =0;i < heads.length; i++){
			intArr.add(25);
		}
		
		List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
		Map<String, String> map = new HashMap<String, String>();
		outFeeProvisionService.exportProCharge(heads, listmap, map, vo);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		String title="产品费用核销明细表";
		Integer[] columnSize = intArr.toArray(new Integer[]{});
		ExcelUtil.export(response, listmap, "产品费用核销明细表.xls", title, heads, columnSize);
		
		return null;
	}
	
	/**
	 * 产品费用预提核销余额表
	 * @return
	 * @throws Exception
	 */
	public String exportProSum() throws Exception{
		setAttr("proSumHeader", Common.getExportHeader("PRODUCTSUM"));
		String customName = new String(vo.getCustom().getCustomName().getBytes("ISO8859_1"),"UTF8");
		String productName = new String(vo.getProduct().getProductName().getBytes("ISO8859_1"),"UTF8");
		String storeName = new String(vo.getStoreName().getBytes("ISO8859_1"),"UTF8");
		vo.setStoreName(storeName);
		vo.getCustom().setCustomName(customName);
		vo.getProduct().setProductName(productName);
		return "exportProSum";
	}
	
	/**
	 * 执行产品费用预提核销余额表的导出
	 * @return
	 * @throws Exception
	 */
	public String doExportProSum() throws Exception{
		initVO();
		List<Object[]> list = outFeeProvisionService.getProductProVer(vo);
		
		String[] heads = StringUtils.split(header, ",");
		List<Integer> intArr = new ArrayList<Integer>();
		for(int i =0; i< heads.length; i++){
			intArr.add(25);
		}
		
		List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
		Map<String,String> map = null;
		for(Object[] obj : list){
			
			Double d7 = obj[7] != null ? Double.parseDouble(obj[7].toString()) : 0d;
			Double d8 = obj[8] != null ? Double.parseDouble(obj[8].toString()) : 0d;
			Double d9 = obj[9] != null ? Double.parseDouble(obj[9].toString()) : 0d;
			if(d7.equals(d8)  && (d7 - d8 - d9) == 0)
				continue;
			
			map = new HashMap<String, String>();
			outFeeProvisionService.exportProSum(heads, obj, map);
			listmap.add(map);
		}
		
		HttpServletResponse response = ServletActionContext.getResponse();
		String title = "产品费用预提核销余额表";
		Integer[] columnSize = intArr.toArray(new Integer[]{});
		ExcelUtil.export(response, listmap, "产品费用预提核销余额表.xls", title, heads, columnSize);
		return null;
	}
	
	/**
	 * 导出预提核销余额表
	 * @return
	 * @throws Exception
	 */
	public String exportDiffer() throws Exception{
		setAttr("differHeader", Common.getExportHeader("ANALYSISDIFFER"));
		String keyword = new String(vo.getCustom().getCustomName().getBytes("ISO8859_1"), "UTF8");
		vo.getCustom().setCustomName(keyword);
		return "exportDiffer";
	}
	
	/**
	 * 执行导出操作
	 * @return
	 * @throws Exception
	 */
	public String doExportDiffer() throws Exception{
		//获取客户列表数据
		CustomVO customVO = new CustomVO();
		if(vo != null && vo.getCustom() != null)
			customVO = ObjectUtil.copy(vo.getCustom(), CustomVO.class);
		customVO.setObjectsPerPage(Integer.MAX_VALUE);
		customVO.initMyOrderStr("order by o.area.parentArea.id, o.area.id, o.id");
		List<Custom> customList = customService.getPageList(customVO).getList();
		
		initVO();
		
		String[] heads = StringUtils.split(header, ",");
		List<Integer> intArr = new ArrayList<Integer>();
		for(int i=0; i< heads.length; i++){
			intArr.add(25);
		}
		List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
		Map<String,String> map = new HashMap<String,String>();
		
		outFeeProvisionService.exportDiffer(heads, customList, map, listmap, vo );
		
		HttpServletResponse response = ServletActionContext.getResponse();
		Integer[] columnSize = intArr.toArray(new Integer[]{});
		ExcelUtil.export(response, listmap, "预提核销余额表.xls","预提核销余额表",heads, columnSize);
		
		return null;
	}

	
	public OutFeeProvisionVO getVo() {
		return vo;
	}

	public void setVo(OutFeeProvisionVO vo) {
		this.vo = vo;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
