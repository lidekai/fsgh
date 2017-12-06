package com.kington.fshg.service.system;

import java.util.List;

import com.kington.fshg.common.PublicType.DictType;
import com.kington.fshg.model.system.Dict;
import com.kington.fshg.model.system.DictVO;
import com.kington.fshg.service.BaseService;

public interface DictService extends BaseService<Dict,DictVO> {
	public List<Dict> getList(DictVO vo) throws Exception;
	/**
	 * 通过类别获取列表,只显示USE状态的数据
	 * @return
	 */
	public List<Dict> getByType(DictType type) throws Exception;
	
	/**
	 * 根据名字返回Dict
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Dict getByName(String name)throws Exception;
	
	public boolean checkExists(String name) throws Exception;
}