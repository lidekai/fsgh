package com.kington.fshg.service;

import com.jtframework.websupport.pagination.PageList;
import com.kington.fshg.model.POSupport;
import com.kington.fshg.model.VOSupport;

/**
 * 基本数据访问接口定义
 * 
 * @author lijialin
 */
public interface BaseService<PO extends POSupport, VO extends VOSupport> {

	/**
	 * 新增或更新实体对象
	 * 
	 * @param entity
	 * @return 已新增或更新的实体对象
	 */
	public PO saveOrUpdate(VO vo) throws Exception;

	/**
	 * 根据实体的主键ID查询实体
	 * 
	 * @param id
	 * @return 如果不存在则返回Null
	 */
	public PO getById(Long id) throws Exception;
	public VO getVOById(Long id) throws Exception;

	/**
	 * 根据对象及ID获取相应的对象，没有值时返回NULL
	 * 
	 * @param <T>
	 * @param clazz
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public <T> T getById(Class<T> clazz, Long id) throws Exception;

	/**
	 * 根据条件查询列表数据
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public PageList<PO> getPageList(VO vo) throws Exception;

	/**
	 * 一次删除多个实体对象
	 * 
	 * @param ids
	 * @return
	 */
	public int clear(String ids) throws Exception;
}
