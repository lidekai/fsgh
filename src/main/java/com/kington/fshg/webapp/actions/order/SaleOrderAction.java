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
import com.kington.fshg.common.PublicType.ActType;
import com.kington.fshg.model.info.Area;
import com.kington.fshg.model.order.SaleOrder;
import com.kington.fshg.model.order.SaleOrderVO;
import com.kington.fshg.model.system.User;
import com.kington.fshg.service.info.CustomService;
import com.kington.fshg.service.order.SaleOrderService;
import com.kington.fshg.service.system.UserService;
import com.kington.fshg.webapp.actions.BaseAction;
import com.kington.fshg.webapp.security.UserContext;

public class SaleOrderAction extends BaseAction {

	private static final long serialVersionUID = -8906932693865540763L;
	
	@Resource
	private SaleOrderService saleOrderService;
	@Resource
	private UserService userService;
	@Resource
	private CustomService customService;
	
	private SaleOrderVO vo;
	
	private String header;  //导出表头
	
	
	public String list() throws Exception{
		if(vo == null){
			vo = new SaleOrderVO();
			vo.setOrderDateStart(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
			vo.setOrderDateEnd(DateFormat.date2Str(new Date(), 2));
		} 
		vo.setObjectsPerPage(Integer.MAX_VALUE);
		vo.setPageNumber(page);
		
		//用户角色判断
		UserContext uc = UserContext.get();
		User user = userService.getByCde(uc.getUserCode());
		String roleName = user.getRoles().get(0).getRoleName();
		if(StringUtils.equals(roleName, "业务员"))
			vo.setUserId(user.getId());
		else if(StringUtils.equals(roleName, "地区经理") || StringUtils.equals(roleName, "大区经理")){
			List<Long> areaList = new ArrayList<Long>();
			for(Area a : user.getAreas()){
				areaList.add(a.getId());
			}
			if(StringUtils.equals(roleName, "地区经理"))
				vo.setAreaIds(areaList);
			else if(StringUtils.equals(roleName, "大区经理"))
				vo.setParentAreaIds(areaList);
		}
		
		vo.initMyOrderStr(" order by o.orderDate ");		
		pageList = saleOrderService.getPageList(vo);
		
		//List<Object> list = saleOrderService.countByVo(vo);
		//页面显示合计
		setAttr("sumList", saleOrderService.countByVo(vo));
		
		return "list";
	}
	
	/**
	 * 删除销售订单
	 */
	public String delete() {
		boolean success = false;
		try {
			int count = saleOrderService.clear(ids);

			success = count > 0;
			if (success) {
				String mesg = "共删除 " + count + "记录";
				if(ids.split(",").length != count)
					mesg += (ids.split(",").length - count) + "条记录未删除成功，可能数据已存在引用，不可删除";
				this.addActionMessage(mesg);
			} else {
				this.addActionError("数据删除失败,可能数据已存在引用，不可删除");
			}
			return list();
		} catch (Exception e) {
			return doException("数据删除失败,可能数据已存在引用");
		}
	}
	
	/**
	 * 从U8导入销售订单
	 */
	public String imp() throws Exception{
		
		int count = saleOrderService.getSaleOrderFromU8(vo);			
		this.addActionMessage("共导入" + count + "条记录");
		return list();
	}
	
	public String edit() throws Exception {
		try {
			if(vo != null && Common.checkLong(vo.getId())){
				vo = saleOrderService.getVOById(vo.getId());
				if(act == null)
					setAct(ActType.EDIT);
			} else {
				setAct(ActType.ADD);
			}
		} catch (Exception e) {
			return doException(e);
		}
		return "edit";
	}
	
	/**
	 * 导出销售订单表
	 * @throws Exception 
	 */
	public String exportSaleOrder() throws Exception{
		setAttr("saleOrderHeader",Common.getExportHeader("SALEORDER"));
		if(StringUtils.isNotBlank(vo.getCustomName())){
			String name = new String(vo.getCustomName().getBytes("ISO8859_1"),"UTF8");
			vo.setCustomName(name);
		}
		return "export";
	}
	
	/**
	 * 执行导出操作
	 */
	public String  doExport() throws Exception{
		if(vo == null){
			vo = new SaleOrderVO();
			vo.setOrderDateStart(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
			vo.setOrderDateEnd(DateFormat.date2Str(new Date(), 2));
		} 
		vo.setObjectsPerPage(Integer.MAX_VALUE);
		vo.initMyOrderStr(" order by o.orderDate ");
		//用户角色判断
		UserContext uc = UserContext.get();
		User user = userService.getByCde(uc.getUserCode());
		String roleName = user.getRoles().get(0).getRoleName();
		if(StringUtils.equals(roleName, "业务员"))
			vo.setUserId(user.getId());
		else if(StringUtils.equals(roleName, "地区经理") || StringUtils.equals(roleName, "大区经理")){
			List<Long> areaList = new ArrayList<Long>();
			for(Area a : user.getAreas()){
				areaList.add(a.getId());
			}
			if(StringUtils.equals(roleName, "地区经理"))
				vo.setAreaIds(areaList);
			else if(StringUtils.equals(roleName, "大区经理"))
				vo.setParentAreaIds(areaList);
		}
		
		List<SaleOrder> list = saleOrderService.getPageList(vo).getList();	
		List<Integer> intArr = new ArrayList<Integer>();
		String[] heads = StringUtils.split(header, ",");
		for(int i =0; i< heads.length; i++){
			intArr.add(25);
		}
		
		List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
		Map<String ,String> map = null;
		
		for(SaleOrder po : list){
			map = new HashMap<String,String>();
			saleOrderService.exportSaleOrder(heads, po, map);
			listmap.add(map);
		}
		
		HttpServletResponse response = ServletActionContext.getResponse();
		String title = "销售订单表";
		Integer[] columnSize = intArr.toArray(new Integer[]{});
		ExcelUtil.export(response, listmap, "销售订单表.xls", title, heads, columnSize);
		return null;
	}

	public SaleOrderVO getVo() {
		return vo;
	}

	public void setVo(SaleOrderVO vo) {
		this.vo = vo;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}
	
	
}
