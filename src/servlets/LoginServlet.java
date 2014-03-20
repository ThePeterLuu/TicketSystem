package servlets;

import java.util.ArrayList;
import java.sql.Timestamp;
import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

import model.ctrl.*;
import model.entity.*;

/**
 * Servlet for handling the login function.
 */
public class LoginServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ServletConfig config = this.getServletConfig();
		ServletContext context = config.getServletContext();
		RequestDispatcher dispatcher;
		
		String invalidCredentials = "Username or password incorrect!";
		String missingCredentials = "Username and password fields must both be filled out!";
		
		if (!request.getParameter("username").equals("") && !request.getParameter("password").equals("")) { // If fields not empty
			String user = request.getParameter("username");
			String pass = request.getParameter("password");
			int loginStatus = LoginController.login(user,pass); // Attempt to login
			switch (loginStatus) {
				case -1: // Failed login
					request.setAttribute("message", invalidCredentials);
					dispatcher = context.getRequestDispatcher("/index.jsp");
					dispatcher.forward(request, response);
					break;
				case 0: // Successful worker login
					session.setAttribute("role", 0);
					ArrayList<Ticket> yourTickets = TicketController.getActiveTicketsByWorker(user);
					ArrayList<String> ticketDates = new ArrayList<String>();
					ArrayList<Annotation> lastAnnotations = new ArrayList<Annotation>(); 
					
					for (int i = 0; i < yourTickets.size(); i++) {
						ticketDates.add(TicketController.getLastActivity(yourTickets.get(i).get_id()));
					}
					
					for (int i = 0; i < yourTickets.size(); i++) {
						lastAnnotations.add(TicketController.getLastAnnotation(yourTickets.get(i).get_id()));
					}
					
					session.setAttribute("username", user);
					session.setAttribute("role", 0);
					session.setAttribute("yourTickets", yourTickets);
					session.setAttribute("ticketDates", ticketDates);
					session.setAttribute("lastAnnotations", lastAnnotations);
					
					dispatcher = context.getRequestDispatcher("/WEB-INF/workerticketlist.jsp");
					dispatcher.forward(request, response);
					break;
				case 1: // Successful manager login
					ArrayList<Ticket> pending = TicketController.getTicketsByQueue("pending");
					ArrayList<Ticket> active = TicketController.getTicketsByQueue("active");
					ArrayList<Ticket> closed = TicketController.getTicketsByQueue("closed");
					
					ArrayList<String> pending_dates = new ArrayList<String>();
					ArrayList<String> active_dates = new ArrayList<String>();
					ArrayList<String> closed_dates = new ArrayList<String>();
					
					ArrayList<String> workers = TicketController.getWorkerNames();
					
					for (int i = 0; i < pending.size(); i++) {
						pending_dates.add(TicketController.getLastActivity(pending.get(i).get_id()));
					}
					for (int i = 0; i < active.size(); i++) {
						active_dates.add(TicketController.getLastActivity(active.get(i).get_id()));
					}
					for (int i = 0; i < closed.size(); i++) {
						closed_dates.add(TicketController.getLastActivity(closed.get(i).get_id()));
					}
					
					session.setAttribute("username", user);
					session.setAttribute("role", 1);
					session.setAttribute("pending", pending);
					session.setAttribute("active", active);
					session.setAttribute("closed", closed);
					session.setAttribute("pending_dates", pending_dates);
					session.setAttribute("active_dates", active_dates);
					session.setAttribute("closed_dates", closed_dates);
					session.setAttribute("workers", workers);
					dispatcher = context.getRequestDispatcher("/WEB-INF/masterticketview.jsp");
					dispatcher.forward(request, response);
					break;
				default:
					break;
			}
		} else {
			request.setAttribute("message", missingCredentials);
			dispatcher = context.getRequestDispatcher("/index.jsp");
			dispatcher.forward(request, response);
		}
	}
}

