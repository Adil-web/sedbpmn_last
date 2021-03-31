package kz.kazgisa.utils;

import java.util.Calendar;
import java.util.Date;

public class CustomDateUtils {
    public static Date getDayStart(Date date) {
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(date);
        calStart.set(Calendar.HOUR_OF_DAY, 0);
        calStart.set(Calendar.MINUTE, 0);
        calStart.set(Calendar.SECOND, 0);
        calStart.set(Calendar.MILLISECOND, 0);
        date = calStart.getTime();
        return date;
    }

    public static Date getDayEnd(Date date) {
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(date);
        calEnd.set(Calendar.HOUR_OF_DAY, 23);
        calEnd.set(Calendar.MINUTE, 59);
        calEnd.set(Calendar.SECOND, 59);
        calEnd.set(Calendar.MILLISECOND, 999);
        date = calEnd.getTime();
        return date;
    }

    public static boolean isWeekend(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }
}
