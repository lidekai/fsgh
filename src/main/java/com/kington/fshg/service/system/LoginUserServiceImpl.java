package com.kington.fshg.service.system;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.ObjectUtil;
import com.kington.fshg.model.system.LoginUser;
import com.kington.fshg.model.system.LoginUserVO;
import com.kington.fshg.model.system.User;
import com.kington.fshg.service.BaseServiceImpl;
import com.kington.fshg.webapp.security.SecurityFilter;
import com.kington.fshg.webapp.security.UserContext;

public class LoginUserServiceImpl extends BaseServiceImpl<LoginUser,LoginUserVO> implements LoginUserService {
	private static final long serialVersionUID = 3178156769246106251L;

	@Override
	public synchronized boolean doLogin(LoginUser po) throws Exception{
		if(po == null) 	throw new Exception("无效的参数");
		try {
//			User u = po.getUser();
//			LoginUser loginUser = getUserLogin(u.getUserCode());
			
//			if(loginUser == null){
				this.merge(po);
				return true;
//			}else{
//				if(loginUser.getIpAddress().equals(po.getIpAddress())){
//					loginUser.setActiveTime(new Date());
//					this.merge(loginUser);
//					return true;
//				}else{
//					return false;
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	} 
	
	@Override
	public LoginUser getUserLogin(String userCde) throws Exception{
		if(StringUtils.isBlank(userCde))	return null;
		String sql = "from LoginUser o where o.user.userCode = :user and o.logoutTime is null";
		Query query = em.createQuery(sql);
		query.setParameter("user", userCde);
		List<LoginUser> list = query.getResultList();
		if(list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public LoginUserVO getUserLoginVO(String userCde) throws Exception{
		LoginUser po = getUserLogin(userCde);
		if(po == null)	return null;
		return ObjectUtil.copy(po, LoginUserVO.class);
	}
	
	@Override
	public Integer getLoginCount() throws Exception {
		String sql = "select count(o.id) from LoginUser o where o.user.userCde = :userCde";
		Query query = em.createQuery(sql);
		query.setParameter("userCde", UserContext.get().getUserCode() );
		return ((Long)query.getSingleResult()).intValue();
	}
	
	@Override
	public boolean doLoginOut(String userCde) throws Exception{
		//退出时，计算计费时间
		LoginUser po = getUserLogin(userCde);
		if(po == null){
			return false;
		}
		
		po.setLogoutTime(new Date());
		this.merge(po);
		return true;
	}
	
	
	
	@Override
	protected void switchVO2PO(LoginUserVO vo,LoginUser po) throws Exception{
		//此方法只添加，不编辑
	}

	@Override
	protected String getQueryStr(LoginUserVO vo){
		StringBuffer sql = new StringBuffer();
		if(StringUtils.isNotBlank(vo.getIpAddress())){
			sql.append(" and o.ipAddress=:ip ");
		}
		if(vo.getMinLoginTime()!=null){
			sql.append(" and o.loginTime >=:minLoginTime ");
		}
		if(vo.getMaxLoginTime()!=null){
			sql.append(" and o.loginTime <=:maxLoginTime ");
		}
		if(vo.getUser()!=null&&StringUtils.isNotBlank(vo.getUser().getUserCode())){
			sql.append(" and o.user.userCde like :Cde ");
		}
		if(vo.getUser()!=null&&StringUtils.isNotBlank(vo.getUser().getUserName())){
			sql.append(" and o.user.userName like :Name ");
		}
		return sql.toString();
	}
	
	@Override
	protected void setQueryParm(Query query, LoginUserVO vo){
		if(StringUtils.isNotBlank(vo.getIpAddress())){
			query.setParameter("ip",vo.getIpAddress());
		}
		if(vo.getMinLoginTime()!=null){
			query.setParameter("minLoginTime",vo.getMinLoginTime());
		}
		if(vo.getMaxLoginTime()!=null){
			query.setParameter("maxLoginTime",vo.getMaxLoginTime());
		}
		if(vo.getUser()!=null&&StringUtils.isNotBlank(vo.getUser().getUserCode())){
			query.setParameter("Cde",Common.SYMBOL_PERCENT+vo.getUser().getUserCode()+Common.SYMBOL_PERCENT);
		}
		if(vo.getUser()!=null&&StringUtils.isNotBlank(vo.getUser().getUserName())){
			query.setParameter("Name",Common.SYMBOL_PERCENT+vo.getUser().getUserName()+Common.SYMBOL_PERCENT);
		}
		
	}
	
	//用户最后的登录信息
	public LoginUser getLastLoginInfo(String userCde)throws Exception{
	   LoginUser po = null;
	   String sql="from LoginUser o where o.id=(select max(o.id) from LoginUser o where o.user.userCde =:cde)";
	   Query query=em.createQuery(sql);
	   query.setParameter("cde",userCde);
	   List<LoginUser> list=query.getResultList();
	   if(list.size()>0){
		 po=list.get(0);
	   }
	   return po;
	}
	
}