package com.kington.fshg.webapp.actions.system;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;


import com.kington.fshg.common.Common;
import com.kington.fshg.common.PublicType.ActType;
import com.kington.fshg.common.PublicType.DictType;
import com.kington.fshg.model.EnumTypeVO;
import com.kington.fshg.model.system.DictVO;
import com.kington.fshg.service.system.DictService;
import com.kington.fshg.webapp.actions.BaseAction;

public class DictAction extends BaseAction {
	private static final long serialVersionUID = 7846315805495159213L;
	@Resource
	private DictService dictService;
	
	private DictVO vo;
	private List<EnumTypeVO> list;
	
	/**
	 * 将字典类型转换成LIST对象
	 */
	private void getDictTypeList(){
		setList(new ArrayList<EnumTypeVO>());
		for(DictType type : DictType.values()){
			EnumTypeVO enumTypeVO = new EnumTypeVO();
			enumTypeVO.setName(type.name());
			enumTypeVO.setText(type.getText());
			getList().add(enumTypeVO);
		}
	}
	
	/**
	 * 列表查询数据
	 * @return
	 */
	public String list() {
		try {
			if (vo == null) vo = new DictVO();
			vo.setPageNumber(page);
			pageList = dictService.getPageList(vo);
		} catch (Exception e) {
			return doException(e);
		}
		getDictTypeList();
		if (act == ActType.SINGLE) {
			return Common.PATH_SINGLE;
		} else {
			return Common.PATH_LIST; 
		}
	}
	
	/**
	 * 跳转至编辑界面
	 * @return
	 */
	public String edit() {
		try {
			if(vo == null || !Common.checkLong(vo.getId())){
				vo = new DictVO();
				setAct(ActType.ADD);
			}else{
				vo = (DictVO) dictService.getVOById(vo.getId());
				if(vo== null) 
					return doException("无效的操作ID");
				setAct(ActType.EDIT);
			}
		} catch (Exception e) {
			return doException(e);
		}
		getDictTypeList();
		return Common.PATH_EDIT;
	}

	/**
	 * 添加或更新数据
	 * @return
	 */
	public String update() {
		try {
			dictService.saveOrUpdate(vo);
		} catch (Exception e) {
			return doException(e);
		}
		this.addActionMessage(Common.OPER_SUCCESS);
		vo = new DictVO();
		return list();
	}
	
	/**
	 * 更新前校验数据合法性
	 */
	public void validateUpdate() {
		this.setInputResult("edit");
		if (vo == null) {
			this.addActionError("对象为空");
		} else {
			if(vo.getDictType() == null){
				this.addActionError("类别不能为空");
			}
			if(vo.getValue() == null){
				this.addActionError("常量数值不能为空");
			}
		}
	}
	
	/**
	 * 批量删除
	 * @return
	 */
	public String delete() {
		try {
			boolean success = false;
			int count = dictService.clear(ids);
			
			success = count > 0;
			if (success) {
				this.addActionMessage("共删除 " + count + "记录");
			} else {
				this.addActionError("数据删除失败");
			}
		} catch (Exception e) {
			return doException(e);
		}
		return list();
	}
	
	/**
	 * 选择类型列表
	 * @author liangjiahong
	 */
	public String get4dict() throws Exception {
		if (vo == null)
			vo = new DictVO();
		try {
			setAttr("dictList",dictService.getList(vo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "get4dict";
	}
	
	public void setVo(DictVO vo) {
		this.vo = vo;
	}

	public DictVO getVo() {
		return vo;
	}

	public List<EnumTypeVO> getList() {
		return list;
	}

	public void setList(List<EnumTypeVO> list) {
		this.list = list;
	}
}
