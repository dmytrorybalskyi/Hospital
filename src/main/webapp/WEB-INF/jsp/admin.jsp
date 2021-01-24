<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta charset="UTF-8">
    <title>Main</title>
</head>
<body>

<p><spring:message code="label.hello"/> ${login}
<span style="float: right">
    <a href="?lang=en">en</a>
    |
    <a href="?lang=ru">ru</a>
</span>

 <p><a href="/addDoctor"><spring:message code="label.addNewDoctor"/></p>

<form action="/logout" method="POST">
     <button type="submit"><spring:message code="label.signOut" /></button>
</form>
</body>
</html>