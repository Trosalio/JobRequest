package client.controller;

import client.ui.model.AdvertiseEditorModel;
import client.ui.model.AdvertiseReviewModel;
import client.ui.model.JobEditorModel;
import client.ui.model.JobReviewModel;
import client.ui.view.AdvertiseEditorView;
import client.ui.view.AdvertiseReviewView;
import client.ui.view.JobEditorView;
import client.ui.view.JobReviewView;
import common.model.Job;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ViewManager {

    private Stage primaryStage;
    private MainController controller;

    // Client Main

    public void showStartUpView() {
        try {
            FXMLLoader startupUILoader = new FXMLLoader(getClass().getResource("/fxml/StartupUI.fxml"));
            Parent root = startupUILoader.load();

            // setup Startup model and UI
            StartupModel model = new StartupModel(this);
            StartupView startupView = startupUILoader.getController();
            startupView.setModel(model);

            // setup the primary stage
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setTitle("Startup");
            primaryStage.setOnHidden(e -> Platform.exit());
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Client - MO

    public void showAdvertiseReviewer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AdvertiseMaster.fxml"));
            Parent root = loader.load();

            // setup Advertise Master model and UI
            AdvertiseMasterModel model = new AdvertiseMasterModel(this);
            AdvertiseMasterView advertiseReviewView = loader.getController();
            advertiseReviewView.setViewModel(model);

            // setup the primary stage
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setTitle("Advertise Master");
            primaryStage.setOnHidden(e -> Platform.exit());
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showAdvertiseEditor(AdvertiseAdapter adapter) {
        try {
            FXMLLoader adsViewerUILoader = new FXMLLoader(getClass().getResource("/fxml/AdvertiseEditorUI.fxml"));
            Parent root = adsViewerUILoader.load();

            // setup Advertise Editor model and UI
            AdvertiseEditorModel model = new AdvertiseEditorModel(adapter);
            AdvertiseEditorView advertiseEditorView = adsViewerUILoader.getController();
            advertiseEditorView.setModel(model);
            advertiseEditorView.setupUI();

            // setup the stage
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Advertise Editor");
            stage.setResizable(false);
            stage.showAndWait();
            return model.isConfirm();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void showJobReviewer(AdvertiseAdapter adapter) {
        try {
            FXMLLoader jobReviewUILoader = new FXMLLoader(getClass().getResource("/fxml/JobReviewUI.fxml"));
            Parent root = jobReviewUILoader.load();

            // setup Job Reviewer model and UI
            JobReviewModel model = new JobReviewModel(this);
            model.setAdapter(adapter);
            JobReviewView jobReviewView = jobReviewUILoader.getController();
            jobReviewView.setModel(model);
            jobReviewView.setupUI();

            // setup the stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Job Reviewer");
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showJobEditor(Job job) {
        try {
            FXMLLoader jobEditUILoader = new FXMLLoader(getClass().getResource("/fxml/JobEditorUI.fxml"));
            Parent root = jobEditUILoader.load();

            // setup Job Editor model and UI
            JobEditorModel model = new JobEditorModel(this);
            model.setJob(job);
            JobEditorView jobEditorView = jobEditUILoader.getController();
            jobEditorView.setModel(model);
            jobEditorView.setupUI();

            // setup the stage
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Job Editor");
            stage.setResizable(false);
            stage.showAndWait();

            return model.isSaved();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Client - CMO
    public void showJobMasterReviewer() {
        try {
            FXMLLoader jobMasterReviewUILoader = new FXMLLoader(getClass().getResource("/fxml/JobMasterReviewUI.fxml"));
            Parent root = jobMasterReviewUILoader.load();

            // setup Job Editor model and UI
            JobMasterReviewModel model = new JobMasterReviewModel(this);
            JobMasterReviewView jobMasterReviewView = jobMasterReviewUILoader.getController();
            jobMasterReviewView.setModel(model);
            jobMasterReviewView.setupUI();

            // setup the stage
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Job Editor");
            stage.setResizable(false);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setController(MainController controller) {
        this.controller = controller;
    }

    public MainController getController() {
        return controller;
    }
}
