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

public class ListTeachers extends HttpServlet {

	private static final long serialVersionUID = 1L;

	Connection connection;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {

    		Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = DriverManager.getConnection(
					"jdbc:mysql://localhost/kiddodb", "root", "551360");
			
			String schoolId = (String) req.getSession()
					.getAttribute("schoolId"); // Integer.getInteger((String)req.getSession().getAttribute("schoolId"));

			String query = "SELECT idTeacher,email,fname,lname,uname FROM teachers";

			PreparedStatement st = connection.prepareStatement(query);

			ResultSet rs = st.executeQuery();
			List<Teacher> teachers = new ArrayList<Teacher>();
			
		    while (rs.next() ){
				Teacher t = new Teacher(rs.getString(1),schoolId,rs.getString(3),rs.getString(4), rs.getString(5),rs.getString(2));
				teachers.add(t);
			}
			
			    
		    req.setAttribute("schoolId", schoolId);
			req.setAttribute("teachers", teachers);
			
			req.getRequestDispatcher("TeacherList.jsp").forward(req, resp);
			
		 } catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("error","There Is No Such an Email in Our Records. Please Try again.");
			req.getRequestDispatcher("TeacherList.jsp").forward(req, resp);

		}
  	  }
		
}
	
	