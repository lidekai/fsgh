package com.kington.fshg.service.charge;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.DateFormat;
import com.kington.fshg.model.charge.OutFeeVerification;
import com.kington.fshg.model.charge.OutFeeVerificationVO;
import com.kington.fshg.service.BaseServiceImpl;

public class OutFeeVerificationServiceImpl extends BaseServiceImpl<OutFeeVerification, OutFeeVerificationVO>
		implements OutFeeVerificationService {
	private static final long serialVersionUID = 3242479044157049513L;

	@Override
	protected String getQueryStr(OutFeeVerificationVO vo) throws Exception {
		StringBuffer sb = new StringBuffer();
		if(vo.getOutFeeProvision() != null){
			if(StringUtils.isNotBlank(vo.getOutFeeProvision().getProvisionCode()))
				sb.append(" and o.outFeeProvision.provisionCode =:provisionCode ");
			if(vo.getOutFeeProvision().getProvisionProject() != null 
					&& vo.getOutFeeProvision().getProvisionProject().getProjectType() != null){
				sb.append(" and o.outFeeProvision.provisionProject.projectType = :projectType ");
			}
		}
		
		if(vo.getCustom() != null){
			if(StringUtils.isNotBlank(vo.getCustom().getCustomName())){
				sb.append(" and o.custom.customName like :customName ");
			}
			if(vo.getCustom().getArea() != null){
				if(Common.checkLong(vo.getCustom().getArea().getId())){
					sb.append(" and o.custom.area.id = :areaId ");
				}else if(vo.getCustom().getArea().getParentArea() != null 
						&& Common.checkLong(vo.getCustom().getArea().getParentArea().getId())){
					sb.append(" and o.custom.area.parentArea.id = :parentId ");
				}
			}
			if(vo.getCustom().getUser() != null 
					&& Common.checkLong(vo.getCustom().getUser().getId())){
				sb.append(" and o.custom.user.id =:userId ");
			}
			if(vo.getCustom().getCustomType() != null  && Common.checkLong(vo.getCustom().getCustomType().getId()))
				sb.append(" and o.custom.customType.id = :customTypeId ");
		}
		
		if(vo.getApproveState() != null)
			sb.append(" and o.approveState =:approveState ");
		if(StringUtils.isNotBlank(vo.getBeginTime()))
			sb.append(" and o.verTime >= :beginTime ");
		if(StringUtils.isNotBlank(vo.getEndTime()))
			sb.append(" and o.verTime < :endTime ");
		if(vo.getVerDirection() != null)
			sb.append(" and o.verDirection =:verDirection ");
		if(vo.getVerType() != null && StringUtils.isNotBlank(vo.getVerType().getDictName()))
			sb.append(" and o.verType.dictName =:dictName ");
		if(StringUtils.isNotBlank(vo.getVerCode()))
			sb.append(" and o.verCode = :verCode ");
		if(Common.checkList(vo.getAreaIds()))
			sb.append(" and o.custom.area.id in (:areaIds)");
		if(Common.checkList(vo.getParentAreaIds()))
			sb.append(" and o.custom.area.parentArea.id in (:parentAreaIds)");
		return sb.toString();
	}

	@Override
	protected void setQueryParm(Query query, OutFeeVerificationVO vo)
			throws Exception {
		if(vo.getOutFeeProvision() != null){
			if(StringUtils.isNotBlank(vo.getOutFeeProvision().getProvisionCode()))
				query.setParameter("provisionCode", vo.getOutFeeProvision().getProvisionCode());
			if(vo.getOutFeeProvision().getProvisionProject() != null 
					&& vo.getOutFeeProvision().getProvisionProject().getProjectType() != null){
				query.setParameter("projectType", vo.getOutFeeProvision().getProvisionProject().getProjectType());
			}
		}
		
		if(vo.getCustom() != null){
			if(StringUtils.isNotBlank(vo.getCustom().getCustomName())){
				query.setParameter("customName", Common.SYMBOL_PERCENT + vo.getCustom().getCustomName() 
						+ Common.SYMBOL_PERCENT);
			}
			if(vo.getCustom().getArea() != null){
				if(Common.checkLong(vo.getCustom().getArea().getId())){
					query.setParameter("areaId", vo.getCustom().getArea().getId());
				}else if(vo.getCustom().getArea().getParentArea() != null 
						&& Common.checkLong(vo.getCustom().getArea().getParentArea().getId())){
					query.setParameter("parentId", vo.getCustom().getArea().getParentArea().getId());
				}
			}
			if(vo.getCustom().getUser() != null 
					&& Common.checkLong(vo.getCustom().getUser().getId())){
				query.setParameter("userId", vo.getCustom().getUser().getId());
			}
			if(vo.getCustom().getCustomType() != null  && Common.checkLong(vo.getCustom().getCustomType().getId()))
				query.setParameter("customTypeId", vo.getCustom().getCustomType().getId());
		}
		
		if(vo.getApproveState() != null)
			query.setParameter("approveState", vo.getApproveState());
		if(StringUtils.isNotBlank(vo.getBeginTime()))
			query.setParameter("beginTime", DateFormat.str2Date(vo.getBeginTime(), 2));
		if(StringUtils.isNotBlank(vo.getEndTime()))
			query.setParameter("endTime", DateFormat.getAfterDayFirst(DateFormat.str2Date(vo.getEndTime(), 2)));
		if(vo.getVerDirection() != null)
			query.setParameter("verDirection", vo.getVerDirection());
		if(vo.getVerType() != null && StringUtils.isNotBlank(vo.getVerType().getDictName()))
			query.setParameter("dictName", vo.getVerType().getDictName());
		if(StringUtils.isNotBlank(vo.getVerCode()))
			query.setParameter("verCode", vo.getVerCode());
		if(Common.checkList(vo.getAreaIds()))
			query.setParameter("areaIds", vo.getAreaIds());
		if(Common.checkList(vo.getParentAreaIds()))
			query.setParameter("parentAreaIds", vo.getParentAreaIds());
	}

	@Override
	protected void switchVO2PO(OutFeeVerificationVO vo, OutFeeVerification po)
			throws Exception {
		if(po == null)
			po = new OutFeeVerification();
		if(vo.getVerDirection() != null)
			po.setVerDirection(vo.getVerDirection());
		if(vo.getVerType() != null)
			po.setVerType(vo.getVerType());
		if(vo.getOutFeeProvision() != null)
			po.setOutFeeProvision(vo.getOutFeeProvision());
		if(vo.getCustom() != null)
			po.setCustom(vo.getCustom());
		if(vo.getApproveState() != null)
			po.setApproveState(vo.getApproveState());
		if(vo.getTotalFee() != null)
			po.setTotalFee(vo.getTotalFee());
		if(StringUtils.isNotBlank(vo.getSalesman()))
			po.setSalesman(vo.getSalesman());
		po.setStoreScale(vo.getStoreScale());
		
		//生成U8收款单相关字段
		if(vo.getIsCreateU8() != null)
			po.setIsCreateU8(vo.getIsCreateU8());
		if(StringUtils.isNotBlank(vo.getMaker()))
			po.setMaker(vo.getMaker());
		if(StringUtils.isNotBlank(vo.getSummary()))
			po.setSummary(vo.getSummary());
		if(StringUtils.isNotBlank(vo.getBackItem()))
			po.setBackItem(vo.getBackItem());
		if(StringUtils.isNotBlank(vo.getCustomItem()))
			po.setCustomItem(vo.getCustomItem());
		if(StringUtils.isNotBlank(vo.getSsCode()))
			po.setSsCode(vo.getSsCode());
		if(StringUtils.isNotBlank(vo.getRemark()))
			po.setRemark(vo.getRemark());
		if(vo.getVerTime() != null)
			po.setVerTime(vo.getVerTime());
		if(vo.getTimeStart() != null)
			po.setTimeStart(vo.getTimeStart());
		if(vo.getTimeEnd() != null)
			po.setTimeEnd(vo.getTimeEnd());
	}

	@Override
	public int delete(Long id) throws Exception {
		String hql = "delete from charge_out_fee_verification where id=" + id;
		Query query = em.createNativeQuery(hql);
		return query.executeUpdate();
	}

	@Override
	@SuppressWarnings("unchecked")
	public synchronized String createCode() throws Exception {
		String code = "D" + DateFormat.date2Str(new Date(), 4);
		String sql = "select verCode from charge_out_fee_verification where verCode like '"+ code +"%' order by verCode desc limit 1 ";
		Query query = em.createNativeQuery(sql);
		
		int number = 0;
		List<Object> list = query.getResultList();
		if(Common.checkList(list) && list.get(0) != null){
			String a = list.get(0).toString();
			number = Integer.parseInt(a.substring(7));
		}
		
		DecimalFormat df = new DecimalFormat("00000");  
		code += df.format(number + 1);
		return code;
	}

	@Override
	public Map<Long, List<OutFeeVerification>> getOutFeeVerMap(
			OutFeeVerificationVO vo) throws Exception {
		vo.setObjectsPerPage(Integer.MAX_VALUE);
		
		Map<Long, List<OutFeeVerification>>  map = new HashMap<Long, List<OutFeeVerification>>();
		Long customId = 0L;
		List<OutFeeVerification> list = new ArrayList<OutFeeVerification>();
		
		for(OutFeeVerification p : getPageList(vo).getList()){
			if(!customId.equals(p.getCustom().getId()) && !customId.equals(0L)){
				map.put(customId, list);
				list = new ArrayList<OutFeeVerification>();
			}
			
			list.add(p);
			customId = p.getCustom().getId();
		}
		map.put(customId, list);
		return map;
	}

	@Override
	public void exportOutFeeVer(String[] heads, OutFeeVerification ofv, Map<String, String> map) {
		String[] header=Common.getExportHeader("OUTFEEVERIFICATION");
		for(String key : heads){
			int i=0;
			if(key.equals(header[i++])){      
				map.put(key, ofv.getVerCode());     //核销编码
			}else if(key.equals(header[i++])){   
				if(ofv.getOutFeeProvision()!=null){
					map.put(key, ofv.getOutFeeProvision().getProvisionCode());	//所属预提
				}
			}else if(key.equals(header[i++])){   
				map.put(key, ofv.getSalesman());				//申请业务员
			}else if(key.equals(header[i++])){					//项目类型
				if(ofv.getOutFeeProvision()!=null&&ofv.getOutFeeProvision().getProvisionProject()!=null){
					map.put(key,ofv.getOutFeeProvision().getProvisionProject().getProjectType().getText());
				}
			}else if(key.equals(header[i++])){					//项目名称
				if(ofv.getOutFeeProvision()!=null && ofv.getOutFeeProvision().getProvisionProject()!=null){
					map.put(key,ofv.getOutFeeProvision().getProvisionProject().getFeeName());
				}
			}else if(key.equals(header[i++])){
				if(ofv.getCustom() != null && StringUtils.isNotBlank(ofv.getCustom().getCustomName())){
					map.put(key, ofv.getCustom().getCustomName());	//所属客户
				}
			}else if(key.equals(header[i++])){
				if(ofv.getVerType() != null){
					map.put(key, ofv.getVerType().getDictName());	//核销类型
				}
			}else if(key.equals(header[i++])){
				if(ofv.getVerDirection()!=null){
					map.put(key, ofv.getVerDirection().getText());	//核销方向
				}
			}else if(key.equals(header[i++])){
				if(ofv.getTotalFee()!=null){
					map.put(key, ofv.getTotalFee().toString());			//总费用(元)
				}
			}else if(key.equals(header[i++])){
				if(ofv.getVerTime()!=null){
					map.put(key, DateFormat.date2Str(ofv.getVerTime(), 2));	//核销时间
				}
			}else if(key.equals(header[i++])){
				if(ofv.getTimeStart()!=null){
					map.put(key, DateFormat.date2Str(ofv.getTimeStart(), 2));	//所属开始区间
				}
			}else if(key.equals(header[i++])){
				if(ofv.getTimeEnd()!=null){
					map.put(key, DateFormat.date2Str(ofv.getTimeEnd(), 2));	//所属结束区间
				}
			}else if(key.equals(header[i++])){
				if(ofv.getApproveState()!=null){
					map.put(key, ofv.getApproveState().getText());		//审核状态
				}
			}else if(key.equals(header[i++])){
				if(ofv.getIsCreateU8()!=null){
					if(ofv.getIsCreateU8()==true){
						map.put(key, "是");							//是否已生成收款单
					}else{
						map.put(key, "否");
					}
				}
			}else if(key.equals(header[i++])){
				map.put(key, ofv.getRemark());					//备注
			}
		}
		
		
	}

	
	
	
	
}
