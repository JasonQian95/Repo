<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
               
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="Javascript/form-util.js"></script>
<script defer src="Javascript/jquery-3.0.0.min.js"></script>
<link rel="stylesheet" type="text/css" href="Style/Teacher_SignUp.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<body>
<jsp:include page="Header.jsp"></jsp:include>
       
       <div id="content">
       
          <c:if test="${not empty error}">
             <div id="error_div"><h6 id="err_message">${error}</h6></div>                 
          </c:if>
          
          <div id="signupdiv">
           <p id="signupmessage">Teacher Registration Form </p>
           <div id="topline"> </div>
           <!--  sign up form -->
            
           <form id="signupform"  onsubmit="return check_form()" method="get" action="Register_Teacher">
           
             <table>
               
               <tr><td>
                 <input type="text" id="email" class="textinput" name="email"  placeholder="Email Address"/>
                 <div class="errors" id="email_err" ><p>hello</p></div>
               </td></tr>
               
               <tr><td>
                  <input type="text" class="textinput" id="email2" name="email2" placeholder="Re-Enter Email Addresss" />
                  <div class="errors" id="email2_err" ><p>hello</p></div>
               </td></tr>
                  
               <tr><td>
                  <input type="password"  id="password" class="textinput" name="password" placeholder="Password"/>
                  <div id="pass_err" class="errors" ><p>hello</p></div>
               </td></tr>
                  
               <tr><td>
                  <input type="text" class="textinput" id="name"  name="firstname" placeholder="First Name"/>
                  <div id="name_err" class="errors" ><p>hello</p></div>
               </td></tr>
               <tr><td>
                  <input type="text" class="textinput" id="lastname"   name="lastname" placeholder="Last Name" /> 
                  <div id="lastname_err" class="errors" ><p>hello</p></div>
               </td></tr>
               
               <tr><td>   
                  <button type="submit" id="form_submit" >Register</button>
               </td></tr>
                                          
             </table> 
              
            </form>
            
             <div id="bottomline"> </div>
                <table id="nav_tab">
                 <tr><td><a href="SignUp.jsp">Click To Go To Your Dashboard</a></td></tr>
             </table>
              
           
          </div> <!-- end of signupdiv -->
       </div>
       
       <div id= "footer">
       <p id="footmessage">&#169;Copyright 2016 Kiddo, All rights reserved.</p>
       </div>       
       

 </body>
</html>