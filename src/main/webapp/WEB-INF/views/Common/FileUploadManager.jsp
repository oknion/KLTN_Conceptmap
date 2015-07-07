<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>

<title>Insert title here</title>
<script src="resources/js/jquery-2.1.1.min.js"></script>
<script src="resources/js/jquery.form.js"></script>
<script>
function showAddDocument(){
	document.getElementById("currentPath").value='${path}';
	 $('#addDocumentModel').modal({show:true});
};
function hideAddDocument(){
	 $('#addDocumentModel').modal({show:false});
};


function showCreateNewFolder(){
	 $('#createNewFolderModal').modal({show:true});
};


function hideCreateNewFolder(){
	 $('#createNewFolderModal').modal({show:false});
	 location.reload();
};
function showDeleteModal(x,y){
	document.getElementById("fileOrfol").value=x;
	document.getElementById("path2FileorFol").value=y;
	 $('#deleteModal').modal({show:true});
};
function hideDeleteModal(){
	 $('#deleteModal').modal({show:false});
	 location.reload();
};
$(document).ready(function(){
	// Upload File
		 $("#uploadDocumentSubmitClick").click(function(e){
			 $('#uploadDocumentSubmitClick').attr('disabled',true);
			 $('#uploadDocumentForm').ajaxForm({
				 success:function(data) { 
					 hideAddDocument(); 
					 location.reload();
			     },
			     dataType:"text"
			 }).submit();
			 $('#uploadDocumentSubmitClick').attr('disabled',false);
		 });
	
	 // New Folder
	 $("#createNewFolderSubmitClick").click(function(e){
		 
		 $('#createNewFolderSubmitClick').attr('disabled',true);
		 $.ajax({
			 
			  type: 'POST',
			  url: 'fileManager/newFolder',
			  data : "path=" + '${path}' + "&folName=" +   document.getElementById("inputFolderName").value,  
		     success : function(response) {
		    	 hideCreateNewFolder();
		     }
			});
		 $('#createNewFolderSubmitClick').attr('disabled',false);
	 });
	 // Delete File or Folder
	 $('#deleteFileorFol').click(function(e){
		 
		 $('#deleteFileorFol').attr('disabled',true);
		if(document.getElementById("fileOrfol").value==1){
			 $.ajax({
				 
				
					  type: 'POST',
					  url: 'fileManager/deleFile',
					  data : "path=" + document.getElementById("path2FileorFol").value,  
				     success : function(response) {
				    	 hideDeleteModal();
				    	 $('#deleteFileorFol').attr('disabled',false);
				     }
				
				});
		
		}else{
			
			 $.ajax({
				 
					
				  type: 'POST',
				  url: 'fileManager/deleFol',
				  data : "path=" + document.getElementById("path2FileorFol").value,  
			     success : function(response) {
			    	 hideDeleteModal();
			    	 $('#deleteFileorFol').attr('disabled',false);
			     }
			
			});
		
		}
		 
	 });
});
function getBackUrl(x){
	var returnString="";
	if(x.length>0){
	var splitString= x.substr(0,x.length-1).lastIndexOf("/");
	returnString=x.substr(0,splitString+1);
	
	}
	document.getElementById("backButton").href="fileManager?path="+returnString;
}
</script>

