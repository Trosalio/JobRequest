package server.serviceImpl;

import common.model.Job;
import common.model.Station;
import common.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.persistence.StationDAO;

import java.util.List;

@Service
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
    public void loadStationsInJob(Job job) {
        System.out.println("loading station list in a job");
        dao.loadStationsInJob(job);
    }

    // Client - Requester
    @Override
    public void addStationInJob(Job job) {
        System.out.println("inserting station list in a job");
        dao.insert(job);
    }

    @Override
    public void updateStationInJob(Job job) {
        System.out.println("updating station list in a job");
        dao.update(job);
    }

    @Override
    public void deleteStationInJob(Job job) {
        System.out.println("deleting station list in a job");
        dao.delete(job);
    }

    // Client - Manager
    @Override
    public void addStation(Station station) {
        System.out.println("Adding a station");
        dao.insert(station);
    }

    @Override
    public void updateStation(Station station) {
        System.out.println("updating a station");
        dao.update(station);
    }

    @Override
    public void deleteStation(Station station) {
        System.out.println("deleting a station");
        dao.delete(station);
    }
}
