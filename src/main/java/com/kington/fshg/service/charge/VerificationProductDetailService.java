package com.kington.fshg.service.charge;

import java.util.List;

import com.kington.fshg.model.charge.VerificationProductDetail;
import com.kington.fshg.model.charge.VerificationProductDetailVO;
import com.kington.fshg.service.BaseService;

public interface VerificationProductDetailService extends BaseService<VerificationProductDetail, VerificationProductDetailVO> {

	/**
	 * 根据核销ID，删除核销产品明细
	 * @param verId	： 合同外费用核销ID
	 * @throws Exception
	 */
	public void deleteProductDetail(Long verId) throws Exception;
	
	
	/**
	 * 根据核销ID，获取核销产品明细
	 * @param verId ： 合同外费用核销ID
	 * @return
	 * @throws Exception
	 */
	public List<VerificationProductDetail> getByVerId(Long verId) throws Exception;
	
	/**
	 * 根据ID删除实体
	 * @param detailId：核销产品明细ID
	 * @return
	 * @throws Exception
	 */
	public int deleteDetail(Long detailId) throws Exception;
}
