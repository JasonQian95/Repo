<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<script defer src="JavaScript/jquery-3.0.0.min.js"></script>
<script defer src="JavaScript/header.js"></script>

<link rel="stylesheet" type="text/css" href="Style/Header_large.css">

<title>Insert title here</title>
</head>

<body>

	<div id="header">
	
		<div id="logocontainer">
			
			<div id="logo">
				<img src="../Images/logo.png"> <a href="http://www.google.com"	style="display: block; height: 100%; width: 100%;"></a>
			</div>	<!--end of Logo div -->
			
	            <div id="info_board">

				<!-- <c:if test="${empty LoggedIn}"> <!-- Check to see if user is logged in -->
				
				
				<ul>
				
					<li>
						<div id="Login_Info">
				            <c:if test="${ empty LogedIn}">
							   <a href="Login.jsp" style="text-decoration: none; margin-left: .4em; border-right: 1px solid #888888;">								Login</a>
							</c:if>
							
							<c:if test="${not empty LogedIn}">
							   <a href="/LogOut" style="text-decoration: none; margin-left: .4em; border-right: 1px solid #888888;">								LogOut</a>
							</c:if>
						</div>
					</li>
				    <li><a href="https://www.google.com" style="text-decoration: none; margin-left: -.4em; border-right: 1px solid #888888;">
					     Contact Us</a></li>
					<li><a href="https://www.yahoo.com"	style="text-decoration: none; margin-top: 0; margin-left: -.4em;">
					     Need Help?</a></li>
					     					     
				</ul>	<!--</c:if> -->

			</div>			<!--end of Info_board div -->

		</div>		<!--end of logocontainer div -->
		
	
    	<div id="menu"></div>
		<!--end of menu div -->

	</div>
	<!--end of header div -->

</body>
</html>