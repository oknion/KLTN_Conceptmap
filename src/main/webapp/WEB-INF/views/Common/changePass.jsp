<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script>   
   	$(document).ready(function(){	   
		
</script>
</head>
<body>
<form class="form-horizontal" action="changePass" method="POST">
  <fieldset>
    <legend>Change Password</legend>
   <div align="center"><c:if test="${error}">  
           <font color="red">Your student ID was invalid!</font><br/><br/>  
  </c:if></div> 
  
    <div class="form-group">
      <label for="inputPassword" class="col-lg-2 control-label">Old Password</label>
      <div class="col-lg-10">
        <input type="password" class="form-control" name="inputPassword" placeholder="Old Password">
      </div>
    </div>
      <div class="form-group">
      <label for="inputNewPassword" class="col-lg-2 control-label">New Password</label>
      <div class="col-lg-10">
        <input type="password" class="form-control" name="inputNewPassword" placeholder="New Password">
      </div>
    </div>
      <div class="form-group">
      <label for="inputNewPassword1" class="col-lg-2 control-label">Confirm Again</label>
      <div class="col-lg-10">
        <input type="password" class="form-control" name="inputNewPassword1" placeholder="Confirm Password">
      </div>
    </div>
    <div class="form-group">
      <div class="col-lg-10 col-lg-offset-2">
       <input type="submit" value="Submit" class="register-button">
      </div>
    </div>
  </fieldset>
</form>

</body>
</html>