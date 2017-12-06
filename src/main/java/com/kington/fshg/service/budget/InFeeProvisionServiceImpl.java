package com.kington.fshg.service.budget;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.DateFormat;
import com.kington.fshg.common.PublicType;
import com.kington.fshg.common.PublicType.ApproveState;
import com.kington.fshg.model.budget.InFeeClause;
import com.kington.fshg.model.budget.InFeeProvision;
import com.kington.fshg.model.budget.InFeeProvisionVO;
import com.kington.fshg.model.info.Custom;
import com.kington.fshg.model.order.TransferVO;
import com.kington.fshg.service.BaseServiceImpl;
import com.kington.fshg.service.info.CustomService;
import com.kington.fshg.service.order.DeliverOrderService;
import com.kington.fshg.service.order.SaleBillService;
import com.kington.fshg.service.order.SaleOrderService;

public class InFeeProvisionServiceImpl extends BaseServiceImpl<InFeeProvision, InFeeProvisionVO>
		implements InFeeProvisionService {
	private static final long serialVersionUID = -8571209508368315557L;
	
	@Resource
	private InFeeClauseService inFeeClauseService;
	@Resource
	private CustomService customService;
	@Resource
	private SaleOrderService saleOrderService;
	@Resource
	private DeliverOrderService deliverOrderService;
	@Resource
	private SaleBillService saleBillService;
	
	@Override
	protected String getQueryStr(InFeeProvisionVO vo) throws Exception {
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
			if(vo.getCustom().getCustomType() != null  && Common.checkLong(vo.getCustom().getCustomType().getId()))
				sb.append(" and o.custom.customType.id = :customTypeId ");
		}

		if(Common.checkList(vo.getAreaIds()))
			sb.append(" and o.custom.area.id in (:areaIds)");
		if(Common.checkList(vo.getParentAreaIds()))
			sb.append(" and o.custom.area.parentArea.id in (:parentAreaIds)");
		if(StringUtils.isNotBlank(vo.getCode())){
			sb.append(" and o.code like :code ");
		}
		
		if(StringUtils.isNotBlank(vo.getCreateTimeStart()))
			sb.append(" and o.provisionTime >= :createTimeStart ");
		if(StringUtils.isNotBlank(vo.getCreateTimeEnd()))
			sb.append(" and o.provisionTime <= :createTimeEnd ");
		
		if(vo.getApproveState() != null)
			sb.append(" and o.approveState =:approveState ");
			sb.append(" and o.inFeeCount > 0 ");
		return sb.toString();
	}

	@Override
	protected void setQueryParm(Query query, InFeeProvisionVO vo)
			throws Exception {
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
			
			if(vo.getCustom().getCustomType() != null  && Common.checkLong(vo.getCustom().getCustomType().getId()))
				query.setParameter("customTypeId", vo.getCustom().getCustomType().getId());
		}
		
		if(Common.checkList(vo.getAreaIds()))
			query.setParameter("areaIds", vo.getAreaIds());
		if(Common.checkList(vo.getParentAreaIds()))
			query.setParameter("parentAreaIds", vo.getParentAreaIds());
		if(StringUtils.isNotBlank(vo.getCode()))
			query.setParameter("code", Common.SYMBOL_PERCENT + vo.getCode() +Common.SYMBOL_PERCENT);
		
		if(StringUtils.isNotBlank(vo.getCreateTimeStart()))
			query.setParameter("createTimeStart", DateFormat.str2Date(vo.getCreateTimeStart(), 2));
		if(StringUtils.isNotBlank(vo.getCreateTimeEnd()))
			query.setParameter("createTimeEnd", DateFormat.str2Date(vo.getCreateTimeEnd(), 2));
		if(vo.getApproveState() != null)
			query.setParameter("approveState", vo.getApproveState());
	}

	@Override
	protected void switchVO2PO(InFeeProvisionVO vo, InFeeProvision po)
			throws Exception {
		if(po == null)
			po = new InFeeProvision();
		
		if(StringUtils.isNotBlank(vo.getCode()))
			po.setCode(vo.getCode());
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
		if(vo.getInFeeCount() != null)
			po.setInFeeCount(vo.getInFeeCount());
		if(vo.getApproveState() != null)
			po.setApproveState(vo.getApproveState());
	}

	@SuppressWarnings("unchecked")
	@Override
	public InFeeProvision getByCode(String code) throws Exception {
		String sql="from InFeeProvision o where o.code= :code ";
		Query query = em.createQuery(sql);
		query.setParameter("code", code);
		List<InFeeProvision> list= query.getResultList();
		if(list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	@Override
	public boolean delete(Long id) throws Exception {
		String hql ="delete from bud_in_fee_provision where id = :id ";
		Query query = em.createNativeQuery(hql);
		query.setParameter("id", id);
		return query.executeUpdate() > 0;
	}

	@Override
	public int createProvision(String ids) throws Exception {
		int count = 0;
		if(StringUtils.isNotBlank(ids)){
			Calendar calendar = Calendar.getInstance();
			//合同内预提只生成当前时间前一个月的预提
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			
			for(String id : ids.split(",")){
				Long customId = Long.parseLong(id);
				InFeeClause clause = inFeeClauseService.getByCusIdAndYear(customId, calendar.get(Calendar.YEAR));
				Custom custom = customService.getById(customId);
				
				if(clause != null){
					//前一个月没有预提的新增，已有的待审核状态的可以只可修改前一个月
					InFeeProvision provision = getByCusAndMon(customId,calendar.getTime());
					if(provision == null){
						provision = new InFeeProvision();
						provision.setCustom(custom);
						provision.setInFeeClause(clause);
						provision.setCode(getCode());
						provision.setProvisionTime(calendar.getTime());
					}else if(provision.getApproveState() != ApproveState.DSP)
						continue;
					
					//查询当年前几个月预提各项费用总和
					Object[] o = getLastSumByCla(clause.getId(),DateFormat.getYearMonthFirst(calendar.getTime(), 0));
					//客户类型是DKA用收货数，其他用发票额
					Double deliverCount = 0d;
					if(custom.getCustomType() != null && custom.getCustomType().getCustomTypeName().indexOf("DKA") > -1)
						deliverCount = deliverOrderService.getOrderCount(custom.getCustomCde(), calendar.getTime(),false);//收货数（发货单实收价格,按照订单日期统计）
					else
					    deliverCount = saleBillService.getCountPrice(custom.getCustomCde(), calendar.getTime());//发票额（本币价税合计，按制单日期）
					Double partDeliverCount = deliverOrderService.getOrderCount(custom.getCustomCde(), calendar.getTime(),true);//部分收货数（现款现货返利发货单实收价格）
					Double saleCount = saleOrderService.getOrderCount(custom.getCustomCde(), calendar.getTime());//订单数（订单价格合计）
					int lastMonCount = 12 - calendar.get(Calendar.MONTH);//剩余月份数
					
					
					//保存进当月预提费用中
					//固定费用计算公式：（合同内条款费用-前几个月费用总和）/剩余月份数
					//比例费用计算公式：（合同内条款费用-前几个月费用总和）（注意涉及到的订单费用是从1月到当前的）
					Double sum = 0d;
					
					//进场费（固定数）
					Double a = getFixCal(clause.getEnterFee(),o[0],lastMonCount);
					provision.setEnterFee(a);
					sum += a;
					
					//年返金（比例）
					Double b = getProCal(clause.getYearReturnFee(),deliverCount,o[1]);
					provision.setYearReturnFee(b);
					sum += b;
					
					//固定费用（固定数）
					Double c = getFixCal(clause.getFixedFee(),o[2],lastMonCount);
					provision.setFixedFee(c);
					sum += c;
					
					//月返金（比例）
					Double d = getProCal(clause.getMonthReturnFee(),deliverCount,o[3]);
					provision.setMonthReturnFee(d);
					sum += d;
					
					//网络信息费用（固定）
					Double e = getFixCal(clause.getNetInfoFee(), o[4], lastMonCount);
					provision.setNetInfoFee(e);
					sum += e;
					
					//配送服务费（比例）
					Double f = getProCal(clause.getDeliveryFee(),deliverCount,o[5]);
					provision.setDeliveryFee(f);
					sum += f;
					
					//海报费（固定）
					Double g = getFixCal(clause.getPosterFee(),o[6],lastMonCount);
					provision.setPosterFee(g);
					sum += g;
					
					//促销陈列费（固定）
					Double h = getFixCal(clause.getPromotionFee(),o[7],lastMonCount);
					provision.setPromotionFee(h);
					sum += h;
					
					//赞助费（特殊算法）
					Double i = getSponsorFee(clause,deliverCount,o[8]);
					provision.setSponsorFee(i);
					sum += i;
					
					//损耗费（比例）
					Double j = getProCal(clause.getLossFee(),deliverCount,o[9]);
					provision.setLossFee(j);
					sum += j;
					
					//固定折扣（订单比例）
					Double k = getProCal(clause.getFixedDiscount(),saleCount,o[10]);
					provision.setFixedDiscount(k);
					sum += k;
					
					//堆头费（固定）
					Double l = getFixCal(clause.getPilesoilFee(),o[11],lastMonCount);
					provision.setPilesoilFee(l);
					sum += l;
					
					//市场费（比例）
					Double m = getProCal(clause.getMarketFee(),deliverCount,o[12]);
					provision.setMarketFee(m);
					sum += m;
					
					//现款现货返利（部分发货比例）
					Double n = getProCal(clause.getCaseReturnFee(),partDeliverCount,o[13]);
					provision.setCaseReturnFee(n);
					sum += n;
					
					//其他费用（固定）
					Double p = getFixCal(clause.getOtherFee(),o[14],lastMonCount);
					provision.setOtherFee(p);
					sum += p;
					
					provision.setInFeeCount(PublicType.setDoubleScale(sum));
					
					if (provision.getCreateTime()==null) {
						provision.setCreateTime(new Date());
						provision.setUpdateTime(provision.getCreateTime());
						
					} else {
						provision.setUpdateTime(new Date());
					}
					
					em.merge(provision);
					em.flush();
					count ++;
				}
			}
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public InFeeProvision getByCusAndMon(Long customId, Date date) {
		String hql = "from InFeeProvision where custom.id = :customId and provisionTime >= :createTimeStart and provisionTime < :createTimeEnd ";
		Query query = em.createQuery(hql);
		query.setParameter("customId", customId);
		query.setParameter("createTimeStart", DateFormat.getYearMonthFirst(date, 0));
		query.setParameter("createTimeEnd", DateFormat.getYearMonthFirst(date, 1));
		List<InFeeProvision> list = query.getResultList();		
		return Common.checkList(list) ? list.get(0) : null;
	}
	
	@SuppressWarnings("unchecked")
	private Object[] getLastSumByCla(Long clauseId, Date date){
		String sql = "SELECT SUM(enterFee),SUM(yearReturnFee),SUM(fixedFee),SUM(monthReturnFee), "
				+ " SUM(netInfoFee),SUM(deliveryFee),SUM(posterFee),SUM(promotionFee),SUM(sponsorFee), "
				+ " SUM(lossFee),SUM(fixedDiscount),SUM(pilesoilFee),SUM(marketFee),SUM(caseReturnFee),SUM(otherFee) "
				+ " FROM bud_in_fee_provision where clause_id = " + clauseId + " and provisionTime < '" + DateFormat.date2Str(date, 9) + "'"
				+ " and provisionTime >= '" + DateFormat.date2Str(DateFormat.getYearFirstDay(date), 9) + "'";
		Query query = em.createNativeQuery(sql);
		List<Object[]> list = query.getResultList();
		return Common.checkList(list) ? list.get(0) : null;
	}

	//预提编号格式：一个字母标识+年月（6位）+ 5位流水号
	@SuppressWarnings("unchecked")
	private synchronized String getCode(){
		String code = "A" + DateFormat.date2Str(new Date(), 4);
		String sql = "select code from bud_in_fee_provision where code like '" + code + "%' order by code desc limit 1 ";
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
	
	private Double getDoubleValue(Object o){
		if( o != null){
			BigDecimal bigDecimal = (BigDecimal)o;
			return bigDecimal.doubleValue();
		}else
			return 0d;
	}
	
	
	//固定费用计算公式：（合同内条款费用-前几个月费用总和）/剩余月份数
	private Double getFixCal(Double clause, Object o, Integer lastMonCount){
		
		Double result = 0d;
		if(clause != null){
			if(o != null)
				result = (clause - (Double)o)/lastMonCount;
			else
				result = clause/lastMonCount;
		}
		
		if(result.compareTo(0d) < 0)
			result = 0d;
		return PublicType.setDoubleScale(result);
	}
	//比例费用计算公式：（合同内条款费用（收货数*条款对应比例）-前几个月费用总和）（注意涉及到的订单费用是从1月到当前的）
	private Double getProCal(Double clause, Double order, Object o){
		
		Double result = 0d;
		if(clause != null && order != null ){
			Double a = clause * order * 0.01d;
			if(o != null)
				result = a - (Double)o;
			else
				result = a;
		}
		
		return PublicType.setDoubleScale(result);
	}
	
	//赞助费计算:（收货数*条款对应比例） - （固定数*门店数） - 前几个月费用总和
	private Double getSponsorFee(InFeeClause clause, Double order, Object o){
		
		Double result = 0d;
		if(clause.getSponsorFee() != null && order != null)
			result = clause.getSponsorFee() * order/100;
			
		if(clause.getFixedSponsorFee() != null && clause.getStoreCount() != null)
			result = result - clause.getFixedSponsorFee() * clause.getStoreCount();
		
		if(o != null)
			result = result - (Double)o;
		
		if(result.compareTo(0d) < 0)
			result = 0d;
		
		return PublicType.setDoubleScale(result);
	}

	@Override
	public void countInFee(InFeeProvision ifp) {
		Double sum = 0d;
		sum = ifp.getEnterFee() + ifp.getFixedFee() + ifp.getYearReturnFee()
				+ ifp.getMonthReturnFee() + ifp.getNetInfoFee() + ifp.getDeliveryFee()
				+ ifp.getPosterFee() + ifp.getPromotionFee() + ifp.getSponsorFee()
				+ ifp.getLossFee() + ifp.getFixedDiscount() + ifp.getPilesoilFee()
				+ ifp.getMarketFee() + ifp.getCaseReturnFee() + ifp.getOtherFee();
		ifp.setInFeeCount(PublicType.setDoubleScale(sum));
		em.merge(ifp);
	}

	@Override
	public Map<Long, List<InFeeProvision>> getInFeeMap(InFeeProvisionVO vo)
			throws Exception {
		
		vo.setObjectsPerPage(Integer.MAX_VALUE);
		
		Long customId = 0L;
		Map<Long, List<InFeeProvision>> map = new HashMap<Long, List<InFeeProvision>>();
		List<InFeeProvision> list = new ArrayList<InFeeProvision>();
		 
		for(InFeeProvision p : getPageList(vo).getList()){
			if(p.getInFeeCount() != null && p.getInFeeCount() != 0){
				if(!customId.equals(p.getCustom().getId()) && !customId.equals(0L)){
					map.put(customId, list);
					list = new ArrayList<InFeeProvision>();
				}
				
				list.add(p);
				customId = p.getCustom().getId();
			}
		}
		
		map.put(customId, list);
		return map;
	}

	@Override
	public List<InFeeProvision> inFeeProvision(InFeeProvisionVO vo) throws Exception {	
			String hql="select o from InFeeProvision o where 1=1 ";
			hql+=getQueryStr(vo)+" order by createTime desc ";
			Query query=em.createQuery(hql);
			setQueryParm(query, vo);
			return query.getResultList();

	}

	@Override
	public void exprotInFeeProvision(String[] heads, InFeeProvision ifp, Map<String, String> map) throws Exception {
		String[] header=Common.getExportHeader("INFEEPROVISION");
		for(String key:heads){
			int i=0;
			if(key.equals(header[i++])){
				map.put(key, ifp.getCode());     		//预提编码"
			}else if(key.equals(header[i++])){
				if(ifp.getCustom().getArea() != null && ifp.getCustom().getArea().getParentArea() != null)
					map.put(key, ifp.getCustom().getArea().getParentArea().getAreaName());			//,"所属大区"
			}else if(key.equals(header[i++])){
				if(ifp.getCustom().getArea() != null)
					map.put(key, ifp.getCustom().getArea().getAreaName());			//,"所属地区"
			}else if(key.equals(header[i++])){
				if(ifp.getCustom().getCustomType() != null)
					map.put(key, ifp.getCustom().getCustomType().getCustomTypeName());			//,"所属分类"
			}else if(key.equals(header[i++])){
				map.put(key, ifp.getCustom().getCustomName());			//,"所属客户"
			}else if(key.equals(header[i++])){
				map.put(key, ifp.getCustom().getCustomCde());			//,"客户编码"
			}else if(key.equals(header[i++])){
				map.put(key, DateFormat.date2Str((Date) ifp.getCreateTime(), 2)); //"预提日期"
			}else if(key.equals(header[i++])){
				if(ifp.getInFeeCount()!=null){
					map.put(key, ifp.getInFeeCount().toString());				//,,"费用总和(元)",
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				if(ifp.getApproveState()!=null){
					map.put(key, ifp.getApproveState().getText());				//"审核状态
				}
			}else if(key.equals(header[i++])){
				if(ifp.getEnterFee()!=null){
					map.put(key, ifp.getEnterFee().toString());				//进场费
				}
			}else if(key.equals(header[i++])){
				if(ifp.getFixedFee()!=null){
					map.put(key, ifp.getFixedFee().toString());				//固定费用
				}
			}else if(key.equals(header[i++])){
				if(ifp.getYearReturnFee()!=null){
					map.put(key, ifp.getYearReturnFee().toString());				//年返金
				}
			}else if(key.equals(header[i++])){
				if(ifp.getMonthReturnFee()!=null){
					map.put(key, ifp.getMonthReturnFee().toString());				//,月返金
				}
			}else if(key.equals(header[i++])){
				if(ifp.getNetInfoFee()!=null){
					map.put(key, ifp.getNetInfoFee().toString());				//网络信息费
				}
			}else if(key.equals(header[i++])){
				if(ifp.getDeliveryFee()!=null){
					map.put(key, ifp.getDeliveryFee().toString());				//配送服务费
				}
			}else if(key.equals(header[i++])){
				if(ifp.getPosterFee()!=null){
					map.put(key, ifp.getPosterFee().toString());				//海报费
				}
			}else if(key.equals(header[i++])){
				if(ifp.getPromotionFee()!=null){
					map.put(key, ifp.getPromotionFee().toString());				//促销陈列费
				}
			}else if(key.equals(header[i++])){
				if(ifp.getSponsorFee()!=null){
					map.put(key, ifp.getSponsorFee().toString());				//赞助费
				}
			}else if(key.equals(header[i++])){
				if(ifp.getLossFee()!=null){
					map.put(key, ifp.getLossFee().toString());				//损耗费
				}
			}else if(key.equals(header[i++])){
				if(ifp.getFixedDiscount()!=null){
					map.put(key, ifp.getFixedDiscount().toString());				//固定折扣
				}
			}else if(key.equals(header[i++])){
				if(ifp.getPilesoilFee()!=null){
					map.put(key, ifp.getPilesoilFee().toString());				//堆头费
				}
			}else if(key.equals(header[i++])){
				if(ifp.getMarketFee()!=null){
					map.put(key, ifp.getMarketFee().toString());				//市场费
				}
			}else if(key.equals(header[i++])){
				if(ifp.getCaseReturnFee()!=null){
					map.put(key, ifp.getCaseReturnFee().toString());				//现款现货返利
				}
			}else if(key.equals(header[i++])){
				if(ifp.getOtherFee()!=null){
					map.put(key, ifp.getOtherFee().toString());				//其他费用
				}
			}
			
		}
		
		
	}

	@Override
	public synchronized int createProvisionNew(String ids, InFeeProvisionVO vo)
			throws Exception {
		int count = 0;
		if(StringUtils.isNotBlank(ids)){
			Calendar calendar = Calendar.getInstance();
			for(String id : ids.split(",")){
				Long customId = Long.parseLong(id);
				InFeeClause clause = inFeeClauseService.getByCusIdAndYear(customId, calendar.get(Calendar.YEAR));
				Custom custom = customService.getById(customId);
				
				if(clause != null){
					//查找当前月份的预提
					InFeeProvision provision = getByCusAndMon(customId,DateFormat.str2Date(vo.getCreateTimeStart(), 2));
					if(provision == null){
						provision = new InFeeProvision();
						provision.setCustom(custom);
						provision.setInFeeClause(clause);
						provision.setCode(getCode());
						//合同内预提预提日期为用户选择的查询区间开始时间
						provision.setProvisionTime(DateFormat.str2Date(vo.getCreateTimeStart(), 2));
						
					}else if(provision.getApproveState() != ApproveState.DSP)
						continue;
					
					//客户类型是DKA用收货数，其他用发票额，时间段来自vo，统计标记了已返利的单据数据
					//固定费用计算公式：当前时间在分摊时间段内，按分摊时间段月份数进行分摊，不再则不计算
					//比例费用计算公式：用相应的基数*百分比
					Double deliverCount = 0d;
					Double sum = 0d;
					if(custom.getCustomType() != null && custom.getCustomType().getCustomTypeName().indexOf("DKA") > -1){
						deliverCount = deliverOrderService.getOrderCountByTime(custom.getCustomCde(), vo.getCreateTimeStart(), vo.getCreateTimeEnd(),true);//收货数（发货单实收价格,按照原始单据日期统计，标记已返利的）
						provision.setSum(deliverCount);
						
						//进场费（固定数）
						Double a = fixedCount(clause.getEnterStartDate(), clause.getEnterEndDate(), calendar.getTime(), clause.getEnterFee());
						provision.setEnterFee(a);
						sum += a;
						
						//年返金（比例）
						Double b = perCount(deliverCount,clause.getYearReturnFee());
						provision.setYearReturnFee(b);
						sum += b;
						
						//月返金（比例）
						Double d = perCount(deliverCount,clause.getMonthReturnFee());
						provision.setMonthReturnFee(d);
						sum += d;
						
						//固定费用（固定数）
						Double c = fixedCount(clause.getFixedStartDate(), clause.getFixedEndDate(), calendar.getTime(), clause.getFixedFee());
						provision.setFixedFee(c);
						sum += c;
						
						//网络信息费用（固定）
						Double e = fixedCount(clause.getNetStartDate(), clause.getNetEndDate(), calendar.getTime(), clause.getNetInfoFee());
						provision.setNetInfoFee(e);
						sum += e;
						
						//配送服务费（比例）
						Double f = perCount(deliverCount, clause.getDeliveryFee());
						provision.setDeliveryFee(f);
						sum += f;
						
						//海报费（固定）
						Double g = fixedCount(clause.getPosterStartDate(), clause.getPosterEndDate(), calendar.getTime(), clause.getPosterFee());
						provision.setPosterFee(g);
						sum += g;
						
						//促销陈列费（固定）
						Double h = fixedCount(clause.getPromotionStartDate(), clause.getPromotionEndDate(), calendar.getTime(), clause.getPromotionFee());
						provision.setPromotionFee(h);
						sum += h;
						
						//赞助费（比例）
						Double i = perCount(deliverCount, clause.getSponsorFee());
						provision.setSponsorFee(i);
						sum += i;
						
						//损耗费（比例）
						Double j = perCount(deliverCount, clause.getLossFee());
						provision.setLossFee(j);
						sum += j;
						
						//固定折扣（订单比例）
						Double k = perCount(deliverCount, clause.getFixedDiscount());
						provision.setFixedDiscount(k);
						sum += k;
						
						//堆头费（固定）
						Double l = fixedCount(clause.getPileStartDate(), clause.getPileEndDate(), calendar.getTime(), clause.getPilesoilFee());
						provision.setPilesoilFee(l);
						sum += l;
						
						//其他费用（固定）
						Double p = fixedCount(clause.getOtherStartDate(), clause.getOtherEndDate(), calendar.getTime(), clause.getOtherFee());
						provision.setOtherFee(p);
						sum += p;
					}
					else{
						deliverCount = saleBillService.getCountPriceByTime(custom.getCustomCde(), vo.getCreateTimeStart(), vo.getCreateTimeEnd());//发票额（本币价税合计，按制单日期,标记已返利）
						provision.setSum(deliverCount);
						
						//进场费（固定数）
						Double a = fixedCount(clause.getEnterStartDate(), clause.getEnterEndDate(), calendar.getTime(), clause.getEnterFee());
						provision.setEnterFee(a);
						sum += a;
						
						//年返金（比例）
						Double b = perCount(deliverCount,clause.getYearReturnFee());
						provision.setYearReturnFee(b);
						sum += b;
						
						//固定费用（固定数）
						Double c = fixedCount(clause.getFixedStartDate(), clause.getFixedEndDate(), calendar.getTime(), clause.getFixedFee());
						provision.setFixedFee(c);
						sum += c;
						
						//市场费（比例）
						Double m = perCount(deliverCount, clause.getMarketFee());
						provision.setMarketFee(m);
						sum += m;
						
						//现款现货返利（比例）
						Double n = perCount(deliverCount, clause.getCaseReturnFee());
						provision.setCaseReturnFee(n);
						sum += n;
						
						//其他费用（固定）
						Double p = fixedCount(clause.getOtherStartDate(), clause.getOtherEndDate(), calendar.getTime(), clause.getOtherFee());
						provision.setOtherFee(p);
						sum += p;
					}
					
					provision.setInFeeCount(PublicType.setDoubleScale(sum));
					
					if (provision.getCreateTime()==null) {
						provision.setCreateTime(new Date());
						provision.setUpdateTime(provision.getCreateTime());
						
					} else {
						provision.setUpdateTime(new Date());
					}
					
					em.merge(provision);
					em.flush();
					count ++;
				}
			}
		}
		return count;
	}
	
	
	//固定数计算
	public Double fixedCount(Date startTime, Date endTime, Date nowTime, Double count){
		if(count != null && count != 0
				&& startTime != null && endTime != null && endTime.getTime() >= startTime.getTime()
				&& nowTime.getTime() >= startTime.getTime() && nowTime.getTime() <= endTime.getTime()){
			Calendar calendar = Calendar.getInstance();
			Calendar calendar1 = Calendar.getInstance();
			calendar.setTime(startTime);
			calendar1.setTime(endTime);
			int c = calendar1.get(Calendar.MONTH) - calendar.get(Calendar.MONTH) + 1;
			return PublicType.setDoubleScale(count / c);
		}else 
			return 0d;
		
	}
	
	//比例计算
	public Double perCount(Double count, Double per){
		if(count != null && count != 0 
				&& per != null && per != 0)
			return PublicType.setDoubleScale(count * per * 0.01);
		else 
			return 0d;
	}
}
