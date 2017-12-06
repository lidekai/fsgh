package com.kington.fshg.service.order;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.DateFormat;
import com.kington.fshg.common.PublicType;
import com.kington.fshg.model.order.SaleOrder;
import com.kington.fshg.model.order.SaleOrderVO;
import com.kington.fshg.service.BaseServiceImpl;

public class SaleOrderServiceImpl extends
		BaseServiceImpl<SaleOrder, SaleOrderVO> implements SaleOrderService {

	private static final long serialVersionUID = -7481625883669081252L;

	@Override
	protected String getQueryStr(SaleOrderVO vo) throws Exception {
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
		if(StringUtils.isNotBlank(vo.getCustomName()))
			sql.append(" and o.customName like :customName ");
		if(StringUtils.isNotBlank(vo.getCustomCde()))
			sql.append(" and o.customCde = :customCde ");
		if(StringUtils.isNotBlank(vo.getProductName()))
			sql.append(" and o.productName like :productName ");
		if(StringUtils.isNotBlank(vo.getProductCde()))
			sql.append(" and o.productCde = :productCde ");
		if(StringUtils.isNotBlank(vo.getStockCde()))
			sql.append(" and o.stockCde = :stockCde ");
		if(StringUtils.isNotBlank(vo.getOrderDateStart()))
			sql.append(" and o.orderDate >= :orderDateStart ");
		if(StringUtils.isNotBlank(vo.getOrderDateEnd()))
			sql.append(" and o.orderDate < :orderDateEnd ");
		if(StringUtils.isNotBlank(vo.getCsoCode()))
			sql.append(" and o.csoCode = :csoCode ");
		
		if(Common.checkList(vo.getAreaIds()))
			sql.append(" and o.customCde in (select customCde from Custom where area.id in (:areaIds))");
		if(Common.checkList(vo.getParentAreaIds()))
			sql.append(" and o.customCde in (select customCde from Custom where area.parentArea.id in (:parentAreaIds))");

		
		return sql.toString();
	}

	@Override
	protected void setQueryParm(Query query, SaleOrderVO vo) throws Exception {
		if(vo.getUserId() != null)
			query.setParameter("userId", vo.getUserId());
		
		if(vo.getArea() != null){
			if(Common.checkLong(vo.getArea().getId()))
				query.setParameter("areaId", vo.getArea().getId());
			else if(vo.getArea().getParentArea() != null 
						&& Common.checkLong(vo.getArea().getParentArea().getId()))
				query.setParameter("parentAreaId", vo.getArea().getParentArea().getId());
		}
		
		if(StringUtils.isNotBlank(vo.getCustomName()))
			query.setParameter("customName", Common.SYMBOL_PERCENT + vo.getCustomName() + Common.SYMBOL_PERCENT);
		if(StringUtils.isNotBlank(vo.getCustomCde()))
			query.setParameter("customCde", vo.getCustomCde());
		if(StringUtils.isNotBlank(vo.getProductName()))
			query.setParameter("productName", Common.SYMBOL_PERCENT + vo.getProductName() + Common.SYMBOL_PERCENT);
		if(StringUtils.isNotBlank(vo.getProductCde()))
			query.setParameter("productCde", vo.getProductCde());
		if(StringUtils.isNotBlank(vo.getStockCde()))
			query.setParameter("stockCde", vo.getStockCde());
		
		if(StringUtils.isNotBlank(vo.getOrderDateStart()))
			query.setParameter("orderDateStart", DateFormat.str2Date(vo.getOrderDateStart(), 2));
		if(StringUtils.isNotBlank(vo.getOrderDateEnd()))
			query.setParameter("orderDateEnd",DateFormat.getAfterDayFirst(DateFormat.str2Date(vo.getOrderDateEnd(), 2)));
		if(StringUtils.isNotBlank(vo.getCsoCode()))
			query.setParameter("csoCode", vo.getCsoCode());
		
		if(Common.checkList(vo.getAreaIds()))
			query.setParameter("areaIds", vo.getAreaIds());
		if(Common.checkList(vo.getParentAreaIds()))
			query.setParameter("parentAreaIds", vo.getParentAreaIds());
		
	}

	@Override
	protected void switchVO2PO(SaleOrderVO vo, SaleOrder po) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public SaleOrder getByAutoId(String autoId) {
		String hql = "from SaleOrder where autoId = " + autoId;
		Query query = em.createQuery(hql);
		List<SaleOrder> list = query.getResultList();
		if(Common.checkList(list))
			return list.get(0);
		return null;
	}


	@Override
	public int getSaleOrderFromU8(SaleOrderVO vo) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet result =  null;
		
		int count = 0;
		try {
			String sql = "select a.autoid as 销售订单子表ID,b.csocode as 销售订单号,b.ddate as 订单日期,b.ccuscode as 客户编码,"
				+ " c.ccusname as 客户名称,a.cinvcode as 存货编号,d.cinvaddcode as 存货代码,d.cinvname as 存货名称,d.cinvstd as 存货规格,"
			    + " a.iquantity as 数量,a.iNatSum/a.iquantity as 本币含税单价,a.iNatSum as 本币价税合计   from so_sodetails a "
				+ " left join so_somain b on b.id=a.id left join customer c on c.ccuscode=b.ccuscode left join inventory d on d.cinvcode=a.cinvcode "
			    + " where 1 = 1 ";
			
			if(StringUtils.isNotBlank(vo.getOrderDateStart()))
				sql += " and b.ddate >= '" + vo.getOrderDateStart() + "' ";
			if(StringUtils.isNotBlank(vo.getOrderDateEnd()))
				sql += " and b.ddate <= '" + vo.getOrderDateEnd() + "' ";
			if(StringUtils.isNotBlank(vo.getCsoCode()))
				sql += " and b.csocode = '" + vo.getCsoCode() + "' ";
			if(StringUtils.isNotBlank(vo.getCustomName()))
				sql += " and c.ccusname like '%" + vo.getCustomName() + "%' ";
			if(StringUtils.isNotBlank(vo.getProductName()))
				sql += " and d.cinvname like '%" + vo.getProductName() + "%' ";
			
			sql += " order by b.ddate,b.csocode,a.cinvcode";
			
			conn = PublicType.getConn();
			stmt = conn.createStatement();
			result =  stmt.executeQuery(sql);
			
			while (result.next()) {
				String autoId = result.getString(1);
				String customCde = result.getString(4);
				String stockCde = result.getString(6);
				
				if(StringUtils.isBlank(autoId) || StringUtils.isBlank(customCde)
						|| StringUtils.isBlank(stockCde))
					continue;
				
				SaleOrder saleOrder = this.getByAutoId(autoId);
				if(saleOrder == null){
					saleOrder = new SaleOrder();
					saleOrder.setAutoId(autoId);
				}
				saleOrder.setCsoCode(result.getString(2));
				if(StringUtils.isNotBlank(result.getString(3)))
					saleOrder.setOrderDate(DateFormat.str2Date(result.getString(3), 9));
				saleOrder.setCustomCde(customCde);
				saleOrder.setCustomName(result.getString(5));
				saleOrder.setStockCde(stockCde);
				saleOrder.setProductCde(result.getString(7));
				saleOrder.setProductName(result.getString(8));
				saleOrder.setStandard(result.getString(9));
				if(StringUtils.isNotBlank(result.getString(10)))
					saleOrder.setCount(Double.parseDouble(result.getString(10)));
				if(StringUtils.isNotBlank(result.getString(11)))
					saleOrder.setLocalPrice(PublicType.setDoubleScale4(Double.parseDouble(result.getString(11))));
				if(StringUtils.isNotBlank(result.getString(12)))
					saleOrder.setCountPrice(PublicType.setDoubleScale(Double.parseDouble(result.getString(12))));
				
				if (saleOrder.getCreateTime()==null) {
					saleOrder.setCreateTime(new Date());
					saleOrder.setUpdateTime(saleOrder.getCreateTime());
					
				} else {
					saleOrder.setUpdateTime(new Date());
				}
				
				em.merge(saleOrder);
				count ++;
    		}
			
        } catch (Exception e) {
            e.printStackTrace();
        }finally {                                                                                                                                                                                                  
			if (result != null) try { result.close(); } catch(Exception e) {}
    		if (stmt != null) try { stmt.close(); } catch(Exception e) {}
    		if (conn != null) try { conn.close(); } catch(Exception e) {}
        }
		
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Double getOrderCount(String customCde, Date date) {
		String sql = "select sum(countPrice) from order_sale where customCde = '" + customCde + "'"
				+ " and orderDate >= '" + DateFormat.date2Str(DateFormat.getYearFirstDay(date), 9) 
				+ "' and orderDate < '" + DateFormat.date2Str(DateFormat.getYearMonthFirst(date, 1),9) + "'";
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
	public List<Object> countByVo(SaleOrderVO vo) {
		String sql = " select sum(count), sum(countPrice) from order_sale where 1 = 1 ";
		if(StringUtils.isNotBlank(vo.getOrderDateStart()))
			sql += " and orderDate >= '" + vo.getOrderDateStart() + "' ";
		if(StringUtils.isNotBlank(vo.getOrderDateEnd()))
			sql += " and orderDate <= '" + vo.getOrderDateEnd() + "' ";
		if(StringUtils.isNotBlank(vo.getCsoCode()))
			sql += " and csocode = '" + vo.getCsoCode() + "' ";
		if(StringUtils.isNotBlank(vo.getCustomName()))
			sql += " and customName like '%" + vo.getCustomName() + "%' ";
		if(StringUtils.isNotBlank(vo.getProductName()))
			sql += " and productName like '%" + vo.getProductName() + "%' ";
		
		if(vo.getUserId() != null)
			sql += " and customCde in (select customCde from info_custom where userId =" + vo.getUserId() + ")";
		if(Common.checkList(vo.getAreaIds()))
			sql += " and customCde in (select customCde from info_custom where areaId in (" + vo.getAreaIds().toString().replace("[", "").replace("]", "") + "))";
		if(Common.checkList(vo.getParentAreaIds()))
			sql += " and customCde in (select customCde from info_custom c left join info_area a on c.areaId = a.id where a.parentId in ("+ vo.getParentAreaIds().toString().replace("[", "").replace("]", "") +"))";

		
		Query query = em.createNativeQuery(sql);
		return query.getResultList();
	}

/*	@Override
	public List<Object[]> saleOrder(SaleOrderVO vo) throws Exception {
		String sql="select csoCode,orderDate,customName,productName,count,localPrice,countPrice from order_sale o where 1=1 ";
		sql+=getQueryStr(vo)+" order by orderDate ";		
		Query query=em.createNativeQuery(sql);
		setQueryParm(query, vo);
		//System.out.println("--------------"+vo);
		return query.getResultList();
	}
*/
	@Override
	public void exportSaleOrder(String[] heads, SaleOrder po, Map<String, String> map) throws Exception {
		String[] header=Common.getExportHeader("SALEORDER");
		for(String key : heads){
			int i=0;
			if(key.equals(header[i++])){             
				map.put(key, po.getCsoCode());//销售订单号
			}else if(key.equals(header[i++])){
				map.put(key,DateFormat.date2Str(po.getOrderDate(), 2));  //订单日期
			}else if(key.equals(header[i++])){
				map.put(key, po.getCustomName()); // 客户名称
			}else if(key.equals(header[i++])){
				map.put(key, po.getProductName()); //存货名称
			}else if(key.equals(header[i++])){
				if(po.getCount()!= null){   			 //数量
					map.put(key, po.getCount().toString());
				}else{
					map.put(key, "0.0");
				}
				
			}else if(key.equals(header[i++])){    // 本币含税单价
				if(po.getLocalPrice()!= null){
					map.put(key, po.getLocalPrice().toString());
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){   //  本币价税合计  
				if(po.getCountPrice() != null){
					map.put(key, po.getCountPrice().toString());
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				map.put(key,po.getCustomCde());  //客户编码
			}else if(key.equals(header[i++])){
				map.put(key,po.getStockCde());  //存货编码
			}else if(key.equals(header[i++])){
				map.put(key,po.getProductCde());  //存货代码
			}else if(key.equals(header[i++])){
				map.put(key,po.getStandard());  //存货规格
			}
		
		}
	
	}
	

	
	
}
