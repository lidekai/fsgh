package com.kington.fshg.model.info;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.CustomerState;
import com.kington.fshg.model.VOSupport;
import com.kington.fshg.model.system.User;

public class CustomVO extends VOSupport {
	private static final long serialVersionUID = 1234827176322810283L;

	private String customCde;//客户编号
	private String customName;//客户名称
	private Area area;//所属地区
	private CustomerState state;//客户状态
	private Double amount;//铺底额度
	private Integer accountDay;//账期
	private Date beginTime;//合作起始日期
	private Date endTime;//合作终止日期
	private String contacts;//联系人
	private String contactInfo;//联系方式
	private String address;//地址
	private Double cargoPrice;//泡货单价
	private Double heavyPrice;//重货单价
	private Double unitPrice;//按件单价
	private Double deliverFee;//配送费
	private CustomsType customType;//客户类型
	private User user;//所属业务员
	private String areaManager;//地区经理
	private String regManager;//大区经理
	private List<Product> products = new ArrayList<Product>();
	private String stockCde;//存货编码
	private String productName;//产品名称
	private String remark;
	private List<Long> areaIds = new ArrayList<Long>();
	private List<Long> parentAreaIds = new ArrayList<Long>();
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
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}


	public String getStockCde() {
		return stockCde;
	}

	public void setStockCde(String stockCde) {
		this.stockCde = stockCde;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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


	public String getProvince() {
		return province;
	}


	public void setProvince(String province) {
		this.province = province;
	}
	
}
