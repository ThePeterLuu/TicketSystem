package model.entity;

/**
 * This class implements the Account interface.
 */
public interface Account extends Entity {
	// Getters
	public String	getUsername();
	public String	getPassword();
	public boolean	getManager();
	
	// Setters
	public void		setUsername(String username);
	public void		setPassword(String password);
	public void 	setManager(boolean isManager);
}
