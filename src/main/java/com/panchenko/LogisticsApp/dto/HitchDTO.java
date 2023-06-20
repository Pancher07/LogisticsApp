package com.panchenko.LogisticsApp.dto;

import com.panchenko.LogisticsApp.model.*;
import com.panchenko.LogisticsApp.model.enumeration.VehicleStatus;

import lombok.Data;

@Data
public class HitchDTO {
    private String location;

    private String comment;

    private VehicleStatus vehicleStatus;

    private TruckTractor truckTractor;

    private Trailer trailer;

    private Driver driver;

    private Project project;

    private Logistician logistician;
}
