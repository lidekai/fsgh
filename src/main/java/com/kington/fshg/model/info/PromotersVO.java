package com.kington.fshg.model.info;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.VOSupport;

public class PromotersVO extends VOSupport {
	private static final long serialVersionUID = 6065096229638060662L;

	private String promotersName;
	private String contactsInfo;
	private String IDCare;
	private Long bankCare;
	private String bankLocal;
	private Store store;
	private Custom custom;
	private String remark;
	private List<Long> areaIds = new ArrayList<Long>();
	private List<Long> parentAreaIds = new ArrayList<Long>();
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				Promoters.class.getSimpleName()) : StringUtils.EMPTY;
	}

	public String getPromotersName() {
		return promotersName;
	}

	public void setPromotersName(String promotersName) {
		this.promotersName = promotersName;
	}

	public String getContactsInfo() {
		return contactsInfo;
	}

	public void setContactsInfo(String contactsInfo) {
		this.contactsInfo = contactsInfo;
	}

	public String getIDCare() {
		return IDCare;
	}

	public void setIDCare(String iDCare) {
		IDCare = iDCare;
	}

	public Long getBankCare() {
		return bankCare;
	}

	public void setBankCare(Long bankCare) {
		this.bankCare = bankCare;
	}

	public String getBankLocal() {
		return bankLocal;
	}

	public void setBankLocal(String bankLocal) {
		this.bankLocal = bankLocal;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
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
}
