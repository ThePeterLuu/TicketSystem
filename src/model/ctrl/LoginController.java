package model.ctrl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import model.entity.*;
import db.*;

/**
 * This class manages login-related database access. Liasons between the DBHelper and the calling servlet.
 */
public class LoginController {
	/**
	 * Returns -1 on a failed login, 0 on a worker login, 1 on a manager login
	 */
	public static int login(String username, String password) {
		DBHelper db = DBHelperFactory.createDBHelper();

		Account temp = db.retrieveAccount(username);
		if (temp == null) {
			return -1;
		}
		
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

		String pass = temp.getPassword();

		if (pass.equals(passHash)) {
			if (temp.getManager()) {
				db.close();
				return 1;
			} else {
				db.close();
				return 0;
			}
		} else {
			db.close();
			return -1;
		}
	}
}
