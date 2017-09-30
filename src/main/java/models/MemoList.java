package models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Project Name: MemoView
 */

public class MemoList {

    private final ObservableList<Memo> events = FXCollections.observableArrayList();
    private final ObjectProperty<Memo> currentMemo = new SimpleObjectProperty<>(null);

}
