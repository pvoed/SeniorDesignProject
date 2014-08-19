<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="Skeleton.jsp" %>

<html>

	<head>
		<Title>Campus Map</Title>
	</head>
	
	<body background="https://cas.ycp.edu/cas/images/cas-bg.jpg">
		<div class="header"><A HREF="editProfile"> ${user.username}</A>&emsp;&emsp;&emsp;&emsp;&emsp;<A HREF="home">Home</A>&emsp;&emsp;&emsp;&emsp;&emsp;<A HREF="login">Logout</A></div>
		<div class="leftSidebar"><br>Campus Utilities <br/><p><A HREF="campusMap">Campus Map</A></p><A HREF="campusShuttleMap">Campus Shuttle Map</A></div>
		<div class="body">
			<iframe src="http://www.nextbus.com/googleMap/?a=york-pa&r=campus&d=loop&s=readco" height="100%" width="100%"></iframe>
		</div>	
		
		<div class="rightSidebar"><p>Campus Shuttle Hours:</p>
								<p>Monday 7:00 a.m. - 1:30 a.m.</p>
								<p>Tuesday 7:00 a.m. - 1:30 a.m.</p>
								<p>Wednesday 7:00 a.m. - 1:30 a.m.</p>
								<p>Thursday 7:00 a.m. - 2:30 a.m.</p>
								<p>Friday 7:00 a.m. - 2:30 a.m.</p>
								<p>Saturday 9:30 p.m. - 2:30 a.m.</p>
								<p>Sunday 9:45 p.m. - 1:30 a.m. </p>
		
		</div>
		<div class="footer">Random Copyright info goes here</div>
				
	</body>
	
</html>