<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="Skeleton.jsp" %>

<html>
	<head>
		<Title>Add Assignment</Title>
		
		<script src="http://code.jquery.com/jquery-latest.js"></script>
		<script type="text/javascript" src="http://jzaefferer.github.com/jquery-validation/jquery.validate.js"></script>
		
		<script type="text/javascript">
			$(document).ready(function() {
				$("#uploadForm").validate();
				$("#addAssignmentForm").validate();
			});
		</script>
	</head>
	
	<body background="https://cas.ycp.edu/cas/images/cas-bg.jpg">
		<div class="header"><A HREF="editProfile"> ${user.username}</A>&emsp;&emsp;&emsp;&emsp;&emsp;<A HREF="home">Home</A>&emsp;&emsp;&emsp;&emsp;&emsp;<A HREF="login">Logout</A></div>
		<div class="leftSidebar"><br>Campus Utilities <br/><p><A HREF="campusMap">Campus Map</A></p><A HREF="campusShuttleMap">Campus Shuttle Map</A></div>
		<div class="body">
			<c:if test="${!empty errorMessage }">
				<div class="error">${errorMessage}</div>
			</c:if>
						
			<form id ="addAssignmentForm" action="${pageContext.servletContext.contextPath}/addAssignment?id=${courseID}" method="post">
				<table>
					<tr>
						<td>
							<table>
								<tr>
									<td class="text">Name: </td>
									<td><input class="required" type="text" name="nameBox" size="12"/></td>
								</tr>
								<tr>
									<td class="text">Year: </td>
									<td> <input class="required" type="text" name="yearBox" size="12"/></td>
								</tr>
								<tr>
									<td class="text">Month: </td>
									<td> <input class="required" type="text" name="monthBox" size="12"/> </td>
								</tr>
								<tr>
									<td class="text">Day: </td>
									<td> <input class="required" type="text" name="dayBox" size="12"/> </td>
								</tr>
								<tr>
									<td class="text">Possible Points: </td>
									<td><input class="required" type="text" name="possibleBox" size="12"/></td>
								</tr>
								<tr>
									<td>
										<select name="grade_weights">
											<% int grade_count = 0; %>
											<c:forEach var="item" items="${ListofGrades}">
												<option value="<%=grade_count%>">${item.name}</option>
												<% grade_count++; %>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<td> <input name="AddAssignmentButton" type="submit" value="Add Assignment" /></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</form>
		
			<div id="uploadDialog">
	            <form id="uploadForm" action="${pageContext.servletContext.contextPath}/addAssignment?id=${courseID}" method="post" enctype="multipart/form-data">
                	<input class="required" type="file" name="file"/> <br/>
                	<input name="UploadButton" type="submit" value="Upload File" />
                </form>
	        </div>
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
