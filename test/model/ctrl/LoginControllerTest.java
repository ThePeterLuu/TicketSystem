package model.ctrl;

import org.junit.Test;
import org.junit.After;

import junit.framework.TestCase;

/**
 * Tests the Login Controller to ensure that it operates properly.
 * Must be run after the DBHelperTest and before the database is reset.
 * @author  Peter Luu
 */
public class LoginControllerTest extends TestCase {
	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * Tests the login controller. The reason these tests are short is because
	 * most of the functionality is proxying it to the DBHelper. These tests
	 * test the additional functionality on top.
	 */
	@Test
	public void testLoginController() {
		// test login
		
		assertEquals("invalid login", -1, LoginController.login("adg", "gfdsah"));
		assertEquals("worker login", 0, LoginController.login("bob", "sup"));
		assertEquals("manager login", 1, LoginController.login("joe", "bye"));
	}
}
