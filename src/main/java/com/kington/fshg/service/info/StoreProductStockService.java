package com.kington.fshg.service.info;

import java.util.List;
import java.util.Map;

import com.kington.fshg.common.excel.vo.StoreProductStockExcelVO;
import com.kington.fshg.model.info.StoreProductStock;
import com.kington.fshg.model.info.StoreProductStockVO;
import com.kington.fshg.service.BaseService;

public interface StoreProductStockService extends
		BaseService<StoreProductStock, StoreProductStockVO> {
	
	/**
	 * 根据ID删除实体对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean delete(Long id) throws Exception;
	/**
	 * 导入门店库存信息
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public String doImports(List<StoreProductStockExcelVO> list) throws Exception;
	
	/**
	 * 导出处理，将表头信息和传入的对象组合成MAP
	 * @param heads  : 表头信息
	 * @param o		    ：传入对象
	 * @param map	 :map集合
	 * @throws Exception
	 */
	public void export(String[] heads, StoreProductStock o, Map<String, String> map) throws Exception;


}
