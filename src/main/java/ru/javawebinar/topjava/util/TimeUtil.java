package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * GKislin
 * 07.01.2015.
 */
public class TimeUtil {

    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static String parseDate(LocalDateTime dateTime){
        return dateTime.toLocalDate() + "  " + dateTime.toLocalTime();
    }
}
