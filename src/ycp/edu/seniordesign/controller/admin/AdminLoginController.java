package ycp.edu.seniordesign.controller.admin;

import java.sql.SQLException;

import ycp.edu.seniordesign.model.Admin;
import ycp.edu.seniordesign.model.persist.Database;

public class AdminLoginController {
	public Admin login(String username, String password) throws SQLException
	{
		return Database.getInstance().authenticateAdmin(username, password);
	}
}
