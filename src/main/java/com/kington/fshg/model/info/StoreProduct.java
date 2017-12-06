package com.kington.fshg.model.info;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.POSupport;
/**
 *	门店产品信息表 
 *
 */
@Entity
@Table(name = "info_store_product")
public class StoreProduct extends POSupport {

	private static final long serialVersionUID = -2358472166697858083L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productId" )
	private Product product;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storeId" )
	private Store store;
	
	
	@Column
	private Double kaPrice;  //直销KA价
	
	@Column
	private Double retailPrice; //终端零售价
	

	public Product getProduct() {
		return product;
	}



	public void setProduct(Product product) {
		this.product = product;
	}



	public Store getStore() {
		return store;
	}



	public void setStore(Store store) {
		this.store = store;
	}





	public Double getKaPrice() {
		return kaPrice;
	}



	public void setKaPrice(Double kaPrice) {
		this.kaPrice = kaPrice;
	}



	public Double getRetailPrice() {
		return retailPrice;
	}



	public void setRetailPrice(Double retailPrice) {
		this.retailPrice = retailPrice;
	}



	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				StoreProduct.class.getSimpleName()) : StringUtils.EMPTY;
	}

}
