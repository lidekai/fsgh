package com.kington.fshg.webapp.actions.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.ExcelUtil;
import com.kington.fshg.common.JsUtils;
import com.kington.fshg.common.PwdUtils;
import com.kington.fshg.common.PublicType.ActType;
import com.kington.fshg.common.PublicType.StateType;
import com.kington.fshg.model.info.Area;
import com.kington.fshg.model.system.AreaUser;
import com.kington.fshg.model.system.Role;
import com.kington.fshg.model.system.RoleUser;
import com.kington.fshg.model.system.RoleVO;
import com.kington.fshg.model.system.User;
import com.kington.fshg.model.system.UserVO;
import com.kington.fshg.service.info.AreaService;
import com.kington.fshg.service.system.RoleService;
import com.kington.fshg.service.system.UserService;
import com.kington.fshg.webapp.actions.BaseAction;
import com.kington.fshg.webapp.security.UserContext;

/**
 * 用户控制类
 * 
 * @author lijialin
 * 
 */
public class UserAction extends BaseAction {
	private static final long serialVersionUID = -5647085048071846148L;
	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	@Resource
	private AreaService areaService;
	
	private UserVO vo;
	private static UserVO uvo = new UserVO();
	private String header;
	private String roleId;
	private String roleName;
	
	
	public String list() {
		if (vo == null)			vo = new UserVO();
		vo.setPageNumber(page);
		if (act == ActType.MULTIPLE || act == ActType.SINGLE) {
			vo.setState(StateType.USE);
		}
		try {
			pageList = userService.getPageList(vo);
			setAttr("areaList", areaService.getLeafNode());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (act == ActType.SINGLE) {
			return Common.PATH_SINGLE;
		} else if (act == ActType.MULTIPLE) {
			return Common.PATH_MULTIPLE;
		} else {
			return "list";
		}
	}
	
	public String edit() throws Exception {
		try {
			if(vo != null && Common.checkLong(vo.getId())){
				vo = userService.getVOById(vo.getId());
				setAct(ActType.EDIT);
			} else {
				setAct(ActType.ADD);
			}
		} catch (Exception e) {
			return doException(e);
		}
		
		
		RoleVO roleVO = new RoleVO();
		roleVO.setObjectsPerPage(Integer.MAX_VALUE);
		roleVO.setState(StateType.USE);
		setAttr("roleList", roleService.getPageList(roleVO).getList());
		setAttr("defPwd",Common.DEF_PWD);
		return "edit";
	}
	
	public String update() throws Exception {
		try {
			User user = userService.saveOrUpdate(vo);
			
			if(StringUtils.isNotBlank(roleId)){
				Role role = roleService.getById(Long.parseLong(roleId));
				RoleUser roleUser = roleService.getRoleUserByUserId(user.getId());
				if(roleUser == null)
					roleUser = new RoleUser();
				if(role != null){
					roleUser.setRole(role);
					roleUser.setUser(user);
					userService.updateRoleUser(roleUser);
				}
				
			}
			
			String[] areaIds 	= ServletActionContext.getRequest().getParameterValues("areaIds");
			if(areaIds != null){
				userService.deleteAreaUser(user.getId());
				for(String areaId : areaIds){
					Area area = areaService.getById(Long.parseLong(areaId));
					AreaUser areaUser = new AreaUser();
					areaUser.setArea(area);
					areaUser.setUser(user);
					userService.updateAreaUser(areaUser);
				}
				
			}
			
		} catch (Exception e) {
			return doException(e);
		}
		this.addActionMessage("操作成功");
		vo = new UserVO();
		return list();
	}

	public void validateUpdate() throws Exception {
		this.setInputResult("edit");
		if (getVo() == null) {
			this.addActionError("对象为空");
		} else {
			if(StringUtils.isBlank(vo.getUserCode())) {
				this.addActionError("用户帐号必须指定");
			}
			if(StringUtils.isBlank(vo.getUserName())) {
				this.addActionError("用户姓名必须指定");
			}
			if(vo.getState() == null) {
				this.addActionError("用户状态不能为空");
			}
			if(Common.checkLong(vo.getId())){
				UserVO uservo = userService.getVOById(vo.getId());
			}else{
				if(userService.checkUserExists(vo.getUserCode())){
					this.addActionError("此用户已存在,请重新输入用户名");
				}
			}
		}
		setAttr("defPwd",Common.DEF_PWD);
	}
	
	public String view() throws Exception {
		try {
			vo = userService.getVOById(vo.getId());
			if (vo == null) return doException("无效的操作ID");
			setAct(ActType.VIEW);
		} catch (Exception e) {
			return doException(e);
		}

		return "view";
	}
	
	public String delete() throws Exception {
		try {
			if(userService.clear(ids) > 0){
				this.addActionMessage("数据删除成功");
			}else{
				this.addActionMessage("数据删除失败，请联系管理员");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return doException("数据删除失败,数据已有其它关联引用");
		}
		return list();
	}

	public String editMyPwd() {
		setAttr("USER_INFO",UserContext.get().getUser());
		return "mypwd";
	}
	
	public String updateMyPwd() {
		try {
			UserVO user= UserContext.get().getUser();
			if (userService.upatePWD(user.getUserCode(), PwdUtils.decode(vo.getPwd()))) {
				user.setUpdate(true);
				userService.saveOrUpdate(user);
				this.addActionMessage("密码修改成功");
			}else{
				this.addActionError("密码修改失败");
			}
		} catch (Exception e) {
			return doException(e);
		}

		return editMyPwd();
	}

	/**
	 * 校验用户是否重复
	 */
	public void checkUser() {
		Long useId = 0L;
		try {
			useId = userService.getUserIdByCode(vo.getUserCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsUtils.writeText(useId.toString());
	}

	public String editPwd() {
		try {
			if (Common.checkLong(vo.getId())) {
				vo = userService.getVOById(getVo().getId());
				if (vo == null)		return doException("无效的操作ID");
			}
		} catch (Exception e) {
			return doException(e);
		}
		return "pwd";
	}
	
	public String updatePwd() {
		try {
			UserVO user = userService.getVOById(vo.getId());
			if(user != null){
				if (userService.upatePWD(user.getUserCode(), PwdUtils.decode(getVo().getPwd()))) {
					this.addActionMessage(Common.OPER_SUCCESS);
				} else {
					this.addActionMessage(Common.OPER_FAIL);
				}
			}else{
				this.addActionError("无法找到对象，更新失败");
			}
		} catch (Exception e) {
			return doException(e);
		}
		
		return list();
	}
	
	/**
	 * 多选用户列表
	 * @return
	 */
	public String get4user(){
		if (vo == null)
			vo = new UserVO();
		vo.setPageNumber(page);
		try {
			setAttr("userList", userService.getPageList(vo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "get4user";
	}
	
	
	/**
	 * 、导出页面
	 * 
	 * @return
	 */
	public String export() {
		//
		this.setAttr("userHeader", Common.getExportHeader("USER"));
		return "export";
	}
	
	/**
	 * 导出处理
	 * 
	 * @return
	 * @throws Exception
	 */
	public String exportExcel() throws Exception {
		if (uvo == null) {
			uvo = new UserVO();
		}
		uvo.setObjectsPerPage(9999);
		List<User> list = userService.getList(uvo);
		List<Integer> intArr = new ArrayList<Integer>();
		String[] heads = StringUtils.split(header, ",");
		for (int i = 0; i < heads.length; i++) {
			intArr.add(25);
		}
		//listtmap 存放每一行的记录
		List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (User user : list) {
			map = new HashMap<String, String>();
			userService.exportCom(heads, user, map);
			listmap.add(map);
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		String title = "用户管理";
		Integer[] columnSize = intArr.toArray(new Integer[]{});
		ExcelUtil.export(response, listmap, "用户管理.xls", title, heads,
				columnSize);
		uvo = new UserVO();
		return null;
	}
	
	/**
	 * 根据用户角色，获取地区数据
	 * @return
	 */
	public String getArea(){
		try {
			JSONArray json = new JSONArray();
			List<Area> list = new ArrayList<Area>();
			if("smallArea".equals(getRoleName())){
				list = areaService.getLeafNode();
			}
			if("bigArea".equals(getRoleName())){
				list = areaService.getFristNode();
			}
			for(int i=0;i< list.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("id", list.get(i).getId());
				obj.put("name", list.get(i).getAreaName());
				json.add(obj);
			}
			JsUtils.writeText(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取角色名称为业务员的用户
	 * @return
	 */
	public String getSalesman(){
		try {
			if(vo == null)
				vo = new UserVO();
			vo.setPageNumber(page);
			String str ="and o.id in (select r.user.id from RoleUser r where r.role.roleName ='业务员')";
			vo.initMyQueryStr(str);
			
			setAttr("userList", userService.getPageList(vo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "getSalesman";
	}
	
//	/**
//	 * 跳转至修改密码界面(仅针对当前用户)
//	 * 
//	 * @return
//	 */
//	public String selfPwd() {
//		
//		return "self-pwd";
//	}
//	
//	/**
//	 * 修改密码(仅针对当前用户)
//	 * 
//	 * @return
//	 */
//	public String selfUpdatePwd() {
//		try {
//			boolean flag = userService.upatePWD(UserContext.get().getUserCode(), getVo().getPwd());
//			if (flag) {
//				this.addActionMessage(Common.OPER_SUCCESS);
//			} else {
//				this.addActionMessage(Common.OPER_FAIL);
//			}
//		} catch (Exception e) {
//			return doException(e);
//		}
//
//		return selfPwd();
//	}
//	
//	/**
//	 * 跳转至个人资料编辑界面（注册用户和管理员共用）
//	 * 
//	 * @return
//	 */
//	public String selfEdit() throws Exception {
//		try {
//			vo = userService.getVOByCde(UserContext.get().getUserCode());
//		} catch (Exception e) {
//			return doException(e);
//		}
//		
//		return "selfEdit";
//	}
//	
//	/**
//	 * 更新个人资料
//	 * 
//	 * @return
//	 */
//	public String selfUpdate() throws Exception {
//		try {
//			vo.setUserCode(UserContext.get().getUserCode());
//			
//			userService.saveOrUpdate(vo);
//		} catch (Exception e) {
//			return doException(e);
//		}
//		
//		this.addActionMessage(Common.OPER_SUCCESS);
//		return selfEdit();
//		
//	}
	
	
	public void setVo(UserVO vo) {
		this.vo = vo;
		this.uvo = vo;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public UserVO getVo() {
		return vo;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
