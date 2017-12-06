package com.kington.fshg.common;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 日期格式处理类，所有日期对象通过此类处理
 * 
 * @author lijialin
 * 
 */
public class DateFormat {

	/**
	 * type1返回日期格式：2012
	 */
	public static final SimpleDateFormat SDF_YYYY = new SimpleDateFormat("yyyy");

	/**
	 * type2返回日期格式：2012-12-12
	 */
	public static final SimpleDateFormat SDF_YYYYMMDD = new SimpleDateFormat(
			"yyyy-MM-dd");
	
	/**
	 * type2返回日期格式：2012-12
	 */
	public static final SimpleDateFormat SDF_YYYYMM = new SimpleDateFormat(
			"yyyy-MM");

	/**
	 * type3返回日期格式：20121212
	 */
	public static final SimpleDateFormat SDF_YYYYMMDD2 = new SimpleDateFormat(
			"yyyyMMdd");

	/**
	 * type4返回日期格式：201212
	 */
	public static final SimpleDateFormat SDF_YYYYMMDD3 = new SimpleDateFormat(
			"yyyyMM");

	/**
	 * type5返回日期格式：130106
	 */
	public static final SimpleDateFormat SDF_YYYYMMDD4 = new SimpleDateFormat(
			"yyMMdd");

	/**
	 * type9返回日期格式：2012-12-12 08:15:26
	 */
	public static final SimpleDateFormat SDF_ALL = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * type6返回日期格式：2013-01-31 13:02
	 */
	public static final SimpleDateFormat SDF_YYYYMMDD6 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");
	/**
	 * type7返回日期格式：201301311302
	 */
	public static final SimpleDateFormat SDF_YYYYMMDD7 = new SimpleDateFormat(
			"yyyyMMddHHmm");
	/**
	 * type7返回日期格式：201301311302
	 */
	public static final SimpleDateFormat SDF_YYYYMMDD8 = new SimpleDateFormat(
			"yyyy年MM月dd日");

	/**
	 * type7返回日期格式：201301311302
	 */
	public static final SimpleDateFormat SDF_YYYYMMDD99 = new SimpleDateFormat(
			"yyyyMMddHHmmssSSSS");

	/**
	 * <pre>
	 * 将日期转换为字符串格式
	 * 根据TYPE返回日期格式
	 * 1:yyyy (2012)
	 * 2:yyyy-MM-dd (2012-12-12)
	 * 3:yyyyMMdd (20121212)
	 * 4:yyyyMM (201212)
	 * 5:yyMMdd (130106)
	 * 9:yyyy-MM-dd HH:mm:ss (2012-12-12 08:15:26)
	 * </pre>
	 * 
	 * @param date
	 *            :如果date为null，则为当前时间
	 * @return type不正确或出现异常时返回空
	 */
	public static String date2Str(Date date, int type) {
		SimpleDateFormat sdf = getSDFByType(type);
		if (sdf == null)
			return null;
		if (date == null)
			date = new Date();
		return sdf.format(date);
	}

