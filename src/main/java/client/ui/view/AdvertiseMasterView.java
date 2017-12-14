package client.ui.view;

import client.controller.AdvertiseAdapter;
import client.ui.model.AdvertiseMasterModel;
import common.formatter.DateFormatter;
import common.utility.AlertBoxSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.time.LocalDate;

public class AdvertiseMasterView {

    @FXML
    private BorderPane window;
    @FXML
    private VBox detailPane;
    @FXML
    private Label refNoLabel, nameLabel, issueDateLabel, jobIdLabel, jobStatusLabel;
    @FXML
    private Hyperlink deleteLink;
    @FXML
    private Button editButton, addButton;
    @FXML
    private TableView<AdvertiseAdapter> adsTable;
    @FXML
    private TableColumn<AdvertiseAdapter, LocalDate> issueDateCol;
    @FXML
    private TableColumn<AdvertiseAdapter, String> refNoCol, nameCol;

    private StackPane placeHolder;

    private AdvertiseMasterModel viewModel;

    public void initialize() {
        placeHolder = new StackPane();
        Label label = new Label("No advertise selected, Try add one");
        label.setFont(Font.font(20));
        placeHolder.getChildren().add(label);
        showPlaceHolder();
    }

    @FXML
    private void onAdd() {
        viewModel.addAdvertise();
    }

    @FXML
    private void onDelete() {
        if (AlertBoxSingleton.getInstance().popAlertBox(
                "Confirmation",
                "Deleting...",
                "Are you sure you want to delete this?")) {
            int removeIndex = adsTable.getSelectionModel().getSelectedIndex();
            viewModel.deleteAdvertise(removeIndex);
            deleteLink.setVisited(false);
        }
    }

    @FXML
    private void onEdit() {
        viewModel.editAdvertise();
    }

    @FXML
    private void onMouseClicked(MouseEvent mouseEvent) {
//        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
//            if (mouseEvent.getClickCount() == 2) {
//                if (adsTable.getSelectionModel().getSelectedItem() != null) {
//                    mouseEvent.consume();
//                    viewModel.openJobReview(adsTable.getSelectionModel().getSelectedItem());
//                } else {
//                    System.out.println("no item was found");
//                }
//            }
//        }
    }

    public void setViewModel(AdvertiseMasterModel model) {
        this.viewModel = model;
        setupTable();
    }

    private void setupTable() {
        // set list into table as usual
        adsTable.setItems(viewModel.getAdvertiseList());
        issueDateCol.setCellValueFactory(cell -> cell.getValue().createDateProperty());
        refNoCol.setCellValueFactory(cell -> cell.getValue().refNoProperty());
        nameCol.setCellValueFactory(cell -> cell.getValue().nameProperty());

        // set default time locale to all date columns
        DateFormatter dateFormatter = new DateFormatter();
        dateFormatter.formatDateColumn(issueDateCol);

        // setup item listener
        adsTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelect, newSelect) -> {
                    viewModel.setCurrentAdapter(newSelect);
                    showDetail();
                });

        viewModel.currentAdapterProperty().addListener((obs, oldAdapter, newAdapter) -> {
            if (newAdapter == null) {
                adsTable.getSelectionModel().clearSelection();
                showPlaceHolder();
            } else {
                adsTable.getSelectionModel().select(newAdapter);
            }
        });
    }

    private void showPlaceHolder() {
        window.setRight(placeHolder);
    }

    private void showDetail() {
        if (viewModel.getCurrentAdapter() != null) {
            window.setRight(detailPane);
            refNoLabel.setText(viewModel.getCurrentAdapter().refNoProperty().get());
            nameLabel.setText(viewModel.getCurrentAdapter().nameProperty().get());
            issueDateLabel.setText(viewModel.getCurrentAdapter().nameProperty().get());
            if (viewModel.getCurrentAdapter().jobProperty().get() != null) {
                jobIdLabel.setText(String.valueOf(viewModel.getCurrentAdapter().jobProperty().get().getId()));
                jobStatusLabel.setText(String.valueOf(viewModel.getCurrentAdapter().jobProperty().get().getStatus()));
            }
        }
    }
}
