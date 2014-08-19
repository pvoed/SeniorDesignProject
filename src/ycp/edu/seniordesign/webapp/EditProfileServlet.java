package ycp.edu.seniordesign.webapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ycp.edu.seniordesign.model.User;
import ycp.edu.seniordesign.model.persist.Database;

public class EditProfileServlet extends HttpServlet
{
private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		if(req.getSession().getAttribute("user") == null)
		{
			req.getRequestDispatcher("/view/login.jsp").forward(req, resp);
		}
		else
		{
			req.getRequestDispatcher("/view/editProfile.jsp").forward(req, resp);
		}
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		if(req.getParameter("ChangeFieldsButton") != null)
		{
			try
			{
				String emailAddress = req.getParameter("newEmailAddressBox");
				String major = req.getParameter("newMajorBox");
				String phoneNumber = req.getParameter("newPhoneNumberBox");
				String officeNumber = req.getParameter("newOfficNumberBox");
				String biography = req.getParameter("newBiographyBox");
				User user = (User) req.getSession().getAttribute("user");
				boolean update = false;
				
				String radioButton = req.getParameter("commuterRadioButton");
				if (radioButton != null) {
					if (radioButton.equals("Commuter")) {
						user.setCommuter(true);
					}
					if (radioButton.equals("Resident")) {
						user.setCommuter(false);
					}
					update = true;
				}
				
				if (emailAddress != null && !emailAddress.equals("")) {
					//Set new email address
					user.setEmailAddress(emailAddress);
					update = true;
				}
				
				if (major != null && !major.equals("")) {
					//Set new major
					user.setMajor(major);
					update = true;
				}
				
				if (phoneNumber != null && !phoneNumber.equals("")) {
					//Set new number
					user.setPhoneNumber(phoneNumber);
					update = true;
				}
				
				if (officeNumber != null && !officeNumber.equals("")) {
					//Set new number
					user.setOfficeNumber(officeNumber);
					update = true;
				}
				
				if (biography != null && !biography.equals("")) {
					//Set new biography
					user.setBiography(biography);
					update = true;
				}

				if (update) {
					Database.getInstance().updateUser(user);
				}
				
				req.getRequestDispatcher("/view/editProfile.jsp").forward(req, resp);
			}
			catch(Exception e)
			{
				System.out.println("Changing fields failed");
				e.printStackTrace();
			}
		}
		
		else if(req.getParameter("ChangePasswordButton") != null)
		{
			System.out.println("Changing password");
			req.getRequestDispatcher("/view/changePassword.jsp").forward(req, resp);
		}	
	}
}
