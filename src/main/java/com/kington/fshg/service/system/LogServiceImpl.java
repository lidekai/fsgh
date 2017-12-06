package com.kington.fshg.service.system;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import com.jtframework.websupport.pagination.PageList;
import com.kington.fshg.common.Common;
import com.kington.fshg.model.system.Log;
import com.kington.fshg.model.system.LogVO;
import com.kington.fshg.model.system.Url;
import com.kington.fshg.model.system.UrlVO;
import com.kington.fshg.service.BaseServiceImpl;

/**
 * 日志处理服务实现类
 * @author lijialin
 *
 */
public class LogServiceImpl extends BaseServiceImpl<Log,LogVO> implements LogService {
	private static final long serialVersionUID = -2544502779729048527L;

	@Resource 
	private LogService logService;
	
	@Override
	protected String getQueryStr(LogVO vo) throws Exception{
		StringBuffer sql = new StringBuffer();
		
		if(StringUtils.isNotBlank(vo.getUserCde())) 	sql.append(" and o.userCde like :userCde");
		if(StringUtils.isNotBlank(vo.getUserName())) 	sql.append(" and o.userName like :userName");
		if(StringUtils.isNotBlank(vo.getIpAddress()))	sql.append(" and o.ipAddress like :ipAddress");
		if(StringUtils.isNotBlank(vo.getLink())) 		sql.append(" and o.link like :link");
		if(vo.getIsPass()!=null)					 	sql.append(" and o.isPass like :isPass");
		
		//时间段
		if(vo.getStartTime() != null)
			sql.append(" and o.createTime >= :startTime ");
		if(vo.getEndTime() != null)
			sql.append(" and o.createTime <= :endTime ");
		
		return sql.toString();
	}
	
	@Override
	protected void setQueryParm(Query query, LogVO vo) throws Exception{
		if(StringUtils.isNotBlank(vo.getUserCde())) 	query.setParameter("userCde", Common.SYMBOL_PERCENT + vo.getUserCde() + Common.SYMBOL_PERCENT);
		if(StringUtils.isNotBlank(vo.getUserName())) 	query.setParameter("userName",Common.SYMBOL_PERCENT + vo.getUserName() + Common.SYMBOL_PERCENT);
		if(StringUtils.isNotBlank(vo.getIpAddress()))	query.setParameter("ipAddress",Common.SYMBOL_PERCENT + vo.getIpAddress() + Common.SYMBOL_PERCENT);
		if(StringUtils.isNotBlank(vo.getLink())) 		query.setParameter("link",Common.SYMBOL_PERCENT + vo.getLink() + Common.SYMBOL_PERCENT);
		if(vo.getIsPass()!=null)						query.setParameter("isPass", vo.getIsPass());
		
		if(vo.getStartTime() != null)
			query.setParameter("startTime", vo.getStartTime());
		if(vo.getEndTime() != null)
			query.setParameter("endTime", vo.getEndTime());
	}

	@Override
	protected void switchVO2PO(LogVO vo,Log po) throws Exception{
	}

	@Override
	public PageList<Log> findLogByUserCde(LogVO vo) throws Exception {
		
		PageList<Log> pageList = new PageList<Log>();
		pageList.setPageNumber(vo.getPageNumber());
		pageList.setObjectsPerPage(vo.getObjectsPerPage());
		pageList.setList(findLogByUser(vo));
		pageList.setFullListSize(findLogByUserCount(vo));
		
		return pageList;
	}
	
	public List<Log> findLogByUser(LogVO vo) throws Exception {
		StringBuffer sql = new StringBuffer("from Log o where 1=1 and  o.userCde = :userCde ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("userCde", vo.getUserCde());
		List<Log> list = query.getResultList();
		
		return list;
	}

	public Integer findLogByUserCount(LogVO vo) throws Exception {
		StringBuffer sql = new StringBuffer("select count(distinct o.id) from Log o where 1=1 and o.userCde = :userCde ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("userCde", vo.getUserCde());
		return ((Long)query.getSingleResult()).intValue();
	}
	
	
	@Override
	public void saveLog(Log po) throws Exception{
		po.setOperInfo(getURL(po.getLink()));
		this.merge(po);
	}
	
	/**
	 * 根据URL获取对应的功能名
	 * @param url
	 * @return
	 * @throws Exception
	 */
	private String getURL(String url) throws Exception{
		String sql = "select o.name from sys_url o where o.urlPath = :urlPath ";
		Query query = em.createNativeQuery(sql);
		query.setParameter("urlPath", url);
		List<Object> list = query.getResultList();
		if(list.size() > 0){
			return (String)list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public void saveUrl(UrlVO vo) throws Exception {
		String sql = "INSERT INTO `sys_url` VALUES (:urlPath, :name);";
		Query query = em.createNativeQuery(sql);
		query.setParameter("urlPath", vo.getUrlPath());
		query.setParameter("name", vo.getName());
		query.executeUpdate();  
		
		
	}

	@Override
	public Url getUrl(String urlPath) throws Exception {
		Url url = null;
		String sql = "from Url o where o.urlPath = :urlPath ";
		Query query = em.createQuery(sql);
		query.setParameter("urlPath", urlPath);
		
		List<Url> list = query.getResultList();
		if (list.size() >0) {
			url = list.get(0);
		}
		
		return url;
	}

	@Override
	public void updateLog() throws Exception {
		String sql = "update sys_log log,sys_url url set log.operinfo = url.name where log.link = url.urlpath and operinfo is  null";
		Query query = em.createNativeQuery(sql);
		query.executeUpdate();
		
	}

	@Override
	public void deleteByMonthYear(Integer month, Integer year) throws Exception {
		String sql = "delete from sys_log where month(createTime) = "+month+" and year(createTime)="+year;
		Query query = em.createNativeQuery(sql);
		query.executeUpdate();
		
	}
}