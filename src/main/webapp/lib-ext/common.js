/* 
 *@名称: common.js
 *@功能: 基本的JS效果
 *@作者: Kish
 *@版本: v3.0
 *@时间: 2013-12-14
 */

$(function(){//开始

	//注册&登录框效果
	$(".log-inp").focus(
		function(){
			$(this).addClass("log-inp-focus");
	}).blur(
		function(){
			$(this).removeClass("log-inp-focus");
	});
	
	//验证码
	$(".code-inp").focus(
		function(){
			$(this).addClass("code-inp-focus");
	}).blur(
		function(){
			$(this).removeClass("code-inp-focus");
	});
	
	$('.toggle').find('span').eq(0).click(function() {
		//alert('show first');
		//判断display属性
		var ishide = $('.sider-left').css('display');
		if(ishide == "block") {
			//当显示的时候，执行收缩功能
			$(".toggle").css({"border-color":"#3d96c9"});
		    $(".sider-toggle").addClass("sider-toggle-focus");
			$(".main-content").css({"margin-right":"-4px"});
			$(".main-content-in").css({"margin-right":"4px"});
			$(".toggle").find("span").addClass("fold").attr("title","展开");
			$(".sider-left").hide(500);
		} else {
			$(".toggle").css({"border-color":"#fff"});
		    $(".sider-toggle").removeClass("sider-toggle-focus");
			$(".main-content").css({"margin-right":"-190px"});
			$(".main-content-in").css({"margin-right":"190px"});
			$(".toggle").find("span").removeClass("fold").attr("title","收缩");
			$(".sider-left").show(500);
		}
	});
})

function initMenuEven(){
	/****adminleft***/
	 $("#menuleft .Menu-tit").click(function(){
		if($(this).attr("class").indexOf("Menu-tit-curr") > 0){
			$(this).removeClass("Menu-tit-curr");
			$("#menuleft .Menua").hide(300);
		}else{
 			$("#menuleft .Menu-tit").removeClass("Menu-tit-curr");
			$("#menuleft .Menua").hide(300);
			$(this).addClass("Menu-tit-curr");
			$(this).parent().next().slideToggle("slow");
		}
	});
	
	$("#menuleft .curr-a").click(function(){
		if($(this).attr("class").indexOf("a-curr") > 0){
			$(this).removeClass("a-curr");
			$("#menuleft .Menub").hide(300);
		}else{
			$("#menuleft .curr-a").removeClass("a-curr");
			$("#menuleft .Menub").hide(300);
			$(this).addClass("a-curr");
			$(this).next().slideToggle("slow");
		}
	});
	//$("#menuleft .Menu-tit").first().parent().next().slideToggle("slow");
}