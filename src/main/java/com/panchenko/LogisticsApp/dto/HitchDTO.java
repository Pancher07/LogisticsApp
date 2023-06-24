package com.panchenko.LogisticsApp.dto;

import com.panchenko.LogisticsApp.model.enumeration.VehicleStatus;

import lombok.Data;

@Data
public class HitchDTO {
    private String location;

    private String comment;

    private VehicleStatus vehicleStatus;

    private Long truckTractorId;

    private Long trailerId;

    private Long driverId;

    private Long projectId;

    private Long logisticianId;
}
