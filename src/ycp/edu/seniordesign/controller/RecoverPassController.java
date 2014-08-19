package ycp.edu.seniordesign.controller;

import java.sql.SQLException;
import java.util.Random;

import ycp.edu.seniordesign.model.User;
import ycp.edu.seniordesign.model.persist.Database;
import ycp.edu.seniordesign.util.HashPassword;

public class RecoverPassController {
	private User user;
	
	public void setModel(User model)
	{
		this.user = model;
	}
	
	public User getModel()
	{
		return user;
	}
	
	public User getUserByUsername(String username) throws SQLException{
		//if it does not exist returns user = null;
		this.user = Database.getInstance().getUserByUsername(username);
		return user;
	}
	public User getUserByEmail(String email) throws SQLException{
		//if it does not exist returns user = null;
		this.user = Database.getInstance().getUserByEmail(email);
		return user;
	}
	
	public boolean changePassword() throws Exception{
		//String salt = HashPassword.generateRandomSalt(new Random());
		//String hashed = HashPassword.computeHash(user.getPassword(), salt);
		//this.user.setPassword(hashed);
		return Database.getInstance().changePassword(this.user, this.user.getPassword());	
	}
	
	public String generateRandPass(){
		// Generate a random password for the user (8 characters long)
		final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final Random rnd = new Random();

		StringBuilder sb = new StringBuilder(8);
		for( int i = 0; i < 8; i++ ) {
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		}
		String password = sb.toString();
		this.user.setPassword(password);
		return password;
	}

}
