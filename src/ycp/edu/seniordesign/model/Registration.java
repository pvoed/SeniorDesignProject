package ycp.edu.seniordesign.model;

import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Registration {
	/**
	 * In hours, the amount of time that a Registration is valid once created.
	 */
	public static final long VALID_DURATION_IN_HOURS = 48L;
	
	private int id;
	private String username;
	private String emailAddress;
	private String url;
	private Timestamp expiration;
	
	public Registration() {
		
	}
	
	public Registration(int id, String username, String emailAddress, String url, Timestamp expiration) {
		this.id =id;
		this.username = username;
		this.emailAddress = emailAddress;
		this.url = url;
		this.expiration = expiration;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Timestamp getExpiration() {
		return expiration;
	}

	public void setExpiration(Timestamp expiration) {
		this.expiration = expiration;
	}
	
	/**
	 * This method can be used to load the fields of a registration from a resultSet to a Registration object
	 * @param resultSet the resultSet to load the fields from
	 * @throws SQLException
	 */
	public void loadFrom(ResultSet resultSet) throws SQLException {
		int index = 1;
		setId(resultSet.getInt(index++));
		setUsername(resultSet.getString(index++));
		setEmailAddress(resultSet.getString(index++));
		setUrl(resultSet.getString(index++));
		setExpiration(resultSet.getTimestamp(index++));
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
		statement.setString(index++, emailAddress);
		statement.setString(index++, url);
		statement.setTimestamp(index++, expiration);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		Registration other = (Registration) obj;
		return id == other.id
			&& username.equals(other.username)
			&& emailAddress.equals(other.emailAddress)
			&& url.equals(other.url)
			&& expiration.equals(other.expiration);
	}
	
	/**
	 * Method to generate random 20 character string for verification URL.
	 * 
	 */	
	public static String generateRandomURL() {
		SecureRandom rand = new SecureRandom();
		String characters = "0123456789abcdefghijklmnopqrstuvwxyz";
		StringBuilder stringBuilder = new StringBuilder(20);
	    for (int i = 0; i < 20; i++)
	    {
	        stringBuilder.append(characters.charAt(rand.nextInt(characters.length())));
	    }
	    return stringBuilder.toString();
	}
	
}
