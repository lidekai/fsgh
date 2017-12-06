package com.kington.fshg.service.info;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.excel.vo.AreaExcelVO;
import com.kington.fshg.model.info.Area;
import com.kington.fshg.model.info.AreaVO;
import com.kington.fshg.service.BaseServiceImpl;

public class AreaServiceImpl extends BaseServiceImpl<Area, AreaVO> implements
		AreaService {

	private static final long serialVersionUID = -4463752301734049065L;
	private static int count = 0;

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
			String sql = "from Area a left join fetch a.areaList where a.id = :id";
			Area  a = (Area)em.createQuery(sql).setParameter("id", id).getSingleResult();
			List<Area> areaList = a.getAreaList();
			Iterator<Area> itType = areaList.iterator();
			while(itType.hasNext()){
				Area area = itType.next();
				if(Common.checkList(area.getAreaList()))
					del(area.getId());
				else{
					this.clear(area.getId().toString());
					count++;
				}
			}
			this.clear(id.toString());
			count++;
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Area getByName(String areaName) throws Exception {
		String hql = "from Area o where o.areaName = :name";
		Query query = em.createQuery(hql);
		query.setParameter("name", areaName);
		List<Area> resultList =  query.getResultList();
		if(Common.checkList(resultList))	
			return resultList.get(0);
		return null;
	}

	@Override
	protected String getQueryStr(AreaVO vo) throws Exception {
		StringBuffer sql = new StringBuffer();
		if(StringUtils.isNotBlank(vo.getAreaName()))
			sql.append(" and o.areaName like :name ");
		
		setOrderBy(" ORDER BY o.lev ");
		return sql.toString();
	}

	@Override
	protected void setQueryParm(Query query, AreaVO vo) throws Exception {
		if(StringUtils.isNotBlank(vo.getAreaName()))
			query.setParameter("name", Common.SYMBOL_PERCENT
					+ vo.getAreaName() + Common.SYMBOL_PERCENT );
		
	}

	@Override
	protected void switchVO2PO(AreaVO vo, Area po) throws Exception {
		if(StringUtils.isNotBlank(vo.getAreaName()))
			po.setAreaName(vo.getAreaName());
		if(vo.getParentArea() != null)
			po.setParentArea(vo.getParentArea());
		if(vo.getLev() != null)
			po.setLev(vo.getLev());
	}

	@Override
	public String doImports(List<AreaExcelVO> list) throws Exception {
		StringBuilder r = new StringBuilder();
		int num = 0, succ = 0, fail = 0;
		
		for(AreaExcelVO vo:list){
			if (vo == null || StringUtils.isBlank(vo.getCid())){
				continue;
			}
			num++;
			String s = StringUtils.EMPTY;
			String d = "序号" + num + "数据失败：";
			
			Area parentArea = null;
			Area area = new Area();
			
			//校验数据有效性
			if(StringUtils.isBlank(vo.getAreaName()))
				s = "地区名称不能为空";
			else if(StringUtils.isNotBlank(vo.getParentArea())){
				parentArea = getByName(vo.getParentArea());
				if(parentArea == null)
					s = "所属地区系统不存在";
				else
					area.setParentArea(parentArea);
			}
			
				
			if(StringUtils.isBlank(s)){
				if(StringUtils.isNotBlank(vo.getParentArea())  
						&& getByParentName(vo.getParentArea(), vo.getAreaName()) != null)
					s = "地区名称已存在";	
				else if(StringUtils.isBlank(vo.getParentArea()) 
						&& getByName(vo.getAreaName()) != null)
					s = "地区名称已存在";	
			}
				
			
			try{
				if(StringUtils.isBlank(s) && StringUtils.isNotBlank(vo.getLev()))
					area.setLev(Integer.parseInt(vo.getLev()));
				else
					area.setLev(1);
			}catch(Exception e){
				s = "级别填写格式不正确";
			}
				
			if (StringUtils.isNotBlank(s)) {
				r.append(d + s + "<br/>");
				fail++;
				continue;
			}
			
			area.setAreaName(vo.getAreaName());
			this.merge(area);
			
			succ++;
		}
		
		return "导入完成，共导入 " + num + " 条数据，其中成功：" + succ + " 条，失败：" + fail+ " 条<br/>" + r.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Area> getLeafNode() throws Exception {
		String hql = "from Area a left join fetch a.areaList where a.areaList is empty ";
		Query query = em.createQuery(hql);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Area> getFristNode() throws Exception {
		String hql = "from Area a where a.parentArea is null ";
		Query query = em.createQuery(hql);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Area getByParentName(String parentName, String areaName)
			throws Exception {
		String hql = "from Area o where o.areaName = :name and o.parentArea.areaName = :parentName ";
		Query query = em.createQuery(hql);
		query.setParameter("name", areaName);
		query.setParameter("parentName", parentName);
		List<Area> resultList =  query.getResultList();
		if(Common.checkList(resultList))	
			return resultList.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Area> getByParentId(Long parentId) throws Exception {
		String hql = "from Area o where o.parentArea.id = :parentId ";
		Query query = em.createQuery(hql);
		query.setParameter("parentId", parentId);
		return query.getResultList();
	}

	@Override
	public List<Area> area(AreaVO vo) {
		try{
			String hql="select o from Area o where 1=1 ";
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
	public void exportArea(String[] heads, Area area, Map<String, String> map) {
		String[] header=Common.getExportHeader("AREA");
		//所属大区","地区名称
		for(String key:heads){
			int i=0;
			if(key.equals(header[i++])){
				if(area.getParentArea()!=null){
					map.put(key, area.getParentArea().getAreaName());
				}
			}else if(key.equals(header[i++])){
				map.put(key, area.getAreaName());
			}
		}
		
	}
	
	

}
