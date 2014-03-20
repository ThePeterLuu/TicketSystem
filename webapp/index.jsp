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
		<h1>Welcome to the Work Ticket System!</h1>
		
		<c:if test="${not empty message}">
			<h2>${message}</h2>
		</c:if>
		
		<p>Submit a ticket here.</p>
		<form method=post action="Ticket">
			Brief description of problem (255 characters or less. We believe in brevity.): <input type="text" name="subject">
			<input type="submit" value="Submit"><input type=reset>
		</form>
		
		<c:choose>
			<c:when test="${not empty username}">
				<p>You are currently logged in as <b>${username}</b>.</p>
				<c:choose>
					<c:when test="${role eq 1}">
						<ul>
							<li><a href="/TicketSystem/Ticket?view=masterticketview">Master Ticket View Link</a></li>
							<li><a href="/TicketSystem/Ticket?view=useradministration">User Administration Page Link</a></li>
						</ul>
					</c:when>
					<c:otherwise>
						<a href="/TicketSystem/Ticket?view=ticketlist">Link to assigned ticket list</a>
					</c:otherwise>
				</c:choose>
				<p><a href="/TicketSystem/Logout">Log out</a></p>
			</c:when>
			<c:otherwise>
				<p>Login if you are IT Helpdesk here. (Note: For an admin credential, please consult the README in the implementation directory. You will then just be able to create a worker account.)</p>
				<form method=post action="Login">
					Username: <input type="text" name="username">
					Password: <input type="password" name="password">
					<input type="submit" value="Login"><input type=reset>
				</form>
			</c:otherwise>
		</c:choose>
	</body>
</html>