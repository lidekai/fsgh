<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/common/meta.jsp"%>
<title>销售订单管理</title>
<script type="text/javascript">
function myReturnValue(){
	
	if(!Validator.Validate(form1,3,3)){
		return;				
	}
	var obj = {};
	
	obj.id = $("#id").val();
	obj.key = $("#key").val();
	obj.hh = $("#hh").val();
	obj.chbm = $("#chbm").val();
	obj.chmc = $("#productId option:selected").text();
	obj.xjpfl = $("#xjpfl").val();
	obj.khsph = $("#khsph").val();
	obj.dfm = $("#dfm").val();
	obj.ggxh = $("#ggxh").val();
	obj.zjl = $("#zjl").val();
	obj.js = $("#js").val();
	obj.sl = $("#sl").val();
	obj.bj = $("#bj").val();
	obj.zqwshj = $("#zqwshj").val();
	obj.hsdj = $("#hsdj").val();
	obj.shl = $("#shl").val();
	obj.wsdj = $("#wsdj").val();
	obj.jshj = $("#jshj").val();
	obj.wsje = $("#wsje").val();
	obj.zke = $("#zke").val();
	obj.kl = $("#kl").val();
	obj.kl2 = $("#kl2").val();
	obj.chyt = $("#chyt").val();
	obj.scrq = $("#scrq").val();
	obj.bz = $("#bz").val();
	obj.productId = $("#productId option:selected").val();
	obj.zhl = $("#zhl").val();
	
	return obj;
}

//根据货号查询产品列表
function getProductList(){
	var hh = $("#hh").val();
	if(hh != "" || hh != null){
		$.ajax({
			type:"post",
			url:"<c:url value='/info/product/getProductJson.jhtml'/>",
			data:{"number":hh},
			dataType:"text",
			success:function(data){
				var date = eval(data);
				var result = "<option value=\"\">请选择</option>";;
				for(var i=0;i<date.length;i++){
					 result += "<option value='" + date[i].id + "'>" + date[i].name + "</option>";
				}
				$("#productId").html(result);  
			}
		});
	}
}

//根据产品id查询存货编码等信息
function getProductInfo(){
	var productId = $("#productId").val();
	var productName = $("#productId").find("option:selected").text();
	$("#chmc").val(productName);
	if(productId != "" || productId != null){
		$.ajax({
			type:"post",
			url:"<c:url value='/info/product/getProductInfoJson.jhtml'/>",
			data:{"productId":productId},
			dataType:"text",
			success:function(data){
				var date = eval(data);
				$("#chbm").val(date[0].stockCde);
				$("#ggxh").val(date[0].standard);
				if(date[0].quote != "null")
					$("#bj").val(date[0].quote);
				else
					$("#bj").val("");
				getRate();
				klChange();
			}
		});
	}
}

//根据存货编码查询主计量与辅计量单位转换关系
function getRate(){
	var chbm = $("#chbm").val();
	if(chbm != "" || chbm != null){
		$.ajax({
			type:"post",
			url:"<c:url value='/sale/sale-order-u8/getRateJson.jhtml'/>",
			data:{"cinvcode":chbm},
			dataType:"text",
			success:function(data){
				var date = eval(data);
				if(date[0] != null){
					$("#zjl").val(date[0].comunit);
					if(date[0].comunit != "null")
						$("#sl_span").html(date[0].comunit);
					if(date[0].sacomunit == "null")
						$("#js_span").html(date[0].sacomunit);
					if(date[0].rate != "null")
						$("#zhl").val(date[0].rate);
					else
						$("#zhl").val("1");
				}
			}
		});
	}
}

//辅计量单位改变，主计量单位对应改变
function jsChange(){
	var zhl = $("#zhl").val();
	var js = $("#js").val();
	if(zhl != null && zhl != '' && js != null && js != '' && zhl != 0.0){
		$("#sl").val((js*zhl).toFixed(4));
		count();
	}
}

