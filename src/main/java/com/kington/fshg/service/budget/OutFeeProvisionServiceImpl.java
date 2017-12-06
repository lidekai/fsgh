package com.kington.fshg.service.budget;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.kington.fshg.common.PublicType.ApproveState;
import com.kington.fshg.model.budget.InFeeProvision;
import com.kington.fshg.model.budget.InFeeProvisionVO;
import com.kington.fshg.model.budget.OutFeeProvision;
import com.kington.fshg.model.budget.OutFeeProvisionVO;
import com.kington.fshg.model.budget.ProvisionProject;
import com.kington.fshg.model.charge.InFeeVerification;
import com.kington.fshg.model.charge.InFeeVerificationVO;
import com.kington.fshg.model.charge.OutFeeVerification;
import com.kington.fshg.model.charge.OutFeeVerificationVO;
import com.kington.fshg.model.info.Custom;
import com.kington.fshg.model.info.CustomsTypeVO;
import com.kington.fshg.model.info.Store;
import com.kington.fshg.service.BaseServiceImpl;
import com.kington.fshg.service.charge.InFeeVerificationService;
import com.kington.fshg.service.charge.OutFeeVerificationService;
import com.kington.fshg.service.info.CustomService;

/**
 *  合同外费用预提服务类
 */
public class OutFeeProvisionServiceImpl extends BaseServiceImpl<OutFeeProvision, OutFeeProvisionVO>
		implements OutFeeProvisionService {
	private static final long serialVersionUID = -698250763388502995L;
	
	@Resource
	private ProvisionProductDetailService ppdService;
	@Resource
	private InFeeProvisionService inFeeProService;
	@Resource
	private InFeeVerificationService inFeeVerService;
	@Resource
	private OutFeeVerificationService outFeeVerService;
	@Resource
	private CustomService customSevice;

	@Override
	protected String getQueryStr(OutFeeProvisionVO vo) throws Exception {
		StringBuffer sb = new StringBuffer();
		if(StringUtils.isNotBlank(vo.getProvisionCode()))
			sb.append(" and o.provisionCode like :code ");
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
		}
		if(vo.getProvisionProject() != null){
			if(vo.getProvisionProject().getProjectType() != null){
				sb.append(" and o.provisionProject.projectType = :type ");
			}
			
			if(StringUtils.isNotBlank(vo.getProvisionProject().getFeeName()))
				sb.append(" and o.provisionProject.feeName like :feeName ");
		}
		if(vo.getApproveState() != null)
			sb.append(" and o.approveState =:approveState ");
		if(StringUtils.isNotBlank(vo.getDateStart()))
			sb.append(" and o.provisionTime >= :dateStart ");
		if(StringUtils.isNotBlank(vo.getDateEnd()))
			sb.append(" and o.provisionTime <= :dateEnd ");
		if(StringUtils.isNotBlank(vo.getStartDate()))
			sb.append(" and o.startTime >= :startDate ");
		if(StringUtils.isNotBlank(vo.getEndDate()))
			sb.append(" and o.endTime <= :endDate ");
		
		if(Common.checkList(vo.getAreaIds()))
			sb.append(" and o.custom.area.id in (:areaIds)");
		if(Common.checkList(vo.getParentAreaIds()))
			sb.append(" and o.custom.area.parentArea.id in (:parentAreaIds)");
		return sb.toString();
	}

	@Override
	protected void setQueryParm(Query query, OutFeeProvisionVO vo)
			throws Exception {
		if(StringUtils.isNotBlank(vo.getProvisionCode()))
			query.setParameter("code",Common.SYMBOL_PERCENT + vo.getProvisionCode() +Common.SYMBOL_PERCENT);
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
		}
		if(vo.getProvisionProject() != null){
			if(vo.getProvisionProject().getProjectType() != null){
				query.setParameter("type", vo.getProvisionProject().getProjectType());
			}
			if(StringUtils.isNotBlank(vo.getProvisionProject().getFeeName()))
				query.setParameter("feeName", Common.SYMBOL_PERCENT + vo.getProvisionProject().getFeeName()
						+ Common.SYMBOL_PERCENT);
		}
		if(vo.getApproveState() != null)
			query.setParameter("approveState", vo.getApproveState());
		
		if(StringUtils.isNotBlank(vo.getDateStart()))
			query.setParameter("dateStart", DateFormat.str2Date(vo.getDateStart(), 2));
		if(StringUtils.isNotBlank(vo.getDateEnd()))
			query.setParameter("dateEnd", DateFormat.str2Date(vo.getDateEnd(), 2));
		
		if(StringUtils.isNotBlank(vo.getStartDate()))
			query.setParameter("startDate", DateFormat.str2Date(vo.getStartDate(), 2));
		if(StringUtils.isNotBlank(vo.getEndDate()))
			query.setParameter("endDate", DateFormat.str2Date(vo.getEndDate(), 2));
		
		if(Common.checkList(vo.getAreaIds()))
			query.setParameter("areaIds", vo.getAreaIds());
		if(Common.checkList(vo.getParentAreaIds()))
			query.setParameter("parentAreaIds", vo.getParentAreaIds());
	}

	@Override
	protected void switchVO2PO(OutFeeProvisionVO vo, OutFeeProvision po)
			throws Exception {
		if(po == null)
			po = new OutFeeProvision();
		if(vo.getCustom() != null)
			po.setCustom(vo.getCustom());
		if(vo.getProvisionProject() != null)
			po.setProvisionProject(vo.getProvisionProject());
		if(StringUtils.isNotBlank(vo.getProvisionCode()))
			po.setProvisionCode(vo.getProvisionCode());
		if(vo.getApproveState() != null)
			po.setApproveState(vo.getApproveState());
		if(StringUtils.isNotBlank(vo.getSalesman()))
			po.setSalesman(vo.getSalesman());
		if(StringUtils.isNotBlank(vo.getOpinion()))
			po.setOpinion(vo.getOpinion());
		if(vo.getTotalFee() != null)
			po.setTotalFee(vo.getTotalFee());
		if(StringUtils.isNotBlank(vo.getRemark()))
			po.setRemark(vo.getRemark());
		if(vo.getProvisionTime() != null)
			po.setProvisionTime(vo.getProvisionTime());
		if(vo.getStartTime() != null)
			po.setStartTime(vo.getStartTime());
		if(vo.getEndTime() != null)
			po.setEndTime(vo.getEndTime());
		
		po.setStoreScale(vo.getStoreScale());
	}

	@SuppressWarnings("unchecked")
	@Override
	public OutFeeProvision getByCde(String cde) throws Exception {
		String sql="from OutFeeProvision o where o.provisionCode =:code ";
		Query query = em.createQuery(sql);
		query.setParameter("code", cde);
		List<OutFeeProvision> list = query.getResultList();
		if(list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	@Override
	public boolean delete(Long id) throws Exception {
		ppdService.deletProductDetail(id);
		String hql="delete from bud_out_fee_provision where id = " + id;
		Query query = em.createNativeQuery(hql);
		return query.executeUpdate() > 0;
	}

	@Override
	public void updateState(Long provisionId, ApproveState state) throws Exception {
		String sql = "update bud_out_fee_provision set approveState = :state where id = :id ";
		Query query = em.createNativeQuery(sql);
		query.setParameter("state", state.toString());
		query.setParameter("id", provisionId);
		query.executeUpdate();
	}
	
	//预提编号格式：一个字母标识+年月（6位）+ 5位流水号
	@SuppressWarnings("unchecked")
	@Override
	public synchronized String getCode(){
		String code = "B" + DateFormat.date2Str(new Date(), 4);
		String sql = "select provisionCode from bud_out_fee_provision where provisionCode like '" + code + "%' order by provisionCode desc limit 1 ";
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
	public Map<Long, List<OutFeeProvisionVO>> getOutFeeMap(OutFeeProvisionVO vo) throws Exception {
		
		vo.setObjectsPerPage(Integer.MAX_VALUE);
		
		Map<Long, List<OutFeeProvisionVO>>  map = new HashMap<Long, List<OutFeeProvisionVO>>();
		Long customId = 0L;
		List<OutFeeProvisionVO> list = new ArrayList<OutFeeProvisionVO>();
		
		Date startDate = DateFormat.str2Date(vo.getDateStart(), 2);
		Date endDate = DateFormat.str2Date(vo.getDateEnd(), 2);
		
		for(Object[] o : getOutFeeProvisionList(vo)){
			if(o[5] != null && Double.parseDouble(o[5].toString()) != 0){
				OutFeeProvisionVO outVO = new OutFeeProvisionVO();
				
				if(o[0] != null)	outVO.setCustom(customSevice.getById(Long.parseLong(o[0].toString())));
				if(o[1] != null)	outVO.setProvisionCode(o[1].toString());
				if(o[2] != null)	outVO.setProjectName(o[2].toString());
				if(o[3] != null)	outVO.setStartTime(DateFormat.str2Date(o[3].toString(), 2));
				if(o[4] != null)	outVO.setEndTime(DateFormat.str2Date(o[4].toString(), 2));
				if(o[5] != null)	outVO.setTotalFee(Double.parseDouble(o[5].toString()));
				if(o[6] != null)	outVO.setProvisionTime(DateFormat.str2Date(o[6].toString(), 2));
				if(o[7] != null){
					outVO.setSjFee(getSjFee(Double.parseDouble(o[7].toString()),outVO,startDate,endDate));
				}	
				
				if(!customId.equals(outVO.getCustom().getId()) && !customId.equals(0L)){
					map.put(customId, list);
					list = new ArrayList<OutFeeProvisionVO>();
				}
				list.add(outVO);
				customId = outVO.getCustom().getId();
			}
		}
		map.put(customId, list);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	private List<Object[]> getOutFeeProvisionList(OutFeeProvisionVO vo){
		String sql = "SELECT b.custom_id,b.provisionCode,p.feeName,b.startTime,b.endTime,b.totalFee,b.provisionTime,TRUNCATE(b.totalFee/( MONTH(b.endTime)-MONTH(b.startTime) +1) ,2) "
				+ " FROM bud_out_fee_provision b LEFT JOIN bud_provision_project p ON b.project_id = p.id "
				+ " where  b.approveState = 'SPJS' ";
		sql += "and ((b.startTime <= ':startTime' AND b.endTime <= ':endTime' AND b.endTime >=':startTime') "
				+ " or (b.startTime <= ':startTime' AND b.endTime >= ':endTime') "
				+ " or (b.startTime >= ':startTime' AND b.startTime <= ':endTime' AND b.endTime >= ':endTime') "
				+ " or (b.startTime >= ':startTime' AND b.endTime <= ':endTime')) ";
		if(StringUtils.isNotBlank(vo.getDateStart()))
			sql = sql.replace(":startTime", vo.getDateStart());
		if(StringUtils.isNotBlank(vo.getDateEnd()))
			sql = sql.replace(":endTime", vo.getDateEnd());
		if(vo.getUserId() != null)
			sql += " and b.custom_id in (select id from info_custom where userId =" + vo.getUserId() + ")";
		if(Common.checkList(vo.getAreaIds()))
			sql += " and b.custom_id in (select id from info_custom where areaId in (" + vo.getAreaIds().toString().replace("[", "").replace("]", "") + "))";
		if(Common.checkList(vo.getParentAreaIds()))
			sql += " and b.custom_id in (select c.id from info_custom c left join info_area a on c.areaId = a.id where a.parentId in ("+ vo.getParentAreaIds().toString().replace("[", "").replace("]", "") +"))";
		Query query = em.createNativeQuery(sql);
		return (List<Object[]>)query.getResultList();
	}
	
	//返回统计月份内，预提所需分摊的月份数（vo为显示列表,start为统计开始时间，end为统计结束时间）
	private Double getSjFee(Double ftFee, OutFeeProvisionVO vo, Date start, Date end){
		Integer monthCount = 0;
		if(ftFee > 0 && start != null && end != null
				&& vo.getStartTime() != null && vo.getEndTime() != null){
			if((start.after(vo.getStartTime()) || start.equals(vo.getStartTime()))
					&& (vo.getEndTime().after(start) || vo.getEndTime().equals(start))
					&& (end.after(vo.getEndTime()) || end.equals(vo.getEndTime())))
				monthCount = DateFormat.getMonthCount(start, vo.getEndTime());
			else if((start.after(vo.getStartTime()) || start.equals(vo.getStartTime()))
					&& vo.getEndTime().after(end))
				monthCount = DateFormat.getMonthCount(start, end);
			else if(vo.getStartTime().after(start) && 
					(end.after(vo.getEndTime()) || end.equals(vo.getEndTime())))
				monthCount = DateFormat.getMonthCount(vo.getStartTime(), vo.getEndTime());
			else if(vo.getStartTime().after(start) && vo.getEndTime().after(end))
				monthCount = DateFormat.getMonthCount(vo.getStartTime(), end);
		}
		return PublicType.setDoubleScale(ftFee*monthCount);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getProDetailList(OutFeeProvisionVO vo)
			throws Exception {
		String sql = "SELECT a1.parentArea,a.areaName,c.customName,c.customCde,b.provisionTime,bp.feeName,p.productName,p.stockCde,p.standard,d.cost,s.id,b.provisionCode,s.storeName "
				+ " FROM bud_provision_product_detail d "
				+ " LEFT JOIN info_store_product sp ON d.store_product_id = sp.id "
				+ " LEFT JOIN info_store s ON sp.storeId = s.id "
				+ " LEFT JOIN info_product p ON sp.productId = p.id "
				+ " LEFT JOIN bud_out_fee_provision b ON d.provision_id = b.id "
				+ " LEFT JOIN bud_provision_project bp ON b.project_id = bp.id "
				+ " LEFT JOIN info_custom c ON b.custom_id = c.id "
				+ " LEFT JOIN info_area a ON c.areaId = a.id "
				+ " LEFT JOIN (SELECT id , areaName parentArea FROM info_area WHERE parentId IS NULL)a1 ON a.parentId = a1.id "
				+ " WHERE b.approveState = 'SPJS' ";
		sql += getWhereSql(vo);
			
		if(StringUtils.isNotBlank(vo.getDateStart()))
			sql += " and b.provisionTime >= '" + vo.getDateStart() + "' ";
		if(StringUtils.isNotBlank(vo.getDateEnd()))
			sql += " and b.provisionTime <= '" + vo.getDateEnd() + "'";
		if(vo.getUserId() != null)
			sql += " and c.userId =" + vo.getUserId();
		if(Common.checkList(vo.getAreaIds()))
			sql += " and a.id in (" + vo.getAreaIds().toString().replace("[", "").replace("]", "") + ")";
		if(Common.checkList(vo.getParentAreaIds()))
			sql += " and a1.id in ("+ vo.getParentAreaIds().toString().replace("[", "").replace("]", "") +")";

		
		sql += " ORDER BY a1.id,a.id,c.id,s.id,b.provisionTime ";
		Query query = em.createNativeQuery(sql);
		return (List<Object[]>)query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getProVerDetailList(OutFeeProvisionVO vo)
			throws Exception {
		String sql = "SELECT a1.parentArea,a.areaName,c.customName,c.customCde,b.verTime,bp.feeName,p.productName,p.stockCde,p.standard,d.cost,s.id,b.verCode,s.storeName "
				+ " FROM charge_verification_product_detail d "
				+ " LEFT JOIN info_store_product sp ON d.store_product_id = sp.id "
				+ " LEFT JOIN info_store s ON sp.storeId = s.id "
				+ " LEFT JOIN info_product p ON sp.productId = p.id "
				+ " LEFT JOIN charge_out_fee_verification b ON d.verificationId = b.id "
				+ " LEFT JOIN bud_out_fee_provision bf ON b.provision_id = bf.id "
				+ " LEFT JOIN bud_provision_project bp ON bf.project_id = bp.id "
				+ " LEFT JOIN info_custom c ON b.custom_id = c.id "
				+ " LEFT JOIN info_area a ON c.areaId = a.id "
				+ " LEFT JOIN (SELECT id , areaName parentArea FROM info_area WHERE parentId IS NULL)a1 ON a.parentId = a1.id "
				+ " WHERE b.approveState = 'SPJS' ";
		sql += getWhereSql(vo);
		
		if(StringUtils.isNotBlank(vo.getDateStart()))
			sql += " and b.verTime >= '" + vo.getDateStart() + "' ";
		if(StringUtils.isNotBlank(vo.getDateEnd()))
			sql += " and b.verTime <= '" + vo.getDateEnd() + "'";
		if(vo.getUserId() != null)
			sql += " and c.userId =" + vo.getUserId();
		if(Common.checkList(vo.getAreaIds()))
			sql += " and a.id in (" + vo.getAreaIds().toString().replace("[", "").replace("]", "") + ")";
		if(Common.checkList(vo.getParentAreaIds()))
			sql += " and a1.id in ("+ vo.getParentAreaIds().toString().replace("[", "").replace("]", "") +")";
		
			
		sql += " ORDER BY a1.id,a.id,c.id,s.id,b.verTime ";
		Query query = em.createNativeQuery(sql);
		return (List<Object[]>)query.getResultList();
	}
	
	private String getWhereSql(OutFeeProvisionVO vo){
		String sql = "";
		if(vo.getCustom() != null){
			if(StringUtils.isNotBlank(vo.getCustom().getCustomName()))
				sql += " and c.customName like '%" + vo.getCustom().getCustomName() + "%' ";
			if(StringUtils.isNotBlank(vo.getCustom().getCustomCde()))
				sql += " and c.customCde = '" + vo.getCustom().getCustomCde() + "' ";
			if(vo.getCustom().getArea() != null){
				if(Common.checkLong(vo.getCustom().getArea().getId()))
					sql += " and a.id = " + vo.getCustom().getArea().getId();
				else if(vo.getCustom().getArea().getParentArea() != null 
						&& Common.checkLong(vo.getCustom().getArea().getParentArea().getId()))
					sql += " and a1.id = " + vo.getCustom().getArea().getParentArea().getId();
			}
		}
		
		if(vo.getProject() != null && Common.checkLong(vo.getProject().getId()))
			sql += " and bp.id = " + vo.getProject().getId();
		
		if(vo.getProduct() != null){
			if(StringUtils.isNotBlank(vo.getProduct().getProductName()))
				sql += " and p.productName like '%" + vo.getProduct().getProductName() + "%' ";
			if(StringUtils.isNotBlank(vo.getProduct().getStockCde()))
				sql += " and p.stockCde = '" + vo.getProduct().getStockCde() + "' ";
		}
		
		if(StringUtils.isNotBlank(vo.getStoreName()))
			sql += " and s.storeName like '%" + vo.getStoreName() + "%' ";
		
		return sql;
	}

	@Override
	public Map<Long, List<Object[]>> getProDetailMap(OutFeeProvisionVO vo)
			throws Exception {
		Map<Long, List<Object[]>> map = new HashMap<Long, List<Object[]>>();
		List<Object[]> list = new ArrayList<Object[]>();
		
		Object storeId = null;
		for(Object[] o : getProDetailList(vo)){
			if(storeId != null && !storeId.equals(o[10])){
				BigInteger  b = (BigInteger )storeId;
				map.put(b.longValue(), list);
				list = new ArrayList<Object[]>();
			}
			
			list.add(o);
			storeId = o[10];
		}
		
		if(storeId != null){
			BigInteger  b = (BigInteger)storeId;
			map.put(b.longValue(), list);
		}
		return map;
	}

	@Override
	public Map<Long, List<Object[]>> getProVerDetailMap(OutFeeProvisionVO vo)
			throws Exception {
		Map<Long, List<Object[]>> map = new HashMap<Long, List<Object[]>>();
		List<Object[]> list = new ArrayList<Object[]>();
		
		Object storeId = null;
		for(Object[] o : getProVerDetailList(vo)){
			if(storeId != null && !storeId.equals(o[10])){
				BigInteger b = (BigInteger)storeId;
				map.put(b.longValue(), list);
				list = new ArrayList<Object[]>();
			}
			
			list.add(o);
			storeId = o[10];
		}
		if(storeId != null){
			BigInteger  b = (BigInteger)storeId;
			map.put(b.longValue(), list);
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getProVer(OutFeeProvisionVO vo) throws Exception {
		String where = "";
		if(StringUtils.isNotBlank(vo.getDateStart()))
			where += " and provisionTime >= '" + vo.getDateStart() + "'";
		if(StringUtils.isNotBlank(vo.getDateEnd()))
			where += " and provisionTime <= '" + vo.getDateEnd() + "'";
		
		String where1 = "  AND ((startTime <= ':startTime' AND endTime <= ':endTime' AND endTime >=':startTime') "
				+ " OR (startTime <= ':startTime' AND endTime >= ':endTime') "
				+ " OR (startTime >= ':startTime' AND startTime <= ':endTime' AND endTime >= ':endTime') "
				+ " OR (startTime >= ':startTime' AND endTime <= ':endTime')) ";
		
		String sql = "SELECT a1.parentArea,a.areaName,c.customName,c.customCde, "
				+ " temp.inProCount,temp1.inVerRCount,temp2.inVerBCount,temp3.outProCount,temp4.outVerRCount,temp5.outVerBCount,temp6.saleBillCount,c.id "
				+ " FROM info_custom c LEFT JOIN info_area a ON c.areaId = a.id "
				+ " LEFT JOIN ( SELECT id , areaName parentArea FROM info_area WHERE parentId IS NULL ) a1 ON a.parentId = a1.id "
				+ " LEFT JOIN ( SELECT SUM(inFeeCount) inProCount,custom_id FROM bud_in_fee_provision WHERE approveState = 'SPJS' " 
				+ where + " GROUP BY custom_id )temp ON temp.custom_id = c.id "
				+ " LEFT JOIN ( SELECT SUM(totalFee) inVerRCount,custom_id FROM charge_in_fee_verification WHERE approveState = 'SPJS' AND verDirection = 'HCYT' "
				+ where.replace("provisionTime", "verDate") + " GROUP BY custom_id )temp1 ON temp1.custom_id = c.id "
				+ " LEFT JOIN ( SELECT SUM(totalFee) inVerBCount,custom_id FROM charge_in_fee_verification WHERE approveState = 'SPJS' AND verDirection = 'LCYT' "
				+ where.replace("provisionTime", "verDate") + " GROUP BY custom_id)temp2 ON temp2.custom_id = c.id "
				+ " LEFT JOIN (SELECT SUM(totalFee) outProCount,custom_id FROM bud_out_fee_provision WHERE approveState = 'SPJS' "
				+ where1.replace(":startTime", vo.getDateStart()).replace(":endTime", vo.getDateEnd()) + " GROUP BY custom_id)temp3 ON temp3.custom_id = c.id "
				+ " LEFT JOIN (SELECT SUM(totalFee) outVerRCount,custom_id FROM charge_out_fee_verification WHERE approveState = 'SPJS' AND verDirection = 'HCYT' "
				+ where.replace("provisionTime", "verTime") + " GROUP BY custom_id )temp4 ON temp4.custom_id = c.id "
				+ " LEFT JOIN (SELECT SUM(totalFee) outVerBCount,custom_id FROM charge_out_fee_verification WHERE approveState = 'SPJS' AND verDirection = 'LCYT' "
				+ where.replace("provisionTime", "verTime") + " GROUP BY custom_id )temp5 ON temp5.custom_id = c.id "
				+ " LEFT JOIN (SELECT SUM(countPrice) saleBillCount, customCde from sale_bill where 1 = 1 " + where.replace("provisionTime", "createDate") + " GROUP BY customCde )temp6 on temp6.customCde = c.customCde "
				+ " where 1 = 1 and (temp.inProCount is not null or temp1.inVerRCount is not null or temp2.inVerBCount is not null or temp3.outProCount is not null or temp4.outVerRCount is not null or temp5.outVerBCount is not null )";
		
		if(vo.getCustom() != null){
			if(StringUtils.isNotBlank(vo.getCustom().getCustomName()))
				sql += " and c.customName like '%" + vo.getCustom().getCustomName() + "%' ";
			if(StringUtils.isNotBlank(vo.getCustom().getCustomCde()))
				sql += " and c.customCde = '" + vo.getCustom().getCustomCde() + "' ";
			if(vo.getCustom().getArea() != null){
				if(vo.getCustom().getArea() != null){
					if(Common.checkLong(vo.getCustom().getArea().getId()))
						sql += " and a.id = " + vo.getCustom().getArea().getId();
					else if(vo.getCustom().getArea().getParentArea() != null 
							&& Common.checkLong(vo.getCustom().getArea().getParentArea().getId()))
						sql += " and a1.id = " + vo.getCustom().getArea().getParentArea().getId();
				}
			}
		}
		
		if(vo.getUserId() != null)
			sql += " and c.userId =" + vo.getUserId();
		if(Common.checkList(vo.getAreaIds()))
			sql += " and a.id in (" + vo.getAreaIds().toString().replace("[", "").replace("]", "") + ")";
		if(Common.checkList(vo.getParentAreaIds()))
			sql += " and a1.id in ("+ vo.getParentAreaIds().toString().replace("[", "").replace("]", "") +")";

	
		sql += " ORDER BY a1.id,a.id,c.id ";
		
		Query query  = em.createNativeQuery(sql);
		List<Object[]> resultList = (List<Object[]>) query.getResultList();
		Map<Long, List<OutFeeProvisionVO>> map = getOutFeeMap(vo);
		
		
		List<Object[]> resultList1 = new ArrayList<Object[]>();
		for(Object[] o : resultList){
			Object[] oo = new Object[13];
			for(int i=0;i<o.length;i++){
				oo[i] = o[i];
			}
			Double sjFee = 0d;
			if(Common.checkList(map.get(Long.parseLong(o[11].toString())))){
				for(OutFeeProvisionVO outVO : map.get(Long.parseLong(o[11].toString())))
					sjFee += outVO.getSjFee();
			}
			oo[12] = sjFee;
			
			resultList1.add(oo);
		}
		
		return resultList1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getProductProVer(OutFeeProvisionVO vo)
			throws Exception {
		String where = " WHERE p.approveState = 'SPJS' ";
		if(StringUtils.isNotBlank(vo.getDateStart()))
			where += " and p.verTime >= '" + vo.getDateStart() + "'";
		if(StringUtils.isNotBlank(vo.getDateEnd()))
			where += " and p.verTime <= '" + vo.getDateEnd() + "'";
		
		String sql = " SELECT a1.parentArea,a.areaName,c.customName,c.customCde,p.productName,p.stockCde,p.standard,temp.proCost ,temp1.verRCost,temp2.verBCost,s.storeName "
				+ " FROM info_store_product sp LEFT JOIN info_store s ON sp.storeId = s.id LEFT JOIN info_custom c ON s.custom_id = c.id "
				+ " LEFT JOIN info_area a ON c.areaId = a.id LEFT JOIN ( SELECT id , areaName parentArea FROM info_area WHERE parentId IS NULL ) a1 ON a.parentId = a1.id "
				+ " LEFT JOIN info_product p ON sp.productId = p.id "
				+ " LEFT JOIN (SELECT b.store_product_id, SUM(b.cost) proCost FROM bud_provision_product_detail b "
				+ " LEFT JOIN bud_out_fee_provision p ON b.provision_id = p.id "
				+ where.replace("verTime", "provisionTime") + " GROUP BY b.store_product_id) temp ON sp.id = temp.store_product_id "
				+ " LEFT JOIN (SELECT v.store_product_id, SUM(v.cost) verRCost FROM charge_verification_product_detail v "
				+ " LEFT JOIN charge_out_fee_verification p ON v.verificationId  = p.id " 
				+ where + " AND p.verDirection = 'HCYT' GROUP BY v.store_product_id) temp1 ON temp1.store_product_id = sp.id "
				+ " LEFT JOIN (SELECT v.store_product_id, SUM(v.cost) verBCost FROM charge_verification_product_detail v "
				+ " LEFT JOIN charge_out_fee_verification p ON v.verificationId  = p.id "
				+ where + " AND p.verDirection = 'LCYT' GROUP BY v.store_product_id) temp2 ON temp2.store_product_id = sp.id WHERE 1 = 1 "
				+ " and (temp.proCost is not null or temp1.verRCost is not null or temp2.verBCost is not null) ";
		
		if(vo.getCustom() != null){
			if(vo.getCustom().getArea() != null){
				if(StringUtils.isNotBlank(vo.getCustom().getCustomName()))
					sql += " and c.customName like '%" + vo.getCustom().getCustomName() + "%' ";
				if(StringUtils.isNotBlank(vo.getCustom().getCustomCde()))
					sql += " and c.customCde = '" + vo.getCustom().getCustomCde() + "' ";
				if(vo.getCustom().getArea() != null){
					if(Common.checkLong(vo.getCustom().getArea().getId()))
						sql += " and a.id = " + vo.getCustom().getArea().getId();
					else if(vo.getCustom().getArea().getParentArea() != null 
							&& Common.checkLong(vo.getCustom().getArea().getParentArea().getId()))
						sql += " and a1.id = " + vo.getCustom().getArea().getParentArea().getId();
				}
			}
		}
		
		if(vo.getProduct() != null){
			if(StringUtils.isNotBlank(vo.getProduct().getProductName()))
				sql += " and p.productName like '%" + vo.getProduct().getProductName() + "%' ";
			if(StringUtils.isNotBlank(vo.getProduct().getStockCde()))
				sql += " and p.stockCde = '" + vo.getProduct().getStockCde() + "' ";
		}
		
		if(StringUtils.isNotBlank(vo.getStoreName()))
			sql += " and s.storeName like '%" + vo.getStoreName() + "%' ";
		
		if(vo.getUserId() != null)
			sql += " and c.userId =" + vo.getUserId();
		if(Common.checkList(vo.getAreaIds()))
			sql += " and a.id in (" + vo.getAreaIds().toString().replace("[", "").replace("]", "") + ")";
		if(Common.checkList(vo.getParentAreaIds()))
			sql += " and a1.id in ("+ vo.getParentAreaIds().toString().replace("[", "").replace("]", "") +")";
	
		sql += " ORDER BY s.id ";
		
		Query query = em.createNativeQuery(sql);

		return (List<Object[]>) query.getResultList();
	}

	@Override
	public void exportBudget(String[] heads, List<Custom> customList, Map<String, String> map, 
			List<Map<String,String>> listmap, OutFeeProvisionVO vo,String type) throws Exception {
		
		//预提明细
		if(type.equals("BUDGET")){
			//合同内预提
			InFeeProvisionVO inVO = new InFeeProvisionVO();
			if(vo.getCustom() != null)
				inVO.setCustom(vo.getCustom());
			inVO.setObjectsPerPage(Integer.MAX_VALUE);
			inVO.setCreateTimeStart(vo.getDateStart());
			inVO.setCreateTimeEnd(vo.getDateEnd());
			inVO.setApproveState(ApproveState.SPJS);
			Map<Long,List<InFeeProvision>> inFeeMap = inFeeProService.getInFeeMap(inVO);
			
			//合同外预提
			Map<Long,List<OutFeeProvisionVO>> outFeeMap = this.getOutFeeMap(vo);
			
			for(Custom custom : customList){
				//合同外预提
				if(Common.checkList(outFeeMap.get(custom.getId()))){
					for(OutFeeProvisionVO outFee : outFeeMap.get(custom.getId())){
						map = new HashMap<String,String>();
						installOutFeeMap(heads, map, outFee,type);
						listmap.add(map);
					}
				}
				//合同内费用预提明细
				if(Common.checkList(inFeeMap.get(custom.getId()))){
					for(InFeeProvision inFee : inFeeMap.get(custom.getId())){
						map = new HashMap<String,String>();
						installInFeeMap(heads, map, inFee,type);
						listmap.add(map);
					}
				}
			}
			
		}
		
		//核销明细表
		if(type.equals("CHARGE")){
			//合同外核销
			Map<Long,List<OutFeeVerification>> outFeeVerMap = null;
			if(type.equals("CHARGE")){
				OutFeeVerificationVO outVerVO = new OutFeeVerificationVO();
				if(vo.getCustom() != null)
					outVerVO.setCustom(vo.getCustom());
				outVerVO.setBeginTime(vo.getDateStart());
				outVerVO.setEndTime(vo.getDateEnd());
				outVerVO.setApproveState(ApproveState.SPJS);
				outFeeVerMap = outFeeVerService.getOutFeeVerMap(outVerVO);
			}
			
			//合同内核销
			Map<Long,List<InFeeVerification>> inFeeVerMap = null;
			if(type.equals("CHARGE")){
				InFeeVerificationVO inVerVO = new InFeeVerificationVO();
				if(vo.getCustom() != null)
					inVerVO.setCustom(vo.getCustom());
				inVerVO.setBeginTime(vo.getDateStart());
				inVerVO.setEndTime(vo.getDateEnd());
				inVerVO.setApproveState(ApproveState.SPJS);
				inFeeVerMap = inFeeVerService.getInFeeVerMap(inVerVO);
			}
			
			for(Custom custom : customList){
				//合同外核销明细
				if(Common.checkList(outFeeVerMap.get(custom.getId()))){
					for(OutFeeVerification outFeeVer : outFeeVerMap.get(custom.getId())){
						map = new HashMap<String,String>();
						installOutFeeVerMap(heads, map, outFeeVer);
						listmap.add(map);
					}
				}
				
				//合同内费用核销明细
				if(Common.checkList(inFeeVerMap.get(custom.getId()))){
					for(InFeeVerification inFeeVer : inFeeVerMap.get(custom.getId())){
						map = new HashMap<String,String>();
						installInFeeVerMap(heads, map, inFeeVer);
						listmap.add(map);
					}
				}
			}
		}
		
	}
	
	/**
	 * 组装合同外预提Map
	 * @param heads:表头信息
	 * @param map 
	 * @param outFee ：合同外预提
	 * @throws Exception
	 */
	public void installOutFeeMap(String[] heads , Map<String,String> map,OutFeeProvisionVO outFee,String type) throws Exception{
		String[] headers = Common.getExportHeader("ANALYSISBUD");
		/*if(type.equals("BUDGET"))
			headers = Common.getExportHeader("ANALYSISBUD");*/
		for(String key : heads){
			int i = 0;
			if(key.equals(headers[i++])){
				//所属大区
				map.put(key, outFee.getCustom().getArea().getParentArea().getAreaName());
			}else if(key.equals(headers[i++])){
				//所属地区
				map.put(key, outFee.getCustom().getArea().getAreaName());
			}else if(key.equals(headers[i++])){
				//客户名称
				map.put(key, outFee.getCustom().getCustomName());
			}else if(key.equals(headers[i++])){
				//客户编码
				map.put(key, outFee.getCustom().getCustomCde());
			}else if(key.equals(headers[i++])){
				//年月
				map.put(key, DateFormat.date2Str(outFee.getProvisionTime(), 2));
			}else if(key.equals(headers[i++])){
				//合同外：预提编号
				map.put(key, outFee.getProvisionCode());
			}else if(key.equals(headers[i++])){
				//合同外：项目
				map.put(key, outFee.getProjectName());
			}else if(key.equals(headers[i++])){
				//合同外：总费用
				map.put(key, outFee.getTotalFee().toString());
			}else if(key.equals(headers[i++])){
				//合同外：开始时间
				map.put(key, DateFormat.date2Str(outFee.getStartTime(), 2));
			}else if(key.equals(headers[i++])){
				//合同外：结束时间 
				map.put(key, DateFormat.date2Str(outFee.getEndTime(), 2));
			}else if(key.equals(headers[i++])){
				//合同外：时间费用
				map.put(key, outFee.getSjFee().toString());
			}else{
				//合同内
				map.put(key, "");
			}
			
		}
	}
	
	
	/**
	 * 组装合同外核销
	 * @param heads:表头信息
	 * @param map 
	 * @param outFee ：合同外核销
	 * @throws Exception
	 */
	public void installOutFeeVerMap(String[] heads , Map<String,String> map,OutFeeVerification outFee) throws Exception{
		String[] headers = Common.getExportHeader("ANALYSISCHAR");
		for(String key : heads){
			int i = 0;
			if(key.equals(headers[i++])){
				//所属大区
				map.put(key, outFee.getCustom().getArea().getParentArea().getAreaName());
			}else if(key.equals(headers[i++])){
				//所属地区
				map.put(key, outFee.getCustom().getArea().getAreaName());
			}else if(key.equals(headers[i++])){
				//客户名称
				map.put(key, outFee.getCustom().getCustomName());
			}else if(key.equals(headers[i++])){
				//客户编码
				map.put(key, outFee.getCustom().getCustomCde());
			}else if(key.equals(headers[i++])){
				//年月
				map.put(key, DateFormat.date2Str(outFee.getVerTime(), 2));
			}else if(key.equals(headers[i++])){
				//类型
				map.put(key, "核销");
			}else if(key.equals(headers[i++])){
				//合同外：编号
				map.put(key, outFee.getVerCode());
			}else if(key.equals(headers[i++])){
				//合同外：项目
				map.put(key, outFee.getOutFeeProvision().getProvisionProject().getFeeName());
			}else if(key.equals(headers[i++])){
				//合同外：费用
				map.put(key, outFee.getOutFeeProvision().getTotalFee().toString());
			}else{
				//合同内
				map.put(key, "");
			}
			
		}
	}
	
	/**
	 * 组装合同内费用预提
	 * @param heads
	 * @param map
	 * @param inFee
	 * @throws Exception
	 */
	public void installInFeeMap(String[] heads , Map<String,String> map,InFeeProvision inFee,String type) throws Exception{
		String[] headers = Common.getExportHeader("ANALYSISBUD");
		/*if(type.equals("BUDGET"))
			headers = Common.getExportHeader("ANALYSISBUD");*/
		for(String key : heads){
			int i = 0;
			if(key.equals(headers[i++])){
				//所属大区
				map.put(key, inFee.getCustom().getArea().getParentArea().getAreaName());
			}else if(key.equals(headers[i++])){
				//所属地区
				map.put(key, inFee.getCustom().getArea().getAreaName());
			}else if(key.equals(headers[i++])){
				//客户名称
				map.put(key, inFee.getCustom().getCustomName());
			}else if(key.equals(headers[i++])){
				//客户编码
				map.put(key, inFee.getCustom().getCustomCde());
			}else if(key.equals(headers[i++])){
				//年月
				map.put(key, DateFormat.date2Str(inFee.getProvisionTime(), 10));
			}else if(key.equals(headers[i++])){
				map.put(key, "");//合同外
			}else if(key.equals(headers[i++])){
				map.put(key, "");//合同外
			}else if(key.equals(headers[i++])){
				map.put(key, "");//合同外
			}else if(key.equals(headers[i++])){
				map.put(key, "");//合同外
			}else if(key.equals(headers[i++])){
				map.put(key, "");//合同外
			}else if(key.equals(headers[i++])){
				map.put(key, "");//合同外
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getCode());//合同内：预提编号
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getEnterFee().toString());//合同内：进场费
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getFixedFee().toString());//合同内：固定费用
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getYearReturnFee().toString());//合同内：年返利
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getMonthReturnFee().toString());//合同内：月返利
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getNetInfoFee().toString());//合同内：网络信息费
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getDeliveryFee().toString());//合同内：配送服务费
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getPosterFee().toString());//合同内：海报费
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getPromotionFee().toString());//合同内：促销陈列费
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getSponsorFee().toString());//合同内：赞助费
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getLossFee().toString());//合同内：损耗费
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getFixedDiscount().toString());//合同内：固定折扣
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getPilesoilFee().toString());//合同内：堆头费
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getMarketFee().toString());//合同内：市场费
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getCaseReturnFee().toString());//合同内：现场现金返利
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getOtherFee().toString());//合同内：其他费用
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getInFeeCount().toString());//合同内：费用总和
			}
			
			
		}
	}
	
	/**
	 * 组装合同内费用核销
	 * @param heads
	 * @param map
	 * @param inFee
	 * @throws Exception
	 */
	public void installInFeeVerMap(String[] heads , Map<String,String> map,InFeeVerification inFee) throws Exception{
		String[] headers = Common.getExportHeader("ANALYSISCHAR");
		for(String key : heads){
			int i = 0;
			if(key.equals(headers[i++])){
				//所属大区
				map.put(key, inFee.getCustom().getArea().getParentArea().getAreaName());
			}else if(key.equals(headers[i++])){
				//所属地区
				map.put(key, inFee.getCustom().getArea().getAreaName());
			}else if(key.equals(headers[i++])){
				//客户名称
				map.put(key, inFee.getCustom().getCustomName());
			}else if(key.equals(headers[i++])){
				//客户编码
				map.put(key, inFee.getCustom().getCustomCde());
			}else if(key.equals(headers[i++])){
				//年月
				map.put(key, DateFormat.date2Str(inFee.getVerDate(), 10));
			}else if(key.equals(headers[i++])){
				//类型
				map.put(key, "核销");
			}else if(key.equals(headers[i++])){
				map.put(key, "");//合同外
			}else if(key.equals(headers[i++])){
				map.put(key, "");//合同外
			}else if(key.equals(headers[i++])){
				map.put(key, "");//合同外
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getVerCode());//合同内：编号
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getEnterFee().toString());//合同内：进场费
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getFixedFee().toString());//合同内：固定费用
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getYearReturnFee().toString());//合同内：年返利
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getMonthReturnFee().toString());//合同内：月返利
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getNetInfoFee().toString());//合同内：网络信息费
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getDeliveryFee().toString());//合同内：配送服务费
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getPosterFee().toString());//合同内：海报费
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getPromotionFee().toString());//合同内：促销陈列费
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getSponsorFee().toString());//合同内：赞助费
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getLossFee().toString());//合同内：损耗费
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getFixedDiscount().toString());//合同内：固定折扣
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getPilesoilFee().toString());//合同内：堆头费
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getMarketFee().toString());//合同内：市场费
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getCaseReturnFee().toString());//合同内：现场现金返利
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getOtherFee().toString());//合同内：其他费用
			}else if(key.equals(headers[i++])){
				map.put(key, inFee.getTotalFee().toString());//合同内：费用总和
			}
			
			
		}
	}
	
	/**
	 * 组装表头信息，若存在双标题，格式为：合同外:项目,费用
	 * @param heads
	 * @param headlist
	 * @throws Exception
	 */
	public void installHeader(String[] heads , List<String> headlist, String type) throws Exception{
		String[] headers = Common.getExportHeader("ANALYSISBUD");
		StringBuffer outSb = new StringBuffer("合同外:");
		StringBuffer inSb = new StringBuffer("合同内:");
		headlist.add("序号");
		for(String key : heads){
			int i = 0;
			if(key.equals(headers[i++])){
				headlist.add(key);
			}else if(key.equals(headers[i++])){
				headlist.add(key);
			}else if(key.equals(headers[i++])){
				headlist.add(key);
			}else if(key.equals(headers[i++])){
				headlist.add(key);
			}else if(key.equals(headers[i++])){
				headlist.add(key);
			}else if(key.equals(headers[i++])){
				outSb.append(key + ",");
			}else if(key.equals(headers[i++])){
				outSb.append(key+ ",");
			}else if(key.equals(headers[i++])){
				outSb.append(key+ ",");
			}else if(key.equals(headers[i++])){
				inSb.append(key + ",");
			}else if(key.equals(headers[i++])){
				inSb.append(key + ",");
			}else if(key.equals(headers[i++])){
				inSb.append(key + ",");
			}else if(key.equals(headers[i++])){
				inSb.append(key + ",");
			}else if(key.equals(headers[i++])){
				inSb.append(key + ",");
			}else if(key.equals(headers[i++])){
				inSb.append(key + ",");
			}else if(key.equals(headers[i++])){
				inSb.append(key + ",");
			}else if(key.equals(headers[i++])){
				inSb.append(key + ",");
			}else if(key.equals(headers[i++])){
				inSb.append(key + ",");
			}else if(key.equals(headers[i++])){
				inSb.append(key + ",");
			}else if(key.equals(headers[i++])){
				inSb.append(key + ",");
			}else if(key.equals(headers[i++])){
				inSb.append(key + ",");
			}else if(key.equals(headers[i++])){
				inSb.append(key + ",");
			}else if(key.equals(headers[i++])){
				inSb.append(key+ ",");
			}else if(key.equals(headers[i++])){
				inSb.append(key + ",");
			}else if(key.equals(headers[i++])){
				inSb.append(key + ",");
			}else if(key.equals(headers[i++])){
				inSb.append(key + ",");
			}
		}
		if(type.equals("CHARGE")){
			headlist.add("类型");
		}
		if(outSb.toString().indexOf(",") != -1){
			headlist.add(outSb.toString());
		}
		if(inSb.toString().indexOf(",") != -1){
			headlist.add(inSb.toString());
		}
	}

	@Override
	public void exportSum(String[] heads, Object[] obj, Map<String, String> map) throws Exception {
		
		String[] headers = Common.getExportHeader("ANALYSISSUM");
		
		Double d4 = obj[4] != null ? Double.parseDouble(obj[4].toString()) : 0d;
		Double d5 = obj[5] != null ? Double.parseDouble(obj[5].toString()) : 0d;
		Double d6 = obj[6] != null ? Double.parseDouble(obj[6].toString()) : 0d;
		Double d7 = obj[7] != null ? Double.parseDouble(obj[7].toString()) : 0d;
		Double d8 = obj[8] != null ? Double.parseDouble(obj[8].toString()) : 0d;
		Double d9 = obj[9] != null ? Double.parseDouble(obj[9].toString()) : 0d;
		Double d10 = obj[10] != null ? Double.parseDouble(obj[10].toString()) : 0d;
		
		for(String key : heads){
			int i =0;
			if(key.equals(headers[i++])){
				map.put(key, obj[0].toString());//所属大区
			}else if(key.equals(headers[i++])){
				map.put(key, obj[1].toString());//所属地区
			}else if(key.equals(headers[i++])){
				map.put(key, obj[2].toString());//客户名称
			}else if(key.equals(headers[i++])){
				map.put(key, obj[3].toString());//客户编码
			}else if(key.equals(headers[i++])){
				//总预提
				Double result1 = (d4 - d5) + (d7 - d8);
				map.put(key, PublicType.setDoubleScale(result1).toString());
			}else if(key.equals(headers[i++])){
				//总核销
				Double result2 = d6 + d9;
				map.put(key, PublicType.setDoubleScale(result2).toString());
			}else if(key.equals(headers[i++])){
				//总余额
				Double result3 = (d4 - d5 - d6) + (d7 - d8 -d9);
				map.put(key, PublicType.setDoubleScale(result3).toString());
			}else if(key.equals(headers[i++])){
				//发票额
				map.put(key, PublicType.setDoubleScale(d10).toString());
			}else if(key.equals(headers[i++])){
				//投入产出比
				map.put(key, (d6 + d9 > 0 && d10 > 0) ?  PublicType.setDoubleScale((d6 + d9)/d10*100).toString() + "%" : "0%");
			}else if(key.equals(headers[i++])){
				//合同内预提
				Double result4 = d4 - d5;
				map.put(key, PublicType.setDoubleScale(result4).toString());
			}else if(key.equals(headers[i++])){
				//合同内核销
				map.put(key, PublicType.setDoubleScale(d6).toString());
			}else if(key.equals(headers[i++])){
				//合同内余额
				Double result5 = d4 - d5 - d6;
				map.put(key, PublicType.setDoubleScale(result5).toString());
			}else if(key.equals(headers[i++])){
				//合同外预提
				Double result6 = d7 - d8;
				map.put(key, PublicType.setDoubleScale(result6).toString());
			}else if(key.equals(headers[i++])){
				//合同外实际预提
				map.put(key, PublicType.setDoubleScale(obj[12] != null ? Double.parseDouble(obj[12].toString()) : 0d).toString());
			}else if(key.equals(headers[i++])){
				//合同外核销
				map.put(key, PublicType.setDoubleScale(d9).toString());
			}else if(key.equals(headers[i++])){
				//合同外余额
				Double result7 = d7 - d8 - d9;
				map.put(key, PublicType.setDoubleScale(result7).toString());
			}
		}
	}

	@Override
	public void exportProBudget(String[] heads, Object[] obj,Map<String, String> map) throws Exception {
		String[] headers = Common.getExportHeader("PRODUCTBUDGET");
		for(String key : heads){
			int i = 0;
			
			if(key.equals(headers[i++])){
				map.put(key, obj[0].toString());//所属大区
			}else if(key.equals(headers[i++])){
				map.put(key, obj[1].toString());//所属地区
			}else if(key.equals(headers[i++])){
				map.put(key, obj[2].toString());//客户名称
			}else if(key.equals(headers[i++])){
				map.put(key, obj[12].toString());//门店名称
			}else if(key.equals(headers[i++])){
				map.put(key, DateFormat.date2Str(DateFormat.str2Date(obj[4].toString(), 2), 2));//年月日
			}else if(key.equals(headers[i++])){
				map.put(key, obj[11].toString());//预提编码
			}else if(key.equals(headers[i++])){
				//费用名称
				if(obj[5] != null){
					map.put(key, obj[5].toString());
				}else{
					map.put(key, "");
				}
			}else if(key.equals(headers[i++])){
				//产品名称
				if(obj[6] != null){
					map.put(key, obj[6].toString());
				}else{
					map.put(key, "");
				}
			}else if(key.equals(headers[i++])){
				//存货编码
				if(obj[7] != null){
					map.put(key, obj[7].toString());
				}else{
					map.put(key, "");
				}
			}else if(key.equals(headers[i++])){
				//产品规格
				if(obj[8] != null){
					map.put(key, obj[8].toString());
				}else{
					map.put(key, "");
				}
			}
			else if(key.equals(headers[i++])){
				//预提费用
				if(obj[9] != null){
					map.put(key, obj[9].toString());
				}else{
					map.put(key, "");
				}
			}
		}
	}

	@Override
	public void exportProCharge(String[] heads,List<Map<String, String>> listmap, Map<String, String> map,OutFeeProvisionVO vo) throws Exception {
		//表头信息
		String[] header = Common.getExportHeader("PRODUCTCHARGE");
		List<Object[]> verList = new ArrayList<Object[]>();
		//核销产品明细
		verList = getProDetailList(vo);
		if(Common.checkList(verList)){
			for(Object[] obj : verList){
				map = new HashMap<String, String>();
				for(String key : heads){
					int i = 0;
					if(key.equals(header[i++])){
						map.put(key, obj[0].toString());//所属大区
					}else if(key.equals(header[i++])){
						map.put(key, obj[1].toString());//所属地区
					}else if(key.equals(header[i++])){
						map.put(key, obj[2].toString());//客户名称
					}else if(key.equals(header[i++])){
						map.put(key, obj[12].toString());//门店名称
					}else if(key.equals(header[i++])){
						Date time = DateFormat.str2Date(obj[4].toString(), 2);
						map.put(key, DateFormat.date2Str(time, 2));//年月
					}else if(key.equals(header[i++])){
						map.put(key, "核销");//类型
					}else if(key.equals(header[i++])){
						map.put(key, obj[11].toString());//编号
					}else if(key.equals(header[i++])){
						map.put(key, obj[5].toString());//费用名称
					}else if(key.equals(header[i++])){
						map.put(key, obj[6].toString());//产品名称
					}else if(key.equals(header[i++])){
						map.put(key, obj[7].toString());//存货编码
					}else if(key.equals(header[i++])){
						map.put(key, obj[8].toString());//产品规格
					}else if(key.equals(header[i++])){
						map.put(key, obj[9].toString());//费用
					}
				}
				listmap.add(map);
			}
		}
	}

	@Override
	public void exportProSum(String[] heads, Object[] obj,Map<String, String> map) throws Exception {
		String[] headers = Common.getExportHeader("PRODUCTSUM");
		
		Double d7 = obj[7] != null ? Double.parseDouble(obj[7].toString()) : 0d;
		Double d8 = obj[8] != null ? Double.parseDouble(obj[8].toString()) : 0d;
		Double d9 = obj[9] != null ? Double.parseDouble(obj[9].toString()) : 0d;
		
		for(String key : heads){
			int i =0;
			if(key.equals(headers[i++])){
				if(obj[0] != null){
					map.put(key, obj[0].toString());//所属大区
				}else{
					map.put(key, "");
				}
			}else if(key.equals(headers[i++])){
				if(obj[1] != null){
					map.put(key, obj[1].toString());//所属地区
				}else{
					map.put(key, "");
				}
			}else if(key.equals(headers[i++])){
				if(obj[2] != null){
					map.put(key, obj[2].toString());//客户名称
				}else{
					map.put(key, "");
				}
			}else if(key.equals(headers[i++])){
				if(obj[10] != null){
					map.put(key, obj[10].toString());//门店名称
				}else{
					map.put(key, "");
				}
			}else if(key.equals(headers[i++])){
				if(obj[4] != null){
					map.put(key, obj[4].toString());//产品名称
				}else{
					map.put(key, "");
				}
			}else if(key.equals(headers[i++])){
				if(obj[5] != null){
					map.put(key, obj[5].toString());//存货编码
				}else{
					map.put(key, "");
				}
			}else if(key.equals(headers[i++])){
				if(obj[6] != null){
					map.put(key, obj[6].toString());//产品规格
				}else{
					map.put(key, "");
				}
			}else if(key.equals(headers[i++])){
				//预提费用
				Double result = d7 - d8;
				map.put(key, PublicType.setDoubleScale(result).toString());
			}else if(key.equals(headers[i++])){
				//核销费用
				map.put(key, d9.toString());
			}else if(key.equals(headers[i++])){
				//余额
				Double result = d7 - d8 - d9;
				map.put(key, PublicType.setDoubleScale(result).toString());
			}
		}
	}

	@Override
	public List<OutFeeProvision> outFeeProvision(OutFeeProvisionVO vo) {
		try {
			String hql="select o from OutFeeProvision o where 1=1 ";
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
	public void exportOutFeeProvision(String[] heads, OutFeeProvision ofp, Map<String, String> map) {
		String[] header=Common.getExportHeader("OUTFEEPROVISION");
		
		for(String key:heads){
			int i=0;
			if(key.equals(header[i++])){
				map.put(key, ofp.getProvisionCode());
			}else if(key.equals(header[i++])){
				if(ofp.getCustom()!=null){
					map.put(key, ofp.getCustom().getCustomName());
				}
			}else if(key.equals(header[i++])){
				map.put(key, ofp.getSalesman());
			}else if(key.equals(header[i++])){
				if(ofp.getProvisionProject()!=null){
					map.put(key, ofp.getProvisionProject().getFeeName());
				}
			}else if(key.equals(header[i++])){
				if(ofp.getProvisionProject()!=null&&ofp.getProvisionProject().getProjectType()!=null){
					map.put(key, ofp.getProvisionProject().getProjectType().getText());
				}
			}else if(key.equals(header[i++])){
				if(ofp.getTotalFee()!=null){
					map.put(key, ofp.getTotalFee().toString());
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				if(ofp.getProvisionTime()!=null){
					map.put(key, DateFormat.date2Str(ofp.getProvisionTime(), 2));
				}
			}else if(key.equals(header[i++])){
				if(ofp.getStartTime()!=null){
					map.put(key, DateFormat.date2Str(ofp.getStartTime(), 2));
				}
			}else if(key.equals(header[i++])){
				if(ofp.getEndTime()!=null){
					map.put(key, DateFormat.date2Str(ofp.getEndTime(), 2));
				}
			}else if(key.equals(header[i++])){
				if(ofp.getApproveState()!=null){
					map.put(key, ofp.getApproveState().getText());
				}
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized Integer createSalePro(OutFeeProvisionVO vo, ProvisionProject pp)
			throws Exception {
		Integer count = 0;
		
		String sql = "SELECT s.custom_id,cast(SUM(b.count) as decimal(10,2)) FROM bud_store_sale_provision b "
				+ "LEFT JOIN info_store s ON b.storeId = s.id where 1=1 ";
		if(vo != null && StringUtils.isNotBlank(vo.getDateStart()))
			sql += " and b.provisionTime>='" + vo.getDateStart() + "'";
		if(vo != null && StringUtils.isNotBlank(vo.getDateEnd()))
			sql += " and b.provisionTime < '" + DateFormat.getMonthFirst(DateFormat.str2Date(vo.getDateEnd(), 10), 1) + "'";
		
		sql += " group by s.custom_id ";
		
		Query query = em.createNativeQuery(sql);
		List<Object[]> resultList = (List<Object[]>)query.getResultList();
		if(Common.checkList(resultList)){
			for(Object[] o : resultList){
				if(o[0] != null && o[1] != null){
					Custom custom =  customSevice.getById(Long.parseLong(o[0].toString()));
					if(custom != null){
						OutFeeProvision ofp = new OutFeeProvision();
						ofp.setCustom(custom);
						ofp.setProvisionProject(pp);
						ofp.setApproveState(ApproveState.DSP);
						ofp.setProvisionTime(DateFormat.str2Date(DateFormat.date2Str(new Date(), 2), 2));
						ofp.setStartTime(DateFormat.str2Date(vo.getDateStart(), 2));
						ofp.setEndTime(DateFormat.str2Date(vo.getDateEnd(), 2));
						ofp.setProvisionCode(getCode());
						ofp.setTotalFee(Double.parseDouble(o[1].toString()));
						ofp.setCreateTime(new Date());
						ofp.setUpdateTime(ofp.getCreateTime());
						if(custom.getUser() != null)
							ofp.setSalesman(custom.getUser().getUserName());
						
						em.merge(ofp);
						count ++;
					}
				}
			}
		}
		return count;
	}

	@Override
	public Integer deleteSalePro(OutFeeProvisionVO vo, ProvisionProject pp)
			throws Exception {
		String sql = "delete from bud_out_fee_provision where project_id =" + pp.getId() ;
		if(vo != null && StringUtils.isNotBlank(vo.getDateStart()))
			sql += " and startTime>='" + vo.getDateStart() + "'";
		if(vo != null && StringUtils.isNotBlank(vo.getDateEnd()))
			sql += " and endTime <= '" + vo.getDateEnd() + "'";

		Query query = em.createNativeQuery(sql);
		return query.executeUpdate();
	}

	@Override
	public Map<Long, List<Object[]>> getProMap(OutFeeProvisionVO vo, String type)
			throws Exception {
		Map<Long, List<Object[]>>  map = new HashMap<Long, List<Object[]>>();
		Long customId = 0L;
		List<Object[]> list = new ArrayList<Object[]>();
		List<Object[]> resultList = new ArrayList<Object[]>();
		
		if(StringUtils.equals(type, "1"))
			resultList = getInFeeList(vo);
		else{
			for(Object[] o : getOutFeeList(vo)){
				Object[] oo = new Object[10];
				for(int i=0;i<o.length;i++)
					oo[i] = o[i];
				
				if(o[3] != null && o[4] != null && o[5] != null
						&& StringUtils.isNotBlank(vo.getDateStart()) && StringUtils.isNotBlank(vo.getDateEnd())){
					OutFeeProvisionVO outVO = new OutFeeProvisionVO();
					outVO.setStartTime(DateFormat.str2Date(o[3].toString(), 2));
					outVO.setEndTime(DateFormat.str2Date(o[4].toString(), 2));
					oo[9] = getSjFee(Double.parseDouble(o[5].toString()),outVO, DateFormat.str2Date(vo.getDateStart(), 2), DateFormat.str2Date(vo.getDateEnd(), 2));
				}else
					oo[9] = 0d;
				
				resultList.add(oo);
			}
		}
			
		
		for(Object[] o : resultList){
			if(!customId.equals(Long.parseLong(o[0].toString())) && !customId.equals(0L)){
				map.put(customId, list);
				list = new ArrayList<Object[]>();
			}
			list.add(o);
			customId = Long.parseLong(o[0].toString());
		}
		map.put(customId, list);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	private List<Object[]> getInFeeList(OutFeeProvisionVO vo){
		String sql = "SELECT b.custom_id, b.code,b.provisionTime,b.sum,temp.inVerR,temp1.inVerB FROM bud_in_fee_provision b "
				+ " LEFT JOIN info_custom c ON b.custom_id = c.id "
				+ " LEFT JOIN info_area a ON c.areaId = a.id LEFT JOIN ( SELECT id , areaName parentArea FROM info_area WHERE parentId IS NULL ) a1 ON a.parentId = a1.id "
				+ " LEFT JOIN ( SELECT provision_id,SUM(totalFee) inVerR FROM charge_in_fee_verification "
				+ " WHERE approveState = 'SPJS' AND verDirection = 'HCYT' GROUP BY provision_id "
				+ " )temp ON b.id = temp.provision_id "
				+ " LEFT JOIN ( SELECT provision_id,SUM(totalFee) inVerB FROM charge_in_fee_verification "
				+ " WHERE approveState = 'SPJS' AND verDirection = 'LCYT' GROUP BY provision_id "
				+ " )temp1 ON b.id = temp1.provision_id "
				+ " WHERE b.approveState = 'SPJS' ";
		
		if(StringUtils.isNotBlank(vo.getDateStart()))
			sql += " and provisionTime >= '" + vo.getDateStart() + "'";
		if(StringUtils.isNotBlank(vo.getDateEnd()))
			sql += " and provisionTime <= '" + vo.getDateEnd() + "'";
		if(vo.getCustom() != null){
			if(vo.getCustom().getArea() != null){
				if(StringUtils.isNotBlank(vo.getCustom().getCustomName()))
					sql += " and c.customName like '%" + vo.getCustom().getCustomName() + "%' ";
				if(StringUtils.isNotBlank(vo.getCustom().getCustomCde()))
					sql += " and c.customCde = '" + vo.getCustom().getCustomCde() + "' ";
				if(vo.getCustom().getArea() != null){
					if(Common.checkLong(vo.getCustom().getArea().getId()))
						sql += " and a.id = " + vo.getCustom().getArea().getId();
					else if(vo.getCustom().getArea().getParentArea() != null 
							&& Common.checkLong(vo.getCustom().getArea().getParentArea().getId()))
						sql += " and a1.id = " + vo.getCustom().getArea().getParentArea().getId();
				}
			}
		}
		
		if(vo.getUserId() != null)
			sql += " and c.userId =" + vo.getUserId();
		if(Common.checkList(vo.getAreaIds()))
			sql += " and a.id in (" + vo.getAreaIds().toString().replace("[", "").replace("]", "") + ")";
		if(Common.checkList(vo.getParentAreaIds()))
			sql += " and a1.id in ("+ vo.getParentAreaIds().toString().replace("[", "").replace("]", "") +")";

		
		Query query = em.createNativeQuery(sql);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	private List<Object[]> getOutFeeList(OutFeeProvisionVO vo){
		String sql = "SELECT b.custom_id, b.provisionCode,b.totalFee,b.startTime,b.endTime, TRUNCATE(b.totalFee/( MONTH(b.endTime)-MONTH(b.startTime) +1) ,2),temp.inVerR,temp1.inVerB,b.provisionTime FROM bud_out_fee_provision b "
				+ " LEFT JOIN info_custom c ON b.custom_id = c.id "
				+ " LEFT JOIN info_area a ON c.areaId = a.id LEFT JOIN ( SELECT id , areaName parentArea FROM info_area WHERE parentId IS NULL ) a1 ON a.parentId = a1.id "
				+ " LEFT JOIN ( SELECT provision_id,SUM(totalFee) inVerR FROM charge_out_fee_verification "
				+ " WHERE approveState = 'SPJS' AND verDirection = 'HCYT' GROUP BY provision_id "
				+ " )temp ON b.id = temp.provision_id "
				+ " LEFT JOIN ( SELECT provision_id,SUM(totalFee) inVerB FROM charge_out_fee_verification "
				+ " WHERE approveState = 'SPJS' AND verDirection = 'LCYT' GROUP BY provision_id "
				+ " )temp1 ON b.id = temp1.provision_id "
				+ " WHERE b.approveState = 'SPJS' ";
		
		String where = "  AND ((b.startTime <= ':startTime' AND b.endTime <= ':endTime' AND b.endTime >=':startTime') "
				+ " OR (b.startTime <= ':startTime' AND b.endTime >= ':endTime') "
				+ " OR (b.startTime >= ':startTime' AND b.startTime <= ':endTime' AND b.endTime >= ':endTime') "
				+ " OR (b.startTime >= ':startTime' AND b.endTime <= ':endTime')) ";
		
		if(StringUtils.isNotBlank(vo.getDateStart()) && StringUtils.isNotBlank(vo.getDateEnd()))
			sql += where.replace(":startTime", vo.getDateStart()).replace(":endTime", vo.getDateEnd());
		
		if(vo.getCustom() != null){
			if(vo.getCustom().getArea() != null){
				if(StringUtils.isNotBlank(vo.getCustom().getCustomName()))
					sql += " and c.customName like '%" + vo.getCustom().getCustomName() + "%' ";
				if(StringUtils.isNotBlank(vo.getCustom().getCustomCde()))
					sql += " and c.customCde = '" + vo.getCustom().getCustomCde() + "' ";
				if(vo.getCustom().getArea() != null){
					if(Common.checkLong(vo.getCustom().getArea().getId()))
						sql += " and a.id = " + vo.getCustom().getArea().getId();
					else if(vo.getCustom().getArea().getParentArea() != null 
							&& Common.checkLong(vo.getCustom().getArea().getParentArea().getId()))
						sql += " and a1.id = " + vo.getCustom().getArea().getParentArea().getId();
				}
			}
		}
		
		if(vo.getUserId() != null)
			sql += " and c.userId =" + vo.getUserId();
		if(Common.checkList(vo.getAreaIds()))
			sql += " and a.id in (" + vo.getAreaIds().toString().replace("[", "").replace("]", "") + ")";
		if(Common.checkList(vo.getParentAreaIds()))
			sql += " and a1.id in ("+ vo.getParentAreaIds().toString().replace("[", "").replace("]", "") +")";

		
		Query query = em.createNativeQuery(sql);
		return query.getResultList();
	}

	@Override
	public void exportDiffer(String[] heads,List<Custom> customList,Map<String,String> map ,
			List<Map<String,String>> listmap, OutFeeProvisionVO vo) throws Exception {
		//合同内预提
		Map<Long,List<Object[]>> inFeeMap = getProMap(vo,"1");
		//合同外预提
		Map<Long,List<Object[]>> outFeeMap = getProMap(vo,"2");
		
		String[] headers = Common.getExportHeader("ANALYSISDIFFER");
		
		for(Custom custom : customList){
			//合同外预提
			if(Common.checkList(outFeeMap.get(custom.getId()))){
				for(Object[] o : outFeeMap.get(custom.getId())){
					map = new HashMap<String,String>();
					
					Double d2 = o[2] != null ? Double.parseDouble(o[2].toString()) : 0d;
					Double d6 = o[6] != null ? Double.parseDouble(o[6].toString()) : 0d;
					Double d7 = o[7] != null ? Double.parseDouble(o[7].toString()) : 0d;
					
					for(String key : heads){
						int i = 0;
						if(key.equals(headers[i++])){
							//所属大区
							map.put(key, custom.getArea().getParentArea().getAreaName());
						}else if(key.equals(headers[i++])){
							//所属地区
							map.put(key, custom.getArea().getAreaName());
						}else if(key.equals(headers[i++])){
							//客户名称
							map.put(key, custom.getCustomName());
						}else if(key.equals(headers[i++])){
							//客户编码
							map.put(key, custom.getCustomCde());
						}else if(key.equals(headers[i++])){
							//合同外：预提编号
							map.put(key, o[1].toString());
						}else if(key.equals(headers[i++])){
							//合同外：制单时间
							if(o[9] != null)
								map.put(key, o[8].toString().split(" ")[0]);
						}else if(key.equals(headers[i++])){
							//合同外：总预提
							map.put(key,  PublicType.setDoubleScale(d2 - d6).toString());
						}else if(key.equals(headers[i++])){
							//合同外：实际预提
							map.put(key, o[9].toString());
						}else if(key.equals(headers[i++])){
							//合同外：开始时间
							if(o[3] != null)
								map.put(key, o[3].toString().split(" ")[0]);
						}else if(key.equals(headers[i++])){
							//合同外：结束时间 
							if(o[4] != null)
								map.put(key, o[4].toString().split(" ")[0]);
						}else if(key.equals(headers[i++])){
							//合同外：核销
							map.put(key, d7.toString());
						}else if(key.equals(headers[i++])){
							//合同外：余额
							map.put(key,  PublicType.setDoubleScale(d2 - d6 - d7).toString());
						}else{
							//合同内
							map.put(key, "");
						}
					}
					listmap.add(map);
				}
			}
			//合同内费用预提明细
			if(Common.checkList(inFeeMap.get(custom.getId()))){
				for(Object[] o  : inFeeMap.get(custom.getId())){
					map = new HashMap<String,String>();
					
					Double d3 = o[3] != null ? Double.parseDouble(o[3].toString()) : 0d;
					Double d4 = o[4] != null ? Double.parseDouble(o[4].toString()) : 0d;
					Double d5 = o[5] != null ? Double.parseDouble(o[5].toString()) : 0d;
					
					for(String key : heads){
						int i = 0;
						if(key.equals(headers[i++])){
							//所属大区
							map.put(key, custom.getArea().getParentArea().getAreaName());
						}else if(key.equals(headers[i++])){
							//所属地区
							map.put(key, custom.getArea().getAreaName());
						}else if(key.equals(headers[i++])){
							//客户名称
							map.put(key, custom.getCustomName());
						}else if(key.equals(headers[i++])){
							//客户编码
							map.put(key, custom.getCustomCde());
						}else if(key.equals(headers[i++])){
							//合同外：预提编号
							map.put(key, "");
						}else if(key.equals(headers[i++])){
							//合同外：制单时间
							map.put(key, "");
						}else if(key.equals(headers[i++])){
							//合同外：总预提
							map.put(key,  "");
						}else if(key.equals(headers[i++])){
							//合同外：实际预提
							map.put(key, "");
						}else if(key.equals(headers[i++])){
							//合同外：开始时间
							map.put(key, "");
						}else if(key.equals(headers[i++])){
							//合同外：结束时间 
							map.put(key, "");
						}else if(key.equals(headers[i++])){
							//合同外：核销
							map.put(key, "");
						}else if(key.equals(headers[i++])){
							//合同外：余额
							map.put(key,  "");
						}else if(key.equals(headers[i++])){
							//合同内：预提编号
							map.put(key, o[1].toString());
						}else if(key.equals(headers[i++])){
							//合同内：预提时间
							if(o[2] != null)
								map.put(key, o[2].toString().split(" ")[0]);
						}else if(key.equals(headers[i++])){
							//合同内：预提
							map.put(key,  PublicType.setDoubleScale(d3 - d4).toString());
						}else if(key.equals(headers[i++])){
							//合同内：核销
							map.put(key, d5.toString());
						}else if(key.equals(headers[i++])){
							//合同内：余额
							map.put(key,  PublicType.setDoubleScale(d3 - d4 - d5).toString());
						}
					}
					listmap.add(map);
				}
			}
			
		}
		
	}
}
