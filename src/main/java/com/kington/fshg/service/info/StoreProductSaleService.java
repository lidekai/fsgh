package com.kington.fshg.service.info;

import java.util.List;
import java.util.Map;

import com.kington.fshg.common.excel.vo.StoreProductSaleExcelVO;
import com.kington.fshg.model.info.StoreProductSale;
import com.kington.fshg.model.info.StoreProductSaleVO;
import com.kington.fshg.service.BaseService;

public interface StoreProductSaleService extends
		BaseService<StoreProductSale, StoreProductSaleVO> {

	/**
	 * 根据ID删除实体对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean delete(Long id) throws Exception;
	
	/**
	 * 导入门店销售信息
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public String doImports(List<StoreProductSaleExcelVO> list) throws Exception;
	
	/**
	 * 导出处理，将表头信息和传入的对象组合成MAP
	 * @param heads  : 表头信息
	 * @param o		    ：传入对象
	 * @param map	 :map集合
	 * @throws Exception
	 */
	public void export(String[] heads, StoreProductSale o, Map<String, String> map) throws Exception;
	
	public List<Object[]> compare(StoreProductSaleVO vo) throws Exception;
	
	public void exportCompare(String[] heads, Object[] o, Map<String, String> map) throws Exception;
	
	public List<Object[]> proportion(StoreProductSaleVO vo) throws Exception;
	
	public void exportProportion(String[] heads, Object[] o, Map<String, String> map) throws Exception;
}
