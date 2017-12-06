package com.kington.fshg.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.util.FileSystemUtils;


/**
 * 系统备份工具
 * @author Van Cheng
 */
public class BackupUtils {
	
	private final static String BACKUP_HOME;
	private final static String UNC_ROOT_DIR;
	static {
		ResourceBundle rb = ResourceBundle.getBundle("application");
		UNC_ROOT_DIR = rb.getString("UNC_ROOT_DIR");
//		ResourceBundle rb = ResourceBundle.getBundle("mysql");
//		BACKUP_HOME = rb.getString("BACKUP_HOME");
		
		String dirName = "/backup" + ServletActionContext.getRequest().getContextPath(); // 安装目录根/backup/[应用程序名]
		File dir = new File(dirName);
		if (!dir.exists()) dir.mkdirs();
		BACKUP_HOME = dir.getAbsolutePath();
	}

	
	/**
	 * 备份
	 * @throws IOException 
	 */
	public static void backup() throws IOException{
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateStr = format.format(new Date());
		File rootDir = new File(
							FilenameUtils.concat(BACKUP_HOME, dateStr));
		if (!rootDir.exists()) rootDir.mkdirs();
		
		/*for (String httpPath : paths){
		File src = new File(ServletActionContext.getServletContext().getRealPath(httpPath));
			if (!src.exists()) continue;
			
			File dest = new File(FilenameUtils.concat(rootDir.getAbsolutePath(), httpPath));
			if (!dest.exists()) dest.mkdirs();
			
			org.springframework.util.FileSystemUtils.copyRecursively(src, dest);
	}*/
		
		/*BackupMysql sql = new BackupMysql(rootDir.getAbsolutePath());
		sql.doExport();*/
		BackupAttach att = new BackupAttach(rootDir.getAbsolutePath());
		att.doExport();
		
	}
		
	
	/**
	 * 还原
	 * @throws IOException 
	 */
	public static boolean restore(String dateStr) throws IOException{
		File rootDir = new File(
				FilenameUtils.concat(BACKUP_HOME, dateStr));
		if (!rootDir.exists()) return false;
		
//		for (String httpPath : paths){
//			// 清空文件目录
//			File dest = new File(ServletActionContext.getServletContext().getRealPath(httpPath));
//			if (dest.exists()) FileSystemUtils.deleteRecursively(dest); // 把旧目录删除
//			dest.mkdirs();
//			
//			// 还原文件目录
//			File src = new File(FilenameUtils.concat(rootDir.getAbsolutePath(), httpPath));
//			if (!src.exists()) continue;
//						
//			org.springframework.util.FileSystemUtils.copyRecursively(src, dest);
//		}
		
		/*BackupMysql sql = new BackupMysql(rootDir.getAbsolutePath());
		sql.doImport();*/
		BackupAttach ba = new BackupAttach(rootDir.getAbsolutePath());
		ba.doImport();
		ba.doImportPhoto();
		return true;
	}
	
	/**
	 * 下载备份文件, ZIP 格式, dat 后缀名.
	 * @param dateStr
	 * @return 如果目录不存在, 返回 false.
	 * @throws IOException
	 */
	public static boolean download(String dateStr){
		try {
			File rootDir = new File(
					FilenameUtils.concat(BACKUP_HOME, dateStr));
			if (!rootDir.exists()) return false; // 目录不存在
			
			String fileName = dateStr + "_系统备份.dat";
			fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
	
			
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			resp.setContentType("application/octet-stream");
			ZipOutputStream zip = new ZipOutputStream(resp.getOutputStream());
			
			ZipIt(zip, rootDir.listFiles());
			
			zip.finish();
			zip.close();
		
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/**
	 * 上传备份文件, ZIP 格式, dat 后缀名.
	 * @param pack
	 * @return 出错信息.
	 */
	public static String upload(File pack){
		try {
			ZipInputStream zip = new ZipInputStream(
										new BufferedInputStream(
											new FileInputStream(pack)));

			boolean isCheck = false;
			ZipEntry entry;
			while((entry = zip.getNextEntry()) != null){
				
				if(isCheck == false) {
					String name = FilenameUtils.separatorsToUnix(entry.getName());
					name = name.substring(0, name.indexOf("/"));
					File backup = new File(FilenameUtils.concat(BACKUP_HOME, entry.getName()));
					if (backup.exists()) return ("系统已存在\"" + name + "\"备份, 请先删除.");
					isCheck = true;
				}
				
				
				File file = new File(FilenameUtils.concat(
													BACKUP_HOME, entry.getName()));

				if (entry.isDirectory()) { // 目录
					if (!file.exists()) file.mkdirs();
				}
				else { // 文档
					if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
									
					int len;
					byte[] buffers = new byte[10240];
					FileOutputStream out = new FileOutputStream(file);
					while ((len = zip.read(buffers)) > 0){
						out.write(buffers, 0, len);
					}
					out.flush();
					out.close();
				}
			}
			zip.close();
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "系统出错, " + e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			return "系统出错, " + e.getMessage();
		}
		
		return null;
	}
	
	
	
	/**
	 * 把文件加入到zip, 如果为目录, 递归添加.
	 * @param zip
	 * @param files
	 */
	private static void ZipIt(ZipOutputStream zip, File[] files){
        try {
        	for (File file : files) {
	        	if (file.isDirectory()){ // 如果为目录, 递归处理.
	        		ZipIt(zip, file.listFiles()); continue;
	        	}
	        	
	        	String zipEntryName = file.getAbsolutePath().substring(BACKUP_HOME.length() + 1);
	        	zip.putNextEntry(
	        			new ZipEntry(zipEntryName));
				
		        int count;
		        byte[] buffer = new byte[10240];
		        FileInputStream fis = new FileInputStream(file);
				while ((count = fis.read(buffer, 0, buffer.length)) > 0){
					zip.write(buffer, 0, count);
				}
				zip.closeEntry();
				fis.close();
        	}
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	
	/**
	 * 删除备份文件
	 */
	public static boolean delete(String dateStr){
		File rootDir = new File(
				FilenameUtils.concat(BACKUP_HOME, dateStr));
		if (!rootDir.exists()) return false;
		
		return FileSystemUtils.deleteRecursively(rootDir);
	}
	
	
	
	/**
	 * 返回备份列表
	 * @return
	 */
	public static List<File> list(){
		File backupDir = new File(BACKUP_HOME);
		
		FilenameFilter filter = new FilenameFilter(){
			@Override
			public boolean accept(File dir, String name) {
				return dir.isDirectory();
			}
		};
		
		List<File> result = new ArrayList<File>();
		for (File dir : backupDir.listFiles(filter)){
			result.add(dir);
		}
		
		return result;
	}
	
	
	
	
	
}
