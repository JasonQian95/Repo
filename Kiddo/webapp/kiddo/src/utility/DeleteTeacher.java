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

public class DeleteTeacher extends HttpServlet {
	
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
			
			String teacherId = (String) req.getParameter("idT");
			
			String query = "DELETE FROM teachers WHERE (idTeacher = ? AND idSchool=?)";

			PreparedStatement st = connection.prepareStatement(query);
			
			st.setString(1,teacherId);
			st.setString(2,schoolId);
			
			st.executeLargeUpdate();
			
			req.getRequestDispatcher("/List_Teachers").forward(req, resp);
			
		 } catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("error","There Is an Error In Our System.");
			req.getRequestDispatcher("TeacherList.jsp").forward(req, resp);

		}
  	  }

}
