package ycp.edu.seniordesign.webapp;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ycp.edu.seniordesign.controller.GradebookController;
import ycp.edu.seniordesign.model.Assignment;
import ycp.edu.seniordesign.model.User;
import ycp.edu.seniordesign.model.persist.Database;

public class StudentGradebookServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		if(req.getSession().getAttribute("user") == null)
		{
			req.getRequestDispatcher("/view/login.jsp").forward(req,resp);
		}
		
		else
		{
			User user = (User) req.getSession().getAttribute("user");
			
			if(user == null)
			{
				req.getRequestDispatcher("/view/login.jsp");
			}
			else
			{
				GradebookController controller = new GradebookController();
				
				controller.setModel(user);
				resp.setContentType("application/json");
				
				int courseID = -1;
				if(req.getQueryString() != null && req.getQueryString().contains("id="))
				{
					courseID = Integer.parseInt(req.getQueryString().split("=")[1]);
				}
				
				if(courseID != -1)
				{
					try 
					{
						if(controller.isStudent(user.getId(), courseID))
						{
							req.getSession().setAttribute("grades", controller.getGrades(courseID));
							req.getSession().setAttribute("course", controller.getCourse(courseID));
							req.getSession().setAttribute("assignments", controller.getStudentAssignments(courseID, user.getId()));
							
							float result = (float) controller.getGrade(controller.getCourse(courseID), user);
							result *= 100;
							result = Math.round(result);
							result /= 100;
							
							req.getSession().setAttribute("grade", result);
						
							req.getRequestDispatcher("/view/studentGradebook.jsp").forward(req, resp);
						}
						else
						{
							req.getRequestDispatcher("view/homePage.jsp").forward(req, resp);
						}
					} 
					catch (SQLException e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					req.getRequestDispatcher("/view/homePage.jsp").forward(req, resp);
				}
			}
		}
	}
}
