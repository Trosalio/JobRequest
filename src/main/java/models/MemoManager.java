package models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.datastorage.DBManager;

/**
 * Project Name: MemoView
 */

public class MemoManager {

    private final ObservableList<Memo> list = FXCollections.observableArrayList();
    private final ObjectProperty<Memo> currentMemo = new SimpleObjectProperty<>(null);
    private DBManager database;

    public void addMemo(Memo memo){
        list.add(memo);
        if(database != null) database.insertRecord();
    }

    public void deleteMemo(int removeIndex){
        list.remove(removeIndex);
        if(database != null) database.deleteRecord();
    }

    public void editMemo(Memo memo){
        if(database != null) database.modifyRecord();
    }

    public ObservableList<Memo> getList() {
        return list;
    }

    public Memo getCurrentMemo() {
        return currentMemo.get();
    }

    public ObjectProperty<Memo> currentMemoProperty() {
        return currentMemo;
    }

    public void setCurrentMemo(Memo currentMemo) {
        this.currentMemo.set(currentMemo);
    }

    public void setDatabase(DBManager database){
        this.database = database;
    }
}
