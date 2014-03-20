package model.entity;

import model.entity.impl.*;

/**
 * This class is the Model Factory for all entity objects.
 */
public class ModelFactory {
	// Account Constructors
	public static Account createAccount() {
		return new AccountImpl();
	}
	
	public static Account createAccount(String username, String password, boolean isManager) {
		return new AccountImpl(username, password, isManager);
	}
	
	public static Account createAccount(int dbid, String username, String password, boolean isManager) {
		return new AccountImpl(dbid, username, password, isManager);
	}
	
	// Ticket Constructors
	public static Ticket createTicket(){
		return new TicketImpl();
	}
	
	public static Ticket createTicket(int workerid, String subject, boolean pending, boolean active, boolean closed){
		return new TicketImpl(workerid, subject, pending, active, closed);
	}
	
	public static Ticket createTicket(int dbid, int workerid, String subject, boolean pending, boolean active, boolean closed){
		return new TicketImpl(dbid, workerid, subject, pending, active, closed);
	}

	// Annotation Constructors
	public static Annotation createAnnotation(){
		return new AnnotationImpl();
	}
	
	public static Annotation createAnnotation(String text, java.sql.Timestamp createdOn, int ticketid, int workerid){
		return new AnnotationImpl(text, createdOn, ticketid, workerid);
	}
	
	public static Annotation createAnnotation(int dbid, String text, java.sql.Timestamp createdOn, int ticketid, int workerid){
		return new AnnotationImpl(dbid, text, createdOn, ticketid, workerid);
	}
}
