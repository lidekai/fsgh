package com.kington.fshg.model.system;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "sys_role_path")
public class RolePath extends POSupport {
	private static final long serialVersionUID = 3304226838628843338L;

	private Long roleId; // [-1:系统通用访问路径][-2:学生用户访问路径][-3:管理员用户访问路径]

	@Column(length = 255)
	private String functionCde;

	@Column(length = 255)
	private String path; // 权限

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFunctionCde() {
		return functionCde;
	}

	public void setFunctionCde(String functionCde) {
		this.functionCde = functionCde;
	}

	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				RolePath.class.getSimpleName()) : StringUtils.EMPTY;
	}
}
