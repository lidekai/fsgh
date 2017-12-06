package com.kington.fshg.model.info;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.VOSupport;

public class StoreProductStockVO extends VOSupport {

	private static final long serialVersionUID = 4926012381914389001L;

	private StoreProduct storeProduct;
	private Double count;//数量
	private Double money;//金额
	private String month;//月份
	private List<Long> areaIds = new ArrayList<Long>();
	private List<Long> parentAreaIds = new ArrayList<Long>();
	
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
				StoreProductStockVO.class.getSimpleName()) : StringUtils.EMPTY;
	}

}
