package com.cgi.ecm.reports.rest_service.csv;

import java.util.Calendar;
import java.util.Date;

/**
 * Group of utilities with days
 * Created by kortatu on 11/08/15.
 */
public class DateUtils {

    public static Date addDays(int daysToAdd) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, daysToAdd);
        return calendar.getTime();
    }

}
