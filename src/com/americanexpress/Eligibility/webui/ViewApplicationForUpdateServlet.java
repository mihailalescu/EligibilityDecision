package com.americanexpress.Eligibility.webui;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.americanexpress.Eligibility.businessobjects.CardApplication;

/**
 * Servlet implementation class ViewApplication
 */
@WebServlet("/admin/ViewApplicationForUpdateStatus")
public class ViewApplicationForUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewApplicationForUpdateServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String applicationIdAsStr = request.getParameter( "applicationId" );
		
		int applicationId = Integer.parseInt( applicationIdAsStr );
		CardApplication cardApplication = CardApplication.getApplicationById(applicationId);
		
		request.setAttribute( "cardApplication", cardApplication );
		
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher( "/admin/cardApplicationUpdateStatusView.jsp" );
		dispatcher.forward(request, response );
	}

}
