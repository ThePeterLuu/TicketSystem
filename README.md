This is a complete project with a complete set of JUnit tests. Not very graphically pretty though.

To run:

1. You will need a MySQL database set up with a database named "ticketsystem" and the tables created using the SQL script in the dbscript folder.
2. You also need to have Tomcat6 running. The username and password for the database can be found in src/db/DBHelperFactory.java

Example Admin Credentials:
Username: admin
Password: hello

Note: If you uncomment the database tests and controller tests in the JUnit task in the build.xml, you must clear the database using the SQL script in /dbscript before running those tests.
You should also run the script after running the tests as well. It will also remove the example admin credentials from the database and replace it with the credentials (Username: joe , Password: bye). The tests all pass, but they contain side-effects and the build script is not set up to automatically regenerate the database at the moment.

Command to compile and deploy: "sudo ant"
