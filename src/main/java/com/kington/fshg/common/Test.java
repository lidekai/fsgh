package com.kington.fshg.common;

import org.apache.commons.lang3.StringUtils;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println(checkIds("http://127.0.0.1/fshg/system/user/edit.jhtml?ddd"));
		long s = 864000000l / 1000 / 60 / 60 / 24;
		System.out.println("11111...>>"+s);
		
		long s1 = 8640000000l / 1000 / 60 / 60 / 24;
		System.out.println("11111...>>"+s1);
		
		double s2 = 1000000l / 1000 / 60 / 60 / 24;
		System.out.println("11111...>>"+s2);
		
		long s3 = -864000000l / 1000 / 60 / 60 / 24;
		System.out.println("11111...>>"+s3);
	}

	private static boolean checkIds(String url){
		String id ="1";
		String key ="22222";
		String ids ="1,2";
		String keys ="dfdfdfdf";
		String table = StringUtils.EMPTY;
		if(StringUtils.isNotBlank(url)){
			String[] s = StringUtils.split(url, "/");
			if(s.length > 2){
				table = s[s.length -2];
			}
		}
		if(table.length() > 0){
			//校验ID
			if(StringUtils.isNotBlank(id)){
				if(!Common.getIdMD5(id, table).equals(key)){
					return false;
				}
			}else if(StringUtils.isNotBlank(ids)){
				String[] idss = StringUtils.split(ids, Common.SYMBOL_COMMA);
				String[] keyss = StringUtils.split(keys, Common.SYMBOL_COMMA);
				if(idss.length != keyss.length){
					return false;
				}
				int i = 0;
				for(String s : idss){
					if(!Common.getIdMD5(s, table).equals(keyss[i])){
						return false;
					}
					i++;
				}
			}
		}
		
		return true;
	}
}
