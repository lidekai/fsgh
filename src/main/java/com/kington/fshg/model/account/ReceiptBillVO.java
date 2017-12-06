package com.kington.fshg.model.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.VOSupport;

public class ReceiptBillVO extends VOSupport {

	private static final long serialVersionUID = -8177234834438875594L;

	private String cvouchType;//类别（48：收；49：付）
	private String cvouchId;//收款单号
	private Date receiptDate;//收款日期
	private String customerCde;//客户编号
	private String customerName;//客户名称
	private String cssCode;//结算编号
	private String cssName;//结算名称
	private Double receiptCount;//本币收款金额 
	private Double receiptCount1;//实际本币收款金额 
	private Double receiveCount;//本币未核余额
	
	private String beginTime;//开始时间
	private String endTime;//结束时间
	private List<Long> areaIds = new ArrayList<Long>();
	private List<Long> parentAreaIds = new ArrayList<Long>();
	private Long userId;//业务员ID
	private Long customTypeId;

	public String getCvouchType() {
		return cvouchType;
	}

	public void setCvouchType(String cvouchType) {
		this.cvouchType = cvouchType;
	}

	public String getCvouchId() {
		return cvouchId;
	}

	public void setCvouchId(String cvouchId) {
		this.cvouchId = cvouchId;
	}

	public Date getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}

	public String getCustomerCde() {
		return customerCde;
	}

	public void setCustomerCde(String customerCde) {
		this.customerCde = customerCde;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCssCode() {
		return cssCode;
	}

	public void setCssCode(String cssCode) {
		this.cssCode = cssCode;
	}

	public String getCssName() {
		return cssName;
	}

	public void setCssName(String cssName) {
		this.cssName = cssName;
	}

	public Double getReceiptCount() {
		return receiptCount;
	}

	public void setReceiptCount(Double receiptCount) {
		this.receiptCount = receiptCount;
	}

	public Double getReceiptCount1() {
		return receiptCount1;
	}

	public void setReceiptCount1(Double receiptCount1) {
		this.receiptCount1 = receiptCount1;
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

	public Double getReceiveCount() {
		return receiveCount;
	}

	public void setReceiveCount(Double receiveCount) {
		this.receiveCount = receiveCount;
	}

	public Long getCustomTypeId() {
		return customTypeId;
	}

	public void setCustomTypeId(Long customTypeId) {
		this.customTypeId = customTypeId;
	}

	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(), 
				ReceiptBillVO.class.getSimpleName()) : StringUtils.EMPTY;
	}

}
