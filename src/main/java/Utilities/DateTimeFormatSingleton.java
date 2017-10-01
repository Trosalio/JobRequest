package Utilities;

import java.time.format.DateTimeFormatter;

/**
 * Project Name: MemoView
 */
public class DateTimeFormatSingleton {
    private static DateTimeFormatSingleton ourInstance = new DateTimeFormatSingleton();

    public static DateTimeFormatSingleton getInstance() {
        return ourInstance;
    }

    private DateTimeFormatSingleton() {
    }

    public DateTimeFormatter getDateTimeFormat(){
        return DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }
    public DateTimeFormatter getDateTimeFormatOf(String dateFormat){
        return DateTimeFormatter.ofPattern(dateFormat);
    }
}
