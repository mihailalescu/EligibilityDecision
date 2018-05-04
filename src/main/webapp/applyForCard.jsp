<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Apply To Test Eligibility</title>
</head>
<body>
<br>
<%
String contextPath = request.getContextPath();

String errorMessage = (String) request.getAttribute( "errorMessage" );
String firstName = request.getParameter( "firstName" );
String lastName = request.getParameter( "lastName" );
String ssn = request.getParameter( "ssn" );
firstName = firstName == null ? "" : firstName;
lastName = lastName == null ? "" : lastName;
ssn = ssn == null ? "" : ssn;

if( errorMessage != null ){
%>
<%=errorMessage%>
<br>
<%}%>
<form name="dataCollection" action="<%=contextPath%>/ProcessEligibility" method="post">
<table>
	<tr> <td> First Name</td> <td> <input type="text" name="firstName" value="<%=firstName%>"></td><tr>
	<tr> <td> Last Name</td> <td> <input type="text" name="lastName" value="<%=lastName%>"></td><tr>
	<tr> <td> SSN</td> <td> <input type="number" name="ssn" min="100000000" max="999999999" value="<%=ssn%>"></td><tr>
	<tr> <td> <input type="submit" name="submit"></td><td>&nbsp;</td><tr>
</table>
</form>
</body>
</html>