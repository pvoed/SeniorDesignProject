<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Password Recovery</title>
		
		<style type="text/css">
			.error
			{
				color: red;
			}
			
			.text
			{
				color: white;
			}
		</style>
	</head>
	
	<body style="background-color:#003366">
		<c:if test="${!empty errorMessage }">
			<div class="error">${errorMessage}</div>
		</c:if>
		
		<form action="${pageContext.servletContext.contextPath}/recoverPass" method="post">
			<table>
				<tr>
					<td class="text">Username: </td>
					<td> <input type="text" name="usernameBox" size="12" value="${username}" /></td>
				</tr>
				
				<tr>
					<td class="text">Email: </td>
					<td><input type="text" name="emailBox" size="12" value="${email}" /></td>
				</tr>
				
				<tr>
					<td> <input name="recoveryButton" type="submit" value="Recover" /></td>
				</tr>
				
			</table>
		</form>
	</body>
</html>