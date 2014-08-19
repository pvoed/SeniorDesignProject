package ycp.edu.seniordesign.model.persist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JDBC utility methods.
 * 
 * @author David Hovemeyer
 */
public class DBUtil {

	/**
	 * Quietly close a PreparedStatemnt.
	 * @param stmt the PreparedStatement to close
	 */
	public static void closeQuietly(PreparedStatement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			// TODO: log
		}
	}

	/**
	 * Quietly close a ResultSet.
	 * @param stmt the ResultSet to close
	 */
	public static void closeQuietly(ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (SQLException e) {
			// TODO: log
		}
	}

	/**
	 * Quietly close a Connection.
	 * @param stmt the Connection to close
	 */
	public static void closeQuietly(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO: log
		}
	}
	
	public static void close(Connection conn) throws SQLException{
		conn.prepareStatement("shutdown").execute();
	}
}
