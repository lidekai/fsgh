package com.kington.fshg.service.logistic;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.DateFormat;
import com.kington.fshg.common.PublicType;
import com.kington.fshg.common.PublicType.ChargeType;
import com.kington.fshg.common.PublicType.DictType;
import com.kington.fshg.model.info.Custom;
import com.kington.fshg.model.info.Product;
import com.kington.fshg.model.logistic.Logistics;
import com.kington.fshg.model.logistic.LogisticsVO;
import com.kington.fshg.model.order.DeliverOrder;
import com.kington.fshg.model.order.DeliverOrderVO;
import com.kington.fshg.model.system.Dict;
import com.kington.fshg.service.BaseServiceImpl;
import com.kington.fshg.service.info.CustomService;
import com.kington.fshg.service.info.ProductService;
import com.kington.fshg.service.order.DeliverOrderService;
import com.kington.fshg.service.system.DictService;

public class LogisticServiceImpl extends
		BaseServiceImpl<Logistics, LogisticsVO> implements LogisticService {
	private static final long serialVersionUID = 2019147229074947117L;
	
	@Resource
	private DeliverOrderService deliverOrderService;
	
	@Resource
	private CustomService customService;
	
	@Resource
	private ProductService productService;
	
	@Resource
	private DictService dictService;

	@Override
	protected String getQueryStr(LogisticsVO vo) throws Exception {
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
		
		if(vo.getDeliverOrder() != null){
			if(StringUtils.isNotBlank(vo.getDeliverOrder().getCdlCode()))
				sb.append(" and o.deliverOrder.cdlCode = :cdlCode");
		}
		
		if(StringUtils.isNotBlank(vo.getOrderStartTime()))
			sb.append(" and o.deliverOrder.deliverDate >= :orderStartTime ");
		if(StringUtils.isNotBlank(vo.getOrderEndTime()))
			sb.append(" and o.deliverOrder.deliverDate <= :orderEndTime ");
		
		if(vo.getProduct() != null){
			if(StringUtils.isNotBlank(vo.getProduct().getStockCde()))
				sb.append(" and o.product.stockCde =:stockCde ");
			if(StringUtils.isNotBlank(vo.getProduct().getProductName()))
				sb.append(" and o.product.productName like :productName ");
		}
		
		if(vo.getApproveState() != null)
			sb.append(" and o.approveState =:approveState ");
		
		if(Common.checkList(vo.getAreaIds()))
			sb.append(" and o.custom.area.id in (:areaIds)");
		if(Common.checkList(vo.getParentAreaIds()))
			sb.append(" and o.custom.area.parentArea.id in (:parentAreaIds)");
		return sb.toString();
	}

	@Override
	protected void setQueryParm(Query query, LogisticsVO vo) throws Exception {
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
		if(vo.getDeliverOrder() != null){
			if(StringUtils.isNotBlank(vo.getDeliverOrder().getCdlCode()))
				query.setParameter("cdlCode", vo.getDeliverOrder().getCdlCode());
		}
		
		if(StringUtils.isNotBlank(vo.getOrderStartTime()))
			query.setParameter("orderStartTime", DateFormat.str2Date(vo.getOrderStartTime(), 2));
		if(StringUtils.isNotBlank(vo.getOrderEndTime()))
			query.setParameter("orderEndTime", DateFormat.str2Date(vo.getOrderEndTime(), 2));
		
		if(vo.getProduct() != null){
			if(StringUtils.isNotBlank(vo.getProduct().getStockCde()))
				query.setParameter("stockCde", vo.getProduct().getStockCde());
			if(StringUtils.isNotBlank(vo.getProduct().getProductName()))
				query.setParameter("productName", Common.SYMBOL_PERCENT + vo.getProduct().getProductName() + Common.SYMBOL_PERCENT);
		}
		
		if(vo.getApproveState() != null)
			query.setParameter("approveState", vo.getApproveState());
		if(Common.checkList(vo.getAreaIds()))
			query.setParameter("areaIds", vo.getAreaIds());
		if(Common.checkList(vo.getParentAreaIds()))
			query.setParameter("parentAreaIds", vo.getParentAreaIds());
		
	}

	@Override
	protected void switchVO2PO(LogisticsVO vo, Logistics po) throws Exception {
		if(vo.getApproveState() != null)
			po.setApproveState(vo.getApproveState());
		
	}

	@Override
	public int createLogistic(Date orderStartTime, Date orderEndTime) throws Exception {
		DeliverOrderVO dVO = new DeliverOrderVO();
		dVO.setDeliverStartTime(orderStartTime);
		dVO.setDeliverEndTime(orderEndTime);
		dVO.setObjectsPerPage(Integer.MAX_VALUE);
		List<DeliverOrder> orderList = deliverOrderService.getPageList(dVO).getList();
		
		int count = 0;
		List<Dict> list = dictService.getByType(DictType.BXBL);
		Double bl = Common.checkList(list) ? list.get(0).getValue() : 0.23d;
		for(DeliverOrder order : orderList){
			if(!check(order.getId())){
				Custom custom = customService.getByCde(order.getCustomCde());
				Product product = productService.getByCde(order.getStockCde());
				if(custom != null && product != null){
					Logistics logistics = new Logistics();
					logistics.setDeliverOrder(order);
					logistics.setProduct(product);
					logistics.setCustom(custom);
					
					//货物重量 = 存货每箱毛重（来自存货信息）x 货物件数（来自发货单）
					if(product.getBoxWeight() != null && order.getNumber() != null)
						logistics.setLogWeight(PublicType.setDoubleScale(product.getBoxWeight() * order.getNumber()));
					else
						logistics.setLogWeight(0d);
					
					//货物体积 = 存货每箱体积（来自存货信息） x 货物件数（来自发货单）
					if(product.getVolume() != null && order.getNumber() != null)
						logistics.setLogVolume(PublicType.setDoubleScale(product.getVolume() * order.getNumber()));
					else
						logistics.setLogVolume(0d);
					
					//计费方式，客户存在件数单价，则按照件数计价
					if(custom.getUnitPrice() != null && !custom.getUnitPrice().equals(0d)){
						logistics.setChargeType(ChargeType.COUNT);
						logistics.setLogPrice(custom.getUnitPrice());
						logistics.setFreight(PublicType.setDoubleScale(custom.getUnitPrice() * order.getNumber()));
					}else {
						logistics.setChargeType(product.getChargeType());
						if(product.getChargeType() == ChargeType.VOLUME && custom.getCargoPrice() != null
								&& logistics.getLogVolume() != null){
							logistics.setLogPrice(custom.getCargoPrice());
							logistics.setFreight(PublicType.setDoubleScale(custom.getCargoPrice() * logistics.getLogVolume()));
						}
						else if(product.getChargeType() == ChargeType.WEIGHT && custom.getHeavyPrice() != null
								&& logistics.getLogWeight() != null){
							logistics.setLogPrice(custom.getHeavyPrice());
							logistics.setFreight(PublicType.setDoubleScale(custom.getHeavyPrice() * logistics.getLogWeight()));
						}
					}
					
					//配送费
					//logistics.setLogDeliverFee(custom.getDeliverFee());
					
					//本币无税金额
					logistics.setDeliverCost(order.getNoTaxPrice());
					
					//保险金额 = 发货单里的本币无税金额 x 0.23%
					if(order.getNoTaxPrice() != null && !order.getNoTaxPrice().equals(0d))
						logistics.setInsuranceFee(PublicType.setDoubleScale(order.getNoTaxPrice() * bl/100d));
					
					//运费合计 = 运费 + (配送费) + 保险金额
					if(logistics.getFreight() != null)
						logistics.setFreightTotal(logistics.getFreight());
					if(logistics.getInsuranceFee() != null){
						if(logistics.getFreightTotal() != null)
							logistics.setFreightTotal(PublicType.setDoubleScale(logistics.getFreightTotal() + logistics.getInsuranceFee()));
						else
							logistics.setFreightTotal(logistics.getInsuranceFee());
					}
					
					logistics.setCreateTime(new Date());
					logistics.setUpdateTime(logistics.getCreateTime());
					
					em.merge(logistics);
					
					count ++;
				}
			}
		}
		
		return count;
	}
	
	//根据发货订单id查询是否已存在物流费用
	private Boolean check(Long orderId){
		String hql = "from Logistics where deliverOrder.id = " + orderId;
		Query query = em.createQuery(hql);
		return (query.getResultList() != null && query.getResultList().size() > 0) ? true : false ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> logisticDetail(LogisticsVO vo) throws Exception {
		String where = " WHERE approveState = 'SPJS' ";
		String where1 = "";
		
		if(StringUtils.isNotBlank(vo.getOrderStartTime()))
			where1 += " AND DATE_FORMAT(createTime,'%Y-%m') = '" + vo.getOrderStartTime() + "' ";
		
		String sql = "SELECT a1.areaName,c.customCde,c.customName, "
				+ " temp.deliverCost,temp.freightTotal, "
				+ " temp1.otherLogisticsCost, "
				+ " temp2.monthDeliverCost,temp2.monthFreight,temp2.monthLogDeliverFee,temp2.monthInsuranceFee,temp2.monthFreightTotal, "
				+ " temp3.monthReturnGoods,temp3.monthCost,temp3.monthStorageShare,temp3.monthWagesShare,temp3.monthTransferCost,temp3.monthOtherLogisticsCost "
				+ " FROM info_custom c LEFT JOIN info_area a ON c.areaId = a.id LEFT JOIN info_area a1 ON a.parentId = a1.id "
				+ " LEFT JOIN (SELECT custom_id,SUM(deliverCost) deliverCost,SUM(freightTotal) freightTotal FROM  logistic_logistic " 
				+ where + " GROUP BY custom_id ) temp ON c.id = temp.custom_id "
				+ " LEFT JOIN (SELECT custom_id,SUM(otherLogisticsCost) otherLogisticsCost FROM  logistics_other_logistics "
				+ where + " GROUP BY custom_id  ) temp1 ON c.id = temp1.custom_id "
				+ " LEFT JOIN (SELECT custom_id,SUM(deliverCost) monthDeliverCost,SUM(freight) monthFreight,SUM(logDeliverFee) monthLogDeliverFee,SUM(insuranceFee) monthInsuranceFee,SUM(freightTotal) monthFreightTotal "
				+ " FROM  logistic_logistic " + where + where1 + " GROUP BY custom_id ) temp2 ON c.id = temp2.custom_id "
				+ " LEFT JOIN (SELECT custom_id,SUM(returnGoods) monthReturnGoods,SUM(cost) monthCost,SUM(storageShare) monthStorageShare,SUM(wagesShare) monthWagesShare,SUM(transferCost) monthTransferCost,SUM(otherLogisticsCost) monthOtherLogisticsCost "
				+ " FROM  logistics_other_logistics " + where + where1 + " GROUP BY custom_id ) temp3 ON c.id = temp3.custom_id ";
		if(vo.getCustom() != null){
			sql += " where 1 = 1 ";
			if(StringUtils.isNotBlank(vo.getCustom().getCustomCde()))
				sql += " and c.customCde = '" + vo.getCustom().getCustomCde() + "'";
			if(StringUtils.isNotBlank(vo.getCustom().getCustomName()))
				sql += " and c.customName like '%" + vo.getCustom().getCustomName() + "%' ";
			if(vo.getCustom().getArea() != null && vo.getCustom().getArea().getParentArea() != null 
					&& vo.getCustom().getArea().getParentArea().getId() != null)
				sql += " and a1.id = " + vo.getCustom().getArea().getParentArea().getId();
			if(vo.getCustom().getUser() != null && vo.getCustom().getUser().getId() != null)
				sql += " and c.userId = " + vo.getCustom().getUser().getId();
		}
		
		if(Common.checkList(vo.getAreaIds()))
			sql += " and a.id in (" + vo.getAreaIds().toString().replace("[", "").replace("]", "") + ")";
		if(Common.checkList(vo.getParentAreaIds()))
			sql += " and a1.id in ("+ vo.getParentAreaIds().toString().replace("[", "").replace("]", "") +")";
		
		sql += " ORDER BY a1.id ";
		
		Query query = em.createNativeQuery(sql);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> logisticAnalysis(LogisticsVO vo) throws Exception {
		String where = " WHERE approveState = 'SPJS' ";
		String where1 = "" , where2 = "", where3 = "";
		
		if(StringUtils.isNotBlank(vo.getOrderStartTime())){
			//当年
			where1 += " AND DATE_FORMAT(createTime,'%Y') = '" + vo.getOrderStartTime().split("-")[0] + "' ";
			//上一年
			where2 += " AND DATE_FORMAT(createTime,'%Y') = '" + (Integer.parseInt(vo.getOrderStartTime().split("-")[0]) - 1) + "' ";
			//当月
			where3 += " AND DATE_FORMAT(createTime,'%Y-%m') = '" + vo.getOrderStartTime() + "' ";
		}
		String sql = " SELECT a1.id, a1.areaName, "
				+ " SUM(t.yearDeliverCost) yearDeliverCost,SUM(t.yearFreightTotal) yearFreightTotal, SUM(t.yearOtherLogisticsCost)yearOtherLogisticsCost, "
				+ " SUM(t.lastYearDeliverCost) lastYearDeliverCost,SUM(t.lastYearFreightTotal) lastYearFreightTotal,SUM(t.lastYearOtherLogisticsCost) lastYearOtherLogisticsCost, "
				+ " sum(t.deliverCost) deliverCost, sum(t.freightTotal) freightTotal,sum(t.otherLogisticsCost)otherLogisticsCost, "
				+ " SUM(t.monthDeliverCost) monthDeliverCost, SUM(t.monthFreightTotal) monthFreightTotal,SUM(t.monthOtherLogisticsCost) monthOtherLogisticsCost "
				+ " FROM info_custom c LEFT JOIN info_area a ON c.areaId = a.id LEFT JOIN info_area a1 ON a.parentId = a1.id LEFT JOIN "
				+ " (SELECT c.id custom_id,temp.yearDeliverCost,temp.yearFreightTotal,temp1.yearOtherLogisticsCost, "
				+ " temp2.lastYearDeliverCost,temp2.lastYearFreightTotal,temp3.lastYearOtherLogisticsCost, "
				+ " temp4.deliverCost,temp4.freightTotal,temp5.otherLogisticsCost, "
				+ " temp6.monthDeliverCost,temp6.monthFreightTotal,temp7.monthOtherLogisticsCost "
				+ " FROM info_custom c  "
				+ " LEFT JOIN (SELECT custom_id ,SUM(deliverCost) yearDeliverCost,SUM(freightTotal) yearFreightTotal FROM logistic_logistic "
				+ where + where1 + " GROUP BY custom_id) temp ON temp.custom_Id = c.id "
				+ " LEFT JOIN (SELECT custom_id,SUM(otherLogisticsCost) yearOtherLogisticsCost FROM logistics_other_logistics "
				+ where + where1 + " GROUP BY custom_id) temp1 ON temp1.custom_Id = c.id "
				+ " LEFT JOIN (SELECT custom_id ,SUM(deliverCost) lastYearDeliverCost,SUM(freightTotal) lastYearFreightTotal FROM logistic_logistic "
				+ where + where2 + " GROUP BY custom_id) temp2 ON temp2.custom_Id = c.id "
				+ " LEFT JOIN (SELECT custom_id,SUM(otherLogisticsCost) lastYearOtherLogisticsCost FROM logistics_other_logistics "
				+ where + where2 + " GROUP BY custom_id) temp3 ON temp3.custom_Id = c.id " 
				+ " LEFT JOIN (SELECT custom_id ,SUM(deliverCost) deliverCost,SUM(freightTotal) freightTotal FROM logistic_logistic "
				+ where + " GROUP BY custom_id) temp4 ON temp4.custom_Id = c.id "
				+ " LEFT JOIN (SELECT custom_id,SUM(otherLogisticsCost) otherLogisticsCost FROM logistics_other_logistics "
				+ where + " GROUP BY custom_id) temp5 ON temp5.custom_Id = c.id "
				+ " LEFT JOIN (SELECT custom_id ,SUM(deliverCost) monthDeliverCost,SUM(freightTotal) monthFreightTotal FROM logistic_logistic "
				+ where + where3 + " GROUP BY custom_id) temp6 ON temp6.custom_Id = c.id "
				+ " LEFT JOIN (SELECT custom_id,SUM(otherLogisticsCost) monthOtherLogisticsCost FROM logistics_other_logistics "
				+ where + where3 + " GROUP BY custom_id) temp7 ON temp7.custom_Id = c.id) t ON c.id = t.custom_id ";
		
		if(vo.getCustom() != null && vo.getCustom().getArea() != null 
				&& vo.getCustom().getArea().getParentArea() != null && Common.checkLong(vo.getCustom().getArea().getParentArea().getId()))
			sql += " where a1.id = " + vo.getCustom().getArea().getParentArea().getId();
		if(vo.getCustom() != null && vo.getCustom().getUser() != null && vo.getCustom().getUser().getId() != null)
			sql += " and c.userId = " + vo.getCustom().getUser().getId();
		
		if(Common.checkList(vo.getAreaIds()))
			sql += " and a.id in (" + vo.getAreaIds().toString().replace("[", "").replace("]", "") + ")";
		if(Common.checkList(vo.getParentAreaIds()))
			sql += " and a1.id in ("+ vo.getParentAreaIds().toString().replace("[", "").replace("]", "") +")";
		
		sql += " GROUP BY a1.id ";
		Query query = em.createNativeQuery(sql);
				
		return query.getResultList();
	}

	@Override
	public void exportDetail(String[] heads, Object[] obj, Map<String, String> map)
			throws Exception {
		String[] header = Common.getExportHeader("LOGDETAIL");
		for(String key : heads){
			int i = 0;
			if(key.equals(header[i++])){
				map.put(key, obj[0].toString());//客户地区
			}else if(key.equals(header[i++])){
				map.put(key, obj[1].toString());//客户编码
			}else if(key.equals(header[i++])){
				map.put(key, obj[2].toString());//客户名称
			}else if(key.equals(header[i++])){
				//累计含税金额
				if(obj[3] != null){
					Double d1 = PublicType.setDoubleScale(Double.parseDouble(obj[3].toString()));
					map.put(key,d1.toString());
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				//累计物流费合计
				Double d4 = obj[4] != null ? Double.parseDouble(obj[4].toString()) : 0d;
				Double d5 = obj[5] != null ? Double.parseDouble(obj[5].toString()) : 0d;
				Double d45 = d4+ d5;
				map.put(key, PublicType.setDoubleScale(d45).toString());
			}else if(key.equals(header[i++])){
				//费用比
				Double d3 = obj[3] != null ? Double.parseDouble(obj[3].toString()) : 0d;
				Double d4 = obj[4] != null ? Double.parseDouble(obj[4].toString()) : 0d;
				Double d5 = obj[5] != null ? Double.parseDouble(obj[5].toString()) : 0d;
				Double d45 = d4 + d5;
				if(d3 != 0 && d45 != null){
					Double dd3 = d45/d3 * 100;
					map.put(key, PublicType.setDoubleScale(dd3).toString() + "%");
				}else{
					map.put(key, "0%");
				}
			}else if(key.equals(header[i++])){
				//当月本币金额
				if(obj[6] != null){
					Double d6 = PublicType.setDoubleScale(Double.parseDouble(obj[6].toString()));
					map.put(key, d6.toString());
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				//当月运费
				if(obj[7] != null){
					Double d7 = PublicType.setDoubleScale(Double.parseDouble(obj[7].toString()));
					map.put(key, d7.toString());
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				//当月配送费
				if(obj[8] != null){
					Double d8 = PublicType.setDoubleScale(Double.parseDouble(obj[8].toString()));
					map.put(key, d8.toString());
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				//当月保险金额
				if(obj[9] != null){
					Double d9 = PublicType.setDoubleScale(Double.parseDouble(obj[9].toString()));
					map.put(key, d9.toString());
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				//当月运费合计
				if(obj[10] != null){
					Double d10 = PublicType.setDoubleScale(Double.parseDouble(obj[10].toString()));
					map.put(key, d10.toString());
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				//当月退货费
				if(obj[11] != null){
					Double d11 = PublicType.setDoubleScale(Double.parseDouble(obj[11].toString()));
					map.put(key, d11.toString());
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				//当月促销道具费
				if(obj[12] != null){
					Double d12 = PublicType.setDoubleScale(Double.parseDouble(obj[12].toString()));
					map.put(key, d12.toString());
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				//当月仓储费
				if(obj[13] != null){
					Double d13 = PublicType.setDoubleScale(Double.parseDouble(obj[13].toString()));
					map.put(key, d13.toString());
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				//当月工资
				if(obj[14] != null){
					Double d14 = PublicType.setDoubleScale(Double.parseDouble(obj[14].toString()));
					map.put(key, d14.toString());
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				//当月调拨费
				if(obj[15] != null){
					Double d15 = PublicType.setDoubleScale(Double.parseDouble(obj[15].toString()));
					map.put(key, d15.toString());
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				//当月运费总计
				Double d16 = obj[16] != null ? Double.parseDouble(obj[16].toString()) : 0d;
				Double d10 = obj[10] != null ? Double.parseDouble(obj[10].toString()) : 0d;
				Double dd16 = d16 + d10;
				map.put(key, PublicType.setDoubleScale(dd16).toString());
			}			
		}
	}

	@Override
	public void exportAnalysis(String[] heads, Object[] obj,
			Map<String, String> map) throws Exception {
		String[] headers = Common.getExportHeader("LOGANALYSIS");
		for(String key : heads){
			int i = 0;
			
			if(key.equals(headers[i++])){//所属大区
				map.put(key, obj[1].toString());
			}else if(key.equals(headers[i++])){
				//当年费用比
				Double d2 = obj[2] != null ? Double.parseDouble(obj[2].toString()) : 0d;
				Double d3 = obj[3] != null ? Double.parseDouble(obj[3].toString()) : 0d;
				Double d4 = obj[4] != null ? Double.parseDouble(obj[4].toString()) : 0d;
				Double d34 = d3 + d4;
				if(d2 != 0 && d34 != null){
					Double result1 = d34/d2 * 100;
					map.put(key, PublicType.setDoubleScale(result1).toString() + "%");
				}else{
					map.put(key, "0%");
				}
			}else if(key.equals(headers[i++])){
				//去年费用比
				Double d5 = obj[5] != null ? Double.parseDouble(obj[5].toString()) : 0d;
				Double d6 = obj[6] != null ? Double.parseDouble(obj[6].toString()) : 0d;
				Double d7 = obj[7] != null ? Double.parseDouble(obj[7].toString()) : 0d;
				Double d67 = d6 + d7;
				if(d5 != 0 && d67 != null){
					Double result2 = d67/d5 * 100;
					map.put(key, PublicType.setDoubleScale(result2).toString() + "%");
				}else{
					map.put(key, "0%");
				}
			}else if(key.equals(headers[i++])){
				//累计本币金额
				if(obj[8] != null){
					Double d8 = Double.parseDouble(obj[8].toString());
					map.put(key, PublicType.setDoubleScale(d8).toString());
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(headers[i++])){
				//累计物流费用
				Double d9 = obj[9] != null ? Double.parseDouble(obj[9].toString()) : 0d;
				Double d10 = obj[10] != null ? Double.parseDouble(obj[10].toString()) : 0d;
				Double d910 = d9 + d10;
				map.put(key, PublicType.setDoubleScale(d910).toString());
			}else if(key.equals(headers[i++])){
				//累计费用比
				Double d8 = obj[8] != null ? Double.parseDouble(obj[8].toString()) : 0d;
				Double d9 = obj[9] != null ? Double.parseDouble(obj[9].toString()) : 0d;
				Double d10 = obj[10] != null ? Double.parseDouble(obj[10].toString()) : 0d;
				Double d910 = d9 + d10;
				if(d8 != 0 && d910 !=null){
					Double result3 = d910/d8 * 100;
					map.put(key, PublicType.setDoubleScale(result3).toString() + "%");
				}else{
					map.put(key, "0%");
				}
			}else if(key.equals(headers[i++])){
				//当月本币金额
				if(obj[11] != null){
					Double d11 = Double.parseDouble(obj[11].toString());
					map.put(key, PublicType.setDoubleScale(d11).toString());
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(headers[i++])){
				//当月物流费用
				Double d12 = obj[12] != null ? Double.parseDouble(obj[12].toString()) : 0d;
				Double d13 = obj[13] != null ? Double.parseDouble(obj[13].toString()) : 0d;
				Double d1213 = d12 + d13;
				map.put(key, PublicType.setDoubleScale(d1213).toString());
			}else if(key.equals(headers[i++])){
				//累计费用比
				Double d11 = obj[11] != null ? Double.parseDouble(obj[11].toString()) : 0d;
				Double d12 = obj[12] != null ? Double.parseDouble(obj[12].toString()) : 0d;
				Double d13 = obj[13] != null ? Double.parseDouble(obj[13].toString()) : 0d;
				Double d1213 = d12 + d13;
				if(d11 != 0 && d1213 != null){
					Double result4 = d1213/d11 * 100;
					map.put(key, PublicType.setDoubleScale(result4).toString()+ "%");
				}else{
					map.put(key, "0%");
				}
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initLogDeliverFee(Date orderStartTime, Date orderEndTime)
			throws Exception {
		String sql,sql1 ;
				
		Date date = orderStartTime;
		while(date.before(orderEndTime) || date == orderEndTime){
			sql = "SELECT l.custom_Id,SUM(l.logDeliverFee) FROM logistic_logistic l "
					+ " LEFT JOIN order_deliver d ON l.delever_order_id = d.id "
					+ " WHERE d.deliverDate = '" + DateFormat.date2Str(date, 2) + "' "
					+ " group by l.custom_id ";
			Query query = em.createNativeQuery(sql);
			List<Object[]> list = query.getResultList();
			for(Object[] o : list){
				if(o[1] == null && o[0] != null){
					
					sql1 = "SELECT l.id FROM logistic_logistic l "
							+ " LEFT JOIN order_deliver d ON l.delever_order_id = d.id "
							+ " WHERE d.deliverDate = '" + DateFormat.date2Str(date, 2) + "' AND l.custom_id = " + o[0].toString()
							+ " order by l.id limit 1 "; 
					
					query = em.createNativeQuery(sql1);
					List<Object> list1 = query.getResultList();
					if(Common.checkList(list1)){
						BigInteger a = (BigInteger)list1.get(0);
						updateLogDeliverFee(Long.parseLong(o[0].toString()), a.longValue());
					}
				}
			}
			date = DateFormat.getNextDay(date);
		}
		
	}
	
	//划分配送费（一个客户一天一次配送费用）
	private int updateLogDeliverFee(Long customId, Long logisticId){
		String sql = "UPDATE logistic_logistic SET logDeliverFee = (SELECT deliverFee FROM info_custom WHERE id = "
				+ customId + "),freightTotal = freightTotal + logDeliverFee WHERE id = " + logisticId;
		Query query = em.createNativeQuery(sql);
		return query.executeUpdate();
	}

	@Override
	public void exportLogistic(String[] heads, Logistics logistics,
			Map<String, String> map) throws Exception {
		String[] header=Common.getExportHeader("LOGISTICS");
		for(String key : heads){
			int i=0;
			if(key.equals(header[i++])){             
				map.put(key, logistics.getDeliverOrder().getCdlCode());//发货单号
			}else if(key.equals(header[i++])){
				map.put(key, logistics.getDeliverOrder().getDeliverDate() != null
						? DateFormat.date2Str(logistics.getDeliverOrder().getDeliverDate(),2) : "");  //发货日期
			}else if(key.equals(header[i++])){
				map.put(key, logistics.getCustom().getCustomCde()); // 客户编码
			}else if(key.equals(header[i++])){
				map.put(key, logistics.getCustom().getCustomName()); // 客户名称
			}else if(key.equals(header[i++])){
				map.put(key, logistics.getProduct().getProductCde()); // 存货代码
			}else if(key.equals(header[i++])){
				map.put(key, logistics.getProduct().getStockCde()); // 存货编码
			}else if(key.equals(header[i++])){
				map.put(key, logistics.getProduct().getProductName()); // 存货名称
			}else if(key.equals(header[i++])){
				map.put(key, logistics.getProduct().getStandard()); // 规格型号
			}else if(key.equals(header[i++])){
				if(logistics.getDeliverOrder().getCount() != null)
					map.put(key, logistics.getDeliverOrder().getCount().toString()); // 数量/件数
			}else if(key.equals(header[i++])){
				if(logistics.getProduct().getBoxWeight() != null)
					map.put(key, logistics.getProduct().getBoxWeight().toString()); // 货物单位重量
			}else if(key.equals(header[i++])){
				if(logistics.getLogWeight() != null)
					map.put(key, logistics.getLogWeight().toString()); // 货物重量
			}else if(key.equals(header[i++])){
				if(logistics.getProduct().getVolume() != null)
					map.put(key, logistics.getProduct().getVolume().toString()); // 货物单位体积（立方米）
			}else if(key.equals(header[i++])){
				if(logistics.getLogVolume() != null)
					map.put(key, logistics.getLogVolume().toString()); // 货物体积（立方米）
			}else if(key.equals(header[i++])){
				if(logistics.getChargeType() != null)
					map.put(key, logistics.getChargeType().getText()); // 计价方式
			}else if(key.equals(header[i++])){
				if(logistics.getLogPrice() != null)
					map.put(key, logistics.getLogPrice().toString()); // 单价
			}else if(key.equals(header[i++])){
				if(logistics.getFreight() != null)
					map.put(key, logistics.getFreight().toString()); // 运费
			}else if(key.equals(header[i++])){
				if(logistics.getLogDeliverFee() != null)
					map.put(key, logistics.getLogDeliverFee().toString()); //配送费
			}else if(key.equals(header[i++])){
				if(logistics.getDeliverCost() != null)
					map.put(key, logistics.getDeliverCost().toString()); // 本币金额
			}else if(key.equals(header[i++])){
				if(logistics.getInsuranceFee() != null)
					map.put(key, logistics.getInsuranceFee().toString()); //保险金额
			}else if(key.equals(header[i++])){
				if(logistics.getCustom().getUser() != null)
					map.put(key, logistics.getCustom().getUser().getUserName()); // 业务员
			}else if(key.equals(header[i++])){
				if(logistics.getFreightTotal() != null)
					map.put(key, logistics.getFreightTotal().toString()); //运费合计
			}
		}
		
	}

	@Override
	public int deleteLogistic(LogisticsVO vo) throws Exception {
		String sql = "delete from logistic_logistic where 1 = 1 ";
		if (StringUtils.isNotBlank(vo.getOrderStartTime()))
			sql += " and delever_order_id in (select id from order_deliver where deliverDate >= '" + vo.getOrderStartTime() + "') ";
		if (StringUtils.isNotBlank(vo.getOrderEndTime()))
			sql += " and delever_order_id in (select id from order_deliver where deliverDate <= '" + vo.getOrderEndTime() + "') ";
		if (vo.getApproveState() != null)
			sql += " and approveState = '" + vo.getApproveState().getName() + "'";
		if (vo.getDeliverOrder() != null && StringUtils.isNotBlank(vo.getDeliverOrder().getCdlCode()))
			sql += " and delever_order_id in (select id from order_deliver where cdlCode ='" + vo.getDeliverOrder().getCdlCode() + "' )";
		if (vo.getCustom() != null && StringUtils.isNotBlank(vo.getCustom().getCustomName()))
			sql += " and custom_id in (select id from info_custom where customName ='" + vo.getCustom().getCustomName() + "' )";
		if (vo.getProduct() != null && StringUtils.isNotBlank(vo.getProduct().getProductName()))
			sql += " and product_id in (select id from info_product where productName ='" + vo.getProduct().getProductName() + "' )";
		if (vo.getProduct() != null && StringUtils.isNotBlank(vo.getProduct().getStockCde()))
			sql += " and product_id in (select id from info_product where stockCde ='" + vo.getProduct().getStockCde() + "' )";

		Query query = em.createNativeQuery(sql);
		return query.executeUpdate();
	}


}
