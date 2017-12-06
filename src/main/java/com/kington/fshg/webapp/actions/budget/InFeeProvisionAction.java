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
import com.kington.fshg.common.ObjectUtil;
import com.kington.fshg.common.PublicType.ActType;
import com.kington.fshg.common.PublicType.ApproveState;
import com.kington.fshg.common.PublicType.VerDirection;
import com.kington.fshg.model.budget.InFeeProvision;
import com.kington.fshg.model.budget.InFeeProvisionVO;
import com.kington.fshg.model.charge.InFeeVerificationVO;
import com.kington.fshg.model.info.Area;
import com.kington.fshg.model.info.Custom;
import com.kington.fshg.model.system.User;
import com.kington.fshg.service.budget.InFeeProvisionService;
import com.kington.fshg.service.charge.InFeeVerificationService;
import com.kington.fshg.service.info.AreaService;
import com.kington.fshg.service.info.CustomsTypeService;
import com.kington.fshg.service.system.DictService;
import com.kington.fshg.service.system.UserService;
import com.kington.fshg.webapp.actions.BaseAction;
import com.kington.fshg.webapp.security.UserContext;

/**
 * 合同内条款设置
 * @author
 *
 */
public class InFeeProvisionAction extends BaseAction {
	private static final long serialVersionUID = -2590504458585177763L;

	@Resource
	private InFeeProvisionService inFeeProvisionService;
	@Resource
	private UserService userService;
	@Resource
	private AreaService areaService;
	@Resource
	private CustomsTypeService customsTypeService;
	@Resource
	private InFeeVerificationService inFeeVerService;
	@Resource
	private DictService dictService;
	
	private InFeeProvisionVO vo;
	
	private String header;
	
