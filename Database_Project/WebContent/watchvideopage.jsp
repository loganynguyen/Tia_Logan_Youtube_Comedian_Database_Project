<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>

<html>

<head>
	
	<meta charset="ISO-8859-1">
	
	<title>Youtube</title>

</head>

<body>
	<center>
	
		<c:forEach var="title" items="${title}">
	                <tr>
	                    <td><c:out value="${title}" /></td>
	                </tr>
	    </c:forEach>
           	
		<form action="searchpage.jsp">
			<button type="submit">Search another video</button>
		</form>
		
	</center>

</body>

</html>