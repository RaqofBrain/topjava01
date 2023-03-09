package ru.javawebinar.topjava.service.datajpa;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNull;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {
    @Test
    public void getWithMeals() {
        User user = service.getWithMeals(USER_ID);
        List<Meal> meals = new ArrayList<>(MealTestData.meals);
        Assertions.assertThat(meals).containsExactlyInAnyOrderElementsOf(MealTestData.meals);
        USER_MATCHER.assertMatch(user, UserTestData.user);
    }

    @Test
    public void getWithMealsUserWithoutFood() {
        assertNull(service.getWithMeals(GUEST_ID));
    }
}
