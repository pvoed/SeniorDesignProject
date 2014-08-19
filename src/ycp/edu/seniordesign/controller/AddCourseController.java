package ycp.edu.seniordesign.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import ycp.edu.seniordesign.model.PendingCourse;
import ycp.edu.seniordesign.model.User;
import ycp.edu.seniordesign.model.persist.Database;

public class AddCourseController {

	public void addPendingCourse(PendingCourse pendingCourse) throws SQLException {
		Database.getInstance().addPendingCourse(pendingCourse);
	}
	
	public PendingCourse setPendingCourse(HttpServletRequest req) throws SQLException {
		// Retrieve the user object from session
		User user = (User) req.getSession().getAttribute("user");
		
		// Create the course object
		PendingCourse course = new PendingCourse();
		course.setCourseName(req.getParameter("courseName"));
		course.setProfessorName(user.getName());
		course.setEmailAddress(user.getEmailAddress());
		course.setTime(req.getParameter("time"));
		course.setCourseNumber(Integer.parseInt(req.getParameter("courseNumber")));
		course.setCourseSection(Integer.parseInt(req.getParameter("sectionNumber")));
		course.setCredits(Integer.parseInt(req.getParameter("credits")));
		course.setDays(req.getParameter("days"));
		course.setLocation(req.getParameter("location"));
		course.setCRN(Integer.parseInt(req.getParameter("CRN")));
		course.setDescription(req.getParameter("description"));
		return course;
		
	}

}
