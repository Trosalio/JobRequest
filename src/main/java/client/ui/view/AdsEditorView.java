package client.ui.view;

import client.controller.AdvertiseAdapter;
import client.ui.model.AdsEditorModel;
import client.utility.AlertBoxSingleton;
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
    private void onCancel() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onSave() {
        if (!(isTxtFEmpty(nameField) || isTxtFEmpty(refNoField) || issueDatePicker.getValue() == null)) {
            if(!viewModel.getHandler().getAdvertiseManager().isReferenceNumberDuplicated(viewModel.getAdapter())) {
                viewModel.saveAdvertise();
                AlertBoxSingleton.getInstance().popAlertBox("Information", "Success", "Advertise is saved!");
                onCancel();
            } else {
                AlertBoxSingleton.getInstance().popAlertBox("Error", "Duplicate Reference Number", "This Reference Number has already been registered!");
                refNoField.requestFocus();
            }
        } else {
            if (isTxtFEmpty(nameField)) {
                AlertBoxSingleton.getInstance().popAlertBox("Error", "Subject is not filled", "Please fill the subject name");
                nameField.requestFocus();
            } else if (isTxtFEmpty(refNoField)) {
                AlertBoxSingleton.getInstance().popAlertBox("Error", "Reference Number is not filled", "Please fill the Reference Number");
                refNoField.requestFocus();
            } else {
                AlertBoxSingleton.getInstance().popAlertBox("Error", "Create Date is not picked", "Please select a create date");
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
        viewModel.getDateFormatter().formatDatePickers(issueDatePicker);
        viewModel.getDateFormatter().preventDatePickedBefore(issueDatePicker, LocalDate.now());
        bindModel();
    }

    private void bindModel() {
        AdvertiseAdapter model = viewModel.getAdapter();
        if (model != null) {
            nameField.textProperty().bindBidirectional(model.nameProperty());
            refNoField.textProperty().bindBidirectional(model.refNoProperty());
            issueDatePicker.valueProperty().bindBidirectional(model.createDateProperty());
            viewModel.getAdapter().reload();
            refNoField.setDisable(!isTxtFEmpty(refNoField));
        }
    }
}

