package com.kington.fshg.model.info;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.POSupport;

/**
 *  促销员信息表 
 *
 */

@Entity
@Table(name = "info_promoters" )
public class Promoters extends POSupport {
	private static final long serialVersionUID = 8753887038930082905L;

	@Column(length = 50)
	private String promotersName;//姓名
	
	@Column(length = 50)
	private String contactsInfo;//联系方式
	
	@Column(length = 20)
	private String IDCare;//身份证号码
	
	@Column(length = 50)
	private Long bankCare;//银行卡号
	
	@Column(length = 500)
	private String bankLocal;//开户地
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="store_id")
	private Store store;//所属门店
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "custom_id")
	private Custom custom;//所属客户
	
	@Column
	private String remark;
	
	
	
	
	
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
	
	

}
