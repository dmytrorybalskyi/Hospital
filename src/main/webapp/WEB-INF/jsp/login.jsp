<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false"%>
<html>
<head>
    <title>Hospital login</title>
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
<h1><spring:message code="label.title" />

<span style="float: right">
    <a href="?lang=en">en</a>
    |
    <a href="?lang=ru">ru</a>
</span>


<form action="/login" method="POST">
    <div><label> <spring:message code="label.login" /> <input type="text" name="login"/> </label></div>
    <div><label> <spring:message code="label.password" />  <input type="password" name="password"/> </label></div>
    <button type="submit"><spring:message code="label.signIn" /></button>
    <p><a href="/registration"><spring:message code="label.registerNewAccount" /></a></p>

        <c:if test="${param.error ne null}">
            <p><spring:message code="label.wrongLogin" /></p>
        </c:if>

</form>
</body>
</html>