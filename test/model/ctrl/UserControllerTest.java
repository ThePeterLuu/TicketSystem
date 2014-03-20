package model.ctrl;

import org.junit.Test;
import org.junit.After;

import junit.framework.TestCase;

/**
 * Tests the User Controller to ensure that it operates properly.
 * Must be run after the DBHelperTest and before the database is reset.
 * @author  Peter Luu
 */
public class UserControllerTest extends TestCase {
	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * Tests the user controller. The reason these tests are short is because
	 * most of the functionality is proxying it to the DBHelper. These tests
	 * test the additional functionality on top.
	 */
	@Test
	public void testUserController() {
		// createAccount is a proxy function for already tested methods in DBHelper.
		
		// test checkPassword
		assertFalse("invalid password", UserController.checkPassword("joe", "blah"));
		assertTrue("valid password", UserController.checkPassword("joe", "bye"));
		
		// test changePassword
		assertFalse("change manager password", UserController.changePassword("joe", "hello"));
		assertTrue("change worker password", UserController.changePassword("bob", "sup"));
	}
}
