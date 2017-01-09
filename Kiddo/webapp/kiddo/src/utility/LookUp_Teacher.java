package utility;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LookUp_Teacher extends HttpServlet {

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
			System.out.println("email-> "+email);
			String query = "SELECT idTeacher,email,fname,lname FROM teachers WHERE (email = ? AND idSchool = ?)";
			
			System.out.println("Query is-> "+query);
			PreparedStatement st = connection.prepareStatement(query);
			
			st.setString(1, email);
			st.setString(2, schoolId);
			
			ResultSet rs = st.executeQuery();
			
			rs.next();
			
			String idTeacher = rs.getString(1);
			String email_str = rs.getString(2);
			String name = rs.getString(3);
			String lname = rs.getString(4);
			
			System.out.println("Read Teacher-> "+name +"  ------  "+ lname);
			
			String query2 = "SELECT name FROM classes WHERE (idSchool = ? AND idTeacher = ?)";
			
			
			PreparedStatement st2 = connection.prepareStatement(query2);
			st2.setString(1,schoolId);
			st2.setString(2,idTeacher);
						
			ResultSet rs2 = st2.executeQuery();
			String tclass=null;
			rs2.next();
			tclass = (String) rs2.getString(1);
			
			
			req.setAttribute("email", email_str);
			req.setAttribute("name", name);
			req.setAttribute("lname", lname);
			req.setAttribute("tclass", tclass);
			req.getRequestDispatcher("Teacher_LookUp.jsp").forward(req, resp);
			
			System.out.println("Read Classes-> "+tclass);
					
			}catch(Exception e){
				e.printStackTrace();
				req.setAttribute("error","There Is No Such an Email in Our Records. Please Try again.");
				req.getRequestDispatcher("Teacher_LookUp.jsp").forward(req, resp);	
				
			}
						
	}
		
}
