package client.ui.model;

import client.controller.AdvertiseAdapter;
import client.controller.ViewManager;
import common.model.Advertise;

import java.time.LocalDate;

public class AdvertiseEditorModel {

    private final ViewManager viewManager;
    private boolean saveBool = false;
    private AdvertiseAdapter adapter;

    public AdvertiseEditorModel(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    public boolean isSaved() {
        if(saveBool){
            saveBool = false;
            return true;
        }
        return false;
    }

    public void saveAdvertise(String subject, String refNumber, LocalDate createDate) {
        Advertise advertise = adapter.getAdaptee();
        advertise.setAdsName(subject);
        advertise.setRefNumber(refNumber);
        advertise.setCreateDate(createDate);
        adapter.updateAdapter();
        saveBool = true;
    }

    public void setAdapter(AdvertiseAdapter adapter){
        this.adapter = adapter;
    }

    public AdvertiseAdapter getAdapter(){
        return this.adapter;
    }
}
