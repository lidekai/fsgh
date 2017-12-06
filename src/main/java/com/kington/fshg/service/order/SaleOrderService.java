package com.kington.fshg.service.order;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kington.fshg.model.order.SaleOrder;
import com.kington.fshg.model.order.SaleOrderVO;
import com.kington.fshg.service.BaseService;

public interface SaleOrderService extends BaseService<SaleOrder, SaleOrderVO> {
	public SaleOrder getByAutoId(String autoId);
	
	public int getSaleOrderFromU8(SaleOrderVO vo);
	
	//查询年初到当月的订单本币金额总数
	public Double getOrderCount(String customCde, Date date);
	
	public List<Object> countByVo(SaleOrderVO vo);
	
	/**
	 * 导出处理，将表头信息和传入的对象组合成MAP
	 * @param heads  : 表头信息
	 * @param o		    ：传入对象
	 * @param map	 :map集合
	 * @throws Exception
	 */
	public void exportSaleOrder(String[] heads, SaleOrder po, Map<String, String> map) throws Exception;


}
