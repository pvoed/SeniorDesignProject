package ycp.edu.seniordesign.model;

import java.sql.SQLException;
import java.util.ArrayList;

import ycp.edu.seniordesign.model.persist.Database;

public class ComputeGrade {
	private Course course;
	private User student;
	private double score;
	
	public ComputeGrade(){
		
	}
	
	public ComputeGrade(Course course, User student){
		this.course = course;
		this.student = student;
	}
	
	public Course getCourse() {
		return course;
	}
	
	public void setCourse(Course course) {
		this.course = course;
	}
	
	public User getStudent() {
		return student;
	}
	
	public void setStudent(User student) {
		this.student = student;
	}
	
	public double getScore() {
		return score;
	}
	
	public void setScore(double score) {
		this.score = score;
	}
	/**
	 * This methods calculates the average grade for all a course using all the assignments for that course
	 * @throws SQLException
	 */
	public void computeScore() throws SQLException{
		ArrayList<Assignment> assignments = Database.getInstance().getAssignmentsForCourse(course.getId(), student.getId());
		
		double currentScore = 0.0;		
		
		for (Assignment assignment : assignments){
			// retrieve the grade weight for this assignment
			int gradeWeight = Database.getInstance().getGradeWeightForAssignment(assignment.getGradeWeightType());
			
			// determine the number of assignments in this category
			int count = 0;
			for(Assignment assignment2 : assignments){
				if (assignment.getGradeWeightType() == assignment2.getGradeWeightType() && assignment2.getEarnedPoints() != -1){
					count++;
				}
			}
			
			// Add this assignments grade to the running total grade for the course
			if(assignment.getEarnedPoints() != -1)
			{
				System.out.println("Using a score of " + assignment.getEarnedPoints() + "/" + assignment.getPossiblePoints() + " with a grade weight of " + gradeWeight);
				currentScore = currentScore + (((double) assignment.getEarnedPoints()/assignment.getPossiblePoints()) * gradeWeight)/count;
			}
		}
				
		score = currentScore;
		System.out.println("Score: " + score);
	}
	
	/**
	 * This method calculates the average grade for a course based only on the assignments for that course
	 * @param assignments
	 * @throws SQLException
	 */
	public void computeScoreForAssignments(ArrayList<Assignment> assignments) throws SQLException{
		double currentScore = 0.0;		
		
		for (Assignment assignment : assignments){
			// retrieve the grade weight for this assignment
			int gradeWeight = Database.getInstance().getGradeWeightForAssignment(assignment.getGradeWeightType());
			
			// determine the number of assignments in this category
			int count = 0;
			for(Assignment assignment2 : assignments){
				if (assignment.getGradeWeightType() == assignment2.getGradeWeightType() && assignment.getEarnedPoints() != -1){
					count++;
				}
			}
			if(assignment.getEarnedPoints() != -1)
			{
				// Add this assignments grade to the running total grade for the course
				System.out.println("Using a score of " + assignment.getEarnedPoints() + "/" + assignment.getPossiblePoints() + " with a grade weight of " + gradeWeight);
				currentScore = currentScore + (((double) assignment.getEarnedPoints()/assignment.getPossiblePoints()) * gradeWeight)/count;
			}
		}
				
		score = currentScore;
		System.out.println("Score: " + score);
	}
	
	public void computePercentNoGW(ArrayList<Assignment> assignments)
	{
		double totalPoints = 0.0;
		double maxPoints = 0.0;
		for(Assignment assign : assignments)
		{
			if(assign.getEarnedPoints() != -1)
			{
				totalPoints += assign.getEarnedPoints();
				maxPoints += assign.getPossiblePoints();
			}
		}
		
		if(maxPoints == 0.0)
		{
			score = 0.0;
		}
		else
		{
			score = totalPoints/maxPoints;
			score *= 100;
		}
	}
}
