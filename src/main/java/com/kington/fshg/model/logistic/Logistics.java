package com.kington.fshg.model.logistic;

import javax.persistence.*;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.ApproveState;
import com.kington.fshg.common.PublicType.ChargeType;
import com.kington.fshg.model.POSupport;
import com.kington.fshg.model.info.Custom;
import com.kington.fshg.model.info.Product;
import com.kington.fshg.model.order.DeliverOrder;

/**
 * 物流费用信息表
 *
 */
@Entity
@Table(name = "logistic_logistic")
public class Logistics extends POSupport {
	private static final long serialVersionUID = -2731172098622438397L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="delever_order_id")
	private DeliverOrder deliverOrder;//U8发货单 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="custom_id")
	private Custom custom;//所属客户
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;//所属产品
	
	@Column
	private Double logWeight;//货物重量（公斤）
	
	@Column
	private Double logVolume;//货物体积（立方米）
	
	@Column
	private Double logPrice;//单价
	
	@Column
	private Double freight;//运费
	
	@Column
	private Double insuranceFee;//保险金额
	
	@Column
	private Double logDeliverFee;//配送费
	
	@Column
	private Double freightTotal;//运费合计
	
	@Column(length = 20)
	@Enumerated(EnumType.STRING)
	private ApproveState approveState = ApproveState.DSP;//审批状态
	
	@Column(length = 50)
	@Enumerated(EnumType.STRING)
	private ChargeType chargeType;//计费形式
	
	@Column
	private Double deliverCost;//发货单实收本币金额
	
	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				Logistics.class.getSimpleName()) : StringUtils.EMPTY;
	}


	public Custom getCustom() {
		return custom;
	}


	public void setCustom(Custom custom) {
		this.custom = custom;
	}

	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

	public Double getFreight() {
		return freight;
	}

	public void setFreight(Double freight) {
		this.freight = freight;
	}

	public Double getInsuranceFee() {
		return insuranceFee;
	}

	public void setInsuranceFee(Double insuranceFee) {
		this.insuranceFee = insuranceFee;
	}

	public Double getFreightTotal() {
		return freightTotal;
	}

	public void setFreightTotal(Double freightTotal) {
		this.freightTotal = freightTotal;
	}

	public DeliverOrder getDeliverOrder() {
		return deliverOrder;
	}


	public void setDeliverOrder(DeliverOrder deliverOrder) {
		this.deliverOrder = deliverOrder;
	}


	public ApproveState getApproveState() {
		return approveState;
	}


	public void setApproveState(ApproveState approveState) {
		this.approveState = approveState;
	}


	public Double getLogWeight() {
		return logWeight;
	}


	public void setLogWeight(Double logWeight) {
		this.logWeight = logWeight;
	}


	public Double getLogVolume() {
		return logVolume;
	}


	public void setLogVolume(Double logVolume) {
		this.logVolume = logVolume;
	}


	public Double getLogPrice() {
		return logPrice;
	}


	public void setLogPrice(Double logPrice) {
		this.logPrice = logPrice;
	}


	public ChargeType getChargeType() {
		return chargeType;
	}


	public void setChargeType(ChargeType chargeType) {
		this.chargeType = chargeType;
	}


	public Double getLogDeliverFee() {
		return logDeliverFee;
	}


	public void setLogDeliverFee(Double logDeliverFee) {
		this.logDeliverFee = logDeliverFee;
	}


	public Double getDeliverCost() {
		return deliverCost;
	}


	public void setDeliverCost(Double deliverCost) {
		this.deliverCost = deliverCost;
	}
	
}