	public String list(){
		try {
			if(vo == null){
				vo = new InFeeProvisionVO();
				vo.setCreateTimeStart(DateFormat.date2Str(DateFormat.getBeforeMonthDay(), 2));
				vo.setCreateTimeEnd(DateFormat.date2Str(new Date(), 2));
			}	
			vo.setPageNumber(page);
			checkRole();
			
			pageList = inFeeProvisionService.getPageList(vo);
			
			setAttr("parentAreas",areaService.getFristNode());
			if(vo != null && vo.getCustom() != null 
					&& vo.getCustom().getArea() != null && vo.getCustom().getArea().getParentArea() != null)
				setAttr("areas",areaService.getByParentId(vo.getCustom().getArea().getParentArea().getId()));
			setAttr("customsTypes",customsTypeService.getLeafNode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	
	
	public String edit(){
		try {
			if(vo != null && Common.checkLong(vo.getId())){
				String start = vo.getCreateTimeStart();
				String end = vo.getCreateTimeEnd();
				
				vo = inFeeProvisionService.getVOById(vo.getId());
				
				vo.setCreateTimeStart(start);
				vo.setCreateTimeEnd(end);
				if(act == null)
					setAct(ActType.EDIT);
			}else{
				setAct(ActType.ADD);
			}
			
			if(vo == null)
				vo = new InFeeProvisionVO();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Common.PATH_EDIT;
	}
	
	public String update(){
		try {
			InFeeProvision po = inFeeProvisionService.saveOrUpdate(vo);
			inFeeProvisionService.countInFee(po);
			if(po != null)
				this.addActionMessage("操作成功！");
			else
				this.addActionError("操作失败！");
		} catch (Exception e) {
			return doException("操作失败！");
		}
		//vo = new InFeeProvisionVO();
		return list();
	}
	
	//批量删除
	public String delete(){
		boolean success = false;
		int count = 0;
		for(String str : ids.split(",")){
			try {	
					vo = inFeeProvisionService.getVOById(Long.parseLong(str));
					if(vo.getApproveState().equals(ApproveState.DSP))
					{
						if(inFeeProvisionService.delete(Long.parseLong(str)))
							count++;
					}
			} catch (Exception e) {
				continue;
			}
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
		vo = new InFeeProvisionVO();
		return list();
	}
	
	public String autoCreate() throws Exception{
		if(StringUtils.isNotBlank(ids)){
			boolean success = false;
			int count = 0;
			
			count = inFeeProvisionService.createProvisionNew(ids,vo);
			
			success = count > 0;
			if(success){
				String mess = "共生成" + count + "条数据";
				if(ids.split(",").length != count){
					mess+="," + (ids.split(",").length - count) + "条数据生成失败，可能已存在审核状态的预提不可更新";
				}
				this.addActionMessage(mess);
			}else{
				this.addActionError("生成失败，可能已存在审核状态的预提不可更新！");
			}
		}
		return list();
	}
	
	
	/**
	 * 根据预提编码判断是否存在
	 */
	public void checkByCode(){
		try {
			Long proId = 0L;
			InFeeProvision ifp = inFeeProvisionService.getByCode(vo.getCode());
			if(ifp != null)
				proId = ifp.getId();
			JsUtils.writeText(proId.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 执行审批操作
	 * @return
	 */
	public String approve(){
		for(String id : ids.split(",")){
			try {
				InFeeProvisionVO inVO = new InFeeProvisionVO();
				inVO = inFeeProvisionService.getVOById(Long.parseLong(id));
				ApproveState state = inVO.getApproveState();
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
				inVO.setApproveState(state);
				inFeeProvisionService.saveOrUpdate(inVO);
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.addActionMessage("审批成功！");
		return list();
		
		/*try {
			if(vo != null && Common.checkLong(vo.getId())){
				vo = inFeeProvisionService.getVOById(vo.getId());
				ApproveState state = vo.getApproveState();
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
				vo.setApproveState(state);
				inFeeProvisionService.saveOrUpdate(vo);
				this.addActionMessage("审批成功！");
			}
		} catch (Exception e) {
			this.addActionError("审批失败！");
			e.printStackTrace();
		}
		//vo = new InFeeProvisionVO();
		return list();*/
	}
	
	/**
	 * 执行核销操作
	 * @return
	 */
	public String ver(){
		boolean success = false;
		int count = 0;
		for(String str : ids.split(",")){
			try {	
				InFeeProvision pro = inFeeProvisionService.getById(Long.parseLong(str));
				if(pro != null && pro.getApproveState().equals(ApproveState.SPJS)){
					
					InFeeVerificationVO verVO = new InFeeVerificationVO();
					ObjectUtil.copy(pro, verVO);

					verVO.setId(null);
					verVO.setVerCode(inFeeVerService.createCode());
					verVO.setApproveState(ApproveState.DSP);
					verVO.setTotalFee(pro.getInFeeCount());
					verVO.setVerDirection(VerDirection.LCYT);
					verVO.setVerType(dictService.getByName("冲预提"));
					verVO.setInFeeProvision(pro);
					inFeeVerService.saveOrUpdate(verVO);
					
					count++;
				}
			} catch (Exception e) {
				continue;
			}
		}
		success = count > 0;
		if(success){
			String mess = "共核销" + count + "条数据";
			if(ids.split(",").length != count){
				mess+="," + (ids.split(",").length - count) + "条数据核销失败，可能状态不是审批结束，不可核销";
			}
			this.addActionMessage(mess);
		}else{
			this.addActionError("核销失败");				
		}	
		return list();
	}
	
	/**
	 * 获取合同内费用预提列表
	 * @return
	 */
	public String getProvision(){
		try {
			if(vo == null){
				vo = new InFeeProvisionVO();
				vo.setCreateTimeStart(DateFormat.date2Str(DateFormat.str2Date(DateFormat.getMonthStart(), 2), 2));
				vo.setCreateTimeEnd(DateFormat.date2Str(new Date(), 2));
			}
			vo.setPageNumber(page);
			vo.setApproveState(ApproveState.SPJS);
			
			checkRole();
			
			pageList = inFeeProvisionService.getPageList(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "getProvision";
	}
	
	/**
	 * 根据预提ID获取实体对象；
	 * 将对象的属性值组装成JSON格式
	 */
	public void getById() throws Exception{
		JSONArray json =new JSONArray();
		JSONObject obj = new JSONObject();
		if(vo != null && Common.checkLong(vo.getId())){
			vo = inFeeProvisionService.getVOById(vo.getId());
			obj.put("enterFee", vo.getEnterFee());
			obj.put("yearReturnFee", vo.getYearReturnFee());
			obj.put("fixedFee", vo.getFixedFee());
			obj.put("monthReturnFee", vo.getMonthReturnFee());
			obj.put("netInfoFee", vo.getNetInfoFee());
			obj.put("deliveryFee", vo.getDeliveryFee());
			obj.put("posterFee", vo.getPosterFee());
			obj.put("promotionFee", vo.getPromotionFee());
			obj.put("sponsorFee", vo.getSponsorFee());
			obj.put("lossFee", vo.getLossFee());
			obj.put("fixedDiscount", vo.getFixedDiscount());
			obj.put("pilesoilFee", vo.getPilesoilFee());
			obj.put("marketFee", vo.getMarketFee());
			obj.put("caseReturnFee", vo.getCaseReturnFee());
			obj.put("otherFee", vo.getOtherFee());
			obj.put("inFeeCount", vo.getInFeeCount());
		}
		json.add(obj);
		JsUtils.writeText(json.toString());
	}
	
	//导出合同内预提
	public String exportInFeeProvision() throws Exception{
		setAttr("inFeeProvisionHeader",Common.getExportHeader("INFEEPROVISION"));
		if(vo.getCustom()!=null&&StringUtils.isNotBlank(vo.getCustom().getCustomName())){
			String name = new String(vo.getCustom().getCustomName().getBytes("ISO8859_1"),"UTF8");
			vo.getCustom().setCustomName(name);
		}
		return "export";
	}
	
	/**
	 * 执行导出
	 * @return
	 * @throws Exception 
	 */
	public String doExport() throws Exception{
		if(vo==null){
			vo=new InFeeProvisionVO();
		}
		vo.setObjectsPerPage(Integer.MAX_VALUE);
		checkRole();
		
		List<InFeeProvision> list =inFeeProvisionService.inFeeProvision(vo);
		List<Integer> intArr = new ArrayList<Integer>();
		String[] heads = StringUtils.split(header, ",");
		for(int i =0; i< heads.length; i++){
			intArr.add(25);
		}
		
		List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
		Map<String ,String> map = null;
		for(InFeeProvision ifp : list){
			map = new HashMap<String,String>();
			inFeeProvisionService.exprotInFeeProvision(heads, ifp, map);
			listmap.add(map);
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		String title = "合同内预提表";
		Integer[] columnSize = intArr.toArray(new Integer[]{});
		ExcelUtil.export(response, listmap, "合同内预提表.xls", title, heads, columnSize);
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
	
	public String getHeader() {
		return header;
	}


	public void setHeader(String header) {
		this.header = header;
	}


	public InFeeProvisionVO getVo() {
		return vo;
	}
	public void setVo(InFeeProvisionVO vo) {
		this.vo = vo;
	}
	
	
}
