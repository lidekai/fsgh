package com.kington.fshg.service.info;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.excel.vo.CustomsTypeExcelVO;
import com.kington.fshg.model.info.CustomsType;
import com.kington.fshg.model.info.CustomsTypeVO;
import com.kington.fshg.service.BaseServiceImpl;

public class CustomsTypeServiceImpl extends BaseServiceImpl<CustomsType, CustomsTypeVO> implements
		CustomsTypeService {
	private static final long serialVersionUID = -693631943493040918L;
	
	private static int count = 0;

	@Override
	protected String getQueryStr(CustomsTypeVO vo) throws Exception {
		StringBuffer sql = new StringBuffer();
		if(StringUtils.isNotBlank(vo.getCustomTypeName()))
			sql.append(" and o.customTypeName like :name ");
		
		setOrderBy(" ORDER BY o.lev ");
		return sql.toString();
	}

	@Override
	protected void setQueryParm(Query query, CustomsTypeVO vo) throws Exception {
		if(StringUtils.isNotBlank(vo.getCustomTypeName()))
			query.setParameter("name", Common.SYMBOL_PERCENT + vo.getCustomTypeName() 
					+ Common.SYMBOL_PERCENT);
	}

	@Override
	protected void switchVO2PO(CustomsTypeVO vo, CustomsType po) throws Exception {
		if(po == null)
			po = new CustomsType();
		if(StringUtils.isNotBlank(vo.getCustomTypeName()))
			po.setCustomTypeName(vo.getCustomTypeName());
		if(vo.getParents() != null)
			po.setParents(vo.getParents());
	}

	@Override
	public CustomsType getByName(String customTypeName) throws Exception {
		String sql = "from CustomsType o where o.customTypeName = :name ";
		Query query= em.createQuery(sql);
		query.setParameter("name", customTypeName);
		@SuppressWarnings("unchecked")
		List<CustomsType> list = query.getResultList();
		if(Common.checkList(list))
			return list.get(0);
		return null;
	}

	@Override
	public int delete(Long id) throws Exception {
		if(id == null)
			return 0;
		count = 0;
		return this.del(id);
	}
	
	public int del(Long id) throws Exception{
		if(id != null){
			String sql = "from CustomsType p left join fetch p.customsTypeList where p.id = :id";
			Query query = em.createQuery(sql);
			query.setParameter("id", id);
			CustomsType ct = (CustomsType)query.getSingleResult();
			List<CustomsType> ctList = ct.getCustomsTypeList();
			Iterator<CustomsType> itType = ctList.iterator();
			while(itType.hasNext()){
				CustomsType c = itType.next();
				if(Common.checkList(c.getCustomsTypeList())){
					this.del(c.getId());
				}else{
					this.clear(c.getId().toString());
					count++;
				}
			}
			this.clear(ct.getId().toString());
			count++;
		}
		return count;
	}

	@Override
	public String doImports(List<CustomsTypeExcelVO> list) throws Exception {
		StringBuilder sb = new StringBuilder();
		int num = 0, succ = 0, fail= 0;
		String s;
		String d = StringUtils.EMPTY;
		
		for(CustomsTypeExcelVO vo : list){
			num++;
			
			CustomsType parents = null;
			s = StringUtils.EMPTY;
			if(vo == null || StringUtils.isBlank(vo.getCid())){
				continue;
			}
			d = "序号" + num + "数据失败：";
			
			//检验数据的有效性
			if(StringUtils.isBlank(vo.getCustomsTypeName())){
				s = "分类名称不能为空";
			}else if(StringUtils.isNotBlank(vo.getCustomsType()) && this.getByName(vo.getCustomsType()) == null){
				s = "所属分类不存在";
			}
			
			if(getByName(vo.getCustomsTypeName()) != null){
				s = "分类已存在";
			}
			
			if(StringUtils.isNotBlank(s)){
				sb.append(d + s + "</br>");
				fail ++;
				continue;
			}
			
			if(StringUtils.isNotBlank(vo.getCustomsType())){
				parents = getByName(vo.getCustomsType());
			}
			
			CustomsType ct = new CustomsType();
			if(parents != null)
				ct.setParents(parents);
			ct.setCustomTypeName(vo.getCustomsTypeName());
			ct.setLev(1);
			if(StringUtils.isNotBlank(vo.getLev()))
				ct.setLev(Integer.parseInt(vo.getLev()));
			this.merge(ct);
			succ++;
		}
		
		return "导入完成，共导入 " + num + " 条数据，其中成功：" + succ + " 条，失败：" + fail+ " 条<br/>" + sb.toString();
	}

	@Override
	public List<CustomsType> customsType(CustomsTypeVO vo) {
		try{
			String hql="select o from CustomsType o where 1=1 ";
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
	public void exportCustomsType(String[] heads, CustomsType ct, Map<String, String> map) {
		String[] header=Common.getExportHeader("CUSTOMSTYPE");
		//所属分类 分类名称
		for(String key:heads){
			int i=0;
			if(key.equals(header[i++])){
				if(ct.getParents()!=null){
					map.put(key, ct.getParents().getCustomTypeName());
				}
			}else if(key.equals(header[i++])){
				map.put(key, ct.getCustomTypeName());
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomsType> getLeafNode() {
		String hql = "from CustomsType a left join fetch a.customsTypeList where a.customsTypeList is empty ";
		Query query = em.createQuery(hql);
		return query.getResultList();
	}

	
	
	
}
