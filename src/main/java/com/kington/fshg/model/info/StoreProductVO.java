package com.kington.fshg.model.info;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.VOSupport;

public class StoreProductVO extends VOSupport {

	private static final long serialVersionUID = 7610691291248886925L;
	
	private Product product;
	private Store store;
	private Double kaPrice;  //直销KA价
	private Double retailPrice; //终端零售价
	private List<Long> areaIds = new ArrayList<Long>();
	private List<Long> parentAreaIds = new ArrayList<Long>();
	
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

	public List<Long> getAreaIds() {
		return areaIds;
	}


	public void setAreaIds(List<Long> areaIds) {
		this.areaIds = areaIds;
	}


	public List<Long> getParentAreaIds() {
		return parentAreaIds;
	}


	public void setParentAreaIds(List<Long> parentAreaIds) {
		this.parentAreaIds = parentAreaIds;
	}
	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				StoreProductVO.class.getSimpleName()) : StringUtils.EMPTY;
	}

}
