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
import com.kington.fshg.model.order.SaleBill;
import com.kington.fshg.model.order.SaleBillVO;
import com.kington.fshg.service.BaseServiceImpl;

public class SaleBillServiceImpl extends BaseServiceImpl<SaleBill, SaleBillVO>
		implements SaleBillService {

	private static final long serialVersionUID = 372191605423644465L;

	@Override
	protected String getQueryStr(SaleBillVO vo) throws Exception {
		StringBuffer sql = new StringBuffer();
		
		if(StringUtils.isNotBlank(vo.getAutoId())){
			sql.append(" and o.autoId = :autoId");
		}
		if(StringUtils.isNotBlank(vo.getCsbvcode())){
			sql.append(" and o.csbvcode = :csbvcode");
		}
		if(StringUtils.equals(vo.getTimeType(), "1")){
			if(StringUtils.isNotBlank(vo.getBeginTime()))
				sql.append(" and o.saleDate >= :beginTime ");
			if(StringUtils.isNotBlank(vo.getEndTime()))
				sql.append(" and o.saleDate < :endTime ");
		}else if(StringUtils.equals(vo.getTimeType(), "2")){
			if(StringUtils.isNotBlank(vo.getBeginTime()))
				sql.append(" and o.deliverDate >= :beginTime ");
			if(StringUtils.isNotBlank(vo.getEndTime()))
				sql.append(" and o.deliverDate < :endTime ");
		}else if(StringUtils.equals(vo.getTimeType(), "3")){
			if(StringUtils.isNotBlank(vo.getBeginTime()))
				sql.append(" and o.createDate >= :beginTime ");
			if(StringUtils.isNotBlank(vo.getEndTime()))
				sql.append(" and o.createDate < :endTime ");
		}
		
		if(StringUtils.isNotBlank(vo.getSaleOrderId())){
			sql.append(" and o.saleOrderId = :saleOrderId");
		}
		
		if(StringUtils.isNotBlank(vo.getCsoCode())){
			sql.append(" and o.csoCode like :csoCode");
		}
		if(StringUtils.isNotBlank(vo.getCustomCde())){
			sql.append(" and o.customCde like :customCde");
		}
		if(StringUtils.isNotBlank(vo.getCustomName())){
			sql.append(" and o.customName like :customName");
		}
		if(StringUtils.isNotBlank(vo.getStockCde())){
			sql.append(" and o.stockCde = :stockCde");
		}
		if(StringUtils.isNotBlank(vo.getProductCde())){
			sql.append(" and o.productCde = :productCde");
		}
		if(StringUtils.isNotBlank(vo.getProductName())){
			sql.append(" and o.productName like :productName");
		}
		if(StringUtils.isNotBlank(vo.getStandard())){
			sql.append(" and o.standard = :standard");
		}
		if(vo.getCount()!=null){
			sql.append(" and o.count = :count");
		}
		if(vo.getLocalPrice()!=null){
			sql.append(" and o.localPrice = :localPrice");
		}
		if(vo.getCountPrice()!=null){
			sql.append(" and o.countPrice = :countPrice");
		}
		if(vo.getNoTaxPrice()!=null){
			sql.append(" and o.noTaxPrice = :noTaxPrice");
		}
		
		if(Common.checkList(vo.getAreaIds()))
			sql.append(" and o.customCde in (select customCde from Custom where area.id in (:areaIds))");
		if(Common.checkList(vo.getParentAreaIds()))
			sql.append(" and o.customCde in (select customCde from Custom where area.parentArea.id in (:parentAreaIds))");
		if(vo.getUserId() != null)
			sql.append(" and o.customCde in (select customCde from Custom where user.id = :userId)");
		
		if(vo.getIsRebate() != null)
			sql.append(" and o.isRebate = :isRebate ");
		
		return sql.toString();
	}

	@Override
	protected void setQueryParm(Query query, SaleBillVO vo) throws Exception {
		if(StringUtils.isNotBlank(vo.getAutoId())){
			query.setParameter("autoId", vo.getAutoId());
		}
		if(StringUtils.isNotBlank(vo.getCsbvcode())){
			query.setParameter("csbvcode", vo.getCsbvcode());
		}
		if(StringUtils.isNotBlank(vo.getCsbvcode())){
			query.setParameter("csbvcode", vo.getCsbvcode());
		}
		if(StringUtils.isNotBlank(vo.getBeginTime())){
			query.setParameter("beginTime", DateFormat.str2Date(vo.getBeginTime(), 2) );
		}
		if(StringUtils.isNotBlank(vo.getEndTime())){
			query.setParameter("endTime", DateFormat.getNextDay(DateFormat.str2Date(vo.getEndTime(), 2)));
		}
		if(StringUtils.isNotBlank(vo.getSaleOrderId())){
			query.setParameter("saleOrderId", vo.getSaleOrderId());
		}
		if(StringUtils.isNotBlank(vo.getCsoCode())){
			query.setParameter("csoCode", Common.SYMBOL_PERCENT +vo.getCsoCode()+Common.SYMBOL_PERCENT );
		}
		if(StringUtils.isNotBlank(vo.getCustomCde())){
			query.setParameter("customCde", Common.SYMBOL_PERCENT +vo.getCustomCde()+Common.SYMBOL_PERCENT );
		}
		if(StringUtils.isNotBlank(vo.getCustomName())){
			query.setParameter("customName", Common.SYMBOL_PERCENT +vo.getCustomName()+Common.SYMBOL_PERCENT );
		}
		if(StringUtils.isNotBlank(vo.getStockCde())){
			query.setParameter("stockCde", vo.getStockCde());
		}
		if(StringUtils.isNotBlank(vo.getProductCde())){
			query.setParameter("productCde", vo.getProductCde());
		}
		if(StringUtils.isNotBlank(vo.getProductName())){
			query.setParameter("productName", Common.SYMBOL_PERCENT+vo.getProductName()+Common.SYMBOL_PERCENT);
		}
		if(StringUtils.isNotBlank(vo.getStandard())){
			query.setParameter("standard", vo.getStandard());
		}
		if(vo.getCount()!=null){
			query.setParameter("count", vo.getCount());
		}
		if(vo.getCount()!=null){
			query.setParameter("localPrice", vo.getLocalPrice());
		}
		if(vo.getCount()!=null){
			query.setParameter("noTaxPrice", vo.getNoTaxPrice());
		}
		
		if(Common.checkList(vo.getAreaIds()))
			query.setParameter("areaIds", vo.getAreaIds());
		if(Common.checkList(vo.getParentAreaIds()))
			query.setParameter("parentAreaIds", vo.getParentAreaIds());
		if(vo.getUserId() != null)
			query.setParameter("userId", vo.getUserId());
		
		if(vo.getIsRebate() != null){
			query.setParameter("isRebate",vo.getIsRebate());
		}
	}

	@Override
	protected void switchVO2PO(SaleBillVO vo, SaleBill po) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public int impSaleBillFromU8(SaleBillVO vo) {
		Connection conn=null;
		Statement state=null;
		ResultSet rs=null;
		int count=0;
		try {
			String sql="select aa.autoid as 销售发票子表Id ,bb.csbvcode as 销售发票号,"
					+ " bb.ddate as 开票日期, a.autoid as 销售订单子表ID,b.csocode as 销售订单号,"
					+ " b.ddate as 订单日期, ee.ddate as 发货日期, bb.ccuscode as 客户编码,c.ccusname as 客户名称,"
					+ " aa.cinvcode as 存货编号,d.cinvaddcode as 存货代码,d.cinvname as 存货名称,"
					+ " d.cinvstd as 存货规格,aa.iquantity as 数量,aa.iNatSum/aa.iquantity as 本币含税单价,"
					+ " aa.iNatSum as 本币价税合计,aa.iNatMoney as 本币无税金额   " 
					+ " from SaleBillVouchs aa left join SaleBillVouch bb on bb.sbvid = aa.sbvid "
					+ " left join so_sodetails a on a.isosid = aa.isosid "
					+ " left join DispatchLists e on e.iDLsID = aa.iDLsID "
					+ " left join DispatchList ee on ee.dlid = e.dlid "
					+ " left join so_somain b on b.id=a.id "
					+ " left join customer c on c.ccuscode=bb.ccuscode "
					+ " left join inventory d on d.cinvcode=aa.cinvcode "
					+" where 1=1 ";
			
			if(StringUtils.endsWith(vo.getTimeType(), "1")){
				if(StringUtils.isNotBlank(vo.getBeginTime()))
					sql += " and b.ddate >= '" + vo.getBeginTime() + "' "; 
				if(StringUtils.isNotBlank(vo.getEndTime()))
					sql += " and b.ddate < '" + vo.getEndTime() + "' ";
			}else if(StringUtils.endsWith(vo.getTimeType(), "2")){
				if(StringUtils.isNotBlank(vo.getBeginTime()))
					sql += " and ee.ddate >= '" + vo.getBeginTime() + "' "; 
				if(StringUtils.isNotBlank(vo.getEndTime()))
					sql += " and ee.ddate <= '" + vo.getEndTime() + "' ";
			}else if(StringUtils.endsWith(vo.getTimeType(), "3")){
				if(StringUtils.isNotBlank(vo.getBeginTime()))
					sql += " and bb.ddate >= '" + vo.getBeginTime() + "' "; 
				if(StringUtils.isNotBlank(vo.getEndTime()))
					sql += " and bb.ddate <= '" + vo.getEndTime() + "' ";
			}
			
			if(StringUtils.isNotBlank(vo.getCsbvcode())){
				sql+=" and bb.csbvcode =' "+vo.getCsbvcode() +"'";
			}
			if(StringUtils.isNotBlank(vo.getCustomName())){
				sql+=" and c.ccusname like '& "+vo.getCustomName() +"' ";
			}
			if(StringUtils.isNotBlank(vo.getProductName())){
				sql+=" and d.cinvname like '& "+vo.getProductName() +"' ";
			}
			
					
			sql+=" order by bb.ddate,bb.cdlcode,aa.cinvcode";
			conn = PublicType.getConn();
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			System.out.println(sql);
			//System.out.println(rs.next());
			while(rs.next()){
				String autoId = rs.getString(1);
				String customCde = rs.getString(8);
				String stockCde = rs.getString(10);
				if(StringUtils.isBlank(autoId) || StringUtils.isBlank(customCde) 
						|| StringUtils.isBlank(stockCde)){
					continue;
				}
				SaleBill po=this.getByAutoId(autoId);
				if(po==null){
					po=new SaleBill();
					po.setAutoId(autoId);
				}
				
				po.setCsbvcode(rs.getString(2));
				if(StringUtils.isNotBlank(rs.getString(3))){
					po.setCreateDate(DateFormat.str2Date(rs.getString(3), 9));
				}
				po.setSaleOrderId(rs.getString(4));
				po.setCsoCode(rs.getString(5));
				if(StringUtils.isNotBlank(rs.getString(6))){
					po.setSaleDate(DateFormat.str2Date(rs.getString(6), 9));
				}
				if(StringUtils.isNotBlank(rs.getString(7))){
					po.setDeliverDate(DateFormat.str2Date(rs.getString(7), 9));
				}
				po.setCustomCde(customCde);
				po.setCustomName(rs.getString(9));
				po.setStockCde(stockCde);
				po.setProductCde(rs.getString(11));
				po.setProductName(rs.getString(12));
				po.setStandard(rs.getString(13));
				if(StringUtils.isNotBlank(rs.getString(14))){
					po.setCount(PublicType.setDoubleScale(Double.parseDouble(rs.getString(14))));				
				}
				if(StringUtils.isNotBlank(rs.getString(15))){
					po.setLocalPrice(PublicType.setDoubleScale4(Double.parseDouble(rs.getString(15))));				
				}
				if(StringUtils.isNotBlank(rs.getString(16))){
					po.setCountPrice(PublicType.setDoubleScale(Double.parseDouble(rs.getString(16))));				
				}
				if(StringUtils.isNotBlank(rs.getString(17))){
					po.setNoTaxPrice(PublicType.setDoubleScale(Double.parseDouble(rs.getString(17))));				
				}
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
			if(conn!=null){try {conn.close();} catch (Exception e) {}}
			if(state!=null){try{state.close();}catch(Exception e){}}
			if(rs!=null){try{rs.close();}catch(Exception e){}}
			
		}
		
		return count;
	}

	@Override
	@SuppressWarnings("unchecked")
	public SaleBill getByAutoId(String autoId) throws Exception {
		String sql= "from SaleBill where autoId = "+ autoId;
		Query query = em.createQuery(sql);
		List<SaleBill> list=query.getResultList();
		if(Common.checkList(list)){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<SaleBill> saleBill(SaleBillVO vo) throws Exception {
		
		String hql="select o from SaleBill o where 1=1 ";
		hql+=getQueryStr(vo)+" order by o.createDate ";
		Query query=em.createQuery(hql);
		setQueryParm(query, vo);
		return query.getResultList();
		
	}

	@Override
	public void exprotSaleBill(String[] heads, SaleBill sb, Map<String, String> map) throws Exception {
		String[] header=Common.getExportHeader("SALEBILL"); 
		for(String key:heads){
			int i=0;
			if(key.equals(header[i++])){
				map.put(key, sb.getCsbvcode());     		//销售订单号"
			}else if(key.equals(header[i++])){
				map.put(key, DateFormat.date2Str(sb.getCreateDate(), 2)); //"制单日期"
			}else if(key.equals(header[i++])){
				map.put(key, DateFormat.date2Str(sb.getSaleDate(), 2)); //"订单日期"
			}else if(key.equals(header[i++])){
				map.put(key, DateFormat.date2Str(sb.getDeliverDate(), 2)); //"发货日期"
			}else if(key.equals(header[i++])){
				map.put(key, sb.getCsoCode()); 								//"销售单号"
			}else if(key.equals(header[i++])){
				map.put(key, sb.getCustomName()); 								//"客户名称"
			}else if(key.equals(header[i++])){
				map.put(key, sb.getProductName()); 								//"存货名称"
			}else if(key.equals(header[i++])){
				if(sb.getCount()!=null){
					map.put(key, sb.getCount().toString());				//,,"数量",
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				if(sb.getCount()!=null){
					map.put(key, sb.getLocalPrice().toString());				//本币含税单价
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				if(sb.getCount()!=null){
					map.put(key, sb.getCountPrice().toString());				//本币价税合计
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				if(sb.getCount()!=null){
					map.put(key, sb.getNoTaxPrice().toString());				//本币无税金额
				}else{
					map.put(key, "0.0");
				}
			}
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> countByVo(SaleBillVO vo) {
		String sql = "select sum(count), sum(countPrice) from sale_bill where 1 = 1 ";
		
		if(StringUtils.endsWith(vo.getTimeType(), "1")){
			if(StringUtils.isNotBlank(vo.getBeginTime()))
				sql+=" and saleDate >= '"+vo.getBeginTime()+"'";
			if(StringUtils.isNotBlank(vo.getEndTime()))
				sql+=" and saleDate <= '"+vo.getEndTime()+"'";
		}else if(StringUtils.endsWith(vo.getTimeType(), "2")){
			if(StringUtils.isNotBlank(vo.getBeginTime()))
				sql+=" and deliverDate >= '"+vo.getBeginTime()+"'";
			if(StringUtils.isNotBlank(vo.getEndTime()))
				sql+=" and deliverDate <= '"+vo.getEndTime()+"'";
		}else if(StringUtils.endsWith(vo.getTimeType(), "3")){
			if(StringUtils.isNotBlank(vo.getBeginTime()))
				sql+=" and createDate >= '"+vo.getBeginTime()+"'";
			if(StringUtils.isNotBlank(vo.getEndTime()))
				sql+=" and createDate <= '"+vo.getEndTime()+"'";
		}
		
		if(StringUtils.isNotBlank(vo.getCsbvcode()))
			sql += " and csbvcode = '" + vo.getCsbvcode() + "' ";
		if(StringUtils.isNotBlank(vo.getCustomName()))
			sql += " and customName like '%" + vo.getCustomName() + "%' ";
		if(StringUtils.isNotBlank(vo.getProductName()))
			sql += " and productName like '%" + vo.getProductName() + "%' ";
		if(vo.getIsRebate() != null)
			sql += " and isRebate = '" + vo.getIsRebate() + "'";
		
		if(vo.getUserId() != null)
			sql += " and customCde in (select customCde from info_custom where userId =" + vo.getUserId() + ")";
		if(Common.checkList(vo.getAreaIds()))
			sql += " and customCde in (select customCde from info_custom where areaId in (" + vo.getAreaIds().toString().replace("[", "").replace("]", "") + "))";
		if(Common.checkList(vo.getParentAreaIds()))
			sql += " and customCde in (select customCde from info_custom c left join info_area a on c.areaId = a.id where a.parentId in ("+ vo.getParentAreaIds().toString().replace("[", "").replace("]", "") +"))";

		Query query = em.createNativeQuery(sql);
		return query.getResultList();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Double getCountPrice(String customCde, Date date) {
		String sql = "select sum(countPrice) from sale_bill where customCde = '" + customCde + "'"
			+ " and createDate >= '" + DateFormat.date2Str(DateFormat.getYearFirstDay(date), 9) 
			+ "' and createDate < '" + DateFormat.date2Str(DateFormat.getYearMonthFirst(date, 1),9) + "'";
		
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
	public Double getCountPriceByTime(String customCde, String startTime,
			String endTime) {
		String sql = "select sum(countPrice) from sale_bill where customCde = '" + customCde + "'"
				+ " and createDate >= '" + startTime + "' and createDate <= '" + endTime + "' and isRebate = 'Y'";
			
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
	public boolean updateRebate(Long id, String isRebate) throws Exception {
		String sql = "update sale_bill set isRebate='" + isRebate + "' where id =" + id;
		Query query = em.createNativeQuery(sql);
		return query.executeUpdate() > 0;
	}

	
}
