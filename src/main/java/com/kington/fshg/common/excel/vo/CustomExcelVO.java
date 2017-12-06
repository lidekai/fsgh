package com.kington.fshg.common.excel.vo;

import java.io.Serializable;
import com.kington.fshg.common.excel.ExcelAnnotation;
public class CustomExcelVO implements Serializable {
	private static final long serialVersionUID = 6053808585688946820L;

	@ExcelAnnotation(exportName="序号")
	private String cid;//序号
	
	@ExcelAnnotation(exportName ="客户编码")
	private String customCde;//客户编号
	
	@ExcelAnnotation(exportName="客户名称")
	private String customName;//客户名称
	
	@ExcelAnnotation(exportName ="所属地区")
	private String areaName;//所属地区
	
	@ExcelAnnotation(exportName ="所属大区")
	private String parentAreaName;//所属地区
	
	@ExcelAnnotation(exportName ="客户状态")
	private String state;//客户状态
	
	@ExcelAnnotation(exportName="业务员账号")
	private String userCode;//业务员账号
	
	@ExcelAnnotation(exportName="铺底额")
	private String amount;//铺底额度
	
	@ExcelAnnotation(exportName="账期")
	private String accountDay;//账期
	
	@ExcelAnnotation(exportName="合作起始日期")
	private String beginTime;//合作起始日期
	
	@ExcelAnnotation(exportName="合作终止日期")
	private String endTime;//合作终止日期
	
	@ExcelAnnotation(exportName="联系人")
	private String contacts;//联系人
	
	@ExcelAnnotation(exportName="联系方式")
	private String contactInfo;//联系方式
	
	@ExcelAnnotation(exportName="地址")
	private String address;//地址
	
	@ExcelAnnotation(exportName="泡货单价")
	private String cargoPrice;//泡货单价
	
	@ExcelAnnotation(exportName="重货单价")
	private String heavyPrice;//重货单价
	
	@ExcelAnnotation(exportName="按件单价")
	private String unitPrice;//按件单价
	
	@ExcelAnnotation(exportName="配送费")
	private String deliverFee;//配送费
	
	@ExcelAnnotation(exportName="客户类型")
	private String customTypeName;//客户类型
	
	@ExcelAnnotation(exportName="地区经理")
	private String areaManager;//地区经理
	
	@ExcelAnnotation(exportName="大区经理")
	private String regManager;//大区经理
	
	@ExcelAnnotation(exportName="备注")
	private String remark;
	
	@ExcelAnnotation(exportName ="省份")
	private String province;//省份
	
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

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

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAccountDay() {
		return accountDay;
	}

	public void setAccountDay(String accountDay) {
		this.accountDay = accountDay;
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

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCargoPrice() {
		return cargoPrice;
	}

	public void setCargoPrice(String cargoPrice) {
		this.cargoPrice = cargoPrice;
	}

	public String getHeavyPrice() {
		return heavyPrice;
	}

	public void setHeavyPrice(String heavyPrice) {
		this.heavyPrice = heavyPrice;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getDeliverFee() {
		return deliverFee;
	}

	public void setDeliverFee(String deliverFee) {
		this.deliverFee = deliverFee;
	}

	public String getCustomTypeName() {
		return customTypeName;
	}

	public void setCustomTypeName(String customTypeName) {
		this.customTypeName = customTypeName;
	}

	public String getAreaManager() {
		return areaManager;
	}

	public void setAreaManager(String areaManager) {
		this.areaManager = areaManager;
	}

	public String getRegManager() {
		return regManager;
	}

	public void setRegManager(String regManager) {
		this.regManager = regManager;
	}

	public String getParentAreaName() {
		return parentAreaName;
	}

	public void setParentAreaName(String parentAreaName) {
		this.parentAreaName = parentAreaName;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
}
