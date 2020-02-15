package edu.ucsd.cse110.walkwalkrevolution.activity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ActivityUtils {

    private static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    private static double CONVERSION_FACTOR = .413;
    private final static double FEET_IN_MILE = 5280;

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

    public static double stepsToMiles(long steps, long height) {
        // https://www.openfit.com/how-many-steps-walk-per-mile
        return steps/(FEET_IN_MILE / (CONVERSION_FACTOR * height / (double)12));
    }

    public static void setConversionFactor(double factor){
        ActivityUtils.CONVERSION_FACTOR = factor;
    }

}
