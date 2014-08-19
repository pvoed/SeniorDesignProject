package ycp.edu.seniordesign.webapp;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ycp.edu.seniordesign.controller.ChangeUserTypeController;
import ycp.edu.seniordesign.model.ChangeUserTypeRequest;

public class ChangeUserTypeServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		// User must be logged in to access this page
		if (req.getSession().getAttribute("user") == null){
			req.getRequestDispatcher("/view/login.jsp").forward(req, resp);
		} else {
			req.getRequestDispatcher("/view/changeUserType.jsp").forward(req, resp);
		}
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		// User must be logged in to access this page
		if (req.getSession().getAttribute("user") == null){
			req.getRequestDispatcher("/view/login.jsp").forward(req, resp);
		} else {
			ChangeUserTypeController controller = new ChangeUserTypeController();
			
			if (req.getParameter("changeUserTypeButton") != null){
				try {
					 ChangeUserTypeRequest changeUserTypeRequest = controller.setChangeUserTypeRequest(req);
					 if (changeUserTypeRequest == null){
						 req.setAttribute("errorMessage", "The information you input does not match the information associated with this account.");
					 } else {
						 controller.addChangeUserTypeRequest(changeUserTypeRequest);
						 req.setAttribute("updateMessage", "Your request has been submitted. Please give us 48 hours to review and approve your request.");
					 }
				} catch (SQLException e) {
					e.printStackTrace();
					req.setAttribute("errorMessage", "Failed to add change user type request");
				}
			}
			req.getRequestDispatcher("/view/changeUserType.jsp").forward(req, resp);
		}
	}
}


