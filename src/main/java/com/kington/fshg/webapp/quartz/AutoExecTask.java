package com.kington.fshg.webapp.quartz;

import java.util.Calendar;
import javax.annotation.Resource;

import com.jtframework.websupport.mvc.action.BaseActionSupport;
import com.kington.fshg.service.system.LogService;


/**
 * 实现定时服务 <br>
 * 使用quartz-1.6.0.jar
 * 
 */
public class AutoExecTask extends BaseActionSupport {
	private static final long serialVersionUID = 4752719157375588269L;
	@Resource
	private LogService logService;
	
	
	// 判断线程是否在运行
	public static boolean isRun4DeleteLog = false;
	
	
	/***
	 * 自动清除操作日志
	 */
	public synchronized void deleteLog(){
		if(isRun4DeleteLog){
			System.out.println("................. deleteLog线程正在运行..........................");
			return;
		}
		
		isRun4DeleteLog = true;
		try {
			Calendar cl = Calendar.getInstance();
			int month = cl.get(Calendar.MONTH)+1;
			int year = cl.get(Calendar.YEAR)-1;
			logService.deleteByMonthYear(month, year);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		isRun4DeleteLog = false;
	}
	
}
