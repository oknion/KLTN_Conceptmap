<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
 <head>
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <!-- Bootstrap -->	
                <link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">                     
                <script type="text/javascript" src="resources/jQuery/jquery-2.1.1.min.js"></script>
                <script type="text/javascript"  src="resources/jquery.mobile-1.4.4.min.js"></script>
                
   <style>
   .img-nav{
   	heigh:30px;
   	width:30px;
   }
   </style>
 </head>
<body>
      <script src="resources/bootstrap/js/bootstrap.min.js"></script>
  <!-- Nav-->
    <div class="navbar navbar-default">
      <!--Nav Header-->
       <div class="navbar-header">
             <img src="resources/images/map.png" alt="Concept Map" width="50" height="50"/>
                  <a class="navbar-brand" href="#" >Concept Map</a>
                </div>
              <!--Nav Header-->

              <!--Nav Left-->
                <ul class="nav navbar-nav navbar-left">
                  <li>
                    <div class="btn-group" >
                      <a href="create" class="btn btn-default" >New  <img class = "img-nav" src="resources/images/create.png" alt=""></a>
                      <a href="listmap" class="btn btn-default">Your's Map <img  class = "img-nav" src="resources/images/listmap.png" alt=""></a>
                      <a href="listshare" class="btn btn-default">List Share<img class = "img-nav" src="resources/images/listshare.png" alt=""></a>
                      <a href="listtask" class="btn btn-default">Your's Task<img class = "img-nav" src="resources/images/listtask.png" alt=""></a>
                       <a href="fileManager?path=" class="btn btn-default">Cloud Storage<img class = "img-nav" src="resources/images/Images-Cloud.png" alt=""></a>
                      <a href="getListFriends" class="btn btn-default">Friends<img class = "img-nav" src="resources/images/friends.png" alt=""></a>
                       <a href="myplanner" class="btn btn-default">Calendar<img class = "img-nav" src="resources/images/calendar.png" alt=""></a>
                      
                    </div>
                  </li>
                </ul>
              <!--/Nav Left-->

              <!--Nav Right-->
                <ul class="nav navbar-nav navbar-right">
                  <ul class="nav nav-pills">                           
                    <!--Account Setting-->
                      <li class="dropdown" style="margin-top: 5px; margin-right: 20px">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                          <img src="resources/images/account.png" width="15" heigh="15" alt="account image">
                          Account Settings
                        </a>
                        <ul class="dropdown-menu">

                          <li><a  href="accountInfo" class="btn btn-warning">Edit Infor</a>                          </li>                    
                          <li><a  href="changePass" class="btn btn-info">Change Password</a></li>
                          <li><a  href="logout" class="btn btn-success">Logout</a></li>             
                        </ul>
                      </li>
                    <!--/Account Setting-->
                  </ul>
                </ul>
              <!--Nav Right-->
            </div>
          <!-- /Nav -->
 </body>
</html>