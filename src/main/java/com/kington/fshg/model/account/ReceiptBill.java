package com.kington.fshg.model.account;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.POSupport;
/**
 *  U8收款单
 *
 */
@Entity
@Table(name = "receipt_bill")
public class ReceiptBill extends POSupport {
	private static final long serialVersionUID = 2162522407519325597L;
	
	@Column(length = 5)
	private String autoId;//收款单子表Id 
	
	@Column(length = 5)
	private String cvouchType;//类别（48：收；49：付）
	
	@Column(length = 30)
	private String cvouchId;//收款单号
	
	private Date receiptDate;//收款日期
	
	@Column(length = 20)
	private String customerCde;//客户编号
	
	@Column(length = 100)
	private String customerName;//客户名称
	
	@Column(length = 3)
	private String cssCode;//结算编号
	
	@Column(length = 12)
	private String cssName;//结算名称
	
	private Double receiptCount;//本币收款金额 
	private Double receiptCount1;//实际本币收款金额 
	
	private Double receiveCount;//本币未核余额
	
	

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

	public String getAutoId() {
		return autoId;
	}

	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}

	public Double getReceiveCount() {
		return receiveCount;
	}

	public void setReceiveCount(Double receiveCount) {
		this.receiveCount = receiveCount;
	}

	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(), 
				ReceiptBill.class.getSimpleName()) : StringUtils.EMPTY;
	}

}
