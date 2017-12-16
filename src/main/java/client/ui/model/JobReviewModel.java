package client.ui.model;

import client.controller.ActionController;
import client.controller.AdvertiseAdapter;
import common.model.Advertise;
import common.model.Job;

public class JobReviewModel {

    private final ActionController handler;
    private Advertise advertise;
    private Job job;
    private boolean state;

    public JobReviewModel(ActionController handler) {
        this.handler = handler;
    }

    public void setAdapter(AdvertiseAdapter adapter) {
        this.advertise = adapter.getModel();
        this.job = advertise.getJob();
    }


    public void setNewJob() {
        this.job = new Job();
    }

    public Job getJob() {
        return job;
    }

    public void publishForm() {
        if (handler.getViewManager().showJobRequestEditor(job)) {
            job.setStatus("READY");
            advertise.setJob(job);
            job.setRefNumber(advertise.getRefNumber());
            handler.handleAdd(job);
            state = true;
        } else {
            state = false;
        }
    }

    public void editForm() {
        if (handler.getViewManager().showJobRequestEditor(job)) {
            job.setStatus("READY");
            advertise.setJob(job);
            handler.handleEdit(job);
            state = true;
        } else {
            state = false;
        }
    }

    public void discardForm() {
        handler.handleRemove(job);
        advertise.setJob(job);
        job = null;
        state = true;
    }

    public void send() {
        job.setStatus("PENDING");
        advertise.setJob(job);
        handler.handleEdit(job);
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
