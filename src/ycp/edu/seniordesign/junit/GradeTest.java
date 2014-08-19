package ycp.edu.seniordesign.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import ycp.edu.seniordesign.model.Grade;

public class GradeTest {
	
	Grade grade = new Grade(1,902751410,1234567890,100);
	Grade grade2 = new Grade(1,902751411, 1234567890, 100);

	@Test
	public void test() {
		//fail("Not yet implemented");
		assertFalse(grade.equals(grade2));
		assertEquals(grade.getId(),1);
		assertEquals(grade.getStudentId(),902751410);
		assertEquals(grade.getCourseId(),1234567890);
		assertEquals(grade.getGrade(),100);
		
		grade.setId(2);
		grade.setStudentId(902751411);
		grade.setCourseId(1234567891);
		grade.setGrade(0);
		
		assertEquals(grade.getId(),2);
		assertEquals(grade.getStudentId(),902751411);
		assertEquals(grade.getCourseId(),1234567891);
		assertEquals(grade.getGrade(),0);
		assertEquals(grade.getStudentId(),grade2.getStudentId());
		assertTrue(grade.equals(grade));
	}
}
