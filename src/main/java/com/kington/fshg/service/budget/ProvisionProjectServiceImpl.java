package com.kington.fshg.service.budget;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.kington.fshg.common.Common;
import com.kington.fshg.model.budget.ProvisionProject;
import com.kington.fshg.model.budget.ProvisionProjectVO;
import com.kington.fshg.service.BaseServiceImpl;

public class ProvisionProjectServiceImpl extends BaseServiceImpl<ProvisionProject, ProvisionProjectVO>
		implements ProvisionProjectService {
	private static final long serialVersionUID = 6424756580796554649L;

	@Override
	protected String getQueryStr(ProvisionProjectVO vo) throws Exception {
		StringBuffer sb = new StringBuffer();
		if(StringUtils.isNotBlank(vo.getFeeName()))
			sb.append(" and o.feeName like :feeName ");
		if(vo.getProjectType() != null)
			sb.append(" and o.projectType = :type ");
		return sb.toString();
	}

	@Override
	protected void setQueryParm(Query query, ProvisionProjectVO vo)
			throws Exception {
		if(StringUtils.isNotBlank(vo.getFeeName()))
			query.setParameter("feeName", Common.SYMBOL_PERCENT + vo.getFeeName() + Common.SYMBOL_PERCENT);
		if(vo.getProjectType() != null)
			query.setParameter("type", vo.getProjectType());
	}

	@Override
	protected void switchVO2PO(ProvisionProjectVO vo, ProvisionProject po)
			throws Exception {
		if(po == null)
			po = new ProvisionProject();
		if(StringUtils.isNotBlank(vo.getFeeName()))
			po.setFeeName(vo.getFeeName());
		if(vo.getProjectType() != null)
			po.setProjectType(vo.getProjectType());
	}

	@Override
	public boolean delete(Long id) throws Exception {
		String hql ="delete from bud_provision_project where id = :id ";
		Query query = em.createNativeQuery(hql);
		query.setParameter("id", id);
		return query.executeUpdate() > 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProvisionProject> provisionProject(ProvisionProjectVO vo) {
		try{
			String hql="select o from ProvisionProject o where 1=1 ";
			hql+=getQueryStr(vo)+" order by createTime desc";
			Query query=em.createQuery(hql);
			setQueryParm(query, vo);
			return query.getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public void exportProvisionProject(String[] heads, ProvisionProject pp, Map<String, String> map) {
		String[] header=Common.getExportHeader("PROVISIONPROJECT");
		//费用名称 项目类型
		for(String key:heads){
			int i=0;
			if(key.equals(header[i++])){
				map.put(key,pp.getFeeName());
			}else if(key.equals(header[i++])){
				if(pp.getProjectType()!=null){
					map.put(key, pp.getProjectType().getText());
				}
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProvisionProject getByName(String name) throws Exception {
		String hql = "from ProvisionProject where feeName ='" + name + "'";
		Query query = em.createQuery(hql);
		List<ProvisionProject> list = (List<ProvisionProject>)query.getResultList();
		return Common.checkList(list) ? list.get(0) : null;
	}

	
	

}
