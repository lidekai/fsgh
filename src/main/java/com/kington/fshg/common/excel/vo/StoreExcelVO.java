package com.kington.fshg.common.excel.vo;

import java.io.Serializable;

import com.kington.fshg.common.excel.ExcelAnnotation;

public class StoreExcelVO implements Serializable {
	private static final long serialVersionUID = 6357243022183474358L;

	@ExcelAnnotation(exportName = "序号")
	private String cid;//序号
	
	@ExcelAnnotation(exportName = "门店编码")
	private String storeCde;
	
	@ExcelAnnotation(exportName = "门店名称")
	private String storeName;
	
	@ExcelAnnotation(exportName = "所属客户编码")
	private String customCde;
	
	@ExcelAnnotation(exportName = "所在城市")
	private String city;
	
	@ExcelAnnotation(exportName = "提成比例")
	private String propertion;
	
	@ExcelAnnotation(exportName = "联系人")
	private String contacts;
	
	@ExcelAnnotation(exportName = "联系电话")
	private String contactsInfo;
	
	@ExcelAnnotation(exportName = "地址")
	private String address;
	
	@ExcelAnnotation(exportName = "备注")
	private String remark;
	
	

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

	public String getStoreCde() {
		return storeCde;
	}

	public void setStoreCde(String storeCde) {
		this.storeCde = storeCde;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getCustomCde() {
		return customCde;
	}

	public void setCustomCde(String customCde) {
		this.customCde = customCde;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getContactsInfo() {
		return contactsInfo;
	}

	public void setContactsInfo(String contactsInfo) {
		this.contactsInfo = contactsInfo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPropertion() {
		return propertion;
	}

	public void setPropertion(String propertion) {
		this.propertion = propertion;
	}
	
}
