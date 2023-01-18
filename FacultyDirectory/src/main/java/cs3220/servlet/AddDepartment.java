package cs3220.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs3220.model.Department;
import cs3220.model.Faculty;

@WebServlet("/AddDepartment")
public class AddDepartment extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public AddDepartment() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/AddDepartment.jsp").forward(request, response);

	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String name = request.getParameter("name");

		Connection c = null;
		try {
			String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu65";
			String username = "cs3220stu65";
			String password = "Nw7CQ2XTj6Ob";

			c = DriverManager.getConnection(url, username, password);
			Statement stmt = c.createStatement();
			String sql = "INSERT INTO Departments (name) VALUES ('" + name + "')";
			stmt.executeUpdate(sql);

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

		response.sendRedirect("DisplayFaculty");
	}

}
