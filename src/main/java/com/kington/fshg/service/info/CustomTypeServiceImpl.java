package com.kington.fshg.service.info;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.info.CustomType;
import com.kington.fshg.model.info.CustomTypeVO;
import com.kington.fshg.service.BaseServiceImpl;

public class CustomTypeServiceImpl extends BaseServiceImpl<CustomType, CustomTypeVO> implements
		CustomTypeService {
	private static final long serialVersionUID = -693631943493040918L;

	@Override
	protected String getQueryStr(CustomTypeVO vo) throws Exception {
		StringBuffer sql = new StringBuffer();
		if(StringUtils.isNotBlank(vo.getName()))
			sql.append(" and o.name like :name ");
		return sql.toString();
	}

	@Override
	protected void setQueryParm(Query query, CustomTypeVO vo) throws Exception {
		if(StringUtils.isNotBlank(vo.getName()))
			query.setParameter("name", Common.SYMBOL_PERCENT + vo.getName() 
					+ Common.SYMBOL_PERCENT);
	}

	@Override
	protected void switchVO2PO(CustomTypeVO vo, CustomType po) throws Exception {
		if(po == null)
			po = new CustomType();
		if(StringUtils.isNotBlank(vo.getName()))
			po.setName(vo.getName());
		if(vo.getParents() != null)
			po.setParents(vo.getParents());
	}

}
