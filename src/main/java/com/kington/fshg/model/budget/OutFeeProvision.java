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
 *	合同外费用预提信息表 
 *
 */

@Entity
@Table(name = "bud_out_fee_provision")
public class OutFeeProvision extends POSupport {
	private static final long serialVersionUID = 2868924249388314690L;

	@Column(length = 20)
	private String provisionCode;//预提编号
	
	@Column(length = 100)
	private String salesman;//业务员
	
	@Column(length = 20)
	@Enumerated(EnumType.STRING)
	private ApproveState approveState;//审批状态
	
	@Column(length = 1000)
	private String opinion;//审批意见
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "custom_id")
	private Custom custom;//所属客户
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	private ProvisionProject provisionProject;//所属合同外预提项目
	
	@Column
	private Double totalFee;//总费用
	
	@Column(length = 50)
	private String storeScale;//门店,比例
	
	@Column
	private Date provisionTime;//制单时间
	
	@Column(length = 255)
	private Date startTime;//预提所属区间起始时间
	
	@Column(length = 255)
	private Date endTime;//预提所属区间结束时间
	
	@Column(length = 2000)
	private String remark;//备注
	
	@Override
	public String getKey() {
		return  Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				OutFeeProvision.class.getSimpleName()) : StringUtils.EMPTY ;
	}


	public String getProvisionCode() {
		return provisionCode;
	}


	public void setProvisionCode(String provisionCode) {
		this.provisionCode = provisionCode;
	}


	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	public ApproveState getApproveState() {
		return approveState;
	}

	public void setApproveState(ApproveState approveState) {
		this.approveState = approveState;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public Custom getCustom() {
		return custom;
	}

	public void setCustom(Custom custom) {
		this.custom = custom;
	}

	public ProvisionProject getProvisionProject() {
		return provisionProject;
	}

	public void setProvisionProject(ProvisionProject provisionProject) {
		this.provisionProject = provisionProject;
	}


	public String getStoreScale() {
		return storeScale;
	}


	public void setStoreScale(String storeScale) {
		this.storeScale = storeScale;
	}


	public Double getTotalFee() {
		return totalFee;
	}


	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}


	public Date getProvisionTime() {
		return provisionTime;
	}


	public void setProvisionTime(Date provisionTime) {
		this.provisionTime = provisionTime;
	}


	public Date getStartTime() {
		return startTime;
	}


	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}


	public Date getEndTime() {
		return endTime;
	}


	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}
