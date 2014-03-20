package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;

import model.ctrl.*;
import model.entity.*;

/**
 * Servlet for handling ticket-related functions.
 */
public class TicketServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ServletConfig config = this.getServletConfig();
		ServletContext context = config.getServletContext();
		RequestDispatcher dispatcher;
		
		if (request.getParameterMap().containsKey("ticketid")) { // Viewing the single ticket view.
			if ((Integer)session.getAttribute("role") != 1 && (Integer)session.getAttribute("role") != 0) { // Authorization check. Must be manager or worker.
				request.setAttribute("message", "You are not authorized to do this action.");
				dispatcher = context.getRequestDispatcher("/");
				dispatcher.forward(request, response);
			}
			
			String ticketID = request.getParameter("ticketid");
			try {
				int ticketNum = Integer.parseInt(ticketID);
				Ticket tick = TicketController.getTicket(ticketNum);
				ArrayList<Annotation> annotations = TicketController.getAnnotations(ticketNum);
				String workername = TicketController.getWorkerName(tick.getWorkerID());
				ArrayList<String> workerarray = new ArrayList<String>();
				ArrayList<String> times = new ArrayList<String>();
				for (int i = 0; i < annotations.size(); i++) {
					workerarray.add(TicketController.getWorkerName(annotations.get(i).getWorkerID()));
				}
				for (int i = 0; i < annotations.size(); i++) {
					times.add(TicketController.getTime(annotations.get(i).getCreatedOn()));
				}
				session.setAttribute("ticket", tick);
				session.setAttribute("annotations", annotations);
				session.setAttribute("workername", workername);
				session.setAttribute("workerarray", workerarray);
				session.setAttribute("times", times);
				dispatcher = context.getRequestDispatcher("/WEB-INF/singleticketview.jsp");
				dispatcher.forward(request, response);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			
		}
		
		if (request.getParameterMap().containsKey("view")) {
			String view = request.getParameter("view");
			
			if (view.equals("masterticketview")) { // Master ticket view 
				if ((Integer)session.getAttribute("role") != 1) { // Manager only authorization check.
					request.setAttribute("message", "You are not authorized to view this ticket list.");
					dispatcher = context.getRequestDispatcher("/");
					dispatcher.forward(request, response);
				} else {
					refreshView(session);
					
					dispatcher = context.getRequestDispatcher("/WEB-INF/masterticketview.jsp");
					dispatcher.forward(request, response);
				}
			}
			
			if (view.equals("useradministration")) { // User administration view
				if ((Integer)session.getAttribute("role") != 1 && (Integer)session.getAttribute("role") != 0) { // Worker and manager authorization check.
					request.setAttribute("message", "You are not authorized to view this page.");
					dispatcher = context.getRequestDispatcher("/");
					dispatcher.forward(request, response);
				} else {
					dispatcher = context.getRequestDispatcher("/WEB-INF/useradministration.jsp");
					dispatcher.forward(request, response);
				}
			}
			
			if (view.equals("ticketlist")) { // Worker ticket list view
				ArrayList<Ticket> yourTickets = TicketController.getActiveTicketsByWorker((String)session.getAttribute("username"));
				ArrayList<String> ticketDates = new ArrayList<String>();
				ArrayList<Annotation> lastAnnotations = new ArrayList<Annotation>(); 
				
				for (int i = 0; i < yourTickets.size(); i++) {
					ticketDates.add(TicketController.getLastActivity(yourTickets.get(i).get_id()));
				}
				
				for (int i = 0; i < yourTickets.size(); i++) {
					lastAnnotations.add(TicketController.getLastAnnotation(yourTickets.get(i).get_id()));
				}
				
				session.setAttribute("yourTickets", yourTickets);
				session.setAttribute("ticketDates", ticketDates);
				session.setAttribute("lastAnnotations", lastAnnotations);
				
				dispatcher = context.getRequestDispatcher("/WEB-INF/workerticketlist.jsp");
				dispatcher.forward(request, response);
			}
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ServletConfig config = this.getServletConfig();
		ServletContext context = config.getServletContext();
		RequestDispatcher dispatcher;
		
		if (request.getParameterMap().containsKey("subject")) { // Creating a new ticket
			String missingSubject = "Description of the problem cannot be blank!";
			
			if (!request.getParameter("subject").equals("")) {
				String subject = request.getParameter("subject");	
				int id = TicketController.createNewTicket(subject);
				request.setAttribute("ticketid", id);
				request.setAttribute("subject", subject);
				dispatcher = context.getRequestDispatcher("/WEB-INF/response.jsp");
				dispatcher.forward(request, response);
			} else {
				request.setAttribute("message", missingSubject);
				dispatcher = context.getRequestDispatcher("/index.jsp");
				dispatcher.forward(request, response);
			}
		}
		
		if (request.getParameterMap().containsKey("queue")) { // Changing a ticket's queue
			if ((Integer)session.getAttribute("role") != 1) { // Manager only authorization check
				request.setAttribute("message", "You are not authorized to do this action.");
				dispatcher = context.getRequestDispatcher("/");
				dispatcher.forward(request, response);
			}
				
			String queue = request.getParameter("queue");
			String ticketID = request.getParameter("id");
			if (queue.equals("closed")) {
				try {
					TicketController.closeTicket(Integer.parseInt(ticketID));
					refreshView(session);
					
					dispatcher = context.getRequestDispatcher("/WEB-INF/masterticketview.jsp");
					dispatcher.forward(request, response);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}	
			}
		}
		
		if (request.getParameterMap().containsKey("worker")) { // Assign/change a ticker's worker
			if ((Integer)session.getAttribute("role") != 1) { // Manager only authorization check
				request.setAttribute("message", "You are not authorized to do this action.");
				dispatcher = context.getRequestDispatcher("/");
				dispatcher.forward(request, response);
			}
			
			String worker = request.getParameter("worker");
			String ticketID = request.getParameter("assignid");
			String message = "Ticket " + ticketID + " assigned to " + worker + ".";
			try {
				TicketController.assignWorker(Integer.parseInt(ticketID), worker);
				request.setAttribute("message", message);
				refreshView(session);
				
				dispatcher = context.getRequestDispatcher("/WEB-INF/masterticketview.jsp");
				dispatcher.forward(request, response);
			} catch (NumberFormatException e) {
					e.printStackTrace();
			}	
		}
		
		if (request.getParameterMap().containsKey("annotate_id")) { // Adding an annotation
			if ((Integer)session.getAttribute("role") != 1 && (Integer)session.getAttribute("role") != 0) { // Manager and worker authorization check
				request.setAttribute("message", "You are not authorized to do this action.");
				dispatcher = context.getRequestDispatcher("/");
				dispatcher.forward(request, response);
			}
			
			String ticketID = request.getParameter("annotate_id");
			String text = request.getParameter("annotation");
			
			try {
				TicketController.createNewAnnotation(text, Integer.parseInt(ticketID), (String)session.getAttribute("username"));
				
				ArrayList<Annotation> annotations = TicketController.getAnnotations(Integer.parseInt(ticketID));
				ArrayList<String> workerarray = new ArrayList<String>();
				ArrayList<String> times = new ArrayList<String>();
				for (int i = 0; i < annotations.size(); i++) {
					workerarray.add(TicketController.getWorkerName(annotations.get(i).getWorkerID()));
				}
				for (int i = 0; i < annotations.size(); i++) {
					times.add(TicketController.getTime(annotations.get(i).getCreatedOn()));
				}
				session.setAttribute("annotations", annotations);
				session.setAttribute("workerarray", workerarray);
				session.setAttribute("times", times);
				
				dispatcher = context.getRequestDispatcher("/WEB-INF/singleticketview.jsp");
				dispatcher.forward(request, response);
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}	
		}
	}
	
	/**
	 * Helper function to poll the database again for current data, which will refresh the session data
	 */
	private void refreshView(HttpSession session) {
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

		session.setAttribute("pending", pending);
		session.setAttribute("active", active);
		session.setAttribute("closed", closed);
		session.setAttribute("pending_dates", pending_dates);
		session.setAttribute("active_dates", active_dates);
		session.setAttribute("closed_dates", closed_dates);
		session.setAttribute("workers", workers);
	}
}

