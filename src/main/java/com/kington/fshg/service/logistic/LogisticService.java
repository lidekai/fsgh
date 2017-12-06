package com.kington.fshg.service.logistic;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kington.fshg.model.logistic.Logistics;
import com.kington.fshg.model.logistic.LogisticsVO;
import com.kington.fshg.service.BaseService;

public interface LogisticService extends BaseService<Logistics, LogisticsVO> {
	
	//生成物流费用
	public int createLogistic(Date orderStartTime, Date orderEndTime) throws Exception ;
	//配送费划分
	public void initLogDeliverFee(Date orderStartTime, Date orderEndTime) throws Exception ;
	//物流费用明细表
	public List<Object[]> logisticDetail(LogisticsVO vo) throws Exception;
	//物流费用分析表
	public List<Object[]> logisticAnalysis(LogisticsVO vo) throws Exception;
	
	/**
	 * 导出处理，将表头信息和传入的对象组合成MAP
	 * @param heads : 表头信息
	 * @param obj	：传入对象
	 * @param map	:map集合
	 * @throws Exception
	 */
	public void exportDetail(String[] heads, Object[] obj, Map<String,String> map) throws Exception;
	
	/**
	 * 导出物流费分析，将表头信息和传入的对象组合成map
	 * @param heads:表头信息
	 * @param obj：传入对象
	 * @param map ：map集合
	 * @throws Exception
	 */
	public void exportAnalysis(String[] heads, Object[] obj, Map<String,String> map) throws Exception;
	
	/**
	 * 导出物流费用，将表头信息和传入的对象组合成map
	 * @param heads:表头信息
	 * @param obj：传入对象
	 * @param map ：map集合
	 * @throws Exception
	 */
	public void exportLogistic(String[] heads, Logistics logistics, Map<String,String> map) throws Exception;
	
	
	public int deleteLogistic(LogisticsVO vo)throws Exception;
}
