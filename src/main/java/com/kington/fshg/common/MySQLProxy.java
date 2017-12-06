/**
 * 
 */
package com.kington.fshg.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Van Cheng
 *
 */
public class MySQLProxy {
	private final static Logger logger = Logger.getLogger(MySQLProxy.class.getName());

	public final static String DB_HOST;		// 地址
	public final static String DB_PORT;		// 端口号
	public final static String DB_NAME;		// 数据库名
	
	public final static String USER_NAME;		// 账号
	public final static String USER_PASSWORD;	// 密码
	
	public final static String CMD_EXPORT;		// 导出命令
	public final static String CMD_IMPORT;		// 导入命令
	
	public final static String DIR_PATH;		//mysql 路径
	
	public final static String CHARACTER_SET;
	public static File mysql_home = null;
	static {
		DB_HOST			= ResourceBundle.getBundle("mysql").getString("DB_HOST");
		DB_PORT			= ResourceBundle.getBundle("mysql").getString("DB_PORT");
		DB_NAME 		= ResourceBundle.getBundle("mysql").getString("DB_NAME");
		
		USER_NAME 		= ResourceBundle.getBundle("mysql").getString("USER_NAME");
		USER_PASSWORD 	= ResourceBundle.getBundle("mysql").getString("USER_PASSWORD");
		
		DIR_PATH = ResourceBundle.getBundle("mysql").getString("MYSQL_HOME");
		mysql_home = new File(FilenameUtils.separatorsToSystem(DIR_PATH));
		
		CHARACTER_SET = checkMysqlCode();
				
		String cmd = StringUtils.EMPTY;
		cmd	+= "\"" + FilenameUtils.concat(mysql_home.getPath(), "bin/mysql") + "\" --protocol tcp -h " + DB_HOST + " -P " + DB_PORT + " -u " + USER_NAME + " -p" + USER_PASSWORD + " -D " + DB_NAME + " < \"{0}\"";
		//if (OSInfo.isWindows())	cmd = "cmd /c \"" + cmd + "\"";
		CMD_IMPORT = cmd;
		System.out.println("MYSQL数据导入命令CMD_IMPORT : " + CMD_IMPORT);
		
		// --skip-comments  --default-character-set=gbk 
		CMD_EXPORT		= FilenameUtils.separatorsToSystem(
							FilenameUtils.concat(mysql_home.getPath(), 
			"bin/mysqldump --protocol tcp ")) + " -h " + DB_HOST + " -P " + DB_PORT + " -u " + USER_NAME + " -p" + USER_PASSWORD + " " + DB_NAME + " {0} -w \"1=1 {1}\" --add-drop-table=false -C --replace -t --lock-all-tables "+CHARACTER_SET;
		System.out.println("MYSQL数据导出命令 CMD_EXPORT : " + CMD_EXPORT);
		
		mysql_home = null;
	}
	
	/**
	 * @param tableName
	 * @param where
	 * @param sql
	 * @return 成功返回 null; 失败返回错误信息
	 * @throws IOException
	 * @throws InterruptedException 
	 * @throws SQLException 
	 */
	public static void exportTable(final String tableName, final String where, StringBuilder sql) 
			throws IOException, InterruptedException, SQLException{
		StringBuilder result = new StringBuilder();
		
		Process process;
		if (OSInfo.isUnix()){
			if (StringUtils.isBlank(CHARACTER_SET)){
				process = Runtime.getRuntime().exec(
					new String[]{
						DIR_PATH + "bin/mysqldump","-h" + DB_HOST,"-P" + DB_PORT,"-u" + USER_NAME,"-p" + USER_PASSWORD,
						"--add-drop-table=false","-C","--replace","-t","--lock-all-tables","--protocol=tcp",
						"--where=1=1 " + where,DB_NAME,tableName});
			} else {
				process = Runtime.getRuntime().exec(
						new String[]{
							DIR_PATH + "bin/mysqldump","-h" + DB_HOST,"-P" + DB_PORT,"-u" + USER_NAME,"-p" + USER_PASSWORD,
							"--add-drop-table=false","-C","--replace","-t","--lock-all-tables","--protocol=tcp",CHARACTER_SET,
							"--where=1=1 " + where,DB_NAME,tableName});
			}
		} else {
			String cmd = MessageFormat.format(CMD_EXPORT, tableName, where);
			if (logger.isDebugEnabled()) logger.debug("导出:\n" + cmd);
			process = Runtime.getRuntime().exec(cmd);
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				process.getInputStream()));
		String line = null;
		while ((line = reader.readLine()) != null) {
			if (logger.isDebugEnabled())
				logger.debug(line);
			result.append(line + "\n");
		}
		
