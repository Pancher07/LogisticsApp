package com.panchenko.LogisticsApp.dto;

import com.panchenko.LogisticsApp.model.enumeration.PetroleumType;
import com.panchenko.LogisticsApp.model.enumeration.PresenceOfPumpOrCalibration;
import lombok.Data;

@Data
public class TrailerDTO {
    private String plateNumber;

    private String model;

    private double volume;

    private PresenceOfPumpOrCalibration calibration;

    private PetroleumType petroleumType;
}
