package com.kington.fshg.service.account;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.DateFormat;
import com.kington.fshg.common.PublicType;
import com.kington.fshg.model.account.ReceiptBill;
import com.kington.fshg.model.account.ReceiptBillVO;
import com.kington.fshg.service.BaseServiceImpl;

public class ReceiptBillServiceImpl extends
		BaseServiceImpl<ReceiptBill, ReceiptBillVO> implements
		ReceiptBillService {

	private static final long serialVersionUID = 4448853425513210333L;

	@Override
	protected String getQueryStr(ReceiptBillVO vo) throws Exception {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" and o.receiveCount > 0 ");
		
		if(StringUtils.isNotBlank(vo.getCvouchId()))
			sql.append(" and o.cvouchId =:cvouchId ");
		if(StringUtils.isNotBlank(vo.getCustomerName()))
			sql.append(" and o.customerName like :customerName ");
		if(StringUtils.isNotBlank(vo.getBeginTime()))
			sql.append(" and o.receiptDate >= :beginTime ");
		if(StringUtils.isNotBlank(vo.getEndTime()))
			sql.append(" and o.receiptDate <= :endTime ");
		
		if(Common.checkLong(vo.getCustomTypeId()))
			sql.append(" and o.customerCde in (select customCde from Custom where customType.id = :customTypeId)");
		
		if(vo.getUserId() != null)
			sql.append(" and o.customerCde in (select customCde from Custom where user.id = :userId)");
		if(Common.checkList(vo.getAreaIds()))
			sql.append(" and o.customerCde in (select customCde from Custom where area.id in (:areaIds))");
		if(Common.checkList(vo.getParentAreaIds()))
			sql.append(" and o.customerCde in (select customCde from Custom where area.parentArea.id in (:parentAreaIds))");
		
		if(StringUtils.isNotBlank(vo.getCustomerCde()))
			sql.append(" and o.customerCde = :customerCde ");
		
		return sql.toString();

	}

	@Override
	protected void setQueryParm(Query query, ReceiptBillVO vo) throws Exception {
		if(StringUtils.isNotBlank(vo.getCvouchId()))
			query.setParameter("cvouchId", vo.getCvouchId());
		if(StringUtils.isNotBlank(vo.getCustomerName()))
			query.setParameter("customerName", Common.SYMBOL_PERCENT + vo.getCustomerName() + Common.SYMBOL_PERCENT);
		if(StringUtils.isNotBlank(vo.getBeginTime()))
			query.setParameter("beginTime", DateFormat.str2Date(vo.getBeginTime(), 2) );
		if(StringUtils.isNotBlank(vo.getEndTime()))
			query.setParameter("endTime", DateFormat.str2Date(vo.getEndTime(), 2));
		
		if(Common.checkLong(vo.getCustomTypeId()))
			query.setParameter("customTypeId", vo.getCustomTypeId());
		
		if(Common.checkList(vo.getAreaIds()))
			query.setParameter("areaIds", vo.getAreaIds());
		if(Common.checkList(vo.getParentAreaIds()))
			query.setParameter("parentAreaIds", vo.getParentAreaIds());
		if(vo.getUserId() != null)
			query.setParameter("userId", vo.getUserId());
		
		if(StringUtils.isNotBlank(vo.getCustomerCde()))
			query.setParameter("customerCde", vo.getCustomerCde());
		
	}

	@Override
	protected void switchVO2PO(ReceiptBillVO vo, ReceiptBill po)
			throws Exception {
		if(vo.getReceiptCount1() != null){
			po.setReceiptCount1(vo.getReceiptCount1());
			po.setReceiveCount(po.getReceiptCount1());
		}
		if(vo.getReceiveCount() != null)
			po.setReceiveCount(vo.getReceiveCount());
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> countByVo(ReceiptBillVO vo) {
		String sql = "select sum(receiptCount), sum(receiptCount1), sum(receiveCount) from receipt_bill where 1 = 1 ";
		
		if(StringUtils.isNotBlank(vo.getCvouchId()))
			sql += " and cvouchId =" + vo.getCvouchId();
		if(StringUtils.isNotBlank(vo.getCustomerName()))
			sql += " and customerName like '" + vo.getCustomerName() + "'";
		if(StringUtils.isNotBlank(vo.getBeginTime()))
			sql += " and receiptDate >= '" + vo.getBeginTime() + "'";
		if(StringUtils.isNotBlank(vo.getEndTime()))
			sql += " and receiptDate <= '" + vo.getEndTime() + "'";
		
		if(vo.getUserId() != null)
			sql += " and customerCde in (select customCde from info_custom where userId =" + vo.getUserId() + ")";
		if(Common.checkList(vo.getAreaIds()))
			sql += " and customerCde in (select customCde from info_custom where areaId in (" + vo.getAreaIds().toString().replace("[", "").replace("]", "") + "))";
		if(Common.checkList(vo.getParentAreaIds()))
			sql += " and customerCde in (select customCde from info_custom c left join info_area a on c.areaId = a.id where a.parentId in ("+ vo.getParentAreaIds().toString().replace("[", "").replace("]", "") +"))";

		Query query = em.createNativeQuery(sql);
		return query.getResultList();
	}

	@Override
	public int impReceiptBillFromU8(ReceiptBillVO vo) {
		Connection conn=null;
		Statement state=null;
		ResultSet rs=null;
		int count=0;
		try {
			String sql="select a.id as 子表标识, b.cvouchtype as 类别, b.cvouchid as 收款单号,b.dvouchdate as 收款日期, "
					+ " b.cdwcode as 客户编号,c.ccusname as 客户名称,b.csscode as 结算编号,d.cSSName  as 结算名称,"
					+ " case when b.cVouchType='49' then -iAmt else iAmt end as 本币收款金额 "
					+ " from ap_closebills a left join ap_closebill b on b.iID=a.iid "
					+ " left join customer c on c.ccuscode=b.cdwcode "
					+ " left join SettleStyle d on d.cSSCode=b.cSSCode  " 
					+ " where b.cflag='ar' and b.cVouchType in ('48','49')  ";
			
			if(StringUtils.isNotBlank(vo.getCvouchId()))
				sql += " and b.cvouchId =" + vo.getCvouchId();
			if(StringUtils.isNotBlank(vo.getCustomerName()))
				sql += " and c.ccusname like '" + vo.getCustomerName() + "'";
			if(StringUtils.isNotBlank(vo.getBeginTime()))
				sql += " and b.dvouchdate >= '" + vo.getBeginTime() + "'";
			if(StringUtils.isNotBlank(vo.getEndTime()))
				sql += " and b.dvouchdate <= '" + vo.getEndTime() + "'";
					
			conn = PublicType.getConn();
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			System.out.println(sql);
			//System.out.println(rs.next());
			while(rs.next()){
				String autoId = rs.getString(1);
				String customCde = rs.getString(5);
				if(StringUtils.isBlank(autoId) || StringUtils.isBlank(customCde) ){
					continue;
				}
				ReceiptBill po=this.getByAutoId(autoId);
				if(po==null){
					po=new ReceiptBill();
					po.setAutoId(autoId);
				}
				
				po.setCvouchType(rs.getString(2));
				po.setCvouchId(rs.getString(3));
				if(StringUtils.isNotBlank(rs.getString(4))){
					po.setReceiptDate(DateFormat.str2Date(rs.getString(4), 9));
				}
				po.setCustomerCde(customCde);
				po.setCustomerName(rs.getString(6));
				po.setCssCode(rs.getString(7));
				po.setCssName(rs.getString(8));
				if(StringUtils.isNotBlank(rs.getString(9)))
					po.setReceiptCount(PublicType.setDoubleScale(Double.parseDouble(rs.getString(9))));	
				
				if(po.getReceiptCount() != null){
					po.setReceiptCount1(po.getReceiptCount());
					po.setReceiveCount(po.getReceiptCount1());
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


	@SuppressWarnings("unchecked")
	private ReceiptBill getByAutoId(String autoId) throws Exception {
		String sql= "from ReceiptBill where autoId = "+ autoId;
		Query query = em.createQuery(sql);
		List<ReceiptBill> list=query.getResultList();
		if(Common.checkList(list)){
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReceiptBill> receiptBill(ReceiptBillVO vo) throws Exception {
		String hql="select o from ReceiptBill o where 1=1 ";
		hql += getQueryStr(vo);
		Query query=em.createQuery(hql);
		setQueryParm(query, vo);
		return query.getResultList();
	}

	@Override
	public void exprotReceiptBill(String[] heads, ReceiptBill rb,Map<String, String> map) throws Exception {
		String[] header=Common.getExportHeader("RECEIPTBILL"); 
		for(String key:heads){
			int i=0;
			if(key.equals(header[i++])){
				map.put(key, StringUtils.equals(rb.getCvouchType(), "48") ? "收" : (StringUtils.equals(rb.getCvouchType(), "49") ? "付" : ""));     		//"类别"
			}else if(key.equals(header[i++])){
				map.put(key, rb.getCvouchId()); //"收款单号"
			}else if(key.equals(header[i++])){
				map.put(key, DateFormat.date2Str(rb.getReceiptDate(), 2)); //"收款日期"
			}else if(key.equals(header[i++])){
				map.put(key, rb.getCustomerCde()); //"客户编号"
			}else if(key.equals(header[i++])){
				map.put(key, rb.getCustomerName()); //"客户名称"
			}else if(key.equals(header[i++])){
				map.put(key, rb.getCssCode()); 								//"结算编号"
			}else if(key.equals(header[i++])){
				map.put(key, rb.getCssName()); 								//"结算名称"
			}else if(key.equals(header[i++])){
				if(rb.getReceiptCount()!=null){
					map.put(key, rb.getReceiptCount().toString());				//"本币收款金额"
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				if(rb.getReceiptCount1()!=null){
					map.put(key, rb.getReceiptCount1().toString());				//"本币实际收款金额"
				}else{
					map.put(key, "0.0");
				}
			}			
		}
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReceiptBill> getListByIds(String ids) throws Exception {
		String sql= "from ReceiptBill where id in (" + ids + ") order by receiptDate ";
		Query query = em.createQuery(sql);
		return query.getResultList();
	}
}
