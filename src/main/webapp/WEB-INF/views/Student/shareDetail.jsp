<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
 <meta name="viewport" content="width=device-width, initial-scale=1.0">
 <!-- Bootstrap -->
 <script  src="../resources/js/go.js"></script>

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
            fromLinkable: false,
            fromLinkableSelfNode: false,
            fromLinkableDuplicates: false,
            toLinkable: false,
            toLinkableSelfNode: false,
            toLinkableDuplicates: false,
            cursor: "pointer"
          }),
        $(go.TextBlock,
          {
            font: "bold 11pt helvetica, bold arial, sans-serif",
            editable: false  // editing the text automatically updates the model data
          },
          new go.Binding("text", "text").makeTwoWay())
      );

    // unlike the normal selection Adornment, this one includes a Button
    myDiagram.nodeTemplate.selectionAdornmentTemplate =
      $(go.Adornment, "Spot",
        $(go.Panel, "Auto",
          $(go.Shape, { fill: null, stroke: "blue", strokeWidth: 2 }),
          $(go.Placeholder)  // this represents the selected Node
        )
      ); // end Adornment

    // clicking the button inserts a new node to the right of the selected node,
    // and adds a link to that new node
  

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

    // read in the JSON-format data from the "mySavedModel" element
   
   	
    loadajax();
    
	
  }

  
</script>
<script>
function loadajax(){
	$.ajax({
			 
		  type: 'GET',
		  url: '../getcm/'+'${loadCmId}',
		  success: function(data) {					  		 			  
			  myDiagram.model = go.Model.fromJson(data);
		  }
	 });
};
</script>
</head>
<body onload="init()">     
 <script src="../resources/jQuery/jquery-2.1.1.min.js"></script>  
          <!-- Section -->
			<div id="sample">
			  	<div id="myDiagram" style="background-color: whitesmoke; border: solid 1px black; width: 100%; height: 570px"></div>
			</div>		
          <!-- /Section -->

  </body>
  </html>