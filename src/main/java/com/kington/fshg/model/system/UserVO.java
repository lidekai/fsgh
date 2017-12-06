package com.kington.fshg.model.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.StateType;
import com.kington.fshg.model.VOSupport;
import com.kington.fshg.model.info.Area;

/**
 * 用户基本信息
 * 
 * @author lijialin
 * 
 */
public class UserVO extends VOSupport {
	private static final long serialVersionUID = -6094894346683319551L;
	private String userCode; // 账号
	private String userName; // 姓名
	private String phone; // 手机号码
	private String pwd; // 登录密码
	private StateType state; // 状态
	private List<Role> roles = new ArrayList<Role>();
	private boolean isUpdate;
	private List<Area> areas;//
	private Long areaId;
	private String areaIds;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public StateType getState() {
		return state;
	}

	public void setState(StateType state) {
		this.state = state;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				User.class.getSimpleName()) : StringUtils.EMPTY;
	}

	public boolean isUpdate() {
		return isUpdate;
	}

	public void setUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getAreaIds() {
		if(Common.checkList(this.areas)){
			areaIds = "";
			for(Area a : this.areas){
				areaIds += a.getId().toString() + ";";
			}
		}
		return areaIds;
	}

	public void setAreaIds(String areaIds) {
		this.areaIds = areaIds;
	}
}
