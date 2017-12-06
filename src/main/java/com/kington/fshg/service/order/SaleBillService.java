package com.kington.fshg.service.order;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kington.fshg.model.order.SaleBill;
import com.kington.fshg.model.order.SaleBillVO;
import com.kington.fshg.service.BaseService;

public interface SaleBillService extends BaseService<SaleBill, SaleBillVO> {

	/**
	 * 从U8导入发票
	 * @param vo
	 * @return
	 */
	int impSaleBillFromU8(SaleBillVO vo);
	
	/**
	 * 根据销售发票子表ID，获取销售发票
	 * @param autoId:发货订单字表ID
	 * @return
	 * @throws Exception
	 */
	public SaleBill getByAutoId(String autoId) throws Exception;
	
	//吧需要导出的记录封装成一个集合
	List<SaleBill> saleBill(SaleBillVO vo) throws Exception;

	void exprotSaleBill(String[] heads, SaleBill saleBill, Map<String, String> map) throws Exception;

	List<Object> countByVo(SaleBillVO vo);
	
	//查询年初到当月的发票额实收本币金额总数
	public Double getCountPrice(String customCde, Date date);
	
	//根据时间段查询发票额实收本币金额总数
	public Double getCountPriceByTime(String customCde, String startTime, String endTime);
	
	/**
	 * 更新发票额是否返利
	 * @param id
	 * @param isRebate
	 * @return
	 * @throws Exception
	 */
	public boolean updateRebate(Long id, String isRebate) throws Exception;

}
