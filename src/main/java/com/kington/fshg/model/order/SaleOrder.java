package com.kington.fshg.model.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.POSupport;
/**
 * 销售订单
 */
@Entity
@Table(name = "order_sale")
public class SaleOrder extends POSupport {

	private static final long serialVersionUID = 74846628310708844L;

	@Column(length = 20)
	private String autoId;//销售订单子表ID
	
	@Column(length = 100)
	private String csoCode;//销售订单号
	
	private Date orderDate;//订单日期
	
	@Column(length = 100)
	private String customCde;//客户编码
	
	@Column(length = 100)
	private String customName;//客户名称
	
	@Column(length = 100)
	private String stockCde;//存货编号
	
	@Column(length = 100)
	private String productCde;//存货代码
	
	@Column(length = 100)
	private String productName;//存货名称
	
	@Column(length = 200)
	private String standard;//存货规格
	
	private Double count;//数量
	
	private Double localPrice;//本币含税单价
	
	private Double countPrice;//本币价税合计
	

	public String getAutoId() {
		return autoId;
	}



	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}



	public String getCsoCode() {
		return csoCode;
	}



	public void setCsoCode(String csoCode) {
		this.csoCode = csoCode;
	}



	public Date getOrderDate() {
		return orderDate;
	}



	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
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



	public Double getLocalPrice() {
		return localPrice;
	}



	public void setLocalPrice(Double localPrice) {
		this.localPrice = localPrice;
	}



	public Double getCount() {
		return count;
	}



	public void setCount(Double count) {
		this.count = count;
	}



	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(), SaleOrder.class.getSimpleName()) : StringUtils.EMPTY;
	}

}
