package com.americanexpress.Eligibility.webui;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.americanexpress.Eligibility.businessobjects.Applicant;
import com.americanexpress.Eligibility.businessobjects.CardApplication;
import com.americanexpress.Eligibility.businessobjects.EligibilityDecision;
import com.americanexpress.Eligibility.businessobjects.CardApplicationWorker;

/**
 * Servlet implementation class ProcessEligibility
 */
@WebServlet("/ProcessEligibility")
public class ProcessEligibilityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProcessEligibilityServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String firstName = request.getParameter( "firstName" );
		String lastName = request.getParameter( "lastName" );
		String ssn = request.getParameter( "ssn" );
		String contextPath = request.getContextPath();
		
		if( firstName == null || lastName == null || ssn == null) {
			request.setAttribute( "errorMessage" , "All input paramaters are mandatory first and last name and SSN" );
			
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher( "/index.jsp" );
			dispatcher.forward(request, response );
		}		
		Applicant applicant = new Applicant(); 
		applicant.setFirstName( firstName );
		applicant.setLastName( lastName);
		long ssnAsLong = Long.parseLong(ssn);
		applicant.setSsn( ssnAsLong );
		
		CardApplication cardApplication = CardApplicationWorker.processApplication(applicant);
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher( "/submissionConfirmation.jsp" );
		
		request.setAttribute( "cardApplication" , cardApplication);
		dispatcher.forward(request, response );
	}

}
