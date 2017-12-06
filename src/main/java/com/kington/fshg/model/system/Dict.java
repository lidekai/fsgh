package com.kington.fshg.model.system;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.DictType;
import com.kington.fshg.model.POSupport;

/**
 * 系统配置表
 * 
 * @author lijialin
 * 
 */
@Entity
@Table(name = "sys_dict")
public class Dict extends POSupport {
	private static final long serialVersionUID = -8579300837291673603L;

	@Basic(fetch = FetchType.LAZY)
	@Column(length = 1000)
	private String remark; // 备注

	@Column(nullable = false, length = 20)
	@Enumerated(EnumType.STRING)
	private DictType dictType; // 配置类型
	
	@Column
	private Double value;//常量数值
	
	@Column
	private String dictName;


	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}



	public DictType getDictType() {
		return dictType;
	}



	public void setDictType(DictType dictType) {
		this.dictType = dictType;
	}



	public Double getValue() {
		return value;
	}



	public void setValue(Double value) {
		this.value = value;
	}


	public String getDictName() {
		return dictName;
	}



	public void setDictName(String dictName) {
		this.dictName = dictName;
	}



	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				Dict.class.getSimpleName()) : StringUtils.EMPTY;
	}
}
