package com.kington.fshg.model.budget;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.VOSupport;
import com.kington.fshg.model.info.Custom;

public class InFeeClauseVO extends VOSupport {

	private static final long serialVersionUID = -6799831108328088825L;

	private Custom custom;//所属客户
	private Double enterFee;//进场费
	private Double yearReturnFee;//年返金
	private Double fixedFee;//固定费用
	private Double monthReturnFee;//月返金
	private Double netInfoFee;//网络信息费
	private Double deliveryFee;//配送服务费
	private Double posterFee;//海报费
	private Double promotionFee;//促销陈列费
	private Double sponsorFee;//赞助费（比例）
	private Double lossFee;//损耗费
	private Double fixedDiscount;//固定折扣
	private Double pilesoilFee;//堆头费
	private Double marketFee;//市场费
	private Double caseReturnFee;//现款现货返利
	private Double otherFee;//其他费用
	private Integer storeCount;//门店数
	private Double fixedSponsorFee;//赞助费（固定）
	private Integer year;//年度
	private Date startDate;
	private Date endDate;
	private String remark;  //备注
	private List<Long> areaIds = new ArrayList<Long>();
	private List<Long> parentAreaIds = new ArrayList<Long>();
	
	private Date enterStartDate;//进场费分摊开始时间
	private Date enterEndDate;//进场费分摊结束时间
	private Date fixedStartDate;//固定费用分摊开始时间
	private Date fixedEndDate;//固定费用分摊结束时间
	private Date netStartDate;//网络信息费分摊开始时间
	private Date netEndDate;//网络信息费分摊结束时间
	private Date posterStartDate;//海报费分摊开始时间
	private Date posterEndDate;//海报费分摊结束时间
	private Date promotionStartDate;//促销陈列费分摊开始时间
	private Date promotionEndDate;//促销陈列费分摊结束时间
	private Date pileStartDate;//堆头费分摊开始时间
	private Date pileEndDate;//堆头费分摊结束时间
	private Date otherStartDate;//其他费用分摊开始时间
	private Date otherEndDate;//其他费用分摊结束时间
	

	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public Custom getCustom() {
		return custom;
	}


	public void setCustom(Custom custom) {
		this.custom = custom;
	}


	public Double getEnterFee() {
		return enterFee;
	}


	public void setEnterFee(Double enterFee) {
		this.enterFee = enterFee;
	}


	public Double getYearReturnFee() {
		return yearReturnFee;
	}


	public void setYearReturnFee(Double yearReturnFee) {
		this.yearReturnFee = yearReturnFee;
	}


	public Double getFixedFee() {
		return fixedFee;
	}


	public void setFixedFee(Double fixedFee) {
		this.fixedFee = fixedFee;
	}


	public Double getMonthReturnFee() {
		return monthReturnFee;
	}


	public void setMonthReturnFee(Double monthReturnFee) {
		this.monthReturnFee = monthReturnFee;
	}


	public Double getNetInfoFee() {
		return netInfoFee;
	}


	public void setNetInfoFee(Double netInfoFee) {
		this.netInfoFee = netInfoFee;
	}


	public Double getDeliveryFee() {
		return deliveryFee;
	}


	public void setDeliveryFee(Double deliveryFee) {
		this.deliveryFee = deliveryFee;
	}


	public Double getPosterFee() {
		return posterFee;
	}


	public void setPosterFee(Double posterFee) {
		this.posterFee = posterFee;
	}


	public Double getPromotionFee() {
		return promotionFee;
	}


	public void setPromotionFee(Double promotionFee) {
		this.promotionFee = promotionFee;
	}


	public Double getSponsorFee() {
		return sponsorFee;
	}


	public void setSponsorFee(Double sponsorFee) {
		this.sponsorFee = sponsorFee;
	}


	public Double getLossFee() {
		return lossFee;
	}


	public void setLossFee(Double lossFee) {
		this.lossFee = lossFee;
	}


	public Double getFixedDiscount() {
		return fixedDiscount;
	}


