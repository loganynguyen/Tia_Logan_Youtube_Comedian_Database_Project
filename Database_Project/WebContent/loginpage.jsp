<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="youtube.UserDAO" %>

<!DOCTYPE html>

<html>

<head>
	
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

	<title>Youtube</title>

</head>


<body>
	
	<h2 align="center">Youtube Login</h1>
	
	<form method="post" action="login">
		<center>
			<table border="2" width="30%" cellpadding="3" bgcolor="pink">
			
				<thead>
					<tr>
						<th colspan="2">Login</th>
					</tr>
				</thead>
				
				<tbody>
					<tr>
						<td>Email</td>
						<td><input type="text"name="email"/></td>
					</tr>
					<tr>
						<td>Password</td>
						<td><input type="password" name="password"/></td>
					</tr>
					<tr>
						<td><button type="submit" id="login" value="login">Login</button></td>
					</tr>
					<tr>
						<td colspan="2"><a href="initializepage.jsp">Register for an account</a></td>
					</tr>
			</table>
		</center>
	</form>
									
</body>

</html>