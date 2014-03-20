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
		<a href="/TicketSystem/Logout">Log out</a>
		<c:if test="${not empty message}">
			<h2>${message}</h2>
		</c:if>
		
		<c:choose>
			<c:when test="${not empty role}">
				<h1>Welcome to the Ticket List View!</h1>
				<h3>Assigned Tickets</h3>
				<table>
					<tr>
						<td>Ticket Number</td>
						<td>Subject</td>
						<td>Last Activity</td>
						<td>Last Annotation</td>
					</tr>
					<c:forEach var="ticket" items="${yourTickets}" varStatus="status">
						<tr>
							<td><a href="/TicketSystem/Ticket?ticketid=${ticket._id}">${ticket._id}</a></td>
							<td>${ticket.subject}</td>
							<td>${ticketDates[status.index]}</td>
							<td>"${lastAnnotations[status.index].text}"</td>
						</tr>
					</c:forEach>	
				</table>
				<ul>
					<li><a href="/TicketSystem/Ticket?view=useradministration">User Administration Page Link</a></li>
					<li><a href="/TicketSystem">Link back to front page</a></li>
				</ul>
			</c:when>
			<c:otherwise>
				<h1>You do not have permissions to access this page.</h1>
			</c:otherwise>
		</c:choose>
	</body>
</html>