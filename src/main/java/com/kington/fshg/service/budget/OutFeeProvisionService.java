package com.kington.fshg.service.budget;

import java.util.List;
import java.util.Map;

import com.kington.fshg.common.PublicType.ApproveState;
import com.kington.fshg.model.budget.OutFeeProvision;
import com.kington.fshg.model.budget.OutFeeProvisionVO;
import com.kington.fshg.model.budget.ProvisionProject;
import com.kington.fshg.model.info.Custom;
import com.kington.fshg.model.info.Store;
import com.kington.fshg.service.BaseService;

/**
 *  合同外费用预提Service 
 */
public interface OutFeeProvisionService extends BaseService<OutFeeProvision, OutFeeProvisionVO> {

	/**
	 * 根据预提编码获取实体
	 * @param cde :预提编码
	 * @return OutFeeProvision实体
	 * @throws Exception
	 */
	public OutFeeProvision getByCde(String cde) throws Exception;
	
	/**
	 * 根据ID删除实体
	 * @param id：预提ID
	 * @return  true Or false
	 * @throws Exception
	 */
	public boolean delete(Long id) throws Exception;
	
	/**
	 * 更新预提审核状态
	 * @param provisionId :预提ID
	 * @param state ：待更新状态
	 * @throws Exception
	 */
	public void updateState(Long provisionId, ApproveState state) throws Exception;
	
	/**
	 * 得到预提编号
	 */
	public String getCode();
	
	
	/**
	 * 导出预提明细表，将表头信息和传入对象组装成map;
	 * @param heads:表头信息
	 * @param custom：客户实体
	 * @param map：待组装的map
	 * @param newHeads：新的表头信息，运用于导出双标题
	 * @param vo：合同外费用预提VO实体
	 * @param type:操作类型：导出预提明细、导出预提核销明细
	 * @throws Exception
	 */
	public void exportBudget(String[] heads,List<Custom> customList,Map<String,String> map ,
			List<Map<String,String>> listmap, OutFeeProvisionVO vo,String type) throws Exception;
	//按客户id排列预提产品明细List
	public Map<Long, List<Object[]>> getProDetailMap(OutFeeProvisionVO vo) throws Exception;
	//产品详细核销列表
	public List<Object[]> getProVerDetailList(OutFeeProvisionVO vo) throws Exception;
	//按客户id排列核销产品明细List
	public Map<Long, List<Object[]>> getProVerDetailMap(OutFeeProvisionVO vo) throws Exception;
	//按客户统计预提核销余额表
	public List<Object[]> getProVer(OutFeeProvisionVO vo) throws Exception;
	//客户产品预提核心余额表
	public List<Object[]> getProductProVer(OutFeeProvisionVO vo) throws Exception;
	
	//按客户id排列合同外预提List
	public Map<Long, List<OutFeeProvisionVO>> getOutFeeMap(OutFeeProvisionVO vo) throws Exception;
	//产品详细预提列表
	public List<Object[]> getProDetailList(OutFeeProvisionVO vo) throws Exception;
	
	/**
	 * 导出预提核销余额表
	 * @param heads:导出的表头信息
	 * @param obj：导出的数组对象
	 * @param map：map集合
	 * @throws Exception
	 */
	public void exportSum(String[] heads, Object[] obj, Map<String,String> map) throws Exception;
	
	/**
	 * 导出产品费用预提明细表
	 * @param heads
	 * @param obj
	 * @param map
	 * @throws Exception
	 */
	public void exportProBudget(String[] heads, Object[] obj, Map<String,String> map) throws Exception;
	
	/**
	 * 导出产品费用预提核销明细表
	 * @param heads
	 * @param listmap
	 * @param map
	 * @param customList
	 * @param vo
	 * @throws Exception
	 */
	public void exportProCharge(String[] heads, List<Map<String,String>> listmap, Map<String,String> map, OutFeeProvisionVO vo) throws Exception;
	
	/**
	 * 导出产品费用预提核销余额表
	 * @param heads
	 * @param obj
	 * @param map
	 * @throws Exception
	 */
	public void exportProSum(String[] heads, Object[] obj, Map<String,String> map) throws Exception;

	public List<OutFeeProvision> outFeeProvision(OutFeeProvisionVO vo);

	public void exportOutFeeProvision(String[] heads, OutFeeProvision ofp, Map<String, String> map);
	
	public Integer createSalePro(OutFeeProvisionVO vo, ProvisionProject pp) throws Exception;
	public Integer deleteSalePro(OutFeeProvisionVO vo, ProvisionProject pp) throws Exception;
	
	//预提核销余额表（按预提合同内type=1，合同外type=2）
	public Map<Long, List<Object[]>> getProMap(OutFeeProvisionVO vo, String type) throws Exception;
	/**
	 * 导出预提核销余额表
	 * @param heads
	 * @param obj
	 * @param map
	 * @throws Exception
	 */
	public void exportDiffer(String[] heads,List<Custom> customList,Map<String,String> map ,
			List<Map<String,String>> listmap, OutFeeProvisionVO vo) throws Exception;
	
}
