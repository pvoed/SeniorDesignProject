package ycp.edu.seniordesign.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import ycp.edu.seniordesign.model.Assignment;
import ycp.edu.seniordesign.model.Course;
import ycp.edu.seniordesign.model.EnrolledCourse;
import ycp.edu.seniordesign.model.User;
import ycp.edu.seniordesign.model.persist.PersistenceException;
import ycp.edu.seniordesign.model.persist.Database;

public class LoginController 
{
	private User user;
	
	public void setModel(User model)
	{
		this.user = model;
	}
	
	public User getModel()
	{
		return user;
	}
	
	public User login(String username, String password) throws SQLException, PersistenceException
	{
		return Database.getInstance().authenticateUser(username, password);
	}
	
	public ArrayList<Course> getEnrolledCourses() throws SQLException
	{
		ArrayList<EnrolledCourse> resultList = Database.getInstance().getEnrolledCoursesForStudent(user);
		
		if(resultList == null)
		{
			return null;
		}
		
		ArrayList<Course> returnList = new ArrayList<Course>();
		
		for(EnrolledCourse c : resultList)
		{
			returnList.add(Database.getInstance().getCourseById(c.getCourseId()));
		}
		
		return returnList;
	}
	
	public ArrayList<Course> getCourses() throws SQLException
	{		
		return Database.getInstance().getCoursesForProfessor(user);
	}
	
	public HashMap<Assignment, String> getUpcomingAssignments (int userId) throws SQLException{
		HashMap<Assignment, String> upcomingAssignments = Database.getInstance().getUpcomingAssignments(userId);
		
		if (upcomingAssignments == null){
			// No upcoming assignments
			return null;
		}
		
		// Sort the upcoming assignments by due date
		HashMap<Assignment, String> sortedMap = new HashMap<Assignment, String>();
		while (!upcomingAssignments.isEmpty()){
			Assignment mostRecentlyDueAssignment = null;
			for (Assignment tempAssignment : upcomingAssignments.keySet()){
				if (mostRecentlyDueAssignment == null || tempAssignment.getDueDate().before(mostRecentlyDueAssignment.getDueDate())){
					mostRecentlyDueAssignment = tempAssignment;
				}
			}
			
			sortedMap.put(mostRecentlyDueAssignment, upcomingAssignments.get(mostRecentlyDueAssignment));
			upcomingAssignments.remove(mostRecentlyDueAssignment);
		}
		
		return sortedMap;
		
	}
}
