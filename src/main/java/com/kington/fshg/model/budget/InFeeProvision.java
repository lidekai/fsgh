package com.kington.fshg.model.budget;

import java.util.Date;

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
import com.kington.fshg.common.PublicType.ApproveState;
import com.kington.fshg.model.POSupport;
import com.kington.fshg.model.info.Custom;

/**
 *	合同内费用预提 
 *
 */
@Entity
@Table(name ="bud_in_fee_provision")
public class InFeeProvision extends POSupport {
	private static final long serialVersionUID = 6177404361946363926L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "custom_id")
	private Custom custom;//所属客户
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clause_id")
	private InFeeClause inFeeClause;//所属条款
	
	@Column(length = 20)
	private String code;//预提编号
	
	@Column
	private Double enterFee;//进场费
	
	@Column
	private Double yearReturnFee;//年返金
	
	@Column
	private Double fixedFee;//固定费用
	
	@Column
	private Double monthReturnFee;//月返金
	
	@Column
	private Double netInfoFee;//网络信息费

	@Column
	private Double deliveryFee;//配送服务费
	
	@Column
	private Double posterFee;//海报费
	
	@Column
	private Double promotionFee;//促销陈列费
	
	@Column
	private Double sponsorFee;//赞助费
	
	@Column
	private Double lossFee;//损耗费
	
	@Column
	private Double fixedDiscount;//固定折扣
	
	@Column
	private Double pilesoilFee;//堆头费
	
	@Column
	private Double marketFee;//市场费
	
	@Column
	private Double caseReturnFee;//现款现货返利
	
	@Column
	private Double otherFee;//其他费用
	
	@Column
	private Double inFeeCount;//费用总和
	
	@Column(length = 20)
	@Enumerated(EnumType.STRING)
	private ApproveState approveState = ApproveState.DSP;//审批状态
	
	private Date provisionTime;//预提时间
	
	private Double sum;//计算基数
	
	
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
