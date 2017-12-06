package com.kington.fshg.model.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.ReceiveState;
import com.kington.fshg.model.VOSupport;

public class ReceiveBillVO extends VOSupport {

	private static final long serialVersionUID = 5204894800312001207L;

	private String autoId;//销售发票子表Id 
	private String csbvcode;//销售发票号
	private Date createDate;//制单时间
	private Date saleDate;//订单日期
	private Date deliverDate;//发货日期
	private Date maturityDate;//到期日期
	private String saleOrderId;//销售订单子表ID
	private String csoCode;//销售订单号
	private String customCde;//客户编码
	private String customName;//客户名称
	private String stockCde;//存货编号
	private String productCde;//存货代码
	private String productName;//存货名称
	private String standard;//存货规格
	private Double count;//数量
	private Double localPrice;//本币含税单价
	private Double countPrice;//本币价税合计
	private Double countPrice1;//实际本币价税合计
	private Double noTaxPrice;//本币无税金额
	private ReceiveState state;//应收单状态
	
	private String timeType;  // 1.订单日期   2.发货日期  3.制单时间 4到期时间
	private String beginTime;//开始时间
	private String endTime;//结束时间
	private List<Long> areaIds = new ArrayList<Long>();
	private List<Long> parentAreaIds = new ArrayList<Long>();
	private Long userId;//业务员ID
	private List<CheckRecord> recordList= new ArrayList<CheckRecord>();
	
	private Double receivePrice;//已收款合计
	private Double actualPrice;//实际收款额
	private Double chargePrice;//待费用发票额
	private Double returnPrice;//待退货额
	private Double holdPrice;//暂扣额
	private Double otherPrice;//其他余额
	private CheckBill checkBill;//待核单表头
	private String customType;//客户类型
	private String customArea;//客户地区
	private String number;//凭证号
	private String areaName;//地区名称
	
	private Date billDate;//发票日期
	
	private String statTime;//账期统计截止时间
	private Long customTypeId;//客户类型
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

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
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

	public List<CheckRecord> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<CheckRecord> recordList) {
		this.recordList = recordList;
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

	public String getStatTime() {
		return statTime;
	}

	public void setStatTime(String statTime) {
		this.statTime = statTime;
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

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Long getCustomTypeId() {
		return customTypeId;
	}

	public void setCustomTypeId(Long customTypeId) {
		this.customTypeId = customTypeId;
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
				ReceiveBillVO.class.getSimpleName()) : StringUtils.EMPTY;
	}

}
