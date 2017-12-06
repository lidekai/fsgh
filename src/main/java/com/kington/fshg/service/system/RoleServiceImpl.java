package com.kington.fshg.service.system;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.system.Function;
import com.kington.fshg.model.system.Role;
import com.kington.fshg.model.system.RolePath;
import com.kington.fshg.model.system.RolePerm;
import com.kington.fshg.model.system.RoleUser;
import com.kington.fshg.model.system.RoleVO;
import com.kington.fshg.model.system.User;
import com.kington.fshg.service.BaseServiceImpl;

/**
 * 角色表
 * @author lijialin
 *
 */
public class RoleServiceImpl extends BaseServiceImpl<Role,RoleVO> implements RoleService {
	private static final long serialVersionUID = 490564675479737637L;
	
	@Resource
	private UserService userService;
	@Resource
	private FunctionService functionService;
	
	@Override
	protected void switchVO2PO(RoleVO vo,Role po) throws Exception{
		if (po == null)
			po = new Role();
		
		if(StringUtils.isNotBlank(vo.getRoleName())) 	po.setRoleName(vo.getRoleName());
		if(StringUtils.isNotBlank(vo.getRemark())) 		po.setRemark(vo.getRemark());
		if(vo.getState() != null) 						po.setState(vo.getState());
	}
	
	@Override
	protected String getQueryStr(RoleVO vo){
		StringBuffer sql = new StringBuffer();
		if(StringUtils.isNotBlank(vo.getRoleName())) 	sql.append(" and o.roleName like :roleName");
		if(vo.getState() != null) 						sql.append(" and o.state = :state");
		
		setOrderBy(" ORDER BY o.id ");
		return sql.toString();
	}
	
	@Override
	protected void setQueryParm(Query query, RoleVO vo){
		if(StringUtils.isNotBlank(vo.getRoleName())) 	query.setParameter("roleName",Common.SYMBOL_PERCENT + vo.getRoleName() + Common.SYMBOL_PERCENT);
		if(vo.getState() != null) 						query.setParameter("state", vo.getState());
	}
	
	@Override
	public List<RoleUser> getRoleUserList(Long roleId) throws Exception{
		if(roleId == null || roleId <= 0) return null;
		String hql = "from RoleUser o where o.role.id = :id";
		Query query = em.createQuery(hql);
		query.setParameter("id", roleId);
		return query.getResultList();
	}

	@Override
	public List<RolePerm> getRolePermList(Long roleId) throws Exception{
		if(roleId == null || roleId <= 0) return null;
		String hql = "from RolePerm o where o.role.id = :id";
		Query query = em.createQuery(hql);
		query.setParameter("id", roleId);
		return query.getResultList();
	}
	
	@Override
	public void saveRoleUser(Long roleId,String userIds) throws Exception{
		if(roleId == null || roleId <= 0 || StringUtils.isBlank(userIds)) return;
		Role role = getById(Role.class,roleId);
		if(role==null) return;
		RoleUser roleUser = null;
		User user = null;
		for(String id : StringUtils.split(userIds,Common.SYMBOL_COMMA)){
			//判断当前用户是否已经添加过了
			if(checkRoleUserExists(roleId,id)){
				continue;
			}
			
			user = userService.getById(new Long(id));
			if(user == null) continue;
			
			roleUser = new RoleUser();
			roleUser.setRole(role);
			roleUser.setUser(user);
			this.merge(roleUser);
		}
	}
	
	@Override
	public void saveRolePerm(Long roleId,String funcIds) throws Exception{
		if(roleId == null || roleId <= 0 ) return;
		Role role = getById(roleId);
		if(role==null) return;
		//保存前先删除数据
		this.delRolePermByRoleId(roleId);
		if(StringUtils.isBlank(funcIds)) return;
		RolePerm rolePerm = null;
		Function function = null;
		for(String id : StringUtils.split(funcIds,Common.SYMBOL_COMMA)){
			function = functionService.getByCde(id);
			if(function == null) continue;
			
			rolePerm = new RolePerm();
			rolePerm.setRole(role);
			rolePerm.setFunction(function);
			this.merge(rolePerm);
		}
		reloadRolePath(roleId);
	}
	
	
	@Override
	public List<Function> getUserFunctionList(String userCde) throws Exception{
		String sql = "SELECT distinct p.function FROM RolePerm p,RoleUser u WHERE p.role.id=u.role.id and u.user.userCode=:userCde "+
				"and u.role.state='USE' and u.user.state='USE' and p.function.state='USE' ORDER BY p.function.clevel";
			Query query = em.createQuery(sql);
			query.setParameter("userCde", userCde);
			return query.getResultList();
	}
	
