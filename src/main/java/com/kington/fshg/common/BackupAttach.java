package com.kington.fshg.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.struts2.ServletActionContext;

import com.kington.fshg.common.FileUtils4UNC;
public class BackupAttach {

	private String backupAttach;
	private String phtotAttach;
	private final static String UNC_ROOT_DIR;
	static{
		ResourceBundle rb = ResourceBundle.getBundle("application");
		UNC_ROOT_DIR = rb.getString("UNC_ROOT_DIR");
	}
	
	public BackupAttach(String rootPath){
		backupAttach = FilenameUtils.concat(rootPath, UNC_ROOT_DIR.substring(UNC_ROOT_DIR.indexOf("\\")+1	,UNC_ROOT_DIR.length()-1)+".zip");
		phtotAttach = FilenameUtils.concat(rootPath, "photo.zip");
	}
	
	/***
	 * 备份附件资料，包含文件下的子文件夹和所有文件
	 * @throws FileNotFoundException 
	 */
	public  boolean doExport(){
		try {
				//附件
				File file = new File(UNC_ROOT_DIR);
				ZipOutputStream out = new ZipOutputStream(new FileOutputStream(backupAttach));
				zip(out,file,file.getName());
				out.close();
				
				//图片
				String path = ServletActionContext.getServletContext().getRealPath("resources/photo/");
				File file1 = new File(path);
				ZipOutputStream out1 = new ZipOutputStream(new FileOutputStream(phtotAttach));
				zip(out1,file1,file1.getName());
				out1.close();
				return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private void zip(ZipOutputStream out,File f,String name){
		try {
				if (f.isDirectory()) {
			           File[] fl = f.listFiles();
						out.putNextEntry(new ZipEntry(name + "/"));
						name = name.length() == 0 ? "" : name + "/";
						for (int i = 0; i < fl.length; i++) {
							zip(out, fl[i], name + fl[i].getName());
						}
		           }else {
			        	   out.putNextEntry(new ZipEntry(name));
			        	   FileInputStream in = new FileInputStream(f);
			        	   int b;
			        	   while ( (b = in.read()) != -1) {
			        		   out.write(b);
			        	   }
			        	   in.close();
		           }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	/**
	 * 恢复附件列表数据
	 */
	public boolean doImport(){
		try {
			ZipFile zip = new ZipFile(backupAttach);
			 for(Enumeration entries = zip.entries();entries.hasMoreElements();){  
		            ZipEntry entry = (ZipEntry)entries.nextElement();  
		            InputStream in = zip.getInputStream(entry);  
		            String outPath = (UNC_ROOT_DIR.substring(0,UNC_ROOT_DIR.indexOf(":"))+":/"+entry.getName()).replaceAll("\\*", "/");  
		            //判断路径是否存在,不存在则创建文件路径  
		            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));  
		            if(!file.exists()){  
		                file.mkdirs();  
		            }  
		            //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压  
		            if(new File(outPath).isDirectory()){  
		                continue;  
		            }  
		            //输出文件路径信息  
		            System.out.println(outPath);  
		              
		            OutputStream out = new FileOutputStream(outPath);  
		            byte[] buf1 = new byte[1024];  
		            int len;  
		            while((len=in.read(buf1))>0){  
		                out.write(buf1,0,len);  
		            }  
		            in.close();  
		            out.close();  
		            }  
		        System.out.println("****************恢复完成********************");  
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 恢复图片数据
	 */
	public boolean doImportPhoto(){
		try {
			ZipFile zip = new ZipFile(phtotAttach);
			 for(Enumeration entries = zip.entries();entries.hasMoreElements();){  
		            ZipEntry entry = (ZipEntry)entries.nextElement();  
		            InputStream in = zip.getInputStream(entry);  
		            
		            String path = ServletActionContext.getServletContext().getRealPath("resources");
		            String outPath = (path+"/"+entry.getName()).replaceAll("\\*", "/");  
		            //判断路径是否存在,不存在则创建文件路径  
		            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));  
		            if(!file.exists()){  
		                file.mkdirs();  
		            }  
		            //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压  
		            if(new File(outPath).isDirectory()){  
		                continue;  
		            }  
		            //输出文件路径信息  
		            System.out.println(outPath);  
		              
		            OutputStream out = new FileOutputStream(outPath);  
		            byte[] buf1 = new byte[1024];  
		            int len;  
		            while((len=in.read(buf1))>0){  
		                out.write(buf1,0,len);  
		            }  
		            in.close();  
		            out.close();  
		            }  
		        System.out.println("****************恢复完成********************");  
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
}
