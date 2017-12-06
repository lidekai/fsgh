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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.ReceiveState;
import com.kington.fshg.model.POSupport;
/**
 *  U8应收单
 *
 */
@Entity
@Table(name = "receive_bill")
public class ReceiveBill extends POSupport {
	private static final long serialVersionUID = 7620584221844072131L;
	
	private Date createDate;//制单时间
	
	private Date saleDate;//订单日期
	
	private Date deliverDate;//发货日期
	
	private String saleOrderId;//销售订单子表ID

	private String csoCode;//销售订单号
	
	@Column(length = 100)
	private String stockCde;//存货编号
	
	@Column(length = 100)
	private String productCde;//存货代码
	
	@Column(length = 100)
	private String productName;//存货名称
	
	@Column(length = 200)
	private String standard;//存货规格
	
	private Double count;//数量
	
	private Double localPrice;//本币含税单价
	
	private Double noTaxPrice;//本币无税金额
	
	@Column(length = 20)
	private String autoId;//销售发票子表Id 
//------------------------以上字段作废--------------------//
	
	@Column(length = 20)
	private String customType;//客户类型
	
	@Column(length = 20)
	private String customArea;//客户地区
	
	@Column(length = 20)
	private String number;//凭证号
	
	@Column(length = 100)
	private String csbvcode;//销售发票号
	
	private Date billDate;//发票日期
	
	@Column(length = 100)
	private String customCde;//客户编码
	
	@Column(length = 100)
	private String customName;//客户名称
	
	private Date maturityDate;//到期日期	
	
	private Double countPrice;//本币发票额
	private Double countPrice1;//本币实际发票额	
	
	@Column(length = 5)
	@Enumerated(EnumType.STRING)
	private ReceiveState state = ReceiveState.WCL;//应收单状态
	
	private Double receivePrice = 0d;//已收款合计
	private Double actualPrice = 0d;//实际收款额
	private Double chargePrice = 0d;//待费用发票额
	private Double returnPrice = 0d;//待退货额
	private Double holdPrice = 0d;//暂扣额
	private Double otherPrice = 0d;//其他余额
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "checkId")
	private CheckBill checkBill;//待核单表头
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "receiveBill")
	@OrderBy(value="createTime desc")
	private List<CheckRecord> recordList= new ArrayList<CheckRecord>();

	private Double verificationPrice;//待核销额
	
	
	
	public Double getVerificationPrice() {
		return verificationPrice;
	}

	public void setVerificationPrice(Double verificationPrice) {
		this.verificationPrice = verificationPrice;
	}

	public String getAutoId() {
		return autoId;
	}

	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}

	public String getCsbvcode() {
		return csbvcode;
	}

	public void setCsbvcode(String csbvcode) {
		this.csbvcode = csbvcode;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public Date getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public String getSaleOrderId() {
		return saleOrderId;
	}

	public void setSaleOrderId(String saleOrderId) {
		this.saleOrderId = saleOrderId;
	}

	public String getCsoCode() {
		return csoCode;
	}

	public void setCsoCode(String csoCode) {
		this.csoCode = csoCode;
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

	public String getStockCde() {
		return stockCde;
	}

	public void setStockCde(String stockCde) {
		this.stockCde = stockCde;
	}

	public String getProductCde() {
		return productCde;
	}

	public void setProductCde(String productCde) {
		this.productCde = productCde;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public Double getCount() {
		return count;
	}

	public void setCount(Double count) {
		this.count = count;
	}

	public Double getLocalPrice() {
		return localPrice;
	}

	public void setLocalPrice(Double localPrice) {
		this.localPrice = localPrice;
	}

	public Double getCountPrice() {
		return countPrice;
	}

	public void setCountPrice(Double countPrice) {
		this.countPrice = countPrice;
	}

	public Double getNoTaxPrice() {
		return noTaxPrice;
	}

	public void setNoTaxPrice(Double noTaxPrice) {
		this.noTaxPrice = noTaxPrice;
	}

	public Double getCountPrice1() {
		return countPrice1;
	}

	public void setCountPrice1(Double countPrice1) {
		this.countPrice1 = countPrice1;
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

	public CheckBill getCheckBill() {
		return checkBill;
	}

	public void setCheckBill(CheckBill checkBill) {
		this.checkBill = checkBill;
	}

	public List<CheckRecord> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<CheckRecord> recordList) {
		this.recordList = recordList;
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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
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
				ReceiveBill.class.getSimpleName()) : StringUtils.EMPTY;
	}

}
