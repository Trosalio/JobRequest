package common.service;

import common.model.Job;
import common.model.Station;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StationService {

    // Common
    List<Station> loadStations();

    void loadStationsInJob(Job job);

    // Client - Requester
    void addStationInJob(Job job);

    void updateStationInJob(Job job);

    void deleteStationInJob(Job job);

    // Client - Manager

    void addStation(Station station);

    void updateStation(Station station);

    void deleteStation(Station station);

}
