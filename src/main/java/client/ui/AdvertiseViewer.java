package client.ui;

import client.controllers.AdvertiseAdapter;
import common.formatter.DateFormatter;
import common.models.Advertise;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import common.utilities.AlertBoxSingleton;

/**
 * Project Name: MemoView
 */

public class AdvertiseViewer {

    @FXML
    private DatePicker cDatePicker, sDatePicker, eDatePicker;
    @FXML
    private TextField subjectTxtF,refNoTxtF;
    @FXML
    private Button cancelButton;

    private Boolean saveBool = false;
    private AdvertiseAdapter adapter;
    private Advertise advertise;

    @FXML
    private void initialize() {
        DateFormatter dateFormatter = new DateFormatter();
        dateFormatter.formatDatePicker(sDatePicker, eDatePicker, cDatePicker);
    }

    @FXML
    private void onCancel() {
        closeWindow();
    }

    @FXML
    private void onSave() {
        if (isValidDate()) {
            if (!(subjectTxtF.getText().isEmpty() || refNoTxtF.getText().isEmpty())) {
                advertise.setCreateDate(cDatePicker.getValue());
                advertise.setAdsName(subjectTxtF.getText());
                advertise.setRefNumber(refNoTxtF.getText());
                advertise.setStartDate(sDatePicker.getValue());
                advertise.setEndDate(eDatePicker.getValue());
                adapter.update();
                AlertBoxSingleton.getInstance().popAlertBox("Information", "Success", "โฆษณาถูกบันทึกแล้ว!");
                saveBool = true;
                closeWindow();
            } else {
                AlertBoxSingleton.getInstance().popAlertBox("Error", "Null Value", "หัวข้อเรื่อง หรือ Reference Number ยังไม่ถูกเติม");
                if (refNoTxtF.getText().isEmpty()) {
                    refNoTxtF.requestFocus();
                }
                if (subjectTxtF.getText().isEmpty()) {
                    subjectTxtF.requestFocus();
                }
            }
        } else {
            AlertBoxSingleton.getInstance().popAlertBox("Error", "Invalid Date Input","วันสิ้นสุดต้องไม่มาก่อนวันเริ่มต้น");
            eDatePicker.setValue(sDatePicker.getValue());
        }

    }

    public void setCurrentAdapter(AdvertiseAdapter currentAdapter) {
        this.adapter = currentAdapter;
        this.advertise = adapter.getAdvertise();
        cDatePicker.setValue(advertise.getCreateDate());
        subjectTxtF.setText(advertise.getAdsName());
        refNoTxtF.setText(advertise.getRefNumber());
        sDatePicker.setValue(advertise.getStartDate());
        eDatePicker.setValue(advertise.getEndDate());
    }

    private void closeWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private boolean isValidDate() {
        return !eDatePicker.getValue().isBefore(sDatePicker.getValue());
    }

    public boolean isSaved() {
        return saveBool;
    }
}

