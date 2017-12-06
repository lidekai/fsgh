package com.kington.fshg.model.info;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.POSupport;

/**
 *	门店信息表 
 */
@Entity
@Table (name = "info_store" )
public class Store extends POSupport {
	private static final long serialVersionUID = -4211706011015587151L;

	@Column (length = 15)
	private String storeCde;//门店编码
	
	@Column(length = 100)
	private String storeName;//门店名称
	
	@Column(length = 50)
	private String city;//所在城市
	
	@Column(length = 50)
	private String contacts;//联系人
	
	@Column(length = 50)
	private String contactsInfo;//联系方式
	
	@Column(length = 500)
	private String address;//地址
	
	private Double propertion;//销售额所占比例（%）
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "custom_id")
	private Custom custom;//所属客户
	
	// 门店存货
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "info_store_product", joinColumns = { @JoinColumn(name = "storeId") }, inverseJoinColumns = { @JoinColumn(name = "productId") })
	private List<Product> products = new ArrayList<Product>();
	
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
				Store.class.getSimpleName()) : StringUtils.EMPTY;
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

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Double getPropertion() {
		return propertion;
	}

	public void setPropertion(Double propertion) {
		this.propertion = propertion;
	}
	
}
