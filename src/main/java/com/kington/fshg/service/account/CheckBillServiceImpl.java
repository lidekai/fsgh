package com.kington.fshg.service.account;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.DateFormat;
import com.kington.fshg.common.PublicType;
import com.kington.fshg.common.PublicType.ReceiveState;
import com.kington.fshg.model.account.CheckBill;
import com.kington.fshg.model.account.CheckBillVO;
import com.kington.fshg.service.BaseServiceImpl;

public class CheckBillServiceImpl extends
		BaseServiceImpl<CheckBill, CheckBillVO> implements
		CheckBillService {

	private static final long serialVersionUID = -4909797206965224829L;

	@Override
	protected String getQueryStr(CheckBillVO vo) throws Exception {
		StringBuffer sql = new StringBuffer();
		
		if(StringUtils.isNotBlank(vo.getCode()))
			sql.append(" and o.code like :code ");
		if(StringUtils.isNotBlank(vo.getCustomName()))
			sql.append(" and o.customName like :customName ");
		if(StringUtils.isNotBlank(vo.getBeginTime()))
			sql.append(" and o.createDate >= :beginTime ");
		if(StringUtils.isNotBlank(vo.getEndTime()))
			sql.append(" and o.createDate <= :endTime ");
		if(vo.getState() != null)
			sql.append(" and o.state = :state ");
		
		if(vo.getUserId() != null)
			sql.append(" and o.customCde in (select customCde from Custom where user.id = :userId)");
		if(Common.checkList(vo.getAreaIds()))
			sql.append(" and o.customCde in (select customCde from Custom where area.id in (:areaIds))");
		if(Common.checkList(vo.getParentAreaIds()))
			sql.append(" and o.customCde in (select customCde from Custom where area.parentArea.id in (:parentAreaIds))");

		return sql.toString();

	}

	@Override
	protected void setQueryParm(Query query, CheckBillVO vo) throws Exception {
		if(StringUtils.isNotBlank(vo.getCode()))
			query.setParameter("code", Common.SYMBOL_PERCENT + vo.getCode() + Common.SYMBOL_PERCENT);
		if(StringUtils.isNotBlank(vo.getCustomName()))
			query.setParameter("customName", Common.SYMBOL_PERCENT + vo.getCustomName() + Common.SYMBOL_PERCENT);
		if(StringUtils.isNotBlank(vo.getBeginTime()))
			query.setParameter("beginTime", DateFormat.str2Date(vo.getBeginTime(), 2) );
		if(StringUtils.isNotBlank(vo.getEndTime()))
			query.setParameter("endTime", DateFormat.str2Date(vo.getEndTime(), 2));
		if(vo.getState() != null)
			query.setParameter("state", vo.getState());
		
		if(Common.checkList(vo.getAreaIds()))
			query.setParameter("areaIds", vo.getAreaIds());
		if(Common.checkList(vo.getParentAreaIds()))
			query.setParameter("parentAreaIds", vo.getParentAreaIds());
		if(vo.getUserId() != null)
			query.setParameter("userId", vo.getUserId());
		
	}

	@Override
	protected void switchVO2PO(CheckBillVO vo, CheckBill po)
			throws Exception {
		if(vo.getState() != null)
			po.setState(vo.getState());
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized String getCode() {
		String code = "YCB" + DateFormat.date2Str(new Date(), 4);
		String sql = "select code from check_bill where code like '" + code + "%' order by code desc limit 1 ";
		Query query = em.createNativeQuery(sql);
		
		int number = 0;
		List<Object> list = query.getResultList();
		if(Common.checkList(list) && list.get(0) != null){
			String a = list.get(0).toString();
			number = Integer.parseInt(a.substring(9));
		}
		
		DecimalFormat df = new DecimalFormat("00000");  
		code += df.format(number + 1);
		return code;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> countByVo(CheckBillVO vo) {
		String sql = "select sum(countPrice), sum(receivePrice), sum(actualPrice), sum(chargePrice),sum(returnPrice),sum(holdPrice),sum(otherPrice)"
				+ " from check_bill where 1 = 1 ";
		
		if(StringUtils.isNotBlank(vo.getCode()))
			sql += " and code = '" + vo.getCode() + "' ";
		if(StringUtils.isNotBlank(vo.getCustomName()))
			sql += " and customName like '%" + vo.getCustomName() + "%' ";
		if(vo.getState() != null)
			sql += " and state = '" + vo.getState() + "'";
		if(StringUtils.isNotBlank(vo.getBeginTime()))
			sql+=" and createDate >= '"+vo.getBeginTime()+"'";
		if(StringUtils.isNotBlank(vo.getEndTime()))
			sql+=" and createDate <= '"+vo.getEndTime()+"'";
		
		
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
	public List<CheckBill> checkBill(CheckBillVO vo) throws Exception {
		String hql="select o from CheckBill o where 1=1 ";
		hql += getQueryStr(vo)+" order by o.createDate ";
		Query query=em.createQuery(hql);
		setQueryParm(query, vo);
		return query.getResultList();
	}

	@Override
	public void exprotCheckBill(String[] heads, CheckBill rb,
			Map<String, String> map) throws Exception {
		String[] header=Common.getExportHeader("CHECKBILL"); 
		for(String key:heads){
			int i=0;
			if(key.equals(header[i++])){
				map.put(key, rb.getCode());     		//单号"
			}else if(key.equals(header[i++])){
				map.put(key, DateFormat.date2Str(rb.getCreateDate(), 2)); //"制单日期"
			}else if(key.equals(header[i++])){
				map.put(key, rb.getCustomCde()); 								//"客户编号"
			}else if(key.equals(header[i++])){
				map.put(key, rb.getCustomName()); 								//"客户名称"
			}else if(key.equals(header[i++])){
				if(rb.getCountPrice()!=null){
					map.put(key, rb.getCountPrice().toString());				//"本币价税合计",
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				map.put(key, rb.getReceivePrice().toString()); 								//"已收款合计"
			}else if(key.equals(header[i++])){
				map.put(key, rb.getActualPrice().toString()); 								//"实际收款额"
			}else if(key.equals(header[i++])){
				map.put(key, rb.getChargePrice().toString()); 								//"待费用发票额"
			}else if(key.equals(header[i++])){
				map.put(key, rb.getReturnPrice().toString()); 								//"待退货额"
			}else if(key.equals(header[i++])){
				map.put(key, rb.getHoldPrice().toString()); 								//"暂扣额"
			}else if(key.equals(header[i++])){
				map.put(key, rb.getOtherPrice().toString()); 								//"其他余额"
			}else if(key.equals(header[i++])){
				map.put(key, rb.getState().getText()); 								//"状态"
			}
		}
		
	}

	@Override
	public void sumCount(Long checkBillId) {
		String sql = "UPDATE check_bill SET receivePrice = (SELECT SUM(receivePrice) receivePrice FROM receive_bill WHERE checkId = " + checkBillId + "),"
				+ "actualPrice = (SELECT SUM(actualPrice) actualPrice FROM receive_bill WHERE checkId = " + checkBillId + "),"
				+ "chargePrice = (SELECT SUM(chargePrice) chargePrice FROM receive_bill WHERE checkId = " + checkBillId + "),"
				+ "holdPrice = (SELECT SUM(holdPrice) holdPrice FROM receive_bill WHERE checkId = " + checkBillId + "),"
				+ "returnPrice = (SELECT SUM(returnPrice) returnPrice FROM receive_bill WHERE checkId = " + checkBillId + "),"
				+ "otherPrice = (SELECT SUM(otherPrice) otherPrice FROM receive_bill WHERE checkId = " + checkBillId + ") where id = " + checkBillId;
		Query query = em.createNativeQuery(sql);
		query.executeUpdate();
		em.clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> statBill(CheckBillVO vo) {
		String sql = "SELECT customCde,customName, SUM(countPrice),SUM(receivePrice),SUM(actualPrice),SUM(chargePrice),SUM(returnPrice),SUM(holdPrice),SUM(otherPrice)"
				+ " FROM check_bill where 1 = 1 ";
		if(StringUtils.isNotBlank(vo.getCustomName()))
			sql += " and customName like '%" + vo.getCustomName().trim() + "%' ";
		if(StringUtils.isNotBlank(vo.getBeginTime()))
			sql +=" and createDate >= '"+vo.getBeginTime()+"' ";
		if(StringUtils.isNotBlank(vo.getEndTime()))
			sql += " and createDate <= '"+vo.getEndTime()+"' ";
		if(vo.getUserId() != null)
			sql += " and customCde in (select customCde from info_custom where userId =" + vo.getUserId() + ")";
		if(Common.checkList(vo.getAreaIds()))
			sql += " and customCde in (select customCde from info_custom where areaId in (" + vo.getAreaIds().toString().replace("[", "").replace("]", "") + "))";
		if(Common.checkList(vo.getParentAreaIds()))
			sql += " and customCde in (select customCde from info_custom c left join info_area a on c.areaId = a.id where a.parentId in ("+ vo.getParentAreaIds().toString().replace("[", "").replace("]", "") +"))";
		
		sql += "GROUP BY customCde ";
		
		Query query = em.createNativeQuery(sql);
				
		return query.getResultList();
	}

	@Override
	public void exprotStatBill(String[] heads, Object[] object,Map<String, String> map) throws Exception {
		String[] header=Common.getExportHeader("STATBILL"); 
		for(String key:heads){
			int i=0;
			if(key.equals(header[i++])){
				map.put(key, object[0].toString()); 								//"客户编号"
			}else if(key.equals(header[i++])){
				map.put(key, object[1].toString()); 								//"客户名称"
			}else if(key.equals(header[i++])){
				map.put(key, PublicType.setDoubleScale((Double)object[2]).toString());
			}else if(key.equals(header[i++])){
				map.put(key, PublicType.setDoubleScale((Double)object[3]).toString()); 								//"已收款合计"
			}else if(key.equals(header[i++])){
				map.put(key, PublicType.setDoubleScale((Double)object[4]).toString()); 								//"实际收款额"
			}else if(key.equals(header[i++])){
				map.put(key, PublicType.setDoubleScale((Double)object[5]).toString()); 								//"待费用发票额"
			}else if(key.equals(header[i++])){
				map.put(key, PublicType.setDoubleScale((Double)object[6]).toString()); 								//"待退货额"
			}else if(key.equals(header[i++])){
				map.put(key, PublicType.setDoubleScale((Double)object[7]).toString()); 								//"暂扣额"
			}else if(key.equals(header[i++])){
				map.put(key, PublicType.setDoubleScale((Double)object[8]).toString()); 								//"其他余额"
			}
		}
		
	}

	@Override
	public void updateState(Long checkBillId) throws Exception {
		//处理表头状态
		CheckBillVO billVO = getVOById(checkBillId);
		if(PublicType.setDoubleScale(billVO.getReceivePrice()).equals(PublicType.setDoubleScale(billVO.getCountPrice()))){
			billVO.setState(ReceiveState.YHX);
			saveOrUpdate(billVO);
		}
		
	}

	@Override
	public int delete(String ids) {
		
		return 0;
	}

	
	
}