	@Override
	public void reloadRolePath(Long id) throws Exception{
		if(id.longValue() <= 0) return;
		this.delRolePath(id);
		List<RolePerm> list = this.getRolePermList(id);
		String s ;
		RolePath path ;
		for(RolePerm r : list){
			s = r.getFunction().getPerLink();
			if(StringUtils.isBlank(s)) continue;
			for(String ss : s.split(Common.SYMBOL_COMMA)){
				if(ss==null || ss.trim().length()==0) continue;
				path = new RolePath();
				path.setRoleId(id);
				path.setFunctionCde(r.getFunction().getFuncCde());
				path.setPath(ss.trim());
				this.merge(path);
			}
		}
	}
	
	@Override
	public void reloadPerm() throws Exception{
		this.delAllPath();
		List<Role> list = this.getAllRole();
		for(Role r : list){
			reloadRolePath(r.getId());
		}
	}

	@Override
	public void reloadPubPerm() throws Exception{
		loadPath();
	}
	
	@Override
	public void loadPath() throws Exception{
		//加载通用链接
		List<RolePath> list = this.getRolePath(new Long(-1));
		Common.PUBLIC_PATH = new ArrayList<String>();
		for(RolePath r : list){
			Common.PUBLIC_PATH.add(r.getPath());
		}
		
		//加载企业通用链接
		list = this.getRolePath(new Long(-2));
		Common.COMP_PATH = new ArrayList<String>();
		for(RolePath r : list){
			Common.COMP_PATH.add(r.getPath());
		}
		
		//加载后台用户通用链接
		list = this.getRolePath(new Long(-3));
		Common.MANAGER_PATH = new ArrayList<String>();
		for(RolePath r : list){
			Common.MANAGER_PATH.add(r.getPath());
		}
		
		System.out.println("####################从数据库加载访问链接##################");
		for(String s : Common.PUBLIC_PATH){
			System.out.println("Common.PUBLIC_PATH:"+s);
		}
		System.out.println("");
		for(String s : Common.COMP_PATH){
			System.out.println("Common.COMPANY_PATH:"+s);
		}
		System.out.println("");
		for(String s : Common.MANAGER_PATH){
			System.out.println("Common.MANAGER_PATH:"+s);
		}
		System.out.println("");
		for(String s : Common.TOP_PATH){
			System.out.println("Common.TOP_PATH:"+s);
		}
		System.out.println("");
		System.out.println("####################END##################");
		
	}
	
	@Override
	public void reloadRolePathByFuncCde(Function f) throws Exception{
		delRolePathByFuncCde(f.getFuncCde());
		if(StringUtils.isNotBlank(f.getPerLink())){
			List<Role> list = getRoleByFuncCde(f.getFuncCde());
			String[] tmp = StringUtils.split(f.getPerLink(),Common.SYMBOL_COMMA);
			RolePath path;
			for(Role r : list){
				for(String ss : tmp){
				if(ss==null || ss.trim().length()==0) continue;
					path = new RolePath();
					path.setRoleId(r.getId());
					path.setFunctionCde(f.getFuncCde());
					path.setPath(ss.trim());
					this.merge(path);
				}
			}
		}
	}
	
	@Override
	public boolean checkRoleUserExists(Long roleId,String userId) throws Exception{
		String hql = "from RoleUser o where o.role.id = :id and o.user.id=:userId";
		Query query = em.createQuery(hql);
		query.setParameter("id", roleId);
		query.setParameter("userId", new Long(userId));
		return query.getResultList().size() > 0;
	}

	private boolean checkRolePermExists(Long roleId,String funcCde) throws Exception{
		String hql = "from RolePerm o where o.role.id = :id and o.function.funcCde=:funcCde";
		Query query = em.createQuery(hql);
		query.setParameter("id", roleId);
		query.setParameter("funcCde", funcCde);
		return query.getResultList().size() > 0;
	}
	
	@Override
	public void delRoleUser(String ids) throws Exception{
		ids = StringUtils.join(StringUtils.split(ids,Common.SYMBOL_COMMA),Common.SYMBOL_COMMA);
		String sql = "DELETE FROM RoleUser WHERE id in ("+ids+")";
		Query query = em.createQuery(sql);
		query.executeUpdate();
	}
	
