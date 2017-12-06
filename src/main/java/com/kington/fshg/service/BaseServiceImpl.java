package com.kington.fshg.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import com.jtframework.websupport.mvc.persist.PersistSupport;
import com.jtframework.websupport.pagination.PageList;
import com.kington.fshg.common.Common;
import com.kington.fshg.common.ObjectUtil;
import com.kington.fshg.common.PublicType;
import com.kington.fshg.common.SafeHtmlUtil;
import com.kington.fshg.model.POSupport;
import com.kington.fshg.model.VOSupport;
import com.kington.fshg.model.system.UserVO;
import com.kington.fshg.webapp.security.UserContext;

/**
 * SERVICE接口基本实现类,实现常用方法自动调用
 * 
 * @author lijialin
 */
public abstract class BaseServiceImpl<PO extends POSupport, VO extends VOSupport>
		extends PersistSupport {
	private static final long serialVersionUID = -5510060541011789396L;
	
	protected Class<PO> poClass;
	protected Class<VO> voClass;
	
	/**
	 *用于自定义排序,如果有值则使用自定义的排序，否则按创建时间排序 
	 */
	private String orderBy;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BaseServiceImpl() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		poClass = (Class) params[0];
		voClass = (Class) params[1];
	}

	/**
	 * 更新数据
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public PO saveOrUpdate(VO vo) throws Exception {
		PO po = null;
		if (vo.getId() == null || vo.getId() <= 0) {// 添加
			po = ObjectUtil.copy(vo, poClass);
		}
		else {// 编辑
			po = getById(poClass, vo.getId());
			if (po != null) {
				switchVO2PO(vo, po);
			} else {
				throw new Exception("无效的ID");
			}
		}
		SafeHtmlUtil.EncodeByObject(po);
		if (po.getCreateTime()==null) {
			po.setCreateTime(new Date());
			po.setUpdateTime(po.getCreateTime());
			
		} else {
			po.setUpdateTime(new Date());
		}
		return em.merge(po);
	}

	public <T extends POSupport> T merge(T t) throws Exception{
		if (t.getCreateTime()==null) {
			t.setCreateTime(new Date());
			t.setUpdateTime(t.getCreateTime());
		} else {
			t.setUpdateTime(new Date());
		}
		return em.merge(t);
	}
	
	/**
	 * 根据ID获取PO对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public PO getById(Long id) throws Exception {
		return this.getById(poClass, id);
	}
	
	public VO getVOById(Long id) throws Exception {
		return ObjectUtil.copy(this.getById(poClass, id),voClass);
	}

	/**
	 * 根据条件查询列表数据
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public PageList<PO> getPageList(VO vo) throws Exception {
		PageList<PO> pageList = new PageList<PO>();
		pageList.setPageNumber(vo.getPageNumber());
		pageList.setObjectsPerPage(vo.getObjectsPerPage());
		pageList.setList(getList(vo));
		pageList.setFullListSize(getListCount(vo));

		return pageList;
	}
	
	/**
	 * 查询列表数据
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<PO> getList(VO vo) throws Exception{

		StringBuffer sql = new StringBuffer("select o from "
				+ poClass.getSimpleName() + " o ");
		//抓取ManyToOne对象
		if(StringUtils.isNotBlank(vo.getMyForeignStr())){
			sql.append(vo.getMyForeignStr());
		}
		sql.append(" where 1=1 ");
		//抽象类BaseServiceImpl的抽象方法getQueryStr(vo)返回一个字符串
		sql.append(getQueryStr(vo));
		
		if(StringUtils.isNotBlank(vo.getMyQueryStr())){
			sql.append(vo.getMyQueryStr());
		}
		if(StringUtils.isNotBlank(vo.getMyOrderStr())){
			//VO中的排序优先生效
			sql.append(vo.getMyOrderStr());
		}else if(StringUtils.isNotBlank(getOrderBy())){
			//抽象类BaseServiceImpl的方法getOrderBy返回字符串orderBy
			sql.append(getOrderBy());
		}else{
			sql.append(" order by o.createTime desc ");
		}
		
		Query query = em.createQuery(sql.toString());
		//抽象类BaseServiceImpl的抽象方法setQueryParm(Query query,VO vo)
		//动态设置查询参数
		setQueryParm(query, vo);
		//设置第一个结果集的索引号为从0开始
		query.setFirstResult(vo.getStartIndex());
		query.setMaxResults(vo.getObjectsPerPage());
		
		return query.getResultList();
	}
	
	public Integer getListCount(VO vo) throws Exception{

		StringBuffer sql = new StringBuffer("select count(o.id) from "+poClass.getSimpleName()+" o where 1=1 ");
		//抽象类BaseServiceImpl的抽象方法getQueryStr(vo)返回一个字符串
		sql.append(getQueryStr(vo));
		if(StringUtils.isNotBlank(vo.getMyQueryStr())){
			sql.append(vo.getMyQueryStr());
		}
		Query query = em.createQuery(sql.toString());
		setQueryParm(query, vo);
		return ((Long)query.getSingleResult()).intValue();
	}

	/**
	 * 根据ID列表删除数据，传入数据前需要先校验数据的合法
	 * @param instr:以,号分隔的ID串
	 * @return
	 * @throws Exception
	 */
	public int clear(String ids) throws Exception {
		try{
			if (ids == null || ids.length() == 0)
				return 0;
			String[] tmpId = StringUtils.split(ids, Common.SYMBOL_COMMA);
			if (tmpId == null || tmpId.length == 0)
				return 0;
			ids = StringUtils.join(tmpId, Common.SYMBOL_COMMA);
			
			String sql = "delete from " + poClass.getSimpleName() + " where id in("	+ ids + ")";
			Query query = em.createQuery(sql);
	
			return query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 根据参数拼接动态参数，继续此类需要实现此抽象方法
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	protected abstract String getQueryStr(VO vo) throws Exception;
	
	/**
	 * 动态设置查询参数，继续此类需要实现此抽象方法
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	protected abstract void setQueryParm(Query query,VO vo) throws Exception;

	/**
	 * 抽象方法:将VO属性COPY至PO中
	 * 
	 * @param vo
	 * @param po
	 * @throws Exception
	 */
	protected abstract void switchVO2PO(VO vo, PO po) throws Exception;

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
}
