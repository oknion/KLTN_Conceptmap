<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >

<head>

  <meta charset="UTF-8">

  <title>Login</title>

    <link rel="stylesheet" href="resources/css/styleLogin.css" media="screen" type="text/css" />
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,700">

	<!--[if lt IE 9]>
		<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->
<style>
input[type=checkbox]
{
  -webkit-appearance:checkbox;
}
</style>
</head>

<body>

    <div id="login">
		<c:url value="/j_spring_security_check" var="security_check_action"/>  
      <c:if test="${error}">  
           <font color="red">Wrong username or password!</font><br/><br/>  
      </c:if>  
        
        <form action="${security_check_action}" method="POST" >  
          <fieldset class="clearfix">
          	
                <p><span class="fontawesome-user"></span><input type="text" name ="username" value="Username" onBlur="if(this.value == '') this.value = 'Username'" onFocus="if(this.value == 'Username') this.value = ''" required></p> <!-- JS because of IE support; better: placeholder="Username" -->
                <p><span class="fontawesome-lock"></span><input type="password" name="password" value="Password" onBlur="if(this.value == '') this.value = 'Password'" onFocus="if(this.value == 'Password') this.value = ''" required></p> <!-- JS because of IE support; better: placeholder="Password" -->
                <p><input type="checkbox" value="yes" name="_spring_security_remember_me"/>Remember me</p> 
              
                <p><input type="submit" value="Sign In"></p>
		
            </fieldset>
         
	      </form>
	      
	      	<!-- <form id="tw_signin" action="<c:url value="signin/twitter"/>" method="POST">
		  	<button type="submit">
		    <img src="<c:url value="resources/images/sign-in-with-twitter.png"/>" />
		  </button>
		</form> -->
			<form id="fb_signin" action="<c:url value="signin/facebook"/>" method="POST">
		  	<button type="submit">
		    <img src="<c:url value="resources/images/sign-in-with-facebook.png"/>" />
		 </button>
	</form>
	
      
      	<a href="register" style="color: #00ff00;" ><i> Or Register here!</i> </a>  
    </div> <!-- end login -->	
			
	
</body>
</html>

</body>

</html>