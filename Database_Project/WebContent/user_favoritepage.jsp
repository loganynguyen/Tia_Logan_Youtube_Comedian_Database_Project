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
            <caption><h2>List of Favorite Comedians</h2></caption>
            <tr>
                <th>Name</th>
                <th>Action</th>
            </tr>
            <c:forEach var="listFav" items="${listFav}">
                <tr>
                    <td><c:out value="${listFav}" /></td>
                    
                    <td>
                 		<form method="post" action="delete">
							<input type="hidden" name="id" id="id" value="${listFav}" />
							<input type="hidden" name="user" id="user" value="" />
							<button type="submit">Delete</button>
						</form>
					</td>
                </tr>
            </c:forEach>
            <h4><a href="user_successpage.jsp">Home</a></h4>
        </table>
    </div>   
</body>

</html>