package client.ui.model;

import client.controller.AdvertiseAdapter;
import client.controller.ViewManager;
import common.model.Advertise;
import common.model.Job;

public class JobReviewModel {

    private final ViewManager viewManager;
    private AdvertiseAdapter adapter;
    private Advertise advertise;
    private Job job;
    private boolean state;

    public JobReviewModel(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    public void setAdapter(AdvertiseAdapter adapter) {
        this.adapter = adapter;
        this.advertise = this.adapter.getAdaptee();
        this.job = (advertise.getJob() == null ? new Job() : advertise.getJob());
    }

    public Job getJob() {
        return job;
    }

    public Advertise getAdvertise() {
        return advertise;
    }

    public void publishForm() {
        if (viewManager.showJobEditor(job)) {
            job.setStatus("พร้อมใช้งาน");
            advertise.setJob(job);
            viewManager.getController().handleAdd(job);
            state = true;
        } else {
            state = false;
        }
    }

    public boolean isStateChanged() {
        if (state) {
            state = false;
            return true;
        }
        return false;
    }

    public void discardForm() {
        Job discardedJob = this.job;
        job.setDefaultValue();
        advertise.setJob(null);
        viewManager.getController().handleRemove(job);
        state = true;
    }

    public void send(){
        // TODO Need MIX to implement this method as it is bound to networking
        state = true;
    }

    public void editForm() {
    }
}
