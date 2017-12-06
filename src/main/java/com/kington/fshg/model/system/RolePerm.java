package com.kington.fshg.model.system;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.POSupport;

/**
 * 角色权限匹配表
 * 
 * @author lijialin
 * 
 */
@Entity
@Table(name = "sys_role_perm")
public class RolePerm extends POSupport {
	private static final long serialVersionUID = 3304226838628843338L;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "roleId")
	private Role role; // 角色

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "functionId")
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
