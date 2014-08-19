<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Login</title>
		
		<script src="http://code.jquery.com/jquery-latest.js"></script>
		<script type="text/javascript" src="http://jzaefferer.github.com/jquery-validation/jquery.validate.js"></script>

		<style type="text/css">
			label.error, .error {color: red;}
		</style>
		
		<script type="text/javascript">
			$(document).ready(function() {
			  	$("#loginForm").validate();
			});
		</script>

	</head>
	
	<body style="background-color:#00FF00">		
		<form id="loginForm" action="${pageContext.servletContext.contextPath}/admin/login" method="post">
			<table>
				<tr>
					<td class="text">Username: </td>
					<td> <input type="text" name="usernameBox" size="12" value="${username}" class="required"/></td>
				</tr>
				
				<tr>
					<td class="text">Password: </td>
					<td><input type="password" name="passwordBox" size="12" value="${password}" class="required"/></td>
				</tr>
				
				<tr>
					<td> <input name="loginButton" type="submit" value="Login" /></td>
				</tr>
			</table>
		</form>
		
		<c:if test="${! empty errorMessage }">
			<p class="error">${errorMessage }</p>
		</c:if>
	</body>
</html>