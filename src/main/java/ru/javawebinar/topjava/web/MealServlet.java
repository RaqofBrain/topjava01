package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private final MealRepository mealRepository = new InMemoryMealRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String MEALS_LIST_JSP = "mealsList.jsp";       // Path to page with list of meals
        String EDIT_MEAL_JSP = "editMeal.jsp";         // Path to edit meal page

        String action = request.getParameter("action");
        action = action == null ? "" : action;
        Meal meal;
        switch (action.toLowerCase()) {
            case "delete":
                log.info("Action: {}", action);
                int id = getIdFromQuery(request);
                mealRepository.delete(id);
                log.info("Deleted meal id: {}", id);
                response.sendRedirect("meals");
                log.info("Redirecting to meals list page");
                break;
            case "create":
                log.info("Action: {}", action);
                meal = new Meal(LocalDateTime.now(), "", 500);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher(EDIT_MEAL_JSP).forward(request, response);
                break;
            case "update":
                log.info("Action: {}", action);
                meal = mealRepository.getById(getIdFromQuery(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher(EDIT_MEAL_JSP).forward(request, response);
                break;
            default:
                log.info("Action: getMealsList");
                request.setAttribute("meals",
                        MealsUtil.getFilteredMealToList(mealRepository.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_THRESHOLD));
                request.getRequestDispatcher(MEALS_LIST_JSP).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        Integer intId = id.isEmpty() ? null : Integer.parseInt(id);
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        Meal meal = new Meal(intId, dateTime, description, calories);
        mealRepository.save(meal);
        log.info("Saving meal: {}", meal);
        response.sendRedirect("meals");
        log.info("Redirect to mealsList");
    }

    private int getIdFromQuery(HttpServletRequest request) {
        String parameterId = request.getParameter("id");
        return Integer.parseInt(parameterId);
    }
}
