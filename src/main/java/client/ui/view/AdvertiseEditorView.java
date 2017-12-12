package client.ui.view;

import client.controller.AdvertiseAdapter;
import common.formatter.DateFormatter;
import common.model.Advertise;
import common.utility.AlertBoxSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AdvertiseEditorView {

    @FXML
    private DatePicker cDatePicker;
    @FXML
    private TextField subjectTxtF, refNoTxtF;
    @FXML
    private Button cancelButton;

    private Boolean saveBool = false;
    private AdvertiseAdapter adapter;
    private Advertise advertise;

    @FXML
    private void initialize() {
        DateFormatter dateFormatter = new DateFormatter();
        dateFormatter.formatDatePicker(cDatePicker);
    }

    @FXML
    private void onCancel() {
        closeWindow();
    }

    @FXML
    private void onSave() {
        if (!(isTxtFEmpty(subjectTxtF) || isTxtFEmpty(refNoTxtF) || cDatePicker.getValue() == null)) {
            advertise.setCreateDate(cDatePicker.getValue());
            advertise.setAdsName(subjectTxtF.getText());
            advertise.setRefNumber(refNoTxtF.getText());
            adapter.updateAdapter();
            AlertBoxSingleton.getInstance().popAlertBox("Information", "Success", "โฆษณาถูกบันทึกแล้ว!");
            saveBool = true;
            closeWindow();
        } else {
            if(isTxtFEmpty(subjectTxtF) ){
                AlertBoxSingleton.getInstance().popAlertBox("Error", "Subject is not filled", "กรุณาใส่หัวข้อเรื่อง");
                subjectTxtF.requestFocus();
            } else if(isTxtFEmpty(refNoTxtF)){
                AlertBoxSingleton.getInstance().popAlertBox("Error", "Reference Number is not filled", "กรุณาใส่ Reference Number");
                refNoTxtF.requestFocus();
            } else {
                AlertBoxSingleton.getInstance().popAlertBox("Error", "Create Date is not picked", "กรุณาเลือกวันที่ถูกสร้าง");
                cDatePicker.setValue(LocalDate.now());
                cDatePicker.requestFocus();
            }
        }
    }

    private boolean isTxtFEmpty(TextField txtF){
        return txtF.getText() == null || txtF.getText().isEmpty();
    }

    public void setCurrentAdapter(AdvertiseAdapter currentAdapter) {
        this.adapter = currentAdapter;
        this.advertise = adapter.getAdaptee();
        cDatePicker.setValue(advertise.getCreateDate());
        subjectTxtF.setText(advertise.getAdsName());
        refNoTxtF.setText(advertise.getRefNumber());
    }

    private void closeWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public boolean isSaved() {
        return saveBool;
    }
}

