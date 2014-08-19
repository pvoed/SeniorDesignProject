<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Login</title>
		
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
		
		<form action="${pageContext.servletContext.contextPath}/login" method="post">
			<table>
				<tr>
					<td class="text">Username: </td>
					<td> <input type="text" name="usernameBox" size="12" value="${username}" /></td>
				</tr>
				
				<tr>
					<td class="text">Password: </td>
					<td><input type="password" name="passwordBox" size="12" value="${password}" /></td>
				</tr>
				
				<tr>
					<td> <input name="loginButton" type="submit" value="Login" /></td>
					<td> <input name="registerButton" type="submit" value="Register" /></td>
					<td> <input name="recoverPassButton" type="submit" value="Recover" /></td>
				</tr>
			</table>
		</form>
	</body>
</html>