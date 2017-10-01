package Utilities;

import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * ~Created by~
 * Name:   Thanapong Supalak (Trosalio)
 * ID:     5810405029
 * Project Name: MemoView
 */
public class DatePickerFormatSingleton {
    private static DatePickerFormatSingleton ourInstance = new DatePickerFormatSingleton();

    public static DatePickerFormatSingleton getInstance() {
        return ourInstance;
    }

    private DatePickerFormatSingleton() {
    }

    public void setDatePickerFormat(DatePicker datePicker, DateTimeFormatter dateTimeFormatter) {
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateTimeFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateTimeFormatter);
                } else {
                    return null;
                }
            }
        });
        datePicker.setValue(LocalDate.now());
    }
}
