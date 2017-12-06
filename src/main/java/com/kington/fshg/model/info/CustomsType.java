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
 *	客户类型 
 */
@Entity
@Table(name = "info_custom_type")
public class CustomsType extends POSupport {
	private static final long serialVersionUID = 8024431847689917238L;

	@Column(length = 100)
	private String customTypeName;//分类名称
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parents" )
	private CustomsType parents;//所属上级
	
	private Integer lev;//等级  排序、
	
	@OneToMany(cascade = CascadeType.ALL ,fetch = FetchType.LAZY ,mappedBy = "parents")
	private List<CustomsType> customsTypeList = new ArrayList<CustomsType>();
	


	public String getCustomTypeName() {
		return customTypeName;
	}

	public void setCustomTypeName(String customTypeName) {
		this.customTypeName = customTypeName;
	}

	public CustomsType getParents() {
		return parents;
	}

	public void setParents(CustomsType parents) {
		this.parents = parents;
	}

	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				CustomsType.class.getSimpleName()) : StringUtils.EMPTY;
	}

	public Integer getLev() {
		return lev;
	}

	public void setLev(Integer lev) {
		this.lev = lev;
	}

	public List<CustomsType> getCustomsTypeList() {
		return customsTypeList;
	}

	public void setCustomsTypeList(List<CustomsType> customsTypeList) {
		this.customsTypeList = customsTypeList;
	}

}
