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
						<h1>User Administration Page</h1>
						
						<h4>Create New Worker Account</p>
						<form method=post action="User">
							<input type="hidden" name="action" value="createaccount">
							Username: <input type="text" name="username">
							New Password: <input type="password" name="password1">
							Confirm New Password: <input type="password" name="password2">
							<input type="submit" value="Create User"><input type=reset>
						</form>
						
						<h4>Change Current Worker's Password</h4>
						<form method=post action="User">
							<input type="hidden" name="action" value="changeworkerpass">
							<select name="worker">
								<c:forEach var="name" items="${workers}">
									<option value="${name}">${name}</option>
								</c:forEach>
							</select>
							New Password: <input type="password" name="password1">
							Confirm New Password: <input type="password" name="password2">
							<input type="submit" value="Change User's Password"><input type=reset>
						</form>
						
						<h4>Change your OWN password</h4>
						<form method=post action="User">
							<input type="hidden" name="action" value="changepassword">
							Old Password: <input type="password" name="passwordold">
							New Password: <input type="password" name="password1">
							Confirm New Password: <input type="password" name="password2">
							<input type="submit" value="Change Password"><input type=reset>
						</form>
					</c:when>
					<c:otherwise>
						<h4>Change your password</h4>
						<form method=post action="User">
							<input type="hidden" name="action" value="changepassword">
							Old Password: <input type="password" name="passwordold">
							New Password: <input type="password" name="password1">
							Confirm New Password: <input type="password" name="password2">
							<input type="submit" value="Change Password"><input type=reset>
						</form>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<h1>You do not have permissions to access this page.</h1>
			</c:otherwise>
		</c:choose>
		<ul>
			<c:if test="${not empty role}">
				<c:choose>
					<c:when test="${role eq 1}">
						<li><a href="/TicketSystem/Ticket?view=masterticketview">Master Ticket View Link</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="/TicketSystem/Ticket?view=ticketlist">Back to assigned ticket list</a></li>
					</c:otherwise>
				</c:choose>
			</c:if>
			<li><a href="/TicketSystem">Link back to front page</a></li>
		</ul>
	</body>
</html>