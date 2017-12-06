package com.kington.fshg.model.system;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.POSupport;
import com.kington.fshg.model.info.Area;

/**
 * 地区用户匹配表
 * 
 */
@Entity
@Table(name = "sys_area_user")
public class AreaUser extends POSupport {
	private static final long serialVersionUID = -5049924319153345705L;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "areaId")
	private Area area; // 角色

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "userId")
	private User user; // 用户
	
	public Area getArea() {
		return area;
	}


	public void setArea(Area area) {
		this.area = area;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				AreaUser.class.getSimpleName()) : StringUtils.EMPTY;
	}

}
