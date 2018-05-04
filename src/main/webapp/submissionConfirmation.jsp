<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.americanexpress.Eligibility.businessobjects.CardApplication" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Eligibility Application Submission Completed</title>
</head>
<body>
<br>
<%
String contextPath = request.getContextPath();

String firstName = request.getParameter( "firstName" );
String lastName = request.getParameter( "lastName" );
String ssn = request.getParameter( "ssn" );
CardApplication cardApplication = (CardApplication) request.getAttribute( "cardApplication");

%>
<table>
	<tr> <td> First Name</td> <td><%=firstName%></td><tr>
	<tr> <td> Last Name</td> <td><%=lastName%></td><tr>
	<tr> <td> SSN</td> <td><%=ssn%></td><tr>
	<tr> <td> Application Id</td><td><%=cardApplication.getApplicationId()%></td><tr>
	<tr> <td> Application Status</td><td><%=cardApplication.getDecision()%></td><tr>
</table>
</body>
</html>