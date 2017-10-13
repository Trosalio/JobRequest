package models.forms;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

/**
 * Project Name: MemoView
 */
public abstract class GenericForm {

    // subject to change into proper attributes
    protected SimpleObjectProperty<LocalDate> dateIssued = new SimpleObjectProperty<>(this, "dateIssued");
    protected SimpleStringProperty subject = new SimpleStringProperty(this, "subject");
    protected SimpleStringProperty recipient = new SimpleStringProperty(this, "recipient");
    // you may change 'Type Of Form' to other type if you see fit
    protected SimpleStringProperty typeOfForm = new SimpleStringProperty(this, "typeOfForm");

    public abstract void editForm();

    public LocalDate getDateIssued() {
        return dateIssued.get();
    }

    public SimpleObjectProperty<LocalDate> dateIssuedProperty() {
        return dateIssued;
    }

    public void setDateIssued(LocalDate dateIssued) {
        this.dateIssued.set(dateIssued);
    }

    public String getSubject() {
        return subject.get();
    }

    public SimpleStringProperty subjectProperty() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject.set(subject);
    }

    public String getRecipient() {
        return recipient.get();
    }

    public SimpleStringProperty recipientProperty() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient.set(recipient);
    }

    public String getTypeOfForm() {
        return typeOfForm.get();
    }

    public SimpleStringProperty typeOfFormProperty() {
        return typeOfForm;
    }

    public void setTypeOfForm(String typeOfForm) {
        this.typeOfForm.set(typeOfForm);
    }
}