//主计量单位改变，辅量单位对应改变
function slChange(){
	var zhl = $("#zhl").val();
	var sl = $("#sl").val();
	if(zhl != null && zhl != '' && sl != null && sl != '' && zhl != 0.0 ){
		$("#js").val((sl/zhl).toFixed(2));
	}else
		$("#js").val("");
	count();
	countZqwshj();
}

//根据数量和含税单价，计算无税单价，价税合计，无税金额
function count(){
	var sl = $("#sl").val();
	var hsdj = $("#hsdj").val();
	
	var jshj,wsdj,wsje = "";
	
	if(sl != null && sl != '' && hsdj != null && hsdj != ''){
		jshj = (sl*hsdj).toFixed(2);//数量*含税单价
		
		var tax = $("#shl").val();
		if(tax == null || tax == ''){
			tax = 17;
			$("#shl").val(tax);
		}
		wsdj = (jshj/(sl*(1 + tax*0.01))).toFixed(6);//价税合计/（数量*（1+税率））
		wsje = (wsdj*sl).toFixed(2);//无税单价*数量
		klChange();
	}
	
	$("#jshj").val(jshj);
	$("#wsdj").val(wsdj);
	$("#wsje").val(wsje);
}

//计算折前无税合计=报价*数量*扣率/(100*1.17)
function countZqwshj(){
	var bj = $("#bj").val();
	var sl = $("#sl").val();
	var kl = $("#kl").val();
	if(bj != "" && sl != "" && kl != ""){
		$("#zqwshj").val((bj*sl*kl/(100*1.17)).toFixed(6));
	}else
		$("#zqwshj").val("");
}

//折扣额=含税单价*数量*扣率2/(100-扣率)*0.01(旧)
//折扣额=报价*数量*（1-扣率1*0.01*扣率2*0.01）
function countZke(){
	var bj = $("#bj").val();
	var sl = $("#sl").val();
	var kl = $("#kl").val();
	var kl2 = $("#kl2").val();
	var hsdj=$("#hsdj").val();
	if(hsdj != "" && sl != "" && kl != "" && kl2 != "" /* && kl != 100 */){
		//$("#zke").val((bj*sl*(1-kl*0.01*kl2*0.01)).toFixed(6));
		  $("#zke").val((hsdj*sl*(1-kl*0.01*kl2*0.01)).toFixed(6));
	}else
		$("#zke").val("0.000");
}

