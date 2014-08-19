package ycp.edu.seniordesign.model.persist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import ycp.edu.seniordesign.model.Admin;
import ycp.edu.seniordesign.model.Assignment;
import ycp.edu.seniordesign.model.ChangeUserTypeRequest;
import ycp.edu.seniordesign.model.Course;
import ycp.edu.seniordesign.model.EnrolledCourse;
import ycp.edu.seniordesign.model.GradeWeight;
import ycp.edu.seniordesign.model.PendingCourse;
import ycp.edu.seniordesign.model.Registration;
import ycp.edu.seniordesign.model.User;
import ycp.edu.seniordesign.util.HashPassword;


public class Database {
	static {
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("Could not load hsql driver");
		}
	}

	private static final String JDBC_URL ="jdbc:hsqldb:file:whiteboard.db";
	
	private static final Database theInstance = new Database();
	
	public static Database getInstance() {
		return theInstance;
	}
	
	public Database(){

	}
	
	/**
	 * Authenticate the user via username and password
	 * @param username the username of the user trying to login
	 * @param password the plain-text password of the user trying to login
	 * @return the User object associated with the username and password
	 * @throws SQLException
	 */
	public User authenticateUser(String username, String password) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);	
			
			// look up user with the given username
			statement = connection.prepareStatement("select * from users where username=?");
			statement.setString(1, username);
			resultSet = statement.executeQuery();
			 
			if (resultSet.next()){
				// there is someone with the given username
				User user = new User();
				user.loadFrom(resultSet);
				
				// Check password
				String hashedPassword = HashPassword.computeHash(password, user.getSalt());
				if (hashedPassword.equals(user.getPassword())){
					// passwords matched
					return user;
				} else {
					// passwords did not match
					return null;
				}
			} else {
				// the user does not exist
				return null;
			}
				 	 
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
			DBUtil.closeQuietly(resultSet);
		}
	}
	
	/**
	 * Authenticate the admin via username and password
	 * @param username the username of the admin trying to login
	 * @param password the plain-text password of the admin trying to login
	 * @return the Admin object associated with the username and password
	 * @throws SQLException
	 */
	public Admin authenticateAdmin(String username, String password) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);	
			
			// look up user with the given username
			statement = connection.prepareStatement("select * from admins where username=?");
			statement.setString(1, username);
			resultSet = statement.executeQuery();
			 
			if (resultSet.next()){
				// there is an admin with the given username
				Admin admin = new Admin();
				admin.loadFrom(resultSet);
				
				// Check password
				String hashedPassword = HashPassword.computeHash(password, admin.getSalt());
				if (hashedPassword.equals(admin.getHashedPassword())){
					// passwords matched
					return admin;
				} else {
					// passwords did not match
					return null;
				}
			} else {
				// the user does not exist
				return null;
			}
				 	 
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
			DBUtil.closeQuietly(resultSet);
		}
	}
	
	/**
	 * Create an account in the user table
	 * @param username the username of the new account
	 * @param name the name of the new user
	 * @param password the plain-text password of the new account
	 * @param emailAddress the emailAddress of the new account
	 * @param type the type of the new account (1 for student, 2 for professor, 3 for both)
	 * @return the id of the newly inserted row, or -1 if the createAccount failed
	 * @throws SQLException
	 */
	public int createAccount(String username, String name, String password, String emailAddress, int type) throws SQLException{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		// TODO: do not allow creation of account with a variation in case on a current username
		// Example: If there is a user with a user name of "username" do not allow creation of an account with the username of 
		// "UsErNaME"
		try {
			connection = DriverManager.getConnection(JDBC_URL);
						
			// check to see if username is taken
			statement = connection.prepareStatement("select * from users where username=?");
			statement.setString(1, username);
			
			resultSet = statement.executeQuery();
			 
			if (resultSet.next()){
				// the username is already taken
				return -1;
			}
			
			// generate random salt and hash password
			String salt = HashPassword.generateRandomSalt(new Random());
			String hashedPassword = HashPassword.computeHash(password, salt);
			
			// add the user to the database
			statement = connection.prepareStatement("insert into users values(NULL,?,?,?,?,?,?,'', 'false')");
			statement.setString(1, username);
			statement.setString(2, name);
			statement.setString(3, hashedPassword);
			statement.setString(4, salt);
			statement.setString(5, emailAddress);
			statement.setInt(6, type);
			statement.execute();
			
			// get the id of the newly inserted row
			statement = connection.prepareStatement("select * from users where username=?");
			statement.setString(1, username);
			resultSet = statement.executeQuery();
			
			resultSet.next();
			return resultSet.getInt(1);
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
			DBUtil.closeQuietly(resultSet);
		}
	}
	
	/**
	 * Delete account from the user table
	 * @param username the username of the account to be deleted
	 * @param password the password of the account to be deleted
	 * @return ture if the account is sucessfully deleted, false otherwise
	 * @throws SQLException
	 */
	public boolean deleteAccount(String username, String password) throws SQLException{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);
				
			// check to see if user exists
			statement = connection.prepareStatement("select * from users where username=?");
			statement.setString(1, username);
			resultSet = statement.executeQuery();
			 
			if (resultSet.next()){
				// there is someone with the given username
				User user = new User();
				user.loadFrom(resultSet);
				
				// Check password
				String hashedPassword = HashPassword.computeHash(password, user.getSalt());
				if (hashedPassword.equals(user.getPassword())){
					// delete the user from the database
					statement = connection.prepareStatement("delete from users where username=? and password=?");
					statement.setString(1,  username);
					statement.setString(2, hashedPassword);
					statement.execute();
				
					return true;
				} else {
					// the user does not exist
					return false;
				}
			}
			
			return false;
		
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
			DBUtil.closeQuietly(resultSet);
		}
	}
	
	/**
	 * Gets the user with the given id
	 * @param id the id of the user to search for
	 * @return the User object with the given id if it exists, otherwise null
	 * @throws SQLException
	 */
	public User getUserById(int id) throws SQLException{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);
			connection.setAutoCommit(false);
						
			statement = connection.prepareStatement("select * from users where id=?");
			statement.setInt(1, id);
			
			resultSet = statement.executeQuery();
			 
			if (resultSet.next()){
				// the user exists
				User user = new User();
				user.loadFrom(resultSet);
				return user;
			}
			
			else {
				// no user exist with the given id
				return null;
			}
			
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
			DBUtil.closeQuietly(resultSet);
		}
	}
	
	public Course getCourseById(int id) throws SQLException{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);
			connection.setAutoCommit(false);
						
			statement = connection.prepareStatement("select * from courses where id=?");
			statement.setInt(1, id);
			
			resultSet = statement.executeQuery();
			 
			if (resultSet.next()){
				// the course exists
				Course course = new Course();
				course.loadFrom(resultSet);
				return course;
			}
			
			else {
				// no course exist with the given id
				return null;
			}
			
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
			DBUtil.closeQuietly(resultSet);

		}
	}
	
	public EnrolledCourse getEnrolledCourseById(int id) throws SQLException{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);
						
			statement = connection.prepareStatement("select * from enrolled_courses where id=?");
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			 
			if (resultSet.next()){
				// the enrolled course exists
				EnrolledCourse course = new EnrolledCourse();
				course.loadFrom(resultSet);
				return course;
			}
			
			else {
				// no enrolled course exist with the given id
				return null;
			}
			
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
			DBUtil.closeQuietly(resultSet);

		}
	}
	
	public Assignment getAssignmentById(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);
			
			statement = connection.prepareStatement("select * from assignments where id=?");
			statement.setInt(1,  id);
			
			resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				Assignment assignment = new Assignment();
				assignment.loadFrom(resultSet);
				return assignment;
			} else {
				return null;
			}
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
			DBUtil.closeQuietly(resultSet);
		}
	}
	
	public Registration getRegistrationById(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);
			
			statement = connection.prepareStatement("select * from registrations where id=?");
			statement.setInt(1,  id);
			
			resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				Registration registration = new Registration();
				registration.loadFrom(resultSet);
				return registration;
			} else {
				return null;
			}
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
			DBUtil.closeQuietly(resultSet);
		}
	}
	
	/**
	 * This method returns a list of all the courses taken by the student
	 * @param user
	 * @return if the user is a professor the method returns null, otherwise the method returns an ArrayList of all the courses the student is enrolled in
	 * @throws SQLException
	 */
	public ArrayList<EnrolledCourse> getEnrolledCoursesForStudent(User user) throws SQLException{
		if (!user.isStudent()){
			// the user that was passed is a professor and thus does not take any classes
			return null;
		}
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);
						
			statement = connection.prepareStatement("select * from enrolled_courses where student_id=?");
			statement.setInt(1, user.getId());
			resultSet = statement.executeQuery();
			
			ArrayList<EnrolledCourse> courses = new ArrayList<EnrolledCourse>(); 
			while (resultSet.next()){
				EnrolledCourse course = new EnrolledCourse();
				course.loadFrom(resultSet);
				courses.add(course);
			}
			
			return courses;
			
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
			DBUtil.closeQuietly(resultSet);
		}
	}
	
	/**
	 * This method returns a list of all the courses taught by a professor
	 * @param user 
	 * @return if the user is a student the method returns null, otherwise the method returns an ArrayList of all the course the professor teaches
	 * @throws SQLException
	 */
	public ArrayList<Course> getCoursesForProfessor(User user) throws SQLException{
		if (!user.isProfessor()){
			// the user that was passed is a student and thus does not teach any classes
			return null;
		}
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);
						
			statement = connection.prepareStatement("select * from courses where professor_id=?");
			statement.setInt(1, user.getId());
			resultSet = statement.executeQuery();
			
			ArrayList<Course> courses = new ArrayList<Course>(); 
			while (resultSet.next()){
				Course course = new Course();
				course.loadFrom(resultSet);
				courses.add(course);
			}
			
			return courses;
			
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
			DBUtil.closeQuietly(resultSet);
		}
	}
	
	/**
	 * This method gets a list of all assignments for the given courses and student
	 * @param courseId the courseId the assignment is for
	 * @param studentId the studentId the assignment is for
	 * @return an ArrayList of the assignments for the given course and student
	 * @throws SQLException
	 */
	public ArrayList<Assignment> getAssignmentsForCourse(int courseId, int studentId) throws SQLException{		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);
			connection.setAutoCommit(false);
						
			statement = connection.prepareStatement("select * from assignments where course_id=? and student_id=?");
			statement.setInt(1, courseId);
			statement.setInt(2,  studentId);
			resultSet = statement.executeQuery();
			
			ArrayList<Assignment> assignments = new ArrayList<Assignment>(); 
			while (resultSet.next()){
				Assignment assignment = new Assignment();
				assignment.loadFrom(resultSet);
				assignments.add(assignment);
			}
			
			return assignments;
			
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
			DBUtil.closeQuietly(resultSet);
		}
	}
	
	public ArrayList<Assignment> getAssignmentsForProfessor(int courseId) throws SQLException{		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ArrayList<Assignment> assignments = new ArrayList<Assignment>(); 
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);
			connection.setAutoCommit(false);
						
			statement = connection.prepareStatement("select distinct name from assignments where course_id=?");
			statement.setInt(1, courseId);
			resultSet = statement.executeQuery();
			ArrayList<String> names = new ArrayList<String>();
			
			while(resultSet.next()){
				names.add(resultSet.getString((1)));
			}
			
			statement = connection.prepareStatement("select * from assignments where name = ?");
			for (String name: names){
				statement.setString(1, name);
				resultSet = statement.executeQuery();
				if (resultSet != null){
					resultSet.next();
					Assignment assignment = new Assignment();
					assignment.loadFrom(resultSet);
					assignments.add(assignment);
				} 
			}
			if (assignments.isEmpty()){
				return null;
			}
			
			return assignments;
			
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
			DBUtil.closeQuietly(resultSet);
		}
	}
	
	/**
	 * This method adds an entry to the Enrolled_Courses table representing a Course that the given user is enrolled in
	 * @param courseId the id of the course to add
	 * @param studentId the id of the student who is enrolling in the course
	 * @param professorId the id of the professor who teaches the course
	 * @return returns the id of the newly inserted EnrolledCourse
	 * @throws SQLException
	 */
	public int addEnrolledCourseForStudent(int studentId, int professorId, int courseId) throws SQLException{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);			
			
			statement = connection.prepareStatement("insert into enrolled_courses values(null,?, ?, ?, ?)");
			statement.setInt(1, studentId);
			statement.setInt(2,  professorId);
			statement.setInt(3, courseId);
			statement.setInt(4, 100); // students start with a 100 in a course they have just enrolled in
			statement.execute();
			
			// Get the id that the course was added with
			statement = connection.prepareStatement("select id from enrolled_courses where student_id=? and professor_id =? and course_id=?");
			statement.setInt(1, studentId);
			statement.setInt(2,  professorId);
			statement.setInt(3, courseId);
			resultSet = statement.executeQuery();
			
			resultSet.next();
			return resultSet.getInt(1);
						
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
			DBUtil.closeQuietly(resultSet);
		}
	}
	
	/**
	 * This method deletes the record in the database representing a course taken by the given student. It is used in the case of 
	 * withdrawals, drops, etc.
	 * @param courseId the id of the course to remove
	 * @param studentId the id of the student who is removing the course
	 * @throws SQLException
	 */
	public void removeEnrolledCourseForStudent(int courseId, int studentId) throws SQLException{
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);			
			
			statement = connection.prepareStatement("delete from enrolled_courses where student_id=? and course_id =?");
			statement.setInt(1, studentId);
			statement.setInt(2, courseId);
			statement.execute();
						
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
		}
	}
	
	
	/**
	 * This method adds a row to the Courses table in the database for a given professor
	 * @param course the course object to store (it should have the professor field set to the id of the professor who will be 
	 * teaching the course
	 * @return the id of the newly inserted row
	 * @throws SQLException
	 */
	public int addCourseForProfessor(Course course) throws SQLException{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);			
			
			statement = connection.prepareStatement("insert into courses values(?,?,?,?,?,?,?,?,?,?,?)");
			course.storeTo(statement);
			statement.setString(1, null);
			statement.execute();
			
			
			// Get the id that the course was added with
			statement = connection.prepareStatement("select id from courses where name =? and professor_id=? and time=? and course_num =? and sec_num=? and credits=? and weekly_days=? and location = ? and CRN =? and description =?");
			statement.setString(1, course.getName());
			statement.setInt(2, course.getProfessorId());
			statement.setString(3, course.getTime());
			statement.setInt(4, course.getCourseNumber());
			statement.setInt(5, course.getSectionNumber());
			statement.setInt(6, course.getCredits());
			statement.setString(7, course.getDays());
			statement.setString(8, course.getLocation());
			statement.setInt(9, course.getCRN());
			statement.setString(10, course.getDescription());
			resultSet = statement.executeQuery();
			
			resultSet.next();
			return resultSet.getInt(1);						
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
			DBUtil.closeQuietly(resultSet);
		}
	}
	
	/**
	 * This method deletes the row from the course table with the courseId
	 * @param courseId the id of the course to remove
	 * @throws SQLException
	 */
	public void removeCourseForProfessor(int courseId) throws SQLException{
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);			
			
			statement = connection.prepareStatement("delete from courses where id =?");
			statement.setInt(1,  courseId);
			statement.execute();
						
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
		}
	}
	
	
	/**
	 * This method adds a row to the Assignments table in the database
	 * @param assignment the assignment object to store
	 * @return the id of the newly inserted row
	 * @throws SQLException
	 */
	public int addAssignmentForCourse(Assignment assignment) throws SQLException{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);			
			
			statement = connection.prepareStatement("insert into assignments values(?,?,?,?,?,?,?,?)");
			assignment.storeTo(statement);
			statement.setString(1, null);
			statement.execute();
			
			// Get the id that the assignment was added with
			statement = connection.prepareStatement("select id from assignments where course_id=? and student_id=? and name=? and due_date=? and grade_weight_id=? and earned_points=? and possible_points=?");
			statement.setInt(1, assignment.getCourseId());
			statement.setInt(2, assignment.getStudentId());
			statement.setString(3, assignment.getName());
			statement.setDate(4, new java.sql.Date(assignment.getDueDate().getTime()));
			statement.setInt(5, assignment.getGradeWeightType());
			statement.setInt(6, assignment.getEarnedPoints());
			statement.setInt(7, assignment.getPossiblePoints());
			resultSet = statement.executeQuery();

			resultSet.next();
			return resultSet.getInt(1);						
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
			DBUtil.closeQuietly(resultSet);
		}
	}
	
	public void removeAssignmentForCourse(int assignmentId) throws SQLException{
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);			
			
			statement = connection.prepareStatement("delete from assignments where id =?");
			statement.setInt(1,  assignmentId);
			statement.execute();
						
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
		}
	}
	
	public boolean changePassword(User user, String newPassword) throws Exception{
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);		
			
			String hashedPassword = HashPassword.computeHash(newPassword, user.getSalt());
			
			statement = connection.prepareStatement("update users set password=? where id=?");  
			statement.setString(1,  hashedPassword);
			statement.setInt(2, user.getId());
			
			int rowsUpdated = statement.executeUpdate();

			if (rowsUpdated == 1){
				// Perfect only one user's password was changed
				return true;
			} else if (rowsUpdated == 0){
				// No user found with the given id
				return false;
			} else {
				// This is bad the password for multiple users was changed
				throw new Exception("Multiple users with the same id (should not be possible)");
			}
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
		}   

	}
	
	public User getUserByUsername(String username) throws SQLException{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);
						
			statement = connection.prepareStatement("select * from users where username=?");
			statement.setString(1, username);
			
			resultSet = statement.executeQuery();
			 
			if (resultSet.next()){
				// the user exists
				User user = new User();
				user.loadFrom(resultSet);
				return user;
			}
			else {
				// no user exist with the given id
				return null;
			}
			
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
			DBUtil.closeQuietly(resultSet);
		}
	}
	
	public User getUserByName(String name) throws Exception{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);
						
			statement = connection.prepareStatement("select * from users where name=?");
			statement.setString(1, name);
			
			resultSet = statement.executeQuery();
			 
			if (resultSet.next()){
				// the user exists
				User user = new User();
				user.loadFrom(resultSet);
				if (resultSet.next()){
					throw new Exception("Multiple users with the same name. Fix this method.");
				}
				return user;
			}
			else {
				// no user exist with the given id
				return null;
			}
			
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
			DBUtil.closeQuietly(resultSet);
		}
	}
	
	public User getUserByEmail(String emailAddress) throws SQLException{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);
						
			statement = connection.prepareStatement("select * from users where emailAddress=?");
			statement.setString(1, emailAddress);
			
			resultSet = statement.executeQuery();
			 
			if (resultSet.next()){
				// the user exists
				User user = new User();
				user.loadFrom(resultSet);
				return user;
			}
			
			else {
				// no user exist with the given id
				return null;
			}
			
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
			DBUtil.closeQuietly(resultSet);
		}
	}

	public ArrayList<Assignment> getInstancesofAssignment(int id, String name) throws SQLException
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try 
		{
			connection = DriverManager.getConnection(JDBC_URL);
			connection.setAutoCommit(false);
			
			statement = connection.prepareStatement("select * from assignments where course_id=? and name=?");
			statement.setInt(1,  id);
			statement.setString(2, name);
			
			resultSet = statement.executeQuery();
			
			ArrayList<Assignment> returnList = new ArrayList<Assignment>();

			while (resultSet.next())
			{
				Assignment assignment = new Assignment();
				assignment.loadFrom(resultSet);
				returnList.add(assignment);
			}
			
			if(returnList.isEmpty())
			{
				return null;
			}
			return returnList;

			
		} 
		finally 
		{
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
			DBUtil.closeQuietly(resultSet);
		}
			
	}

	public void updateUser(User user) throws SQLException{
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);
			
			statement = connection.prepareStatement("update users set emailaddress =?, major =?, commuter =?, password =? where id =?");
			statement.setString(1, user.getEmailAddress());
			statement.setString(2, user.getMajor());
			statement.setBoolean(3, user.isCommuter());
			statement.setString(4, user.getPassword());
			statement.setInt(5, user.getId());
			statement.execute();
			
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);

		}
	}
	
	/**
	 * This method adds a new registration to the Registrations table in the database
	 * @param username the username of the user that the registration will create
	 * @param emailAddress the emailAddress of the user the registration will create
	 * @param url the secret url
	 * @return the id of the newly inserted row
	 * @throws SQLException
	 */
	public int addRegistration(String username, String emailAddress, String url) throws SQLException{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		// Calculate the expiration date of the registration
		Timestamp expiration = new Timestamp(System.currentTimeMillis() + Registration.VALID_DURATION_IN_HOURS);
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);			
			
			statement = connection.prepareStatement("insert into registrations values(NULL,?,?,?,?)");
			statement.setString(1, username);
			statement.setString(2, emailAddress);
			statement.setString(3, url);
			statement.setTimestamp(4, expiration);
			statement.execute();
			
			// Get the id of the registration that was just added
			statement = connection.prepareStatement("select id from registrations where username=? and email_address=? and url=? and expiration=?");
			statement.setString(1, username);
			statement.setString(2, emailAddress);
			statement.setString(3, url);
			statement.setTimestamp(4, expiration);
			resultSet = statement.executeQuery();

			resultSet.next();
			return resultSet.getInt(1);						
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
			DBUtil.closeQuietly(resultSet);
		}
	}
	
	/**
	 * This method removes a registration from the Registrations table in the database
	 * @param registrationId the id of the registration to remove
	 * @return a Registration object representing the row that was removed
	 * @throws Exception 
	 */
	public Registration removeRegistration(String url) throws Exception{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		Registration registration = new Registration();
	
		try {
			connection = DriverManager.getConnection(JDBC_URL);			
			
			statement = connection.prepareStatement("select * from registrations where url=?");
			statement.setString(1, url);
			resultSet = statement.executeQuery();
			
			if (resultSet == null){
				// No registration was found with the given id
				return null;
			} else {
				resultSet.next();
				registration.loadFrom(resultSet);
			}
			if (resultSet.next()){
				// multiple matches for the registrationId
				throw new Exception("Multiple registrations with the same id (should not happen)");
			} else{
				// delete the registration
				statement = connection.prepareStatement("delete from registrations where url=?");
				statement.setString(1, url);
				statement.execute();
				return registration;
			}

				
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
			DBUtil.closeQuietly(resultSet);
		}
	}
	
	public void updateAssignment(Assignment assign) throws SQLException
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try
		{
			conn = DriverManager.getConnection(JDBC_URL);
			conn.setAutoCommit(false);
			
			stmt = conn.prepareStatement("UPDATE ASSIGNMENTS SET grade_weight_id=?, earned_points=?, possible_points=? WHERE name=? AND student_id=?");
			
			stmt.setInt(1, assign.getGradeWeightType());
			stmt.setInt(2, assign.getEarnedPoints());
			stmt.setInt(3, assign.getPossiblePoints());
			stmt.setString(4, assign.getName());
			stmt.setInt(5, assign.getStudentId());
			
			stmt.executeUpdate();
		}
		finally
		{
			DBUtil.close(conn);
			DBUtil.closeQuietly(stmt);
		}
	}
	public Registration getRegistrationCode(String url) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);
			
			statement = connection.prepareStatement("select * from registrations where url=?");
			statement.setString(1,  url);
			
			resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				Registration registration = new Registration();
				registration.loadFrom(resultSet);
				return registration;
			} else {
				return null;
			}
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
			DBUtil.closeQuietly(resultSet);
		}
	}

	public int getGradeWeightForAssignment(int assignmentId) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);
			
			statement = connection.prepareStatement("select weight from grade_weights where id=?");
			statement.setInt(1,  assignmentId);
			
			resultSet = statement.executeQuery();
			
			if (resultSet.next()){
				return resultSet.getInt(1);	
			} else {
				return -1;
			}
			
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
			DBUtil.closeQuietly(resultSet);
		}
	}
	
	public boolean isStudentinClass(int userID, int courseID) throws SQLException
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		try
		{
			conn = DriverManager.getConnection(JDBC_URL);
			conn.setAutoCommit(false);
			
			stmt = conn.prepareStatement("select * from enrolled_courses where student_id=? and course_id=?");
			stmt.setInt(1, userID);
			stmt.setInt(2, courseID);
			
			result = stmt.executeQuery();
			
			if(result.next())
			{
				return true;
			}
			
			return false;
		}
		finally
		{
			DBUtil.close(conn);
			DBUtil.closeQuietly(stmt);
			DBUtil.closeQuietly(result);
		}
	}
	
	public boolean isProfessorinClass(int userID, int courseID) throws SQLException
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		try
		{
			conn = DriverManager.getConnection(JDBC_URL);
			conn.setAutoCommit(false);
			
			stmt = conn.prepareStatement("select * from courses where professor_id=? and id=?");
			
			stmt.setInt(1, userID);
			stmt.setInt(2, courseID);
			
			result = stmt.executeQuery();
			
			if(result.next())
			{
				return true;
			}
			
			return false;
		}
		finally
		{
			DBUtil.close(conn);
			DBUtil.closeQuietly(stmt);
			DBUtil.closeQuietly(stmt);
		}
	}
	
	public void changeUserType(User user) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);
			
			statement = connection.prepareStatement("update users set usertype=? where id=?");
			statement.setInt(1,  user.getType());
			statement.setInt(2, user.getId());
			
			statement.executeUpdate();
			
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);

		}
		

	}
	
	public ArrayList<Integer> getNamesforCourse(int courseID) throws SQLException
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		try
		{
			conn = DriverManager.getConnection(JDBC_URL);
			conn.setAutoCommit(false);
			
			stmt = conn.prepareStatement("select distinct student_id from assignments where course_id=?");
			stmt.setInt(1, courseID);
			
			resultSet = stmt.executeQuery();
			
			ArrayList<Integer> returnList = new ArrayList<Integer>();
			
			while(resultSet.next())
			{
				returnList.add(resultSet.getInt(1));
			}
			
			return returnList;
		}
		finally
		{
			DBUtil.close(conn);
			DBUtil.closeQuietly(stmt);
			DBUtil.closeQuietly(resultSet);
		}
	}

	public void addPendingCourse(PendingCourse pendingCourse) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);			
			
			statement = connection.prepareStatement("insert into pending_courses values(NULL,?,?,?,?,?,?,?,?,?,?,?)");
			
			statement.setString(1, pendingCourse.getCourseName());
			statement.setString(2, pendingCourse.getProfessorName());
			statement.setString(3, pendingCourse.getEmailAddress());
			statement.setString(4, pendingCourse.getTime());
			statement.setInt(5, pendingCourse.getCourseNumber());
			statement.setInt(6, pendingCourse.getCourseSection());
			statement.setInt(7, pendingCourse.getCredits());
			statement.setString(8, pendingCourse.getDays());
			statement.setString(9, pendingCourse.getLocation());
			statement.setInt(10, pendingCourse.getCRN());		
			statement.setString(11, pendingCourse.getDescription());
			
			statement.execute();
			
			// TODO: have this method return the id of the newly inserted row
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
			DBUtil.closeQuietly(resultSet);
		}
		
	}

	public ArrayList<PendingCourse> getPendingCourses() throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);			
			
			statement = connection.prepareStatement("select * from pending_courses");
			
			resultSet = statement.executeQuery();
			
			ArrayList<PendingCourse> pendingCourses = new ArrayList<PendingCourse>();
						
			while (resultSet.next()){
				PendingCourse pendingCourse = new PendingCourse();
				pendingCourse.loadFrom(resultSet);
				pendingCourses.add(pendingCourse);
			}
			
			if (pendingCourses.isEmpty()){
				return null;
			} else {
				return pendingCourses;
			}
			
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
			DBUtil.closeQuietly(resultSet);
		}
	}

	public void removePendingCourse(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);			
			
			statement = connection.prepareStatement("delete from pending_courses where id=?");
			statement.setInt(1,  id);
			
			statement.execute();
						
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
		}
	}
	
	/**
	 * This method returns all assignments for the user with the given id that are due within the next week
	 * @param id
	 * @return an ArrayList of all assignments that are due within the next week 
	 * @throws SQLException
	 */
	public HashMap<Assignment, String> getUpcomingAssignments(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
				
		try {
			connection = DriverManager.getConnection(JDBC_URL);			
			
			statement = connection.prepareStatement("select * from assignments where student_id=?");
			statement.setInt(1, id);
			
			resultSet = statement.executeQuery();
			
			HashMap<Assignment, String> upcomingAssignments = new HashMap<Assignment, String>();
						
			while (resultSet.next()){
				Assignment assignment = new Assignment();
				assignment.loadFrom(resultSet);
				
				// This date is one week ahead of the current time
				Date weekAhead = new Date(System.currentTimeMillis() + (7 * 1000 * 60 * 60 * 24));
				
				if (!assignment.isOverdue() && assignment.getDueDate().before(weekAhead)){
					// Only add the assignment to the list if it is due within the next week and is not currently overdue
					
					// Lookup the course name the assignment is due for
					statement = connection.prepareStatement("select * from courses where id=?");
					statement.setInt(1, assignment.getCourseId());
					ResultSet resultSet2 = statement.executeQuery();
					resultSet2.next();
					String courseName = resultSet2.getString(2);
					upcomingAssignments.put(assignment, courseName);
				}
			}
			
			if (upcomingAssignments.isEmpty()){
				return null;
			} else {
				return upcomingAssignments;
			}
			
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
			DBUtil.closeQuietly(resultSet);
		}
	}
	
	public ArrayList<GradeWeight> getGradesforCourse(int courseID) throws SQLException
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		try
		{
			conn = DriverManager.getConnection(JDBC_URL);
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement("Select * from grade_weights where course_id=?");
			
			stmt.setInt(1, courseID);
			
			resultSet = stmt.executeQuery();
			
			ArrayList<GradeWeight> returnList = new ArrayList<GradeWeight>();
			
			while(resultSet.next())
			{
				GradeWeight g = new GradeWeight();
				g.loadFrom(resultSet);
				returnList.add(g);
			}
			
			return returnList;
		}
		finally
		{
			DBUtil.close(conn);
			DBUtil.closeQuietly(stmt);
			DBUtil.closeQuietly(resultSet);
		}
	}

	public int addGradeWeight(int gradeWeight, int courseId, String name) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		try
		{
			conn = DriverManager.getConnection(JDBC_URL);
		
			// Insert the new row
			stmt = conn.prepareStatement("insert into grade_weights values(NULL, ?, ?, ?)");
			stmt.setString(1, name);
			stmt.setInt(2, gradeWeight);	
			stmt.setInt(3, courseId);
			stmt.execute();
			
			// Return the id of the newly inserted row
			stmt = conn.prepareStatement("select * from grade_weights where weight=?");
			stmt.setInt(1, gradeWeight);
			resultSet = stmt.executeQuery();
			
			if(resultSet.next()) {
				GradeWeight grade = new GradeWeight();
				grade.loadFrom(resultSet);
				return grade.getId();
			}
			
			return -1;
		}
		finally
		{
			DBUtil.close(conn);
			DBUtil.closeQuietly(stmt);
			DBUtil.closeQuietly(resultSet);
		}
	}

	public void addChangeUserTypeRequest(ChangeUserTypeRequest changeUserTypeRequest) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);			
			
			statement = connection.prepareStatement("insert into User_Type_Changes values(NULL,?,?,?)");
			
			statement.setString(1, changeUserTypeRequest.getUsername());
			statement.setString(2, changeUserTypeRequest.getEmailAddress());
			statement.setString(3, changeUserTypeRequest.getNewUserType());
			
			statement.execute();
			
			// TODO: have this method return the id of the newly inserted row
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
			DBUtil.closeQuietly(resultSet);
		}
		
	}

	public ArrayList<ChangeUserTypeRequest> getChangeUserTypeRequests() throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);			
			
			statement = connection.prepareStatement("select * from user_type_changes");
			
			resultSet = statement.executeQuery();
			
			ArrayList<ChangeUserTypeRequest> changeUserTypeRequests = new ArrayList<ChangeUserTypeRequest>();
						
			while (resultSet.next()){
				ChangeUserTypeRequest changeUserTypeRequest = new ChangeUserTypeRequest();
				changeUserTypeRequest.loadFrom(resultSet);
				changeUserTypeRequests.add(changeUserTypeRequest);
			}
			
			if (changeUserTypeRequests.isEmpty()){
				return null;
			} else {
				return changeUserTypeRequests;
			}
			
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
			DBUtil.closeQuietly(resultSet);
		}
	}

	public void removeChangeUserType(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = DriverManager.getConnection(JDBC_URL);			
			
			statement = connection.prepareStatement("delete from user_type_changes where id=?");
			statement.setInt(1,  id);
			
			statement.execute();
						
		} finally {
			DBUtil.close(connection);
			DBUtil.closeQuietly(statement);
		}
	}

	public void createAssignment(int userID, int courseID, String name, Date date, int possible, int weight_id) throws SQLException
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		try
		{
			ArrayList<Integer> student_List = getStudentsinCourse(courseID);
			
			conn = DriverManager.getConnection(JDBC_URL);
			
			//FIXME: Need special handling for this?? earned points of 0 will throw grades off - special value?? -1 maybe
				//that will display a 0 on the grade page but not be taken into account on the grade calculation
			stmt = conn.prepareStatement("insert into assignments values (NULL, ?, ?, ?, ?, ?, -1, ?)"); 
			
			stmt.setInt(1, courseID);
			//statically set Name, Due Date, grade_weight_id, earned_points, possible_points
			stmt.setString(3, name);
			stmt.setDate(4, new java.sql.Date(date.getTime()));
			stmt.setInt(5, weight_id);
			stmt.setInt(6, possible);
			
			//foreach student in the class, set the student id and execute
			for(int i : student_List)
			{
				stmt.setInt(2, i);
				stmt.execute();
			}
			//determine whether or not to add a category to the grade_weight table
		}
		finally
		{
			DBUtil.close(conn);
			DBUtil.closeQuietly(stmt);
			DBUtil.closeQuietly(resultSet);
		}
	}
	
	public ArrayList<Integer> getStudentsinCourse(int courseID) throws SQLException
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		try
		{
			conn = DriverManager.getConnection(JDBC_URL);
			
			stmt = conn.prepareStatement("select distinct student_id from assignments where course_id=?");
			stmt.setInt(1, courseID);
			
			resultSet = stmt.executeQuery();
			
			ArrayList<Integer> returnList = new ArrayList<Integer>();
			
			while(resultSet.next())
			{
				returnList.add(resultSet.getInt(1));
			}
			
			return returnList;
		}
		finally
		{
			DBUtil.close(conn);
			DBUtil.closeQuietly(stmt);
			DBUtil.closeQuietly(resultSet);
		}
	}

	public int getWeightfromName(String name, int courseID) throws SQLException 
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		
		try
		{
			conn = DriverManager.getConnection(JDBC_URL);
			
			stmt = conn.prepareStatement("select id from grade_weights where name=? and course_id=?");
			stmt.setString(1, name);
			stmt.setInt(2, courseID);
			
			resultSet = stmt.executeQuery();
			
			if(resultSet.next())
			{
				return resultSet.getInt(1);
			}
			else
			{
				return -1;
			}
		}
		finally
		{
			DBUtil.close(conn);
			DBUtil.closeQuietly(stmt);
			DBUtil.closeQuietly(resultSet);
		}
	}
		
}
