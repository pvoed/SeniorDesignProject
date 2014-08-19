<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>Admin - Change User Type</title>
		
		<script src="http://code.jquery.com/jquery-latest.js"></script>
		<script type="text/javascript" src="http://jzaefferer.github.com/jquery-validation/jquery.validate.js"></script>

		<style type="text/css">
			label.error, .error {color: red;}
		</style>
		<script type="text/javascript">
			$(document).ready(function() {
			  	$("#changeUserTypeForm").validate();
			  	
			  	$(".approve").click(function() {
					requestId = $(this).attr('id').match(/(\d+)$/)[1];
					$("#requestIdElt").attr('value', requestId);
					$("#actionElt").attr('value', 'approve');
					$("changeUserTypeForm").submit();
				});
				
				$(".reject").click(function() {
					requestId = $(this).attr('id').match(/(\d+)$/)[1];
					$("#requestIdElt").attr('value', requestId);
					$("#actionElt").attr('value', 'reject');
					$("changeUserTypeForm").submit();
				});
			});
		</script>
	</head>
	
	<body style="background-color:#00FF00">	
		<a href="/Whiteboard/admin/home">Admin Home</a> <br />
		<a href="/Whiteboard/admin/approveCourse">Add Course</a> <br />
		<a href="/Whiteboard/admin/changeUserType">Change User Type</a> <br />
		<a href="/Whiteboard/admin/login">Logout</a> <p />
		
		<form id="changeUserTypeForm" action="${pageContext.servletContext.contextPath}/admin/changeUserType" method="post">
			<input type="hidden" name="submitted" value="true" />
			<input id="requestIdElt" type="hidden" name="requestId" value="-1" />
			<input id="actionElt" type="hidden" name="action" value="" />
			
			<c:if test="${empty changeUserTypeRequests}">
				There are currently no requests to change user types.
			</c:if>
			
			<c:if test="${! empty changeUserTypeRequests}">
				<c:forEach var="changeUserTypeRequest" items="${changeUserTypeRequests}">
					<table>
						<tr><td>Name:</td><td><input type="text" class="required" name="name${changeUserTypeRequest.id}" size="25" value="${changeUserTypeRequest.username}"/></td></tr>
						<tr><td>Email Address:</td><td><input type="text" class="required email" name="emailAddress${changeUserTypeRequest.id}" size="25" value="${changeUserTypeRequest.emailAddress}"/></td></tr>
						<tr><td>User Type:</td><td><input type="text" class="required" name="userType${changeUserTypeRequest.id}" size="25" value="${changeUserTypeRequest.newUserType}"/></td></tr>
						<tr><td><input id="approveChangeUserTypeButton${changeUserTypeRequest.id}" class="approve" type="submit" value="Accept" /></td><td><input id="rejectChangeUserTypeButton${changeUserTypeRequest.id}" class="reject" type="submit" value="Reject" /></td></tr>
					</table>
					<br/ >
				</c:forEach>
			</c:if>
		</form>
		
		<c:if test="${! empty errorMessage }">
			<p class="error">${errorMessage }</p>
		</c:if>
		<c:if test="${! empty updateMessage }">
			<p>${updateMessage }</p>
		</c:if>
	</body>
</html>