<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
 <meta name="viewport" content="width=device-width, initial-scale=1.0">
 <!-- Bootstrap -->
 <script  src="../resources/js/go.js"></script>
 <script src="../resources/js/jquery-2.1.1.min.js"></script>
<script src="http://malsup.github.com/jquery.form.js"></script>
 <!-- Bootstrap Multiselect
   <script src="resources/bootstrap/js/bootstrap-select.min.js"></script>
   <link rel="stylesheet" href="resources/bootstrap/css/bootstrap-select.min.css"> -->
  <title>Create Page</title>
 
 <script>
 var nodeNameJson=jQuery.parseJSON('{}');
 
function showAddDocumentModel(){
	 $('#addDocumentModel').modal({show:true});
};
function hideAddDocumentModel(){
	 $('#addDocumentModel').modal('hide');
}
function showAddNodeName(){
	 $('#AddNodeName').modal({show:true});
};
function hideAddNodeName(){
	 $('#AddNodeName').modal('hide');
}
 function loadajax(){	  
	  $.ajax({
			 
		  type: 'GET',
		  url: 'getcm/'+'${loadCmId}',
		  success: function(data) {		
					 			  
			  myDiagram.model = go.Model.fromJson(data);
			  document.getElementById("editCmId").value=data.cmId;
			  document.getElementById("inputCmName").value=data.cmName;
			  document.getElementById("textAreaCmDes").value=data.description;
		  }
	 });
};

 </script>
 <script id="code">

  function init() {
    if (window.goSamples) goSamples();  // init for these samples -- you don't need to call this
    var $ = go.GraphObject.make;  // for conciseness in defining templates

    myDiagram =
      $(go.Diagram, "myDiagram",  // must name or refer to the DIV HTML element
        {
          // start everything in the middle of the viewport
          initialContentAlignment: go.Spot.Center,
          // have mouse wheel events zoom in and out instead of scroll up and down
          "toolManager.mouseWheelBehavior": go.ToolManager.WheelZoom,
          // support double-click in background creating a new node
          "clickCreatingTool.archetypeNodeData": { text: "New Concept" },
          // enable undo & redo
          "undoManager.isEnabled": true
        });

    // when the document is modified, add a "*" to the title and enable the "Save" button
    myDiagram.addDiagramListener("Modified", function(e) {
      var button = document.getElementById("SaveButton");
      if (button) button.disabled = !myDiagram.isModified;
      var idx = document.title.indexOf("*");
      if (myDiagram.isModified) {
        if (idx < 0) document.title += "*";
      } else {
        if (idx >= 0) document.title = document.title.substr(0, idx); 
      }
    });

    // define the Node template
    myDiagram.nodeTemplate =
      $(go.Node, "Auto",
        new go.Binding("location", "loc", go.Point.parse).makeTwoWay(go.Point.stringify),
        // define the node's outer shape, which will surround the TextBlock
        $(go.Shape, "RoundedRectangle",
          {
            parameter1: 20,  // the corner has a large radius
            fill: $(go.Brush, go.Brush.Linear, { 0: "rgb(254, 201, 0)", 1: "rgb(254, 162, 0)" }),
            stroke: "black",
            portId: "",
            fromLinkable: true,
            fromLinkableSelfNode: true,
            fromLinkableDuplicates: true,
            toLinkable: true,
            toLinkableSelfNode: true,
            toLinkableDuplicates: true,
            cursor: "pointer"
          }),
        $(go.TextBlock,
          {
            font: "bold 11pt helvetica, bold arial, sans-serif",
            editable: true  // editing the text automatically updates the model data
          },
          new go.Binding("text", "text").makeTwoWay())
      );

    // unlike the normal selection Adornment, this one includes a Button
    myDiagram.nodeTemplate.selectionAdornmentTemplate =
      $(go.Adornment, "Spot",
        $(go.Panel, "Auto",
          $(go.Shape, { fill: null, stroke: "blue", strokeWidth: 2 }),
          $(go.Placeholder)  // this represents the selected Node
        ),
        // the button to create a "next" node, at the top-right corner
        $("Button",
          {
            alignment: go.Spot.TopRight,
            click: addNodeAndLink  // this function is defined below
          },
          $(go.Shape, "PlusLine", { desiredSize: new go.Size(6, 6) })
        ) // end button
        ,
        $("Button",
                {
                  alignment: go.Spot.TopLeft,
                  click: addNodeName  // this function is defined below
                },
                $(go.Shape, "PlusLine", { desiredSize: new go.Size(6, 6) })
              ) // end button
      ); // end Adornment

      function addNodeAndLink(e, obj) {
          var adorn = obj.part;
          e.handled = true;
     

          // get the node data for which the user clicked the button
          var fromNode = adorn.adornedPart;     
          var fromData = fromNode.data;     
          document.getElementById('documentCmId').value='${cmTempId}';
          document.getElementById('documentCcId').value=fromData.key;
          
          showAddDocumentModel();
          
        };
    // clicking the button inserts a new node to the right of the selected node,
    // and adds a link to that new node
    function addNodeName(e, obj) {
      var adorn = obj.part;
      e.handled = true;
 

      // get the node data for which the user clicked the button
      var fromNode = adorn.adornedPart;     
      var fromData = fromNode.data;          
      document.getElementById('CcIdAddNodeName').value=fromData.key;  
      document.getElementById('inputAddNodeName').value=nodeNameJson[fromData.key];
      showAddNodeName();
      
    };

    // replace the default Link template in the linkTemplateMap
    myDiagram.linkTemplate =
      $(go.Link,  // the whole link panel
        { curve: go.Link.Bezier, adjusting: go.Link.Stretch, reshapable: true },
        new go.Binding("points").makeTwoWay(),
        new go.Binding("curviness", "curviness"),
        $(go.Shape,  // the link shape
          { isPanelMain: true, stroke: "black", strokeWidth: 1.5 }),
        $(go.Shape,  // the arrowhead
          { toArrow: "standard", stroke: null }),
        $(go.Panel, "Auto",
          $(go.Shape,  // the link shape
            {
              fill: $(go.Brush, go.Brush.Radial,
                      { 0: "rgb(240, 240, 240)", 0.3: "rgb(240, 240, 240)", 1: "rgba(240, 240, 240, 0)" }),
              stroke: null
            }),
          $(go.TextBlock, "transition",  // the label
            {
              textAlign: "center",
              font: "10pt helvetica, arial, sans-serif",
              stroke: "black",
              margin: 4,
              editable: true  // editing the text automatically updates the model data
            },
            new go.Binding("text", "text").makeTwoWay())
        )
      );
   
	
    //if edit: send request and get and load conceptmap
    if('${loadCmId}'!=null||'${loadCmId}'!=""){
   		loadajax();
    };
    
    
  }

  
  
