package client.ui.view;

import client.controller.AdvertiseAdapter;
import client.ui.model.AdsEditorModel;
import common.utility.AlertBoxSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AdsEditorView {

    @FXML
    private DatePicker issueDatePicker;
    @FXML
    private TextField nameField, refNoField;

    private AdsEditorModel viewModel;

    @FXML
    private void initialize() {

    }

    @FXML
    private void onCancel() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onSave() {
        if (!(isTxtFEmpty(nameField) || isTxtFEmpty(refNoField) || issueDatePicker.getValue() == null)) {
            viewModel.saveAdvertise();
            AlertBoxSingleton.getInstance().popAlertBox("Information", "Success", "โฆษณาถูกบันทึกแล้ว!");
            onCancel();
        } else {
            if (isTxtFEmpty(nameField)) {
                AlertBoxSingleton.getInstance().popAlertBox("Error", "Subject is not filled", "กรุณาใส่หัวข้อเรื่อง");
                nameField.requestFocus();
            } else if (isTxtFEmpty(refNoField)) {
                AlertBoxSingleton.getInstance().popAlertBox("Error", "Reference Number is not filled", "กรุณาใส่ Reference Number");
                refNoField.requestFocus();
            } else {
                AlertBoxSingleton.getInstance().popAlertBox("Error", "Create Date is not picked", "กรุณาเลือกวันที่ถูกสร้าง");
                issueDatePicker.setValue(LocalDate.now());
                issueDatePicker.requestFocus();
            }
        }
    }

    private boolean isTxtFEmpty(TextField txtF){
        return txtF.getText() == null || txtF.getText().isEmpty();
    }

    public void setViewModel(AdsEditorModel viewModel) {
        this.viewModel = viewModel;
        viewModel.getDateFormatter().formatDatePicker(issueDatePicker);
        bindModel();
    }

    private void bindModel() {
        AdvertiseAdapter model = viewModel.getAdapter();
        if (model != null) {
            nameField.textProperty().bindBidirectional(model.nameProperty());
            refNoField.textProperty().bindBidirectional(model.refNoProperty());
            issueDatePicker.valueProperty().bindBidirectional(model.createDateProperty());
            viewModel.getAdapter().reload();
        }
    }
}

