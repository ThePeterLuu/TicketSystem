package servlets;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

import model.ctrl.*;

/**
 * Servlet for handling user-related functions.
 */
public class UserServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ServletConfig config = this.getServletConfig();
		ServletContext context = config.getServletContext();
		RequestDispatcher dispatcher;
		
		if (request.getParameterMap().containsKey("action")) {
			String action = request.getParameter("action");
			
			if (action.equals("createaccount") || action.equals("changeworkerpass")) {
				if ((Integer)session.getAttribute("role") != 1) { // Authorization check
					request.setAttribute("message", "You are not authorized to do this action.");
					dispatcher = context.getRequestDispatcher("/");
					dispatcher.forward(request, response);
				} else {
					if (action.equals("createaccount")) { // Create a new account
						String user = request.getParameter("username");
						String pass1 = request.getParameter("password1");
						String pass2 = request.getParameter("password2");
						
						if (user.equals("") || pass1.equals("") || pass2.equals("")) {
							request.setAttribute("message", "All fields required.");
						} else if (!pass1.equals(pass2)) {
							request.setAttribute("message", "Password fields must match.");
						} else {
							UserController.createAccount(user, pass1);
							String msg = "User " + user + " successfully created.";
							request.setAttribute("message", msg);
						}
					}
					
					if (action.equals("changeworkerpass")) { // Change existing account password
						String user = request.getParameter("worker");
						String pass1 = request.getParameter("password1");
						String pass2 = request.getParameter("password2");
						
						if (user.equals("") || pass1.equals("") || pass2.equals("")) {
							request.setAttribute("message", "All fields required.");
						} else if (!pass1.equals(pass2)) {
							request.setAttribute("message", "Password fields must match.");
						} else {
							boolean success = UserController.changePassword(user, pass1);
							String msg = "";
							if (success) {
								msg = "Password for " + user + " successfully changed.";
							} else {
								msg = "Unable to change this user's password. User is a manager.";
							}
							
							request.setAttribute("message", msg);
						}
					}
					
					dispatcher = context.getRequestDispatcher("/WEB-INF/useradministration.jsp");
					dispatcher.forward(request, response);
				}
			}
			
			if (action.equals("changepassword")) { // No authorization check required for changing your own password
				String passwordold = request.getParameter("passwordold");
				String pass1 = request.getParameter("password1");
				String pass2 = request.getParameter("password2");
				
				if (passwordold.equals("") || pass1.equals("") || pass2.equals("")) {
					request.setAttribute("message", "All fields required.");
				} else if (!UserController.checkPassword((String)session.getAttribute("username"), passwordold)) {
					request.setAttribute("message", "Current password incorrect.");
				} else if (!pass1.equals(pass2)) {
					request.setAttribute("message", "Password fields must match.");
				} else {
					UserController.changePassword((String)session.getAttribute("username"), pass1);
					String msg = "Password successfully changed.";
					request.setAttribute("message", msg);
				}
				
				dispatcher = context.getRequestDispatcher("/WEB-INF/useradministration.jsp");
				dispatcher.forward(request, response);
			}
		} else {
			request.setAttribute("message", "Not a valid action.");
			dispatcher = context.getRequestDispatcher("/");
			dispatcher.forward(request, response);
		}
	}
}

