package com.kington.fshg.webapp.actions.sale;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.DateFormat;
import com.kington.fshg.common.JsUtils;
import com.kington.fshg.common.PublicType;
import com.kington.fshg.common.PublicType.DictType;
import com.kington.fshg.model.EnumTypeVO;
import com.kington.fshg.model.info.Area;
import com.kington.fshg.model.info.CustomVO;
import com.kington.fshg.model.info.Product;
import com.kington.fshg.model.sale.SaleOrderU8DetailVO;
import com.kington.fshg.model.sale.SaleOrderU8VO;
import com.kington.fshg.model.system.Dict;
import com.kington.fshg.model.system.User;
import com.kington.fshg.service.info.AreaService;
import com.kington.fshg.service.info.ProductService;
import com.kington.fshg.service.sale.SaleOrderU8Service;
import com.kington.fshg.service.system.DictService;
import com.kington.fshg.service.system.UserService;
import com.kington.fshg.webapp.actions.BaseAction;
import com.kington.fshg.webapp.security.UserContext;

public class SaleOrderU8Action extends BaseAction{

	private static final long serialVersionUID = -3844470415300455471L;
	
	@Resource
	private SaleOrderU8Service saleOrderU8Service;
	@Resource
	private ProductService productService;
	@Resource
	private UserService userService;
	@Resource
	private DictService dictService;
	
	private SaleOrderU8VO vo;
	private SaleOrderU8DetailVO detailVO;
	private Double ddxye;//订单信用额问题；1修改信用额0不修改
	private Double xye = 0d;//客户信用额度
	private Double xyye = 0d;//客户信用余额
	
	public String list() throws Exception{
		
		if(vo != null){
			//用户角色判断
			UserContext uc = UserContext.get();
			User user = userService.getByCde(uc.getUserCode());
			String roleName = user.getRoles().get(0).getRoleName();
			
			if(StringUtils.equals(roleName, "业务员")){
				if(StringUtils.isNotBlank(user.getUserName()))
					vo.setPresonName(user.getUserName());
				else
					return "list";
			}else if(StringUtils.equals(roleName, "地区经理")){
				if(!Common.checkList(user.getAreas())){
					String areaNames = "";
					for(Area a : user.getAreas()){
						if(StringUtils.isNotBlank(areaNames))
							areaNames += ",";
						areaNames += a.getAreaName();
					}
					vo.setCustomArea(areaNames);
				}else
					return "list";
			}else if(StringUtils.equals(roleName, "大区经理")){
				String areaNames = "";
				for(Area a : user.getAreas()){
					for(Area area : a.getAreaList()){
						if(StringUtils.isNotBlank(areaNames))
							areaNames += ",";
						areaNames += "'" + area.getAreaName() + "'";
					}
				}
				if(StringUtils.isNotBlank(areaNames)){
					vo.setCustomArea(areaNames);
				}else
					return "list";
			}
			
		}else
			vo = new SaleOrderU8VO();
		
		if(StringUtils.isBlank(vo.getOrderCode()) && StringUtils.isBlank(vo.getBeginTime()))
			vo.setBeginTime(DateFormat.date2Str(new Date(), 2));
		if(StringUtils.isBlank(vo.getOrderCode()) && StringUtils.isBlank(vo.getEndTime()))
			vo.setEndTime(vo.getBeginTime());
		
		setAttr("detailList", saleOrderU8Service.getDetailList2(vo));
		return "list";
	}
	
