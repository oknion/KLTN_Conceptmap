<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Insert title here</title>
<style>
	.img-command{
		width:10px;
		height:10px;
	}
</style>
</head>
<body>
<form action="edituserinfo" method="POST">
<table>
 <tr> 
 <td>Username :</td>
 <td> <input type="text" name="username" readonly value="${user.userId}"><br>
 </td>
 </tr>
 <tr>
 <td>
  Full Name:</td>
  <td> <input type="text" name="fullName" value="${user.fullName}"><br>
 </td>
 </tr> 
  <tr>
  <td>
  Email    : 
  </td>
  <td><input type="text" name="email" value="${user.email}"><br>
  </td>
  </tr>
  <tr>
  <td>
  Role     : </td>
  <td>
  <select name="role">
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
  
			</select>
			</td></tr>
			<tr>
	<td>		
  Class   : </td>
  <td><select name="classes">
			    <c:choose>
			      <c:when test="${user.classes.classId=='IT01'}">
			      <option value="IT01" selected>IT01</option>
			      </c:when>
			      <c:otherwise> <option value="IT01" selected>IT01</option>
			      </c:otherwise>
				</c:choose>
			  	 <c:choose >
			      <c:when test="${user.classes.classId=='IT02'}">
			      <option value="IT02" selected>IT02</option>
			      </c:when>
			      <c:otherwise> <option value="IT02">IT02</option>
			      </c:otherwise>
				</c:choose>
			</select>
			</td>
			</tr>
			<tr><td>
  Student ID: </td>
  <td> <input type="text" name="studentId" value="${user.mssv}"><br>
  </td>
  </tr>
  </table>
  <input type="submit" value="Submit">
  
</form>

<table class="table table-striped table-hover ">
  <thead>
    <tr>
      <th>#</th>
      <th>Username</th>
      <th>Full Name</th>
      <th>Email</th>
      <th>Role</th>
      <th>Class</th>
      <th>Student ID</th>
      <th>Edit</th>
     
    </tr>
  </thead>
  <tbody>
 <c:forEach items="${listUser}" var="user" >
 <tr>
 <td></td>
	<td>${user.userId}</td>
	<td>${user.fullName}</td>
	<td>${user.email}</td>
	<td>${user.role}</td>
	<td>${user.classes.className}</td>
	
	<td>${user.mssv}</td>
	
	<td><a href="edituserinfo?userId=${user.userId}">Edit</a></td> 	
</tr>
 </c:forEach>

  
         
  </tbody>
</table> 
</body>
</html>