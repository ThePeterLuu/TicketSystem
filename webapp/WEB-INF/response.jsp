<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Ticket System</title>
	</head>
	<body>
		<p>Thanks for submitting your ticket! The ticket ID is ${ticketid} and the subject is "${subject}". We will resolve your ticket as soon as you can.</p>
		<a href="/TicketSystem">Link back to front page</a>
	</body>
</html>