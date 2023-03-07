package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.USER_MATCHER;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {
    @Test
    public void getWithMeals() {
        User user = service.getWithMeals(USER_ID);
        List<Meal> meals = new ArrayList<>(MealTestData.meals);
        Collections.reverse(meals);
        MEAL_MATCHER.assertMatch(user.getMeals(), meals);
        USER_MATCHER.assertMatch(user, UserTestData.user);
    }
}
