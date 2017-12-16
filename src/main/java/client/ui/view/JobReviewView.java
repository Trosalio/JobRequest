package client.ui.view;

import client.ui.model.JobReviewModel;
import client.utility.AlertBoxSingleton;
import common.formatter.DateFormatter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.time.temporal.ChronoUnit;

public class JobReviewView {

    @FXML
    private Label detailNameLbl, requesterLbl, typeOfMediaLbl;
    @FXML
    private Label stationListLbl, qtyLbl, totalQtyLbl;
    @FXML
    private DatePicker fromDatePicker, toDatePicker;
    @FXML
    private Label totalDayLbl;
    @FXML
    private Label statusLbl;
    @FXML
    private Button publishBtn, editBtn, discardBtn, sendBtn;
    @FXML
    private HBox publishBox;

    private JobReviewModel model;

    @FXML
    private void onPublishForm() {
        model.publishForm();
        if (model.isStateChanged()) {
            updateJobInfo();
        }
    }

    @FXML
    private void onDiscardForm() {
        if (AlertBoxSingleton.getInstance().popAlertBox(
                "Confirmation",
                "Discarding...",
                "'Discard' this form?")) {
            model.discardForm();
            if (model.isStateChanged()) {
                updateJobInfo();
            }
        }
    }

    @FXML
    private void onEditForm() {
        if (AlertBoxSingleton.getInstance().popAlertBox(
                "Confirmation",
                "Editing...",
                "'Edit' this form?")) {
            model.editForm();
            if (model.isStateChanged()) {
                updateJobInfo();
            }
        }
    }

    @FXML
    private void onSend() {
        if (AlertBoxSingleton.getInstance().popAlertBox(
                "Confirmation",
                "Sending...",
                "'Send' this form?")) {
            model.send();
            if (model.isStateChanged()) {
                updateJobInfo();
            }
        }
    }

    public void setupUI() {
        // format all date components
        DateFormatter dateFormatter = new DateFormatter();
        dateFormatter.formatDatePicker(fromDatePicker, toDatePicker);
        publishBox.getChildren().remove(editBtn);
        publishBox.getChildren().remove(publishBtn);
        updateJobInfo();
    }

    private void updateJobInfo() {
        if (model.getJob() == null) {
            setDefaultJobInfo();
            model.setNewJob();
        } else {
            detailNameLbl.setText(model.getJob().getJobDetail());
            requesterLbl.setText(model.getJob().getRequester());
            typeOfMediaLbl.setText(model.getJob().getTypeOfMedia());
            stationListLbl.setText(model.getJob().getListOfStations());
            qtyLbl.setText(Integer.toString(model.getJob().getQuantity()));
            totalQtyLbl.setText(Integer.toString(model.getJob().getStations().size() * model.getJob().getQuantity()));
            fromDatePicker.setValue(model.getJob().getFromDate());
            toDatePicker.setValue(model.getJob().getToDate());
            totalDayLbl.setText(Long.toString(1 + ChronoUnit.DAYS.between(model.getJob().getFromDate(), model.getJob().getToDate())));
            statusLbl.setText(model.getJob().getStatus());
            discardBtn.setDisable(false);
            sendBtn.setDisable(false);
            if (!publishBox.getChildren().contains(editBtn)) {
                publishBox.getChildren().add(editBtn);
            }
            if (publishBox.getChildren().contains(publishBtn)) {
                publishBox.getChildren().remove(publishBtn);
            }
            if(!model.getJob().getStatus().equals("READY")){
                sendBtn.setDisable(true);
                editBtn.setDisable(true);
                discardBtn.setDisable(true);
            }
        }
    }

    private void setDefaultJobInfo() {
        detailNameLbl.setText("<Detail Name>");
        requesterLbl.setText("<Reference Number>");
        typeOfMediaLbl.setText("<Type of Media>");
        stationListLbl.setText("<Station List>");
        qtyLbl.setText("-");
        totalQtyLbl.setText("-");
        fromDatePicker.setValue(null);
        toDatePicker.setValue(null);
        totalDayLbl.setText("-");
        statusLbl.setText("NOT YET REQUEST");
        discardBtn.setDisable(true);
        sendBtn.setDisable(true);
        if (!publishBox.getChildren().contains(publishBtn)) {
            publishBox.getChildren().add(publishBtn);
        }
        if (publishBox.getChildren().contains(editBtn)) {
            publishBox.getChildren().remove(editBtn);
        }

    }

    public void setModel(JobReviewModel model) {
        this.model = model;
    }
}

