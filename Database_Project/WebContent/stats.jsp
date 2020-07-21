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
  		<h4><a href="root_successpage.jsp">Home</a></h4>
    	
	    <form method="post" action="viewUserVideos">
			<table border="1" width="70%" align="center">  
		        <caption><h2>Users that have posted the most videos</h2></caption> 	
	         	<tr>
	            	<th>Username/email</th>
		        </tr>    
	            <tr>
	            	<c:forEach var="prodUsers" items="${prodUsers}">
		                <td>
		                	<c:out value="${prodUsers.email}"/>
		            		<input type="hidden" name="field" value="${prodUsers.email}"/>
		            		<button type="submit" id="viewUserVideos">View their videos</button>
	            		</td>
        		    </c:forEach>
           		</tr>	
		    </table>
	    </form>
	    
	    <table border="1" width="70%" align="center">  
	        <caption><h2>Users that give the most positive reviews</h2></caption> 	
         	<tr>
            	<th>Username/email</th>
	        </tr>    
        	<c:forEach var="posUsers" items="${posUsers}">
	            <tr>
	                <td><c:out value="${posUsers.email}"/></td>
	            </tr>
        	</c:forEach>
	    </table>
	    
	    <table border="1" width="70%" align="center">  
	        <caption><h2>Videos with all poor reviews</h2></caption> 	
         	<tr>
            	<th>Title</th>
	        </tr>    
        	<c:forEach var="poorVideos" items="${poorVideos}">
	            <tr>
	                <td><c:out value="${poorVideos.title}"/></td>
	            </tr>
        	</c:forEach>
	    </table>
	    
   	    <table border="1" width="70%" align="center">  
	        <caption><h2>Users with identical favorite lists</h2></caption> 	
        	
        	<tr>
	           	<th>User 1</th>
	           	<th>User 2</th>
	        </tr>
	        
	       	<c:forEach var="identialUsers" items="${identialUsers}">
	       		<tr> 		    
		       		<td>
			       		<form method="post" action="favorite_alt">
			               	<c:out value="${identialUsers.user1}"/>
			           		<input type="hidden" name="email" value="${identialUsers.user1}"/>
			           		<button type="submit" id="favorite_alt">View list</button>
			           	</form>
		           	</td>
		           	<td>
			           	<form method="post" action="favorite_alt">
			               	<c:out value="${identialUsers.user2}"/>
			           		<input type="hidden" name="email" value="${identialUsers.user2}"/>
			           		<button type="submit" id="favorite_alt">View list</button>
			           	</form>
		       		</td>
	        	</tr>
        	</c:forEach>
	    </table>
		
		<form method="post" action="commonFavTool">
		    <table border="1" width="70%" align="center">  
		        <caption><h2>Common Favorite Comedian Tool</h2></caption> 	
	        	<tr>
		           	<th>Name</th>
		        </tr>
		       	<tr>
		        	<td>
						<select name="user1">
							<option value="">Select the 1st comedian</option>
			             	<c:forEach var="userList" items="${userList}">				
								<option value="${userList.email}"><c:out value="${userList.email}"/></option>
							</c:forEach>
						</select>
						
						<select name="user2">
							<option value="">Select the 2nd comedian</option>
			             	<c:forEach var="userList" items="${userList}">				
								<option value="${userList.email}"><c:out value="${userList.email}"/></option>
							</c:forEach>
						</select>
						
						<button type="submit" id="commonFavTool">Go</button>
					</td>
				</tr>
		    </table>
	    </form>

    </center>
</body>

</html>