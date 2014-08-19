package ycp.edu.seniordesign.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangeUserTypeRequest {
	private int id;
	private String username;
	private String emailAddress;
	private String newUserType;
	
	
	public ChangeUserTypeRequest(){
		
	}
	
	public ChangeUserTypeRequest(int id, String username, String emailAddress, String newUserType){
		this.id = id;
		this.username = username;
		this.emailAddress = emailAddress;
		this.newUserType = newUserType;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public String getNewUserType() {
		return newUserType;
	}
	
	public void setNewUserType(String newUserType) {
		this.newUserType = newUserType;
	}
	
	/**
	 * This method can be used to load the fields of a change user type request from a resultSet to a ChangeUserTypeRequest object
	 * @param resultSet the resultSet to load the fields from
	 * @throws SQLException
	 * Note: The fields in this method must be set in the same order the columns occur in the database
	 */
	public void loadFrom(ResultSet resultSet) throws SQLException {
		int index = 1;
		setId(resultSet.getInt(index++));
		setUsername(resultSet.getString(index++));
		setEmailAddress(resultSet.getString(index++));
		setNewUserType(resultSet.getString(index++));
	}
	
	/**
	 * This method can be used to store the fields from a ChangeUserTypeRequest object to a prepared statement
	 * @param statement the PreparedStatement to store the fields to
	 * @throws SQLException
	 * Note: The fields in this method must be set in the same order the columns occur in the database
	 */
	 
	 public void storeTo(PreparedStatement statement) throws SQLException {
		int index = 1;
		statement.setInt(index++, id);
		statement.setString(index++, username);
		statement.setString(index++, emailAddress);
		statement.setString(index++, newUserType);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		ChangeUserTypeRequest other = (ChangeUserTypeRequest) obj;
		
			return id == other.id
				&& username.equals(other.username)
				&& emailAddress.equals(other.emailAddress)
				&& newUserType.equals(other.newUserType);
	}

}
