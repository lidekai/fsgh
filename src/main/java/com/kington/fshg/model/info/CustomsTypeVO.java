package com.kington.fshg.model.info;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.VOSupport;

public class CustomsTypeVO extends VOSupport {
	private static final long serialVersionUID = -8292287981501395476L;
	
	private String customTypeName;
	private CustomsType parents;
	private Integer lev;


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

}
