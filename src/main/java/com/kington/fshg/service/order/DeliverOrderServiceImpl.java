package com.kington.fshg.service.order;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.DateFormat;
import com.kington.fshg.common.PublicType;
import com.kington.fshg.common.PublicType.IsType;
import com.kington.fshg.model.order.DeliverOrder;
import com.kington.fshg.model.order.DeliverOrderVO;
import com.kington.fshg.service.BaseServiceImpl;

public class DeliverOrderServiceImpl extends BaseServiceImpl<DeliverOrder, DeliverOrderVO> implements
		DeliverOrderService {
	private static final long serialVersionUID = -1586254885047027636L;

	@Override
	protected String getQueryStr(DeliverOrderVO vo) throws Exception {
		StringBuffer sql = new StringBuffer();
		if(vo.getUserId() != null)
			sql.append(" and o.customCde in (select customCde from Custom where user.id = :userId)");
		if(vo.getArea() != null){
			if(Common.checkLong(vo.getArea().getId()))
				sql.append(" and o.customCde in (select customCde from Custom where area.id = :areaId) ");
			else if(vo.getArea().getParentArea() != null 
						&& Common.checkLong(vo.getArea().getParentArea().getId()))
					sql.append(" and o.customCde in (select customCde from Custom where area.parentArea.id = :parentAreaId) ");
		}
		if(StringUtils.isNotBlank(vo.getCdlCode()))
			sql.append(" and o.cdlCode like :cdeCode ");
		if(StringUtils.isNotBlank(vo.getCustomName()))
			sql.append(" and o.customName like :customName ");
		if(StringUtils.isNotBlank(vo.getCustomCde()))
			sql.append(" and o.customCde = :customCde ");
		if(StringUtils.isNotBlank(vo.getStockName()))
			sql.append(" and o.stockName like :stockName ");
		if(StringUtils.isNotBlank(vo.getProductCde()))
			sql.append(" and o.productCde = :productCde ");
		if(StringUtils.isNotBlank(vo.getStockCde()))
			sql.append(" and o.stockCde = :stockCde ");
		if(StringUtils.isNotBlank(vo.getCsoCode()))
			sql.append(" and o.csoCode = :csoCode ");
		
		if(StringUtils.endsWith(vo.getTimeType(), "1")){
			if(StringUtils.isNotBlank(vo.getBeginTime()))
				sql.append(" and o.orderDate >= :beginTime ");
			if(StringUtils.isNotBlank(vo.getEndTime()))
				sql.append(" and o.orderDate < :endTime ");
		}else if(StringUtils.endsWith(vo.getTimeType(), "2")){
			if(StringUtils.isNotBlank(vo.getBeginTime()))
				sql.append(" and o.deliverDate >= :beginTime ");
			if(StringUtils.isNotBlank(vo.getEndTime()))
				sql.append(" and o.deliverDate < :endTime ");
		}else if(StringUtils.endsWith(vo.getTimeType(), "3")){
			if(StringUtils.isNotBlank(vo.getBeginTime()))
				sql.append(" and o.originalDate >= :beginTime ");
			if(StringUtils.isNotBlank(vo.getEndTime()))
				sql.append(" and o.originalDate < :endTime ");
		}
		
		if(vo.getDeliverStartTime() != null)
			sql.append(" and o.deliverDate >= :deliverStartTime ");
		if(vo.getDeliverEndTime() != null)
			sql.append(" and o.deliverDate < :deliverEndTime ");
		
		if(vo.getIsRebate() != null)
			sql.append(" and o.isRebate = :isRebate ");
		
		if(StringUtils.isNotBlank(vo.getSaleTypeCode()))
			sql.append(" and o.saleTypeCode = :saleTypeCode ");
		
		if(Common.checkList(vo.getAreaIds()))
			sql.append(" and o.customCde in (select customCde from Custom where area.id in (:areaIds))");
		if(Common.checkList(vo.getParentAreaIds()))
			sql.append(" and o.customCde in (select customCde from Custom where area.parentArea.id in (:parentAreaIds))");
		
		return sql.toString();
	}

	@Override
	protected void setQueryParm(Query query, DeliverOrderVO vo) throws Exception {
		if(vo.getUserId() != null)
			query.setParameter("userId", vo.getUserId());
		
		if(vo.getArea() != null){
			if(Common.checkLong(vo.getArea().getId()))
				query.setParameter("areaId", vo.getArea().getId());
			else if(vo.getArea().getParentArea() != null 
						&& Common.checkLong(vo.getArea().getParentArea().getId()))
				query.setParameter("parentAreaId", vo.getArea().getParentArea().getId());
		}
		if(StringUtils.isNotBlank(vo.getCdlCode()))
			query.setParameter("cdeCode", Common.SYMBOL_PERCENT + vo.getCdlCode() + Common.SYMBOL_PERCENT);
		if(StringUtils.isNotBlank(vo.getCustomName()))
			query.setParameter("customName", Common.SYMBOL_PERCENT + vo.getCustomName() + Common.SYMBOL_PERCENT);
		if(StringUtils.isNotBlank(vo.getCustomCde()))
			query.setParameter("customCde", vo.getCustomCde());
		if(StringUtils.isNotBlank(vo.getStockName()))
			query.setParameter("stockName", Common.SYMBOL_PERCENT + vo.getStockName() + Common.SYMBOL_PERCENT);
		if(StringUtils.isNotBlank(vo.getProductCde()))
			query.setParameter("productCde", vo.getProductCde());
		if(StringUtils.isNotBlank(vo.getStockCde()))
			query.setParameter("stockCde", vo.getStockCde());
		if(StringUtils.isNotBlank(vo.getCsoCode()))
			query.setParameter("csoCode", vo.getCsoCode());
		if(StringUtils.isNotBlank(vo.getBeginTime()))
			query.setParameter("beginTime", DateFormat.str2Date(vo.getBeginTime(), 2));
		if(StringUtils.isNotBlank(vo.getEndTime()))
			query.setParameter("endTime", DateFormat.getAfterDayFirst(DateFormat.str2Date(vo.getEndTime(), 2)));
		if(vo.getIsRebate() != null){
			query.setParameter("isRebate",vo.getIsRebate());
		}
			
		
		if(vo.getDeliverStartTime() != null)
			query.setParameter("deliverStartTime", vo.getDeliverStartTime());
		if(vo.getDeliverEndTime() != null)
			query.setParameter("deliverEndTime", vo.getDeliverEndTime());
		
		if(StringUtils.isNotBlank(vo.getSaleTypeCode()))
			query.setParameter("saleTypeCode", vo.getSaleTypeCode());
		
		if(Common.checkList(vo.getAreaIds()))
			query.setParameter("areaIds", vo.getAreaIds());
		if(Common.checkList(vo.getParentAreaIds()))
			query.setParameter("parentAreaIds", vo.getParentAreaIds());
		
	}

	@Override
	protected void switchVO2PO(DeliverOrderVO vo, DeliverOrder po)
			throws Exception {
		if(po == null)
			po = new DeliverOrder();
		if(vo.getReceiveNum() != null)
			po.setReceiveNum(vo.getReceiveNum());
		if(vo.getTotal() != null)
			po.setTotal(vo.getTotal());
	}

	@Override
	public int impDeliveOrderFromU8(DeliverOrderVO vo){
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		
		int count = 0;
		try {
			String sql = "select aa.autoid as 发货子表ID,bb.cdlcode as 发货单号,bb.ddate as 发货日期,a.autoid as 销售订单子表ID,"
					+ "b.csocode as 销售订单号,b.ddate as 订单日期,bb.ccuscode as 客户编码,c.ccusname as 客户名称,aa.cinvcode as 存货编号,"
					+ "d.cinvaddcode as 存货代码,d.cinvname as 存货名称,d.cinvstd as 存货规格,aa.iquantity as 数量,aa.iNatSum/aa.iquantity as 本币含税单价,"
					+ "aa.iNatSum as 本币价税合计  ,s.cstcode as 销售类型编码, s.cstname as 销售类型名称,aa.inum as 件数,aa.inatmoney as 本币无税金额,aa.iquotedprice as 报价,"
					+ "aa.kl as 扣率, aa.kl2 as 二次扣率  , bb.cDefine6 as 单据原始日期 ,bb.cDefine11 as 卖场订单号 from DispatchLists aa left join DispatchList bb on bb.dlid=aa.dlid left join so_sodetails a on a.isosid=aa.iSOsID "
					+ "left join so_somain b on b.id=a.id left join customer c on c.ccuscode=bb.ccuscode left join inventory d on d.cinvcode=aa.cinvcode left join SaleType s on bb.cstcode = s.cstcode "
					+ " where 1 = 1 ";
			
			if(StringUtils.endsWith(vo.getTimeType(), "1")){
				if(StringUtils.isNotBlank(vo.getBeginTime()))
					sql += " and b.ddate >= '" + vo.getBeginTime() + "' "; 
				if(StringUtils.isNotBlank(vo.getEndTime()))
					sql += " and b.ddate <= '" + vo.getEndTime() + "' ";
			}else if(StringUtils.endsWith(vo.getTimeType(), "2")){
				if(StringUtils.isNotBlank(vo.getBeginTime()))
					sql += " and bb.ddate >= '" + vo.getBeginTime() + "' "; 
				if(StringUtils.isNotBlank(vo.getEndTime()))
					sql += " and bb.ddate <= '" + vo.getEndTime() + "' ";
			}else if(StringUtils.endsWith(vo.getTimeType(), "3")){
				if(StringUtils.isNotBlank(vo.getBeginTime()))
					sql += " and bb.cDefine6 >= '" + vo.getBeginTime() + "' "; 
				if(StringUtils.isNotBlank(vo.getEndTime()))
					sql += " and bb.cDefine6 <= '" + vo.getEndTime() + "' ";
			}
			
			if(StringUtils.isNotBlank(vo.getCdlCode()))
				sql += " and bb.cdlcode = '" + vo.getCdlCode() + "' ";
			if(StringUtils.isNotBlank(vo.getCustomName()))
				sql += " and c.ccusname like '%" + vo.getCustomName() + "' ";
			if(StringUtils.isNotBlank(vo.getStockName()))
				sql += " and d.cinvname like '%" + vo.getStockName() + "' ";
			if(StringUtils.isNotBlank(vo.getSaleTypeCode()))
				sql += " and s.cstcode = '" + vo.getSaleTypeCode() + "' ";
			
			sql += "order by bb.ddate,bb.cdlcode,aa.cinvcode";
			conn = PublicType.getConn();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
		
			
			while(result.next()){
				String autoId = result.getString(1);
				String customCde = result.getString(7);
				String stockCde = result.getString(9);
				
				if(StringUtils.isBlank(autoId) || StringUtils.isBlank(customCde) 
						|| StringUtils.isBlank(stockCde)){
					continue;
				}
				DeliverOrder po = this.getByAutoId(autoId);
				if(po == null){
					po = new DeliverOrder();
					po.setAutoId(autoId);
				}
				po.setCdlCode(result.getString(2));
				if(StringUtils.isNotBlank(result.getString(3)))
					po.setDeliverDate(DateFormat.str2Date(result.getString(3), 9));
				po.setSaleOrderId(result.getString(4));
				po.setCsoCode(result.getString(5));
				if(StringUtils.isNotBlank(result.getString(6)))
					po.setOrderDate(DateFormat.str2Date(result.getString(6), 9));
				po.setCustomCde(customCde);
				po.setCustomName(result.getString(8));
				po.setStockCde(stockCde);
				po.setProductCde(result.getString(10));
				po.setStockName(result.getString(11));
				po.setStandard(result.getString(12));
				if(StringUtils.isNotBlank(result.getString(13))){
					po.setCount(PublicType.setDoubleScale(Double.parseDouble(result.getString(13))));
					if(po.getReceiveNum() == null)
						po.setReceiveNum(po.getCount());//实收数
				}
				if(StringUtils.isNotBlank(result.getString(14)))
					po.setLocalPrice(PublicType.setDoubleScale4(Double.parseDouble(result.getString(14))));
				
				//System.out.println(result.getString(14));
				if(StringUtils.isNotBlank(result.getString(15))){
					po.setCountPrice(PublicType.setDoubleScale(Double.parseDouble(result.getString(15))));
					if(po.getTotal() == null)
						po.setTotal(po.getCountPrice());
				}
				
				po.setSaleTypeCode(result.getString(16));
				po.setSaleType(result.getString(17));
				
				if(StringUtils.isNotBlank(result.getString(18)))
					po.setNumber(PublicType.setDoubleScale(Double.parseDouble(result.getString(18))));
				
				if(StringUtils.isNotBlank(result.getString(19)))
					po.setNoTaxPrice(PublicType.setDoubleScale(Double.parseDouble(result.getString(19))));
				
				if(StringUtils.isNotBlank(result.getString(20)))
					po.setiQuotedPrice(PublicType.setDoubleScale(Double.parseDouble(result.getString(20))));
				
				if(StringUtils.isNotBlank(result.getString(21)))
					po.setKl(PublicType.setDoubleScale(Double.parseDouble(result.getString(21))));
				
				if(StringUtils.isNotBlank(result.getString(22)))
					po.setKl2(PublicType.setDoubleScale(Double.parseDouble(result.getString(22))));
				
				if(StringUtils.isNotBlank(result.getString(23)))
					po.setOriginalDate(DateFormat.str2Date(result.getString(23), 9));
				
				po.setStoreCde(result.getString(24));
				
				//票折（公式=报价*数量*（100-扣率2）/100）
				po.setTicketDiscount(PublicType.setDoubleScale(po.getiQuotedPrice()*po.getCount()*(100-po.getKl2())/100));
				
				//费用折（公式=报价*数量*扣率2*（100-扣率1）/100）
				po.setCostDiscount(PublicType.setDoubleScale(po.getiQuotedPrice()*po.getCount()*po.getKl2()/100*(100-po.getKl())/100));
				
				if(po.getCreateTime() == null){
					po.setCreateTime(new Date());
					po.setUpdateTime(po.getCreateTime());
				}else{
					po.setUpdateTime(new Date());
				}
				em.merge(po);
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(result != null) try{ result.close();} catch(Exception e){}
			if(stmt != null) try{ stmt.close();} catch(Exception e){} 
			if(conn != null) try{ conn.close();} catch(Exception e){} 
		}
		return count;
	}

	@Override
	@SuppressWarnings("unchecked")
	public DeliverOrder getByAutoId(String autoId) throws Exception {
		String sql= "from DeliverOrder where autoId = "+ autoId;
		Query query = em.createQuery(sql);
		List<DeliverOrder> list = query.getResultList();
		if(Common.checkList(list)){
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean updateDeliver(Long id, String isRebate) throws Exception {
		String hql="update order_deliver set isRebate=:isRebate where id = :id ";
		Query query = em.createNativeQuery(hql);
		query.setParameter("isRebate", isRebate);
		query.setParameter("id", id);
		return query.executeUpdate() > 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Double getOrderCount(String customCde, Date date, Boolean isRebate) {
		String sql = "select sum(total) from order_deliver where customCde = '" + customCde + "'"
		+ " and orderDate >= '" + DateFormat.date2Str(DateFormat.getYearFirstDay(date), 9) 
		+ "' and orderDate < '" + DateFormat.date2Str(DateFormat.getYearMonthFirst(date, 1),9) + "'";
		
		if(isRebate)
			sql += " and isRebate = 'Y' ";
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> countByVo(DeliverOrderVO vo) {
		String sql = " select sum(receiveNum), sum(total), sum(noTaxPrice) from order_deliver where 1 = 1 ";
		if(StringUtils.endsWith(vo.getTimeType(), "1")){
			if(StringUtils.isNotBlank(vo.getBeginTime()))
				sql += " and orderDate >= '" + vo.getBeginTime() + "' "; 
			if(StringUtils.isNotBlank(vo.getEndTime()))
				sql += " and orderDate <= '" + vo.getEndTime() + "' ";
		}else if(StringUtils.endsWith(vo.getTimeType(), "2")){
			if(StringUtils.isNotBlank(vo.getBeginTime()))
				sql += " and deliverDate >= '" + vo.getBeginTime() + "' "; 
			if(StringUtils.isNotBlank(vo.getEndTime()))
				sql += " and deliverDate <= '" + vo.getEndTime() + "' ";
		}else if(StringUtils.endsWith(vo.getTimeType(), "3")){
			if(StringUtils.isNotBlank(vo.getBeginTime()))
				sql += " and originalDate >= '" + vo.getBeginTime() + "' "; 
			if(StringUtils.isNotBlank(vo.getEndTime()))
				sql += " and originalDate <= '" + vo.getEndTime() + "' ";
		}
		
		if(StringUtils.isNotBlank(vo.getCdlCode()))
			sql += " and cdlCode = '" + vo.getCdlCode() + "' ";
		if(StringUtils.isNotBlank(vo.getCustomName()))
			sql += " and customName like '%" + vo.getCustomName() + "%' ";
		if(StringUtils.isNotBlank(vo.getStockName()))
			sql += " and stockName like '%" + vo.getStockName() + "%' ";
		
		if(vo.getIsRebate() != null)
			sql += " and isRebate = '" + vo.getIsRebate() + "'";
		
		if(StringUtils.isNotBlank(vo.getSaleTypeCode()))
			sql += " and saleTypeCode = '" + vo.getSaleTypeCode() + "'";
	
		if(vo.getUserId() != null)
			sql += " and customCde in (select customCde from info_custom where userId =" + vo.getUserId() + ")";
		if(Common.checkList(vo.getAreaIds()))
			sql += " and customCde in (select customCde from info_custom where areaId in (" + vo.getAreaIds().toString().replace("[", "").replace("]", "") + "))";
		if(Common.checkList(vo.getParentAreaIds()))
			sql += " and customCde in (select customCde from info_custom c left join info_area a on c.areaId = a.id where a.parentId in ("+ vo.getParentAreaIds().toString().replace("[", "").replace("]", "") +"))";

		
		Query query = em.createNativeQuery(sql);
		return query.getResultList();
	}

	@Override
	public List<DeliverOrderVO> getSaleTypeList() throws Exception {
		String sql = "select cstcode, cstname from SaleType ";
		
		Connection conn = PublicType.getConn();
		Statement stmt = conn.createStatement();
		ResultSet result = stmt.executeQuery(sql);
		
		List<DeliverOrderVO> list = new ArrayList<DeliverOrderVO>();
		while(result.next()){
			DeliverOrderVO vo = new DeliverOrderVO();
			vo.setSaleTypeCode(result.getString(1));
			vo.setSaleType(result.getString(2));
			list.add(vo);
		}
		
		return list;
	}

	/*@Override
	public List<Object[]> deliverOrder(DeliverOrderVO vo) throws Exception {
		String sql="select cdlCode,orderDate,deliverDate,customName,stockName,receiveNum,localPrice,countPrice,isRebate from order_deliver o where 1=1 ";
		sql+=getQueryStr(vo)+" order by orderDate ";		
		Query query=em.createNativeQuery(sql);		
		setQueryParm(query, vo);
		if(vo.getIsRebate()!=null){
			query.setParameter("isRebate",vo.getIsRebate().toString());
		}
		//System.out.println("~~~~~~~~~~"+sql+"------------"+vo.getIsRebate().toString());
		return query.getResultList();
	}*/

	@Override
	public void exportDeliverOrder(String[] heads, DeliverOrder d, Map<String, String> map) throws Exception {
		String[] header=Common.getExportHeader("DELIVERORDER");
		for(String key : heads){
			int i=0;
			if(key.equals(header[i++])){   
				map.put(key, d.getCdlCode());					//发货单号
			}else if(key.equals(header[i++])){
				map.put(key,DateFormat.date2Str(d.getOrderDate(), 2));  //订单日期
			}else if(key.equals(header[i++])){
				map.put(key,DateFormat.date2Str(d.getDeliverDate(), 2));  //发货日期
			}else if(key.equals(header[i++])){
				map.put(key,d.getCustomName());						//客户名称
			}else if(key.equals(header[i++])){
				map.put(key,d.getStockName());						//存货名称
			}else if(key.equals(header[i++])){
				if(d.getReceiveNum()!= null){   								//实收数
					map.put(key, d.getReceiveNum().toString());
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				if(d.getLocalPrice() != null){   								//"本币含税单价"
					map.put(key,d.getLocalPrice().toString());
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				if(d.getCountPrice()!= null){   								//"本币价税合计"
					map.put(key, d.getCountPrice().toString());
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){					   //是否返利
				if(d.getIsRebate()!=null){
					map.put(key, d.getIsRebate().getText());
				}					
			}else if(key.equals(header[i++])){
				map.put(key,d.getCsoCode());						//销售订单号
			}else if(key.equals(header[i++])){
				map.put(key,d.getCustomCde());						//客户编码
			}else if(key.equals(header[i++])){
				map.put(key,d.getStockCde());						//存货编码
			}else if(key.equals(header[i++])){
				map.put(key,d.getProductCde());						//存货代码
			}else if(key.equals(header[i++])){
				map.put(key,d.getStandard());						//存货规格
			}else if(key.equals(header[i++])){					   
				if(d.getCount()!=null){
					map.put(key, d.getCount().toString());			//数量
				}					
			}else if(key.equals(header[i++])){					   
				if(d.getNumber()!=null){
					map.put(key, d.getNumber().toString());			//件数
				}else{
					map.put(key,"0.0");
				}
			}else if(key.equals(header[i++])){					   				
					map.put(key, d.getSaleType());			//销售类型					
			}else if(key.equals(header[i++])){					   
				if(d.getiQuotedPrice()!=null){
					map.put(key, d.getiQuotedPrice().toString());			//报价
				}					
			}else if(key.equals(header[i++])){					   
				if(d.getKl()!=null){
					map.put(key, d.getKl().toString());			//扣率
				}					
			}else if(key.equals(header[i++])){					   
				if(d.getKl2()!=null){
					map.put(key, d.getKl2().toString());			//二次扣率
				}					
			}else if(key.equals(header[i++])){					   
				if(d.getTicketDiscount()!=null){
					//map.put(key, String.valueOf(d.getiQuotedPrice()*d.getCount()*(100-d.getKl2())/100));			//票折
					map.put(key, d.getTicketDiscount().toString());
				}					
			}else if(key.equals(header[i++])){					   
				if(d.getCostDiscount()!=null){
					//map.put(key,String.valueOf(d.getiQuotedPrice()*d.getCount()*d.getKl2()/100*(100-d.getKl())/100) );			//费用折
					map.put(key, d.getCostDiscount().toString());
				}					
			}			
		}
					
	}

	@Override
	public Double getOrderCountByTime(String customCde, String startTime,
			String endTime, Boolean isRebate) {
		String sql = "select sum(total) from order_deliver where customCde = '" + customCde + "'"
				+ " and originalDate >= '" + startTime + "' and originalDate <= '" + endTime + "'";
				
		if(isRebate)
			sql += " and isRebate = 'Y' ";
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

	
}
