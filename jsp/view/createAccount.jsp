<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>Create Account</title>
		
		<script src="http://code.jquery.com/jquery-latest.js"></script>
		<script type="text/javascript" src="http://jzaefferer.github.com/jquery-validation/jquery.validate.js"></script>

		<style type="text/css">
			label.error {color: red;}
		</style>
		<script type="text/javascript">
			$(document).ready(function() {
			  	$("#createAccountForm").validate();
			});
		</script>
		
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
	
	<body style="background-color:#663300">
		<c:if test="${!empty errorMessage }">
			<div class="error">${errorMessage }</div>
		</c:if>
		
		<form id="createAccountForm" action="${pageContext.servletContext.contextPath}/createAccount" method="post">
			<table>
				<tr>
					<td class="text">Username:</td>
					<td> <input type="text" class="required" name="usernameBox" size="12" value="${username}" minlength="6" /></td>
				</tr>
				
				<tr>
					<td class="text">Password: </td>
					<td><input type="password" class="required" name="passwordBox" size="12" value="${password}" minlength="8"/></td>
				</tr>
				
				<tr>
					<td class="text">Email: </td>
					<td><input type ="text" class="required email" name="emailBox" size="12" value="${email}" /></td>
				</tr>
				
				<tr>
					<td> <input name="createButton" type="submit" value="Create Account" /></td>
				</tr>
			</table>
		</form>
	</body>
</html>