</script>
 <script>
	 
	 $(document).ready(function(){
		
		 $('#AddNodeNameSubmitClick').click(function(e){
			$('#AddNodeNameSubmitClick').attr('disabled',true);
			nodeNameJson[document.getElementById('CcIdAddNodeName').value]=document.getElementById('inputAddNodeName').value;
			$('#AddNodeNameSubmitClick').attr('disabled',false);
			hideAddNodeName();
			
		 });
		
		 
		 $("#uploadDocumentSubmitClick").click(function(e){
			 $('#uploadDocumentSubmitClick').attr('disabled',true);
			 $('#uploadDocumentForm').ajaxForm({
				 success:function(data) { 
					 hideAddDocumentModel();
					 
			     },
			     dataType:"text"
			 }).submit();
			 $('#uploadDocumentSubmitClick').attr('disabled',false);
		 });
		 $('#myDiagramImgBtn').click(function(e){
			 $("#myDiagramImg").attr("src",myDiagram.makeImageData({
				 scale:0.5,				
				 size: new go.Size(300,200),
				 maxSize: new go.Size(300,200)
			 }));
			
			 
		 });
		  $('#Submit').click( function (e) {
			  $('#Submit').attr('disabled',true);
			  var json = jQuery.parseJSON(myDiagram.model.toJson());
			  if(document.getElementById("editCmId").value!=""){
				  json["cmId"]=document.getElementById("editCmId").value;
			  }else{
				  json["cmId"]=0;
			  };
			  
			 
			  for (var key in nodeNameJson) {
				  $.each(json.nodeDataArray, function(i, item) {
					  if (nodeNameJson.hasOwnProperty(key)&&(json.nodeDataArray[i].key==parseFloat(key) )) {
						    json.nodeDataArray[i].ccName=nodeNameJson[key];
						 };
					   
					});
				 
				};
			 alert(JSON.stringify(json));
			  
			  json["cmSessionTempId"]='${cmTempId}';
			  json["cmName"]=document.getElementById("inputCmName").value;
			  json["description"]=document.getElementById("textAreaCmDes").value;
              json["imgString"]= myDiagram.makeImageData({				 
            	scale:0.5,
            	
  				size: new go.Size(300,200),
  				 maxSize: new go.Size(300,200)
 			 });		
             if('${taskInfo}'!=null){
            	 json["taskId"]='${taskInfo.taskId}';
             }else{
            	 json["taskId"]=0;
             };
             
		      $.ajax({
					 
				  type: 'POST',
				  url: 'gojspost',
				  dataType: 'json',				 
				  data: JSON.stringify(json),
				  contentType: 'application/json',
				  success: function(data) {					 
					 $('#myModal').modal('hide');					
				  }
			 })
			 $('#Submit').attr('disabled',false);
		  });
	 });
		
	
	 
 </script>
