package client.controller;

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

    private AdvertiseManager advertiseManager;
    private AdvertiseService advertiseService;
    private JobService jobService;
    private StationService stationService;
    private ViewManager viewManager;

    public MainController(AdvertiseManager advertiseManager, ViewManager viewManager) {
        this.advertiseManager = advertiseManager;
        this.viewManager = viewManager;
    }

    public void handleLoad() {
        advertiseManager.loadAdvertises(advertiseService.loadAdvertises());
    }

    public void start(Stage primaryStage) {
        viewManager.setPrimaryStage(primaryStage);
        viewManager.setController(this);
        viewManager.showStartUpView();
    }

    public void handleAdd(AdvertiseAdapter adepter) {
        advertiseService.addAdvertise(adepter.getAdaptee());
    }

    public void handleEdit(AdvertiseAdapter adepter) {
        advertiseService.updateAdvertise(adepter.getAdaptee());
    }

    public void handleRemove(AdvertiseAdapter adepter) {
        advertiseService.deleteAdvertise(adepter.getAdaptee());
    }

    public void handleAdd(Job job) {
        jobService.addJob(job);
    }

    public void handleEdit(Job job) {
        jobService.updateJob(job);
    }

    public void handleRemove(Job job) {
        jobService.deleteJob(job);
    }

    public List<Station> loadStationList() {
        return stationService.loadStations();
    }

    public List<String> loadCandidateTypeOfMedia() {
        final String file_url = "type_of_media.txt";
        List<String> typeOfMedia = new ArrayList<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file_url));
            for(String media = reader.readLine(); media != null; media = reader.readLine()){
                typeOfMedia.add(media);
            }
        } catch (IOException e) {
            System.err.println("File in "+ file_url + " not found");
            e.printStackTrace();
        }
        return typeOfMedia;
    }

    public AdvertiseManager getAdvertiseManager() {
        return advertiseManager;
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
