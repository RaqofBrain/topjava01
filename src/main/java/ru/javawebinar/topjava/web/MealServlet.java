package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.MealRepositoryImpl;
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
    private final MealRepository mealRepository = new MealRepositoryImpl();
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String action = request.getParameter("action");

        if (action == null) {
            log.debug("getAllMeals");
            request.setAttribute("meals",
                    MealsUtil.getFilteredMealToList(mealRepository.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));

            request.getRequestDispatcher("mealsList.jsp").forward(request, response);
        }
        else if (action.equalsIgnoreCase("delete")) {
            int id = getIdFromQuery(request);
            mealRepository.delete(id);
            response.sendRedirect("meals");
        }
        else if (action.equalsIgnoreCase("create")) {
            Meal meal = new Meal(LocalDateTime.now(), "", 500);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("editMeal.jsp").forward(request, response);
        }
        else if (action.equalsIgnoreCase("update")) {
            Meal meal = mealRepository.getById(getIdFromQuery(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("editMeal.jsp").forward(request, response);
        }
        else {
            response.sendRedirect("meals");
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

        response.sendRedirect("meals");
    }

    private int getIdFromQuery(HttpServletRequest request) {
        String parameterId = request.getParameter("id");
        return Integer.parseInt(parameterId);
    }
}
