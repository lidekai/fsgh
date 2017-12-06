package com.kington.fshg.service.system;

import java.util.List;

import com.kington.fshg.model.system.Function;
import com.kington.fshg.model.system.Role;
import com.kington.fshg.model.system.RolePerm;
import com.kington.fshg.model.system.RoleUser;
import com.kington.fshg.model.system.RoleVO;
import com.kington.fshg.service.BaseService;

/**
 * 角色表
 * @author lijialin
 *
 */
public interface RoleService extends BaseService<Role,RoleVO>{
	/**
	 * 获取角色用户列表
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public List<RoleUser> getRoleUserList(Long roleId) throws Exception;
	
	/**
	 * 获取角色权限列表
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public List<RolePerm> getRolePermList(Long roleId) throws Exception;
	
	/**
	 * 添加角色用户对象
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public void saveRoleUser(Long roleId,String userIds) throws Exception;
	
	/**
	 * 添加角色权限对象
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public void saveRolePerm(Long roleId,String funcIds) throws Exception;
	
	/**
	 * 删除角色用户
	 * @param roleId
	 * @param ids
	 * @throws Exception
	 */
	public void delRoleUser(String ids) throws Exception;
	
	/**
	 * 删除角色权限
	 * @param roleId
	 * @param ids
	 * @throws Exception
	 */
	public void delRolePerm(String ids) throws Exception;
	public void delRolePath(String ids) throws Exception;
	
	/**
	 * 获取一个用户的授权菜单
	 * @param userCde
	 * @return
	 * @throws Exception
	 */
	public List<Function> getUserFunctionList(String userCde) throws Exception; 
	
	/**
	 * 重新加载角色的PATH
	 * @param id
	 * @throws Exception
	 */
	public void reloadRolePath(Long id) throws Exception;
	
	/**
	 * 重新加载所有角色数据
	 * @throws Exception
	 */
	public void reloadPerm() throws Exception;
	public void reloadPubPerm() throws Exception;
	
	/**
	 * 启动时加载权限链接,只加载-1-2-3的数据
	 * @throws Exception
	 */
	public void loadPath() throws Exception;
	
	/**
	 * 菜单内容修改后,通过此更新权限表
	 * 先删除再添加
	 * @param funcCde
	 * @throws Exception
	 */
	public void reloadRolePathByFuncCde(Function f) throws Exception;
	
	/**
	 * 检验用户是否拥有按钮权限
	 * @param userCde
	 * @param funCde
	 * @return
	 * @throws Exception
	 */
	public boolean checkPermissionByUserCde(Long userId,String funCde) throws Exception;
	
	/**
	 * 检验用户是否拥有链接权限
	 * @param userCde
	 * @param funCde
	 * @return
	 * @throws Exception
	 */
	public boolean checkURLByUserCde(Long userId,String url) throws Exception;
	
	public boolean checkRoleUserExists(Long roleId,String userId) throws Exception;
	
	/**
	 * 根据用户ID获取角色用户
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public RoleUser getRoleUserByUserId(Long userId) throws Exception;
}
