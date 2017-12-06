package com.kington.fshg.service.system;

import java.io.File;
import java.util.List;

import com.kington.fshg.common.PublicType.AttachType;
import com.kington.fshg.model.system.Attach;
import com.kington.fshg.model.system.AttachVO;
import com.kington.fshg.service.BaseService;

/**
 * 项目附件服务接口
 * 
 * @author Aaron.Tao
 *
 */
public interface AttachService extends BaseService<Attach,AttachVO> {
	public List<Attach> getAttList(AttachVO vo) throws Exception;
	
	/**
	 * 保存附件列表
	 */
	public List<String> saveAttachList(AttachType type, AttachVO vo, File[] uploads, String[] uploadsFileName) throws Exception;
	
	/**
	 * 保存单个文件
	 */
	public Attach saveAttach(AttachType type, AttachVO vo, File uploads, String uploadsFileName) throws Exception;
	
	/**
	 * 关联到实体
	 * @param example
	 * @return
	 */
	public int relate(AttachVO example);
	
	public void download(Long id);
	
	/**
	 * 删除正式附件列表的记录
	 */
	public void deleteAttach(Attach po) throws Exception;
	
	/**
	 * 更新临时附件为正式
	 */
	public void updataAttach(Attach po) throws Exception;
	
}
