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
 *	门店产品销售信息表 
 *
 */
@Entity
@Table(name = "info_store_product_sale")
public class StoreProductSale extends POSupport {

	private static final long serialVersionUID = -3572551916288853888L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storeProductId" )
	private StoreProduct storeProduct;
	
	@Column
	private Double saleCount;//销量
	
	@Column
	private Double standardSaleMoney;//标准价销售额（标准价*销量）
	
	@Column
	private Double retailSaleMoney;//终端零售价销售额（录入）
	
	@Column
	private String yearMonth;//月份2017-01
	
	
	public StoreProduct getStoreProduct() {
		return storeProduct;
	}



	public void setStoreProduct(StoreProduct storeProduct) {
		this.storeProduct = storeProduct;
	}



	public Double getSaleCount() {
		return saleCount;
	}



	public void setSaleCount(Double saleCount) {
		this.saleCount = saleCount;
	}



	public Double getStandardSaleMoney() {
		return standardSaleMoney;
	}



	public void setStandardSaleMoney(Double standardSaleMoney) {
		this.standardSaleMoney = standardSaleMoney;
	}



	public Double getRetailSaleMoney() {
		return retailSaleMoney;
	}



	public void setRetailSaleMoney(Double retailSaleMoney) {
		this.retailSaleMoney = retailSaleMoney;
	}



	public String getYearMonth() {
		return yearMonth;
	}



	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}



	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				StoreProductSale.class.getSimpleName()) : StringUtils.EMPTY;
	}

}
