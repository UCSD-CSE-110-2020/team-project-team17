package edu.ucsd.cse110.walkwalkrevolution.activity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ActivityUtils {

    private static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    public static LocalDateTime stringToTime(String time) {
        return LocalDateTime.parse(time, formatter);
    }

    public static String timeToString(LocalDateTime dateTime){
        return dateTime.format(formatter);
    }

    public static String timeToMonthDay(LocalDateTime dateTime){
        int mth = dateTime.getMonthValue();
        int day = dateTime.getDayOfMonth();
        return (mth < 10 ? "0" : "") + mth +"/"+ (day < 10 ? "0" : "") + day;
    }

}
