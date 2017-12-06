package com.kington.fshg.service.sale;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.DateFormat;
import com.kington.fshg.common.PublicType;
import com.kington.fshg.common.PublicType.DictType;
import com.kington.fshg.model.EnumTypeVO;
import com.kington.fshg.model.info.Product;
import com.kington.fshg.model.sale.SaleOrderCode;
import com.kington.fshg.model.sale.SaleOrderU8DetailVO;
import com.kington.fshg.model.sale.SaleOrderU8VO;
import com.kington.fshg.model.system.Dict;
import com.kington.fshg.service.BaseServiceImpl;
import com.kington.fshg.service.info.ProductService;
import com.kington.fshg.service.system.DictService;

public class SaleOrderU8ServiceImpl extends
		BaseServiceImpl<SaleOrderCode, SaleOrderU8VO> implements
		SaleOrderU8Service {

	private static final long serialVersionUID = 7260521457675498130L;
	
	@Resource
	private ProductService productService;
	@Resource
	private DictService dictService;
	
	@SuppressWarnings("unchecked")
	@Override
	public synchronized String getCode() {
		String code = "YMD" + DateFormat.date2Str(new Date(), 3);
		String sql = "select code from sale_order_code where code like '" + code + "%' order by code desc limit 1 ";
		Query query = em.createNativeQuery(sql);
		
		int number = 0;
		List<Object> list = query.getResultList();
		if(Common.checkList(list) && list.get(0) != null){
			String a = list.get(0).toString();
			number = Integer.parseInt(a.substring(11));
		}
		
		DecimalFormat df = new DecimalFormat("00000");  
		code += df.format(number + 1);
		
		SaleOrderCode orderCode = new SaleOrderCode();
		orderCode.setCode(code);
		orderCode.setCreateTime(new Date());
		orderCode.setUpdateTime(orderCode.getCreateTime());
		em.merge(orderCode);
		
		return code;
	}

	@Override
	protected String getQueryStr(SaleOrderU8VO vo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setQueryParm(Query query, SaleOrderU8VO vo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void switchVO2PO(SaleOrderU8VO vo, SaleOrderCode po)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<EnumTypeVO> getDepartmentList() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		List<EnumTypeVO> resultList = new ArrayList<EnumTypeVO> ();
		
		String sql = "select cDepCode,cDepName from department ";
		
		try {
			conn = PublicType.getConn();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			
			while(result.next()){
				EnumTypeVO vo = new EnumTypeVO();
				vo.setText(result.getString(1));
				vo.setName(result.getString(2));
				
				resultList.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(result != null) try{ result.close();} catch(Exception e){}
			if(stmt != null) try{ stmt.close();} catch(Exception e){} 
			if(conn != null) try{ conn.close();} catch(Exception e){} 
		}
		return resultList;
	}

	@Override
	public List<EnumTypeVO> getSaletypeList() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		List<EnumTypeVO> resultList = new ArrayList<EnumTypeVO> ();
		
		String sql = "select cSTCode ,cSTName  from saletype ";
		
		try {
			conn = PublicType.getConn();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			
			while(result.next()){
				EnumTypeVO vo = new EnumTypeVO();
				vo.setText(result.getString(1));
				vo.setName(result.getString(2));
				
				resultList.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(result != null) try{ result.close();} catch(Exception e){}
			if(stmt != null) try{ stmt.close();} catch(Exception e){} 
			if(conn != null) try{ conn.close();} catch(Exception e){} 
		}
		return resultList;
	}

	@Override
	public List<Object[]> getExchList() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		List<Object[]> resultList = new ArrayList<Object[]> ();
		
		String sql = "select  cexch_name,nflat  from exch order by cexch_name, iyear desc,iperiod desc,cdate desc";
		
		try {
			conn = PublicType.getConn();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			
			String cexch = "";
			while(result.next()){
				Object[] o = new Object[2];
				
				if(!StringUtils.equals(cexch, result.getString(1))){
					o[0] = result.getString(1);
					o[1] = result.getObject(2);
					
					cexch = o[0].toString();
					resultList.add(o);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(result != null) try{ result.close();} catch(Exception e){}
			if(stmt != null) try{ stmt.close();} catch(Exception e){} 
			if(conn != null) try{ conn.close();} catch(Exception e){} 
		}
		return resultList;
	}

	@Override
	public List<EnumTypeVO> getPersonList(String depCode) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		List<EnumTypeVO> resultList = new ArrayList<EnumTypeVO> ();
		
		String sql = "select cPersonCode,cPersonName from person where cDepCode = '" + depCode + "'";
		
		try {
			conn = PublicType.getConn();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			
			while(result.next()){
				EnumTypeVO vo = new EnumTypeVO();
				vo.setText(result.getString(1));
				vo.setName(result.getString(2));
				
				resultList.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(result != null) try{ result.close();} catch(Exception e){}
			if(stmt != null) try{ stmt.close();} catch(Exception e){} 
			if(conn != null) try{ conn.close();} catch(Exception e){} 
		}
		return resultList;
	}

	@Override
	public synchronized Boolean insertU8(SaleOrderU8VO vo, Double ddxye) throws Exception {
		//U8数据库连接
		Connection conn = PublicType.getConn();
		Statement stmt = conn.createStatement();
		ResultSet result = null;
		
		String sql = "";
		Integer iId = 0;//表头id
		Integer id = 0;//表体id
		String remote = "100";//id头部字符串
		
		//查询表头最大id号
		sql = " select top 1 iFatherId from ufsystem..UA_Identity where cacc_id= '" + PublicType.getDataBaseName() + "' and cvouchtype='SOMain'";
		iId = getResultInt(sql,result,stmt);
		if(iId + 1 > 9999999)	iId = 1;
		else iId ++;
		
		//查询表体最大id号
		sql = " select top 1 iChildId from ufsystem..UA_Identity where cacc_id= '" + PublicType.getDataBaseName() + "' and cvouchtype='SOMain'";
		id = getResultInt(sql,result,stmt);
		if(id + 1 > 9999999)	id = 1;
		else id ++;
		
		//插入表头id,订单号，输单日期，销售类型编码，业务类型，部门编码，业务负责人编码，客户编码，税率，币种，汇率，
		//单据模版号，退货标志（1-退货；0-正常），是否整单打折，状态（0-未审核；1-已审核），是否控制工作流，收货人电话
		//收货日期，卖场订单号，备注
		sql = "insert into so_somain (id,csocode,ddate,cSTCode,cBusType,cDepCode,cPersonCode,ccuscode,"
				+ "itaxrate, cexch_name, iexchrate,ivtid,breturnflag,bdisflag,istatus,iswfcontrolled,cDefine12,cDefine4,cDefine11,cMemo) values ("
				+ remote + String.format("%07d", iId) + ",'" + vo.getOrderCode() + "','" + DateFormat.date2Str(vo.getOrderDate(), 2) + "','"
				+ vo.getSaleTypeCode() + "','普通销售','" + vo.getDepartmentCode() + "','" + vo.getPersonCode() + "','" + vo.getCustomCode() + "',"
				+ vo.getITaxRate() + ",'" + vo.getCexchName() + "'," + vo.getIExchRate() + ",95,0,0,0,1,'" + vo.getShrPhone() + "','"
				+ DateFormat.date2Str(vo.getShDate(), 2) + "','" + vo.getStoreOrderCode() +  "','" + vo.getRemark() + "')";
		stmt.addBatch(sql);
		
		//插入表体
		Double jshj = 0d;
		if(Common.checkList(vo.getDetailList())){
			for(SaleOrderU8DetailVO detailVO  : vo.getDetailList()){
				if(StringUtils.equals(detailVO.getCInvCode(), "del"))
					continue;
				
				Double iTax = PublicType.setDoubleScale6(detailVO.getJshj() - detailVO.getWsje());//原币税额
				
				Double inatmoney =  PublicType.setDoubleScale(detailVO.getWsje() * vo.getIExchRate() * 0.01);//本币无税金额=原币无税金额*汇率
				Double inatUnitPrice = PublicType.setDoubleScale6(inatmoney / detailVO.getSl());//本币无税单价=本币无税金额/数量
				Double inattax = PublicType.setDoubleScale6(inatmoney * detailVO.getTax() * 0.01);//本币税额 = 本币无税金额*税率
				Double inatsum = PublicType.setDoubleScale(inatmoney + inattax);//本币价税合计 = 本币无税金额 +税额
				Double inatdiscount = PublicType.setDoubleScale4(detailVO.getZke() * vo.getIExchRate() * 0.01);//本币折扣额=原币折扣额 *汇率
				
				Double zhl = null;
				if(detailVO.getJs() != null && detailVO.getSl() != null && detailVO.getJs() != 0)
					zhl = PublicType.setDoubleScale6(detailVO.getSl() / detailVO.getJs() );
				
				
				//主表标识id，字表标识id,订单编号，存货编码，存货名称，件数，数量，原币无税单价，原币含税单价，原币无税金额，原币税额，原币价税合计，原币折扣额
				//本币无税单价 ，本币无税金额，本币税额，本币价税合计，本币折扣额，扣率，扣率2，税率，
				//计量单位编码（select top 1 cSaComUnitCode from inventory where cinvcode=''），
				//新旧品分类，客户商品号，对方名，折前无税合计，存货用途，生产日期，备注,转换率
				sql = "insert into so_sodetails (id,isosid,csocode,cinvcode,cInvName, "
						+ "inum,iquantity,iUnitPrice,iTaxUnitPrice,imoney,itax,isum,idiscount,"
						+ "inatUnitPrice,inatmoney,inattax,inatsum,inatdiscount,kl,kl2,itaxrate,"
						+ "cDefine29,cDefine26,cDefine33,cDefine27,cFree2,cDefine36,cDefine28,iInvExchRate,iQuotedPrice ) values ( '"
						+ remote + String.format("%07d", iId) + "','" + remote + String.format("%07d", id) + "','" + vo.getOrderCode() + "','"
						+ detailVO.getCInvCode() + "','" + detailVO.getChmc() + "'," + detailVO.getJs() + "," + detailVO.getSl() + ","
						+ detailVO.getWsdj() + "," + detailVO.getHsdj() + "," + detailVO.getWsje() + "," + iTax + "," + detailVO.getJshj() + "," + detailVO.getZke() + ","
						+ inatUnitPrice + "," + inatmoney + "," + inattax + "," + inatsum + "," + inatdiscount + ","
						+ detailVO.getKl() + "," + detailVO.getKl2() + "," + detailVO.getTax() + ",'"
						+ detailVO.getXjpfl() + "'," + detailVO.getKhsph() + ",'" + detailVO.getDfm() + "'," + detailVO.getZqwshj() + ",'"
						+ detailVO.getChyt() + "','" + DateFormat.date2Str(detailVO.getScrq(), 2) + "','" + detailVO.getBz() + "'," + zhl + "," + detailVO.getBj() + ")";
				
				jshj += detailVO.getJshj();
				stmt.addBatch(sql);
				id ++;
			}
		}
		
		//修改最大id号表
		sql = " update ufsystem..ua_identity set ifatherid=" + iId + ",ichildid=" + id + " where cacc_id='" + PublicType.getDataBaseName() + "' and cvouchtype='SOMain'";
		stmt.addBatch(sql);
		
		//修改信用额
		if(ddxye == 1 && StringUtils.isNotBlank(vo.getCustomCode())){
			sql  = " update SA_CreditSum set [fSOSum]=isnull(fSOSum ,0)+" 
					+ jshj + " where  iType =1 and ccuscode='" + vo.getCustomCode() + "'";
			stmt.addBatch(sql);
		}
		
		try{
			conn.setAutoCommit(false);
			stmt.executeBatch();
			conn.commit();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			conn.rollback();
			return false;
		}finally{
			if(result != null) try{ result.close();} catch(Exception e){}
			if(stmt != null) try{ stmt.close();} catch(Exception e){} 
			if(conn != null) try{ conn.close();} catch(Exception e){} 
		}
		
	}

	private static Integer getResultInt(String sql,ResultSet result,Statement stmt) throws Exception{
		result = stmt.executeQuery(sql);
		while(result.next()){
			return result.getInt(1);
		}
		return 0;
	}
	
	private static String getResultString(String sql,ResultSet result,Statement stmt) throws Exception{
		result = stmt.executeQuery(sql);
		while(result.next()){
			return result.getString(1);
		}
		return "";
	}
	
	private static Double getResultDouble(String sql,ResultSet result,Statement stmt) throws Exception{
		result = stmt.executeQuery(sql);
		while(result.next()){
			return result.getDouble(1);
		}
		return 0d;
	}

	@Override
	public List<SaleOrderU8DetailVO> getDetailList(SaleOrderU8VO vo) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		
		List<SaleOrderU8DetailVO> resultList = new ArrayList<SaleOrderU8DetailVO>();
		
		try{			
			String sql = "select a.autoid as 销售订单子表ID,b.id as 销售订单主表ID,b.csocode as 销售订单号,b.ddate as 订单日期,b.ccuscode as 客户编码,"
					+ "c.ccusname as 客户名称,a.cinvcode as 存货编号,d.cinvaddcode as 存货代码,d.cinvname as 存货名称,d.cinvstd as 存货规格,a.iquantity as 数量,"
					+ "a.cUnitID as 单位,a.iSum/a.iquantity as 原币含税单价,a.iSum as 原币价税合计,t.cDepName as 销售部门,b.cPersonCode as 业务员,p.cPersonName as 业务员名称,"
					+ "b.cexch_name as 币别,b.iExchRate as 汇率,e.cstname as 销售类型,c.cCusDefine3 as 客户地区,b.cDefine12 as 收货人电话,b.cDefine4 as 收货日期,"
					+ "b.cDefine11 as 卖场订单号,a.cDefine29 as 新旧品分类,a.cDefine26 as 客户商品号,a.cDefine33 as 对方名,a.cDefine27 as 折前无税合计,"
					+ "a.cFree2 as 存货用途,a.cDefine36 as 生产日期,a.cDefine28 as 备注 ,c.cCusDefine3 as 客户地区 ,b.iStatus as 审核状态   from so_sodetails a left join so_somain b on b.id=a.id "
					+ "left join customer c on c.ccuscode=b.ccuscode left join inventory d on d.cinvcode=a.cinvcode left join saletype e on e.cstcode=b.cstcode " 
					+ "left join person p on b.cPersonCode = p.cPersonCode left join department t on b.cdepcode = t.cDepCode where 1 = 1 ";
			
			if(StringUtils.isNotBlank(vo.getCustomName()))
				sql += " and c.cCusName like '%" + vo.getCustomName() + "%' ";
			
			if(StringUtils.isNotBlank(vo.getOrderCode()))
				sql += " and b.csocode = '" + vo.getOrderCode() + "'";
				
			if(StringUtils.isNotBlank(vo.getBeginTime()))
				sql += " and b.ddate >= '" + vo.getBeginTime() + "' ";
			
			if(StringUtils.isNotBlank(vo.getEndTime()))
				sql += " and b.ddate <= '" + vo.getEndTime() + "' ";
			
			if(StringUtils.isNotBlank(vo.getPresonName()))
				sql += " and p.cPersonName = '" + vo.getPresonName() + "' ";
			
			if(StringUtils.isNotBlank(vo.getCustomArea()))
				sql += " and c.cCusDefine3 in ( '" + vo.getCustomArea() + "')";		
			
			if(vo.getState() != null)
				sql += " and b.iStatus = " + vo.getState();
			
			sql += "order by b.ddate,b.csocode,a.cinvcode";
			
			conn = PublicType.getConn();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			
			while(result.next()){
				SaleOrderU8VO saleOrderU8VO = new SaleOrderU8VO();
				SaleOrderU8DetailVO detailVO = new SaleOrderU8DetailVO();
				
				detailVO.setId(result.getLong(1));
				
				saleOrderU8VO.setId(result.getLong(2));
				saleOrderU8VO.setOrderCode(result.getString(3));
				
				String orderDate = result.getString(4);
				if(StringUtils.isNotBlank(orderDate))
					saleOrderU8VO.setOrderDate(DateFormat.str2Date(orderDate, 2));
				
				saleOrderU8VO.setCustomCode(result.getString(5));
				saleOrderU8VO.setCustomName(result.getString(6));
				
				detailVO.setCInvCode(result.getString(7));
				detailVO.setHh(result.getString(8));
				detailVO.setChmc(result.getString(9));
				detailVO.setGgxh(result.getString(10));
				detailVO.setSl(Double.parseDouble(result.getString(11)));
				detailVO.setHsdj(result.getDouble(13));
				detailVO.setJshj(result.getDouble(14));
				
				saleOrderU8VO.setDepartmentCode(result.getString(15));
				saleOrderU8VO.setPersonCode(result.getString(16));
				saleOrderU8VO.setPresonName(result.getString(17));
				saleOrderU8VO.setCexchName(result.getString(18));
				saleOrderU8VO.setIExchRate(result.getFloat(19));
				saleOrderU8VO.setSaleTypeCode(result.getString(20));
				saleOrderU8VO.setCustomArea(result.getString(21));
				saleOrderU8VO.setShrPhone(result.getString(22));
				
				String shDate = result.getString(23);
				if(StringUtils.isNotBlank(shDate))
					saleOrderU8VO.setShDate(DateFormat.str2Date(shDate, 2));
					
				saleOrderU8VO.setStoreOrderCode(result.getString(24));
				
				detailVO.setXjpfl(result.getString(25));
				detailVO.setKhsph(result.getFloat(26));
				detailVO.setDfm(result.getString(27));
				detailVO.setZqwshj(result.getDouble(28));
				detailVO.setChyt(result.getString(29));
				
				String scrq = result.getString(30);
				if(StringUtils.isNotBlank(scrq))
					detailVO.setScrq(DateFormat.str2Date(scrq, 2));
				
				detailVO.setBz(result.getString(31));
				saleOrderU8VO.setCustomArea(result.getString(32));
				saleOrderU8VO.setState(result.getInt(33));
				
				detailVO.setVo(saleOrderU8VO);
				resultList.add(detailVO);
			}
			
			return resultList;
		}catch(Exception e){
			e.printStackTrace();
			conn.rollback();
			return resultList;
		}finally{
			if(result != null) try{ result.close();} catch(Exception e){}
			if(stmt != null) try{ stmt.close();} catch(Exception e){} 
			if(conn != null) try{ conn.close();} catch(Exception e){} 
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<SaleOrderU8DetailVO> getDetailList2(SaleOrderU8VO vo) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		
		List<SaleOrderU8DetailVO> resultList = new ArrayList<SaleOrderU8DetailVO>();
		
		try{			
			String sql = "select a.autoid as 销售订单子表ID,b.id as 销售订单主表ID,b.csocode as 销售订单号,b.ddate as 订单日期,b.ccuscode as 客户编码,"
					+ "c.ccusname as 客户名称,a.cinvcode as 存货编号,d.cinvaddcode as 存货代码,d.cinvname as 存货名称,d.cinvstd as 存货规格,a.iquantity as 数量,"
					+ "a.cUnitID as 单位,a.iSum/a.iquantity as 原币含税单价,a.iSum as 原币价税合计,t.cDepName as 销售部门,b.cPersonCode as 业务员,p.cPersonName as 业务员名称,"
					+ "b.cexch_name as 币别,b.iExchRate as 汇率,e.cstname as 销售类型,c.cCusDefine3 as 客户地区,b.cDefine12 as 收货人电话,b.cDefine4 as 收货日期,"
					+ "b.cDefine11 as 卖场订单号,a.cDefine29 as 新旧品分类,a.cDefine26 as 客户商品号,a.cDefine33 as 对方名,a.cDefine27 as 折前无税合计,"
					+ "a.cFree2 as 存货用途,a.cDefine36 as 生产日期,a.cDefine28 as 备注 ,c.cCusDefine3 as 客户地区 ,b.iStatus as 审核状态   from so_sodetails a left join so_somain b on b.id=a.id "
					+ "left join customer c on c.ccuscode=b.ccuscode left join inventory d on d.cinvcode=a.cinvcode left join saletype e on e.cstcode=b.cstcode " 
					+ "left join person p on b.cPersonCode = p.cPersonCode left join department t on b.cdepcode = t.cDepCode where 1 = 1 ";
			
			if(StringUtils.isNotBlank(vo.getCustomName()))
				sql += " and c.cCusName like '%" + vo.getCustomName() + "%' ";
			
			if(StringUtils.isNotBlank(vo.getOrderCode()))
				sql += " and b.csocode = '" + vo.getOrderCode() + "'";
				
			if(StringUtils.isNotBlank(vo.getBeginTime()))
				sql += " and b.ddate >= '" + vo.getBeginTime() + "' ";
			
			if(StringUtils.isNotBlank(vo.getEndTime()))
				sql += " and b.ddate <= '" + vo.getEndTime() + "' ";
			
			if(StringUtils.isNotBlank(vo.getPresonName()))
				sql += " and p.cPersonName = '" + vo.getPresonName() + "' ";
			
			if(StringUtils.isNotBlank(vo.getCustomArea()))
				sql += " and c.cCusDefine3 in ( '" + vo.getCustomArea() + "')";		
			
			if(vo.getState() != null)
				sql += " and b.iStatus = " + vo.getState();
			
			sql += "order by b.ddate,b.csocode,a.cinvcode";
			
			conn = PublicType.getConn();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			
			String code1="",code2="";
			SaleOrderU8VO saleOrderU8VO = new SaleOrderU8VO();
			SaleOrderU8DetailVO detailVO = new SaleOrderU8DetailVO();
			while(result.next()){
				code1=result.getString(3);
				if(code1.equals(code2)){
					resultList.remove(detailVO);
					detailVO.setSl(detailVO.getSl()+Double.parseDouble(result.getString(11)));
					detailVO.setZqwshj(detailVO.getZqwshj()+result.getDouble(28));
					detailVO.setJshj(detailVO.getJshj()+result.getDouble(14));
					detailVO.setHsdj(detailVO.getHsdj()+result.getDouble(13));
				}else{
					//如果订单号不一致
					saleOrderU8VO=new SaleOrderU8VO();
					detailVO=new SaleOrderU8DetailVO();
					
					detailVO.setId(result.getLong(1));
					
					saleOrderU8VO.setId(result.getLong(2));
					saleOrderU8VO.setOrderCode(result.getString(3));
					
					String orderDate = result.getString(4);
					if(StringUtils.isNotBlank(orderDate))
						saleOrderU8VO.setOrderDate(DateFormat.str2Date(orderDate, 2));
					
					saleOrderU8VO.setCustomCode(result.getString(5));
					saleOrderU8VO.setCustomName(result.getString(6));
					
					detailVO.setCInvCode(result.getString(7));
					detailVO.setHh(result.getString(8));
					detailVO.setChmc(result.getString(9));
					detailVO.setGgxh(result.getString(10));
					detailVO.setSl(Double.parseDouble(result.getString(11)));
					detailVO.setHsdj(result.getDouble(13));
					detailVO.setJshj(result.getDouble(14));
					
					saleOrderU8VO.setDepartmentCode(result.getString(15));
					saleOrderU8VO.setPersonCode(result.getString(16));
					saleOrderU8VO.setPresonName(result.getString(17));
					saleOrderU8VO.setCexchName(result.getString(18));
					saleOrderU8VO.setIExchRate(result.getFloat(19));
					saleOrderU8VO.setSaleTypeCode(result.getString(20));
					saleOrderU8VO.setCustomArea(result.getString(21));
					saleOrderU8VO.setShrPhone(result.getString(22));
					
					String shDate = result.getString(23);
					if(StringUtils.isNotBlank(shDate))
						saleOrderU8VO.setShDate(DateFormat.str2Date(shDate, 2));
						
					saleOrderU8VO.setStoreOrderCode(result.getString(24));
					
					detailVO.setXjpfl(result.getString(25));
					detailVO.setKhsph(result.getFloat(26));
					detailVO.setDfm(result.getString(27));
					detailVO.setZqwshj(result.getDouble(28));
					detailVO.setChyt(result.getString(29));
					
					String scrq = result.getString(30);
					if(StringUtils.isNotBlank(scrq))
						detailVO.setScrq(DateFormat.str2Date(scrq, 2));
					
					detailVO.setBz(result.getString(31));
					saleOrderU8VO.setCustomArea(result.getString(32));
					saleOrderU8VO.setState(result.getInt(33));
					
					detailVO.setVo(saleOrderU8VO);
				}
				
				
				
				code2=code1;
				resultList.add(detailVO);
			}
			
			return resultList;
		}catch(Exception e){
			e.printStackTrace();
			conn.rollback();
			return resultList;
		}finally{
			if(result != null) try{ result.close();} catch(Exception e){}
			if(stmt != null) try{ stmt.close();} catch(Exception e){} 
			if(conn != null) try{ conn.close();} catch(Exception e){} 
		}
	}
	
	@Override
	public SaleOrderU8VO getVO(Long id) throws Exception{
		SaleOrderU8VO vo = new SaleOrderU8VO();
		List<SaleOrderU8DetailVO> detailVOList = new ArrayList<SaleOrderU8DetailVO>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		
		try{
			//查询表头
			String sql = "select b.id as 主表id,b.csocode as 订单号,b.ddate as 输单日期, b.cdepcode as 销售部门编码,"
					+ " b.cstcode as 销售类型编码,c.ccuscode as 客户编码,c.ccusname as 客户名称,c.cCusDefine3 as 客户地区,"
					+ " b.iTaxRate as 税率,b.cexch_name as 币种,b.iExchRate as 汇率,b.cDefine12 as 收货人电话 ,"
					+ " p.cPersonCode as 业务员编码,b.cDefine4 as 收货日期,b.cDefine11 as 卖场订单号 ,b.cMemo as 备注, b.iStatus as 审核状态  "
					+ " from so_somain b left join customer c on c.ccuscode=b.ccuscode left join person p on b.cPersonCode = p.cPersonCode "
					+ " where id = " + id;
			
			conn = PublicType.getConn();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			
			while(result.next()){
				vo.setId(result.getLong(1));
				vo.setOrderCode(result.getString(2));
				
				String orderDate = result.getString(3);
				if(StringUtils.isNotBlank(orderDate))
					vo.setOrderDate(DateFormat.str2Date(orderDate, 2));
				
				vo.setDepartmentCode(result.getString(4));
				vo.setSaleTypeCode(result.getString(5));
				vo.setCustomCode(result.getString(6));
				vo.setCustomName(result.getString(7));
				vo.setCustomArea(result.getString(8));
				
				String iTaxRate = result.getString(9);
				if(StringUtils.isNotBlank(iTaxRate))
					vo.setITaxRate(Float.parseFloat(iTaxRate));
				
				vo.setCexchName(result.getString(10));
				
				String iExchRate = result.getString(11);
				if(StringUtils.isNotBlank(iExchRate))
					vo.setIExchRate(Float.parseFloat(iExchRate));
				
				vo.setShrPhone(result.getString(12));
				vo.setPersonCode(result.getString(13));
				
				String shDate = result.getString(14);
				if(StringUtils.isNotBlank(shDate))
					vo.setShDate(DateFormat.str2Date(shDate, 2));
				
				vo.setStoreOrderCode(result.getString(15));
				vo.setRemark(result.getString(16));
				vo.setState(result.getInt(17));
			}
			
			//查询表体
			sql = " select a.id as 主表id,a.isosid as 字表id,d.cInvAddCode as 货号,d.cInvCode as 存货编码,d.cinvname as 存货名称,"
					+ " a.cDefine29 as 新旧品分类,a.cDefine26 as 客户商品号,a.cDefine33 as 对方名,d.cinvstd as 规格型号, "
					+ " a.iquantity as 数量,a.iQuotedPrice as 报价,a.cDefine27 as 折前无税合计,a.iSum/a.iquantity as 含税单价, "
					+ " a.iTaxRate as 税率, a.iMoney/a.iquantity as 无税单价, a.iSum as 价税合计, a.iMoney as 无税金额,"
					+ " a.iDisCount as 折扣额, a.KL as 扣率, a.KL2 as 扣率2,a.cFree2 as 存货用途,a.cDefine36 as 生产日期,a.cDefine28 as 备注, "
					+ " a.iInvExchRate as 转换率, c.ccomunitname as 主计量  from so_sodetails a left join inventory d on d.cinvcode=a.cinvcode "
					+ " left join ComputationUnit c on d.cComUnitCode = c.cComunitCode and d.cgroupcode = c.cgroupcode"
					+ " where a.id = " + id + "  order by a.autoId";
			
			result = stmt.executeQuery(sql);
			
			Double js = 0d,sl = 0d,zqwshj = 0d,jshj = 0d,wsje = 0d,zke = 0d;
			
			while(result.next()){
				SaleOrderU8DetailVO detailVO = new SaleOrderU8DetailVO();
				detailVO.setId(result.getLong(2));
				detailVO.setHh(result.getString(3));
				detailVO.setCInvCode(result.getString(4));
				if(StringUtils.isNotBlank(detailVO.getCInvCode())){
					Product p = productService.getByCde(detailVO.getCInvCode());
					if(p != null)
						detailVO.setProductId(p.getId());
				}
				
				detailVO.setChmc(result.getString(5));
				detailVO.setXjpfl(result.getString(6));
				detailVO.setKhsph(result.getFloat(7));
				detailVO.setDfm(result.getString(8));
				detailVO.setGgxh(result.getString(9));
				detailVO.setSl(result.getDouble(10));
				detailVO.setBj(result.getDouble(11));
				detailVO.setZqwshj(result.getDouble(12));
				detailVO.setHsdj(result.getDouble(13));
				detailVO.setTax(result.getDouble(14));
				detailVO.setWsdj(PublicType.setDoubleScale6(result.getDouble(15)));
				detailVO.setJshj(result.getDouble(16));
				detailVO.setWsje(result.getDouble(17));
				detailVO.setZke(result.getDouble(18));
				detailVO.setKl(result.getDouble(19));
				detailVO.setKl2(result.getDouble(20));
				detailVO.setChyt(result.getString(21));
				
				String scrq = result.getString(22);
				if(StringUtils.isNotBlank(scrq))
					detailVO.setScrq(DateFormat.str2Date(scrq, 2));
				
				detailVO.setBz(result.getString(23));
				detailVO.setZhl(result.getDouble(24));
				if(detailVO.getSl() != null && detailVO.getZhl() != null)
					detailVO.setJs(PublicType.setDoubleScale(detailVO.getSl()*detailVO.getZhl()));
				detailVO.setZjl(result.getString(25));
				
				detailVOList.add(detailVO);
				
				if(detailVO.getJs() != null)	js += detailVO.getJs();
				if(detailVO.getSl() != null)	sl += detailVO.getSl();
				if(detailVO.getZqwshj() != null)	zqwshj += detailVO.getZqwshj();
				if(detailVO.getJshj() != null)	jshj += detailVO.getJshj();
				if(detailVO.getWsje() != null)	wsje += detailVO.getWsje();
				if(detailVO.getZke() != null)	zke += detailVO.getZke();
			}
			
			vo.setDetailList(detailVOList);
			vo.setJs(js);
			vo.setSl(sl);
			vo.setZqwshj(zqwshj);
			vo.setJshj(jshj);
			vo.setWsje(wsje);
			vo.setZke(zke);
			return vo;
		}catch(Exception e){
			e.printStackTrace();
			conn.rollback();
			return vo;
		}finally{
			if(result != null) try{ result.close();} catch(Exception e){}
			if(stmt != null) try{ stmt.close();} catch(Exception e){} 
			if(conn != null) try{ conn.close();} catch(Exception e){} 
		}
	}

	@Override
	public Boolean deleteOrder(Long id) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		
		try{
			conn = PublicType.getConn();
			stmt = conn.createStatement();
			Boolean flag = false;
			String sql = "";
			Double jshj = 0d;
			String customCde = "";
			
			List<Dict> dictList = dictService.getByType(DictType.DDXYE);
			if(Common.checkList(dictList) && dictList.get(0).getValue() == 1){
				sql = "select cCusCode from so_somain where id = " + id;
				customCde = getResultString(sql, result, stmt);
				
				sql = "select sum(isum) from so_sodetails where id = " + id;
				jshj = getResultDouble(sql, result, stmt);
			}
			
			sql = "delete from so_sodetails where id = " + id;
			flag = stmt.executeUpdate(sql) > 0;
			
			if(flag){
				//修改信用额
				if(StringUtils.isNotBlank(customCde) && jshj != 0){
					sql  = " update SA_CreditSum set [fSOSum]=isnull(fSOSum ,0)-" 
							+ jshj + " where  iType =1 and ccuscode='" + customCde + "'";
					stmt.executeUpdate(sql);
				}
				
				sql = "delete from so_somain where id = " + id;
				flag = stmt.executeUpdate(sql) > 0;
			}
			
			return flag;
		}catch(Exception e){
			e.printStackTrace();
			conn.rollback();
			return false;
		}finally{
			if(stmt != null) try{ stmt.close();} catch(Exception e){} 
			if(conn != null) try{ conn.close();} catch(Exception e){} 
		}
	}

	@Override
	public Boolean deleteDetail(Long id, Double ddxye, String customCde) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		
		Double jshj = 0d;
		String sql = "";
		
		try{
			conn = PublicType.getConn();
			stmt = conn.createStatement();
			Boolean flag = false;
			
			sql = "select isum from so_sodetails where isosid = " + id;
			jshj += getResultDouble(sql, result, stmt);
			
			sql = "delete from so_sodetails where isosid = " + id;
			flag = stmt.executeUpdate(sql) > 0;
			
			//修改信用额
			if(ddxye == 1 && StringUtils.isNotBlank(customCde)){
				sql  = " update SA_CreditSum set [fSOSum]=isnull(fSOSum ,0)-" 
						+ jshj + " where  iType =1 and ccuscode='" + customCde + "'";
				flag = stmt.executeUpdate(sql) > 0;
			}
			
			return flag;
		}catch(Exception e){
			e.printStackTrace();
			conn.rollback();
			return false;
		}finally{
			if(stmt != null) try{ stmt.close();} catch(Exception e){} 
			if(conn != null) try{ conn.close();} catch(Exception e){} 
		}
	}

	@Override
	public Boolean updateU8(SaleOrderU8VO vo, Double ddxye) throws Exception {
		//U8数据库连接
		Connection conn = PublicType.getConn();
		Statement stmt = conn.createStatement();
		ResultSet result = null;
		
		String sql = "";
		
		//查询表体最大id号
		Integer id = 0;//表体id
		String remote = "100";//id头部字符串
		sql = " select top 1 iChildId from ufsystem..UA_Identity where cacc_id= '" + PublicType.getDataBaseName() + "' and cvouchtype='SOMain'";
		id = getResultInt(sql,result,stmt);
		if(id + 1 > 9999999)	id = 1;
		else id ++;
		
		//输单日期，销售类型编码，业务类型，部门编码，业务负责人编码，客户编码，税率，币种，汇率，
		//单据模版号，退货标志（1-退货；0-正常），是否整单打折，状态（0-未审核；1-已审核），是否控制工作流，收货人电话
		//收货日期，卖场订单号，备注
		sql = "update so_somain set ddate = '" + DateFormat.date2Str(vo.getOrderDate(), 2) + "', " 
				+ " cSTCode ='" + vo.getSaleTypeCode() + "', cDepCode='" + vo.getDepartmentCode() + "',"
				+ " cPersonCode='" + vo.getPersonCode() + "',ccuscode='" + vo.getCustomCode() + "',"
				+ " itaxrate=" + vo.getITaxRate() + ",cexch_name='" +  vo.getCexchName() + "',"
				+ " iexchrate=" + vo.getIExchRate() + ",cDefine12='" + vo.getShrPhone() + "',"
				+ " cDefine4='" + DateFormat.date2Str(vo.getShDate(), 2) + "',cDefine11 ='" + vo.getStoreOrderCode() + "',"
				+ " cMemo='" + vo.getRemark() + "' where id = " + vo.getId();
		stmt.addBatch(sql);
		
		//更新表体
		
		Double jshj = 0d;
		if(Common.checkList(vo.getDetailList())){
			for(SaleOrderU8DetailVO detailVO  : vo.getDetailList()){
				if(StringUtils.equals(detailVO.getCInvCode(), "del"))
					continue;
				
				Double iTax = detailVO.getJshj() - detailVO.getWsje();//原币税额
				
				Double inatmoney =  detailVO.getWsje() * vo.getIExchRate() * 0.01;//本币无税金额=原币无税金额*汇率
				Double inatUnitPrice = inatmoney / detailVO.getSl();//本币无税单价=本币无税金额/数量
				Double inattax = inatmoney * detailVO.getTax() * 0.01;//本币税额 = 本币无税金额*税率
				Double inatsum = inatmoney + inattax;//本币价税合计 = 本币无税金额 +税额
				Double inatdiscount = detailVO.getZke() * vo.getIExchRate() * 0.01;//本币折扣额=原币折扣额 *汇率
				
				Double zhl = null;
				if(detailVO.getJs() != null && detailVO.getSl() != null && detailVO.getJs() != 0)
					zhl = PublicType.setDoubleScale6(detailVO.getSl() / detailVO.getJs() );
				
				if(detailVO.getId() != null && detailVO.getId().toString().length() < 10 ){//新增表体
					//主表标识id，字表标识id,订单编号，存货编码，存货名称，件数，数量，原币无税单价，原币含税单价，原币无税金额，原币税额，原币价税合计，原币折扣额
					//本币无税单价 ，本币无税金额，本币税额，本币价税合计，本币折扣额，扣率，扣率2，税率，
					//计量单位编码（select top 1 cSaComUnitCode from inventory where cinvcode=''），
					//新旧品分类，客户商品号，对方名，折前无税合计，存货用途，生产日期，备注,转换率
					sql = "insert into so_sodetails (id,isosid,csocode,cinvcode,cInvName, "
							+ "inum,iquantity,iUnitPrice,iTaxUnitPrice,imoney,itax,isum,idiscount,"
							+ "inatUnitPrice,inatmoney,inattax,inatsum,inatdiscount,kl,kl2,itaxrate,"
							+ "cDefine29,cDefine26,cDefine33,cDefine27,cFree2,cDefine36,cDefine28,iInvExchRate,iQuotedPrice ) values ( '"
							+ vo.getId() + "','" + remote + String.format("%07d", id) + "','" + vo.getOrderCode() + "','"
							+ detailVO.getCInvCode() + "','" + detailVO.getChmc() + "'," + detailVO.getJs() + "," + detailVO.getSl() + ","
							+ detailVO.getWsdj() + "," + detailVO.getHsdj() + "," + detailVO.getWsje() + "," + iTax + "," + detailVO.getJshj() + "," + detailVO.getZke() + ","
							+ inatUnitPrice + "," + inatmoney + "," + inattax + "," + inatsum + "," + inatdiscount + ","
							+ detailVO.getKl() + "," + detailVO.getKl2() + "," + detailVO.getTax() + ",'"
							+ detailVO.getXjpfl() + "'," + detailVO.getKhsph() + ",'" + detailVO.getDfm() + "'," + detailVO.getZqwshj() + ",'"
							+ detailVO.getChyt() + "','" + DateFormat.date2Str(detailVO.getScrq(), 2) + "','" + detailVO.getBz() + "'," + zhl + "," + detailVO.getBj() + ")";
					
					stmt.addBatch(sql);
					
					jshj += detailVO.getJshj();
					id ++;
					
				}else{//更新表体
					
					sql = "select isum from so_sodetails where isosid = " + detailVO.getId();
					jshj -= getResultDouble(sql,result,stmt);
					
					//存货编码，存货名称，件数，数量，原币无税单价，原币含税单价，原币无税金额，原币税额，原币价税合计，原币折扣额
					//本币无税单价 ，本币无税金额，本币税额，本币价税合计，本币折扣额，扣率，扣率2，税率，
					//计量单位编码（select top 1 cSaComUnitCode from inventory where cinvcode=''），
					//新旧品分类，客户商品号，对方名，折前无税合计，存货用途，生产日期，备注
					sql = "update so_sodetails set cinvcode = '" + detailVO.getCInvCode() + "',cInvName='" + detailVO.getChmc() + "',"
							+ " inum=" + detailVO.getJs() + ",iquantity=" + detailVO.getSl() + ",iUnitPrice=" + detailVO.getWsdj() + ","
							+ " iTaxUnitPrice=" + detailVO.getHsdj() + ",imoney=" +  detailVO.getWsje() + ",itax=" + iTax + ",isum=" 
							+ detailVO.getJshj() + ",idiscount=" + detailVO.getZke() + ",inatUnitPrice="
							+ inatUnitPrice + ",inatmoney=" + inatmoney + ",inattax=" + inattax + ",inatsum=" + inatsum + ",inatdiscount=" 
							+ inatdiscount + ",kl=" + detailVO.getKl() + ",kl2=" + detailVO.getKl2() + ",itaxrate=" + detailVO.getTax() + ",cunitid="
							+ "(select top 1 cSaComUnitCode from inventory where cinvcode='" + detailVO.getCInvCode() + "')" + ",cDefine29='"
							+ detailVO.getXjpfl() + "',cDefine26='" + detailVO.getKhsph() + "',cDefine33='" + detailVO.getDfm() + "',cDefine27=" 
							+ detailVO.getZqwshj() + ",cFree2='" + detailVO.getChyt() + "',cDefine36='" + DateFormat.date2Str(detailVO.getScrq(), 2) 
							+ "',cDefine28='" + detailVO.getBz() + "', iInvExchRate=" + zhl + " where isosid = " + detailVO.getId();
					
					jshj += detailVO.getJshj();
					stmt.addBatch(sql);
				}
			}
		}
		if(id != 0){
			//修改最大id号表
			sql = " update ufsystem..ua_identity set ichildid=" + id + " where cacc_id='" + PublicType.getDataBaseName() + "' and cvouchtype='SOMain'";
			stmt.addBatch(sql);
		}
		
		//修改信用额
		if(ddxye == 1 && StringUtils.isNotBlank(vo.getCustomCode())){
			sql  = " update SA_CreditSum set [fSOSum]=isnull(fSOSum ,0)+" 
					+ jshj + " where  iType =1 and ccuscode='" + vo.getCustomCode() + "'";
			stmt.addBatch(sql);
		}
		
		try{
			conn.setAutoCommit(false);
			stmt.executeBatch();
			conn.commit();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			conn.rollback();
			return false;
		}finally{
			if(stmt != null) try{ stmt.close();} catch(Exception e){} 
			if(conn != null) try{ conn.close();} catch(Exception e){} 
		}
	}

	@Override
	public Object[] getRate(String cinvcode) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		Object[] resultArray = new Object[3];
		
		String sql = " select top 1 c.ccomunitname,c1.ccomunitname, c1.iChangRate from  inventory i "
				+ " left join ComputationUnit c on i.cComUnitCode = c.cComunitCode and i.cgroupcode = c.cgroupcode "
				+ " left join ComputationUnit c1  on i.cSAComUnitCode  = c1.cComunitCode and i.cgroupcode = c1.cgroupcode "
				+ " where i.cinvcode = '" + cinvcode + "'";
		
		try {
			conn = PublicType.getConn();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			
			while(result.next()){
				resultArray[0] = result.getString(1);
				resultArray[1] = result.getString(2);
				resultArray[2] = result.getDouble(3);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(result != null) try{ result.close();} catch(Exception e){}
			if(stmt != null) try{ stmt.close();} catch(Exception e){} 
			if(conn != null) try{ conn.close();} catch(Exception e){} 
		}
		return resultArray;
	}

	@Override
	public Double getXye(String customerCode) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		
		Double xye = 0d;
		
		String sql = "select top 1 icuscreline from customer where cCusCode='" + customerCode + "'";
		
		try {
			conn = PublicType.getConn();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			
			while(result.next()){
				xye = result.getDouble(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(result != null) try{ result.close();} catch(Exception e){}
			if(stmt != null) try{ stmt.close();} catch(Exception e){} 
			if(conn != null) try{ conn.close();} catch(Exception e){} 
		}
		return xye;
	}

	@Override
	public Double getXyye(String customerCode) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		
		Double xyye = 0d;
		
		String sql = "select (isnull(farsum,0) + isnull(f08sum,0) + isnull(fSOSum,0) + isnull(fDLSum,0) + isnull(fBLSum,0) ) from SA_CreditSum "
					+ " where  iType =1 and ccuscode='" + customerCode + "'";
		
		try {
			conn = PublicType.getConn();
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			
			while(result.next()){
				xyye = result.getDouble(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(result != null) try{ result.close();} catch(Exception e){}
			if(stmt != null) try{ stmt.close();} catch(Exception e){} 
			if(conn != null) try{ conn.close();} catch(Exception e){} 
		}
		return xyye;
	}

}
