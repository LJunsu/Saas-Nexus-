<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	${credential }
	<ul>
		<c:forEach items="${labelList }" var="mail">
			<li>${mail.snippet }</li>
		</c:forEach>
	</ul>
</body>
</html>