	/**
	 * <pre>
	 * 将字符串格式日期转换为Date类型
	 * 根据TYPE返回日期格式
	 * 1:yyyy (2012)
	 * 2:yyyy-MM-dd (2012-12-12)
	 * 3:yyyyMMdd (20121212)
	 * 9:yyyy-MM-dd HH:mm:ss (2012-12-12 08:15:26)
	 * </pre>
	 * 
	 * @param date
	 *            :如果date为null或空字符串，则为当前时间
	 * @return type不正确或出现异常时返回空
	 */
	public static Date str2Date(String date, int type) {
		SimpleDateFormat sdf = getSDFByType(type);
		if (sdf == null)
			return null;
		if (StringUtils.isBlank(date))
			return new Date();
		try {
			Date d = sdf.parse(date);
			return d;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * <pre>
	 * 根据参数获取相应的格式化对象
	 * 1:yyyy (2012)
	 * 2:yyyy-MM-dd (2012-12-12)
	 * 3:yyyyMMdd (20121212)
	 * 9:yyyy-MM-dd HH:mm:ss (2012-12-12 08:15:26)
	 * 7:yyyyMMddHHmm (201301311302)
	 * 
	 * <pre>
	 * @param type
	 * @return type不正确或出现异常时返回空
	 */
	private static SimpleDateFormat getSDFByType(int type) {
		if (type == 1) {
			return SDF_YYYY;
		} else if (type == 2) {
			return SDF_YYYYMMDD;
		} else if (type == 3) {
			return SDF_YYYYMMDD2;
		} else if (type == 4) {
			return SDF_YYYYMMDD3;
		} else if (type == 5) {
			return SDF_YYYYMMDD4;
		} else if (type == 9) {
			return SDF_ALL;
		} else if (type == 6) {
			return SDF_YYYYMMDD6;
		} else if (type == 7) {
			return SDF_YYYYMMDD7;
		} else if (type == 8) {
			return SDF_YYYYMMDD8;
		} else if (type == 99) {
			return SDF_YYYYMMDD99;
		} else if (type == 10){
			return SDF_YYYYMM;
		} else {
			return null;
		}
	}

	/**
	 * 将日期添加指定小时数
	 * 
	 * @param d
	 *            ：需要添加的日期类，如果为NULL，则取当前时间
	 * @param step
	 *            ：需要增加的小时数，如果减少写负数
	 * @return
	 * @throws Exception
	 */
	public static Date addDateByStep(Date d, int stepType, Integer step)
			throws Exception {
		if (d == null)
			d = new Date();
		if (step == null)
			step = new Integer(0);

		Calendar cd = Calendar.getInstance();
		cd.setTime(d);
		cd.add(stepType, step);
		return cd.getTime();
	}
	
	public static Date setDateValue(Date d, int stepType, Integer step)
			throws Exception {
		if (d == null)
			d = new Date();
		if (step == null)
			step = new Integer(0);

		Calendar cd = Calendar.getInstance();
		cd.setTime(d);
		cd.set(stepType, step);
		return cd.getTime();
	}
	
	//得到上个月的第一天
	public static Date getBeforeMonthDay(){
		Calendar cd = Calendar.getInstance();
		cd.setTime(new Date());
		cd.set(Calendar.MONTH, cd.get(Calendar.MONTH)-1);
		cd.set(Calendar.DAY_OF_MONTH, 1);
		return cd.getTime();
	}
	
	/***
	 * 获取当年的第一天日期
	 * @return
	 */
	 public static  Date getCurrYearFirst(){  
	        Calendar currCal=Calendar.getInstance();    
	        int currentYear = currCal.get(Calendar.YEAR);  
	        return getYearFirst(currentYear);  
	    }  
	 public static Date getYearFirst(int year){  
	        Calendar calendar = Calendar.getInstance();  
	        calendar.clear();  
	        calendar.set(Calendar.YEAR, year);  
	        Date currYearFirst = calendar.getTime();  
	        return currYearFirst;  
	    }  
	 
	 /***
	  * 获取当天开始时间、结束时间
	 * @throws ParseException 
	  */
	 public static Date getDayStart() throws ParseException{
		 Calendar todayStart = Calendar.getInstance();
		 
	        todayStart.set(Calendar.HOUR, 0);  
	        todayStart.set(Calendar.MINUTE, 0);  
	        todayStart.set(Calendar.SECOND, 0);  
	        todayStart.set(Calendar.MILLISECOND, 0);
	        
	        SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss");
	        String str = format.format(todayStart.getTime());
		 return format.parse(str);
	 }
	 
	 public static Date getDayEnd() throws ParseException{
		 Calendar todayEnd = Calendar.getInstance();
		 
	        todayEnd.set(Calendar.HOUR, 23);  
	        todayEnd.set(Calendar.MINUTE, 59);  
	        todayEnd.set(Calendar.SECOND, 59);  
	        todayEnd.set(Calendar.MILLISECOND, 999);  
	        
	        SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
	        String str = format.format(todayEnd.getTime());
	        
	      return format.parse(str);
	 }
	 
	public static List<String> yearList(){
		List<String> yearList = new ArrayList<String>();
		
		Calendar date = Calendar.getInstance();
		int year = date.get(Calendar.YEAR);
		Integer minYear = 2015;
		for(int i=2015; i<year+1; i++){
			yearList.add(minYear.toString());
			minYear ++;
		}
		
		return yearList;
	}
	
	 /***
	  * 获取当前月份第一天零时零分零秒
	 * @throws ParseException 
	  */
	 public static String getMonthStart() throws ParseException{
		 
		 Calendar today = Calendar.getInstance();
		 
         today.set(Calendar.HOUR_OF_DAY, 0);  
         today.set(Calendar.MINUTE, 0);  
         today.set(Calendar.SECOND, 0);  
         today.set(Calendar.MILLISECOND, 0);
         today.set(Calendar.DAY_OF_MONTH, 1);
        
         SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss");
	     String str = format.format(today.getTime());
		 return date2Str(format.parse(str), 9);
	 }
	 
	 /***
	  * 获取指定年月第一天零时零分零秒
	  * type = 1获取指定年月后一个月的第一天零时零分零秒
	 * @throws ParseException 
	  */
	 public static Date getYearMonthFirst(Date date,int type){  
	        Calendar calendar = Calendar.getInstance();  
	        calendar.setTime(date);
	        if(type == 1)
	        	calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);  
	        calendar.set(Calendar.HOUR_OF_DAY, 0);  
	        calendar.set(Calendar.MINUTE, 0);  
	        calendar.set(Calendar.SECOND, 0);  
	        calendar.set(Calendar.MILLISECOND, 0);
	        calendar.set(Calendar.DAY_OF_MONTH, 1);
	        return calendar.getTime();  
	 }  
	 
	 public static String getMonthFirst(Date date,int type){
		 return date2Str(getYearMonthFirst(date,type), 9);
	 }
	 
	 /***
	  * 获取指定年月日后第一天零时零分零秒
	 * @throws ParseException 
	  */
	 public static Date getAfterDayFirst(Date date){  
	        Calendar calendar = Calendar.getInstance();  
	        calendar.setTime(date);
	        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);  
	        calendar.set(Calendar.HOUR_OF_DAY, 0);  
	        calendar.set(Calendar.MINUTE, 0);  
	        calendar.set(Calendar.SECOND, 0);  
	        calendar.set(Calendar.MILLISECOND, 0);
	        return calendar.getTime();  
	 }
	 
