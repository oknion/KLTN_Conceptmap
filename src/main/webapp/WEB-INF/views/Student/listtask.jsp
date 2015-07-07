<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
  <head>
                <meta name="viewport" content="width=device-width, initial-scale=1.0">               
                <!--Hover image-->
                <link rel="stylesheet" href="resources/bootstrap/css/hoverstyle1.css">
                <link rel="stylesheet" href="resources/bootstrap/css/hoverstyle_common.css">               
                <link rel="stylesheet" href="resources/bootstrap/css/Oswaldfont.css">
                <!--/Hover image -->                           
            	
               <title>List Task</title>            
 </head>
<script>
function getTaskDocuments(cmId){
	 $('#tableGetDocuments').empty();
	$.ajax({
		type: 'POST',
		  url: 'getTaskDocuments',			 			 
		  data: "cmId="+cmId,			
		  success:function(data){
			 
			  data.forEach(function(entry){	
				  var row=' <tr> <td>'+entry.documentName+'</td> <td> <a href="http://'+window.location.host+'/tiles/download/' + entry.documentId + '" target="_blank">Download</a> '+'</td>';
				 $('#tableGetDocuments').append(row);
			  });
			
			  $('#getDocumentModal').modal({show:true})
		  }
	});
}
function taskDetail(x) {	 
	   document.getElementById("taskId").value=x; // set hidden field "shareCmId" = current clicked conceptmap id
		$.ajax({							 
			  type: 'POST',
			  url: 'getTaskInfo',			 			 
			  data: "taskid="+x,			
			  success:function(data){
				  
				  document.getElementById("inputTaskname").value=data.taskName;
				  document.getElementById("inputTaskDescription").value=data.taskDes;
				  showMyModel();
			  }
		 });
	  
}
function showMyModel(){
	$('#myModal').modal({show:true}); // show popup share for user to enter information
}
 
</script>
<body>
<!-- Section -->
               <div>
               <table class="table table-hover">
			    <thead>
			      <tr>
			        <th>TaskId</th>
			        <th>Name</th>
			        <th>Description</th>
			        <th>Deadline</th>
			        <th>Get documents</th>
			        <th>Submit Answer</th>
			      </tr>
			    </thead>
			    <tbody>
			      
               <c:forEach items="${listTask}" var="task"> 
                 <tr>
			        <td>${task.taskId }</td>
			        <td>${task.taskName }</td>
			        <td>${task.taskDescription }</td>
			        <td>${task.deadLine }</td>
			        <td><a href="#" onclick="getTaskDocuments(${task.conceptmap.cmId})">Get documents</a></td>
			         <c:if test="${!task.hethan}">
					<td><a  href="dotask${task.taskId}" class="info">Submit</a></td>
					</c:if>
					<c:if test="${task.hethan}">
						<td><label  class="warning" >Expired</label></td>
					</c:if>
			      </tr>
			     
               </c:forEach>
                </tbody>
			      </table>
                        <div class="container" style="z-index: 1; width:100%">
                          <div class="main" id="hoverid">
                        <!--     <c:forEach items="${listTask}" var="listTask">                           
				                <div class="view view-first">
				                   <img src="resources/images/cat.jpg" />
				                       	<div class="mask">
				                         <h2>${listTask.taskName}</h2>
				                         
				                         <p>${listTask.taskDescription} <span style="color:red" >${listTask.deadLine}</span></p>
                           				 <c:if test="${!listTask.hethan}">
										   	<a  href="dotask${listTask.taskId}" class="info">Do</a>
										</c:if>
                           				
                                 		 <a onclick="taskDetail(${listTask.taskId})"  href="#" class="info">Read more</a>
                                		</div>
                              	</div>  
                            </c:forEach> -->
                          </div>
                        </div>  
                  </div>
                  
  <!-- /Section -->
  <!--Popup Save-->
                  <div class="modal" id="myModal">
                      <div class="modal-dialog">
                        <div class="modal-content">
                          <!--Model Header-->
                          <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title">Task Information</h4>
                          </div>
                          <!--/Model Header-->
                          <div class="modal-body">
                            <form class="form-horizontal">
                              <!--Group Name-->
                                  <div class="form-group">
                                   <input type="hidden" id="taskId">
                                    <label for="inputUsername" class="col-lg-2 control-label"  style="color: white">Name</label>
                                    <div class="col-lg-10">
                                      <input type="text" class="form-control" id="inputTaskname" disabled placeholder="Name">
                                    </div>
                                  </div>
                                <!--Group Name-->
                                <!--GroupDescription-->
                                  <div class="form-group">
                                    <label for="textArea" class="col-lg-2 control-label">Description</label>
                                    <div class="col-lg-10">
                                      <textarea class="form-control" rows="3" id="inputTaskDescription" disabled></textarea>

                                    </div>
                                  </div>
                                <!--GroupDescription-->             
							</form>
                          </div>
                          <!--Model Footer-->
                          <div class="modal-footer">
                            <a href="#" data-dismiss="modal" class="btn">Close</a>                            
                          </div>

                          <!--/Model Footer-->
                        </div>
                      </div>
                  </div>
          <!--/Popup Save-->
          <!--Popup Get documents-->
                  <div class="modal" id="getDocumentModal">
                      <div class="modal-dialog">
                        <div class="modal-content">
                          <!--Model Header-->
                          <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title">Task' Documents</h4>
                          </div>
                          <!--/Model Header-->
                          <div class="modal-body">
                            <form class="form-horizontal">
                              <!--Group Name-->
                                 
                                <!--Group Name-->
                                <!--GroupDescription-->
                                  <div class="form-group">
                                	<table class="table table-hover">
								    <thead>
								      <tr>
								        <th>Document's Name</th>
								        <th>Download</th>
								        </tr>
								    </thead>
									    <tbody id="tableGetDocuments">								      
						                    
									     </tbody>
								     </table>
                                  </div>
                                <!--GroupDescription-->             
							</form>
                          </div>
                          <!--Model Footer-->
                          <div class="modal-footer">
                            <a href="#" data-dismiss="modal" class="btn">Close</a>                            
                          </div>

                          <!--/Model Footer-->
                        </div>
                      </div>
                  </div>
          <!--/Popup Get documents-->
                    

 </body>
</html>