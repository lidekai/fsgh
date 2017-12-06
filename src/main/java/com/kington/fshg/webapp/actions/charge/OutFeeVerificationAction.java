package com.kington.fshg.webapp.actions.charge;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.DateFormat;
import com.kington.fshg.common.ExcelUtil;
import com.kington.fshg.common.JsUtils;
import com.kington.fshg.common.PublicType;
import com.kington.fshg.common.PublicType.ActType;
import com.kington.fshg.common.PublicType.ApproveState;
import com.kington.fshg.common.PublicType.DictType;
import com.kington.fshg.common.PublicType.ProjectType;
import com.kington.fshg.model.budget.OutFeeProvisionVO;
import com.kington.fshg.model.budget.ProvisionProductDetail;
import com.kington.fshg.model.budget.ProvisionProductDetailVO;
import com.kington.fshg.model.charge.OutFeeVerification;
import com.kington.fshg.model.charge.OutFeeVerificationVO;
import com.kington.fshg.model.charge.VerificationProductDetailVO;
import com.kington.fshg.model.info.Area;
import com.kington.fshg.model.info.Custom;
import com.kington.fshg.model.system.Dict;
import com.kington.fshg.model.system.User;
import com.kington.fshg.service.budget.OutFeeProvisionService;
import com.kington.fshg.service.budget.ProvisionProductDetailService;
import com.kington.fshg.service.charge.InFeeVerificationService;
import com.kington.fshg.service.charge.OutFeeVerificationService;
import com.kington.fshg.service.charge.VerificationProductDetailService;
import com.kington.fshg.service.info.AreaService;
import com.kington.fshg.service.info.CustomsTypeService;
import com.kington.fshg.service.system.DictService;
import com.kington.fshg.service.system.UserService;
import com.kington.fshg.webapp.actions.BaseAction;
import com.kington.fshg.webapp.security.UserContext;

/**
 *	合同外费用核销 
 *
 */
public class OutFeeVerificationAction extends BaseAction {
	private static final long serialVersionUID = -769806392814332892L;

	
	@Resource
	private OutFeeVerificationService outFeeService;
	@Resource
	private AreaService areaService;
	@Resource
	private CustomsTypeService customsTypeService;
	@Resource
	private UserService userService;
	@Resource
	private OutFeeProvisionService outFeeProvisionService;
	@Resource
	private ProvisionProductDetailService ppdService;
	@Resource
	private VerificationProductDetailService vpdService;
	@Resource
	private DictService dictService;
	@Resource
	private InFeeVerificationService inFeeService;
	
	private OutFeeVerificationVO vo;
	private List<VerificationProductDetailVO> vpdList = new ArrayList<VerificationProductDetailVO>();//核销产品明细List
	private List<ProvisionProductDetailVO> ppdList = new ArrayList<ProvisionProductDetailVO>();//预提产品明细List
	private String storeNum;//门店数
	private String feeScale;//费用比例
	
	private String header; //导出表头

