package common.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Advertise implements Serializable {
    private String adsName;
    private String refNumber;
    private LocalDate createDate;
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

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return String.format(
                "Ads[adsName='%s'%n refNumber: %s%n createDate: %s%n job id: %s%n]"
                , adsName, refNumber, createDate, job);
    }
}
