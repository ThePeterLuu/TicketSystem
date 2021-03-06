package db;

import db.impl.*;

public class DBHelperFactory {
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ticketsystem";
	private static final String DB_USER = "username";
	private static final String DB_PASS = "password";

	public static DBHelper createDBHelper() {
		return new DBHelperImpl(JDBC_URL, DB_USER, DB_PASS);
	}
};
