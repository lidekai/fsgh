package com.kington.fshg.model.sale;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.VOSupport;

public class SaleOrderU8VO extends VOSupport {

	private static final long serialVersionUID = 8666153628796307348L;
	
	private String orderCode;//订单号
	private Date orderDate;//输单日期
	private String departmentCode;//销售部门编码/名称
	private String saleTypeCode;//销售类型编码/名称
	private String customCode;//客户编码
	private String customName;//客户名称
	private String customArea;//客户地区
	private Float iTaxRate;//税率
	private String cexchName;//币种
	private Float iExchRate;//汇率
	private String shrPhone;//收货人电话（对应U8自定义12）
	private String personCode;//业务员编码
	private String presonName;//业务负责人
	private Date shDate;//收货日期（对应U8自定义4）
	private String storeOrderCode;//卖场订单号（对应U8自定义11）	
	private String remark;//备注
	private List<SaleOrderU8DetailVO> detailList = new ArrayList<SaleOrderU8DetailVO>();
	
	private String beginTime;
	private String endTime;
	
	private Double js;//件数合计
	private Double sl;//数量合计
	private Double zqwshj;//折前无税合计
	private Double jshj;//价税合计
	private Double wsje;//无税金额
	private Double zke;//折扣额合计
	private Integer state;//审核状态；（0-未审核；1-已审核）
	

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getSaleTypeCode() {
		return saleTypeCode;
	}

	public void setSaleTypeCode(String saleTypeCode) {
		this.saleTypeCode = saleTypeCode;
	}

	public String getCustomCode() {
		return customCode;
	}

	public void setCustomCode(String customCode) {
		this.customCode = customCode;
	}

	public Float getITaxRate() {
		return iTaxRate;
	}

	public void setITaxRate(Float iTaxRate) {
		this.iTaxRate = iTaxRate;
	}

	public String getCexchName() {
		return cexchName;
	}

	public void setCexchName(String cexchName) {
		this.cexchName = cexchName;
	}

	public Float getIExchRate() {
		return iExchRate;
	}

	public void setIExchRate(Float iExchRate) {
		this.iExchRate = iExchRate;
	}

	public String getShrPhone() {
		return shrPhone;
	}

	public void setShrPhone(String shrPhone) {
		this.shrPhone = shrPhone;
	}

	public Date getShDate() {
		return shDate;
	}

	public void setShDate(Date shDate) {
		this.shDate = shDate;
	}

	public String getStoreOrderCode() {
		return storeOrderCode;
	}

	public void setStoreOrderCode(String storeOrderCode) {
		this.storeOrderCode = storeOrderCode;
	}

	public List<SaleOrderU8DetailVO> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<SaleOrderU8DetailVO> detailList) {
		this.detailList = detailList;
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	public String getCustomArea() {
		return customArea;
	}

	public void setCustomArea(String customArea) {
		this.customArea = customArea;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	public String getPresonName() {
		return presonName;
	}

	public void setPresonName(String presonName) {
		this.presonName = presonName;
	}

	
	public Double getJs() {
		return js;
	}

	public void setJs(Double js) {
		this.js = js;
	}

	public Double getSl() {
		return sl;
	}

	public void setSl(Double sl) {
		this.sl = sl;
	}

	public Double getZqwshj() {
		return zqwshj;
	}

	public void setZqwshj(Double zqwshj) {
		this.zqwshj = zqwshj;
	}

	public Double getJshj() {
		return jshj;
	}

	public void setJshj(Double jshj) {
		this.jshj = jshj;
	}

	public Double getWsje() {
		return wsje;
	}

	public void setWsje(Double wsje) {
		this.wsje = wsje;
	}

	public Double getZke() {
		return zke;
	}

	public void setZke(Double zke) {
		this.zke = zke;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				"SaleOrderU8") : StringUtils.EMPTY;
	}

}
