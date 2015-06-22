package com.soundmix.domain;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class TestTimeUtil {
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss", Locale.getDefault());

    public static void setCurrentTimeTo(String dateFormat) throws ParseException {
        DateTimeUtils.setCurrentMillisFixed(simpleDateFormat.parse(dateFormat).getTime());
    }

    public static DateTime toDateTime(String dateAsString) {
        try {
            return new DateTime(simpleDateFormat.parse(dateAsString));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void resetTime() {
        DateTimeUtils.setCurrentMillisSystem();
    }
}

