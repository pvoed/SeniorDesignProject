package ycp.edu.seniordesign.controller.admin;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import ycp.edu.seniordesign.model.ChangeUserTypeRequest;
import ycp.edu.seniordesign.model.User;
import ycp.edu.seniordesign.model.persist.Database;

public class AdminChangeUserTypeController {

	public boolean changeUserType(ChangeUserTypeRequest changeUserTypeRequest) throws Exception {
		// Convert the string usertype value into the corresponding int
		int userTypeValue;
		if (changeUserTypeRequest.getNewUserType().equals("Student")){
			userTypeValue = User.STUDENT_PROFILE;
		} else if (changeUserTypeRequest.getNewUserType().equals("Professor")){
			userTypeValue = User.PROFESSOR_PROFILE;
		} else if (changeUserTypeRequest.getNewUserType().equals("Both")){
			userTypeValue = User.PROFESSOR_STUDENT_PROFILE;
		} else {
			throw new Exception("Invalid user type (this should not happen");
		}
		
		// get the id of the user account that needs changed
		User user = Database.getInstance().getUserByEmail(changeUserTypeRequest.getEmailAddress());
		if (user == null){
			// No user found with the given email address
			return false;
		} else if (!user.getName().equals(changeUserTypeRequest.getUsername())){		
			return false;
		}
		
		// change the user type and update it in the database
		user.setType(userTypeValue);
		Database.getInstance().changeUserType(user);
		
		return true;
	}

	public ArrayList<ChangeUserTypeRequest> getChangeUserTypeRequests() throws SQLException {
		return Database.getInstance().getChangeUserTypeRequests();
	}

	public ChangeUserTypeRequest getChangeUserTypeRequest(HttpServletRequest req) {
		// Determine which request was submitted
		String requestId = (String) req.getParameter("requestId");
		
		// Create the ChangeUserTypeRequest object
		ChangeUserTypeRequest changeUserTypeRequest = new ChangeUserTypeRequest();
		changeUserTypeRequest.setUsername(req.getParameter("name" + requestId));
		changeUserTypeRequest.setEmailAddress(req.getParameter("emailAddress" + requestId));
		changeUserTypeRequest.setNewUserType(req.getParameter("userType" + requestId));
		
		return changeUserTypeRequest;
		
	}

	public void removeChangeUserType(HttpServletRequest req) throws NumberFormatException, SQLException {
		Database.getInstance().removeChangeUserType(Integer.parseInt(req.getParameter("requestId")));
		
	}

}
