package model.ctrl;

import java.util.ArrayList;
import java.text.SimpleDateFormat;

import model.entity.*;
import db.*;

/**
 * This class manages ticket-related database access. Liasons between the DBHelper and the calling servlet.
 */
public class TicketController {
	/**
	 * Returns an ArrayList of tickets by queue
	 */
	public static ArrayList<Ticket> getTicketsByQueue(String queueName) {
		DBHelper db = DBHelperFactory.createDBHelper();

		int queueType = 0;
		
		String requestedQueue = queueName.toLowerCase();
		
		if (requestedQueue.equals("pending")) {
			queueType = 0;
		}
		if (requestedQueue.equals("active")) {
			queueType = 1;
		}
		if (requestedQueue.equals("closed")) {
			queueType = 2;
		}
		
		ArrayList<Ticket> result = db.createTicketArrayListByQueue(queueType);
		
		db.close();
		return result;
	}
	
	/**
	 * Returns a formatted string that indicates the last time a Ticket was worked on
	 * (in other words, the date of the last annotation)
	 */
	public static String getLastActivity(int ticketNumber) {
		DBHelper db = DBHelperFactory.createDBHelper();
		ArrayList<Annotation> annos = db.createAnnotationArrayListByTicket(ticketNumber);
		
		java.sql.Timestamp lastActivity = new java.sql.Timestamp(0L);
		
		for (int i = 0; i < annos.size(); i++) {
			java.sql.Timestamp ts = annos.get(i).getCreatedOn();
			
			if (ts.after(lastActivity)) {
				lastActivity.setTime(ts.getTime());
			}
		}

		String result = getTime(lastActivity);
		
		db.close();
		return result;
	}
	
	/**
	 * Creates a new Ticket based on user input for the subject. Returns the Ticket ID.
	 */
	public static int createNewTicket(String desc) {
		DBHelper db = DBHelperFactory.createDBHelper();
		Ticket tick = ModelFactory.createTicket(0, desc, true, false, false);
		int dbid = db.storeTicket(tick);
		
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp ts = new java.sql.Timestamp(date.getTime());
		Annotation open_ticket_annotation = ModelFactory.createAnnotation("Ticket has been opened.", ts, dbid, 0);
		db.storeAnnotation(open_ticket_annotation);
		
		db.close();
		return dbid;
	}
	
	/**
	 * Returns an ArrayList of all the registered accounts
	 */
	public static ArrayList<String> getWorkerNames() {
		DBHelper db = DBHelperFactory.createDBHelper();
		ArrayList<String> result = db.createUsernameArrayList();
		
		db.close();
		return result;
	}
	
	/**
	 * Closes a specified ticket.
	 */
	public static void closeTicket(int ticketNum) {
		DBHelper db = DBHelperFactory.createDBHelper();
		db.updateTicketQueue(ticketNum, 2);
		db.close();
	}
	
	/**
	 * Gets a specified ticket.
	 */
	public static Ticket getTicket(int ticketNum) {
		DBHelper db = DBHelperFactory.createDBHelper();
		Ticket result = db.retrieveTicket(ticketNum);
		
		db.close();
		return result;
	}
	
	/**
	 * Returns an ArrayList of Annotations with a TicketID.
	 */
	public static ArrayList<Annotation> getAnnotations(int ticketNum) {
		DBHelper db = DBHelperFactory.createDBHelper();
		ArrayList<Annotation> result = db.createAnnotationArrayListByTicket(ticketNum);
		
		db.close();
		return result;
	}
	
	/**
	 * Returns a String for a workername by workerid.
	 */
	public static String getWorkerName(int workerid) {
		if (workerid == 0) {
			return "TicketSystem";
		}
			
		DBHelper db = DBHelperFactory.createDBHelper();
		Account temp = db.retrieveAccount(workerid);
		String result = temp.getUsername();
		
		db.close();
		return result;
	}
	
	/**
	 * Helper function. Returns a formatted String given a Timestamp.
	 */
	public static String getTime(java.sql.Timestamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String time = sdf.format(ts);
		
		return time;
	}
	
	/**
	 * Assigns a worker by name to a ticket by ID.
	 */
	public static void assignWorker(int ticketNum, String workername) {
		DBHelper db = DBHelperFactory.createDBHelper();
		Account temp = db.retrieveAccount(workername);
		db.updateTicketWorker(ticketNum, temp.get_id());
		db.updateTicketQueue(ticketNum, 1);
		db.close();
	}
	
	/**
	 * Creates a new Annotation with specified text, ticket ID, and worker name.
	 */
	public static void createNewAnnotation(String text, int ticketid, String workername) {
		DBHelper db = DBHelperFactory.createDBHelper();
		
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp ts = new java.sql.Timestamp(date.getTime());
		Account temp = db.retrieveAccount(workername);
		
		Annotation anno = ModelFactory.createAnnotation(text, ts, ticketid, temp.get_id());
		db.storeAnnotation(anno);
		db.close();
	}
	
	/**
	 * Returns an ArrayList of Tickets by worker username.
	 */
	public static ArrayList<Ticket> getActiveTicketsByWorker(String username) {
		DBHelper db = DBHelperFactory.createDBHelper();
		Account temp = db.retrieveAccount(username);
		ArrayList<Ticket> temp2 = db.createTicketArrayListByWorker(temp.get_id());
		ArrayList<Ticket> result = new ArrayList<Ticket>();
		
		for (int i = 0; i < temp2.size(); i++) {
			if (temp2.get(i).getActive()) {
				result.add(temp2.get(i));
			}
		}
		
		db.close();
		return result;
	}
	
	/**
	 * Returns the last Annotation by date attached to a specified TicketID.
	 */
	public static Annotation getLastAnnotation(int ticketID) {
		DBHelper db = DBHelperFactory.createDBHelper();
		ArrayList<Annotation> temp = db.createAnnotationArrayListByTicket(ticketID);
		
		Annotation result = temp.get(temp.size() - 1);
		
		db.close();
		return result;
	}
}
