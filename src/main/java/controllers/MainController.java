package controllers;

import Utilities.DateTimeFormatSingleton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Memo;
import models.MemoManager;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Project Name: MemoView
 */

public class MainController {

    @FXML
    private Button deleteButton, editButton;
    @FXML
    private TableView<Memo> memoTable;
    @FXML
    private TableColumn<Memo, LocalDate> cDateColumn, sDateColumn, eDateColumn;
    @FXML
    private TableColumn<Memo, String> subjColumn, refNoColumn;

    private MemoManager memoManager;

    @FXML
    private void onAdd() {
        Memo memo = new Memo();
        if (popMemoWindow(memo)) {
            memoManager.addMemo(memo);
            changeButtonsState();
        }
    }

    @FXML
    private void onDelete() {
        int removeIndex = memoTable.getSelectionModel().getSelectedIndex();
        memoManager.deleteMemo(removeIndex);
        changeButtonsState();
    }

    @FXML
    private void onEdit() {
        Memo memo = memoManager.getCurrentMemo();
        if (memo != null) {
            if (popMemoWindow(memo)) {
                memoManager.editMemo(memo);
            }
        }
    }

    @FXML
    private void onMouseClicked(MouseEvent mouseEvent){
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            if(mouseEvent.getClickCount() == 2){
                if(memoTable.getSelectionModel().getSelectedItem() != null) {
                    popMemoMasterWindow(memoTable.getSelectionModel().getSelectedItem());
                } else {
                    System.out.println("no item was found");
                }
            }
        }
    }

    private boolean popMemoWindow(Memo memo) {
        try {
            FXMLLoader memoUILoader = new FXMLLoader(getClass().getResource("/fxml/MemoUI.fxml"));
            Parent root = memoUILoader.load();
            MemoController memoController = memoUILoader.getController();
            memoController.setCurrentMemo(memo);

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Memo");
            stage.setResizable(false);
            stage.showAndWait();

            return memoController.isSaved();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    private void popMemoMasterWindow(Memo memo) {
        try {
            FXMLLoader memoMasterUI = new FXMLLoader(getClass().getResource("/fxml/MemoMasterUI.fxml"));
            Parent root = memoMasterUI.load();
            MemoMasterController memoMasterController = memoMasterUI.getController();
            memoMasterController.setCurrentMemo(memo);

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Memo Master");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUpTableView() {
        memoTable.setItems(memoManager.getList());
        cDateColumn.setCellValueFactory(cell -> cell.getValue().createMemoDateProperty());
        subjColumn.setCellValueFactory(cell -> cell.getValue().memoNameProperty());
        refNoColumn.setCellValueFactory(cell -> cell.getValue().refNumberProperty());
        sDateColumn.setCellValueFactory(cell -> cell.getValue().startMemoDateProperty());
        eDateColumn.setCellValueFactory(cell -> cell.getValue().endMemoDateProperty());

        changeButtonsState();
        setDateColumnsFormat();
        setUpItemListener();
    }

    private void changeButtonsState() {
        if (memoManager.getList().isEmpty()) {
            deleteButton.setDisable(true);
            editButton.setDisable(true);
            editButton.setVisible(false);
            deleteButton.setVisible(false);
        } else {
            deleteButton.setDisable(false);
            editButton.setDisable(false);
            editButton.setVisible(true);
            deleteButton.setVisible(true);
        }
    }

    private void setDateColumnsFormat() {
        setDateColumnFormat(cDateColumn);
        setDateColumnFormat(sDateColumn);
        setDateColumnFormat(eDateColumn);
    }

    private void setDateColumnFormat(TableColumn<Memo, LocalDate> column) {
        column.setCellFactory(cell -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty)
                    setText(null);
                else
                    setText(DateTimeFormatSingleton.getInstance().getDateTimeFormat().format(item));
            }

        });
    }

    private void setUpItemListener() {
        memoTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> memoManager.setCurrentMemo(newSelection));

        memoManager.currentMemoProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null) {
                memoTable.getSelectionModel().clearSelection();
            } else {
                memoTable.getSelectionModel().select(newSelection);
            }
        });
    }

    public void setMemoManager(MemoManager memoManager) {
        this.memoManager = memoManager;
    }
}
