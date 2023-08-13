package com.panchenko.LogisticsApp.repository;

import com.panchenko.LogisticsApp.model.Hitch;
import com.panchenko.LogisticsApp.model.enumeration.TypeOfLightProduct;
import com.panchenko.LogisticsApp.model.enumeration.VehicleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HitchRepository extends JpaRepository<Hitch, Long> {
    Optional<List<Hitch>> findAllByVehicleStatus(VehicleStatus vehicleStatus);

    Optional<List<Hitch>> findAllByLoadedWithProduct(TypeOfLightProduct typeOfLightProduct);
}
