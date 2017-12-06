package com.kington.fshg.webapp.actions.budget;

import java.util.ArrayList;
import java.util.Date;
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
import com.kington.fshg.common.DateFormat;
import com.kington.fshg.common.ExcelUtil;
import com.kington.fshg.common.JsUtils;
import com.kington.fshg.common.PublicType;
import com.kington.fshg.common.PublicType.ActType;
import com.kington.fshg.common.PublicType.ApproveState;
import com.kington.fshg.common.PublicType.ProjectType;
import com.kington.fshg.model.budget.OutFeeProvision;
import com.kington.fshg.model.budget.OutFeeProvisionVO;
import com.kington.fshg.model.budget.ProvisionProductDetail;
import com.kington.fshg.model.budget.ProvisionProductDetailVO;
import com.kington.fshg.model.budget.ProvisionProject;
import com.kington.fshg.model.info.Area;
import com.kington.fshg.model.info.Custom;
import com.kington.fshg.model.info.Product;
import com.kington.fshg.model.system.User;
import com.kington.fshg.service.budget.OutFeeProvisionService;
import com.kington.fshg.service.budget.ProvisionProductDetailService;
import com.kington.fshg.service.budget.ProvisionProjectService;
import com.kington.fshg.service.info.CustomService;
import com.kington.fshg.service.info.ProductService;
import com.kington.fshg.service.system.UserService;
import com.kington.fshg.webapp.actions.BaseAction;
import com.kington.fshg.webapp.security.UserContext;

/**
 *
 * 合同外费用预提
 *
 */
public class OutFeeProvisionAction extends BaseAction {
	private static final long serialVersionUID = -3065739852056324682L;

	@Resource
	private OutFeeProvisionService outFeeProvisionService;
	@Resource
	private UserService userService;
	@Resource
	private ProductService productService;
	@Resource
	private ProvisionProductDetailService ppdService;
	@Resource
	private ProvisionProjectService provisionProjectService;
	
	private OutFeeProvisionVO vo;
	
	private String storeNum;//门店数
	private String feeScale;//费用比例
	private String header; //导出表头
	
	private List<ProvisionProductDetailVO> ppdList = new ArrayList<ProvisionProductDetailVO>();
	
