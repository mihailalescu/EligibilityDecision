package test.com.americanexpress.Eligibility.restapi;

import static org.junit.Assert.*;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.web.context.ContextLoader;

import java.util.ArrayList;
import java.util.Random;

import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.PathParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import java.io.StringReader;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.americanexpress.Eligibility.businessobjects.Applicant;
import com.americanexpress.Eligibility.businessobjects.CardApplication;
import com.americanexpress.Eligibility.businessobjects.EligibilityDecision;
import com.americanexpress.Eligibility.restapi.CardApplicationAPI;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MultivaluedMap;


public class CardApplicationAPITest extends JerseyTest {

	private static Random random;
	
	 @Override
    protected Application configure() {
		 /*		 
	       ResourceConfig rc = new ResourceConfig(CardApplicationAPI.class)
	    		   .property(
	    	                "contextConfigLocation",
	    	                "WebContent/META-INF/context.xml"
	    	            );
		  */
		 enable(TestProperties.LOG_TRAFFIC);
	     enable(TestProperties.DUMP_ENTITY);
	     ResourceConfig rc = new ResourceConfig(CardApplicationAPI.class);
	       /*
	       rc.property("contextConfig",new ClassPathXmlApplicationContext(
	       //new String[]{"resources/META-INF/context.xml"},
   		   new String[]{"context.xml"},
	       ContextLoader.getCurrentWebApplicationContext()));
	       */
	       return rc;
    }
	 
/*	 
	 @Override
	 protected DeploymentContext configureDeployment(){
	     return ServletDeploymentContext
	             .forServlet(new ServletContainer( new ResourceConfig( CardApplicationAPI.class )
	                     .packages("provider_package") ) )
	             .contextParam("contextConfigLocation", "/META-INF/context.xml")
	             .initParam(ServerProperties.PROVIDER_PACKAGES, "provider_package")
	             .build();
	 }
*/

	
	@Before
	public void setUp(){
		random = new Random();
		MysqlDataSource mySqlDS = null;

        mySqlDS = new MysqlDataSource();
	    mySqlDS.setURL("jdbc:mysql://localhost:3306/cardApplication");
	    mySqlDS.setUser("root");
	    mySqlDS.setPassword("bigsecret");
	    
	    SimpleNamingContextBuilder builder = null;
	    try {
	        builder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();
	        builder.bind("java:comp/env/jdbc/eligibility",mySqlDS);
	    } catch (NamingException e) {
	    	e.printStackTrace();
		    fail("Failed creating Initial Context");
	    }
	    
	}	
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testSayHello() {
		Client client = ClientBuilder.newClient();
	    WebTarget target = client.target("http://localhost:8080/EligibilityDecision/rest");		
	    WebTarget targetUpdated = target.path("cardApplicationAPI/sayHello");
	    assertNotNull("targetUpdated object cannot be null", targetUpdated );
		Response response = targetUpdated.request().get();
        String hello = response.readEntity(String.class);
        assertEquals("Hello World!", hello);
        response.close();
	}


	@Test
	public void testSubmitApplication() {
		Applicant applicant = new Applicant();
		applicant.setFirstName("firstName" + random.nextInt( 100 ) );
		applicant.setLastName("lastName" + random.nextInt( 100 ) );
		applicant.setSsn(111220000 + random.nextInt( 1000 ) );		
		
		Client client = ClientBuilder.newClient();
	    WebTarget target = client.target("http://localhost:8080/EligibilityDecision/rest");	
	    WebTarget targetUpdated = target.path("cardApplicationAPI/submitApplication");
	    assertNotNull("targetUpdated object cannot be null", targetUpdated );
		Response response = targetUpdated.request("application/json").put(Entity.json( applicant) );
				
		CardApplication cardApplication = response.readEntity( CardApplication.class);
		assertNotEquals( "ApplicationId should be different then -1 ", -1, cardApplication.getApplicationId() );
		System.out.println( "ApplicationId=" + cardApplication.getApplicationId()  );
	}
	

