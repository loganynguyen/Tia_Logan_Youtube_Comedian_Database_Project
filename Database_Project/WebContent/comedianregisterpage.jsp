<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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

	<form method="post" action="comedianRegister">
		<center>
			<table border="1" width="30%" cellpadding="5" bgcolor="pink">
				<center><h2>Comedian Registration Page</h2></center>
				<center><h3>A comedians must be registered before submitting a new video!</h3></center>
				<thead>
					<tr>
						<th colspan="2">Enter the following details of the comedian:</th>
					</tr>
				</thead>
				
				<tbody>
					<tr>
						<td>First name: </td>
						<td><input type="text" name="comedianf" required/></td>
					</tr>
					
					<tr>
						<td>Last name: </td>
						<td><input type="text" name="comedianl" required/></td>
					</tr>
					<tr>
						<td>Birthplace:</td>
						<td><input type="text" name="birthplace" required/></td>
					</tr>
					<tr>
						<td>Birthdate:</td>
						<td><input type="date" name="birthdate" required/></td>
					</tr>
								
					<tr>
						<td><button type="submit" id="comedianRegister" value="comedianRegister">Register Comedian</button></td>
					</tr>					
			</table>
		</center>
	</form>

</body>

</html>