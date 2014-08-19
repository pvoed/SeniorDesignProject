<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="Skeleton.jsp" %>

<html>

	<head>
		<Title>Change Password</Title>
		
		<script src="http://code.jquery.com/jquery-latest.js"></script>
		<script type="text/javascript" src="http://jzaefferer.github.com/jquery-validation/jquery.validate.js"></script>
		
		<script type="text/javascript">
			$(document).ready(function() {
			  	$("#changePasswordForm").validate();
			});
		</script>
	</head>
	
	<body background="https://cas.ycp.edu/cas/images/cas-bg.jpg">
		<div class="header"><A HREF="editProfile"> ${user.username}</A>&emsp;&emsp;&emsp;&emsp;&emsp;<A HREF="home">Home</A>&emsp;&emsp;&emsp;&emsp;&emsp;<A HREF="login">Logout</A></div>
		<div class="leftSidebar"><br>Campus Utilities <br/><p><A HREF="campusMap">Campus Map</A></p><A HREF="campusShuttleMap">Campus Shuttle Map</A></div>
		<div class="body">
			<c:if test="${isStudent||isProfessor}">
				<c:if test="${!empty errorMessage }">
					<div class="error">${errorMessage}</div>
				</c:if>
							
				<form id ="changePasswordForm" action="${pageContext.servletContext.contextPath}/changePassword" method="post">
					<table>
						<tr>
							<td class="text">Old Password: </td>
							<td> <input class="required" type="password" name="oldPasswordBox" size="12" value="${password}" /></td>
						</tr>
						
						<tr>
							<td class="text">New Password: </td>
							<td> <input id="newPasswordBox" class="required" type="password" name="newPasswordBox" size="12" value="${password}" minlength="8"/></td>
						</tr>
						
						<tr>
							<td class="text">Confirm New Password: </td>
							<td> <input name="confirmNewPasswordBox" class="required" type="password" name="confirmNewPasswordBox" size="12" value="${password}" minlength="8" /></td>
						</tr>
						
						<tr>
							<td> <input name="ChangePasswordButton" type="submit" value="Change Password" /></td>
						</tr>
					</table>
				</form>
			</c:if>	
		</div>	
		
		<div class="rightSidebar">
			<c:if test="${isStudent}">
				<c:if test="${enrolledCourses != null}">
					Enrolled Courses: <br/>
					<c:forEach var="course" items="${enrolledCourses}">
						<A HREF="scourse?id=${course.id}">${course.name}</A><br/>
					</c:forEach>
				</c:if>
			</c:if>
			<c:if test="${isProfessor}">
				<c:if test="${taughtCourses != null}">
					Taught Courses: <br/>
					<c:forEach var="course" items="${taughtCourses}">
						<A HREF="pcourse?id=${course.id}">${course.name}</A><br/>
					</c:forEach>
				</c:if>
			</c:if>
			<c:if test="${isStudent}">
				Upcoming Assignments: <br />
				<c:if test="${upcomingAssignments == null}">
					No upcoming assignments.<br />
				</c:if>
				<c:if test="${upcomingAssignments != null}">
					<c:forEach var="assignment" items="${upcomingAssignments}">
						<b>${assignment.key.name}</b> <br />
						Class: ${assignment.value} <br />
						Due Date: ${assignment.key.dueDate} <p />
					</c:forEach>
				</c:if>
			</c:if>
			<p />
			<A HREF="addCourse">Add Course</A><br/>
			<A HREF="changeUserType">Change User Type</A><br/>
		</div>
		<div class="footer">Random Copyright info goes here</div>
				
	</body>
	
</html>