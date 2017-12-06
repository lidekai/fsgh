package com.kington.fshg.model.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.VOSupport;
import com.kington.fshg.model.info.Area;

public class SaleOrderVO extends VOSupport {

	private static final long serialVersionUID = 7813364569039557921L;
	
	private Long autoId;//销售订单子表ID
	private String csoCode;//销售订单号
	private Date orderDate;//订单日期
	private String orderDateStart;
	private String orderDateEnd;
	private String customCde;//客户编码
	private String customName;//客户名称
	private String stockCde;//存货编号
	private String productCde;//存货代码
	private String productName;//存货名称
	private String standard;//存货规格
	private Double count;//数量
	private Double localPrice;//本币含税单价
	private Double countPrice;//本币价税合计
	
	private Long userId;//业务员id
	private Area area;//区域id
	
	private List<Long> areaIds = new ArrayList<Long>();
	private List<Long> parentAreaIds = new ArrayList<Long>();
	
	public Long getAutoId() {
		return autoId;
	}



	public void setAutoId(Long autoId) {
		this.autoId = autoId;
	}



	public String getCsoCode() {
		return csoCode;
	}



	public void setCsoCode(String csoCode) {
		this.csoCode = csoCode;
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



	public String getStockCde() {
		return stockCde;
	}



	public void setStockCde(String stockCde) {
		this.stockCde = stockCde;
	}



	public String getProductCde() {
		return productCde;
	}



	public void setProductCde(String productCde) {
		this.productCde = productCde;
	}



	public String getProductName() {
		return productName;
	}



	public void setProductName(String productName) {
		this.productName = productName;
	}



	public String getStandard() {
		return standard;
	}



	public void setStandard(String standard) {
		this.standard = standard;
	}


	public Double getCountPrice() {
		return countPrice;
	}



	public void setCountPrice(Double countPrice) {
		this.countPrice = countPrice;
	}


	public Long getUserId() {
		return userId;
	}



	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public Area getArea() {
		return area;
	}



	public void setArea(Area area) {
		this.area = area;
	}



	public Double getCount() {
		return count;
	}



	public void setCount(Double count) {
		this.count = count;
	}



	public Date getOrderDate() {
		return orderDate;
	}



	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}



	public Double getLocalPrice() {
		return localPrice;
	}



	public void setLocalPrice(Double localPrice) {
		this.localPrice = localPrice;
	}


	public String getOrderDateStart() {
		return orderDateStart;
	}



	public void setOrderDateStart(String orderDateStart) {
		this.orderDateStart = orderDateStart;
	}



	public String getOrderDateEnd() {
		return orderDateEnd;
	}



	public void setOrderDateEnd(String orderDateEnd) {
		this.orderDateEnd = orderDateEnd;
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

	@Override
	public String toString() {
		return "SaleOrderVO [autoId=" + autoId + ", csoCode=" + csoCode + ", orderDate=" + orderDate
				+ ", orderDateStart=" + orderDateStart + ", orderDateEnd=" + orderDateEnd + ", customCde=" + customCde
				+ ", customName=" + customName + ", stockCde=" + stockCde + ", productCde=" + productCde
				+ ", productName=" + productName + ", standard=" + standard + ", count=" + count + ", localPrice="
				+ localPrice + ", countPrice=" + countPrice + ", userId=" + userId + ", area=" + area + "]";
	}



	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(), SaleOrderVO.class.getSimpleName()) : StringUtils.EMPTY;
	}

}
