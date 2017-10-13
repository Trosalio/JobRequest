import controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.MemoManager;
import models.persistents.DBManager;
import models.persistents.DatabaseConnector;
import models.persistents.SQLiteConnector;

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
        MemoManager memoManager = new MemoManager();
        DBManager dbManager = new DBManager(memoManager);
        DatabaseConnector databaseConnector = new SQLiteConnector();
        dbManager.setDatabaseConnector(databaseConnector);

        FXMLLoader mainUILoader = new FXMLLoader(getClass().getResource("/fxml/MainUI.fxml"));
        Parent root = mainUILoader.load();
        MainController mainController = mainUILoader.getController();
        mainController.setMemoManager(memoManager);
        mainController.setUpTableView();

        stage.setScene(new Scene(root));
        stage.setTitle("Memorandum View");
        stage.setResizable(false);
        stage.show();
    }
}
