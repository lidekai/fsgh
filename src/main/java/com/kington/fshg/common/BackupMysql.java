package com.kington.fshg.common;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.struts2.ServletActionContext;



/**
 * 备份MySql数据库，本地必须要能运行mysqldump命令
 * 
 * @author Administrator
 * 
 */
public class BackupMysql {
	private String bkDate = null; // 备份时间
	private final static String newLine = OSInfo.isUnix() ? "\n" : "\r\n";
	public final static String exportCMD;
	public final static String importCMD;
	private final static String BACKUP_PATH 
		= FilenameUtils.separatorsToSystem(ServletActionContext.getServletContext().getRealPath("resources/backup/"));

	private String backupFile;
	private static String MYSQL_PATH;
	static {
		File mysql_home = new File(FilenameUtils.separatorsToSystem(MySQLProxy.DIR_PATH));
		
		MYSQL_PATH = FilenameUtils.separatorsToSystem(FilenameUtils.concat(mysql_home.getPath(), "bin/"));
		exportCMD = MYSQL_PATH +"mysqldump --protocol tcp " + " -h" + MySQLProxy.DB_HOST + " -P"
				+ MySQLProxy.DB_PORT + " -u" + MySQLProxy.USER_NAME + " -p"
				+ MySQLProxy.USER_PASSWORD + " " + MySQLProxy.DB_NAME + " " + MySQLProxy.CHARACTER_SET; //--default-character-set=gbk
		System.out.println("数据库备份导出命令 exportCMD : " + exportCMD);
		 
		importCMD =MYSQL_PATH +"mysql --protocol tcp " + " -h "	+ MySQLProxy.DB_HOST + " -P " + MySQLProxy.DB_PORT + " -u "
				+ MySQLProxy.USER_NAME + " -p" + MySQLProxy.USER_PASSWORD
				+ " -D " + MySQLProxy.DB_NAME + " < \"{0}\"";
		System.out.println("数据库备份导入命令 importCMD : " + importCMD);
	}

	public BackupMysql(String rootPath) {
//		this.bkDate = bkDate;
//		backupFile = BACKUP_PATH + MySQLProxy.DB_NAME + "." + bkDate + ".zip";
		backupFile = FilenameUtils.concat(rootPath, "data.zip");
	}

	/**
	 * 备份数据库数据，包含SQL及upfile目录下所有文件
	 * 
	 * @return
	 */
	public boolean doExport() {
		try {
			System.out.println("导出命令:" + exportCMD);
			
			Process process = Runtime.getRuntime().exec(exportCMD);

			// 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行
			InputStream in = process.getInputStream();// 控制台的输出信息作为输入流

			InputStreamReader xx = new InputStreamReader(in);

			String inStr;
			StringBuffer sb = new StringBuffer("");
			String outStr;

			// 组合控制台输出信息字符串
			BufferedReader br = new BufferedReader(xx);
			while ((inStr = br.readLine()) != null) {
				System.out.println("CONSOLE:"+inStr);
				sb.append(inStr + newLine);
			}
			outStr = sb.toString();

			File f = new File(BACKUP_PATH);
			if (!f.exists()) {
				f.mkdirs();
			}

			ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(
					backupFile));
			if (outStr.length() > 0) {
				zip.putNextEntry(new ZipEntry("sql" + File.separator
						+ MySQLProxy.DB_NAME +".sql"));
				zip.write(outStr.toString().getBytes());
				zip.closeEntry();
			}
			in.close();
			xx.close();
			br.close();
			outStr = null;

			zip.finish();
			zip.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 恢复数据库数据
	 * 
	 * @return
	 */
	public boolean doImport() {
		try {
			ZipInputStream zip = new ZipInputStream(new BufferedInputStream(
					new FileInputStream(backupFile)));

			String backup_sql_path = FilenameUtils.concat(BACKUP_PATH, "sql");
			File sqlFile = new File(backup_sql_path);
			if (!sqlFile.exists()) {
				sqlFile.mkdirs();
			}
			File outFile;
			File f;
			ZipEntry entry;
			while ((entry = zip.getNextEntry()) != null) {
				System.out.println("解压文件名：" + entry.getName());
				f =new File(entry.getName());
				outFile = new File(FilenameUtils.concat(BACKUP_PATH, entry.getName()));
				FileOutputStream out = new FileOutputStream(outFile);
				byte[] buffers = new byte[1024];
				int len;
				while ((len = zip.read(buffers)) > 0) {
					out.write(buffers, 0, len);
				}
				out.flush();
				out.close();
			}
			zip.close();

			
			Runtime runtime = Runtime.getRuntime();
			String loginCMD = new StringBuffer(MYSQL_PATH).append("mysql --protocol tcp -u")
					.append(MySQLProxy.USER_NAME).append(" -p").append(
							MySQLProxy.USER_PASSWORD).append(" -h").append(
							MySQLProxy.DB_HOST).append(" -P").append(
							MySQLProxy.DB_PORT).toString();
			String switchCommand = new StringBuffer("use ").append(
					MySQLProxy.DB_NAME).toString();
			
			String importCommand = new StringBuffer("source ").append(
					FilenameUtils.concat(backup_sql_path, MySQLProxy.DB_NAME
							+".sql")).toString();
			//由于mysql 5.6 数据库对于点号开头的文件夹或文件不支持，因此文件的分隔符改成 /
			importCommand=importCommand.replaceAll("\\\\","/");
			
			System.out.println("开始导入 loginCMD：" + loginCMD);
			Process process = runtime.exec(loginCMD);
			
			OutputStream os = process.getOutputStream();
			OutputStreamWriter writer = new OutputStreamWriter(os);
			System.out.println("开始导入 switchCommand：：" + switchCommand);
			writer.write(switchCommand);
			writer.write(newLine);
			System.out.println("开始导入importCommand：" + importCommand);
			writer.write(importCommand);
			writer.write(newLine);
			writer.flush();
			writer.close();
			os.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 删除备份文件
	 */
	public boolean delBackup() {
//		boolean brt = false;
//		// 获取文件名
//		String backupFile = MySQLProxy.BACKUP_PATH + MySQLProxy.DB_NAME + "."
//				+ bkDate + ".sql";
//
//		File f = new File(backupFile);
//		if (f.exists() && f.isFile()) {
//			f.delete();
//			brt = true;
//		}
//		f = null;
//		return brt;
		return true;
	}
}