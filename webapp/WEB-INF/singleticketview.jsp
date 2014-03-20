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
		<h1>Viewing Ticket ${ticket._id}</h1>
		<c:if test="${ticket.pending eq true}">
			<p>Ticket is <b>PENDING</b>.</p>
		</c:if>
		<c:if test="${ticket.active eq true}">
			<p>Ticket is <b>ACTIVE</b>.</p>
		</c:if>
		<c:if test="${ticket.closed eq true}">
			<p>Ticket is <b>CLOSED</b>.</p>
		</c:if>
		<p>Worker assigned to the ticket is <b>${workername}</b> (ID: ${ticket.workerID})</p>
		<p>Ticket subject line: "${ticket.subject}"</p>
		
		<c:forEach var="anno" items="${annotations}" varStatus="status">
			<p>Annotation on <i>${times[status.index]}</i> by <b>${workerarray[status.index]}</b> (Worker ID: ${anno.workerID}): "${anno.text}"</p>
		</c:forEach>
		
		<h4>New Annotation</h4>
		<form method=post action="Ticket">
			<input type="hidden" name="annotate_id" value="${ticket._id}">
			Enter Annotation: <input type="text" name="annotation">
			<input type="submit" value="Add"><input type=reset>
		</form>
		
		<c:choose>
			<c:when test="${role eq 1}">
				<ul>
					<li><a href="/TicketSystem/Ticket?view=masterticketview">Back to Master Ticket View Link</a></li>
					<li><a href="/TicketSystem/Ticket?view=useradministration">User Administration Page Link</a></li>
				</ul>
			</c:when>
			<c:otherwise>
				<a href="/TicketSystem/Ticket?view=ticketlist">Back to assigned ticket list</a>
			</c:otherwise>
		</c:choose>
				
		<a href="/TicketSystem">Link back to front page</a>
	</body>
</html>