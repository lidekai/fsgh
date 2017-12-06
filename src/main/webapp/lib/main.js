/* 
 *@名称: common.js
 *@功能: 基本的JS效果
 *@作者: Kish
 *@版本: v2.0
 *@时间: 2013-12-14
 */

$(function(){//开始

/*======================================================================================================================================*/ 
	
	//遍历所有需要实现全选的复选框
	$(".table .allchkBox").each(function(index){   //全选

		//捕获当前点击复选框的单击事件
		$(this).click(function() {   //勾选、取消后的行背景色

			var flag = this.checked; //判断当前复选框是否选中, true - 选中 , false - 取消选中

			//遍历当前选中复选框的table
			$($(".table").get(index)).find('.chkBox').each(function(chkindex) {   //勾选、取消后的行背景色
				
				this.checked = flag;

				if($(this).is(":checked")){
					$(this).parents("tr").addClass("tr-checked");
				}else{
					$(this).parents("tr").removeClass("tr-checked");
				}
			});
		});
	});
	
/*======================================================================================================================================*/ 	


//结束	
})

