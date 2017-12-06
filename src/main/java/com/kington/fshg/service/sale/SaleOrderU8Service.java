package com.kington.fshg.service.sale;

import java.util.List;

import com.kington.fshg.model.EnumTypeVO;
import com.kington.fshg.model.sale.SaleOrderCode;
import com.kington.fshg.model.sale.SaleOrderU8DetailVO;
import com.kington.fshg.model.sale.SaleOrderU8VO;
import com.kington.fshg.service.BaseService;

public interface SaleOrderU8Service extends BaseService<SaleOrderCode, SaleOrderU8VO> {

	public String getCode();
	
	public List<EnumTypeVO> getDepartmentList();//查询U8部门表 department
	
	public List<EnumTypeVO> getSaletypeList();//查询U8销售类型表 saletype
	
	public List<Object[]> getExchList();//查询U8币种表 exch
	
	public List<EnumTypeVO> getPersonList(String depCode);//查询U8业务员表 person 根据部门编码
	
	public Boolean insertU8(SaleOrderU8VO vo, Double ddxye) throws Exception ;//新增U8销售订单
	
	public Boolean updateU8(SaleOrderU8VO vo, Double ddxye) throws Exception;//更新U8销售单
	
	public List<SaleOrderU8DetailVO> getDetailList(SaleOrderU8VO vo) throws Exception; //查询U8销售订单明细表
	
	public SaleOrderU8VO getVO(Long id) throws Exception ;//根据id查询U8销售订单
	
	public Boolean deleteOrder(Long id) throws Exception;//根据id删除整个订单
	
	public Boolean deleteDetail(Long id, Double ddxye, String customCde) throws Exception;//根据id删除表体
	
	public Object[] getRate(String cinvcode) throws Exception;//根据存货编码获取U8主计量与辅计量换算率
	
	public Double getXye(String customerCode);//根据客户编码查询U8信用额
	
	public Double getXyye(String customerCode);//根据客户编码查询U8信用余额

	public List<SaleOrderU8DetailVO> getDetailList2(SaleOrderU8VO vo) throws Exception;
}
