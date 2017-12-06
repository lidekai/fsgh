package com.kington.fshg.service.budget;

import java.util.List;
import java.util.Map;

import com.kington.fshg.common.excel.vo.StoreSaleProExcelVO;
import com.kington.fshg.model.budget.StoreSaleProvision;
import com.kington.fshg.model.budget.StoreSaleProvisionVO;
import com.kington.fshg.service.BaseService;

public interface StoreSaleProService extends BaseService<StoreSaleProvision, StoreSaleProvisionVO> {
	/**
	 * 导入
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public String doImports(List<StoreSaleProExcelVO> list) throws Exception;
	
	public void export(String[] heads, StoreSaleProvision ssp, Map<String, String> map);
}
