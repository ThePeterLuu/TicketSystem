package model.ctrl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import model.entity.*;
import db.*;

/**
 * This class manages user-related database access. Liasons between the DBHelper and the calling servlet.
 */
public class UserController {
	/**
	 * Creates an account with specified username and password.
	 */
	public static void createAccount (String username, String password) {
		DBHelper db = DBHelperFactory.createDBHelper();
		Account temp = ModelFactory.createAccount(username, password, false);
		db.storeAccount(temp);
		db.close();
	}
	
	/**
	 * Returns true if the password is correct for the username, false if it is not.
	 */
	public static boolean checkPassword (String username, String password) {
		DBHelper db = DBHelperFactory.createDBHelper();
		Account temp = db.retrieveAccount(username);
		
		String passHash = "";
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(password.getBytes("UTF-8"));
			passHash = db.toHexString(hash);
		} catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		} catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
		
		if (passHash.equals(temp.getPassword())) {
			db.close();
			return true;
		} else {
			db.close();
			return false;
		}
	}
	
	/**
	 * Changes the password of a user by specified username and password.
	 * Returns true if successful, false if the user is a manager (manager password changes are restricted).
	 */
	public static boolean changePassword (String username, String password) {
		DBHelper db = DBHelperFactory.createDBHelper();
		Account temp = db.retrieveAccount(username);
		if (temp.getManager()) {
			db.close();
			return false;
		} else {
			db.updateAccountPassword(username, password);
			db.close();
			return true;
		}
	}
}
