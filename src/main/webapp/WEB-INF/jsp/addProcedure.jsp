<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>Add Procedure</title>
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
<span style="float: right">
    <a href="?lang=en">en</a>
    |
    <a href="?lang=ru">ru</a>
</span>

<form action="/procedure/${treatment.id}" method="POST">
    Name of Procedure: <br/><input type="text" name="procedureName"><br/>
        <select class="selectpicker form-control form-select-button" name="type" required>
            <c:forEach items="${types}" var="type">
                <option value="${type.id}">${type.name}</option>
            </c:forEach>
        </select>

        <select class="selectpicker form-control form-select-button" name="doctor" required>
            <c:forEach items="${doctors}" var="doctor">
                    <option value="${doctor.id}">${doctor.name}</option>
            </c:forEach>
        </select>
           ${typeError}
    <button type="submit"><spring:message code="label.add"/></button>
           <c:if test="${message==true}">
                 <p><spring:message code="label.userExists" /></p>
           </c:if>
</form>
</body>
</html>