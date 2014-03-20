package model.entity.impl;

import model.entity.*;

/**
 * This class implements the Account object.
 */
public class AccountImpl implements Account {

	/*
	 * Attributes
	 */
	
	// Entity attributes
	private int p_id;
	private boolean p_written;
	
	// Account attributes
	private String username;
	private String password;
	private boolean manager;
	
	/*
	 * Constructors
	 */
	
	// Default constructor
	public AccountImpl() {
		// no default constructor. 
	}
	
	// New object constructor
	public AccountImpl(String username, String password, boolean isManager) {
		setUsername(username);
		setPassword(password);
		setManager(isManager);
		p_id = 0;
		p_written = false;
	}
	
	// Fetch object from database constructor
	public AccountImpl(int dbid, String username, String password, boolean isManager) {
		setUsername(username);
		setPassword(password);
		setManager(isManager);
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
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public boolean getManager() {
		return manager;
	}
	
	// Setters
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public void setManager(boolean isManager) {
		this.manager = isManager;
	}
}
