<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<!DOCTYPE html>

<html>

<head>
	
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<title>Youtube</title>

</head>

<body>
    <div align="center">
        <table border="1" cellpadding="5">
            <caption><h2>Search List</h2></caption>
            <tr>
                <th>Title</th>
                <th>Date</th>
                <th>Description</th>
            </tr>
            <c:forEach var="people" items="${listVideo}">
                <tr>
                    <td><c:out value="${video.title}" /></td>
                    <td><c:out value="${comedian.firstname}" /></td>
                    <td><c:out value="${comedian.lastname}" /></td>
                    <td><c:out value="${video.description}" /></td>
                    <td>
                        <a href="edit?id=<c:out value='${people.id}' />">Edit</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="delete?id=<c:out value='${people.id}' />">Delete</a>                     
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>   
</body>
</html>