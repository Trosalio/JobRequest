package common.service;

import common.model.Job;
import common.model.Station;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StationService {

    // Common
    List<Station> loadStations();

    List<Station> loadStationsInJob(int jobID);

    // Client - Requester
    void addStationInJob(Job job);

    void updateStationInJob(Job job);

}
