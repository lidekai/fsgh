package com.kington.fshg.model.info;
import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.VOSupport;

public class AreaVO extends VOSupport {
	private static final long serialVersionUID = 8477576325687331852L;

	private String areaName;//地区名称
	private Area parentArea;//所属上级类型
	private Integer lev;// 级别，排序
	
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Area getParentArea() {
		return parentArea;
	}

	public void setParentArea(Area parentArea) {
		this.parentArea = parentArea;
	}

	public Integer getLev() {
		return lev;
	}

	public void setLev(Integer lev) {
		this.lev = lev;
	}

	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				AreaVO.class.getSimpleName()) : StringUtils.EMPTY;
	}

}