	public String list(){
		try {
			if(vo == null){
				vo = new OutFeeVerificationVO();
				vo.setBeginTime(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
				vo.setEndTime(DateFormat.date2Str(new Date(), 2));
			}
			vo.setPageNumber(page);
			
			checkRole();
			
			pageList = outFeeService.getPageList(vo);
			setAttr("verTypeList" , dictService.getByType(DictType.HXLX));
			setAttr("parentAreas",areaService.getFristNode());
			if(vo != null && vo.getCustom() != null 
					&& vo.getCustom().getArea() != null && vo.getCustom().getArea().getParentArea() != null)
				setAttr("areas",areaService.getByParentId(vo.getCustom().getArea().getParentArea().getId()));
			setAttr("customsTypes",customsTypeService.getLeafNode());
		}catch (Exception e) {
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
				String beginTime = vo.getBeginTime();
				String endTime = vo.getEndTime();
				
				vo = outFeeService.getVOById(vo.getId());
				vo.setBeginTime(beginTime);
				vo.setEndTime(endTime);
				
				if(vo.getOutFeeProvision().getProvisionProject().getProjectType() == ProjectType.CPMXL){
					setAttr("detailList", vpdService.getByVerId(vo.getId()));
				}else if(vo.getOutFeeProvision().getProvisionProject().getProjectType() == ProjectType.CXYGZL){
					storeNum = vo.getStoreScale().split(",")[0];
					feeScale = vo.getStoreScale().split(",")[1];
				}
				if(act == null)
					setAct(ActType.EDIT);
			}else{
				setAct(ActType.ADD);
			}
			
			if(vo == null){
				vo = new OutFeeVerificationVO();
				vo.setVerTime(new Date());
				vo.setTimeStart(new Date());
				vo.setTimeEnd(new Date());
			}
			
			setAttr("verTypeList" , dictService.getByType(DictType.HXLX));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Common.PATH_EDIT;
	}
	
	
	/**
	 * 添加或更新数据
	 * @return
	 */
	public synchronized String update(){
		try {
			if(vo != null && !Common.checkLong(vo.getId())){
				vo.setVerCode(outFeeService.createCode());//自动生成核销编码
			}
			if(vo.getVerType() != null && Common.checkLong(vo.getVerType().getId())){
				vo.setVerType(dictService.getById(vo.getVerType().getId()));
			}
			
			vo.setTotalFee(PublicType.setDoubleScale(vo.getTotalFee()));
			OutFeeVerification po = outFeeService.saveOrUpdate(vo);
			Double totalFee = 0d;
			
			//根据核销ID，删除核销产品明细
			vpdService.deleteProductDetail(po.getId());
			
			if(vo.getOutFeeProvision().getProvisionProject().getProjectType() == ProjectType.CXYGZL){
				vo.setStoreScale(storeNum + "," + feeScale);//门店，比例
			}else if(vo.getOutFeeProvision().getProvisionProject().getProjectType() == ProjectType.CPMXL){
				if(Common.checkList(vpdList)){
					for(VerificationProductDetailVO vpdVO : vpdList){
						totalFee +=vpdVO.getCost();
						vpdVO.setOutFeeVerification(po);
						vpdService.saveOrUpdate(vpdVO);
					}
				}
				vo.setStoreScale(null);
			}
			
			vo.setTotalFee(totalFee);
			vo.setApproveState(ApproveState.DSP);
			vo.setId(po.getId());
			outFeeService.saveOrUpdate(vo);
			this.addActionMessage("操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.addActionError("操作失败!");
		}
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
					if(outFeeService.delete(Long.parseLong(str)) > 0){
						count++;
					}
				} catch (Exception e) {
					continue;
				}
			}
			
			success = count > 0;
			if(success){
				String mess = "共删除" + count + "条记录";
				if(ids.split(",").length != count){
					mess+="," + (ids.split(",").length - count) + "条记录删除失败，可能存在关联不可删除！";
				}
				this.addActionMessage(mess);
			}else{
				this.addActionError("数据删除失败，可能存在关联不可删除！");
			}
		return list();
	}
	
