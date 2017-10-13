package models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.persistents.DBManager;

/**
 * Project Name: MemoView
 */

public class MemoManager {

    private final ObservableList<Memo> memoList = FXCollections.observableArrayList();
    private final ObjectProperty<Memo> currentMemo = new SimpleObjectProperty<>(null);
    private DBManager database;

    public void addMemo(Memo memo){
        memoList.add(memo);
        if(database != null) database.insertRecord();
    }

    public void deleteMemo(int removeIndex){
        memoList.remove(removeIndex);
        if(database != null) database.deleteRecord();
    }

    public void editMemo(Memo memo){
        if(database != null) database.modifyRecord();
    }

    public ObservableList<Memo> getMemoList() {
        return memoList;
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
