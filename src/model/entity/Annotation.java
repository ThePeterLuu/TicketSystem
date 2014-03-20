package model.entity;

/**
 * This class implements the Annotation interface.
 */
public interface Annotation extends Entity {
	// Getters
	public java.sql.Timestamp 	getCreatedOn();
	public int 	getTicketID();
	public int 	getWorkerID();
	public String	getText();

	// Setters
	public void 	setCreatedOn(java.sql.Timestamp createdon);
	public void 	setTicketID(int ticketid);
	public void 	setWorkerID(int workerid);
	public void		setText(String text);
};
