package ycp.edu.seniordesign.webapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ycp.edu.seniordesign.controller.LoginController;
import ycp.edu.seniordesign.model.User;

public class LoginServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.getSession().invalidate();
		req.getRequestDispatcher("/view/login.jsp").forward(req, resp);
		//resp.sendRedirect("/Whiteboard/login");
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String username = req.getParameter("usernameBox");
		String password = req.getParameter("passwordBox");
		
		LoginController controller = new LoginController();
		
		String errorMessage = null;		
				
		if(req.getParameter("loginButton") != null)
		{
			try
			{
				User result = controller.login(username, password);
				
				if(result == null)
				{ 
					errorMessage = "Login failed";
					req.setAttribute("errorMessage", errorMessage);	
					req.getRequestDispatcher("/view/login.jsp").forward(req, resp);
				}
				else
				{
					errorMessage = "Login successful";
					req.getSession().setAttribute("user", result);
					
					controller.setModel(result);
					
					req.getSession().setAttribute("isProfessor", controller.getModel().isProfessor());
					req.getSession().setAttribute("isStudent", controller.getModel().isStudent());
					
					req.getSession().setAttribute("enrolledCourses", controller.getEnrolledCourses());
					req.getSession().setAttribute("taughtCourses", controller.getCourses());
					
					System.out.println(result.getUsername());
					
					req.setAttribute("errorMessage", errorMessage);	
					
					// Create a session attribute storing upcoming assignments and the course they are due in
					req.getSession().setAttribute("upcomingAssignments", controller.getUpcomingAssignments(result.getId()));
					req.getRequestDispatcher("/view/homePage.jsp").forward(req,resp);
				}
			}
			catch(Exception e)
			{
				System.out.println("Login database fail");
				e.printStackTrace();
			}
		}
		
		else if(req.getParameter("registerButton") != null)
		{
			System.out.println("Registering a new account");
			req.getRequestDispatcher("/view/createAccount.jsp").forward(req, resp);
		}
		
		else if(req.getParameter("recoverPassButton") != null)
		{
			System.out.println("Recovering Password");
			req.getRequestDispatcher("/view/recoverPass.jsp").forward(req, resp);
		}
	}
}
