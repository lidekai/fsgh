package com.kington.fshg.common.excel.vo;

import java.io.Serializable;

import com.kington.fshg.common.excel.ExcelAnnotation;

/**
 * 地区导入模板
 * @author  xn
 * @date  2016-12-19
 */
public class AreaExcelVO implements Serializable {
	private static final long serialVersionUID = -397519574562105546L;

	@ExcelAnnotation(exportName = "序号")
	private String cid;//序号

	@ExcelAnnotation(exportName = "所属大区")
	private String parentArea; //所属地区

	@ExcelAnnotation(exportName = "地区名称")
	private String areaName;//地区名称

	@ExcelAnnotation(exportName = "级别")
	private String lev; //级别

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getLev() {
		return lev;
	}

	public void setLev(String lev) {
		this.lev = lev;
	}

	public String getParentArea() {
		return parentArea;
	}

	public void setParentArea(String parentArea) {
		this.parentArea = parentArea;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

}
