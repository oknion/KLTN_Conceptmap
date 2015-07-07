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
<script>
function taskDetail(x) {	 
	//   document.getElementById("taskId").value=x; // set hidden field "shareCmId" = current clicked conceptmap id
		$.ajax({							 
			  type: 'POST',
			  url: 'getTaskInfo',			 			 
			  data: "taskid="+x,			
			  success:function(data){	
				  document.getElementById("currentEditTaskId").value=x;
				  
				  document.getElementById("inputTaskname").value=data.taskName;
				  document.getElementById("inputTaskDescription").value=data.taskDes;
				  var listUser=data.listTaskUsername.split(",");
				  $('.multiSel').empty();
				  listUser.forEach(function(entry){		
					  if(entry!=''){
					  entry=entry+',';
					  var html = '<span title="' + entry + '">' + entry + '</span>'+'';
				        $('.multiSel').append(html);
				        $(".hida").hide();
				        $("#friendList li").each(function(index){
				        	var livalue=$(this).text().trim()+',';
				        	if(livalue==entry){
				        		$(this).children("input").prop( "checked",true );
				        	}
				        });
				 	 };
				  })
				  document.getElementById("dtp_input1").value=data.datetime;				  
				  showMyModel();
			  }
		 });
	  
}
function hideTaskModal(){
	$('#makeTaskModal').modal('hide');
}
function showMyModel(){
	$('#makeTaskModal').modal({show:true}); // show popup share for user to enter information
}
$(document).ready(function(){
	 $(".dropdown dt a").on('click', function () {
		    $(".dropdown dd ul").slideToggle('fast');
		});

		$(".dropdown dd ul li a").on('click', function () {
		    $(".dropdown dd ul").hide();
		});

		function getSelectedValue(id) {
		     return $("#" + id).find("dt a span.value").html();
		}

		$(document).bind('click', function (e) {
		    var $clicked = $(e.target);
		    if (!$clicked.parents().hasClass("dropdown")) $(".dropdown dd ul").hide();
		});


		$('.mutliSelect input[type="checkbox"]').on('click', function () {
		  
		    var title = $(this).closest('.mutliSelect').find('input[type="checkbox"]').val(),
		        title = $(this).val() + ",";
		  
		    if ($(this).is(':checked')) {
		        var html = '<span title="' + title + '">' + title + '</span>';
		        $('.multiSel').append(html);
		        $(".hida").hide();
		    } 
		    else {
		        $('span[title="' + title + '"]').remove();
		        var ret = $(".hida");
		        $('.dropdown dt a').append(ret);
		        
		    }
		});
		$('#makeTaskBtn').click(function(e){
			   $('#makeTaskBtn').attr('disabled',true); // Disable ShareCmBtn
				  // Create Json to post to Controller 
				 var json = "{\"taskName\":\" "+document.getElementById("inputTaskname").value+"\",\"taskDes\":\""+document.getElementById("inputTaskDescription").value+"\",\"datetime\":\""+
				   				document.getElementById("dtp_input1").value+
					  			"\",\"listTaskUsername\":\"" +  $('dt p').text()+"\",\"taskId\":" + document.getElementById("currentEditTaskId").value+"}";
					 //User ajax to post json to controller 
				
					  	$.ajax({							 
						  type: 'POST',
						  url: 'updateTask',
						  dataType: 'json',				 
						  data: json,
						  contentType: 'application/json',
						  success:function(data){
							  hideTaskModal();
							  location.reload();
						  }
					 })
				 
			  });
		
		
		
});
 
