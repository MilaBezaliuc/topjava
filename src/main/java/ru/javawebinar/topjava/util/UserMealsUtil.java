package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) throws IOException{

        List<UserMeal> mealList = new ArrayList<>();
        LocalDate date = LocalDate.of(2012, Month.JANUARY, 1 );
        Random r = new Random();
        for (int i=0; i<100000; i++) {
            date = date.plus(1, ChronoUnit.DAYS);
            mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(10, 0)), "Завтрак", 300 + r.nextInt(400)));
            mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(13, 0)), "Обед", 800 + r.nextInt(400)));
            mealList.add(new UserMeal(LocalDateTime.of(date, LocalTime.of(20, 0)), "Ужин", 300 + r.nextInt(400)));
        }

        LocalDateTime beforeRun = LocalDateTime.now();
        List<UserMealWithExceed> exceedList = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(13,0), 2000);
        //List<UserMealWithExceed> exceedList = getFilteredWithExceeded_usingLoop(mealList, LocalTime.of(7, 0), LocalTime.of(13,0), 2000);
        long runTimeMs = beforeRun.until(LocalDateTime.now(), ChronoUnit.MILLIS);
        long trueCount = exceedList.stream().filter(UserMealWithExceed::isExceed).count();
        System.out.printf("Implementation 1, mealList.size() = %d, exceedList.size() = %d, TRUE count = %d, runtime = %d%n", mealList.size(), exceedList.size(), trueCount, runTimeMs);

    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> totalCaloriesForDate = mealList
                .stream()
                .collect(Collectors.groupingBy(userMeal -> userMeal.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));

        return
                mealList.stream()
                        .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                        .map(meal -> new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                                totalCaloriesForDate.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
                        .collect(Collectors.toList());
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded_usingLoop(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesSumPerDay = new HashMap<>();
        for (UserMeal userMeal : mealList) {
            LocalDate date = userMeal.getDateTime().toLocalDate();
            caloriesSumPerDay.put(date, caloriesSumPerDay.getOrDefault(date, 0)  + userMeal.getCalories());
        }

        List<UserMealWithExceed> userMealWithExceeds = new ArrayList<>();
        for (UserMeal userMeal : mealList) {
            LocalDateTime dateTime = userMeal.getDateTime();
            if (TimeUtil.isBetween(dateTime.toLocalTime(), startTime, endTime)) {
                userMealWithExceeds.add(new UserMealWithExceed(dateTime, userMeal.getDescription(), userMeal.getCalories(),
                        caloriesSumPerDay.get(dateTime.toLocalDate()) > caloriesPerDay));
            }
        }
        return userMealWithExceeds;
    }

}
