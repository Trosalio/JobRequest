package client.controller;

import common.model.Job; /**
 * Project Name: JobRequest
 */
public class JobAdapter {

    private Job adaptee;

    public JobAdapter(Job adaptee) {
        this.adaptee = adaptee;
        updateAdapter();
    }

    private void updateAdapter() {
    }

    public Job getAdaptee() {
        return adaptee;
    }
}
