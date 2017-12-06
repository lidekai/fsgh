package com.kington.fshg.model.info;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.ChargeType;
import com.kington.fshg.model.POSupport;

/**
 *	产品信息表 
 *
 */

@Entity
@Table(name = "info_product")
public class Product extends POSupport {
	private static final long serialVersionUID = 4139443751338666049L;

	@Column(length = 100)
	private String stockCde;//存货编码
	
	@Column(length = 100)
	private String productCde;//存货代码
	
	@Column(length = 100)
	private String number;//货号
	
	@Column(length = 100)
	private String productName;//产品名称
	
	@Column(length = 200)
	private String standard;//产品规格
	
	@Column
	private Double boxWeight;//每箱毛重（公斤）
	
	@Column(name = "length")
	private Double length;//长度
	
	@Column(name = "width")
	private Double width;//宽度
	
	@Column(name = "height")
	private Double height;//高度
	
	@Column(name = "weight")
	private Double meterWeight;//每立方米重量(公斤)
	
	@Column(length = 50)
	@Enumerated(EnumType.STRING)
	private ChargeType chargeType;//计费形式
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productTypeId")
	private ProductType productType;//产品类型

	@Column(name = "volume")
	private Double volume;//每箱体积（立方米）
	
	@Column(name="remark")
	private String remark;  //备注
	
	@Column(name="newProduct")
	private Boolean newProduct; //是否新品 
	
	@Column(name="startTime")
	private Date startTime;   //启用时间
	
	@Column
	private Double standardPrice;  //标准价
	
	@Column
	private String unit;  //单位
	
	@Column
	private String barCode; //条形码
	
	@Column
	private String path;//图片地址 
	
	@Column
	private Double quote; //报价
	
	

	public Double getQuote() {
		return quote;
	}

	public void setQuote(Double quote) {
		this.quote = quote;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public Boolean getNewProduct() {
		return newProduct;
	}

	public void setNewProduct(Boolean newProduct) {
		this.newProduct = newProduct;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Double getStandardPrice() {
		return standardPrice;
	}

	public void setStandardPrice(Double standardPrice) {
		this.standardPrice = standardPrice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				Product.class.getSimpleName()) : StringUtils.EMPTY;
	}

	public String getStockCde() {
		return stockCde;
	}

	public void setStockCde(String stockCde) {
		this.stockCde = stockCde;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public Double getBoxWeight() {
		return boxWeight;
	}

	public void setBoxWeight(Double boxWeight) {
		this.boxWeight = boxWeight;
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getMeterWeight() {
		return meterWeight;
	}

	public void setMeterWeight(Double meterWeight) {
		this.meterWeight = meterWeight;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public ChargeType getChargeType() {
		return chargeType;
	}

	public void setChargeType(ChargeType chargeType) {
		this.chargeType = chargeType;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public String getProductCde() {
		return productCde;
	}

	public void setProductCde(String productCde) {
		this.productCde = productCde;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
