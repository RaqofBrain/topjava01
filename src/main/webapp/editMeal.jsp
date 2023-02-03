<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<c:set var="title" value="${param.get('action') == 'create' ? 'Create' : 'Edit'}"/>
<fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedDateTime}" var="formattedDateTime"/>

<html>
    <head>
        <title>${title} Meal | Java Enterprise (Topjava)</title>
    </head>
    <body>
        <h3><a href="index.html">Home</a></h3>
        <hr>
        <h2>${title} Meal</h2>
        <form method="POST" action="meals">
            <input type="hidden" name="id" value="${meal.id}">
            <p>DateTime: <input type="datetime-local" name="dateTime" value="${formattedDateTime}"></p>
            <p>Description: <input type="text" name="description" value="${meal.description}"></p>
            <p>Calories: <input type="number" name="calories" value="${meal.calories}"></p>
            <input type="submit" value="Submit">
            <button onclick="window.history.back()" type="button">Cancel</button>
        </form>
    </body>
</html>
