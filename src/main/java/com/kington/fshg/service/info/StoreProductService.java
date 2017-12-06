package com.kington.fshg.service.info;

import java.util.List;
import java.util.Map;

import com.kington.fshg.common.excel.vo.StoreProductExcelVO;
import com.kington.fshg.model.info.StoreProduct;
import com.kington.fshg.model.info.StoreProductVO;
import com.kington.fshg.service.BaseService;

public interface StoreProductService extends
		BaseService<StoreProduct, StoreProductVO> {
	
	public StoreProduct getByStoPro(Long storeId,Long productId);
	
	/**
	 * 根据ID删除实体对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean delete(Long id) throws Exception;
	
	/**
	 * 导入门店信息
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public String doImports(List<StoreProductExcelVO> list) throws Exception;
	
	public StoreProduct getByStoNamePro(String customName, String storeName, String stockCde);

	public void exportStoreProduct(String[] heads, StoreProduct sp, Map<String, String> map);
}
