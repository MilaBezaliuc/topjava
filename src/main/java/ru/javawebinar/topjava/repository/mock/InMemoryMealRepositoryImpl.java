package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.to.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Comparator<Meal> USER_MEAL_COMPARATOR = Comparator.comparing(Meal::getDateTime).reversed();
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        //MealsUtil.MEALS.forEach(this::save);
        MealsUtil.MEALS.forEach(um -> save(um, 2));
        save(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510), 1);
        save(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500), 1);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Objects.requireNonNull(meal);
        Integer mealId = meal.getId();
        if (meal.isNew()) {
            mealId = counter.incrementAndGet();
            meal.setId(mealId);
        }
        else if (get(mealId, userId) == null) {
            return null;
        }
        Map<Integer, Meal> map = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        map.put(meal.getId(), meal);
        map.put(mealId, meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return repository.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        return userMeals == null ? null : userMeals.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        Map<Integer, Meal> mealMap = repository.get(userId);
        return mealMap == null? Collections.emptyList():
                mealMap.values()
                .stream()
                .sorted(USER_MEAL_COMPARATOR).collect(Collectors.toList());
    }
}

