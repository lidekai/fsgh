package com.kington.fshg.model.info;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.VOSupport;

public class CustomTypeVO extends VOSupport {
	private static final long serialVersionUID = -8292287981501395476L;
	
	private String name;
	private CustomType parents;

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
