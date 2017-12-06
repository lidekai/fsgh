package com.kington.fshg.common.excel.vo;

import java.io.Serializable;

import com.kington.fshg.common.excel.ExcelAnnotation;

/**
 * 产品导入模板
 * @author  xn
 * @date  2016-12-21
 */
public class ProductExcelVO implements Serializable {
	private static final long serialVersionUID = -1992031701854244543L;

	@ExcelAnnotation(exportName = "序号")
	private String cid;//序号

	@ExcelAnnotation(exportName = "存货编码")
	private String stockCde;//存货编码
	
	@ExcelAnnotation(exportName = "存货代码")
	private String productCde;//存货代码
	
	@ExcelAnnotation(exportName = "货号")
	private String number;//货号
	
	@ExcelAnnotation(exportName = "所属分类")
	private String productType;//所属分类
	
	@ExcelAnnotation(exportName = "产品名称")
	private String productName;//产品名称
	
	@ExcelAnnotation(exportName = "产品规格")
	private String standard;//产品规格
	
	@ExcelAnnotation(exportName = "每箱毛重")
	private String boxWeight;//每箱毛重（公斤）
	
	@ExcelAnnotation(exportName = "纸箱长")
	private String length;//长度
	
	@ExcelAnnotation(exportName = "纸箱宽")
	private String width;//宽度
	
	@ExcelAnnotation(exportName = "纸箱高")
	private String height;//高度
	
	/*不再导入@ExcelAnnotation(exportName = "每立方米重量")
	private String meterWeight;//每立方米重量(公斤)
*/	
	@ExcelAnnotation(exportName = "备注")
	private String remark;
	
	@ExcelAnnotation(exportName = "启用时间")
	private String startTime;   //启用时间
	
	@ExcelAnnotation(exportName = "是否新品")
	private String newProduct; //是否新品 
	
	@ExcelAnnotation(exportName = "标准价")
	private String standardPrice;  //标准价
	
	@ExcelAnnotation(exportName = "报价")
	private String quote;  //报价
	
	@ExcelAnnotation(exportName = "单位")
	private String unit; 
	
	@ExcelAnnotation(exportName = "条形码")
	private String barCode;  
	
	
	
	
	public String getQuote() {
		return quote;
	}
	public void setQuote(String quote) {
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
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getNewProduct() {
		return newProduct;
	}
	public void setNewProduct(String newProduct) {
		this.newProduct = newProduct;
	}
	public String getStandardPrice() {
		return standardPrice;
	}
	public void setStandardPrice(String standardPrice) {
		this.standardPrice = standardPrice;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
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

	public String getBoxWeight() {
		return boxWeight;
	}

	public void setBoxWeight(String boxWeight) {
		this.boxWeight = boxWeight;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

/*	public String getMeterWeight() {
		return meterWeight;
	}

	public void setMeterWeight(String meterWeight) {
		this.meterWeight = meterWeight;
	}*/

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
	
}
