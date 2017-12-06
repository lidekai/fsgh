package com.kington.fshg.webapp.json;

import com.kington.fshg.common.PublicType.AttachType;

/**
 * uploadify 附件上传
 * 
 */
public class AttachJSON {

	private Long id;
	private AttachType attachType;// 类型
	private Long nodeId; // 与类型对应表的关联ID
	private String name;// 文件名称
	private String ext;// 扩展名
	private String path;// 文件存储路径
	private Integer count = 0;// 下载次数
	private String key;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public AttachType getAttachType() {
		return attachType;
	}

	public void setAttachType(AttachType attachType) {
		this.attachType = attachType;
	}

}