package com.kington.fshg.model.logistic;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.ApproveState;
import com.kington.fshg.common.PublicType.ChargeType;
import com.kington.fshg.model.VOSupport;
import com.kington.fshg.model.info.Custom;
import com.kington.fshg.model.info.Product;
import com.kington.fshg.model.order.DeliverOrder;

public class LogisticsVO extends VOSupport {
	private static final long serialVersionUID = 1225932221767200795L;

	private DeliverOrder deliverOrder;//U8发货单 
	private Custom custom;//所属客户
	private Product product;//所属产品
	private Float logWeight;//货物重量（公斤）
	private Float logVolume;//货物体积（立方米）
	private Float logPrice;//单价
	private Float freight;//运费
	private Float insuranceFee;//保险金额
	private Float freightTotal;//运费合计
	private String orderStartTime;
	private String orderEndTime;
	private ApproveState approveState;
	private ChargeType chargeType;//计费形式
	private Double logDeliverFee;//配送费
	private Double deliverCost;//发货单实收本币金额
	private List<Long> areaIds = new ArrayList<Long>();
	private List<Long> parentAreaIds = new ArrayList<Long>();
	
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

	public Float getFreight() {
		return freight;
	}

	public void setFreight(Float freight) {
		this.freight = freight;
	}

	public Float getInsuranceFee() {
		return insuranceFee;
	}

	public void setInsuranceFee(Float insuranceFee) {
		this.insuranceFee = insuranceFee;
	}

	public Float getFreightTotal() {
		return freightTotal;
	}

	public void setFreightTotal(Float freightTotal) {
		this.freightTotal = freightTotal;
	}

	public String getOrderStartTime() {
		return orderStartTime;
	}


	public void setOrderStartTime(String orderStartTime) {
		this.orderStartTime = orderStartTime;
	}


	public String getOrderEndTime() {
		return orderEndTime;
	}


	public void setOrderEndTime(String orderEndTime) {
		this.orderEndTime = orderEndTime;
	}


	public ApproveState getApproveState() {
		return approveState;
	}


	public void setApproveState(ApproveState approveState) {
		this.approveState = approveState;
	}


	public DeliverOrder getDeliverOrder() {
		return deliverOrder;
	}


	public void setDeliverOrder(DeliverOrder deliverOrder) {
		this.deliverOrder = deliverOrder;
	}


	public ChargeType getChargeType() {
		return chargeType;
	}


	public void setChargeType(ChargeType chargeType) {
		this.chargeType = chargeType;
	}


	public Float getLogWeight() {
		return logWeight;
	}


	public void setLogWeight(Float logWeight) {
		this.logWeight = logWeight;
	}


	public Float getLogVolume() {
		return logVolume;
	}


	public void setLogVolume(Float logVolume) {
		this.logVolume = logVolume;
	}


	public Float getLogPrice() {
		return logPrice;
	}


	public void setLogPrice(Float logPrice) {
		this.logPrice = logPrice;
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
	

	public List<Long> getAreaIds() {
		return areaIds;
	}


	public void setAreaIds(List<Long> areaIds) {
		this.areaIds = areaIds;
	}


	public List<Long> getParentAreaIds() {
		return parentAreaIds;
	}


	public void setParentAreaIds(List<Long> parentAreaIds) {
		this.parentAreaIds = parentAreaIds;
	}
	
}
