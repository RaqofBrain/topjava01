<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
    <head>
        <title>Meals</title>
    </head>
    <body>
        <h3><a href="../index.html">Home</a></h3>
        <hr>
        <h2>Meals</h2>
        <table border="1">
            <tr>
                <td>Date</td>
                <td>Description</td>
                <td>Calories</td>
            </tr>
            <c:forEach items="${meals}" var="meal">
                <c:if test="${meal.excess == false}">
                    <tr style="color: green">
                </c:if>
                <c:if test="${meal.excess == true}">
                    <tr style="color: red">
                </c:if>
                        <td>
                            <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
                            <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}"/>
                        </td>
                        <td>${meal.description}</td>
                        <td>${meal.calories}</td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
