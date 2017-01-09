<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="Style/Teacher_SignUp_Res.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<body>
       <jsp:include page="Header.jsp"></jsp:include>
       
       <div id="content">
          <div id="signupdiv">
           <p id="signupmessage">You Successfully Register The Below Info in Our System</p>
           <div id="topline"> </div>
           <table id="info_tab">
           <tr><td><p class="lables">Name:</p></td> <td><p class="info"> ${name}</p></td></tr>
           <tr><td><p class="lables">Last Name:</p></td> <td><p class="info"> ${lname}</p></td></tr>
           <tr><td><p class="lables">email:</p></td> <td><p class="info">${email}</p></td></tr>
           <tr><td><p class="lables">Passwrod:</p></td> <td><p class="info">${pass}</p></td></tr>
           </table>   
           <div id="bottomline"> </div>
           <table id="nav_tab">
             <tr><td><a href="SignUp.jsp">To Dashboard</a></td><td><a href="Teacher_SignUp.jsp">Register More Info</a> </td></tr>
           </table>
                
          </div> <!-- end of signupdiv -->
       </div>
        
       <div id= "footer">
       <p id="footmessage">&#169;Copyright 2016 Kiddo, All rights reserved.</p>
       </div>       
       

 </body>
</html>