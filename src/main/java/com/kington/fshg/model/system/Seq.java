package com.kington.fshg.model.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.POSupport;

/**
 * 系统序列号配置
 * 
 * @author lijialin
 * 
 */
@Entity
@Table(name = "sys_seqno")
public class Seq extends POSupport {
	private static final long serialVersionUID = -8579300837291673603L;

	@Column(nullable = false, length = 50)
	private String tableName; // 表名

	@Column(nullable = false, length = 50)
	private String prefixion; // 前缀

	private Integer seqNum; // 当前序号

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setPrefixion(String prefixion) {
		this.prefixion = prefixion;
	}

	public String getPrefixion() {
		return prefixion;
	}

	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				Seq.class.getSimpleName()) : StringUtils.EMPTY;
	}
}
