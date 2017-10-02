package controllers;

import Utilities.DatePickerFormatter;
import Utilities.DateTimeFormatSingleton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Memo;

import java.io.IOException;
import java.time.format.DateTimeFormatter;


/**
 * Project Name: MemoView
 */

public class MemoMasterController {

    @FXML
    private TableView<?> formTable;
    @FXML
    private Label subjectLabel, refNoLabel, receiverLabel;
    @FXML
    private DatePicker cDatePicker, sDatePicker, eDatePicker;
    private Memo memo;

    @FXML
    private void initialize() {
        setDatePickers();
    }

    @FXML
    private void onCreateForm() {

    }

    @FXML
    private void onDelete() {

    }

    @FXML
    private void onEdit() {

    }

    @FXML
    private void onSend() {

    }

    @FXML
    private void onChangeReceiver() {
        String receiverEmail = popEmailChangerWindow();
        if (receiverEmail != null) {
            receiverLabel.setText(receiverEmail);
        }
    }

    private void setDatePickers() {
        DateTimeFormatter dtf = DateTimeFormatSingleton.getInstance().getDateTimeFormat();
        DatePickerFormatter dpf = new DatePickerFormatter();
        dpf.format(cDatePicker, dtf);
        dpf.format(sDatePicker, dtf);
        dpf.format(eDatePicker, dtf);
    }


    private String popEmailChangerWindow() {
        try {
            FXMLLoader emailChangerUILoader = new FXMLLoader(getClass().getResource("/fxml/EmailChangerUI.fxml"));
            Parent root = emailChangerUILoader.load();
            EmailChangerController emailChangerController = emailChangerUILoader.getController();
            emailChangerController.setReceiverTxtF(receiverLabel.getText());
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Changing E-mail");
            stage.setResizable(false);
            stage.showAndWait();
            return emailChangerController.getReceiverEmail();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setCurrentMemo(Memo currentMemo) {
        this.memo = currentMemo;
        subjectLabel.setText(memo.getMemoName());
        refNoLabel.setText(memo.getRefNumber());
        cDatePicker.setValue(memo.getCreateMemoDate());
        sDatePicker.setValue(memo.getStartMemoDate());
        eDatePicker.setValue(memo.getEndMemoDate());
    }
}

