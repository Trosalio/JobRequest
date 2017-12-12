package client.controller;

import client.ui.view.AdvertiseReviewView;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ViewManager {

    private Stage primaryStage;
    private MainController mainController;

    public void showPrimaryStage() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("src/main/resource/fxml/AdvertiseReviewUI.fxml"));
            Parent root = loader.load();

            // setup Advertise Master UI
            AdvertiseReviewView advertiseReviewView = loader.getController();
            advertiseReviewView.setStageController(mainController);

            // setup the primary stage
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setTitle("Advertise Viewer");
            primaryStage.setOnHidden(e -> Platform.exit());
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPrimaryStage(Stage primaryStage) { this.primaryStage = primaryStage; }

    public void setupStageControl(MainController mainController) {
        this.mainController = mainController;
    }


}
