package net.xdstar.rememberutil.Util;

import net.xdstar.rememberutil.DataBase.DBController;

import java.util.Calendar;

/**
 * Created by ljy on 16/10/25.
 */

public class RememberUtil {
    public static int YEARS_HOUR = 365 * 24;
    public static int MONTH_HOUR = 30 * 24;
    public static int DAY_HOUR = 24;

    private static RememberUtil instance = null;

    public static RememberUtil getInstance() {
        if (instance == null) {
            instance = new RememberUtil();
        }
        return instance;
    }

    public void updateUnit(int id) {
        DBController.instance().updateUnit(id);
    }

    public double computePriority(String oldUpdateTime, String newUpdateTime, int newReviseTime) {
        Calendar oldCalendar = TextUtil.String2Calendar(oldUpdateTime);
        Calendar newCalendar = TextUtil.String2Calendar(newUpdateTime);
        int timeOffset = getCalendarOffset(oldCalendar, newCalendar);
        double R1 = 1 - 0.56 * Math.pow(timeOffset, 0.06);
        double R2 = 1 / ((double)newReviseTime + 1);
        double newPriority = 1 / R1 + R2;   //It means reviseTime can only ensure decimal fraction， updateTime ensure integer.
        return newPriority;
    }

    public int getCalendarOffset(Calendar oldCalendar, Calendar newCalendar) {
        int yearOffset = (newCalendar.get(Calendar.YEAR) - oldCalendar.get(Calendar.YEAR)) * YEARS_HOUR;
        int monthOffset = (newCalendar.get(Calendar.MONTH) - oldCalendar.get(Calendar.MONTH)) * MONTH_HOUR;
        int dayOffset = (newCalendar.get(Calendar.DAY_OF_MONTH) - oldCalendar.get(Calendar.DAY_OF_MONTH)) * DAY_HOUR;
        int newHour = (newCalendar.get(Calendar.HOUR_OF_DAY));
        int oldHour = oldCalendar.get(Calendar.HOUR_OF_DAY);
        int hourOffset = newHour - oldHour;
        int offset = yearOffset + monthOffset + dayOffset + hourOffset;
        return offset;

    }


}
