package com.panchenko.LogisticsApp.service;

import com.panchenko.LogisticsApp.dto.TruckTractorDTO;
import com.panchenko.LogisticsApp.model.TruckTractor;

import java.util.List;

public interface TruckTractorService {
    TruckTractor create(TruckTractor truckTractor);

    TruckTractor readById(long id);

    TruckTractor update(TruckTractor truckTractor, TruckTractorDTO truckTractorDTO);

    void delete(long id);

    List<TruckTractor> getAll();

    TruckTractor convertToTruckTractor(TruckTractorDTO truckTractorDTO);

    TruckTractorDTO convertToTruckTractorDTO(TruckTractor truckTractor);
}
