package com.kington.fshg.service.info;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType;
import com.kington.fshg.common.excel.vo.StoreProductStockExcelVO;
import com.kington.fshg.model.info.StoreProduct;
import com.kington.fshg.model.info.StoreProductStock;
import com.kington.fshg.model.info.StoreProductStockVO;
import com.kington.fshg.service.BaseServiceImpl;

public class StoreProductStockServiceImpl extends
		BaseServiceImpl<StoreProductStock, StoreProductStockVO> implements
		StoreProductStockService {
	private static final long serialVersionUID = 2662186311365651437L;

	@Resource
	private StoreProductService storeProductService;
	
	@Override
	protected String getQueryStr(StoreProductStockVO vo) throws Exception {
		StringBuffer sql = new StringBuffer();
		if(vo.getStoreProduct() != null){
			if(vo.getStoreProduct().getStore() != null){
				if(StringUtils.isNotBlank(vo.getStoreProduct().getStore().getStoreName()))
					sql.append(" and o.storeProduct.store.storeName like :storeName ");
				if(vo.getStoreProduct().getStore().getCustom() != null){
					if(StringUtils.isNotBlank(vo.getStoreProduct().getStore().getCustom().getCustomName()))
						sql.append(" and o.storeProduct.store.custom.customName like :customName ");
					if(vo.getStoreProduct().getStore().getCustom().getUser() != null
							&& Common.checkLong(vo.getStoreProduct().getStore().getCustom().getUser().getId())){
						sql.append(" and o.storeProduct.store.custom.user.id = :userId ");
					}
				}
			}
			
			if(vo.getStoreProduct().getProduct() != null){
				if(StringUtils.isNotBlank(vo.getStoreProduct().getProduct().getProductName()))
					sql.append(" and o.storeProduct.product.productName like :productName ");
				if(StringUtils.isNotBlank(vo.getStoreProduct().getProduct().getStockCde()))
					sql.append(" and o.storeProduct.product.stockCde = :stockCde ");
				if(StringUtils.isNotBlank(vo.getStoreProduct().getProduct().getNumber()))
					sql.append(" and o.storeProduct.product.number = :number ");
				
			}
		}
		
		if(StringUtils.isNotBlank(vo.getMonth()))
			sql.append(" and o.month = :month ");
		
		if(Common.checkList(vo.getAreaIds()))
			sql.append(" and o.storeProduct.store.custom.area.id in (:areaIds)");
		if(Common.checkList(vo.getParentAreaIds()))
			sql.append(" and o.storeProduct.store.custom.area.parentArea.id in (:parentAreaIds)");
		return sql.toString();
	}

	@Override
	protected void setQueryParm(Query query, StoreProductStockVO vo)
			throws Exception {
		if(vo.getStoreProduct() != null){
			if(vo.getStoreProduct().getStore() != null){
				if(StringUtils.isNotBlank(vo.getStoreProduct().getStore().getStoreName()))
					query.setParameter("storeName", Common.SYMBOL_PERCENT + vo.getStoreProduct().getStore().getStoreName()
							+ Common.SYMBOL_PERCENT);
				if(vo.getStoreProduct().getStore().getCustom() != null){
					if(StringUtils.isNotBlank(vo.getStoreProduct().getStore().getCustom().getCustomName()))
						query.setParameter("customName", Common.SYMBOL_PERCENT + vo.getStoreProduct().getStore().getCustom().getCustomName()
								+ Common.SYMBOL_PERCENT);
				}
				if(vo.getStoreProduct().getStore().getCustom().getUser() != null
						&& Common.checkLong(vo.getStoreProduct().getStore().getCustom().getUser().getId())){
					query.setParameter("userId", vo.getStoreProduct().getStore().getCustom().getUser().getId());
				}
			}
			
			if(vo.getStoreProduct().getProduct() != null){
				if(StringUtils.isNotBlank(vo.getStoreProduct().getProduct().getProductName()))
					query.setParameter("productName", Common.SYMBOL_PERCENT + vo.getStoreProduct().getProduct().getProductName()
							+ Common.SYMBOL_PERCENT);
				if(StringUtils.isNotBlank(vo.getStoreProduct().getProduct().getStockCde()))
					query.setParameter("stockCde", vo.getStoreProduct().getProduct().getStockCde());
				if(StringUtils.isNotBlank(vo.getStoreProduct().getProduct().getNumber()))
					query.setParameter("number", vo.getStoreProduct().getProduct().getNumber());
			}
		}	
		
		if(StringUtils.isNotBlank(vo.getMonth()))
			query.setParameter("month", vo.getMonth());
		
		if(Common.checkList(vo.getAreaIds()))
			query.setParameter("areaIds", vo.getAreaIds());
		if(Common.checkList(vo.getParentAreaIds()))
			query.setParameter("parentAreaIds", vo.getParentAreaIds());
	}

	@Override
	protected void switchVO2PO(StoreProductStockVO vo, StoreProductStock po)
			throws Exception {
		if(vo.getStoreProduct() != null)
			po.setStoreProduct(vo.getStoreProduct());
		if(vo.getCount() != null)
			po.setCount(vo.getCount());
		if(vo.getMoney() != null)
			po.setMoney(vo.getMoney());
		if(StringUtils.isNotBlank(vo.getMonth()))
			po.setMonth(vo.getMonth());
		
	}
	
	@Override
	public boolean delete(Long id) throws Exception {
		if(id == null)
			return false;
		String sql ="delete from info_store_product_stock where id = :id ";
		Query query = em.createNativeQuery(sql);
		query.setParameter("id", id);
		return query.executeUpdate() > 0;
	}

	@Override
	public String doImports(List<StoreProductStockExcelVO> list)
			throws Exception {
		StringBuilder sb = new StringBuilder();
		int num =0, succ = 0,fail = 0;
		String s = StringUtils.EMPTY;
		String d = StringUtils.EMPTY;
		
		for(StoreProductStockExcelVO vo : list){
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
			
			StoreProduct po = storeProductService.getByStoNamePro(vo.getCustomName(), vo.getStoreName(), vo.getStockCde());
			if(po == null){
				sb.append(d + "门店存货信息不存在</br>");
				fail++;
				continue;
			}
				
			StoreProductStock sps = new StoreProductStock();
			sps.setStoreProduct(po);
			sps.setCount(vo.getCount());
			sps.setMonth(vo.getMonth());
			if(po.getProduct().getStandardPrice() != null)
				sps.setMoney(PublicType.setDoubleScale(vo.getCount() * po.getProduct().getStandardPrice()));
				
			this.merge(sps);
			succ++;
		}
		
		return "导入完成，共导入 " + num + " 条数据，其中成功：" + succ + " 条，失败：" + fail+ " 条<br/>" + sb.toString();

	}
	
	public String checkVO(StoreProductStockExcelVO vo) throws Exception{
		if(vo == null)
			return "导入对象不能为空";
		if(StringUtils.isBlank(vo.getStoreName()))
			return "门店名称不能为空";
		if(StringUtils.isBlank(vo.getCustomName()))
			return "所属客户名称不能为空";
		if(StringUtils.isBlank(vo.getStockCde())){
			return "存货编码码不能为空";
		}
		if(vo.getCount() == null){
			return "数量不能为空";
		}
		if(StringUtils.isBlank(vo.getMonth())){
			return "年月不能为空";
		}
		
		return null;
	}

	@Override
	public void export(String[] heads, StoreProductStock o,
			Map<String, String> map) throws Exception {
		String[] header=Common.getExportHeader("STOREPRODUCTSTOCK");
		for(String key : heads){
			int i=0;
			if(key.equals(header[i++])){             
				map.put(key, o.getStoreProduct().getStore().getCustom().getCustomName());//客户名称
			}else if(key.equals(header[i++])){
				map.put(key, o.getStoreProduct().getStore().getStoreName());  //门店名称
			}else if(key.equals(header[i++])){
				map.put(key, o.getStoreProduct().getProduct().getStockCde()); // 存货编码
			}else if(key.equals(header[i++])){
				map.put(key, o.getStoreProduct().getProduct().getNumber()); // 产品货号
			}else if(key.equals(header[i++])){
				map.put(key, o.getStoreProduct().getProduct().getProductName()); // 产品名称
			}else if(key.equals(header[i++])){
				map.put(key, o.getStoreProduct().getProduct().getStandard()); // 规格
			}else if(key.equals(header[i++])){
				map.put(key, o.getStoreProduct().getProduct().getUnit()); // 单位
			}else if(key.equals(header[i++])){
				if(o.getStoreProduct().getProduct().getStandardPrice() != null)
					map.put(key, o.getStoreProduct().getProduct().getStandardPrice().toString()); // 标准价
			}else if(key.equals(header[i++])){
				if(o.getCount() != null)
					map.put(key, o.getCount().toString()); // 数量
			}else if(key.equals(header[i++])){
				if(o.getMoney() != null)
					map.put(key, o.getMoney().toString()); // 金额
			}else if(key.equals(header[i++])){
				map.put(key, o.getMonth()); // 年月
			}
		}
		
	}

}
