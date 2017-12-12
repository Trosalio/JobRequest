package client.ui.view;

import client.ui.model.AdvertiseEditorModel;
import common.formatter.DateFormatter;
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

    private AdvertiseEditorModel model;

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
            String subject = subjectTxtF.getText();
            String refNumber = refNoTxtF.getText();
            LocalDate createDate = cDatePicker.getValue();
            model.saveAdvertise(subject, refNumber, createDate);
            AlertBoxSingleton.getInstance().popAlertBox("Information", "Success", "โฆษณาถูกบันทึกแล้ว!");
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

    private void closeWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void setModel(AdvertiseEditorModel model) {
        this.model = model;
    }

    public void setupUI() {
        cDatePicker.setValue(model.getAdapter().getAdaptee().getCreateDate());
        subjectTxtF.setText(model.getAdapter().getAdaptee().getAdsName());
        refNoTxtF.setText(model.getAdapter().getAdaptee().getRefNumber());
    }
}

