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

    public void load(List<Advertise> source) {
        source.forEach(advertise -> advertiseList.add(new AdvertiseAdapter(advertise)));
    }

    public void add(AdvertiseAdapter adapter) {
        advertiseList.add(adapter);
    }

    public void remove(AdvertiseAdapter adapter) {
        advertiseList.remove(adapter);
    }

    //--------------------------- OTHER ----------------------------------

    public ObservableList<AdvertiseAdapter> getAdvertiseList() {
        return advertiseList;
    }

    public boolean isAlreadyExist(AdvertiseAdapter adapter) {
        return advertiseList.stream()
                .map(a -> a.refNoProperty().get())
                .anyMatch(ref -> ref.equals(adapter.refNoProperty().get()));
    }
}
