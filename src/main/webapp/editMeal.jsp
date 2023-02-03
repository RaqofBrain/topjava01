<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
    <head>
        <title>Add/Edit Meal | Java Enterprise (Topjava)</title>
    </head>
    <body>
        <h3><a href="index.html">Home</a></h3>
        <hr>
        <h2>Add Meal</h2>
        <form method="POST" action="meals">
            <input type="hidden" name="id" value="${meal.id}">
            <p>DateTime: <input type="datetime-local" name="dateTime" value="${meal.dateTime}"></p>
            <p>Description: <input type="text" name="description" value="${meal.description}"></p>
            <p>Calories: <input type="number" name="calories" value="${meal.calories}"></p>
            <input type="submit" value="Submit">
            <button onclick="window.history.back()" type="button">Cancel</button>
        </form>
    </body>
</html>
