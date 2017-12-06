package com.kington.fshg.model.info;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.ChargeType;
import com.kington.fshg.model.VOSupport;

public class ProductVO extends VOSupport {
	private static final long serialVersionUID = -8971781256345669243L;

		private String productName;
		private String standard;
		private String stockCde;
		private String productCde;//存货代码
		private String number;
		private Double boxWeight = 0d;
		private Double length = 0d;
		private Double width = 0d;
		private Double height = 0d;
		private Double meterWeight = 0d;
		private ChargeType chargeType;
		private ProductType productType = new ProductType();
		private Double volume = 0d;
		private String remark;
		private Boolean newProduct; //是否新品 
		private Date startTime;   //启用时间
		private Double standardPrice;  //标准价
		private String unit;  //单位
		private String barCode; //条形码
		private String path;//图片地址 
		private Double quote ;  //报价
		

		
		
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

	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				Product.class.getSimpleName()) : StringUtils.EMPTY;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
