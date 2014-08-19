package ycp.edu.seniordesign.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ycp.edu.seniordesign.model.Admin;

public class AdminTest {
	Admin admin1 = new Admin(1, "username", "password", "salt");
	Admin admin2 = new Admin(1, "username", "password", "salt");


	@Test
	public void test() {
		assertTrue(admin1.equals(admin2));
		admin1.setId(2);
		admin1.setUsername("username2");
		admin1.setHashedPassword("password2");
		admin1.setSalt("salt2");
		assertEquals(admin1.getId(), 2);
		assertEquals(admin1.getUsername(), "username2");
		assertEquals(admin1.getHashedPassword(), "password2");
		assertEquals(admin1.getSalt(), "salt2");
		assertFalse(admin1.equals(admin2));
	}
}
