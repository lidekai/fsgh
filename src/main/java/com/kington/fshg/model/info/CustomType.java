package com.kington.fshg.model.info;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.POSupport;

/**
 *	客户类型 
 */
@Entity
@Table(name = "info_custom_type")
public class CustomType extends POSupport {
	private static final long serialVersionUID = 8024431847689917238L;

	@Column(length = 100)
	private String name;//分类名称
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "parents" )
	private CustomType parents;//所属上级
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CustomType getParents() {
		return parents;
	}

	public void setParents(CustomType parents) {
		this.parents = parents;
	}

	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				CustomType.class.getSimpleName()) : StringUtils.EMPTY;
	}

}
