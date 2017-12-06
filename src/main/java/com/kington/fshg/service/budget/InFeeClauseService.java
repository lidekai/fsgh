package com.kington.fshg.service.budget;

import java.util.List;
import java.util.Map;

import com.kington.fshg.common.excel.vo.InFeeClauseExcelVO;
import com.kington.fshg.model.budget.InFeeClause;
import com.kington.fshg.model.budget.InFeeClauseVO;
import com.kington.fshg.service.BaseService;

public interface InFeeClauseService extends
		BaseService<InFeeClause, InFeeClauseVO> {
	
	public InFeeClause getByCusIdAndYear(Long customId, Integer year);

	public void exportInFeeClause(String[] heads, InFeeClause ifc, Map<String, String> map);

	public Object importInFeeClause(List<InFeeClauseExcelVO> list) throws Exception;

}