<style>
body {
  padding: 0 2em;

  -webkit-font-smoothing: antialiased;
  text-rendering: optimizeLegibility;
  color: #444;
  background: #eee;
}
.rwd-table {
margin-top:10px;
  background: #34495E;
  color: #fff;
  border-radius: .4em;
  overflow: hidden;
  
  tr {
    border-color: lighten(#34495E, 10%);
    text-align: center;
    
  }
  
  th, td {
 	 margin-top:10px;
 	 text-align: center;
     margin: .5em 1em;
     @media (min-width: $breakpoint-alpha) { 
      padding: 1em !important; 
    }
  }
  th, td:before {
    color: #dd5;
  }
}

	.img-command{
		width:30px;
		height:30px;
		margin-left:10px;
	}
	h1 {
  font-weight: normal;
  letter-spacing: -1px;
  color: #34495E;
 text-align: center;
 align:center;
}
h5{
 font-weight: normal;
  letter-spacing: -1px;
  color: #34495E;
 text-align: left;
 align:left;
}
	
</style>
</head>
<body onload="getBackUrl('${path}')">
<h1>List of File</h1>
<a id="backButton" href='fileManager?path='><img  class = "img-command" src="resources/images/back_button.png" alt=""></a>

<img onclick="showCreateNewFolder()" class = "img-command" src="resources/images/new_folder.png" alt="">
<img onclick="showAddDocument()" class = "img-command" src="resources/images/upload_file.png" alt="">

<table class="rwd-table" style="font-size:17px">
    <tr> <th>Name
            </th>
       
           
            <th>
                Delete
            </th>
            <th>
                Download
            </th>
            
    </tr>
     <c:forEach items="${listFile}" var="file" >
     	<tr>
     	<td>${file}
     	</td>
     	<td>
     	<a onclick='showDeleteModal(1,"${path}${file}")' href="#" class="info" style="color:red">Del</a>
     	</td>
     	<td>
     	<a href="fileManager/download?path=${path}${file}" target="_blank">Download</a>
     	</td>
     	</tr>
        </c:forEach>
        <c:forEach items="${listFol}" var="fol" >
     	<tr>
     	<td><a href='fileManager?path=${path}${fol}/'>${fol}</a>
     	</td>
     	<td>
     	<a onclick='showDeleteModal(2,"${path}${fol}/")' href="#" class="info" style="color:red">Del</a>
     	</td>
     	<td>
     	</td>
     	</tr>
        </c:forEach>
</table>

   <!--Popup createNewFolderModal-->
                  <div class="modal" id="createNewFolderModal">
                      <div class="modal-dialog">
                        <div class="modal-content">
                          <!--Model Header-->
                          <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title">New Folder</h4>
                          </div>
                          <!--/Model Header-->
                          <div class="modal-body">
                            <form class="form-horizontal"  id="createNewFolderForm" method="post" action="fileUploadController/upload" enctype="multipart/form-data">
                              
                                <!--Group Name-->
                                  <div class="form-group">
                                  
                                  <label for="inputFolderName" class="col-lg-2 control-label"  style="color: white"></label>
                                    <div class="col-lg-10" id="ab">
                                     	<input type="text" class="form-control" id="inputFolderName" name='inputFolderName' placeholder="Name">
                                    </div>
                                  </div>
                                <!--/Group Name-->
                                                                  
                            </form>
                          </div>
                          <!--Model Footer-->
                           <div class="modal-footer">                         
                            
                            <a href="#" class="btn" id="createNewFolderSubmitClick" >Create</a>
                          </div>
                          <!--/Model Footer-->
                        </div>
                      </div>
                  </div>
          <!--/Popup AddDocument-->   
          <!--Popup Delete File or Folder-->
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
                  		 <input type="hidden" id="fileOrfol"/>
                          <input type="hidden" id="path2FileorFol"/>
                  		        
						<label>Are you sure? </label>
						
                  </form>
                </div>
              <!--/Model Body-->

              <!--Model Footer-->
                <div class="modal-footer">
                  <a href="#" data-dismiss="modal" class="btn">No</a>
                  <a href="#" id="deleteFileorFol" class="btn">Yes</a>
                </div>
              <!--/Model Footer-->
            </div>
          </div>
        </div>
      <!--/Popup Delete  File or Folder-->	
      <!--Popup AddDocument-->
                  <div class="modal" id="addDocumentModel">
                      <div class="modal-dialog">
                        <div class="modal-content">
                          <!--Model Header-->
                          <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title">Add Document</h4>
                          </div>
                          <!--/Model Header-->
                          <div class="modal-body">
                            <form class="form-horizontal"  id="uploadDocumentForm" method="post" action="fileManager/upload" enctype="multipart/form-data">
                              
                                <!--Group Name-->
                                  <div class="form-group">
                                 
                                  <input type="hidden" class="form-control" name="currentPath" id="currentPath" />
                                    <label for="inputDocumentName" class="col-lg-2 control-label"  style="color: white"></label>
                                    <div class="col-lg-10" id="addDocumentName">
                                     	
                                     	<input type="text" class="form-control" id="inputDocumentName" placeholder="Name">
                                    </div>
                                  </div>
                                <!--Group Name-->
                                <!--GroupDescription-->
                                 <div class="form-group" > 
                                                             
                                    <label for="inputDocumentFile" class="col-lg-2 control-label"  style="color: white">File</label>
                                    <div class="col-lg-10" id="uploadDocumentBody">
                                     	<input type="file" id="inputDocumentFile" name="file"><br />
                                    </div>
                                   
                                  </div>
                                   
                                <!--GroupDescription-->                                   
                            </form>
                          </div>
                          <!--Model Footer-->
                           <div class="modal-footer">                         
                            
                            <a href="#" class="btn" id="uploadDocumentSubmitClick" >Upload</a>
                          </div>
                          <!--/Model Footer-->
                        </div>
                      </div>
                  </div>
          <!--/Popup AddDocument-->   
</body>
</html>