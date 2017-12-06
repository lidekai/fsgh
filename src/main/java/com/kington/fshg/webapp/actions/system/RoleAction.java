package com.kington.fshg.webapp.actions.system;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.FileUtils4UNC;
import com.kington.fshg.common.PublicType.ActType;
import com.kington.fshg.common.PublicType.AttachType;
import com.kington.fshg.common.PublicType.StateType;
import com.kington.fshg.model.system.Attach;
import com.kington.fshg.model.system.AttachVO;
import com.kington.fshg.model.system.Function;
import com.kington.fshg.model.system.FunctionVO;
import com.kington.fshg.model.system.RolePerm;
import com.kington.fshg.model.system.RoleUser;
import com.kington.fshg.model.system.RoleVO;
import com.kington.fshg.model.system.UserVO;
import com.kington.fshg.service.system.AttachService;
import com.kington.fshg.service.system.FunctionService;
import com.kington.fshg.service.system.RoleService;
import com.kington.fshg.service.system.UserService;
import com.kington.fshg.webapp.actions.BaseAction;
import com.kington.fshg.webapp.security.UserContext;

public class RoleAction extends BaseAction {
	private static final long serialVersionUID = 7846315805495159213L;
	@Resource
	private RoleService roleService;
	@Resource
	private FunctionService functionService;
	@Resource
	private UserService userService;
	@Resource
	private AttachService attachService;

	private RoleVO vo;
	private UserVO userVO;
	private List<RoleUser> userList;// 角色用户列表
	private String treeJSON;// 菜单列表
	
	private AttachVO attachVO;

	public String list() throws Exception {
		if (vo == null) {
			vo = new RoleVO();
		}
		try {
			vo.setPageNumber(page);
			pageList = roleService.getPageList(vo);
		} catch (Exception e) {
			return doException(e);
		}

		return Common.PATH_LIST;
	}

	public String edit() throws Exception {
		String rt;
		if (act == ActType.MULTIPLE) {
			rt = "user";
		} else {
			rt = Common.PATH_EDIT;
		}

		if (vo != null && Common.checkLong(vo.getId())) {
			vo = roleService.getVOById(vo.getId());
			if (vo == null)
				return doException("无效的操作ID");
			setAct(ActType.EDIT);
		} else {
			vo = new RoleVO();
			setAct(ActType.ADD);
		}

		return rt;
	}

	public String update() throws Exception {
		try {
			roleService.saveOrUpdate(vo);
		} catch (Exception e) {
			return doException(e);
		}
		vo = new RoleVO();
		return list();
	}

	public void validateUpdate() {
		this.setInputResult("edit");
		if (vo == null) {
			this.addActionError("对象为空");
		} else {
			if (StringUtils.isBlank(vo.getRoleName())) {
				this.addActionError("角色名称不能为空");
			}
			if (vo.getState() == null) {
				this.addActionError("状态不能为空");
			}
		}
	}

	public String delete() throws Exception {
		try {
			boolean success = false;
			int count = roleService.clear(ids);
			roleService.delRolePath(ids);
			success = count > 0;
			if (success) {
				this.addActionMessage("共删除 " + count + "记录");
			} else {
				this.addActionError("数据删除失败");
			}
		} catch (Exception e) {
			return doException("此角色有关联用户或权限，无法删除");
		}
		return list();
	}

	public String editUser() throws Exception {
		vo = roleService.getVOById(vo.getId());
		userList = roleService.getRoleUserList(vo.getId());

		return "user";
	}
	
	public String getUser() {
		if(userVO == null) setUserVO(new UserVO());
		userVO.setObjectsPerPage(99);
		userVO.setState(StateType.USE);
		try {
			pageList = userService.getPageList(userVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "get-user";
	}

	/**
	 * 编辑角色权限匹配
	 * 
	 * @return
	 */
	public String editPerm() throws Exception {
		try {
			vo = roleService.getVOById(vo.getId());
			List<RolePerm> rolePermList = roleService.getRolePermList(vo.getId());
			List<String> checkList = new ArrayList<String>();// 已选中的权限列表
			for (RolePerm p : rolePermList) {
				checkList.add(p.getFunction().getFuncCde());
			}
			// 获取当前用户的菜单权限列表
			List<Function> list;
			String cde = getUserCde();
			if ("admin".equalsIgnoreCase(cde)) {
				list = functionService.getList(new FunctionVO());
			} else {
				list = roleService.getUserFunctionList(getUserCde());
			}

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
				// 之前已经配置的功能，默认选中
				if (checkList.contains(func.getFuncCde())) {
					member.put("checked", true);
				}
				member.put("open", true);
				json.add(member);
			}
			treeJSON = json.toString();
		} catch (Exception e) {
			return doException(e);
		}

		return "perm";
	}

	/**
	 * 保存角色用户
	 * 
	 * @return
	 */
	public String updateRoleUser() throws Exception {
		try {
			roleService.saveRoleUser(vo.getId(), UserContext.get().getRequest().getParameter("idss"));
		} catch (Exception e) {
			return doException(e);
		}
		return editUser();
	}

	/**
	 * 保存角色用户
	 * 
	 * @return
	 */
	public String updateRolePerm() throws Exception {
		try {
			roleService.saveRolePerm(vo.getId(), UserContext.get().getRequest().getParameter("idss"));
		} catch (Exception e) {
			return doException(e);
		}
	//	return doAlertInfoNoRef(Common.OPER_SUCCESS);
		vo = new RoleVO();
		return list();
	}

	public String deleteRoleUser() throws Exception {
		try {
			roleService.delRoleUser(UserContext.get().getRequest().getParameter("idss"));
		} catch (Exception e) {
			return doException(e);
		}
		return editUser();
	}

	public String reloadPerm() throws Exception {
		try {
			roleService.reloadPerm();
			this.addActionMessage("加载成功");
		} catch (Exception e) {
			return doException(e);
		}
		return list();
	}
	
	public String reloadPubPerm() throws Exception {
		try {
			roleService.reloadPubPerm();
			this.addActionMessage("加载成功");
		} catch (Exception e) {
			return doException(e);
		}
		return list();
	}
	
	public String companyInfo(){
		vo = new RoleVO();
		vo.setId(1L);
		return "companyInfo";
	}
	
	public String company() throws Exception{
		
		if(attachVO == null)
			attachVO = new AttachVO();
		
		attachVO.setAttachType(AttachType.GSZZ);
		attachVO.setObjectsPerPage(Integer.MAX_VALUE);
		List<Attach> list = attachService.getAttList(attachVO);
		setAttr("list", list);
		return "company";
	}

	public void setVo(RoleVO vo) {
		this.vo = vo;
	}

	public RoleVO getVo() {
		return vo;
	}

	public void setUserList(List<RoleUser> userList) {
		this.userList = userList;
	}

	public List<RoleUser> getUserList() {
		return userList;
	}

	public void setTreeJSON(String treeJSON) {
		this.treeJSON = treeJSON;
	}

	public String getTreeJSON() {
		return treeJSON;
	}
	public UserVO getUserVO() {
		return userVO;
	}

	public AttachVO getAttachVO() {
		return attachVO;
	}

	public void setAttachVO(AttachVO attachVO) {
		this.attachVO = attachVO;
	}

	public UserVO setUserVO(UserVO userVO) {
		this.userVO = userVO;
		return userVO;
	}
}
