package com.kington.fshg.model.info;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import com.kington.fshg.common.Common;
import com.kington.fshg.model.VOSupport;

public class StoreVO extends VOSupport {
	private static final long serialVersionUID = 982535628263382144L;

	private String storeCde;
	private String storeName;
	private String city;
	private String contacts;
	private String contactsInfo;
	private String address;
	private Custom custom;
	private String remark;
	private Double propertion;//销售额所占比例（%）
	
	private List<Long> areaIds = new ArrayList<Long>();
	private List<Long> parentAreaIds = new ArrayList<Long>();


	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Custom getCustom() {
		return custom;
	}

	public void setCustom(Custom custom) {
		this.custom = custom;
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
	
	public Double getPropertion() {
		return propertion;
	}

	public void setPropertion(Double propertion) {
		this.propertion = propertion;
	}

	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				Store.class.getSimpleName()) : StringUtils.EMPTY;
	}

}
