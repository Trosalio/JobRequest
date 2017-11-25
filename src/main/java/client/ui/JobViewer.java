package client.ui;

import common.models.Job;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class JobViewer {

    @FXML
    private TextField detailTxtF, requesterTxtF, quantityTxtF;
    @FXML
    private ComboBox<String> TypeOfMediaCBox;
    @FXML
    private ListView<String> candidateList, selectedList;
    @FXML
    private Button deselectBtn, selectBtn;
    @FXML
    private DatePicker startDatePicker, endDatePicker;
    @FXML
    private Button submitBtn, cancelBtn;
    private boolean saved;
    private Job job;

    @FXML
    void onCancelForm() {

    }

    @FXML
    void onChooseTypeOfMedia() {

    }

    @FXML
    void onDeselect() {

    }

    @FXML
    void onSelect() {

    }

    @FXML
    void onSubmitForm() {

    }

    public void setCurrentJob(Job currentJob) {
        job = currentJob;
    }

    public boolean isSaved() {
        return saved;
    }
}
