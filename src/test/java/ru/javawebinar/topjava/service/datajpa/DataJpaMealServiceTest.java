package ru.javawebinar.topjava.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.NOT_FOUND;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaMealServiceTest extends MealServiceTest {
    @Test
    public void getMealWithUser() {
        Meal meal = service.getWithUser(MEAL1_ID, USER_ID);
        MEAL_MATCHER.assertMatch(meal, meal1);
        USER_MATCHER.assertMatch(meal.getUser(), user);
    }

    @Test
    public void getMealWithUserNotOwn() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> service.getWithUser(MEAL1_ID, GUEST_ID));
        Assert.assertEquals("Not found entity with id=" + MEAL1_ID, exception.getMessage());
    }

    @Test
    public void getMealWithUserNonExistingMeal() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> service.getWithUser(NOT_FOUND, USER_ID));
        Assert.assertEquals("Not found entity with id=" + NOT_FOUND, exception.getMessage());
    }
}