	 /***
	  * 获取指定年第一天零时零分零秒
	 * @throws ParseException 
	  */
	 public static Date getYearFirstDay(Date date){
		 Calendar calendar = Calendar.getInstance();  
	        calendar.setTime(date);
	        calendar.set(Calendar.MONTH, 0);
	        calendar.set(Calendar.DAY_OF_MONTH, 1);  
	        calendar.set(Calendar.HOUR_OF_DAY, 0);  
	        calendar.set(Calendar.MINUTE, 0);  
	        calendar.set(Calendar.SECOND, 0);  
	        calendar.set(Calendar.MILLISECOND, 0);
	        return calendar.getTime();  
	 }
	 
	 /***
	  * 根据yyyy-MM得到当年第一天零时零分零秒
	 * @throws ParseException 
	  */
	 public static String getYearFirstDayStr(String date){
		 return date2Str(getYearFirstDay(str2Date(date, 10)), 9);
	 }
	 
	 /***
	  * 根据yyyy-MM得到去年第一天零时零分零秒
	 * @throws ParseException 
	  */
	 public static String getLastYearFirstDayStr(String date){
		 Calendar calendar = Calendar.getInstance();  
		 calendar.clear();
		 calendar.set(Calendar.YEAR, Integer.parseInt(date.split("-")[0]) - 1);
		 return date2Str(getYearFirstDay(calendar.getTime()), 9);
	 }
	 
	 /***
	  * 根据yyyy-MM得到明年第一天零时零分零秒
	 * @throws ParseException 
	  */
	 public static String getNextYearFirstDayStr(String date){
		 Calendar calendar = Calendar.getInstance();  
		 calendar.clear();
		 calendar.set(Calendar.YEAR, Integer.parseInt(date.split("-")[0]) + 1);
		 return date2Str(getYearFirstDay(calendar.getTime()), 9);
	 }
	 
	 /***
	  * 获取指定时间后一天
	 * @throws ParseException 
	  */
	 public static Date getNextDay(Date date){
		 Calendar calendar = Calendar.getInstance();  
		 calendar.setTime(date);
		 calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
		 return calendar.getTime();
	 }
	 
	 //计算两个日期直接相差的月份数
	 public static Integer getMonthCount(Date date1, Date date2){
		 Calendar calendar1 = Calendar.getInstance();  
		 Calendar calendar2 = Calendar.getInstance(); 
		 
		 calendar1.setTime(date1);
		 calendar2.setTime(date2);
		 Long b = calendar1.getTimeInMillis();
		 Long c = calendar2.getTimeInMillis();
		 Long d = b - c;
		 
		 Long a = (calendar1.getTimeInMillis() - calendar2.getTimeInMillis())/1000/3600/24/28;
		 return  Math.abs(a.intValue());
	 }
	 
		public static void main(String[] args)throws Exception{
			/*String a = "A20170100100";
			String b = a.substring(7);
			int c = Integer.parseInt(b);*/
			
			System.out.println(PublicType.setDoubleScale4(0.252562));
			//System.out.println(str2Date("2017-12-01", 2).equals(str2Date("2017-12-01", 2))  ? '1': '0');
		}
	 
}
