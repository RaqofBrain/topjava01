package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(1, 0), LocalTime.of(23, 59), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(6, 0), LocalTime.of(15, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumOfDayCalories = new HashMap<>();
        for (UserMeal meal : meals) {
            LocalDate date = meal.getDateTime().toLocalDate();
            sumOfDayCalories.put(date, sumOfDayCalories.getOrDefault(date, 0) + meal.getCalories());
        }

        List<UserMealWithExcess> result = new ArrayList<>();
        for (UserMeal meal : meals) {
            LocalDateTime localDateTime = meal.getDateTime();
            if (TimeUtil.isBetweenHalfOpen(localDateTime.toLocalTime(), startTime, endTime))
                result.add(new UserMealWithExcess(localDateTime, meal.getDescription(), meal.getCalories(),
                        sumOfDayCalories.get(localDateTime.toLocalDate()) > caloriesPerDay));
        }

        return result;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dayCaloriesSum = meals.stream()
                .collect(Collectors.groupingBy(m -> m.getDateTime().toLocalDate(),
                        Collectors.summingInt(UserMeal::getCalories)));

        return meals.stream()
                .filter(m -> TimeUtil.isBetweenHalfOpen(m.getDateTime().toLocalTime(), startTime, endTime))
                .map(m -> new UserMealWithExcess(m.getDateTime(), m.getDescription(), m.getCalories(),
                        dayCaloriesSum.get(m.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
