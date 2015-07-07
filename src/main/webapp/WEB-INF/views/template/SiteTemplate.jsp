<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Spring MVC - Tiles Integration tutorial</title>
	<link rel="stylesheet" href="resources/css/screen.css" type="text/css" media="screen, projection"></link>
	<link rel="stylesheet" href="resources/css/print.css" type="text/css" media="print"></link>
	<!--[if IE]>
	<link rel="stylesheet" href="resources/css/ie.css" type="text/css" media="screen, projection">
	<![endif]-->


</head>
<body style=" margin:0;	padding:0;
	height: 100%;
	background-color:#DFDFDF;">

	<div class="container" style="border: #C1C1C1 solid 1px; position:absolute; border-radius:10px;min-height:90%; width:100%;">
		<!-- Header -->
		<tiles:insertAttribute name="header" />
		<!-- Menu Page -->		
		<!-- Body Page -->
		<div style="padding-bottom:15px; height: 85%;">
			<tiles:insertAttribute name="body" />	
		</div>
		
	</div>
	<!-- Footer Page -->
	<div  style="width:100%;
			 height:10px;
			 position:absolute;
			 bottom:10%;
			 left:0;">
	<tiles:insertAttribute name="footer" />
</div>

</body>
</html>