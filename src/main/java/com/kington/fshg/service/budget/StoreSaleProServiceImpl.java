package com.kington.fshg.service.budget;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.DateFormat;
import com.kington.fshg.common.PublicType;
import com.kington.fshg.common.excel.vo.StoreSaleProExcelVO;
import com.kington.fshg.model.budget.StoreSaleProvision;
import com.kington.fshg.model.budget.StoreSaleProvisionVO;
import com.kington.fshg.model.info.Store;
import com.kington.fshg.service.BaseServiceImpl;
import com.kington.fshg.service.info.StoreService;

public class StoreSaleProServiceImpl extends BaseServiceImpl<StoreSaleProvision, StoreSaleProvisionVO> implements
		StoreSaleProService {

	private static final long serialVersionUID = -8400352577217194064L;
	
	@Resource
	private StoreService storeService;

	@Override
	protected String getQueryStr(StoreSaleProvisionVO vo) throws Exception {
		StringBuffer sql = new StringBuffer();
		if(vo.getStore() != null){
			if(StringUtils.isNotBlank(vo.getStore().getStoreName()))
				sql.append(" and o.store.storeName like :name ");
			if(StringUtils.isNotBlank(vo.getStore().getStoreCde()))
				sql.append(" and o.store.storeCde like :storeCde ");
			
			if(vo.getStore().getCustom() != null){
				if(vo.getStore().getCustom().getArea() != null ){
					if(Common.checkLong(vo.getStore().getCustom().getArea().getId())){
						sql.append(" and o.store.custom.area.id = :areaId ");
					}else if(vo.getStore().getCustom().getArea().getParentArea() != null 
							&& Common.checkLong(vo.getStore().getCustom().getArea().getParentArea().getId())){
						sql.append(" and o.store.custom.area.parentArea.id = :areaParendtId ");
					}
				}
				if(vo.getStore().getCustom().getUser() != null && Common.checkLong(vo.getStore().getCustom().getUser().getId()))
					sql.append(" and o.store.custom.user.id =:userId ");
			}
		}
		
		if(Common.checkList(vo.getAreaIds()))
			sql.append(" and o.store.custom.area.id in (:areaIds)");
		if(Common.checkList(vo.getParentAreaIds()))
			sql.append(" and o.store.custom.area.parentArea.id in (:parentAreaIds)");
		
		if(StringUtils.isNotBlank(vo.getDateStart()))
			sql.append(" and o.provisionTime >= :dateStart ");
		if(StringUtils.isNotBlank(vo.getDateEnd()))
			sql.append(" and o.provisionTime < :dateEnd ");
		
		return sql.toString();
	}

	@Override
	protected void setQueryParm(Query query, StoreSaleProvisionVO vo)
			throws Exception {
		if(vo.getStore() != null){
			if(StringUtils.isNotBlank(vo.getStore().getStoreName()))
				query.setParameter("name", Common.SYMBOL_PERCENT + vo.getStore().getStoreName() + Common.SYMBOL_PERCENT);
			if(StringUtils.isNotBlank(vo.getStore().getStoreCde()))
				query.setParameter("storeCde", vo.getStore().getStoreCde());
			
			if(vo.getStore().getCustom() != null){
				if(vo.getStore().getCustom().getArea() != null ){
					if(Common.checkLong(vo.getStore().getCustom().getArea().getId())){
						query.setParameter("areaId", vo.getStore().getCustom().getArea().getId());
					}else if(vo.getStore().getCustom().getArea().getParentArea() != null 
							&& Common.checkLong(vo.getStore().getCustom().getArea().getParentArea().getId())){
						query.setParameter("areaParendtId", vo.getStore().getCustom().getArea().getParentArea().getId());
					}
				}
				if(vo.getStore().getCustom().getUser() != null && Common.checkLong(vo.getStore().getCustom().getUser().getId()))
					query.setParameter("userId", vo.getStore().getCustom().getUser().getId());
			}
		}
		
		if(Common.checkList(vo.getAreaIds()))
			query.setParameter("areaIds", vo.getAreaIds());
		if(Common.checkList(vo.getParentAreaIds()))
			query.setParameter("parentAreaIds", vo.getParentAreaIds());
		
		if(StringUtils.isNotBlank(vo.getDateStart()))
			query.setParameter("dateStart", DateFormat.getYearMonthFirst(DateFormat.str2Date(vo.getDateStart(), 10), 0));
		if(StringUtils.isNotBlank(vo.getDateEnd()))
			query.setParameter("dateEnd", DateFormat.getYearMonthFirst(DateFormat.str2Date(vo.getDateEnd(), 10), 1));

	}

	@Override
	protected void switchVO2PO(StoreSaleProvisionVO vo, StoreSaleProvision po)
			throws Exception {
		if(vo.getStore() != null)
			po.setStore(vo.getStore());
		if(vo.getProvisionTime() != null)
			po.setProvisionTime(vo.getProvisionTime());
		if(vo.getSaleSum() != null)
			po.setSaleSum(vo.getSaleSum());
		if(vo.getFixSum() != null)
			po.setFixSum(vo.getFixSum());
		
	}

	@Override
	public String doImports(List<StoreSaleProExcelVO> list) throws Exception {
		StringBuilder sb = new StringBuilder();
		int num =0, succ = 0,fail = 0;
		String s = StringUtils.EMPTY;
		String d = StringUtils.EMPTY;
		
		for(StoreSaleProExcelVO vo : list){
			if(vo.getCid() == null)
				continue;
			num++;
			d = "序号"+ num +"数据失败：";
			
			Store store = null;
			if(StringUtils.isBlank(vo.getStoreCde()))
				s = "门店编码不能为空";
			else{
				store = storeService.getByCde(vo.getStoreCde().trim());
				String reg="^((?:19|20)\\d\\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";
				if(store == null)
					s = "门店编码不存在";
				else if(StringUtils.isBlank(vo.getSaleSum()))
					s = "销售额不能为空";
				else if(StringUtils.isBlank(vo.getProvisionTime()))
					s = "预提日期不能为空";
				else if(!vo.getProvisionTime().matches(reg))
					s = "日期格式不正确";
			}
			
			if(StringUtils.isNotBlank(s)){
				sb.append(d + s + "</br>");
				fail++;
				continue;
			}
			
			StoreSaleProvision po = new StoreSaleProvision();
			po.setStore(store);
			if(StringUtils.isNotBlank(vo.getSaleSum()))
				po.setSaleSum(Double.parseDouble(vo.getSaleSum()));
			if(StringUtils.isNotBlank(vo.getFixSum()))
				po.setFixSum(Double.parseDouble(vo.getFixSum()));
			if(StringUtils.isNotBlank(vo.getProvisionTime()))
				po.setProvisionTime(DateFormat.str2Date(vo.getProvisionTime(), 2));
			
			po.setCount(count(po));
			this.merge(po);
			succ++;
		}
		
		return "导入完成，共导入 " + num + " 条数据，其中成功：" + succ + " 条，失败：" + fail+ " 条<br/>" + sb.toString();

	}

	private Double count(StoreSaleProvision po){
		Double count = 0d;
		if(po.getStore().getPropertion() != null && po.getSaleSum() != null)
			count = PublicType.setDoubleScale(po.getStore().getPropertion() * po.getSaleSum() * 0.01);
		if(po.getFixSum() != null)
			count = PublicType.setDoubleScale(count + po.getFixSum());
		
		return count;
	}

	@Override
	public void export(String[] heads, StoreSaleProvision ssp,
			Map<String, String> map) {
		String[] header=Common.getExportHeader("STORESALEPRO");
		//"所属客户","门店编码","门店名称","销售占比","销售额","固定金额","预提日期","预提费用"
		for(String key:heads){
			int i=0;
			if(key.equals(header[i++])){
				if(ssp.getStore() != null && ssp.getStore().getCustom() != null)
					map.put(key,ssp.getStore().getCustom().getCustomName());
			}else if(key.equals(header[i++])){
				if(ssp.getStore() != null)
					map.put(key, ssp.getStore().getStoreCde());
			}else if(key.equals(header[i++])){
				if(ssp.getStore() != null)
					map.put(key, ssp.getStore().getStoreName());
			}else if(key.equals(header[i++])){
				if(ssp.getStore() != null && ssp.getStore().getPropertion() != null)
					map.put(key, ssp.getStore().getPropertion() + "%");
			}else if(key.equals(header[i++])){
				if(ssp.getSaleSum() != null)
					map.put(key, ssp.getSaleSum().toString());
			}else if(key.equals(header[i++])){
				if(ssp.getFixSum() != null)
					map.put(key, ssp.getFixSum().toString());
			}else if(key.equals(header[i++])){
				if(ssp.getProvisionTime() != null)
					map.put(key, DateFormat.date2Str(ssp.getProvisionTime(), 2));
			}else if(key.equals(header[i++])){
				if(ssp.getCount() != null)
					map.put(key, ssp.getCount().toString());
			}
		}
	}

}
