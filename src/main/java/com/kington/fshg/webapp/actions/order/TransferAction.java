package com.kington.fshg.webapp.actions.order;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.DateFormat;
import com.kington.fshg.common.ExcelUtil;
import com.kington.fshg.model.order.Transfer;
import com.kington.fshg.model.order.TransferVO;
import com.kington.fshg.service.order.TransferService;
import com.kington.fshg.webapp.actions.BaseAction;

public class TransferAction extends BaseAction {
	private static final long serialVersionUID = -3338524857723628446L;

	@Resource
	private TransferService transferService;
	
	private TransferVO vo;
	private String header;  //导出表头
	
	public String list(){
		try {
			if(vo == null){
				vo = new TransferVO();
				vo.setTransBeginTime(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
				vo.setTransEndTime(DateFormat.date2Str(new Date(), 2));
			}
			vo.setObjectsPerPage(Integer.MAX_VALUE);
			vo.setPageNumber(page);
			vo.initMyOrderStr(" order by o.transDate ");
			pageList = transferService.getPageList(vo);
			
			//页面显示合计
			setAttr("sum", transferService.countByVo(vo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	
	
	public String edit(){
		try {
			if(vo != null && Common.checkLong(vo.getId())){
				vo = transferService.getVOById(vo.getId());
			}else{
				this.addActionError("无效的操作ID！");
				return list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "edit";
	}
	
	/**
	 * 从U8中导入仓库调拨单
	 * @return
	 */
	public String imp(){
		try {
			int count = transferService.getTransferFromU8(vo);
			this.addActionMessage("共导入 " + count + "条数据！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list();
	}
	
	/**
	 * 批量删除
	 * @return
	 */
	public String delete(){
		try {
			boolean success = false;
			int count = transferService.clear(ids);
			
			success = count > 0;
			if(success){
				String mess ="共删除" + count + "条数据";
				if(count != ids.split(",").length){
					mess +="," + (ids.split(",").length - count) + "条数据删除失败，可能存在关联不可删除！";
				}
				this.addActionMessage(mess);
			}else{
				this.addActionError("数据删除失败，可能存在关联不可删除！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.addActionError("数据删除失败，可能存在关联不可删除！");
		}
		return list();
	}
	
	/**
	 * 导出仓库调拨单
	 * @return
	 * @throws Exception 
	 */
	public String exportTransfer() throws Exception{
		setAttr("transferHeader",Common.getExportHeader("TRANSFER"));
		if(StringUtils.isNotBlank(vo.getStockName())){
			String name = new String(vo.getStockName().getBytes("ISO8859_1"),"UTF8");
			vo.setStockName(name);
		}
		if(StringUtils.isNotBlank(vo.getInWhouseName())){
			String name = new String(vo.getInWhouseName().getBytes("ISO8859_1"),"UTF8");
			vo.setInWhouseName(name);
		}
		if(StringUtils.isNotBlank(vo.getOutWhouseName())){
			String name = new String(vo.getOutWhouseName().getBytes("ISO8859_1"),"UTF8");
			vo.setOutWhouseName(name);
		}
		return "export";
	}
	
	/**
	 * 执行导出
	 * @return
	 * @throws Exception 
	 */
	public String doExport() throws Exception{
		if(vo==null){
			vo=new TransferVO();
		}
		vo.setObjectsPerPage(Integer.MAX_VALUE);
		vo.initMyOrderStr(" order by o.transDate ");
		List<Transfer> list = transferService.getPageList(vo).getList();	
		List<Integer> intArr = new ArrayList<Integer>();
		String[] heads = StringUtils.split(header, ",");
		for(int i =0; i< heads.length; i++){
			intArr.add(25);
		}
		
		List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
		Map<String ,String> map = null;
		
		for(Transfer tf : list){
			map = new HashMap<String,String>();
			transferService.exportTransfer(heads, tf, map);
			listmap.add(map);
		}	
		HttpServletResponse response = ServletActionContext.getResponse();
		String title = "仓库调拨单";
		Integer[] columnSize = intArr.toArray(new Integer[]{});
		ExcelUtil.export(response, listmap, "仓库调拨单.xls", title, heads, columnSize);
		return null;
	}
	
	
	
	public String getHeader() {
		return header;
	}


	public void setHeader(String header) {
		this.header = header;
	}


	public TransferVO getVo() {
		return vo;
	}

	public void setVo(TransferVO vo) {
		this.vo = vo;
	}
	
	
}
