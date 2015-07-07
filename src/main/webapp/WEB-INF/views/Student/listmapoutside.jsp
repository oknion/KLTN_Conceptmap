<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <link href="../resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <!--Hover image-->
    <link rel="stylesheet" href="../resources/bootstrap/css/hoverstyle1.css">
    <link rel="stylesheet" href="../resources/bootstrap/css/hoverstyle_common.css">
    <link rel="stylesheet" href="../resources/bootstrap/css/Oswaldfont.css">

  <!--/Hover image -->
    <link rel="stylesheet" href="../resources/bootstrap/css/style.css" >
    <script type="text/javascript" src="../resources/jQuery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript"  src="../resources/jquery.mobile-1.4.4.min.js"></script>
    <script src="../resources/bootstrap/js/bootstrap.min.js"></script>
<!-- Share script -->
<style>
tr {
width: 100%;
display: inline-table;
}
table{
 height:1px; 
}
tbody{
  overflow: auto;
  height: 200px;
  width: 100%;
  position: absolute;
}
</style>
<script>
	function shareCM(x) {	 
		 document.getElementById("shareCmId").value=x; // set hidden field "shareCmId" = current clicked conceptmap id
		 $.ajax({
			 type: 'POST',
			  url: 'getListShareUsername',				 
			  data: 'shareCmId='+x,
			  success:function(data){
				  document.getElementById("listUsername").value=data;
				  $('#shareModal').modal({show:true}); // show popup share for user to enter information
			  }
		 });
		
	}
	function hideShareModel(){
		 $('#shareModal').modal('hide');
	}
	function scoreCm(x){
		 $.ajax({
			 
				  type: 'GET',
				  url: '../getError/'+x,
				  success: function(data) {	
					
					$('#tdbodye').empty();				
					  //alert(JSON.stringify(data));
					var i=0;
					data.forEach(function(entry) {
						if(entry.name!="score"){
						i++;
						var s="";
						var documentId=0;
						
						if(entry.documents[0]!=null)
						 {
								documentId=entry.documents[0].documentId;
						 };
  					if(documentId!=0){
  						s+=' <tr class="info"><td>'+i+'</td><td>' + entry.descrip + 	'</td><td><a href="http://'+window.location.host+'/tiles/download/' + documentId + '" target="_blank">Download</a> </td></tr>';
  					}else{
  						s+=' <tr class="info"><td>'+i+'</td><td>'+entry.descrip+'</td><td>No Document is available for this error!</td></tr>';
  					};	   	
  					
						$('#tdbodye').append(s);
				}else{
						 document.getElementById('cmScore').innerHTML =entry.descrip;
					}
						
					   
					});
					 $('#myModalScore').modal({show:true});
				  }
			 	});
		
	}
</script>   
<script>   
   	$(document).ready(function(){	   
		$('#ShareCmBtn').click( function (e) {
			$('#ShareCmBtn').attr('disabled',true); // Disable ShareCmBtn
			// Create Json to post to Controller
			var json = "{\"cmId\":"+document.getElementById("shareCmId").value+",\"checkme\":"+$("#checkmePublic").is(":checked")+
					",\"listUsername\":\"" + document.getElementById("listUsername").value+"\"}";
			//User ajax to post json to controller 
				$.ajax({							 
					type: 'POST',
					url: '../sharecm',
					dataType: 'json',				 
					data: json,
					contentType: 'application/json',
					success:function(data){
						hideShareModel(); // hide popup shareModal	
						$('#ShareCmBtn').attr('disabled',false);// Enable button ShareCmBtn
					}
				})
				
				
			 });    
   		});
</script>
</head>
<body>
      <!-- Section -->
      <section>
        <div class="container" style="z-index: 1" style="width:90%">
          <div class="main" id="hoverid">

            <c:forEach items="${listCM}" var="listCM">
                <div class="view view-first">
                   <img src="${listCM.imgString }" />
                       <div class="mask">
                           <h2>${listCM.cmName}</h2>
                           <p>${listCM.description} ${listCM.dateCreate}</p>
                  		 <!--   <a  href="../${listCM.cmId }"  target="_parent" class="info">Edit</a> 
                		   <a onclick="shareCM(${listCM.cmId})" href="#" class="info">Share</a>-->
                		   <a  href="../${listCM.cmId }"  target="_parent" class="info">Edit</a>
                		   		  
                 		   <a onclick="scoreCm(${listCM.cmId})" href="#" class="info">Score</a>
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
                  <button type="button" class="close" data-dismiss="modal" aria-hidden="true" >x</button>
                  <h4 class="modal-title" style="color: white;">Share with</h4>
                  
                </div>
              <!--/Model Header-->

              <!--Model Body-->
                <div class="modal-body">
                  <form class="form-horizontal">

                      <!-- Show Task input Script-->
                        <script type="text/javascript">
                          $(document).ready(function(){
                          // $("#extra").css("display","true");
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
                          <label for="checkme"class="col-lg-2 control-label" style="color: white;">Public</label>
                          <div class="col-lg-8" >

                            <input type="checkbox"  id="checkmePublic" style="margin-top: 10px;"/>

                          </div>
                        </div>
                      <!--/Check box form group-->

                      <!--Show when check box is checked-->
                          <div  id="extra">
                            <!-- Select Single-->
								<div class="form-group">
                                          <label class="col-lg-5 control-label"></label>
                                          <textarea id="listUsername" style="height:100px">
                                          </textarea>                                         
                                 </div>	
                            <!-- /Select Single-->
                          </div>
                      <!--/Show when check box is checked-->

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
      <!--Popup  Score_Errors-->
              <div class="modal" id="myModalScore" >
                <div class="modal-dialog">
                  <div class="modal-content">
                    
                    <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                      <h4 class="modal-title" style="color: white">Result!</h4>
                    </div>

                  
                      <label > Score : <span style="font-size: 30px; color: green; align:center"><label  id="cmScore"></label> </span>
                      </label>
                      <table class="table table-striped table-hover ">
                            <thead>
                              <tr>
                                <th>#</th>
                                <th>Error</th>
                                <th>Document</th>                                
                              </tr>
                            </thead>
                            <tbody id="tdbodye">
                             
                              
                            </tbody>
                          </table> 
                  

                    

                  </div>
                </div>
              </div>
          <!--/Popup  Score_Errors-->
      
</body>
</html>

