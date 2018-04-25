<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.americanexpress.Eligibility.businessobjects.CardApplication" %>
<%@ page import="com.americanexpress.Eligibility.businessobjects.Applicant" %>
<%@ page import="com.americanexpress.Eligibility.businessobjects.EligibilityDecision" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Eligibility Application</title>
</head>
<body>
<br>
<% 
String message = (String) request.getAttribute("message");
if( message != null && ! message.trim().equals("")){
%>
<br>
<%=message %>
<br>
<%
}
String contextPath = request.getContextPath();

ArrayList<CardApplication> cardApplicationList = (ArrayList<CardApplication>) request.getAttribute( "cardApplicationList" );

if( cardApplicationList == null || cardApplicationList.isEmpty()  ){
%>
<br>
There are no cards application to view!
<%}
else{%>
<br>
<b>Only Pending Applications are updatable.</b>
<br><br>
<table border="1">
	<tr> 
		<td> ApplicationId</td> 
		<td> First Name</td> 
		<td> Last Name</td> 
		<td> SSN</td> 
		<td> ApplicationStatus</td> 
	<tr>	
<% 	for (CardApplication cardApplication: cardApplicationList){
		Applicant applicant = cardApplication.getApplicant();
		if(cardApplication.getDecision() == EligibilityDecision.PENDING_MANUAL ){
%>		
	
	<tr>
		<td><a href="<%=contextPath%>/admin/ViewApplicationForUpdateStatus?applicationId=<%=cardApplication.getApplicationId()%>"><%=cardApplication.getApplicationId()%></a></td>
		<td><a href="<%=contextPath%>/admin/ViewApplicationForUpdateStatus?applicationId=<%=cardApplication.getApplicationId()%>"><%=applicant.getFirstName() %></a></td>
		<td><a href="<%=contextPath%>/admin/ViewApplicationForUpdateStatus?applicationId=<%=cardApplication.getApplicationId()%>"><%=applicant.getLastName() %></a></td>
		<td><a href="<%=contextPath%>/admin/ViewApplicationForUpdateStatus?applicationId=<%=cardApplication.getApplicationId()%>"><%=applicant.getSsn() %></a></td>
		<td><a href="<%=contextPath%>/admin/ViewApplicationForUpdateStatus?applicationId=<%=cardApplication.getApplicationId()%>"><%=cardApplication.getDecision().toString()%></a></td> 
	<tr>	
	<%	}else{%>
	<tr>
		<td><%=cardApplication.getApplicationId()%></td>
		<td><%=applicant.getFirstName() %></td>
		<td><%=applicant.getLastName() %></td>
		<td><%=applicant.getSsn() %></td>
		<td><%=cardApplication.getDecision().toString()%></td> 
	<tr>	
<%		}
	}%>
</table>
<% }%>
</body>
</html>