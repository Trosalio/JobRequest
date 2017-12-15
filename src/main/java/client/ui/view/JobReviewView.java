package client.ui.view;

import client.ui.model.JobReviewModel;
import common.formatter.DateFormatter;
import common.utility.AlertBoxSingleton;
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
        updateJobInfo();
    }

    @FXML
    private void onDiscardForm() {
        if (AlertBoxSingleton.getInstance().popAlertBox(
                "Confirmation",
                "Discarding...",
                "Are you sure, you want to 'Discard' this from?")) {
            model.discardForm();
            updateJobInfo();
        }
    }

    @FXML
    private void onEditForm() {
        if (AlertBoxSingleton.getInstance().popAlertBox(
                "Confirmation",
                "Editing...",
                "Are you sure, you want to 'Edit' this from?")) {
            model.editForm();
            updateJobInfo();
        }
    }

    @FXML
    private void onSend() {
        if (AlertBoxSingleton.getInstance().popAlertBox(
                "Confirmation",
                "Sending...",
                "Are you sure, you want to 'Send' this from?")) {
            model.send();
            updateJobInfo();
        }
    }

    public void setupUI() {
        // format all date components
        DateFormatter dateFormatter = new DateFormatter();
        dateFormatter.formatDatePicker(fromDatePicker, toDatePicker);
        updateJobInfo();
    }

    private void updateJobInfo() {
        if (model.isStateChanged()) {
            if (model.getJob().isDefault()) {
                setDefaultJobInfo();
            } else {
                detailNameLbl.setText(model.getJob().getJobDetail());
                requesterLbl.setText(model.getJob().getRequester());
                typeOfMediaLbl.setText(model.getJob().getTypeOfMedia());
                stationListLbl.setText(model.getJob().getListOfStations());
                qtyLbl.setText(Integer.toString(model.getJob().getQuantity()));
                totalQtyLbl.setText(Integer.toString(model.getJob().getStations().size() * model.getJob().getQuantity()));
                fromDatePicker.setValue(model.getJob().getFromDate());
                toDatePicker.setValue(model.getJob().getToDate());
                totalDayLbl.setText(Long.toString(ChronoUnit.DAYS.between(model.getJob().getFromDate(), model.getJob().getToDate())));
                statusLbl.setText(model.getJob().getStatus());
                discardBtn.setDisable(false);
                sendBtn.setDisable(false);
                publishBox.getChildren().add(editBtn);
                publishBox.getChildren().remove(publishBtn);
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
        publishBox.getChildren().add(publishBtn);
        publishBox.getChildren().remove(editBtn);
        discardBtn.setDisable(true);
        sendBtn.setDisable(true);
    }

    public void setModel(JobReviewModel model) {
        this.model = model;
    }
}

