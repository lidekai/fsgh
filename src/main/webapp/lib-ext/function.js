/* 
 *@名称: function.js
 *@功能: 函数方法
 *@作者: Kish
 *@版本: v2.0
 *@时间: 2013-12-14
 */
 
//后台首页中间自适应高度
function midAutoH(){
	var middleContent=document.getElementById("middle-content"),content=document.getElementById("content");
	middleContent.style.height = (document.body.clientHeight-118) +"px";  // 获取中间高度
	content.style.height = (document.body.clientHeight-118) +"px";
}

//********
$(document).ready(function () {
	navImgShowHide();
	$(window).resize(function() {
	navImgShowHide();
	});
});

function navImgShowHide(){
	var _width=$(window).width();	
	var _imgWidth=0;
	_imgWidth=$(".top-menu img").size()*195;
	if(_imgWidth<_width)
		$(".top-nav").hide();
	else{
		 $(".top-nav").show();
		}
}