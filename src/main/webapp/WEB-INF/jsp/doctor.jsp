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

<form action="/logout" method="POST">
     <button type="submit"><spring:message code="label.signOut" /></button>
</form>

         <c:forEach items="${treatments}" var="treatment">
           <table class="table">
             <thead>
             <tr>
                 <td><b>Name</b></td>
                 <td><b>Category</b></td>
                 <td>Diagnosis</td>
                 <td></td>
                 <td></td>
                 <td></td>
             </tr>
             </thead>
             <tr>
                 <td>${treatment.patient.name}</td>
                 <td>${treatment.category.name}</td>
                 <form action="/diagnosis/${treatment.id}" method="POST">
                 <td><input type="text" name="diagnosis" value=${treatment.diagnosis}></td>
                 <td><button type="submit">edit</button></td>
                 </form>
                 <td><button type="submit"><a href="/addProcedure/${treatment.id}">add procedure</a></button></td>
                 <form action="/discharge/${treatment.id}" method="POST">
                 <td><button type="submit">discharge</button></td>
                 </form>
             </tr>
             <tr>
                 <td><b>Procedure name</b></td>
                 <td><b>Type</b></td>
                 <td><b>Status</b></td>
             </tr>
                <c:forEach items="${treatment.proceduresList}" var="procedures">
                 <tr>
                          <td>${procedures.procedureName}</td>
                          <td>${procedures.type.name}</td>
                          <td>${procedures.status.name}</td>
                 </tr>
                </c:forEach>
             </br>
         </c:forEach>
     </table>


         <table class="table">
              <thead>
              <tr>
                  <td><b>Name</b></td>
                  <td><b>type</b></td>
                  <td>Status</td>
                  <td></td>
              </tr>
              </thead>
                     <c:forEach items="${proceduresList}" var="procedures">
                     <form action="doProcedures" method="POST">
                      <tr>
                      <td>${procedures.procedureName}</td>
                      <td>${procedures.type.name}</td>
                      <td>${procedures.status.name}</td>
                      <input type="hidden" name="procedures_id" value=${procedures.id}>
                      <td><button type="submit">execute</a></button></td>
                       </tr>
                      </form>
                       </c:forEach>
          </table>

</body>
</html>