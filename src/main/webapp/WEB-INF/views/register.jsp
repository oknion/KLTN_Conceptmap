<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>Registration Form</title>
  <link rel="stylesheet" href="resources/css/registerCss.css" type="text/css"/>
  <script src="resources/js/angular.min.js"></script>
</head>
<body>
<h1 class="register-title">Welcome</h1>
 <div align="center"><c:if test="${error}">  
           <font color="red">Account's already exist or your information is invalid!</font><br/><br/>  
      </c:if></div>  
  <form class="register" action="registerAccount" method="POST" >
    <div class="register-switch">
    <input type="radio" name="sex" value="M" id="sex_m" class="register-switch-input" checked>
    <label for="sex_m" class="register-switch-label">Male</label>
    <input type="radio" name="sex" value="F" id="sex_f" class="register-switch-input">
    <label for="sex_f" class="register-switch-label">Female</label>
    </div>
    <input type="text" name="userId" class="register-input" placeholder="Username" ng-minlength=5>
    <input type="text" name="studentId" class="register-input" placeholder="Student Id" ng-minlength=5>
   	Class: <select name="classes">
   				<option value="IT02" selected>IT02</option>
			      <option value="IT01" selected>IT01</option>
			      
	</select>
	Khoa:<select name="khoa">
					<option value="CKD" selected>XYZ</option>
				      <option value="CNTT" selected>CNTT</option>
				      
	</select>
    <input type="email" name="email" class="register-input" placeholder="Email address">
    <input type="password" name="password" class="register-input" placeholder="Password">
    <input type="text" name="fullName" class="register-input" placeholder="Full Name">
    <input type="submit" value="Create Account" class="register-button">
    <a href="login" style="color: #00ff00;" ><i> Or Login here!</i> </a>  
  </form>
 

</body>
</html>