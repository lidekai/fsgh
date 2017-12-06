package com.kington.fshg.model.system;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.NoticeType;
import com.kington.fshg.common.PublicType.StateType;
import com.kington.fshg.model.VOSupport;

/**
 * 通知公告表
 * 
 * @author Aaron.Tao
 * 
 */
public class NoticeVO extends VOSupport {
	private static final long serialVersionUID = 1956693019552066796L;

	private String title;// 标题

	private String content;// 内容

	private NoticeType noticeType; // 类型[NOTICE:通知公告 / HELP:支持与帮助]

	private StateType state; // 状态
	
	private String startTime;// 开始时间(用于搜索)
	private String endTime;// 结束时间

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


	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
