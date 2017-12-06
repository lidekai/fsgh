package com.kington.fshg.service.info;

import java.util.List;
import java.util.Map;

import com.kington.fshg.common.excel.vo.CustomsTypeExcelVO;
import com.kington.fshg.model.info.CustomsType;
import com.kington.fshg.model.info.CustomsTypeVO;
import com.kington.fshg.service.BaseService;

public interface CustomsTypeService extends BaseService<CustomsType, CustomsTypeVO> {

	/**
	 * 根据客户分类名称获取对象
	 * @param customTypeName
	 * @return
	 * @throws Exception
	 */
	public CustomsType getByName(String customTypeName) throws Exception;
	
	/**
	 * 客户分类信息导入
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public String doImports(List<CustomsTypeExcelVO> list) throws Exception;
	
	public int delete(Long id) throws Exception;

	public List<CustomsType> customsType(CustomsTypeVO vo);

	public void exportCustomsType(String[] heads, CustomsType ct, Map<String, String> map);
	
	public List<CustomsType> getLeafNode();
}
