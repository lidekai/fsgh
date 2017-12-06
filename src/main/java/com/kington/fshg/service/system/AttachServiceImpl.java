package com.kington.fshg.service.system;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.FileUtils4UNC;
import com.kington.fshg.common.PublicType.AttachType;
import com.kington.fshg.common.SafeHtmlUtil;
import com.kington.fshg.model.system.Attach;
import com.kington.fshg.model.system.AttachVO;
import com.kington.fshg.service.BaseServiceImpl;

/**
 * 项目附件服务接口实现类
 * 
 * @author Aaron.Tao
 *
 */
public class AttachServiceImpl extends BaseServiceImpl<Attach,AttachVO> implements AttachService {
	private static final long serialVersionUID = 8176597992745707469L;

	@Override
	protected void switchVO2PO(AttachVO vo, Attach po) throws Exception{
		if (po == null)			po = new Attach();

		if(vo.getAttachType() != null)					po.setAttachType(vo.getAttachType());
		if(vo.getNodeId() != null) 						po.setNodeId(vo.getNodeId());
		if(StringUtils.isNotBlank(vo.getName())) 		po.setName(SafeHtmlUtil.HTMLEntityEncode(vo.getName()));
		if(StringUtils.isNotBlank(vo.getExt())) 		po.setExt(SafeHtmlUtil.HTMLEntityEncode(vo.getExt()));
		if(StringUtils.isNotBlank(vo.getPath())) 		po.setPath(vo.getPath());
		if(vo.getCount() != null) 						po.setCount(vo.getCount());
	}
	
	@Override
	public List<String> saveAttachList(AttachType type, AttachVO vo, File[] uploads, String[] uploadsFileName) throws Exception{
		
		List<String> errMsg = new ArrayList<String>();
		if (uploads !=null) {
			File upload = null;
			String uploadFileName = "";
			for (int i = 0; i < uploads.length; i++) {
				upload = uploads[i];
				uploadFileName = uploadsFileName[i];
				if (upload.exists()){
					this.saveAttach(type, vo, upload, uploadFileName);
				} else {
					errMsg.add("\"" + uploadFileName + "\"为空文件，无法上传，请重新选择");
				}
			}
		}
		
		return errMsg;
	}
	
	@Override
	public List<Attach> getAttList(AttachVO vo) throws Exception{
		return this.getList(vo);
	}
	
	@Override
	public Attach saveAttach(AttachType type, AttachVO vo, File uploads, String uploadsFileName) throws Exception{
		try {
			AttachVO attVo = new AttachVO();
			attVo.setAttachType(type);
			if(vo != null){
				attVo.setNodeId(vo.getNodeId());
			}
			attVo.setName(uploadsFileName);
			attVo.setExt(FilenameUtils.getExtension(uploadsFileName));
			if(type != AttachType.GSZZ){
				File savedFile = FileUtils4UNC.saveForEncrypt(uploads, uploadsFileName, null);
				attVo.setPath(FileUtils4UNC.getRelativePath(savedFile.getPath()));
			}else if(type == AttachType.GSZZ){
				File newFile = FileUtils4UNC.save(FileUtils4UNC.Dir.Photo, uploads,  uploadsFileName);
				attVo.setPath( FileUtils4UNC.Dir.Photo.getHttpPath() + newFile.getName());
			}
			return this.saveOrUpdate(attVo);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	
	@Override
	protected String getQueryStr(AttachVO vo){
		StringBuffer sql = new StringBuffer();
		if(vo.getNodeId() != null) 						sql.append(" and o.nodeId = :nodeId");
		if(vo.getAttachType() != null) 					sql.append(" and o.attachType = :attachType");
		if(StringUtils.isNotBlank(vo.getName()))		sql.append(" and o.name like :name ");
		return sql.toString();
	}
	
	@Override
	protected void setQueryParm(Query query, AttachVO vo){
		if(vo.getNodeId() != null) 							query.setParameter("nodeId", vo.getNodeId());
		if(vo.getAttachType() != null) 						query.setParameter("attachType", vo.getAttachType());
		if(StringUtils.isNotBlank(vo.getName()))			query.setParameter("name", Common.SYMBOL_PERCENT + vo.getName() + Common.SYMBOL_PERCENT);
	}
	

	@Override
	public int relate(AttachVO example) {
		String ids = ServletActionContext.getRequest().getParameter("multiFileIds");
		if(!Common.checkIds(ids)){
			return 0;
		}
		StringBuilder sql = new StringBuilder();
		if (example.getAttachType() != null)				sql.append("attachType=:AttachType, ");
		if (example.getNodeId() != null)					sql.append("nodeId=:nodeId, ");
		
		if (sql.length() == 0) return 0;
		sql.append("updateTime=:currentDate,version=version+1 ");
		
		sql.append("where id in (" + ids + " -1) and nodeId <= 0");
		Query query = em.createQuery("update Attach set " +  sql.toString());
		if (example.getAttachType() != null)				query.setParameter("AttachType", example.getAttachType());
		if (example.getNodeId() != null)					query.setParameter("nodeId", example.getNodeId());
		query.setParameter("currentDate", new Date());
		
		return query.executeUpdate();
	}

	@Override
	public void download(Long id) {
		try {
			AttachVO avo = this.getVOById(id);
			if(avo.getAttachType() != AttachType.GSZZ){
				File file = new File(FileUtils4UNC.getRealPath(avo.getPath()));
				FileUtils4UNC.download(file,avo.getName());
			}else{
				File file = new File(ServletActionContext.getServletContext().getRealPath(avo.getPath()));
				FileUtils4UNC.download1(file,avo.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void deleteAttach(Attach po) throws Exception{
		String sql="delete from  Attach o where o.attachType=:attachType and o.nodeId=:nodeId ";
		Query q = em.createQuery(sql);
		q.setParameter("attachType", po.getAttachType());
		q.setParameter("nodeId", po.getNodeId());
		q.executeUpdate();
	}

	@Override
	public void updataAttach(Attach po) throws Exception {
		String sql="UPDATE sys_attach SET attachType='COMPANY' where nodeId="+po.getNodeId()+" and attachType='"+po.getAttachType()+"'";
		Query q =em.createNativeQuery(sql);
		q.executeUpdate();
	}
}
