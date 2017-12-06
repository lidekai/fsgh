package com.kington.fshg.webapp.actions.system;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.JsUtils;
import com.kington.fshg.common.PublicType.ActType;
import com.kington.fshg.common.PublicType.StateType;
import com.kington.fshg.model.system.Function;
import com.kington.fshg.model.system.FunctionVO;
import com.kington.fshg.model.system.RolePerm;
import com.kington.fshg.model.system.RoleUser;
import com.kington.fshg.model.system.RoleVO;
import com.kington.fshg.service.system.FunctionService;
import com.kington.fshg.service.system.RoleService;
import com.kington.fshg.webapp.actions.BaseAction;
import com.kington.fshg.webapp.security.UserContext;

public class FunctionAction extends BaseAction {
	private static final long serialVersionUID = 7846315805495159213L;
	@Resource
	private FunctionService functionService;
	@Resource
	private RoleService roleService;
	
	private FunctionVO vo;
	
	private List<Function> funcList;

	

	private List<RoleUser> userList;// 角色用户列表
	private String treeJSON;// 菜单列表
	public String list() throws Exception{
		if (vo == null)
			vo = new FunctionVO();
		if (act == ActType.MULTIPLE || act == ActType.SINGLE) {
			// 单选或多选界面时，只查询出使用状态的数据
			vo.setState(StateType.USE);
		}
		funcList = functionService.getList(vo);
		if (act == ActType.SINGLE) {
			return Common.PATH_SINGLE;
		} else {
			return Common.PATH_LIST;
		}
	}
	
	public String edit() throws Exception {
		try {
			if (vo != null && StringUtils.isNotBlank(vo.getFuncCde())) {
				vo = functionService.getVOByCde(getVo().getFuncCde());
				if (vo == null)
					return doException("无效的操作ID");
				setAct(ActType.EDIT);
			} else {
				vo = new FunctionVO();
				setAct(ActType.ADD);
			}
		} catch (Exception e) {
			return doException(e);
		}
		funcList = functionService.getList(new FunctionVO());
		return Common.PATH_EDIT;
	}

	public String update() throws Exception {
		if (vo.getParent() == null
				|| StringUtils.isBlank(vo.getParent().getFuncCde())) {
			vo.setParent(null);
		}
		try {
			Function ff = functionService.saveOrUpdate(vo);
			roleService.reloadRolePathByFuncCde(ff);
		} catch (Exception e) {
			return doException(e);
		}
		vo = null;
		return list();
	}
	
	/**
	 * 更新前校验数据合法性
	 */
	public void validateUpdate() throws Exception {
		this.setInputResult("edit");
		if (vo == null) {
			this.addActionError("对象为空");
		} else {
			if (StringUtils.isBlank(vo.getFuncCde())){
				this.addActionError("功能代码不能为空");
			}else{
				//功能代码用大写字母
				vo.setFuncCde(vo.getFuncCde().toUpperCase());
			}
			if (StringUtils.isBlank(vo.getFuncName())){
				this.addActionError("功能名称不能为空");
			}
			if(vo.getState() == null){
				this.addActionError("状态不能为空");
			}
			if(vo.getMenuType() == null){
				this.addActionError("功能类别不能为空");
			}
			if (act == ActType.ADD	&& functionService.checkFunctionExists(vo.getFuncCde())) {
				this.addActionError("此功能代码已存在");
			}
		}
	}
	
	public String delete() {
		boolean success = false;
		try {
			int count = functionService.clear(ids);

			success = count > 0;
			if (success) {
				this.addActionMessage("共删除 " + count + "记录");
			} else {
				this.addActionError("数据删除失败");
			}
			return list();
		} catch (Exception e) {
			return doException("数据删除失败,可能数据已存在引用");
		}

	}

	
/**
 * 
 * 菜单权限目录查看
 * @return
 * @throws Exception
 */
	public String functionTree()throws Exception{
		try{
			
			List<Function> list;
			list = functionService.getList(new FunctionVO());

			// 组装JSON数据，返回到前台供zTree使用
			JSONArray json = new JSONArray();

			for (Function func : list) {
				JSONObject member = new JSONObject();
				member.put("id", func.getFuncCde());
				member.put("name", func.getFuncName());
				if (func.getParent() == null) {
					member.put("pid", 0);
				} else {
					member.put("pid", func.getParent().getFuncCde());
				}
				member.put("open", true);
				json.add(member);
			}
			treeJSON = json.toString();
		} catch (Exception e) {
			return doException(e);
		}

		return "viewtree";
	}

	
	
	/**
	 * 校验功能代码是否重复
	 */
	public void checkFunc() {
		String rt = null;
		try {
			boolean flag = functionService.checkFunctionExists(vo.getFuncCde());
			if (flag) {
				rt = "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsUtils.writeText(rt);
	}
	public void setVo(FunctionVO vo) {
		this.vo = vo;
	}

	public FunctionVO getVo() {
		return vo;
	}

	public void setFuncList(List<Function> funcList) {
		this.funcList = funcList;
	}

	public List<Function> getFuncList() {
		return funcList;
	}
	public List<RoleUser> getUserList() {
		return userList;
	}

	public void setUserList(List<RoleUser> userList) {
		this.userList = userList;
	}

	public String getTreeJSON() {
		return treeJSON;
	}

	public void setTreeJSON(String treeJSON) {
		this.treeJSON = treeJSON;
	}
}
