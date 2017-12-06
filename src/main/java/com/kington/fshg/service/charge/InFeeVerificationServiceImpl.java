package com.kington.fshg.service.charge;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
import com.kington.fshg.common.PublicType.DictType;
import com.kington.fshg.model.charge.InFeeVerification;
import com.kington.fshg.model.charge.InFeeVerificationVO;
import com.kington.fshg.model.system.Dict;
import com.kington.fshg.service.BaseServiceImpl;
import com.kington.fshg.service.system.DictService;

public class InFeeVerificationServiceImpl extends BaseServiceImpl<InFeeVerification, InFeeVerificationVO>
		implements InFeeVerificationService {
	private static final long serialVersionUID = 4301947478187806660L;
	
	@Resource
	private DictService dictService;

	@Override
	protected String getQueryStr(InFeeVerificationVO vo) throws Exception {
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
		if(vo.getInFeeProvision() != null && StringUtils.isNotBlank(vo.getInFeeProvision().getCode()))
			sb.append(" and o.inFeeProvision.code = :provisionCode ");
		if(StringUtils.isNotBlank(vo.getVerCode()))
			sb.append(" and o.verCode like :verCode ");
		if(vo.getVerDirection() != null)
			sb.append(" and o.verDirection = :verDirection ");
		if(vo.getVerType() != null && StringUtils.isNotBlank(vo.getVerType().getDictName()))
			sb.append(" and o.verType.dictName = :dictName ");
		if(vo.getApproveState() != null)
			sb.append(" and o.approveState = :approveState ");
		if(StringUtils.isNotBlank(vo.getBeginTime()))
			sb.append(" and o.verDate >= :beginTime ");
		if(StringUtils.isNotBlank(vo.getEndTime()))
			sb.append(" and o.verDate < :endTime ");
		if(Common.checkList(vo.getAreaIds()))
			sb.append(" and o.custom.area.id in (:areaIds)");
		if(Common.checkList(vo.getParentAreaIds()))
			sb.append(" and o.custom.area.parentArea.id in (:parentAreaIds)");
			sb.append(" and o.totalFee <> 0 ");
		return sb.toString();
	}

	@Override
	protected void setQueryParm(Query query, InFeeVerificationVO vo)
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
		if(vo.getInFeeProvision() != null && StringUtils.isNotBlank(vo.getInFeeProvision().getCode()))
			query.setParameter("provisionCode", vo.getInFeeProvision().getCode());
		if(StringUtils.isNotBlank(vo.getVerCode()))
			query.setParameter("verCode",Common.SYMBOL_PERCENT + vo.getVerCode() + Common.SYMBOL_PERCENT);
		if(vo.getVerDirection() != null)
			query.setParameter("verDirection", vo.getVerDirection());
		if(vo.getVerType() != null && StringUtils.isNotBlank(vo.getVerType().getDictName()))
			query.setParameter("dictName", vo.getVerType().getDictName());
		if(vo.getApproveState() != null)
			query.setParameter("approveState", vo.getApproveState());
		if(StringUtils.isNotBlank(vo.getBeginTime()))
			query.setParameter("beginTime", DateFormat.str2Date(vo.getBeginTime(), 2));
		if(StringUtils.isNotBlank(vo.getEndTime()))
			query.setParameter("endTime", DateFormat.getAfterDayFirst(DateFormat.str2Date(vo.getEndTime(), 2)));
		if(Common.checkList(vo.getAreaIds()))
			query.setParameter("areaIds", vo.getAreaIds());
		if(Common.checkList(vo.getParentAreaIds()))
			query.setParameter("parentAreaIds", vo.getParentAreaIds());
	}

	@Override
	protected void switchVO2PO(InFeeVerificationVO vo, InFeeVerification po)
			throws Exception {
		if(po == null)
			po = new InFeeVerification();
		if(StringUtils.isNotBlank(vo.getVerCode()))
			po.setVerCode(vo.getVerCode());
		if(vo.getCustom() != null)
			po.setCustom(vo.getCustom());
		if(vo.getInFeeProvision() != null)
			po.setInFeeProvision(vo.getInFeeProvision());
		if(vo.getVerDirection() != null)
			po.setVerDirection(vo.getVerDirection());
		if(vo.getVerType() != null)
			po.setVerType(vo.getVerType());
		if(vo.getEnterFee() != null)
			po.setEnterFee(vo.getEnterFee());
		if(vo.getYearReturnFee() != null)
			po.setYearReturnFee(vo.getYearReturnFee());
		if(vo.getFixedFee() != null)
			po.setFixedFee(vo.getFixedFee());
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
		if(vo.getFixedSponsorFee() != null)
			po.setFixedSponsorFee(vo.getFixedSponsorFee());
		if(vo.getApproveState() != null)
			po.setApproveState(vo.getApproveState());
		
		
		
		
		//生成U8收款单相关字段
		if(vo.getIsCreateU8() != null)
			po.setIsCreateU8(vo.getIsCreateU8());
		if(StringUtils.isNotBlank(vo.getMaker()))
			po.setMaker(vo.getMaker());
		if(StringUtils.isNotBlank(vo.getSummary()))
			po.setSummary(vo.getSummary());
		if(StringUtils.isNotBlank(vo.getBackItem()))
			po.setBackItem(vo.getBackItem());
		if(StringUtils.isNotBlank(vo.getCustomItem()))
			po.setCustomItem(vo.getCustomItem());
		if(StringUtils.isNotBlank(vo.getSsCode()))
			po.setSsCode(vo.getSsCode());
		
		if(vo.getVerDate() != null)
			po.setVerDate(vo.getVerDate());
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized String createCode() throws Exception {
		//预提编号格式：一个字母标识+年月（6位）+ 5位流水号
		String code = "C" + DateFormat.date2Str(new Date(), 4);
		String sql = "select verCode from charge_in_fee_verification where verCode like '"+ code +"%' order by verCode desc limit 1 ";
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

	@Override
	public void countTotalFee(InFeeVerification po) throws Exception {
		Double sum = 0d;
		if(po != null){
			sum = po.getEnterFee() + po.getFixedFee() + po.getYearReturnFee() 
				+ po.getMonthReturnFee() + po.getNetInfoFee() + po.getDeliveryFee()
				+ po.getPosterFee() + po.getPromotionFee() + po.getSponsorFee()
				+ po.getLossFee() + po.getFixedDiscount() + po.getPilesoilFee()
				+ po.getCaseReturnFee() + po.getMarketFee() + po.getOtherFee();
			po.setTotalFee(PublicType.setDoubleScale(sum));
			em.merge(po);
		}
	}

	@Override
	public Map<Long, List<InFeeVerification>> getInFeeVerMap(InFeeVerificationVO vo) throws Exception {
		vo.setObjectsPerPage(Integer.MAX_VALUE);
		
		Long customId = 0L;
		Map<Long, List<InFeeVerification>> map = new HashMap<Long, List<InFeeVerification>>();
		List<InFeeVerification> list = new ArrayList<InFeeVerification>();
		 
		for(InFeeVerification p : getPageList(vo).getList()){
			if(!customId.equals(p.getCustom().getId()) && !customId.equals(0L)){
				map.put(customId, list);
				list = new ArrayList<InFeeVerification>();
			}
			
			list.add(p);
			customId = p.getCustom().getId();
		}
		
		map.put(customId, list);
		return map;
	}

	@Override
	public synchronized String expOrderToU8(Date updateTime,String customCde,String summary,Double total,String maker,
			String backItem,String customItem,String cSSCode) throws Exception {
		//U8数据库连接
		Connection conn = PublicType.getConn();
		Statement stmt = conn.createStatement();
		ResultSet result = null;
		
		String sql = "";
		Integer maxNumber = 0;//最大流水号
		Integer maxNumberLength = 10;//流水号长度
		Integer iId = 0;//表头id
		Integer id = 0;//表体id
		String remote = "100";//id头部字符串
		
		//结算方式编码验证
		sql = " select * from SettleStyle where cSSCode = '" + cSSCode + "'";
		if(!stmt.executeQuery(sql).next()) 
			return "4";
		
		sql = " select * from code where ccode = '" + backItem + "'";
		if(!stmt.executeQuery(sql).next())
			return "5";
		
		sql = " select * from code where ccode = '" + customItem + "'";
		if(!stmt.executeQuery(sql).next())
			return "6";
		
		//查询最大流水号
		sql = " select cNumber as Maxnumber From VoucherHistory  with (NOLOCK) Where  CardNumber='RR' and cContent is NULL ";
		maxNumber = getResultInt(sql,result,stmt);
		if(maxNumber != null)
			maxNumber ++;
		else 
			maxNumber = 1;
		
		//流水号长度
		sql = " select max(len(cvouchid)) from Ap_CloseBill";
		maxNumberLength = getResultInt(sql,result,stmt);
		if(maxNumberLength.equals(0))
			maxNumberLength = 10;
		
		//表头最大id号
		sql = " select top 1 iFatherId from ufsystem..UA_Identity where cacc_id= '" + PublicType.getDataBaseName() + "' and cvouchtype='SK'";
		iId = getResultInt(sql, result, stmt);
		if(iId + 1 > 9999999)	iId = 1;
		else iId ++;
		
		//表体最大id号
		sql = " select top 1 iChildId from ufsystem..UA_Identity where cacc_id= '" + PublicType.getDataBaseName() + "' and cvouchtype='SK'";
		id = getResultInt(sql, result, stmt);
		if(id + 1 > 9999999)	id = 1;
		else id ++;
		
		//结算月判断（结算月大于生成时间，不再生成U8收款单）
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(updateTime);
		sql = " Select Max(iPeriod) From GL_Mend Where bFlag_AR<>0 and iyear =" + calendar.get(Calendar.YEAR); 
		if( getResultInt(sql, result, stmt) > calendar.get(Calendar.MONTH))//Calendar的月份比实际月份小1
			return "2";
		
		//根据客户编码查询
		//部门编码
		sql = " select top 1 b.cdepcode from customer a left join department b on b.cdepcode=a.cCusDepart where a.ccuscode= '" + customCde + "'";
		String depCode = getResultStr(sql, result, stmt);
		//人员编码
		sql = " select top 1 cCusPPerson from customer where ccuscode= '" + customCde + "'";
		String personCode = getResultStr(sql, result, stmt);
		//项目编码
		sql = " select top 1 cDCCode from customer where ccuscode='" + customCde + "'";
		String itemCode = getResultStr(sql, result, stmt);
		//项目名称
		sql = " select top 1 cDCName from DistrictClass where cDCCode='" + customCde + "'";
		String itemName = getResultStr(sql, result, stmt);
		
		//生成收款单据头
		sql = " Insert Into Ap_CloseBill (cVouchType,cVouchID,dVouchDate,iPeriod,cDwCode,"
			+ " cSSCode,cDigest,cexch_name,iExchRate,iAmount,iAmount_f,"
			+ " iRAmount,iRAmount_f,cOperator,cCode,iPayForOther,cFlag,iID,"
			+ " bFromBank,bToBank,bSure,VT_ID,iAmount_s,dcreatesystime,"
			+ " cDeptCode,cPerson,cItem_Class,cItemCode) values ( "
			+ " '48','" + String.format("%0" + maxNumberLength + "d", maxNumber) + "','" + DateFormat.date2Str(updateTime, 2) + "','" + (calendar.get(Calendar.MONTH) + 1) + "','"
			+ customCde + "','" + cSSCode + "','" + summary + "','人民币',1," 
			+ total + "," + total + "," + total + "," + total + ",'"
			+ maker + "','" + backItem + "',0,'AR','" + remote + String.format("%07d", iId) + "',0,0,0,8052,0,'" + DateFormat.date2Str(new Date(), 2) + "',"
			+ (StringUtils.isBlank(depCode) ? "null," : "'" + depCode + "',") 
			+ (StringUtils.isBlank(personCode) ? "null," : "'" + personCode + "',")
			+ "'00'," + (StringUtils.isBlank(itemCode) ? "null" : "'" + personCode + "'") + ")";
		stmt.addBatch(sql);
		
		//生成收款单表体
		sql = " INSERT INTO Ap_CloseBills (iID,ID,iType,bPrePay,cCusVen,iAmt_f,iAmt,iRAmt_f,iRAmt,cKm,iAmt_s,iRAmt_s,iOrderType,"
				+ " cDepCode,cPersonCode ,cXmClass ,cXm ,cItemName) VALUES ("
				+ " '" + remote + String.format("%07d", iId) + "','" + remote + String.format("%07d", id) + "',0,0,'" + customCde + "'," 
				+ total + "," + total + "," + total + "," + total + ",'"
				+ customItem + "',0,0,null,"
				+ (StringUtils.isBlank(depCode) ? "null," : "'" + depCode + "',") 
				+ (StringUtils.isBlank(personCode) ? "null," : "'" + personCode + "',")
				+ "'00'," 
				+ (StringUtils.isBlank(itemCode) ? "null," : "'" + personCode + "',")
				+  (StringUtils.isBlank(itemName) ? "null" : "'" + itemName + "'") + ")";
		
		stmt.addBatch(sql);
		
		
		List<Dict> dictList = dictService.getByType(DictType.SKDXYE);
		if(Common.checkList(dictList) && dictList.get(0).getValue() == 1){
			//修改客户信用余额表
			sql = " if not exists(select top 1 ccuscode from SA_CreditSum "
					+ " where  iType =1 and ccuscode='" + customCde + "')"
					+ " insert into SA_CreditSum  (iType,ccuscode,farsum ) values ("
					+ "1,'" + customCde + "',-" + total + ")"
					+ " Else update SA_CreditSum set [farsum]=isnull(farsum,0)-" 
					+ total + " where  iType =1 and ccuscode='" + customCde + "'";
			stmt.addBatch(sql);
		}
		
		//修改最大单号表信息
		sql = "update VoucherHistory set cNumber='" + maxNumber + "' Where  CardNumber='RR' and cContent is NULL ";
		stmt.addBatch(sql);
		
		
		//修改最大单号
		sql = " update ufsystem..ua_identity set ifatherid= " + iId + ",ichildid=" + id + " where cacc_id='"
				+ PublicType.getDataBaseName() + "' and cvouchtype='SK' ";
		stmt.addBatch(sql);
		
		try{
			conn.setAutoCommit(false);
			stmt.executeBatch();
			conn.commit();
			return "1";
		}catch(Exception e){
			e.printStackTrace();
			conn.rollback();
			return "3";
		}finally{
			if(result != null) try{ result.close();} catch(Exception e){}
			if(stmt != null) try{ stmt.close();} catch(Exception e){} 
			if(conn != null) try{ conn.close();} catch(Exception e){} 
		}
	}
	
	private static String getResultStr(String sql,ResultSet result,Statement stmt) throws Exception{
		result = stmt.executeQuery(sql);
		while(result.next()){
			return result.getString(1);
		}
		return "";
	}
	
	private static Integer getResultInt(String sql,ResultSet result,Statement stmt) throws Exception{
		result = stmt.executeQuery(sql);
		while(result.next()){
			return result.getInt(1);
		}
		return 0;
	}
	
	public static void main(String args[]) throws Exception{
		Connection conn = PublicType.getConn();
		Statement stmt = conn.createStatement();
		String sql = "select * from SettleStyle where cSSCode = '104'";
		ResultSet result = stmt.executeQuery(sql);
		System.out.println(result.next());
	}

	@Override
	public List<InFeeVerification> inFeeVerification(InFeeVerificationVO vo) {
		try {
			String hql="select o from InFeeVerification o where 1=1 ";
			hql+=getQueryStr(vo)+" order by o.createTime desc ";
			Query query=em.createQuery(hql);
			setQueryParm(query, vo);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void exportInFeeVerification(String[] heads, InFeeVerification ifv, Map<String, String> map) {
		String[] header=Common.getExportHeader("INFEEVERIFICATION");
		for(String key:heads){
			int i=0;
			if(key.equals(header[i++])){
				map.put(key, ifv.getVerCode());
			}else if(key.equals(header[i++])){
				if(ifv.getInFeeProvision()!=null){
					map.put(key, ifv.getInFeeProvision().getCode());
				}
			}else if(key.equals(header[i++])){
				if(ifv.getCustom()!=null){
					map.put(key, ifv.getCustom().getCustomName());
				}
			}else if(key.equals(header[i++])){
				if(ifv.getTotalFee()!=null){
					map.put(key, ifv.getTotalFee().toString());
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				if(ifv.getVerDirection()!=null){
					map.put(key, ifv.getVerDirection().getText());
				}
			}else if(key.equals(header[i++])){
				if(ifv.getVerType()!=null){
					map.put(key, ifv.getVerType().getDictName());
				}
			}else if(key.equals(header[i++])){
				if(ifv.getVerDate()!=null){
					map.put(key, DateFormat.date2Str(ifv.getVerDate(), 2));
				}
			}else  if(key.equals(header[i++])){
				if(ifv.getApproveState()!=null){
					map.put(key, ifv.getApproveState().getText());
				}
			}else  if(key.equals(header[i++])){
				if(ifv.getIsCreateU8()!=null){
					if(ifv.getIsCreateU8()==true){
						map.put(key, "是");
					}else{
						map.put(key, "否");
					}
				}
			}else  if(key.equals(header[i++])){
				if(ifv.getEnterFee()!=null){
					map.put(key, ifv.getEnterFee().toString());		//进场费
				}
			}else  if(key.equals(header[i++])){
				if(ifv.getFixedFee()!=null){
					map.put(key, ifv.getFixedFee().toString());		//固定费用
				}
			}else  if(key.equals(header[i++])){
				if(ifv.getYearReturnFee()!=null){
					map.put(key, ifv.getYearReturnFee().toString());		//"年返金"
				}
			}else  if(key.equals(header[i++])){
				if(ifv.getMonthReturnFee()!=null){
					map.put(key, ifv.getMonthReturnFee().toString());		//月返金
				}
			}else  if(key.equals(header[i++])){
				if(ifv.getNetInfoFee()!=null){
					map.put(key, ifv.getNetInfoFee().toString());		//网络信息费"
				}
			}else  if(key.equals(header[i++])){
				if(ifv.getDeliveryFee()!=null){
					map.put(key, ifv.getDeliveryFee().toString());		//配送服务费
				}
			}else  if(key.equals(header[i++])){
				if(ifv.getPosterFee()!=null){
					map.put(key, ifv.getPosterFee().toString());		//海报费
				}
			}else  if(key.equals(header[i++])){
				if(ifv.getPromotionFee()!=null){
					map.put(key, ifv.getPromotionFee().toString());		//促销陈列费
				}
			}else  if(key.equals(header[i++])){
				if(ifv.getSponsorFee()!=null){
					map.put(key, ifv.getSponsorFee().toString());		//赞助费
				}
			}else  if(key.equals(header[i++])){
				if(ifv.getLossFee()!=null){
					map.put(key, ifv.getLossFee().toString());		//损耗费
				}
			}else  if(key.equals(header[i++])){
				if(ifv.getFixedDiscount()!=null){
					map.put(key, ifv.getFixedDiscount().toString());		//固定折扣
				}
			}else  if(key.equals(header[i++])){
				if(ifv.getPilesoilFee()!=null){
					map.put(key, ifv.getPilesoilFee().toString());		//堆头费
				}
			}else  if(key.equals(header[i++])){
				if(ifv.getMarketFee()!=null){
					map.put(key, ifv.getMarketFee().toString());		//市场费
				}
			}else  if(key.equals(header[i++])){
				if(ifv.getCaseReturnFee()!=null){
					map.put(key, ifv.getCaseReturnFee().toString());		//现款现货返利
				}
			}else  if(key.equals(header[i++])){
				if(ifv.getOtherFee()!=null){
					map.put(key, ifv.getOtherFee().toString());		//其他费用
				}
			}
		}
		
	}
	
	
	
}
