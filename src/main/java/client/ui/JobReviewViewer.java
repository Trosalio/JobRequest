package client.ui;

import common.formatter.DateFormatter;
import common.models.Advertise;
import common.models.Job;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.temporal.ChronoUnit;

/**
 * Project Name: MemoView
 */
public class JobReviewViewer {

    @FXML
    private DatePicker cDatePicker, sDatePicker, eDatePicker;
    @FXML
    private Label subjectLabel, refNoLabel;
    @FXML
    private Label detailNameLbl, requesterLbl, typeOfMediaLbl;
    @FXML
    private Label stationListLbl, qtyLbl, totalQtyLbl;
    @FXML
    private DatePicker fromDatePicker, toDatePicker;
    @FXML
    private Label totalDayLbl;
    @FXML
    private Label statusLbl;

    private Advertise advertise;
    private Job job;

    @FXML
    private void onPublishForm() {
        if (popJobWindow(job)) {
            job.setStatus("พร้อมใช้งาน");
            updateJobInfo();
        }
    }

    @FXML
    private void onDiscardForm() {
        job.setDefaultValue();
        updateJobInfo();
    }

    @FXML
    private void onSend() {
        // Need MIX to implement this method - it is bound to network connection
    }

    private boolean popJobWindow(Job job) {
        try {
            FXMLLoader JobUILoader = new FXMLLoader(getClass().getResource("/JobUI.fxml"));
            Parent root = JobUILoader.load();
            JobViewer jobViewer = JobUILoader.getController();
            jobViewer.setCurrentJob(job);

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Job");
            stage.setResizable(false);
            stage.showAndWait();
            return jobViewer.isSaved();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setCurrentAds(Advertise currentAdvertise) {
        advertise = currentAdvertise;
        job = advertise.getJob();
        prepareComponents();
    }

    public void prepareComponents() {
        // format all date components
        DateFormatter dateFormatter = new DateFormatter();
        dateFormatter.formatDatePicker(cDatePicker, sDatePicker, eDatePicker, fromDatePicker, toDatePicker);

        // setup components
        updateAdvertiseInfo();
        updateJobInfo();
    }

    private void updateAdvertiseInfo() {
        subjectLabel.setText(advertise.getAdsName());
        refNoLabel.setText(advertise.getRefNumber());
        cDatePicker.setValue(advertise.getCreateDate());
        sDatePicker.setValue(advertise.getStartDate());
        eDatePicker.setValue(advertise.getEndDate());
    }

    private void updateJobInfo() {
        if(job.isDefault()){
            setDefaultJobInfo();
        } else {
            detailNameLbl.setText(job.getJobDetail());
            requesterLbl.setText(job.getRequester());
            typeOfMediaLbl.setText(job.getTypeOfMedia());
            stationListLbl.setText(job.getStationList());
            qtyLbl.setText(Integer.toString(job.getQuantity()));
            totalQtyLbl.setText(Integer.toString(job.getStation().size() * job.getQuantity()));
            fromDatePicker.setValue(job.getFromDate());
            toDatePicker.setValue(job.getToDate());
            totalDayLbl.setText(Long.toString(ChronoUnit.DAYS.between(job.getFromDate(), job.getToDate())));
            statusLbl.setText(job.getStatus());
        }
    }

    private void setDefaultJobInfo() {
        detailNameLbl.setText("<หัวข้อเรื่อง>");
        requesterLbl.setText("<Reference Number>");
        typeOfMediaLbl.setText("<ประเภทของสื่อ>");
        stationListLbl.setText("<รายการของสถานที่>");
        qtyLbl.setText("-");
        totalQtyLbl.setText("-");
        fromDatePicker.setValue(null);
        toDatePicker.setValue(null);
        totalDayLbl.setText("-");
        statusLbl.setText("ยังไม่ถูกสร้าง");
    }
}

