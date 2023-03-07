package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class DataJpaMealRepository implements MealRepository {

    private final CrudMealRepository crudMealRepository;
    private final CrudUserRepository crudUserRepository;

    public DataJpaMealRepository(CrudMealRepository crudMealRepository, CrudUserRepository crudUserRepository) {
        this.crudMealRepository = crudMealRepository;
        this.crudUserRepository = crudUserRepository;
    }

    @Override
    //Suppressing NPE warning for last return (already checked for null)
    @SuppressWarnings("DataFlowIssue")
    public Meal save(Meal meal, int userId) {
        meal.setUser(crudUserRepository.getReferenceById(userId));
        if (meal.isNew()) {
            crudMealRepository.save(meal);
            return meal;
        }
        return (get(meal.getId(), userId) == null) ? null : crudMealRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudMealRepository.delete(id, userId) != 0;
    }

    @Override
    @SuppressWarnings("DataFlowIssue")
    public Meal get(int id, int userId) {
        Optional<Meal> mealOptional = crudMealRepository.findById(id);
        return mealOptional.filter(meal -> meal.getUser().getId() == userId).orElse(null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudMealRepository.getAll(userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudMealRepository.getBetweenHalfOpen(userId, startDateTime, endDateTime);
    }

    @Override
    public Meal getWithUser(int id, int userId) {
        return crudMealRepository.getWithUser(id, userId);
    }
}
