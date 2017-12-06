package com.kington.fshg.model.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.ReceiveState;
import com.kington.fshg.model.POSupport;
/**
 *  待核单表头
 *
 */
@Entity
@Table(name = "check_bill")
public class CheckBill extends POSupport {

	private static final long serialVersionUID = 6484866579105249373L;
	
	@Column(length = 20)
	private String code;//单号（例如：YCB20170901000001）
	
	@Column(length = 100)
	private String customCde;//客户编码
	
	@Column(length = 100)
	private String customName;//客户名称
	
	@Column(length = 20)
	private String customType;//客户类型
	
	@Column(length = 20)
	private String customArea;//客户地区
	
	private Double countPrice;//本币价税合计
	
	private Date createDate;//制单时间
	
	@Column(length = 5)
	@Enumerated(EnumType.STRING)
	private ReceiveState state = ReceiveState.DHX;//状态
	
	private Double receivePrice = 0d;//已收款合计
	private Double actualPrice = 0d;//实际收款额
	private Double chargePrice = 0d;//待费用发票额
	private Double returnPrice = 0d;//待退货额
	private Double holdPrice = 0d;//暂扣额
	private Double otherPrice = 0d;//其他余额
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "checkBill")
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
				CheckBill.class.getSimpleName()) : StringUtils.EMPTY;
	}

}
