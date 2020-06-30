<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	
    <div align="center">
       <h3 align="center"> User Table </h3>

	<table border="1" width="70%" align="center">

	<tr>

	<th>Videos</th>
	<tr>
		<td><a href = "youtube.com">Link</a></td>
	</tr>

	<c:forEach items="${listVideo}" var="video">

		<tr>	
			<td><a src="${listVideo}">${listVideo}</a></td>
		</tr>

</c:forEach>

</table>
    </div>   
</body>
</html>