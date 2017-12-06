package com.kington.fshg.common.excel.vo;

import java.io.Serializable;
import com.kington.fshg.common.excel.ExcelAnnotation;
public class InFeeClauseExcelVO implements Serializable {
	private static final long serialVersionUID = 6053808585688946820L;

	@ExcelAnnotation(exportName="序号")
	private String cid;//序号
	
	@ExcelAnnotation(exportName ="客户编码")
	private String customCde;//客户编号
	
	@ExcelAnnotation(exportName="客户名称")
	private String customName;//客户名称
	
	@ExcelAnnotation(exportName ="进场费")
	private String enterFee;//进场费
	
	@ExcelAnnotation(exportName="进场费分摊开始时间")
	private String enterStartDate;
	
	@ExcelAnnotation(exportName="进场费分摊结束时间")
	private String enterEndDate;
	
	@ExcelAnnotation(exportName ="年返金")
	private String yearReturnFee;//年返金
	
	@ExcelAnnotation(exportName ="固定费用")
	private String fixedFee;//固定费用
	
	@ExcelAnnotation(exportName="固定费用分摊开始时间")
	private String fixedStartDate;
	
	@ExcelAnnotation(exportName="固定费用分摊结束时间")
	private String fixedEndDate;
	
	@ExcelAnnotation(exportName="月返金")
	private String monthReturnFee;//月返金
	
	@ExcelAnnotation(exportName="网络信息费")
	private String netInfoFee;//网络信息费
	
	@ExcelAnnotation(exportName="网络信息费分摊开始时间")
	private String netStartDate;
	
	@ExcelAnnotation(exportName="网络信息费分摊结束时间")
	private String netEndDate;
	
	@ExcelAnnotation(exportName="配送服务费")
	private String deliveryFee;//配送服务费
	
	@ExcelAnnotation(exportName="海报费")
	private String posterFee;//海报费
	
	@ExcelAnnotation(exportName="海报费分摊开始时间")
	private String posterStartDate;
	
	@ExcelAnnotation(exportName="海报费分摊结束时间")
	private String posterEndDate;
	
	@ExcelAnnotation(exportName="促销陈列费")
	private String promotionFee;//促销陈列费
	
	@ExcelAnnotation(exportName="促销陈列费分摊开始时间")
	private String promotionStartDate;
	
	@ExcelAnnotation(exportName="促销陈列费分摊结束时间")
	private String promotionEndDate;
	
	@ExcelAnnotation(exportName="赞助费")
	private String sponsorFee;//赞助费（比例）
	
	@ExcelAnnotation(exportName="损耗费")
	private String lossFee;//损耗费
	
	@ExcelAnnotation(exportName="固定折扣")
	private String fixedDiscount;//固定折扣
	
	@ExcelAnnotation(exportName="堆头费")
	private String pilesoilFee;//堆头费
	
	@ExcelAnnotation(exportName="堆头费分摊开始时间")
	private String pileStartDate;
	
	@ExcelAnnotation(exportName="堆头费分摊结束时间")
	private String pileEndDate;
	
	@ExcelAnnotation(exportName="市场费")
	private String marketFee;//市场费
		
	@ExcelAnnotation(exportName="现款现货返利")
	private String caseReturnFee;//现款现货返利
	
	@ExcelAnnotation(exportName="其他费用")
	private String otherFee;//其他费用
	
	@ExcelAnnotation(exportName="其他费用分摊开始时间")
	private String otherStartDate;
	
	@ExcelAnnotation(exportName="其他费用分摊结束时间")
	private String otherEndDate;
	
	/*@ExcelAnnotation(exportName="门店数")
	private String storeCount;//门店数
	
	@ExcelAnnotation(exportName="赞助费(固定)")
	private String fixedSponsorFee;*/
	
	@ExcelAnnotation(exportName="年度")
	private String year;
	
	@ExcelAnnotation(exportName="开始日期")
	private String startDate;
	
	@ExcelAnnotation(exportName="结束日期")
	private String endDate;
	
	@ExcelAnnotation(exportName="备注")
	private String remark;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
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

	public String getEnterFee() {
		return enterFee;
	}

	public void setEnterFee(String enterFee) {
		this.enterFee = enterFee;
	}

	public String getYearReturnFee() {
		return yearReturnFee;
	}

	public void setYearReturnFee(String yearReturnFee) {
		this.yearReturnFee = yearReturnFee;
	}

	public String getFixedFee() {
		return fixedFee;
	}

	public void setFixedFee(String fixedFee) {
		this.fixedFee = fixedFee;
	}

	public String getMonthReturnFee() {
		return monthReturnFee;
	}

