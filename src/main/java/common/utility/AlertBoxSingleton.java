package common.utility;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertBoxSingleton {
    private static AlertBoxSingleton ourInstance = new AlertBoxSingleton();

    public static AlertBoxSingleton getInstance() {
        return ourInstance;
    }

    private AlertBoxSingleton() {
    }

    public boolean popAlertBox(String alertType, String title, String msg) {
        boolean isConfirmation = alertType.toUpperCase().equals("CONFIRMATION");
        boolean isError = alertType.toUpperCase().equals("ERROR");
        boolean isInformation = alertType.toUpperCase().equals("INFORMATION");
        if (isConfirmation) {
            return  popConfirmationBox(title, msg);
        } else if (isError) {
            popErrorBox(title, msg);
        } else if (isInformation) {
            popInfomationBox(title, msg);
        }
        return true;
    }

    public void popErrorBox(String title, String msg) {
        Alert alertBox = new Alert(Alert.AlertType.ERROR);
        alertBox.setTitle(title);
        alertBox.setHeaderText(null);
//        alertBox.initStyle(StageStyle.UTILITY);
        alertBox.setContentText(msg);
        alertBox.showAndWait();
    }

    public void popInfomationBox(String title, String msg) {
        Alert alertBox = new Alert(Alert.AlertType.INFORMATION);
        alertBox.setTitle(title);
        alertBox.setHeaderText(null);
//        alertBox.initStyle(StageStyle.UTILITY);
        alertBox.setContentText(msg);
        alertBox.showAndWait();
    }

    public boolean popConfirmationBox(String title, String msg) {
        Alert alertBox = new Alert(Alert.AlertType.CONFIRMATION);
        alertBox.setTitle(title);
        alertBox.setHeaderText(null);
        alertBox.setContentText(msg);
        Optional<ButtonType> result = alertBox.showAndWait();
        return result.filter(buttonType -> buttonType == ButtonType.OK).isPresent();
    }
}
