package client.ui.model;

import client.controller.AdvertiseAdapter;
import client.controller.ViewManager;
import common.model.Advertise;
import common.model.Job;

public class JobReviewModel {

    private final ViewManager viewManager;
    private Advertise advertise;
    private Job job;
    private boolean state;

    public JobReviewModel(ViewManager viewManager) {
        this.viewManager = viewManager;
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

    public Advertise getAdvertise() {
        return advertise;
    }

    public void publishForm() {
        if (viewManager.showJobEditor(job)) {
            job.setStatus("READY");
            advertise.setJob(job);
            job.setRefNumber(advertise.getRefNumber());
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
        advertise.setJob(null);
        state = true;
    }

    public void send() {
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
