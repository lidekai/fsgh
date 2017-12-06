package com.kington.fshg.model.charge;

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
import com.kington.fshg.common.PublicType.VerDirection;
import com.kington.fshg.model.POSupport;
import com.kington.fshg.model.budget.OutFeeProvision;
import com.kington.fshg.model.info.Custom;
import com.kington.fshg.model.system.Dict;

/**
 *	合同外费用核销 
 *
 */
@Entity
@Table(name = "charge_out_fee_verification")
public class OutFeeVerification extends POSupport {
	private static final long serialVersionUID = 4858667879067008719L;

	@Column(length = 100)
	private String verCode;//核销编码
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "custom_id")
	private Custom custom;//所属客户
	
	@Column(length = 20)
	@Enumerated(EnumType.STRING)
	private VerDirection verDirection;//核销方向
	
	@Column(length = 100)
	private String salesman;//申请业务员
	
	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "provision_id")
	private OutFeeProvision outFeeProvision;//所属合同外费用预提
	
	@Column(length = 20)
	@Enumerated(EnumType.STRING)
	private ApproveState approveState;//审批状态
	
	@Column
	private Double totalFee;//总费用
	
	@Column(length = 100)
	private String storeScale;//门店,比例
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "verType_id")
	private Dict verType;//核销类型
	
	//生成U8收款单相关字段
	private Boolean isCreateU8 = false;//是否已生成U8收款单
	private String maker;//制单人
	private String summary;//摘要
	private String backItem;//收款银行科目
	private String customItem;//对方科目
	private String ssCode;//结算方式编码
	
	@Column(length = 255)
	private Date verTime;//核销时间
	
	@Column(length = 255)
	private Date timeStart;
	
	@Column(length = 255)
	private Date timeEnd;
	
	@Column(length = 2000)
	private String remark;
	
	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				OutFeeVerification.class.getSimpleName()) : StringUtils.EMPTY;
	}

	public String getVerCode() {
		return verCode;
	}

	public void setVerCode(String verCode) {
		this.verCode = verCode;
	}

	public VerDirection getVerDirection() {
		return verDirection;
	}

	public void setVerDirection(VerDirection verDirection) {
		this.verDirection = verDirection;
	}

	public Dict getVerType() {
		return verType;
	}

	public void setVerType(Dict verType) {
		this.verType = verType;
	}

	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	public OutFeeProvision getOutFeeProvision() {
		return outFeeProvision;
	}

	public void setOutFeeProvision(OutFeeProvision outFeeProvision) {
		this.outFeeProvision = outFeeProvision;
	}

	public ApproveState getApproveState() {
		return approveState;
	}

	public void setApproveState(ApproveState approveState) {
		this.approveState = approveState;
	}

	public Double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

	public String getStoreScale() {
		return storeScale;
	}

	public void setStoreScale(String storeScale) {
		this.storeScale = storeScale;
	}

	public Custom getCustom() {
		return custom;
	}

	public void setCustom(Custom custom) {
		this.custom = custom;
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


	public Date getVerTime() {
		return verTime;
	}

	public void setVerTime(Date verTime) {
		this.verTime = verTime;
	}

	public Date getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}

	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
