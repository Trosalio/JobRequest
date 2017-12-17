package client.ui.model;

import client.controller.ActionController;
import client.controller.AdvertiseAdapter;
import common.formatter.DateFormatter;

public class AdsEditorModel {

    private final DateFormatter dateFormatter = new DateFormatter();
    private boolean confirm = false;
    private AdvertiseAdapter adapter;
    private ActionController handler;

    public AdsEditorModel(AdvertiseAdapter adapter) {
        this.adapter = adapter;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void saveAdvertise() {
        adapter.save();
        confirm = true;
    }

    public boolean checkExistence() {
        return handler.getAdvertiseManager().isAlreadyExist(adapter);
    }

    public AdvertiseAdapter getAdapter() {
        return this.adapter;
    }

    public DateFormatter getDateFormatter() {
        return dateFormatter;
    }

    public void setHandler(ActionController handler) {
        this.handler = handler;
    }

}
