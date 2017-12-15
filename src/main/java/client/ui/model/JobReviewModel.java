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
        this.advertise = this.adapter.getModel();
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
            job.setStatus("READY");
            advertise.setJob(job);
            viewManager.getHandler().handleAdd(job);
            state = true;
        } else {
            state = false;
        }
    }

    public void editForm() {
        if (viewManager.showJobEditor(job)) {
            job.setStatus("READY");
            advertise.setJob(job);
            viewManager.getHandler().handleEdit(job);
            state = true;
        } else {
            state = false;
        }
    }

    public void discardForm() {
        viewManager.getHandler().handleRemove(job);
        job.setDefaultValue();
        advertise.setJob(null);
        state = true;
    }

    public void send(){
        job.setStatus("PENDING");
        advertise.setJob(job);
        viewManager.getHandler().handleEdit(job);
        state = true;
    }

    public boolean isStateChanged() {
        if (state) {
            state = false;
            return true;
        }
        return false;
    }
}
