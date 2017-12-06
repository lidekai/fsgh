package com.kington.fshg.model.budget;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.ApproveState;
import com.kington.fshg.model.VOSupport;
import com.kington.fshg.model.info.Custom;

public class InFeeProvisionVO extends VOSupport {
	private static final long serialVersionUID = 4877950907116323824L;

	private Custom custom;//所属客户
	private InFeeClause inFeeClause;//所属条款
	private String code;//预提编号
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
	private Double inFeeCount;//费用总和
	private Date provisionTime;//预提时间
	private Double sum;//计算基数
	
	private String createTimeStart;
	private String createTimeEnd;
	private ApproveState approveState;
	private List<Long> areaIds = new ArrayList<Long>();
	private List<Long> parentAreaIds = new ArrayList<Long>();
	
	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				InFeeProvision.class.getSimpleName()) : StringUtils.EMPTY;
	}
	public Custom getCustom() {
		return custom;
	}
	public void setCustom(Custom custom) {
		this.custom = custom;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public Double getInFeeCount() {
		return inFeeCount;
	}
	public void setInFeeCount(Double inFeeCount) {
		this.inFeeCount = inFeeCount;
	}
	public String getCreateTimeStart() {
		return createTimeStart;
	}
	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}
	public String getCreateTimeEnd() {
		return createTimeEnd;
	}
	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
	public ApproveState getApproveState() {
		return approveState;
	}
	public void setApproveState(ApproveState approveState) {
		this.approveState = approveState;
	}
	public InFeeClause getInFeeClause() {
		return inFeeClause;
	}
	public void setInFeeClause(InFeeClause inFeeClause) {
		this.inFeeClause = inFeeClause;
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
	public Date getProvisionTime() {
		return provisionTime;
	}
	public void setProvisionTime(Date provisionTime) {
		this.provisionTime = provisionTime;
	}
	public Double getSum() {
		return sum;
	}
	public void setSum(Double sum) {
		this.sum = sum;
	}
	
}