		StringBuilder error = new StringBuilder();
		reader = new BufferedReader(new InputStreamReader(
				process.getErrorStream()));
		line = null;
		while ((line = reader.readLine()) != null) {
			if (logger.isDebugEnabled())
				logger.debug(line);
			error.append(line + "\n");
		}

		process.waitFor();

		if (process.exitValue() == 0) {
			if (logger.isDebugEnabled())
				logger.debug("导出成功, SQL:\n" + result.toString());
		}

		if (process.exitValue() != 0) {
			String errorMsg = "导出失败, 错误信息:\n" + error.toString();
			if (logger.isDebugEnabled())
				logger.debug(errorMsg);
			
			throw new SQLException(errorMsg);
		}

		sql.append(result.toString()); // 写入导出 sql 到流

	}
	
	public static void importData(String fileFullPath) throws IOException, InterruptedException, SQLException {
		String cmd = MessageFormat.format(CMD_IMPORT, fileFullPath);
		if (logger.isDebugEnabled()) logger.debug("导入:\n" + cmd);
		
		Process process;
		process = Runtime.getRuntime().exec(cmd);
		
		StringBuilder result = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				process.getInputStream()));
		String line = null;
		while ((line = reader.readLine()) != null) {
			if (logger.isDebugEnabled())
				logger.debug(line);
			result.append(line + "\n");
		}
		
		StringBuilder error = new StringBuilder();
		reader = new BufferedReader(new InputStreamReader(
				process.getErrorStream()));
		line = null;
		while ((line = reader.readLine()) != null) {
			if (logger.isDebugEnabled()) 
				logger.debug(line);
			error.append(line + "\n");
		}
		
		process.waitFor();

		if (error.length() > 0) {
			String errorMsg = "导出失败, 错误信息:\n" + error.toString();
			if (logger.isDebugEnabled())
				logger.debug(errorMsg);
			
			throw new SQLException(errorMsg);
		}
			

	}
	
	/**
	 * 检测MYSQL数据库编码格式，通过默认格式导出sys_user表中ID为1的内置记录，判断中文值是否相等，不相等则设置为GBK编码
	 * @return
	 */
	public static String checkMysqlCode(){
		String cset = StringUtils.EMPTY;
		System.out.println("######################测试数据库编码start##########################");
		String common = FilenameUtils.separatorsToSystem(
				FilenameUtils.concat(mysql_home.getPath(), "bin/mysqldump")) + " --protocol tcp -h " + DB_HOST + " -P " + DB_PORT + " -u " + USER_NAME + " -p" + USER_PASSWORD + " " + DB_NAME + " test_encoded --add-drop-table=false -C --replace -t --lock-all-tables  ";
		
		System.out.println("测试编码命令："+common);
		
		try {
			Process process = Runtime.getRuntime().exec(common);

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line = null;
			StringBuffer result = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				result.append(line + "\n");
			}
			System.out.println("执行结果："+result.toString());
			if(result.toString().indexOf("中文测试") == -1){
				cset = "--default-character-set=gbk";
				System.out.println("中文编码异常，设置为GBK编码:"+cset);
			}else{
				System.out.println("中文编码正常不需要设置");
			}
			result = null;
			line = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		common = null;
		
		System.out.println("######################测试数据库编码end##########################");
		return cset;
	}
	
	
}
