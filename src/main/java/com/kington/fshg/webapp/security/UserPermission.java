package com.kington.fshg.webapp.security;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.MyApplicationContextUtil;
import com.kington.fshg.service.system.RoleService;
//import com.kington.fshg.service.system.RoleService;

/**
 * 用户权限设置
 * 
 * @author lijialin
 * 
 */
public class UserPermission {
	private static Logger logger = Logger.getLogger(UserPermission.class);
	private static RoleService roleService = (RoleService)(MyApplicationContextUtil.getBean("roleService"));;

	/**
	 * 判断当前用户访问的路径是否合法
	 * @param userAccount
	 * @param URL
	 * @return
	 */
	public static boolean checkURL(Long userId, String URL) {
		try {
			if (Common.checkLong(userId) && StringUtils.isNotBlank(URL)) {
				//先判断是否公共权限
				for(String path : Common.MANAGER_PATH){
					if(Common.matcher.match(path, URL)){
						return true;
					}
				}
				
				//再判断是否分配此链接权限
				return roleService.checkURLByUserCde(userId, URL);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 判断是否是企业的URL
	 * @param URL
	 * @return
	 */
	public static boolean checkCompURL(String URL) {
		if (StringUtils.isNotBlank(URL)) {
			for(String parten : Common.COMP_PATH){
				if(Common.matcher.match(parten, URL)){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 判断是否顶级的权限，需要进行ID和KEY进行校验
	 * @param url
	 * @return 
	 */
	public static boolean checkTopUrl(String url){
		if (StringUtils.isNotBlank(url)) {
			for(String parten : Common.TOP_PATH){
				if(Common.matcher.match(parten, url)){
					return true;
				}
			}
		}
		return false;
	}
	
//	/**
//	 * 将配置文件中的数据加载至内存
//	 */
//	private static void loadConfig() {
//		// 从配置文件读取已配置的通用路径
//		ResourceBundle rb = ResourceBundle.getBundle("application");
//
//		// 读取企业访问路径
//		String COMPANY_PATH = rb.getString("COMPANY_PATH");
//		List<String> companyList = new ArrayList<String>();
//		if (StringUtils.isNotBlank(COMPANY_PATH)) {
//			String[] paths = StringUtils.split(COMPANY_PATH, ",");
//			for (String s : paths) {
//				companyList.add(s);
//			}
//		}
//		setUserURL(UserType.COMPANY.name(), companyList);
//
//		// 读取企业访问路径
//		String MANAGER_PATH = rb.getString("MANAGER_PATH");
//		List<String> managerList = new ArrayList<String>();
//		if (StringUtils.isNotBlank(MANAGER_PATH)) {
//			String[] paths = StringUtils.split(MANAGER_PATH, ",");
//			for (String s : paths) {
//				logger.info("add managerList:"+s);
//				managerList.add(s);
//			}
//		}
//		setMangerUrlList(managerList);
//	}
}
