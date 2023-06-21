package com.panchenko.LogisticsApp.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.panchenko.LogisticsApp.model.*;
import com.panchenko.LogisticsApp.model.enumeration.OrderStatus;
import com.panchenko.LogisticsApp.model.enumeration.PresenceOfPumpOrCalibration;
import com.panchenko.LogisticsApp.model.enumeration.TypeOfLightProduct;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ManagerOrderResponse {
    TypeOfLightProduct typeOfLightProduct;
    double volume;
    PresenceOfPumpOrCalibration pump;
    PresenceOfPumpOrCalibration calibration;
    String contact;
    LocalDateTime uploadingDateTime;
    OrderStatus orderStatus;
    long contractorId;
    long managerId;
    long taskListId;
    long hitchId;

    public ManagerOrderResponse(ManagerOrder managerOrder) {
        typeOfLightProduct = managerOrder.getTypeOfLightProduct();
        volume = managerOrder.getVolume();
        pump = managerOrder.getPump();
        calibration = managerOrder.getCalibration();
        contact = managerOrder.getContact();
        uploadingDateTime = managerOrder.getUploadingDateTime();
        orderStatus = managerOrder.getOrderStatus();
        contractorId = managerOrder.getContractor().getId();
        managerId = managerOrder.getManager().getId();
        if (managerOrder.getTaskList() != null) {
            taskListId = managerOrder.getTaskList().getId();
        } else {
            taskListId = 0;
        }
        if (managerOrder.getTaskList() != null) {
            hitchId = managerOrder.getHitch().getId();
        } else {
            hitchId = 0;
        }
    }
}
