package com.kington.fshg.common.excel.vo;

import java.io.Serializable;

import com.kington.fshg.common.excel.ExcelAnnotation;

public class StoreSaleProExcelVO implements Serializable {

	private static final long serialVersionUID = -3119462398982862244L;

	@ExcelAnnotation(exportName = "序号")
	private String cid;//序号
	
	@ExcelAnnotation(exportName = "门店编码")
	private String storeCde;
	
	@ExcelAnnotation(exportName = "门店名称")
	private String storeName;
	
	@ExcelAnnotation(exportName = "销售额")
	private String saleSum;
	
	@ExcelAnnotation(exportName = "固定金额")
	private String fixSum;
	
	@ExcelAnnotation(exportName = "预提日期")
	private String provisionTime;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getStoreCde() {
		return storeCde;
	}

	public void setStoreCde(String storeCde) {
		this.storeCde = storeCde;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getSaleSum() {
		return saleSum;
	}

	public void setSaleSum(String saleSum) {
		this.saleSum = saleSum;
	}

	public String getFixSum() {
		return fixSum;
	}

	public void setFixSum(String fixSum) {
		this.fixSum = fixSum;
	}

	public String getProvisionTime() {
		return provisionTime;
	}

	public void setProvisionTime(String provisionTime) {
		this.provisionTime = provisionTime;
	}
	
}
