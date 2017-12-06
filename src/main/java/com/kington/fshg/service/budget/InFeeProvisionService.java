package com.kington.fshg.service.budget;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kington.fshg.model.budget.InFeeProvision;
import com.kington.fshg.model.budget.InFeeProvisionVO;
import com.kington.fshg.service.BaseService;

public interface InFeeProvisionService extends BaseService<InFeeProvision, InFeeProvisionVO> {

	
	/**
	 * 根据编码获取对象
	 * @param code ：预提编号
	 * @return 实体对象
	 * @throws Exception
	 */
	public InFeeProvision getByCode(String code) throws Exception;
	
	/**
	 * 根据ID删除对象
	 * @param id
	 * @return 
	 * @throws Exception
	 */
	public boolean delete(Long id) throws Exception;
	
	/**
	 * 根据客户id生成当月预提(已弃用)
	 */
	public int createProvision(String ids) throws Exception;
	
	/**
	 * 根据客户id生成当月预提
	 */
	public int createProvisionNew(String ids, InFeeProvisionVO vo) throws Exception;
	
	public InFeeProvision getByCusAndMon(Long customId, Date date);
	
	/**
	 * 计算费用总和
	 */
	public void countInFee(InFeeProvision inFeeProvision);
	
	public Map<Long, List<InFeeProvision>> getInFeeMap(InFeeProvisionVO vo) throws Exception;
	
	//吧需要导出的记录封装成一个集合
	public List<InFeeProvision> inFeeProvision(InFeeProvisionVO vo) throws Exception;

	public void exprotInFeeProvision(String[] heads, InFeeProvision ifp, Map<String, String> map) throws Exception;
}
