package ycp.edu.seniordesign.webapp.admin2;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ycp.edu.seniordesign.controller.admin.AdminChangeUserTypeController;
import ycp.edu.seniordesign.model.ChangeUserTypeRequest;

public class AdminChangeUserTypeServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		// Must be logged in as an admin to view this page
		if (req.getSession().getAttribute("admin") == null){
			getServletContext().getRequestDispatcher("/view/admin/login.jsp").forward(req, resp);
		} else {
			AdminChangeUserTypeController controller = new AdminChangeUserTypeController();
			try {
				req.setAttribute("changeUserTypeRequests", controller.getChangeUserTypeRequests());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			req.getRequestDispatcher("/view/admin/changeUserType.jsp").forward(req, resp);
		}	
		
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		// Must be logged in as an admin to view this page
		if (req.getSession().getAttribute("admin") == null){
			getServletContext().getRequestDispatcher("/view/admin/login.jsp").forward(req, resp);
		} else {	
			AdminChangeUserTypeController controller = new AdminChangeUserTypeController();
							
			String action = req.getParameter("action");
			
			if (action.equals("approve")){
				try {
					// Change the user type
					ChangeUserTypeRequest changeUserTypeRequest = controller.getChangeUserTypeRequest(req);
					if (changeUserTypeRequest == null){
						req.setAttribute("errorMessage", "Failed to the change user type.");
					} else {
						boolean result = controller.changeUserType(changeUserTypeRequest);
						if (result) {
							req.setAttribute("updateMessage", "Successfully change user type for: " + changeUserTypeRequest.getUsername());
							
							// Remove the request from the database
							controller.removeChangeUserType(req);
						} else {
							req.setAttribute("errorMessage", "Failed to change the user type for: " + changeUserTypeRequest.getUsername());
						}
					}

				} catch (SQLException e) {
					e.printStackTrace();
					req.setAttribute("errorMessage", "Failed to change the user type");
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (action.equals("reject")){
				// Remove the request from the database
				try {
					controller.removeChangeUserType(req);
					req.setAttribute("updateMessage", "Rejected user type change for: " + req.getParameter("name" + req.getParameter("requestId")));
				} catch (NumberFormatException | SQLException e) {
					e.printStackTrace();
				}
			}
			
			try {
				req.setAttribute("changeUserTypeRequests", controller.getChangeUserTypeRequests());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			req.getRequestDispatcher("/view/admin/changeUserType.jsp").forward(req, resp);
		}
	}
}
