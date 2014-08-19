package ycp.edu.seniordesign.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	
	/**
	 * Profile type for students
	 */
	public static final int STUDENT_PROFILE = 1;
	
	/**
	 * Profile type for professors
	 */
	public static final int PROFESSOR_PROFILE = 2;
	/**
	 * Profile type for professor and students
	 */
	public static final int PROFESSOR_STUDENT_PROFILE = 3;
	
	private int id;
	private String username;
	private String name;
	private String emailAddress;
	private String password; // this password is encrypted
	private String salt;
	private int type;
	private String major;
	private boolean commuter;
	private String phoneNumber;
	private String officeNumber;
	private String biography;
	
	public User(){
		
	}
	

	public User(int id, String username, String name, String password, String salt, String emailAddress, int type, String major, boolean commuter, String phoneNumber, String officeNumber, String biography){
		this.id = id;
		this.username = username;
		this.name = name;
		this.emailAddress = emailAddress;
		this.password = password;
		this.salt = salt;
		this.type = type;
		this.major = major;
		this.commuter = commuter;
		this.phoneNumber = phoneNumber;
		this.officeNumber = officeNumber;
		this.biography = biography;
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
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getSalt(){
		return salt;
	}
	
	public void setSalt(String salt){
		this.salt = salt;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}
	
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getOfficeNumber() {
		return officeNumber;
	}
	
	public void setOfficeNumber(String officeNumber) {
		this.officeNumber = officeNumber;
	}
	
	public String getBiography() {
		return biography;
	}
	
	public void setBiography(String biography) {
		this.biography = biography;
	}
	
	public boolean isProfessor(){
		if (type == PROFESSOR_PROFILE || type == PROFESSOR_STUDENT_PROFILE){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isStudent()
	{
		if (type == STUDENT_PROFILE || type == PROFESSOR_STUDENT_PROFILE)
		{
			return true;
		}
		return false;
	}
	
	public boolean isCommuter() {
		return commuter;
	}

	public void setCommuter(boolean commuter) {
		this.commuter = commuter;
	}
	
	/**
	 * This method can be used to load the fields of a user from a resultSet to a User object
	 * @param resultSet the resultSet to load the fields from
	 * @throws SQLException
	 */
	public void loadFrom(ResultSet resultSet) throws SQLException {
		int index = 1;
		setId(resultSet.getInt(index++));
		setUsername(resultSet.getString(index++));
		setName(resultSet.getString(index++));
		setPassword(resultSet.getString(index++));
		setSalt(resultSet.getString(index++));
		setEmailAddress(resultSet.getString(index++));
		setType(resultSet.getInt(index++));
		setMajor(resultSet.getString(index++));
		setCommuter(resultSet.getBoolean(index++));
		setPhoneNumber(resultSet.getString(index++));
		setOfficeNumber(resultSet.getString(index++));
		setBiography(resultSet.getString(index++));
	}
	
	/**
	 * This method can be used to store the fields from a user object to a prepared statement
	 * @param statement the PreparedStatement to store the fields to
	 * @throws SQLException
	 */
	
	public void storeTo(PreparedStatement statement) throws SQLException {
		int index = 1;
		statement.setInt(index++, id);
		statement.setString(index++, username);
		statement.setString(index++, name);
		statement.setString(index++, password);
		statement.setString(index++, salt);
		statement.setString(index++, emailAddress);
		statement.setInt(index++, type);
		statement.setString(index++, major);
		statement.setBoolean(index++, commuter);
		statement.setString(index++, phoneNumber);
		statement.setString(index++, officeNumber);
		statement.setString(index++, biography);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		User other = (User) obj;
		return id == other.id
			&& username.equals(other.username)
			&& name.equals(other.name)
			&& password.equals(other.password)
			&& salt.equals(other.salt)
			&& emailAddress.equals(other.emailAddress)
			&& type == other.type
			&& major.equals(other.major)
			&& commuter == other.commuter
			&& phoneNumber == other.phoneNumber
			&& officeNumber == other.officeNumber
			&& biography == other.biography;
	}

}
