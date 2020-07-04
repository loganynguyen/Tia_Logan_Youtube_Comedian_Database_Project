<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<!DOCTYPE html>

<html>

<head>
	
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
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

	<form method="post" action="search">
		<center>
			<table border="1" width="30%" cellpadding="5" bgcolor="lightblue">
				
				<thead>
					<tr>
						<th colspan="2">Search</th>
					</tr>
				</thead>
				
				<tbody>
					<tr>
						<td><input type="text" name="field"/></td>
						
						<td><button type="submit" id="search" value="search">Search</button></td>
					</tr>
				</tbody>
				
				<h4><a href="user_successpage.jsp">Home</a></h4>
				
			</table>
		</center>
	</form>
	
</body>

</html>