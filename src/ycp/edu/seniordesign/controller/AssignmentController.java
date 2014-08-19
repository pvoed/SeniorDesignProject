package ycp.edu.seniordesign.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import ycp.edu.seniordesign.model.GradeWeight;
import ycp.edu.seniordesign.model.persist.Database;

public class AssignmentController 
{
	public void CreateAssignment(int userID, int courseID, String name, Date date, int possible, int weight_id) throws SQLException
	{
		Database.getInstance().createAssignment(userID, courseID, name, date, possible, weight_id);
	}
	
	public ArrayList<GradeWeight> getWeights(int courseID) throws SQLException
	{
		return Database.getInstance().getGradesforCourse(courseID);
	}
	
	public int getWeightfromName(String name, int courseID) throws SQLException
	{
		return Database.getInstance().getWeightfromName(name, courseID);
	}
}