	public void setMonthReturnFee(String monthReturnFee) {
		this.monthReturnFee = monthReturnFee;
	}

	public String getNetInfoFee() {
		return netInfoFee;
	}

	public void setNetInfoFee(String netInfoFee) {
		this.netInfoFee = netInfoFee;
	}

	public String getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(String deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public String getPosterFee() {
		return posterFee;
	}

	public void setPosterFee(String posterFee) {
		this.posterFee = posterFee;
	}

	public String getPromotionFee() {
		return promotionFee;
	}

	public void setPromotionFee(String promotionFee) {
		this.promotionFee = promotionFee;
	}

	public String getSponsorFee() {
		return sponsorFee;
	}

	public void setSponsorFee(String sponsorFee) {
		this.sponsorFee = sponsorFee;
	}

	public String getLossFee() {
		return lossFee;
	}

	public void setLossFee(String lossFee) {
		this.lossFee = lossFee;
	}

	public String getFixedDiscount() {
		return fixedDiscount;
	}

	public void setFixedDiscount(String fixedDiscount) {
		this.fixedDiscount = fixedDiscount;
	}

	public String getPilesoilFee() {
		return pilesoilFee;
	}

	public void setPilesoilFee(String pilesoilFee) {
		this.pilesoilFee = pilesoilFee;
	}

	public String getMarketFee() {
		return marketFee;
	}

	public void setMarketFee(String marketFee) {
		this.marketFee = marketFee;
	}

	public String getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(String otherFee) {
		this.otherFee = otherFee;
	}

/*	public String getStoreCount() {
		return storeCount;
	}

	public void setStoreCount(String storeCount) {
		this.storeCount = storeCount;
	}

	public String getFixedSponsorFee() {
		return fixedSponsorFee;
	}

	public void setFixedSponsorFee(String fixedSponsorFee) {
		this.fixedSponsorFee = fixedSponsorFee;
	}*/

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCaseReturnFee() {
		return caseReturnFee;
	}

	public void setCaseReturnFee(String caseReturnFee) {
		this.caseReturnFee = caseReturnFee;
	}

	public String getEnterStartDate() {
		return enterStartDate;
	}

	public void setEnterStartDate(String enterStartDate) {
		this.enterStartDate = enterStartDate;
	}

	public String getEnterEndDate() {
		return enterEndDate;
	}

	public void setEnterEndDate(String enterEndDate) {
		this.enterEndDate = enterEndDate;
	}

	public String getFixedStartDate() {
		return fixedStartDate;
	}

	public void setFixedStartDate(String fixedStartDate) {
		this.fixedStartDate = fixedStartDate;
	}

	public String getFixedEndDate() {
		return fixedEndDate;
	}

	public void setFixedEndDate(String fixedEndDate) {
		this.fixedEndDate = fixedEndDate;
	}

	public String getNetStartDate() {
		return netStartDate;
	}

	public void setNetStartDate(String netStartDate) {
		this.netStartDate = netStartDate;
	}

	public String getNetEndDate() {
		return netEndDate;
	}

	public void setNetEndDate(String netEndDate) {
		this.netEndDate = netEndDate;
	}

	public String getPosterStartDate() {
		return posterStartDate;
	}

	public void setPosterStartDate(String posterStartDate) {
		this.posterStartDate = posterStartDate;
	}

	public String getPosterEndDate() {
		return posterEndDate;
	}

	public void setPosterEndDate(String posterEndDate) {
		this.posterEndDate = posterEndDate;
	}

	public String getPromotionStartDate() {
		return promotionStartDate;
	}

	public void setPromotionStartDate(String promotionStartDate) {
		this.promotionStartDate = promotionStartDate;
	}

	public String getPromotionEndDate() {
		return promotionEndDate;
	}

	public void setPromotionEndDate(String promotionEndDate) {
		this.promotionEndDate = promotionEndDate;
	}

	public String getPileStartDate() {
		return pileStartDate;
	}

	public void setPileStartDate(String pileStartDate) {
		this.pileStartDate = pileStartDate;
	}

	public String getPileEndDate() {
		return pileEndDate;
	}

	public void setPileEndDate(String pileEndDate) {
		this.pileEndDate = pileEndDate;
	}

	public String getOtherStartDate() {
		return otherStartDate;
	}

	public void setOtherStartDate(String otherStartDate) {
		this.otherStartDate = otherStartDate;
	}

	public String getOtherEndDate() {
		return otherEndDate;
	}

	public void setOtherEndDate(String otherEndDate) {
		this.otherEndDate = otherEndDate;
	}

}
