package com.kington.fshg.model.system;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.VOSupport;

/**
 * 数据库备份
 */
public class BackupVO extends VOSupport {
	private static final long serialVersionUID = 4754125593043439104L;

	private String bkTime; // 备份时间
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
