package com.kington.fshg.model.account;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.ReceiveState;
import com.kington.fshg.model.VOSupport;

public class CheckRecordVO extends VOSupport {

	private static final long serialVersionUID = -5815498246142916632L;
	
	private ReceiveState state = ReceiveState.YHX;//状态
	
	//本次核销
	private Double receivePrice;//已收款合计
	private Double actualPrice;//实际收款额
	private Double chargePrice;//待费用发票额
	private Double returnPrice;//待退货额
	private Double holdPrice;//暂扣额
	private Double otherPrice;//其他余额
	
	//核销前
	private Double receivePriceOld;//已收款合计
	private Double actualPriceOld;//实际收款额
	private Double chargePriceOld;//待费用发票额
	private Double returnPriceOld;//待退货额
	private Double holdPriceOld;//暂扣额
	private Double otherPriceOld;//其他余额
	
	private Double receiptCount;//核销钱收款单未核余额
	private ReceiveBill receiveBill;//应收单
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
				CheckRecordVO.class.getSimpleName()) : StringUtils.EMPTY;
	}

}
