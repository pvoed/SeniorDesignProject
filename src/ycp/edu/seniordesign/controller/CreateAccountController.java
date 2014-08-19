package ycp.edu.seniordesign.controller;

import java.sql.SQLException;
import java.util.Random;

import ycp.edu.seniordesign.model.Registration;
import ycp.edu.seniordesign.model.User;
import ycp.edu.seniordesign.model.persist.PersistenceException;
import ycp.edu.seniordesign.model.persist.Database;

public class CreateAccountController 
{
	private User user;
	private Registration reg;

	public void setModel(User model)
	{
		this.user = model;
	}
	public void setReg(Registration reg){
		this.reg = reg;
	}
	public User getModel(){
		return this.user;
	}
	public Registration getReg(){
		return this.reg;
	}
	

	public Boolean createAccount(String username, String password, String email) throws SQLException, PersistenceException
	{
		System.out.println("Attempts to create an account");
		//public int createAccount(String username, String name, String password, String emailAddress, int type)
		int i = Database.getInstance().createAccount(username,"name", password, email, user.STUDENT_PROFILE);
		if (i>= 0){
			return true;
		}
		return false;
	}
	public String generateRandomString(){

		// Generate a random password for the user (8 characters long)
		final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final Random rnd = new Random();

		StringBuilder sb = new StringBuilder(8);
		for( int i = 0; i < 8; i++ ) {
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		}
		return sb.toString();

	}
	public Boolean addRegistration(String username, String email, String random)throws SQLException{
		//if the current user does not exist in our actual database user tables already
		//add the user with the registration code to the inactive users table
		if (Database.getInstance().getUserByUsername(user.getUsername()) == null){
			int i = Database.getInstance().addRegistration(username, email, random);
			if (i>=0){
			return true;
			}
		}
		return false;

	}
	public Boolean removeaddRegistration(String url)throws Exception{
		if ((this.reg = Database.getInstance().getRegistrationCode(url))!= null){
			if((this.reg = Database.getInstance().removeRegistration(url))!= null){
				return true;
			}
		}
		return false;
	}
	public Boolean getRegistrationByUrl(String url)throws SQLException{
		if((this.reg = Database.getInstance().getRegistrationCode(url))!=null){
			return true;
		}

		return false;
	}
}
