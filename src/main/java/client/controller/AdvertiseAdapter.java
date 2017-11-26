package client.controller;

import common.model.Advertise;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class AdvertiseAdapter {

    private Advertise advertise;
    private final SimpleStringProperty name = new SimpleStringProperty(this, "name");
    private final SimpleStringProperty refNo = new SimpleStringProperty(this, "refNo");
    private final SimpleObjectProperty<LocalDate> createDate = new SimpleObjectProperty<>(this,"createDate");
    private final SimpleObjectProperty<LocalDate> startDate = new SimpleObjectProperty<>(this, "startDate");
    private final SimpleObjectProperty<LocalDate> endDate = new SimpleObjectProperty<>(this, "endDate");

    public AdvertiseAdapter(Advertise advertise) {
        this.advertise = advertise;
        update();
    }

    public void update() {
        name.set(advertise.getAdsName());
        refNo.set(advertise.getRefNumber());
        createDate.set(advertise.getCreateDate());
        startDate.set(advertise.getStartDate());
        endDate.set(advertise.getEndDate());
    }

    // -------------------  Getter & Setter --------------------
    public Advertise getAdvertise() {
        return advertise;
    }

    public void setAdvertise(Advertise advertise){
        this.advertise = advertise;
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

    public SimpleObjectProperty<LocalDate> startDateProperty() {
        return startDate;
    }

    public SimpleObjectProperty<LocalDate> endDateProperty() {
        return endDate;
    }
}
