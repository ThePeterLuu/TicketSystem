package model.entity.impl;

import model.entity.*;

/**
 * This class implements the Annotation object.
 */
public class AnnotationImpl implements Annotation {
	/*
	 * Attributes
	 */
	
	// Entity attributes
	private int p_id;
	private boolean p_written;
	
	// Ticket attributes
	private java.sql.Timestamp createdOn;
	private int ticketID;
	private int workerID;
	private String text;
	
	/*
	 * Constructors
	 */
	
	// Default constructor
	public AnnotationImpl() {
		// no default constructor. 
	}
	
	// New object constructor
	public AnnotationImpl(String text, java.sql.Timestamp createdOn, int ticketID, int workerID) {
		setWorkerID(workerID);
		setText(text);
		setCreatedOn(createdOn);
		setTicketID(ticketID);
		p_id = 0;
		p_written = false;
	}
	
	// Fetch object from database constructor
	public AnnotationImpl(int dbid, String text, java.sql.Timestamp createdOn, int ticketID, int workerID) {
		setWorkerID(workerID);
		setText(text);
		setCreatedOn(createdOn);
		setTicketID(ticketID);
		p_id = dbid;
		p_written = true;
	}
	
	/*
	 * Methods
	 */
	
	// Entity methods
	public boolean get_isProxy() {
		return this.p_written;
	}
	
	public void set_isProxy() {
		this.p_written = true;
	}
	
	public int get_id() {
		return this.p_id;
	}
	
	public void set_id(int id) {
		this.p_id = id;
	}
	
	// Getters
	public String getText() {
		return text;
	}
	
	public int getWorkerID() {
		return workerID;
	}
	
	public int getTicketID() {
		return ticketID;
	}
	
	public java.sql.Timestamp getCreatedOn() {
		return createdOn;
	}
	
	// Setters
	public void setText(String text) {
		this.text = text;
	}
	
	public void setWorkerID(int workerID) {
		this.workerID = workerID;
	}

	public void setTicketID(int ticketID) {
		this.ticketID = ticketID;
	}
	
	public void setCreatedOn(java.sql.Timestamp createdOn) {
		this.createdOn = createdOn;
	}
}
