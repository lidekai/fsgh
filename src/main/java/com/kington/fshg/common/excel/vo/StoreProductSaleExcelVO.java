package com.kington.fshg.common.excel.vo;

import java.io.Serializable;

import com.kington.fshg.common.excel.ExcelAnnotation;

public class StoreProductSaleExcelVO implements Serializable {

	private static final long serialVersionUID = 1210063752235457874L;

	@ExcelAnnotation(exportName = "序号")
	private String cid;//序号
	
	@ExcelAnnotation(exportName = "门店名称")
	private String storeName;
	
	@ExcelAnnotation(exportName = "所属客户名称")
	private String customName;
	
	@ExcelAnnotation(exportName = "存货编码")
	private String stockCde;
	
	@ExcelAnnotation(exportName = "存货名称")
	private String productName;
	
	@ExcelAnnotation(exportName = "销量")
	private Double saleCount;
	
	@ExcelAnnotation(exportName = "终端零售价销售额")
	private Double retailSaleMoney;
	
	@ExcelAnnotation(exportName = "年月")
	private String yearMonth;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getSaleCount() {
		return saleCount;
	}

	public void setSaleCount(Double saleCount) {
		this.saleCount = saleCount;
	}

	public Double getRetailSaleMoney() {
		return retailSaleMoney;
	}

	public void setRetailSaleMoney(Double retailSaleMoney) {
		this.retailSaleMoney = retailSaleMoney;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

}
