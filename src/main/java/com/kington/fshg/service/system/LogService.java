package com.kington.fshg.service.system;

import com.jtframework.websupport.pagination.PageList;
import com.kington.fshg.model.system.Log;
import com.kington.fshg.model.system.LogVO;
import com.kington.fshg.model.system.Url;
import com.kington.fshg.model.system.UrlVO;
import com.kington.fshg.service.BaseService;

/**
 * 日志处理服务类
 * @author lijialin
 *
 */
public interface LogService extends BaseService<Log, LogVO> {

	/**
	 * 保存日志数据
	 * 
	 * @param po
	 * @throws Exception
	 */
	public void saveLog(Log po) throws Exception;
	

	/**
	 * 查询具体用用户的操作日志
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public PageList<Log> findLogByUserCde(LogVO vo) throws Exception;
	
	
	/**
	 * 保存日志URL数据
	 * 
	 * @param po
	 * @throws Exception
	 */
	public void saveUrl(UrlVO vo) throws Exception;
	
	/**
	 * 根据url地址获取数据
	 * @param urlPath
	 * @return
	 * @throws Exception
	 */
	public Url getUrl(String urlPath) throws Exception;
	
	/**
	 * 更新日志数据
	 * @throws Exception
	 */
	public void updateLog() throws Exception;
	
	/***
	 * 清除前一年当前月的日志
	 */
	public void deleteByMonthYear(Integer month,Integer year) throws Exception;
}