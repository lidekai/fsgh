package com.kington.fshg.service.info;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.excel.vo.ProductTypeExcelVO;
import com.kington.fshg.model.info.ProductType;
import com.kington.fshg.model.info.ProductTypeVO;
import com.kington.fshg.service.BaseServiceImpl;

public class ProductTypeServiceImpl extends BaseServiceImpl<ProductType, ProductTypeVO> implements
		ProductTypeService {
	private static final long serialVersionUID = 8660483061433699398L;
	private static int count = 0;

	@Override
	protected String getQueryStr(ProductTypeVO vo) throws Exception {
		StringBuffer sql = new StringBuffer();
		if(StringUtils.isNotBlank(vo.getProductTypeName()))
			sql.append(" and o.productTypeName like :name ");
		
		setOrderBy(" ORDER BY o.lev ");
		return sql.toString();
	}

	@Override
	protected void setQueryParm(Query query, ProductTypeVO vo) throws Exception {
		if(StringUtils.isNotBlank(vo.getProductTypeName()))
			query.setParameter("name", Common.SYMBOL_PERCENT
					+ vo.getProductTypeName() + Common.SYMBOL_PERCENT );
		
	}

	@Override
	protected void switchVO2PO(ProductTypeVO vo, ProductType po)
			throws Exception {
		if(StringUtils.isNotBlank(vo.getProductTypeName()))
			po.setProductTypeName(vo.getProductTypeName());
		if(vo.getProductType() != null)
			po.setProductType(vo.getProductType());
		if(vo.getLev() != null)
			po.setLev(vo.getLev());
	}
	
	@Override
	public int delete(Long id) throws Exception {
		if(id == null){
			return 0;
		}
		count = 0;
		return this.del(id);
	}
	
	private int del(Long id) throws Exception{
		if(id != null){
			String sql = "from ProductType p left join fetch p.productTypeList where p.id = :id";
			ProductType  e = (ProductType)em.createQuery(sql).setParameter("id", id).getSingleResult();
			List<ProductType> productTypes = e.getProductTypeList();
			Iterator<ProductType> itType = productTypes.iterator();
			while(itType.hasNext()){
				ProductType p = itType.next();
				if(Common.checkList(p.getProductTypeList()))
					del(p.getId());
				else{
					this.clear(p.getId().toString());
					count++;
				}
			}
			this.clear(id.toString());
			count++;
		}
		return count;
	}
	
	@Override
	public String doImports(List<ProductTypeExcelVO> list) throws Exception {
		StringBuilder r = new StringBuilder();
		int num = 0, succ = 0, fail = 0;
		
		for(ProductTypeExcelVO vo:list){
			if (vo == null || StringUtils.isBlank(vo.getCid())){
				continue;
			}
			num++;
			String s = StringUtils.EMPTY;
			String d = "序号" + num + "数据失败：";
			
			ProductType parent=null;
			ProductType pt = new ProductType();
			
			//校验数据有效性
			if(StringUtils.isBlank(vo.getProductTypeName()))
				s = "分类名称不能为空";
			else if(StringUtils.isNotBlank(vo.getProductType())){
				parent = getByName(vo.getProductType());
				if(parent == null)
					s = "所属分类系统不存在";
				else
					pt.setProductType(parent);
			}
			
			if(StringUtils.isBlank(s) && getByName(vo.getProductTypeName()) != null)	
				s = "产品分类已存在";
			
			try{
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getLev()))
					pt.setLev(Integer.parseInt(vo.getLev()));
				else
					pt.setLev(1);
			}catch(Exception e){
				s = "级别填写格式不正确";
			}
				
			if (StringUtils.isNotBlank(s)) {
				r.append(d + s + "<br/>");
				fail++;
				continue;
			}
			
			pt.setProductTypeName(vo.getProductTypeName());
			this.merge(pt);
			
			succ++;
		}
		
		return "导入完成，共导入 " + num + " 条数据，其中成功：" + succ + " 条，失败：" + fail+ " 条<br/>" + r.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProductType getByName(String productTypeName) throws Exception {
		String hql = "from ProductType o where o.productTypeName = :name";
		Query query = em.createQuery(hql);
		query.setParameter("name", productTypeName);
		List<ProductType> resultList =  query.getResultList();
		if(Common.checkList(resultList))	
			return resultList.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductType> getChildList() throws Exception {
		String hql = "from ProductType p left join fetch p.productTypeList where p.productTypeList is empty ";
		Query query = em.createQuery(hql);
		return query.getResultList();
	}

	@Override
	public List<ProductType> productType(ProductTypeVO vo) {
		try{
			String hql="select o from ProductType o where 1=1 ";
			hql+=getQueryStr(vo);
			Query query=em.createQuery(hql);
			setQueryParm(query, vo);
			return query.getResultList();		
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public void exportProductType(String[] heads, ProductType pt, Map<String, String> map) {
		String[] header=Common.getExportHeader("PRODUCTTYPE");
		//所属分类 分类名称
		for(String key:heads){
			int i=0;
			if(key.equals(header[i++])){
				if(pt.getProductType()!=null){
					map.put(key, pt.getProductType().getProductTypeName());
				}
			}else if(key.equals(header[i++])){
				map.put(key, pt.getProductTypeName());
			}
		}
		
	}

	
	
	
}
