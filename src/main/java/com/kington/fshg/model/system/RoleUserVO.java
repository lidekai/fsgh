package com.kington.fshg.model.system;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.VOSupport;

/**
 * 角色权限匹配表
 * 
 * @author lijialin
 * 
 */
public class RoleUserVO extends VOSupport {
	private static final long serialVersionUID = 5614949534079896848L;

	private Role role; // 功能代码
	private User user; // 用户

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
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
				RoleUser.class.getSimpleName()) : StringUtils.EMPTY;
	}
}
