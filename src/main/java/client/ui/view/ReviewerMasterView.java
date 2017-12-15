package client.ui.view;

import client.controller.JobAdapter;
import client.ui.model.JobMasterModel;
import common.formatter.DateFormatter;
import common.model.Station;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.time.LocalDate;
import java.util.stream.Collectors;


public class JobMasterView {

    @FXML
    public BorderPane window;
    @FXML
    public TableView<JobAdapter> jobTable;
    @FXML
    public TableColumn<JobAdapter, LocalDate> fromDateCol;
    @FXML
    public TableColumn<JobAdapter, String> statusCol;
    @FXML
    public TableColumn<JobAdapter, String> detailNameCol;
    @FXML
    public VBox detailPane;
    @FXML
    public Label detailNameLabel, requesterLabel, typeOfMediaLabel,
            stationsLabel, quantityLabel, fromDateLabel, toDateLabel, statusLabel;
    @FXML
    public Button acceptButton, rejectButton;

    private StackPane placeHolder;
    private JobMasterModel viewModel;

    public void initialize() {
        Label label = new Label("No Job is selected.");
        label.setFont(Font.font(18));
        placeHolder = new StackPane();
        placeHolder.setPrefSize(detailPane.getPrefWidth(), detailPane.getPrefHeight());
        placeHolder.getChildren().add(label);
        showPlaceHolder();
    }

    @FXML
    public void onRefresh() {
        viewModel.refreshList();
        System.out.println("refreshing...");
    }

    @FXML
    public void onAccept() {
        viewModel.acceptJob();
        System.out.println("accepting...");
    }

    @FXML
    public void onReject() {
        viewModel.rejectJob();
        System.out.println("rejecting...");
    }


    public void setModel(JobMasterModel viewModel) {
        this.viewModel = viewModel;
        setupTable();
    }

    @SuppressWarnings("Duplicates")
    private void setupTable() {
        jobTable.setItems(viewModel.getJobList());
        fromDateCol.setCellValueFactory(cell -> cell.getValue().fromDateProperty());
        statusCol.setCellValueFactory(cell -> cell.getValue().statusProperty());
        detailNameCol.setCellValueFactory(cell -> cell.getValue().jobDetailProperty());

        // set default time locale to all date columns
        DateFormatter dateFormatter = new DateFormatter();
        dateFormatter.formatDateColumn(fromDateCol);

        // setup item listener
        jobTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelect, newSelect) -> {
                    if (window.getRight().equals(placeHolder))
                        window.setRight(detailPane);
                    viewModel.setCurrentAdapter(newSelect);
                    showDetail();
                });

        viewModel.currentAdapterProperty().addListener((obs, oldAdapter, newAdapter) -> {
            if (newAdapter == null) {
                jobTable.getSelectionModel().clearSelection();
                showPlaceHolder();
            } else {
                jobTable.getSelectionModel().select(newAdapter);
            }
        });
    }

    private void showPlaceHolder() {
        window.setRight(placeHolder);
    }

    private void showDetail() {
        acceptButton.setDisable(false);
        rejectButton.setDisable(false);
        JobAdapter current = viewModel.getCurrentAdapter();
        if (current != null) {
            detailNameLabel.setText(current.jobDetailProperty().get());
            requesterLabel.setText(current.requesterProperty().get());
            typeOfMediaLabel.setText(current.typeOfMediaProperty().get());
            stationsLabel.setText(current.stationsProperty().get()
                    .stream()
                    .map(Station::getCode)
                    .collect(Collectors.joining(",")));
            quantityLabel.setText(String.valueOf(current.quantityProperty().get()));
            fromDateLabel.setText(current.fromDateProperty().get().format(viewModel.getDateFormatter().getFormatter()));
            toDateLabel.setText(current.toDateProperty().get().format(viewModel.getDateFormatter().getFormatter()));
            String status = current.statusProperty().get();
            stationsLabel.setText(status);
            if (status.equals("ACCEPT") || status.equals("REJECT")) {
                acceptButton.setDisable(true);
                rejectButton.setDisable(true);
            }
        }
    }

}
