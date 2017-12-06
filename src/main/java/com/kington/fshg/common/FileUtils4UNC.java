/**
 * 
 */
package com.kington.fshg.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

/**
 * 文件实用类
 * 
 * @author Van Cheng
 * 
 */
public class FileUtils4UNC {
	
	private static final String UNC_ROOT_DIR;
	static {
		ResourceBundle rb = ResourceBundle.getBundle("application");
		UNC_ROOT_DIR	= rb.getString("UNC_ROOT_DIR");
	}
		
	public static String getRealPath(String relativePat){
		return FilenameUtils.concat(UNC_ROOT_DIR, relativePat);
	}
	
	public static String getRelativePath(String realPath){
		if (!realPath.startsWith(UNC_ROOT_DIR)){
			return realPath;
		}
		return realPath.substring(UNC_ROOT_DIR.length());
	}
	
	/**
	 * 会打乱流
	 * @param file
	 * @param fileName
	 * @param saveName
	 * @return
	 * @throws IOException
	 */
	public static File saveForEncrypt(File file, String fileName, String saveName) throws IOException {
		String ext = FilenameUtils.getExtension(fileName); // 文件扩展名
		if (StringUtils.isBlank(saveName)){ // 如果没有指定保存名,则随机生成
			saveName = getRandomName();
		}
		saveName = FilenameUtils.removeExtension(saveName) + "." + ext;
		
		byte[] b = new byte[1];
		b[0] = '0';
		ByteBuffer bbt = ByteBuffer.allocate(1);
		bbt.position(0);
		bbt.put(b);
		FileChannel input = null, output = null;
		input = new FileInputStream(file).getChannel();
		
		String dirPath = getSavePath().getPath();
		File newFile = new File(
							FilenameUtils.concat(
									dirPath, saveName));
		if (!newFile.getParentFile().isDirectory()) newFile.getParentFile().mkdirs();
		if (!newFile.isFile()) newFile.createNewFile();
		
		// 临时文件
		File tmpFile = new File(FilenameUtils.normalize("\\temp\\", true) + getRandomName());
		if (!tmpFile.getParentFile().isDirectory()) tmpFile.getParentFile().mkdirs();
		if (!tmpFile.isFile()) tmpFile.createNewFile();
		output = new FileOutputStream(tmpFile).getChannel();
		ByteBuffer bb = ByteBuffer.allocate(999);
		while (input.read(bb)!=-1){
		   bb.flip();
		   output.write(bb);
		   bbt.flip();
		   output.write(bbt);
		   bb.clear();
		}
		bbt.clear();
		input.close();
		output.close();
		
		// 拷贝到另外的服务器
		FileUtils.copyFile(tmpFile, newFile);
		
		tmpFile.delete(); // 删除临时文件
		
		return newFile;
	}

