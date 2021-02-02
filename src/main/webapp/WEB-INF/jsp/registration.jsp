<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>Hospital registration</title>
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
<h1><spring:message code="label.registration" />
<span style="float: right">
    <a href="?lang=en">en</a>
    |
    <a href="?lang=ru">ru</a>
</span>
<form action="/registration" method="POST" commandName="patient">
    <spring:message code="label.login" />:<br/><input type="text" name="login"><br/>
       <c:if test="${loginNotBlank==true}">
            <p><spring:message code="label.loginBlank" /></p>
       </c:if>
       <c:if test="${loginLength==true}">
            <p><spring:message code="label.loginTooLong" /></p>
       </c:if>
    <spring:message code="label.password" />:<br/><input type="password" name="password"><br/>
       <c:if test="${passwordNotBlank==true}">
            <p><spring:message code="label.passwordBlank" /></p>
       </c:if>
    <spring:message code="label.name" />:<br/><input type="text" name="name"><br/>
       <c:if test="${nameNotBlank==true}">
             <p><spring:message code="label.nameBlank" /></p>
       </c:if>
       <c:if test="${nameLength==true}">
             <p><spring:message code="label.nameTooLong" /></p>
       </c:if>
    <spring:message code="label.age" />:<br/><input required type="number" name="age"><br/>
       <c:if test="${ageMax==true}">
             <p><spring:message code="label.ageMax" /></p>
       </c:if>
       <c:if test="${ageMin==true}">
             <p><spring:message code="label.ageMin" /></p>
       </c:if>
    <button type="submit"><spring:message code="label.register" /></button>
       <c:if test="${message==true}">
             <p><spring:message code="label.userExists" /></p>
       </c:if>
</form>
</body>
</html>