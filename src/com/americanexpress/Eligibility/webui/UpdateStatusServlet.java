package com.americanexpress.Eligibility.webui;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.americanexpress.Eligibility.businessobjects.CardApplication;
import com.americanexpress.Eligibility.businessobjects.EligibilityDecision;

/**
 * Servlet implementation class UpdateStatusServlet
 */
@WebServlet("/admin/UpdateApplicationStatus")
public class UpdateStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateStatusServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String applicationIdAsStr = request.getParameter( "applicationId" );
		int applicationId = Integer.parseInt( applicationIdAsStr );

		String applicationStatus = request.getParameter( "applicationStatus" );
		
		CardApplication cardApplication = new CardApplication();
		cardApplication.setApplicationId(applicationId);
		if(applicationStatus != null && !applicationStatus.trim().equals("")) {
			applicationStatus = applicationStatus.trim();
			if(applicationStatus.equals( "approve" ) ) {
				cardApplication.setDecision(EligibilityDecision.APPROVED);
			}
			else if(applicationStatus.equals( "deny" ) ) {
				cardApplication.setDecision(EligibilityDecision.DENIED);
			}
			else {
				throw new ServletException("Error: Incorrect or empty application status submitted #" +applicationStatus + "#" );
			}
		}
		else {
			throw new ServletException("Error: Application status is null or empty #" +applicationStatus + "#" );
		}
				
		cardApplication.persistApplicationStatus();
		
		request.setAttribute( "message", "Status updated to " + cardApplication.getDecision().toString() + " for application " + applicationIdAsStr );
		
		ArrayList<CardApplication> cardApplicationList = null;

		cardApplicationList = CardApplication.getApplicationList( null );			
		request.setAttribute( "cardApplicationList", cardApplicationList );
		
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher( "/admin/cardApplicationList.jsp" );
		dispatcher.forward(request, response );
	}

}
