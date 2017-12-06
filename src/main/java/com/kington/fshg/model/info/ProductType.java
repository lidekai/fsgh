package com.kington.fshg.model.info;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.POSupport;

/**
 *	产品类型信息表 
 *
 */

@Entity
@Table(name = "info_product_type")
public class ProductType extends POSupport {
	private static final long serialVersionUID = 5908798434827328971L;

	@Column(length = 50)
	private String productTypeName;//分类名称
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentId")
	private ProductType productType;//所属上级类型
	
	private Integer lev;// 级别，排序
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "productType")
	private List<ProductType> productTypeList= new ArrayList<ProductType>();
	
	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
					ProductType.class.getSimpleName()) : StringUtils.EMPTY; 
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public Integer getLev() {
		return lev;
	}

	public void setLev(Integer lev) {
		this.lev = lev;
	}

	public List<ProductType> getProductTypeList() {
		return productTypeList;
	}

	public void setProductTypeList(List<ProductType> productTypeList) {
		this.productTypeList = productTypeList;
	}
	
}
