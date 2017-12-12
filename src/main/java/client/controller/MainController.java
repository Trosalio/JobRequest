package client.controller;

import common.model.Job;
import common.model.Station;
import common.service.AdvertiseService;
import common.service.JobService;
import common.service.StationService;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class MainController {
    private AdvertiseManager advertiseManager;
    private AdvertiseService advertiseService;
    private JobService jobService;
    private StationService stationService;
    private ViewManager viewManager;

    public MainController(AdvertiseManager advertiseManager, ViewManager viewManager){
        this.advertiseManager = advertiseManager;
        this.viewManager = viewManager;
    }

    public void handleLoad() {
        advertiseManager.loadAdvertises(advertiseService.loadAdvertises());
    }

    public void start(Stage primaryStage) {
        viewManager.setPrimaryStage(primaryStage);
        viewManager.setupStageControl(this);
        viewManager.showPrimaryStage();
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

    public List<Station> getStationsInJob(Job job) {
        return stationService.loadStationsInJob();
    }

    public List<Station> getStationList() {
        return stationService.loadStations();
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
