package client.controller;

import common.model.Advertise;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class AdvertiseAdapter {

    private Advertise adaptee;
    private final SimpleStringProperty name = new SimpleStringProperty(this, "name");
    private final SimpleStringProperty refNo = new SimpleStringProperty(this, "refNo");
    private final SimpleObjectProperty<LocalDate> createDate = new SimpleObjectProperty<>(this,"createDate");
    public AdvertiseAdapter(Advertise adaptee) {
        this.adaptee = adaptee;
        updateAdapter();
    }

    public void updateAdapter() {
        name.set(adaptee.getAdsName());
        refNo.set(adaptee.getRefNumber());
        createDate.set(adaptee.getCreateDate());
    }

    public void updateAdaptee() {
        if(adaptee != null) {
            adaptee.setAdsName(name.get());
            adaptee.setRefNumber(refNo.get());
            adaptee.setCreateDate(createDate.get());
        }
    }

    // -------------------  Getter --------------------
    public Advertise getAdaptee() {
        return adaptee;
    }

    // -----------------------  Property -----------------------
    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleStringProperty refNoProperty() {
        return refNo;
    }

    public SimpleObjectProperty<LocalDate> createDateProperty() {
        return createDate;
    }
}
