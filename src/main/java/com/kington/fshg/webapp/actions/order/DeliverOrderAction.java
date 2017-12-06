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
import com.kington.fshg.common.PublicType;
import com.kington.fshg.common.PublicType.ActType;
import com.kington.fshg.model.info.Area;
import com.kington.fshg.model.order.DeliverOrder;
import com.kington.fshg.model.order.DeliverOrderVO;
import com.kington.fshg.model.system.User;
import com.kington.fshg.service.info.CustomService;
import com.kington.fshg.service.order.DeliverOrderService;
import com.kington.fshg.service.system.UserService;
import com.kington.fshg.webapp.actions.BaseAction;
import com.kington.fshg.webapp.security.UserContext;

public class DeliverOrderAction extends BaseAction {
	private static final long serialVersionUID = 5174492301359509456L;

	@Resource
	private DeliverOrderService deliverOrderService;
	@Resource
	private UserService userService;
	@Resource
	private CustomService customService;
	
	private DeliverOrderVO vo;
	
	private String header;  //导出表头
	
	public String list(){
		try {
			if(vo == null){
				vo = new DeliverOrderVO();
				vo.setBeginTime(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
				vo.setEndTime(DateFormat.date2Str(new Date(), 2));
				vo.setTimeType("3");
			}
			vo.setObjectsPerPage(Integer.MAX_VALUE);
			vo.setPageNumber(page);
			
			UserContext uc = UserContext.get();
			User user = userService.getByCde(uc.getUserCode());
			String roleName = user.getRoles().get(0).getRoleName();
			if(StringUtils.equals(roleName, "业务员")){
				vo.setUserId(user.getId());
			}else if(StringUtils.equals(roleName, "地区经理") || StringUtils.equals(roleName, "大区经理")){
				List<Long> areaList = new ArrayList<Long>();
				for(Area a : user.getAreas()){
					areaList.add(a.getId());
				}
				if(StringUtils.equals(roleName, "地区经理"))
					vo.setAreaIds(areaList);
				else if(StringUtils.equals(roleName, "大区经理"))
					vo.setParentAreaIds(areaList);
			}
			
			vo.initMyOrderStr(" order by o.orderDate,o.cdlCode ");
			pageList = deliverOrderService.getPageList(vo);
			//页面显示合计
			setAttr("sumList", deliverOrderService.countByVo(vo));
			//销售类型List
			setAttr("saleTypeList", deliverOrderService.getSaleTypeList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	
	
	public String edit(){
		try {
			if(vo != null && Common.checkLong(vo.getId())){
				String timeType = vo.getTimeType();
				String beginTime = vo.getBeginTime();
				String endTime = vo.getEndTime();
				
				vo = deliverOrderService.getVOById(vo.getId());
				vo.setTimeType(timeType);
				vo.setBeginTime(beginTime);
				vo.setEndTime(endTime);
				if(act == null)
					setAct(ActType.EDIT);
			}else{
				this.addActionError("无效的操作ID");
				return list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "edit";
	}
	
	/**
	 * 从U8中导入发货订单
	 * @return
	 */
	public String imp(){
		try {
			int count = deliverOrderService.impDeliveOrderFromU8(vo);
			this.addActionMessage("共导入 " + count + "条数据；");
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
			int count = deliverOrderService.clear(ids);
			
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
			this.addActionError("数据删除失败，可能存在关联不可删除！");
		}
		return list();
	}
	
	public String isRebate(){
		int count = 0;
		String isType = ServletActionContext.getRequest().getParameter("isType");
		for(String str : ids.split(",")){
			try {
				if(deliverOrderService.updateDeliver(Long.parseLong(str), isType)){
					count++;
				}
			} catch (Exception e) {
				continue;
			}
		}
		this.addActionMessage("共更新"+ count + "条数据！");
		return list();
	}
	
	public String update(){
		try {
			Double total = (vo.getCountPrice()/vo.getCount()) * vo.getReceiveNum();
			total = PublicType.setDoubleScale(total);
			vo.setTotal(total);
			deliverOrderService.saveOrUpdate(vo);
			this.addActionMessage("操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.addActionError("操作失败！");
		}
		//vo = new DeliverOrderVO();
		return list();
	}
	
	/**
	 * 导出发货订单表
	 * @throws UnsupportedEncodingException 
	 */
	public String exportDeliverOrder() throws UnsupportedEncodingException{
		setAttr("deliverOrderHeader",Common.getExportHeader("DELIVERORDER"));
		if(StringUtils.isNotBlank(vo.getCustomName())){
			String name = new String(vo.getCustomName().getBytes("ISO8859_1"),"UTF8");
			vo.setCustomName(name);
		}
		if(StringUtils.isNotBlank(vo.getStockName())){
			String name = new String(vo.getStockName().getBytes("ISO8859_1"),"UTF8");
			vo.setStockName(name);
		}
		return "export";
	}
	

	/**
	 * 执行导出操作
	 */
	public String  doExport() throws Exception{
		if(vo==null){
			vo=new DeliverOrderVO();
			vo.setBeginTime(DateFormat.date2Str(DateFormat.getYearMonthFirst(new Date(), 0), 2));
			vo.setEndTime(DateFormat.date2Str(new Date(), 2));
			vo.setTimeType("1");
		}
		vo.setObjectsPerPage(Integer.MAX_VALUE);
		vo.setPageNumber(page);
		vo.initMyOrderStr(" order by o.orderDate ");
		//角色判断
		UserContext uc = UserContext.get();
		User user = userService.getByCde(uc.getUserCode());
		String roleName = user.getRoles().get(0).getRoleName();
		if(StringUtils.equals(roleName, "业务员")){
			vo.setUserId(user.getId());
		}else if(StringUtils.equals(roleName, "地区经理") || StringUtils.equals(roleName, "大区经理")){
			List<Long> areaList = new ArrayList<Long>();
			for(Area a : user.getAreas()){
				areaList.add(a.getId());
			}
			if(StringUtils.equals(roleName, "地区经理"))
				vo.setAreaIds(areaList);
			else if(StringUtils.equals(roleName, "大区经理"))
				vo.setParentAreaIds(areaList);
		}
		
		List<DeliverOrder> list = deliverOrderService.getPageList(vo).getList();
		List<Integer> intArr = new ArrayList<Integer>();
		String[] heads = StringUtils.split(header, ",");
		for(int i =0; i< heads.length; i++){
			intArr.add(25);
		}
		
		List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
		Map<String ,String> map = null;
		
		for(DeliverOrder d : list){
			map = new HashMap<String,String>();
			deliverOrderService.exportDeliverOrder(heads, d, map);
			listmap.add(map);
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		String title = "发货订单表";
		Integer[] columnSize = intArr.toArray(new Integer[]{});
		ExcelUtil.export(response, listmap, "发货订单表.xls", title, heads, columnSize);
		
		return null;
	}
	
	
	
	
	
	
	
	public String getHeader() {
		return header;
	}


	public void setHeader(String header) {
		this.header = header;
	}


	public DeliverOrderVO getVo() {
		return vo;
	}
	public void setVo(DeliverOrderVO vo) {
		this.vo = vo;
	}
}
