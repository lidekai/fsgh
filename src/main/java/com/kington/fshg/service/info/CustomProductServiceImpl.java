package com.kington.fshg.service.info;

import javax.persistence.Query;

import com.kington.fshg.model.info.CustomProduct;
import com.kington.fshg.model.info.CustomProductVO;
import com.kington.fshg.service.BaseServiceImpl;

public class CustomProductServiceImpl extends BaseServiceImpl<CustomProduct, CustomProductVO> implements
		CustomProductService {
	private static final long serialVersionUID = -4936154914806050821L;


	@Override
	protected String getQueryStr(CustomProductVO vo) throws Exception {
		return null;
	}

	@Override
	protected void setQueryParm(Query query, CustomProductVO vo)
			throws Exception {
	}

	@Override
	protected void switchVO2PO(CustomProductVO vo, CustomProduct po)
			throws Exception {
	}

}
