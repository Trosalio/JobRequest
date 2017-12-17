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
        boolean exist = viewModel.checkExistence();
        boolean allFill = !(nameField.getText().isEmpty() || refNoField.getText().isEmpty());
        if (allFill) {
            if (exist && !refNoField.isDisable()) {
                AlertBoxSingleton.getInstance().popAlertBox("Error",
                        "Reference Number existed.",
                        "This Reference Number has already been registered, please change.");
                refNoField.requestFocus();
            } else {
                viewModel.saveAdvertise();
                AlertBoxSingleton.getInstance().popAlertBox("Information",
                        "Success",
                        "Advertise is saved!");
                onCancel();
            }
        } else {
            if (nameField.getText().isEmpty()) {
                AlertBoxSingleton.getInstance().popAlertBox("Error",
                        "Subject is not filled.",
                        "Please fill the subject name.");
                nameField.requestFocus();
            } else if (refNoField.getText().isEmpty()) {
                AlertBoxSingleton.getInstance().popAlertBox("Error",
                        "Reference Number is not filled.",
                        "Please fill the Reference Number.");
                refNoField.requestFocus();
            }
        }

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
            refNoField.setDisable(!refNoField.getText().isEmpty());
        }
    }
}

