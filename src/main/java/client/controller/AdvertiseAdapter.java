package client.controller;

import common.model.Advertise;
import common.model.Job;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class AdvertiseAdapter {

    private final SimpleObjectProperty<LocalDate> createDate = new SimpleObjectProperty<>(this, "createDate");
    private final SimpleStringProperty name = new SimpleStringProperty(this, "name");
    private final SimpleStringProperty refNo = new SimpleStringProperty(this, "refNo");
    private final SimpleObjectProperty<Job> job = new SimpleObjectProperty<>(this, "job");
    private Advertise model;

    public AdvertiseAdapter(Advertise model) {
        this.model = model;
        reload();
    }

    public void reload() {
        name.set(model.getAdsName());
        refNo.set(model.getRefNumber());
        createDate.set(model.getCreateDate());
        job.set(model.getJob());
    }

    public void save() {
        if (model != null) {
            model.setAdsName(name.get());
            model.setRefNumber(refNo.get());
            model.setCreateDate(createDate.get());
            model.setJob(job.get());
        }
    }

    // -------------------  Getter --------------------
    public Advertise getModel() {
        return model;
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

    public SimpleObjectProperty<Job> jobProperty() {
        return job;
    }
}
