package com.kington.fshg.model.charge;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.VOSupport;
import com.kington.fshg.model.info.Product;
import com.kington.fshg.model.info.StoreProduct;

public class VerificationProductDetailVO extends VOSupport {
	private static final long serialVersionUID = 8632155982954790844L;

	private StoreProduct storeProduct;//所属产品
	private OutFeeVerification outFeeVerification;//所属合同外费用核销
	private Double cost;//费用
	private String remark;
	
	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				VerificationProductDetail.class.getSimpleName()) : StringUtils.EMPTY;
	}

	public StoreProduct getStoreProduct() {
		return storeProduct;
	}

	public void setStoreProduct(StoreProduct storeProduct) {
		this.storeProduct = storeProduct;
	}

	public OutFeeVerification getOutFeeVerification() {
		return outFeeVerification;
	}

	public void setOutFeeVerification(OutFeeVerification outFeeVerification) {
		this.outFeeVerification = outFeeVerification;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	

}
