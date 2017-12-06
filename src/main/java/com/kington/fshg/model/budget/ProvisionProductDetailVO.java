package com.kington.fshg.model.budget;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.VOSupport;
import com.kington.fshg.model.info.Product;
import com.kington.fshg.model.info.StoreProduct;

public class ProvisionProductDetailVO extends VOSupport {
	private static final long serialVersionUID = -4391044908303703323L;

	private StoreProduct storeProduct;
	private OutFeeProvision provision;
	private Float cost;
	private String remark;//备注
	
	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(), 
				ProvisionProductDetail.class.getSimpleName()) : StringUtils.EMPTY;
	}

	public StoreProduct getStoreProduct() {
		return storeProduct;
	}

	public void setStoreProduct(StoreProduct storeProduct) {
		this.storeProduct = storeProduct;
	}

	public OutFeeProvision getProvision() {
		return provision;
	}

	public void setProvision(OutFeeProvision provision) {
		this.provision = provision;
	}

	public Float getCost() {
		return cost;
	}

	public void setCost(Float cost) {
		this.cost = cost;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
