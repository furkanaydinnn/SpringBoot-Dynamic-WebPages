<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create New Owner</title>
</head>
<body>
		<form:form modelAttribute="owner" method="post">
			First Name:<form:input path="firstName"/><br/>
			Last Name:<form:input path="lastName"/><br/>
			<form:button name="submit">Delete</form:button>
	   </form:form>
</body>
</html>