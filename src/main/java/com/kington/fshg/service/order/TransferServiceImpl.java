package com.kington.fshg.service.order;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
import com.kington.fshg.model.info.Custom;
import com.kington.fshg.model.info.Product;
import com.kington.fshg.model.order.Transfer;
import com.kington.fshg.model.order.TransferVO;
import com.kington.fshg.service.BaseServiceImpl;
import com.kington.fshg.service.info.CustomService;
import com.kington.fshg.service.info.ProductService;

public class TransferServiceImpl extends BaseServiceImpl<Transfer, TransferVO> implements
		TransferService {
	private static final long serialVersionUID = 9143385664816069254L;
	
	@Resource
	private ProductService productService;
	
	@Resource
	private CustomService customService;

	@Override
	protected String getQueryStr(TransferVO vo) throws Exception {
		StringBuffer sql = new StringBuffer();
		if(StringUtils.isNotBlank(vo.getTransCode()))
			sql.append(" and o.transCode like :transCode ");
		if(StringUtils.isNotBlank(vo.getInWhouseCode()))
			sql.append(" and o.inWhouseCode =:inWhouseCode ");
		if(StringUtils.isNotBlank(vo.getInWhouseName()))
			sql.append(" and o.inWhouseName like :inWhouseName ");
		if(StringUtils.isNotBlank(vo.getOutWhouseCode()))
			sql.append(" and o.outWhouseCode =:outWhouseCode ");
		if(StringUtils.isNotBlank(vo.getOutWhouseName()))
			sql.append(" and o.outWhouseName like :outWhouseName ");
		if(StringUtils.isNotBlank(vo.getStockName()))
			sql.append(" and o.stockName like :stockName ");
		if(StringUtils.isNotBlank(vo.getProductCde()))
			sql.append(" and o.productCde = :productCde ");
		if(StringUtils.isNotBlank(vo.getStockCde()))
			sql.append(" and o.stockCde = :stockCde ");
		if(StringUtils.isNotBlank(vo.getTransBeginTime()))
			sql.append(" and o.transDate >= :beginTime ");
		if(StringUtils.isNotBlank(vo.getTransEndTime()))
			sql.append(" and o.transDate < :endTime ");
		return sql.toString();
	}

	@Override
	protected void setQueryParm(Query query, TransferVO vo) throws Exception {
		if(StringUtils.isNotBlank(vo.getTransCode()))
			query.setParameter("transCode",Common.SYMBOL_PERCENT + vo.getTransCode() + Common.SYMBOL_PERCENT);
		if(StringUtils.isNotBlank(vo.getInWhouseCode()))
			query.setParameter("inWhouseCode",vo.getInWhouseCode());
		if(StringUtils.isNotBlank(vo.getInWhouseName()))
			query.setParameter("inWhouseName", Common.SYMBOL_PERCENT + vo.getInWhouseName() + Common.SYMBOL_PERCENT);
		if(StringUtils.isNotBlank(vo.getOutWhouseCode()))
			query.setParameter("outWhouseCode", vo.getOutWhouseCode());
		if(StringUtils.isNotBlank(vo.getOutWhouseName()))
			query.setParameter("outWhouseName", Common.SYMBOL_PERCENT + vo.getOutWhouseName() + Common.SYMBOL_PERCENT);
		if(StringUtils.isNotBlank(vo.getStockName()))
			query.setParameter("stockName", Common.SYMBOL_PERCENT + vo.getStockName() + Common.SYMBOL_PERCENT);
		if(StringUtils.isNotBlank(vo.getProductCde()))
			query.setParameter("productCde", vo.getProductCde());
		if(StringUtils.isNotBlank(vo.getStockCde()))
			query.setParameter("stockCde", vo.getStockCde());
		if(StringUtils.isNotBlank(vo.getTransBeginTime()))
			query.setParameter("beginTime", DateFormat.str2Date(vo.getTransBeginTime(), 2));
		if(StringUtils.isNotBlank(vo.getTransEndTime()))
			query.setParameter("endTime", DateFormat.getAfterDayFirst(DateFormat.str2Date(vo.getTransEndTime(), 2)));
	}

	@Override
	protected void switchVO2PO(TransferVO vo, Transfer po) throws Exception {
		
	}

	@Override
	public int getTransferFromU8(TransferVO vo){
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		
		int count = 0;
		try {
			String sql =" select a.autoid as 调拨子表ID,b.ctvcode as 调拨单号,b.dtvdate as 调拨日期,b.cowhcode as 调出仓库编码,"
					+ "c1.cwhname as 调出仓库名称,b.ciwhcode as 调入仓库编码,c2.cwhname as 调入仓库名称,a.cinvcode as 存货编号,"
					+ "d.cinvaddcode as 存货代码,d.cinvname as 存货名称,d.cinvstd as 存货规格,a.iTVNum  as 件数,a.iTVACost  as 单价,"
					+ "a.iTVAPrice  as 金额 from TransVouchs a left join TransVouch b on b.id=a.id left join warehouse c1 on c1.cwhcode=b.cowhcode "
					+ "left join warehouse c2 on c2.cwhcode=b.ciwhcode left join inventory d on d.cinvcode=a.cinvcode"
					+ " where 1 = 1 ";
			
			if(StringUtils.isNotBlank(vo.getTransCode()))
				sql += " and b.ctvcode = '" + vo.getTransCode() + "' ";
			if(StringUtils.isNotBlank(vo.getStockName()))
				sql += " and d.cinvname like '%" + vo.getStockName() + "%' ";
			if(StringUtils.isNotBlank(vo.getInWhouseName()))
				sql += " and c2.cwhname like '%" + vo.getInWhouseName() + "%' ";
			if(StringUtils.isNotBlank(vo.getOutWhouseName()))
				sql += " and c1.cwhname like '%" + vo.getOutWhouseName() + "%' ";
			if(StringUtils.isNotBlank(vo.getTransBeginTime()))
				sql += " and b.dtvdate >= '" + vo.getTransBeginTime() + "' ";
			if(StringUtils.isNotBlank(vo.getTransEndTime()))
				sql += " and b.dtvdate <= '" + vo.getTransEndTime() + "' ";
					
			sql += "order by b.dtvdate,b.ctvcode,a.cinvcode";
			conn = PublicType.getConn();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			
			Custom custom = customService.getByCde("06");//“上海利丰仓”
			if(custom == null)
				return 0;
				
			String transferDate = "";
			while(result.next()){
				String autoId = result.getString(1);
				String transCode = result.getString(2);
				String stockCde = result.getString(8);
				
				if(StringUtils.isBlank(autoId) || StringUtils.isBlank(transCode) || StringUtils.isBlank(stockCde))
					continue;
				Product product = productService.getByCde(stockCde);
				if(product == null)
					continue;
				Transfer po = this.getByAutoId(autoId);
				if(po == null){
					po = new Transfer();
					po.setAutoId(autoId);
				}
				po.setTransCode(transCode);
				if(StringUtils.isNotBlank(result.getString(3)))
					po.setTransDate(DateFormat.str2Date(result.getString(3), 9));
				po.setOutWhouseCode(result.getString(4));
				po.setOutWhouseName(result.getString(5));
				po.setInWhouseCode(result.getString(6));
				po.setInWhouseName(result.getString(7));
				po.setStockCde(stockCde);
				po.setProductCde(result.getString(9));
				po.setStockName(result.getString(10));
				po.setStandard(result.getString(11));
				if(StringUtils.isNotBlank(result.getString(12)))
					po.setCount(PublicType.setDoubleScale(Double.parseDouble(result.getString(12))));
				if(StringUtils.isNotBlank(result.getString(13)))
					po.setPrice(PublicType.setDoubleScale4(Double.parseDouble(result.getString(13))));
				if(StringUtils.isNotBlank(result.getString(14)))
					po.setMoney(PublicType.setDoubleScale(Double.parseDouble(result.getString(14))));
				
				//计费方式，客户存在件数单价，则按照件数计价
				if(product.getChargeType() == ChargeType.VOLUME && custom.getCargoPrice() != null
						&& po.getCount() != null && product.getVolume() != null){
					po.setFreight(PublicType.setDoubleScale(product.getVolume() * po.getCount() * custom.getCargoPrice()));
				}
				else if(product.getChargeType() == ChargeType.WEIGHT && custom.getHeavyPrice() != null
						&& po.getCount() != null && product.getBoxWeight() != null){
					po.setFreight(PublicType.setDoubleScale(product.getBoxWeight() * po.getCount() * custom.getHeavyPrice()));
				}
				
				po.setFreightTotal(po.getFreight());
				if(StringUtils.isBlank(transferDate) 
						|| !StringUtils.equals(transferDate, DateFormat.date2Str(po.getTransDate(), 2))){
					po.setLogDeliverFee(custom.getDeliverFee());
					po.setFreightTotal(po.getFreight() + po.getLogDeliverFee());
					
					transferDate = DateFormat.date2Str(po.getTransDate(), 2);
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
			if(result != null) try{result.close();}catch (Exception e) {}
			if(stmt != null) try{stmt.close();}catch(Exception e){}
			if(conn != null) try{conn.close();}catch(Exception e){}
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Transfer getByAutoId(String autoId) throws Exception {
		String sql ="from Transfer where autoId = "+ autoId;
		Query query =em.createQuery(sql);
		List<Transfer> list = query.getResultList();
		if(Common.checkList(list))
			return list.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Double getTransferCost() throws Exception {
		String sql = "select sum(freightTotal) from order_transfer "
		+ " where transDate >= '" + DateFormat.getMonthStart() 
		+ "' and transDate < '" + DateFormat.getMonthFirst(new Date(), 1) + "'";
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
	public Double countByVo(TransferVO vo) {
		String sql = " select sum(freightTotal) from order_transfer where 1 = 1 ";
		if(StringUtils.isNotBlank(vo.getTransCode()))
			sql += " and transCode = '" + vo.getTransCode() + "' ";
		if(StringUtils.isNotBlank(vo.getStockName()))
			sql += " and stockName like '%" + vo.getStockName() + "%' ";
		if(StringUtils.isNotBlank(vo.getInWhouseName()))
			sql += " and inWhouseName like '%" + vo.getInWhouseName() + "%' ";
		if(StringUtils.isNotBlank(vo.getOutWhouseName()))
			sql += " and outWhouseName like '%" + vo.getOutWhouseName() + "%' ";
		if(StringUtils.isNotBlank(vo.getTransBeginTime()))
			sql += " and transDate >= '" + vo.getTransBeginTime() + "' ";
		if(StringUtils.isNotBlank(vo.getTransEndTime()))
			sql += " and transDate <= '" + vo.getTransEndTime() + "' ";
		
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
	public void exportTransfer(String[] heads, Transfer tf, Map<String, String> map) throws Exception {
		String[] header=Common.getExportHeader("TRANSFER");
		for(String key : heads){    
			int i=0;
			if(key.equals(header[i++])){
				map.put(key,tf.getTransCode());    					//调拨单号
			}else if(key.equals(header[i++])){
				map.put(key,DateFormat.date2Str(tf.getTransDate(), 2));   //调拨日期
			}else if(key.equals(header[i++])){
				map.put(key, tf.getOutWhouseName());					//调出仓库名称
			}else if(key.equals(header[i++])){
				map.put(key, tf.getInWhouseName());					//调入仓库名称
			}else if(key.equals(header[i++])){
				map.put(key, tf.getStockName());					//存货名称
			}else if(key.equals(header[i++])){
				if(tf.getPrice()!= null){   								//单价
					map.put(key, tf.getPrice().toString());
				}else{
					map.put(key, "0.0");
				}					
			}else if(key.equals(header[i++])){
				if(tf.getMoney() != null){   								//金额
					map.put(key, tf.getMoney().toString());
				}else{
					map.put(key, "0.0");
				}					
			}else if(key.equals(header[i++])){
				map.put(key, tf.getInWhouseCode());					//调入仓库编码
			}else if(key.equals(header[i++])){
				map.put(key, tf.getOutWhouseCode());					//调出仓库编码
			}else if(key.equals(header[i++])){
				map.put(key, tf.getStockCde());					//存货编码
			}else if(key.equals(header[i++])){
				map.put(key, tf.getProductCde());					//存货代码
			}else if(key.equals(header[i++])){
				map.put(key, tf.getStandard());					//存货规格
			}else if(key.equals(header[i++])){
				if(tf.getCount()!=null){
					map.put(key, tf.getCount().toString());       //件数
				}
			}else if(key.equals(header[i++])){
				if(tf.getFreight() != null){   								//运费
					map.put(key, tf.getFreight().toString());
				}					
			}else if(key.equals(header[i++])){
				if(tf.getLogDeliverFee() != null){   								//配送费
					map.put(key, tf.getLogDeliverFee().toString());
				}					
			}else if(key.equals(header[i++])){
				if(tf.getFreightTotal() != null){   								//运费合计
					map.put(key, tf.getFreightTotal().toString());
				}					
			}
			
		}
	}

}
