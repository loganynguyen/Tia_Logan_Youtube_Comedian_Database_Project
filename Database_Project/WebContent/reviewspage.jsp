<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>

<html>

<head>

	<meta charset="ISO-8859-1">
	<title>Youtube</title>

</head>

<body>
<h4>
<%
if(session != null)
{
	if(session.getAttribute("currentUsername") != null)
	{
		String currentUser = (String) session.getAttribute("currentUsername");
		String currentPassword = (String) session.getAttribute("currentPassword");
		out.println("Current user: ");
		out.print(currentUser);
	}
	else
	{
		response.sendRedirect("loginpage.jsp");
	}
}
%>
</h4>
<p align = 'right'><form method = "post" action="logout"><button type="submit" id="logout" value="logout">logout</button></form></p>

    <div align="center">

		<table border="1" width="70%" align="center">
            <caption><h2>Reviews</h2></caption>
            
            <tr>
                <th>username</th>
                <th>remark</th>
                <th>score</th>
            </tr>
            
            <c:forEach var="review" items="${listReviews}">
                <tr>
                    <td><c:out value="${review.username}" /></td>
                    <td><c:out value="${review.remark}" /></td>
                    <td><c:out value="${review.score}" /></td>
                </tr>
           	</c:forEach>
        </table>
         	
	</div> 
	
</body>

</html>

<!--  review -->