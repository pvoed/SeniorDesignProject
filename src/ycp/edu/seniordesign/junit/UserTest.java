package ycp.edu.seniordesign.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import ycp.edu.seniordesign.model.User;

public class UserTest {

	User user = new User(1, "msteppe", "Mike Steppe", "password", "salt", "msteppe@ycp.edu" , 0, "CS", true, "12345367890", "HUM 111", "Biography goes here");
	User user2 = new User(1,"msteppe", "Mike Steppe", "password", "salt", "msteppe@ycp.edu" , 0, "CS", true, "2222222222", "HUM 111", "Biography goes there");
	
	@Test
	public void test() {
		assertFalse(user.equals(user2));
		assertEquals(user.getId(),1);
		assertEquals(user.getUsername(),"msteppe");
		assertEquals(user.getName(), "Mike Steppe");
		assertEquals(user.getEmailAddress(),"msteppe@ycp.edu");
		assertEquals(user.getPassword(),"password");
		assertEquals(user.getSalt(),"salt");
		assertEquals(user.getType(),0);
		assertEquals(user.getMajor(), "CS");
		assertTrue(user.isCommuter());
		assertEquals(user.getPhoneNumber(),"12345367890");
		assertEquals(user.getOfficeNumber(),"HUM 111");
		assertEquals(user.getBiography(),"Biography goes here");
		
		user.setId(2);
		user.setUsername("nbrady");
		user.setName("Nick Brady");
		user.setEmailAddress("nbrady@ycp.edu");
		user.setPassword("incorrect");
		user.setSalt("saltier");
		user.setType(1);
		user.setMajor("Math");
		user.setCommuter(false);
		user.setPhoneNumber("2222222222");
		user.setOfficeNumber("HUM 123");
		user.setBiography("Biography goes there");
		
		assertEquals(user.getId(),2);
		assertEquals(user.getUsername(),"nbrady");
		assertEquals(user.getName(),"Nick Brady");
		assertEquals(user.getEmailAddress(),"nbrady@ycp.edu");
		assertEquals(user.getPassword(),"incorrect");
		assertEquals(user.getSalt(),"saltier");
		assertEquals(user.getType(),1);
		assertEquals(user.getMajor(), "Math");
		assertFalse(user.isCommuter());
		assertTrue(user.equals(user));
		assertFalse(user.equals(user2));
		
		assertFalse(user.isProfessor());
	}

}
