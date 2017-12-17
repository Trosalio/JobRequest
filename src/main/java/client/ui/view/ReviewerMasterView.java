package client.ui.view;

import client.controller.JobAdapter;
import client.ui.model.ReviewerMasterModel;
import client.utility.AlertBoxSingleton;
import common.formatter.DateFormatter;
import common.model.Station;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;


public class ReviewerMasterView {

    @FXML
    private BorderPane window;
    @FXML
    private TableView<JobAdapter> jobTable;
    @FXML
    private TableColumn<JobAdapter, LocalDate> fromDateCol;
    @FXML
    private TableColumn<JobAdapter, String> statusCol;
    @FXML
    private TableColumn<JobAdapter, String> detailNameCol;
    @FXML
    private VBox detailPane;
    @FXML
    private Label detailNameLabel, requesterLabel, typeOfMediaLabel,
            stationsLabel, quantityLabel, fromDateLabel, statusLabel;
    @FXML
    public Button acceptButton, rejectButton;

    private StackPane placeHolder;
    private ReviewerMasterModel viewModel;

    public void initialize() {
        Tooltip nameTooltip = new Tooltip();
        Tooltip stationTooltip = new Tooltip();
        nameTooltip.setWrapText(true);
        stationTooltip.setWrapText(true);
        detailNameLabel.setTooltip(nameTooltip);
        stationsLabel.setTooltip(stationTooltip);
        nameTooltip.setPrefWidth(300);
        stationTooltip.setPrefWidth(300);

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
        if (AlertBoxSingleton.getInstance().popAlertBox(
                "Confirmation",
                "Accepting...",
                "Are you sure you want to accept this job?")) {
            viewModel.reviewAndSend("ACCEPT");
            showDetail();
        }
    }

    @FXML
    public void onReject() {
        if (AlertBoxSingleton.getInstance().popAlertBox(
                "Confirmation",
                "Rejecting...",
                "Are you sure you want to reject this job?")) {
            viewModel.reviewAndSend("REJECT");
            showDetail();
        }
    }


    public void setModel(ReviewerMasterModel viewModel) {
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
            String detailName = current.jobDetailProperty().get();
            detailNameLabel.setText(detailName);
            detailNameLabel.getTooltip().setText(detailName);
            requesterLabel.setText(current.requesterProperty().get());
            typeOfMediaLabel.setText(current.typeOfMediaProperty().get());

            // set station text
            String station = current.stationsProperty().get()
                    .stream()
                    .map(Station::getCode)
                    .collect(Collectors.joining(", "));
            stationsLabel.setText(station);
            stationsLabel.getTooltip().setText(station);


            // set quantity text
            int quantity = current.quantityProperty().get();
            int total = current.stationsProperty().size() * quantity;
            quantityLabel.setText(String.format("%d (TOTAL: %d spots)", quantity, total));

            // set date text
            String fromDate = current.fromDateProperty().get().format(viewModel.getDateFormatter().getFormatter());
            String toDate = current.toDateProperty().get().format(viewModel.getDateFormatter().getFormatter());
            long period = ChronoUnit.DAYS.between(current.fromDateProperty().get(), current.toDateProperty().get().plusDays(1));
            fromDateLabel.setText(String.format("%s - %s (%d days)", fromDate, toDate, period));

            // set status text
            String status = current.statusProperty().get();
            statusLabel.setText(status);
            acceptButton.setDisable(status.equals("ACCEPT") || status.equals("REJECT"));
            rejectButton.setDisable(status.equals("ACCEPT") || status.equals("REJECT"));
        }
    }

}
