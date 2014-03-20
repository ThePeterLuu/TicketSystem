This is a complete project with a complete set of JUnit tests. Not very graphically pretty though.

To run: You will need a MySQL database set up with a database named "ticketsystem" and the tables created using the SQL script in the dbscript folder. You also need to have Tomcat6 running. The username and password for the database can be found in src/db/DBHelperFactory.java

Example Admin Credentials:
Username: admin
Password: hello

Note: If you uncomment the database tests and controller tests in the JUnit task in the build.xml, please note that
you MUST clear the database (the sql script in folder /dbscript will do that) before running those tests and it's recommended
that you clear AFTER running those tests too. It will also remove the example admin credentials from the database, instead replacing
it with credentials (Username: joe , Password: bye). The tests all pass, but they have side-effects on the database.

Command to compile and deploy: "sudo ant"
