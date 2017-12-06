package com.kington.fshg.model.budget;

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
 *	合同外费用项目预提产品明细信息表 
 *
 */

@Entity
@Table(name = "bud_provision_product_detail")
public class ProvisionProductDetail extends POSupport {
	private static final long serialVersionUID = 4510887054919152416L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="store_product_id")
	private StoreProduct storeProduct;//门店存货
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "provision_id")
	private OutFeeProvision provision;//所属合同外费用预提
	
	@Column(name = "cost")
	private Float cost;//费用
	
	@Column(length = 500)
	private String remark;//备注
	
	@Override
	public String getKey() {
		return  Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				ProvisionProductDetail.class.getSimpleName()) : StringUtils.EMPTY ;	
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
