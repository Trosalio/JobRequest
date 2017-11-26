package client.ui;

import common.model.Job;
import common.model.Station;
import common.utility.AlertBoxSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JobViewer {

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
    private Button cancelBtn;
    private List<Station> stationList = new ArrayList<>();
    private boolean saved;
    private Job job;

    @FXML
    public void initialize() {
        typeOfMediaCBox.getItems().addAll(
                "<เลือกประเภทของสื่อ...>",
                "Standard Poster",
                "Balustrade Sticker",
                "Poster TIM",
                "T/O Sticker",
                "Information Board",
                "Platform Truss",
                "Banner",
                "Bulk Head",
                "Pump Room"
        );
        typeOfMediaCBox.getSelectionModel().selectFirst();

        setupCandidateList();
    }

    private void setupCandidateList() {
        loadStationList();
        ObsCandidateList = FXCollections.observableList(stationList);
        candidateListView.setItems(ObsCandidateList.sorted());
    }

    private void loadStationList() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("station_list.txt"));
            for(String line : lines){
                String[] data = line.split(" ");
                Station station = new Station(data[0], data[1]);
                stationList.add(station);
            }
        } catch (IOException e) {
            System.err.println("File not found");
            e.printStackTrace();
        }
    }

    private boolean isCompleted() {
        return !(isTxtFEmpty(detailTxtF) || isTxtFEmpty(requesterTxtF) || typeOfMediaCBox.getSelectionModel().getSelectedIndex() == 0 ||
                isTxtFEmpty(quantityTxtF) || !isValidInteger(quantityTxtF) || job.getStations().isEmpty() ||
                fromDatePicker.getValue() == null || toDatePicker.getValue() == null || !isValidDate());
    }

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

    private void onChoose(ListView<Station> srcView, ObservableList<Station> srcObs, ObservableList<Station> destObs){
        Station station = srcView.getSelectionModel().getSelectedItem();
        if(station != null){
            destObs.add(station);
            srcObs.remove(station);
        }
    }

    private void onChooseAll(ListView<Station> srcView, ObservableList<Station> srcObs, ObservableList<Station> destObs){
        List<Station> stations = srcView.getItems();
        destObs.addAll(stations);
        srcObs.removeAll(stations);
    }

    @FXML
    private void onSubmit() {
        if (isCompleted()) {
            updateJobInfo();
            saved = true;
            closeWindow();
        } else if (isTxtFEmpty(detailTxtF)) {
            AlertBoxSingleton.getInstance().popAlertBox("Error", "Detail info is not filled", "กรุณาระบุฃื่อเรื่อง");
            detailTxtF.requestFocus();
        } else if (isTxtFEmpty(requesterTxtF)) {
            AlertBoxSingleton.getInstance().popAlertBox("Error", "Requester info is not filled", "กรุณรนะบุผู้ขอติดตั้งสื่อ");
            requesterTxtF.requestFocus();
        } else if (typeOfMediaCBox.getSelectionModel().getSelectedIndex() == 0) {
            AlertBoxSingleton.getInstance().popAlertBox("Error", "Type of Media is not selected", "กรุณาเลือกหรือกรอกประเภทของสื่อประชาสัมพันธ์");
            typeOfMediaCBox.requestFocus();
        } else if (job.getStations().isEmpty()) {
            AlertBoxSingleton.getInstance().popAlertBox("Error", "No Station is selected", "กรุณาเลือกสถานีที่ต้องการติดตั้ง");
        } else if (isTxtFEmpty(quantityTxtF) || !isValidInteger(quantityTxtF)) {
            if (isTxtFEmpty(quantityTxtF)) {
                AlertBoxSingleton.getInstance().popAlertBox("Error", "Quantity info is not filled", "กรุณาระบุจำนวนที่ต้องการติดตั้ง");
            } else {
                AlertBoxSingleton.getInstance().popAlertBox("Error", "Not an integer value", "กรุณาระบุจำนวนที่ต้องการติดตั้งเป็นที่เป็นตัวเลขและมีค่ามากกว่า 0 เท่านั้น");
            }
            quantityTxtF.requestFocus();
        } else if (fromDatePicker.getValue() == null) {
            AlertBoxSingleton.getInstance().popAlertBox("Error", "From Date is not filled", "กรุณาระบุวันเริ่มต้น");
            fromDatePicker.requestFocus();
        } else if ((toDatePicker.getValue() == null) || !isValidDate()) {
            if((toDatePicker.getValue() == null)){
                AlertBoxSingleton.getInstance().popAlertBox("Error", "To Date is not filled", "กรุณาระบุวันสิ้นสุด");
            } else {
                AlertBoxSingleton.getInstance().popAlertBox("Error", "InvalidDate", "วันสิ้นสุดต้องไม่มาก่อนวันเริ่มต้น");
            }
            toDatePicker.requestFocus();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void setCurrentJob(Job currentJob) {
        job = currentJob;
        setUpJob();
    }

    private void setUpJob() {
        detailTxtF.setText(job.getJobDetail());
        requesterTxtF.setText(job.getRequester());
        if(job.getTypeOfMedia() != null){
            typeOfMediaCBox.setValue(job.getTypeOfMedia());
        } else {
            typeOfMediaCBox.getSelectionModel().selectFirst();
        }
        ObsSelectedList = FXCollections.observableList(job.getStations());
        selectedListView.setItems(ObsSelectedList.sorted());
        ObsCandidateList.removeAll(ObsSelectedList);
        if(job.getQuantity() > 0){
            quantityTxtF.setText(Integer.toString(job.getQuantity()));
        } else {
            quantityTxtF.setText(null);
        }
        fromDatePicker.setValue(job.getFromDate());
        toDatePicker.setValue(job.getToDate());
    }

    private void updateJobInfo() {
        job.setJobDetail(detailTxtF.getText());
        job.setRequester(requesterTxtF.getText());
        job.setTypeOfMedia(typeOfMediaCBox.getSelectionModel().getSelectedItem());
        job.setQuantity(Integer.parseInt(quantityTxtF.getText()));
        job.setFromDate(fromDatePicker.getValue());
        job.setToDate(toDatePicker.getValue());
    }

    public boolean isSaved() {
        return saved;
    }

    private boolean isTxtFEmpty(TextField txtF){
        return txtF.getText() == null || txtF.getText().isEmpty();
    }
    private boolean isValidInteger(TextField txtF) {
        return txtF.getText().matches("[0-9]+") && Integer.parseInt(txtF.getText()) > 0;
    }

    private boolean isValidDate() {
        return !toDatePicker.getValue().isBefore(fromDatePicker.getValue());
    }
}
