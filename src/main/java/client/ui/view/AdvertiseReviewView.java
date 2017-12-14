package client.ui.view;

import client.controller.AdvertiseAdapter;
import client.ui.model.AdvertiseReviewModel;
import common.formatter.DateFormatter;
import common.utility.AlertBoxSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

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

    private AdvertiseReviewModel model;

    @FXML
    private void onAdd() {
        model.addAdvertise();
        if (model.isStateChanged()) {
            changeButtonsState();
        }
    }

    @FXML
    private void onDelete() {
        if (AlertBoxSingleton.getInstance().popAlertBox("Confirmation", "Deleting...", "คุณต้องการจะลบแบบโฆษณานี้หรือไม่?")) {
            int removeIndex = adsTable.getSelectionModel().getSelectedIndex();
            model.deleteAdvertise(removeIndex);
            if (model.isStateChanged()) {
                changeButtonsState();
            }
        }
    }

    @FXML
    private void onEdit() {
        if (AlertBoxSingleton.getInstance().popAlertBox("Confirmation", "Editing...", "คุณต้องการจะแก้ไขแบบโฆษณานี้หรือไม่?")) {
            model.editAdvertise();
        }
    }

    @FXML
    private void onMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getClickCount() == 2) {
                if (adsTable.getSelectionModel().getSelectedItem() != null) {
                    mouseEvent.consume();
                    model.openJobReview(adsTable.getSelectionModel().getSelectedItem());
                } else {
                    System.out.println("no item was found");
                }
            }
        }
    }

    public void setupUI() {
        // set list into table as usual
        adsTable.setItems(model.getAdManager().getAdvertiseList());
        refNoColumn.setCellValueFactory(cell -> cell.getValue().refNoProperty());
        subjColumn.setCellValueFactory(cell -> cell.getValue().nameProperty());
        cDateColumn.setCellValueFactory(cell -> cell.getValue().createDateProperty());

        // set default time locale to all date columns
        DateFormatter dateFormatter = new DateFormatter();
        dateFormatter.formatDateColumn(cDateColumn);

        // setup item listener
        adsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> model.getAdManager().setCurrentAdapter(newSelection));

        model.getAdManager().currentAdapterProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null) {
                adsTable.getSelectionModel().clearSelection();
            } else {
                adsTable.getSelectionModel().select(newSelection);
            }
        });
        changeButtonsState();
    }

    private void changeButtonsState() {
        if (model.getAdManager().getAdvertiseList().isEmpty()) {
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

    public void setModel(AdvertiseReviewModel model) {
        this.model = model;
    }
}
