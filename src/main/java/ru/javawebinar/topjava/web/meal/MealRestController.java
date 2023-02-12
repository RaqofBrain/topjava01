package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.getAuthUserId;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);
    private final MealService mealService;

    public MealRestController(MealService mealService) {
        this.mealService = mealService;
    }

    public List<MealTo> getAll() {
        log.info("Get all");
        return MealsUtil.getTos(mealService.getAll(getAuthUserId()), authUserCaloriesPerDay());
    }

    public List<MealTo> getFilteredList(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info("Get filtered list");
        return MealsUtil.getFilteredTos(mealService.getFilteredList(getAuthUserId(), startDate, endDate), authUserCaloriesPerDay(), startTime, endTime);
    }
    public Meal create(Meal meal) {
        log.info("Creating meal: {}", meal);
        checkNew(meal);
        return mealService.create(meal, getAuthUserId());
    }

    public void update(Meal meal, int id) {
        log.info("Updating: {} with id: {}", meal, meal.getId());
        assureIdConsistent(meal, id);
        mealService.update(meal, getAuthUserId());
    }

    public Meal getById(int id) {
        log.info("Get by id: {}", id);
        return mealService.get(id, getAuthUserId());
    }

    public void delete(int id) {
        log.info("Deleting: {}", id);
        mealService.delete(id, getAuthUserId());
    }
}