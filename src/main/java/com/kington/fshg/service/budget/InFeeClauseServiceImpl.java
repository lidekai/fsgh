package com.kington.fshg.service.budget;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.DateFormat;
import com.kington.fshg.common.excel.vo.InFeeClauseExcelVO;
import com.kington.fshg.model.budget.InFeeClause;
import com.kington.fshg.model.budget.InFeeClauseVO;
import com.kington.fshg.model.info.Custom;
import com.kington.fshg.service.BaseServiceImpl;
import com.kington.fshg.service.info.CustomService;

public class InFeeClauseServiceImpl extends
		BaseServiceImpl<InFeeClause, InFeeClauseVO> implements
		InFeeClauseService {
	private static final long serialVersionUID = -335165662700788911L;
	
	@Resource
	private CustomService customService;

	@Override
	protected String getQueryStr(InFeeClauseVO vo) throws Exception {
		StringBuffer sb = new StringBuffer();
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
			if(vo.getCustom().getUser() != null && Common.checkLong(vo.getCustom().getUser().getId())){
				sb.append(" and o.custom.user.id =:userId ");
			}
			if(StringUtils.isNotBlank(vo.getCustom().getCustomCde()))
				sb.append(" and o.custom.customCde = :customCde ");
		}
		
		if(Common.checkList(vo.getAreaIds()))
			sb.append(" and o.custom.area.id in (:areaIds)");
		if(Common.checkList(vo.getParentAreaIds()))
			sb.append(" and o.custom.area.parentArea.id in (:parentAreaIds)");
		
		if(vo.getYear() != null)
			sb.append(" and o.year = :year ");
		return sb.toString();
	}

	@Override
	protected void setQueryParm(Query query, InFeeClauseVO vo) throws Exception {
		if(vo.getCustom() != null){
			if(StringUtils.isNotBlank(vo.getCustom().getCustomName())){
				query.setParameter("customName", Common.SYMBOL_PERCENT+ vo.getCustom().getCustomName() 
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
			if(vo.getCustom().getUser() != null && Common.checkLong(vo.getCustom().getUser().getId())){
				query.setParameter("userId", vo.getCustom().getUser().getId());
			}
			if(StringUtils.isNotBlank(vo.getCustom().getCustomCde()))
				query.setParameter("customCde", vo.getCustom().getCustomCde());
		}
		
		if(Common.checkList(vo.getAreaIds()))
			query.setParameter("areaIds", vo.getAreaIds());
		if(Common.checkList(vo.getParentAreaIds()))
			query.setParameter("parentAreaIds", vo.getParentAreaIds());
		if(vo.getYear() != null)
			query.setParameter("year", vo.getYear());
		
	}

	@Override
	protected void switchVO2PO(InFeeClauseVO vo, InFeeClause po)
			throws Exception {
		if(po == null)
			po = new InFeeClause();
		
		if(vo.getCustom() != null)
			po.setCustom(vo.getCustom());
		if(vo.getEnterFee() != null)
			po.setEnterFee(vo.getEnterFee());
		if(vo.getFixedFee() != null)
			po.setFixedFee(vo.getFixedFee());
		if(vo.getYearReturnFee() != null)
			po.setYearReturnFee(vo.getYearReturnFee());
		if(vo.getMonthReturnFee() != null)
			po.setMonthReturnFee(vo.getMonthReturnFee());
		if(vo.getNetInfoFee() != null)
			po.setNetInfoFee(vo.getNetInfoFee());
		if(vo.getDeliveryFee() != null)
			po.setDeliveryFee(vo.getDeliveryFee());
		if(vo.getPosterFee() != null)
			po.setPosterFee(vo.getPosterFee());
		if(vo.getPromotionFee() != null)
			po.setPromotionFee(vo.getPromotionFee());
		if(vo.getSponsorFee() != null)
			po.setSponsorFee(vo.getSponsorFee());
		if(vo.getLossFee() != null)
			po.setLossFee(vo.getLossFee());
		if(vo.getFixedDiscount() != null)
			po.setFixedDiscount(vo.getFixedDiscount());
		if(vo.getPilesoilFee() != null)
			po.setPilesoilFee(vo.getPilesoilFee());
		if(vo.getMarketFee() != null)
			po.setMarketFee(vo.getMarketFee());
		if(vo.getCaseReturnFee() != null)
			po.setCaseReturnFee(vo.getCaseReturnFee());
		if(vo.getOtherFee() != null)
			po.setOtherFee(vo.getOtherFee());
		if(vo.getFixedSponsorFee() != null)
			po.setFixedSponsorFee(vo.getFixedSponsorFee());
		if(vo.getStoreCount() != null)
			po.setStoreCount(vo.getStoreCount());
		if(vo.getYear() != null)
			po.setYear(vo.getYear());
		if(vo.getStartDate()!=null){
			po.setStartDate(vo.getStartDate());
		}
		if(vo.getEndDate()!=null){
			po.setEndDate(vo.getEndDate());
		}
		if(StringUtils.isNotBlank(vo.getRemark())){
			po.setRemark(vo.getRemark());
		}
		
		if(vo.getEnterStartDate() != null)
			po.setEnterStartDate(vo.getEnterStartDate());
		if(vo.getEnterEndDate() != null)
			po.setEnterEndDate(vo.getEnterEndDate());
		if(vo.getFixedStartDate() != null)
			po.setFixedStartDate(vo.getFixedStartDate());
		if(vo.getFixedEndDate() != null)
			po.setFixedEndDate(vo.getFixedEndDate());
		if(vo.getNetStartDate() != null)
			po.setNetStartDate(vo.getNetStartDate());
		if(vo.getNetEndDate() != null)
			po.setNetEndDate(vo.getNetEndDate());
		if(vo.getPosterStartDate() != null)
			po.setPosterStartDate(vo.getPosterStartDate());
		if(vo.getPosterEndDate() != null)
			po.setPosterEndDate(vo.getPosterEndDate());			
		if(vo.getPromotionStartDate() != null)
			po.setPromotionStartDate(vo.getPromotionStartDate());
		if(vo.getPromotionEndDate() != null)
			po.setPromotionEndDate(vo.getPromotionEndDate());
		if(vo.getPileStartDate() != null)
			po.setPileStartDate(vo.getPileStartDate());
		if(vo.getPileEndDate() != null)
			po.setPileEndDate(vo.getPileEndDate());		
		if(vo.getOtherStartDate() != null)
			po.setOtherStartDate(vo.getOtherStartDate());
		if(vo.getOtherEndDate() != null)
			po.setOtherEndDate(vo.getOtherEndDate());
	}

	@SuppressWarnings("unchecked")
	@Override
	public InFeeClause getByCusIdAndYear(Long customId, Integer year) {
		String hql = "from InFeeClause where custom.id = :customId and year = :year ";
		Query query = em.createQuery(hql);
		query.setParameter("customId", customId);
		query.setParameter("year", year);
		List<InFeeClause> list = query.getResultList();		
		return Common.checkList(list) ? list.get(0) : null;
	}

	

	@Override
	public void exportInFeeClause(String[] heads, InFeeClause ifc, Map<String, String> map) {
		String[] header=Common.getExportHeader("INFEECLAUSE");
		//"年度","所属客户","固定费用","固定费用分摊开始时间","固定费用分摊结束时间","网络信息费","网络信息费分摊开始时间","网络信息费分摊结束时间","配送服务费(百分比)","年返金(百分比)","月返金(百分比)",
		//"进场费","进场费分摊开始时间","进场费分摊结束时间","海报费","海报费分摊开始时间","海报费分摊结束时间","促销陈列费","促销陈列费分摊开始时间","促销陈列费分摊结束时间","赞助费(比例)","固定折扣(比例)","堆头费","堆头费分摊开始时间","堆头费用分摊结束时间","市场费(比例)","现款现货返利(比例)",
		//"其他费用","其他费用分摊开始时间","其他费用分摊结束时间","损耗费","开始日期","结束日期","备注"
		for(String key :heads){
			int i=0;
			if(key.equals(header[i++])){
				if(ifc.getYear()!=null){
					map.put(key, ifc.getYear().toString());
				}else{
					map.put(key, String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
				}
			}else if(key.equals(header[i++])){
				if(ifc.getCustom()!=null){
					map.put(key, ifc.getCustom().getCustomName());
				}
			}else if(key.equals(header[i++])){
				if(ifc.getFixedFee()!=null){
					map.put(key, ifc.getFixedFee().toString());
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				if(ifc.getFixedStartDate() != null){
					map.put(key, DateFormat.date2Str(ifc.getFixedStartDate(), 2));
				}
			}else if(key.equals(header[i++])){
				if(ifc.getFixedEndDate() != null){
					map.put(key, DateFormat.date2Str(ifc.getFixedEndDate(), 2));
				}
			}else if(key.equals(header[i++])){
				if(ifc.getNetInfoFee()!=null){
					map.put(key, ifc.getNetInfoFee().toString());
				}else{
					map.put(key,"0.0");
				}
			}else if(key.equals(header[i++])){
				if(ifc.getNetStartDate() != null){
					map.put(key, DateFormat.date2Str(ifc.getNetStartDate(), 2));
				}
			}else if(key.equals(header[i++])){
				if(ifc.getNetEndDate() != null){
					map.put(key, DateFormat.date2Str(ifc.getNetEndDate(), 2));
				}
			}else if(key.equals(header[i++])){
				if(ifc.getDeliveryFee()!=null){
					map.put(key, ifc.getDeliveryFee().toString());
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				if(ifc.getYearReturnFee()!=null){
					map.put(key, ifc.getYearReturnFee().toString());
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				if(ifc.getMonthReturnFee()!=null){
					map.put(key, ifc.getMonthReturnFee().toString());
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				if(ifc.getEnterFee()!=null){						//进场费
					map.put(key, ifc.getEnterFee().toString());
				}
			}else if(key.equals(header[i++])){
				if(ifc.getEnterStartDate() != null){
					map.put(key, DateFormat.date2Str(ifc.getEnterStartDate(), 2));
				}
			}else if(key.equals(header[i++])){
				if(ifc.getEnterEndDate() != null){
					map.put(key, DateFormat.date2Str(ifc.getEnterEndDate(), 2));
				}
			}else if(key.equals(header[i++])){
				if(ifc.getPosterFee()!=null){						//海报费
					map.put(key, ifc.getPosterFee().toString());
				}
			}else if(key.equals(header[i++])){
				if(ifc.getPosterStartDate() != null){
					map.put(key, DateFormat.date2Str(ifc.getPosterStartDate(), 2));
				}
			}else if(key.equals(header[i++])){
				if(ifc.getPosterEndDate() != null){
					map.put(key, DateFormat.date2Str(ifc.getPosterEndDate(), 2));
				}
			}else if(key.equals(header[i++])){
				if(ifc.getPromotionFee()!=null){						//促销陈列费
					map.put(key, ifc.getPromotionFee().toString());
				}
			}else if(key.equals(header[i++])){
				if(ifc.getPromotionStartDate() != null){
					map.put(key, DateFormat.date2Str(ifc.getPromotionStartDate(), 2));
				}
			}else if(key.equals(header[i++])){
				if(ifc.getPromotionEndDate() != null){
					map.put(key, DateFormat.date2Str(ifc.getPromotionEndDate(), 2));
				}
			}else if(key.equals(header[i++])){
				if(ifc.getSponsorFee()!=null){						//赞助费(比例)
					map.put(key, ifc.getSponsorFee().toString());
				}
			}else if(key.equals(header[i++])){
				if(ifc.getFixedDiscount()!=null){						//固定折扣
					map.put(key, ifc.getFixedDiscount().toString());
				}
			}else if(key.equals(header[i++])){
				if(ifc.getPilesoilFee()!=null){						//堆头费
					map.put(key, ifc.getPilesoilFee().toString());
				}
			}else if(key.equals(header[i++])){
				if(ifc.getPileStartDate() != null){
					map.put(key, DateFormat.date2Str(ifc.getPileStartDate(), 2));
				}
			}else if(key.equals(header[i++])){
				if(ifc.getPileEndDate() != null){
					map.put(key, DateFormat.date2Str(ifc.getPileEndDate(), 2));
				}
			}else if(key.equals(header[i++])){
				if(ifc.getMarketFee()!=null){						//市场费
					map.put(key, ifc.getMarketFee().toString());
				}
			}else if(key.equals(header[i++])){
				if(ifc.getCaseReturnFee()!=null){						//现款现货返利
					map.put(key, ifc.getCaseReturnFee().toString());
				}
			}else if(key.equals(header[i++])){
				if(ifc.getOtherFee()!=null){						//其他费用
					map.put(key, ifc.getOtherFee().toString());
				}
			}else if(key.equals(header[i++])){
				if(ifc.getOtherStartDate() != null){
					map.put(key, DateFormat.date2Str(ifc.getOtherStartDate(), 2));
				}
			}else if(key.equals(header[i++])){
				if(ifc.getOtherEndDate() != null){
					map.put(key, DateFormat.date2Str(ifc.getOtherEndDate(), 2));
				}
			}else if(key.equals(header[i++])){
				if(ifc.getLossFee()!=null){						//损耗费
					map.put(key, ifc.getLossFee().toString());
				}
			}else if(key.equals(header[i++])){
				if(ifc.getStartDate()!=null){						//开始日期
					map.put(key, DateFormat.date2Str(ifc.getStartDate(), 2));
				}
			}else if(key.equals(header[i++])){
				if(ifc.getEndDate()!=null){						//结束日期
					map.put(key, DateFormat.date2Str(ifc.getEndDate(), 2));
				}
			}else if(key.equals(header[i++])){							
					map.put(key, ifc.getRemark());		//备注
				
			}
		}
		
	}

	@Override
	public Object importInFeeClause(List<InFeeClauseExcelVO> list) throws Exception{
		StringBuilder sb = new StringBuilder();
		int num =0 , succ = 0, fail = 0;
		String s = StringUtils.EMPTY;
		String d = StringUtils.EMPTY;
		for(InFeeClauseExcelVO vo:list){
			num++;
			if(vo.getCid() == null)
				continue;
			d="序号"+ num + "数据失败：";
			s = checkVO(vo);
			//根据客户编码判断是否已存在，如存在更新，否则新增；
			InFeeClause po=null;
			
			if(StringUtils.isBlank(vo.getCustomCde()))
				s= "客户编码不能为空";			
			
			if(StringUtils.isNotBlank(vo.getCustomCde())){
				Custom custom=customService.getByCde(vo.getCustomCde());
				if(custom==null){
					s="客户编码不存在";
				}
			}
					
			try {
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getYear())){
					Integer.parseInt(vo.getYear().trim());
				}
			} catch (Exception e) {
				s = "年度格式不正确";
			}		
			if(StringUtils.isNotBlank(vo.getYear())){
				po=this.getByCusCdeAndYear(vo.getCustomCde(),vo.getYear().trim());
			}else{
				s="年度不能为空";
			}
			
			if(po==null){
				po=new InFeeClause();
			}
			
			
			try {
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getEnterFee())){
					po.setEnterFee(Double.parseDouble(vo.getEnterFee()));
					if(StringUtils.isBlank(vo.getEnterStartDate()))
						s = "进场费分摊开始时间不可为空";
					else{
						Date date = DateFormat.str2Date(vo.getEnterStartDate(), 2);
						if(date == null)
							s = "进场费分摊开始时间格式错误";
						else{
							po.setEnterStartDate(date);
							if(StringUtils.isBlank(vo.getEnterEndDate()))
								s = "进场费分摊结束时间不可为空";
							else{
								date = DateFormat.str2Date(vo.getEnterEndDate(), 2);
								if(date == null)
									s = "进场费结束开始时间格式错误";
								else
									po.setEnterEndDate(date);
							}
						}
					}
				}
			} catch (Exception e) {
				s = "进场费填写格式不正确";
			}
			
			try {
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getYearReturnFee()))
					po.setYearReturnFee(Double.parseDouble(vo.getYearReturnFee()));
			} catch (Exception e) {
				s = "年返金填写格式不正确";
			}
			try {
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getFixedFee())){
					po.setFixedFee(Double.parseDouble(vo.getFixedFee()));
					if(StringUtils.isBlank(vo.getFixedStartDate()))
						s = "固定费用分摊开始时间不可为空";
					else{
						Date date = DateFormat.str2Date(vo.getFixedStartDate(), 2);
						if(date == null)
							s = "固定费用分摊开始时间格式错误";
						else{
							po.setFixedStartDate(date);
							if(StringUtils.isBlank(vo.getFixedEndDate()))
								s = "固定费用分摊结束时间不可为空";
							else{
								date = DateFormat.str2Date(vo.getFixedEndDate(), 2);
								if(date == null)
									s = "固定费用结束开始时间格式错误";
								else
									po.setFixedEndDate(date);
							}
						}
					}
				}
			} catch (Exception e) {
				s = "固定费用填写格式不正确";
			}
			try {
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getMonthReturnFee()))
					po.setMonthReturnFee(Double.parseDouble(vo.getMonthReturnFee()));
			} catch (Exception e) {
				s = "月返金填写格式不正确";
			}
			try {
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getNetInfoFee())){
					po.setNetInfoFee(Double.parseDouble(vo.getNetInfoFee()));
					if(StringUtils.isBlank(vo.getNetStartDate()))
						s = "网络信息费分摊开始时间不可为空";
					else{
						Date date = DateFormat.str2Date(vo.getNetStartDate(), 2);
						if(date == null)
							s = "网络信息费分摊开始时间格式错误";
						else{
							po.setNetStartDate(date);
							if(StringUtils.isBlank(vo.getNetEndDate()))
								s = "网络信息费分摊结束时间不可为空";
							else{
								date = DateFormat.str2Date(vo.getNetEndDate(), 2);
								if(date == null)
									s = "网络信息费结束开始时间格式错误";
								else
									po.setNetEndDate(date);
							}
						}
					}
				}
			} catch (Exception e) {
				s = "网络信息费填写格式不正确";
			}
			try {
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getDeliveryFee()))
					po.setDeliveryFee(Double.parseDouble(vo.getDeliveryFee()));
			} catch (Exception e) {
				s = "配送服务费填写格式不正确";
			}
			
			
			try {
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getPosterFee())){
					po.setPosterFee(Double.parseDouble(vo.getPosterFee()));
						
					if(StringUtils.isBlank(vo.getPosterStartDate()))
						s = "海报费分摊开始时间不可为空";
					else{
						Date date = DateFormat.str2Date(vo.getPosterStartDate(), 2);
						if(date == null)
							s = "海报费分摊开始时间格式错误";
						else{
							po.setPosterStartDate(date);
							if(StringUtils.isBlank(vo.getPosterEndDate()))
								s = "海报费分摊结束时间不可为空";
							else{
								date = DateFormat.str2Date(vo.getPosterEndDate(), 2);
								if(date == null)
									s = "海报费结束开始时间格式错误";
								else
									po.setPosterEndDate(date);
							}
						}
					}
				}
			} catch (Exception e) {
				s = "海报费填写格式不正确";
			}
			try {
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getPromotionFee())){
					po.setPromotionFee(Double.parseDouble(vo.getPromotionFee()));
					
					if(StringUtils.isBlank(vo.getPromotionStartDate()))
						s = "促销陈列费分摊开始时间不可为空";
					else{
						Date date = DateFormat.str2Date(vo.getPromotionStartDate(), 2);
						if(date == null)
							s = "促销陈列费分摊开始时间格式错误";
						else{
							po.setPromotionStartDate(date);
							if(StringUtils.isBlank(vo.getPromotionEndDate()))
								s = "促销陈列费分摊结束时间不可为空";
							else{
								date = DateFormat.str2Date(vo.getPromotionEndDate(), 2);
								if(date == null)
									s = "促销陈列费结束开始时间格式错误";
								else
									po.setPromotionEndDate(date);
							}
						}
					}
				}
			} catch (Exception e) {
				s = "促销陈列费填写格式不正确";
			}
			try {
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getSponsorFee()))
					po.setSponsorFee(Double.parseDouble(vo.getSponsorFee()));
			} catch (Exception e) {
				s = "赞助费（比例）填写格式不正确";
			}
			try {
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getLossFee()))
					po.setLossFee(Double.parseDouble(vo.getLossFee()));
			} catch (Exception e) {
				s = "损耗费填写格式不正确";
			}
			try {
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getFixedDiscount()))
					po.setFixedDiscount(Double.parseDouble(vo.getFixedDiscount()));
			} catch (Exception e) {
				s = "固定折扣填写格式不正确";
			}
			
			try {
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getPilesoilFee())){
					po.setPilesoilFee(Double.parseDouble(vo.getPilesoilFee()));

					if(StringUtils.isBlank(vo.getPileStartDate()))
						s = "堆头费分摊开始时间不可为空";
					else{
						Date date = DateFormat.str2Date(vo.getPileStartDate(), 2);
						if(date == null)
							s = "堆头费分摊开始时间格式错误";
						else{
							po.setPileStartDate(date);
							if(StringUtils.isBlank(vo.getPileEndDate()))
								s = "堆头费分摊结束时间不可为空";
							else{
								date = DateFormat.str2Date(vo.getPileEndDate(), 2);
								if(date == null)
									s = "堆头费结束开始时间格式错误";
								else
									po.setPileEndDate(date);
							}
						}
					}
				}
			} catch (Exception e) {
				s = "堆头费填写格式不正确";
			}
			try {
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getMarketFee()))
					po.setMarketFee(Double.parseDouble(vo.getMarketFee()));
			} catch (Exception e) {
				s = "市场费写格式不正确";
			}
			try {
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getCaseReturnFee()))
					po.setCaseReturnFee(Double.parseDouble(vo.getCaseReturnFee()));
			} catch (Exception e) {
				s = "现款现货返利格式不正确";
			}
			try {
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getOtherFee())){
					po.setOtherFee(Double.parseDouble(vo.getOtherFee()));
					
					if(StringUtils.isBlank(vo.getOtherStartDate()))
						s = "其他费用分摊开始时间不可为空";
					else{
						Date date = DateFormat.str2Date(vo.getOtherStartDate(), 2);
						if(date == null)
							s = "其他费用分摊开始时间格式错误";
						else{
							po.setOtherStartDate(date);
							if(StringUtils.isBlank(vo.getOtherEndDate()))
								s = "其他费用分摊结束时间不可为空";
							else{
								date = DateFormat.str2Date(vo.getOtherEndDate(), 2);
								if(date == null)
									s = "其他费用结束开始时间格式错误";
								else
									po.setOtherEndDate(date);
							}
						}
					}
					
				}
			} catch (Exception e) {
				s = "其他费用填写格式不正确";
			}
