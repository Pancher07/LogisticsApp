package com.panchenko.LogisticsApp.dto;

import com.panchenko.LogisticsApp.model.enumeration.OrderStatus;
import com.panchenko.LogisticsApp.model.enumeration.PetroleumType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoadingOrderDTO {
    private String loadingPoint;

    private PetroleumType petroleumType;

    private int countOfVehicle;

    private LocalDateTime loadingDateTime;

    private OrderStatus orderStatus;

    private Long taskListId;
}
