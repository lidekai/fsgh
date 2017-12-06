<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图片轮播</title>
<link href="<c:url value='/css/Style1.css'/>" rel="stylesheet" type="text/css" />
<script src="<c:url value='/lib/jquery-1.10.2.js'/>"></script>  
<script src="<c:url value='/lunbo/lunbo.js'/>"></script>
</head>
<body>
	 
        <div class="Teac">
            <ul>
                <li class="Zzhao">
                    <a href="#"><img src="<c:url value='/resources/photo/2017/02-17/2017031714224319148.jpg' />" /></a>
                </li>
                <li class="Zzhao_cent">
                    <a href="#"><img src="<c:url value='/images/lunbo/teach_2.png' />" /></a>
                </li>
                <li class="Zzhao">
                    <a href="#"><img src="<c:url value='/images/lunbo/teach_3.png' />" /></a>
                </li>
                <li class="Zzhao">
                    <a href="#"><img src="<c:url value='/images/lunbo/teach_4.png' />" /></a>
                </li>                            
            </ul>
           
     
    </div> 
	
</body>
</html>