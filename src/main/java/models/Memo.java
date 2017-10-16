package models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.forms.GenericForm;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Project Name: MemoView
 */

public class Memo {


    private SimpleStringProperty memoName = new SimpleStringProperty(this, "memoName");
    private SimpleStringProperty refNumber = new SimpleStringProperty(this, "refNumber");
    private SimpleObjectProperty<LocalDate> createMemoDate = new SimpleObjectProperty<>(this, "createMemoDate");
    private SimpleObjectProperty<LocalDate> startMemoDate = new SimpleObjectProperty<>(this, "startMemoDate");
    private SimpleObjectProperty<LocalDate> endMemoDate = new SimpleObjectProperty<>(this, "endMemoDate");
    private ArrayList<GenericForm> forms = new ArrayList<>();
    private SimpleIntegerProperty numberOfForms = new SimpleIntegerProperty(this, "numberOfForms", forms.size());

    public Memo(){
        memoName.set("");
        refNumber.set("");
        createMemoDate.set(LocalDate.now());
        startMemoDate.set(LocalDate.now());
        endMemoDate.set(LocalDate.now().plusDays(1));
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

    public LocalDate getCreateMemoDate() {
        return createMemoDate.get();
    }

    public ObjectProperty<LocalDate> createMemoDateProperty() {
        return createMemoDate;
    }

    public void setCreateMemoDate(LocalDate createMemoDate) {
        this.createMemoDate.set(createMemoDate);
    }

    public LocalDate getStartMemoDate() {
        return startMemoDate.get();
    }

    public ObjectProperty<LocalDate> startMemoDateProperty() {
        return startMemoDate;
    }

    public void setStartMemoDate(LocalDate startMemoDate) {
        this.startMemoDate.set(startMemoDate);
    }

    public LocalDate getEndMemoDate() {
        return endMemoDate.get();
    }

    public ObjectProperty<LocalDate> endMemoDateProperty() {
        return endMemoDate;
    }

    public void setEndMemoDate(LocalDate endMemoDate) {
        this.endMemoDate.set(endMemoDate);
    }

    public int getNumberOfForms() {
        numberOfForms.set(forms.size());
        return numberOfForms.get();
    }

    public SimpleIntegerProperty numberOfFormsProperty() {
        return numberOfForms;
    }

    public void updateNumberOfForm(){
        numberOfForms.set(forms.size());
    }

    public ArrayList<GenericForm> getForms() {
        return forms;
    }
}
