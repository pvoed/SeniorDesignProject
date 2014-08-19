<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="Skeleton.jsp" %>

<html>

	<head>
		<Title>Add Course</Title>
		
		<script src="http://code.jquery.com/jquery-latest.js"></script>
		<script type="text/javascript" src="http://jzaefferer.github.com/jquery-validation/jquery.validate.js"></script>

		<style type="text/css">
			label.error, .error {color: red;}
		</style>
		<script type="text/javascript">
			$(document).ready(function() {
			  	$("#addCourseForm").validate();
			});
		</script>
	</head>
	
	<body background="https://cas.ycp.edu/cas/images/cas-bg.jpg">
		<div class="header"><A HREF="editProfile"> ${user.username}</A>&emsp;&emsp;&emsp;&emsp;&emsp;<A HREF="home">Home</A>&emsp;&emsp;&emsp;&emsp;&emsp;<A HREF="login">Logout</A></div>
		<div class="leftSidebar"> Bus Schedule <br/> Campus Map</div>
		
		<div class="body">
			<form id="addCourseForm" action="${pageContext.servletContext.contextPath}/addCourse" method="post">
				<input type="hidden" name="submitted" value="true" />
		
				<table>
					<tr><td>Course Name:</td><td><input type="text" class="required" name="courseName" size="25"/></td></tr>
					<tr><td>Time:</td><td><input type="text" class="required" name="time" size="25" /></td></tr>
					<tr><td>Course Number:</td><td><input type="text" class="required digits" name="courseNumber" minlength="3" maxlength="3" size="25" /></td></tr>
					<tr><td>Section Number:</td><td><input type="text" class="required digits" name="sectionNumber" minlength="3" maxlength="3" size="25" /></td></tr>
					<tr><td>Credits:</td><td><input type="text" class="required digits" name="credits" minlength="1" maxlength="1"size="25" /></td></tr>
					<tr><td>Days:</td><td><input type="text" class="required" name="days" size="25" /></td></tr>
					<tr><td>Location:</td><td><input type="text" class="required" name="location" size="25" /></td></tr>
					<tr><td>CRN:</td><td><input type="text" class="required digits" name="CRN" maxlength="5" size="25" /></td></tr>
					<tr><td>Description:</td><td><input type="text"  class="required" name="description" size="25" /></td></tr>
					<tr><td><input name="addCourseButton" type="submit" value="Submit" /></td>
				</table>
			</form>
			
		<c:if test="${! empty errorMessage }">
			<p class="error">${errorMessage }</p>
		</c:if>
		<c:if test="${! empty updateMessage }">
			<p>${updateMessage }</p>
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
			<p />
			<c:if test="${isProfessor}">
				<c:if test="${taughtCourses != null}">
					Taught Courses: <br/>
					<c:forEach var="course" items="${taughtCourses}">
						<A HREF="pcourse?id=${course.id}">${course.name}</A><br/>
					</c:forEach>
				</c:if>
			</c:if>
			<p />
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