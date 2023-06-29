package com.panchenko.LogisticsApp.dto;

import com.panchenko.LogisticsApp.model.enumeration.TaskListAndOrderStatus;

import lombok.Data;

@Data
public class TaskListDTO {
    private TaskListAndOrderStatus status;
    private Long logisticianId;
}
