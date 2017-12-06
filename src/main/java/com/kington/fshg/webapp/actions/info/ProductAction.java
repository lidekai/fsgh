package com.kington.fshg.webapp.actions.info;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.kington.fshg.common.Common;
import com.kington.fshg.common.ExcelUtil;
import com.kington.fshg.common.FileUtils4UNC;
import com.kington.fshg.common.JsUtils;
import com.kington.fshg.common.PublicType.ActType;
import com.kington.fshg.common.PublicType.ChargeType;
import com.kington.fshg.common.PublicType.DictType;
import com.kington.fshg.common.excel.ImportExcel;
import com.kington.fshg.common.excel.vo.ProductExcelVO;
import com.kington.fshg.model.info.Area;
import com.kington.fshg.model.info.CustomVO;
import com.kington.fshg.model.info.Product;
import com.kington.fshg.model.info.ProductVO;
import com.kington.fshg.model.system.Dict;
import com.kington.fshg.model.system.User;
import com.kington.fshg.service.info.ProductService;
import com.kington.fshg.service.info.ProductTypeService;
import com.kington.fshg.service.system.DictService;
import com.kington.fshg.webapp.actions.BaseAction;
import com.kington.fshg.webapp.security.UserContext;

public class ProductAction  extends BaseAction {
	private static final long serialVersionUID = -204752489022432238L;
	
	private ProductVO vo;
	private File xlsfileupload;// 导入excel文件
	
	@Resource
	private ProductService productService;
	@Resource
	private ProductTypeService productTypeService;
	@Resource
	private DictService dictService;
	
	private String header;
	
