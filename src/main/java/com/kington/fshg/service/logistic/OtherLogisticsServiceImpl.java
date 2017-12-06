package com.kington.fshg.service.logistic;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.DateFormat;
import com.kington.fshg.common.PublicType;
import com.kington.fshg.common.PublicType.DictType;
import com.kington.fshg.model.logistic.OtherLogistics;
import com.kington.fshg.model.logistic.OtherLogisticsVO;
import com.kington.fshg.model.system.Dict;
import com.kington.fshg.service.BaseServiceImpl;
import com.kington.fshg.service.order.TransferService;
import com.kington.fshg.service.system.DictService;

import freemarker.template.utility.DateUtil;

public class OtherLogisticsServiceImpl extends
		BaseServiceImpl<OtherLogistics, OtherLogisticsVO> implements
		OtherLogisticsService {
	private static final long serialVersionUID = -8801776416139712540L;
	
	@Resource
	private TransferService transferService;
	@Resource
	private DictService dictService;

	@Override
	protected String getQueryStr(OtherLogisticsVO vo) throws Exception {
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
		}
		
		if(StringUtils.isNotBlank(vo.getCreateStartTime()))
			sb.append(" and o.createTime >= :createStartTime ");
		if(StringUtils.isNotBlank(vo.getCreateEndTime()))
			sb.append(" and o.createTime < :createEndTime ");
		
		
		if(vo.getApproveState() != null)
			sb.append(" and o.approveState =:approveState ");
		
		if(Common.checkList(vo.getAreaIds()))
			sb.append(" and o.custom.area.id in (:areaIds)");
		if(Common.checkList(vo.getParentAreaIds()))
			sb.append(" and o.custom.area.parentArea.id in (:parentAreaIds)");
		return sb.toString();
	}

	@Override
	protected void setQueryParm(Query query, OtherLogisticsVO vo)
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
		}
		
		if(StringUtils.isNotBlank(vo.getCreateStartTime()))
			query.setParameter("createStartTime", DateFormat.str2Date(vo.getCreateStartTime(), 2));
		if(StringUtils.isNotBlank(vo.getCreateEndTime()))
			query.setParameter("createEndTime", DateFormat.getAfterDayFirst(DateFormat.str2Date(vo.getCreateEndTime(), 2)));
		
		if(vo.getApproveState() != null)
			query.setParameter("approveState", vo.getApproveState());
		
		if(Common.checkList(vo.getAreaIds()))
			query.setParameter("areaIds", vo.getAreaIds());
		if(Common.checkList(vo.getParentAreaIds()))
			query.setParameter("parentAreaIds", vo.getParentAreaIds());
		
	}

	@Override
	protected void switchVO2PO(OtherLogisticsVO vo, OtherLogistics po)
			throws Exception {
		if(vo.getCustom() != null)
			po.setCustom(vo.getCustom());
		if(vo.getCost() != null)
			po.setCost(vo.getCost());
		if(vo.getWagesShare() != null)
			po.setWagesShare(vo.getWagesShare());
		if(vo.getReturnGoods() != null)
			po.setReturnGoods(vo.getReturnGoods());
		if(vo.getStorageShare() != null)
			po.setStorageShare(vo.getStorageShare());
		if(vo.getTransferCost() != null)
			po.setTransferCost(vo.getTransferCost());
		if(vo.getApproveState() != null)
			po.setApproveState(vo.getApproveState());
		if(vo.getSalesamount() != null)
			po.setSalesamount(vo.getSalesamount());
		if(vo.getOtherLogisticsCost() != null)
			po.setOtherLogisticsCost(vo.getOtherLogisticsCost());
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OtherLogistics> getMonByCustom(Long customId) throws Exception {
		String hql = "from OtherLogistics where custom.id = :customId "
				+ " and createTime >= :createStartTime and createTime < :createEndTime ";
		Query query = em.createQuery(hql);
		query.setParameter("customId", customId);
		query.setParameter("createStartTime", DateFormat.str2Date(DateFormat.getMonthStart(), 2));
		query.setParameter("createEndTime",  DateFormat.getYearMonthFirst(new Date(), 1));
		
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Double getMonCost(String createStartTime, String createEndTime) throws Exception {
		String sql = "select sum(salesamount) from logistics_other_logistics "
				+ " where DATE_FORMAT(createTime,'%Y-%m-%d') >= '" + createStartTime + "' and DATE_FORMAT(createTime,'%Y-%m-%d') <= '" + createEndTime + "'";
		Query query = em.createNativeQuery(sql);
		List<Object> list = query.getResultList();
		Double a = 0d;
		if(Common.checkList(list)){
			Double sum = (Double)list.get(0);
			if(sum != null){
				BigDecimal bigDecimal = new BigDecimal(sum);
				a = bigDecimal.setScale(2, 4).doubleValue();
			}
		}
		return a;
	}

	@Override
	public int ftCost(OtherLogisticsVO vo) throws Exception {
		int succes = 0;
		
		//当月总的销售额
		Double saleF = getMonCost(vo.getCreateStartTime(),vo.getCreateEndTime());
		//当月调拨费
		Double dbF = transferService.getTransferCost();
		//工资
		List<Dict> dictList = dictService.getByType(DictType.GZ);
		Double gzF = Common.checkList(dictList) ? dictList.get(0).getValue() : 3116.5d; 
		//仓储
		dictList = dictService.getByType(DictType.CC);
		Double ccF = Common.checkList(dictList) ? dictList.get(0).getValue() : 5448.11d; 
		
		OtherLogisticsVO vo1 = new OtherLogisticsVO();
		vo1.setCreateStartTime(vo.getCreateStartTime());
		vo1.setCreateEndTime(vo.getCreateEndTime());
		vo1.setObjectsPerPage(Integer.MAX_VALUE);
		List<OtherLogistics> list = getPageList(vo1).getList();
		
		for(OtherLogistics o : list){
			Double salesamount = o.getSalesamount();
			if(salesamount != null && !salesamount.equals(0d) && saleF != null && !saleF.equals(0d)){
				Double bl = salesamount / saleF ;
				o.setWagesShare(PublicType.setDoubleScale(gzF * bl));
				o.setStorageShare(PublicType.setDoubleScale(ccF * bl));
				o.setTransferCost(PublicType.setDoubleScale(dbF * bl));
				o.setOtherLogisticsCost(PublicType.setDoubleScale(o.getReturnGoods() + o.getStorageShare() + o.getTransferCost()
						+ o.getWagesShare() + o.getCost()));
				
				o.setUpdateTime(new Date());
				em.merge(o);
			}
			succes ++;
			
		}
		return succes;
	}

	@Override
	public void export(String[] heads, OtherLogistics o, Map<String, String> map)
			throws Exception {
		String[] header=Common.getExportHeader("OTHERLOGISTICS");
		for(String key : heads){
			int i=0;
			if(key.equals(header[i++])){
				map.put(key, o.getCustom().getCustomName()); // 客户名称
			}else if(key.equals(header[i++])){
				if(o.getCost() != null)
					map.put(key, o.getCost().toString()); // 促销道具
			}else if(key.equals(header[i++])){
				if(o.getReturnGoods() != null)
					map.put(key, o.getReturnGoods().toString()); // 退货
			}else if(key.equals(header[i++])){
				if(o.getSalesamount() != null)
					map.put(key, o.getSalesamount().toString()); // 销售额
			}else if(key.equals(header[i++])){
				if(o.getWagesShare() != null)
					map.put(key, o.getWagesShare().toString()); // 工资分摊
			}else if(key.equals(header[i++])){
				if(o.getStorageShare() != null)
					map.put(key, o.getStorageShare().toString()); //仓储分摊
			}else if(key.equals(header[i++])){
				if(o.getTransferCost() != null)
					map.put(key, o.getTransferCost().toString()); // 调拨费分摊
			}else if(key.equals(header[i++])){
				if(o.getOtherLogisticsCost() != null)
					map.put(key, o.getOtherLogisticsCost().toString()); // 运费合计
			}else if(key.equals(header[i++])){
				if(o.getCreateTime() != null)
					map.put(key, DateFormat.date2Str(o.getCreateTime(), 2)); // 提交日期
			}
		}
		
	}


}
