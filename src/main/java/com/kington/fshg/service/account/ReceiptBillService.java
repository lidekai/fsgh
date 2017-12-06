package com.kington.fshg.service.account;

import java.util.List;
import java.util.Map;

import com.kington.fshg.model.account.ReceiptBill;
import com.kington.fshg.model.account.ReceiptBillVO;
import com.kington.fshg.service.BaseService;

public interface ReceiptBillService extends BaseService<ReceiptBill, ReceiptBillVO> {

	List<Object> countByVo(ReceiptBillVO vo);
	
	/**
	 * 从U8导入收款单
	 * @param vo
	 * @return
	 */
	int impReceiptBillFromU8(ReceiptBillVO vo);
	
	//把需要导出的记录封装成一个集合
	List<ReceiptBill> receiptBill(ReceiptBillVO vo) throws Exception;

	void exprotReceiptBill(String[] heads, ReceiptBill receiptBill, Map<String, String> map) throws Exception;
	
	List<ReceiptBill> getListByIds(String ids) throws Exception;
}
