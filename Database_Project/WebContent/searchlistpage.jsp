<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
  
<!DOCTYPE html>

<html>

<head>

	<meta charset="ISO-8859-1">
	<title>Youtube</title>

</head>

<body>
    <div align="center">

		<table border="1" width="70%" align="center">
            <caption><h2>Search Results</h2></caption>
            
            <tr>
                <th>Title</th>
                <th>Comedian</th>
                <th>Description</th>
                <th></th>
            </tr>
            
            <c:forEach var="video" items="${listVideo}">
                <tr>
                    <td><c:out value="${video.title}" /></td>
                    <td><c:out value="${video.description}" /></td>
                 	<td>
                 		<form method="post" action="watch">
							<input type="hidden" name="url" id="url" value="${video.url}" />
							<button type="submit">Watch</button>
						</form>
					</td>
                </tr>
           	</c:forEach>
        </table>
         	
	</div> 
	
</body>

</html>