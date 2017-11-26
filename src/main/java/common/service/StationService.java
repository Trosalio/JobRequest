package common.service;

import common.model.Station;

import java.util.List;

/**
 * ON PROGRESS!
 */

public interface StationService {
    List<Station> loadStations();

    void addStation(Station station);

    void updateStation(Station station);

    void deleteStation(Station station);
}