	public String list(){
		try {
			if(vo == null){
				vo = new OutFeeProvisionVO();
				vo.setDateStart(DateFormat.date2Str(DateFormat.getBeforeMonthDay(), 2));
				vo.setDateEnd(DateFormat.date2Str(new Date(), 2));
			}
			vo.setPageNumber(page);
			checkRole();
			
			pageList = outFeeProvisionService.getPageList(vo);
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
				String startDate = vo.getStartDate();
				String endDate = vo.getEndDate();
				
				vo = outFeeProvisionService.getVOById(vo.getId());
				
				vo.setDateStart(dateStart);
				vo.setDateEnd(dateEnd);
				vo.setStartDate(startDate);
				vo.setEndDate(endDate);
				
				setAttr("DetailList", ppdService.getByProId(vo.getId()));
				if(act == null)
					setAct(ActType.EDIT);
			}else{
				setAct(ActType.ADD);
			}
			if(vo == null){
				vo = new OutFeeProvisionVO();
				vo.setProvisionTime(new Date());
				vo.setStartTime(new Date());
				vo.setEndTime(new Date());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Common.PATH_EDIT;
	}

	/**
	 * 更新或添加数据
	 * @return
	 */
	public synchronized String update(){
		try {
			if(vo != null && !Common.checkLong(vo.getId()))
				vo.setProvisionCode(outFeeProvisionService.getCode());
			vo.setTotalFee(PublicType.setDoubleScale(vo.getTotalFee()));
			OutFeeProvision po = outFeeProvisionService.saveOrUpdate(vo);
			Double totalFee = 0d;
			//根据预提ID删除产品明细
			ppdService.deletProductDetail(po.getId());
			
			//当项目类型为：促销员工资类
			/*if(vo.getProvisionProject().getProjectType() == ProjectType.CXYGZL){
				vo.setStoreScale(storeNum+ ","+ feeScale);//保存门店数，费用比例
			}else */
			if(vo.getProvisionProject().getProjectType() == ProjectType.CPMXL){//当项目类型为产品明细类
				if(ppdList != null && ppdList.size() > 0){
					//保存产品明细
					for(ProvisionProductDetailVO ppdVO : ppdList){
						totalFee+=ppdVO.getCost();
						ppdVO.setProvision(po);
						ppdService.saveOrUpdate(ppdVO);
					}
				}
				//vo.setStoreScale(null);//将门店数比例设为空
				vo.setTotalFee(totalFee);
			}
			
			vo.setApproveState(ApproveState.DSP);
			vo.setId(po.getId());
			outFeeProvisionService.saveOrUpdate(vo);
			this.addActionMessage("操作成功！");
			
		} catch (Exception e) {
			e.printStackTrace();
			this.addActionError("操作失败！");
		}
		//vo = new OutFeeProvisionVO();
		return list();
	}
	
	/**
	 * 批量删除
	 * @return
	 */
	public String delete(){
		boolean success = false;
		int count = 0;
		for(String str : ids.split(",")){
			try {
				OutFeeProvisionVO oVO = outFeeProvisionService.getVOById(Long.parseLong(str));
				if(oVO.getApproveState().equals(ApproveState.DSP)){
					if(outFeeProvisionService.delete(Long.parseLong(str))){
					count++;
					}
				}
			} catch (Exception e) {
				continue;
			}
		}
		
		success = count > 0;
		if(success){
			String mess = "共删除" + count + "条数据";
			if(ids.split(",").length != count){
				mess +="," + (ids.split(",").length - count) + "条数据删除失败，可能存在关联不可删除！";
			}
			this.addActionMessage(mess);
		}else{
			this.addActionError("数据删除失败，可能存在关联不可删除！");
		}
		//vo = new OutFeeProvisionVO();
		return list();
	}
	
	/**
	 * 审核页面
	 * @return
	 */
	public String audit(){
		try {
			if(vo != null && Common.checkLong(vo.getId())){
				String dateStart = vo.getDateStart();
				String dateEnd = vo.getDateEnd();
				String startDate = vo.getStartDate();
				String endDate = vo.getEndDate();
				
				vo = outFeeProvisionService.getVOById(vo.getId());
				
				vo.setDateStart(dateStart);
				vo.setDateEnd(dateEnd);
				vo.setStartDate(startDate);
				vo.setEndDate(endDate);
				setAct(act);
			}else{
				this.addActionError("无效的操作ID！");
				return list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Common.PATH_AUDIT;
	}
	
	/**
	 * 执行审批操作
	 * @return
	 */
	public String approve(){
		for(String id : ids.split(",")){
			try {
				OutFeeProvisionVO outVo = new OutFeeProvisionVO();
				outVo = outFeeProvisionService.getVOById(Long.parseLong(id));
				ApproveState state = outVo.getApproveState();
			//根据当前的审批状态，更新为下一审批环节
			//审批
			if(act == ActType.AUDIT){
				if(state == ApproveState.DSP){
					state = ApproveState.ESZ;
				}else if(state == ApproveState.ESZ){
					state = ApproveState.ZSZ;
				}else if(state == ApproveState.ZSZ){
					state = ApproveState.SPJS;
				}
			}else if(act == ActType.REAUDIT){//反审批
				if(state == ApproveState.ESZ){
					state = ApproveState.DSP;
				}else if(state == ApproveState.ZSZ){
					state = ApproveState.ESZ;
				}else if(state == ApproveState.SPJS){
					state = ApproveState.ZSZ;
				}
			}
			outVo.setApproveState(state);
			outFeeProvisionService.saveOrUpdate(outVo);
			}catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			}
		this.addActionMessage("审批成功！");
		return list();
	}
	
	
	/**
	 * 通过预提编码，验证是否已存在
	 */
	public void checkByCode(){
		try {
			Long proId = 0L;
			OutFeeProvision po = outFeeProvisionService.getByCde(vo.getProvisionCode());
			if(po != null)
				proId = po.getId();
			JsUtils.writeText(proId.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据客户ID，获取该客户的所有产品；
	 */
	public void getProductMap(){
		try {
			JSONArray json = new JSONArray();
			List<Product> list = productService.getListByCustomId(vo.getCustom().getId());
			for(int i=0;i< list.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("id", list.get(i).getId());
				obj.put("name", list.get(i).getProductName());
				json.add(obj);
			}
			JsUtils.writeText(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过预提ID，获取产品明细，并组装成JSON格式；
	 */
	public void getDetail(){
		try {
			JSONArray json = new JSONArray();
			List<ProvisionProductDetail> list = ppdService.getByProId(vo.getId());
			for(int i=0;i< list.size() ; i++){
				JSONObject obj = new JSONObject();
				obj.put("id", list.get(i).getId());
				obj.put("productId", list.get(i).getStoreProduct().getProduct().getId());//产品ID
				obj.put("productName", list.get(i).getStoreProduct().getProduct().getProductName());
				obj.put("productType", list.get(i).getStoreProduct().getProduct().getProductType().getProductTypeName());
				obj.put("stockCde", list.get(i).getStoreProduct().getProduct().getStockCde());
				obj.put("number", list.get(i).getStoreProduct().getProduct().getNumber());
				obj.put("standard", list.get(i).getStoreProduct().getProduct().getStandard());
				obj.put("cost", list.get(i).getCost());//费用
				obj.put("storeName", list.get(i).getStoreProduct().getStore().getStoreName());
				obj.put("remark", list.get(i).getRemark());
				obj.put("storeProductId", list.get(i).getStoreProduct().getId());
				json.add(obj);
			}
			JsUtils.writeText(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据产品明细ID ，删除单个产品明细
	 */
	public void deleteDetail(){
		try {
			String detailId = ServletActionContext.getRequest().getParameter("detailId");
			if(vo != null && Common.checkLong(vo.getId())){
				vo = outFeeProvisionService.getVOById(vo.getId());
				ProvisionProductDetailVO ppdVO = ppdService.getVOById(Long.parseLong(detailId));
				//删除产品明细的同时，更新预提总费用
				if(ppdVO != null && ppdVO.getCost() != null){
					vo.setTotalFee(vo.getTotalFee() - ppdVO.getCost());
					outFeeProvisionService.saveOrUpdate(vo);
				}
			}
			int count = ppdService.delete(Long.parseLong(detailId));
			JsUtils.writeText(String.valueOf(count));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 获取合同外费用预提
	 * @return
	 */
	public String getProvision(){
		try {
			if(vo == null){
				vo = new OutFeeProvisionVO();
				vo.setDateStart(DateFormat.date2Str(DateFormat.getBeforeMonthDay(), 2));
				vo.setDateEnd(DateFormat.date2Str(new Date(), 2));
			
			}
			vo.setPageNumber(page);			
			vo.setApproveState(ApproveState.SPJS);
			checkRole();
			
			pageList = outFeeProvisionService.getPageList(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "getProvision";
	}
	
	
	/**
	 * 角色判断
	 * @throws Exception
	 */
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
	
	/**
	 * 导出合同外预提
	 * @return
	 * @throws Exception 
	 */
	public String exportOutFeeProvision() throws Exception{
		setAttr("outFeeProvisionHeader", Common.getExportHeader("OUTFEEPROVISION"));
		if(vo.getCustom()!=null&&StringUtils.isNotBlank(vo.getCustom().getCustomName())){
			String name = new String(vo.getCustom().getCustomName().getBytes("ISO8859_1"),"UTF8");
			vo.getCustom().setCustomName(name);
		}
		if(vo.getProvisionProject()!=null&&StringUtils.isNotBlank(vo.getProvisionProject().getFeeName())){
			String name = new String(vo.getProvisionProject().getFeeName().getBytes("ISO8859_1"),"UTF8");
			vo.getProvisionProject().setFeeName(name);
		}
		return "export";
	}
	
	/**
	 * 执行导出
	 * @return
	 */
	public String doExport(){
		try{
			if(vo == null){
				vo = new OutFeeProvisionVO();
				vo.setDateEnd(DateFormat.date2Str(new Date(), 10));
				vo.setDateStart(DateFormat.date2Str(new Date(), 10));
			}
			vo.setObjectsPerPage(Integer.MAX_VALUE);
			//角色判断
			checkRole();
			
			List<OutFeeProvision> list=outFeeProvisionService.outFeeProvision(vo);
			List<Integer> intArr = new ArrayList<Integer>();
			String[] heads = StringUtils.split(header, ",");
			for(int i =0; i< heads.length; i++){
				intArr.add(25);
			}
			
			List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
			Map<String ,String> map = null;
			for(OutFeeProvision ofp:list){
				map=new HashMap<String, String>();
				outFeeProvisionService.exportOutFeeProvision(heads,ofp,map);
				listmap.add(map);
			}
			HttpServletResponse response = ServletActionContext.getResponse();
			String title = "合同外预提表";
			Integer[] columnSize = intArr.toArray(new Integer[]{});
			ExcelUtil.export(response, listmap, "合同外预提表.xls", title, heads, columnSize);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public String createSalePro(){
		ProvisionProject pp;
		try {
			pp = provisionProjectService.getByName("促销员工资");
			if(pp == null)
				this.addActionError("请添加费用名称为\"促销员工资\"的合同外预提项目");
			else{
				//删除预提时间区间内的促销员工资预提
				Integer deleteCount = outFeeProvisionService.deleteSalePro(vo, pp);
				//重新生成提时间区间内的促销员工资预提
				Integer createCount = outFeeProvisionService.createSalePro(vo, pp);
				
				this.addActionMessage("共删除"+ deleteCount + "条促销员工资预提，共生成"+ createCount +"条促销员工资预提");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.addActionError("操作异常，请联系管理员");
		}
		return list();
	}
	
	public OutFeeProvisionVO getVo() {
		return vo;
	}

	public void setVo(OutFeeProvisionVO vo) {
		this.vo = vo;
	}

	public String getStoreNum() {
		return storeNum;
	}

	public void setStoreNum(String storeNum) {
		this.storeNum = storeNum;
	}

	public String getFeeScale() {
		return feeScale;
	}

	public void setFeeScale(String feeScale) {
		this.feeScale = feeScale;
	}

	public List<ProvisionProductDetailVO> getPpdList() {
		return ppdList;
	}

	public void setPpdList(List<ProvisionProductDetailVO> ppdList) {
		this.ppdList = ppdList;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}
	
	
}
