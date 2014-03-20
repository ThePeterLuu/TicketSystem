package model.entity;

import org.junit.Test;
import org.junit.After;

import junit.framework.TestCase;

/**
 * Tests an Annotation to verify that it holds the supplied data.
 * @author  Peter Luu
 */
public class AnnotationTest extends TestCase {
	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * By testing the constructor, we can implicitly test the getters and setters.
	 * We also test the model factory by doing this too.
	 */
	@Test
	public void testAnnotation() {
		// Test the constructor
		java.util.Date date = new java.util.Date(1000L); // Not strictly necessary because we could directly initialize the timestamp but well, more comprehensive this way
		java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
		Annotation instance1 = ModelFactory.createAnnotation("hello", timestamp, 12, 15);
		assertEquals("Instance1 workerid",15,instance1.getWorkerID());
		assertEquals("Instance1 text","hello",instance1.getText());
		assertEquals("Instance1 ticketid",12,instance1.getTicketID());
		assertEquals("Instance1 datetime",1000L,instance1.getCreatedOn().getTime());
		assertEquals("Instance1 p_id",0,instance1.get_id());
		assertEquals("Instance1 p_written",false,instance1.get_isProxy());
		
		java.util.Date date2 = new java.util.Date(2000L);
		java.sql.Timestamp ts2 = new java.sql.Timestamp(date2.getTime());
		Annotation instance2 = ModelFactory.createAnnotation(17,"bye",ts2,20,26);
		assertEquals("Instance2 workerid",26,instance2.getWorkerID());
		assertEquals("Instance2 text","bye",instance2.getText());
		assertEquals("Instance2 ticketid",20,instance2.getTicketID());
		assertEquals("Instance2 datetime",2000L,instance2.getCreatedOn().getTime());
		assertEquals("Instance2 p_id",17,instance2.get_id());
		assertEquals("Instance2 p_written",true,instance2.get_isProxy());
	}
}
