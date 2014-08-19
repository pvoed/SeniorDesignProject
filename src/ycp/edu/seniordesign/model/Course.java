package ycp.edu.seniordesign.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Course {
	int id;
	String name;
	int professorId;
	String time;
	int courseNumber;
	int sectionNumber;
	int credits;
	String days;
	String location;
	int CRN;
	String description;
	
	public Course() {
		
	}
	
	public Course(int id, String name, int professorId, String time, int courseNumber, int sectionNumber, int credits, String days, String location, int CRN, String description) {
		this.id = id;
		this.name = name;
		this.professorId = professorId;
		this.time = time;
		this.courseNumber = courseNumber;
		this.sectionNumber = sectionNumber;
		this.credits = credits;
		this.days = days;
		this.location = location;
		this.CRN = CRN;
		this.description = description;
	}	
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getProfessorId(){
		return professorId;
	}
	
	public void setProfessorId(int professorId){
		this.professorId = professorId;
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
	
	public int getSectionNumber() {
		return sectionNumber;
	}
	
	public void setSectionNumber(int sectionNumber) {
		this.sectionNumber = sectionNumber;
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
	
	public void setCRN(int cRN) {
		CRN = cRN;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * This method can be used to load the fields of a course from a resultSet to a Course object
	 * @param resultSet the resultSet to load the fields from
	 * @throws SQLException
	 */
	public void loadFrom(ResultSet resultSet) throws SQLException {
		int index = 1;
		setId(resultSet.getInt(index++));
		setName(resultSet.getString(index++));
		setProfessorId(resultSet.getInt(index++));
		setTime(resultSet.getString(index++));
		setCourseNumber(resultSet.getInt(index++));
		setSectionNumber(resultSet.getInt(index++));
		setCredits(resultSet.getInt(index++));
		setDays(resultSet.getString(index++));
		setLocation(resultSet.getString(index++));
		setCRN(resultSet.getInt(index++));
		setDescription(resultSet.getString(index++));
	}
	
	//TODO: storeTo method
	/**
	 * This method can be used to store the fields from a course object to a prepared statement
	 * @param statement the PreparedStatement to store the fields to
	 * @throws SQLException
	 */
	public void storeTo(PreparedStatement statement) throws SQLException {
		int index = 1;
		statement.setInt(index++, id);
		statement.setString(index++, name);
		statement.setInt(index++, professorId);
		statement.setString(index++, time);
		statement.setInt(index++, courseNumber);
		statement.setInt(index++, sectionNumber);
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
		Course other = (Course) obj;
		return id == other.id
			&& name.equals(other.name)
			&& professorId == other.professorId
			&& time.equals(other.time)
			&& courseNumber == other.courseNumber
			&& sectionNumber == other.sectionNumber
			&& credits == other.credits
			&& days.equals(other.days)
			&& location.equals(other.location)
			&& CRN == other.CRN
			&& description.equals(other.description);
	}
}
