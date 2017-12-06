package com.kington.fshg.model.charge;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.POSupport;
import com.kington.fshg.model.info.Product;
import com.kington.fshg.model.info.StoreProduct;

/**
 *	合同外费用项目核销产品明细表 
 *
 */
@Entity
@Table(name ="charge_verification_product_detail")
public class VerificationProductDetail extends POSupport {
	private static final long serialVersionUID = 2280842101468973163L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="store_product_id")
	private StoreProduct storeProduct;//所属产品
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="verificationId")
	private OutFeeVerification outFeeVerification;//所属合同外费用核销
	
	@Column
	private Double cost;//费用
	
	@Column(length = 255)
	private String remark;//备注
	
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
