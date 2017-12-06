package com.kington.fshg.service.charge;

import java.util.List;
import java.util.Map;

import com.kington.fshg.model.charge.OutFeeVerification;
import com.kington.fshg.model.charge.OutFeeVerificationVO;
import com.kington.fshg.service.BaseService;

public interface OutFeeVerificationService extends BaseService<OutFeeVerification, OutFeeVerificationVO> {

	/**
	 * 自动生成核销编码
	 * @return
	 * @throws Exception
	 */
	public String createCode() throws Exception;
	
	/**
	 * 根据ID删除实体
	 * @param id
	 * @throws Exception
	 */
	public int delete(Long id) throws Exception;
	
	public Map<Long, List<OutFeeVerification>> getOutFeeVerMap(OutFeeVerificationVO vo) throws Exception;

	public void exportOutFeeVer(String[] heads, OutFeeVerification ofv, Map<String, String> map);
}
