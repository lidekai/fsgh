package com.kington.fshg.service.system;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.ObjectUtil;
import com.kington.fshg.model.system.AreaUser;
import com.kington.fshg.model.system.RoleUser;
import com.kington.fshg.model.system.User;
import com.kington.fshg.model.system.UserVO;
import com.kington.fshg.service.BaseServiceImpl;


public class UserServiceImpl extends BaseServiceImpl<User,UserVO> implements UserService {
	private static final long serialVersionUID = 1018568357069818982L;
	
	@Override
	protected void switchVO2PO(UserVO vo,User po) throws Exception{}
	
	@SuppressWarnings("unchecked")
	@Override
	public User getByCde(String cde) throws Exception{
		if(StringUtils.isBlank(cde)) return null;
		String hql = "from User o where o.userCode = :userCode";
		Query query = em.createQuery(hql);
		query.setParameter("userCode", cde);
		
		List<User> list = query.getResultList();
		if(list.size() > 0 ){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public UserVO getVOByCde(String cde) throws Exception{
		return ObjectUtil.copy(getByCde(cde),UserVO.class);
	}
	
	@Override
	public User saveOrUpdate(UserVO vo) throws Exception{
		if(vo == null || StringUtils.isBlank(vo.getUserCode())) return null;
		//如果用户已存在,则更新,否则添加
		User po;
		if(checkUserExists(vo.getUserCode())){
			po = getByCde(vo.getUserCode());
			po.setUpdateTime(new Date());
			//密码不在此处理
			if(StringUtils.isNotBlank(vo.getUserName())) 	po.setUserName(vo.getUserName());
			if(StringUtils.isNotBlank(vo.getPhone())) 		po.setPhone(vo.getPhone());
			if(vo.getState() != null)						po.setState(vo.getState());
			if(vo.isUpdate())									po.setUpdate(vo.isUpdate());
		}else{
			po = ObjectUtil.copy(vo, User.class);
			po.setCreateTime(new Date());
			po.setUpdateTime(new Date());
			po.setPwd(Common.getMD5(vo.getPwd() == null?Common.DEF_PWD : vo.getPwd()));
		}
		
		return this.merge(po);
	}
	
	@Override
	protected String getQueryStr(UserVO vo) {
		StringBuffer sql = new StringBuffer();
		if (StringUtils.isNotBlank(vo.getUserCode())) 	sql.append(" and o.userCode like :userCde");
		if (StringUtils.isNotBlank(vo.getUserName())) 	sql.append(" and o.userName like :userName");
		if (vo.getState() != null)						sql.append(" and o.state = :state");
		if (vo.getAreaId() != null) sql.append(" and o.id in (select u.id from User u left join u.areas a where a.id = :areaId ) ");

		return sql.toString();
	}

	@Override
	protected void setQueryParm(Query query, UserVO vo) {
		if (StringUtils.isNotBlank(vo.getUserCode()))
			query.setParameter("userCde",Common.SYMBOL_PERCENT +vo.getUserCode().trim()+Common.SYMBOL_PERCENT);
		if (StringUtils.isNotBlank(vo.getUserName()))
			query.setParameter("userName", Common.SYMBOL_PERCENT + vo.getUserName().trim()	+ Common.SYMBOL_PERCENT);
		if (vo.getState() != null)				query.setParameter("state", vo.getState());
		if (vo.getAreaId() != null)  query.setParameter("areaId", vo.getAreaId());
	}
	
	@Override
	public boolean upatePWD(String userCde,String pwd) throws Exception{
		if(StringUtils.isBlank(userCde) || StringUtils.isBlank(pwd)){
			throw new Exception("用户或密码为空");
		}
		
		User po = getByCde(userCde);
		if(po == null) throw new Exception("无效的用户帐号");
		po.setUpdateTime(new Date());
		po.setPwd(Common.getMD5(pwd));
		return this.merge(po)!=null;
	}
	
	@Override
	public boolean checkUserExists(String cde) throws Exception{
		String sql = "select 1 from User a where a.userCode=:userCode";
		Query query = em.createQuery(sql);
		query.setParameter("userCode", cde);
		if(query.getResultList().size() > 0 ){
			return true;
		}
		return false;
	}
	
	public Long getUserIdByCode(String cde) throws Exception{
		String sql = "select a.id from User a where a.userCode=:userCode";
		Query query = em.createQuery(sql);
		query.setParameter("userCode", cde);
		if(query.getResultList().size() > 0 ){
			return (Long)query.getSingleResult();
		}
		return new Long(0);
	}
	
	@Override
	public User getByCompanyId(Long id)throws Exception{
		String sql = "from User o where o.company.id=:cid";
		Query query = em.createQuery(sql);
		query.setParameter("cid", id);
		List<User> list = query.getResultList();
		if(list.size() > 0 ){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public void exportCom(String[] heads, User po, Map<String, String> map)
			throws Exception {
		String[] comHeader = Common.getExportHeader("USER");
		for(String key : heads){
			int i = 0;
			if(key.equals(comHeader[i++])){//账号
				map.put(key, po.getUserCode());
			}else if(key.equals(comHeader[i++])){//姓名
				map.put(key, po.getUserName());
			}else if(key.equals(comHeader[i++])){//手机号码
				map.put(key, po.getPhone());
			}
//			else if(key.equals(comHeader[i++])){//登录密码
//				map.put(key, po.getPwd());
//			}
			else if(key.equals(comHeader[i++])){//状态
				if(po.getState() == null){
					map.put(key, "");
				}else{
					map.put(key, po.getState().getText());
				}
			}else if(key.equals(comHeader[i++])){//企业名称
				/*if(po.getCompany() == null || po.getCompany().getName() == null){
					map.put(key, "");
				}else{
					map.put(key, po.getCompany().getName());
				}*/
			}
		}
	}
	
	@Override
	public void updateRoleUser(RoleUser roleUser) {
		try {
			this.merge(roleUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getSalesman() throws Exception {
		String hql = "select o.user from RoleUser o where o.role.roleName = '业务员' and o.user.state = 'USE'";
		Query query = em.createQuery(hql);
		return query.getResultList();
	}
	
	@Override
	public void updateAreaUser(AreaUser areaUser) {
		try {
			this.merge(areaUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteAreaUser(Long userId) {
		String sql = "delete from sys_area_user where userId = " + userId;
		Query query = em.createNativeQuery(sql);
		query.executeUpdate();		
	}

}
