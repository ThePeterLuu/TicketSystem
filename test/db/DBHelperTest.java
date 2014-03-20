package db;

import model.entity.*;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.After;

import junit.framework.TestCase;

/**
 * Tests the DBHelper to verify that it holds the supplied data.
 * @author  Peter Luu
 */
public class DBHelperTest extends TestCase {
	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * By testing the constructor, we can implicitly test the getters and setters.
	 * We also test the model factory by doing this too.
	 * This test only works with a blank database and the database should be cleared after running the test,
	 * since it has many side-effects.
	 */
	@Test
	public void testDBHelper() {
		// Construct this thing!
		DBHelper instance1 = DBHelperFactory.createDBHelper();
		
		/*
		 * Account tests (this also includes the password hash test implicitly)
		 */
		 
		// Test the store account method and retrieve by ID in one fell swoop
		Account temp = ModelFactory.createAccount(0,"joe","hello",true);
		int dbid = instance1.storeAccount(temp);
		Account retrieved = instance1.retrieveAccount(dbid);
		assertEquals("Instance1 properly stores/retrieves dbid",dbid,retrieved.get_id());
		assertEquals("Instance1 properly stores/retrieves username","joe",retrieved.getUsername());
		assertEquals("Instance1 properly stores/retrieves password","2CF24DBA5FB0A30E26E83B2AC5B9E29E1B161E5C1FA7425E73043362938B9824",retrieved.getPassword());
		assertEquals("Instance1 properly stores/retrieves manager",true,retrieved.getManager());
		
		// Test the retrieve by username method
		retrieved = instance1.retrieveAccount("joe");
		assertEquals("Instance1 properly retrieves dbid by username",dbid,retrieved.get_id());
		assertEquals("Instance1 properly retrieves username by username","joe",retrieved.getUsername());
		assertEquals("Instance1 properly retrieves password by username","2CF24DBA5FB0A30E26E83B2AC5B9E29E1B161E5C1FA7425E73043362938B9824",retrieved.getPassword());
		assertEquals("Instance1 properly retrieves manager by username",true,retrieved.getManager());
		
		// Test the updateAccountPassword method
		instance1.updateAccountPassword("joe","bye");
		retrieved = instance1.retrieveAccount("joe");
		assertEquals("Instance1 properly changes password","B49F425A7E1F9CFF3856329ADA223F2F9D368F15A00CF48DF16CA95986137FE8",retrieved.getPassword());
		
		// Test the createUsernameArraylist method
		temp = ModelFactory.createAccount(0,"bob","sup",false);
		instance1.storeAccount(temp);
		
		ArrayList<String> usernames = instance1.createUsernameArrayList();
		assertTrue("Instance1 properly retrieves a list of usernames",usernames.size() == 2);
		assertTrue("Instance1 contains usernames properly pt1",usernames.contains("joe"));
		assertTrue("Instance1 contains usernames properly pt2",usernames.contains("bob"));
		
		/*
		 * Ticket tests
		 */
		
		// Store and retrieve again are combined here!
		Ticket temp2 = ModelFactory.createTicket(0, 1, "hello", true, false, false);
		dbid = instance1.storeTicket(temp2);
		Ticket retrieved2 = instance1.retrieveTicket(dbid);
		assertEquals("Instance1 properly stores/retrieves ticket dbid",dbid,retrieved2.get_id());
		assertEquals("Instance1 properly stores/retrieves ticket workeridfk",1,retrieved2.getWorkerID());
		assertEquals("Instance1 properly stores/retrieves ticket subject","hello",retrieved2.getSubject());
		assertTrue("Instance1 properly stores/retrieves ticket pending status",retrieved2.getPending());
		assertFalse("Instance1 properly stores/retrieves ticket active status",retrieved2.getActive());
		assertFalse("Instance1 properly stores/retrieves ticket closed status",retrieved2.getClosed());
		
		// Test the updateTicketQueue method
		instance1.updateTicketQueue(dbid, 1);
		retrieved2 = instance1.retrieveTicket(dbid);
		assertFalse("Instance1 properly updates pending status",retrieved2.getPending());
		assertTrue("Instance1 properly updates ticket active status",retrieved2.getActive());
		assertFalse("Instance1 properly updates ticket closed status",retrieved2.getClosed());
		
		instance1.updateTicketQueue(dbid, 2);
		retrieved2 = instance1.retrieveTicket(dbid);
		assertFalse("Instance1 properly updates pending status",retrieved2.getPending());
		assertFalse("Instance1 properly updates ticket active status",retrieved2.getActive());
		assertTrue("Instance1 properly updates ticket closed status",retrieved2.getClosed());
		
		instance1.updateTicketQueue(dbid, 0);
		retrieved2 = instance1.retrieveTicket(dbid);
		assertTrue("Instance1 properly updates pending status",retrieved2.getPending());
		assertFalse("Instance1 properly updates ticket active status",retrieved2.getActive());
		assertFalse("Instance1 properly updates ticket closed status",retrieved2.getClosed());
		
		// Test the updateTicketWorker method
		instance1.updateTicketWorker(dbid, 2);
		retrieved2 = instance1.retrieveTicket(dbid);
		assertEquals("Instance 1 properly updates the ticket worker",2,retrieved2.getWorkerID());
		
		// Test the createTicketArrayListByQueue method
		temp2 = ModelFactory.createTicket(0, 1, "hello", true, false, false);
		instance1.storeTicket(temp2);
		temp2 = ModelFactory.createTicket(0, 1, "hello", true, false, false);
		instance1.storeTicket(temp2);
		temp2 = ModelFactory.createTicket(0, 1, "hello", false, true, false);
		instance1.storeTicket(temp2); // There should be 3 in pending, 1 in active, 0 in closed
		
		ArrayList<Ticket> ticketArray = instance1.createTicketArrayListByQueue(0);
		assertTrue("Instance1 properly retrieves a list of tickets by queue",ticketArray.size() == 3);
		ticketArray = instance1.createTicketArrayListByQueue(1);
		assertTrue("Instance1 properly retrieves a list of tickets by queue",ticketArray.size() == 1);
		ticketArray = instance1.createTicketArrayListByQueue(2);
		assertTrue("Instance1 properly retrieves a list of tickets by queue",ticketArray.size() == 0);
		
		temp2 = ModelFactory.createTicket(0, 2, "hello", false, false, true);
		instance1.storeTicket(temp2);
		ticketArray = instance1.createTicketArrayListByQueue(2);
		assertTrue("Instance1 properly retrieves a list of tickets by queue",ticketArray.size() == 1);
		
		// Now there should be 3 tickets for worker1 and 2 ticket for worker2
		ticketArray = instance1.createTicketArrayListByWorker(1);
		assertTrue("Instance1 properly retrieves a list of tickets by worker",ticketArray.size() == 3);
		ticketArray = instance1.createTicketArrayListByWorker(2);
		assertTrue("Instance1 properly retrieves a list of tickets by worker",ticketArray.size() == 2);
		
		
		/*
		 * Annotation tests
		 */
		
		// We test store and retrieve in one fell swoop
		
		java.util.Date date = new java.util.Date(12000L);
		java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
		Annotation temp3 = ModelFactory.createAnnotation(0, "text", timestamp, 3, 2);
		instance1.storeAnnotation(temp3);
		
		java.util.Date date2 = new java.util.Date(42000L);
		java.sql.Timestamp timestamp2 = new java.sql.Timestamp(date2.getTime());
		temp3 = ModelFactory.createAnnotation(0, "text2", timestamp2, 3, 1);
		instance1.storeAnnotation(temp3);
		
		temp3 = ModelFactory.createAnnotation(0, "text3", timestamp, 2, 1);
		dbid = instance1.storeAnnotation(temp3);
		
		ArrayList<Annotation> annoArray = instance1.createAnnotationArrayListByTicket(2);
		assertTrue("Instance1 properly retrieves 1 annotation for ticket2",annoArray.size() == 1);
		Annotation retrieved4 = annoArray.get(0);
		assertEquals("Instance1 annotation store/retrieve dbid",dbid,retrieved4.get_id());
		assertEquals("Instance1 annotation store/retrieve text","text3",retrieved4.getText());
		assertEquals("Instance1 annotation store/retrieve timestamp",timestamp.getTime(),retrieved4.getCreatedOn().getTime());
		assertEquals("Instance1 annotation store/retrieve ticketId",2,retrieved4.getTicketID());
		assertEquals("Instance1 annotaiton store/retrieve workerId",1,retrieved4.getWorkerID());
		
		annoArray = instance1.createAnnotationArrayListByTicket(3);
		assertTrue("Instance1 properly retrieves 2 annotations for ticket3",annoArray.size() == 2);
		
		annoArray = instance1.createAnnotationArrayListByTicket(4);
		assertTrue("Instance1 properly retrieves 0 annotations for ticket4",annoArray.size() == 0);
		
		instance1.close();
	}
}
