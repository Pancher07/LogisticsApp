package com.panchenko.LogisticsApp.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.panchenko.LogisticsApp.model.LoadingOrder;
import com.panchenko.LogisticsApp.model.enumeration.OrderStatus;
import com.panchenko.LogisticsApp.model.enumeration.PetroleumType;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LoadingOrderResponse {
    String loadingPoint;

    PetroleumType petroleumType;

    int countOfVehicle;

    LocalDateTime loadingDateTime;

    OrderStatus orderStatus;

    long tasksListId;

    public LoadingOrderResponse(LoadingOrder loadingOrder) {
        loadingPoint = loadingOrder.getLoadingPoint();
        petroleumType = loadingOrder.getPetroleumType();
        countOfVehicle = loadingOrder.getCountOfVehicle();
        loadingDateTime = loadingOrder.getLoadingDateTime();
        orderStatus = loadingOrder.getOrderStatus();
        if (loadingOrder.getTaskList() != null) {
            tasksListId = loadingOrder.getTaskList().getId();
        } else {
            tasksListId = 0;
        }
    }
}
