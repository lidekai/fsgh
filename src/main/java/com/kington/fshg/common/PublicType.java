package com.kington.fshg.common;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;


/**
 * 统一管理系统枚举型对象或通用参数
 * 
 * @author lijialin
 * 
 */
public class PublicType {

	/**
	 * 系统代号
	 */
	public final static String SYSTEM_CODE = "fshg";

	/**
	 * 系统名称
	 */
	public final static String SYSTEM_NAME = "客户预算核销系统" ;

	/**
	 * 系统登录用户控制总数(预留)
	 */
	public final static Integer SYSTEM_USER_COUNT = 25;


	/**
	 * 通用配置类型(数据字典类型)
	 * 
	 * @author lijialin
	 * 
	 */
	public static enum DictType {
		ZPBZ("重泡货标准"),
		BXBL("保险金额比例"),
		GZ("工资"),
		CC("仓储"),
		HXLX("核销类型"),
		SKDKM("U8收款单科目"),
		SKDXYE("收款单信用额问题"),
		DDXYE("销售订单信用额问题")
		;
		private String text;
		public String getText() {
			return this.text;
		}
		private DictType(String str) {
			this.text = str;
		}
		public String getName() {
			return this.name();
		}
	}
	
	
	/**
	 * 状态类型
	 * 
	 * @author lijialin
	 * 
	 */
	public static enum StateType {
		USE("启用"), 
		STOP("禁用"),
		
		;
		private String text;
		public String getText() {
			return this.text;
		}
		public String getName() {
			return this.name();
		}
		private StateType(String str) {
			this.text = str;
		}
	}
	
	
	/**
	 * 审核类型
	 */
	public static enum AuditType {
		Y("合格"), 
		N("不合格"),
		;
		private String text;
		public String getText() {
			return this.text;
		}
		public String getName() {
			return this.name();
		}
		private AuditType(String str) {
			this.text = str;
		}
	}
	

	/**
	 * 菜单权限类型
	 * 
	 * @author lijialin
	 * 
	 */
	public static enum FuncType {
		MENU("菜单"), 
		BUTTON("按钮");
		private String text;

		public String getText() {
			return this.text;
		}

		public String getName() {
			return this.name();
		}

		private FuncType(String str) {
			this.text = str;
		}
	}

	/**
	 * 通过类型
	 * 
	 * @author lijialin
	 * 
	 */
	public static enum IsType {
		Y("是"), 
		N("否");
		private String text;

		public String getText() {
			return this.text;
		}

		public String getName() {
			return this.name();
		}

		private IsType(String str) {
			this.text = str;
		}
		
	}

	/**
	 * 操作类型
	 * 
	 * @author lijialin
	 * 
	 */
	public static enum ActType {
		ADD("添加"), 
		EDIT("编辑"), 
		VIEW("查看"), 
		SHOW("查看"), 
		AUDIT("审核"), 
		SINGLE("单选"), 
		MULTIPLE("多选"),
		APPLY("申请"),
		REG("注册"),
		CUSPRO("客户存货"),
		REAUDIT("反审核"),
		COUNT("统计"),
		AUTOHX("自动核销"),
		XZHX("选择核销");
		private String text;

		public String getText() {
			return this.text;
		}

		public String getName() {
			return this.name();
		}

		private ActType(String str) {
			this.text = str;
		}
	}
	public static enum NoticeType {
		NOTICE("通知公告"), 
		HELP("支持与帮助");

		private String text;

		public String getText() {
			return this.text;
		}

		public String getName() {
			return this.name();
		}

		private NoticeType(String str) {
			this.text = str;
		}
	}
	/**
	 *	客户状态 
	 */
	public static enum CustomerState{
		ZCHZ("正常合作"),
		ZZHZ("终止合作");
		private String text;
		public String getText(){
			return this.text;
		}
		public String getName(){
			return this.name();
		}
		private CustomerState(String str){
			this.text = str;
		}
	}
	/**
	 * 附件类型
	 * @author lijialin
	 *
	 */
	public static enum AttachType{
		CPXX("产品信息","2356987412"),
		GSZZ("公司资质","4554654644");
		private String text;
		private String key;//加密用KEY
		public String getText(){
			return this.text;
		}
		public String getKey(){
			return this.key;
		}
		public String getName(){
			return this.name();
		}
		private AttachType(String str,String str1){
			this.text = str;
			this.key = str1;
		}
	}

