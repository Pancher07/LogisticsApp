package com.panchenko.LogisticsApp.dto;

import com.panchenko.LogisticsApp.model.enumeration.PresenceOfAPump;
import lombok.Data;

@Data
public class TruckTractorDTO {

    private String plateNumber;

    private String model;

    private PresenceOfAPump pump;
}
