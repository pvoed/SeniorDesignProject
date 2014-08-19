<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="Skeleton.jsp" %>
<%@ page import="ycp.edu.seniordesign.model.User" %>

<html>

	<head>
		<Title>Edit Profile</Title>
	</head>
	
	<body background="https://cas.ycp.edu/cas/images/cas-bg.jpg">
		<div class="header"><A HREF="editProfile"> ${user.username}</A>&emsp;&emsp;&emsp;&emsp;&emsp;<A HREF="home">Home</A>&emsp;&emsp;&emsp;&emsp;&emsp;<A HREF="login">Logout</A></div>
		<div class="leftSidebar"><br>Campus Utilities <br/><p><A HREF="campusMap">Campus Map</A></p><A HREF="campusShuttleMap">Campus Shuttle Map</A></div>
		<div class="body">
			<c:if test="${isStudent&&(!isProfessor)}">
				<c:if test="${!empty errorMessage }">
					<div class="error">${errorMessage}</div>
				</c:if>
							
				UserName: ${user.username} <br/>
				Current Email Address: ${user.emailAddress} <br/>
				<c:if test="${user.commuter}">Current Status: Commuter</c:if><c:if test="${!user.commuter}">Current Status: Resident</c:if> <br/>
				Current Major: ${user.major} <br/>				
				
				<form action="${pageContext.servletContext.contextPath}/editProfile" method="post">
					<table>
						<tr>
							<td class="text">New Email Address: </td>
							<td> <input type="text" name="newEmailAddressBox" size="12" value="${user.emailAddress}" /></td>
						</tr>
						
						<tr>
							<td class="text">New Status: </td>
							<td> <input type="radio" name="commuterRadioButton" value="Commuter"/> Commuter</td>
						</tr>
						
						<tr>
							<td> <input type="radio" name="commuterRadioButton" value="Resident"/> Resident</td>
						</tr>
						
						<tr>
							<td class="text">New Major: </td>
							<td> <input type="text" name="newMajorBox" size="12" value="${user.major}" /></td>
						</tr>
						
						<tr>
							<td> <input name="ChangeFieldsButton" type="submit" value="Edit Profile" /></td>
							<td> <input name="ChangePasswordButton" type="submit" value="Change Password" /></td>
						</tr>
					</table>
				</form>				
			</c:if>
			
			<c:if test="${isProfessor}">
				<c:if test="${!empty errorMessage }">
					<div class="error">${errorMessage}</div>
				</c:if>
							
				UserName: ${user.username} <br/>
				Current Email Address: ${user.emailAddress} <br/>
				<c:if test="${isProfessor&&isStudent}">Current Major: ${user.major}<br/></c:if>			
				Current Phone Number: ${user.phoneNumber} <br/>
				Current Office Number: ${user.officeNumber} <br/>
				Current Biography: ${user.biography} <br/>
				
				<%
					User user = (User) request.getSession().getAttribute("user");
					System.out.println(user.getName());
					System.out.println(user.getPhoneNumber());
					System.out.println(user.getOfficeNumber());
					System.out.println(user.getEmailAddress());
				%>
				
				<form action="${pageContext.servletContext.contextPath}/editProfile" method="post">
					<table>
						<tr>
							<td class="text">New Email Address: </td>
							<td> <input type="text" name="newEmailAddressBox" size="12" value="${user.emailAddress}" /></td>
						</tr>
						
						<c:if test="${isProfessor&&isStudent}">
							<tr>
								<td class="text">New Major: </td>
								<td> <input type="text" name="newMajorBox" size="12" value="${user.major}" /></td>
							</tr>
						</c:if>
						
						<tr>
							<td class="text">New Phone Number: </td>
							<td> <input type="text" name="newPhoneNumberBox" size="12" value="${user.phoneNumber}" /></td>
						</tr>
						
						<tr>
							<td class="text">New Office Number: </td>
							<td> <input type="text" name="newOfficeNumberBox" size="12" value="${user.officeNumber}" /></td>
						</tr>
						
						<tr>
							<td class="test">New Biography: </td>
							<td> <textarea class="FormElement" name="newBiographyBox" id="term" cols="40" rows="4"></textarea></td>
						<tr>
						
						
						<tr>
							<td> <input name="ChangeFieldsButton" type="submit" value="Edit Profile" /></td>
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

		
		
