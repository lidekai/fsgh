package com.kington.fshg.model.system;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.DictType;
import com.kington.fshg.model.VOSupport;

/**
 * 系统配置表
 * 
 * @author lijialin
 * 
 */
public class DictVO extends VOSupport {
	private static final long serialVersionUID = -8579300837291673603L;

	private String remark; // 备注
	private DictType dictType;// 配置类型
	private Double value;//常量数值
	private String dictName;//常量名称


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
