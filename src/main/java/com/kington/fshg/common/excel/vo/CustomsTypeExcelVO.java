package com.kington.fshg.common.excel.vo;

import java.io.Serializable;

import com.kington.fshg.common.excel.ExcelAnnotation;

public class CustomsTypeExcelVO implements Serializable {
	private static final long serialVersionUID = 2913604947141310292L;

	@ExcelAnnotation(exportName = "序号")
	private String cid;//序号

	@ExcelAnnotation(exportName = "所属分类")
	private String customsType; //所属分类

	@ExcelAnnotation(exportName = "分类名称")
	private String customsTypeName;//分类名称

	@ExcelAnnotation(exportName = "排序")
	private String lev; //级别

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCustomsType() {
		return customsType;
	}

	public void setCustomsType(String customsType) {
		this.customsType = customsType;
	}

	public String getCustomsTypeName() {
		return customsTypeName;
	}

	public void setCustomsTypeName(String customsTypeName) {
		this.customsTypeName = customsTypeName;
	}

	public String getLev() {
		return lev;
	}

	public void setLev(String lev) {
		this.lev = lev;
	}
	
	
}
