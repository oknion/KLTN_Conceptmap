<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script>
function findFriends(){
	$('#findFriendsTable').empty();
	var s='<tr><th>Username</th><th>Full Name</th><th>Student ID</th><th>Email</th><th>Add Friend</th></tr>';
	var friendName = document.getElementById("friendName").value
	
	if(friendName!=""){
	$.ajax({
		 type: 'POST',
		 url: 'findFriend',
		 data : "friendName=" + friendName,  
	     success : function(data) {
	    	 $('#findFriendsTable').append(s);
	    	data.forEach(function(entry) {
	    		
	    		var addString='<tr>'+'<td>'+entry.username+'</td>'
	    		+'<td>'+entry.fullName+'</td>'
	    		+'<td>'+entry.mssv+'</td>'
	    		+'<td>'+entry.email+'</td>'
	    		+'<td> <a href="addFriend?friendId='+entry.username+'">Add</a></td>'
	    		
	    						+'</tr>';
	    						
	    		$('#findFriendsTable').append(addString);
	    	});
	    }
	});
	}else{
		$('#findFriendsTable').empty();
	}
	
}
</script>
</head>
<body>
 Search: <input type="text" id="friendName" name="fname"> <button type="button" onclick='findFriends()'>Search</button>
<table id="findFriendsTable">
  
</table>
<hr>
<table>
  <tr>
    <th>Full Name</th>
    <th>Student ID</th> 
    <th>Status</th>
    <th>Delete</th>
  </tr>
  <c:forEach items="${unconfirmFriends}" var="friend">
       <tr>
    <td>${friend.sourceUser.fullName}</td>
    <td>${friend.sourceUser.mssv }</td> 
   		 <c:if test="${friend.status}">
					 <td>  Confirmed!</td>
					</c:if>
					<c:if test="${!friend.status}">
					 <td>  <a href="confirmAddFriend?friendId=${friend.friendId}">Confirm</a></td>
					</c:if>
	 <td><a href="deleteAddFriend?friendId=${friend.friendId }">Del</a></td>
    
  </tr>                    
   </c:forEach> 
  <c:forEach items="${friends}" var="friend">
       <tr>
    <td>${friend.desUser.fullName}</td>
    <td>${friend.desUser.mssv }</td> 
   		 <c:if test="${friend.status}">
					 <td>  Confirmed!</td>
					</c:if>
					<c:if test="${!friend.status}">
					 <td>  Waitting</td>
					</c:if>
	  <td><a href="deleteAddFriend?friendId=${friend.friendId }">Del</a></td>
    
  </tr>                    
   </c:forEach>  
  
</table>
</body>
</html>