	private File upload; //上传的文件
    private String uploadFileName; //文件名称
    private String uploadContentType; //文件类型
	
	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String list() throws Exception {
		if (vo == null)				vo = new ProductVO();		
		vo.setPageNumber(page);
		if("".equals(this.getRequest().getParameter("vo.newProduct"))){
			vo.setNewProduct(null);
		}
		try {
			pageList = productService.getPageList(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setAttr("productTypeList", productTypeService.getChildList());
		return "list";
	}
	
	public String edit() throws Exception {
		try {
			if(vo != null && Common.checkLong(vo.getId())){
				vo = productService.getVOById(vo.getId());
				if(act == null)
					setAct(ActType.EDIT);
			} else {
				setAct(ActType.ADD);
			}
			if(vo == null)
				vo = new ProductVO();
			
			setAttr("productTypeList", productTypeService.getChildList());
		} catch (Exception e) {
			return doException(e);
		}
		return "edit";
	}
	
	public String update() throws Exception {
		try {
			//图片上传
			if(upload != null){
				if (uploadContentType.contains("jpeg") || uploadContentType.contains("jpg")
		                 || uploadContentType.contains("gif") || uploadContentType.contains("png")){
					if(vo != null && Common.checkLong(vo.getId())){
						String oldPath = productService.getById(vo.getId()).getPath();
						if(StringUtils.isNotBlank(oldPath)){
							FileUtils4UNC.delete(oldPath);
						}
					}
					
					File newFile = FileUtils4UNC.save(FileUtils4UNC.Dir.Photo, upload,  uploadFileName);
					vo.setPath( FileUtils4UNC.Dir.Photo.getHttpPath() + newFile.getName());
					
				}else{
					addActionError("上传图片的类型有误");
					return "edit";
				}
			}
			
			
			if(StringUtils.isNotBlank(vo.getStockCde())){
				Product product = productService.getByCde(vo.getStockCde());
				if(product != null && (!Common.checkLong(vo.getId()) || !vo.getId().equals(product.getId()))){
					this.addActionMessage("存货编码已存在，请重新填写");
					return edit();
				}
			}
			//货物每立方米大于或等于260公斤的按重货计算，货物每立方米小于260公斤的按泡货计算
			List<Dict> list = dictService.getByType(DictType.ZPBZ);
			Double bz = Common.checkList(list) ? list.get(0).getValue() : 260d;
			if(vo.getMeterWeight() != null && vo.getMeterWeight().compareTo(bz) >= 0)
				vo.setChargeType(ChargeType.WEIGHT);
			else
				vo.setChargeType(ChargeType.VOLUME);
			if(vo.getProductType() != null && Common.checkLong(vo.getProductType().getId()))
				vo.setProductType(productTypeService.getById(vo.getProductType().getId()));
			
			vo.setVolume(Common.multDouble(vo.getWidth(), vo.getLength(), vo.getHeight()));
			
			productService.saveOrUpdate(vo);
		} catch (Exception e) {
			return doException(e);
		}
		this.addActionMessage("操作成功");
		//vo = new ProductVO();
		return list();
	}
	
	/**
	 * 删除
	 */
	public String delete() {
		boolean success = false;
		try {
			int count = productService.clear(ids);

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
	 * 导入产品分类
	 */
	public String imp(){
		return "imp";
	}
	
	/**
	 * 执行导入
	 */
	public String doImp(){
		try{
			ImportExcel<ProductExcelVO> imp = new ImportExcel<ProductExcelVO>(ProductExcelVO.class);
			List<ProductExcelVO> result = null;
			result = (ArrayList<ProductExcelVO>) imp.importExcel(getXlsfileupload());
			
			if (result != null && result.size() > 0) {
				setAttr("info",productService.doImports(result));
			} else {
				setAttr("info", "没有可导入的信息");
			}
		}catch(Exception e){
			return this.doException(e);
		}
		
		return "imp";
	}
	
	
	/**
	 * 批量修改是否新品
	 */
	public String modifyNewProduct() throws Exception{
		int count = 0;
		String isType = ServletActionContext.getRequest().getParameter("isType");
		for(String str : ids.split(",")){
			try {
				if(productService.updateNewProduct(Long.parseLong(str), isType)){
					count++;
				}
			} catch (Exception e) {
				continue;
			}
		}
		this.addActionMessage("共更新"+ count + "条数据！");
		return list();
	}
	
	
	public String getProduct(){
		try {
			String customId = ServletActionContext.getRequest().getParameter("customId");
			setAttr("productList",productService.getListByCustomId(Long.parseLong(customId)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "getProduct";
	}
	
	/**
	 * 选择产品列表
	 * @return
	 */
	public String selectProduct(){
		try {
			if(vo == null)
				vo = new ProductVO();
			vo.setPageNumber(page);
			
			pageList = productService.getPageList(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "selectProduct";
	}
	
	
	/**
	 * 导出产品信息表
	 * @return
	 * @throws Exception 
	 */
	public String exportProduct() throws Exception{
		setAttr("productHeader", Common.getExportHeader("PRODUCT"));		
		if("".equals(this.getRequest().getParameter("vo.newProduct"))){
			vo.setNewProduct(null);
		}
		if(StringUtils.isNotBlank(vo.getProductName())){
			String keyword=new String(vo.getProductName().getBytes("ISO8859_1"),"UTF8");
			vo.setProductName(keyword);
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
			vo=new ProductVO();
		}
		if("".equals(this.getRequest().getParameter("vo.newProduct"))){
			vo.setNewProduct(null);
		}
		vo.setObjectsPerPage(Integer.MAX_VALUE);
		List<Product> list=productService.getPageList(vo).getList();
		List<Integer> intArr = new ArrayList<Integer>();
		String[] heads = StringUtils.split(header, ",");
		for(int i =0; i< heads.length; i++){
			intArr.add(25);
		}
		
		List<Map<String,String>> listmap = new ArrayList<Map<String,String>>();
		Map<String ,String> map = null;
		for(Product product : list){
			map=new HashMap<String, String>();
			productService.exportProduct(heads,product,map);
			listmap.add(map);
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		String title = "产品信息表";
		Integer[] columnSize = intArr.toArray(new Integer[]{});
		ExcelUtil.export(response, listmap, "产品信息表.xls", title, heads, columnSize);
		
		return null;
	}
	
	public void getProductJson() throws Exception{
		String result = "";
		String number = ServletActionContext.getRequest().getParameter("number");
		if(StringUtils.isNotBlank(number)){
			List<Product> productList = productService.getListByNumber(number);
			if(Common.checkList(productList)){
				for(Product p : productList){
					result += "{\"id\":\"" + p.getId() + "\",\"name\":\"" + p.getProductName() + "(" + p.getStandard() + ")\"},"; 
				}
			}
			
			if(StringUtils.isNotBlank(result))
				result.substring(0, result.length()-1);
		}
		result = "[" + result + "]";
		JsUtils.writeText(result);
	}
	
	public void getProductInfoJson() throws Exception{
		String result = "";
		String productId = ServletActionContext.getRequest().getParameter("productId");
		if(StringUtils.isNotBlank(productId)){
			Product product = productService.getById(Long.parseLong(productId));
			if(product != null){
				result += "{\"stockCde\":\"" + product.getStockCde() + "\",\"standard\":\"" + product.getStandard()+ "\",\"quote\":\"" + product.getQuote()+ "\"}"; 
			}
		}
		result = "[" + result + "]";
		JsUtils.writeText(result);
	}
	
	public String productImg() throws Exception{
		setAttr("imgMap", productService.productImgMap());
		return "img";
	}
	
	public String viewProduct() throws Exception{
		if(id != null)
			setAttr("product", productService.getById(id));
		return "view";
	}
	
	
	public ProductVO getVo() {
		return vo;
	}

	public void setVo(ProductVO vo) {
		this.vo = vo;
	}

	public File getXlsfileupload() {
		return xlsfileupload;
	}

	public void setXlsfileupload(File xlsfileupload) {
		this.xlsfileupload = xlsfileupload;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
}
