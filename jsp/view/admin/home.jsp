<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Admin Home</title>
	</head>
	
	<body style="background-color:#00FF00">
		<a href="/Whiteboard/admin/home">Admin Home</a> <br />
		<a href="/Whiteboard/admin/approveCourse">Add Course</a> <br />
		<a href="/Whiteboard/admin/changeUserType">Change User Type</a> <br />
		<a href="/Whiteboard/admin/login">Logout</a> <p />
		
		Welcome "${admin.username}".
	</body>
</html>