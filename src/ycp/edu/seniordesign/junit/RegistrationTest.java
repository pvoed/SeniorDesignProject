package ycp.edu.seniordesign.junit;

import static org.junit.Assert.*;

import java.sql.Timestamp;

import org.junit.Test;

import ycp.edu.seniordesign.model.Registration;

public class RegistrationTest {
	String url = Registration.generateRandomURL();
	Timestamp current = new Timestamp(System.currentTimeMillis());
	Registration registration1 = new Registration(1, "username", "email@whiteboard.org", url, current);
	Registration registration2 = new Registration(1, "username", "email@whiteboard.org", url, current);
	
	@Test
	public void test() {
		assertTrue(registration1.equals(registration2));
		registration1.setId(2);
		registration1.setUsername("username2");
		registration1.setEmailAddress("email2@whiteboard.org");
		registration1.setUrl("url");
		registration1.setExpiration(new Timestamp(current.getTime() + Registration.VALID_DURATION_IN_HOURS));
		assertEquals(registration1.getId(), 2);
		assertEquals(registration1.getUsername(), "username2");
		assertEquals(registration1.getEmailAddress(), "email2@whiteboard.org");
		assertEquals(registration1.getUrl(), "url");
		assertEquals(registration1.getExpiration().getTime(), current.getTime() + Registration.VALID_DURATION_IN_HOURS);
		assertFalse(registration1.equals(registration2));
	}

}
