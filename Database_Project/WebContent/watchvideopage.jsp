<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>

<html>

<head>
	
	<meta charset="ISO-8859-1">
	
	<title>Youtube</title>

</head>

<body>
	<center>
        <c:forEach var="video" items="${listVideo}">
		
	        <video controls>
				<source id="${video.url}" src="" type=video/mp4>
				<source id="${video.url}" src="" type=video/ogg>
				<source id="${video.url}" src="" type=video/webm>
				Your browser doesn't support the video tag.			
			</video>
			
			<a href="${video.url}">"${video.url}"</a>					
			<h2><c:out value="${video.title}" /></h2>
	        <h5><c:out value="${video.description}" /></h4>
			         	
			<form action="searchpage.jsp">
				<button type="submit">Search another video</button>
			</form>
		</c:forEach>
		
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
						<td><input type="text" name="review"/></td>
						<td><button type="submit" id="review" value="review">Submit</button></td>
					</tr>
			</table>
		</center>
	</form>
		
	</center>

</body>

</html>