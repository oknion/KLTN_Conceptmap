<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">
<head>

  <!--Hover image-->
    <link rel="stylesheet" href="resources/bootstrap/css/hoverstyle1.css">
    <link rel="stylesheet" href="resources/bootstrap/css/hoverstyle_common.css">
    <link rel="stylesheet" href="resources/bootstrap/css/Oswaldfont.css">
 <link rel="stylesheet" href="resources/css/shareModalCss.css">
  <!--/Hover image -->
    <link rel="stylesheet" href="resources/bootstrap/css/style.css" >


    
   <script type="text/javascript">
   function shareCM(x) {	 
	   document.getElementById("shareCmId").value=x; // set hidden field "shareCmId" = current clicked conceptmap id
	   $.ajax({
			 type: 'POST',
			  url: 'getListShareUsername',				 
			  data: 'shareCmId='+x,
			  success:function(data){
				  $('.multiSel').empty();
				  data.forEach(function(entry){					 
					  entry=entry+',';
					  var html = '<span title="' + entry + '">' + entry + '</span>'+'';
				        $('.multiSel').append(html);
				        $(".hida").hide();
				        $("#friendListShare li").each(function(index){
				        	var livalue=$(this).text().trim()+',';
				        	if(livalue==entry){
				        		$(this).children("input").prop( "checked",true );
				        	}
				        })
				  })
				   $('#shareModal').modal({show:true}); // show popup share for user to enter information
			  }
		});
	
	}
   function hideTaskModal(){						  
	     $('.multiSel').empty();
	     $("#friendList li").each(function(index){
	        	
	        		$(this).children("input").prop( "checked",false );
	        	
	        });
		  $('#makeTaskModal').modal('hide'); // hide popup makeTaskModal
		  $('#makeTaskBtn').attr('disabled',false);// Enable button makeTaskBtn
   };
   function hideShareModel(){

        $('.multiSel').empty();
		$('#shareModal').modal('hide');
	}
   function makeTask(x){
	   document.getElementById("makeTaskCmId").value=x;
	   $('#makeTaskModal').modal({show:true});
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
	   
		   $('#ShareCmBtn').click( function (e) {
				  $('#ShareCmBtn').attr('disabled',true); // Disable ShareCmBtn
				  // Create Json to post to Controller
				  var json = "{\"cmId\":"+document.getElementById("shareCmId").value+",\"listUsername\":\"" +  $('dt p').text()+"\"}";
					 //User ajax to post json to controller 
					
					  	$.ajax({							 
						  type: 'POST',
						  url: 'sharecm',
						  dataType: 'json',				 
						  data: json,
						  contentType: 'application/json',
						  success:function(){
							 	 hideShareModel(); // hide popup shareModal	
								 $('#ShareCmBtn').attr('disabled',false);// Enable button ShareCmBtn
							  
						  }
					 })
				
			  });
		   
		   $('#makeTaskBtn').click(function(e){
			   $('#makeTaskBtn').attr('disabled',true); // Disable ShareCmBtn
				  // Create Json to post to Controller 
				 var json = "{\"cmId\":\""+document.getElementById("makeTaskCmId").value+"\",\"taskName\":\" "+document.getElementById("inputTaskName").value+"\",\"taskDes\":\""+document.getElementById("textAreaTaskDes").value+"\",\"datetime\":\""+
				   				document.getElementById("dtp_input1").value+
					  			"\",\"listTaskUsername\":\"" +  $('#dtTaskModal p').text()+"\"}";
					 //User ajax to post json to controller 
				
					  	$.ajax({							 
						  type: 'POST',
						  url: 'makeTask',
						  dataType: 'json',				 
						  data: json,
						  contentType: 'application/json',
						  success:function(data){
							  hideTaskModal();
						  }
					 })
				 
			  });
		  
	});
   </script>
