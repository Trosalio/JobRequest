package client.controller;

import common.model.Advertise;
import common.model.Job;
import common.model.Station;
import common.service.AdvertiseService;
import common.service.JobService;
import common.service.StationService;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MainController {

    private AdvertiseService advertiseService;
    private JobService jobService;
    private StationService stationService;
    private AdvertiseManager advertiseManager;
    private JobManager jobManager;
    private ViewManager viewManager;

    public MainController(AdvertiseManager advertiseManager, JobManager jobManager, ViewManager viewManager) {
        this.advertiseManager = advertiseManager;
        this.jobManager = jobManager;
        this.viewManager = viewManager;
    }

    // Client - MO
    public void handleLoadAdvertises() {
        List<Advertise> source = advertiseService.loadAdvertises();
        for (Advertise advertise : source) { if (advertise.getJobID() >= 0) advertise.setJob(loadJob(advertise.getJobID())); }
        advertiseManager.loadAdvertises(source);
    }

    public void handleAdd(AdvertiseAdapter adapter) {
        advertiseManager.addAdvertise(adapter);
        Advertise advertise = adapter.getAdaptee();
        advertiseService.addAdvertise(advertise);
        if(advertise.getJob() != null){
            handleAdd(advertise.getJob());
        }
    }

    public void handleEdit(AdvertiseAdapter adapter) {
        advertiseManager.editAds(adapter);
        Advertise advertise = adapter.getAdaptee();
        advertiseService.updateAdvertise(advertise);
        if(advertise.getJob() != null){
            handleEdit(advertise.getJob());
        }
    }

    public void handleRemove(int removedIndex) {
        AdvertiseAdapter removedAdapter = advertiseManager.deleteAdvertise(removedIndex);
        Advertise removedAdvertise = removedAdapter.getAdaptee();
        advertiseService.deleteAdvertise(removedAdvertise);
        // In case that CASCADE ON DELETE did not work
//        if(removedAdvertise.getJob() != null){
//            handleRemove(removedAdvertise.getJob());
//        }
    }

    public void handleAdd(Job job) {
        jobService.addJob(job);
        stationService.addStationInJob(job);
    }

    public void handleEdit(Job job) {
        jobService.updateJob(job);
        stationService.updateStationInJob(job);
    }

    public void handleRemove(Job job) {
        jobService.deleteJob(job);
        // In case that CASCADE ON DELETE did not work
//        stationService.deleteStationInJob(job);
    }

    public List<Station> loadStationList() {
        return stationService.loadStations();
    }

    public List<String> loadCandidateTypeOfMedia() {
        final String file_url = "type_of_media.txt";
        List<String> typeOfMedia = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file_url));
            for (String media = reader.readLine(); media != null; media = reader.readLine()) {
                typeOfMedia.add(media);
            }
        } catch (IOException e) {
            System.err.println("File in " + file_url + " not found");
            e.printStackTrace();
        }
        return typeOfMedia;
    }

    public AdvertiseManager getAdvertiseManager() {
        return advertiseManager;
    }


    // Client - CMO
    public void handleLoadJobs() {
        List<Job> source = jobService.loadJobs();
        source.forEach(job -> stationService.loadStationsInJob(job));
        jobManager.loadJobs(source);
    }

    // Common

    public void start(Stage primaryStage) {
        viewManager.setPrimaryStage(primaryStage);
        viewManager.setController(this);
        viewManager.showStartUpView();
    }

    private Job loadJob(int jobID) {
        Job job = jobService.loadJob(jobID);
        stationService.loadStationsInJob(job);
        return job;
    }

    @Autowired
    public void setAdvertiseService(AdvertiseService advertiseService) {
        this.advertiseService = advertiseService;
    }

    @Autowired
    public void setJobService(JobService jobService) {
        this.jobService = jobService;
    }

    @Autowired
    public void setStationService(StationService stationService) {
        this.stationService = stationService;
    }
}
