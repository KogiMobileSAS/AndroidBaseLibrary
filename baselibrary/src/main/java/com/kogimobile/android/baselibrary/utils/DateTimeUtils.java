package com.kogimobile.android.baselibrary.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @author Julian Cardona on 4/14/15.
 */
public class DateTimeUtils {

    public static DateTimeFormatter presenter;
    public static DateTimeFormatter formatter;

    public static String convertToPatternFromPattern(String fromPattern,String date,String toPattern){
        return getStringPatternFromDateTime(toPattern, getDateTimeFromPattern(fromPattern, date));
    }

    public static DateTime getDateTimeFromPattern(String inputPattern,String date){
        formatter = DateTimeFormat.forPattern(inputPattern);
        return formatter.parseDateTime(date);
    }

    public static String getStringPatternFromDateTime(String pattern, DateTime date){
        presenter =  DateTimeFormat.forPattern(pattern);
        return presenter.print(date);
    }

}
