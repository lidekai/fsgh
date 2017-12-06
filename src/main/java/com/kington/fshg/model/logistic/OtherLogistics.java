package com.kington.fshg.model.logistic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.ApproveState;
import com.kington.fshg.model.POSupport;
import com.kington.fshg.model.info.Custom;

/**
 *	其它物流费用信息表 
 *
 */
@Entity
@Table(name = "logistics_other_logistics")
public class OtherLogistics extends POSupport {
	private static final long serialVersionUID = -3013483828812047905L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "custom_id")
	private Custom custom;//所属客户
	
	@Column(name = "cost")
	private Double cost;//促销费用
	
	@Column
	private Double returnGoods;//退货
	
	@Column
	private Double wagesShare;//工资分摊
	
	@Column
	private Double storageShare;//仓储分摊
	
	@Column
	private Double salesamount;//销售金额
	@Column
	private Double transferCost;//调拨费
	@Column
	private Double otherLogisticsCost;//运费合计
	
	@Column(length = 20)
	@Enumerated(EnumType.STRING)
	private ApproveState approveState = ApproveState.DSP;//审批状态
	
	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(), 
				OtherLogistics.class.getSimpleName()) : StringUtils.EMPTY;
	}

	public Custom getCustom() {
		return custom;
	}

	public void setCustom(Custom custom) {
		this.custom = custom;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getReturnGoods() {
		return returnGoods;
	}

	public void setReturnGoods(Double returnGoods) {
		this.returnGoods = returnGoods;
	}

	public Double getWagesShare() {
		return wagesShare;
	}

	public void setWagesShare(Double wagesShare) {
		this.wagesShare = wagesShare;
	}

	public Double getStorageShare() {
		return storageShare;
	}

	public void setStorageShare(Double storageShare) {
		this.storageShare = storageShare;
	}

	public Double getSalesamount() {
		return salesamount;
	}

	public void setSalesamount(Double salesamount) {
		this.salesamount = salesamount;
	}

	public ApproveState getApproveState() {
		return approveState;
	}

	public void setApproveState(ApproveState approveState) {
		this.approveState = approveState;
	}

	public Double getTransferCost() {
		return transferCost;
	}

	public void setTransferCost(Double transferCost) {
		this.transferCost = transferCost;
	}

	public Double getOtherLogisticsCost() {
		return otherLogisticsCost;
	}

	public void setOtherLogisticsCost(Double otherLogisticsCost) {
		this.otherLogisticsCost = otherLogisticsCost;
	}
	
}
