package controllers;

import Utilities.DateTimeFormatSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Memo;
import models.MemoList;

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

    private MemoList memoList;

    @FXML
    public void initialize() {
        memoList = new MemoList();
        setUpTableView();
    }

    @FXML
    void onAdd() {
        Memo memo = new Memo();
        if (popMemoWindow(memo)) {
            memoList.addMemo(memo);
            changeButtonsState();
        }
    }


    @FXML
    void onDelete() {
        int removeIndex = memoTable.getSelectionModel().getSelectedIndex();
        memoList.deleteMemo(removeIndex);
        changeButtonsState();
    }

    @FXML
    void onEdit() {
        Memo memo = memoList.getCurrentMemo();
        if (memo != null) {
            if (popMemoWindow(memo)) {
                memoList.editMemo(memo);
//                modifyEventInfo(memo);
            }
        }
    }

    private void setUpTableView() {
        memoTable.setItems(memoList.getList());
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
        if (memoList.getList().isEmpty()) {
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
        column.setCellFactory(cell -> new TableCell<Memo, LocalDate>() {
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
        memoTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            memoList.setCurrentMemo(newSelection);
//            modifyEventInfo(newSelection);
        });

        memoList.currentMemoProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null) {
                memoTable.getSelectionModel().clearSelection();
            } else {
                memoTable.getSelectionModel().select(newSelection);
            }
        });
    }

    private boolean popMemoWindow(Memo memo) {
        return true;
    }

//    void modifyEventInfo(DateEvent event) {
//        if (event != null) {
//            DateEvent currentEvent = eventTable.getSelectionModel().getSelectedItem();
//            eventNameLbl.setText(currentEvent.getEventName());
//            eventPriorityLbl.setText(convertPriorityToText(currentEvent.getEventPriority()));
//            eventDateLbl.setText(dateTimeFormatter.format(currentEvent.getEventStartDate()));
//            eventDescTxtA.setText(currentEvent.getEventDescription());
//            recurrenceLbl.setText(convertRecurrenceBooleanToText(currentEvent));
//        }
//    }
}
