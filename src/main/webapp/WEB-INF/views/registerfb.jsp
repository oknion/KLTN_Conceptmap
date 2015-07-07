<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>Registration Form</title>
  <link rel="stylesheet" href="resources/css/registerCss.css" type="text/css"/>
  <script src="resources/js/angular.min.js"></script>
</head>
<body>
<h1 class="register-title">Welcome</h1>
 <div align="center"><c:if test="${error}">  
           <font color="red">Your student ID was invalid!</font><br/><br/>  
  </c:if></div>  
  <form class="register" action="signup" method="POST" >
  <input type="hidden" name="scope" value="email" />
    <input type="text" name="studentId" class="register-input" placeholder="
Student Id" value="ConHeo01">
<input type="text" name="fullName" class="register-input" placeholder="
Full Name" value="Nguyên Con Heo">
Class:<select name="classes">
 <option value="IT02" selected>Heo Mập Hơn</option>
			      <option value="IT01" selected>Heo Mập</option>
			     
</select>
<br>
Khoa:<select name="khoa">
			      <option value="CNTT" selected>CNTT</option>
			      
</select>

    <input type="submit" value="Create Account" class="register-button">
    <a href="login" style="color: #00ff00;" ><i> Or Login here!</i> </a>  
  </form>
 

</body>
</html>