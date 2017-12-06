package com.kington.fshg.model.info;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.VOSupport;

public class CustomProductVO extends VOSupport {
	private static final long serialVersionUID = -742484001013039112L;
	
	private Custom custom;
	private Product product;

	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				CustomProduct.class.getSimpleName()) : StringUtils.EMPTY;
	}

	public Custom getCustom() {
		return custom;
	}

	public void setCustom(Custom custom) {
		this.custom = custom;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
