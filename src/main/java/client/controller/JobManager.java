package client.controller;

import common.model.Job;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobManager {

    private final ObservableList<JobAdapter> jobList = FXCollections.observableArrayList();
    private final ObjectProperty<JobAdapter> currentJobs = new SimpleObjectProperty<>(null);

    //--------------------------- Simple CRUD Operation ---------------------------

    public void loadJobs(List<Job> source){
        source.forEach(job -> jobList.add(new JobAdapter(job)));
    }

    public void approveJob(JobAdapter adapter){
        jobList.add(adapter);
        Job job = adapter.getAdaptee();
    }

    public JobAdapter disapproveJob(int removeIndex){
        return jobList.remove(removeIndex);
    }

    //--------------------------- Accessor ----------------------------------

    public ObservableList<JobAdapter> getAdvertiseList() {
        return jobList;
    }

    public JobAdapter getCurrentAdapter() {
        return currentJobs.get();
    }

    public ObjectProperty<JobAdapter> currentAdapterProperty() {
        return currentJobs;
    }

    public void setCurrentAdapter(JobAdapter currentJob) {
        this.currentJobs.set(currentJob);
    }
}
