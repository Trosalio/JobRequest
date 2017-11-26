package common.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Advertise implements Serializable {


    private String adsName;
    private String refNumber;
    private LocalDate createDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private Job job;

    public Advertise() {
        setDefaultValue();
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public void setDefaultValue(){
        adsName = "";
        refNumber = "";
        createDate = LocalDate.now();
        startDate = LocalDate.now();
        endDate = LocalDate.now().plusDays(1);
        job = new Job();
    }
}
