<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="Style/Login.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
</head>

<body>

  <jsp:include page="Header.jsp"></jsp:include> 
       
       <div id="content">
          
          <c:if test="${not empty error}">
             <div id="error_div"><h6 id="err_message">${error}</h6></div>                 
          </c:if>
                   
          <div id="logindiv">
           <p id="loginmessage"> Login</p>
           <div id="topline"> </div>
           <form method="get" action="Login">
                 <table border=0 style="color:#595959; margin: 1.6em auto; font: 1em Arial, Helvetica, sans-serif;">
                 <tr><td><p>Username:</p></td><td><input type="text" class="Inputs" name="uname" maxlength="20"></td></tr>
                 <tr><td><p>Password:</p></td><td><input type="password" class="Inputs" name="pass" maxlength="15"></td></tr>
                 <tr><td></td><td><input type="submit" id="submitbutton" value="Login"/></td></tr>
                 </table>
           </form>
           
                     
           <a href="" id="loginlabel">Forgotten Your Username or Password?</a>
           
           <div id="bottomline"> </div>
           </div>
           
                      
       </div> <!-- End of content-->
       
       <div id= "footer">
       <p id="footmessage">&#169;Copyright 2016 Kiddo, All rights reserved.</p>
       </div>
        
</body>
</html>