/*			try {
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getStoreCount()))
					po.setStoreCount(Integer.parseInt(vo.getStoreCount()));
			} catch (Exception e) {
				s = "门店数填写格式不正确";
			}
			
			try {
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getFixedSponsorFee()))
					po.setFixedSponsorFee(Double.parseDouble(vo.getFixedSponsorFee()));
			} catch (Exception e) {
				s = "赞助费（固定）填写格式不正确";
			}*/
			try {
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getYear()))
					po.setYear(Integer.parseInt(vo.getYear()));
			} catch (Exception e) {
				s = "年度填写格式不正确";
			}
			
			if(s != null){
				sb.append(d + s + "<br/>");
				fail++;
				continue;
			}
			
			po.setCustom(customService.getByCde(vo.getCustomCde()));
			po.setYear(Integer.parseInt(vo.getYear()));
			if(vo.getStartDate() != null)
				po.setStartDate(DateFormat.str2Date(vo.getStartDate(), 2));
			if(vo.getEndDate()!= null)
				po.setEndDate(DateFormat.str2Date(vo.getEndDate(), 2));
			if(StringUtils.isNotBlank(vo.getRemark()))
				po.setRemark(vo.getRemark().trim());
			this.merge(po);
			succ++;
			
		}		
		return "导入完成，共导入 " + num + " 条数据，其中成功：" + succ + " 条，失败：" + fail+ " 条<br/>" + sb.toString();
	}

	
	
	private InFeeClause getByCusCdeAndYear(String customCde, String year) {
		String hql="from InFeeClause where custom.customCde = :customCde and year = :year ";
		Query query = em.createQuery(hql);
		query.setParameter("customCde", customCde);
		try{
			query.setParameter("year", Integer.parseInt(year));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		List<InFeeClause> list = query.getResultList();		
		return Common.checkList(list) ? list.get(0) : null;
		
	}

	private String checkVO(InFeeClauseExcelVO vo) throws Exception{
		if(vo == null)
			return "导入对象不能为空";
		
		if(StringUtils.isBlank(vo.getCustomCde()))
			return "客户编码不能为空";
		
		if(StringUtils.isBlank(vo.getYear())){
			return "年度不能为空";
		}else{
			if(!vo.getYear().matches("[0-9]{4}")){
				return "年度格式不正确，格式如：2017";
			}
		}
			
		
		if (StringUtils.isNotBlank(vo.getStartDate()) || StringUtils.isNotBlank(vo.getEndDate())) {
			if (DateFormat.str2Date(vo.getStartDate(),2) == null || DateFormat.str2Date(vo.getEndDate(), 2) == null) {
				return "日期格式不正确，格式为:2016-08-25";
			}
		}
		
		return null;
	}
	
	

}
