package com.americanexpress.Eligibility.businessobjects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.americanexpress.Eligibility.businessobjects.EligibilityDecision;
import com.americanexpress.Eligibility.businessobjects.Applicant;;

public class CardApplicationWorker {
	private static Random random = new Random();
	
	
	public static CardApplication processApplication(Applicant applicant) {
		
		EligibilityDecision eligibilityDecision = fakeRandomEligibilityDecision( applicant );
		CardApplication cardApplication = new CardApplication( applicant, eligibilityDecision );
		cardApplication.saveNewApplication( );
		return cardApplication;
	}
	
	/**
	 * 
	 * @param ignoredApplicant The parameter is ignored. The decision is made randomly. A random integer generator is used (0 to 9). For 0 and 1,
	 * the decision is denied for  2, 3 and 4 the decision is go to manual approval process, if greater than 4, the decision is approved.
	 * @return 
	 */
	private static EligibilityDecision fakeRandomEligibilityDecision( Applicant ignoredApplicant ) {
		
		int randomEligibilityScore = random.nextInt(10);
		EligibilityDecision eligibilityDecision;
		switch(randomEligibilityScore) {
			case 0:
			case 1:
				eligibilityDecision = EligibilityDecision.DENIED;break;
			case 2: 
			case 3:
			case 4:
				eligibilityDecision = EligibilityDecision.PENDING_MANUAL;break;
			default: eligibilityDecision = EligibilityDecision.APPROVED;break;
		}
		return eligibilityDecision;
	}
	

	
}
