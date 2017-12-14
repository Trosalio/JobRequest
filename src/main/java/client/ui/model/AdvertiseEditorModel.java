package client.ui.model;

import client.controller.AdvertiseAdapter;

public class AdvertiseEditorModel {
    private boolean confirm = false;
    private AdvertiseAdapter adapter;

    public AdvertiseEditorModel(AdvertiseAdapter adapter) {
        this.adapter = adapter;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void saveAdvertise() {
        adapter.save();
        confirm = true;
    }

    public AdvertiseAdapter getAdapter(){
        return this.adapter;
    }
}
