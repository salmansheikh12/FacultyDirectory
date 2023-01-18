package cs3220.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs3220.model.Department;
import cs3220.model.Faculty;

@WebServlet(urlPatterns = "/DisplayFaculty", loadOnStartup = 1)
public class DisplayFaculty extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public DisplayFaculty() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection c = null;
		try {
			String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu65";
			String username = "cs3220stu65";
			String password = "Nw7CQ2XTj6Ob";

			c = DriverManager.getConnection(url, username, password);
			Statement stmt = c.createStatement();
			Statement stmt2 = c.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Departments");

			// Lists
			List<Department> departments = new ArrayList<Department>();

			while (rs.next()) {

				ResultSet rs2 = stmt2.executeQuery("select * from Faculty");

				List<Faculty> faculty = new ArrayList<Faculty>();

				Department entry = new Department();
				entry.setName(rs.getString("name"));
				entry.setId(rs.getInt("id"));
				departments.add(entry);

				while (rs2.next()) {
					if (rs.getInt("id") == (rs2.getInt("departmentsID"))) {
						Faculty entry2 = new Faculty();
						entry2.setChair(rs2.getBoolean("Department_Chair"));
						entry2.setName(rs2.getString("faculty"));
						faculty.add(entry2);

					}
				}

				entry.setFaculty(faculty); // add the professors into the faculty list.

			} // end of while

			request.getSession().setAttribute("departments", departments); // session scope
		

		}

		catch (SQLException e) {
			throw new ServletException(e);
		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				throw new ServletException(e);
			}
		}

		request.getRequestDispatcher("/WEB-INF/DisplayFaculty.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
