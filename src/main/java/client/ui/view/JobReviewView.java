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
    private DatePicker cDatePicker;
    @FXML
    private Label subjectLabel, refNoLabel;
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
        if (AlertBoxSingleton.getInstance().popAlertBox("Confirmation", "Discarding...", "คุณต้องการจะยกเลิกแบบฟอร์มร้องขอนี้หรือไม่?")) {
            model.discardForm();
            updateJobInfo();
        }
    }

    @FXML
    private void onEditForm() {
        if (AlertBoxSingleton.getInstance().popAlertBox("Confirmation", "Editing...", "คุณต้องการจะแก้ไจแบบฟอร์มร้องขอนี้หรือไม่?")) {
            model.editForm();
            updateJobInfo();
        }
    }

    @FXML
    private void onSend() {
        if (AlertBoxSingleton.getInstance().popAlertBox("Confirmation", "Sending...", "คุณต้องการจะส่งแบบฟอร์มร้องขอนี้หรืิอไม่?")) {
            model.send();
            updateJobInfo();
        }
    }

    public void prepareComponents() {
        // format all date components
        DateFormatter dateFormatter = new DateFormatter();
        dateFormatter.formatDatePicker(cDatePicker, fromDatePicker, toDatePicker);

        // setup components
        updateAdvertiseInfo();
        updateJobInfo();
    }

    private void updateAdvertiseInfo() {
        subjectLabel.setText(model.getAdvertise().getAdsName());
        refNoLabel.setText(model.getAdvertise().getRefNumber());
        cDatePicker.setValue(model.getAdvertise().getCreateDate());
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
        detailNameLbl.setText("<หัวข้อเรื่อง>");
        requesterLbl.setText("<Reference Number>");
        typeOfMediaLbl.setText("<ประเภทของสื่อ>");
        stationListLbl.setText("<รายการของสถานที่>");
        qtyLbl.setText("-");
        totalQtyLbl.setText("-");
        fromDatePicker.setValue(null);
        toDatePicker.setValue(null);
        totalDayLbl.setText("-");
        statusLbl.setText("ยังไม่ถูกสร้าง");
        publishBox.getChildren().add(publishBtn);
        publishBox.getChildren().remove(editBtn);
        discardBtn.setDisable(true);
        sendBtn.setDisable(true);
    }

    public void setModel(JobReviewModel model) {
        this.model = model;
    }
}

