package com.kington.fshg.model.account;

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
import com.kington.fshg.common.PublicType.ReceiveState;
import com.kington.fshg.model.POSupport;
/**
 *  核销记录表
 *
 */
@Entity
@Table(name = "check_record")
public class CheckRecord extends POSupport {

	private static final long serialVersionUID = 5449525779742166687L;
	
	@Column(length = 5)
	@Enumerated(EnumType.STRING)
	private ReceiveState state = ReceiveState.YHX;//状态
	
	//本次核销
	private Double receivePrice = 0d;//已收款合计
	private Double actualPrice = 0d;//实际收款额
	private Double chargePrice = 0d;//待费用发票额
	private Double returnPrice = 0d;//待退货额
	private Double holdPrice = 0d;//暂扣额
	private Double otherPrice = 0d;//其他余额
	
	//核销前
	private Double receivePriceOld = 0d;//已收款合计
	private Double actualPriceOld = 0d;//实际收款额
	private Double chargePriceOld = 0d;//待费用发票额
	private Double returnPriceOld = 0d;//待退货额
	private Double holdPriceOld = 0d;//暂扣额
	private Double otherPriceOld = 0d;//其他余额
	
	private Double receiptCount = 0d;//核销钱收款单未核余额
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiveId")
	private ReceiveBill receiveBill;//应收单
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiptId")
	private ReceiptBill receiptBill;//收款单

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

	public ReceiveBill getReceiveBill() {
		return receiveBill;
	}

	public void setReceiveBill(ReceiveBill receiveBill) {
		this.receiveBill = receiveBill;
	}

	public ReceiptBill getReceiptBill() {
		return receiptBill;
	}

	public void setReceiptBill(ReceiptBill receiptBill) {
		this.receiptBill = receiptBill;
	}

	public Double getReceivePriceOld() {
		return receivePriceOld;
	}

	public void setReceivePriceOld(Double receivePriceOld) {
		this.receivePriceOld = receivePriceOld;
	}

	public Double getChargePriceOld() {
		return chargePriceOld;
	}

	public void setChargePriceOld(Double chargePriceOld) {
		this.chargePriceOld = chargePriceOld;
	}

	public Double getReturnPriceOld() {
		return returnPriceOld;
	}

	public void setReturnPriceOld(Double returnPriceOld) {
		this.returnPriceOld = returnPriceOld;
	}

	public Double getHoldPriceOld() {
		return holdPriceOld;
	}

	public void setHoldPriceOld(Double holdPriceOld) {
		this.holdPriceOld = holdPriceOld;
	}

	public Double getOtherPriceOld() {
		return otherPriceOld;
	}

	public void setOtherPriceOld(Double otherPriceOld) {
		this.otherPriceOld = otherPriceOld;
	}

	public Double getReceiptCount() {
		return receiptCount;
	}

	public void setReceiptCount(Double receiptCount) {
		this.receiptCount = receiptCount;
	}

	public Double getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(Double actualPrice) {
		this.actualPrice = actualPrice;
	}


	public Double getActualPriceOld() {
		return actualPriceOld;
	}

	public void setActualPriceOld(Double actualPriceOld) {
		this.actualPriceOld = actualPriceOld;
	}

	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(), 
				CheckRecord.class.getSimpleName()) : StringUtils.EMPTY;
	}

}
