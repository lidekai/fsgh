package com.kington.fshg.service.account;

import java.util.List;
import java.util.Map;

import com.kington.fshg.common.PublicType.ActType;
import com.kington.fshg.common.PublicType.IsType;
import com.kington.fshg.model.account.CheckRecordVO;
import com.kington.fshg.model.account.ReceiptBill;
import com.kington.fshg.model.account.ReceiveBill;
import com.kington.fshg.model.account.ReceiveBillVO;
import com.kington.fshg.service.BaseService;

public interface ReceiveBillService extends BaseService<ReceiveBill, ReceiveBillVO> {
	
	List<Object> countByVo(ReceiveBillVO vo, ActType act);
	
	/**
	 * 从U8导入应收单
	 * @param vo
	 * @return
	 */
	int impReceiveBillFromU8(ReceiveBillVO vo);
	
	/**
	 * 根据销售发票子表ID，获取销售发票
	 * @param autoId:发货订单字表ID
	 * @return
	 * @throws Exception
	 */
	public ReceiveBill getByAutoId(String autoId) throws Exception;
	
	public ReceiveBill getByCsbvcode(String csbvcode) throws Exception;
	
	//把需要导出的记录封装成一个集合
	List<ReceiveBill> receiveBill(ReceiveBillVO vo) throws Exception;

	void exprotReceiveBill(String[] heads, ReceiveBill receiveBill, Map<String, String> map) throws Exception;

    public Boolean createCheck(String ids) throws Exception;
    
    //保存明细核销过程
    public void saveCheckRecord(ReceiveBillVO receiveBillVO,Long receiptId,CheckRecordVO checkRecordVO) throws Exception;
    
    //取消核销记录
    public void qxCheckRecord(Long recordId) throws Exception;
    
    //账期统计
    public List<Object[]> statAccount(ReceiveBillVO vo) throws Exception;
    public void exprotStatAccount(String[] heads, Object[] object, Map<String, String> map) throws Exception;

    public Integer autoHx(List<ReceiveBill> receiveBillList, List<ReceiptBill> receiptBillList, IsType isType) throws Exception;
    
	List<ReceiveBill> getListByIds(String ids) throws Exception;
}
