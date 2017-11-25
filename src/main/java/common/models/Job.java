package common.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Project Name: MemoView
 */
public class Job implements Serializable {

    private int id;
    private String JobDetail;
    private String requester;
    private String typeOfMedia;
    private ArrayList<Station> station;
    private int quantity;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String status;

    public Job(){
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

    public ArrayList<Station> getStation() {
        return station;
    }

    public void setStation(ArrayList<Station> station) {
        this.station = station;
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

    public String getStationList() {
        String stationList;
        if(!station.isEmpty()){
            stationList = station.stream().map(Station::getCode).collect(Collectors.joining(", "));
        } else { // will never be used, but for the sake of testing whether there is a bug
            stationList = "[รายการสถานี]";
        }
        return stationList;
    }

    public void setDefaultValue() {
        JobDetail = "[ชื่อรายการ]";
        requester = "[ชื่อผู้ขอ]";
        typeOfMedia = "[ประเภทสื่อ]";
        station = new ArrayList<>();
        quantity = 0;
        fromDate = LocalDate.now();
        toDate = LocalDate.now().plusDays(1);
        status = "[รอการสร้างฟอร์ม]";
    }
}
