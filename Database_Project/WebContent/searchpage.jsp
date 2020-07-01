<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<!DOCTYPE html>

<html>

<head>
	
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<title>Youtube</title>

</head>

<body>

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
					<tr>
						<td colspan="2"><a href="loginpage.jsp">Login</a></td>
					</tr>
			</table>
		</center>
	</form>

</body>

</html>