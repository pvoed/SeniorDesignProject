package ycp.edu.seniordesign.controller.admin;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import ycp.edu.seniordesign.model.Course;
import ycp.edu.seniordesign.model.PendingCourse;
import ycp.edu.seniordesign.model.User;
import ycp.edu.seniordesign.model.persist.Database;

public class AdminApproveCourseController {
	
	public int addCourse(Course course) throws SQLException{
		return Database.getInstance().addCourseForProfessor(course);
	}

	public Course getCourse(HttpServletRequest req) throws SQLException {
		// Determine which request was submitted
		String requestId = (String) req.getParameter("requestId");
		
		// Look up the professor id for the given professor
		User user = Database.getInstance().getUserByEmail(req.getParameter("emailAddress" + requestId));
		if (user == null){
			System.out.println("break1");
			// No such user
			return null;
		} else if (user.getId() != 1){
			//FIXME:
			System.out.println("break2");
			// The user found was a student, they are not allowed to create course
			return null;
		} else if (!user.getName().equals(req.getParameter("professorName" + requestId))){
			System.out.println("break3");
			// The name field for the user did not match what was given
			return null;
		}
		
		// Create the course object
		Course course = new Course();
		course.setName(req.getParameter("courseName" + requestId));
		course.setProfessorId(user.getId());
		course.setTime(req.getParameter("time" + requestId));
		course.setCourseNumber(Integer.parseInt(req.getParameter("courseNumber" + requestId)));
		course.setSectionNumber(Integer.parseInt(req.getParameter("sectionNumber" + requestId)));
		course.setCredits(Integer.parseInt(req.getParameter("credits" + requestId)));
		course.setDays(req.getParameter("days" + requestId));
		course.setLocation(req.getParameter("location" + requestId));
		course.setCRN(Integer.parseInt(req.getParameter("CRN" + requestId)));
		course.setDescription(req.getParameter("description" + requestId));
		return course;
		
	}

	public ArrayList<PendingCourse> getPendingCourses() throws SQLException {
		return Database.getInstance().getPendingCourses();
	}

	public void removeCourse(HttpServletRequest req) throws NumberFormatException, SQLException {
		Database.getInstance().removePendingCourse(Integer.parseInt(req.getParameter("requestId")));
	}
}
