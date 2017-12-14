package client.controller;

import common.model.Advertise;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdvertiseManager {

    private final ObservableList<AdvertiseAdapter> advertiseList = FXCollections.observableArrayList();

    //--------------------------- Simple CRUD Operation ---------------------------

    public void loadAdvertises(List<Advertise> source) {
        source.forEach(advertise -> advertiseList.add(new AdvertiseAdapter(advertise)));
    }

    public void addAdvertise(AdvertiseAdapter adapter) {
        advertiseList.add(adapter);
    }

    public AdvertiseAdapter deleteAdvertise(int removeIndex) {
        return advertiseList.remove(removeIndex);
    }

    //--------------------------- Accessor ----------------------------------

    public ObservableList<AdvertiseAdapter> getAdvertiseList() {
        return advertiseList;
    }

}
