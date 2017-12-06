package com.kington.fshg.model.info;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.POSupport;

/**
 *	客户产品信息表 
 *
 */
@Entity
@Table(name = "info_custom_product")
public class CustomProduct extends POSupport {
	private static final long serialVersionUID = -5383322062008305968L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customId")
	private Custom custom;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productId" )
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
