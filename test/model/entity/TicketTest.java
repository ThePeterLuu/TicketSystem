package model.entity;

import org.junit.Test;
import org.junit.After;

import junit.framework.TestCase;

/**
 * Tests a Ticket to verify that it holds the supplied data.
 * @author  Peter Luu
 */
public class TicketTest extends TestCase {
	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * By testing the constructor, we can implicitly test the getters and setters.
	 * We also test the model factory by doing this too.
	 */
	@Test
	public void testTicket() {
		// Test the constructor
		Ticket instance1 = ModelFactory.createTicket(12,"hello",true,false,false);
		assertEquals("Instance1 workerid",12,instance1.getWorkerID());
		assertEquals("Instance1 subject","hello",instance1.getSubject());
		assertEquals("Instance1 pending",true,instance1.getPending());
		assertEquals("Instance1 active",false,instance1.getActive());
		assertEquals("Instance1 closed",false,instance1.getClosed());
		assertEquals("Instance1 p_id",0,instance1.get_id());
		assertEquals("Instance1 p_written",false,instance1.get_isProxy());
		
		Ticket instance2 = ModelFactory.createTicket(15,22,"bye",false,true,false);
		assertEquals("Instance2 workerid",22,instance2.getWorkerID());
		assertEquals("Instance2 subject","bye",instance2.getSubject());
		assertEquals("Instance2 pending",false,instance2.getPending());
		assertEquals("Instance2 active",true,instance2.getActive());
		assertEquals("Instance2 closed",false,instance2.getClosed());
		assertEquals("Instance2 p_id",15,instance2.get_id());
		assertEquals("Instance2 p_written",true,instance2.get_isProxy());
		
		// Test behaviour of the setPending, setActive, setClosed
		instance2.setActive(false);
		assertEquals("Instance2 setAllFlagsFalse",true,instance2.getActive());
		instance2.setPending(true);
		assertEquals("Instance2 setPendingTrue",true,instance2.getPending());
		assertEquals("Instance2 activeQueueAfterPendingSet",false,instance2.getActive());
	}
}
