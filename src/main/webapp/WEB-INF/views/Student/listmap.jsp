<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
  <head>
                <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
                 <meta http-equiv="Content-Type" content="text/html; charset=utf-8">              
                <!--Hover image-->
                <link rel="stylesheet" href="resources/bootstrap/css/hoverstyle1.css">
                <link rel="stylesheet" href="resources/bootstrap/css/hoverstyle_common.css">               
                <link rel="stylesheet" href="resources/bootstrap/css/Oswaldfont.css">
                <link rel="stylesheet" href="resources/css/shareModalCss.css">
                <!--/Hover image -->                           
      

                <!-- ColorBox-->
                
                <link rel="stylesheet" href="resources/bootstrap/css/colorbox.css" type="text/css">
                <script type="text/javascript"  src="resources/bootstrap/js/jquery.colorbox-min.js"></script>
                
                <!-- /ColorBox-->
                <title>List Map</title>
   				
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
				        $("#friendList li").each(function(index){
				        	
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
   function deleteCM(x){
	   document.getElementById("deleteCmId").value=x;
	   $('#deleteModal').modal({show:true}); // show popup share for user to enter information
   }
   function hideShareModel(){
		 $('#shareModal').modal('hide');
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
	   
	   $(".iframe").colorbox({iframe:true, width:"100%", height:"90%"});
	   $('#ShareCmBtn').click( function (e) {
			  $('#ShareCmBtn').attr('disabled',true); // Disable ShareCmBtn
			  // Create Json to post to Controller
			  var json = "{\"cmId\":"+document.getElementById("shareCmId").value+
				  			",\"listUsername\":\"" +  $('dt p').text()+"\"}";
				 //User ajax to post json to controller 
				
				  	$.ajax({							 
					  type: 'POST',
					  url: 'sharecm',
					  dataType: 'json',				 
					  data: json,
					  contentType: 'application/json',
					  success:function(response){
						
						  hideShareModel(); // hide popup shareModal	
						  $('#ShareCmBtn').attr('disabled',false);// Enable button ShareCmBtn
						  
					  }
				 });
			
		  });	 
	   $('#DeleteCmBtn').click(function(e){
		   alert("123");
		   $('#DeleteCmBtn').attr('disabled',true);
		   $.ajax({							 
				  type: 'GET',
				  url: 'deletecm/'+  document.getElementById("deleteCmId").value,				 			 
				  success:function(data){
					   location.reload();
					   $('#DeleteCmBtn').attr('disabled',false);
					   $('#deletesModal').modal('hide');
					   location.reload(true);
			}
		});
		  
	   });
});
</script>
                

</head>
<body>
<!-- Section -->
               <div>
                        <div class="container" style="z-index: 1; width:100% ;height:auto; font-family:"Times New Roman", Times, serif;">
                          <div class="main" id="hoverid">
                           <c:forEach items="${listCM}" var="listCM" >
                           	<div class="view view-first" style=" border: 2px solid #002758; ">
                                <img src="resources/images/cat.jpg" />
                                <div class="mask">
                                  <h2>${listCM.taskName}</h2>
                                  <p>${listCM.taskDescription} ${listCM.deadLine }</p>
                                  <a   class='iframe' href="listmapDetail/${listCM.taskId}" title="Detail List">Read More</a>
                                </div>
                                   <label>${listCM.taskName}</label>
                             </div> 
                         
                           </c:forEach>  
                           <c:forEach items="${nonTaskCM}" var="nonTaskCM">
                           	<div class="view view-first" style=" border: 2px solid #a1a1a1;">
                                <img src="${nonTaskCM.imgString }" />
                                <div class="mask">
                                  <h2>${nonTaskCM.cmName}</h2>
                                  <p>${nonTaskCM.description} ${nonTaskCM.dateCreate }</p>
                                  <a  href="${nonTaskCM.cmId }"  target="_parent" class="info">Edit</a>
                		   		  <a onclick="shareCM(${nonTaskCM.cmId})" href="#" class="info">Share</a>
                 		   		  <a onclick="deleteCM(${nonTaskCM.cmId})" href="#" class="info" >Del</a>
                                </div>
                                 <label>${nonTaskCM.cmName}</label>
                             </div> 
                           </c:forEach>                              
                          </div>
                        </div>  
                  </div>
          
  <!-- /Section -->
      <!--Popup Share-->
        <div class="modal" id="shareModal">
          <div class="modal-dialog">
            <div class="modal-content">
				
              <!--Model Header-->
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal" aria-hidden="true" >x</button>
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

  <dt>
    <a href="#">
      <span class="hida">Select</span>    
      <p id="listUsername" class="multiSel"></p>  
    </a>
    </dt>

  <dd>
    <div class="mutliSelect" >
      <ul id="friendList">
      <c:forEach items="${listFriend}" var="friend" >
        <li>
          <input type="checkbox" value="${friend.desUser.userId }" />${friend.desUser.userId }</li>
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
                  <a href="#" data-dismiss="modal" class="btn btn-primary">Close</a>
                  <a href="#" id="ShareCmBtn" class="btn btn-primary">Share</a>
                </div>
              <!--/Model Footer-->
            </div>
          </div>
        </div>
      <!--/Popup Share-->		
 
 <!--Popup Delete Conceptmap-->
        <div class="modal" id="deleteModal">
          <div class="modal-dialog">
            <div class="modal-content">
				
              <!--Model Header-->
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal" aria-hidden="true" >x</button>
                  <h4 class="modal-title" style="color: white;">Confirm !</h4>
                  
                </div>
              <!--/Model Header-->

              <!--Model Body-->
                <div class="modal-body">
                  <form class="form-horizontal">
                  		  <input type="hidden" id="deleteCmId">           
						<label>Are you sure? </label>
						
                  </form>
                </div>
              <!--/Model Body-->

              <!--Model Footer-->
                <div class="modal-footer">
                  <a href="#" data-dismiss="modal" class="btn btn-primary">No</a>
                  <a href="#" id="DeleteCmBtn" class="btn btn-primary">Yes</a>
                </div>
              <!--/Model Footer-->
            </div>
          </div>
        </div>
      <!--/Popup Delete Conceptmap-->	                        

 </body>
</html>