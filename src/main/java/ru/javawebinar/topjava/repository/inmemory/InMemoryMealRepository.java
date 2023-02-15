package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, 1));
        save(new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
                "Еда второго пользователя", 1000), 2);
        save(new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).minus(1, ChronoUnit.HOURS),
                "Еда второго пользователя", 1000), 2);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("Saving: {} for user: {}", meal, userId);
        Map<Integer, Meal> userMealsMap = repository.computeIfAbsent(userId, map -> new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            userMealsMap.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return userMealsMap.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("Deleting: {} for user: {}", id, userId);
        Map<Integer, Meal> userMealsMap = repository.get(userId);
        return userMealsMap != null && userMealsMap.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("Getting: {} for user: {}", id, userId);
        Map<Integer, Meal> userMealsMap = repository.get(userId);
        return userMealsMap == null ? null : userMealsMap.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("Get all for user: {}", userId);
        return getAllFiltered(userId, meal -> true);
    }

    @Override
    public List<Meal> getFilteredList(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("Get filtered list");
        return getAllFiltered(userId, meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDate(), startDate, endDate));
    }

    private List<Meal> getAllFiltered(int userId, Predicate<Meal> filter) {
        Map<Integer, Meal> meals = repository.get(userId);
        return CollectionUtils.isEmpty(meals) ? Collections.emptyList() :
                meals.values().stream()
                        .filter(filter)
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                        .collect(Collectors.toList());
    }
}
