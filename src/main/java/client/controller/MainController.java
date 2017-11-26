package client.controller;

import client.ClientMain;
import client.ui.AdvertiseMasterViewer;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class MainController {
    private AdvertiseManager adManager;

    public void handleLoad() {
        this.adManager = new AdvertiseManager();
    }

    public void start() {
//        DBManager dbManager = new DBManager(advertiseManager);
//        DBConnector DBConnector = new SQLiteConnector();
//        dbManager.setDatabaseConnector(DBConnector);
        try {
            FXMLLoader adMaster = new FXMLLoader(getClass().getResource("/AdvertiseMasterUI.fxml"));
            Parent root = adMaster.load();
            AdvertiseMasterViewer adMasterViewer = adMaster.getController();
            adMasterViewer.setAdvertiseManager(adManager);
            adMasterViewer.setUpTableView();

            ClientMain.getPrimaryStage().setScene(new Scene(root));
            ClientMain.getPrimaryStage().setTitle("Advertise Master View");
            ClientMain.getPrimaryStage().setResizable(false);
            // perform cleanup before closing the program
            ClientMain.getPrimaryStage().setOnHidden(e -> Platform.exit());
            ClientMain.getPrimaryStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
