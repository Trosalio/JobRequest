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
public class ActionController {
    private AdvertiseService advertiseService;
    private JobService jobService;
    private StationService stationService;
    private AdvertiseManager advertiseManager;
    private ViewManager viewManager;

    public ActionController(AdvertiseManager advertiseManager, ViewManager viewManager) {
        this.advertiseManager = advertiseManager;
        this.viewManager = viewManager;
    }

    public void start(Stage primaryStage) {
        viewManager.setPrimaryStage(primaryStage);
        viewManager.setHandler(this);
        viewManager.showStartUpView();
    }

    public void handleLoadAds() {
        List<Advertise> source = advertiseService.loadAdvertises();
        for (Advertise advertise : source) {
            if (advertise.getJobID() >= 0)
                advertise.setJob(loadJob(advertise.getJobID()));
        }
        advertiseManager.load(source);
    }


    public void handleAdd(AdvertiseAdapter adepter) {
        advertiseManager.add(adepter);
        advertiseService.addAdvertise(adepter.getModel());
    }

    public void handleEdit(AdvertiseAdapter adapter) {
        Advertise advertise = adapter.getModel();
        advertiseService.updateAdvertise(advertise);
        if (advertise.getJob() != null) {
            handleEdit(advertise.getJob());
        }
    }

    public void handleRemove(AdvertiseAdapter adapter) {
        advertiseManager.remove(adapter);
        Advertise removedAdvertise = adapter.getModel();
        advertiseService.deleteAdvertise(removedAdvertise);
        // In case that CASCADE ON DELETE did not work
//        if(removedAdvertise.getJob() != null){
//            handleRemove(removedAdvertise.getJob());
//        }
    }


    //---------------------------- JOB Handler -------------------------

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

    // Client - CMO
    public List<Job> handleLoadJobs() {
        List<Job> source = jobService.loadJobs();
        source.forEach(job -> stationService.loadStationsInJob(job));
        return source;
    }

    private Job loadJob(int jobID) {
        Job job = jobService.loadJob(jobID);
        stationService.loadStationsInJob(job);
        return job;
    }

    //---------------------------- Accessor Wired -------------------------

    public AdvertiseManager getAdvertiseManager() {
        return advertiseManager;
    }


    public ViewManager getViewManager() {
        return viewManager;
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
