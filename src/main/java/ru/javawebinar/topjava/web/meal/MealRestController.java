package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */

@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    public Meal get(int id){
        return service.get(id, AuthorizedUser.id());
    }

    public Meal create(Meal meal){
        meal.setId(null);
        return service.save(meal, AuthorizedUser.id());
    }

    public void update(Meal meal, int id){
        meal.setId(id);
        service.update(meal, AuthorizedUser.id());
    }

    public List<MealWithExceed> getAll(){
        return MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay());
    }

    public void delete(int id){
        service.delete(id, AuthorizedUser.id());
    }
}
