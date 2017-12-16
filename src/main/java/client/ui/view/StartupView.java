package client.ui.view;

import client.ui.model.StartupModel;
import javafx.fxml.FXML;

/**
 * Project Name: JobRequest
 */
public class StartupView {
    private StartupModel model;

    public void setModel(StartupModel model) {
        this.model = model;
    }

    @FXML
    public void onMOClicked() {
        model.openAdvertiseReviewer();
    }

    @FXML
    public void onCMOClicked() {
        model.openJobMasterReviewer();
    }
}
