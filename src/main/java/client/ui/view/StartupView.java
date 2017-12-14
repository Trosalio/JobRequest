package client.ui.view;

import client.ui.model.StartupModel;
import javafx.event.ActionEvent;

/**
 * Project Name: JobRequest
 */
public class StartupView {
    private StartupModel model;

    public void setModel(StartupModel model) {
        this.model = model;
    }

    public void onMOClicked(ActionEvent actionEvent) {
        model.openAdvertiseReviewer();
    }

    public void onCMOClicked(ActionEvent actionEvent) {
        model.openJobMasterReviewer();
    }
}
