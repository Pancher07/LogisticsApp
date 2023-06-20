package com.panchenko.LogisticsApp.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.panchenko.LogisticsApp.model.TaskList;
import com.panchenko.LogisticsApp.model.enumeration.TaskListStatus;
import lombok.Value;

@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TaskListResponse {
    TaskListStatus status;
    long logisticianId;

    public TaskListResponse(TaskList taskList) {
        status = taskList.getStatus();
        logisticianId = taskList.getLogistician().getId();
    }
}
