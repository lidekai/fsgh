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
 * 角色用户匹配表
 * 
 * @author lijialin
 * 
 */
@Entity
@Table(name = "sys_role_user")
public class RoleUser extends POSupport {
	private static final long serialVersionUID = 3304226838628843338L;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "roleId")
	private Role role; // 角色

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "userId")
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
