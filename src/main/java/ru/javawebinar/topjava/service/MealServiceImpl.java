package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.Meal;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * GKislin
 * 06.03.2015.
 */

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    public Meal get(int id, int userId){
        return ExceptionUtil.checkNotFoundWithId(repository.get(id, userId), id);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        ExceptionUtil.checkNotFoundWithId(repository.delete(id, userId), id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.getAll(userId).stream().collect(Collectors.toList());
    }

    @Override
    public Meal update(Meal meal, int userId) throws NotFoundException {
        return ExceptionUtil.checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }
}
