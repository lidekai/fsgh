package com.kington.fshg.webapp.actions.budget;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.ExcelUtil;
import com.kington.fshg.common.PublicType.ActType;
import com.kington.fshg.model.budget.ProvisionProject;
import com.kington.fshg.model.budget.ProvisionProjectVO;
import com.kington.fshg.service.budget.ProvisionProjectService;
import com.kington.fshg.webapp.actions.BaseAction;


/**
 *	合同外费用预提项目 
 *
 */
public class ProvisionProjectAction extends BaseAction {
	private static final long serialVersionUID = -7291893731419958028L;

	@Resource
	private ProvisionProjectService provisionProjectService;
	
	private ProvisionProjectVO vo;
	private String header;
	
	public String list(){
		try {
			if(vo == null)
				vo = new ProvisionProjectVO();
			vo.setPageNumber(page);
			
			pageList = provisionProjectService.getPageList(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	
	
	public String edit(){
		try {
			if(vo != null && Common.checkLong(vo.getId())){
				vo = provisionProjectService.getVOById(vo.getId());
				if(act == null)
					setAct(ActType.EDIT);
			}else{
				setAct(ActType.ADD);
			}
			if(vo == null)
				vo = new ProvisionProjectVO();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Common.PATH_EDIT;
	}
	
	/**
	 * 添加或更新数据
	 * @return 
	 */
	public String update(){
		try {
			ProvisionProject po = provisionProjectService.saveOrUpdate(vo);
			if(po != null)
				this.addActionMessage("操作成功！");
			else
				this.addActionError("操作失败！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//vo = new ProvisionProjectVO();
		return list();
	}
	
	/**
	 * 批量删除
	 * @return 删除结果
	 */
	public String delete(){
		boolean success = false;
		int count = 0;
		for(String str : ids.split(",")){
			try{
				if(provisionProjectService.delete(Long.parseLong(str))){
					count++;
				}
			}catch(Exception e){
				continue;
			}
		}
		success = count > 0;
		if(success){
			String mess = "共删除"+ count + "条数据";
			if(count != ids.split(",").length){
				mess +="," + (ids.split(",").length - count) + "条数据删除失败，可能存在关联引用不可删除";
			}
			this.addActionMessage(mess);
		}else{
			this.addActionError("数据删除失败，可能存在关联引用，不可删除！");
		}
		
		return list();
	}

	/**
	 * 获取预提项目列表
	 * @return
	 */
	public String getProject(){
		try {
			if(vo == null)
				vo = new ProvisionProjectVO();
			vo.setPageNumber(page);
			
			pageList = provisionProjectService.getPageList(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "getProject";
	}
	
	/**
	 * 导出合同外预提项目
	 * @return
	 * @throws Exception 
	 */
	public String exportProvisionProject() throws Exception{
		setAttr("provisionProjectHeader", Common.getExportHeader("PROVISIONPROJECT"));
		if(StringUtils.isNotBlank(vo.getFeeName())){
			String name = new String(vo.getFeeName().getBytes("ISO8859_1"),"UTF8");
			vo.setFeeName(name);
		}
		return "export";
	}
	
	/**
	 * 执行导出
	 * @return
	 */
	public String doExport(){
		if(vo == null)
			vo = new ProvisionProjectVO();
		vo.setObjectsPerPage(Integer.MAX_VALUE);
		List<ProvisionProject> list=provisionProjectService.provisionProject(vo);
		List<Integer> intArr = new ArrayList<Integer>();
		String[] heads = StringUtils.split(header, ",");
		for(int i =0; i< heads.length; i++){
			intArr.add(25);
		}
		
		List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
		Map<String ,String> map = null;
		for(ProvisionProject pp:list){
			map=new HashMap<String, String>();
			provisionProjectService.exportProvisionProject(heads,pp,map);
			listmap.add(map);
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		String title = "合同外预提项目";
		Integer[] columnSize = intArr.toArray(new Integer[]{});
		ExcelUtil.export(response, listmap, "合同外预提项目.xls", title, heads, columnSize);
		
		return null;
	}
	
	public ProvisionProjectVO getVo() {
		return vo;
	}

	public void setVo(ProvisionProjectVO vo) {
		this.vo = vo;
	}


	public String getHeader() {
		return header;
	}


	public void setHeader(String header) {
		this.header = header;
	}
	
}
