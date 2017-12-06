package com.kington.fshg.model.order;

import java.util.Date;

import javax.persistence.Column;

import org.apache.commons.lang.StringUtils;
import com.kington.fshg.common.Common;
import com.kington.fshg.model.VOSupport;

public class TransferVO extends VOSupport {
	private static final long serialVersionUID = -4506418444370088493L;

	private String autoId;//调拨子表ID
	private String transCode;//调拨单号
	private Date transDate;//调拨日期
	private String outWhouseCode;//调出仓库编号
	private String outWhouseName;//调出仓库名称
	private String inWhouseCode;//调入仓库编号
	private String inWhouseName;//调入仓库名称
	private String stockCde;//存货编码
	private String stockName;//存货名称
	private String productCde;//存货代码
	private String standard;//存货规格
	private Double count;//数量
	private Double price;//单价
	private Double money;//金额
	private Double freight;//运费
	private Double logDeliverFee;//配送费
	private Double freightTotal;//运费合计
	
	private String transBeginTime;//调拨开始日期
	private String transEndTime;//调拨结束日期
	
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

	public String getTransBeginTime() {
		return transBeginTime;
	}

	public void setTransBeginTime(String transBeginTime) {
		this.transBeginTime = transBeginTime;
	}

	public String getTransEndTime() {
		return transEndTime;
	}

	public void setTransEndTime(String transEndTime) {
		this.transEndTime = transEndTime;
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
