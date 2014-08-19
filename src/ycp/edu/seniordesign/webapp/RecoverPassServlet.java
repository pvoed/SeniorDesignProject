
package ycp.edu.seniordesign.webapp;

import java.io.IOException;
import java.sql.SQLException;
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



import ycp.edu.seniordesign.controller.RecoverPassController;
import ycp.edu.seniordesign.model.User;

public class RecoverPassServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

		req.getRequestDispatcher("/view/recoverPass.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

		if(req.getParameter("recoveryButton") != null)
		{
			System.out.println("went into the if");
			boolean isFound = false;
			RecoverPassController controller = new RecoverPassController();
			String Euser = "whiteboardseniordesign@gmail.com";
			String Epassword = "randompassword";
			
			
			if(req.getParameter("usernameBox")!= null && req.getParameter("usernameBox")!= ""){
				String username = req.getParameter("usernameBox");
				try {
					//controller.setModel(controller.getUserByUsername(username));
					User u = controller.getUserByUsername(username);
					controller.setModel(u);
					System.out.println("Userfound via username");
				} catch (SQLException e) {
					System.out.println("Error in retreiving user by username.");
					e.printStackTrace();
				}
				if(controller.getModel()!=null){
					isFound = true;
				}
				
				
			}else if(req.getParameter("emailBox")!=null && req.getParameter("emailBox")!=""){
				String emailTo = req.getParameter("emailBox");
				try {
					//controller.setModel(controller.getUserByEmail(emailTo));
					User u = controller.getUserByEmail(emailTo);
					controller.setModel(u);

					System.out.println("Userfound via email.");
				} catch (SQLException e) {
					System.out.println("Error in retreiving user by email.");
					e.printStackTrace();
				}
				if(controller.getModel()!=null){
					isFound = true;
				}
			}else {
				System.out.println("Must enter user/email");
				req.getRequestDispatcher("/view/recoverPass.jsp").forward(req, resp);
			}
			System.out.println("isFound: " + isFound);
			if(isFound){
			String emailTo = controller.getModel().getEmailAddress();
			//generate pass
			String s = controller.generateRandPass();
			controller.getModel().setPassword(s);
			try {
				controller.changePassword();
			} catch (Exception e) {
				System.out.println("Error in changing password.");
				e.printStackTrace();
			}
			
			String message = "Your new password for username: " + controller.getModel().getUsername() + " will be: " + s;
			String subjectLine = "Recover Password [Whiteboard]";

			
			String errorMessage = null;
			Properties props = new Properties();
			//props.setProperty("mail.host", "smtp.gmail.com");
			props.put("mail.smtps.auth", "true");
			props.setProperty("mail.smtps.port", "465");
			props.setProperty("mail.smtps.auth", "true");
			props.setProperty("mail.smtps.starttls.enable", "true");

			//authenicates the gmail account
			Authenticator auth = new SMTPAuthenticator(Euser, Epassword);
			Session session = Session.getInstance(props, auth);

			MimeMessage msg = new MimeMessage(session);
			
				try {

					msg.setText(message);
					msg.setSubject(subjectLine);
					//from
					msg.setFrom(new InternetAddress(Euser));
					//to
					msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));


					Transport t = session.getTransport("smtps"); //.send(msg);
					try {
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
				System.out.println("Password changed and sent.");
			}
		}



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
