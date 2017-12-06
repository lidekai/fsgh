package com.kington.fshg.service.system;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;


import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.DictType;
import com.kington.fshg.common.PublicType.StateType;
import com.kington.fshg.model.system.Dict;
import com.kington.fshg.model.system.DictVO;
import com.kington.fshg.service.BaseServiceImpl;

public class DictServiceImpl extends BaseServiceImpl<Dict,DictVO> implements DictService {
	private static final long serialVersionUID = 3178156769246106251L;

	@Override
	protected void switchVO2PO(DictVO vo,Dict po) throws Exception{
		if (po == null)
			po = new Dict();

		if(vo.getDictType() != null) 								po.setDictType(vo.getDictType());
		po.setRemark(vo.getRemark());
		if(vo.getValue() != null)	po.setValue(vo.getValue());
		if(StringUtils.isNotBlank(vo.getDictName()))		po.setDictName(vo.getDictName());
	}

	@Override
	protected String getQueryStr(DictVO vo){
		StringBuffer sql = new StringBuffer();
		if(vo.getDictType() != null) 					sql.append(" and o.dictType = :dictType");
		if(StringUtils.isNotBlank(vo.getDictName()))	sql.append(" and o.dictName like :dictName ");
		return sql.toString();
	}
	
	@Override
	protected void setQueryParm(Query query, DictVO vo){
		if(vo.getDictType() != null) 					query.setParameter("dictType", vo.getDictType());
		if(StringUtils.isNotBlank(vo.getDictName()))	query.setParameter("dictName", Common.SYMBOL_PERCENT + vo.getDictName() + Common.SYMBOL_PERCENT);
	}
	
	@Override
	public List<Dict> getByType(DictType type) throws Exception{
		StringBuffer sb = new StringBuffer("from Dict o where dictType=:type ");
		Query query = em.createQuery(sb.toString());
		query.setParameter("type", type);
		return query.getResultList();
	}
	

	@Override
	public Dict getByName(String name) throws Exception {
		String sql = "from Dict where dictName =:name";
		Query query = em.createQuery(sql);
		query.setParameter("name", name);
		List<Dict> list = query.getResultList();
		if(list.size() > 0)		return list.get(0);
		else 					return null;
	}
	
	public boolean checkExists(String name) throws Exception{
		String sql = "select count(id) from Dict where dictName =:name";
		Query query = em.createQuery(sql);
		query.setParameter("name", name);
		return ((Long)query.getSingleResult()).intValue() > 0 ? true : false;
	}

}