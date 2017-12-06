package com.kington.fshg.webapp.actions.system;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.FileUtils4UNC;
import com.kington.fshg.common.JsUtils;
import com.kington.fshg.common.PublicType.AttachType;
import com.kington.fshg.model.system.Attach;
import com.kington.fshg.model.system.AttachVO;
import com.kington.fshg.service.system.AttachService;
import com.kington.fshg.webapp.actions.BaseAction;
import com.kington.fshg.webapp.json.AttachJSON;

public class AttachAction extends BaseAction {
	private static final long serialVersionUID = -4306718302172684761L;
	
	@Resource
	private AttachService attachService;
	
	private AttachVO vo;
	
	private File res;
	private String resFileName;
	private String chName;    // 下载的模版的 中文名
	private String xlsName;    // 下载模版的文件名（存放在服务器上的文件名）
	
	private String code;
	private String attachType;

	public void list() throws Exception{
		
		if(StringUtils.isBlank(code) || StringUtils.isBlank(attachType) ||StringUtils.isBlank(getKey())){
			JsUtils.writeText("");
		}
		AttachType type = AttachType.valueOf(attachType);
		if(Common.getAttachMD5(code, type).equals(getKey())){
			if(vo == null) vo = new AttachVO();
			vo.setAttachType(type);
			vo.setNodeId(new Long(code));
			if(vo.getNodeId().longValue() <= 0){
				List<AttachJSON> tempResult = new ArrayList<AttachJSON>();
				JsUtils.writeText(JSONArray.fromObject(tempResult.toArray()).toString());
				return;
			}
			vo.setNodeId(new Long(code));
			vo.setObjectsPerPage(1000);
			List<Attach> lst = attachService.getAttList(vo);
			
			List<AttachJSON> result = new ArrayList<AttachJSON>();
			for (Attach vo : lst){
				AttachJSON attach = new AttachJSON();
				BeanUtils.copyProperties(attach, vo);
				result.add(attach);
			}
			JsUtils.writeText(JSONArray.fromObject(result.toArray()).toString());
		}else{
			JsUtils.writeText("");
		}
	}

	public void up() throws Exception {
		if(StringUtils.isBlank(resFileName)){
			JsUtils.writeText("error");
			return;
		}
		if(StringUtils.isBlank(attachType)){
			JsUtils.writeText(null);
		}
		
		AttachType type = AttachType.valueOf(attachType);
		vo = new AttachVO();
		vo.setAttachType(type);
		if(StringUtils.isNotBlank(code)){
			vo.setNodeId(new Long(code));
		}
		
		// 保存文件
		Attach po = attachService.saveAttach(vo.getAttachType(), vo, res, resFileName);
		
		// json 返回保存信息 
		AttachJSON attach = new AttachJSON();
		BeanUtils.copyProperties(attach, po);
		
		JsUtils.writeText(JSONObject.fromObject(attach).toString());
	}
	
	/**
	 * 删除
	 */
	public void delete() throws Exception{
		if (checkKey()){
			AttachVO vo = attachService.getVOById(getId());
			if(vo.getAttachType() == AttachType.GSZZ)
				FileUtils4UNC.delete(vo.getPath());
			else{
				File file = new File(FileUtils4UNC.getRealPath(vo.getPath()));
				if(file.exists())
					file.delete();
			}
				
			attachService.clear(vo.getId().toString());
		}
	}
	
	public void down() throws Exception {
		if (checkKey()){
			attachService.download(getId());
		}
	}
	
	/**
	 * 下载模版
	 */
	public void downStencil() {
		try {
			
//			if (xlsName.equals(Common.TEMPLATE_PAY)) {
//				chName = Common.TEMPLATE_PAY_NAME;
//			} else if (xlsName.equals(Common.TEMPLATE_GOV)) {
//				chName = Common.TEMPLATE_GOV_NAME;
//			} else if (xlsName.equals(Common.TEMPLATE_BLACK)) {
//				chName = Common.TEMPLATE_BLACK_NAME;
//			} else if (xlsName.equals(Common.TEMPLATE_FORM)) {
//				chName = Common.TEMPLATE_FORM_NAME;
//			}
//
//			String loadPath =  Common.TEMPLATE_PATH + xlsName + ".xls";
//
//			ResourceManager.download(loadPath, chName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 /**
	  * 验证 key 码
	 * @return
	 */
	private boolean checkKey(){
		if (StringUtils.isNotBlank(this.getKey())){
			try {
				 Attach po = new Attach();
				 po.setId(getId());
				 if (po.getKey().equals(this.getKey())){
					 return true;
				 }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		JsUtils.alert("非法操作，key 不一致。");
		return false;
	 }
	
	public File getRes() {
		return res;
	}
	public void setRes(File res) {
		this.res = res;
	}
	public String getResFileName() {
		return resFileName;
	}
	public void setResFileName(String resFileName) {
		this.resFileName = resFileName;
	}

	public AttachVO getVo() {
		return vo;
	}

	public void setVo(AttachVO vo) {
		this.vo = vo;
	}

	public String getXlsName() {
		return xlsName;
	}

	public void setXlsName(String xlsName) {
		this.xlsName = xlsName;
	}

	public String getChName() {
		return chName;
	}

	public void setChName(String chName) {
		this.chName = chName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAttachType() {
		return attachType;
	}

	public void setAttachType(String attachType) {
		this.attachType = attachType;
	}


}