	@Test
	public void testGetApplicationsList() {
		final ArrayList<CardApplication> returnType;
		
		String applicationStatus = null;// EligibilityDecision.PENDING_MANUAL.toString();
		

		Client client = ClientBuilder.newClient();
	    WebTarget target = client.target("http://localhost:8080/EligibilityDecision/rest");	
	    WebTarget targetUpdated = target.path("cardApplicationAPI/getApplicationsList");
	    assertNotNull("targetUpdated object cannot be null", targetUpdated );
		Response response = targetUpdated.request("application/json").get( );
		
		ArrayList<CardApplication>  cardApplicationList = new ArrayList<CardApplication> ();
		cardApplicationList = response.readEntity( cardApplicationList.getClass() );
				
		// Ensure that we have at least one element 
		if( cardApplicationList == null || cardApplicationList.size() ==0 ) {
			Applicant applicant = new Applicant();
			applicant.setFirstName("firstName" + random.nextInt( 100 ) );
			applicant.setLastName("lastName" + random.nextInt( 100 ) );
			applicant.setSsn(111330000 + random.nextInt( 1000 ) );	
				
		    WebTarget targetUpdatedAddApplication = target.path("cardApplicationAPI/submitApplication");
		    assertNotNull("targetUpdated object cannot be null", targetUpdated );
			Response responseAddApplication = targetUpdatedAddApplication.request("application/json").put(Entity.json( applicant) );
			CardApplication cardApplication = responseAddApplication.readEntity( CardApplication.class);
			assertNotEquals( "ApplicationId should be different then -1 ", -1, cardApplication.getApplicationId() );
			
			
			Response response2 = targetUpdated.request("application/json").get( );
			cardApplicationList = response2.readEntity( cardApplicationList.getClass() );
		}
		
		assertNotEquals( "Application list should have one or more elments ", 0, cardApplicationList.size());
		System.out.println( "cardApplicationList size = " + cardApplicationList.size() );
	}

	
	@Test
	public void testGetApplicationById() {
		
		Client client = ClientBuilder.newClient();
	    WebTarget target = client.target("http://localhost:8080/EligibilityDecision/rest");	
	    WebTarget targetUpdated = target.path("cardApplicationAPI/getApplicationsList");
	    assertNotNull("targetUpdated object cannot be null", targetUpdated );
		Response response = targetUpdated.request("application/json").get( );
		
		ArrayList<CardApplication>  cardApplicationList = new ArrayList<CardApplication> ();
		cardApplicationList = response.readEntity( cardApplicationList.getClass() );
		
		int listSize = cardApplicationList.size();
		
		int randomIndex = random.nextInt( listSize );
		
		/*
		for (Object cardApplication: cardApplicationList){
			System.out.println( cardApplication.getClass() );
		}
		*/

		//Correction code due to Jackson documented strange behavior that converts ArrayList elements to java.util.LinkedHashMap
		ObjectMapper mapper = new ObjectMapper();
		CardApplication randomCardApplication = mapper.convertValue(cardApplicationList.get( randomIndex ), CardApplication.class);
		
		int randomCardApplicationApplicationId = randomCardApplication.getApplicationId();
		
		targetUpdated = target.path("cardApplicationAPI/getApplicationById/" + randomCardApplicationApplicationId ).
				queryParam("applicationId", randomCardApplicationApplicationId  );
	    assertNotNull("targetUpdated object cannot be null", targetUpdated );
	    
	    //MultivaluedMap<String, String> params = new MultivaluedMapImpl();;
	    //params.add("user", user.toUpperCase()); 
		Response response2 = targetUpdated.request("application/json").get( );
		
		CardApplication cardApplication = response2.readEntity( CardApplication.class);
		assertEquals( "ApplicationId should be eequal to randomCardApplicationApplicationId", 
				randomCardApplicationApplicationId, cardApplication.getApplicationId() );
		System.out.println( "ApplicationId=" + cardApplication.getApplicationId()  );
	}
	
	@Test
	public void testPersistApplicationStatus() {
		
		int testApplicationId = 1;
		
		Client client = ClientBuilder.newClient();
	    WebTarget target = client.target("http://localhost:8080/EligibilityDecision/rest");	
	    WebTarget targetUpdated = target.path("cardApplicationAPI/getApplicationById/" + testApplicationId);
	    assertNotNull("targetUpdated object cannot be null", targetUpdated );
		Response response = targetUpdated.request("application/json").get();
		CardApplication cardApplication = response.readEntity( CardApplication.class);
		
		EligibilityDecision newStatus = EligibilityDecision.PENDING_MANUAL;
		cardApplication.setDecision(newStatus);
		
		targetUpdated = target.path("cardApplicationAPI/persistApplicationStatus");
	    assertNotNull("targetUpdated object cannot be null", targetUpdated );
		Response response2 = targetUpdated.request("application/json").post( Entity.json( cardApplication ) );
		assertEquals( "Response status should be equal to 204", 204, response2.getStatus());
		
		
		targetUpdated = target.path("cardApplicationAPI/getApplicationById/" + testApplicationId);
	    assertNotNull("targetUpdated object cannot be null", targetUpdated );
		Response response3 = targetUpdated.request("application/json").get();
		CardApplication cardApplicationReadFromDb = response3.readEntity( CardApplication.class);
		
		assertEquals( "Application Status should be set to new value ", cardApplicationReadFromDb.getDecision() , newStatus );
		System.out.println( "ApplicationId=" + cardApplication.getApplicationId()  );
		
	}
	
}
