#
# this script creates the schema for the Web Project 2
#

#
# Table structure for table 'Accounts'
# Password is stored in SHA256 hash form
#
DROP TABLE IF EXISTS Accounts;
#
CREATE TABLE Accounts (
  WorkerID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  Username VARCHAR(255) NOT NULL,
  Password CHAR(64) NOT NULL,
  Manager BOOLEAN,
  UNIQUE (Username)
);

#
# Table structure for table 'Tickets'
#
DROP TABLE IF EXISTS Tickets;
#
CREATE TABLE Tickets (
  WorkerID_FK INT UNSIGNED,
  TicketID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  Subject VARCHAR(255) NOT NULL,
  Pending BOOLEAN,
  Active BOOLEAN,
  Closed BOOLEAN,
  FOREIGN KEY (WorkerID_FK) REFERENCES Accounts(WorkerID)
);

#
# Table structure for table 'Annotations'
#
DROP TABLE IF EXISTS Annotations;
#
CREATE TABLE Annotations (
  TicketID_FK INT UNSIGNED,
  WorkerID_FK INT UNSIGNED,
  AnnotationID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  Annotation TEXT NOT NULL,
  CreatedOn DATETIME NOT NULL,
  FOREIGN KEY (TicketID_FK) REFERENCES Tickets(TicketID),
  FOREIGN KEY (WorkerID_FK) REFERENCES Accounts(WorkerID)
);
