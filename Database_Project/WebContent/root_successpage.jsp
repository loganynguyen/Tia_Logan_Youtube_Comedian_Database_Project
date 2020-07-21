<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
	
	<h2 align="center">The database has been initialized!</h1>
	
	<center>
	
	    <form action="videoinsertpage.jsp">
			<button type="submit">Upload a Video</button>
		</form>
		
		<br>
		
		<form action="searchpage.jsp">
			<button type="submit">Search Videos</button>
		</form>
		
		<br>
		
		<form action="user_favoritepage.jsp">
			<button type="submit">Your Favorite Comedians List</button>
		</form>
		
		<br>
		
		<form method="post" action="stats">
			<button type="submit">Database Statistics</button>
		</form>
		
		<br>
		
		<form method="post" action="listCool">
			<button color= "red" type="submit">Who is cool</button>
		</form>
		
		<br>
		
		<form method="post" action="listNew">
			<button type="submit">Who is new</button>
		</form>
				
		<br>
		
		<form method="post" action="listHot">
			<button type="submit">Who is hot</button>
		</form>
				
		<br>
		
		<form method="post" action="listTop">
			<button type="submit">Who is top</button>
		</form>
		<br>
		<form method="post" action="listPopular">
			<button type="submit">Popular Tags</button>
		</form>
		<br>
			</center>
		
	

</body>

</html>