	@Override
	public void delRolePerm(String ids) throws Exception{
		ids = StringUtils.join(StringUtils.split(ids,Common.SYMBOL_COMMA),Common.SYMBOL_COMMA);
		String sql = "DELETE FROM RolePerm WHERE id in ("+ids+")";
		Query query = em.createQuery(sql);
		query.executeUpdate();
	}
	
	@Override
	public void delRolePath(String ids) throws Exception{
		ids = StringUtils.join(StringUtils.split(ids,Common.SYMBOL_COMMA),Common.SYMBOL_COMMA);
		String sql = "DELETE FROM RolePath WHERE roleId in ("+ids+")";
		Query query = em.createQuery(sql);
		query.executeUpdate();
	}
	
	public void delRolePath(Long id) throws Exception{
		String sql = "DELETE FROM RolePath WHERE roleId = :roleId";
		Query query = em.createQuery(sql);
		query.setParameter("roleId", id);
		query.executeUpdate();
	}

	private void delRolePermByRoleId(Long id) throws Exception{
		String sql = "DELETE FROM RolePerm WHERE role.id=:id";
		Query query = em.createQuery(sql);
		query.setParameter("id", id);
		query.executeUpdate();
	}
	
	public void delAllPath() throws Exception{
		String sql = "DELETE FROM RolePath WHERE roleId > 0";
		Query query = em.createQuery(sql);
		query.executeUpdate();
	}
	
	public List<RolePath> getRolePath(Long id) throws Exception{
		String sql = " FROM RolePath WHERE roleId =:roleId";
		Query query = em.createQuery(sql);
		query.setParameter("roleId", id);
		return query.getResultList();
	}
	
	public List<Role> getAllRole() throws Exception{
		String hql = "from Role";
		Query query = em.createQuery(hql);
		return query.getResultList();
	}
	
	private final static String roleSql = "SELECT 1 FROM sys_role_perm T,sys_role_user T1 WHERE T.ROLEID = T1.ROLEID AND T.FUNCTIONID = :funcCde AND T1.USERID = :userId";
	@Override
	public boolean checkPermissionByUserCde(Long userId,String funCde) throws Exception{
		if(!Common.checkLong(userId) || StringUtils.isBlank(funCde)) return false;
		
		Query query = em.createNativeQuery(roleSql);
		query.setParameter("funcCde", funCde);
		query.setParameter("userId", userId);
		if(query.getResultList().size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	private final static String urlSql1 = "SELECT 1 FROM sys_role_path T,sys_role_user T1 WHERE T.ROLEID = T1.ROLEID AND T.PATH = :path AND T1.USERID = :user";
	private final static String urlSql2 = "SELECT T.PATH FROM sys_role_path T,sys_role_user T1 WHERE T.ROLEID = T1.ROLEID AND INSTR(T.PATH,'*')>0 AND T1.USERID = :user";
	@Override
	public boolean checkURLByUserCde(Long userId,String url) throws Exception{
		if(!Common.checkLong(userId) || StringUtils.isBlank(url)) return false;
		Query query = em.createNativeQuery(urlSql1);
		query.setParameter("user", userId);
		query.setParameter("path", url);
		if(query.getResultList().size()>0){
			return true;
		}else{
			//没有找到链接时，再找通配符比较
			query = em.createNativeQuery(urlSql2);
			query.setParameter("user", userId);
			List<Object> list = query.getResultList();
			for(Object o : list){
				if(o != null && StringUtils.isNotBlank(o.toString())){
					if(Common.matcher.match(o.toString().trim(), url)){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 根据FUNCTIONCDE删除对应的数据,以便重新写入
	 * @param cde
	 * @throws Exception
	 */
	private void delRolePathByFuncCde(String cde) throws Exception{
		String sql = "DELETE FROM RolePath WHERE functionCde=:cde";
		Query query = em.createQuery(sql);
		query.setParameter("cde", cde);
		query.executeUpdate();
	}
	
	private List<Role> getRoleByFuncCde(String cde ) throws Exception{
		String sql = " SELECT r.role FROM RolePerm r WHERE r.function.funcCde =:cde";
		Query query = em.createQuery(sql);
		query.setParameter("cde", cde);
		return query.getResultList();
	}

	@Override
	public RoleUser getRoleUserByUserId(Long userId) throws Exception {
		String sql="from RoleUser o where o.user.id = :userId ";
		Query query = em.createQuery(sql);
		query.setParameter("userId", userId);
		List<RoleUser> list = query.getResultList();
		if(list != null && list.size() > 0)
			return list.get(0);
		return null;
	}
}