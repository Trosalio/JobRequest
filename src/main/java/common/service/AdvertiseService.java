package common.service;

import common.model.Advertise;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ON PROGRESS!
 */

@Service
public interface AdvertiseService {
    List<Advertise> loadAdvertises();

    void addAdvertise(Advertise advertise);

    void updateAdvertise(Advertise advertise);

    void deleteAdvertise(Advertise advertise);
}
