package com.kington.fshg.model.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.POSupport;

/**
 *	仓库调拨单信息表 
 *
 */

@Entity
@Table(name = "order_transfer")
public class Transfer extends POSupport {
	private static final long serialVersionUID = 6736036732216970800L;

	@Column(length = 20)
	private String autoId;//调拨子表ID
	
	@Column(length = 100)
	private String transCode;//调拨单号
	
	private Date transDate;//调拨日期
	
	@Column(length = 100)
	private String outWhouseCode;//调出仓库编号
	
	@Column(length =100)
	private String outWhouseName;//调出仓库名称
	
	@Column(length = 100)
	private String inWhouseCode;//调入仓库编号
	
	@Column(length = 100)
	private String inWhouseName;//调入仓库名称
	
	@Column(length =100)
	private String stockCde;//存货编码
	
	@Column(length = 100)
	private String stockName;//存货名称
	
	@Column(length = 100)
	private String productCde;//存货代码
	
	@Column(length = 100)
	private String standard;//存货规格
	
	@Column(length = 20)
	private Double count;//件数
	
	@Column(length = 20)
	private Double price;//单价
	
	@Column(length = 20)
	private Double money;//金额
	
	@Column
	private Double freight;//运费
	
	@Column
	private Double logDeliverFee;//配送费
	
	@Column
	private Double freightTotal;//运费合计
	
	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(), 
			Transfer.class.getSimpleName()) : StringUtils.EMPTY;
	}

	public String getAutoId() {
		return autoId;
	}

	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public String getOutWhouseCode() {
		return outWhouseCode;
	}

	public void setOutWhouseCode(String outWhouseCode) {
		this.outWhouseCode = outWhouseCode;
	}

	public String getOutWhouseName() {
		return outWhouseName;
	}

	public void setOutWhouseName(String outWhouseName) {
		this.outWhouseName = outWhouseName;
	}

	public String getInWhouseCode() {
		return inWhouseCode;
	}

	public void setInWhouseCode(String inWhouseCode) {
		this.inWhouseCode = inWhouseCode;
	}

	public String getInWhouseName() {
		return inWhouseName;
	}

	public void setInWhouseName(String inWhouseName) {
		this.inWhouseName = inWhouseName;
	}

	public String getStockCde() {
		return stockCde;
	}

	public void setStockCde(String stockCde) {
		this.stockCde = stockCde;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getProductCde() {
		return productCde;
	}

	public void setProductCde(String productCde) {
		this.productCde = productCde;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public Double getCount() {
		return count;
	}

	public void setCount(Double count) {
		this.count = count;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getFreight() {
		return freight;
	}

	public void setFreight(Double freight) {
		this.freight = freight;
	}

	public Double getLogDeliverFee() {
		return logDeliverFee;
	}

	public void setLogDeliverFee(Double logDeliverFee) {
		this.logDeliverFee = logDeliverFee;
	}

	public Double getFreightTotal() {
		return freightTotal;
	}

	public void setFreightTotal(Double freightTotal) {
		this.freightTotal = freightTotal;
	}

}
