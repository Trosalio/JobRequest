package models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Project Name: MemoView
 */

public class MemoList {

    private final ObservableList<Memo> list = FXCollections.observableArrayList();
    private final ObjectProperty<Memo> currentMemo = new SimpleObjectProperty<>(null);

    public void addMemo(Memo memo){
        list.add(memo);
    }

    public void deleteMemo(int removeIndex){
        list.remove(removeIndex);
    }

    public void editMemo(Memo memo){
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
}