	public void setFixedDiscount(Double fixedDiscount) {
		this.fixedDiscount = fixedDiscount;
	}


	public Double getPilesoilFee() {
		return pilesoilFee;
	}


	public void setPilesoilFee(Double pilesoilFee) {
		this.pilesoilFee = pilesoilFee;
	}


	public Double getMarketFee() {
		return marketFee;
	}


	public void setMarketFee(Double marketFee) {
		this.marketFee = marketFee;
	}


	public Double getCaseReturnFee() {
		return caseReturnFee;
	}


	public void setCaseReturnFee(Double caseReturnFee) {
		this.caseReturnFee = caseReturnFee;
	}


	public Double getOtherFee() {
		return otherFee;
	}


	public void setOtherFee(Double otherFee) {
		this.otherFee = otherFee;
	}


	public Integer getStoreCount() {
		return storeCount;
	}


	public void setStoreCount(Integer storeCount) {
		this.storeCount = storeCount;
	}


	public Double getFixedSponsorFee() {
		return fixedSponsorFee;
	}


	public void setFixedSponsorFee(Double fixedSponsorFee) {
		this.fixedSponsorFee = fixedSponsorFee;
	}


	public Integer getYear() {
		return year;
	}


	public void setYear(Integer year) {
		this.year = year;
	}


	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				InFeeClauseVO.class.getSimpleName()) : StringUtils.EMPTY;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	
	public Date getEnterStartDate() {
		return enterStartDate;
	}
	public void setEnterStartDate(Date enterStartDate) {
		this.enterStartDate = enterStartDate;
	}
	public Date getEnterEndDate() {
		return enterEndDate;
	}
	public void setEnterEndDate(Date enterEndDate) {
		this.enterEndDate = enterEndDate;
	}
	public Date getFixedStartDate() {
		return fixedStartDate;
	}
	public void setFixedStartDate(Date fixedStartDate) {
		this.fixedStartDate = fixedStartDate;
	}
	public Date getFixedEndDate() {
		return fixedEndDate;
	}
	public void setFixedEndDate(Date fixedEndDate) {
		this.fixedEndDate = fixedEndDate;
	}
	public Date getNetStartDate() {
		return netStartDate;
	}
	public void setNetStartDate(Date netStartDate) {
		this.netStartDate = netStartDate;
	}
	public Date getNetEndDate() {
		return netEndDate;
	}
	public void setNetEndDate(Date netEndDate) {
		this.netEndDate = netEndDate;
	}
	public Date getPosterStartDate() {
		return posterStartDate;
	}
	public void setPosterStartDate(Date posterStartDate) {
		this.posterStartDate = posterStartDate;
	}
	public Date getPosterEndDate() {
		return posterEndDate;
	}
	public void setPosterEndDate(Date posterEndDate) {
		this.posterEndDate = posterEndDate;
	}
	public Date getPromotionStartDate() {
		return promotionStartDate;
	}
	public void setPromotionStartDate(Date promotionStartDate) {
		this.promotionStartDate = promotionStartDate;
	}
	public Date getPromotionEndDate() {
		return promotionEndDate;
	}
	public void setPromotionEndDate(Date promotionEndDate) {
		this.promotionEndDate = promotionEndDate;
	}
	public Date getPileStartDate() {
		return pileStartDate;
	}
	public void setPileStartDate(Date pileStartDate) {
		this.pileStartDate = pileStartDate;
	}
	public Date getPileEndDate() {
		return pileEndDate;
	}
	public void setPileEndDate(Date pileEndDate) {
		this.pileEndDate = pileEndDate;
	}
	public Date getOtherStartDate() {
		return otherStartDate;
	}
	public void setOtherStartDate(Date otherStartDate) {
		this.otherStartDate = otherStartDate;
	}
	public Date getOtherEndDate() {
		return otherEndDate;
	}
	public void setOtherEndDate(Date otherEndDate) {
		this.otherEndDate = otherEndDate;
	}
}
