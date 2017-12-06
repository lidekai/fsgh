package com.kington.fshg.model.system;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.NoticeType;
import com.kington.fshg.common.PublicType.StateType;
import com.kington.fshg.model.POSupport;

/**
 * 通知公告表
 * 
 * @author Aaron.Tao
 * 
 */
@Entity
@Table(name = "notice")
public class Notice extends POSupport {
	private static final long serialVersionUID = 1956693019552066796L;

	@Column(nullable = false, length = 500)
	private String title;// 标题

	@Lob
	@Basic(fetch=FetchType.LAZY)
	private String content;// 内容

	@Column(nullable = false, length = 20)
	@Enumerated(EnumType.STRING)
	private NoticeType noticeType; // 类型[NOTICE:通知公告 / HELP:支持与帮助]

	@Column(nullable = false, length = 10)
	@Enumerated(EnumType.STRING)
	private StateType state; // 状态


	@Override
	public String getKey() {
		return Common.checkLong(getId()) ? Common.getIdMD5(getId().toString(),
				Notice.class.getSimpleName()) : StringUtils.EMPTY;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public NoticeType getNoticeType() {
		return noticeType;
	}


	public void setNoticeType(NoticeType noticeType) {
		this.noticeType = noticeType;
	}


	public StateType getState() {
		return state;
	}


	public void setState(StateType state) {
		this.state = state;
	}
	
	
}