function klChange(){
	countZqwshj();
	countZke();
}
</script>
<style type="text/css">
.myWidth{
	float: left;
	width: 90px;
	padding-top: 5px;
	text-align: left
}
.myMargin{
margin-left: 90px;
}
.myInput{
	width: 130px;
}
.control-group{
	margin-bottom: 5px;
}
</style>
</head>
<body style="min-width: 900px;">
<form name="form1" action="update.jhtml" method="post">
<input type="hidden" id="id" value="${detailVO.id}"/>
<input type="hidden" id="key" value="${detailVO.key}"/>
	<div class="jt-container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
						<div class="row-fluid">
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">货号</label>
									<div class="myMargin">
										<input name="" value="${detailVO.hh}" type="text" class="myInput" id="hh" dataType="Require" msg="请输入" onchange="getProductList()"/>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">存货名称</label>
									<div class="myMargin">
										<select id="productId" dataType="Require" msg="请选择" onchange="getProductInfo()" style="width: 130px;">
											<option value="">请选择</option>
											<c:forEach items="${productList}" var="product">
												<option value="${product.id}" ${detailVO.productId == product.id ? 'selected' : '' }>${product.productName}(${product.standard })</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">存货编码<span>*</span></label>
									<div class="myMargin">
										<input name="" value="${detailVO.CInvCode}" type="text" class="myInput" id="chbm" readonly="readonly" dataType="Require" msg="不可为空" />
									</div>
								</div>
							</div>
						</div>
						
						<div class="row-fluid">
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">新旧品分类</label>
									<div class="myMargin">
										<input name="" value="${detailVO.xjpfl}" type="text" class="myInput" id="xjpfl"/>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">客户商品号</label>
									<div class="myMargin">
										<input name="" value="${detailVO.khsph}" type="text" class="myInput" id="khsph" dataType="Double" msg="格式错误"/>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">对方名</label>
									<div class="myMargin">
										<input name="" value="${detailVO.dfm}" type="text" class="myInput" id="dfm"/>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row-fluid">
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">规格型号</label>
									<div class="myMargin">
										<input name="" value="${detailVO.ggxh}" type="text" class="myInput" id="ggxh" readonly="readonly"/>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">报价</label>
									<div class="myMargin">
										<input name="" value="${detailVO.bj}" type="text" class="myInput" id="bj" onchange="countZqwshj()"/>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">含税单价<span>*</span></label>
									<div class="myMargin">
										<input name="" value="${detailVO.hsdj}" type="text" class="myInput" id="hsdj" dataType="Require" msg="不可为空" onchange="count()"/>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row-fluid">
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">件数</label>
									<div class="myMargin">
										<input name="" value="${detailVO.js}" type="text" class="myInput" id="js" onchange="jsChange()"/>
										<span id="js_span"></span>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">转换率</label>
									<div class="myMargin">
										<input name="" value="${detailVO.zhl}" type="text" class="myInput" id="zhl" readonly="readonly"/>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">数量<span>*</span></label>
									<div class="myMargin">
										<input name="" value="${detailVO.sl}" type="text" class="myInput" id="sl" onchange="slChange()" dataType="DoubleR" msg="格式错误"/>
										<input name="" value="${detailVO.zjl}" type="hidden" id="zjl"/>
										<span id="sl_span"></span>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row-fluid">
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">折前无税合计</label>
									<div class="myMargin">
										<input name="" value="${detailVO.zqwshj}" type="text" class="myInput" id="zqwshj" />
									</div>
								</div>
							</div>
							
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">税率（%）</label>
									<div class="myMargin">
										<input name="" value="${detailVO.tax}" type="text" class="myInput" id="shl" readonly="readonly"/>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">无税单价</label>
									<div class="myMargin">
										<input name="" value="${detailVO.wsdj}" type="text" class="myInput" id="wsdj" />
									</div>
								</div>
							</div>
						</div>
						
						<div class="row-fluid">
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">价税合计</label>
									<div class="myMargin">
										<input name="" value="${detailVO.jshj}" type="text" class="myInput" id="jshj" />
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">无税金额</label>
									<div class="myMargin">
										<input name="" value="${detailVO.wsje}" type="text" class="myInput" id="wsje" />
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">存货用途</label>
									<div class="myMargin">
										<input name="" value="${detailVO.chyt}" type="text" class="myInput" id="chyt" />
									</div>
								</div>
							</div>
						</div>
						
						<div class="row-fluid">
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">扣率（%）</label>
									<div class="myMargin">
										<input name="" value="${empty detailVO.kl ? '100' : detailVO.kl}" type="text" class="myInput" id="kl" onchange="klChange()"/>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">扣率2（%）</label>
									<div class="myMargin">
										<input name="" value="${empty detailVO.kl2 ? '100' : detailVO.kl2}" type="text" class="myInput" id="kl2" onchange="countZke()"/>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">折扣额</label>
									<div class="myMargin">
										<input name="" value="${detailVO.zke}" type="text" class="myInput" id="zke" />
									</div>
								</div>
							</div>
						</div>
						
						<div class="row-fluid">
							<div class="span4">
								<div class="control-group">
									<label class="myWidth">生产日期</label>
									<div class="myMargin">
										<input name="vo.customName" class="myInput Wdate" id="scrq" 
											value="<fmt:formatDate value="${detailVO.scrq}" pattern="yyyy-MM-dd" />" type="text" readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
									</div>
								</div>
							</div>
							<div class="span8">
								<div class="control-group">
									<label class="myWidth">备注</label>
									<div class="myMargin">
										<input name="" value="${detailVO.bz}" type="text" style="width:430px; " id="bz"/>
									</div>
								</div>
							</div>
						</div>
				</div>
			</div>
		</div>
	</div>
</form>
</body>
</html>