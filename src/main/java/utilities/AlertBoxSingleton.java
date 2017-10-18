package utilities;

import javafx.scene.control.Alert;

/**
 * Project Name: MemoView
 */
public class AlertBoxSingleton {
    private static AlertBoxSingleton ourInstance = new AlertBoxSingleton();

    public static AlertBoxSingleton getInstance() {
        return ourInstance;
    }

    private AlertBoxSingleton() {
    }

    private Alert.AlertType getAlertTypeFromString(String title) {
        return title.toUpperCase().equals("ERROR") ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION;
    }

    public void popAlertBox(String title, String msg) {
        Alert alertBox = new Alert(getAlertTypeFromString(title));
        alertBox.setTitle(title);
        alertBox.setHeaderText(null);
        alertBox.setContentText(msg);
        alertBox.showAndWait();
    }
}
