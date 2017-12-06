package com.kington.fshg.common.excel.vo;

import java.io.Serializable;

import com.kington.fshg.common.excel.ExcelAnnotation;

public class CustomProductExcelVO implements Serializable {
	private static final long serialVersionUID = -7316568302054531869L;
	
	@ExcelAnnotation(exportName = "序号")
	private String cid;//序号

	@ExcelAnnotation(exportName = "客户编码")
	private String customCde; //客户编码

	@ExcelAnnotation(exportName = "存货编码")
	private String stockCde; //存货编码

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCustomCde() {
		return customCde;
	}

	public void setCustomCde(String customCde) {
		this.customCde = customCde;
	}

	public String getStockCde() {
		return stockCde;
	}

	public void setStockCde(String stockCde) {
		this.stockCde = stockCde;
	}

}
