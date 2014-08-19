package ycp.edu.seniordesign.webapp.admin2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ycp.edu.seniordesign.controller.admin.AdminLoginController;
import ycp.edu.seniordesign.model.Admin;

public class AdminLoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.getSession().invalidate();
		req.getRequestDispatcher("/view/admin/login.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String username = req.getParameter("usernameBox");
		String password = req.getParameter("passwordBox");
		
		AdminLoginController controller = new AdminLoginController();
						
		if(req.getParameter("loginButton") != null){
			try{
				Admin admin = controller.login(username, password);
				
				if (admin == null){ 
					req.setAttribute("errorMessage", "Invalid username/password");
					req.getRequestDispatcher("/view//admin/login.jsp").forward(req, resp);
				} else{
					req.getSession().setAttribute("admin", admin);
					req.getRequestDispatcher("/view/admin/home.jsp").forward(req,resp);
				}
			}
			catch(Exception e){
				req.setAttribute("errorMessage", "Invalid username/password");
				e.printStackTrace();
			}
		}		
	}
}
