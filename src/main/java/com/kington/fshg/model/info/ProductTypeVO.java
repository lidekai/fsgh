package com.kington.fshg.model.info;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.VOSupport;

public class ProductTypeVO extends VOSupport {
	private static final long serialVersionUID = 8305344417591067027L;
	
	private String productTypeName;
	private ProductType productType;
	private Integer lev;// 级别，排序

	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				ProductType.class.getSimpleName()) : StringUtils.EMPTY; 
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public Integer getLev() {
		return lev;
	}

	public void setLev(Integer lev) {
		this.lev = lev;
	}

}
