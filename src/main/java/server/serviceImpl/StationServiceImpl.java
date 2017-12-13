package server.serviceImpl;

import common.model.Job;
import common.model.Station;
import common.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import server.persistence.StationDAO;

import java.util.List;

/**
 * ON PROGRESS!
 */

public class StationServiceImpl implements StationService {

    private final StationDAO dao;

    @Autowired
    public StationServiceImpl(StationDAO dao) {
        this.dao = dao;
    }

    // Common
    @Override
    public List<Station> loadStations() {
        System.out.println("loading station list");
        return dao.loadAll();
    }

    @Override
    public List<Station> loadStationsInJob(Job job) {
        System.out.println("loading station list in a job");
        return dao.loadAll(job);
    }

    // Client - Requester
    @Override
    public void addStationInJob(Job job) {
        dao.insert(job);
    }

    @Override
    public void updateStationInJob(Job job) {
        dao.update(job);
    }

    @Override
    public void deleteStationInJob(Job job) {
        dao.delete(job);
    }

    // Client - Manager
    @Override
    public void addStation(Station station) {
        dao.insert(station);
    }

    @Override
    public void updateStation(Station station) {
        dao.update(station);
    }

    @Override
    public void deleteStation(Station station) {
        dao.delete(station);
    }
}
