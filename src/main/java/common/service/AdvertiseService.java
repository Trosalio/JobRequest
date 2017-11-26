package common.service;

import common.model.Advertise;

import java.util.List;

/**
 * ON PROGRESS!
 */

public interface AdvertiseService {
    List<Advertise> loadAdvertises();

    void addAdvertise(Advertise advertise);

    void updateAdvertise(Advertise advertise);

    void deleteAdvertise(Advertise advertise);
}
