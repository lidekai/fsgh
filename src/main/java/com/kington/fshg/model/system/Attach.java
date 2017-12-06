package com.kington.fshg.model.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.AttachType;
import com.kington.fshg.model.POSupport;

/**
 * 附件表
 * 
 * @author lijialin
 * 
 */
@Entity
@Table(name = "sys_attach")
public class Attach extends POSupport {
	private static final long serialVersionUID = 6434363354176519532L;

	@Column(nullable = false, length = 20)
	@Enumerated(EnumType.STRING)
	private AttachType attachType;// 类型

	private Long nodeId; // 与类型对应表的关联ID

	@Column(nullable = false, length = 500)
	private String name;// 文件名称

	@Column(nullable = false, length = 20)
	private String ext;// 扩展名

	@Column(nullable = false, length = 1000)
	private String path;// 文件存储路径

	@Column(nullable = false)
	private Integer count = 0;// 下载次数

	public AttachType getAttachType() {
		return attachType;
	}

	public void setAttachType(AttachType attachType) {
		this.attachType = attachType;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(), Attach.class.getSimpleName()) : StringUtils.EMPTY;
//		return getId() == null ? "" : Common.getIdMD5(getId().toString(),
//				Attach.class.getName());
	}

}