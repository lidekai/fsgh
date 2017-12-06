package com.kington.fshg.model.info;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.VOSupport;

public class StoreProductSaleVO extends VOSupport {

	private static final long serialVersionUID = -6084616985147600823L;
	
	private StoreProduct storeProduct;
	private Double saleCount;//销量
	private Double standardSaleMoney;//标准价销售额（标准价*销量）
	private Double retailSaleMoney;//终端零售价销售额（录入）
	private String yearMonth;//月份2017-01
	private List<Long> areaIds = new ArrayList<Long>();
	private List<Long> parentAreaIds = new ArrayList<Long>();
	private Long userId;//业务员ID
	
	private String startMonth;
	private String endMonth;
	private Long areaId;
	private Long parentAreaId;
	private String customName;
	private String storeName;
	private String productName;
	
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

	public String getStartMonth() {
		return startMonth;
	}


	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}


	public String getEndMonth() {
		return endMonth;
	}


	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public Long getAreaId() {
		return areaId;
	}


	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}


	public Long getParentAreaId() {
		return parentAreaId;
	}


	public void setParentAreaId(Long parentAreaId) {
		this.parentAreaId = parentAreaId;
	}


	public String getCustomName() {
		return customName;
	}


	public void setCustomName(String customName) {
		this.customName = customName;
	}


	public String getStoreName() {
		return storeName;
	}


	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				StoreProductSaleVO.class.getSimpleName()) : StringUtils.EMPTY;
	}

}
