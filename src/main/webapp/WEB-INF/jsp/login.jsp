<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false"%>
<html>
<head>
    <title>Hospital login</title>
    <meta charset="UTF-8">
</head>
       <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
<style>

    body {
        padding: 20px;
        background: url("https://www.luxinombra.net/wp-content/uploads/2018/06/350829-widescreen-website-background-1920x1080-windows-xp.jpg");
    }

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
        width: 85%;
        height: 30px;
        margin-top: 20px;
        text-transform: uppercase;
        font-weight: bold;
    }

</style>
<body>
<h1><spring:message code="label.title" />

       <div class="btn-toolbar position-absolute top-0 end-0" role="toolbar" aria-label="Toolbar with button groups">
        <div class="btn-group me-2" role="group" aria-label="First group">
           <a href="?lang=en" class="btn btn-secondary btn-sm"><spring:message code="label.en" /></a>
           <a href="?lang=ru" class="btn btn-secondary btn-sm"><spring:message code="label.ru" /></a>
        </div>
       </div>



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