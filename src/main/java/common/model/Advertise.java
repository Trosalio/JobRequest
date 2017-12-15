package common.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Advertise implements Serializable {
    private String adsName;
    private String refNumber;
    private LocalDate createDate;
    private int jobID = -1;
    private Job job;

    public Advertise() {
        adsName = "";
        refNumber = "";
        createDate = LocalDate.now();
    }

    public String getAdsName() {
        return adsName;
    }

    public void setAdsName(String adsName) {
        this.adsName = adsName;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
        this.jobID = job.getId();
    }

    @Override
    public String toString() {
        return String.format(
                "Ads[adsName='%s'%n refNumber: %s%n createDate: %s%n job: %s%n]", adsName, refNumber, createDate, job);
    }
}
