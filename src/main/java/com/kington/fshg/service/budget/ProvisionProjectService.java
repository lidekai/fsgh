package com.kington.fshg.service.budget;

import java.util.List;
import java.util.Map;

import com.kington.fshg.model.budget.ProvisionProject;
import com.kington.fshg.model.budget.ProvisionProjectVO;
import com.kington.fshg.service.BaseService;

public interface ProvisionProjectService extends BaseService<ProvisionProject, ProvisionProjectVO> {

	/**
	 * 根据对象ID删除对象
	 * @param id 对象ID
	 * @return true OR false
	 * @throws Exception
	 */
	public boolean delete(Long id) throws Exception;

	public List<ProvisionProject> provisionProject(ProvisionProjectVO vo);

	public void exportProvisionProject(String[] heads, ProvisionProject pp, Map<String, String> map);
	
	public ProvisionProject getByName(String name) throws Exception;
}
