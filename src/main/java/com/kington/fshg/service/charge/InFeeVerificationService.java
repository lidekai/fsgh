package com.kington.fshg.service.charge;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kington.fshg.model.charge.InFeeVerification;
import com.kington.fshg.model.charge.InFeeVerificationVO;
import com.kington.fshg.service.BaseService;

public interface InFeeVerificationService extends BaseService<InFeeVerification, InFeeVerificationVO> {

	
	/**
	 * 生成核销编号
	 * @return
	 * @throws Exception
	 */
	public String createCode() throws Exception;
	
	/**
	 * 计算费用总和
	 * @param po
	 * @throws Exception
	 */
	public void countTotalFee(InFeeVerification po) throws Exception;
	
	
	public Map<Long, List<InFeeVerification>> getInFeeVerMap(InFeeVerificationVO vo) throws Exception;
	
	//生成U8收款单  1：生成成功;2:记录所在月份已经结账;3:插入信息失败;4:结算方式编码不存在;5:收款银行科目不存在;6:对方科目不存在
	public String expOrderToU8(Date updateTime,String customCde,String summary,
			Double total,String maker,String backItem,String customItem,String cSSCode) throws Exception;

	public List<InFeeVerification> inFeeVerification(InFeeVerificationVO vo);

	public void exportInFeeVerification(String[] heads, InFeeVerification ifv, Map<String, String> map);
}
