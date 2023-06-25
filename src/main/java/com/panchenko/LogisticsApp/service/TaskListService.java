package com.panchenko.LogisticsApp.service;

import com.panchenko.LogisticsApp.dto.TaskListDTO;
import com.panchenko.LogisticsApp.model.TaskList;

import java.util.List;

public interface TaskListService {
    TaskList create(TaskList taskList);

    TaskList readById(long id);

    TaskList update(TaskList taskList, TaskListDTO taskListDTO);

    void delete(long id);

    List<TaskList> getAll();

    TaskList convertToTaskList(TaskListDTO taskListDTO);

    TaskListDTO convertToTaskListDTO(TaskList taskList);
}
