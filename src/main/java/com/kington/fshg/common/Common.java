package com.kington.fshg.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.AntPathMatcher;

import com.jtframework.websupport.utils.EncryptUtil;
import com.kington.fshg.common.PublicType.AttachType;

/**
 * 统一管理一些常用的方法或对象
 * 
 * @author lijialin
 * 
 */
public class Common {

	/**
	 * 系统通用访问路径，此路径的请求不需要权限验证
	 */
	public static List<String> PUBLIC_PATH;

	/**
	 * 所有企业用户有权限访问的链接
	 */
	public static List<String> COMP_PATH;
	
	/**
	 * 所有后台管理员有权限访问的链接
	 */
	public static List<String> MANAGER_PATH;
	
	/**
	 * 高级权限列表，匹配的链接需要校验ID和KEY是否一致
	 * 先校验id,再校验ids
	 */
	public static List<String> TOP_PATH;
	
	public static String DEF_PWD;
	public static String DEF_YXNS;//证书有效年限
	public static String SYSTEM_TYPE;//#系统类型
	public static String EXPORT_PARA="EXPORT_PARA";//导出时使用的查询字段SESSIONKEY
	static {
		// 从配置文件读取已配置的通用路径
		ResourceBundle rb = ResourceBundle.getBundle("application");
		DEF_PWD = rb.getString("DEF_PWD");
		DEF_YXNS = rb.getString("DEF_YXNS");
		SYSTEM_TYPE = rb.getString("SYSTEM_TYPE");
		System.out.println("system_type:"+SYSTEM_TYPE);
		String TOP_PATHS = rb.getString("TOP_PATH");
		String[] s = StringUtils.split(TOP_PATHS, Common.SYMBOL_COMMA);
		TOP_PATH = new ArrayList<String>();
		for(String ss : s){
			TOP_PATH.add(ss);
		}
	}
	
	/**
	 * IP链接提交限制配置
	 */
	public static Map<String,Integer> IP_URL_MAP = null;	
	/**
	 * 系统在线用户
	 */
	public static Integer CUR_USER_NUM = 0;

	/**
	 * 空格
	 */
	public final static String SYMBOL_SPACES = " ";

	/**
	 * 逗号 ,
	 */
	public final static String SYMBOL_COMMA = ",";

	/**
	 * 百分号 %
	 */
	public final static String SYMBOL_PERCENT = "%";
	
	/**
	 * 等号 ,
	 */
	public final static String SYMBOL_EQUATE = "=";
	
	/**
	 * 连接符 &
	 */
	public final static String SYMBOL_A = "&";

	/**
	 * 网页基本路径 /
	 */
	public final static String BASE_PATH = "/";

	/**
	 * 返回界面 list
	 */
	public final static String PATH_LIST = "list";

	/**
	 * 返回界面 edit
	 */
	public final static String PATH_EDIT = "edit";
	/**
	 * 返回界面 audit
	 */
	public final static String PATH_AUDIT = "audit";

	/**
	 * 返回界面 single
	 */
	public final static String PATH_SINGLE = "single";

	/**
	 * 返回界面 multiple
	 */
	public final static String PATH_MULTIPLE = "multiple";

	/**
	 * 提示信息：操作成功
	 */
	public final static String OPER_SUCCESS = "操作成功";

	/**
	 * 提示信息：操作失败
	 */
	public final static String OPER_FAIL = "操作失败";
	
	/**
	 * 注册页面不设权限
	 */
	public final static String PATH_REGESITER = "/zsgl/company-temporary/";

	/**
	 * 路径匹配对象
	 */
	public static AntPathMatcher matcher = new AntPathMatcher();
	
	public static void main(String[] args)throws Exception{
		//String s = "/system/user/list.jhtml";
		//String parent = "/**/*.jhtml";
		//System.out.println("result:"+matcher.match(parent,s));
		//System.out.println(getPYIndexStr("Ic",true));
	}

	/**
	 * MD5加密密钥
	 */
	private final static String MD5_KEY = "_2SP5_4JG2_3dIEk.e";

	/**
	 * 获取加上密钥并加密后的串
	 * 
	 * @param text
	 * @return
	 */
	public static final String getMD5(String text) {
		return EncryptUtil.MD5(MD5_KEY + text);
	}
	

	private final static String IDMD5_KEY = "125";
	private final static String IDMD5_KEY2 = "895";

	public static final String getIdMD5(String text, String table) {
		return EncryptUtil.MD5(MD5_KEY + text + IDMD5_KEY + table.toUpperCase() + IDMD5_KEY2);
	}
	
