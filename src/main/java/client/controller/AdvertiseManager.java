package client.controller;

import common.model.Advertise;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdvertiseManager {

    private final ObservableList<AdvertiseAdapter> advertiseList = FXCollections.observableArrayList();
    private final ObjectProperty<AdvertiseAdapter> currentAds = new SimpleObjectProperty<>(null);

    public void addAds(AdvertiseAdapter adapter){
        advertiseList.add(adapter);
        Advertise advertise = adapter.getAdvertise();
//        if(database != null) database.insertRecord(advertise);
    }

    public void deleteAds(int removeIndex){
        advertiseList.remove(removeIndex);
//        if(database != null) database.deleteRecord(removeIndex);
    }

    public void editAds(AdvertiseAdapter adapter){
        Advertise advertise = adapter.getAdvertise();
//        if(database != null) database.modifyRecord(advertise);
    }

    public ObservableList<AdvertiseAdapter> getAdvertiseList() {
        return advertiseList;
    }

    public AdvertiseAdapter getCurrentAdapter() {
        return currentAds.get();
    }

    public ObjectProperty<AdvertiseAdapter> currentAdapterProperty() {
        return currentAds;
    }

    public void setCurrentAdapter(AdvertiseAdapter currentAds) {
        this.currentAds.set(currentAds);
    }
}
