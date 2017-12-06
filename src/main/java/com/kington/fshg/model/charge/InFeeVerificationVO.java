package com.kington.fshg.model.charge;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.ApproveState;
import com.kington.fshg.common.PublicType.VerDirection;
import com.kington.fshg.model.VOSupport;
import com.kington.fshg.model.budget.InFeeProvision;
import com.kington.fshg.model.info.Custom;
import com.kington.fshg.model.system.Dict;

public class InFeeVerificationVO extends VOSupport {
	private static final long serialVersionUID = 3410580264284782673L;
	
	private String verCode;//核销编号
	private Custom custom;//所属客户
	private InFeeProvision inFeeProvision;//所属合同内费用预提
	private VerDirection verDirection;//核销方向：红冲预提、蓝冲预提
	//private VerType verType;//核销类型：冲预提、现金支付、冲应收、货补、加量装
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
	private Double fixedSponsorFee;//赞助费（固定）
	private ApproveState approveState;//审批状态
	private Double totalFee;//总费用
	private Dict verType;//核销类型
	private Date verDate = new Date();//核销日期
	
	private String beginTime;
	private String endTime;
	
	
	//生成U8收款单相关字段
	private Boolean isCreateU8;//是否已生成U8收款单
	private String maker;//制单人
	private String summary;//摘要
	private String backItem;//收款银行科目
	private String customItem;//对方科目
	private String ssCode;//结算方式编码
	private List<Long> areaIds = new ArrayList<Long>();
	private List<Long> parentAreaIds = new ArrayList<Long>();
	
	


	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				InFeeVerification.class.getSimpleName()) : StringUtils.EMPTY;
	}
	
	public String getVerCode() {
		return verCode;
	}

	public void setVerCode(String verCode) {
		this.verCode = verCode;
	}

	public Custom getCustom() {
		return custom;
	}
	public void setCustom(Custom custom) {
		this.custom = custom;
	}
	public InFeeProvision getInFeeProvision() {
		return inFeeProvision;
	}
	public void setInFeeProvision(InFeeProvision inFeeProvision) {
		this.inFeeProvision = inFeeProvision;
	}
	public VerDirection getVerDirection() {
		return verDirection;
	}
	public void setVerDirection(VerDirection verDirection) {
		this.verDirection = verDirection;
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
	public Double getFixedSponsorFee() {
		return fixedSponsorFee;
	}
	public void setFixedSponsorFee(Double fixedSponsorFee) {
		this.fixedSponsorFee = fixedSponsorFee;
	}

	public ApproveState getApproveState() {
		return approveState;
	}

	public void setApproveState(ApproveState approveState) {
		this.approveState = approveState;
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

	public Double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

	public Dict getVerType() {
		return verType;
	}

	public void setVerType(Dict verType) {
		this.verType = verType;
	}

	public Boolean getIsCreateU8() {
		return isCreateU8;
	}

	public void setIsCreateU8(Boolean isCreateU8) {
		this.isCreateU8 = isCreateU8;
	}

	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getBackItem() {
		return backItem;
	}

	public void setBackItem(String backItem) {
		this.backItem = backItem;
	}

	public String getCustomItem() {
		return customItem;
	}

	public void setCustomItem(String customItem) {
		this.customItem = customItem;
	}

	public String getSsCode() {
		return ssCode;
	}

	public void setSsCode(String ssCode) {
		this.ssCode = ssCode;
	}

	public Date getVerDate() {
		return verDate;
	}

	public void setVerDate(Date verDate) {
		this.verDate = verDate;
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

}
