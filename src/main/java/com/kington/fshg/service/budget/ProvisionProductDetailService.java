package com.kington.fshg.service.budget;

import java.util.List;
import java.util.Map;

import com.kington.fshg.model.budget.ProvisionProductDetail;
import com.kington.fshg.model.budget.ProvisionProductDetailVO;
import com.kington.fshg.service.BaseService;

public interface ProvisionProductDetailService extends BaseService<ProvisionProductDetail, ProvisionProductDetailVO> {

	
	/**
	 * 根据产品ID 和 预提ID获取产品明细
	 * @param productId：产品ID
	 * @param provisionId：预提ID
	 * @return
	 * @throws Exception
	 */
	public ProvisionProductDetail getByProIdAndProId(Long productId,Long provisionId) throws Exception;
	
	/**
	 * 保存产品明细
	 * @param ppd：产品明细实体
	 * @throws Exception
	 */
	public void saveProductDetail(ProvisionProductDetail ppd) throws Exception;
	
	/**
	 * 根据预提ID获取 list集合
	 * @param provisionId：预提ID
	 * @return 产品明细集合
	 * @throws Exception
	 */
	public List<ProvisionProductDetail> getByProId(Long provisionId) throws Exception;
	
	/**
	 * 根据预提ID删除产品明细
	 * @param provisionId：预提ID
	 * @return 删除记录数
	 * @throws Exception
	 */
	public int deletProductDetail(Long provisionId) throws Exception;
	
	public int delete(Long id) throws Exception;
}
