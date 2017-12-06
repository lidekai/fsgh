package com.kington.fshg.service.budget;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.kington.fshg.model.budget.ProvisionProductDetail;
import com.kington.fshg.model.budget.ProvisionProductDetailVO;
import com.kington.fshg.service.BaseServiceImpl;

public class ProvisionProductDetailServiceImpl extends BaseServiceImpl<ProvisionProductDetail, ProvisionProductDetailVO>
		implements ProvisionProductDetailService {
	private static final long serialVersionUID = -1658800904971168654L;

	@Override
	protected String getQueryStr(ProvisionProductDetailVO vo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setQueryParm(Query query, ProvisionProductDetailVO vo)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void switchVO2PO(ProvisionProductDetailVO vo,
			ProvisionProductDetail po) throws Exception {
		if(po == null)
			po = new ProvisionProductDetail();
		if(vo.getStoreProduct() != null)
			po.setStoreProduct(vo.getStoreProduct());
		if(vo.getProvision() != null)
			po.setProvision(vo.getProvision());
		if(vo.getCost() != null)
			po.setCost(vo.getCost());
		if(StringUtils.isNotBlank(vo.getRemark()))
			po.setRemark(vo.getRemark());
	}

	@Override
	public ProvisionProductDetail getByProIdAndProId(Long productId,
			Long provisionId) throws Exception {
		String sql="from ProvisionProductDetail o where o.product.id =:productId and o.provision.id =:provisionId";
		Query query = em.createQuery(sql);
		query.setParameter("productId", productId);
		query.setParameter("provisionId", provisionId);
		List<ProvisionProductDetail> list = query.getResultList();
		if(list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	@Override
	public void saveProductDetail(ProvisionProductDetail ppd) throws Exception {
		this.merge(ppd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProvisionProductDetail> getByProId(Long provisionId)
			throws Exception {
		String sql="from ProvisionProductDetail o where o.provision.id = "+ provisionId ;
		Query query = em.createQuery(sql);
		return query.getResultList();
	}

	@Override
	public int deletProductDetail(Long provisionId) throws Exception {
		String hql = "delete from bud_provision_product_detail where provision_id = " + provisionId;
		Query query = em.createNativeQuery(hql);
		return query.executeUpdate();
	}

	@Override
	public int delete(Long id) throws Exception {
		String hql = "delete from bud_provision_product_detail where id = " + id;
		Query query = em.createNativeQuery(hql);
		return query.executeUpdate();
	}

}
