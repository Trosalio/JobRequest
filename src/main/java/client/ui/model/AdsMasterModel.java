package client.ui.model;

import client.controller.ActionController;
import client.controller.AdvertiseAdapter;
import common.formatter.DateFormatter;
import common.model.Advertise;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;

public class AdsMasterModel {

    private final ActionController handler;
    private final ObjectProperty<AdvertiseAdapter> currentAds = new SimpleObjectProperty<>(null);
    private final DateFormatter dateFormatter = new DateFormatter();

    public AdsMasterModel(ActionController handler) {
        this.handler = handler;
        refresh();
    }

    public void refresh() {
        getAdvertiseList().clear();
        handler.handleLoadAds();
    }

    public void addAdvertise() {
        AdvertiseAdapter temp = new AdvertiseAdapter(new Advertise());
        if (handler.getViewManager().showAdvertiseEditor(temp)) {

            handler.handleAdd(temp);
            setCurrentAdapter(temp);
        }
    }

    public void deleteAdvertise() {
        AdvertiseAdapter current = getCurrentAdapter();
        if (current != null) handler.handleRemove(current);

    }

    public void editAdvertise() {
        AdvertiseAdapter current = getCurrentAdapter();
        if (current != null) {
            AdvertiseAdapter temp = new AdvertiseAdapter(current.getModel());
            if (handler.getViewManager().showAdvertiseEditor(temp)) {
                current.reload();
                setCurrentAdapter(current);
                handler.handleEdit(current);
            }
        }
    }

    public void openJobReview() {
        handler.getViewManager().showJobReviewer(getCurrentAdapter());
        currentAds.get().reload();
    }

    public ObservableList<AdvertiseAdapter> getAdvertiseList() {
        return handler.getAdvertiseManager().getAdvertiseList();
    }

    public AdvertiseAdapter getCurrentAdapter() {
        return currentAds.get();
    }

    public void setCurrentAdapter(AdvertiseAdapter currentAds) {
        this.currentAds.set(currentAds);
    }

    public ObjectProperty<AdvertiseAdapter> currentAdapterProperty() {
        return currentAds;
    }

    public DateFormatter getDateFormatter() {
        return dateFormatter;
    }


}
