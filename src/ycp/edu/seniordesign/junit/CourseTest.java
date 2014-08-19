package ycp.edu.seniordesign.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import ycp.edu.seniordesign.model.Course;

public class CourseTest {
	
	Course course = new Course(1, "Calc",9, "8AM - 9AM", 320, 101, 4, "MWF", "KEC 119", 123456, "This is a math class.");
	Course course2 = new Course(1, "Calc 2",9 , "8AM - 9AM", 320, 101, 4, "MWF", "KEC 119", 123456, "This is a math class.");	
	
	@Test
	public void test() {
		assertEquals(course.getId(),1);
		assertEquals(course.getName(),"Calc");
		assertEquals(course.getProfessorId(), 9);
		assertEquals(course.getTime(),"8AM - 9AM");
		assertEquals(course.getCourseNumber(),320);
		assertEquals(course.getSectionNumber(),101);
		assertEquals(course.getCredits(),4);
		assertEquals(course.getDays(),"MWF");
		assertEquals(course.getLocation(),"KEC 119");
		assertEquals(course.getCRN(),123456);
		assertEquals(course.getDescription(),"This is a math class.");
		assertTrue(course.equals(course));
		assertFalse(course.equals(course2));
		assertTrue(course2.equals(course2));
		
		course.setId(2);		
		course.setName("Calc 2");	
		course.setProfessorId(9);
		course.setTime("9AM - 10AM");
		course.setCourseNumber(100);
		course.setSectionNumber(105);
		course.setCredits(3);
		course.setDays("TR");
		course.setLocation("HUM 128");
		course.setCRN(654321);
		course.setDescription("This is a harder math class.");
		
		assertEquals(course.getId(),2);
		assertEquals(course.getName(),"Calc 2");
		assertEquals(course.getProfessorId(), 9);
		assertEquals(course.getTime(),"9AM - 10AM");
		assertEquals(course.getCourseNumber(),100);
		assertEquals(course.getSectionNumber(),105);
		assertEquals(course.getCredits(),3);
		assertEquals(course.getDays(),"TR");
		assertEquals(course.getLocation(),"HUM 128");
		assertEquals(course.getCRN(),654321);
		assertEquals(course.getDescription(),"This is a harder math class.");
		assertTrue(course.equals(course));
		assertFalse(course.equals(course2));		
	}	
}
