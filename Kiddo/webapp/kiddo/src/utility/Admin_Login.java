package utility;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.catalina.Session;

public class Admin_Login extends HttpServlet {

private static final long serialVersionUID = 1L;
	
	Connection connection;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		try{
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/kiddodb","root","551360");
			
			String uname = req.getParameter("uname");
			String pass = req.getParameter("pass");			
			
			String query = "SELECT uname,pass,idSchool FROM administrators WHERE ( uname = ? AND pass = ? )";
			PreparedStatement st = connection.prepareStatement(query);
			st.setString(1, uname);
			st.setString(2, pass);
									
			ResultSet rs = st.executeQuery();
			if ( rs.next() ) {
				req.getSession().setAttribute("schoolId", rs.getString(3));
				req.getSession().setAttribute("LogedIn","isLoggedIn");
				resp.sendRedirect("SignUp.jsp");				
			}else{
				req.setAttribute("error","The Username and Password you Entered Dont Exist in Our System Please Try Again");
				req.getRequestDispatcher("Login.jsp").forward(req, resp);
			}
			
			
			
			
		}catch(Exception e){System.out.println("Some Error Happened!!!!!    "+ e);}
		
	}
	
	
}
