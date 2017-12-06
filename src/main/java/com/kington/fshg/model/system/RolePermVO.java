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
public class RolePermVO extends VOSupport {
	private static final long serialVersionUID = 5614949534079896848L;

	private Role role; // 角色
	private Function function; // 权限

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				RolePerm.class.getSimpleName()) : StringUtils.EMPTY;
	}
}
