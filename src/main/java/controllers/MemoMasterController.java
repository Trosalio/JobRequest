package controllers;

import formatter.DateFormatter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.FormManager;
import models.Memo;
import models.forms.GenericForm;
import models.forms.MockedForm;

import java.io.IOException;
import java.time.LocalDate;


/**
 * Project Name: MemoView
 */

public class MemoMasterController {

    @FXML
    private TableView<GenericForm> formTable;
    @FXML
    private Label subjectLabel, refNoLabel, recipientLabel;
    @FXML
    private DatePicker cDatePicker, sDatePicker, eDatePicker;
    @FXML
    private TableColumn<GenericForm, LocalDate> dateIssuedColumn;
    @FXML
    private TableColumn<GenericForm, String> subjectColumn, sentToColumn, typeOfFormColumn;

    private Memo memo;
    private FormManager formManager;


    @FXML
    private void onCreateForm() {
        formManager.addForm(new MockedForm(recipientLabel.getText()));
    }

    @FXML
    private void onDeleteForm() {
        formManager.deleteForm(formTable.getSelectionModel().getSelectedIndex());
    }

    @FXML
    private void onEditForm() {
        // implements edit form here
        GenericForm genericForm = formTable.getSelectionModel().getSelectedItem();
        genericForm.editForm();
    }

    @FXML
    private void onSend() {
        System.out.println("Successfully sent forms to " + recipientLabel.getText() + '!');
    }

    @FXML
    private void onChangeRecipient() {
        String receiverEmail = popEmailChangerWindow();
        if (receiverEmail != null) {
            recipientLabel.setText(receiverEmail);
        }
    }

    private String popEmailChangerWindow() {
        try {
            FXMLLoader emailChangerUILoader = new FXMLLoader(getClass().getResource("/fxml/EmailChangerUI.fxml"));
            Parent root = emailChangerUILoader.load();
            EmailChangerController emailChangerController = emailChangerUILoader.getController();
            emailChangerController.setReceiverTxtF(recipientLabel.getText());
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

    public void prepareComponents() {
        // set cell values and listener as usual
        setFormManager();
        formTable.setItems(formManager.getViewingForms());
        dateIssuedColumn.setCellValueFactory(cell -> cell.getValue().dateIssuedProperty());
        subjectColumn.setCellValueFactory(cell -> cell.getValue().subjectProperty());
        sentToColumn.setCellValueFactory(cell -> cell.getValue().recipientProperty());
        typeOfFormColumn.setCellValueFactory(cell -> cell.getValue().typeOfFormProperty());
        setUpItemListener();

        // format all date components
        DateFormatter dateFormatter = new DateFormatter();
        dateFormatter.formatDatePicker(cDatePicker, sDatePicker, eDatePicker);
        dateFormatter.formatDateColumn(dateIssuedColumn);

    }


    private void setUpItemListener() {
        formTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> formManager.setCurrentForm(newSelection));

        formManager.currentFormProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null) {
                formTable.getSelectionModel().clearSelection();
            } else {
                formTable.getSelectionModel().select(newSelection);
            }
        });
    }

    private void setFormManager() {
        this.formManager = new FormManager(memo.getForms());
    }
}

