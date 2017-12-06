package com.kington.fshg.service.charge;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.jtframework.websupport.pagination.PageList;
import com.kington.fshg.model.charge.VerificationProductDetail;
import com.kington.fshg.model.charge.VerificationProductDetailVO;
import com.kington.fshg.service.BaseServiceImpl;

public class VerificationProductDetailServiceImpl extends
		BaseServiceImpl<VerificationProductDetail, VerificationProductDetailVO> implements VerificationProductDetailService {
	private static final long serialVersionUID = 7444757007255426621L;

	@Override
	protected String getQueryStr(VerificationProductDetailVO vo)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setQueryParm(Query query, VerificationProductDetailVO vo)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void switchVO2PO(VerificationProductDetailVO vo,
			VerificationProductDetail po) throws Exception {
		if(po == null)
			po =new VerificationProductDetail();
		if(vo.getStoreProduct() != null)
			po.setStoreProduct(vo.getStoreProduct());
		if(vo.getOutFeeVerification() != null)
			po.setOutFeeVerification(vo.getOutFeeVerification());
		if(vo.getCost() != null)
			po.setCost(vo.getCost());
		if(StringUtils.isNotBlank(vo.getRemark()))
			po.setRemark(vo.getRemark());
		
	}

	@Override
	public void deleteProductDetail(Long verId) throws Exception {
		String hql = "delete from charge_verification_product_detail where verificationId = "+ verId;
		Query query =em.createNativeQuery(hql);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VerificationProductDetail> getByVerId(Long verId)
			throws Exception {
		String sql = "from VerificationProductDetail o where o.outFeeVerification.id = " + verId;
		Query query = em.createQuery(sql);
		return query.getResultList();
	}

	@Override
	public int deleteDetail(Long detailId) throws Exception {
		String hql ="delete from charge_verification_product_detail where id = "+ detailId ;
		Query query = em.createNativeQuery(hql);
		return query.executeUpdate();
	}


}