</head>
<body onload="init()">       
	 	 
          <!-- Section -->
			<div id="sample">
			  <div id="myDiagram" style="background-color: whitesmoke; border: solid 1px black; width: 100%; height: 400px"></div>
			  <div>			  
				<a data-toggle="modal" href="#myModal" class="btn btn-warning">Save</a>				   
			  	<a data-toggle="modal" href="#myModalScore" class="btn btn-warning">Score</a>
			  	<a data-toggle="modal" href="#addDocumentModel" class="btn btn-warning">Add document</a>
			 	  <a href="#" class="btn" id="myDiagramImgBtn" >Save IMG</a>
			  </div>
			 </div>  			
			  <img id="myDiagramImg" src="" alt="some_text">
             
          <!-- /Section -->
          <!--Popup Save-->
                  <div class="modal" id="myModal">
                      <div class="modal-dialog">
                        <div class="modal-content">
                          <!--Model Header-->
                          <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title">Save</h4>
                          </div>
                          <!--/Model Header-->
                          <div class="modal-body">
                            <form class="form-horizontal"  >
                              
                                <!--Group Name-->
                                  <div class="form-group">
                                  <input type="hidden" id="editCmId"/>
                                    <label for="inputCmName" class="col-lg-2 control-label"  style="color: white">Name</label>
                                    <div class="col-lg-10">
                                      <input type="text" class="form-control" id="inputCmName" placeholder="Name">
                                    </div>
                                  </div>
                                <!--Group Name-->
                                <!--GroupDescription-->
                                  <div class="form-group">
                                    <label for="textAreaCmDes" class="col-lg-2 control-label">Description</label>
                                    <div class="col-lg-10">
                                      <textarea class="form-control" rows="3" id="textAreaCmDes"></textarea>

                                    </div>
                                  </div>
                                <!--GroupDescription-->                                   
                            </form>
                          </div>
                          <!--Model Footer-->
                           <div class="modal-footer">                         
                            <a href="#" data-dismiss="modal" class="btn">Close</a>
                            <a href="#" class="btn" id="Submit" >Save</a>
                          </div>
                          <!--/Model Footer-->
                        </div>
                      </div>
                  </div>
          <!--/Popup Save--> 
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
                            <form class="form-horizontal"  id="uploadDocumentForm" method="post" action="fileUploadController/upload" enctype="multipart/form-data">
                              
                                <!--Group Name-->
                                  <div class="form-group">
                                  <input type="hidden" class="form-control" name='documentCmId' id="documentCmId"/>
                                  <input type="hidden" class="form-control" name='documentCcId' id="documentCcId" />
                                    <label for="inputDocumentName" class="col-lg-2 control-label"  style="color: white">Name</label>
                                    <div class="col-lg-10">
                                      <input type="text" class="form-control" id="inputDocumentName" name='inputDocumentName' placeholder="Name">
                                    </div>
                                  </div>
                                <!--Group Name-->
                                <!--GroupDescription-->
                                 <div class="form-group" > 
                                                             
                                    <label for="inputDocumentFile" class="col-lg-2 control-label"  style="color: white">File</label>
                                    <div class="col-lg-10">
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
          <!--Popup AddNodeName-->
                  <div class="modal" id="AddNodeName">
                      <div class="modal-dialog">
                        <div class="modal-content">
                          <!--Model Header-->
                          <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title">Add NodeName</h4>
                          </div>
                          <!--/Model Header-->
                          <div class="modal-body">
                            <form class="form-horizontal"  id="AddNodeNameForm" method="post" action="fileUploadController/upload" enctype="multipart/form-data">
                              
                                <!--Group Name-->
                                  <div class="form-group">                                  
                                  <input type="hidden" class="form-control" name='CcIdAddNodeName' id="CcIdAddNodeName" />
                                    <label for="inputDocumentName" class="col-lg-2 control-label"  style="color: white">Name</label>
                                    <div class="col-lg-10">
                                      <input type="text" class="form-control" id="inputAddNodeName" name='inputDocumentName' placeholder="Name">
                                    </div>
                                  </div>
                                <!--Group Name-->                                                            
                            </form>
                          </div>
                          <!--Model Footer-->
                           <div class="modal-footer">                         
                            
                            <a href="#" class="btn" id="AddNodeNameSubmitClick" >Save</a>
                          </div>
                          <!--/Model Footer-->
                        </div>
                      </div>
                  </div>
          <!--/Popup AddNodeName-->         
          <!--Popup  Score_Errors-->
              <div class="modal" id="myModalScore" >
                <div class="modal-dialog">
                  <div class="modal-content">
                    
                    <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                      <h4 class="modal-title" style="color: white">Result!</h4>
                    </div>

                    <div class="modal-body" style="color: white">
                      <label > Score : <span style="font-size: 20px; color: green;"> 90</span></label>
                      <table class="table table-striped table-hover ">
                            <thead>
                              <tr>
                                <th>#</th>
                                <th>Error</th>
                                <th>Document</th>                                
                              </tr>
                            </thead>
                            <tbody>
                             
                              <tr class="info">
                                <td>3</td>
                                <td>Column content</td>
                                <td>Column content</td>
                              </tr>
                              <tr class="success">
                                <td>4</td>
                                <td>Column content</td>
                                <td>Column content</td>
                              </tr>
                              <tr class="danger">
                                <td>5</td>
                                <td>Column content</td>
                                <td>Column content</td>
                              </tr>
                              <tr class="warning">
                                <td>6</td>
                                <td>Column content</td>
                                <td>Column content</td>
                              </tr>
                              <tr class="active">
                                <td>7</td>
                                <td>Column content</td>
                                <td>Column content</td>
                              </tr>
                            </tbody>
                          </table> 
                    </div>

                     <div class="modal-footer">                   
                      <a href="#" data-dismiss="modal" class="btn btn-primary">OK!</a>
                    </div>

                  </div>
                </div>
              </div>
          <!--/Popup  Score_Errors-->

  </body>
  </html>