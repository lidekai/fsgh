package com.kington.fshg.service.info;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.excel.vo.StoreExcelVO;
import com.kington.fshg.model.info.Custom;
import com.kington.fshg.model.info.Store;
import com.kington.fshg.model.info.StoreVO;
import com.kington.fshg.service.BaseServiceImpl;

public class StoreServiceImpl extends BaseServiceImpl<Store, StoreVO> implements
		StoreService {
	private static final long serialVersionUID = 475592686457385266L;

	@Resource
	private CustomService customService;
	
	@Override
	protected String getQueryStr(StoreVO vo) throws Exception {
		StringBuffer sql = new StringBuffer();
		if(StringUtils.isNotBlank(vo.getStoreName()))
			sql.append(" and o.storeName like :name ");
		if(StringUtils.isNotBlank(vo.getStoreCde()))
			sql.append(" and o.storeCde like :storeCde ");
		if(StringUtils.isNotBlank(vo.getContacts()))
			sql.append(" and o.contacts like :contacts ");
		
		if(vo.getCustom() != null){
			if(vo.getCustom().getArea() != null ){
				if(Common.checkLong(vo.getCustom().getArea().getId())){
					sql.append(" and o.custom.area.id = :areaId ");
				}else if(vo.getCustom().getArea().getParentArea() != null 
						&& Common.checkLong(vo.getCustom().getArea().getParentArea().getId())){
					sql.append(" and o.custom.area.parentArea.id = :areaParendtId ");
				}
			}
			if(vo.getCustom().getUser() != null && Common.checkLong(vo.getCustom().getUser().getId()))
				sql.append(" and o.custom.user.id =:userId ");
			if(StringUtils.isNotBlank(vo.getCustom().getCustomName()))
				sql.append(" and o.custom.customName like :customName ");
		}
		
		if(Common.checkList(vo.getAreaIds()))
			sql.append(" and o.custom.area.id in (:areaIds)");
		if(Common.checkList(vo.getParentAreaIds()))
			sql.append(" and o.custom.area.parentArea.id in (:parentAreaIds)");
		return sql.toString();
	}

	@Override
	protected void setQueryParm(Query query, StoreVO vo) throws Exception {
		if(StringUtils.isNotBlank(vo.getStoreName()))
			query.setParameter("name", Common.SYMBOL_PERCENT + vo.getStoreName()
					+ Common.SYMBOL_PERCENT);
		if(StringUtils.isNotBlank(vo.getStoreCde()))
			query.setParameter("storeCde", Common.SYMBOL_PERCENT + vo.getStoreCde()
					+ Common.SYMBOL_PERCENT);
		if(StringUtils.isNotBlank(vo.getContacts()))
			query.setParameter("contacts", Common.SYMBOL_PERCENT + vo.getContacts()
					+ Common.SYMBOL_PERCENT);
		
		if(vo.getCustom() != null){
			if(vo.getCustom().getArea() != null){
				if(Common.checkLong(vo.getCustom().getArea().getId())){
					query.setParameter("areaId", vo.getCustom().getArea().getId());
				}else if(vo.getCustom().getArea().getParentArea() != null 
						&&  Common.checkLong(vo.getCustom().getArea().getParentArea().getId())){
					query.setParameter("areaParendtId", vo.getCustom().getArea().getParentArea().getId());
				}
			}
			if(vo.getCustom().getUser() != null && Common.checkLong(vo.getCustom().getUser().getId()))
				query.setParameter("userId", vo.getCustom().getUser().getId());
			
			if(StringUtils.isNotBlank(vo.getCustom().getCustomName()))
				query.setParameter("customName", Common.SYMBOL_PERCENT + vo.getCustom().getCustomName()
						+ Common.SYMBOL_PERCENT);
		}
		
		if(Common.checkList(vo.getAreaIds()))
			query.setParameter("areaIds", vo.getAreaIds());
		if(Common.checkList(vo.getParentAreaIds()))
			query.setParameter("parentAreaIds", vo.getParentAreaIds());
		
	}

	@Override
	protected void switchVO2PO(StoreVO vo, Store po) throws Exception {
		if(po == null)
			po = new Store();
		if(StringUtils.isNotBlank(vo.getStoreName()))
			po.setStoreName(vo.getStoreName());
		if(StringUtils.isNotBlank(vo.getStoreCde()))
			po.setStoreCde(vo.getStoreCde());
		if(StringUtils.isNotBlank(vo.getContacts()))
			po.setContacts(vo.getContacts());
		if(StringUtils.isNotBlank(vo.getContactsInfo()))
			po.setContactsInfo(vo.getContactsInfo());
		if(StringUtils.isNotBlank(vo.getCity()))
			po.setCity(vo.getCity());
		if(StringUtils.isNotBlank(vo.getAddress()))
			po.setAddress(vo.getAddress());
		if(vo.getCustom() != null)
			po.setCustom(vo.getCustom());
		if(StringUtils.isNotBlank(vo.getRemark())){
			po.setRemark(vo.getRemark());
		}
		if(vo.getPropertion() != null)
			po.setPropertion(vo.getPropertion());
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Store getByCde(String cde) throws Exception {
		String sql="from Store o where o.storeCde =:cde ";
		Query query =em.createQuery(sql);
		query.setParameter("cde", cde);
		List<Store> list = query.getResultList();
		if(list != null && list.size() >0 )
			return list.get(0);
		return null;
	}

	@Override
	public String doImports(List<StoreExcelVO> list) throws Exception {
		StringBuilder sb = new StringBuilder();
		int num =0, succ = 0,fail = 0;
		String s = StringUtils.EMPTY;
		String d = StringUtils.EMPTY;
		
		for(StoreExcelVO vo : list){
			if(vo.getCid() == null)
				continue;
			num++;
			d = "序号"+ num +"数据失败：";
			s = checkVO(vo);
			if(StringUtils.isNotBlank(s)){
				sb.append(d + s + "</br>");
				fail++;
				continue;
			}
			
			Store po = null;
			if(StringUtils.isNotBlank(vo.getStoreCde())){
				po = this.getByCde(vo.getStoreCde().trim());
			}
			if(po == null){
				po = new Store();
				po.setStoreCde(vo.getStoreCde().trim());
			}
			
			po.setStoreName(vo.getStoreName().trim());
			po.setCustom(customService.getByCde(vo.getCustomCde().trim()));
			po.setCity(vo.getCity());
			po.setContacts(vo.getContacts());
			po.setContactsInfo(vo.getContactsInfo());
			po.setAddress(vo.getAddress());
			po.setRemark(vo.getRemark());
			if(StringUtils.isNotBlank(vo.getPropertion()))
				po.setPropertion(Double.parseDouble(vo.getPropertion()));
			
			this.merge(po);
			succ++;
		}
		
		return "导入完成，共导入 " + num + " 条数据，其中成功：" + succ + " 条，失败：" + fail+ " 条<br/>" + sb.toString();
	}
	
	public String checkVO(StoreExcelVO vo) throws Exception{
		if(vo == null)
			return "导入对象不能为空";
		if(StringUtils.isBlank(vo.getStoreCde()))
			return "门店编码不能为空";
		if(StringUtils.isBlank(vo.getStoreName()))
			return "门店名称不能为空";
		if(StringUtils.isBlank(vo.getCustomCde())){
			return "所属客户编码不能为空";
		}else{
			Custom custom = customService.getByCde(vo.getCustomCde().trim());
			if(custom == null)
				return "不存在编码为 "+vo.getCustomCde() +" 的客户；";
		}
		
		return null;
	}

	@Override
	public boolean delete(Long id) throws Exception {
		if(id == null)
			return false;
		String sql ="delete from info_store where id = :id ";
		Query query = em.createNativeQuery(sql);
		query.setParameter("id", id);
		return query.executeUpdate() > 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getNumByCostomId(Long customId) throws Exception {
		String sql="from Store o where o.custom.id =:customId ";
		Query query = em.createQuery(sql);
		query.setParameter("customId", customId);
		List<Store> list = query.getResultList();
		return list.size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Store> getByCustomId(Long customId) throws Exception {
		String sql = "from Store o where o.custom.id =:customId ";
		Query query = em.createQuery(sql);
		query.setParameter("customId", customId);
		List<Store> list = query.getResultList();
		return list;
	}


	@Override
	public void exportStore(String[] heads, Store store, Map<String, String> map) {
		String[] header=Common.getExportHeader("STORE");
		//门店名称","门店编码","联系人","所在城市","销售占比","所属客户" "联系电话","地址","备注
		for(String key:heads){
			int i=0;
			if(key.equals(header[i++])){
				map.put(key, store.getStoreName());
			}else if(key.equals(header[i++])){
				map.put(key, store.getStoreCde());
			}else if(key.equals(header[i++])){
				map.put(key, store.getContacts());
			}else if(key.equals(header[i++])){
				map.put(key, store.getCity());
			}else if(key.equals(header[i++])){
				if(store.getPropertion() != null)
					map.put(key, store.getPropertion().toString());
			}else if(key.equals(header[i++])){
				if(store.getCustom()!=null){
					map.put(key, store.getCustom().getCustomName());
				}
			}else if(key.equals(header[i++])){
				map.put(key, store.getContactsInfo());
			}else if(key.equals(header[i++])){
				map.put(key, store.getAddress());
			}else if(key.equals(header[i++])){
				map.put(key, store.getRemark());
			}
			
		
			
		}
		
	}
	
	

}
