package client.controller;

import common.model.Job;
import common.model.Station;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Project Name: JobRequest
 */
public class JobAdapter {

    private SimpleStringProperty jobDetail = new SimpleStringProperty(this, "jobDetail");
    private SimpleStringProperty requester = new SimpleStringProperty(this, "requester");
    private SimpleStringProperty typeOfMedia = new SimpleStringProperty(this, "typeOfMedia");
    private SimpleListProperty<Station> stations = new SimpleListProperty<>(this, "stations");
    private SimpleIntegerProperty quantity = new SimpleIntegerProperty(this, "quantity");
    private SimpleObjectProperty<LocalDate> fromDate = new SimpleObjectProperty<>(this, "fromDate");
    private SimpleObjectProperty<LocalDate> toDate = new SimpleObjectProperty<>(this, "toDate");
    private SimpleStringProperty status = new SimpleStringProperty(this, "status");
    private Job model;

    public JobAdapter(Job model) {
        this.model = model;
        reload();
    }

    public void reload() {
        jobDetail.set(model.getJobDetail());
        requester.set(model.getRequester());
        typeOfMedia.set(model.getTypeOfMedia());
        stations.set(FXCollections.observableArrayList(model.getStations()));
        quantity.set(model.getQuantity());
        fromDate.set(model.getFromDate());
        toDate.set(model.getToDate());
        status.set(model.getStatus());
    }

    public void save() {
        if (model != null) {
            model.setJobDetail(jobDetail.get());
            model.setRequester(requester.get());
            model.setTypeOfMedia(typeOfMedia.get());
            model.setStations(new ArrayList<>(stations.get()));
            model.setQuantity(quantity.get());
            model.setFromDate(fromDate.get());
            model.setToDate(toDate.get());
            model.setStatus(status.get());
        }
    }

    public Job getModel() {
        return model;
    }

    // -----------------------  Property -----------------------


    public SimpleStringProperty jobDetailProperty() {
        return jobDetail;
    }

    public SimpleStringProperty requesterProperty() {
        return requester;
    }

    public SimpleStringProperty typeOfMediaProperty() {
        return typeOfMedia;
    }

    public SimpleListProperty<Station> stationsProperty() {
        return stations;
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }

    public SimpleObjectProperty<LocalDate> fromDateProperty() {
        return fromDate;
    }

    public SimpleObjectProperty<LocalDate> toDateProperty() {
        return toDate;
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }
}
