package com.kington.fshg.webapp.actions.charge;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.DateFormat;
import com.kington.fshg.common.ExcelUtil;
import com.kington.fshg.common.PublicType.ActType;
import com.kington.fshg.common.PublicType.ApproveState;
import com.kington.fshg.common.PublicType.DictType;
import com.kington.fshg.model.charge.InFeeVerification;
import com.kington.fshg.model.charge.InFeeVerificationVO;
import com.kington.fshg.model.info.Area;
import com.kington.fshg.model.info.Custom;
import com.kington.fshg.model.info.Store;
import com.kington.fshg.model.system.Dict;
import com.kington.fshg.model.system.User;
import com.kington.fshg.service.charge.InFeeVerificationService;
import com.kington.fshg.service.info.AreaService;
import com.kington.fshg.service.info.CustomsTypeService;
import com.kington.fshg.service.system.DictService;
import com.kington.fshg.service.system.UserService;
import com.kington.fshg.webapp.actions.BaseAction;
import com.kington.fshg.webapp.security.UserContext;

/**
 *	合同内费用核销 
 *
 */
public class InFeeVerificationAction extends BaseAction {
	private static final long serialVersionUID = -7829896956361019665L;

	
	@Resource
	private InFeeVerificationService inFeeService;
	@Resource
	private AreaService areaService;
	@Resource
	private CustomsTypeService customsTypeService;
	@Resource
	private UserService userService;
	@Resource
	private DictService dictService;
	
	private InFeeVerificationVO vo;
	private String header;
	
	public String list(){
		try {
			if(vo == null){
				vo = new InFeeVerificationVO();
				vo.setBeginTime(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
				vo.setEndTime(DateFormat.date2Str(new Date(), 2));
			}
			vo.setPageNumber(page);
			
			checkRole();
			
			pageList = inFeeService.getPageList(vo);
			
			setAttr("verTypeList", dictService.getByType(DictType.HXLX));
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
	
	/**
	 * 编辑
	 * @return
	 */
	public String edit(){
		try {
			if(vo != null && Common.checkLong(vo.getId())){
				String beginTime = vo.getBeginTime();
				String endTime = vo.getEndTime();
				
				vo = inFeeService.getVOById(vo.getId());
				vo.setBeginTime(beginTime);
				vo.setEndTime(endTime);
				if(act == null)
					setAct(ActType.EDIT);
			}else{
				setAct(ActType.ADD);
			}
			
			if(vo == null)
				vo = new InFeeVerificationVO();
			
			setAttr("verTypeList", dictService.getByType(DictType.HXLX));
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
				vo.setVerCode(inFeeService.createCode());
			}
			
			if(vo.getVerType() != null && Common.checkLong(vo.getVerType().getId())){
				vo.setVerType(dictService.getById(vo.getVerType().getId()));
			}
			
			vo.setApproveState(ApproveState.DSP);
			InFeeVerification po = inFeeService.saveOrUpdate(vo);
			inFeeService.countTotalFee(po);
			if(po != null)
				this.addActionMessage("操作成功！");
			else
				this.addActionError("操作失败！");
		} catch (Exception e) {
			e.printStackTrace();
			this.addActionError("操作失败！");
		}
		//vo = new InFeeVerificationVO();
		return list();
	}
	
	/**
	 * 审批页面
	 * @return
	 */
	public String audit(){
		try {
			if(vo != null && Common.checkLong(vo.getId())){
				String beginTime = vo.getBeginTime();
				String endTime = vo.getEndTime();
				
				vo = inFeeService.getVOById(vo.getId());
				vo.setBeginTime(beginTime);
				vo.setEndTime(endTime);
			}else{
				this.addActionError("无效的操作！");
				return list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "audit";
	}
	
	/**
	 * 执行审批操纵
	 * @return
	 */
	public String approve(){
		for(String id : ids.split(",")){
			try {
				InFeeVerificationVO iVO = inFeeService.getVOById(Long.parseLong(id));
				
				if(iVO.getApproveState() == ApproveState.DSP)//审批
					iVO.setApproveState(ApproveState.SPJS);
				else if(iVO.getApproveState() == ApproveState.SPJS)//反审
					iVO.setApproveState(ApproveState.DSP);
				inFeeService.saveOrUpdate(iVO);
			} catch (Exception e) {
				e.printStackTrace();
				this.addActionError("操作失败！");
			}
		}
		this.addActionMessage("操作成功！");
		//vo = new InFeeVerificationVO();
		return list();
	}
	
	/**
	 * 批量删除
	 * @return
	 */
	public String delete(){
		try {
			boolean success = false;
			int count = inFeeService.clear(ids);
			
			success = count > 0;
			if(success){
				String mess = "共删除" + count + "条记录";
				if(ids.split(",").length != count){
					mess+="," + (ids.split(",").length - count) + "条记录删除失败，可能存在关联不可删除！";
				}
				this.addActionMessage(mess);
			}else{
				this.addActionError("数据删除失败，可能存在关联信息不可删除！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.addActionError("数据删除失败，可能存在关联信息不可删除！");
		}
		//vo = new InFeeVerificationVO();
		return list();
	}
	
	/**
	 * 生成U8收款单
	 * @return
	 */
	public String createOrderU8(){
		try {
			InFeeVerification po = inFeeService.getById(vo.getId());
			String result = inFeeService.expOrderToU8(po.getUpdateTime(), po.getCustom().getCustomCde(), vo.getSummary(), 
					po.getTotalFee(), vo.getMaker(), vo.getBackItem(), vo.getCustomItem(), vo.getSsCode());
			
			if(StringUtils.equals(result, "1")){
				vo.setIsCreateU8(true);
				inFeeService.saveOrUpdate(vo);
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
				
				vo = inFeeService.getVOById(vo.getId());
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
			}
			
			return "U8";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list();
	}
	
	/**
	 * 导出合同内费用核销表
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String exportInFeeVerification() throws Exception{
		setAttr("inFeeVerificationHeader", Common.getExportHeader("INFEEVERIFICATION"));
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
				vo = new InFeeVerificationVO();
				vo.setBeginTime(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
				vo.setEndTime(DateFormat.date2Str(new Date(), 2));
			}
				vo.setObjectsPerPage(Integer.MAX_VALUE);
				
				checkRole();
				List<InFeeVerification> list=inFeeService.inFeeVerification(vo);
				List<Integer> intArr = new ArrayList<Integer>();
				String[] heads = StringUtils.split(header, ",");
				for(int i =0; i< heads.length; i++){
					intArr.add(25);
				}
				
				List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
				Map<String ,String> map = null;
				for(InFeeVerification ifv:list){
					map=new HashMap<String, String>();
					inFeeService.exportInFeeVerification(heads,ifv,map);
					listmap.add(map);
				}
				HttpServletResponse response = ServletActionContext.getResponse();
				String title = "合同内费用核销表";
				Integer[] columnSize = intArr.toArray(new Integer[]{});
				ExcelUtil.export(response, listmap, "合同内费用核销表.xls", title, heads, columnSize);
				
				
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
	public InFeeVerificationVO getVo() {
		return vo;
	}

	public void setVo(InFeeVerificationVO vo) {
		this.vo = vo;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}
	 
}
