<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/WEB-INF/common/exception.jsp"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图片轮播</title>
<link href="<c:url value='/css/Style1.css'/>" rel="stylesheet" type="text/css" />
<script src="<c:url value='/lib/jquery-1.10.2.js'/>"></script>  
<script src="<c:url value='/lunbo/lunbo.js'/>"></script>
<script type="text/javascript">
	function viewProduct(id){
		var url = "<c:url value='/info/product/viewProduct.jhtml'/>?id=" + id;
		window.open(url);
	}
</script>
</head>
<body>
	 
        <div class="Teac">
            <ul>
                	<c:forEach items="${imgMap}" var="map" varStatus="s">
		                <li class="Zzhao_cent">
	                    	<a href="" onclick="viewProduct(${map.key})"><img src="<c:url value='../../${map.value}' />" /></a>
		                </li>
                	</c:forEach>
            </ul>
           
     
    </div> 
	
</body>
</html>