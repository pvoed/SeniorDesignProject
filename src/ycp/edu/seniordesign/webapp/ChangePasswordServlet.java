package ycp.edu.seniordesign.webapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ycp.edu.seniordesign.model.User;
import ycp.edu.seniordesign.model.persist.Database;
import ycp.edu.seniordesign.util.HashPassword;

public class ChangePasswordServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
		
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		if(req.getSession().getAttribute("user") == null)
		{
			req.getRequestDispatcher("/view/login.jsp").forward(req, resp);
		}
		else
		{
			req.getRequestDispatcher("/view/changePassword.jsp").forward(req, resp);
		}
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		if(req.getParameter("ChangePasswordButton") != null) 
		{
			try
			{
				User user = (User) req.getSession().getAttribute("user");
				String oldPassword = req.getParameter("oldPasswordBox");
				String newPassword = req.getParameter("newPasswordBox");
				String confirmPassword = req.getParameter("confirmNewPasswordBox");
				boolean update = false;
				String errorMessage = "Password Changed Successfully";
				
				String salt = user.getSalt();
				String hashedPassword = HashPassword.computeHash(oldPassword, salt);
				
				if (hashedPassword.equals(user.getPassword())) 
				{	
					if (oldPassword.equals(newPassword))
					{
						errorMessage = "Old and new passwords are the same";
						req.setAttribute("errorMessage", errorMessage);
						req.getRequestDispatcher("/view/changePassword.jsp").forward(req, resp);
					}
					if (newPassword.equals(confirmPassword)) 
					{
						hashedPassword = HashPassword.computeHash(newPassword, salt);
						user.setPassword(hashedPassword);
						req.setAttribute("errorMessage", errorMessage);
						update = true;
					}
					else
					{
						errorMessage = "New passwords do not match";
						req.setAttribute("errorMessage", errorMessage);
						req.getRequestDispatcher("/view/changePassword.jsp").forward(req, resp);
					}
				} 
				else 
				{
					errorMessage = "Old password incorrect";
					req.setAttribute("errorMessage", errorMessage);
					req.getRequestDispatcher("/view/changePassword.jsp").forward(req, resp);
				}
				
				if (update) {
					Database.getInstance().updateUser(user);
				}
				
				System.out.println("Changing password successful");
				req.getRequestDispatcher("/view/changePassword.jsp").forward(req, resp);
			}
			catch(Exception e)
			{
				System.out.println("Changing password failed");
				e.printStackTrace();
			}
		}
	}
}
