package utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
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

public class Register_Teach_Serv extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	Connection connection;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		try{
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/kiddodb","root","551360");
			String schoolId = (String)req.getSession().getAttribute("schoolId") ; //Integer.getInteger((String)req.getSession().getAttribute("schoolId"));
			String email = req.getParameter("email");
			String pass = req.getParameter("password");	
			String name = req.getParameter("firstname");
			String lname = req.getParameter("lastname");	
			
			String query = "INSERT INTO teachers(idSchool,email,uname,pass,fname,lname) VALUES (?,?,'kiddo1',?,?,?)";
			PreparedStatement st = connection.prepareStatement(query);
			
			st.setString(1, schoolId);
			st.setString(2, email);
			st.setString(3, pass);
			st.setString(4, name);
			st.setString(5, lname);
			
			st.executeLargeUpdate();
			
			req.setAttribute("name",name);
			req.setAttribute("lname",lname);
			req.setAttribute("pass",pass);
			req.setAttribute("email",email);
			
			
			req.getRequestDispatcher("Teacher_SignUp_Res.jsp").forward(req, resp);
			
			
		}catch(Exception e){ 
            System.out.println(e);
			req.setAttribute("error","There  Was an Error in The System.  Please Try Again and Make Sure all Provided Data is Correct.");
			req.getRequestDispatcher("Teacher_SignUp.jsp").forward(req, resp);
		}
		
	}
	
	
	
	

}
