<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<body style=" margin:0;	padding:0;
	height: 100%;
	background-color:#DFDFDF;">

		<!-- Header -->
		<tiles:insertAttribute name="header" />
		<!-- Menu Page -->		
		<!-- Body Page -->
		
        <div class="content" id="content"  >
           <div id="scheduler" style="margin-left:auto;margin-right:auto;align:center;width:900px">${body}</div>
        </div>
    
	
	<!-- Footer Page -->
	
	<tiles:insertAttribute name="footer" />

   </body>
</html>