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
				<c:choose>
					<c:when test="${role eq 1}">
						<h1>Welcome to the Master Ticket View!</h1>
						<h3>Pending Tickets</h3>
						<table>
							<tr>
								<td>Ticket Number</td>
								<td>Subject</td>
								<td>Last Activity</td>
								<td>Close Ticket Button</td>
								<td>Assign Ticket To Worker</td>
							</tr>
							<c:forEach var="ticket" items="${pending}" varStatus="status">
								<tr>
									<td><a href="/TicketSystem/Ticket?ticketid=${ticket._id}">${ticket._id}</a></td>
									<td>${ticket.subject}</td>
									<td>${pending_dates[status.index]}</td>
									<td>
										<form method=post action="Ticket">
											<input type="hidden" name="id" value="${ticket._id}">
											<input type="hidden" name="queue" value="closed">
											<input type="submit" value="Close Ticket">
										</form>
									</td>
									<td>
										<form method=post action="Ticket">
											<input type="hidden" name="assignid" value="${ticket._id}">
											<select name="worker">
												<c:forEach var="name" items="${workers}">
													<option value="${name}">${name}</option>
												</c:forEach>
											</select>
											<input type="submit" value="Assign">
										</form>
									</td>
								</tr>
							</c:forEach>	
						</table>
						
						<h3>Active Tickets</h3>
						<table>
							<tr>
								<td>Ticket Number</td>
								<td>Subject</td>
								<td>Last Activity</td>
								<td>Close Ticket Button</td>
								<td>Assign Ticket To Worker</td>
							</tr>
							<c:forEach var="ticket" items="${active}" varStatus="status">
								<tr>
									<td><a href="/TicketSystem/Ticket?ticketid=${ticket._id}">${ticket._id}</a></td>
									<td>${ticket.subject}</td>
									<td>${active_dates[status.index]}</td>
									<td>
										<form method=post action="Ticket">
											<input type="hidden" name="id" value="${ticket._id}">
											<input type="hidden" name="queue" value="closed">
											<input type="submit" value="Close Ticket">
										</form>
									</td>
									<td>
										<form method=post action="Ticket">
											<input type="hidden" name="assignid" value="${ticket._id}">
											<select name="worker">
												<c:forEach var="name" items="${workers}">
													<option value="${name}">${name}</option>
												</c:forEach>
											</select>
											<input type="submit" value="Assign">
										</form>
									</td>
								</tr>
							</c:forEach>	
						</table>
						
						<h3>Closed Tickets</h3>
						<table>
							<tr>
								<td>Ticket Number</td>
								<td>Subject</td>
								<td>Last Activity</td>
							</tr>
							<c:forEach var="ticket" items="${closed}" varStatus="status">
								<tr>
									<td><a href="/TicketSystem/Ticket?ticketid=${ticket._id}">${ticket._id}</a></td>
									<td>${ticket.subject}</td>
									<td>${closed_dates[status.index]}</td>
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
			</c:when>
			<c:otherwise>
				<h1>You do not have permissions to access this page.</h1>
			</c:otherwise>
		</c:choose>
	</body>
</html>