package common.formatter;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * This helper class handles format date components
 * into a desirable format.
 */

public class DateFormatter {

    private String datePattern = "dd/MM/yyyy";

    /**
     * Fluent method for change date pattern.
     *
     * @param pattern - Pattern to be change into
     * @return Object itself after a modification.
     */
    public DateFormatter ofPattern(String pattern) {
        datePattern = pattern;
        return this;
    }

    /**
     * @return Current common.formatter based on current pattern
     */
    public DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofPattern(datePattern);
    }

    /**
     * Format all DatePickers value into a current format
     *
     * @param datePickers A list of DatePicker to be formatted.
     */
    public void formatDatePickers(DatePicker... datePickers) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        for (DatePicker datePicker : datePickers) {
            datePicker.setConverter(new StringConverter<>() {
                @Override
                public String toString(LocalDate date) {
                    return (date != null ? formatter.format(date) : "");
                }

                @Override
                public LocalDate fromString(String string) {
                    if (string != null && !string.isEmpty()) {
                        return LocalDate.parse(string, formatter);
                    } else {
                        return null;
                    }
                }
            });
            if (datePicker.getValue() != null) {
                datePicker.setValue(datePicker.getValue());
            } else {
                datePicker.setValue(LocalDate.now());
            }
        }
    }

    /**
     * Set date picker to prevent it from being able to pick a date before an allowed date
     *
     * @param datePicker  a date picker to be prevented from picking a certain date
     * @param allowedDate the first date that date picker will allow to pick
     */
    public void preventDatePickedBefore(DatePicker datePicker, LocalDate allowedDate) {
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                boolean isDateBefore = false;
                if (allowedDate != null) {
                    isDateBefore = date.isBefore(allowedDate);
                }
                if (empty || isDateBefore) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ff696e;");
                }

            }
        });
    }

    /**
     * Format all LocalDate TableColumn into a current format.
     *
     * @param columns A list of TableColumn from TableView
     * @param <T>     A class of where property LocalDate belongs.
     */
    @SafeVarargs
    public final <T> void formatDateColumn(TableColumn<T, LocalDate>... columns) {
        for (TableColumn<T, LocalDate> column : columns) {
            column.setCellFactory(cell -> new TableCell<>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        setText(DateTimeFormatter.ofPattern(datePattern).format(item));
                    }
                }

            });
        }
    }
}
