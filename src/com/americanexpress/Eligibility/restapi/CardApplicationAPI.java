package com.americanexpress.Eligibility.restapi;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.americanexpress.Eligibility.businessobjects.CardApplication;
import com.americanexpress.Eligibility.businessobjects.CardApplicationWorker;
import com.americanexpress.Eligibility.businessobjects.EligibilityDecision;
import com.americanexpress.Eligibility.businessobjects.Applicant;


@Path("cardApplicationAPI")
public class CardApplicationAPI {


		
   @Context
   private UriInfo uriInfo;
   @Context Request request;	   

   @GET
   @Path("sayHello")
   @Produces("text/plain")
   public String sayHello( ){
		
	   //return Response.ok(cardApplication, MediaType.APPLICATION_JSON).build();
	   //return Response.ok().entity( applicantionId ).build();
	   return "Hello World!";
	}


	@PUT
	@Path("submitApplication")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public static CardApplication submitApplication(Applicant applicant ){
		
		CardApplication cardApplication = CardApplicationWorker.processApplication(applicant);
		return cardApplication;
	}

	   
	@GET
	@Path("getApplicationsList")
	@Consumes("text/plain")
	@Produces(MediaType.APPLICATION_JSON)
	public static ArrayList<CardApplication> getApplicationsList( String applicationStatus ){
		
		EligibilityDecision applicationStatusEnumVal = null;
		if(applicationStatus != null && !applicationStatus.trim().equals("") ) {
			applicationStatusEnumVal = EligibilityDecision.valueOf(applicationStatus);
		}
		ArrayList<CardApplication> applicationList = CardApplication.getApplicationList( applicationStatusEnumVal) ;
		
		return applicationList;
	}

	@GET
	@Path("getApplicationById/{applicationId}")
	@Consumes("text/plain")
	@Produces(MediaType.APPLICATION_JSON)
	public static CardApplication getApplicationById( @PathParam("applicationId")  int applicationId ){
		CardApplication cardApplication = CardApplication.getApplicationById( applicationId ) ;
		
		return cardApplication;
	}

	@POST
	@Path("persistApplicationStatus")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public static void persistApplicationStatus( CardApplication cardApplication  ){
		cardApplication.setDecision( EligibilityDecision.PENDING_MANUAL );
		cardApplication.persistApplicationStatus();
		
		return;
	}

	
}
