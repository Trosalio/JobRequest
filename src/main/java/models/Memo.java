package models;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

/**
 * Project Name: MemoView
 */

public class Memo {


    private SimpleStringProperty memoName = new SimpleStringProperty(this, "memoName");
    private SimpleStringProperty refNumber = new SimpleStringProperty(this, "refNumber");
    private SimpleObjectProperty createMemoDate = new SimpleObjectProperty(this, "createMemoDate");
    private SimpleObjectProperty startMemoDate = new SimpleObjectProperty(this, "startMemoDate");
    private SimpleObjectProperty endMemoDate = new SimpleObjectProperty(this, "endMemoDate");

    public Memo(){
        setMemoName("");
        setRefNumber("");
        setCreateMemoDate(LocalDate.now());
        setStartMemoDate(LocalDate.now());
        setEndMemoDate(LocalDate.now().plusDays(1));
    }

    public String getMemoName() {
        return memoName.get();
    }

    public SimpleStringProperty memoNameProperty() {
        return memoName;
    }

    public void setMemoName(String memoName) {
        this.memoName.set(memoName);
    }

    public String getRefNumber() {
        return refNumber.get();
    }

    public SimpleStringProperty refNumberProperty() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber.set(refNumber);
    }

    public Object getCreateMemoDate() {
        return createMemoDate.get();
    }

    public SimpleObjectProperty createMemoDateProperty() {
        return createMemoDate;
    }

    public void setCreateMemoDate(Object createMemoDate) {
        this.createMemoDate.set(createMemoDate);
    }

    public Object getStartMemoDate() {
        return startMemoDate.get();
    }

    public SimpleObjectProperty startMemoDateProperty() {
        return startMemoDate;
    }

    public void setStartMemoDate(Object startMemoDate) {
        this.startMemoDate.set(startMemoDate);
    }

    public Object getEndMemoDate() {
        return endMemoDate.get();
    }

    public SimpleObjectProperty endMemoDateProperty() {
        return endMemoDate;
    }

    public void setEndMemoDate(Object endMemoDate) {
        this.endMemoDate.set(endMemoDate);
    }
}
