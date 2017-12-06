package com.kington.fshg.model.info;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.CustomerState;
import com.kington.fshg.model.POSupport;
import com.kington.fshg.model.system.User;

/**
 *	客户信息表 
 */

@Entity
@Table(name = "info_custom")
public class Custom extends POSupport {
	private static final long serialVersionUID = 861656073665072502L;

	@Column(length = 100)
	private String customCde;//客户编号
	
	@Column(length = 100)
	private String customName;//客户名称
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "areaId")
	private Area area;//所属地区
	
	@Column(length = 20)
	@Enumerated(EnumType.STRING)
	private CustomerState state;//客户状态
	
	@Column(length = 10)
	private Double amount;//铺底额度
	
	@Column(length = 11)
	private Integer accountDay;//账期
	
	@Column
	private Date beginTime;//合作起始日期
	
	@Column
	private Date endTime;//合作终止日期
	
	@Column(length = 50)
	private String contacts;//联系人
	
	@Column(length = 50)
	private String contactInfo;//联系方式
	
	@Column(length = 500)
	private String address;//地址
	
	@Column(length = 10)
	private Double cargoPrice;//泡货单价
	
	@Column(length = 10)
	private Double heavyPrice;//重货单价
	
	@Column(length = 10)
	private Double unitPrice;//按件单价
	
	@Column
	private Double deliverFee;//配送费
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "custom_type")
	private CustomsType customType;//客户类型
	
	// 客户存货
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "info_custom_product", joinColumns = { @JoinColumn(name = "customId") }, inverseJoinColumns = { @JoinColumn(name = "productId") })
	private List<Product> products = new ArrayList<Product>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="userId")
	private User user;//所属业务员
	
	@Column(length = 20)
	private String areaManager;//地区经理
	
	@Column(length = 20)
	private String regManager;//大区经理
	
	@Column
	private String remark;
	
	@Column
	private String province;//省份
	
	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				Custom.class.getSimpleName()) : StringUtils.EMPTY;
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

	public CustomerState getState() {
		return state;
	}

	public void setState(CustomerState state) {
		this.state = state;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getAccountDay() {
		return accountDay;
	}

	public void setAccountDay(Integer accountDay) {
		this.accountDay = accountDay;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
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

	public Double getCargoPrice() {
		return cargoPrice;
	}

	public void setCargoPrice(Double cargoPrice) {
		this.cargoPrice = cargoPrice;
	}

	public Double getHeavyPrice() {
		return heavyPrice;
	}

	public void setHeavyPrice(Double heavyPrice) {
		this.heavyPrice = heavyPrice;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public CustomsType getCustomType() {
		return customType;
	}

	public void setCustomType(CustomsType customType) {
		this.customType = customType;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
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


	public Double getDeliverFee() {
		return deliverFee;
	}


	public void setDeliverFee(Double deliverFee) {
		this.deliverFee = deliverFee;
	}


	public String getProvince() {
		return province;
	}


	public void setProvince(String province) {
		this.province = province;
	}
	
}
