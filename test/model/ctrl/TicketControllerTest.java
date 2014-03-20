package model.ctrl;

import java.util.ArrayList;
import model.entity.*;

import org.junit.Test;
import org.junit.After;

import junit.framework.TestCase;

/**
 * Tests the Ticket Controller to ensure that it operates properly.
 * Must be run after the DBHelperTest and before the database is reset.
 * @author  Peter Luu
 */
public class TicketControllerTest extends TestCase {
	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * Tests the ticket controller.
	 */
	@Test
	public void testTicketController() {
		// Tests getTicketsByQueue
		ArrayList<Ticket> pending = TicketController.getTicketsByQueue("pending");
		ArrayList<Ticket> active = TicketController.getTicketsByQueue("active");
		ArrayList<Ticket> closed = TicketController.getTicketsByQueue("closed");
		
		assertEquals("pending ticketsbyqueue size", 3, pending.size());
		assertEquals("active ticketsbyqueue size", 1, active.size());
		assertEquals("closed ticketsbyqueue size", 1, closed.size());
		
		Ticket tempTicket = pending.get(pending.size() - 1);
		
		assertEquals("ticket data integrity", "hello", tempTicket.getSubject());
		assertTrue("ticket data integrity", tempTicket.getPending());
		assertFalse("ticket data integrity", tempTicket.getActive());
		assertFalse("ticket data integrity", tempTicket.getClosed());
		
		// Tests getLastActivity
		assertEquals("getLastActivity", "31/12/1969 19:00:42", TicketController.getLastActivity(3));
		
		// createNewTicket is a proxy function for already tested methods in DBHelper.
		
		// Tests getWorkerNames
		ArrayList<String> workerNames = TicketController.getWorkerNames();
		assertTrue("workernames",workerNames.size() == 2);
		assertTrue("workernames",workerNames.contains("joe"));
		assertTrue("workernames",workerNames.contains("bob"));
		
		// closeTicket and getTicket and getAnnotations are proxy functions for already tested methods in DBHelper.
		
		// Test getWorkerName
		assertEquals("getIndividualWorkerName", "joe", TicketController.getWorkerName(1));
	}
}