	/**
	 * 根据ID获取合同外费用预提
	 */
	public void getProvisionById(){
		try {
			JSONArray json = new JSONArray();
			if(vo.getOutFeeProvision() != null && Common.checkLong(vo.getOutFeeProvision().getId())){
				OutFeeProvisionVO provisionVO= outFeeProvisionService.getVOById(vo.getOutFeeProvision().getId());
				if(provisionVO.getProvisionProject().getProjectType() == ProjectType.CXYGZL){
					//促销员工资类：门店，比例；总费用
					JSONObject obj = new JSONObject();
					/*obj.put("storeNum", provisionVO.getStoreScale().split(",")[0]);
					obj.put("feeScale", provisionVO.getStoreScale().split(",")[1]);*/
					obj.put("totalFee", provisionVO.getTotalFee());
					json.add(obj);
				}else if(provisionVO.getProvisionProject().getProjectType() == ProjectType.CPMXL){
					//产品明细类：产品信息
					List<ProvisionProductDetail> list= ppdService.getByProId(provisionVO.getId());
					if(Common.checkList(list)){
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
							obj.put("totalFee",provisionVO.getTotalFee());
							obj.put("storeProductId", list.get(i).getStoreProduct().getId());
							obj.put("remark", list.get(i).getRemark());
							obj.put("storeName", list.get(i).getStoreProduct().getStore().getStoreName());
							json.add(obj);
						}
					}
				}else if(provisionVO.getProvisionProject().getProjectType() == ProjectType.QTFYL){
					//其他费用类:总费用
					JSONObject obj= new JSONObject();
					obj.put("totalFee", provisionVO.getTotalFee());
					json.add(obj);
				}
			}
			JsUtils.writeText(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除单个产品明细
	 */
	public void deleteDetail(){
		try {
			String detailId = ServletActionContext.getRequest().getParameter("detailId");//核销ID
			if(vo != null && Common.checkLong(vo.getId())){
				vo = outFeeService.getVOById(vo.getId());
				VerificationProductDetailVO vpdVO = vpdService.getVOById(Long.parseLong(detailId));
				Double totalFee = 0d;
				
				if(vpdVO != null &&  vpdVO.getCost() != null){
					//设置总费用
					totalFee = vo.getTotalFee() - vpdVO.getCost();
					if(totalFee < 0)
						totalFee = 0d;
					vo.setTotalFee(PublicType.setDoubleScale(totalFee));
					outFeeService.saveOrUpdate(vo);
				}
			}
			int count = vpdService.deleteDetail(Long.parseLong(detailId));
			JsUtils.writeText(String.valueOf(count));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 审批
	 * @return
	 */
	public String audit(){
		try {
			if(vo != null && Common.checkLong(vo.getId())){
				String beginTime = vo.getBeginTime();
				String endTime = vo.getEndTime();
				
				vo = outFeeService.getVOById(vo.getId());
				vo.setBeginTime(beginTime);
				vo.setEndTime(endTime);
				
				if(vo.getOutFeeProvision().getProvisionProject().getProjectType() == ProjectType.CPMXL){
					setAttr("detailList", vpdService.getByVerId(vo.getId()));
				}else if(vo.getOutFeeProvision().getProvisionProject().getProjectType() == ProjectType.CXYGZL){
					storeNum = vo.getOutFeeProvision().getStoreScale().split(",")[0];
					feeScale = vo.getOutFeeProvision().getStoreScale().split(",")[1];
				}
				if(act == null)
					setAct(ActType.EDIT);
			}else{
				this.addActionError("无效的操作！");
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
				OutFeeVerificationVO oVO = outFeeService.getVOById(Long.parseLong(id));
				if(oVO.getApproveState() == ApproveState.DSP)//审批
					oVO.setApproveState(ApproveState.SPJS);
				else if(oVO.getApproveState() == ApproveState.SPJS)//反审
					oVO.setApproveState(ApproveState.DSP);
				outFeeService.saveOrUpdate(oVO);
			} catch (Exception e) {
				e.printStackTrace();
				this.addActionError("操作失败！");
			}
			}
			this.addActionMessage("操作成功！");
			return list();
	}
	
	/**
	 * 生成U8收款单
	 * @return
	 */
	public String createOrderU8(){
		try {
			OutFeeVerification po = outFeeService.getById(vo.getId());
			String result = inFeeService.expOrderToU8(po.getUpdateTime(), po.getCustom().getCustomCde(), vo.getSummary(), 
					po.getTotalFee(), vo.getMaker(), vo.getBackItem(), vo.getCustomItem(), vo.getSsCode());
			
			if(StringUtils.equals(result, "1")){
				vo.setIsCreateU8(true);
				outFeeService.saveOrUpdate(vo);
				this.addActionMessage("生成U8收款单成功");
			}else if(StringUtils.equals(result, "2"))
					this.addActionMessage("生成U8收款单失败，因为记录所在月份已经结账");
			else if(StringUtils.equals(result, "3"))
				this.addActionMessage("生成U8收款单失败");
			else if(StringUtils.equals(result, "4"))
				this.addActionMessage("生成U8收款单失败，结算方式编码不存在");
			else if(StringUtils.equals(result, "5"))
				this.addActionMessage("生成U8收款单失败，收款银行科目不存在");
			else if(StringUtils.equals(result, "6"))
				this.addActionMessage("生成U8收款单失败，对方科目不存在");
			
		} catch (Exception e) {
			this.addActionMessage("操作失败");
			e.printStackTrace();
		}
		return list();
	}
	
	public String editOrderU8(){
		try {
			if(vo != null && Common.checkLong(vo.getId())){
				String beginTime = vo.getBeginTime();
				String endTime = vo.getEndTime();
				
				vo = outFeeService.getVOById(vo.getId());
				vo.setBeginTime(beginTime);
				vo.setEndTime(endTime);
				
				if(!vo.getIsCreateU8()){
					for(Dict d : dictService.getByType(DictType.SKDKM)){
						if(StringUtils.endsWith(d.getDictName(), "制单人"))
							vo.setMaker(d.getRemark());
						else if(StringUtils.endsWith(d.getDictName(), "摘要"))
							vo.setSummary(d.getRemark());
						else if(StringUtils.endsWith(d.getDictName(), "借方科目"))
							vo.setBackItem(d.getRemark());
						else if(StringUtils.endsWith(d.getDictName(), "贷方科目"))
							vo.setCustomItem(d.getDictName());
						else if(StringUtils.endsWith(d.getDictName(), "结算方式编码"))
							vo.setSsCode(d.getDictName());
					}
				}
				return "U8";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list();
	}
	
	/**
	 * 导出合费用核销
	 * @return
	 */
	public String exportOutFeeVer()throws Exception{
		setAttr("outFeeVerHeader", Common.getExportHeader("OUTFEEVERIFICATION"));
		if(vo.getCustom()!=null && StringUtils.isNotBlank(vo.getCustom().getCustomName())){
			String name = new String(vo.getCustom().getCustomName().getBytes("ISO8859_1"),"UTF8");
			vo.getCustom().setCustomName(name);
		} 
		if(vo.getVerType() != null){
			String name = new String(vo.getVerType().getDictName().getBytes("ISO8859_1"),"UTF8");
			vo.getVerType().setDictName(name);
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
				vo = new OutFeeVerificationVO();
				vo.setBeginTime(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
				vo.setEndTime(DateFormat.date2Str(new Date(), 2));
			}
			vo.setObjectsPerPage(Integer.MAX_VALUE);
			checkRole();
			
			List<OutFeeVerification> list=outFeeService.getPageList(vo).getList();
			
			List<Integer> intArr = new ArrayList<Integer>();
			String[] heads = StringUtils.split(header, ",");
			for(int i =0; i< heads.length; i++){
				intArr.add(25);
			}
			
			List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
			Map<String ,String> map = null;
			
			for(OutFeeVerification ofv:list){
				map=new HashMap<String, String>();
				outFeeService.exportOutFeeVer(heads,ofv,map);
				listmap.add(map);
			}
			HttpServletResponse response = ServletActionContext.getResponse();
			String title = "合同外费用核销表";
			Integer[] columnSize = intArr.toArray(new Integer[]{});
			ExcelUtil.export(response, listmap, "合同外费用核销表.xls", title, heads, columnSize);
			
		}catch(Exception e){
			e.printStackTrace();
		}
			
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
	
	public OutFeeVerificationVO getVo() {
		return vo;
	}

	public void setVo(OutFeeVerificationVO vo) {
		this.vo = vo;
	}

	public List<VerificationProductDetailVO> getVpdList() {
		return vpdList;
	}

	public void setVpdList(List<VerificationProductDetailVO> vpdList) {
		this.vpdList = vpdList;
	}

	public List<ProvisionProductDetailVO> getPpdList() {
		return ppdList;
	}

	public void setPpdList(List<ProvisionProductDetailVO> ppdList) {
		this.ppdList = ppdList;
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

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}
	
	
	
}
