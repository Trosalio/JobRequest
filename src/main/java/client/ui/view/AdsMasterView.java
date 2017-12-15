package client.ui.view;

import client.controller.AdvertiseAdapter;
import client.ui.model.AdsMasterModel;
import common.formatter.DateFormatter;
import common.utility.AlertBoxSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.time.LocalDate;

public class AdsMasterView {

    @FXML
    private BorderPane window;
    @FXML
    private VBox detailPane;
    @FXML
    private Label refNoLabel, nameLabel, issueDateLabel, jobIdLabel, jobStatusLabel;
    @FXML
    private Hyperlink deleteLink;
    @FXML
    private TableView<AdvertiseAdapter> adsTable;
    @FXML
    private TableColumn<AdvertiseAdapter, LocalDate> issueDateCol;
    @FXML
    private TableColumn<AdvertiseAdapter, String> refNoCol, nameCol;

    private StackPane placeHolder;

    private AdsMasterModel viewModel;

    public void initialize() {
        Label label = new Label("No advertise is selected.");
        label.setFont(Font.font(18));
        placeHolder = new StackPane();
        placeHolder.setPrefSize(detailPane.getPrefWidth(), detailPane.getPrefHeight());
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
            viewModel.deleteAdvertise();
            deleteLink.setVisited(false);
        }
    }

    @FXML
    private void onEdit() {
        viewModel.editAdvertise();
        showDetail();
    }

    @FXML
    private void onMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getClickCount() == 2) {
                if (adsTable.getSelectionModel().getSelectedItem() != null) {
                    mouseEvent.consume();
                    viewModel.openJobReview(adsTable.getSelectionModel().getSelectedItem());
                } else {
                    System.out.println("no item was found");
                }
            }
        }
    }

    public void setViewModel(AdsMasterModel model) {
        this.viewModel = model;
        setupTable();
    }

    @SuppressWarnings("Duplicates")
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
                    if (window.getRight().equals(placeHolder))
                        window.setRight(detailPane);
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
        AdvertiseAdapter current = viewModel.getCurrentAdapter();
        if (current != null) {
            refNoLabel.setText(current.refNoProperty().get());
            nameLabel.setText(viewModel.getCurrentAdapter().nameProperty().get());
            issueDateLabel.setText(current.createDateProperty().get().format(viewModel.getDateFormatter().getFormatter()));
            if (viewModel.getCurrentAdapter().jobProperty().get() != null) {
                jobIdLabel.setText(String.valueOf(viewModel.getCurrentAdapter().jobProperty().get().getId()));
                jobStatusLabel.setText(String.valueOf(viewModel.getCurrentAdapter().jobProperty().get().getStatus()));
            }
        }
    }
}