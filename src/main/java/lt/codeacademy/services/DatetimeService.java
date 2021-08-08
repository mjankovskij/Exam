package lt.codeacademy.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DatetimeService {

    public static String getDateTimeString() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return myDateObj.format(myFormatObj);
    }

    public static long datetimeToLong(String datetime) {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(datetime, formatter)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }

    public static boolean hasTimePassed(String datetime) {
        long d1 = datetimeToLong(datetime);
        long d2 = datetimeToLong(getDateTimeString());
        d1 = d1 / (60 * 1000);
        d2 = d2 / (60 * 1000);
        return d2-d1 > 120;
    }

    public static boolean hasTimePassed(long d1) {
        long d2 = datetimeToLong(getDateTimeString());
        d1 = d1 / (60 * 1000);
        d2 = d2 / (60 * 1000);
        return d2-d1 > 120;
    }
}
