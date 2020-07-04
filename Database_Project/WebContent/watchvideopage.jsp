<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
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

	<center>
        <c:forEach var="video" items="${listVideo}">
					
			<iframe width="893" height="502" src="${video.url}" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
			<h2><c:out value="${video.title}" /></h2>
	        <h5><c:out value="${video.description}" /></h4>	
	        
	        <form method="post" action="addFav">
	        	<center>
      				<input type="hidden" name="id" value="${video.comedianid}"/>
	        		<button type="submit" id="addFav" value="addFav">Add comedian to list of favorites</button>
	        	</center>	
			</form>
			
			<form method="post" action="review">
				<center>
					<table border="1" width="30%" cellpadding="5" bgcolor="white">
						
						<thead>
							<tr>
								<th colspan="2">Submit Review</th>
							</tr>
						</thead>
						
						<tbody>
							<tr>
								<td><input type="text" name="remark"/></td>
								<input type="hidden" name="url" value="${video.url}"/>
								<input type="hidden" name="postUser" value="${video.postUser}"/>						
																
								<td>
									<select name="rating">
										<option value="">Select Rating</option>
										<option value="poor">Poor</option>
										<option value="fair">Fair</option>							
										<option value="good">Good</option>
										<option value="excellent">Excellent</option>
									</select>
								</td>
							</tr>
							
							<td><button type="submit" id="review" value="review">Submit</button></td>
						<h4><a href="user_successpage.jsp">Home</a></h4>
						</tbody>
					</table>
				</center>
			</form>
	</c:forEach>
	
	<br>
	
	<form action="searchpage.jsp">
		<button type="submit">Search another video</button>
	</form>
		
	</center>

</body>

</html>