</head>
<body>
      <!-- Section -->
      <section>
        <div class="container" style="z-index: 1; width:100%">
          <div class="main" id="hoverid">
			
			 <c:forEach items="${listCM}" var="listCM">
                <div class="view view-first">
                   <img src="${listCM.imgString }" />
                       <div class="mask">
                           <h2>${listCM.cmName}</h2>
                           <p>${listCM.description} ${listCM.dateCreate}</p>                           
                           <a  href="${listCM.cmId }"  target="_parent" class="info">Edit</a>
                           <a onclick="makeTask(${listCM.cmId})" href="#" class="info">Task</a>
               			   <a onclick="shareCM(${listCM.cmId})" href="#" class="info">Share</a>
               			  
                       </div>
                 </div> 
               </c:forEach>
             
          </div>
        </div>  
      </section>  
      <!-- /Section -->

      <!--Popup Share-->
        <div class="modal" id="shareModal">
          <div class="modal-dialog">
            <div class="modal-content">
				
              <!--Model Header-->
                <div class="modal-header">
                  <a onclick="hideShareModel()" class="close" data-dismiss="modal" aria-hidden="true" href="#" >x</a>
                  <h4 class="modal-title" style="color: white;">Share with</h4>
                  
                </div>
              <!--/Model Header-->

              <!--Model Body-->
                <div class="modal-body">
                  <form class="form-horizontal">

                      <!-- Show Task input Script
                        <script type="text/javascript">
                          $(document).ready(function(){
                          // Add onclick handler to checkbox w/id checkme
                          $("#checkmePublic").click(function(){
                          // If checked
                          if ($("#checkmePublic").is(":checked"))
                          {
                           //show the hidden div
                          $("#extra").hide("fast");
                           }
                          else
                          {
                          //otherwise, hide it
                          $("#extra").show("fast");
                          }
                          });
                          });
                        </script>
                      <!-- /Show Task input Script-->

                      <!--Check box form group-->
                        <div class="form-group">
                        <input type="hidden" id="shareCmId">
                         <!--  <label for="checkme"class="col-lg-2 control-label" style="color: white;">Public</label>
                          <div class="col-lg-8" >

                            <input type="checkbox"  id="checkmePublic" style="margin-top: 10px;"/>

                          </div>
                        </div>-->
                      <!--/Check box form group-->

                      <!--Show when check box is checked-->
                          <div  id="extra">
                            <!-- Select Single-->
								<div class="form-group">
                                          <label class="col-lg-2 control-label">Select</label>
                                          <input type="hidden" style="height:0px;width:0px">
                                            
                                           <dl class="dropdown">

  <dt >
    <a href="#">
      <span class="hida">Select</span>    
      <p id="listUsername" class="multiSel"></p>  
    </a>
    </dt>

  <dd id="ddShareModal">
    <div class="mutliSelect" >
      <ul id="friendListShare">
      
          
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
                            <!-- /Select Single-->
                           
                          </div>
                      <!--/Show when check box is checked-->
					</div>
					
                  </form>
                </div>
              <!--/Model Body-->


              <!--Model Footer-->
                <div class="modal-footer">
                   <a onclick="hideShareModel()" href="#" class="btn btn-primary">Close</a>
                  <a href="#" id="ShareCmBtn" class="btn btn-primary">Share</a>
                </div>
              <!--/Model Footer-->
            </div>
          </div>
        </div>
      <!--/Popup Share-->
        <!-- Bootstrap Multiselect-->
   
     <!--Popup Make Task-->
                  <div class="modal" id="makeTaskModal">
                      <div class="modal-dialog">
                        <div class="modal-content">
                          <!--Model Header-->
                          <div class="modal-header">
                            <a onclick="hideTaskModal()" class="close" data-dismiss="modal" aria-hidden="true" href="#" >x</a>
                  
                            <h4 class="modal-title">Make Task</h4>
                          </div>
                          <!--/Model Header-->
                          <div class="modal-body">
                            <form class="form-horizontal">                            
                                <!--Group Name-->
                                 <div class="form-group">
                                    <input type="hidden" id="makeTaskCmId">
                                    <label for="inputTaskName" class="col-lg-2 control-label"  style="color: white">Task Name</label>
                                    <div class="col-lg-10">
                                     <input type="text" class="form-control" id="inputTaskName" placeholder="Task Name">
                                    </div>
                                  </div>
                                <!--Group Name-->
                                <!--GroupDescription-->
                                  <div class="form-group">
                                    <label for="textAreaTaskDes" class="col-lg-2 control-label">Description</label>
                                    <div class="col-lg-10">
                                      <textarea class="form-control" rows="3" id="textAreaTaskDes"></textarea>

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
                                          
                                          <input type="hidden" style="height:0px;width:0px">
                                          <dl class="dropdown">

  <dt id="dtTaskModal">
    <a href="#">
      <span class="hida">Select</span>    
      <p id="listTaskUsername" class="multiSel"></p>  
    </a>
    </dt>

  <dd id="ddTaskModal">
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
	                            
	                             <a onclick="hideTaskModal()" href="#" class="btn primary">Close</a>
	                            <a href="#" id="makeTaskBtn" class="btn btn-primary">Save</a>
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

