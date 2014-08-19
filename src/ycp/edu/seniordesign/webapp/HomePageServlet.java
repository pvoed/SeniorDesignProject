package ycp.edu.seniordesign.webapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ycp.edu.seniordesign.model.persist.Database;

public class HomePageServlet extends HttpServlet 
{
private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		if(req.getSession().getAttribute("user") == null) {
			req.getRequestDispatcher("/view/login.jsp").forward(req, resp);
		}
		else {
			req.getRequestDispatcher("/view/homePage.jsp").forward(req, resp);
		}
	}
}
