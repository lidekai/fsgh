package com.kington.fshg.model.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.ReceiveState;
import com.kington.fshg.model.VOSupport;

public class CheckBillVO extends VOSupport {

	private static final long serialVersionUID = -2785294400458873252L;

	private String code;//单号（例如：YCB20170901000001）
	private String customType;//客户类型
	private String customArea;//客户地区
	private String customCde;//客户编码
	private String customName;//客户名称
	private Double countPrice;//本币价税合计
	private Date createDate;//制单时间
	private ReceiveState state;//状态
	
	private Double receivePrice;//已收款合计
	private Double actualPrice;//实际收款额
	private Double chargePrice;//待费用发票额
	private Double returnPrice;//待退货额
	private Double holdPrice;//暂扣额
	private Double otherPrice;//其他余额
	
	private String beginTime;//开始时间
	private String endTime;//结束时间
	private List<Long> areaIds = new ArrayList<Long>();
	private List<Long> parentAreaIds = new ArrayList<Long>();
	private Long userId;//业务员ID
	
	private List<ReceiveBill> receiveList= new ArrayList<ReceiveBill>();
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCustomCde() {
		return customCde;
	}

	public void setCustomCde(String customCde) {
		this.customCde = customCde;
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	public Double getCountPrice() {
		return countPrice;
	}

	public void setCountPrice(Double countPrice) {
		this.countPrice = countPrice;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public ReceiveState getState() {
		return state;
	}

	public void setState(ReceiveState state) {
		this.state = state;
	}

	public Double getReceivePrice() {
		return receivePrice;
	}

	public void setReceivePrice(Double receivePrice) {
		this.receivePrice = receivePrice;
	}

	public Double getChargePrice() {
		return chargePrice;
	}

	public void setChargePrice(Double chargePrice) {
		this.chargePrice = chargePrice;
	}

	public Double getReturnPrice() {
		return returnPrice;
	}

	public void setReturnPrice(Double returnPrice) {
		this.returnPrice = returnPrice;
	}

	public Double getHoldPrice() {
		return holdPrice;
	}

	public void setHoldPrice(Double holdPrice) {
		this.holdPrice = holdPrice;
	}

	public Double getOtherPrice() {
		return otherPrice;
	}

	public void setOtherPrice(Double otherPrice) {
		this.otherPrice = otherPrice;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<ReceiveBill> getReceiveList() {
		return receiveList;
	}

	public void setReceiveList(List<ReceiveBill> receiveList) {
		this.receiveList = receiveList;
	}

	public String getCustomType() {
		return customType;
	}

	public void setCustomType(String customType) {
		this.customType = customType;
	}

	public String getCustomArea() {
		return customArea;
	}

	public void setCustomArea(String customArea) {
		this.customArea = customArea;
	}

	public Double getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(Double actualPrice) {
		this.actualPrice = actualPrice;
	}

	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(), 
				CheckBillVO.class.getSimpleName()) : StringUtils.EMPTY;
	}

}
