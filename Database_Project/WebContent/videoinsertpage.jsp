<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
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

	<form method="post" action="videoSubmit">
		<center>
			<table border="1" width="30%" cellpadding="5" bgcolor="pink">
				<center><h2>Video Submission Page</h2></center>
				<thead>
					<tr>
						<th colspan="2">Enter the following details of the video:</th>
					</tr>
				</thead>
				
				<tbody>
					<tr>
						<td>URL: </td>
						<td><input type="text" name="url" required/></td>
					</tr>
					
					<tr>
						<td>Comedian First Name: </td>
						<td><input type="text" name="comedianf" required/></td>
					</tr>
						<tr>
						<td>Comedian Last Name: </td>
						<td><input type="text" name="comedianl" required/></td>
					</tr>
					<tr>
						<td>Title of the video</td>
						<td><input type="text" name="title" required/></td>
					</tr>
					<tr>
						<td>Description</td>
						<td><input type="text" name="description" required/></td>
					</tr>
					<tr>
						<td>Tags</td>
						<td><input type="text" name="tag" /></td>
					</tr>
				
					<tr>
						<td><button type="submit" id="videoSubmit" value="videoSubmit">Submit Video</button></td>
					</tr>
					
					<h4><a href="user_successpage.jsp">Home</a></h4>
				</tbody>
			</table>
		</center>
	</form>	

</body>

</html>