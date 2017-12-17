package client.ui.view;

import client.ui.model.JobRequestEditorModel;
import client.utility.AlertBoxSingleton;
import common.model.Station;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class JobRequestEditorView {

    @FXML
    private TextField detailTxtF, requesterTxtF, quantityTxtF;
    @FXML
    private ComboBox<String> typeOfMediaCBox;
    @FXML
    private ListView<Station> candidateListView, selectedListView;
    @FXML
    private ObservableList<Station> ObsCandidateList, ObsSelectedList;
    @FXML
    private DatePicker fromDatePicker, toDatePicker;
    @FXML
    private Button submitBtn, cancelBtn;
    private JobRequestEditorModel model;

    @FXML
    private void onCancel() {
        closeWindow();
    }

    @FXML
    private void onSelect() {
        onChoose(candidateListView, ObsCandidateList, ObsSelectedList);
    }

    @FXML
    private void onSelectAll() {
        onChooseAll(candidateListView, ObsCandidateList, ObsSelectedList);
    }

    @FXML
    private void onDeselect() {
        onChoose(selectedListView, ObsSelectedList, ObsCandidateList);
    }

    @FXML
    private void onDeselectAll() {
        onChooseAll(selectedListView, ObsSelectedList, ObsCandidateList);
    }

    private void onChoose(ListView<Station> srcView, ObservableList<Station> srcObs, ObservableList<Station> destObs) {
        Station station = srcView.getSelectionModel().getSelectedItem();
        if (station != null) {
            destObs.add(station);
            srcObs.remove(station);
        }
    }

    private void onChooseAll(ListView<Station> srcView, ObservableList<Station> srcObs, ObservableList<Station> destObs) {
        List<Station> stations = srcView.getItems();
        destObs.addAll(stations);
        srcObs.removeAll(stations);
    }

    @FXML
    private void onSubmit() {
        if (isCompleted()) {
            updateJobInfo();
            closeWindow();
        } else if (isTxtFEmpty(detailTxtF)) {
            AlertBoxSingleton.getInstance().popAlertBox("Error", "Detail name is not filled", "Please fill a detail name");
            detailTxtF.requestFocus();
        } else if (isTxtFEmpty(requesterTxtF)) {
            AlertBoxSingleton.getInstance().popAlertBox("Error", "Requester is not filled", "Please fill a requester detail");
            requesterTxtF.requestFocus();
        } else if (model.getJob().getStations().isEmpty()) {
            AlertBoxSingleton.getInstance().popAlertBox("Error", "No Station is selected", "Please select some station");
        } else if (isTxtFEmpty(quantityTxtF) || !isValidInteger(quantityTxtF)) {
            if (isTxtFEmpty(quantityTxtF)) {
                AlertBoxSingleton.getInstance().popAlertBox("Error", "Quantity info is not filled", "Please fill a quantity");
            } else {
                AlertBoxSingleton.getInstance().popAlertBox("Error", "Invalid value", "Please fill a natural number within a range 1 - 30");
            }
            quantityTxtF.requestFocus();
        } else if (fromDatePicker.getValue() == null) {
            AlertBoxSingleton.getInstance().popAlertBox("Error", "From Date is not filled", "Please select \'From Date\'");
            fromDatePicker.requestFocus();
        } else if ((toDatePicker.getValue() == null) || !isValidDate()) {
            if ((toDatePicker.getValue() == null)) {
                AlertBoxSingleton.getInstance().popAlertBox("Error", "To Date is not filled", "Please select \'End Date\'");
            } else {
                AlertBoxSingleton.getInstance().popAlertBox("Error", "InvalidDate", "\'End date\' must not go before \'From date\'");
            }
            toDatePicker.requestFocus();
        }
    }


    private void updateJobInfo() {
        String detail = detailTxtF.getText();
        String requester = requesterTxtF.getText();
        String typeOfMedia = typeOfMediaCBox.getSelectionModel().getSelectedItem();
        int quantity = Integer.parseInt(quantityTxtF.getText());
        LocalDate fromDate = fromDatePicker.getValue();
        LocalDate toDate = toDatePicker.getValue();
        model.updateJobInfo(detail, requester, typeOfMedia, quantity, fromDate, toDate);
    }


    private boolean isCompleted() {
        return !(isTxtFEmpty(detailTxtF) || isTxtFEmpty(requesterTxtF) ||
                isTxtFEmpty(quantityTxtF) || !isValidInteger(quantityTxtF) || model.getJob().getStations().isEmpty() ||
                fromDatePicker.getValue() == null || toDatePicker.getValue() == null || !isValidDate());
    }

    private boolean isTxtFEmpty(TextField txtF) {
        return txtF.getText() == null || txtF.getText().isEmpty();
    }

    private boolean isValidInteger(TextField txtF) {
        return txtF.getText().matches("[0-9]+") && 0 < Integer.parseInt(txtF.getText()) && (Integer.parseInt(txtF.getText())) <= 30;
    }

    private boolean isValidDate() {
        return !toDatePicker.getValue().isBefore(fromDatePicker.getValue());
    }

    public void setupUI() {
        setupTextField();
        setupDatePickers();
        setupTypeOfMediaComboBox();
        setupSelectedList();
        setupCandidateList();
    }

    private void setupTextField() {
        detailTxtF.setText(model.getJob().getJobDetail());
        requesterTxtF.setText(model.getJob().getRequester());
        quantityTxtF.setText(Integer.toString(model.getJob().getQuantity()));
    }

    private void setupDatePickers() {
        model.getDateFormatter().formatDatePickers(fromDatePicker, toDatePicker);
        model.getDateFormatter().preventDatePickedBefore(fromDatePicker, model.getIssueDate().plusWeeks(1));
        fromDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> model.getDateFormatter().preventDatePickedBefore(toDatePicker, newValue));
        fromDatePicker.setValue(model.getJob().getFromDate());
        toDatePicker.setValue(model.getJob().getToDate());
    }

    private void setupTypeOfMediaComboBox() {
        typeOfMediaCBox.getItems().addAll(model.getCandidateTypeOfMedia());
        if (model.getJob().getTypeOfMedia() != null) {
            typeOfMediaCBox.setValue(model.getJob().getTypeOfMedia());
        } else {
            typeOfMediaCBox.getSelectionModel().selectFirst();
        }
    }

    private void setupSelectedList() {
        ObsSelectedList = FXCollections.observableList(model.getStationsInJob());
        selectedListView.setItems(ObsSelectedList.sorted());
    }

    private void setupCandidateList() {
        ObsCandidateList = FXCollections.observableList(model.getStationList());
        ObsCandidateList.removeAll(ObsSelectedList);
        candidateListView.setItems(ObsCandidateList.sorted());
    }

    private void closeWindow() {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void setModel(JobRequestEditorModel model) {
        this.model = model;
    }
}
