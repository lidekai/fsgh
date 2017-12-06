package com.kington.fshg.service.system;

import java.util.List;

import com.kington.fshg.model.system.Function;
import com.kington.fshg.model.system.FunctionVO;

/**
 * 功能权限表
 * @author lijialin
 *
 */
public interface FunctionService {
	public Function saveOrUpdate(FunctionVO vo) throws Exception;

	/**
	 * 根据权限帐号查询对象
	 */
	public Function getByCde(String cde) throws Exception;
	public FunctionVO getVOByCde(String cde) throws Exception;

	/**
	 * 根据查询条件查询权限
	 */
	public List<Function> getList(FunctionVO vo) throws Exception;

	/**
	 * 根据权限代码删除对象
	 */
	public boolean delete(String cde) throws Exception;

	/**
	 * 批量删除权限
	 */
	public int clear(String ids) throws Exception;
	
	/**
	 * 校验FuncCde是否已经存在
	 * @param cde
	 * @return
	 * @throws Exception
	 */
	public boolean checkFunctionExists(String cde) throws Exception;
	
}
