package com.kington.fshg.model.budget;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.ApproveState;
import com.kington.fshg.model.VOSupport;
import com.kington.fshg.model.info.Custom;
import com.kington.fshg.model.info.Product;

public class OutFeeProvisionVO extends VOSupport {
	private static final long serialVersionUID = 6467473154860726837L;

	private Custom custom;
	private String provisionCode;
	private String salesman;
	private ApproveState approveState;
	private String opinion;
	private ProvisionProject provisionProject;
	private Double totalFee;//总费用
	private String storeScale;//门店,比例
	
	private String dateStart;
	private String dateEnd;
	
	private Product product;
	private ProvisionProject project;
	private String remark;//备注
	
	private Date provisionTime;//预提时间
	private Date startTime;//所属区间起始时间
	private Date endTime;//所属区间结束时间
	
	private String startDate;
	private String endDate;
	
	private String storeName;//门店名称
	private List<Long> areaIds = new ArrayList<Long>();
	private List<Long> parentAreaIds = new ArrayList<Long>();
	
	private Double sjFee;//实际预提费用（按月份分摊后）
	private Long customId;
	private String projectName;
	
	private Long userId;//业务员ID
	
	@Override
	public String getKey() {
		return  Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				OutFeeProvision.class.getSimpleName()) : StringUtils.EMPTY ;
	}

	public Custom getCustom() {
		return custom;
	}

	public void setCustom(Custom custom) {
		this.custom = custom;
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

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ProvisionProject getProject() {
		return project;
	}

	public void setProject(ProvisionProject project) {
		this.project = project;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
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

	public Double getSjFee() {
		return sjFee;
	}

	public void setSjFee(Double sjFee) {
		this.sjFee = sjFee;
	}

	public Long getCustomId() {
		return customId;
	}

	public void setCustomId(Long customId) {
		this.customId = customId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
