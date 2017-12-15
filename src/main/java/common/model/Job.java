package common.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Job implements Serializable {

    private int id;
    private String JobDetail;
    private String requester;
    private String typeOfMedia;
    private ArrayList<Station> stations;
    private int quantity;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String status;
    private String refNumber;

    public Job() {
        setDefaultValue();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJobDetail() {
        return JobDetail;
    }

    public void setJobDetail(String formDetail) {
        JobDetail = formDetail;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getTypeOfMedia() {
        return typeOfMedia;
    }

    public void setTypeOfMedia(String typeOfMedia) {
        this.typeOfMedia = typeOfMedia;
    }

    public ArrayList<Station> getStations() {
        return stations;
    }

    public void setStations(ArrayList<Station> stations) {
        this.stations = stations;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    public String getListOfStations() {
        String stationList;
        if (!stations.isEmpty()) {
            stationList = stations.stream().map(Station::getCode).collect(Collectors.joining(", "));
        } else { // will never be used, but for the sake of testing whether there is a bug
            stationList = "[รายการสถานี]";
        }
        return stationList;
    }

    public void setDefaultValue() {
        id = -1;
        JobDetail = null;
        requester = null;
        typeOfMedia = null;
        stations = new ArrayList<>();
        quantity = 0;
        fromDate = null;
        toDate = null;
        status = null;
    }

    public boolean isDefault() {
        return ((id == -1) && (JobDetail == null) && (requester == null) && (typeOfMedia == null) && (stations.isEmpty()) &&
                (quantity == 0) && (fromDate == null) && (toDate == null) && (status == null));
    }


}
