package models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.forms.GenericForm;

import java.util.ArrayList;

/**
 * Project Name: MemoView
 */
public class FormManager {

    private final ObservableList<GenericForm> viewingForms;
    private final ObjectProperty<GenericForm> currentForm = new SimpleObjectProperty<>(null);

    public FormManager(ArrayList<GenericForm> formList){
        viewingForms = FXCollections.observableList(formList);
    }

    public void addForm(GenericForm form){
        viewingForms.add(form);
    }

    public void deleteForm(int index){
        viewingForms.remove(index);
    }

    public ObservableList<GenericForm> getViewingForms() {
        return viewingForms;
    }

    public GenericForm getCurrentForm() {
        return currentForm.get();
    }

    public ObjectProperty<GenericForm> currentFormProperty() {
        return currentForm;
    }

    public void setCurrentForm(GenericForm currentForm) {
        this.currentForm.set(currentForm);
    }

}
