import controllers.MemoUIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Project Name: MemoView
 */

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        MemoUIController mainUIController = new MemoUIController();

        FXMLLoader mainUILoader = new FXMLLoader(getClass().getResource("MainUI.fxml"));
        mainUILoader.setController(mainUIController);

        Parent root = mainUILoader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Memorandum View");

        stage.setResizable(false);
        stage.show();
    }
}
