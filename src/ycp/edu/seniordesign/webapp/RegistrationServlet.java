package ycp.edu.seniordesign.webapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ycp.edu.seniordesign.controller.CreateAccountController;

public class RegistrationServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.getRequestDispatcher("/view/registration.jsp").forward(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		CreateAccountController AcctController = new CreateAccountController();



		String errorMessage = null;

		boolean result = false;

		if(req.getParameter("RegisterButton") != null)
		{
			System.out.println("Enters here");
			try
			{

				//result = controller.createAccount(username, password, email);

				
				String registrationCode = req.getParameter("registrationCode");
				if(AcctController.getRegistrationByUrl(registrationCode)){
					if(AcctController.removeaddRegistration(registrationCode)){
						AcctController.createAccount(AcctController.getModel().getUsername(), "password", AcctController.getModel().getEmailAddress());
					}
				}
		
		
			}
			catch(Exception e)
			{
				System.out.println("Create Account fail");
			}
		}

		req.setAttribute("errorMessage", errorMessage);

		req.getRequestDispatcher("/view/login.jsp").forward(req,resp);
	}
}