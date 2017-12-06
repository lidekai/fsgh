package com.kington.fshg.service.info;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.excel.vo.PromotersExcelVO;
import com.kington.fshg.model.info.Custom;
import com.kington.fshg.model.info.Promoters;
import com.kington.fshg.model.info.PromotersVO;
import com.kington.fshg.model.info.Store;
import com.kington.fshg.service.BaseServiceImpl;

public class PromotersServiceImpl extends BaseServiceImpl<Promoters, PromotersVO> implements
		PromotersService {
	private static final long serialVersionUID = -1975469893311069125L;

	@Resource
	private CustomService customService;
	@Resource
	private StoreService storeService;
	
	@Override
	protected String getQueryStr(PromotersVO vo) throws Exception {
		StringBuffer sb = new StringBuffer();
		if(StringUtils.isNotBlank(vo.getPromotersName()))
			sb.append(" and o.promotersName like :name ");
		if(StringUtils.isNotBlank(vo.getIDCare()))
			sb.append(" and o.IDCare like :idCare ");
		if(vo.getCustom() != null){
			if(vo.getCustom().getArea() != null){
				if(Common.checkLong(vo.getCustom().getArea().getId())){
					sb.append(" and o.custom.area.id = :areaId ");
				}else if(vo.getCustom().getArea().getParentArea() != null
						&& Common.checkLong(vo.getCustom().getArea().getParentArea().getId())){
					sb.append(" and o.custom.area.parentArea.id = :areaParentId ");
				}
			}
			
			if(vo.getCustom().getUser() != null && Common.checkLong(vo.getCustom().getUser().getId())){
				sb.append(" and o.custom.user.id = :userId ");
			}
			
			if(StringUtils.isNotBlank(vo.getCustom().getCustomName()))
				sb.append(" and o.custom.customName like :customName ");
		}
		
		if(Common.checkList(vo.getAreaIds()))
			sb.append(" and o.custom.area.id in (:areaIds)");
		if(Common.checkList(vo.getParentAreaIds()))
			sb.append(" and o.custom.area.parentArea.id in (:parentAreaIds)");
		return sb.toString();
	}

	@Override
	protected void setQueryParm(Query query, PromotersVO vo) throws Exception {
		if(StringUtils.isNotBlank(vo.getPromotersName()))
			query.setParameter("name", Common.SYMBOL_PERCENT + vo.getPromotersName() + 
					Common.SYMBOL_PERCENT);
		if(StringUtils.isNotBlank(vo.getIDCare()))
			query.setParameter("idCare", Common.SYMBOL_PERCENT+ vo.getIDCare() +Common.SYMBOL_PERCENT);
		
		if(vo.getCustom() != null){
			if(vo.getCustom().getArea() != null){
				if(Common.checkLong(vo.getCustom().getArea().getId())){
					query.setParameter("areaId", vo.getCustom().getArea().getId());
				}else if(vo.getCustom().getArea().getParentArea() != null
						&& Common.checkLong(vo.getCustom().getArea().getParentArea().getId())){
					query.setParameter("areaParentId", vo.getCustom().getArea().getParentArea().getId());
				}
			}
			
			if(vo.getCustom().getUser() != null && Common.checkLong(vo.getCustom().getUser().getId())){
				query.setParameter("userId", vo.getCustom().getUser().getId());
			}
			
			if(StringUtils.isNotBlank(vo.getCustom().getCustomName()))
				query.setParameter("customName", Common.SYMBOL_PERCENT+ vo.getCustom().getCustomName() +Common.SYMBOL_PERCENT);
		}
		if(Common.checkList(vo.getAreaIds()))
			query.setParameter("areaIds", vo.getAreaIds());
		if(Common.checkList(vo.getParentAreaIds()))
			query.setParameter("parentAreaIds", vo.getParentAreaIds());
	}

	@Override
	protected void switchVO2PO(PromotersVO vo, Promoters po) throws Exception {
		if(po == null)
			po = new Promoters();
		if(StringUtils.isNotBlank(vo.getPromotersName()))
			po.setPromotersName(vo.getPromotersName());
		if(StringUtils.isNotBlank(vo.getContactsInfo()))
			po.setContactsInfo(vo.getContactsInfo());
		if(StringUtils.isNotBlank(vo.getIDCare()))
			po.setIDCare(vo.getIDCare());
		if(StringUtils.isNotBlank(vo.getBankLocal()))
			po.setBankLocal(vo.getBankLocal());
		if(vo.getBankCare() != null)
			po.setBankCare(vo.getBankCare());
		if(vo.getStore() != null)
			po.setStore(vo.getStore());
		if(vo.getCustom() != null)
			po.setCustom(vo.getCustom());
		if(StringUtils.isNotBlank(vo.getRemark())){
			po.setRemark(vo.getRemark());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Promoters getByIDCare(String IdCare) throws Exception {
		String sql="from Promoters o where o.IDCare = :IDCare ";
		Query query = em.createQuery(sql);
		query.setParameter("IDCare", IdCare);
		List<Promoters> list = query.getResultList();
		if(list != null && list.size() >0)
			return list.get(0);
		return null;
	}

	@Override
	public String doImports(List<PromotersExcelVO> list) throws Exception {
		StringBuilder sb = new StringBuilder();
		int num =0, succ= 0, fail =0;
		String s = StringUtils.EMPTY;
		String d = StringUtils.EMPTY;
		
		for(PromotersExcelVO vo : list){
			if(vo.getCid() == null)
				continue;
			
			num++;
			d = "序号" + num + "数据失败";
			s = checkVO(vo);
			if(StringUtils.isNotBlank(s)){
				sb.append(d + s + "</br>");
				fail ++;
				continue;
			}
			
			//根据身份证号判断是否存在，如果存在更新数据，否则新增
			Promoters prom = null;
			if(StringUtils.isNotBlank(vo.getIDCare())){
				prom = this.getByIDCare(vo.getIDCare().trim());
			}
			if(prom == null){
				prom = new Promoters();
				prom.setIDCare(vo.getIDCare().trim());
			}
			prom.setPromotersName(vo.getPromotersName());
			prom.setCustom(customService.getByCde(vo.getCustomCde()));
			prom.setStore(storeService.getByCde(vo.getStoreCde()));
			prom.setBankCare(Long.parseLong(vo.getBankCare().trim()));
			prom.setBankLocal(vo.getBankLocal());
			prom.setContactsInfo(vo.getContactsInfo());
			prom.setRemark(vo.getRemark());
			
			this.merge(prom);
			succ++;
		}
		return "导入完成，共导入 " + num + " 条数据，其中成功：" + succ + " 条，失败：" + fail+ " 条<br/>" + sb.toString();
	}
	
	public String checkVO(PromotersExcelVO vo) throws Exception{
		if(vo == null)
			return "导入对象不能为空";
		if(StringUtils.isBlank(vo.getIDCare()))
			return "身份证号不能为空";
		if(StringUtils.isBlank(vo.getPromotersName()))
			return "促销员名称不能为空";
		if(StringUtils.isBlank(vo.getCustomCde()))
			return "所属客户编码不能为空";
		if(StringUtils.isBlank(vo.getStoreCde()))
			return "所属门店编码不能为空";
		
		if(StringUtils.isNotBlank(vo.getCustomCde())){
			Custom custom = customService.getByCde(vo.getCustomCde());
			if(custom == null)
				return "不存在该客户信息";
		}
		
		if(StringUtils.isNotBlank(vo.getStoreCde())){
			Store store = storeService.getByCde(vo.getStoreCde());
			if(store == null)
				return "不存在该门店信息";
		}
		
		return null;
	}



	@Override
	public void exportPromoters(String[] heads, Promoters p, Map<String, String> map) {
		String[] header=Common.getExportHeader("PROMOTERS");
		//促销员名称","身份证号","联系电话","所在门店","所属客户   银行卡号","开户地","备注"
		for(String key:heads){
			int i=0;
			if(key.equals(header[i++])){
				map.put(key, p.getPromotersName());
			}else if(key.equals(header[i++])){
				map.put(key, p.getIDCare());
			}else if(key.equals(header[i++])){
				map.put(key, p.getContactsInfo());
			}else if(key.equals(header[i++])){
				if(p.getStore()!=null){
					map.put(key, p.getStore().getStoreName());
				}
			}else if(key.equals(header[i++])){
				if(p.getCustom()!=null){
					map.put(key, p.getCustom().getCustomName());
				}
			}else if(key.equals(header[i++])){
				if(p.getBankCare()!=null){
					map.put(key, p.getBankCare().toString());
				}				
			}else if(key.equals(header[i++])){
				map.put(key, p.getBankLocal());
			}else if(key.equals(header[i++])){
				map.put(key, p.getRemark());
			}
			
		}
		
		
	}
	
	

}
