package com.kington.fshg.model.logistic;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.ApproveState;
import com.kington.fshg.model.VOSupport;
import com.kington.fshg.model.info.Custom;

public class OtherLogisticsVO extends VOSupport {
	private static final long serialVersionUID = -8753569396558775646L;

	private Custom custom;//所属客户
	private Double cost;//促销费用
	private Double returnGoods;//退货
	private Double wagesShare;//工资分摊
	private Double storageShare;//仓储分摊
	private Double salesamount;//销售金额
	private Double transferCost;//调拨费
	
	private String createStartTime;//提交开始时间
	private String createEndTime;//提交结束时间	
	private ApproveState approveState;//审核状态
	private Double otherLogisticsCost;//运费合计
	private List<Long> areaIds = new ArrayList<Long>();
	private List<Long> parentAreaIds = new ArrayList<Long>();
	
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

	public String getCreateStartTime() {
		return createStartTime;
	}

	public void setCreateStartTime(String createStartTime) {
		this.createStartTime = createStartTime;
	}

	public String getCreateEndTime() {
		return createEndTime;
	}

	public void setCreateEndTime(String createEndTime) {
		this.createEndTime = createEndTime;
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
