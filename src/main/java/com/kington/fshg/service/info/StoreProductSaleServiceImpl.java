package com.kington.fshg.service.info;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType;
import com.kington.fshg.common.excel.vo.StoreProductSaleExcelVO;
import com.kington.fshg.model.info.StoreProduct;
import com.kington.fshg.model.info.StoreProductSale;
import com.kington.fshg.model.info.StoreProductSaleVO;
import com.kington.fshg.service.BaseServiceImpl;

public class StoreProductSaleServiceImpl extends
		BaseServiceImpl<StoreProductSale, StoreProductSaleVO> implements
		StoreProductSaleService {

	private static final long serialVersionUID = 5283509998894537868L;
	
	@Resource
	private StoreProductService storeProductService;

	@Override
	protected String getQueryStr(StoreProductSaleVO vo) throws Exception {
		StringBuffer sql = new StringBuffer();
		if(vo.getStoreProduct() != null){
			if(vo.getStoreProduct().getStore() != null){
				if(StringUtils.isNotBlank(vo.getStoreProduct().getStore().getStoreName()))
					sql.append(" and o.storeProduct.store.storeName like :storeName ");
				if(vo.getStoreProduct().getStore().getCustom() != null){
					if(StringUtils.isNotBlank(vo.getStoreProduct().getStore().getCustom().getCustomName()))
						sql.append(" and o.storeProduct.store.custom.customName like :customName ");
				}
				if(vo.getStoreProduct().getStore().getCustom().getUser() != null
						&& Common.checkLong(vo.getStoreProduct().getStore().getCustom().getUser().getId())){
					sql.append(" and o.storeProduct.store.custom.user.id = :userId ");
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
		
		if(StringUtils.isNotBlank(vo.getYearMonth()))
			sql.append(" and o.yearMonth = :yearMonth ");
		
		if(Common.checkLong(vo.getUserId()))
			sql.append(" and o.storeProduct.store.custom.user.id = :userId ");
		if(Common.checkList(vo.getAreaIds()))
			sql.append(" and o.storeProduct.store.custom.area.id in (:areaIds)");
		if(Common.checkList(vo.getParentAreaIds()))
			sql.append(" and o.storeProduct.store.custom.area.parentArea.id in (:parentAreaIds)");
		return sql.toString();
	}

	@Override
	protected void setQueryParm(Query query, StoreProductSaleVO vo)
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
		
		if(StringUtils.isNotBlank(vo.getYearMonth()))
			query.setParameter("yearMonth", vo.getYearMonth());
		
		if(Common.checkLong(vo.getUserId()))
			query.setParameter("userId", vo.getUserId());
		if(Common.checkList(vo.getAreaIds()))
			query.setParameter("areaIds", vo.getAreaIds());
		if(Common.checkList(vo.getParentAreaIds()))
			query.setParameter("parentAreaIds", vo.getParentAreaIds());
		
	}

	@Override
	protected void switchVO2PO(StoreProductSaleVO vo, StoreProductSale po)
			throws Exception {
		if(vo.getStoreProduct() != null)
			po.setStoreProduct(vo.getStoreProduct());
		if(vo.getSaleCount() != null)
			po.setSaleCount(vo.getSaleCount());
		if(vo.getStandardSaleMoney() != null)
			po.setStandardSaleMoney(vo.getStandardSaleMoney());
		if(vo.getRetailSaleMoney() != null)
			po.setRetailSaleMoney(vo.getRetailSaleMoney());
		if(StringUtils.isNotBlank(vo.getYearMonth()))
			po.setYearMonth(vo.getYearMonth());
		
	}
	
	@Override
	public boolean delete(Long id) throws Exception {
		if(id == null)
			return false;
		String sql ="delete from info_store_product_sale where id = :id ";
		Query query = em.createNativeQuery(sql);
		query.setParameter("id", id);
		return query.executeUpdate() > 0;
	}
	
	@Override
	public String doImports(List<StoreProductSaleExcelVO> list)
			throws Exception {
		StringBuilder sb = new StringBuilder();
		int num =0, succ = 0,fail = 0;
		String s = StringUtils.EMPTY;
		String d = StringUtils.EMPTY;
		
		for(StoreProductSaleExcelVO vo : list){
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
				
			StoreProductSale sps = new StoreProductSale();
			sps.setStoreProduct(po);
			sps.setSaleCount(vo.getSaleCount());
			sps.setRetailSaleMoney(vo.getRetailSaleMoney());
			sps.setYearMonth(vo.getYearMonth());
			if(po.getProduct().getStandardPrice() != null)
				sps.setStandardSaleMoney(PublicType.setDoubleScale(vo.getSaleCount() * po.getProduct().getStandardPrice()));
				
			this.merge(sps);
			succ++;
		}
		
		return "导入完成，共导入 " + num + " 条数据，其中成功：" + succ + " 条，失败：" + fail+ " 条<br/>" + sb.toString();

	}
	
	public String checkVO(StoreProductSaleExcelVO vo) throws Exception{
		if(vo == null)
			return "导入对象不能为空";
		if(StringUtils.isBlank(vo.getStoreName()))
			return "门店名称不能为空";
		if(StringUtils.isBlank(vo.getCustomName()))
			return "所属客户名称不能为空";
		if(StringUtils.isBlank(vo.getStockCde())){
			return "存货编码码不能为空";
		}
		if(vo.getSaleCount() == null){
			return "销量不能为空";
		}
		if(vo.getRetailSaleMoney() == null){
			return "终端零售价销售额不能为空";
		}
		if(StringUtils.isBlank(vo.getYearMonth())){
			return "年月不能为空";
		}
		
		return null;
	}

	@Override
	public void export(String[] heads, StoreProductSale o,
			Map<String, String> map) throws Exception {
		String[] header=Common.getExportHeader("STOREPRODUCTSALE");
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
				if(o.getSaleCount() != null)
					map.put(key, o.getSaleCount().toString()); // 销量
			}else if(key.equals(header[i++])){
				if(o.getStandardSaleMoney() != null)
					map.put(key, o.getStandardSaleMoney().toString()); // 标准价销售额
			}else if(key.equals(header[i++])){
				if(o.getRetailSaleMoney() != null)
					map.put(key, o.getRetailSaleMoney().toString()); // 终端零售价销售额
			}else if(key.equals(header[i++])){
				map.put(key, o.getYearMonth()); // 年月
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> compare(StoreProductSaleVO vo) throws Exception {
		String sql = "SELECT a1.parentArea,a.areaName,c.customCde,c.customName,s.storeCde,s.storeName,p.productCde,p.productName, "
				+ "IF(temp.countSum IS NULL ,0,temp.countSum),IF(temp.moneySum IS NULL ,0,temp.moneySum),IF(temp1.countSum IS NULL ,0,temp1.countSum),IF(temp1.moneySum IS NULL ,0,temp1.moneySum) "
				+ "FROM( SELECT storeProductId,SUM(saleCount) countSum,SUM(retailSaleMoney) moneySum "
				+ "FROM info_store_product_sale where yearMonth='" + vo.getStartMonth() + "' GROUP BY storeProductId) temp "
				+ "LEFT JOIN( SELECT storeProductId,SUM(saleCount) countSum,SUM(retailSaleMoney) moneySum "
				+ "FROM info_store_product_sale WHERE yearMonth  = '" + vo.getEndMonth() + "' GROUP BY storeProductId) temp1 "
				+ "ON temp.storeProductId = temp1.storeProductId "
				+ "LEFT JOIN info_store_product sp ON temp.storeProductId = sp.id "
				+ "LEFT JOIN info_product p ON sp.productId = p.id "
				+ "LEFT JOIN info_store s ON sp.storeId = s.id "
				+ "LEFT JOIN info_custom c ON s.custom_id = c.id "
				+ "LEFT JOIN sys_user u ON c.userId = u.id "
				+ "LEFT JOIN info_area a ON c.areaId = a.id "
				+ "LEFT JOIN (SELECT id , areaName parentArea FROM info_area WHERE parentId IS NULL)a1 ON a.parentId = a1.id where 1 = 1 ";
		
		if(Common.checkLong(vo.getUserId()))
			sql += " and u.id = " + vo.getUserId();
		if(Common.checkList(vo.getAreaIds()))
			sql += " and a.id in (" + vo.getAreaIds().toString().replace("[", "").replace("]", "") + ")";
		if(Common.checkList(vo.getParentAreaIds()))
			sql += " and a1.id in (" + vo.getParentAreaIds().toString().replace("[", "").replace("]", "") + ")";
		
		if(Common.checkLong(vo.getParentAreaId()))
			sql += " and a1.id = " + vo.getParentAreaId();
		if(Common.checkLong(vo.getAreaId()))
			sql += " and a.id = " + vo.getAreaId();
		if(StringUtils.isNotBlank(vo.getCustomName()))
			sql += " and c.customName like '%" + vo.getCustomName() + "%' " ;
		if(StringUtils.isNotBlank(vo.getStoreName()))
			sql += " and s.storeName like '%" + vo.getStoreName() + "%' " ;
		if(StringUtils.isNotBlank(vo.getProductName()))
			sql += " and p.productName like '%" + vo.getProductName() + "%' " ;
		
		Query query = em.createNativeQuery(sql);
		
		return (List<Object[]>) query.getResultList();
	}

	@Override
	public void exportCompare(String[] heads, Object[] o,Map<String, String> map) throws Exception {
		String[] header=Common.getExportHeader("COMPARE");
		
		for(String key : heads){
			int i=0;
			if(key.equals(header[i++])){             
				map.put(key, o[0].toString());//所属大区
			}else if(key.equals(header[i++])){
				map.put(key, o[1].toString());  //所属地区
			}else if(key.equals(header[i++])){
				map.put(key, o[2].toString());  //客户编码
			}else if(key.equals(header[i++])){
				map.put(key, o[3].toString());  //客户名称
			}else if(key.equals(header[i++])){
				map.put(key, o[4].toString());  //门店编码
			}else if(key.equals(header[i++])){
				map.put(key, o[5].toString());  //门店名称
			}else if(key.equals(header[i++])){
				map.put(key, o[6].toString());  //产品编码
			}else if(key.equals(header[i++])){
				map.put(key, o[7].toString());  //产品名称
			}else if(key.equals(header[i++])){
				map.put(key, o[8].toString());  //前期销售数量
			}else if(key.equals(header[i++])){
				map.put(key, o[10].toString());  //后期销售数量
			}else if(key.equals(header[i++])){
				if(StringUtils.equals(o[8].toString(), "0"))	
					map.put(key, "0%");  //销售数量对比
				else{
					double old = Double.parseDouble(o[8].toString());
					double news = Double.parseDouble(o[10].toString());
					map.put(key, PublicType.setDoubleScale((news-old)/old*100) + "%");  //销售数量对比
				}
			}else if(key.equals(header[i++])){
				map.put(key, o[9].toString());  //前期销售额
			}else if(key.equals(header[i++])){
				map.put(key, o[11].toString());  //后期销售额
			}else if(key.equals(header[i++])){
				if(StringUtils.equals(o[9].toString(), "0"))	
					map.put(key, "0%");  //销售额对比
				else{
					double old = Double.parseDouble(o[9].toString());
					double news = Double.parseDouble(o[11].toString());
					map.put(key, PublicType.setDoubleScale((news-old)/old*100) + "%");  //销售额对比
				}
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> proportion(StoreProductSaleVO vo) throws Exception {
		String sql = "SELECT a1.parentArea,a.areaName,c.customCde,c.customName,s.storeCde,s.storeName,p.productCde,p.productName, "
				+ "IF(temp.countSum IS NULL ,0,temp.countSum),IF(temp.moneySum IS NULL ,0,temp.moneySum),IF(temp1.countSum IS NULL ,0,temp1.countSum),IF(temp1.moneySum IS NULL ,0,temp1.moneySum) "
				+ "FROM( SELECT s.id storeId,ps.storeProductId,SUM(ps.saleCount) countSum,SUM(ps.retailSaleMoney) moneySum  "
				+ "FROM info_store_product_sale ps LEFT JOIN info_store_product sp ON ps.storeProductId = sp.id "
				+ "LEFT JOIN info_store s ON sp.storeId = s.id " 
				+ "WHERE yearMonth>='" + vo.getStartMonth()  + "' AND yearMonth <= '" + vo.getEndMonth() 
				+ "' GROUP BY ps.storeProductId ORDER BY s.id,ps.storeProductId) temp "
				+ "LEFT JOIN( SELECT s.id,SUM(ps.saleCount) countSum,SUM(ps.retailSaleMoney) moneySum  "
				+ "FROM info_store_product_sale ps LEFT JOIN info_store_product sp ON ps.storeProductId = sp.id "
				+ "LEFT JOIN info_store s ON sp.storeId = s.id WHERE yearMonth>='" + vo.getStartMonth() + "' AND yearMonth <= '" + vo.getEndMonth() + "' "
				+ "GROUP BY s.id ORDER BY s.id,ps.storeProductId) temp1 ON temp.storeId = temp1.id "
				+ "LEFT JOIN info_store_product sp ON temp.storeProductId = sp.id "
				+ "LEFT JOIN info_product p ON sp.productId = p.id "
				+ "LEFT JOIN info_store s ON sp.storeId = s.id "
				+ "LEFT JOIN info_custom c ON s.custom_id = c.id "
				+ "LEFT JOIN sys_user u ON c.userId = u.id "
				+ "LEFT JOIN info_area a ON c.areaId = a.id "
				+ "LEFT JOIN (SELECT id , areaName parentArea FROM info_area WHERE parentId IS NULL)a1 ON a.parentId = a1.id where 1 = 1 ";
		
		if(Common.checkLong(vo.getUserId()))
			sql += " and u.id = " + vo.getUserId();
		if(Common.checkList(vo.getAreaIds()))
			sql += " and a.id in (" + vo.getAreaIds().toString().replace("[", "").replace("]", "") + ")";
		if(Common.checkList(vo.getParentAreaIds()))
			sql += " and a1.id in (" + vo.getParentAreaIds().toString().replace("[", "").replace("]", "") + ")";
		
		if(Common.checkLong(vo.getParentAreaId()))
			sql += " and a1.id = " + vo.getParentAreaId();
		if(Common.checkLong(vo.getAreaId()))
			sql += " and a.id = " + vo.getAreaId();
		if(StringUtils.isNotBlank(vo.getCustomName()))
			sql += " and c.customName like '%" + vo.getCustomName() + "%' " ;
		if(StringUtils.isNotBlank(vo.getStoreName()))
			sql += " and s.storeName like '%" + vo.getStoreName() + "%' " ;
		if(StringUtils.isNotBlank(vo.getProductName()))
			sql += " and p.productName like '%" + vo.getProductName() + "%' " ;
		
		Query query = em.createNativeQuery(sql);
		
		return (List<Object[]>) query.getResultList();
	}

	@Override
	public void exportProportion(String[] heads, Object[] o,
			Map<String, String> map) throws Exception {
		String[] header=Common.getExportHeader("PROPORTION");
		
		for(String key : heads){
			int i=0;
			if(key.equals(header[i++])){             
				map.put(key, o[0].toString());//所属大区
			}else if(key.equals(header[i++])){
				map.put(key, o[1].toString());  //所属地区
			}else if(key.equals(header[i++])){
				map.put(key, o[2].toString());  //客户编码
			}else if(key.equals(header[i++])){
				map.put(key, o[3].toString());  //客户名称
			}else if(key.equals(header[i++])){
				map.put(key, o[4].toString());  //门店编码
			}else if(key.equals(header[i++])){
				map.put(key, o[5].toString());  //门店名称
			}else if(key.equals(header[i++])){
				map.put(key, o[6].toString());  //产品编码
			}else if(key.equals(header[i++])){
				map.put(key, o[7].toString());  //产品名称
			}else if(key.equals(header[i++])){
				map.put(key, o[8].toString());  //产品销售数量
			}else if(key.equals(header[i++])){
				map.put(key, o[10].toString());  //门店销售数量
			}else if(key.equals(header[i++])){
				if(StringUtils.equals(o[8].toString(), "0"))	
					map.put(key, "0%");  //销售数量占比
				else{
					double product = Double.parseDouble(o[8].toString());
					double store = Double.parseDouble(o[10].toString());
					map.put(key, PublicType.setDoubleScale(product/store*100) + "%");  //销售数量占比
				}
			}else if(key.equals(header[i++])){
				map.put(key, o[9].toString());  //产品销售额
			}else if(key.equals(header[i++])){
				map.put(key, o[11].toString());  //门店销售额
			}else if(key.equals(header[i++])){
				if(StringUtils.equals(o[9].toString(), "0"))	
					map.put(key, "0%");  //销售额占比
				else{
					double product = Double.parseDouble(o[9].toString());
					double store = Double.parseDouble(o[11].toString());
					map.put(key, PublicType.setDoubleScale(product/store*100) + "%");  //销售额占比
				}
			}
		}
		
	}




}
