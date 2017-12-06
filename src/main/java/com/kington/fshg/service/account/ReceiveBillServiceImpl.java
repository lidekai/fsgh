package com.kington.fshg.service.account;

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
import com.kington.fshg.common.PublicType.ActType;
import com.kington.fshg.common.PublicType.IsType;
import com.kington.fshg.common.PublicType.ReceiveState;
import com.kington.fshg.model.account.CheckBill;
import com.kington.fshg.model.account.CheckBillVO;
import com.kington.fshg.model.account.CheckRecord;
import com.kington.fshg.model.account.CheckRecordVO;
import com.kington.fshg.model.account.ReceiptBill;
import com.kington.fshg.model.account.ReceiptBillVO;
import com.kington.fshg.model.account.ReceiveBill;
import com.kington.fshg.model.account.ReceiveBillVO;
import com.kington.fshg.service.BaseServiceImpl;

public class ReceiveBillServiceImpl extends
		BaseServiceImpl<ReceiveBill, ReceiveBillVO> implements
		ReceiveBillService {

	private static final long serialVersionUID = 585767560480406944L;
	
	@Resource
	private CheckBillService checkBillService;
	@Resource
	private ReceiptBillService receiptService;
	@Resource
	private CheckRecordService checkRecordService;

	@Override
	protected String getQueryStr(ReceiveBillVO vo) throws Exception {
		StringBuffer sql = new StringBuffer();
		
		if(StringUtils.isNotBlank(vo.getCsbvcode())){
			sql.append(" and o.csbvcode = :csbvcode");
		}
		if(StringUtils.isNotBlank(vo.getBeginTime()))
			sql.append(" and o.billDate >= :beginTime ");
		if(StringUtils.isNotBlank(vo.getEndTime()))
			sql.append(" and o.billDate <= :endTime ");
		
		if(StringUtils.isNotBlank(vo.getCustomName())){
			sql.append(" and o.customName like :customName");
		}
		
		if(vo.getState() != null)
			sql.append(" and o.state = :state ");
		
		if(StringUtils.isNotBlank(vo.getAreaName()))
			sql.append(" and o.customCde in (select customCde from Custom where area.areaName = :areaName)");
		
		if(Common.checkLong(vo.getCustomTypeId()))
			sql.append(" and o.customCde in (select customCde from Custom where customType.id = :customTypeId)");
		
		if(vo.getUserId() != null)
			sql.append(" and o.customCde in (select customCde from Custom where user.id = :userId)");
		if(Common.checkList(vo.getAreaIds()))
			sql.append(" and o.customCde in (select customCde from Custom where area.id in (:areaIds))");
		if(Common.checkList(vo.getParentAreaIds()))
			sql.append(" and o.customCde in (select customCde from Custom where area.parentArea.id in (:parentAreaIds))");
		
		return sql.toString();
	}

	@Override
	protected void setQueryParm(Query query, ReceiveBillVO vo) throws Exception {
		if(StringUtils.isNotBlank(vo.getCsbvcode())){
			query.setParameter("csbvcode", vo.getCsbvcode());
		}
		if(StringUtils.isNotBlank(vo.getBeginTime())){
			query.setParameter("beginTime", DateFormat.str2Date(vo.getBeginTime(), 2) );
		}
		if(StringUtils.isNotBlank(vo.getEndTime())){
			query.setParameter("endTime", DateFormat.str2Date(vo.getEndTime(), 2));
		}
		if(StringUtils.isNotBlank(vo.getCustomName())){
			query.setParameter("customName", Common.SYMBOL_PERCENT +vo.getCustomName()+Common.SYMBOL_PERCENT );
		}
		
		if(vo.getState() != null)
			query.setParameter("state", vo.getState());
		
		if(StringUtils.isNotBlank(vo.getAreaName()))
			query.setParameter("areaName", vo.getAreaName());
		
		if(Common.checkLong(vo.getCustomTypeId()))
			query.setParameter("customTypeId", vo.getCustomTypeId());
		
		if(Common.checkList(vo.getAreaIds()))
			query.setParameter("areaIds", vo.getAreaIds());
		if(Common.checkList(vo.getParentAreaIds()))
			query.setParameter("parentAreaIds", vo.getParentAreaIds());
		if(vo.getUserId() != null)
			query.setParameter("userId", vo.getUserId());
		
	}

	@Override
	protected void switchVO2PO(ReceiveBillVO vo, ReceiveBill po)
			throws Exception {
		if(vo.getCountPrice1() != null){
			po.setCountPrice1(vo.getCountPrice1());
			po.setChargePrice(po.getCountPrice1());
		}
		if(vo.getReceivePrice() != null)
			po.setReceivePrice(vo.getReceivePrice());
		if(vo.getChargePrice() != null)
			po.setChargePrice(vo.getChargePrice());
		if(vo.getHoldPrice() != null)
			po.setHoldPrice(vo.getHoldPrice());
		if(vo.getReturnPrice() != null)
			po.setReturnPrice(vo.getReturnPrice());
		if(vo.getOtherPrice() != null)
			po.setOtherPrice(vo.getOtherPrice());
		if(vo.getActualPrice() != null)
			po.setActualPrice(vo.getActualPrice());
		
		if(vo.getState() != null)
			po.setState(vo.getState());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> countByVo(ReceiveBillVO vo, ActType act) {
		/*String sql = "select sum(count), sum(countPrice), sum(countPrice1), sum(receivePrice), sum(chargePrice), "
				+ "sum(returnPrice), sum(holdPrice), sum(otherPrice) from receive_bill where 1 = 1 ";*/
		String sql = "select sum(count), sum(countPrice), sum(countPrice1), sum(receivePrice), sum(chargePrice),sum(actualPrice),  "
				+ "sum(returnPrice), sum(holdPrice), sum(otherPrice) from receive_bill where 1 = 1 ";
		
		if(StringUtils.isNotBlank(vo.getBeginTime()))
			sql+=" and billDate >= '"+vo.getBeginTime()+"'";
		if(StringUtils.isNotBlank(vo.getEndTime()))
			sql+=" and billDate <= '"+vo.getEndTime()+"'";
		
		if(StringUtils.isNotBlank(vo.getCsbvcode()))
			sql += " and csbvcode = '" + vo.getCsbvcode() + "' ";
		if(StringUtils.isNotBlank(vo.getCustomName()))
			sql += " and customName like '%" + vo.getCustomName() + "%' ";
		if(vo.getState() != null && act == null)
			sql += " and state = '" + vo.getState() + "'";
		if(act == ActType.COUNT)
			sql += " and state <> 'WCL'";
		
		
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
	public int impReceiveBillFromU8(ReceiveBillVO vo) {
		Connection conn=null;
		Statement state=null;
		ResultSet rs=null;
		int count=0;
		try {
			String sql="select d.cCCname as 客户类型,c.cCusDefine3 as 客户地区,"
					+ "(select top 1 gl_accvouch.csign+'-'+convert(varchar,gl_accvouch.ino_id) from gl_accvouch where gl_accvouch.coutno_id=aa.cclue)  as 凭证号,"
					+ "bb.csbvcode as 销售发票号,bb.ddate as 发票日期,bb.ccuscode as 客户编码,c.ccusname as 客户名称,"
					+ "bb.dGatheringDate as 到期日,sum(aa.iNatSum) as 本币发票额   from SaleBillVouchs aa "
					+ "left join SaleBillVouch bb on bb.sbvid = aa.sbvid "
					+ "left join customer c on c.ccuscode=bb.ccuscode "
					+ "left join CustomerClass d on d.cCCCode=c.cCCCode "
					+ "where 1 = 1 ";
			
			if(StringUtils.isNotBlank(vo.getBeginTime()))
				sql += " and bb.ddate >= '" + vo.getBeginTime() + "' "; 
			if(StringUtils.isNotBlank(vo.getEndTime()))
				sql += " and bb.ddate <= '" + vo.getEndTime() + "' ";
			
			if(StringUtils.isNotBlank(vo.getCsbvcode())){
				sql+=" and bb.csbvcode =' "+vo.getCsbvcode() +"'";
			}
			if(StringUtils.isNotBlank(vo.getCustomName())){
				sql+=" and c.ccusname like '& "+vo.getCustomName() +"' ";
			}
			
			if(StringUtils.isNotBlank(vo.getAreaName()))
				sql += " and c.cCusDefine3 = '" + vo.getAreaName() + "' ";
					
			sql +="group by d.cCCname,c.cCusDefine3,aa.cClue,bb.csbvcode,bb.ddate,bb.ccuscode,c.ccusname,bb.dGatheringDate "
				+ "order by bb.ddate,bb.csbvcode ";
			conn = PublicType.getConn();
			state = conn.createStatement();
			rs = state.executeQuery(sql);
			System.out.println(sql);
			//System.out.println(rs.next());
			while(rs.next()){
				String customCde = rs.getString(6);
				String csbvcode = rs.getString(4);
				
				if(StringUtils.isBlank(customCde) || StringUtils.isBlank(csbvcode)){
					continue;
				}
				
				ReceiveBill po = getByCsbvcode(csbvcode);
				if(po == null)
					po = new ReceiveBill();
				po.setCustomType(rs.getString(1));
				po.setCustomArea(rs.getString(2));
				po.setNumber(rs.getString(3));
				po.setCsbvcode(rs.getString(4));
				if(StringUtils.isNotBlank(rs.getString(5))){
					po.setBillDate(DateFormat.str2Date(rs.getString(5), 2));
				}
				po.setCustomCde(rs.getString(6));
				po.setCustomName(rs.getString(7));
				if(StringUtils.isNotBlank(rs.getString(8))){
					po.setMaturityDate(DateFormat.str2Date(rs.getString(8), 2));
				}
				if(StringUtils.isNotBlank(rs.getString(9))){
					po.setCountPrice(PublicType.setDoubleScale(Double.parseDouble(rs.getString(9))));				
				}
				if(po.getCountPrice() != null && po.getState() == ReceiveState.WCL){
					po.setCountPrice1(po.getCountPrice());
					po.setChargePrice(po.getCountPrice1());
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
	/*@Override
	public int impReceiveBillFromU8(ReceiveBillVO vo) {
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
					+ " aa.iNatSum as 本币价税合计,aa.iNatMoney as 本币无税金额  , bb.dGatheringDate as 到期日期  " 
					+ " from SaleBillVouchs aa left join SaleBillVouch bb on bb.sbvid = aa.sbvid "
					+ " left join so_sodetails a on a.isosid = aa.isosid "
					+ " left join DispatchLists e on e.iDLsID = aa.iDLsID "
					+ " left join DispatchList ee on ee.dlid = e.dlid "
					+ " left join so_somain b on b.id=a.id "
					+ " left join customer c on c.ccuscode=bb.ccuscode "
					+ " left join inventory d on d.cinvcode=aa.cinvcode "
					+" where aa.iNatSum > 0 ";
			
			if(StringUtils.endsWith(vo.getTimeType(), "1")){
				if(StringUtils.isNotBlank(vo.getBeginTime()))
					sql += " and b.ddate >= '" + vo.getBeginTime() + "' "; 
				if(StringUtils.isNotBlank(vo.getEndTime()))
					sql += " and b.ddate <= '" + vo.getEndTime() + "' ";
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
			}else if(StringUtils.endsWith(vo.getTimeType(), "4")){
				if(StringUtils.isNotBlank(vo.getBeginTime()))
					sql += " and bb.dGatheringDate >= '" + vo.getBeginTime() + "' "; 
				if(StringUtils.isNotBlank(vo.getEndTime()))
					sql += " and bb.dGatheringDate <= '" + vo.getEndTime() + "' ";
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
				ReceiveBill po=this.getByAutoId(autoId);
				if(po==null){
					po=new ReceiveBill();
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
				if(po.getCountPrice() != null && po.getState() == ReceiveState.WCL){
					po.setCountPrice1(po.getCountPrice());
					po.setChargePrice(po.getCountPrice1());
				}
				
				if(StringUtils.isNotBlank(rs.getString(17))){
					po.setNoTaxPrice(PublicType.setDoubleScale(Double.parseDouble(rs.getString(17))));				
				}
				if(StringUtils.isNotBlank(rs.getString(18))){
					po.setMaturityDate(DateFormat.str2Date(rs.getString(18), 9));
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
	}*/

	@SuppressWarnings("unchecked")
	@Override
	public ReceiveBill getByAutoId(String autoId) throws Exception {
		String sql= "from ReceiveBill where autoId = "+ autoId;
		Query query = em.createQuery(sql);
		List<ReceiveBill> list=query.getResultList();
		if(Common.checkList(list)){
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReceiveBill> receiveBill(ReceiveBillVO vo) throws Exception {
		String hql="select o from ReceiveBill o where 1=1 ";
		hql += getQueryStr(vo);
		if(StringUtils.isNotBlank(vo.getMyQueryStr()))
			hql +=  vo.getMyQueryStr();
		hql += " order by o.createDate ";
		Query query=em.createQuery(hql);
		setQueryParm(query, vo);
		return query.getResultList();
	}

	@Override
	public void exprotReceiveBill(String[] heads, ReceiveBill rb,Map<String, String> map) throws Exception {
		String[] header=Common.getExportHeader("RECEIVEBILL"); 
		for(String key:heads){
			int i=0;
			if(key.equals(header[i++])){
				map.put(key, rb.getCsbvcode());     		//"销售发票号"
			}else if(key.equals(header[i++])){
				map.put(key, rb.getNumber());     		//"凭证号"
			}else if(key.equals(header[i++])){
				map.put(key, DateFormat.date2Str(rb.getBillDate(), 2)); //"发票日期"
			}else if(key.equals(header[i++])){
				map.put(key, DateFormat.date2Str(rb.getMaturityDate(), 2)); //"到期日期"
			}else if(key.equals(header[i++])){
				map.put(key, rb.getCustomType()); 								//"客户类型"
			}else if(key.equals(header[i++])){
				map.put(key, rb.getCustomArea()); 								//"客户地区"
			}else if(key.equals(header[i++])){
				map.put(key, rb.getCustomCde()); 								//"客户编码"
			}else if(key.equals(header[i++])){
				map.put(key, rb.getCustomName()); 								//"客户名称"
			}else if(key.equals(header[i++])){
				if(rb.getCountPrice()!=null){
					map.put(key, rb.getCountPrice().toString());				//本币发票额
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				if(rb.getCountPrice1()!=null){
					map.put(key, rb.getCountPrice1().toString());				//本币实际发票额
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
	public Boolean createCheck(String ids) throws Exception {
		Boolean flage = false;
		
		ids = ids.substring(0,ids.length() - 1);
		String sql = "select sum(countPrice1) from receive_bill where id in(" + ids + ")";
		Query query = em.createNativeQuery(sql);
		Double count = PublicType.setDoubleScale((Double)query.getResultList().get(0));
		
		String customCde = "";
		String customName = "";
		String customType = "";
		String customArea = "";
		if(StringUtils.isNotBlank(ids.split(",")[0])){
			ReceiveBill bill = this.getById(Long.parseLong(ids.split(",")[0]));
			if(bill != null){
				customCde = bill.getCustomCde();
				customName = bill.getCustomName();
				customType = bill.getCustomType();
				customArea = bill.getCustomArea();
			}
		}
		
		if(StringUtils.isNotBlank(customName) && StringUtils.isNotBlank(customCde)){
			CheckBillVO checkBillVO = new CheckBillVO();
			checkBillVO.setCreateDate(DateFormat.str2Date(DateFormat.date2Str(new Date(), 2), 2));
			checkBillVO.setCode(checkBillService.getCode());
			checkBillVO.setCountPrice(count);
			checkBillVO.setChargePrice(count);
			checkBillVO.setCustomCde(customCde);
			checkBillVO.setCustomName(customName);
			checkBillVO.setCustomArea(customArea);
			checkBillVO.setCustomType(customType);
			checkBillVO.setState(ReceiveState.DHX);
			
			CheckBill checkBill = checkBillService.saveOrUpdate(checkBillVO);
			sql = "update receive_bill set state='DHX',checkId=" + checkBill.getId() + " where id in(" + ids + ")";
			query = em.createNativeQuery(sql);
			int success = query.executeUpdate();
			if(success == ids.split(",").length)
				flage = true;
			em.clear();
		} 
		
		return flage;
	}

	@Override
	public void saveCheckRecord(ReceiveBillVO receiveBillVO, Long receiptId,CheckRecordVO checkRecordVO) throws Exception{
		ReceiveBill receiveBill = this.getById(receiveBillVO.getId());
		ReceiptBill receiptBill = receiptService.getById(receiptId);
		
		//保存核销前各项结果到记录表
		checkRecordVO.setReceivePriceOld(receiveBill.getReceivePrice());
		checkRecordVO.setChargePriceOld(receiveBill.getChargePrice());
		checkRecordVO.setReturnPriceOld(receiveBill.getReturnPrice());
		checkRecordVO.setHoldPriceOld(receiveBill.getHoldPrice());
		checkRecordVO.setOtherPriceOld(receiveBill.getOtherPrice());
		checkRecordVO.setActualPriceOld(receiveBill.getActualPrice());
		
		checkRecordVO.setReceiptCount(receiptBill.getReceiveCount());
		
		checkRecordVO.setReceiveBill(receiveBill);
		checkRecordVO.setReceiptBill(receiptBill);
		
		CheckRecord checkRecord = checkRecordService.saveOrUpdate(checkRecordVO);
		
		if(checkRecord.getReceivePrice() != null && checkRecord.getReceivePrice() != 0){
			//收款单修改未核余额
			receiptBill.setReceiveCount(PublicType.setDoubleScale(receiptBill.getReceiveCount() - checkRecord.getReceivePrice()));
			ReceiptBillVO receiptVO = new ReceiptBillVO();
			receiptVO.setId(receiptBill.getId());
			receiptVO.setReceiveCount(receiptBill.getReceiveCount());
			receiptService.saveOrUpdate(receiptVO);
			
			//应收单明细修改收款额
			receiveBillVO.setReceivePrice(PublicType.setDoubleScale(receiveBill.getReceivePrice() + checkRecord.getReceivePrice()));
		}
		
		if(receiveBillVO.getChargePrice() == null && checkRecord.getChargePrice() != null && checkRecord.getChargePrice() != 0)
			receiveBillVO.setChargePrice(PublicType.setDoubleScale(checkRecord.getChargePriceOld() - checkRecord.getChargePrice()));
		if(receiveBillVO.getReturnPrice() == null && checkRecord.getReturnPrice() != null && checkRecord.getReturnPrice() != 0)
			receiveBillVO.setReturnPrice(PublicType.setDoubleScale(checkRecord.getReturnPriceOld() - checkRecord.getReturnPrice()));
		if(receiveBillVO.getHoldPrice() == null && checkRecord.getHoldPrice() != null && checkRecord.getHoldPrice() != 0)
			receiveBillVO.setHoldPrice(PublicType.setDoubleScale(checkRecord.getHoldPriceOld() - checkRecord.getHoldPrice()));
		if(receiveBillVO.getOtherPrice() == null && checkRecord.getOtherPrice() != null && checkRecord.getOtherPrice() != 0)
			receiveBillVO.setOtherPrice(PublicType.setDoubleScale(checkRecord.getOtherPriceOld() - checkRecord.getOtherPrice()));
		if(receiveBillVO.getActualPrice() == null && checkRecord.getActualPrice() != null && checkRecord.getActualPrice() != 0)
			receiveBillVO.setActualPrice(PublicType.setDoubleScale(checkRecord.getActualPriceOld() - checkRecord.getActualPrice()));
		
		if(receiveBillVO.getReceivePrice().equals(receiveBill.getCountPrice1()))
			receiveBillVO.setState(ReceiveState.YHX);
		
		this.saveOrUpdate(receiveBillVO);
		
	}

	@Override
	public void qxCheckRecord(Long recordId) throws Exception {
		CheckRecord record = checkRecordService.getById(recordId);
		//应收单修改
		ReceiveBillVO receiveBillVO = new ReceiveBillVO();
		receiveBillVO.setId(record.getReceiveBill().getId());
		receiveBillVO.setReceivePrice(record.getReceivePriceOld());
		receiveBillVO.setChargePrice(record.getChargePriceOld());
		receiveBillVO.setReturnPrice(record.getReturnPriceOld());
		receiveBillVO.setHoldPrice(record.getHoldPriceOld());
		receiveBillVO.setOtherPrice(record.getOtherPriceOld());
		receiveBillVO.setActualPrice(record.getActualPriceOld());
		this.saveOrUpdate(receiveBillVO);
		//收款单修改
		ReceiptBillVO receiptBillVO = new ReceiptBillVO();
		receiptBillVO.setId(record.getReceiptBill().getId());
		receiptBillVO.setReceiveCount(PublicType.setDoubleScale(record.getReceiptBill().getReceiveCount() + record.getReceivePrice()));
		receiptService.saveOrUpdate(receiptBillVO);
		//记录修改状态
		CheckRecordVO recordVO = new CheckRecordVO();
		recordVO.setId(record.getId());
		recordVO.setState(ReceiveState.YQX);
		checkRecordService.saveOrUpdate(recordVO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> statAccount(ReceiveBillVO vo) throws Exception {
		String ye = "(countPrice1 - receivePrice)";
		String zq = "(TO_DAYS('" + vo.getStatTime() + "') - TO_DAYS(maturityDate))";
		
		String sql = "SELECT customCde,customName,ROUND(SUM" + ye + ",2) ye,"
				+ "ROUND(SUM(CASE WHEN " + zq + " <= 30 THEN " + ye + " ELSE 0 END),2) AS ye1,"
				+ "ROUND(SUM(CASE WHEN " + zq + " > 30 AND " + zq + " <= 60 THEN " + ye + "ELSE 0 END),2) AS ye2,"
				+ "ROUND(SUM(CASE WHEN " + zq + " > 60 AND " + zq + " <= 90 THEN " + ye + "ELSE 0 END),2) AS ye3,"
				+ "ROUND(SUM(CASE WHEN " + zq + " > 91 AND " + zq + " <= 120 THEN " + ye + "ELSE 0 END),2) AS ye4,"
				+ "ROUND(SUM(CASE WHEN " + zq + " > 121 AND " + zq + " <= 150 THEN " + ye + "ELSE 0 END),2) AS ye5,"
				+ "ROUND(SUM(CASE WHEN " + zq + " > 151 AND " + zq + " <= 180 THEN " + ye + "ELSE 0 END),2) AS ye6,"
				+ "ROUND(SUM(CASE WHEN " + zq + " > 181 AND " + zq + " <= 365 THEN " + ye + "ELSE 0 END),2) AS ye7,"
				+ "ROUND(SUM(CASE WHEN " + zq + " > 367 AND " + zq + " <= 730 THEN " + ye + "ELSE 0 END),2) AS ye8,"
				+ "ROUND(SUM(CASE WHEN " + zq + " > 731 AND " + zq + " <= 1095 THEN " + ye + "ELSE 0 END),2) AS ye9,"
				+ "ROUND(SUM(CASE WHEN " + zq + " > 1095 THEN " + ye + " ELSE 0 END),2) AS ye10 FROM receive_bill where 1=1 ";
		
		if(StringUtils.isNotBlank(vo.getBeginTime()))
			sql+=" and billDate >= '"+vo.getBeginTime()+"'";
		if(StringUtils.isNotBlank(vo.getEndTime()))
			sql+=" and billDate <= '"+vo.getEndTime()+"'";
		
		if(StringUtils.isNotBlank(vo.getCustomName()))
			sql += " and customName like '%" + vo.getCustomName() + "%' ";
		
		if(vo.getUserId() != null)
			sql += " and customCde in (select customCde from info_custom where userId =" + vo.getUserId() + ")";
		if(Common.checkList(vo.getAreaIds()))
			sql += " and customCde in (select customCde from info_custom where areaId in (" + vo.getAreaIds().toString().replace("[", "").replace("]", "") + "))";
		if(Common.checkList(vo.getParentAreaIds()))
			sql += " and customCde in (select customCde from info_custom c left join info_area a on c.areaId = a.id where a.parentId in ("+ vo.getParentAreaIds().toString().replace("[", "").replace("]", "") +"))";

		sql += " GROUP BY customCde ";
		Query query = em.createNativeQuery(sql);
		return query.getResultList();
	}

	@Override
	public void exprotStatAccount(String[] heads, Object[] object,
			Map<String, String> map) throws Exception {
		String[] header=Common.getExportHeader("STATACCOUNT"); 
		for(String key:heads){
			int i=0;
			if(key.equals(header[i++])){
				map.put(key, object[0].toString()); 								//"客户编号"
			}else if(key.equals(header[i++])){
				map.put(key, object[1].toString()); 								//"客户名称"
			}else if(key.equals(header[i++])){
				map.put(key, PublicType.setDoubleScale((Double)object[2]).toString());
			}else if(key.equals(header[i++])){
				map.put(key, PublicType.setDoubleScale((Double)object[3]).toString()); 						
			}else if(key.equals(header[i++])){
				map.put(key, PublicType.setDoubleScale((Double)object[4]).toString()); 							
			}else if(key.equals(header[i++])){
				map.put(key, PublicType.setDoubleScale((Double)object[5]).toString()); 								
			}else if(key.equals(header[i++])){
				map.put(key, PublicType.setDoubleScale((Double)object[6]).toString()); 								
			}
		}
	}

	@Override
	public Integer autoHx(List<ReceiveBill> receiveBillList,
			List<ReceiptBill> receiptBillList, IsType isType) throws Exception {
		int count = 0;
		
		if(Common.checkList(receiveBillList) && Common.checkList(receiptBillList)){
			if(isType == IsType.Y){//先是否先核销收款金额与应收单待核销价税合计相等的单据
				for(ReceiptBill receiptBill : receiptBillList){//将收款单全部核销完毕结束
					if(receiptBill.getReceiveCount() > 0){//收款单未核销金额大于0
						for(ReceiveBill receiveBill : receiveBillList){
							if(receiveBill.getCustomCde().equals(receiptBill.getCustomerCde())
									&& receiptBill.getReceiveCount().equals(receiveBill.getCountPrice1() - receiveBill.getReceivePrice())
									&& receiptBill.getReceiveCount() > 0){
								//保存核销记录
								CheckRecord checkRecord = saveCheckRecord(receiveBill,receiptBill);
								//本次核销
								checkRecord.setReceivePrice(receiptBill.getReceiveCount());
								checkRecord.setActualPrice(receiveBill.getActualPrice());								
								checkRecord.setChargePrice(receiveBill.getChargePrice());
								checkRecord.setHoldPrice(receiveBill.getHoldPrice());
								checkRecord.setReturnPrice(receiveBill.getReturnPrice());
								checkRecord.setOtherPrice(receiveBill.getOtherPrice());
								checkRecord.setUpdateTime(new Date());
								em.merge(checkRecord);
								
								//应收单修改各项金额
								receiveBill.setReceivePrice(receiveBill.getReceivePrice() + receiptBill.getReceiveCount());
								receiveBill.setActualPrice(0d);
								receiveBill.setChargePrice(0d);
								receiveBill.setHoldPrice(0d);
								receiveBill.setReturnPrice(0d);
								receiveBill.setOtherPrice(0d);
								receiveBill.setState(ReceiveState.YHX);
								receiveBill.setUpdateTime(new Date());
								em.merge(receiveBill);
								
								//收款单未核销金额变为0
								receiptBill.setReceiveCount(0d);
								receiptBill.setUpdateTime(new Date());
								em.merge(receiptBill);
								
								count ++;
							}								
						}
					}
				}
			}
			
			for(ReceiptBill receiptBill : receiptBillList){//将收款单全部核销完毕结束
				if(receiptBill.getReceiveCount() > 0){//收款单未核销金额大于0
					for(ReceiveBill receiveBill : receiveBillList){
						if(receiveBill.getCustomCde().equals(receiptBill.getCustomerCde())
								&& receiveBill.getState() == ReceiveState.DHX
								&& receiptBill.getReceiveCount() > 0){
							//保存核销记录
							CheckRecord checkRecord = saveCheckRecord(receiveBill,receiptBill);
							//应收单修改各项金额
							Double receiveCountOld = receiptBill.getReceiveCount();
							if(receiptBill.getReceiveCount() > 0){
								if(receiptBill.getReceiveCount() >=  receiveBill.getActualPrice()){
									checkRecord.setActualPrice(receiveBill.getActualPrice());
									receiptBill.setReceiveCount(PublicType.setDoubleScale(receiptBill.getReceiveCount() - receiveBill.getActualPrice()));
									receiveBill.setActualPrice(0d);
								}else{
									checkRecord.setActualPrice(receiptBill.getReceiveCount());
									receiveBill.setActualPrice(PublicType.setDoubleScale(receiveBill.getActualPrice() - receiptBill.getReceiveCount()));
									receiptBill.setReceiveCount(0d);
								}
							}
							if(receiptBill.getReceiveCount() > 0){
								if(receiptBill.getReceiveCount() >=  receiveBill.getChargePrice()){
									checkRecord.setChargePrice(receiveBill.getChargePrice());
									receiptBill.setReceiveCount(PublicType.setDoubleScale(receiptBill.getReceiveCount() - receiveBill.getChargePrice()));
									receiveBill.setChargePrice(0d);
								}else{
									checkRecord.setChargePrice(receiptBill.getReceiveCount());
									receiveBill.setChargePrice(PublicType.setDoubleScale(receiveBill.getChargePrice() - receiptBill.getReceiveCount()));
									receiptBill.setReceiveCount(0d);
								}
							}
							if(receiptBill.getReceiveCount() > 0){
								if(receiptBill.getReceiveCount() >=  receiveBill.getReturnPrice()){
									checkRecord.setReturnPrice(receiveBill.getReturnPrice());
									receiptBill.setReceiveCount(PublicType.setDoubleScale(receiptBill.getReceiveCount() - receiveBill.getReturnPrice()));
									receiveBill.setReturnPrice(0d);
								}else{
									checkRecord.setReturnPrice(receiptBill.getReceiveCount());
									receiveBill.setReturnPrice(PublicType.setDoubleScale(receiveBill.getReturnPrice() - receiptBill.getReceiveCount()));
									receiptBill.setReceiveCount(0d);
								}
								
							}
							if(receiptBill.getReceiveCount() > 0){
								if(receiptBill.getReceiveCount() >=  receiveBill.getHoldPrice()){
									checkRecord.setHoldPrice(receiveBill.getHoldPrice());
									receiptBill.setReceiveCount(PublicType.setDoubleScale(receiptBill.getReceiveCount() - receiveBill.getHoldPrice()));
									receiveBill.setHoldPrice(0d);
								}else{
									checkRecord.setHoldPrice(receiptBill.getReceiveCount());
									receiveBill.setHoldPrice(PublicType.setDoubleScale(receiveBill.getHoldPrice() - receiptBill.getReceiveCount()));
									receiptBill.setReceiveCount(0d);
								}
							}
							if(receiptBill.getReceiveCount() > 0){
								if(receiptBill.getReceiveCount() >=  receiveBill.getOtherPrice()){
									checkRecord.setOtherPrice(receiveBill.getOtherPrice());
									receiptBill.setReceiveCount(PublicType.setDoubleScale(receiptBill.getReceiveCount() - receiveBill.getOtherPrice()));
									receiveBill.setOtherPrice(0d);
								}else{
									checkRecord.setOtherPrice(receiptBill.getReceiveCount());
									receiveBill.setOtherPrice(PublicType.setDoubleScale(receiveBill.getReturnPrice() - receiptBill.getReceiveCount()));
									receiptBill.setReceiveCount(0d);
								}
							}
							
							receiveBill.setReceivePrice(PublicType.setDoubleScale(receiveBill.getReceivePrice() + receiveCountOld - receiptBill.getReceiveCount()));
							checkRecord.setReceivePrice(receiveCountOld - receiptBill.getReceiveCount());
							checkRecord.setUpdateTime(new Date());
							em.merge(checkRecord);
							
							if(receiveBill.getReceivePrice().equals(receiveBill.getCountPrice1()))
								receiveBill.setState(ReceiveState.YHX);
							receiveBill.setUpdateTime(new Date());
							em.merge(receiveBill);
							
							//收款单保存
							receiptBill.setUpdateTime(new Date());
							em.merge(receiptBill);
							
							count ++;
						}
					}
				}
			}
		}
		return count;
	}
	
	private CheckRecord saveCheckRecord(ReceiveBill receiveBill, ReceiptBill receiptBill) throws Exception{
		CheckRecordVO checkRecordVO = new CheckRecordVO();
		//核销前记录
		checkRecordVO.setReceivePriceOld(receiveBill.getReceivePrice());
		checkRecordVO.setChargePriceOld(receiveBill.getChargePrice());
		checkRecordVO.setHoldPriceOld(receiveBill.getHoldPrice());
		checkRecordVO.setReturnPriceOld(receiveBill.getReturnPrice());
		checkRecordVO.setOtherPriceOld(receiveBill.getOtherPrice());
		checkRecordVO.setActualPriceOld(receiveBill.getActualPrice());
		//收款单未核金额
		checkRecordVO.setReceiptCount(receiptBill.getReceiveCount());
		
		checkRecordVO.setReceiveBill(receiveBill);
		checkRecordVO.setReceiptBill(receiptBill);
		
		return checkRecordService.saveOrUpdate(checkRecordVO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ReceiveBill getByCsbvcode(String csbvcode) throws Exception {
		String sql= "from ReceiveBill where csbvcode = '"+ csbvcode + "'";
		Query query = em.createQuery(sql);
		List<ReceiveBill> list=query.getResultList();
		if(Common.checkList(list)){
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReceiveBill> getListByIds(String ids) throws Exception {
		String sql= "from ReceiveBill where id in (" + ids + ") order by billDate ";
		Query query = em.createQuery(sql);
		return query.getResultList();
	}
	
}
