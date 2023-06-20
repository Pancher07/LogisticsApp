package com.panchenko.LogisticsApp.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.panchenko.LogisticsApp.model.*;
import com.panchenko.LogisticsApp.model.enumeration.VehicleStatus;
import lombok.Value;

@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class HitchResponse {
    String location;

    String comment;

    VehicleStatus vehicleStatus;

    long truckTractorId;

    long trailerId;

    long driverId;

    long projectId;

    long logisticianId;

    public HitchResponse(Hitch hitch) {
        location = hitch.getLocation();
        comment = hitch.getComment();
        vehicleStatus = hitch.getVehicleStatus();
        truckTractorId = hitch.getTruckTractor().getId();
        trailerId = hitch.getTrailer().getId();
        driverId = hitch.getDriver().getId();
        projectId = hitch.getProject().getId();
        logisticianId = hitch.getLogistician().getId();
    }
}
