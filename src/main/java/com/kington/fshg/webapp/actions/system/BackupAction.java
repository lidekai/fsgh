package com.kington.fshg.webapp.actions.system;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jtframework.websupport.mvc.action.BaseActionSupport;
import com.kington.fshg.common.BackupUtils;
import com.kington.fshg.common.JsUtils;
import com.kington.fshg.webapp.actions.BaseAction;
import com.kington.fshg.webapp.security.log.ClassLogInfo;
import com.kington.fshg.webapp.security.log.MethodLogInfo;

@ClassLogInfo(name = "数据库备份")
public class BackupAction extends BaseAction {
	private static final long serialVersionUID = 7019580114474268014L;

	private List<File> list;	//备份列表
	private String dateStr;	//文件名
	
	@MethodLogInfo(name ="列表查询")
	public String list(){
		list = BackupUtils.list();
		return "list";
	}
	
	@MethodLogInfo(name = "数据备份")
	public String doBackup() throws Exception{
		BackupUtils.backup();
		return list();
	}
	
	@MethodLogInfo(name ="数据恢复")
	public String doImport() throws Exception{
		if(BackupUtils.restore(dateStr)){
			this.addActionMessage("数据恢复成功");
		}else{
			this.addActionError("数据恢复失败，备份文件已删除");
		}
		return this.redirect("/system/backup/list.jhtml");
	}
	
	@MethodLogInfo(name ="删除备份")
	public String deleteBackup() throws Exception{
		if(BackupUtils.delete(dateStr)){
			this.addActionMessage("数据备份删除成功");
		}else{
			this.addActionError("删除失败，备份数据不存在或已被删除");
		}
		return this.redirect("/system/backup/list.jhtml");
	}
	
	public void download() throws IOException{
		if(BackupUtils.download(dateStr) == false){
			JsUtils.alert("无法提供：\""+dateStr+"\"下载.备份不存在，可能已被删除");
		}
	}
	
	/***
	 * 上传备份文件
	 * @return
	 */
	private File pack;
	private String packFileName;
	public String upload(){
		String mes = BackupUtils.upload(pack);
		if(StringUtils.isNotBlank(mes)){
			setAttr("result", mes);
		}else{
			setAttr("result", "上传成功");
		}
		return "upfile";
	}

	public List<File> getList() {
		return list;
	}

	public void setList(List<File> list) {
		this.list = list;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public File getPack() {
		return pack;
	}

	public void setPack(File pack) {
		this.pack = pack;
	}

	public String getPackFileName() {
		return packFileName;
	}

	public void setPackFileName(String packFileName) {
		this.packFileName = packFileName;
	}
	
	
}
