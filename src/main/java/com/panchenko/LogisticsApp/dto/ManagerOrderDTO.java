package com.panchenko.LogisticsApp.dto;

import com.panchenko.LogisticsApp.model.enumeration.OrderStatus;
import com.panchenko.LogisticsApp.model.enumeration.PresenceOfPumpOrCalibration;
import com.panchenko.LogisticsApp.model.enumeration.TypeOfLightProduct;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ManagerOrderDTO {
    private TypeOfLightProduct typeOfLightProduct;
    private double volume;
    private PresenceOfPumpOrCalibration pump;
    private PresenceOfPumpOrCalibration calibration;
    private String contact;
    private LocalDateTime uploadingDateTime;
    private OrderStatus orderStatus;
    private Long contractorId;
    private Long managerId;
    private Long taskListId;
    private Long hitchId;
}