	private final static String ATTACH_IDMD5_KEY = "2158EkliuSQa";
	/**
	 * 附件下载所需求的KEY
	 * @param text
	 * @param type
	 * @return
	 */
	public static final String getAttachMD5(String text,AttachType type) {
		if(StringUtils.isBlank(text) || type == null) return null;
		return EncryptUtil.MD5(ATTACH_IDMD5_KEY + text + type.getKey() );
	}

	/**
	 * 校验IDS的有效性，全部通过，号分隔，并能转换成Long
	 * 
	 * @param ids
	 * @return
	 */
	public static final boolean checkIds(String ids) {
		if (StringUtils.isBlank(ids))
			return false;

		String[] tmp = StringUtils.split(ids, SYMBOL_COMMA);
		Long l = null;
		try {
			for (String s : tmp) {
				l = new Long(s);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 校验LONG对象是否大于0
	 * 
	 * @param i
	 * @return:true Long是大于0的值
	 */
	public static final boolean checkLong(Long l) {
		if (l == null || l <= 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 随机生成 a 到 b (包含b)的整数:
	 * 
	 * @return
	 */
	public static int makeRandom(int a, int b) {
		return (int) (Math.random() * (b - a + 1)) + a;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestPara(HttpServletRequest request){
		Map requestParams = request.getParameterMap();
		
		String valueStr = StringUtils.EMPTY;
		String name;
		String[] values;
		String rt = StringUtils.EMPTY;
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			name = (String) iter.next();
			values = (String[]) requestParams.get(name);
			valueStr = StringUtils.EMPTY;
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			rt += name + Common.SYMBOL_EQUATE + valueStr + Common.SYMBOL_A;
		}
		if(rt.length() > 1500)	rt = rt.substring(0,1500);
		if(rt.endsWith("&"))	rt = rt.substring(0,rt.length() -1);
		return rt;
	}
	
	/**
	 * 获取真实的IP地址
	 * @param request
	 * @return
	 */
	public final static String getRealIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 手机号验证
	 * 
	 * @param str
	 * 
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) {
		if(!StringUtils.isNotBlank(str)) return false;
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}
	
	/**
	 * 获取导出表头信息
	 * @param type
	 * @return
	 */
	public static String[] getExportHeader(String type){
		if(type.equals("LOGDETAIL")){
			//物流明细
			return new String[]{"客户地区","客户编码","客户名称","累计无税金额","累计物流费合计","费用比","当月本币金额","当月运费","当月配送费","当月保险金额","当月运费合计","当月退货费","当月促销道具费","当月仓储费","当月工资","当月调拨费","当月运费总计"};
		}else if(type.equals("LOGANALYSIS")){
			//物流分析
			return new String[]{"所属大区","当年费用比","去年费用比","累计本币金额","累计物流费用","累计费用比","当月本币金额","当月物流费用","当月累计费用比"};
		}else if(type.equals("ANALYSISBUD")){
			//预提明细
			return new String[]{"所属大区","所属地区","客户名称","客户编码","年月","编号(合同外)","项目","总费用","开始时间","结束时间","实际费用","编号(合同内)","进场费","固定费用","年返金","月返金","网络信息费","配送服务费","海报费","促销陈列费","赞助费","损耗费","固定折扣","堆头费","市场费","现款现货返利","其他费用","费用总和"};
		}else if(type.equals("ANALYSISCHAR")){
			//预提核销明细
			return new String[]{"所属大区","所属地区","客户名称","客户编码","年月","类型","编号(合同外)","项目","费用","编号(合同内)","进场费","固定费用","年返金","月返金","网络信息费","配送服务费","海报费","促销陈列费","赞助费","损耗费","固定折扣","堆头费","市场费","现款现货返利","其他费用","费用总和"};
		}else if(type.equals("ANALYSISSUM")){
			//客户费用分析表
			return new String[]{"所属大区","所属地区","客户名称","客户编码","总预提","总核销","总余额","发票额","投入产出比","合同内预提","合同内核销","合同内余额","合同外预提","合同外实际预提","合同外核销","合同外余额"};
		}else if(type.equals("PRODUCTBUDGET")){
			//产品费用预提明细表
			return new String[]{"所属大区","所属地区","客户名称","门店名称","预提日期","预提编码","费用名称","产品名称","存货编码","产品规格","预提费用"};
		}else if(type.equals("PRODUCTCHARGE")){
			//产品费用预提核销明细表
			return new String[]{"所属大区","所属地区","客户名称","门店名称","日期","类型","编号","费用名称","产品名称","存货编码","产品规格","费用"};
		}else if(type.equals("PRODUCTSUM")){
			//产品费用预提核销余额表
			return new String[]{"所属大区","所属地区","客户名称","门店名称","产品名称","存货编码","产品规格","预提费用","核销费用","余额"};
		}else if(type.equals("SALEORDER")){
			//销售订单表
			return new String[]{"销售订单号","订单日期","客户名称","存货名称","数量","本币含税单价","本币价税合计","客户编码","存货编码","存货代码","存货规格"};
		}else if(type.equals("DELIVERORDER")){
			//发货订单表
			return new String[]{"发货单号","订单日期","发货日期","客户名称","存货名称","实收数","本币含税单价","本币价税合计","是否返利","销售订单号","客户编码","存货编码","存货代码","存货规格","数量","件数","销售类型","报价","扣率","二次扣率","票折","费用折"};
		}else if(type.equals("TRANSFER")){
			//仓库调拨单
			return new String[]{"调拨单号","调拨日期","调出仓库名称","调入仓库名称","存货名称","单价","金额","调入仓库编码","调出仓库编码","存货编码","存货代码","存货规格","件数","运费","配送费","运费合计"};
		}else if(type.equals("INFEEPROVISION")){
			//合同内预提
			return new String[]{"预提编码","所属大区","所属地区","所属分类","所属客户","客户编码","预提日期","费用总和(元)","审核状态","进场费","固定费用","年返金","月返金","网络信息费","配送服务费","海报费","促销陈列费","赞助费","损耗费","固定折扣","堆头费","市场费","现款现货返利","其他费用"};
		}else if(type.equals("SALEBILL")){
			//销售发票
			return new String[]{"销售发票号","制单时间","订单日期","发货日期","销售订单号","客户名称","存货名称","数量","本币含税单价","本币价税合计","本币无税金额"};
		}else if(type.equals("PRODUCT")){
			//产品信息表
			return new String[]{"存货编码","存货代码","货号","产品名称","产品类型","产品规格","每箱毛重（公斤）","每箱体积（立方米)","每箱立方米重量（公斤）","计费形式","是否新品","纸箱长(厘米)","纸箱宽(厘米)","纸箱高(厘米)","标准价","报价","启用时间","单位","条形码","备注"};
		}else if(type.equals("CUSTOM")){
			//客户信息表
			return new String[]{"客户编码","客户名称","所属大区","所属地区","所属业务员","客户类型","省份","联系人","客户状态","联系电话","地址","铺底额","账期(天)","合作起始日期","合作终止日期","泡货单价","重货单价","按件单价","配送费","地区经理","大区经理","备注"};
		}else if(type.equals("CUSTOMPRODUCT")){
			//客户存货表
			return new String[] {"客户编码","客户名称","产品信息 ( 产品存货编码 | 产品名称 | 产品规格 | 产品所属分类)"};
		}else if(type.equals("PROMOTERS")){
			//促销员信息表
			return new String[] {"促销员名称","身份证号","联系电话","所在门店","所属客户","银行卡号","开户地","备注"};
		}else if(type.equals("STOREPRODUCTSTOCK")){
			//门店库存信息表
			return new String[]{"客户名称","门店名称","存货编码","产品货号","产品名称","规格","单位","标准价","数量","金额","年月"};
		}else if(type.equals("STOREPRODUCTSALE")){
			//门店销售信息表
			return new String[]{"客户名称","门店名称","存货编码","产品货号","产品名称","规格","单位","标准价","销量","标准价销售额","终端零售价销售额","年月"};
		}else if(type.equals("LOGISTICS")){
			//物流费用
			return new String[]{"发货单号","发货日期","客户编码","客户名称","存货代码","存货编码","存货名称","规格型号","数量/包数","货物单位重量","货物重量","货物单位体积（立方米）","货物体积（立方米）","计价方式","单价","运费","配送费","本币金额","保险金额","业务员","运费合计"};
		}else if(type.equals("OTHERLOGISTICS")){
			//其他物流费用
			return new String[]{"客户名称","促销道具","退货","销售额","工资分摊","仓储分摊","调拨费分摊","运费合计","提交日期"};
		}else if(type.equals("STORE")){
			//门店信息表
			return new String[] {"门店名称","门店编码","联系人","所在城市","提成比例","所属客户","联系电话","地址","备注"};
		}else if(type.equals("STOREPRODUCT")){
			//门店存货表
			return new String[]{"客户名称","门店名称","门店编码","存货编码","产品货号","产品名称","标准价","直销KA价","终端零售价"};
		}else if(type.equals("AREA")){
			//地区信息
			return new String[] {"所属大区","地区名称"};
		}else if(type.equals("PRODUCTTYPE")){
			//产品分类
			return new String[]{"所属分类","分类名称"};
		}else if(type.equals("CUSTOMSTYPE")){
			//客户分类
			return new String[]{"所属分类","分类名称"};
		}else if(type.equals("INFEECLAUSE")){
			//合同内条款
			return new String[]{"年度","所属客户","固定费用","固定费用分摊开始时间","固定费用分摊结束时间","网络信息费","网络信息费分摊开始时间","网络信息费分摊结束时间","配送服务费(百分比)","年返金(百分比)","月返金(百分比)",
					"进场费","进场费分摊开始时间","进场费分摊结束时间","海报费","海报费分摊开始时间","海报费分摊结束时间","促销陈列费","促销陈列费分摊开始时间","促销陈列费分摊结束时间","赞助费(比例)","固定折扣(比例)","堆头费","堆头费分摊开始时间","堆头费用分摊结束时间","市场费(比例)","现款现货返利(比例)",
					"其他费用","其他费用分摊开始时间","其他费用分摊结束时间","损耗费","开始日期","结束日期","备注"};
		}else if(type.equals("PROVISIONPROJECT")){
			//合同外预提项目
			return new String[] {"费用名称","项目类型"};
		}else if(type.equals("OUTFEEPROVISION"))	{
			//合同外预提
			return new String[]{"预提编码","所属客户","申请业务员","项目名称","所属项目类型","总费用(元)","制单时间","预提所属开始时间","预提所属结束时间","审核状态"};
		}else if(type.equals("INFEEVERIFICATION")){
			//合同内费用核销
			return new String[] {"核销编号","所属预提","所属客户","费用总和(元)","核销方向","核销类型","核销时间","审批状态","是否已生成收款单","进场费","固定费用","年返金","月返金","网络信息费","配送服务费","海报费","促销陈列费","赞助费","损耗费","固定折扣","堆头费","市场费","现款现货返利","其他费用"};
		}else if(type.equals("OUTFEEVERIFICATION")){
			//合同外费用核销
			return new String[] {"核销编码","所属预提","申请业务员","项目类型","项目名称","所属客户","核销类型","核销方向","总费用(元)","核销时间","所属开始区间","所属结束区间","审核状态","是否已生成收款单","备注"};
		}else if(type.equals("STORESALEPRO")){
			//门店销售预提
			return new String[] {"所属客户","门店编码","门店名称","提成比例","销售额","固定金额","预提日期","预提费用"};
		}else if(type.equals("ANALYSISDIFFER")){
			//预提核销余额表
			return new String[] {"所属大区","所属地区","客户名称","客户编码","预提编号(合同外)","制单时间(合同外)","总预提(合同外)","实际预提(合同外)","开始时间(合同外)","结束时间(合同外)","核销(合同外)","余额(合同外)","预提编号(合同内)","预提时间(合同内)","预提(合同内)","核销(合同内)","余额(合同内)"};
		}else if(type.equals("RECEIVEBILL")){
			//应收单
			return new String[]{"销售发票号","凭证号","发票时间","到期日期","客户类型","客户地区","客户编码","客户名称","本币发票额","本币实际发票额","已收款合计","实际收款额","待费用发票额","待退货额","暂扣额","其他余额","状态"};
		}else if(type.equals("RECEIPTBILL")){
			//收款单
			return new String[]{"类别","收款单号","收款日期","客户编号","客户名称","结算编号","结算名称","本币收款金额","本币实际收款金额"};
		}else if(type.equals("CHECKBILL")){
			//待核单
			return new String[]{"单号","制单日期","客户编号","客户名称","本币发票额","已收款合计","实际收款额","待费用发票额","待退货额","暂扣额","其他余额","状态"};
		}else if(type.equals("STATBILL")){
			//待核单余额表
			return new String[]{"客户编号","客户名称","本币发票额","已收款合计","实际收款额","待费用发票额","待退货额","暂扣额","其他余额"};
		}else if(type.equals("STATACCOUNT")){
			//账期统计表
			return new String[]{"客户编号","客户名称","总余额","1-30天","31-60天","61-90天","91-120天","121-150天","151-180天","半年至1年","1年至2年","2年至3年","3年以上"};
		}else if(type.equals("COMPARE")){
			//门店同期销售对比表
			return new String[]{"所属大区","所属地区","客户编码","客户名称","门店编码","门店名称","产品编码","产品名称","前期销售数量","后期销售数量","销售数量对比","前期销售额","后期销售额","销售额对比"};
		}else if(type.equals("PROPORTION")){
			//门店销售占比表
			return new String[]{"所属大区","所属地区","客户编码","客户名称","门店编码","门店名称","产品编码","产品名称","产品销售数量","门店销售数量","销售数量占比","产品销售额","门店销售额","销售额占比"};
		}
		
		return null;
	}
	
	/**
	* 返回首字母
	* @param strChinese
	* @param bUpCase
	* @return
	*/
   public static String getPYIndexStr(String strChinese, boolean bUpCase){
       try{
           StringBuffer buffer = new StringBuffer();
           byte b[] = strChinese.getBytes("GBK");//把中文转化成byte数组
           for(int i = 0; i < b.length; i++){

               if((b[i] & 255) > 128){
                   int char1 = b[i++] & 255;
                   char1 <<= 8;//左移运算符用“<<”表示，是将运算符左边的对象，向左移动运算符右边指定的位数，并且在低位补零。其实，向左移n位，就相当于乘上2的n次方
                   int chart = char1 + (b[i] & 255);
                   buffer.append(getPYIndexChar((char)chart, bUpCase));
                   continue;
               }
               char c = (char)b[i];
               if(!Character.isJavaIdentifierPart(c))//确定指定字符是否可以是 Java 标识符中首字符以外的部分。
                   c = 'A';
               buffer.append(c);
           }
           return buffer.toString();

       }catch(Exception e){
           System.out.println((new StringBuilder()).append("\u53D6\u4E2D\u6587\u62FC\u97F3\u6709\u9519").append(e.getMessage()).toString());
       }

       return null;
   }

   /**
    * 得到首字母
    * @param strChinese
    * @param bUpCase
    * @return
    */
   private static char getPYIndexChar(char strChinese, boolean bUpCase){
       int charGBK = strChinese;
       char result;
       if(charGBK >= 45217 && charGBK <= 45252)
           result = 'A';
       else
       if(charGBK >= 45253 && charGBK <= 45760)
           result = 'B';
       else
       if(charGBK >= 45761 && charGBK <= 46317)
           result = 'C';
       else
       if(charGBK >= 46318 && charGBK <= 46825)
           result = 'D';
       else
       if(charGBK >= 46826 && charGBK <= 47009)
           result = 'E';
       else
       if(charGBK >= 47010 && charGBK <= 47296)
           result = 'F';
       else
       if(charGBK >= 47297 && charGBK <= 47613)
           result = 'G';
       else
       if(charGBK >= 47614 && charGBK <= 48118)
           result = 'H';
       else
       if(charGBK >= 48119 && charGBK <= 49061)
           result = 'J';
       else
       if(charGBK >= 49062 && charGBK <= 49323)
           result = 'K';
       else
       if(charGBK >= 49324 && charGBK <= 49895)
           result = 'L';
       else
       if(charGBK >= 49896 && charGBK <= 50370)
           result = 'M';
       else
       if(charGBK >= 50371 && charGBK <= 50613)
           result = 'N';
       else
       if(charGBK >= 50614 && charGBK <= 50621)
           result = 'O';
       else
       if(charGBK >= 50622 && charGBK <= 50905)
           result = 'P';
       else
       if(charGBK >= 50906 && charGBK <= 51386)
           result = 'Q';
       else
       if(charGBK >= 51387 && charGBK <= 51445)
           result = 'R';
       else
       if(charGBK >= 51446 && charGBK <= 52217)
           result = 'S';
       else
       if(charGBK >= 52218 && charGBK <= 52697)
           result = 'T';
       else
       if(charGBK >= 52698 && charGBK <= 52979)
           result = 'W';
       else
       if(charGBK >= 52980 && charGBK <= 53688)
           result = 'X';
       else
       if(charGBK >= 53689 && charGBK <= 54480)
           result = 'Y';
       else
       if(charGBK >= 54481 && charGBK <= 55289)
           result = 'Z';
       else
           result = (char)(65 + (new Random()).nextInt(25));
       if(!bUpCase)
           result = Character.toLowerCase(result);
       return result;
   }
   
   /**
	 * 校验list对象是否为空
	 * 
	 * @param i
	 * @return:true 不为空
	 */
   public static boolean checkList(List<?> list){
	   if(list == null || list.size() < 1)
		   return false;
	   else
		   return true;
				   
   }
   
   /**
  	 * 长宽高计算体积（注意单位换算）
  	 */
   public static Double multDouble(Double a , Double a1, Double a2){
	   BigDecimal b = new BigDecimal(Double.toString(a));
       BigDecimal b1 = new BigDecimal(Double.toString(a1));
       BigDecimal b2 = new BigDecimal(Double.toString(a2));
       BigDecimal b3 = new BigDecimal(1000000);
       
	   return  b.multiply(b1).multiply(b2).divide(b3).setScale(4,4).doubleValue();
   }
   
}
