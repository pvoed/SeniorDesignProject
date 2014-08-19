package ycp.edu.seniordesign.webapp.admin2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminHomeServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		// Must be logged in as an admin to view this page
		if (req.getSession().getAttribute("admin") == null){
			getServletContext().getRequestDispatcher("/view/admin/login.jsp").forward(req, resp);
		} else {
			req.setAttribute("admin", req.getSession().getAttribute("admin"));
			req.getRequestDispatcher("/view/admin/home.jsp").forward(req, resp);
		}	
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
	
	}
}
