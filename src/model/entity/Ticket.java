package model.entity;

/**
 * This class implements the Ticket interface.
 */
public interface Ticket extends Entity {
	// Getters
	public boolean 	getPending();
	public boolean 	getActive();
	public boolean	getClosed();
	public int 		getWorkerID();
	public String	getSubject();

	// Setters
	public void 	setPending(boolean pending);
	public void 	setActive(boolean active);
	public void		setClosed(boolean closed);
	public void 	setWorkerID(int workerid);
	public void		setSubject(String subject);
};
