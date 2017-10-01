package controllers;

import Utilities.AlertBoxSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Project Name: MemoView
 */

public class EmailChangerController {

    @FXML
    private TextField receiverTxtF;
    @FXML
    private Button cancelButton;
    private String receiverEmail;

    @FXML
    private void onChange() {
        if(!receiverTxtF.getText().isEmpty()){
            receiverEmail = receiverTxtF.getText();
        }
        AlertBoxSingleton.getInstance().popAlertBox("Success", "Receiver Email is changed!");
        closeWindow();
    }

    @FXML
    private void onCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void setReceiverTxtF(String receiverEmail){
        receiverTxtF.setPromptText(receiverEmail);
        this.receiverEmail = receiverEmail;
    }

    public String getReceiverEmail(){
        return receiverEmail;
    }
}

