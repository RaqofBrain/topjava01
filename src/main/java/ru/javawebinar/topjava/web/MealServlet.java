package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private ConfigurableApplicationContext context;
    private MealRestController mealRestController;

    @Override
    public void init() {
        context = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealRestController = context.getBean(MealRestController.class);
    }

    @Override
    public void destroy() {
        context.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);

        if (meal.isNew()) {
            mealRestController.create(meal);
        }
        else {
            mealRestController.update(meal, meal.getId());
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int mealId = getId(request);
                log.info("Delete id={}", mealId);
                mealRestController.delete(mealId);
                response.sendRedirect("meals");
                break;
            case "filter":
                log.info("get filtered list");
                String startDate = request.getParameter("startDate");
                String endDate = request.getParameter("endDate");
                String startTime = request.getParameter("startTime");
                String endTime = request.getParameter("endTime");

                request.setAttribute("meals", mealRestController.getFilteredList(
                        startDate.isEmpty() ? null : LocalDate.parse(startDate),
                        endDate.isEmpty() ? null : LocalDate.parse(endDate),
                        startTime.isEmpty() ? null : LocalTime.parse(startTime),
                        endTime.isEmpty() ? null : LocalTime.parse(endTime)
                ));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(null, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                       mealRestController.getById(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals",mealRestController.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
