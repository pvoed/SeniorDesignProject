<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="Skeleton.jsp" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ycp.edu.seniordesign.model.Assignment" %>
<%@ page import="ycp.edu.seniordesign.model.GradeWeight" %>

<html>

	<head>
		<Title>Gradebook</Title>
	</head>
	
	<% int counter = 0; %>
	
	<body background="https://cas.ycp.edu/cas/images/cas-bg.jpg">
		<div class="header"><A HREF="editProfile"> ${user.username}</A>&emsp;&emsp;&emsp;&emsp;&emsp;<A HREF="home">Home</A>&emsp;&emsp;&emsp;&emsp;&emsp;<A HREF="login">Logout</A></div>
		<div class="leftSidebar"><br>Campus Utilities <br/><p><A HREF="campusMap">Campus Map</A></p><A HREF="campusShuttleMap">Campus Shuttle Map</A></div>
		<div class="body">
			<div style="text-align:center">${course.name}</div>
			<br/>
			<table border="1" style="text-align:center; margin-left:auto; margin-right:auto; empty-cells:hide;">
				<tr>
					<td>Assignment</td>
					<td>Due Date</td>
					<td>Category</td>
					<td>Earned Points</td>
					<td>Possible Points</td>
				</tr>
				<c:forEach var="assign" items="${assignments}">
					<%
						ArrayList<Assignment> assignments = (ArrayList<Assignment>) request.getSession().getAttribute("assignments");
						Assignment assignment = assignments.get(counter);
						ArrayList<GradeWeight> gradeList = (ArrayList<GradeWeight>) request.getSession().getAttribute("grades");
						
						for(GradeWeight g : gradeList)
						{
							if(g.getId() == assignment.getGradeWeightType())
							{
								request.setAttribute("Type", g.getName());
							}
						}
					%>
					<tr>
						<td>${assign.name}</td>
						<td>${assign.dueDate}</td>
						<td>${Type}</td>
						<c:choose>
							<c:when test="${assign.earnedPoints == -1}">
								<td align="center">-</td>
							</c:when>
							<c:otherwise>
								<td align="center">${assign.earnedPoints}</td>
							</c:otherwise>
						</c:choose>
						<td align="center">${assign.possiblePoints}</td>
					</tr>
					<% counter++; %>
				</c:forEach>
				<tr>
					<td colspan="3" align="center">Course Average: </td>
					<td>${grade}</td>
				</tr>
			</table>
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