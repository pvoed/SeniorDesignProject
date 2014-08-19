package ycp.edu.seniordesign.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;

import ycp.edu.seniordesign.model.Assignment;
import ycp.edu.seniordesign.model.ComputeGrade;
import ycp.edu.seniordesign.model.Course;
import ycp.edu.seniordesign.model.GradeWeight;
import ycp.edu.seniordesign.model.User;
import ycp.edu.seniordesign.model.persist.PersistenceException;
import ycp.edu.seniordesign.model.persist.Database;

public class GradebookController 
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
	
	public Course getCourse(int id) throws SQLException
	{
		return Database.getInstance().getCourseById(id);
	}
	
	public ArrayList<Assignment> getStudentAssignments (int courseID, int studentID) throws SQLException
	{
		return Database.getInstance().getAssignmentsForCourse(courseID, studentID);
	}
	
	public ArrayList<Assignment> getProfessorAssignments(int id) throws SQLException
	{
		return Database.getInstance().getAssignmentsForProfessor(id);
	}
	
	public TreeMap<Integer, String> linkStudentNames(ArrayList<Assignment> assignments) throws SQLException
	{
		TreeMap<Integer, String> returnMap = new TreeMap<Integer, String>();
		
		for(Assignment a : assignments)
		{
			String name = Database.getInstance().getUserById(a.getStudentId()).getName();
			if(!returnMap.containsKey(a.getStudentId()))
			{
				System.out.println(name);
				returnMap.put(a.getStudentId(), name);
			}
		}
		
		return returnMap;
	}
	
	public ArrayList<Assignment> getAssignments(int courseID, String name) throws SQLException
	{
		return Database.getInstance().getInstancesofAssignment(courseID, name);
	}
	
	public void updateAssignment(Assignment assign) throws SQLException
	{
		Database.getInstance().updateAssignment(assign);
	}

	public double getGrade(Course c, User u) throws SQLException 
	{
		ComputeGrade returnGrade = new ComputeGrade(c, u);
		
		returnGrade.computeScore();
		
		return returnGrade.getScore();
	}

	public boolean isProfessor(int id, int courseID) throws SQLException 
	{
		return Database.getInstance().isProfessorinClass(id, courseID);
	}
	
	public boolean isStudent(int id, int courseID) throws SQLException
	{
		return Database.getInstance().isStudentinClass(id, courseID);
	}
	
	public ArrayList<Integer> getNamesForCourse(int courseID) throws SQLException
	{
		return Database.getInstance().getNamesforCourse(courseID);
	}
	
	public ArrayList<GradeWeight> getGrades(int courseID) throws SQLException
	{
		return Database.getInstance().getGradesforCourse(courseID);
	}
}