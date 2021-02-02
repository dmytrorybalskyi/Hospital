<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta charset="UTF-8">
    <title>Treatment</title>
</head>
<body>

<span style="float: right">
    <a href="?lang=en">en</a>
    |
    <a href="?lang=ru">ru</a>
</span>

    <table class="table">
         <thead>
         <tr>
             <td><b>Name</b></td>
             <td><b>Category</b></td>
             <td></td>
             <td></td>
         </tr>
         </thead>

         <form action="/setDoctor/${treatment.id}" method="POST">
             <tr>
                 <td>${treatment.patient.name}</td>
                 <td>${treatment.category.name}</td>
                 <td><select class="selectpicker form-control form-select-button" name="doctor" required>
                                 <c:forEach items="${doctors}" var="doctor">
                                     <option value="${doctor.id}">${doctor.name}</option>
                                 </c:forEach>
                       </select></td>
                 <td><button type="submit">Set</a></button></td>
             </tr>
         </form>
     </table>
</form>
</body>
</html>