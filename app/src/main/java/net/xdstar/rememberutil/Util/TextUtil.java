package net.xdstar.rememberutil.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ljy on 16/10/25.
 */

//YEAR-MONTH-DAY
public class TextUtil {
    public static String calendar2String(Calendar calendar) {
        String formatStr = String.format("%1$s-%2$s-%3$s %4$s:%5$s", "yyyy", "MM", "dd", "HH", "mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
        String outputStr = dateFormat.format(calendar.getTime());
        return outputStr;
    }

    public static String calendarString2DateString(String calendarString) {
        Calendar calendar = String2Calendar(calendarString);
        String formatStr = String.format("%1$s-%2$s-%3$s", "yyyy", "MM", "dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
        String outputStr = dateFormat.format(calendar.getTime());
        return outputStr;
    }

    public static String calendar2DateString(Calendar calendar) {
        String formatStr = String.format("%1$s-%2$s-%3$s", "yyyy", "MM", "dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
        String outputStr = dateFormat.format(calendar.getTime());
        return outputStr;
    }

    public static Calendar String2Calendar(String initDate) {
        if (initDate == null) {
            return null;
        }
        String year = spliteString(initDate, "-", "index", "front");
        String monthAndDayAndHour = spliteString(initDate, "-", "index", "back");
        String month = spliteString(monthAndDayAndHour, "-", "index", "front");
        String dayAndHour = spliteString(monthAndDayAndHour, "-", "index", "back");
        String day = spliteString(dayAndHour, " ", "index", "front");
        String hourAndMinute = spliteString(dayAndHour, " ", "index", "back");
        String hour = spliteString(hourAndMinute, ":", "index", "front");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day), Integer.parseInt(hour), 0);
        return calendar;
    }


    public static String spliteString(String srcStr, String pattern,
                                      String indexOrLast, String frontOrBack) {
        String result = "";
        int loc = -1;
        if (indexOrLast.equalsIgnoreCase("index")) {
            loc = srcStr.indexOf(pattern);
        } else {
            loc = srcStr.lastIndexOf(pattern);
        }
        if (frontOrBack.equalsIgnoreCase("front")) {
            if (loc != -1)
                result = srcStr.substring(0, loc);
        } else {
            if (loc != -1)
                result = srcStr.substring(loc + 1, srcStr.length());
        }
        return result;
    }
}
