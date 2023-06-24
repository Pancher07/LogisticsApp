package com.panchenko.LogisticsApp.dto;

import com.panchenko.LogisticsApp.model.enumeration.TaskListStatus;

import lombok.Data;

@Data
public class TaskListDTO {
    private TaskListStatus status;
    private Long logisticianId;
}
