package ycp.edu.seniordesign.webapp;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ycp.edu.seniordesign.controller.AddCourseController;
import ycp.edu.seniordesign.model.PendingCourse;

public class AddCourseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		// User must be logged in to access this page
		if (req.getSession().getAttribute("user") == null){
			req.getRequestDispatcher("/view/login.jsp").forward(req, resp);
		} else {
			req.getRequestDispatcher("/view/addCourse.jsp").forward(req, resp);
		}
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		// User must be logged in to access this page
		if (req.getSession().getAttribute("user") == null){
			req.getRequestDispatcher("/view/login.jsp").forward(req, resp);
		} else {
			AddCourseController controller = new AddCourseController();
			
			if (req.getParameter("addCourseButton") != null){
				try {
					 PendingCourse pendingCourse = controller.setPendingCourse(req);
					 controller.addPendingCourse(pendingCourse);
					 req.setAttribute("updateMessage", "Your request has been submitted. Please give us 48 hours to review and approve your request.");
				} catch (SQLException e) {
					e.printStackTrace();
					req.setAttribute("errorMessage", "Failed to add pending course");
				}
			}
			req.getRequestDispatcher("/view/addCourse.jsp").forward(req, resp);
		}
	}
}
