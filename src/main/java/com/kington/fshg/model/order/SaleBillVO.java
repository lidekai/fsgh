package com.kington.fshg.model.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.IsType;
import com.kington.fshg.model.VOSupport;

public class SaleBillVO extends VOSupport {

	private static final long serialVersionUID = 4702656376776657381L;

	private String autoId;//销售发票子表Id 
	private String csbvcode;//销售发票号
	private Date createDate;//制单时间
	private Date saleDate;//订单日期
	private Date deliverDate;//发货日期
	private String saleOrderId;//销售订单子表ID
	private String csoCode;//销售订单号
	private String customCde;//客户编码
	private String customName;//客户名称
	private String stockCde;//存货编号
	private String productCde;//存货代码
	private String productName;//存货名称
	private String standard;//存货规格
	private Double count;//数量
	private Double localPrice;//本币含税单价
	private Double countPrice;//本币价税合计
	private Double noTaxPrice;//本币无税金额
	private IsType isRebate;//是否返利
	
	private String timeType;  // 1.订单日期   2.发货日期  3.制单时间
	private String beginTime;//开始时间
	private String endTime;//结束时间
	private List<Long> areaIds = new ArrayList<Long>();
	private List<Long> parentAreaIds = new ArrayList<Long>();
	private Long userId;//业务员ID

	public String getAutoId() {
		return autoId;
	}


	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}


	public String getCsbvcode() {
		return csbvcode;
	}


	public void setCsbvcode(String csbvcode) {
		this.csbvcode = csbvcode;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public Date getSaleDate() {
		return saleDate;
	}


	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
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


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
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


	public Double getNoTaxPrice() {
		return noTaxPrice;
	}


	public void setNoTaxPrice(Double noTaxPrice) {
		this.noTaxPrice = noTaxPrice;
	}

	
	public String getTimeType() {
		return timeType;
	}


	public void setTimeType(String timeType) {
		this.timeType = timeType;
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

	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public IsType getIsRebate() {
		return isRebate;
	}


	public void setIsRebate(IsType isRebate) {
		this.isRebate = isRebate;
	}


	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(), 
				SaleBillVO.class.getSimpleName()) : StringUtils.EMPTY;
	}

}
