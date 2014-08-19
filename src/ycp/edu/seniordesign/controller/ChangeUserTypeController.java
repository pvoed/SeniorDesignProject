package ycp.edu.seniordesign.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import ycp.edu.seniordesign.model.ChangeUserTypeRequest;
import ycp.edu.seniordesign.model.User;
import ycp.edu.seniordesign.model.persist.Database;

public class ChangeUserTypeController {
	
	public void addChangeUserTypeRequest(ChangeUserTypeRequest changeUserTypeRequest) throws SQLException {
		Database.getInstance().addChangeUserTypeRequest(changeUserTypeRequest);
	}
	
	public ChangeUserTypeRequest setChangeUserTypeRequest(HttpServletRequest req) throws SQLException {
		// Create the ChangeUserTypeRequest object
		ChangeUserTypeRequest changeUserTypeRequest = new ChangeUserTypeRequest();
		changeUserTypeRequest.setUsername(req.getParameter("name"));
		changeUserTypeRequest.setEmailAddress(req.getParameter("emailAddress"));
		changeUserTypeRequest.setNewUserType(req.getParameter("userType"));
		
		// Make sure the entered information is for the current logged in user
		User user = (User) req.getSession().getAttribute("user");
		if (!user.getName().equals(changeUserTypeRequest.getUsername()) || !user.getEmailAddress().equals(changeUserTypeRequest.getEmailAddress())){
			// Invalid credentials
			return null;
		}
		return changeUserTypeRequest;
		
	}
}
