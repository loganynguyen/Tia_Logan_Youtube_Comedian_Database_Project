<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>

<html>

<head>
	
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Youtube</title>

</head>

<body>

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
						<td><input type="text" name="url"/></td>
					</tr>
					
					<td>
						 <label >Choose the comedian for the video:</label> </td>
				    <td>
 						 <select id="cars" name="cars">
   						 <option value="volvo">Volvo</option>
    					<option value="saab">Saab</option>
   						 <option value="fiat">Fiat</option>
   						 <option value="audi">Audi</option>
 						 </select>
					</td>
					<tr>
						<td>Title of the video</td>
						<td><input type="text" name="title"/></td>
					</tr>
					<tr>
						<td>Description</td>
						<td><input type="text" name="description"/></td>
					</tr>
					<tr>
						<td>Tags</td>
						<td><input type="text" name="tag"/></td>
					</tr>
				
					<tr>
						<td><button type="submit" id="videoSubmit" value="videoSubmit">Submit Video</button></td>
					</tr>
					<tr>
						<td colspan="2"><a href="registerpage.jsp">Not a Registered User? Register for an account</a></td>
					</tr>
			</table>
		</center>
	</form>

</body>

</html>