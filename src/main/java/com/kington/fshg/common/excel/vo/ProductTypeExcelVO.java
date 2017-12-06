package com.kington.fshg.common.excel.vo;

import java.io.Serializable;

import com.kington.fshg.common.excel.ExcelAnnotation;

/**
 * 产品分类导入模板
 * @author  xn
 * @date  2016-12-19
 */
public class ProductTypeExcelVO implements Serializable {
	private static final long serialVersionUID = 4082661962999258909L;
	
	@ExcelAnnotation(exportName = "序号")
	private String cid;//序号

	@ExcelAnnotation(exportName = "所属分类")
	private String productType; //所属分类

	@ExcelAnnotation(exportName = "分类名称")
	private String productTypeName;//分类名称

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

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

}
