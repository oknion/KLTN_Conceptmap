<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Insert title here</title>
<style>
    .error {
        color: red;
         
    }
</style>
</head>
<body>
	    <div align="left">
        <h2>Account information</h2>
       <form action="accountInfo" method="POST">
       <table style="font-size:17px;">
 <tr>
 	<td> Username:</td>
 	<td> <input type="text" name="username" disabled readonly value="${user.userId}"></td>
 </tr> 
 <tr><td> Full Name:</td><td> <input type="text" name="fullName" value="${user.fullName}"></td></tr>
 <tr>
 	<td> Email
 	    : </td><td><input type="text" name="email" value="${user.email}"></td>
  </tr>
  <tr>
  <td>Role  
     :</td>
     <td> <select name="role" disabled readonly>
			   	<c:choose>
			      <c:when test="${user.role=='student'}">
			      <option value="Student" selected>Student</option>
			      </c:when>
			      <c:otherwise> <option value="Student">Student</option>
			      </c:otherwise>
				</c:choose>
			  	<c:choose>
			      <c:when test="${user.role=='admin'}">
			      <option value="admin" selected>Admin</option>
			      </c:when>
			      <c:otherwise> <option value="admin">Admin</option>
			      </c:otherwise>
				</c:choose>
			  	<c:choose>
			      <c:when test="${user.role=='teacher'}">
			      <option value="teacher" selected>Teacher</option>
			      </c:when>
			      <c:otherwise> <option value="teacher">Teacher</option>
			      </c:otherwise>
				</c:choose>
  
			</select></td>
</tr>
<tr>			
<td>  Class   :</td>
<td> <select name="classes" disabled readonly>
			    <c:choose>
			      <c:when test="${user.classes.classId=='01'}">
			      <option value="01" selected>IT01</option>
			      </c:when>
			      <c:otherwise> <option value="01" selected>IT01</option>
			      </c:otherwise>
				</c:choose>
			  	 <c:choose >
			      <c:when test="${user.classes.classId=='02'}">
			      <option value="02" selected>IT02</option>
			      </c:when>
			      <c:otherwise> <option value="02">IT02</option>
			      </c:otherwise>
				</c:choose>
			</select></td></tr>
			<tr><td>
  Student ID: </td><td ><input type="text" name="studentId" value="${user.mssv}" disabled readonly></td></tr>
  <tr><td>
  Khoa:</td><td><input type="text" name = "khoa" value="${user.khoa }" disabled readonly><br>
  </td></tr>
  </table>
  <input type="submit" value="Save">
</form>
    </div>
</body>
</html>