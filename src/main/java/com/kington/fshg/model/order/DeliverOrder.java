package com.kington.fshg.model.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.IsType;
import com.kington.fshg.model.POSupport;

/**
 *  销售发货单信息表
 *
 */

@Entity
@Table(name = "order_deliver")
public class DeliverOrder extends POSupport {
	private static final long serialVersionUID = 6887819064445466260L;

	@Column(length = 20)
	private String autoId;//发货子表ID
	
	@Column(length = 100)
	private String cdlCode;//发货单号
	
	private Date deliverDate;//发货日期
	
	@Column(length = 20)
	private String saleOrderId;//销售订单ID
	
	@Column(length =100)
	private String csoCode;//销售订单号
	
	private Date orderDate;//订单日期
	
	@Column(length = 100)
	private String customCde;//客户编码
	
	@Column(length = 100)
	private String customName;//客户名称
	
	@Column(length = 100)
	private String stockCde;//存货编码
	
	@Column(length = 100)
	private String productCde;//存货代码
	
	@Column(length =100)
	private String stockName;//存货名称
	
	@Column(length = 200)
	private String standard;//存货规格
	
	private Double count;//数量
	
	private Double localPrice;//本币含税单价
	
	private Double countPrice;//本币价税合计
	
	private Double receiveNum;//实收数
	
	private Double total;//合计
	
	private Double number;//件数
	private Double noTaxPrice;//无税金额
	private String saleTypeCode;//销售类型编码
	private String saleType;//销售类型
	private Double iQuotedPrice;//报价
	private Double kl;//扣率
	private Double kl2;//二次扣率
	
	private Double ticketDiscount;//票折（公式=报价*数量*（100-扣率2）/100）
	private Double costDiscount;//费用折（公式=报价*数量*扣率2*（100-扣率1）/100）
	
	private Date originalDate;//原始单据日期
	
	private String storeCde;//卖场订单号
	
	@Column(length = 2)
	@Enumerated(EnumType.STRING)
	private IsType isRebate = IsType.Y;//是否返利
	
	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(), 
				DeliverOrder.class.getSimpleName()) : StringUtils.EMPTY;
	}

	public String getAutoId() {
		return autoId;
	}

	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}

	public String getCdlCode() {
		return cdlCode;
	}

	public void setCdlCode(String cdlCode) {
		this.cdlCode = cdlCode;
	}

	public Date getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}

	public String getSaleOrderId() {
		return saleOrderId;
	}

	public void setSaleOrderId(String saleOrderId) {
		this.saleOrderId = saleOrderId;
	}

	public String getCsoCode() {
		return csoCode;
	}

	public void setCsoCode(String csoCode) {
		this.csoCode = csoCode;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getCustomCde() {
		return customCde;
	}

	public void setCustomCde(String customCde) {
		this.customCde = customCde;
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	public String getStockCde() {
		return stockCde;
	}

	public void setStockCde(String stockCde) {
		this.stockCde = stockCde;
	}

	public String getProductCde() {
		return productCde;
	}

	public void setProductCde(String productCde) {
		this.productCde = productCde;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
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

	public Double getLocalPrice() {
		return localPrice;
	}

	public void setLocalPrice(Double localPrice) {
		this.localPrice = localPrice;
	}

	public Double getCountPrice() {
		return countPrice;
	}

	public void setCountPrice(Double countPrice) {
		this.countPrice = countPrice;
	}

	public Double getReceiveNum() {
		return receiveNum;
	}

	public void setReceiveNum(Double receiveNum) {
		this.receiveNum = receiveNum;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public IsType getIsRebate() {
		return isRebate;
	}

	public void setIsRebate(IsType isRebate) {
		this.isRebate = isRebate;
	}

	public Double getNumber() {
		return number;
	}

	public void setNumber(Double number) {
		this.number = number;
	}

	public Double getNoTaxPrice() {
		return noTaxPrice;
	}

	public void setNoTaxPrice(Double noTaxPrice) {
		this.noTaxPrice = noTaxPrice;
	}

	public String getSaleTypeCode() {
		return saleTypeCode;
	}

	public void setSaleTypeCode(String saleTypeCode) {
		this.saleTypeCode = saleTypeCode;
	}

	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	public Double getiQuotedPrice() {
		return iQuotedPrice;
	}

	public void setiQuotedPrice(Double iQuotedPrice) {
		this.iQuotedPrice = iQuotedPrice;
	}

	public Double getKl() {
		return kl;
	}

	public void setKl(Double kl) {
		this.kl = kl;
	}

	public Double getKl2() {
		return kl2;
	}

	public void setKl2(Double kl2) {
		this.kl2 = kl2;
	}

	public Double getTicketDiscount() {
		return ticketDiscount;
	}

	public void setTicketDiscount(Double ticketDiscount) {
		this.ticketDiscount = ticketDiscount;
	}

	public Double getCostDiscount() {
		return costDiscount;
	}

	public void setCostDiscount(Double costDiscount) {
		this.costDiscount = costDiscount;
	}

	public Date getOriginalDate() {
		return originalDate;
	}

	public void setOriginalDate(Date originalDate) {
		this.originalDate = originalDate;
	}

	public String getStoreCde() {
		return storeCde;
	}

	public void setStoreCde(String storeCde) {
		this.storeCde = storeCde;
	}
	
}
