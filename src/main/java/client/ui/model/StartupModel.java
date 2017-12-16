package client.ui.model;

import client.controller.ViewManager; /**
 * Project Name: JobRequest
 */
public class StartupModel {

    private ViewManager viewManager;

    public StartupModel(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    public void openAdvertiseReviewer() {
        viewManager.showAdvertiseMaster();
    }

    public void openJobMasterReviewer() {
        viewManager.showReviewerMaster();
    }
}
