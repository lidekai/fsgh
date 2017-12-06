package com.kington.fshg.service.account;

import java.util.List;
import java.util.Map;

import com.kington.fshg.model.account.CheckBill;
import com.kington.fshg.model.account.CheckBillVO;
import com.kington.fshg.service.BaseService;

public interface CheckBillService extends BaseService<CheckBill, CheckBillVO> {
	
	//待核单编号格式：YCB + 年月（6位）+ 5位流水号
	public String getCode();
	
	List<Object> countByVo(CheckBillVO vo);
	
	//把需要导出的记录封装成一个集合
	List<CheckBill> checkBill(CheckBillVO vo) throws Exception;
	
	void exprotCheckBill(String[] heads, CheckBill checkBill, Map<String, String> map) throws Exception;
	
	//根据明细统计表头数据信息
	void sumCount(Long checkBillId);
	
	//待核单表头余额表
	List<Object[]> statBill(CheckBillVO vo);
	
	void exprotStatBill(String[] heads, Object[] object, Map<String, String> map) throws Exception;
	
	void updateState(Long checkBillId) throws Exception;

	public int delete(String ids);

}
