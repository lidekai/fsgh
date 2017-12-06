package com.kington.fshg.common.excel.vo;

import java.io.Serializable;

import com.kington.fshg.common.excel.ExcelAnnotation;

public class StoreProductExcelVO implements Serializable {
	private static final long serialVersionUID = 7349257105971090274L;

	@ExcelAnnotation(exportName = "序号")
	private String cid;//序号
	
	@ExcelAnnotation(exportName = "客户名称")
	private String customName;
	
	@ExcelAnnotation(exportName = "门店名称")
	private String storeName;
	
	@ExcelAnnotation(exportName = "存货编码")
	private String stockCde;
	
	@ExcelAnnotation(exportName = "存货名称")
	private String productName;
	
	@ExcelAnnotation(exportName = "直销KA价")
	private String kaPrice;
	
	@ExcelAnnotation(exportName = "终端零售价")
	private String retailPrice;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
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

	public String getKaPrice() {
		return kaPrice;
	}

	public void setKaPrice(String kaPrice) {
		this.kaPrice = kaPrice;
	}

	public String getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(String retailPrice) {
		this.retailPrice = retailPrice;
	}
	
}
