package com.kington.fshg.common.sms.zgyd;

import java.util.*;

public class SerialNumFactory {
	private static  Map<String,String> map=new HashMap<String, String>(); 
	private  static String STATNUM="000001";
	public  String getYearAndMonth(){
		StringBuffer sb=new StringBuffer();
		Calendar cal = new GregorianCalendar();
		int year = cal.get(Calendar.YEAR); 
		int month = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		sb.append(year);
		if(month<10){
			sb.append("0"+month);
		}else{
			sb.append(month);
		}
		if (day<10) {
			sb.append("0"+day);
		} else {
			sb.append(day);
		}
		return sb.toString();
	}
	
	public String getLastSixNum(String s){
		String rs=s;
		 int i=Integer.parseInt(rs);
		 i+=1;
		 rs=""+i;
		 for (int j = rs.length(); j <6; j++) {
			rs="0"+rs;
		}        
        return rs;  
	}
	public synchronized  String getNum(){
		String yearAMon=getYearAndMonth();
		String last6Num=map.get(yearAMon);
		if(last6Num==null){
			map.put(yearAMon,STATNUM);
		}else{
			map.put(yearAMon,getLastSixNum(last6Num));
		}
		return yearAMon+map.get(yearAMon);
	}
	public static void main(String[] args) {
		SerialNumFactory factory = new SerialNumFactory();
		System.out.println(factory.getNum());
		System.out.println(factory.getNum());
	}
}
