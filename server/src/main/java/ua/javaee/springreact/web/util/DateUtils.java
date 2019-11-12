package ua.javaee.springreact.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static final String DEFAULT_PATTERN = "dd-MM-yyyy";

    public static Date parseDate(String dateToParse) {
        try {
            Date date = new SimpleDateFormat(DEFAULT_PATTERN).parse(dateToParse);
            return date;
        } catch (ParseException e) {
            return null;
        } catch (NullPointerException e) {
            return null;
        }
    }


    public static Date parseDate(String dateToParse, String pattern) {
        try {
            Date date = new SimpleDateFormat(pattern).parse(dateToParse);
            return date;
        } catch (ParseException e) {
            return null;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static Date parseDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_PATTERN);
        String dateString = formatter.format(date);
        return parseDate(dateString);
    }
}
