package com.kington.fshg.service.system;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.DateFormat;
import com.kington.fshg.model.system.Notice;
import com.kington.fshg.model.system.NoticeVO;
import com.kington.fshg.service.BaseServiceImpl;

/**
 * 通知公告
 * 
 * @author Aaron.Tao
 *
 */
public class NoticeServiceImpl extends BaseServiceImpl<Notice,NoticeVO> implements NoticeService {
	private static final long serialVersionUID = 3178156769246106251L;

	@Override
	protected void switchVO2PO(NoticeVO vo,Notice po) throws Exception{
		if (po == null)
			po = new Notice();

		if(StringUtils.isNotBlank(vo.getTitle())) 				po.setTitle(vo.getTitle());
		if(vo.getState() != null) 											po.setState(vo.getState());
		po.setContent(vo.getContent());
	}

	@Override
	protected String getQueryStr(NoticeVO vo){
		StringBuffer sql = new StringBuffer();
		if(StringUtils.isNotBlank(vo.getTitle())) 	sql.append(" and o.title like :title");
		if(vo.getNoticeType() != null) 					sql.append(" and o.noticeType = :noticeType");
		if(vo.getState() != null) 								sql.append(" and o.state = :state");
		
		// 按发布时间区间查询
		if (StringUtils.isNotBlank(vo.getStartTime())) {
			sql.append(" and date(o.createTime) >= :startTime ");
		}
		if (StringUtils.isNotBlank(vo.getEndTime())) {
			sql.append(" and date(o.createTime) <= :endTime ");
		}
				
		return sql.toString();
	}
	
	@Override
	protected void setQueryParm(Query query, NoticeVO vo){
		if(StringUtils.isNotBlank(vo.getTitle())) 	query.setParameter("title",Common.SYMBOL_PERCENT + vo.getTitle() + Common.SYMBOL_PERCENT);
		if(vo.getNoticeType() != null) 					query.setParameter("noticeType", vo.getNoticeType());
		if(vo.getState() != null) 								query.setParameter("state", vo.getState());
		
		// 按发布时间区间查询
		if (StringUtils.isNotBlank(vo.getStartTime())) {
			query.setParameter("startTime",DateFormat.str2Date(vo.getStartTime(), 2));
		}
		if (StringUtils.isNotBlank(vo.getEndTime())) {
			query.setParameter("endTime",DateFormat.str2Date(vo.getEndTime(), 2));
		}
				
	}
	
}