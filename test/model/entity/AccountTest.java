package model.entity;

import org.junit.Test;
import org.junit.After;

import junit.framework.TestCase;

/**
 * Tests an account to verify that it holds the supplied data.
 * @author  Peter Luu
 */
public class AccountTest extends TestCase {
	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * By testing the constructor, we can implicitly test the getters and setters.
	 * We also test the model factory by doing this too.
	 */
	@Test
	public void testAccount() {
		// Test the constructor
		Account instance1 = ModelFactory.createAccount("joe","passhash",true);
		assertEquals("Instance1 Username","joe",instance1.getUsername());
		assertEquals("Instance1 Password hash","passhash",instance1.getPassword());
		assertEquals("Instance1 Manager flag",true,instance1.getManager());
		assertEquals("Instance1 p_id",0,instance1.get_id());
		assertEquals("Instance1 p_written",false,instance1.get_isProxy());
		
		Account instance2 = ModelFactory.createAccount(120,"bob","passhash2",false);
		assertEquals("Instance2 Username","bob",instance2.getUsername());
		assertEquals("Instance2 Password hash","passhash2",instance2.getPassword());
		assertEquals("Instance2 Manager flag",false,instance2.getManager());
		assertEquals("Instance2 p_id",120,instance2.get_id());
		assertEquals("Instance2 p_written",true,instance2.get_isProxy());
	}
}
