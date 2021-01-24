<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>Add Doctor</title>
    <meta charset="UTF-8">
</head>
<style>
    form {
        width: 300px;
        margin: 0 auto;
        font-family: Arial, sans-serif;
        font-size: 20px;

    }
    form input {
        width: 100%;
        line-height: 40px;
        font-size: 20px;
        padding-left: 15px;
        margin: 9px 0;
    }
    form button {
        width: 70%;
        height: 30px;
        margin-top: 20px;
        text-transform: uppercase;
        font-weight: bold;
    }
</style>
<body>
<h1><spring:message code="label.newDoctor"/>
<span style="float: right">
    <a href="?lang=en">en</a>
    |
    <a href="?lang=ru">ru</a>
</span>

<form action="/addDoctor" method="POST">
    <spring:message code="label.login" />:<br/><input type="text" name="login"><br/>
    <spring:message code="label.password" />:<br/><input type="password" name="password"><br/>
    <spring:message code="label.name" />:<br/><input type="text" name="name"><br/>
        <select class="selectpicker form-control form-select-button" name="category" required>
            <c:forEach items="${categories}" var="category">
                <option value="${category.id}">${category.name}</option>
            </c:forEach>
        </select>
    <button type="submit"><spring:message code="label.add"/></button>
    <p>${message}</p>
</form>
</body>
</html>