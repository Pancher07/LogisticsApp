package com.panchenko.LogisticsApp.dto;

import com.panchenko.LogisticsApp.model.enumeration.OrderStatus;
import com.panchenko.LogisticsApp.model.enumeration.PetroleumType;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoadingOrderDTO {

    @Size(min = 2, max = 30, message = "поле не може бути пустим")
    //@NotNull(message = "поле не може бути пустим")
    private String loadingPoint;

    private PetroleumType petroleumType;

    private int countOfVehicle;

    private LocalDateTime loadingDateTime;

    private OrderStatus orderStatus;

    private Long taskListId;
}
