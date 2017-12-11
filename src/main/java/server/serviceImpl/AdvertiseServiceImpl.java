package server.serviceImpl;

import common.model.Advertise;
import common.service.AdvertiseService;
import org.springframework.beans.factory.annotation.Autowired;
import server.persistence.AdvertiseDAO;

import java.util.List;

/**
 * Project Name: JobRequest
 */
public class AdvertiseServiceImpl implements AdvertiseService {

    private final AdvertiseDAO dao;

    @Autowired
    public AdvertiseServiceImpl(AdvertiseDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<Advertise> loadAdvertises() {
        return dao.loadAll();
    }

    @Override
    public void addAdvertise(Advertise advertise) {
        dao.insert(advertise);
    }

    @Override
    public void updateAdvertise(Advertise advertise) {
        dao.update(advertise);
    }

    @Override
    public void deleteAdvertise(Advertise advertise) {
        dao.delete(advertise);
    }
}