	public static void download(File file,String filename){
		if (!file.exists() || file.isDirectory()){
			//JsUtils.alert("\"" + file.getName() + "\" 文件不存在，或已被删除。");
			String alert = "alert('" + StringEscapeUtils.escapeJavaScript("\"" + file.getName() + "\" 文件不存在，或已被删除。") + "');\n"
			                        +"window.history.go(-1);";
			JsUtils.exec(alert);
		}
		
		OutputStream os = null;
		InputStream is = null;

		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.reset();
			filename = new String(filename.getBytes("GBK"), "ISO8859-1");
			resp.setHeader("Content-Disposition", "attachment;filename=" + filename);
			resp.setContentType("application/octet-stream");
			os = new BufferedOutputStream(resp.getOutputStream());
			is = new BufferedInputStream(new FileInputStream(file));
			byte[] buf = new byte[1000];
			int len = 0;
			while ((len = is.read(buf)) != -1) {
				os.write(buf, 0, len - 1);
			//	os.write(buf, 0, len);
			}
			os.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) is.close();
			} catch (Exception ex) {}
			try {
				if (os != null) os.close();
			} catch (Exception ex) {}
		}
	}
	
	public static void download1(File file,String filename){
		if (!file.exists() || file.isDirectory()){
			//JsUtils.alert("\"" + file.getName() + "\" 文件不存在，或已被删除。");
			String alert = "alert('" + StringEscapeUtils.escapeJavaScript("\"" + file.getName() + "\" 文件不存在，或已被删除。") + "');\n"
			                        +"window.history.go(-1);";
			JsUtils.exec(alert);
		}
		
		OutputStream os = null;
		InputStream is = null;

		try {
			HttpServletResponse resp = ServletActionContext.getResponse();
			resp.reset();
			filename = new String(filename.getBytes("GBK"), "ISO8859-1");
			resp.setHeader("Content-Disposition", "attachment;filename=" + filename);
			resp.setContentType("application/octet-stream");
			os = new BufferedOutputStream(resp.getOutputStream());
			is = new BufferedInputStream(new FileInputStream(file));
			byte[] buf = new byte[1000];
			int len = 0;
			while ((len = is.read(buf)) != -1) {
				//os.write(buf, 0, len - 1);
				os.write(buf, 0, len);
			}
			os.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) is.close();
			} catch (Exception ex) {}
			try {
				if (os != null) os.close();
			} catch (Exception ex) {}
		}
	}
	
	
	/**
	 * 获得保存路径
	 * [UNC_ROOT_PATH]/[year]-[month]/
	 * @return
	 */
	private static File getSavePath(){
		// 格式为： "[year]-[month]/"
		String subPath = DateFormat.date2Str(new Date(), 4);
		// 创建目录
		String path = FilenameUtils.concat(UNC_ROOT_DIR, subPath);
		
		File dir = new File(path);
		if (!dir.exists()) dir.mkdirs();
		return dir;
	}

	/**
	 * 获取随机名
	 * @return
	 */
	public static String getRandomName(){
		Random r = new Random();
		int ranNum = (int) (r.nextDouble() * (99999999 - 10000000 + 1)) + 10000000; // 获取随机数
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss"); // 时间格式化的格式
		String timeStr = format.format(new Date()); // 当前时间
		
		return timeStr + "_" + ranNum;
	}

	public static enum Dir {
		Default("WEB-INF/upfile/"), Photo("resources/photo/"), File("resources/upload/"), SysPhoto("resources/sysphoto/");

		private String httpPath;

		private Dir(String httpPath) {
			this.httpPath = FilenameUtils.normalize(httpPath + "/");
		}

		public String getHttpPath() {
			Calendar cal = Calendar.getInstance();
			Integer year = cal.get(Calendar.YEAR);
			Integer month = cal.get(Calendar.MONTH);
			Integer day = cal.get(Calendar.DAY_OF_MONTH);

			// 格式为： "[year]/[month]-[date]/"
			String subPath = year + "/" + StringUtils.leftPad(month.toString(), 2, "0") + "-" + StringUtils.leftPad(day.toString(), 2, "0");

			// 创建目录
			String path = FilenameUtils.concat(ServletActionContext.getServletContext().getRealPath(httpPath), subPath);
			File dir = new File(path);
			if (!dir.exists())
				dir.mkdirs();

			return FilenameUtils.normalize(this.httpPath + subPath + "/");
		}

	}

	/**
	 * 删除文件
	 * 
	 * @param httpPath
	 *            如 WEB-INF/upfile/file 形式
	 * @return
	 */
	public static boolean delete(String httpPath) {
		if (StringUtils.isBlank(httpPath))
			return false;
		File file = new File(ServletActionContext.getServletContext().getRealPath(httpPath));
		if (!file.exists())
			return false;
		return file.delete();
	}

	/**
	 * 检查路径的文件是否存在
	 * 
	 * @param httpPath
	 *            如 WEB-INF/upfile/file 形式
	 * @return
	 */
	public static boolean isExist(String httpPath) {
		File file = new File(ServletActionContext.getServletContext().getRealPath(httpPath));
		return file.exists();
	}

	/**
	 * 指定保存名称 saveName。不会打乱流
	 * 
	 * @param file
	 * @param fileName
	 * @param saveName
	 *            不含扩展名；如果 StringUtils.isBlank,随机生成。
	 * @return
	 * @throws IOException
	 */
	public static File save(Dir saveDir, File file, String fileName, String saveName) throws IOException {
		String ext = FilenameUtils.getExtension(fileName); // 文件扩展名
		if (StringUtils.isBlank(saveName)) { // 如果没有指定保存名,则随机生成
			saveName = getRandomName();
		}
		saveName = FilenameUtils.removeExtension(saveName) + "." + ext;
		byte[] b = new byte[1];
		b[0] = '0';
		ByteBuffer bbt = ByteBuffer.allocate(1);
		bbt.position(0);
		bbt.put(b);
		FileChannel input = null, output = null;
		input = new FileInputStream(file).getChannel();

		String dirPath = ServletActionContext.getServletContext().getRealPath(saveDir.getHttpPath());
		File newFile = new File(FilenameUtils.concat(dirPath, saveName));

		if (!newFile.getParentFile().isDirectory())
			newFile.getParentFile().mkdirs();
		if (!newFile.isFile())
			newFile.createNewFile();
		output = new FileOutputStream(newFile).getChannel();
		ByteBuffer bb = ByteBuffer.allocate(999);
		while (input.read(bb) != -1) {
			bb.flip();
			output.write(bb);
			// bbt.flip();
			// output.write(bbt);
			bb.clear();
		}
		bbt.clear();
		// output.transferFrom(input, 0, input.size());
		input.close();
		output.close();

		return newFile;
	}

	/**
	 * 以svaeName为路径存储
	 * 
	 * @param saveDir
	 * @param file
	 * @param fileName
	 * @param saveName
	 * @return
	 * @throws IOException
	 */
	public static File saveForEncrypt2(Dir saveDir, File file, String fileName, String saveName) throws IOException {

		byte[] b = new byte[1];
		b[0] = '0';
		ByteBuffer bbt = ByteBuffer.allocate(1);
		bbt.position(0);
		bbt.put(b);
		FileChannel input = null, output = null;
		input = new FileInputStream(file).getChannel();

		String dirPath = ServletActionContext.getServletContext().getRealPath("");
		System.out.println("dirPath =:" + dirPath);
		File newFile = new File(FilenameUtils.concat(dirPath, saveName));

		if (!newFile.getParentFile().isDirectory())
			newFile.getParentFile().mkdirs();
		if (!newFile.isFile())
			newFile.createNewFile();
		output = new FileOutputStream(newFile).getChannel();
		ByteBuffer bb = ByteBuffer.allocate(999);
		while (input.read(bb) != -1) {
			bb.flip();
			output.write(bb);
			bbt.flip();
			output.write(bbt);
			bb.clear();
		}
		bbt.clear();
		// output.transferFrom(input, 0, input.size());
		input.close();
		output.close();

		return newFile;
	}

	/**
	 * 保存文件， 随机生成保存文件名
	 * 
	 * @param res
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static File save(File res, String fileName) throws IOException {
		return save(Dir.Default, res, fileName, null);
	}

	public static File save(File file, String fileName, String saveName) throws IOException {
		return save(Dir.Default, file, fileName, saveName);
	}

	public static File save(Dir dir, File file, String fileName) throws IOException {
		return save(dir, file, fileName, null);
	}


}
