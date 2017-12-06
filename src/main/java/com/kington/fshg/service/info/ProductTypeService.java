package com.kington.fshg.service.info;

import java.util.List;
import java.util.Map;

import com.kington.fshg.common.excel.vo.ProductTypeExcelVO;
import com.kington.fshg.model.info.ProductType;
import com.kington.fshg.model.info.ProductTypeVO;
import com.kington.fshg.service.BaseService;

public interface ProductTypeService extends BaseService<ProductType, ProductTypeVO> {
	
	public int delete(Long id) throws Exception;
	/**
	 * 产品分类导入
	 */
	public String doImports(List<ProductTypeExcelVO> list) throws Exception;
	
	public ProductType getByName (String productTypeName) throws Exception;
	
	public List<ProductType> getChildList() throws Exception;
	public List<ProductType> productType(ProductTypeVO vo);
	public void exportProductType(String[] heads, ProductType pt, Map<String, String> map);
}
