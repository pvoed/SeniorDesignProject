<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>Admin - Add Course</title>
		
		<script src="http://code.jquery.com/jquery-latest.js"></script>
		<script type="text/javascript" src="http://jzaefferer.github.com/jquery-validation/jquery.validate.js"></script>

		<style type="text/css">
			label.error, .error {color: red;}
		</style>
		<script type="text/javascript">
			var requestId;
						
			$(document).ready(function() {
			  	$("#approveCourseForm").validate();
			  	
				$(".approve").click(function() {
					requestId = $(this).attr('id').match(/(\d+)$/)[1];
					$("#requestIdElt").attr('value', requestId);
					$("#actionElt").attr('value', 'approve');
					$("approveCourseForm").submit();
				});
				
				$(".reject").click(function() {
					requestId = $(this).attr('id').match(/(\d+)$/)[1];
					$("#requestIdElt").attr('value', requestId);
					$("#actionElt").attr('value', 'reject');
					$("approveCourseForm").submit();
				});
			});

		</script>
	</head>
	
	<body style="background-color:#00FF00">	
		<a href="/Whiteboard/admin/home">Admin Home</a> <br />
		<a href="/Whiteboard/admin/approveCourse">Add Course</a> <br />
		<a href="/Whiteboard/admin/changeUserType">Change User Type</a> <br />
		<a href="/Whiteboard/admin/login">Logout</a> <p />
		
		<form id="approveCourseForm" action="${pageContext.servletContext.contextPath}/admin/approveCourse" method="post">
			<input type="hidden" name="submitted" value="true" />
			<input id="requestIdElt" type="hidden" name="requestId" value="-1" />
			<input id="actionElt" type="hidden" name="action" value="" />
			
			<c:if test="${empty pendingCourses}">
				There are currently no requests to add courses.
			</c:if>
			
			<c:if test="${! empty pendingCourses}">
				<c:forEach var="pendingCourse" items="${pendingCourses}">
					<table>
						<tr><td>Course Name:</td><td><input type="text" class="required" name="courseName${pendingCourse.id}" size="25" value="${pendingCourse.courseName}"/></td></tr>
						<tr><td>Professor Name:</td><td><input type="text" class="required" name="professorName${pendingCourse.id}" size="25" value="${pendingCourse.professorName}"/></td></tr>
						<tr><td>Email Address:</td><td><input type="text" class="required email" name="emailAddress${pendingCourse.id}" size="25" value="${pendingCourse.emailAddress}"/></td></tr>
						<tr><td>Time:</td><td><input type="text" class="required" name="time${pendingCourse.id}" size="25" value="${pendingCourse.time}"/></td></tr>
						<tr><td>Course Number:</td><td><input type="text" class="required digits" name="courseNumber${pendingCourse.id}" minlength="3" maxlength="3" size="25" value="${pendingCourse.courseNumber}"/></td></tr>
						<tr><td>Section Number:</td><td><input type="text" class="required digits" name="sectionNumber${pendingCourse.id}" minlength="3" maxlength="3" size="25" value="${pendingCourse.courseSection}"/></td></tr>
						<tr><td>Credits:</td><td><input type="text" class="required digits" name="credits${pendingCourse.id}" minlength="1" maxlength="1"size="25" value="${pendingCourse.credits}"/></td></tr>
						<tr><td>Days:</td><td><input type="text" class="required" name="days${pendingCourse.id}" size="25" value="${pendingCourse.days}"/></td></tr>
						<tr><td>Location:</td><td><input type="text" class="required" name="location${pendingCourse.id}" size="25" value="${pendingCourse.location}"/></td></tr>
						<tr><td>CRN:</td><td><input type="text" class="required digits" name="CRN${pendingCourse.id}" maxlength="5" size="25" value="${pendingCourse.CRN}"/></td></tr>
						<tr><td>Description:</td><td><input type="text"  class="required" name="description${pendingCourse.id}" size="25" value="${pendingCourse.description}"/></td></tr>
						<tr><td><input id="approveCourseButton${pendingCourse.id}" class="approve" type="submit" value="Accept" /></td><td><input id="rejectCourseButton${pendingCourse.id}" class="reject" type="submit" value="Reject" /></td></tr>
					</table>
					<br />
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