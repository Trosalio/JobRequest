package controllers;

import utilities.AlertBoxSingleton;
import utilities.DatePickerFormatter;
import utilities.DateTimeFormatSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Memo;

import java.time.format.DateTimeFormatter;

/**
 * Project Name: MemoView
 */

public class MemoController {

    @FXML
    private DatePicker sDatePicker;
    @FXML
    private DatePicker eDatePicker;
    @FXML
    private DatePicker cDatePicker;
    @FXML
    private TextField subjectTxtF;
    @FXML
    private TextField refNoTxtF;
    @FXML
    private Button cancelButton;

    private Boolean saveBool = false;
    private Memo memo;

    @FXML
    private void initialize() {
        setsDatePickers();
    }

    @FXML
    private void onCancel() {
        closeWindow();
    }

    @FXML
    private void onSave() {
        if (isValidDate()) {
            if (!(subjectTxtF.getText().isEmpty() || refNoTxtF.getText().isEmpty())) {
                memo.setCreateMemoDate(cDatePicker.getValue());
                memo.setMemoName(subjectTxtF.getText());
                memo.setRefNumber(refNoTxtF.getText());
                memo.setStartMemoDate(sDatePicker.getValue());
                memo.setEndMemoDate(eDatePicker.getValue());
                AlertBoxSingleton.getInstance().popAlertBox("Success", "Memo is saved!");
                saveBool = true;
                closeWindow();
            } else {
                AlertBoxSingleton.getInstance().popAlertBox("Error", "Subject or Reference Number is not filled");
                if (refNoTxtF.getText().isEmpty()) {
                    refNoTxtF.requestFocus();
                }
                if (subjectTxtF.getText().isEmpty()) {
                    subjectTxtF.requestFocus();
                }
            }
        } else {
            AlertBoxSingleton.getInstance().popAlertBox("Error", "End Date must not end before Start Date");
            eDatePicker.setValue(sDatePicker.getValue());
        }

    }

    public void setCurrentMemo(Memo currentMemo) {
        this.memo = currentMemo;
        cDatePicker.setValue(memo.getCreateMemoDate());
        subjectTxtF.setText(memo.getMemoName());
        refNoTxtF.setText(memo.getRefNumber());
        sDatePicker.setValue(memo.getStartMemoDate());
        eDatePicker.setValue(memo.getEndMemoDate());
    }

    private boolean isValidDate() {
        return !eDatePicker.getValue().isBefore(sDatePicker.getValue());
    }

    private void setsDatePickers() {
        DateTimeFormatter dtf = DateTimeFormatSingleton.getInstance().getDateTimeFormat();
        DatePickerFormatter dpf = new DatePickerFormatter();
        dpf.format(cDatePicker, dtf);
        dpf.format(sDatePicker, dtf);
        dpf.format(eDatePicker, dtf);
    }


    private void closeWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public boolean isSaved() {
        return saveBool;
    }
}

