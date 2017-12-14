package client.ui.model;

import client.controller.ViewManager;
import common.model.Job;
import common.model.Station;

import java.time.LocalDate;
import java.util.List;

public class JobEditorModel {

    private final ViewManager viewManager;
    private boolean saveBool;
    private List<String> typeOfMedia;

    private Job job;

    public JobEditorModel(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    public void updateJobInfo(String detail, String requester, String typeOfMedia, int quantity, LocalDate fromDate, LocalDate toDate) {
        job.setJobDetail(detail);
        job.setRequester(requester);
        job.setTypeOfMedia(typeOfMedia);
        job.setQuantity(quantity);
        job.setFromDate(fromDate);
        job.setToDate(toDate);
        saveBool = true;
    }

    public boolean isSaved() {
        return saveBool;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public List<Station> getStationList() {
        return viewManager.getController().loadStationList();
    }

    public List<Station>  getStationsInJob() {
        return job.getStations();
    }

    public List<String> getCandidateTypeOfMedia() {
        if (typeOfMedia == null){
            typeOfMedia = viewManager.getController().loadCandidateTypeOfMedia();
        }
        return typeOfMedia;
    }
}
