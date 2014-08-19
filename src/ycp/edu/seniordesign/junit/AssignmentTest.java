package ycp.edu.seniordesign.junit;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import ycp.edu.seniordesign.model.Assignment;

public class AssignmentTest {
	// TODO: use the new constructor and test the new fields
	Assignment assign = new Assignment(1, 1, 1, "Test 1", null, 50, 50, 50);
	Assignment assign2 = new Assignment(1, 1, 1, "Test 2", null, 50, 50, 50);
	
	Date date = new Date(91, 8, 8);

	@Test
	public void test() {
		assertFalse(assign.equals(assign2));
		assertTrue(assign.equals(assign));
		assertEquals(assign.getId(),1);
		assertEquals(assign.getCourseId(), 1);
		assertEquals(assign.getStudentId(), 1);
		assertEquals(assign.getName(),"Test 1");
		assertEquals(assign.getDueDate(), null);
		assertEquals(assign.getGradeWeightType(),50);
		assertEquals(assign.getEarnedPoints(), 50);
		assertEquals(assign.getPossiblePoints(), 50);
		
		assign.setId(2);
		assign.setCourseId(2);
		assign.setStudentId(2);
		assign.setDueDate(date);
		assign.setName("Quiz 1");		
		assign.setGradeWeightType(25);
		assign.setEarnedPoints(25);
		assign.setPossiblePoints(25);
		
		assertEquals(assign.getId(),2);
		assertEquals(assign.getCourseId(), 2);
		assertEquals(assign.getStudentId(), 2);
		assertEquals(assign.getDueDate(), date);
		assertEquals(assign.getName(),"Quiz 1");
		assertEquals(assign.getGradeWeightType(),25);
		assertEquals(assign.getEarnedPoints(), 25);
		assertEquals(assign.getPossiblePoints(), 25);
		assertTrue(assign.equals(assign));
		assertTrue(assign.isOverdue());
	}
}
