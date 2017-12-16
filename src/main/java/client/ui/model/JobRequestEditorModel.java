package client.ui.model;

import client.controller.ActionController;
import common.model.Job;
import common.model.Station;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JobRequestEditorModel {

    private final ActionController handler;
    private boolean saveBool;
    private List<String> typeOfMedia;

    private Job job;

    public JobRequestEditorModel(ActionController handler) {
        this.handler = handler;
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
        return handler.loadStationList();
    }

    public List<Station> getStationsInJob() {
        return job.getStations();
    }

    public List<String> getCandidateTypeOfMedia() {
        if (typeOfMedia == null) {
            typeOfMedia = loadCandidateTypeOfMedia();
        }
        return typeOfMedia;
    }

    private List<String> loadCandidateTypeOfMedia() {
        List<String> typeOfMedia = new ArrayList<>();
        try {
            File file = new File(getClass().getResource("/type_of_media.txt").toURI());
            BufferedReader reader = new BufferedReader(new FileReader(file));
            for (String media = reader.readLine(); media != null; media = reader.readLine()) {
                typeOfMedia.add(media);
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return typeOfMedia;
    }
}
