package com.kington.fshg.service.info;

import java.util.List;
import java.util.Map;

import com.kington.fshg.common.excel.vo.PromotersExcelVO;
import com.kington.fshg.model.info.Promoters;
import com.kington.fshg.model.info.PromotersVO;
import com.kington.fshg.service.BaseService;

public interface PromotersService extends BaseService<Promoters, PromotersVO> {

	/**
	 * 根据身份证号获取对象
	 * @param IdCare
	 * @return
	 * @throws Exception
	 */
	public Promoters getByIDCare(String IdCare) throws Exception;
	
	/**
	 * 导入促销员信息
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public String doImports(List<PromotersExcelVO> list) throws Exception;


	public void exportPromoters(String[] heads, Promoters p, Map<String, String> map);
}
