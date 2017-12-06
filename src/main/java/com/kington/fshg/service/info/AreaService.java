package com.kington.fshg.service.info;

import java.util.List;
import java.util.Map;

import com.kington.fshg.common.excel.vo.AreaExcelVO;
import com.kington.fshg.model.info.Area;
import com.kington.fshg.model.info.AreaVO;
import com.kington.fshg.service.BaseService;

public interface AreaService extends BaseService<Area, AreaVO> {
	
	public int delete(Long id) throws Exception;
	/**
	 * 产品分类导入
	 */
	public String doImports(List<AreaExcelVO> list) throws Exception;
	
	public Area getByName (String areaName) throws Exception;
	
	public Area getByParentName(String parentName, String areaName) throws Exception;
	
	/**
	 * 获取叶子节点
	 * @return
	 * @throws Exception
	 */
	public List<Area> getLeafNode() throws Exception;
	
	/**
	 * 获取一级节点
	 * @return
	 * @throws Exception
	 */
	public List<Area> getFristNode() throws Exception;
	
	public List<Area> getByParentId(Long parentId) throws Exception;
	public List<Area> area(AreaVO vo);
	public void exportArea(String[] heads, Area area, Map<String, String> map);
}
