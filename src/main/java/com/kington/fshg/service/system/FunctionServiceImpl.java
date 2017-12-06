package com.kington.fshg.service.system;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import com.jtframework.websupport.mvc.persist.PersistSupport;
import com.kington.fshg.common.Common;
import com.kington.fshg.common.ObjectUtil;
import com.kington.fshg.model.system.Function;
import com.kington.fshg.model.system.FunctionVO;

/**
 * 功能权限表
 * @author lijialin
 *
 */
public class FunctionServiceImpl extends PersistSupport implements FunctionService {
	private static final long serialVersionUID = -8402946791465917814L;

	@Override
	public Function saveOrUpdate(FunctionVO vo) throws Exception{
		if(vo == null || StringUtils.isBlank(vo.getFuncCde())) return null;
		//如果已存在,则更新,否则添加
		Function po = getByCde(vo.getFuncCde());
		if( po != null){
			po.setUpdateTime(new Date());
			if(StringUtils.isNotBlank(vo.getFuncName())) 	po.setFuncName(vo.getFuncName());
			if(vo.getParent() != null){
				po.setParent(getByCde(vo.getParent().getFuncCde()));
			}else{
				po.setParent(null);
			}
			po.setClevel(vo.getClevel());
			po.setSort(vo.getSort());
			po.setLink(vo.getLink());
			if(StringUtils.isNotBlank(vo.getPerLink())) 	po.setPerLink(vo.getPerLink());
			if(vo.getMenuType() != null) 					po.setMenuType(vo.getMenuType());
			if(vo.getState() != null) 						po.setState(vo.getState());
		}else{
			po = new Function();
			po = ObjectUtil.copy(vo, Function.class);
			po.setCreateTime(new Date());
			po.setUpdateTime(new Date());
		}
		return em.merge(po);
	}
	
	@Override
	public Function getByCde(String cde) throws Exception{
		return em.find(Function.class, cde);
	}
	
	@Override
	public FunctionVO getVOByCde(String cde) throws Exception{
		return ObjectUtil.copy(getByCde(cde),FunctionVO.class);
	}
	
	@Override
	public List<Function> getList(FunctionVO vo) throws Exception{

		StringBuffer sql = new StringBuffer("select o from Function o where 1=1 ");
		sql.append(getQueryStr(vo));
		sql.append(" order by o.clevel");
		
		Query query = em.createQuery(sql.toString());
		setQueryParm(query, vo);
		
		return query.getResultList();
	}

	public Integer getListCount(FunctionVO vo) throws Exception{

		StringBuffer sql = new StringBuffer("select count(o.funcCde) from Function o where 1=1 ");
		sql.append(getQueryStr(vo));
		
		Query query = em.createQuery(sql.toString());
		setQueryParm(query, vo);
		return ((Long)query.getSingleResult()).intValue();
	}
	
	private String getQueryStr(FunctionVO vo){
		StringBuffer sql = new StringBuffer();
		if(StringUtils.isNotBlank(vo.getFuncCde())) 	sql.append(" and o.funcCde like :funcCde");
		if(StringUtils.isNotBlank(vo.getFuncName())) 	sql.append(" and o.funcName like :funcName");
		if(vo.getState() != null) 						sql.append(" and o.state = :state");
		if(vo.getSort() != null) 						sql.append(" and o.sort = :sort");
		if(vo.getMenuType() != null) 					sql.append(" and o.menuType = :menuType");
		
		return sql.toString();
	}
	
	private void setQueryParm(Query query, FunctionVO vo){
		if(StringUtils.isNotBlank(vo.getFuncCde())) 	query.setParameter("funcCde",Common.SYMBOL_PERCENT + vo.getFuncCde() + Common.SYMBOL_PERCENT);
		if(StringUtils.isNotBlank(vo.getFuncName())) 	query.setParameter("funcName",Common.SYMBOL_PERCENT + vo.getFuncName() + Common.SYMBOL_PERCENT);
		if(vo.getState() != null) 						query.setParameter("state", vo.getState());
		if(vo.getSort() != null) 						query.setParameter("sort", vo.getSort());
		if(vo.getMenuType() != null) 					query.setParameter("menuType", vo.getMenuType());
	}
	@Override
	public boolean delete(String cde) throws Exception {
		try {
			String sql = "DELETE FROM RolePerm WHERE function.funcCde=:funcCde";
			Query query = em.createQuery(sql);
			query.setParameter("funcCde", cde);
			query.executeUpdate();
			
			sql = "DELETE FROM RolePath WHERE functionCde=:funcCde";
			query = em.createQuery(sql);
			query.setParameter("funcCde", cde);
			query.executeUpdate();
			
			sql = "DELETE FROM Function WHERE funcCde=:funcCde";
			query = em.createQuery(sql);
			query.setParameter("funcCde", cde);
			return query.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public int clear(String instr) throws Exception {
		if (instr == null || instr.length() == 0)
			return 0;

		Integer delNum = 0;
		String[] cdes = StringUtils.split(instr,Common.SYMBOL_COMMA);
		for(String s : cdes){
			if(delete(s)) delNum ++;
		}
		
		return delNum;
	}
	
	@Override
	public boolean checkFunctionExists(String cde) throws Exception{
		String sql = "SELECT COUNT(funcCde) FROM Function where funcCde = :funcCde";
		Query query = em.createQuery(sql);
		query.setParameter("funcCde", cde);
		return ((Long)query.getSingleResult()).intValue() > 0 ;
	}
}