	/**
	 *	审批状态 
	 *
	 */
	public static enum ApproveState{
		DSP("待审批"),
		YSZ("一审中"),
		ESZ("二审中"),
		ZSZ("终审中"),
		SPJS("审批结束");
		private String text;
		public String getText(){
			return this.text;
		}
		public String getName(){
			return this.name();
		}
		private ApproveState(String str){
			this.text = str;
		}
	}
	
	/**
	 * 产品计价方式
	 * 
	 * @author lijialin
	 * 
	 */
	public static enum ChargeType {
		WEIGHT("重货"), 
		VOLUME("泡货"),
		COUNT("按件数计算");

		private String text;
		public String getText() {
			return this.text;
		}
		public String getName() {
			return this.name();
		}
		private ChargeType(String str) {
			this.text = str;
		}
	}
	
	
	public static Connection getConn() throws Exception{
        Connection conn = null;
        try {
        	ResourceBundle rb = ResourceBundle.getBundle("jdbc1");
            String url = rb.containsKey("jdbc.url") ? rb.getString("jdbc.url") : null;
    		String username = rb.containsKey("jdbc.username") ? rb.getString("jdbc.username") : null;
    		String password  = rb.containsKey("jdbc.password") ? rb.getString("jdbc.password") : null;
    		String driver = rb.containsKey("jdbc.driver") ? rb.getString("jdbc.driver") : null;
    		
    		Class.forName(driver);
            conn = DriverManager.getConnection(url,username,password);
            return conn;
         } catch (SQLException e) {
             System.out.println("操作错误");
             System.out.println("SQL STATE: " + e.getSQLState());
             System.out.println("ERROR CODE: " + e.getErrorCode());
             System.out.println("MESSAGE: " + e.getMessage());
             System.out.println();
             e = e.getNextException();
             e.printStackTrace();
         } catch (Exception e) {
             e.printStackTrace();
         }
        return null;
		
	}
	
	public static String getDataBaseName() throws Exception{
		ResourceBundle rb = ResourceBundle.getBundle("jdbc1");
        String url = rb.containsKey("jdbc.url") ? rb.getString("jdbc.url") : null;
        String a = url.split(";")[1];
        String b = a.split("=")[1];
        if(StringUtils.isNotBlank(url))
        	return b.substring(7,10);
        else 
        	return null;
	}
	
	/**
	 * 预提项目类型
	 *
	 */
	public static enum ProjectType{
		CXYGZL("促销员工资类"),
		CPMXL("产品明细类"),
		QTFYL("其他费用类");
		private String text;
		public String getText(){
			return this.text;
		}
		public String getName(){
			return this.name();
		}
		private ProjectType(String str){
			this.text = str;
		}
	}
	
	public static Float setFloatScale(Float a){
		if(a != null && !a.equals(0F)){
			BigDecimal b = new BigDecimal(Float.toString(a));
			return b.setScale(2, 4).floatValue();
		}else
			return 0F;
	}
	
	public static Double setDoubleScale(Double a){
		if(a != null && !a.equals(0d)){
			BigDecimal b = new BigDecimal(Double.toString(a));
			return b.setScale(2, 4).doubleValue();
		}else
			return 0d;
	}
	
	public static Double setDoubleScale4(Double a){
		if(a != null && !a.equals(0d)){
			BigDecimal b = new BigDecimal(Double.toString(a));
			return b.setScale(4, 4).doubleValue();
		}else
			return 0d;
	}
	
	public static Double setDoubleScale6(Double a){
		if(a != null && !a.equals(0d)){
			BigDecimal b = new BigDecimal(Double.toString(a));
			return b.setScale(6, 4).doubleValue();
		}else
			return 0d;
	}
	
	
	/**
	 *	核销方向 
	 */
	public enum VerDirection{
		HCYT("红冲预提"),
		LCYT("蓝冲预提");
		private String text;
		public String getText(){
			return this.text;
		}
		public String getName(){
			return this.name();
		}
		private VerDirection(String str){
			this.text = str;
		}
	}
	
	/**
	 * 核销类型
	 */
	public enum VerType{
		CYT("冲预提"),
		XJZF("现金支付"),
		CYS("冲应收"),
		HB("货补"),
		JLZ("加量装");
		private String text;
		public String getText(){
			return this.text;
		}
		public String getName(){
			return this.name();
		}
		private VerType(String str){
			this.text = str;
		}
	}
	
	/**
	 *	应收单状态
	 */
	public static enum ReceiveState{
		WCL("未处理"),
		DHX("待核销"),
		YHX("已核销"),
		YQX("已取消");
		private String text;
		public String getText(){
			return this.text;
		}
		public String getName(){
			return this.name();
		}
		private ReceiveState(String str){
			this.text = str;
		}
	}
}
