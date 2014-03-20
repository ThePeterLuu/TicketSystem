package model.entity.impl;

import model.entity.*;

/**
 * This class implements the Ticket object.
 */
public class TicketImpl implements Ticket {

	/*
	 * Attributes
	 */
	
	// Entity attributes
	private int p_id;
	private boolean p_written;
	
	// Ticket attributes
	private int workerID;
	private String subject;
	private boolean pending;
	private boolean active;
	private boolean closed;
	
	/*
	 * Constructors
	 */
	
	// Default constructor
	public TicketImpl() {
		// no default constructor. 
	}
	
	// New object constructor
	public TicketImpl(int workerid, String subject, boolean pending, boolean active, boolean closed) {
		setWorkerID(workerid);
		setSubject(subject);
		setPending(pending);
		setActive(active);
		setClosed(closed);
		p_id = 0;
		p_written = false;
	}
	
	// Fetch object from database constructor
	public TicketImpl(int dbid, int workerid, String subject, boolean pending, boolean active, boolean closed) {
		setWorkerID(workerid);
		setSubject(subject);
		setPending(pending);
		setActive(active);
		setClosed(closed);
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
	public String getSubject() {
		return subject;
	}
	
	public int getWorkerID() {
		return workerID;
	}
	
	public boolean getPending() {
		return pending;
	}
	
	public boolean getActive() {
		return active;
	}
	
	public boolean getClosed() {
		return closed;
	}
	
	// Setters
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public void setWorkerID(int workerID) {
		this.workerID = workerID;
	}

	public void setPending(boolean pending) {
		if (pending) {
			this.active = false;
			this.closed = false;
			this.pending = pending;
		} else {
			if (!getActive() && !getClosed()) {
				return;
			}
		}
	}
	
	public void setActive(boolean active) {
		if (active) {
			this.pending = false;
			this.closed = false;
			this.active = active;
		} else {
			if (!getPending() && !getClosed()) {
				return;
			}
		}
	}
	
	public void setClosed(boolean closed) {
		if (closed) {
			this.active = false;
			this.pending = false;
			this.closed = closed;
		} else {
			if (!getActive() && !getPending()) {
				return;
			}
		}
	}
}
