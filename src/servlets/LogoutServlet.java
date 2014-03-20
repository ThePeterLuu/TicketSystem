package servlets;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

import model.ctrl.*;

/**
 * Servlet for handling the logout function.
 */
public class LogoutServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ServletConfig config = this.getServletConfig();
		ServletContext context = config.getServletContext();
		RequestDispatcher dispatcher;
	
		// Remove all attributes that were created when logging in.
		session.removeAttribute("username");
		session.removeAttribute("role");
		session.removeAttribute("pending");
		session.removeAttribute("active");
		session.removeAttribute("closed");
		session.removeAttribute("pending_dates");
		session.removeAttribute("active_dates");
		session.removeAttribute("closed_dates");
		session.removeAttribute("workers");
		
		request.setAttribute("message", "You have been logged out.");
		
		dispatcher = context.getRequestDispatcher("/");
		dispatcher.forward(request, response);
	}
}

