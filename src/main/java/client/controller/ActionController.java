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
            Job job = jobService.loadJob(advertise.getRefNumber());
            advertise.setJob(job);
            if (job != null) {
                job.getStations().addAll(stationService.loadStationsInJob(job.getId()));
            }
        }
        advertiseManager.load(source);
    }

    public void handleAdd(AdvertiseAdapter adapter) {
        advertiseManager.add(adapter);
        advertiseService.addAdvertise(adapter.getModel());
    }

    public void handleEdit(AdvertiseAdapter adapter) {
        advertiseService.updateAdvertise(adapter.getModel());
    }

    public void handleRemove(AdvertiseAdapter adapter) {
        advertiseManager.remove(adapter);
        advertiseService.deleteAdvertise(adapter.getModel());
    }


    //---------------------------- JOB Handler -------------------------

    public List<Job> handleLoadJobs() {
        List<Job> source = jobService.loadJobs();
        source.forEach(job -> job.getStations().addAll(stationService.loadStationsInJob(job.getId())));
        return source;
    }

    public void handleAdd(Job job) {
        int id = jobService.addJob(job);
        job.setId(id);
        stationService.addStationInJob(job);
    }

    public void handleEdit(Job job) {
        jobService.updateJob(job);
        stationService.updateStationInJob(job);
    }

    public void handleRemove(Job job) {
        jobService.deleteJob(job);
    }

    public List<Station> loadStationList() {
        return stationService.loadStations();
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
