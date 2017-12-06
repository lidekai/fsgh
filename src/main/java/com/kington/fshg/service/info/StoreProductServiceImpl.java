package com.kington.fshg.service.info;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.excel.vo.StoreProductExcelVO;
import com.kington.fshg.model.info.Custom;
import com.kington.fshg.model.info.Product;
import com.kington.fshg.model.info.Store;
import com.kington.fshg.model.info.StoreProduct;
import com.kington.fshg.model.info.StoreProductVO;
import com.kington.fshg.model.info.StoreVO;
import com.kington.fshg.service.BaseServiceImpl;

public class StoreProductServiceImpl extends
		BaseServiceImpl<StoreProduct, StoreProductVO> implements
		StoreProductService {

	private static final long serialVersionUID = -5540464249790318101L;
	
	@Resource
	private StoreService storeService;
	
	@Resource
	private ProductService productService;

	@Override
	protected String getQueryStr(StoreProductVO vo) throws Exception {
		StringBuffer sql = new StringBuffer();
		if(vo.getStore() != null){
			if(StringUtils.isNotBlank(vo.getStore().getStoreName()))
				sql.append(" and o.store.storeName like :name ");
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
				
				if(StringUtils.isNotBlank(vo.getStore().getCustom().getCustomName()))
					sql.append(" and o.store.custom.customName like :customName ");
				if(vo.getStore().getCustom().getId() != null)
					sql.append(" and o.store.custom.id =:customId ");
				
			}
		}
		
		if(vo.getProduct() != null){
			if(StringUtils.isNotBlank(vo.getProduct().getNumber()))
				sql.append(" and o.product.number = :number ");
			if(StringUtils.isNotBlank(vo.getProduct().getProductName()))
				sql.append(" and o.product.productName like :productName ");
			if(StringUtils.isNotBlank(vo.getProduct().getStockCde()))
				sql.append(" and o.product.stockCde = :stockCde ");
		}
		
		if(Common.checkList(vo.getAreaIds()))
			sql.append(" and o.store.custom.area.id in (:areaIds)");
		if(Common.checkList(vo.getParentAreaIds()))
			sql.append(" and o.store.custom.area.parentArea.id in (:parentAreaIds)");
		
		return sql.toString();
	}

	@Override
	protected void setQueryParm(Query query, StoreProductVO vo)
			throws Exception {
		if(vo.getStore() != null){
			if(StringUtils.isNotBlank(vo.getStore().getStoreName()))
				query.setParameter("name", Common.SYMBOL_PERCENT + vo.getStore().getStoreName()
					+ Common.SYMBOL_PERCENT);
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
				if(StringUtils.isNotBlank(vo.getStore().getCustom().getCustomName()))
					query.setParameter("customName", Common.SYMBOL_PERCENT + vo.getStore().getCustom().getCustomName()
							+ Common.SYMBOL_PERCENT);
				if(vo.getStore().getCustom().getId() != null)
					query.setParameter("customId", vo.getStore().getCustom().getId());
			}
		}
		
		if(vo.getProduct() != null){
			if(StringUtils.isNotBlank(vo.getProduct().getNumber()))
				query.setParameter("number", vo.getProduct().getNumber());
			if(StringUtils.isNotBlank(vo.getProduct().getProductName()))
				query.setParameter("productName", Common.SYMBOL_PERCENT + vo.getProduct().getProductName()
						+ Common.SYMBOL_PERCENT);
			if(StringUtils.isNotBlank(vo.getProduct().getStockCde()))
				query.setParameter("stockCde", vo.getProduct().getStockCde());
		}
		
		if(Common.checkList(vo.getAreaIds()))
			query.setParameter("areaIds", vo.getAreaIds());
		if(Common.checkList(vo.getParentAreaIds()))
			query.setParameter("parentAreaIds", vo.getParentAreaIds());
		
	}

	@Override
	protected void switchVO2PO(StoreProductVO vo, StoreProduct po)
			throws Exception {
		if(vo.getStore() != null)
			po.setStore(vo.getStore());
		if(vo.getProduct() != null)
			po.setProduct(vo.getProduct());
		if(vo.getKaPrice() != null)
			po.setKaPrice(vo.getKaPrice());
		if(vo.getRetailPrice() != null)
			po.setRetailPrice(vo.getRetailPrice());
		
	}

	@Override
	public StoreProduct getByStoPro(Long storeId, Long productId) {
		String hql = "from StoreProduct where store.id =" + storeId + " and product.id =" + productId;
		Query query = em.createQuery(hql);
		if(Common.checkList(query.getResultList()))
			return (StoreProduct)query.getResultList().get(0);
		else
			return null;
	}

	@Override
	public boolean delete(Long id) throws Exception {
		if(id == null)
			return false;
		String sql ="delete from info_store_product where id = :id ";
		Query query = em.createNativeQuery(sql);
		query.setParameter("id", id);
		return query.executeUpdate() > 0;
	}

	@Override
	public String doImports(List<StoreProductExcelVO> list) throws Exception {
		StringBuilder sb = new StringBuilder();
		int num =0, succ = 0,fail = 0,add = 0;
		String s = StringUtils.EMPTY;
		String d = StringUtils.EMPTY;
		
		for(StoreProductExcelVO vo : list){
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
			
			StoreProduct po = null;
			Store store = null;
			Product product = null;
			if(StringUtils.isNotBlank(vo.getCustomName()) && StringUtils.isNotBlank(vo.getStoreName())){
				StoreVO svo = new StoreVO();
				Custom c = new Custom();
				c.setCustomName(vo.getCustomName());
				
				svo.setCustom(c);
				svo.setStoreName(vo.getStoreName());
				List<Store> storeList = storeService.getPageList(svo).getList();
				if(!Common.checkList(storeList)){
					sb.append(d + "门店信息不存在</br>");
					fail++;
					continue;
				}else
					store = storeList.get(0);
					
			}
			if(StringUtils.isNotBlank(vo.getStockCde())){
				product = productService.getByCde(vo.getStockCde());
				if(product == null){
					sb.append(d + "存货编码不存在</br>");
					fail++;
					continue;
				}
			}
			
			po = getByStoPro(store.getId(), product.getId());
			if(po == null){
				po = new StoreProduct();
				po.setStore(store);
				po.setProduct(product);
				add ++;
			} 
			if(StringUtils.isNotBlank(vo.getKaPrice()))
				po.setKaPrice(Double.parseDouble(vo.getKaPrice()));
			
			if(StringUtils.isNotBlank(vo.getRetailPrice()))
				po.setRetailPrice(Double.parseDouble(vo.getRetailPrice()));
				
			this.merge(po);
			succ++;
		}
		
		return "导入完成，共导入 " + num + " 条数据，其中成功：" + succ + " 条，新增：" + add + "条，更新：" + (succ - add) + "条，失败：" + fail+ " 条<br/>" + sb.toString();

	}
	
	public String checkVO(StoreProductExcelVO vo) throws Exception{
		if(vo == null)
			return "导入对象不能为空";
		if(StringUtils.isBlank(vo.getCustomName()))
			return "客户名称不能为空";
		if(StringUtils.isBlank(vo.getStoreName()))
			return "门店名称不能为空";
		if(StringUtils.isBlank(vo.getStockCde())){
			return "存货编码码不能为空";
		}
		
		return null;
	}

	@Override
	public StoreProduct getByStoNamePro(String customName, String storeName,
			String stockCde) {
		String hql = "from StoreProduct where store.storeName ='" + storeName + "' and store.custom.customName ='" + customName
				+ "' and product.stockCde ='" + stockCde + "'";
		Query query = em.createQuery(hql);
		if(Common.checkList(query.getResultList()))
			return (StoreProduct)query.getResultList().get(0);
		else
			return null;
	}

	

	@Override
	public void exportStoreProduct(String[] heads, StoreProduct sp, Map<String, String> map) {
		String[] header=Common.getExportHeader("STOREPRODUCT");
		//"直销KA价","终端零售价
		for(String key:heads){
			int i=0;
			if(key.equals(header[i++])){
				if(sp.getStore()!=null&&sp.getStore().getCustom()!=null){
					map.put(key, sp.getStore().getCustom().getCustomName());
				}
			}else if(key.equals(header[i++])){
				if(sp.getStore()!=null){
					map.put(key, sp.getStore().getStoreName());
				}
			}else if(key.equals(header[i++])){
				if(sp.getStore()!=null){
					map.put(key, sp.getStore().getStoreCde());
				}
			}else if(key.equals(header[i++])){
				if(sp.getProduct()!=null){
					map.put(key, sp.getProduct().getStockCde());
				}
			}else if(key.equals(header[i++])){
				if(sp.getProduct()!=null){
					map.put(key, sp.getProduct().getNumber());
				}
			}else if(key.equals(header[i++])){
				if(sp.getProduct()!=null){
					map.put(key, sp.getProduct().getProductName());
				}
			}else if(key.equals(header[i++])){
				if(sp.getProduct()!=null){
					if(sp.getProduct().getStandardPrice()!=null){
						map.put(key, sp.getProduct().getStandardPrice().toString());
					}else{
						map.put(key,"0.0");
					}
				}
			}else if(key.equals(header[i++])){
				if(sp.getKaPrice()!=null){
					map.put(key, sp.getKaPrice().toString());
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				if(sp.getRetailPrice()!=null){
					map.put(key, sp.getRetailPrice().toString());
				}else{
					map.put(key, "0.0");
				}
			}
			
		}
		
	}
	
	
	
	
	
	
}
