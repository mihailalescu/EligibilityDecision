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
import com.americanexpress.Eligibility.businessobjects.CardApplicationWorker;
import com.americanexpress.Eligibility.businessobjects.EligibilityDecision;

/**
 * Servlet implementation class CardApplicationListServlet
 */
@WebServlet("/admin/CardApplicationListServlet")
public class CardApplicationListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CardApplicationListServlet() {
        super();

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doService(request, response);
	}

	/*
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doService(request, response);
	}

	private void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String showOnlyPending = request.getParameter( "showOnlyPending" );
		ArrayList<CardApplication> cardApplicationList = null;
		if( showOnlyPending == null || ! showOnlyPending.trim().equals( "true" ) ) {
			cardApplicationList = CardApplication.getApplicationList( null );			
		}
		else{		
			cardApplicationList = CardApplication.getApplicationList(EligibilityDecision.PENDING_MANUAL );			
		}
		request.setAttribute( "cardApplicationList", cardApplicationList );
		
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher( "/admin/cardApplicationList.jsp" );
		dispatcher.forward(request, response );
	}
}
