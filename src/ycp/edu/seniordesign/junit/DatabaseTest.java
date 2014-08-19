package ycp.edu.seniordesign.junit;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.Random;

import org.junit.Test;

import ycp.edu.seniordesign.model.Assignment;
import ycp.edu.seniordesign.model.Course;
import ycp.edu.seniordesign.model.EnrolledCourse;
import ycp.edu.seniordesign.model.User;
import ycp.edu.seniordesign.model.persist.Database;
import ycp.edu.seniordesign.util.HashPassword;

public class DatabaseTest {
	String testSalt = HashPassword.generateRandomSalt(new Random());
	String testPassword = HashPassword.computeHash("password", testSalt);
	User testStudent = new User(999999, "username", "Test Student", testPassword, testSalt, "emailAddress", User.STUDENT_PROFILE, "CS", true, "1111111111", "HUM 111", "Biography");
	User testProfessor = new User(999999, "testProfessor", "Test Professor",  testPassword, testSalt, "emailAddress", User.PROFESSOR_PROFILE, "None", true, "1111111111", "HUM 111", "Biography");
	Course testCourse = new Course(999999, "Calc", testProfessor.getId(), "8AM - 9AM", 320, 101, 4, "MWF", "KEC 119", 123456, "This is a math class.");
	EnrolledCourse testEnrolledCourse = new EnrolledCourse(999999, testStudent.getId(), testProfessor.getId(), testCourse.getId(), 100);
	Assignment testAssignment = new Assignment(999999, testCourse.getId(), testStudent.getId(), "Homework #1", new Date(112, 8, 1), 1, 20, 20);
	
	@Test
	// This method  tests operations associated with the users table (createAccount, authenticate user, etc.)
	public void testUserOperations() throws Exception {
		int id = Database.getInstance().createAccount(testStudent.getUsername(), testStudent.getName(), testStudent.getPassword(), testStudent.getEmailAddress(), testStudent.getType());
		assertTrue(id != -1);
		testStudent.setId(id);
		assertTrue(Database.getInstance().authenticateUser(testStudent.getUsername(), testStudent.getPassword()) != null);
		assertTrue(Database.getInstance().createAccount(testStudent.getUsername(), testStudent.getName(), testStudent.getPassword(), testStudent.getEmailAddress(), testStudent.getType()) == -1);
		assertEquals(Database.getInstance().getUserByUsername(testStudent.getUsername()).getEmailAddress(), testStudent.getEmailAddress());
		assertEquals(Database.getInstance().getUserByEmail(testStudent.getEmailAddress()).getName(), testStudent.getName());
		testStudent = Database.getInstance().getUserById(id);
		testSalt = testStudent.getSalt();
		assertTrue(Database.getInstance().changePassword(testStudent, "newPassword"));
		testStudent.setPassword(HashPassword.computeHash("newPassword", testStudent.getSalt()));
		assertTrue(Database.getInstance().deleteAccount(testStudent.getUsername(), "newPassword"));
	}
	
	@Test
	// This method tests operations associated with the enrolled_courses table (addEnrolledCourseForStudent, removeEnrolledCourseForStudent, etc.)
	public void testEnrolledCourseOperations() throws SQLException{
		int id = Database.getInstance().addEnrolledCourseForStudent(testStudent.getId(), testProfessor.getId(), testCourse.getId());
		testEnrolledCourse.setId(id);
		assertEquals(Database.getInstance().getEnrolledCourseById(id).getGrade(), 100);
		assertEquals(Database.getInstance().getEnrolledCoursesForStudent(testStudent).get(0), testEnrolledCourse);
		Database.getInstance().removeEnrolledCourseForStudent(testCourse.getId(), testStudent.getId());
		assertEquals(Database.getInstance().getEnrolledCourseById(testEnrolledCourse.getId()), null);		
	}
	
	@Test
	// This method tests operations associated with the course table
	public void testCourseOperations() throws SQLException{
		int id = Database.getInstance().addCourseForProfessor(testCourse);
		testCourse.setId(id);
		assertEquals(Database.getInstance().getCourseById(id), testCourse);
		assertEquals(Database.getInstance().getCoursesForProfessor(testProfessor).get(0), testCourse);
		Database.getInstance().removeCourseForProfessor(id);
		assertEquals(Database.getInstance().getCourseById(testCourse.getId()), null);
	}
	
	@Test
	// This method tests operations associated with the assignments table 
	public void testAssignmentOperations() throws SQLException{
		int id = Database.getInstance().addAssignmentForCourse(testAssignment);
		testAssignment.setId(id);
		assertEquals(Database.getInstance().getAssignmentById(id).getName(), testAssignment.getName());
		Database.getInstance().removeAssignmentForCourse(testAssignment.getId());
		assertEquals(Database.getInstance().getAssignmentById(id), null);
	}
	
	@Test
	// This method tests operations associated with the admins table 
	public void testAdminOperations() throws SQLException{
		assertTrue(Database.getInstance().authenticateAdmin("TestAdmin", "password") != null);
	}

}
