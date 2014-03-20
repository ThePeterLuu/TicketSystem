package db.impl;

import java.util.ArrayList;
import java.io.UnsupportedEncodingException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import db.*;
import model.entity.*;

/**
 *	This class is responsible for retrieving, storing, and updating data to and from the database.
 */
public class DBHelperImpl implements DBHelper {
	private Connection conn = null;

	/**
	 *	Constructs the DBHelper.
	 */
	public DBHelperImpl(String url, String user, String pass) {
		try {
			// create the driver for MySQL
			Class.forName("com.mysql.jdbc.Driver");
			// establish the database connection
			conn = DriverManager.getConnection(url, user, pass);
		} catch(SQLException e) {
			System.out.println("Exception in database setup: " + e.getMessage());
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *	Closing function for the DBHelper.
	 */
	public void close() {
		try {
			if (conn != null) { 
				conn.close();
			}
		} catch (Exception e) {
		}
	}
	
	// Account Methods
	
	/**
	 *	Returns null if there's no valid account with the username
	 */
	public Account retrieveAccount(int id) {
		Account result = null;
		ResultSet rs = null;
		PreparedStatement statement = null;
		
		try {
			statement = conn.prepareStatement("select * from Accounts where WorkerID = ?");
			statement.setInt(1, id);
		
			int workerid;
			String username;
			String pass;
			boolean manager;
			
			rs = statement.executeQuery();
			if (rs.next()) {
				workerid = rs.getInt("WorkerID");
				username = rs.getString("Username");
				pass = rs.getString("Password");
				manager = rs.getBoolean("Manager");
		  
				result = ModelFactory.createAccount(workerid, username, pass, manager);
			} else {
			}
		} catch(SQLException e){
			e.printStackTrace();
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {}
			try { if (statement != null) statement.close(); } catch (Exception e) {}
		}
		
		return result;
	}

	/**
	 *	Returns null if there's no valid account with the username
	 */
	public Account retrieveAccount(String user) {
		Account result = null;
		ResultSet rs = null;
		PreparedStatement statement = null;
		
		try {
			statement = conn.prepareStatement("select * from Accounts where Username = ?");
			statement.setString(1, user);
		
			int workerid;
			String username;
			String pass;
			boolean manager;
			
			rs = statement.executeQuery();
			if (rs.next()) {
				workerid = rs.getInt("WorkerID");
				username = rs.getString("Username");
				pass = rs.getString("Password");
				manager = rs.getBoolean("Manager");
		  
				result = ModelFactory.createAccount(workerid, username, pass, manager);
			} else {
			}
		} catch(SQLException e){
			e.printStackTrace();
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {}
			try { if (statement != null) statement.close(); } catch (Exception e) {}
		}
		
		return result;
	}

	/**
	 *	Stores an account in the database.
	 */
	public int storeAccount(Account acc) {
		int result = 0;
		ResultSet rs = null;
		PreparedStatement statement = null;
		
		try {
			statement = conn.prepareStatement("insert into Accounts (Username, Password, Manager) values (?,?,?)", Statement.RETURN_GENERATED_KEYS);
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(acc.getPassword().getBytes("UTF-8"));
			
			statement.setString(1, acc.getUsername());
			statement.setString(2, toHexString(hash));
			statement.setBoolean(3, acc.getManager());
			statement.executeUpdate();
			
			rs = statement.getGeneratedKeys();
			rs.next();
			
			result = rs.getInt(1);
		} catch(SQLException e){
			e.printStackTrace();
		} catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		} catch(UnsupportedEncodingException e){
			e.printStackTrace();
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {}
			try { if (statement != null) statement.close(); } catch (Exception e) {}
		}
		
		return result;
	}
	
	/**
	 *	Updates an account password.
	 */
	public void updateAccountPassword(String user, String password) {
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement("update Accounts set Password=? where Username=?");
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(password.getBytes("UTF-8"));
			
			statement.setString(1,toHexString(hash));
			statement.setString(2,user);
			statement.executeUpdate();
		} catch(SQLException e){
			e.printStackTrace();
		} catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		} catch(UnsupportedEncodingException e){
			e.printStackTrace();
		} finally {
			try { if (statement != null) statement.close(); } catch (Exception e) {}
		}
	}
	
	/**
	 *	Returns an ArrayList of all usernames of all accounts that exist in the database.
	 */
	public ArrayList<String> createUsernameArrayList() {
		ArrayList<String> usernames = new ArrayList<String>();
		ResultSet rs = null;
		PreparedStatement statement = null;
		
		try {
			statement = conn.prepareStatement("select Username from Accounts");	
			rs = statement.executeQuery();

			while (rs.next()) {
				usernames.add(rs.getString("Username"));
			}	
		} catch(SQLException e){
			e.printStackTrace();
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {}
			try { if (statement != null) statement.close(); } catch (Exception e) {}
		}
		
		return usernames;
	}

	/**
	 *	Returns a ticket object with the specified ID. Returns null if there is no ticket with that ID.
	 */
	public Ticket retrieveTicket(int id) {
		Ticket result = null;
		ResultSet rs = null;
		PreparedStatement statement = null;
		
		try {
			statement = conn.prepareStatement("select * from Tickets where TicketID = ?");
			statement.setInt(1, id);
		
			int workerid_fk;
			int ticketid;
			String subject;
			boolean pending;
			boolean active;
			boolean closed;

			rs = statement.executeQuery();
			
			if (rs.next()) {
				workerid_fk = rs.getInt("WorkerID_FK");
				ticketid = rs.getInt("TicketID");
				subject = rs.getString("Subject");
				pending = rs.getBoolean("Pending");
				active = rs.getBoolean("active");
				closed = rs.getBoolean("closed");
		  
				result = ModelFactory.createTicket(ticketid, workerid_fk, subject, pending, active, closed);
			} else {
			}
		} catch(SQLException e){
			e.printStackTrace();
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {}
			try { if (statement != null) statement.close(); } catch (Exception e) {}
		}
		
		return result;
	}
	
	/**
	 *	Stores a ticket.
	 */
	public int storeTicket(Ticket tick) {
		int result = 0;
		ResultSet rs = null;
		PreparedStatement statement = null;
		
		try {
			statement = conn.prepareStatement("insert into Tickets (WorkerID_FK, Subject, Pending, Active, Closed) values (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			
			statement.setInt(1, tick.getWorkerID());
			statement.setString(2, tick.getSubject());
			statement.setBoolean(3, tick.getPending());
			statement.setBoolean(4, tick.getActive());
			statement.setBoolean(5, tick.getClosed());
			statement.executeUpdate();
			
			rs = statement.getGeneratedKeys();
			rs.next();
			
			result = rs.getInt(1);
		} catch(SQLException e){
			e.printStackTrace();
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {}
			try { if (statement != null) statement.close(); } catch (Exception e) {}
		}
		
		return result;
	}
	
	/**
	 *	Updates a ticket's queue type. Will have no effect if ticket ID does not exist.
	 *  If an invalid queue number is entered, it will set to pending queue.
	 */
	public void updateTicketQueue(int id, int queueType) {
		PreparedStatement statement = null;
		try {
			switch (queueType) {
				case 0:
					statement = conn.prepareStatement("update Tickets set Pending=true,Active=false,Closed=false where TicketID=?");
					break;
				case 1:
					statement = conn.prepareStatement("update Tickets set Pending=false,Active=true,Closed=false where TicketID=?");
					break;
				case 2:
					statement = conn.prepareStatement("update Tickets set Pending=false,Active=false,Closed=true where TicketID=?");
					break;
				default:
					statement = conn.prepareStatement("update Tickets set Pending=true,Active=false,Closed=false where TicketID=?");
					break;
			}
		
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch(SQLException e){
			e.printStackTrace();
		} finally {
			try { if (statement != null) statement.close(); } catch (Exception e) {}
		}
	}
	
	/**
	 *	Updates a ticket's worker. Will have no effect if ticket ID does not exist.
	 */
	public void updateTicketWorker(int id, int workerId) {
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement("update Tickets set WorkerID_FK=? where TicketID=?");
			
			statement.setInt(1, workerId);
			statement.setInt(2, id);
			statement.executeUpdate();
		} catch(SQLException e){
			e.printStackTrace();
		} finally {
			try { if (statement != null) statement.close(); } catch (Exception e) {}
		}
	}
	
	/**
	 *	Returns an ArrayList of tickets by the specified queue.
	 */
	public ArrayList<Ticket> createTicketArrayListByQueue(int queueType) {
		ArrayList<Ticket> result = new ArrayList<Ticket>();
		ResultSet rs = null;
		PreparedStatement statement = null;
		
		try {
			String sqlString;
			
			switch (queueType) {
				case 0:
					sqlString = "select * from Tickets where Pending=true";
					break;
				case 1:
					sqlString = "select * from Tickets where Active=true";
					break;
				case 2:
					sqlString = "select * from Tickets where Closed=true";
					break;
				default:
					sqlString = "select * from Tickets where Pending=true";
					break;
			}
		
			statement = conn.prepareStatement(sqlString);
			rs = statement.executeQuery();

			while (rs.next()) {
				int dbid;
				int workerid;
				String subject;
				boolean pending;
				boolean active;
				boolean closed;
				
				dbid = rs.getInt("TicketID");
				workerid = rs.getInt("WorkerID_FK");
				subject = rs.getString("Subject");
				pending = rs.getBoolean("Pending");
				active = rs.getBoolean("Active");
				closed = rs.getBoolean("Closed");
				
				Ticket temp = ModelFactory.createTicket(dbid, workerid, subject, pending, active, closed);
				result.add(temp);
			}
		} catch(SQLException e){
			e.printStackTrace();
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {}
			try { if (statement != null) statement.close(); } catch (Exception e) {}
		}
		
		return result;
	}
	
	/**
	 *	Returns an ArrayList of tickets by the specified worker.
	 */
	public ArrayList<Ticket> createTicketArrayListByWorker(int workerId) {
		ArrayList<Ticket> result = new ArrayList<Ticket>();
		ResultSet rs = null;
		PreparedStatement statement = null;
		
		try {
			statement = conn.prepareStatement("select * from Tickets where WorkerID_FK=?");
			statement.setInt(1, workerId);
			
			rs = statement.executeQuery();

			while (rs.next()) {
				int dbid;
				int workerid;
				String subject;
				boolean pending;
				boolean active;
				boolean closed;
				
				dbid = rs.getInt("TicketID");
				workerid = rs.getInt("WorkerID_FK");
				subject = rs.getString("Subject");
				pending = rs.getBoolean("Pending");
				active = rs.getBoolean("Active");
				closed = rs.getBoolean("Closed");
				
				Ticket temp = ModelFactory.createTicket(dbid, workerid, subject, pending, active, closed);
				result.add(temp);
			}
		} catch(SQLException e){
			e.printStackTrace();
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {}
			try { if (statement != null) statement.close(); } catch (Exception e) {}
		}
		
		return result;
	}
	
	/**
	 *	Stores an annotation.
	 */
	public int storeAnnotation(Annotation anno) {
		int result = 0;
		ResultSet rs = null;
		PreparedStatement statement = null;
		
		try {
			statement = conn.prepareStatement("insert into Annotations (TicketID_FK, WorkerID_FK, Annotation, CreatedOn) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			
			statement.setInt(1, anno.getTicketID());
			statement.setInt(2, anno.getWorkerID());
			statement.setString(3, anno.getText());
			statement.setTimestamp (4, anno.getCreatedOn());
			statement.executeUpdate();
			
			rs = statement.getGeneratedKeys();
			rs.next();
			
			result = rs.getInt(1);
		} catch(SQLException e){
			e.printStackTrace();
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {}
			try { if (statement != null) statement.close(); } catch (Exception e) {}
		}
		
		return result;
	}
	
	/**
	 *	Returns an ArrayList of Annotations by the specified ticket.
	 */
	public ArrayList<Annotation> createAnnotationArrayListByTicket(int ticketId) {
		ArrayList<Annotation> result = new ArrayList<Annotation>();
		ResultSet rs = null;
		PreparedStatement statement = null;
		
		try {
			statement = conn.prepareStatement("select * from Annotations where TicketID_FK=? order by CreatedOn ASC");
			statement.setInt(1, ticketId);

			rs = statement.executeQuery();

			while (rs.next()) {
				int ticketid_fk;
				int workerid_fk;
				int annotationid;
				String annotation;
				Timestamp createdOn;
				
				ticketid_fk = rs.getInt("TicketID_FK");
				workerid_fk = rs.getInt("WorkerID_FK");
				annotationid = rs.getInt("AnnotationID");
				annotation = rs.getString("Annotation");
				createdOn = rs.getTimestamp("CreatedOn");
				
				Annotation temp = ModelFactory.createAnnotation(annotationid, annotation, createdOn, ticketid_fk, workerid_fk);
				result.add(temp);
			}
		} catch(SQLException e){
			e.printStackTrace();
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {}
			try { if (statement != null) statement.close(); } catch (Exception e) {}
		}
		
		return result;
	}
	
	/**
	 *	Helper function to convert byte arrays to hex strings.
	 */
	public String toHexString(byte[] byteArray) {
		char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		char[] chars = new char[byteArray.length * 2];

		int temp;
		for (int i = 0; i < byteArray.length; i++) {
			temp = byteArray[i] & 0xFF;
			chars[i * 2] = hexArray[temp / 16];
			chars[i * 2 + 1] = hexArray[temp % 16];
		}

		return new String(chars);
	}
}
