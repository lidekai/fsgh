package com.kington.fshg.service.order;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kington.fshg.model.order.DeliverOrder;
import com.kington.fshg.model.order.DeliverOrderVO;
import com.kington.fshg.service.BaseService;

public interface DeliverOrderService extends BaseService<DeliverOrder, DeliverOrderVO> {

	/**
	 * 从U8中导入发货订单
	 * @param orderBeginTime
	 * @param orderEndTime
	 * @return
	 * @throws Exception
	 */
	public int impDeliveOrderFromU8(DeliverOrderVO vo) throws Exception;
	
	/**
	 * 根据发货订单子表ID，获取发货订单
	 * @param autoId:发货订单字表ID
	 * @return
	 * @throws Exception
	 */
	public DeliverOrder getByAutoId(String autoId) throws Exception;
	
	/**
	 * 更新发货订单是否返利
	 * @param id
	 * @param isRebate
	 * @return
	 * @throws Exception
	 */
	public boolean updateDeliver(Long id, String isRebate) throws Exception;
	
	//查询年初到当月的发货单实收本币金额总数
	public Double getOrderCount(String customCde, Date date, Boolean isRebate);
	
	//根据时间段查询发货单实收本币金额总数
	public Double getOrderCountByTime(String customCde,String startTime, String endTime, Boolean isRebate);
	
	public List<Object> countByVo(DeliverOrderVO vo);
	
	//实时查询U8销售类型
	public List<DeliverOrderVO> getSaleTypeList() throws Exception;
	
	
	/**
	 * 导出处理，将表头信息和传入的对象组合成MAP
	 * @param heads  : 表头信息
	 * @param o		    ：传入对象
	 * @param map	 :map集合
	 * @throws Exception
	 */
	public void exportDeliverOrder(String[] heads, DeliverOrder deliver, Map<String, String> map) throws Exception;
}
