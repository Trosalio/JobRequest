package common.service;

import common.model.Station;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StationService {
    List<Station> loadStations();

    List<Station> loadStationsInJob();

    void addStation(Station station);

    void updateStation(Station station);

    void deleteStation(Station station);
}
