package com.kington.fshg.service.info;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.excel.vo.CustomProductExcelVO;
import com.kington.fshg.common.DateFormat;
import com.kington.fshg.common.PublicType.CustomerState;
import com.kington.fshg.common.excel.vo.CustomExcelVO;
import com.kington.fshg.model.info.Area;
import com.kington.fshg.model.info.Custom;
import com.kington.fshg.model.info.CustomProduct;
import com.kington.fshg.model.info.CustomVO;
import com.kington.fshg.model.info.Product;
import com.kington.fshg.model.info.CustomsType;
import com.kington.fshg.model.system.User;
import com.kington.fshg.model.system.UserVO;
import com.kington.fshg.service.BaseServiceImpl;
import com.kington.fshg.service.system.UserService;
import com.kington.fshg.webapp.security.UserContext;

public class CustomServiceImpl extends BaseServiceImpl<Custom, CustomVO> implements
		CustomService {
	private static final long serialVersionUID = -5701450602774680534L;
	
	@Resource
	private ProductService productService;

	@Resource
	private CustomsTypeService customsTypeService;
	
	@Resource
	private AreaService areaService;
	@Resource
	private UserService userService;

	@Override
	protected String getQueryStr(CustomVO vo) throws Exception {
		StringBuffer sql = new StringBuffer();
		if(StringUtils.isNotBlank(vo.getCustomCde()))
			sql.append(" and o.customCde like :code ");
		if(StringUtils.isNotBlank(vo.getCustomName()))
			sql.append(" and o.customName like :name ");
		if(vo.getState() != null)
			sql.append(" and o.state =:state ");
		
		if(vo.getArea() != null){
			if(Common.checkLong(vo.getArea().getId()))
				sql.append(" and o.area.id = :areaId ");
			else {
				if(vo.getArea().getParentArea() != null 
						&& Common.checkLong(vo.getArea().getParentArea().getId()))
					sql.append(" and o.area.parentArea.id = :parentAreaId ");
				if(StringUtils.isNotBlank(vo.getArea().getAreaName()))
					sql.append(" and o.area.areaName like :areaName ");
			}
		}
		if(Common.checkList(vo.getAreaIds()))
			sql.append(" and o.area.id in (:areaIds)");
		if(Common.checkList(vo.getParentAreaIds()))
			sql.append(" and o.area.parentArea.id in (:parentAreaIds)");
		if(StringUtils.isNotBlank(vo.getContacts()))
			sql.append(" and o.contacts like :contacts ");
		if(vo.getCustomType() != null && StringUtils.isNotBlank(vo.getCustomType().getCustomTypeName()))
			sql.append(" and o.customType.customTypeName like :customType ");
		if(vo.getUser() != null && Common.checkLong(vo.getUser().getId()))
			sql.append(" and o.user.id = :userId ");
		
		if(StringUtils.isNotBlank(vo.getStockCde()) || StringUtils.isNotBlank(vo.getProductName())){
			sql.append(" and o.id in (select cp.custom.id from CustomProduct cp where  ");
			if(StringUtils.isNotBlank(vo.getStockCde()))
				sql.append( " cp.product.stockCde = :stockCde ");
			if(StringUtils.isNotBlank(vo.getProductName()))
				sql.append(" cp.product.productName like :productName ");
			
			sql.append(")");
		}
		
		return sql.toString();
	}

	@Override
	protected void setQueryParm(Query query, CustomVO vo) throws Exception {
		if(StringUtils.isNotBlank(vo.getCustomCde()))
			query.setParameter("code", Common.SYMBOL_PERCENT + vo.getCustomCde() +Common.SYMBOL_PERCENT);
		if(StringUtils.isNotBlank(vo.getCustomName()))
			query.setParameter("name", Common.SYMBOL_PERCENT + vo.getCustomName() + Common.SYMBOL_PERCENT);
		if(vo.getState() != null)
			query.setParameter("state", vo.getState());
		if(vo.getArea() != null){
			if(Common.checkLong(vo.getArea().getId()))
				query.setParameter("areaId",vo.getArea().getId());
			else {
				if(vo.getArea().getParentArea() != null 
						&& Common.checkLong(vo.getArea().getParentArea().getId()))
					query.setParameter("parentAreaId",vo.getArea().getParentArea().getId());
				if(StringUtils.isNotBlank(vo.getArea().getAreaName()))
					query.setParameter("areaName", Common.SYMBOL_PERCENT + vo.getArea().getAreaName() + Common.SYMBOL_PERCENT);
			}
		}
		if(Common.checkList(vo.getAreaIds()))
			query.setParameter("areaIds", vo.getAreaIds());
		if(Common.checkList(vo.getParentAreaIds()))
			query.setParameter("parentAreaIds", vo.getParentAreaIds());
		
		if(StringUtils.isNotBlank(vo.getContacts()))
			query.setParameter("contacts", Common.SYMBOL_PERCENT + vo.getContacts() + Common.SYMBOL_PERCENT);
		if(vo.getCustomType() != null && StringUtils.isNotBlank(vo.getCustomType().getCustomTypeName()))
			query.setParameter("customType", Common.SYMBOL_PERCENT + vo.getCustomType().getCustomTypeName() +Common.SYMBOL_PERCENT);
		if(StringUtils.isNotBlank(vo.getStockCde()))
			query.setParameter("stockCde", vo.getStockCde());
		if(StringUtils.isNotBlank(vo.getProductName()))
			query.setParameter("productName", Common.SYMBOL_PERCENT + vo.getProductName() +Common.SYMBOL_PERCENT);
		if(vo.getUser() != null && Common.checkLong(vo.getUser().getId()))
			query.setParameter("userId", vo.getUser().getId());
	}

	@Override
	protected void switchVO2PO(CustomVO vo, Custom po) throws Exception {
		if(po == null)
			po = new Custom();
		if(StringUtils.isNotBlank(vo.getCustomCde()))
			po.setCustomCde(vo.getCustomCde());
		if(StringUtils.isNotBlank(vo.getCustomName()))
			po.setCustomName(vo.getCustomName());
		if(StringUtils.isNotBlank(vo.getAddress()))
			po.setAddress(vo.getAddress());
		if(vo.getArea() != null)
			po.setArea(vo.getArea());
		if(vo.getState() != null)
			po.setState(vo.getState());
		if(vo.getAmount() != null)
			po.setAmount(vo.getAmount());
		if(vo.getAccountDay() != null)
			po.setAccountDay(vo.getAccountDay());
		if(vo.getBeginTime() != null)
			po.setBeginTime(vo.getBeginTime());
		if(vo.getEndTime() != null)
			po.setEndTime(vo.getEndTime());
		if(StringUtils.isNotBlank(vo.getContactInfo()))
			po.setContactInfo(vo.getContactInfo());
		if(StringUtils.isNotBlank(vo.getContacts()))
			po.setContacts(vo.getContacts());
		if(vo.getCargoPrice() != null)
			po.setCargoPrice(vo.getCargoPrice());
		if(vo.getUnitPrice() != null)
			po.setUnitPrice(vo.getUnitPrice());
		if(vo.getHeavyPrice() != null)
			po.setHeavyPrice(vo.getHeavyPrice());
		if(vo.getCustomType() != null)
			po.setCustomType(vo.getCustomType());
		if(vo.getUser() != null)
			po.setUser(vo.getUser());
		if(StringUtils.isNotBlank(vo.getAreaManager()))
			po.setAreaManager(vo.getAreaManager());
		if(StringUtils.isNotBlank(vo.getRegManager()))
			po.setRegManager(vo.getRegManager());
		if(StringUtils.isNotBlank(vo.getRemark())){
			po.setRemark(vo.getRemark());
		}
		if(vo.getDeliverFee() != null)
			po.setDeliverFee(vo.getDeliverFee());
		if(StringUtils.isNotBlank(vo.getProvince()))
			po.setProvince(vo.getProvince());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Custom getByCde(String cde) throws Exception {
		String sql="from Custom o where o.customCde = :cde ";
		Query query = em.createQuery(sql);
		query.setParameter("cde", cde);
		List<Custom> list = query.getResultList();
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Custom getByName(String name) throws Exception {
		String sql="from Custom o where o.customName = :name ";
		Query query = em.createQuery(sql);
		query.setParameter("name", name);
		List<Custom> list = query.getResultList();
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void saveCuspro(CustomProduct cuspro) throws Exception {
		try {
			this.merge(cuspro);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public int deletCuspro(Long customId) throws Exception {
		String sql = "delete from info_custom_product where customId = " + customId;
		Query query = em.createNativeQuery(sql);
		return query.executeUpdate();		
	}

	@Override
	public String doImpCuspro(List<CustomProductExcelVO> list) throws Exception {
		StringBuilder r = new StringBuilder();
		int num = 0, succ = 0, fail = 0;
		
		for(CustomProductExcelVO vo:list){
			if (vo == null || StringUtils.isBlank(vo.getCid())){
				continue;
			}
			num++;
			String s = StringUtils.EMPTY;
			String d = "序号" + num + "数据失败：";
			
			Custom custom = null;
			Product product = null;
			
			//校验数据有效性
			if(StringUtils.isBlank(vo.getCustomCde())){
				s = "客户编码不能为空";
			}else if (StringUtils.isBlank(vo.getStockCde())){
				s = "存货编码不能为空";
			}
			
			if(StringUtils.isBlank(s)){
				custom = this.getByCde(vo.getCustomCde());
				
				if(custom == null)		s = "客户编码不正确";
				else{
					product = productService.getByCde(vo.getStockCde());
					if(product == null)   s = "存货编码不正确";
				}
			}
			
			if(StringUtils.isBlank(s)){
				String hql = "from CustomProduct o where o.custom.id = " + custom.getId() 
						+ " and o.product.id = " + product.getId();
				Query query = em.createQuery(hql);
				if(Common.checkList(query.getResultList()))
					s = "客户存货信息已存在";
			}
				
			if (StringUtils.isNotBlank(s)) {
				r.append(d + s + "<br/>");
				fail++;
				continue;
			}
			
			CustomProduct cp = new CustomProduct();
			cp.setCustom(custom);
			cp.setProduct(product);
			this.saveCuspro(cp);			
			
			succ++;
		}
		
		return "导入完成，共导入 " + num + " 条数据，其中成功：" + succ + " 条，失败：" + fail+ " 条<br/>" + r.toString();

		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getProductIds(Long customId) throws Exception {
		String hql = "select o.product.id from CustomProduct o where o.custom.id = " + customId;
		Query query = em.createQuery(hql);
		return query.getResultList();
	}

	@Override
	public boolean delete(Long id) throws Exception {
		String hql="delete from info_custom where id = :id";
		Query query = em.createNativeQuery(hql);
		query.setParameter("id", id);
		return query.executeUpdate() > 0;
	}

	@Override
	public String importCustom(List<CustomExcelVO> list) throws Exception {
		StringBuilder sb = new StringBuilder();
		int num =0 , succ = 0, fail = 0;
		String s = StringUtils.EMPTY;
		String d = StringUtils.EMPTY;
		for(CustomExcelVO vo : list){
			num++;
			if(vo.getCid() == null)
				continue;
			d="序号"+ num + "数据失败：";
			s = checkVO(vo);
			
			//根据客户编码判断是否已存在，如存在更新，否则新增；
			Custom po = null;
			if(StringUtils.isNotBlank(vo.getCustomCde())){
				po = this.getByCde(vo.getCustomCde().trim());
			}else{
				s = "客户编码不能为空";
			}
			if(po == null)
				po = new Custom();
			
			
			try {
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getAmount()))
					po.setAmount(Double.parseDouble(vo.getAmount()));
			} catch (Exception e) {
				s = "铺底额填写格式不正确";
			}
			
			try {
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getAccountDay()))
					po.setAccountDay(Integer.parseInt(vo.getAccountDay()));
			} catch (Exception e) {
				s = "账期填写格式不正确";
			}
			
			try {
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getCargoPrice()))
					po.setCargoPrice(Double.parseDouble(vo.getCargoPrice()));
			} catch (Exception e) {
				s = "泡货单价填写格式不正确";
			}
			
			try {
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getHeavyPrice()))
					po.setHeavyPrice(Double.parseDouble(vo.getHeavyPrice()));
			} catch (Exception e) {
				s = "重货单价填写格式不正确";
			}
			
			try {
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getUnitPrice()))
					po.setUnitPrice(Double.parseDouble(vo.getUnitPrice()));
			} catch (Exception e) {
				s = "按件单价填写格式不正确";
			}
			
			try {
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getDeliverFee()))
					po.setDeliverFee(Double.parseDouble(vo.getDeliverFee()));
			} catch (Exception e) {
				s = "配送费填写格式不正确";
			}
			
			if(s != null){
				sb.append(d + s + "<br/>");
				fail++;
				continue;
			}
			
			
			po.setCustomCde(vo.getCustomCde().trim());
			po.setCustomName(vo.getCustomName().trim());
			po.setArea(areaService.getByParentName(vo.getParentAreaName().trim(), vo.getAreaName().trim()));
			if(StringUtils.isNotBlank(vo.getUserCode()))
				po.setUser(userService.getByCde(vo.getUserCode().trim()));
			po.setCustomType(customsTypeService.getByName(vo.getCustomTypeName().trim()));
			po.setState(CustomerState.valueOf(vo.getState()));
			if(vo.getBeginTime() != null)
				po.setBeginTime(DateFormat.str2Date(vo.getBeginTime(), 2));
			if(vo.getEndTime() != null)
				po.setEndTime(DateFormat.str2Date(vo.getEndTime(), 2));
			po.setContacts(vo.getContacts());
			po.setContactInfo(vo.getContactInfo());
			po.setAddress(vo.getAddress());
			po.setAreaManager(vo.getAreaManager());
			po.setRegManager(vo.getRegManager());
			po.setRemark(vo.getRemark());
			po.setProvince(vo.getProvince());
			this.merge(po);
			succ++;
		}
		return "导入完成，共导入 " + num + " 条数据，其中成功：" + succ + " 条，失败：" + fail+ " 条<br/>" + sb.toString();
	}
	
	public String checkVO(CustomExcelVO vo) throws Exception{
		if(vo == null)
			return "导入对象不能为空";
		if(StringUtils.isBlank(vo.getCustomName()))
			return "客户名称不能为空";
		
		if(StringUtils.isBlank(vo.getCustomTypeName())){
			return "客户类型不能为空";
		}else{
			CustomsType ct = customsTypeService.getByName(vo.getCustomTypeName().trim());
			if(ct == null)
				return "该客户类型不存在";
		}
		
		if(StringUtils.isBlank(vo.getParentAreaName())){
			return "所属大区不能为空";
		}else{
			Area area = areaService.getByName(vo.getAreaName().trim());
			if(area == null)
				return "该大区不存在";
		}
		
		if(StringUtils.isBlank(vo.getAreaName())){
			return "所属地区不能为空";
		}else{
			Area area = areaService.getByParentName(vo.getParentAreaName().trim(), vo.getAreaName().trim());
			if(area == null)
				return "该地区不存在";
		}
		
		if(StringUtils.isNotBlank(vo.getUserCode())){
			User user = userService.getByCde(vo.getUserCode().trim());
			if(user == null)
				return "该业务员不存在";
		}
		
		CustomerState cs = null;
		for(CustomerState c : cs.values()){
			if(c.getText().equals(StringUtils.replace(vo.getState()," ", ""))){
				cs = c;
			}
		}
		if(cs == null)
			return "客户状态格式不正确，正确格式为：正常合作,终止合作";
		else
			vo.setState(cs.getName());
		
		if (StringUtils.isNotBlank(vo.getBeginTime()) || 
				StringUtils.isNotBlank(vo.getEndTime())) {
			if (DateFormat.str2Date(vo.getBeginTime(), 2) == null || 
					DateFormat.str2Date(vo.getEndTime(), 2) == null) {
				return "日期格式不正确，格式为:2016-08-25";
			}
		}
		
		return null;
	}

	
	@Override
	public void exportCustom(String[] heads, Custom ct, Map<String, String> map) {
		String[] header=Common.getExportHeader("CUSTOM");
	
		for(String key:heads){
			int i=0;
			if(key.equals(header[i++])){
				map.put(key, ct.getCustomCde());   //客户编码
			}else if(key.equals(header[i++])){
				map.put(key, ct.getCustomName());  //客户名称
			}else if(key.equals(header[i++])){
				if(ct.getArea()!=null){
					map.put(key, ct.getArea().getParentArea().getAreaName());  //所属大区
				}
			}else if(key.equals(header[i++])){
				if(ct.getArea()!=null){
					map.put(key, ct.getArea().getAreaName());  //所属地区
				}
			}else if(key.equals(header[i++])){
				if(ct.getUser()!=null){
					map.put(key, ct.getUser().getUserName()); //所属业务员
				}
			}else if(key.equals(header[i++])){
				if(ct.getCustomType()!=null){
					map.put(key, ct.getCustomType().getCustomTypeName()); //客户类型
				}
			}else if(key.equals(header[i++])){
				map.put(key, ct.getProvince());  //省份
			}else if(key.equals(header[i++])){
				map.put(key, ct.getContacts());  //联系人
			}else if(key.equals(header[i++])){
				if(ct.getState()!=null){
					map.put(key, ct.getState().getText());  //客户状态
				}
			}else if(key.equals(header[i++])){
				map.put(key, ct.getContactInfo());  //联系电话
			}else if(key.equals(header[i++])){
				map.put(key, ct.getAddress());  //地址
			}else if(key.equals(header[i++])){
				if(ct.getAmount()!=null){
					map.put(key, ct.getAmount().toString());	//铺底额
				}			  
			}else if(key.equals(header[i++])){
				if(ct.getAccountDay()!=null){
					map.put(key, ct.getAccountDay().toString());  //账期(天)
				}				
			}else if(key.equals(header[i++])){
				if(ct.getBeginTime()!=null){
					map.put(key, DateFormat.date2Str(ct.getBeginTime(), 2));  //合作起始日期
				}
				
			}else if(key.equals(header[i++])){
				if(ct.getEndTime()!=null){
					map.put(key, DateFormat.date2Str(ct.getEndTime(), 2));  //合作终止日期
				}				
			}else if(key.equals(header[i++])){
				if(ct.getCargoPrice()!=null){
					map.put(key, ct.getCargoPrice().toString());	//泡货单价
				}			  
			}else if(key.equals(header[i++])){
				if(ct.getHeavyPrice()!=null){
					map.put(key, ct.getHeavyPrice().toString());	//重货单价
				}			  
			}else if(key.equals(header[i++])){
				if(ct.getUnitPrice()!=null){
					map.put(key, ct.getUnitPrice().toString());	//按件单价
				}else{
					map.put(key, "0.0");
				}
			}else if(key.equals(header[i++])){
				if(ct.getDeliverFee()!=null){
					map.put(key, ct.getDeliverFee().toString());	//配送费
				}			  
			}else if(key.equals(header[i++])){
				map.put(key, ct.getAreaManager());  //地区经理
			}else if(key.equals(header[i++])){
				map.put(key, ct.getRegManager());  //大区经理
			}else if(key.equals(header[i++])){
				map.put(key, ct.getRemark());  //备注
			}
		}	
	}

	

}
