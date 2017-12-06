package com.kington.fshg.service.order;

import java.util.List;
import java.util.Map;

import com.kington.fshg.model.order.Transfer;
import com.kington.fshg.model.order.TransferVO;
import com.kington.fshg.service.BaseService;

public interface TransferService extends BaseService<Transfer, TransferVO> {

	/**
	 * 从U8中导入仓库调拨单
	 * @param beginTime：调拨日期时间段起始日期
	 * @param endTime：调拨日期时间段结束日期
	 * @return
	 * @throws Exception
	 */
	public int getTransferFromU8(TransferVO vo) throws Exception;
	
	/**
	 * 根据调拨子表ID，获取实体
	 * @param autoId：调拨子表ID
	 * @return
	 * @throws Exception
	 */
	public Transfer getByAutoId(String autoId) throws Exception;
	
	//获取当月的调拨费
	public Double getTransferCost() throws Exception;
	
	public Double countByVo(TransferVO vo);
	
	/**
	 * 导出处理，将表头信息和传入的对象组合成MAP
	 * @param heads  : 表头信息
	 * @param o		    ：传入对象
	 * @param map	 :map集合
	 * @throws Exception
	 */

	public void exportTransfer(String[] heads, Transfer tf, Map<String, String> map) throws Exception;
	
}
