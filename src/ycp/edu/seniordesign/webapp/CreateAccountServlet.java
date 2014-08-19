package ycp.edu.seniordesign.webapp;

import java.io.IOException;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ycp.edu.seniordesign.controller.CreateAccountController;

public class CreateAccountServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.getRequestDispatcher("/view/createAccount.jsp").forward(req, resp);
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

				String Euser = "whiteboardseniordesign@gmail.com";
				String Epassword = "randompassword";
				String authCode = AcctController.generateRandomString();
				//user form data
				String username = req.getParameter("usernameBox");
				String name = req.getParameter("nameBox");
				String emailTo = req.getParameter("emailBox");
				String message = name +", your random code: " + authCode+ " is for the account: " + username;
				String subjectLine = "Whiteboard: Registraion Code";
				
				//if this username is not found in our main directory than proceed to
				//add into the inactiveUsers table and store the random string 
				if(AcctController.addRegistration(username, emailTo, authCode)){

					//preparing to send the message
					Properties props = new Properties();
					//props.setProperty("mail.host", "smtp.gmail.com");
					props.put("mail.smtps.auth", "true");
					props.setProperty("mail.smtps.port", "465");
					props.setProperty("mail.smtps.auth", "true");
					props.setProperty("mail.smtps.starttls.enable", "true");

					//authenicates the gmail account
					System.out.println("Auth the SMTP server...");
					Authenticator auth = new SMTPAuthenticator(Euser, Epassword);
					Session session = Session.getInstance(props, auth);
					MimeMessage msg = new MimeMessage(session);
					try {
						//loads message
						msg.setText(message);
						msg.setSubject(subjectLine);
						//from
						msg.setFrom(new InternetAddress(Euser));
						//to
						msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
						System.out.println("Attempting to send...");

						Transport t = session.getTransport("smtps"); //.send(msg);
						try {
							//sending
							t.connect("smtp.gmail.com", Euser, Epassword);
							t.sendMessage(msg, msg.getAllRecipients());
						} finally {
							t.close();
						}
					} catch (AuthenticationFailedException err) {
						System.out.println(err);

					} catch (AddressException err) {
						System.out.println(err);

					} catch (MessagingException err) {
						System.out.println(err);
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
	private class SMTPAuthenticator extends Authenticator {

		private PasswordAuthentication authentication;

		public SMTPAuthenticator(String Euser, String Epassword) {
			authentication = new PasswordAuthentication(Euser, Epassword);
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return authentication;
		}
	}
}

