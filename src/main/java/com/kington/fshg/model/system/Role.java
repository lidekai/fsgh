package com.kington.fshg.model.system;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.StateType;
import com.kington.fshg.model.POSupport;

/**
 * 角色表
 * 
 * @author lijialin
 * 
 */
@Entity
@Table(name = "sys_role")
public class Role extends POSupport {
	private static final long serialVersionUID = -8579300837291673603L;

	@Column(nullable = false, length = 255)
	private String roleName; // 名称

	@Basic(fetch = FetchType.LAZY)
	@Column(length = 1000)
	private String remark; // 备注

	@Column(nullable = false, length = 10)
	@Enumerated(EnumType.STRING)
	private StateType state; // 状态

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public StateType getState() {
		return state;
	}

	public void setState(StateType state) {
		this.state = state;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleName() {
		return roleName;
	}

	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				Role.class.getSimpleName()) : StringUtils.EMPTY;
	}

}
