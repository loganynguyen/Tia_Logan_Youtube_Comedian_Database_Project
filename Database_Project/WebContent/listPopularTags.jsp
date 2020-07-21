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
            <caption><h2>Popular Tags</h2></caption>
            
            <c:forEach var="popularTags" items="${popularTags}">
                <tr>
                    <td><c:out value="${popularTags}" /></td>
                          	
                </tr>
           	</c:forEach>
           	
           	<h4><a href="root_successpage.jsp">Home</a></h4>
           	
        </table>
         	
	</div> 
	
</body>

</html>