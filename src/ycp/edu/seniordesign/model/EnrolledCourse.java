package ycp.edu.seniordesign.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnrolledCourse {
	private int id;
	private int studentId;
	private int professorId;
	private int courseId;
	private int grade;
	
	public EnrolledCourse(){
		
	}
	
	public EnrolledCourse(int id, int studentId, int professorId,
			int courseId, int grade) {
		super();
		this.id = id;
		this.studentId = studentId;
		this.professorId = professorId;
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

	public int getProfessorId() {
		return professorId;
	}

	public void setProfessorId(int professorId) {
		this.professorId = professorId;
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
	
	/**
	 * This method can be used to load the fields of an enrolled course from a resultSet to an EnrolledCourse object
	 * @param resultSet the resultSet to load the fields from
	 * @throws SQLException
	 */
	public void loadFrom(ResultSet resultSet) throws SQLException {
		int index = 1;
		setId(resultSet.getInt(index++));
		setStudentId(resultSet.getInt(index++));
		setProfessorId(resultSet.getInt(index++));
		setCourseId(resultSet.getInt(index++));
		setGrade(resultSet.getInt(index++));
	}
	
	/**
	 * This method can be used to store the fields from a user object to a prepared statement
	 * @param statement the PreparedStatement to store the fields to
	 * @throws SQLException
	 */
	
	public void storeTo(PreparedStatement statement) throws SQLException {
		int index = 1;
		statement.setInt(index++, id);
		statement.setInt(index++, studentId);
		statement.setInt(index++, professorId);
		statement.setInt(index++, courseId);
		statement.setInt(index++, grade);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		EnrolledCourse other = (EnrolledCourse) obj;
		return id == other.id
			&& studentId == other.studentId
			&& professorId == other.professorId
			&& courseId == other.courseId
			&& grade == other.grade;
	}

}
