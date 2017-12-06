package com.kington.fshg.common.excel.vo;

import java.io.Serializable;

import com.kington.fshg.common.excel.ExcelAnnotation;

public class PromotersExcelVO implements Serializable {
	private static final long serialVersionUID = 5990124647869679479L;

	@ExcelAnnotation(exportName = "序号")
	private String cid;//序号
	
	@ExcelAnnotation(exportName = "身份证号")
	private String IDCare;//身份证号
	
	@ExcelAnnotation(exportName = "促销员名称")
	private String promotersName;
	
	@ExcelAnnotation(exportName = "所属门店编码")
	private String storeCde;
	
	@ExcelAnnotation(exportName = "所属客户编码")
	private String customCde;
	
	@ExcelAnnotation(exportName = "银行卡号")
	private String bankCare;
	
	@ExcelAnnotation(exportName = "开户地")
	private String bankLocal;
	
	@ExcelAnnotation(exportName = "联系电话")
	private String contactsInfo;
	
	@ExcelAnnotation(exportName = "备注")
	private String remark;
	
	
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getIDCare() {
		return IDCare;
	}

	public void setIDCare(String iDCare) {
		IDCare = iDCare;
	}

	public String getPromotersName() {
		return promotersName;
	}

	public void setPromotersName(String promotersName) {
		this.promotersName = promotersName;
	}


	public String getStoreCde() {
		return storeCde;
	}

	public void setStoreCde(String storeCde) {
		this.storeCde = storeCde;
	}

	public String getCustomCde() {
		return customCde;
	}

	public void setCustomCde(String customCde) {
		this.customCde = customCde;
	}

	public String getBankCare() {
		return bankCare;
	}

	public void setBankCare(String bankCare) {
		this.bankCare = bankCare;
	}

	public String getBankLocal() {
		return bankLocal;
	}

	public void setBankLocal(String bankLocal) {
		this.bankLocal = bankLocal;
	}

	public String getContactsInfo() {
		return contactsInfo;
	}

	public void setContactsInfo(String contactsInfo) {
		this.contactsInfo = contactsInfo;
	}
	
	
}
