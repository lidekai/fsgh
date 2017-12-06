package com.kington.fshg.service.info;

import java.math.BigInteger;
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
import com.kington.fshg.common.PublicType.ChargeType;
import com.kington.fshg.common.PublicType.DictType;
import com.kington.fshg.common.excel.vo.ProductExcelVO;
import com.kington.fshg.model.info.Product;
import com.kington.fshg.model.info.ProductType;
import com.kington.fshg.model.info.ProductVO;
import com.kington.fshg.model.system.Dict;
import com.kington.fshg.service.BaseServiceImpl;
import com.kington.fshg.service.system.DictService;

public class ProductServiceImpl extends BaseServiceImpl<Product, ProductVO> implements
		ProductService {
	private static final long serialVersionUID = 7352501048165656839L;
	
	@Resource
	public ProductTypeService productTypeService;
	@Resource
	private DictService dictService;

	@Override
	protected String getQueryStr(ProductVO vo) throws Exception {
		StringBuffer sql = new StringBuffer();
		if(StringUtils.isNotBlank(vo.getStockCde()))
			sql.append(" and o.stockCde like :stockCde ");
		if(StringUtils.isNotBlank(vo.getProductCde()))
			sql.append(" and o.productCde like :productCde ");
		if(StringUtils.isNotBlank(vo.getProductName()))
			sql.append(" and o.productName like :name ");
		if(StringUtils.isNotBlank(vo.getNumber()))
			sql.append(" and o.number like :number ");
		if(vo.getProductType() != null && Common.checkLong(vo.getProductType().getId()))
			sql.append(" and o.productType.id =:productTypeId ");
		if(vo.getChargeType() != null)
			sql.append(" and o.chargeType =:chargeType ");
		if(vo.getNewProduct()!=null){
			sql.append(" and o.newProduct =:newProduct ");
		}
		return sql.toString();
	}

	@Override
	protected void setQueryParm(Query query, ProductVO vo) throws Exception {
		if(StringUtils.isNotBlank(vo.getStockCde()))
			query.setParameter("stockCde", Common.SYMBOL_PERCENT + vo.getStockCde() 
					+ Common.SYMBOL_PERCENT);
		if(StringUtils.isNotBlank(vo.getProductCde()))
			query.setParameter("productCde", Common.SYMBOL_PERCENT + vo.getProductCde() 
					+ Common.SYMBOL_PERCENT);
		if(StringUtils.isNotBlank(vo.getProductName()))
			query.setParameter("name", Common.SYMBOL_PERCENT + vo.getProductName() 
					+ Common.SYMBOL_PERCENT);
		if(StringUtils.isNotBlank(vo.getNumber()))
			query.setParameter("number", Common.SYMBOL_PERCENT + vo.getNumber()
					+ Common.SYMBOL_PERCENT);
		if(vo.getProductType() != null && Common.checkLong(vo.getProductType().getId()))
			query.setParameter("productTypeId", vo.getProductType().getId());
		if(vo.getChargeType() != null)
			query.setParameter("chargeType", vo.getChargeType());
		if(vo.getNewProduct()!=null){
			query.setParameter("newProduct", vo.getNewProduct());
		}
			
	}

	@Override
	protected void switchVO2PO(ProductVO vo, Product po) throws Exception {
		if(po == null)
			po = new Product();
		if(StringUtils.isNotBlank(vo.getStockCde()))
			po.setStockCde(vo.getStockCde());
		if(StringUtils.isNotBlank(vo.getProductName()))
			po.setProductName(vo.getProductName());
		if(StringUtils.isNotBlank(vo.getStandard()))
			po.setStandard(vo.getStandard());
		if(StringUtils.isNotBlank(vo.getNumber()))
			po.setNumber(vo.getNumber());
		if(vo.getBoxWeight() != null)
			po.setBoxWeight(vo.getBoxWeight());
		if(vo.getWidth() != null)
			po.setWidth(vo.getWidth());
		if(vo.getLength() != null)
			po.setLength(vo.getLength());
		if(vo.getHeight() != null)
			po.setHeight(vo.getHeight());
		if(vo.getMeterWeight() != null)
			po.setMeterWeight(vo.getMeterWeight());
		if(vo.getChargeType() != null)
			po.setChargeType(vo.getChargeType());
		if(vo.getProductType() != null)
			po.setProductType(vo.getProductType());
		if(vo.getVolume() != null)
			po.setVolume(vo.getVolume());
		if(StringUtils.isNotBlank(vo.getProductCde()))
			po.setProductCde(vo.getProductCde());
		if(StringUtils.isNotBlank(vo.getRemark())){
			po.setRemark(vo.getRemark());
		}
		if(vo.getNewProduct()!=null){			
			po.setNewProduct(vo.getNewProduct());		
		}
		if(vo.getStandardPrice()!=null){
			po.setStandardPrice(vo.getStandardPrice());
		}
		if(vo.getStartTime()!=null){
			po.setStartTime(vo.getStartTime());
		}
		if(StringUtils.isNotBlank(vo.getUnit())){
			po.setUnit(vo.getUnit());
		}
		if(StringUtils.isNotBlank(vo.getBarCode())){
			po.setBarCode(vo.getBarCode());
		}
		if(StringUtils.isNotBlank(vo.getPath()))
			po.setPath(vo.getPath());
		
		if(vo.getQuote()!=null){
			po.setQuote(vo.getQuote());
		}
		
	}

	@Override
	public Product getByCde(String stockCde) throws Exception {
		String hql = "from Product o where o.stockCde = :cde";
		Query query = em.createQuery(hql);
		query.setParameter("cde", stockCde);
		if(Common.checkList(query.getResultList()))
			return (Product)query.getResultList().get(0);
		return null;
	}

	@Override
	public String doImports(List<ProductExcelVO> list) throws Exception {
		StringBuilder r = new StringBuilder();
		int num = 0, succ = 0, fail = 0;
		String s = StringUtils.EMPTY;
		String d = StringUtils.EMPTY;
		
		for(ProductExcelVO vo:list){
			if (vo == null || StringUtils.isBlank(vo.getCid())){
				continue;
			}
			num++;
			d = "序号" + num + "数据失败：";
			
			Product product = new Product();
			ProductType productType = null;
			
			//校验数据有效性
			if(StringUtils.isBlank(vo.getStockCde()))
				s = "存货编码不能为空";
			else if(StringUtils.isBlank(vo.getProductType()))
				s = "所属分类不能为空";
			else if(StringUtils.isBlank(vo.getProductName()))
				s = "产品名称不能为空";
			else {
				productType = productTypeService.getByName(vo.getProductType());
				if(productType == null)
					s = "所属分类系统不存在";
				else{
					product = this.getByCde(vo.getStockCde());
					if(product == null)  product = new Product();
					product.setProductType(productType);
				}
			}
			
			try{
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getBoxWeight()))
					product.setBoxWeight(Double.parseDouble(vo.getBoxWeight()));				
			}catch(Exception e){
				s = "每箱毛重填写格式不正确";
			}
			
			try{
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getLength()))
					product.setLength(Double.parseDouble(vo.getLength()));				
			}catch(Exception e){
				s = "纸箱长填写格式不正确";
			}
			
			try{
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getWidth()))
					product.setWidth(Double.parseDouble(vo.getWidth()));				
			}catch(Exception e){
				s = "纸箱宽填写格式不正确";
			}
			
			try{
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getHeight()))
					product.setHeight(Double.parseDouble(vo.getHeight()));				
			}catch(Exception e){
				s = "纸箱高填写格式不正确";
			}
			
			/*try{
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getMeterWeight()))
					product.setMeterWeight(Double.parseDouble(vo.getMeterWeight()));				
			}catch(Exception e){
				s = "每立方米重量填写格式不正确";
			}*/
			
			
			try{
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getStandardPrice()))
					product.setStandardPrice(Double.parseDouble(vo.getStandardPrice()));				
			}catch(Exception e){
				s = "标准价格式不正确";
			}
			

			try{
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getQuote()))
					product.setQuote(Double.parseDouble(vo.getQuote()));				
			}catch(Exception e){
				s = "报价格式不正确";
			}
			
			try{
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getNewProduct())){
					if("是".equals(vo.getNewProduct())){
						product.setNewProduct(true);
					}else if("否".equals(vo.getNewProduct())){
						product.setNewProduct(false);
					}else{
						s="是否新品格式不正确(只能填是否)";
					}						
				}else{
					product.setNewProduct(false);
				}
								
			}catch(Exception e){
				s = "是否新品格式不正确";
			}
			
			try{
				if(StringUtils.isBlank(s)&&StringUtils.isNotBlank(vo.getStartTime())){
					String reg="^((?:19|20)\\d\\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";
					if(vo.getStartTime().matches(reg)){
						product.setStartTime(DateFormat.str2Date(vo.getStartTime(), 2));
					}else{
						s= "日期格式不正确";
					}
					
				}else if(StringUtils.isBlank(vo.getStartTime())){
					product.setStartTime(new Date());
				}			
			}catch(Exception e){
				e.printStackTrace();
			}
		
			if (StringUtils.isNotBlank(s)) {
				r.append(d + s + "<br/>");
				fail++;
				continue;
			}
			
			product.setStockCde(vo.getStockCde());
			product.setNumber(vo.getNumber());
			product.setProductName(vo.getProductName());
			product.setStandard(vo.getStandard());
			product.setProductCde(vo.getProductCde());
			product.setRemark(vo.getRemark());
			product.setUnit(vo.getUnit());
			product.setBarCode(vo.getBarCode());
			
			if(product.getLength() != null && product.getWidth() != null && product.getHeight() != null)
				product.setVolume(Common.multDouble(product.getLength(), product.getWidth(), product.getHeight()));
			
			if(product.getBoxWeight() != null && product.getVolume() != null)
				product.setMeterWeight(PublicType.setDoubleScale(product.getBoxWeight()/product.getVolume()));
			
			List<Dict> dictList = dictService.getByType(DictType.ZPBZ);
			Double bz = Common.checkList(dictList) ? dictList.get(0).getValue() : 260d;
			if(product.getMeterWeight() != null && product.getMeterWeight().compareTo(bz) >= 0)
				product.setChargeType(ChargeType.WEIGHT);
			else
				product.setChargeType(ChargeType.VOLUME);
			
			this.merge(product);			
			
			succ++;
		}
		
		return "导入完成，共导入 " + num + " 条数据，其中成功：" + succ + " 条，失败：" + fail+ " 条<br/>" + r.toString();

	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, List<ProductVO>> getProductMap() throws Exception {
		String sql = "SELECT t.productTypeName,p.id,p.productName,p.standard FROM "
				+ "info_product p LEFT JOIN info_product_type t ON  p.productTypeId = t.id "
				+ "ORDER BY p.productTypeId ";
		Query query = em.createNativeQuery(sql);
		List<Object[]> resultList = query.getResultList();
		Map<String, List<ProductVO>> productMap = new HashMap<String, List<ProductVO>>();
		
		if(Common.checkList(resultList)){
			String productType = resultList.get(0)[0].toString();
			List<ProductVO> list = new ArrayList<ProductVO>();
			for(Object[] o : resultList){
				ProductVO productVO = new ProductVO();
				productVO.setId(Long.parseLong(o[1].toString()));
				productVO.setProductName(o[2].toString() + "(" + o[3].toString() + ")");
				
				if(!StringUtils.equals(productType, o[0].toString())){
					productMap.put(productType, list);
					list = new ArrayList<ProductVO>();
					list.add(productVO);
					productType = o[0].toString();
				}else
					list.add(productVO);
			}
			productMap.put(productType, list);
		}
		return productMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getListByCustomId(Long customId) throws Exception {
		String sql="select o.product from CustomProduct o where o.custom.id = :customId ";
		Query query = em.createQuery(sql);
		query.setParameter("customId", customId);
		List<Product> list = query.getResultList();
		return list;
	}

	
	@Override
	public boolean updateNewProduct(long id, String isType) {
		Product po=em.find(Product.class, id);
		po.setNewProduct(Boolean.parseBoolean(isType));	
		try {
			this.merge(po);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return  true;
	}

	@Override
	public void exportProduct(String[] heads, Product pd, Map<String, String> map) {
		String[] header=Common.getExportHeader("PRODUCT");
		for(String key :heads){
			int i=0;
			if(key.equals(header[i++])){
				map.put(key, pd.getStockCde());   //存货编码
			}else if(key.equals(header[i++])){
				map.put(key, pd.getProductCde());  //存货代码
			}else if(key.equals(header[i++])){
				map.put(key, pd.getNumber());	//货号
			}else if(key.equals(header[i++])){
				map.put(key, pd.getProductName()); //产品名称
			}else if(key.equals(header[i++])){
				map.put(key, pd.getProductType().getProductTypeName());  //产品类型
			}else if(key.equals(header[i++])){
				map.put(key, pd.getStandard());  //产品规格
			}else if(key.equals(header[i++])){
				if(pd.getBoxWeight()!=null){
					map.put(key, pd.getBoxWeight().toString());				//每箱毛重（公斤）
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				if(pd.getVolume()!=null){
					map.put(key, pd.getVolume().toString());				//每箱体积（立方米)
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				if(pd.getMeterWeight()!=null){
					map.put(key, pd.getMeterWeight().toString());				//每箱立方米重量（公斤）
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				map.put(key, pd.getChargeType().getText());		//计费形式		
			}else if(key.equals(header[i++])){
				if(pd.getNewProduct()==true){
					map.put(key, "是");							//是否新品
				}
				if(pd.getNewProduct()==false){
					map.put(key, "否");
				}
				
			}else if(key.equals(header[i++])){
				if(pd.getLength()!=null){
					map.put(key, pd.getLength().toString());				//纸箱长(厘米)
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				if(pd.getWidth()!=null){
					map.put(key, pd.getWidth().toString());				//纸箱宽(厘米)"
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				if(pd.getHeight()!=null){
					map.put(key, pd.getHeight().toString());				//纸箱高(厘米)
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				if(pd.getStandardPrice()!=null){
					map.put(key, pd.getStandardPrice().toString());				//标准价
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){ 							//报价
				if(pd.getQuote()!=null){
					map.put(key, pd.getQuote().toString());
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				if(pd.getStartTime()!=null){
					map.put(key, DateFormat.date2Str(pd.getStartTime(), 2));				//启用时间
				}
			}else if(key.equals(header[i++])){
				map.put(key, pd.getUnit());				//单位
			}else if(key.equals(header[i++])){
				map.put(key, pd.getBarCode());				//条形码
			}else if(key.equals(header[i++])){
				map.put(key, pd.getRemark());				//备注
			}

		}
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Long, String> productImgMap() {
		String sql = "select id, path from info_product where path is not null ";
		Query query = em.createNativeQuery(sql);
		List<Object[]> list = query.getResultList();
		
		Map<Long, String> map = new HashMap<Long, String>();
		for(Object[] o : list){
			BigInteger b = (BigInteger)o[0];
			map.put(b.longValue(), o[1].toString().replace("\\", "/"));
		}
		return map;
	}

	@Override
	public Product getByName(String name) throws Exception {
		String hql = "from Product o where o.productName = :name";
		Query query = em.createQuery(hql);
		query.setParameter("name", name);
		if(Common.checkList(query.getResultList()))
			return (Product)query.getResultList().get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getListByNumber(String number) throws Exception {
		String sql="from Product o where o.number like :number order by o.productName ";
		Query query = em.createQuery(sql);
		query.setParameter("number", Common.SYMBOL_PERCENT + number + Common.SYMBOL_PERCENT);
		List<Product> list = query.getResultList();
		return list;
	}

}
