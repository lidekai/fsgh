package com.kington.fshg.service.info;

import java.util.List;
import java.util.Map;

import com.kington.fshg.common.excel.vo.CustomProductExcelVO;
import com.kington.fshg.common.excel.vo.CustomExcelVO;
import com.kington.fshg.model.info.Area;
import com.kington.fshg.model.info.Custom;
import com.kington.fshg.model.info.CustomProduct;
import com.kington.fshg.model.info.CustomVO;
import com.kington.fshg.service.BaseService;

public interface CustomService extends BaseService<Custom, CustomVO> {

	/**
	 * 根据客户编码获取对象
	 * @param cde
	 * @return
	 * @throws Exception
	 */
	public Custom getByCde(String cde) throws Exception;
	
	public void saveCuspro(CustomProduct cuspro) throws Exception;
	
	public int deletCuspro(Long customId) throws Exception;
	
	public String doImpCuspro(List<CustomProductExcelVO> list) throws Exception;
	
	public Custom getByName(String name) throws Exception;
	
	public List<Long> getProductIds(Long customId) throws Exception;
	
	/**
	 * 根据ID删除对象，删除成功返回true
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean delete(Long id) throws Exception;
	
	
	/**
	 * 执行客户信息的导入
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public String importCustom(List<CustomExcelVO> list) throws Exception;
	
	public void exportCustom(String[] heads, Custom custom, Map<String, String> map);
	
}
