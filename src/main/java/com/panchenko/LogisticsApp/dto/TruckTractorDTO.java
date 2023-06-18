package com.panchenko.LogisticsApp.dto;

import com.panchenko.LogisticsApp.model.enumeration.PresenceOfPumpOrCalibration;
import lombok.Data;

@Data
public class TruckTractorDTO {

    private String plateNumber;

    private String model;

    private PresenceOfPumpOrCalibration pump;
}
