package db;

import java.util.ArrayList;

import model.entity.*;

public interface DBHelper {
	// Account storage and retrieval
	public Account retrieveAccount(int id);
	public Account retrieveAccount(String username);
	public int storeAccount(Account acc);
	public void updateAccountPassword(String username, String password);
	public ArrayList<String> createUsernameArrayList();
	
	// Ticket storage and retrieval
	public Ticket retrieveTicket(int id);
	public int storeTicket(Ticket tick);
	public void updateTicketQueue(int id, int queueType);
	public void updateTicketWorker(int id, int workerId);
	public ArrayList<Ticket> createTicketArrayListByQueue(int queueType);
	public ArrayList<Ticket> createTicketArrayListByWorker(int workerId);
	
	// Annotation storage and retrieval
	public int storeAnnotation(Annotation anno);
	public ArrayList<Annotation> createAnnotationArrayListByTicket(int ticketId);
	
	// Helper function
	public String toHexString(byte[] byteArray);
	
	// Closing function
	public void close();
};
