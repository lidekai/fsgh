package com.kington.fshg.service.logistic;

import java.util.List;
import java.util.Map;

import com.kington.fshg.model.logistic.OtherLogistics;
import com.kington.fshg.model.logistic.OtherLogisticsVO;
import com.kington.fshg.service.BaseService;

public interface OtherLogisticsService extends
		BaseService<OtherLogistics, OtherLogisticsVO> {

	//根据客户id得到当月其他费用项
	public List<OtherLogistics> getMonByCustom(Long customId) throws Exception;
	
	//统计销售额
	public Double getMonCost(String createStartTime, String createEndTime) throws Exception;
	
	//当月分摊
	public int ftCost(OtherLogisticsVO vo) throws Exception;
	
	/**
	 * 导出其他物流费用，将表头信息和传入的对象组合成map
	 * @param heads:表头信息
	 * @param obj：传入对象
	 * @param map ：map集合
	 * @throws Exception
	 */
	public void export(String[] heads, OtherLogistics o, Map<String,String> map) throws Exception;
}
