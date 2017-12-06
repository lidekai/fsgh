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
 *	门店产品库存信息表 
 *
 */
@Entity
@Table(name = "info_store_product_stock")
public class StoreProductStock extends POSupport {

	private static final long serialVersionUID = 1102127326929373924L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storeProductId" )
	private StoreProduct storeProduct;
	
	@Column
	private Double count;//数量
	
	@Column
	private Double money;//金额
	
	@Column
	private String month;//月份2017-01

	public StoreProduct getStoreProduct() {
		return storeProduct;
	}

	public void setStoreProduct(StoreProduct storeProduct) {
		this.storeProduct = storeProduct;
	}

	public Double getCount() {
		return count;
	}

	public void setCount(Double count) {
		this.count = count;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				StoreProductStock.class.getSimpleName()) : StringUtils.EMPTY;
	}

}