	public String edit() throws Exception{
		if(vo == null){
			vo = new SaleOrderU8VO();
			vo.setOrderCode(saleOrderU8Service.getCode());
		}else if (Common.checkLong(vo.getId())){
			try {
				vo = saleOrderU8Service.getVO(vo.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotBlank(vo.getDepartmentCode()))
			setAttr("personList", saleOrderU8Service.getPersonList(vo.getDepartmentCode()));
			
		setAttr("departmentList", saleOrderU8Service.getDepartmentList());
		setAttr("saletypeList", saleOrderU8Service.getSaletypeList());
		
		List<Dict> dictList = dictService.getByType(DictType.DDXYE);
		if(Common.checkList(dictList))
			ddxye = dictList.get(0).getValue();
		if(ddxye == 1 && StringUtils.isNotBlank(vo.getCustomCode())){
			xye = saleOrderU8Service.getXye(vo.getCustomCode());
			xyye = saleOrderU8Service.getXyye(vo.getCustomCode());
		}
		
		return "edit";
	}
	
	public String update() throws Exception{
		if(vo != null){
			boolean flag = false;
			
			if(Common.checkLong(vo.getId())){//更新
				flag = saleOrderU8Service.updateU8(vo,ddxye);
			}else{//新增
				try {
					flag = saleOrderU8Service.insertU8(vo,ddxye);
				} catch (Exception e) {
					flag = false;
					e.printStackTrace();
				}
			}
			
			if(flag)
				addActionMessage("操作成功");
			else
				addActionError("操作失败");
		}
		return list();
	}
	
	public String detail() throws Exception{
		if(detailVO != null){
			if(StringUtils.isNotBlank(detailVO.getChmc()))
				detailVO.setChmc(new String(detailVO.getChmc().getBytes("ISO8859_1"), "UTF8"));
			if(StringUtils.isNotBlank(detailVO.getXjpfl()))
				detailVO.setXjpfl(new String(detailVO.getXjpfl().getBytes("ISO8859_1"), "UTF8"));
			if(StringUtils.isNotBlank(detailVO.getDfm()))
				detailVO.setDfm(new String(detailVO.getDfm().getBytes("ISO8859_1"), "UTF8"));
			if(StringUtils.isNotBlank(detailVO.getZjl()))
				detailVO.setZjl(new String(detailVO.getZjl().getBytes("ISO8859_1"), "UTF8"));
			if(StringUtils.isNotBlank(detailVO.getBz()))
				detailVO.setBz(new String(detailVO.getBz().getBytes("ISO8859_1"), "UTF8"));
			if(StringUtils.isNotBlank(detailVO.getChyt()))
				detailVO.setChyt(new String(detailVO.getChyt().getBytes("ISO8859_1"), "UTF8"));
			if(StringUtils.isNotBlank(detailVO.getGgxh()))
				detailVO.setGgxh(new String(detailVO.getGgxh().getBytes("ISO8859_1"), "UTF8"));
			if(StringUtils.isNotBlank(detailVO.getHh())){
				setAttr("productList", productService.getListByNumber(detailVO.getHh()));
			}
			if(detailVO.getJs() != null && detailVO.getSl() != null
					&& detailVO.getJs() != 0)
				detailVO.setZhl(PublicType.setDoubleScale6(detailVO.getSl() / detailVO.getJs()));
			if(StringUtils.isNotBlank(detailVO.getHh()))
				setAttr("productList", productService.getListByNumber(detailVO.getHh()));
		}
		return "detail";
	}
	
	public String delete() throws Exception{
		
		List<Dict> dictList = dictService.getByType(DictType.DDXYE);
		if(Common.checkList(dictList))
			ddxye = dictList.get(0).getValue();
		
		boolean flag = false;
		if(vo != null && Common.checkLong(vo.getId()))
			flag = saleOrderU8Service.deleteOrder(vo.getId());
		if(flag)
			addActionMessage("删除成功");
		else
			addActionError("删除失败");
		return list();
	}
	
	public void deleteDetail(){
		boolean flag = false;
		
		String customCde = ServletActionContext.getRequest().getParameter("customCde");
		try {
			if(id != null)
				flag = saleOrderU8Service.deleteDetail(id,ddxye,customCde);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(flag)
			JsUtils.writeText("1");
		else
			JsUtils.writeText("0," + xyye);
		
	} 
	
	/**
	 * 选择币种
	 * @return
	 */
	public String selectCexch(){
		try {
			setAttr("cexchList", saleOrderU8Service.getExchList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "selectCexch";
	}
	
	//根据部门编码查询业务负责人列表
	public void getPersonJson() throws Exception{
		String result = "";
		String departmentCode = ServletActionContext.getRequest().getParameter("departmentCode");
		if(StringUtils.isNotBlank(departmentCode)){
			List<EnumTypeVO> list = saleOrderU8Service.getPersonList(departmentCode);
			if(Common.checkList(list)){
				for(EnumTypeVO e : list){
					result += "{\"code\":\"" + e.getText() + "\",\"name\":\"" + e.getName() + "\"},"; 
				}
			}
			
			if(StringUtils.isNotBlank(result))
				result.substring(0, result.length()-1);
		}
		result = "[" + result + "]";
		JsUtils.writeText(result);
	}
	
	//根据存货编码查询主、副计量单位以及转换率
	public void getRateJson() throws Exception{
		String result = "";
		String cinvcode = ServletActionContext.getRequest().getParameter("cinvcode");
		//cinvcode = "10100782";
		if(StringUtils.isNotBlank(cinvcode)){
			Object[] o = saleOrderU8Service.getRate(cinvcode);
			if(o.length == 3){
				result += "{\"comunit\":\"" + o[0] + "\",\"sacomunit\":\"" + (o[1] != null ? "" : o[1]) + "\",\"rate\":\"" + o[2] + "\"}"; 
			}
		}
		result = "[" + result + "]";
		JsUtils.writeText(result);
	}
	
	//根据客户编码查询客户信用额
	public void getXyeJson() throws Exception{
		String result = "";
		String customCde = ServletActionContext.getRequest().getParameter("customCde");
		if(StringUtils.isNotBlank(customCde)){
			result += "{\"xye\":\"" + saleOrderU8Service.getXye(customCde) + "\",\"xyye\":\"" + saleOrderU8Service.getXyye(customCde) + "\"}"; 
		}
		result = "[" + result + "]";
		JsUtils.writeText(result);
	}

	public SaleOrderU8VO getVo() {
		return vo;
	}

	public void setVo(SaleOrderU8VO vo) {
		this.vo = vo;
	}

	public SaleOrderU8DetailVO getDetailVO() {
		return detailVO;
	}

	public void setDetailVO(SaleOrderU8DetailVO detailVO) {
		this.detailVO = detailVO;
	}

	public Double getDdxye() {
		return ddxye;
	}

	public void setDdxye(Double ddxye) {
		this.ddxye = ddxye;
	}

	public Double getXye() {
		return xye;
	}

	public void setXye(Double xye) {
		this.xye = xye;
	}

	public Double getXyye() {
		return xyye;
	}

	public void setXyye(Double xyye) {
		this.xyye = xyye;
	}

}