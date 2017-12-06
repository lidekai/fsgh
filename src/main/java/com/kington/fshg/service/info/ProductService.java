package com.kington.fshg.service.info;

import java.util.List;
import java.util.Map;

import com.kington.fshg.common.excel.vo.ProductExcelVO;
import com.kington.fshg.model.info.Product;
import com.kington.fshg.model.info.ProductVO;
import com.kington.fshg.service.BaseService;

public interface ProductService extends BaseService<Product, ProductVO> {
	
	public Product getByCde(String stockCde) throws Exception;
	public Product getByName(String name) throws Exception;
	/**
	 * 产品导入
	 */
	public String doImports(List<ProductExcelVO> list) throws Exception;
	
	public Map<String,List<ProductVO>> getProductMap() throws Exception;
	
	/**
	 * 根据客户ID，获取该客户所有产品
	 * @param customId：客户ID
	 * @return List
	 * @throws Exception
	 */
	public List<Product> getListByCustomId(Long customId) throws Exception;
	
	/**
	 * 更新是否新货
	 * @param id
	 * @param isType
	 * @return
	 */
	public boolean updateNewProduct(long id, String isNewProduct);
	public void exportProduct(String[] heads, Product product, Map<String, String> map);
	
	public Map<Long, String> productImgMap();
	
	public List<Product> getListByNumber(String number) throws Exception;
}
