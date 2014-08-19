package ycp.edu.seniordesign.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


// NOTE: THIS CLASS IN UNNECESSARY AND WILL BE DELELTED ON 10/10 UNLESS I AM CONVINCED IT IS LEGITIMATE
public class Grade {
	int id;
	int studentId;
	int courseId;
	int grade; // move to assignment
	
	public Grade() {
		
	}
	
	public Grade(int id, int studentId, int courseId, int grade) {
		this.id = id;
		this.studentId = studentId;
		this.courseId = courseId;
		this.grade = grade;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getStudentId() {
		return studentId;
	}
	
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	public int getCourseId() {
		return courseId;
	}
	
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	
	public int getGrade() {
		return grade;
	}
	
	public void setGrade(int grade) {
		this.grade = grade;
	}
	
	//TODO: loadFrom, storeTo, equals
	/**
	 * This method can be used to load the fields of a grade from a resultSet to a Grade object
	 * @param resultSet the resultSet to load the fields from
	 * @throws SQLException
	 */
	public void loadFrom(ResultSet resultSet) throws SQLException {
		int index = 1;
		setId(resultSet.getInt(index++));
		setStudentId(resultSet.getInt(index++));
		setCourseId(resultSet.getInt(index++));
		setGrade(resultSet.getInt(index++));
	}
	
	/**
	 * This method can be used to store the fields from a grade object to a prepared statement
	 * @param statement the PreparedStatement to store the fields to
	 * @throws SQLException
	 */
	public void storeTo(PreparedStatement statement) throws SQLException {
		int index = 1;
		statement.setInt(index++, id);
		statement.setInt(index++, studentId);
		statement.setInt(index++, courseId);
		statement.setInt(index++, grade);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		Grade other = (Grade) obj;
		return id == other.id
			&& studentId == other.studentId
			&& courseId == other.courseId
			&& grade == other.grade;
	}
}
