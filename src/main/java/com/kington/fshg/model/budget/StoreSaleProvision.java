package com.kington.fshg.model.budget;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.POSupport;
import com.kington.fshg.model.info.Store;
/**
 *	门店销售预提
 *
 */
@Entity
@Table(name = "bud_store_sale_provision")
public class StoreSaleProvision extends POSupport {

	private static final long serialVersionUID = 2671993121698538854L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storeId" )
	private Store store;
	
	@Column
	private Date provisionTime;//预提时间

	@Column
	private Double saleSum;//销售额
	
	@Column
	private Double fixSum;//固定金额
	
	@Column
	private Double count;//预提费用

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

	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				StoreSaleProvision.class.getSimpleName()) : StringUtils.EMPTY;
	}

}
