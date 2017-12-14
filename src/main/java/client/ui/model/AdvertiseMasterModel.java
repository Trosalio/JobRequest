package client.ui.model;

import client.controller.AdvertiseAdapter;
import client.controller.ViewManager;
import common.model.Advertise;
import common.utility.AlertBoxSingleton;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;

public class AdvertiseMasterModel {

    private final ViewManager viewManager;
    private final ObjectProperty<AdvertiseAdapter> currentAds = new SimpleObjectProperty<>(null);

    public AdvertiseMasterModel(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    public void addAdvertise() {
        AdvertiseAdapter adapter = new AdvertiseAdapter(new Advertise());
        if (viewManager.showAdvertiseEditor(adapter)) {
            viewManager.getController().getAdvertiseManager().addAdvertise(adapter);
            viewManager.getController().handleAdd(adapter);
        }
    }

    public void deleteAdvertise(int removedIndex) {
        if (AlertBoxSingleton.getInstance().popAlertBox(
                "Confirmation",
                "Deleting...",
                "Delete this ads?")) {
            AdvertiseAdapter removedAdapter = viewManager.getController().getAdvertiseManager().deleteAdvertise(removedIndex);
            viewManager.getController().handleRemove(removedAdapter);
        }
    }

    public void editAdvertise() {
        AdvertiseAdapter adapter = currentAds.get();
        if (adapter != null) {
            if (viewManager.showAdvertiseEditor(adapter)) {
                viewManager.getController().handleEdit(adapter);
            }
        }
    }

    public void openJobReview(AdvertiseAdapter adapter) {
        viewManager.showJobReviewer(adapter);
    }

    public ObservableList<AdvertiseAdapter> getAdvertiseList() {
        return viewManager.getController().getAdvertiseManager().getAdvertiseList();
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

}
