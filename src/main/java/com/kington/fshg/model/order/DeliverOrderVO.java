package com.kington.fshg.model.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.IsType;
import com.kington.fshg.model.VOSupport;
import com.kington.fshg.model.info.Area;

public class DeliverOrderVO extends VOSupport {
	private static final long serialVersionUID = 707052247279537875L;

	private String autoId;//发货子表ID
	private String cdlCode;//发货单号
	private Date deliverDate;//发货日期
	private String saleOrderId;//销售订单字表ID
	private String csoCode;//销售订单号
	private Date orderDate;//订单日期
	private String customCde;//客户编码
	private String customName;//客户名称
	private String stockCde;//存货编码
	private String productCde;//存货代码
	private String stockName;//存货名称
	private String standard;//存货规格
	private Double count;//数量
	private Double localPrice;//本币含税单价
	private Double countPrice;//本币价税合计
	private Double receiveNum;//实收数
	private Double total;//合计
	private Long userId;//业务员ID
	private Area area;//所属区域
	private IsType isRebate;//是否返利
	
	private String timeType;//1:订单日期；2：发货单日期
	private String beginTime;//发货订单开始时间
	private String endTime;//发货订单结束时间
	
	private Date deliverStartTime;//发货单开始时间
	private Date deliverEndTime;//发货单结束时间
	
	private Double number;//件数
	private Double noTaxPrice;//无税金额
	private String saleTypeCode;//销售类型编码
	private String saleType;//销售类型
	private Double iQuotedPrice;//报价
	private Double kl;//扣率
	private Double kl2;//二次扣率
	private Double ticketDiscount;
	private Double costDiscount;
	private List<Long> areaIds = new ArrayList<Long>();
	private List<Long> parentAreaIds = new ArrayList<Long>();
	private Date originalDate;//原始单据日期
	private String storeCde;//卖场订单号
	
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


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
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

	public Date getDeliverStartTime() {
		return deliverStartTime;
	}

	public void setDeliverStartTime(Date deliverStartTime) {
		this.deliverStartTime = deliverStartTime;
	}

	public Date getDeliverEndTime() {
		return deliverEndTime;
	}

	public void setDeliverEndTime(Date deliverEndTime) {
		this.deliverEndTime = deliverEndTime;
	}

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
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

	@Override
	public String toString() {
		return "DeliverOrderVO [autoId=" + autoId + ", cdlCode=" + cdlCode + ", deliverDate=" + deliverDate
				+ ", saleOrderId=" + saleOrderId + ", csoCode=" + csoCode + ", orderDate=" + orderDate + ", customCde="
				+ customCde + ", customName=" + customName + ", stockCde=" + stockCde + ", productCde=" + productCde
				+ ", stockName=" + stockName + ", standard=" + standard + ", count=" + count + ", localPrice="
				+ localPrice + ", countPrice=" + countPrice + ", receiveNum=" + receiveNum + ", total=" + total
				+ ", userId=" + userId + ", area=" + area + ", isRebate=" + isRebate + ", timeType=" + timeType
				+ ", beginTime=" + beginTime + ", endTime=" + endTime + ", deliverStartTime=" + deliverStartTime
				+ ", deliverEndTime=" + deliverEndTime + "]";
	}
	
}
