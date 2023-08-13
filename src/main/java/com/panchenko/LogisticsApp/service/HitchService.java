package com.panchenko.LogisticsApp.service;

import com.panchenko.LogisticsApp.dto.HitchDTO;
import com.panchenko.LogisticsApp.model.Hitch;
import com.panchenko.LogisticsApp.model.enumeration.TypeOfLightProduct;
import com.panchenko.LogisticsApp.model.enumeration.VehicleStatus;

import java.util.List;

public interface HitchService {
    Hitch create(Hitch hitch);

    Hitch readById(long id);

    Hitch update(Hitch hitch, HitchDTO hitchDTO);

    void delete(long id);

    List<Hitch> getAll();

    Hitch convertToHitch(HitchDTO hitchDTO);

    HitchDTO convertToHitchDTO(Hitch hitch);

    List<Hitch> getAllByVehicleStatus(VehicleStatus vehicleStatus);

    List<Hitch> getAllByLoadedWithProduct(TypeOfLightProduct typeOfLightProduct);
}
