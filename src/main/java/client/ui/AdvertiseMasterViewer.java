package client.ui;

import client.controllers.AdvertiseAdapter;
import common.formatter.DateFormatter;
import common.models.Advertise;
import client.controllers.AdvertiseManager;
import common.utilities.AlertBoxSingleton;
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

/**
 * Project Name: MemoView
 */

public class AdvertiseMasterViewer {

    @FXML
    private Button deleteButton, editButton;
    @FXML
    private TableView<AdvertiseAdapter> adsTable;
    @FXML
    private TableColumn<AdvertiseAdapter, LocalDate> cDateColumn, sDateColumn, eDateColumn;
    @FXML
    private TableColumn<AdvertiseAdapter, String> subjColumn, refNoColumn;

    private AdvertiseManager advertiseManager;

    @FXML
    private void onAdd() {
        AdvertiseAdapter adapter = new AdvertiseAdapter(new Advertise());
        if (popAdsViewerWindow(adapter)) {
            advertiseManager.addAds(adapter);
            changeButtonsState();
        }
    }

    @FXML
    private void onDelete() {
        if(AlertBoxSingleton.getInstance().popAlertBox("Confirmation", "Deleting...", "คุณต้องการจะลบโฆษณานี้?")){
            int removeIndex = adsTable.getSelectionModel().getSelectedIndex();
            advertiseManager.deleteAds(removeIndex);
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
                    popJobReviewWindow(adsTable.getSelectionModel().getSelectedItem().getAdvertise());
                } else {
                    System.out.println("no item was found");
                }
            }
        }
    }

    private boolean popAdsViewerWindow(AdvertiseAdapter adapter) {
        try {
            FXMLLoader adsViewerUILoader = new FXMLLoader(getClass().getResource("/AdvertiseUI.fxml"));
            Parent root = adsViewerUILoader.load();
            AdvertiseViewer advertiseViewer = adsViewerUILoader.getController();
            advertiseViewer.setCurrentAdapter(adapter);

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Advertise");
            stage.setResizable(false);
            stage.showAndWait();
            return advertiseViewer.isSaved();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void popJobReviewWindow(Advertise advertise) {
        try {
            FXMLLoader JobReviewUILoader = new FXMLLoader(getClass().getResource("/JobReviewUI.fxml"));
            Parent root = JobReviewUILoader.load();
            JobReviewViewer jobReviewViewer = JobReviewUILoader.getController();
            jobReviewViewer.setCurrentAds(advertise);
            jobReviewViewer.prepareComponents();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Advertise Master");
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUpTableView() {
        // set list into table as usual
        adsTable.setItems(advertiseManager.getAdvertiseList());
        cDateColumn.setCellValueFactory(cell -> cell.getValue().createDateProperty());
        subjColumn.setCellValueFactory(cell -> cell.getValue().nameProperty());
        refNoColumn.setCellValueFactory(cell -> cell.getValue().refNoProperty());
        sDateColumn.setCellValueFactory(cell -> cell.getValue().startDateProperty());
        eDateColumn.setCellValueFactory(cell -> cell.getValue().endDateProperty());

        // set default time locale to all date columns
        DateFormatter dateFormatter = new DateFormatter();
        dateFormatter.formatDateColumn(cDateColumn, sDateColumn, eDateColumn);
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
}
