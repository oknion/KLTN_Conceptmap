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
            	
                <!-- ColorBox-->
                <link rel="stylesheet" href="resources/bootstrap/css/colorbox.css" type="text/css">
                <script type="text/javascript"  src="resources/bootstrap/js/jquery.colorbox-min.js"></script>
                <script>
                $(document).ready(function(){
                            //Examples of how to assign the Colorbox event to elements
                            $(".iframe").colorbox({iframe:true, width:"80%", height:"100%"});
                            $(".inline").colorbox({inline:true, width:"80%"});
                            $(".ajax").colorbox({width:"80%", height:"90%"});
                            $(".group1").colorbox({rel:'group1'});
                          });
                </script>
                <!-- /ColorBox-->
                <title>List Map</title>
</head>
<body>
<!-- Section -->
               <div>
                        <div class="container" style="z-index: 1;height:100%; width:100%" >
                          <div class="main" id="hoverid">
                            <c:forEach items="${listShare}" var="listShare">
				                <div class="view view-first">
				                   <img src="${listShare.conceptmap.imgString}" />
				                       	<div class="mask">
				                         <h2>${listShare.conceptmap.cmName} by ${listShare.conceptmap.user.userId }</h2>
				                         <p>${listShare.conceptmap.description} ${listShare.shareDate}</p>
				                         <a  class='iframe' href="listshareDetail/${listShare.conceptmap.cmId}" title="Detail List">Read More</a>
                                </div>
                              </div> 
                              </c:forEach>
                            
                          </div>
                        </div>  
                  </div>
  <!-- /Section -->
  
                    

 </body>
</html>