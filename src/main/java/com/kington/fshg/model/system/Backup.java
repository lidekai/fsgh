package com.kington.fshg.model.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.POSupport;

/**
 * 数据库备份
 */
@Entity
@Table(name = "sys_backup")
public class Backup extends POSupport {
	private static final long serialVersionUID = 4754125593043439104L;

	@Column(nullable = false)
	private String bkTime; // 备份时间
	@Column(nullable = true)
	private String reTime; // 恢复时间

	public String getBkTime() {
		return bkTime;
	}

	public void setBkTime(String bkTime) {
		this.bkTime = bkTime;
	}

	public String getReTime() {
		return reTime;
	}

	public void setReTime(String reTime) {
		this.reTime = reTime;
	}

	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				Backup.class.getSimpleName()) : StringUtils.EMPTY;
	}

}
