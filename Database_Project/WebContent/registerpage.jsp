<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="youtube.UserDAO" %>

<!DOCTYPE html>

<html>

<head>
	
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<title>Youtube</title>

</head>

<body>
	<form method="post" action="register">
		<center>
			<table border="1" width="30%" cellpadding="5" bgcolor="pink">
				
				<thead>
					<tr>
						<th colspan="2">Registration</th>
					</tr>
				</thead>
				
				<tbody>
					<tr>
						<td>First Name</td>
						<td><input type="text" name="firstname" required/></td>
					</tr>
					<tr>
						<td>Last Name</td>
						<td><input type="text" name="lastname" required/></td>
					</tr>
					<tr>
						<td>Email/Username</td>
						<td><input type="email" name="email" required/></td>
					</tr>
					<tr>
						<td>Password</td>
						<td><input type="password" name="password" required/></td>
					</tr>
					<tr>
						<td>Confirm Password</td>
						<td><input type="password" name="password2" required/></td>
					</tr>
					<tr>
						<td>Age</td>
						<td><input type="text" name="age" required/></td>
					</tr>
					<tr>
						<td><button type="submit" id="register" value="register">Submit</button></td>
					</tr>
					<tr>
						<td colspan="2"><a href="loginpage.jsp">Already Registered? Login Here</a></td>
					</tr>
				</tbody>
			</table>
		</center>
	</form>

</body>

</html>