<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.americanexpress.Eligibility.businessobjects.CardApplication" %>
<%@ page import="com.americanexpress.Eligibility.businessobjects.Applicant" %>
<%@ page import="com.americanexpress.Eligibility.businessobjects.EligibilityDecision" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Update Application Status</title>
</head>
<body>
<br>
<%
String contextPath = request.getContextPath();

CardApplication cardApplication = (CardApplication) request.getAttribute( "cardApplication" );
%>
<form name="upadteStatus" action="<%=contextPath%>/admin/UpdateApplicationStatus" method="post">
<input type="hidden" name="applicationId" value="<%=cardApplication.getApplicationId()%>">
<table>
	<tr> <td> Application Id</td> <td><%=cardApplication.getApplicationId()%></td><tr>
	<tr> <td> First Name</td> <td><%=cardApplication.getApplicant().getFirstName()%></td><tr>
	<tr> <td> Last Name</td> <td><%=cardApplication.getApplicant().getLastName()%></td><tr>
	<tr> <td> SSN</td> <td><%=cardApplication.getApplicant().getSsn()%></td><tr>
	<tr> <td> Approve</td> 
	<td> 
		<input type="radio" name="applicationStatus" value="approve"> Approve &nbsp; &nbsp; 
		<input type="radio" name="applicationStatus" value="deny"> Deny</td><tr>
	<tr> <td> <input type="submit" name="submit"></td><td>&nbsp;</td><tr>
</table>
</form>
</body>
</html>