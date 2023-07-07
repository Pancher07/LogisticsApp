package com.panchenko.LogisticsApp.dto;

import com.panchenko.LogisticsApp.model.enumeration.PresenceOfPumpOrCalibration;
import com.panchenko.LogisticsApp.model.enumeration.TaskListAndOrderStatus;
import com.panchenko.LogisticsApp.model.enumeration.TypeOfLightProduct;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ManagerOrderDTO {
    private Long id;
    private TypeOfLightProduct typeOfLightProduct;
    private Double volume;
    private PresenceOfPumpOrCalibration pump;
    private PresenceOfPumpOrCalibration calibration;
    private String contact;
    private LocalDateTime uploadingDateTime;
    private TaskListAndOrderStatus orderStatus;
    private Long contractorId;
    private Long managerId;
    private Long taskListId;
    private Long hitchId;
}
