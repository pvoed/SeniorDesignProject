package ycp.edu.seniordesign.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PendingCourse {
	private int id;
	private String courseName;
	private String professorName;
	private String emailAddress;
	private String time;
	private int courseNumber;
	private int courseSection;
	private int credits;
	private String days;
	private String location;
	private int CRN;
	private String description;
	
	public PendingCourse(){
		
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getCourseName() {
		return courseName;
	}
	
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public String getProfessorName() {
		return professorName;
	}
	
	public void setProfessorName(String professorName) {
		this.professorName = professorName;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public int getCourseNumber() {
		return courseNumber;
	}
	
	public void setCourseNumber(int courseNumber) {
		this.courseNumber = courseNumber;
	}
	
	public int getCourseSection() {
		return courseSection;
	}
	
	public void setCourseSection(int courseSection) {
		this.courseSection = courseSection;
	}
	
	public int getCredits() {
		return credits;
	}
	
	public void setCredits(int credits) {
		this.credits = credits;
	}
	
	public String getDays() {
		return days;
	}
	
	public void setDays(String days) {
		this.days = days;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public int getCRN() {
		return CRN;
	}
	
	public void setCRN(int CRN) {
		this.CRN = CRN;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * This method can be used to load the fields of a pending course from a resultSet to a PendingCourse object
	 * @param resultSet the resultSet to load the fields from
	 * @throws SQLException
	 */
	public void loadFrom(ResultSet resultSet) throws SQLException {
		int index = 1;
		setId(resultSet.getInt(index++));
		setCourseName(resultSet.getString(index++));
		setProfessorName(resultSet.getString(index++));
		setEmailAddress(resultSet.getString(index++));
		setTime(resultSet.getString(index++));
		setCourseNumber(resultSet.getInt(index++));
		setCourseSection(resultSet.getInt(index++));
		setCredits(resultSet.getInt(index++));
		setDays(resultSet.getString(index++));
		setLocation(resultSet.getString(index++));
		setCRN(resultSet.getInt(index++));
		setDescription(resultSet.getString(index++));
	}
	
	/**
	 * This method can be used to store the fields from a user object to a prepared statement
	 * @param statement the PreparedStatement to store the fields to
	 * @throws SQLException
	 */
	
	public void storeTo(PreparedStatement statement) throws SQLException {
		int index = 1;
		statement.setInt(index++, id);
		statement.setString(index++, courseName);
		statement.setString(index++, professorName);
		statement.setString(index++, emailAddress);
		statement.setString(index++, time);
		statement.setInt(index++, courseNumber);
		statement.setInt(index++, courseSection);
		statement.setInt(index++, credits);
		statement.setString(index++, days);
		statement.setString(index++, location);
		statement.setInt(index++, CRN);		
		statement.setString(index++, description);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		PendingCourse other = (PendingCourse) obj;
		return id == other.id
			&& courseName.equals(other.courseName)
			&& professorName.equals(other.professorName)
			&& emailAddress.equals(other.emailAddress)
			&& time.equals(other.time)
			&& courseNumber == other.courseNumber
			&& courseSection == other.courseSection
			&& credits == other.credits
			&& days.equals(other.days)
			&& location.equals(other.location)
			&& CRN == other.CRN
			&& description.equals(other.description);
	}
}
