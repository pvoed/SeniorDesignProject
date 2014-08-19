package ycp.edu.seniordesign.webapp;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ycp.edu.seniordesign.controller.GradebookController;
import ycp.edu.seniordesign.model.Assignment;
import ycp.edu.seniordesign.model.ComputeGrade;
import ycp.edu.seniordesign.model.Course;
import ycp.edu.seniordesign.model.User;
import ycp.edu.seniordesign.model.persist.Database;

public class ProfessorGradebookServlet extends HttpServlet 
{

	/**
	 * 
	 */
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
					if(controller.isProfessor(user.getId(), courseID))
					{
						req.getSession().setAttribute("ListofGrades", controller.getGrades(courseID));
						
						ArrayList<Integer> idList = controller.getNamesForCourse(courseID);
						ArrayList<String> names = new ArrayList<String>();
						
						req.getSession().setAttribute("idList", idList);
						
						for(int i : idList)
						{
							names.add(Database.getInstance().getUserById(i).getName());
							req.getSession().setAttribute("Values" + i, controller.getStudentAssignments(courseID, i));
						}
						
						req.getSession().setAttribute("names", names);
						
						req.getSession().setAttribute("course", controller.getCourse(courseID));
						ArrayList<Assignment> temp = controller.getStudentAssignments(courseID, idList.get(0));
						ArrayList<Assignment> passList;
						ComputeGrade cg = new ComputeGrade();
						TreeMap<String, Integer> att = new TreeMap<String, Integer>();
						
						for(Assignment a : temp)
						{
							passList = controller.getAssignments(a.getCourseId(), a.getName());
							cg.computePercentNoGW(passList);
							att.put(a.getName(), (int) cg.getScore());
						}
						
						req.getSession().setAttribute("Grades", att);
						req.getSession().setAttribute("assignments", temp);
						
						req.getRequestDispatcher("/view/professorGradebook.jsp").forward(req, resp);
					}
					else
					{
						req.getRequestDispatcher("/view/homePage.jsp").forward(req, resp);
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
	
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		if(req.getParameter("updateGrades") != null)
		{
			ArrayList<Assignment> assignments = (ArrayList<Assignment>) req.getSession().getAttribute("assignments");
			int count = (int) req.getSession().getAttribute("counter");
			GradebookController controller = new GradebookController();
			
			boolean flag;
			
			for(int i = 0; i < count; i ++)
			{
				flag = false;
				Assignment assign = assignments.get(i);
				if(assign.getGradeWeightType() != Integer.parseInt(req.getParameter("Weight".concat("" + i))))
				{
					assign.setGradeWeightType(Integer.parseInt(req.getParameter("Weight".concat("" + i))));
					flag = true;
				}
				if(assign.getEarnedPoints() != Integer.parseInt(req.getParameter("Earned".concat("" + i))))
				{
					assign.setEarnedPoints(Integer.parseInt(req.getParameter("Earned".concat("" + i))));
					flag = true;	
				}
				if(assign.getPossiblePoints() != Integer.parseInt(req.getParameter("Possible".concat("" + i))))
				{
					assign.setPossiblePoints(Integer.parseInt(req.getParameter("Possible".concat("" + i))));
					flag = true;
				}
				
				if(flag)
				{
					try 
					{
						controller.updateAssignment(assign);
						req.getSession().setAttribute("errorMessage", "Update Successful");
						
						Course c = (Course) req.getSession().getAttribute("course");
						TreeMap<String, Integer> temp = new TreeMap<String, Integer>();
						ComputeGrade cg = new ComputeGrade(c, (User) req.getSession().getAttribute("user"));
						cg.computePercentNoGW(controller.getAssignments(c.getId(), assign.getName()));
						int avg = (int) cg.getScore();
						temp = (TreeMap<String, Integer>) req.getSession().getAttribute("Grades");
						temp.remove(assign.getName());
						temp.put(assign.getName(), avg);
					} 
					catch (SQLException e) 
					{
						e.printStackTrace();
					}
				}
			}
			
			req.getRequestDispatcher("/view/professorGradebook.jsp").forward(req, resp);
		}
	}
}