</script>
</head>
<body>
<!-- Section 
              
                        <div class="container" style="z-index: 1; width:100%">
                          <div class="main" id="hoverid">
                           
                             <c:forEach items="${listTask}" var="listTask">
				                <div class="view view-first">
				                   <img src="${listTask.conceptmap.imgString }" />
				                       	<div class="mask">
				                         <h2>${listTask.taskName}</h2>
				                         <p>${listTask.taskDescription} <span style="color:red" >${listTask.deadLine}</span></p>
                             			<a  href="${listTask.conceptmap.cmId}" class="info">Edit CM</a>
                                 		 <a onclick="taskDetail(${listTask.taskId})"  href="#" class="info">Read more</a>
                                		</div>
                              	</div> 
                            </c:forEach>
                            
                          </div>
                        </div>   -->
   <table class="table table-hover" style="font-size:15px">
			    <thead>
			      <tr>
			        <th>TaskId</th>
			        <th>Name</th>
			        <th>Description</th>
			        <th>Deadline</th>
			       	<th>Edit Answer</th>
			       	<th>Edit Other</th>
			        <th>Report</th>
			      </tr>
			    </thead>
			    <tbody>
			  
			     <c:forEach items="${listTask}" var="task">
			       <tr>
			     <td>${task.taskId }</td>
			     <td>${task.taskName }</td>
			     <td>${task.taskDescription }</td>
			     <td>${task.deadLine }</td>
			     <td><a  href="${task.conceptmap.cmId}" class="info">Edit Answer</a></td> 
			     <td><a onclick="taskDetail(${task.taskId})"  href="#" class="info">Others</a> </td>            		
			     <td><a href="getReport?taskId=${task.taskId}" target="_blank" >report </a></td>
			     </tr>
			     </c:forEach>
			    </tbody>
   </table>		                       
    <!--Popup Make Task-->
                  <div class="modal" id="makeTaskModal">
                      <div class="modal-dialog">
                        <div class="modal-content">
                          <!--Model Header-->
                          <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title">Task's Information</h4>
                          </div>
                          <!--/Model Header-->
                          <div class="modal-body">
                            <form class="form-horizontal">                            
                                <!--Group Name-->
                                 <div class="form-group">
                                    <input type="hidden" id="makeTaskCmId">
                                    <label for="inputTaskName" class="col-lg-2 control-label"  style="color: white">Task Name</label>
                                    <div class="col-lg-10">
                                     <input type="text" class="form-control" id="inputTaskname" placeholder="Task Name">
                                    </div>
                                  </div>
                                <!--Group Name-->
                                <!--GroupDescription-->
                                  <div class="form-group">
                                    <label for="textAreaTaskDes" class="col-lg-2 control-label">Description</label>
                                    <div class="col-lg-10">
                                      <textarea class="form-control" rows="3" id="inputTaskDescription"></textarea>
                                    </div>
                                  </div>
                                <!--GroupDescription--> 
                                     <div class="form-group">
                                        <label for="dtp_input1" class="col-lg-2 control-label">DeadLine</label>
                                         <div class="col-lg-10">
                                        <div class="input-group date form_datetime col-md-12" data-date="1979-09-16T05:25" data-date-format="dd MM yyyy - HH:mm" data-link-field="dtp_input1">
                                          <input class="form-control" size="10" type="text" value="" readonly>                                          
                                          <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                                        </div>
                                        <input type="hidden" id="dtp_input1" value="" /><br/>
                                         </div>
                                      </div>
								  <!--Taskfor-->                                   
                                      <!-- Select Single User-->
                                      <div class="form-group">                                        
                                          <label class="col-lg-2 control-label">Task for</label>
                                          <div class="col-lg-10">                                          
                                          <input type="hidden" id="currentEditTaskId" style="height:0px;width:0px">
                                          <dl class="dropdown">
											  <dt>
											    <a href="#">
											      <span class="hida">Select</span>    
											      <p class="multiSel"></p>  
											    </a>
											    </dt>											
											  <dd  >
											    <div class="mutliSelect" >
											      <ul id="friendList">
											      	<li><input type="checkbox"  value="IT01" />IT01</li>
											         <li><input type="checkbox"  value="IT02" />IT02</li>
											      <c:forEach items="${listFriend}" var="friend" >
											        <li>
											          <input type="checkbox"  value="${friend.desUser.userId }" />${friend.desUser.userId }</li>
											        </c:forEach>
											        <c:forEach items="${listFriend1}" var="friend" >
											        <li>
											          <input type="checkbox" value="${friend.sourceUser.userId }" />${friend.sourceUser.userId }</li>
											        </c:forEach>											        
											      </ul>
											    </div>
											  </dd>											 
											</dl>         
                                          </div>                                         
                                        </div>
                                      <!-- /Select Single User-->                    
                                  <!--/Taskfor-->
                            </form>
                          </div>
                          <!--Model Footer-->
	                          <div class="modal-footer">
	                            <a href="#" data-dismiss="modal" class="btn">Close</a>
	                            <a href="#" id="makeTaskBtn" class="btn">Save</a>
	                          </div>
                          <!--/Model Footer-->
                        </div>
                      </div>
                  </div>
      <!--/Popup Make Task-->
        <!--DateTimePicker-->
            <script type="text/javascript" src="resources/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
            <link rel="stylesheet" href="resources/bootstrap/css/bootstrap-datetimepicker.min.css">
            <script type="text/javascript">
            $('.form_datetime').datetimepicker({
              //language:  'fr',
              weekStart: 1,
              todayBtn:  1,
              autoclose: 1,
              todayHighlight: 1,
              startView: 2,
              forceParse: 0,
              showMeridian: 1
            });

            </script>
          <!--/DateTimePicker-->
 </body>
</html>