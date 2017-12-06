package com.kington.fshg.model.budget;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.VOSupport;
import com.kington.fshg.model.info.Store;

public class StoreSaleProvisionVO extends VOSupport {

	private static final long serialVersionUID = -1839624168255113721L;
	
	private Store store;
	private Date provisionTime;//预提时间
	private Double saleSum;//销售额
	private Double fixSum;//固定金额
	private Double count;//预提费用
	
	private String dateStart;
	private String dateEnd;
	private List<Long> areaIds = new ArrayList<Long>();
	private List<Long> parentAreaIds = new ArrayList<Long>();
	
	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Date getProvisionTime() {
		return provisionTime;
	}

	public void setProvisionTime(Date provisionTime) {
		this.provisionTime = provisionTime;
	}

	public Double getSaleSum() {
		return saleSum;
	}

	public void setSaleSum(Double saleSum) {
		this.saleSum = saleSum;
	}

	public Double getFixSum() {
		return fixSum;
	}

	public void setFixSum(Double fixSum) {
		this.fixSum = fixSum;
	}

	public Double getCount() {
		return count;
	}

	public void setCount(Double count) {
		this.count = count;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
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
				StoreSaleProvisionVO.class.getSimpleName()) : StringUtils.EMPTY ;
	}

}
