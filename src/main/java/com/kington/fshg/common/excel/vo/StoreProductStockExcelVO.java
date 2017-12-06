package com.kington.fshg.common.excel.vo;

import java.io.Serializable;

import com.kington.fshg.common.excel.ExcelAnnotation;

public class StoreProductStockExcelVO implements Serializable {

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
	
	@ExcelAnnotation(exportName = "数量")
	private Double count;
	
	@ExcelAnnotation(exportName = "年月")
	private String month;

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

	public Double getCount() {
		return count;
	}

	public void setCount(Double count) {
		this.count = count;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
	
}
