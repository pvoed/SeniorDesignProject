package ycp.edu.seniordesign.junit;

import static org.junit.Assert.*;
import org.junit.Test;

import ycp.edu.seniordesign.model.EnrolledCourse;

public class EnrolledCourseTest {
	
	EnrolledCourse course = new EnrolledCourse(1,1,1,1,1);
	EnrolledCourse course2 = new EnrolledCourse(2,2,2,2,2);
	
	@Test
	public void test() {
		assertEquals(course.getId(),1);
		assertEquals(course.getStudentId(), 1);
		assertEquals(course.getProfessorId(), 1);
		assertEquals(course.getCourseId(), 1);
		assertEquals(course.getGrade(), 1);
		assertTrue(course.equals(course));
		assertFalse(course.equals(course2));
		assertTrue(course2.equals(course2));
		
		course.setId(2);
		course.setStudentId(2);
		course.setProfessorId(2);
		course.setCourseId(2);
		course.setGrade(2);
		
		assertEquals(course.getId(),2);
		assertEquals(course.getStudentId(), 2);
		assertEquals(course.getProfessorId(), 2);
		assertEquals(course.getCourseId(), 2);
		assertEquals(course.getGrade(), 2);
		assertTrue(course.equals(course));
		assertTrue(course.equals(course2));
	}

}
