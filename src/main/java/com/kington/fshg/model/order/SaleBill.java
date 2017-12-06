package com.kington.fshg.model.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.IsType;
import com.kington.fshg.model.POSupport;
/**
 *  U8发票额
 *
 */
@Entity
@Table(name = "sale_bill")
public class SaleBill extends POSupport {

	private static final long serialVersionUID = -7514461072521113827L;
	
	@Column(length = 20)
	private String autoId;//销售发票子表Id 
	
	@Column(length = 100)
	private String csbvcode;//销售发票号
	
	private Date createDate;//制单时间
	
	private Date saleDate;//订单日期
	
	private Date deliverDate;//发货日期
	
	private String saleOrderId;//销售订单子表ID

	private String csoCode;//销售订单号
	
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
	
	private Double noTaxPrice;//本币无税金额
	
	@Column(length = 2)
	@Enumerated(EnumType.STRING)
	private IsType isRebate = IsType.Y;//是否返利
	

	public String getAutoId() {
		return autoId;
	}


	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}


	public String getCsbvcode() {
		return csbvcode;
	}


	public void setCsbvcode(String csbvcode) {
		this.csbvcode = csbvcode;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public Date getSaleDate() {
		return saleDate;
	}


	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}


	public Date getDeliverDate() {
		return deliverDate;
	}


	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}


	public String getSaleOrderId() {
		return saleOrderId;
	}


	public void setSaleOrderId(String saleOrderId) {
		this.saleOrderId = saleOrderId;
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


	public Double getCount() {
		return count;
	}


	public void setCount(Double count) {
		this.count = count;
	}


	public Double getLocalPrice() {
		return localPrice;
	}


	public void setLocalPrice(Double localPrice) {
		this.localPrice = localPrice;
	}


	public Double getCountPrice() {
		return countPrice;
	}


	public void setCountPrice(Double countPrice) {
		this.countPrice = countPrice;
	}


	public Double getNoTaxPrice() {
		return noTaxPrice;
	}


	public void setNoTaxPrice(Double noTaxPrice) {
		this.noTaxPrice = noTaxPrice;
	}


	public IsType getIsRebate() {
		return isRebate;
	}


	public void setIsRebate(IsType isRebate) {
		this.isRebate = isRebate;
	}


	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(), 
				SaleBill.class.getSimpleName()) : StringUtils.EMPTY;
	}

}
