package client.controller;

import common.model.Advertise;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdvertiseManager {

    private final ObservableList<AdvertiseAdapter> advertiseList = FXCollections.observableArrayList();
    private final ObjectProperty<AdvertiseAdapter> currentAds = new SimpleObjectProperty<>(null);

    //--------------------------- Simple CRUD Operation ---------------------------

    public void loadAdvertises(List<Advertise> source){
        source.forEach(advertise -> advertiseList.add(new AdvertiseAdapter(advertise)));
    }

    public void addAdvertise(AdvertiseAdapter adapter){
        advertiseList.add(adapter);
        Advertise advertise = adapter.getAdaptee();
    }

    public void deleteAdvertise(int removeIndex){
        advertiseList.remove(removeIndex);
    }

    public void editAds(AdvertiseAdapter adapter){
        Advertise advertise = adapter.getAdaptee();
    }

    //--------------------------- Accessor ----------------------------------

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
