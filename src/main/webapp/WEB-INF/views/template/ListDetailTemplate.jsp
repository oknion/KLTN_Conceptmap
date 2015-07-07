<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>        
<style>
body{ margin-top:20px; margin-bottom:20px; background-color:#DFDFDF;}
</style>
</head>
<body>
	<div class="container" style="border: #C1C1C1 solid 1px; border-radius:1px;min-height:100%;">
		<!-- Body Page -->
		<div class="span-29 last">
			<tiles:insertAttribute name="body" />
		</div>

	</div>
</body>
</html>