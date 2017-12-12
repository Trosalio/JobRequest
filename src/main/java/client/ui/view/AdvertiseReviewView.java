package client.ui.view;

import client.controller.AdvertiseAdapter;
import client.controller.MainController;
import common.formatter.DateFormatter;
import common.model.Advertise;
import client.controller.AdvertiseManager;
import common.utility.AlertBoxSingleton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class AdvertiseReviewView {

    @FXML
    private Button deleteButton, editButton;
    @FXML
    private TableView<AdvertiseAdapter> adsTable;
    @FXML
    private TableColumn<AdvertiseAdapter, LocalDate> cDateColumn;
    @FXML
    private TableColumn<AdvertiseAdapter, String> refNoColumn, subjColumn;

    private AdvertiseManager advertiseManager;
    private MainController mainController;

    @FXML
    private void onAdd() {
        AdvertiseAdapter adapter = new AdvertiseAdapter(new Advertise());
        if (popAdsViewerWindow(adapter)) {
            advertiseManager.addAdvertise(adapter);
            changeButtonsState();
        }
    }

    @FXML
    private void onDelete() {
        if(AlertBoxSingleton.getInstance().popAlertBox("Confirmation", "Deleting...", "คุณต้องการจะลบโฆษณานี้?")){
            int removeIndex = adsTable.getSelectionModel().getSelectedIndex();
            advertiseManager.deleteAdvertise(removeIndex);
            changeButtonsState();
        }
    }

    @FXML
    private void onEdit() {
        AdvertiseAdapter adapter = advertiseManager.getCurrentAdapter();
        if (adapter != null) {
            if (popAdsViewerWindow(adapter)) {
                advertiseManager.editAds(adapter);
            }
        }
    }

    @FXML
    private void onMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getClickCount() == 2) {
                if (adsTable.getSelectionModel().getSelectedItem() != null) {
                    mouseEvent.consume();
                    popJobReviewWindow(adsTable.getSelectionModel().getSelectedItem().getAdaptee());
                } else {
                    System.out.println("no item was found");
                }
            }
        }
    }

    private boolean popAdsViewerWindow(AdvertiseAdapter adapter) {
        try {
            FXMLLoader adsViewerUILoader = new FXMLLoader(getClass().getResource("/fxml/AdvertiseEditorUI.fxml"));
            Parent root = adsViewerUILoader.load();
            AdvertiseEditorView advertiseEditorView = adsViewerUILoader.getController();
            advertiseEditorView.setCurrentAdapter(adapter);

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Advertise");
            stage.setResizable(false);
            stage.showAndWait();
            return advertiseEditorView.isSaved();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void popJobReviewWindow(Advertise advertise) {
        try {
            FXMLLoader JobReviewUILoader = new FXMLLoader(getClass().getResource("/fxml/JobReviewUI.fxml"));
            Parent root = JobReviewUILoader.load();
            JobReviewView jobReviewView = JobReviewUILoader.getController();
            jobReviewView.setCurrentAds(advertise);
            jobReviewView.prepareComponents();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Job Review");
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUpTableView() {
        // set list into table as usual
        adsTable.setItems(advertiseManager.getAdvertiseList());
        refNoColumn.setCellValueFactory(cell -> cell.getValue().refNoProperty());
        subjColumn.setCellValueFactory(cell -> cell.getValue().nameProperty());
        cDateColumn.setCellValueFactory(cell -> cell.getValue().createDateProperty());

        // set default time locale to all date columns
        DateFormatter dateFormatter = new DateFormatter();
        dateFormatter.formatDateColumn(cDateColumn);
        changeButtonsState();
        setUpItemListener();
    }

    private void changeButtonsState() {
        if (advertiseManager.getAdvertiseList().isEmpty()) {
            deleteButton.setDisable(true);
            editButton.setDisable(true);
            editButton.setVisible(false);
            deleteButton.setVisible(false);
            adsTable.getSelectionModel().clearSelection();
        } else {
            deleteButton.setDisable(false);
            editButton.setDisable(false);
            editButton.setVisible(true);
            deleteButton.setVisible(true);
        }
    }


    private void setUpItemListener() {
        adsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> advertiseManager.setCurrentAdapter(newSelection));

        advertiseManager.currentAdapterProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null) {
                adsTable.getSelectionModel().clearSelection();
            } else {
                adsTable.getSelectionModel().select(newSelection);
            }
        });
    }

    public void setAdvertiseManager(AdvertiseManager advertiseManager) {
        this.advertiseManager = advertiseManager;
    }

    public void setStageController(MainController mainController) {
        this.mainController = mainController;
    }
}
