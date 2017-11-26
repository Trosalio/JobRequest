//package server.service;
//
//import common.model.Advertise;
//import common.service.AdvertiseService;
//import server.persistence.AdvertiseDAO;
//
//import java.util.List;
//
///**
// * ON PROGRESS!
// */
//
//public class AdvertiseServiceImpl implements AdvertiseService {
//
//    private final AdvertiseDAO<Advertise> dao;
//
//    @Autowired
//    public AdvertiseServiceImpl(AdvertiseDAO<Advertise> dao) {
//        this.dao = dao;
//    }
//
//    @Override
//    public List<Advertise> loadAdvertises() {
//        return dao.loadAll();
//    }
//
//    @Override
//    public void addAdvertise(Advertise advertise) {
//        dao.insert(advertise);
//    }
//
//    @Override
//    public void updateAdvertise(Advertise advertise) {
//        dao.update(advertise);
//    }
//
//    @Override
//    public void deleteAdvertise(Advertise advertise) {
//        dao.delete(advertise);
//    }
//}
