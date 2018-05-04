package com.americanexpress.Eligibility.businessobjects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CardApplication {
	private int applicationId = -1; 
	private Applicant applicant;
	private EligibilityDecision decision;
	
	public CardApplication() {
		
	}
	
	public CardApplication( Applicant applicant,EligibilityDecision decision ) {
		this.applicant =applicant;
		this .decision = decision;
	}
	
	public CardApplication( Applicant applicant, int applicationId, EligibilityDecision decision ) {
		this.applicant =applicant;
		this.applicationId = applicationId;
		this .decision = decision;
	}
	
	public Applicant getApplicant() {
		return applicant;
	}
	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}
	public EligibilityDecision getDecision() {
		return decision;
	}
	public void setDecision(EligibilityDecision decision) {
		this.decision = decision;
	}

	public int getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}
	
public static ArrayList<CardApplication> getApplicationList( EligibilityDecision eligibilityDecision  ) {
		
		Connection conn = null;
		ArrayList<CardApplication> cardApplicationsArrayList = new ArrayList<CardApplication>() ;
		try {
			InitialContext initialContext = new InitialContext();
			Context environmentContext = (Context) initialContext.lookup("java:comp/env");
			String dataResourceName = "jdbc/eligibility";
			DataSource dataSource = (DataSource) environmentContext.lookup(dataResourceName);
			conn = dataSource.getConnection();
			PreparedStatement statement = null;
			String selectQuery = null;
			if( eligibilityDecision != null) {
				selectQuery = "Select * from applications ehere applicationStatus = ? ";
				statement = conn.prepareStatement( selectQuery );
				statement.setString(1, eligibilityDecision.toString());
			}
			else{
				selectQuery = "Select * from applications ";
				statement = conn.prepareStatement( selectQuery );				
			}
						
			ResultSet rs = statement.executeQuery();
			
	        while( rs.next() ){
	        	String firstName = rs.getString("firstName"); 
	        	String lastName = rs.getString("lastName");
	        	long ssn = rs.getLong("ssn");
	        	
	        	Applicant applicant = new Applicant( firstName, lastName, ssn );
	        	
	        	int applicationId = rs.getInt("applicationId");
	        	String applicationStatus = rs.getString("applicationStatus");	        	
	        	EligibilityDecision decision = EligibilityDecision.valueOf(applicationStatus);
	           
	        	CardApplication cardApplication = new CardApplication( applicant, applicationId, decision );
	        	
	        	cardApplicationsArrayList.add( cardApplication );
	        }
        	
        }
        catch (SQLException sqlEx){
        	throw new RuntimeException("Database exception when saving applicaton", sqlEx );
        } catch (NamingException nameEx) {
        	throw new RuntimeException("NamingException exception when saving applicaton", nameEx );
		}
        finally {
        	if (conn != null) {
        		try {
        			conn.close();
        		}
        		catch (SQLException ignoredEx){}
        	}
        	conn = null;
        }
		return cardApplicationsArrayList;
	}

	public static CardApplication getApplicationById( int applicationId ) {
		
		Connection conn = null;
		CardApplication cardApplication = null;
		
		try {
			InitialContext initialContext = new InitialContext();
			Context environmentContext = (Context) initialContext.lookup("java:comp/env");
			String dataResourceName = "jdbc/eligibility";
			DataSource dataSource = (DataSource) environmentContext.lookup(dataResourceName);
			conn = dataSource.getConnection();
			PreparedStatement statement = null;
			String selectQuery = null;

			selectQuery = "Select * from applications where applicationId = ? ";
			statement = conn.prepareStatement( selectQuery );
			statement.setInt( 1, applicationId );
						
			ResultSet rs = statement.executeQuery();
			
	        while( rs.next() ){
	        	String firstName = rs.getString("firstName"); 
	        	String lastName = rs.getString("lastName");
	        	long ssn = rs.getLong("ssn");
	        	
	        	Applicant applicant = new Applicant( firstName, lastName, ssn );
	        		        	
	        	String applicationStatus = rs.getString("applicationStatus");	        	
	        	EligibilityDecision decision = EligibilityDecision.valueOf(applicationStatus);
	           
	        	cardApplication = new CardApplication( applicant, applicationId, decision );
	        }
        	
        }
        catch (SQLException sqlEx){
        	throw new RuntimeException("Database exception when saving applicaton", sqlEx );
        } catch (NamingException nameEx) {
        	throw new RuntimeException("NamingException exception when saving applicaton", nameEx );
		}
        finally {
        	if (conn != null) {
        		try {
        			conn.close();
        		}
        		catch (SQLException ignoredEx){}
        	}
        	conn = null; 
        }
		return cardApplication;
	}
	
	public void persistApplicationStatus() {
		
		Connection conn = null;
		try {
			InitialContext initialContext = new InitialContext();
			Context environmentContext = (Context) initialContext.lookup("java:comp/env");
			String dataResourceName = "jdbc/eligibility";
			DataSource dataSource = (DataSource) environmentContext.lookup(dataResourceName);
			conn = dataSource.getConnection();
			//String insertStatement = "Update applications set firstName = ?, lastName =?, ssn=?, applicationStatus=? where application id = ?";
			String insertStatement = "Update applications set applicationStatus= ? where applicationId = ?";
				
			PreparedStatement statement = conn.prepareStatement( insertStatement );
			
		
			/*
			statement.setString( 1, applicant.getFirstName() );
			statement.setString( 2, applicant.getLastName() );
			statement.setLong( 3, applicant.getSsn() );
			statement.setString( 4, decision.toString() );
			statement.setInt( 5, applicationId );
			*/
			statement.setString( 1, decision.toString() );
			statement.setInt( 2, applicationId );
			
			statement.executeUpdate();
        }
        catch (SQLException sqlEx){
        	throw new RuntimeException("Database exception when saving applicaton", sqlEx );
        } catch (NamingException nameEx) {
        	throw new RuntimeException("NamingException exception when saving applicaton", nameEx );
		}
        finally {
        	if (conn != null) {
        		try {
        			conn.close();
        		}
        		catch (SQLException ignoredEx){}
        	}
        	conn = null; 
        }
	}
	
	public int saveNewApplication() {
		
		Connection conn = null;
		int lastInsertedRowId = -1;
		try {
			InitialContext initialContext = new InitialContext();
			Context environmentContext = (Context) initialContext.lookup("java:comp/env");
			String dataResourceName = "jdbc/eligibility";
			DataSource dataSource = (DataSource) environmentContext.lookup(dataResourceName);
			conn = dataSource.getConnection();
			String insertStatement = "insert into applications( firstName, lastName, ssn, applicationStatus )  values( ?, ?, ?, ?)"; 
				
			PreparedStatement statement = conn.prepareStatement( insertStatement, Statement.RETURN_GENERATED_KEYS );
			
			
			statement.setString( 1, applicant.getFirstName() );
			statement.setString( 2, applicant.getLastName() );
			statement.setLong( 3, applicant.getSsn() );
			statement.setString( 4, this.getDecision().toString() );
			
			statement.executeUpdate();
			
			ResultSet rs = statement.getGeneratedKeys();
		    if (rs.next()){
		    	lastInsertedRowId =rs.getInt(1);
		    }
		    rs.close();
		    this.applicationId = lastInsertedRowId;
		    
        }
        catch (SQLException sqlEx){
        	throw new RuntimeException("Database exception when saving applicaton", sqlEx );
        } catch (NamingException nameEx) {
        	throw new RuntimeException("NamingException exception when saving applicaton", nameEx );
		}
        finally {
        	if (conn != null) {
        		try {
        			conn.close();
        		}
        		catch (SQLException ignoredEx){}
        	}
        	conn = null; 
        }
		return this.applicationId;
	}
}
