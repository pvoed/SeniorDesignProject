package ycp.edu.seniordesign.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GradeWeight {
	private int id;
	private String name;
	private int weight;
	private int courseId;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getCourseId() {
		return courseId;
	}
	
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getWeight()
	{
		return weight;
	}
	
	public void setWeight(int weight)
	{
		this.weight = weight;
	}
	/**
	 * This method can be used to load the fields of a grade from a resultSet to a Grade object
	 * @param resultSet the resultSet to load the fields from
	 * @throws SQLException
	 */
	public void loadFrom(ResultSet resultSet) throws SQLException {
		int index = 1;
		setId(resultSet.getInt(index++));
		setName(resultSet.getString(index++));
		setWeight(resultSet.getInt(index++));
		setCourseId(resultSet.getInt(index++));
	}
	
	/**
	 * This method can be used to store the fields from a grade object to a prepared statement
	 * @param statement the PreparedStatement to store the fields to
	 * @throws SQLException
	 */
	public void storeTo(PreparedStatement statement) throws SQLException {
		int index = 1;
		statement.setInt(index++, id);
		statement.setString(index++, name);
		statement.setInt(index++, weight);
		statement.setInt(index++, courseId);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		GradeWeight other = (GradeWeight) obj;
		return id == other.id
			&& name.equals(other.name)
			&& courseId == other.courseId;
	}
}
