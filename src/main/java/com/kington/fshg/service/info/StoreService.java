package com.kington.fshg.service.info;

import java.util.List;
import java.util.Map;

import com.kington.fshg.common.excel.vo.StoreExcelVO;
import com.kington.fshg.model.info.Store;
import com.kington.fshg.model.info.StoreVO;
import com.kington.fshg.service.BaseService;

public interface StoreService extends BaseService<Store, StoreVO> {

	/**
	 * 根据门店编码获取对象
	 * @param cde
	 * @return
	 * @throws Exception
	 */
	public Store getByCde(String cde) throws Exception;
	
	
	/**
	 * 导入门店信息
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public String doImports(List<StoreExcelVO> list) throws Exception;
	
	
	/**
	 * 根据ID删除实体对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean delete(Long id) throws Exception;
	
	/**
	 * 根据客户ID，获取该客户下的所有门店数量
	 * @param customId ： 客户实体ID
	 * @return 门店数
	 * @throws Exception
	 */
	public int getNumByCostomId(Long customId) throws Exception;
	
	/**
	 * 根据客户ID，获取该客户所有门店
	 * @param customId：客户id
	 * @return
	 * @throws Exception
	 */
	public List<Store> getByCustomId(Long customId) throws Exception;


	public void exportStore(String[] heads, Store store, Map<String, String> map);
}
