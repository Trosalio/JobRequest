package client.ui.model;

import client.controller.AdvertiseAdapter;
import client.controller.AdvertiseManager;
import client.controller.ViewManager;
import common.model.Advertise;
import common.utility.AlertBoxSingleton;

public class AdvertiseReviewModel {

    private final ViewManager viewManager;
    private boolean state = false;

    public AdvertiseReviewModel(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    public void addAdvertise() {
        AdvertiseAdapter adapter = new AdvertiseAdapter(new Advertise());
        if (viewManager.showAdvertiseEditor(adapter)) {
            viewManager.getController().handleAdd(adapter);
            state = true;
        } else {
            state = false;
        }
    }

    public void deleteAdvertise(int removedIndex) {
        if (AlertBoxSingleton.getInstance().popAlertBox("Confirmation", "Deleting...", "คุณต้องการจะลบโฆษณานี้?")) {
            AdvertiseAdapter removedAdapter = viewManager.getController().getAdvertiseManager().deleteAdvertise(removedIndex);
            viewManager.getController().handleRemove(removedAdapter);
            state = true;
        } else {
            state = false;
        }
    }

    public void editAdvertise() {
        AdvertiseAdapter adapter = viewManager.getController().getAdvertiseManager().getCurrentAdapter();
        if (adapter != null) {
            if (viewManager.showAdvertiseEditor(adapter)) {
                viewManager.getController().getAdvertiseManager().editAds(adapter);
                viewManager.getController().handleEdit(adapter);
            }
        }
    }

    public void openJobReview(AdvertiseAdapter adapter) {
        viewManager.showJobReviewer(adapter);
    }

    public AdvertiseManager getAdManager() {
        return viewManager.getController().getAdvertiseManager();
    }

    public boolean isStateChanged() {
        if (state) {
            state = false;
            return true;
        }
        return false;
    }
}
