<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Task's Report</title>
 <link rel="stylesheet" href="resources/css/expandablelist.css">
  <script type="text/javascript" src="resources/jQuery/jquery-2.1.1.min.js"></script>
                
<script>

function loadlistConceptmap(){
	$.ajax({
		 type: 'POST',
		  url: 'getReport',				 
		  data: 'taskId=4' ,
		  success:function(data){
			  alert(data);
			  $.each(data,function(key,value){
				 alert(key);
				 alert(value.score);
			  });
		  }
	});
}
function prepareList() {
	$('#expList').find('li:has(ul)').unbind('click').click(function(event) {
		if(this == event.target) {
			$(this).toggleClass('expanded');
			$(this).children('ul').toggle('medium');
		}
		return false;
	}).addClass('collapsed').removeClass('expanded').children('ul').hide();
 
	//Hack to add links inside the cv
	$('#expList a').unbind('click').click(function() {
		window.open($(this).attr('href'));
		return false;
	});
	//Create the button functionality
	$('#expandList').unbind('click').click(function() {
		$('.collapsed').addClass('expanded');
		$('.collapsed').children().show('medium');
	})
	$('#collapseList').unbind('click').click(function() {
		$('.collapsed').removeClass('expanded');
		$('.collapsed').children().hide('medium');
	})
};
	 
	  $(document).ready( function() {
	      prepareList();
	  });
	//Create the button funtionality
	    $('#expandList')
	    .unbind('click')
	    .click( function() {
	        $('.collapsed').addClass('expanded');
	        $('.collapsed').children().show('medium');
	    })
	    $('#collapseList')
	    .unbind('click')
	    .click( function() {
	        $('.collapsed').removeClass('expanded');
	        $('.collapsed').children().hide('medium');
	    });
</script>
</head>
<body>
<!-- <div class="listControl"><a id="expandList"></a>Expand All <a id="collapseList"></a>Collapse All</div> -->
Task's name: ${task.taskName }<br>
Task's description: ${task.taskDescription }
<hr>
<!-- <div id="listContainer">
  <ul id="expList">
  <li> -->
  <table class="table table-hover" style="width:70%">
    	<tr class="tableHead">
	    	<td>Username</td>
	    	<td>Full name</td>
	    	<td> Highest score</td>
	    	<td> Lowest score</td>
	    	<td> No.</td>
	    	<td> Avg</td>
    	</tr>
   	
  <c:forEach items="${reportInfors}" var="reportInfor" >
  	<tr>
  		<td>${reportInfor.username }</td>
    	<td>${reportInfor.fullName }</td>
    	<td> ${reportInfor.highestScore }</td>
    	<td> ${reportInfor.lowestScore }</td>
    	<td>${reportInfor.no }</td>
    	<td> ${reportInfor.score }</td>
  	</tr>
  	
  	
   	<!-- <ul>
		   	<table>
		    	<tr>
		    	<th>Date</th>
		    	<th>Score</th>
		    	<th>Errors</th>	
		    	</tr>
		   	
		  	<c:forEach items="${reportInforDetails}" var="reportInforDetail" >
		  		<c:set var="username" scope="session" value="${reportInforDetail.username}"/>
				<c:if test="${username ==reportInfor.username}">
					
					  	<tr>
					  	<td>${reportInforDetail.datetimeDate }</td>
					    	<td>${reportInforDetail.score }</td>
					    	<td> ${reportInforDetail.numError }</td>
					    	
					  	</tr>
					  	
					
				  	
				</c:if>
		  	</c:forEach>
		  	</table>
  	</ul> -->
  	
  	</c:forEach>
    
 	</table>
      
  <!--  </li>	    
    
   
  </ul>
</div> -->
</body>
</html>